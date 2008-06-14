package ${package}.dao.dialect;

public interface Dialect {

	public String getLimitString(String sql, boolean hasOffset);

	public String getLimitString(String sql, int offset, int limit);

	public boolean supportsLimit();
}
