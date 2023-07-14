package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author basharin
 * @ created 23.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardClientConfirmRequest implements ConfirmRequest
{
	private List<String> additionInfo;
	private String errorMessage;
	private final Object MESSAGES_LOCKER = new Object();
	private List<String> messageList;
	private boolean isPreConfirm;
	private boolean errorFieldPassword;

	public CardClientConfirmRequest()
	{
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

	public void resetMessages()
	{
		errorMessage = null;
		messageList = null;  
	}
}
