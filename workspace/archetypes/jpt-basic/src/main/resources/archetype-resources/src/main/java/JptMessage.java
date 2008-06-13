package ${package};

public class JptMessage extends JptException {

	private static final long serialVersionUID = 6101132086333254749L;

	public JptMessage() {
		super();
	}

	public JptMessage(String message, Throwable cause) {
		super(message, cause);
	}

	public JptMessage(String message) {
		super(message);
	}

	public JptMessage(Throwable cause) {
		super(cause);
	}

	public JptMessage(String messageKey, String defaultMessage) {
		super(messageKey, defaultMessage);
	}

}
