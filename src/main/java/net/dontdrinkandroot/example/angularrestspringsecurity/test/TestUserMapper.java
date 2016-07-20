package net.dontdrinkandroot.example.angularrestspringsecurity.test;

import net.dontdrinkandroot.example.angularrestspringsecurity.test.TestUser;

public interface TestUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
}