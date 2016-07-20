package net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry;

import java.util.List;

import net.dontdrinkandroot.example.angularrestspringsecurity.dao.BaseDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.Comments;

/**
 * @ClassName: CommentDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月12日上午11:16:45
 * @Version V1.00
 */
public interface CommentDao  extends BaseDao<Comments, Long> {

	List<Comments> getCommentsList(Long id);
	
	int updateUpVotes(Long id);
	
	Long getReplyNum(Long id);
	
	List<Comments> getReplyList(Long id);
	
	Long getCommentNum(Long id);
	
}
