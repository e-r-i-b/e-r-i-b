package com.rssl.phizic.auth.modes;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: moshenko $
 * @ $Revision: 43580 $
 */

public class NotConfirmRequest implements ConfirmRequest
{
	private String errorMessage;
	private final Object MESSAGES_LOCKER = new Object();
	private List<String> messageList;

	public NotConfirmRequest(){}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.none;
	}

	public boolean isError()
	{
		return !StringHelper.isEmpty(errorMessage);
	}

	public boolean isErrorFieldPassword()
	{
		return false;
	}

	public void setErrorFieldPassword(boolean error)
	{
		
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

	public boolean isPreConfirm()
	{
		return true;
	}

	public void setPreConfirm(boolean isPreConfirm)
	{		
	}

	public void resetMessages()
	{
		messageList = null;
		errorMessage = null;
	}
}