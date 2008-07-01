package ${package}.model;

import java.io.Serializable;
import java.util.Date;

import ${package}.util.DateUtil;

public class PublicObject extends BaseObject implements Serializable {

	private static final long serialVersionUID = -3940529561796846033L;

	private Date[] dateValues;

	private Date[] dateFromValues;

	private Date[] dateToValues;

	private Integer[] intValues;

	private Double[] numValues;

	private String[] strValues;

	public Date[] getDateValues() {
		return dateValues;
	}

	public void setDateValues(Date[] dateValues) {
		this.dateValues = dateValues;
	}

	public Date[] getDateFromValues() {
		if (dateFromValues == null && dateValues != null) {
			dateFromValues = new Date[dateValues.length];
			for (int i = 0; i < dateValues.length; i++) {
				dateFromValues[i] = DateUtil.getDayFrom(dateValues[i]);
			}
		}
		return dateFromValues;
	}

	public void setDateFromValues(Date[] dateFromValues) {
		this.dateFromValues = dateFromValues;
	}

	public Date[] getDateToValues() {
		if (dateToValues == null && dateValues != null) {
			dateToValues = new Date[dateValues.length];
			for (int i = 0; i < dateValues.length; i++) {
				dateToValues[i] = DateUtil.getDayTo(dateValues[i]);
			}
		}
		return dateToValues;
	}

	public void setDateToValues(Date[] dateToValues) {
		this.dateToValues = dateToValues;
	}

	public Integer[] getIntValues() {
		return intValues;
	}

	public void setIntValues(Integer[] intValues) {
		this.intValues = intValues;
	}

	public Double[] getNumValues() {
		return numValues;
	}

	public void setNumValues(Double[] numValues) {
		this.numValues = numValues;
	}

	public String[] getStrValues() {
		return strValues;
	}

	public void setStrValues(String[] strValues) {
		this.strValues = strValues;
	}

}
