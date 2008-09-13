package ${package}.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import ${package}.JptError;
import ${package}.JptMessage;
import ${package}.model.helper.ModelHelper;
import ${package}.model.helper.Pager;
import ${package}.model.helper.ParamsWrapper;
import ${package}.service.Manager;

public class BasicAction<T extends Manager> extends BaseAction<T> {

	private static final long serialVersionUID = 3525445612504421307L;

	protected Pager pager;

	protected String name;

	protected Object model;

	protected String modelName;

	protected Object filters;

	protected Object sorters = new HashMap();

	protected Object fields = new HashMap();

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	/**
	 * @return 分页对象。
	 */
	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	/**
	 * @return 所操作模型的名称。例如user、userByName等。
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 客户端引用模型对象所用的名称。
	 */
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Object getFilters() {
		return filters;
	}

	public void setFilters(Object filters) {
		this.filters = filters;
	}

	public Object getSorters() {
		return sorters;
	}

	public void setSorters(Object sorters) {
		this.sorters = sorters;
	}

	public Object getFields() {
		return fields;
	}

	public void setFields(Object fields) {
		this.fields = fields;
	}

	/**
	 * 列出当前页所有符合条件的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'index' method");
		}

		clean();

		ParamsWrapper parameters = buildParamsWrapper();

		long totalRows = manager.total(getListName(), parameters);
		if (pager == null) {
			pager = new Pager();
		}
		pager.setTotalRows(totalRows);

		parameters.setOffset(pager.getOffset());
		parameters.setLimit(pager.getLimit());

		if (totalRows > 0) {
			root.put(getModelsName(), getModels(parameters));
		} else {
			root.put(getModelsName(), Collections.EMPTY_LIST);
		}
		root.put("params", params);
		root.put("pager", pager);
		writeModelDependency("index");

		logRoot();

		return SUCCESS;
	}

	/**
	 * 列出所有符合条件的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String list() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'list' method");
		}

		clean();

		root.put(getModelsName(), getModels(buildParamsWrapper()));
		root.put("params", params);
		writeModelDependency("list");

		logRoot();

		return SUCCESS;
	}

	/**
	 * 显示/查看指定ID的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String show() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'show' method");
		}

		clean();

		writeModel();
		writeModelDependency("show");

		logRoot();

		return SUCCESS;
	}

	/**
	 * 初始化要修改的指定ID的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String edit() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'edit' method");
		}

		clean();

		writeModel();
		writeModelDependency("edit");

		logRoot();

		return SUCCESS;
	}

	/**
	 * 初始化一个新模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String editNew() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'editNew' method");
		}

		clean();

		writeModel();
		writeModelDependency("editNew");

		logRoot();

		return SUCCESS;
	}

	/**
	 * 保存新创建的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String create() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'create' method");
		}

		clean();

		try {
			prepareModel();
			manager.save(name, model);
			addActionMessage(getText("messages.created"));
		} catch (JptMessage e) {
			addActionMessage(getText(e.getMessageKey()));
		} catch (JptError e) {
			addActionError(getText(e.getMessageKey()));
			return INPUT;
		}

		return MSG;
	}

	/**
	 * 保存已修改的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'update' method");
		}

		clean();

		try {
			prepareModel();
			manager.update(name, model);
			addActionMessage(getText("messages.updated"));
		} catch (JptMessage e) {
			addActionMessage(getText(e.getMessageKey()));
		} catch (JptError e) {
			addActionError(getText(e.getMessageKey()));
			return INPUT;
		}

		return MSG;
	}

	/**
	 * 删除指定ID的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String destroy() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'destroy' method");
		}

		clean();

		manager.remove(name, model);

		addActionMessage(getText("messages.destroyed"));

		return MSG;
	}

	/**
	 * 删除所有选中的模型对象。
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * @throws Exception
	 */
	public String delete() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'delete' method");
		}

		clean();

		manager.remove(name, model);

		addActionMessage(getText("messages.deleted"));

		return MSG;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'execute' method");
		}
		return SUCCESS;
	}

	protected void prepareModel() {
	}

	private ParamsWrapper buildParamsWrapper() {
		ParamsWrapper parameters = new ParamsWrapper();
		parameters.setFilters(filters);
		parameters.setSorters(sorters);
		return parameters;
	}

	@SuppressWarnings("unchecked")
	private Object getModels(ParamsWrapper params) throws Exception {
		if (params.isPaginate()) {
			return new ArrayList(manager.page(getListName(), params, params
					.getOffset(), params.getLimit()));
		}
		rawdata = new ArrayList(manager.find(getListName(), params));
		return rawdata;
	}

	@SuppressWarnings("unchecked")
	private void writeModel() throws Exception {
		rawdata = manager.get(name, model);
		if (rawdata != null) {
			root.put(modelName, rawdata);
		} else {
			root.put(modelName, Collections.EMPTY_MAP);
		}
		root.put("params", params);
	}

	@SuppressWarnings("unchecked")
	private void writeModelDependency(String method) throws Exception {
		String[] dependency = ModelHelper.getModelDependency(StringUtils
				.uncapitalize(name)
				+ "." + method);
		if (dependency != null) {
			for (String depName : dependency) {
				Object depObj = new ArrayList(manager.find(depName, model));
				if (depObj != null) {
					root.put(depName, depObj);
				} else {
					root.put(depName, Collections.EMPTY_LIST);
				}
			}
		}
	}

	private String getModelsName() {
		return modelName + "s";
	}

	private String getListName() {
		if (name.equalsIgnoreCase(modelName)) {
			return name += "s";
		}
		return name;
	}

}
