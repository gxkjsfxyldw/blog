package com.ldw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldw.blog.pojo.Admin;
import com.ldw.blog.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from ms_permission where id in(select permission_id from ms_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);
}
