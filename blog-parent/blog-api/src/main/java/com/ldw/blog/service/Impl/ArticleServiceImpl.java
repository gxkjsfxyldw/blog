package com.ldw.blog.service.Impl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldw.blog.dao.dos.Archives;
import com.ldw.blog.dao.mapper.ArticleBodyMapper;
import com.ldw.blog.dao.mapper.ArticleMapper;
import com.ldw.blog.dao.mapper.ArticleTagMapper;
import com.ldw.blog.dao.pojo.Article;
import com.ldw.blog.dao.pojo.ArticleBody;
import com.ldw.blog.dao.pojo.ArticleTag;
import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.service.*;
import com.ldw.blog.utils.UserThreadLocal;
import com.ldw.blog.vo.*;
import com.ldw.blog.vo.params.ArticleParam;
import com.ldw.blog.vo.params.PageParams;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    RedisTemplate redisTemplate;

    //文章列表  新版
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPagesize());

        IPage<Article> articleIPage = articleMapper.listArticle(
                                            page,
                                            pageParams.getCategoryId(),
                                            pageParams.getTagId(),
                                            pageParams.getYear(),
                                            pageParams.getMonth()  );
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
    }
    //文章列表  旧版
//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /* 分页查询 article 数据库表  */
//        //1.获取查询 分页的首页和最大页
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPagesize());
//        //设置查询条件
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if(pageParams.getCategoryId()!=null){//只查询对应分类的文章列表
//            //相当于 category_id=#{categoryId}
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articletaglist=new ArrayList<>();
//        if(pageParams.getTagId()!=null){
//            //加入标签 条件查询
//            //article表中，并没有tag字段，一篇文章 有多个标签
//            //article_tag 是一对多的关系： article_id tag_id
//            LambdaQueryWrapper<ArticleTag>articletag=new LambdaQueryWrapper<>();
//            articletag.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articlestags = articleTagMapper.selectList(articletag);
//            for (ArticleTag articlestag : articlestags) {
//                articletaglist.add(articlestag.getArticleId());
//            }
//            if(articletaglist.size()>0){
//                //id in(1,2,3,)
//                queryWrapper.in(Article::getId,articletaglist);
//            }
//
//        }
//        //条件1 按照时间进行排序  进行倒序的排列  orderByAsc表示 order by createe_date desc
//        queryWrapper.orderByAsc(Article::getCreateDate);
//        //条件2 是否置顶排序
//        queryWrapper.orderByAsc(Article::getWeight);
//
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        //获取数据库查询结果 ，但是不能直接返回
//        List<Article> records = articlePage.getRecords();
//        //要转换成vo对象
//        List<ArticleVo> articleVolist= copyList(records,true,true);//调用下面的函数
//        //根据前端接口要求返回相应的数据集
//        return Result.success(articleVolist);
//    }

    //首页最热文章
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据浏览量排序
        queryWrapper.orderByAsc(Article::getViewCounts);
        //提取两条数据
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit); //最上面的5条
        //select id ,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }
    //最新文章
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据浏览量排序
        queryWrapper.orderByAsc(Article::getCreateDate);
        //提取两条数据
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit); //最上面的5条
        //select id ,title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    //文章归档
    @Override
    public Result listArchives() {
        List<Archives>archivesList=articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;
    //查看文章详情
    @Override
    public Result findArticleById(Long articleId) {
        /*
            1.根据id查询文章信息
            2.根据bodyId和categoryId 去做关联查询
         */
        Article article=this.articleMapper.selectById(articleId);
        ArticleVo articlevo = cope(article, true, true,true,true);
        //查看完文章了，新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        //更新增加了此次接口的耗时如果一旦更新出问题，不能影响查看文章的操作
        //线程池可以把更新操作扔到线程池中去执行，和主线程就不相关了

        threadService.updateArticleViewCount(articleMapper,article);
        //看一下能不能从redis中读取到缓存
        String viewCount=(String)redisTemplate.opsForHash().get("view_count",String.valueOf(articleId));
        if(viewCount!=null){
            articlevo.setViewCounts(Integer.parseInt(viewCount));
        }
        return Result.success(articlevo);
    }

//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;    //缓存一致问题未解决

    //文章发布
    @Override
    public Result publish(ArticleParam articleParam) {
        //此接口，要加入到登录拦截器中
        SysUser sysUser= UserThreadLocal.get();//获取当前登录用户信息
        /*
            1.发布文章  目的 构建Article对象
            2.作者id  当前登录用户信息
            3.标签  要将标签加入到 关联表中
            4.body 内容存储  article 需要bodyId
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        //缓存新改 用pojo里面的转换为string再存储
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        //插入之后 会生成一个文章id
        this.articleMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body  插入body表
        ArticleBody articleBody  = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContenHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        //根据前端的要求返回一个键值对
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());

        return Result.success(map);
    }

    //数据转移
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVolist=new ArrayList<>();
        for (Article record : records) {
            articleVolist.add(cope(record,isTag,isAuthor,false,false));//将需要转换的值传入cope函数中进行转换
        }
        return articleVolist;
    }

    //数据转移 重载即可 ，让有需要的接口自动选择到此方法
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean iscategory) {
        List<ArticleVo> articleVolist=new ArrayList<>();
        for (Article record : records) {
            articleVolist.add(cope(record,isTag,isAuthor,isBody,iscategory));//将需要转换的值传入cope函数中进行转换
        }
        return articleVolist;
    }

    @Autowired
    private CategoryService categoryService;

    private ArticleVo cope(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean iscategory){
        //新建一个vo的对象
        ArticleVo articleVo=new ArticleVo();
        //缓存新改 用pojo里面的转换为string再存储
        articleVo.setId(String.valueOf(article.getId()));

        //将vo对象和pojo里面对象里面相同的属性的值cope到vo里面去
        BeanUtils.copyProperties(article,articleVo);
        //单独设置vo里面没有pojo属性的值
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口，都需要标签，作者信息,当有接口需要时再加上
        if(isTag){
            Long articleId=article.getId();//获取文章id
            //设置标签    需要找到到标签再文章列表的id
            articleVo.setTags(tagService.findTagByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId=article.getAuthorId();//*
            SysUser sysUser=sysUserService.findUserById(authorId);
            UserVo userVo=new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId().toString());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
//            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if(isBody){
            Long bodyid=article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyid));
        }
        if(iscategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));

        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    private ArticleBodyVo findArticleBodyById(Long bodyid) {
        ArticleBody articleBody=articleBodyMapper.selectById(bodyid);
        ArticleBodyVo articleBodyVo=new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
