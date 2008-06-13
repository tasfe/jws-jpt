package ${package}.model.helper;

import java.util.HashMap;

public class ParamsWrapper {

	public final static String SORT_ASC = "asc";

	public final static String SORT_DESC = "desc";

	private Object filters;

	private Object sorters;

	private boolean sortable;

	private Integer limit;

	private Integer offset;

	public ParamsWrapper() {
		super();
		reset();
	}

	public ParamsWrapper(ParamsWrapper params) {
		super();
		if (params != null) {
			filters = params.filters;
			sorters = params.sorters;
			limit = params.limit;
			offset = params.offset;
			sortable = params.sortable;
		}
	}

	public void reset() {
		filters = new HashMap();
		sorters = new HashMap();
		limit = null;
		offset = null;
		sortable = true;
	}

	public Object getFilters() {
		return filters;
	}

	public void setFilters(Object filters) {
		this.filters = filters;
	}

	public Object getSorters() {
		if (sortable) {
			return sorters;
		}
		return null;
	}

	public void setSorters(Object sorters) {
		this.sorters = sorters;
	}

	public int getLimit() {
		if (isPaginate() && limit == null) {
			return Integer.MAX_VALUE;
		}
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		if (isPaginate() && offset == null) {
			return 0;
		}
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isPaginate() {
		return offset != null || limit != null;
	}

}