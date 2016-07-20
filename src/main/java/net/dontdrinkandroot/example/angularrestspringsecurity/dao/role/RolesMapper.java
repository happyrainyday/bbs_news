package net.dontdrinkandroot.example.angularrestspringsecurity.dao.role;

import java.util.List;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.Roles;

public interface RolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);
    
    List<Roles> getRolesList();
}