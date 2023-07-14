package com.rssl.phizic.auth.modes;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: krenev_a $
 * @ $Revision: 40494 $
 */
public class PasswordCardConfirmRequest implements ConfirmRequest, Serializable
{
	private Integer passwordNumber;
	private String cardNumber;
	private List<String> additionInfo;
	private String errorMessage;
	private final Object MESSAGES_LOCKER = new Object();
	private List<String> messageList;
	private boolean isPreConfirm;
	private boolean errorFieldPassword;

	/**
	 * @param cardNumber номер карты паролей
	 * @param passwordNumber номер пароля в карте
	 * @param additionInfo доп. информация
	 */
	public PasswordCardConfirmRequest(String cardNumber, Integer passwordNumber, List<String> additionInfo)
	{
		this.cardNumber = cardNumber;
		this.passwordNumber = passwordNumber;
		this.additionInfo = additionInfo;
	}

	public boolean isErrorFieldPassword()
	{
		return errorFieldPassword;
	}

	public void setErrorFieldPassword(boolean error)
	{
		errorFieldPassword = error;
	}
	
	/**
	 * @return дополнительная информация
	 */
	public List<String> getAdditionInfo()
	{
		return additionInfo;
	}

	public void setAdditionInfo(List<String> additionInfo)
	{
		this.additionInfo = additionInfo;
	}

	/**
	 * @return номер карты паролей
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @return номер пароля в карте
	 */
	public Integer getPasswordNumber()
	{
		return passwordNumber;
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.card;
	}

	public boolean isError()
	{
		return !StringHelper.isEmpty(errorMessage);
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

	public boolean isPreConfirm()
	{
		return isPreConfirm;
	}

	public void setPreConfirm(boolean isPreConfirm)
	{
		this.isPreConfirm = isPreConfirm;
	}

	/*Обнулить сообщения об ошибках и информационные сообщения */
	public void resetMessages()
	{
		messageList = null;
		errorMessage = null;
	}
}