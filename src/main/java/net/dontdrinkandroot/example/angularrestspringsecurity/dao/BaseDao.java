package net.dontdrinkandroot.example.angularrestspringsecurity.dao;

import java.util.List;

import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Entity;


public interface BaseDao<T extends Entity, I>
{

	List<T> findAll();


	T find(I id);


	int save(T object);


	int delete(I id);

}