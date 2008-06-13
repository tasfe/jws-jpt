package ${package}.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.ActionValidatorManagerFactory;
import com.opensymphony.xwork2.validator.FieldValidator;
import com.opensymphony.xwork2.validator.Validator;

public class ModelValidatorAction extends ActionSupport {

	private static final long serialVersionUID = -577272285160720452L;

	private String modelPackageName = "${package}.model";

	private String model;

	public String getModelPackageName() {
		return modelPackageName;
	}

	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return 校验器。
	 */
	public List getValidators() {
		List<Validator> validators = new ArrayList<Validator>();
		Class modelClass = getActionClass();
		if (modelClass != null) {
			for (Validator validator : ActionValidatorManagerFactory
					.getInstance().getValidators(modelClass, null)) {
				if (validator instanceof FieldValidator) {
					validators.add(validator);
				}
			}
		}
		return validators;
	}

	private Class getActionClass() {
		try {
			return ClassUtils.forName(modelPackageName + "."
					+ StringUtils.capitalize(model));
		} catch (Exception e) {
		}
		return null;
	}

}
