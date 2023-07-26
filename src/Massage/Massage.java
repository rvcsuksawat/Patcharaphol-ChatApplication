package Massage;

import java.io.Serializable;

public class Massage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type;
	protected String sender;
	protected String content;
	protected String recipient;

	public Massage() {
		super();
	}

	public Massage(String type, String sender, String content, String recipient) {
		super();
		this.type = type;
		this.sender = sender;
		this.content = content;
		this.recipient = recipient;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
