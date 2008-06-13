package ${package}.model.helper;

import ${package}.Constants;

public class Pager {

	private long totalRows = 0;

	private int limit = Constants.getPagerLimit();

	private int offset = 1;

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setDefaultLimit() {
		this.limit = Constants.getPagerLimit();
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
