package model.entities;

import java.util.Date;

import model.entities.enums.PaymentStatus;
import model.entities.enums.PaymentType;

public class Payment {
	private float totalAmount;
	private PaymentStatus status;
	private PaymentType paymentType;
	private Date paymentTime;
	
	private Client client;
	
	public Payment(Payment payment) {
		this.totalAmount = payment.getTotalAmount();
		this.status = payment.getStatus();
		this.paymentType = payment.getPaymentType();
		this.paymentTime = payment.getPaymentTime();
		this.client = payment.getClient();
	}
	
	public Payment(float totalAmount, PaymentType paymentType, Date paymentTime, Client client) {
		this.totalAmount = totalAmount;
		status = PaymentStatus.WAITING;
		this.paymentType = paymentType;
		this.paymentTime = paymentTime;
		this.client = client;
	}
	
	public float getTotalAmount() {
		return totalAmount;
	}
	
	public PaymentStatus getStatus() {
		return status;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	public Date getPaymentTime() {
		return paymentTime;
	}
	
	public Client getClient() {
		return new Client(client);
	}
	
	public void makePayment() {
		this.status = PaymentStatus.UNDER_REVIEW;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + Float.floatToIntBits(totalAmount);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (paymentType != other.paymentType)
			return false;
		if (status != other.status)
			return false;
		if (Float.floatToIntBits(totalAmount) != Float.floatToIntBits(other.totalAmount))
			return false;
		return true;
	}
}