package net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.Comments;

/**
 * @ClassName: JpaCommentDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月12日下午1:39:31
 * @Version V1.00
 */
public class JpaCommentDao implements CommentDao {

	@Autowired
	CommentsMapper commentsMapper;

	/* 
	 * @Name: findAll
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月12日下午2:53:15
	 * @return
	 */
	@Override
	public List<Comments> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @Name: find
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月12日下午2:53:15
	 * @param id
	 * @return
	 */
	@Override
	public Comments find(Long id) {
		return this.commentsMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @Name: save
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月12日下午2:53:15
	 * @param object
	 * @return
	 */
	@Override
	public int save(Comments object) {
		return this.commentsMapper.insertSelective(object);
	}

	/* 
	 * @Name: delete
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月12日下午2:53:15
	 * @param id
	 * @return
	 */
	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* 
	 * @Name: getCommentsList
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月13日上午10:42:12
	 * @param id
	 * @return
	 */
	@Override
	public List<Comments> getCommentsList(Long id) {
		return this.commentsMapper.getCommentsList(id);
	}

	/* 
	 * @Name: updateUpVotes
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月13日下午2:53:48
	 * @param id
	 * @return
	 */
	@Override
	public int updateUpVotes(Long id) {
		return this.commentsMapper.updateUpVotes(id);
	}

	/* 
	 * @Name: getReplyNum
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月14日上午11:00:19
	 * @param id
	 * @return
	 */
	@Override
	public Long getReplyNum(Long id) {
		
		return this.commentsMapper.getReplyNum(id);
	}

	/* 
	 * @Name: getReplyList
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月14日下午1:32:54
	 * @param id
	 * @return
	 */
	@Override
	public List<Comments> getReplyList(Long id) {
		return this.commentsMapper.getReplyList(id);
	}

	/* 
	 * @Name: getCommentNum
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月14日下午4:09:54
	 * @param id
	 * @return
	 */
	@Override
	public Long getCommentNum(Long id) {
		return this.commentsMapper.getCommentNum(id);
	}
	
	

}
