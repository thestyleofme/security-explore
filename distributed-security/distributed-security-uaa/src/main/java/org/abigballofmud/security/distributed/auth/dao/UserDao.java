package org.abigballofmud.security.distributed.auth.dao;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.abigballofmud.security.distributed.common.model.UserDTO;
import org.abigballofmud.security.distributed.common.model.PermissionDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * UserDao
 * </p>
 *
 * @author isacc 2020/5/13 1:38
 * @since 1.0
 */
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDTO getUserByUsername(String username) {
        String sql = "select id, username, password, fullname, mobile from t_user where username =?";
        List<UserDTO> list = jdbcTemplate.query(sql, new Object[]{username},
                new BeanPropertyRowMapper<>(UserDTO.class));
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public List<String> findPermissionByUserId(String userId){
        String sql = "SELECT " +
                "t_permission.id," +
                "t_permission.`code`," +
                "t_permission.description," +
                "t_permission.url " +
                "FROM " +
                "t_user_role," +
                "t_user," +
                "t_role_permission," +
                "t_permission " +
                "WHERE " +
                "t_user.id = ? "+
                "AND t_user_role.user_id = t_user.id " +
                "AND t_role_permission.role_id = t_user_role.role_id " +
                "AND t_role_permission.permission_id = t_permission.id";
        List<PermissionDTO> permissionList = jdbcTemplate.query(sql, new Object[]{userId},
                new BeanPropertyRowMapper<>(PermissionDTO.class));
        if(CollectionUtils.isEmpty(permissionList)){
            return Collections.emptyList();
        }
        return permissionList.stream().map(PermissionDTO::getCode).collect(Collectors.toList());
    }

}
