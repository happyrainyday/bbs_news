package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

import net.dontdrinkandroot.example.angularrestspringsecurity.model.Comments;

/**
 * @ClassName: CommtsVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月14日上午11:18:05
 * @Version V1.00
 */
public class CommentsVo extends Comments {

	private Long replyNum;

	public Long getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Long replyNum) {
		this.replyNum = replyNum;
	}
	
	
}
