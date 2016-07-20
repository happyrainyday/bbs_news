package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

/**
 * @ClassName: CommentDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月11日下午7:01:40
 * @Version V1.00
 */
public class CommentDto {
	
	private String comment;

	private Long topicId;

	private Long userId;

	private Long replyId;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

}
