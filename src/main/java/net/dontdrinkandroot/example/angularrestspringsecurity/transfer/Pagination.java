package net.dontdrinkandroot.example.angularrestspringsecurity.transfer;

import java.util.List;

/**
 * @ClassName: Pagination
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tjf
 * @date 2016年7月5日下午8:20:00
 * @Version V1.00
 */
public class Pagination <T>{

	private int currentPage;
	
	private int totalPage;
	
	private int pageSize;
	
	private List<T> content;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
