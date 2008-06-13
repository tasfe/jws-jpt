package ${package};

public class JptException extends Exception {

	private static final long serialVersionUID = 2365431397337460109L;

	private String messageKey;

	public JptException() {
		super();
	}

	public JptException(String message) {
		super(message);
	}

	public JptException(String messageKey, String defaultMessage) {
		super(defaultMessage);
		this.messageKey = messageKey;
	}

	public JptException(Throwable cause) {
		super(cause);
	}

	public JptException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
}
