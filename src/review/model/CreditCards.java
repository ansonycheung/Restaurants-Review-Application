package review.model;

import java.util.Date;

public class CreditCards {
	protected long cardNumber;
	protected Date expiration;
	protected String userName;

	public CreditCards(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public CreditCards(long cardNumber, Date expiration, String userName) {
		this.cardNumber = cardNumber;
		this.expiration = expiration;
		this.userName = userName;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
