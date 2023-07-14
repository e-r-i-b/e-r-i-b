package com.rssl.phizic.operations.ext.sbrf.strategy;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Moshenko
 * Date: 14.05.12
 * Time: 11:50
 * Запрос для стратегии iPasCapConfirmStrategy
 */
public class iPasCapConfirmRequest implements ConfirmRequest
{
	private String cardNumber;
	private boolean isPreConfirm;
	private boolean errorFieldPassword;
	private String errorMessage;
	private final Object MESSAGES_LOCKER = new Object();
	private List<String> messageList;
	private boolean isConfirmEnter = false;  //для настроек видимости продуктов, признак того, что подтверждали вход на страницу

	iPasCapConfirmRequest(String cardNuber)
	{
		this.cardNumber = cardNuber;
	}
	
	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.cap; 
	}

	public boolean isError()
	{
		return !StringHelper.isEmpty(errorMessage);
	}

	public boolean isErrorFieldPassword()
	{
		return errorFieldPassword;
	}

	public void setErrorFieldPassword(boolean error)
	{
		errorFieldPassword = error;
	}

	public boolean isPreConfirm()
	{
		return isPreConfirm;
	}

	public void setPreConfirm(boolean isPreConfirm)
	{
		this.isPreConfirm = isPreConfirm;
	}


	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public List<String> getMessages()
	{
		return messageList;
	}

	public void addMessage(String message)
	{
		synchronized (MESSAGES_LOCKER)
		{
			if(messageList == null)
				messageList = new ArrayList<String>();
			messageList.add(message);
		}
	}

	public List<String> getAdditionInfo()
	{
		return null;
	}

	/**
	 * Номер карты сгенерировавшей САP пароль
	 * @return
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public boolean isConfirmEnter()
	{
		return isConfirmEnter;
	}

	public void setConfirmEnter(boolean confirmEnter)
	{
		isConfirmEnter = confirmEnter;
	}

	public void resetMessages()
	{
		messageList = null;
		errorMessage = null;
	}
}
