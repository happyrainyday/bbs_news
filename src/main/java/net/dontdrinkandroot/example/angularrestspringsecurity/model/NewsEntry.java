package net.dontdrinkandroot.example.angularrestspringsecurity.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonView;

import net.dontdrinkandroot.example.angularrestspringsecurity.JsonViews;
import net.dontdrinkandroot.example.angularrestspringsecurity.entity.Entity;

public class NewsEntry implements Entity {
	private static final long serialVersionUID = -3951168315588276848L;

	private Long id;

    private String content;

    private Date date;
    
    private Long userId;
    
    private Long pageViews;

    private Long upVotes;

    private Long downVotes;

	public Long getPageViews() {
		return pageViews;
	}


	public void setPageViews(Long pageViews) {
		this.pageViews = pageViews;
	}


	public Long getUpVotes() {
		return upVotes;
	}


	public void setUpVotes(Long upVotes) {
		this.upVotes = upVotes;
	}


	public Long getDownVotes() {
		return downVotes;
	}


	public void setDownVotes(Long downVotes) {
		this.downVotes = downVotes;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public NewsEntry()
	{
		this.date = new Date();
	}


	@JsonView(JsonViews.Admin.class)
	public Long getId()
	{
		return this.id;
	}


	@JsonView(JsonViews.User.class)
	public Date getDate()
	{
		return this.date;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}


	@JsonView(JsonViews.User.class)
	public String getContent()
	{
		return this.content;
	}


	public void setContent(String content)
	{
		this.content = content;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	@Override
	public String toString()
	{
		return String.format("NewsEntry[%d, %s, %d]", this.id, this.content, this.userId);
	}

}