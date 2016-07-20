package net.dontdrinkandroot.example.angularrestspringsecurity.dao.role;

import java.util.List;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
    
    List<UserRole> selectRoleByUid(Long userId);
}