package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.gate.bankroll.CardUseData;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 15.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class CardUseDataImpl implements CardUseData
{
	private String authCode;
	private Calendar authDate;
	private Calendar authTime;
	private String cardNumber;
	private String clientName;
	private String confirmClerkName;
	private Long confirmClerkNumber;
	private Calendar confirmDate;
	private Calendar confirmTime;
	private Calendar experationDate;
	private Calendar operationDate;
	private Calendar operationTime;
	private String paymasterName;
	private Long paymasterNumber;
	private Calendar paymentDate;
	private Calendar paymentTime;

	public String getAuthCode()
	{
		return authCode;
	}

	public void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public Calendar getAuthDate()
	{
		return authDate;
	}

	public void setAuthDate(Calendar authDate)
	{
		this.authDate = authDate;
	}

	public Calendar getAuthTime()
	{
		return authTime;
	}

	public void setAuthTime(Calendar authTime)
	{
		this.authTime = authTime;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getConfirmClerkName()
	{
		return confirmClerkName;
	}

	public void setConfirmClerkName(String confirmClerkName)
	{
		this.confirmClerkName = confirmClerkName;
	}

	public Long getConfirmClerkNumber()
	{
		return confirmClerkNumber;
	}

	public void setConfirmClerkNumber(Long confirmClerkNumber)
	{
		this.confirmClerkNumber = confirmClerkNumber;
	}

	public Calendar getConfirmDate()
	{
		return confirmDate;
	}

	public void setConfirmDate(Calendar confirmDate)
	{
		this.confirmDate = confirmDate;
	}

	public Calendar getConfirmTime()
	{
		return confirmTime;
	}

	public void setConfirmTime(Calendar confirmTime)
	{
		this.confirmTime = confirmTime;
	}

	public Calendar getExperationDate()
	{
		return experationDate;
	}

	public void setExperationDate(Calendar experationDate)
	{
		this.experationDate = experationDate;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public Calendar getOperationTime()
	{
		return operationTime;
	}

	public void setOperationTime(Calendar operationTime)
	{
		this.operationTime = operationTime;
	}

	public String getPaymasterName()
	{
		return paymasterName;
	}

	public void setPaymasterName(String paymasterName)
	{
		this.paymasterName = paymasterName;
	}

	public Long getPaymasterNumber()
	{
		return paymasterNumber;
	}

	public void setPaymasterNumber(Long paymasterNumber)
	{
		this.paymasterNumber = paymasterNumber;
	}

	public Calendar getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(Calendar paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	public Calendar getPaymentTime()
	{
		return paymentTime;
	}

	public void setPaymentTime(Calendar paymentTime)
	{
		this.paymentTime = paymentTime;
	}
}
