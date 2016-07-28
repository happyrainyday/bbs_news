package net.dontdrinkandroot.example.angularrestspringsecurity.dao.user;

import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.User;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.UserAndRole;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUsername(String name);
    
    int updateByUsernameSelective(User record);
    
    List<User> getUserList();
    
    List<UserAndRole> getUserPageList(Map<String, Object> map);
    
    List<UserAndRole> SearchUserPageList(Map<String, Object> map);
    
    int getUserNum(Map<String, Object> map);
}