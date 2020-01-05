package cn.edu.tzc.blog.domain;

import java.util.List;

public class Page<T> {
	private int pageIndex;//当前页
	private int pageSize;//每一页的数量
	private int total;//总共的数据量
	
	private int totalPage;//总页数
	private int startIndex;//开始索引
	
	private List<T> list;//每页显示的数据
	
	//分页显示的页数
	private int start;
	private int end;
	
	/**
	 * 判断是否有上一页
	 * @return
	 */
	public boolean isHasPreviouse() {
		if(pageIndex == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否有下一页
	 * @return
	 */
	public boolean isHasNext() {
		if(pageIndex == getLast()) {
			return false;
		}
		return true;
	}
	
	
	public int getTotalPage() {
		int totalPage;
		if(0 == total%pageSize)
			totalPage = total/pageSize;
		else
			totalPage = total/pageSize+1;
		/*if(0 == totalPage)
			totalPage = 1;*/
		
		return totalPage;
	}
	
	/**
	 * 计算得到尾页
	 * @return
	 */
	public int getLast() {
		int last;
		if(0 == total%pageSize) {
			last = total/pageSize;
		}else {
			last = total/pageSize+1;
		}
		last = last<0?0:last;
		return last;
	}
	
	public Page(int pageIndex,int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	public Page(int pageIndex,int pageSize,int total) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.total = total;
		if(total%pageSize == 0) {
			this.totalPage = total/pageSize;
		}else {
			this.totalPage = total/pageSize+1;
		}
		this.startIndex = (pageIndex-1)*pageSize;
		
		//显示5页
		this.start = 1;
		if(total<pageSize) {
			this.end = 0;
		}else {
			this.end = 5;
		}
		
		
		
		if(totalPage<=5) {
			this.end = this.totalPage;
		}else {
			//总页数大于5，那么就要根据当前是第几页，来判断start和end为多少
			this.start = pageIndex-2;
			this.end = pageIndex +2;
			
			if(start<0) {
				this.start = 1;
				this.end = 5;
			}
			if(end>this.totalPage) {
				this.end = totalPage;
				this.start = end-5;
			}
		}
	
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
