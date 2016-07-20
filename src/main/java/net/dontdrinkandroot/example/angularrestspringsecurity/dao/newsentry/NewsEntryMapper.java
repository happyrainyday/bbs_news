package net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry;

import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntry;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntryUser;

public interface NewsEntryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NewsEntry record);

    int insertSelective(NewsEntry record);

    NewsEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NewsEntry record);

    int updateByPrimaryKey(NewsEntry record);
    
    List<NewsEntry> getNewsEntryList();
    
    List<NewsEntryUser> findNewsEntryUserList();
    
    List<NewsEntryUser> getNewsPageList(Map<String, Object> map);
    
    int getNewsNum();
    
    int updatePageViews(Long id);
    
    int updateUpVotes(Long id);
    
    int updateDownVotes(Long id);
}