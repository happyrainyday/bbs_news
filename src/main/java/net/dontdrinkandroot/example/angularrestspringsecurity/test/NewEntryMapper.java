package net.dontdrinkandroot.example.angularrestspringsecurity.test;

import net.dontdrinkandroot.example.angularrestspringsecurity.test.NewEntry;

public interface NewEntryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NewEntry record);

    int insertSelective(NewEntry record);

    NewEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NewEntry record);

    int updateByPrimaryKey(NewEntry record);
}