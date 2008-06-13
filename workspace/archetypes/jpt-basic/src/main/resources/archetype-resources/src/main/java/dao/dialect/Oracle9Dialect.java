package ${package}.dao.dialect;

public class Oracle9Dialect implements Dialect {

	public String getLimitString(String sql, boolean hasOffset) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" FOR UPDATE")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer sb = new StringBuffer(sql.length() + 100);
		if (hasOffset) {
			sb
					.append("SELECT * FROM ( SELECT row_.*, rownum AS rownum_ FROM ( ");
		} else {
			sb.append("SELECT * FROM ( ");
		}
		sb.append(sql);
		if (hasOffset) {
			sb.append(" ) row_ WHERE rownum < ?) WHERE rownum_ >= ?");
		} else {
			sb.append(" ) WHERE rownum < ?");
		}

		if (isForUpdate) {
			sb.append(" FOR UPDATE");
		}

		return sb.toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" FOR UPDATE")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer sb = new StringBuffer(sql.length() + 100);
		if (offset > 1) {
			sb
					.append("SELECT * FROM ( SELECT row_.*, rownum AS rownum_ FROM ( ");
		} else {
			sb.append("SELECT * FROM ( ");
		}
		sb.append(sql);
		if (offset > 1) {
			sb.append(" ) row_ WHERE rownum < ").append(offset + limit).append(
					") WHERE rownum_ >= ").append(offset);
		} else {
			sb.append(" ) WHERE rownum < ").append(limit + 1);
		}

		if (isForUpdate) {
			sb.append(" FOR UPDATE");
		}

		return sb.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

}
