package com.cjlr.entity;

/**
 * 分页
 */
public class PagingBean {
	private Integer pageCount;      // 总记录数
	private Integer itemCount = 10; // 每页记录数
	private Integer pageNum = 1;    // 当前页数
	private Integer maxPageNum;     // 最大页数
	private Integer prePageNum;     // 上一页
	private Integer nextPageNum;    // 下一页

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;

		this.maxPageNum = pageCount / itemCount + (pageCount % itemCount > 0 ? 1 : 0);
		this.prePageNum = pageNum - 1 < 1 ? 1 : pageNum - 1;
		this.nextPageNum = pageNum + 1 > maxPageNum ? maxPageNum : pageNum + 1;

	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		try {
			if (itemCount != null && !"".equals(itemCount) && Integer.valueOf(itemCount) > 0) {
				this.itemCount = Integer.valueOf(itemCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		try {
			if (pageNum != null && !"".equals(pageNum) && Integer.valueOf(pageNum) > 0) {
				this.pageNum = Integer.valueOf(pageNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Integer getMaxPageNum() {
		return maxPageNum;
	}

	public Integer getPrePageNum() {
		return prePageNum;
	}

	public Integer getNextPageNum() {
		return nextPageNum;
	}

}
