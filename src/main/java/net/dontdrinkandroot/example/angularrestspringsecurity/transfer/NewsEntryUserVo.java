package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.NewsEntryUser;

/**
 * @ClassName: NewsEntryUserVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月14日下午4:06:01
 * @Version V1.00
 */
public class NewsEntryUserVo extends NewsEntryUser {

	private Long commentsNum;

	public Long getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(Long commentsNum) {
		this.commentsNum = commentsNum;
	}
	
}
