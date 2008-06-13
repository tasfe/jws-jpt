package ${package}.webapp.action;

import java.util.List;

public class BatchAction extends BaseAction {

	private static final long serialVersionUID = -4404861425249559711L;

	private List models;

	private String name;

	public String update() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'update' method");
		}

		clearErrorsAndMessages();

		manager.update(name, models);

		addActionMessage(getText("messages.updated"));

		return SUCCESS;
	}

	public String create() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering 'create' method");
		}

		clearErrorsAndMessages();

		manager.save(name, models);

		addActionMessage(getText("messages.created"));

		return SUCCESS;
	}

}
