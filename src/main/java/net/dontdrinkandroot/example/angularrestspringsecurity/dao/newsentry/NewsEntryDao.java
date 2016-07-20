package net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry;

import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.example.angularrestspringsecurity.dao.Dao;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntry;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link NewsEntry}s.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface NewsEntryDao extends Dao<NewsEntry, Long>
{

	public <T> List<T> getNewEntryUserList();

	public <T> List<T> getNewsPageList(Map<String, Object> map);

	public int getNewsNum();

	public int updateNewsEntry(NewsEntry newsEntry);

	public int updatePageViews(Long id);

	public int updateUpVotes(Long id);

	public int updateDownVotes(Long id);
	
}