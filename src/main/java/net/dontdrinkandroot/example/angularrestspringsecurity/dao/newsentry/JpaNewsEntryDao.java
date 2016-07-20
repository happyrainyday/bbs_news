package net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntry;


/**
 * JPA Implementation of a {@link NewsEntryDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaNewsEntryDao implements NewsEntryDao
{

	@Autowired
	NewsEntryMapper newsEntryMapper;
    
	@Override
	@Transactional(readOnly = true)
	public List<NewsEntry> findAll()
	{
		return this.newsEntryMapper.getNewsEntryList();
	}

	/* 
	 * @Name: find
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午4:59:39
	 * @param id
	 * @return
	 */
	@Override
	public NewsEntry find(Long id) {
		return this.newsEntryMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @Name: save
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午4:59:39
	 * @param newsEntry
	 * @return
	 */
	@Override
	public NewsEntry save(NewsEntry newsEntry) {
		newsEntryMapper.insertSelective(newsEntry);
		return null;
	}

	/* 
	 * @Name: delete
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年6月15日下午4:59:39
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		this.newsEntryMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @Name: getNewEntryUserList
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月2日上午11:49:58
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> getNewEntryUserList() {
		return (List<T>)this.newsEntryMapper.findNewsEntryUserList();
	}

	/* 
	 * @Name: getNewsPageList
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月7日下午6:00:52
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getNewsPageList(Map<String, Object> map) {
		return (List<T>)this.newsEntryMapper.getNewsPageList(map);
	}

	/* 
	 * @Name: getNewsNum
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月7日下午6:05:20
	 * @return
	 */
	@Override
	public int getNewsNum() {
	
		return this.newsEntryMapper.getNewsNum();
	}

	/* 
	 * @Name: updateNewsEntry
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月9日下午3:15:35
	 * @param newsEntry
	 * @return
	 */
	@Override
	public int updateNewsEntry(NewsEntry newsEntry) {
		return this.newsEntryMapper.updateByPrimaryKeySelective(newsEntry);
	}

	/* 
	 * @Name: updatePageViews
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月11日下午12:05:52
	 * @param map
	 * @return
	 */
	@Override
	public int updatePageViews(Long id) {
		return this.newsEntryMapper.updatePageViews(id);
	}

	/* 
	 * @Name: updateUpVotes
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月11日下午12:05:52
	 * @param map
	 * @return
	 */
	@Override
	public int updateUpVotes(Long id) {
		return this.newsEntryMapper.updateUpVotes(id);
	}

	/* 
	 * @Name: updateDownVotes
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年7月11日下午12:05:52
	 * @param map
	 * @return
	 */
	@Override
	public int updateDownVotes(Long id) {
		return this.newsEntryMapper.updateDownVotes(id);
	}

}
