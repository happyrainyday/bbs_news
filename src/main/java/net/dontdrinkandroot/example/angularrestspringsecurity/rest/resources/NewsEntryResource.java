package net.dontdrinkandroot.example.angularrestspringsecurity.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import net.dontdrinkandroot.example.angularrestspringsecurity.BaseResult;
import net.dontdrinkandroot.example.angularrestspringsecurity.Constant;
import net.dontdrinkandroot.example.angularrestspringsecurity.ErrorCodeEnum;
import net.dontdrinkandroot.example.angularrestspringsecurity.FormatUtils;
import net.dontdrinkandroot.example.angularrestspringsecurity.JsonViews;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry.CommentDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.dao.newsentry.NewsEntryDao;
import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Role;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntry;
import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntryUser;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.NewsEntryUserVo;
import net.dontdrinkandroot.example.angularrestspringsecurity.transfer.Pagination;


@Component
@Path("/news")
public class NewsEntryResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsEntryDao newsEntryDao;
    
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private ObjectMapper mapper;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException
	{
		this.logger.info("list()");
		
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
//		List<NewsEntry> allEntries = this.newsEntryDao.findAll();
		List<NewsEntryUser> allEntries = this.newsEntryDao.getNewEntryUserList();
		return viewWriter.writeValueAsString(allEntries);
	}

	@GET
	@Path("/pager")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getNewsPageList(@QueryParam("pageIndex") String pageIndex, @QueryParam("pageSize") String pageSize){
		int index = 0;
		int size = 0;
		
		if (-1 == FormatUtils.checkIsInteger(pageIndex)) {
			index = Constant.DEFAULT_PAGE_INDEX;
		} else {
			index = Integer.parseInt(pageIndex);
		}

		if (-1 == FormatUtils.checkIsInteger(pageSize)) {
			size = Constant.DEFAULT_PAGE_SIZE;
		} else {
			size = Integer.parseInt(pageSize);
		}

		Pagination<NewsEntryUserVo> pagination = new Pagination<NewsEntryUserVo>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageIndex", (index - 1) * size);
		map.put("pageSize", size);
		List<NewsEntryUserVo> list = new ArrayList<NewsEntryUserVo>();
		NewsEntryUserVo nev = null;
		List<NewsEntryUser> newsPageList = this.newsEntryDao.getNewsPageList(map);
		for (NewsEntryUser newsEntryUser : newsPageList) {
			nev = new NewsEntryUserVo();
			nev.setCommentsNum(this.commentDao.getCommentNum(newsEntryUser.getId()));
			nev.setContent(newsEntryUser.getContent());
			nev.setDate(newsEntryUser.getDate());
			nev.setDownVotes(newsEntryUser.getDownVotes());
			nev.setUpVotes(newsEntryUser.getUpVotes());
			nev.setId(newsEntryUser.getId());
			nev.setPageViews(newsEntryUser.getPageViews());
			nev.setUser(newsEntryUser.getUser());
			nev.setUserId(newsEntryUser.getUserId());
			list.add(nev);
		}
		pagination.setContent(list);
		pagination.setCurrentPage(index);
		pagination.setPageSize(size);
		pagination.setTotalPage(this.newsEntryDao.getNewsNum());
		
//		ObjectWriter viewWriter;
//		if (this.isAdmin()) {
//			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
//		} else {
//			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
//		}
//		List<NewsEntry> allEntries = this.newsEntryDao.findAll();
//		List<NewsEntryUser> allEntries = this.newsEntryDao.getNewsPageList(map);
//		return viewWriter.writeValueAsString(pagination);
		
		return new BaseResult<>(pagination);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public NewsEntry read(@PathParam("id") Long id)
	{
		this.logger.info("read(id)");

		NewsEntry newsEntry = this.newsEntryDao.find(id);
		if (newsEntry == null) {
			throw new WebApplicationException(404);
		}
		return newsEntry;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public NewsEntry create(NewsEntry newsEntry)
	{
		this.logger.info("create(): " + newsEntry);
        
		return this.newsEntryDao.save(newsEntry);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public BaseResult<?> update(@PathParam("id") Long id, NewsEntry newsEntry)
	{
		this.logger.info("update(): " + newsEntry);
		int status = this.newsEntryDao.updateNewsEntry(newsEntry);
		if (status < 1){
			return new BaseResult<>("50000", "更新失败");
		}
		
		return new BaseResult<>();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{param}")
	public BaseResult<?> updateVoter(@PathParam("id") Long id, @PathParam("param")String param)
	{
		this.logger.info("update(): " + id + " oper: " + param);
		if (StringUtils.isBlank(param)){
			return new BaseResult<>(ErrorCodeEnum.PARAMETERS_IS_NULL_1);
		}
		
		if ("up".equals(param)){
			int status = this.newsEntryDao.updateUpVotes(id);
			if (status < 1){
				return new BaseResult<>("50000", "更新失败");
			}
		} 
		
		if ("down".equals(param)){
			int status = this.newsEntryDao.updateDownVotes(id);
			if (status < 1){
				return new BaseResult<>("50000", "更新失败");
			}
		} 
		
        if ("pageViews".equals(param)) {
			int status = this.newsEntryDao.updatePageViews(id);
			if (status < 1){
				return new BaseResult<>("50000", "更新失败");
			}
		}
		
		return new BaseResult<>();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public BaseResult<?> delete(@PathParam("id") Long id)
	{
		this.logger.info("delete(id)");

		this.newsEntryDao.delete(id);
		
		return new BaseResult<>();
	}

	private boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

}