package com.rssl.phizic.logging.confirm;

import java.io.Serializable;
import java.util.Calendar;

/**
 * «апрос на ввод парол€
 * @author lukina
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class OperationConfirmLogEntry implements Serializable
{
	private Long id;
	private Calendar date;
	private String sessionId;
	private String operationUID;
	private ConfirmState state;
	private String message; //текст смс
	private String recipient;  //телефон или карта, по которой отправлено сообщение
	private String cardNumber; //номер чека
	private String passwordNumber;  //номер парол€
	private ConfirmType type;  //способ подтверждени€ (смс/чек)
	private boolean checkIMSI; //Ѕыла ли проверка IMSI
	private String confirmCode;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date - дата
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public ConfirmState getState()
	{
		return state;
	}

	public void setState(ConfirmState state)
	{
		this.state = state;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getPasswordNumber()
	{
		return passwordNumber;
	}

	public void setPasswordNumber(String passwordNumber)
	{
		this.passwordNumber = passwordNumber;
	}

	public ConfirmType getType()
	{
		return type;
	}

	public void setType(ConfirmType type)
	{
		this.type = type;
	}

	public boolean isCheckIMSI()
	{
		return checkIMSI;
	}

	public void setCheckIMSI(boolean checkIMSI)
	{
		this.checkIMSI = checkIMSI;
	}

	/**
	 * ”становить использованный код подтвереждени€
	 * @param confirmCode код подтверждени€
	 */
	public void setConfirmCode(String confirmCode)
	{
		this.confirmCode = confirmCode;
	}

	/**
	 *
	 * @return использованный код подтвереждени€(введенный пользователем)
	 */
	public String getConfirmCode()
	{
		return confirmCode;
	}
}
