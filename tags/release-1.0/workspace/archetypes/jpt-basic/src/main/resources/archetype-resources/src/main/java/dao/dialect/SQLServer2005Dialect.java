package ${package}.dao.dialect;

public class SQLServer2005Dialect implements Dialect {

	public String getLimitString(String sql, boolean hasOffset) {
		return null;
	}

	public String getLimitString(String sql, int offset, int limit) {
		return null;
	}

	public boolean supportsLimit() {
		return false;
	}

}
