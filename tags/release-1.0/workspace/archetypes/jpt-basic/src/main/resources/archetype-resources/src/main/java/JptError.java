package ${package};

public class JptError extends JptException {

	private static final long serialVersionUID = -2220621405627418592L;

	public JptError() {
		super();
	}

	public JptError(String message, Throwable cause) {
		super(message, cause);
	}

	public JptError(String message) {
		super(message);
	}

	public JptError(Throwable cause) {
		super(cause);
	}

	public JptError(String messageKey, String defaultMessage) {
		super(messageKey, defaultMessage);
	}

}
