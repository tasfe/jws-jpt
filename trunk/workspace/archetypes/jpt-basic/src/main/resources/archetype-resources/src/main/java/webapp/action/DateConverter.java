package ${package}.webapp.action;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import ${package}.util.DateUtil;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.util.TypeConversionException;

public class DateConverter extends StrutsTypeConverter {

	public Object convertFromString(Map ctx, String[] value, Class arg2) {
		if (value[0] == null || value[0].trim().equals("")) {
			return null;
		}

		try {
			return DateUtil.str2date(value[0]);
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new TypeConversionException(pe.getMessage());
		}
	}

	public String convertToString(Map ctx, Object data) {
		return DateUtil.date2str((Date) data);
	}
}