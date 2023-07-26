package Client;

import Massage.Massage;

public class MsgClient extends Massage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgClient(String type, String sender, String content, String recipient) {
		super();
		this.type = type;
		this.sender = sender;
		this.content = content;
		this.recipient = recipient;
	}

}