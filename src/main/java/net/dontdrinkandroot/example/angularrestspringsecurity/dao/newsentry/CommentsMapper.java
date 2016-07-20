package net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry;

import java.util.List;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.Comments;

public interface CommentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comments record);

    int insertSelective(Comments record);

    Comments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);
    
    List<Comments> getCommentsList(Long id);
    
    int updateUpVotes(Long id);
    
    Long getReplyNum(Long id);
    
    List<Comments> getReplyList(Long id);
    
    Long getCommentNum(Long id);
}