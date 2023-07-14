package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Request при подтверждении операции
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class OneTimePasswordConfirmRequest implements ConfirmRequest, Serializable
{
	protected ConfirmableObject confirmableObject;
	private String errorMessage;
	private final Object MESSAGES_LOCKER = new Object();
	private List<String> messageList;
	private boolean isPreConfirm;
	private boolean requredNewPassword = false;
	private boolean isPasswordFieldError = false;

	public OneTimePasswordConfirmRequest(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public OneTimePasswordConfirmRequest() {}

	public boolean isErrorFieldPassword()
	{
		return isPasswordFieldError;
	}

	public void setErrorFieldPassword(boolean error)
	{
		isPasswordFieldError = error;
	}

	public abstract ConfirmStrategyType getStrategyType();

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

	public List<String> getAdditionInfo()
	{
		return null;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public boolean isPreConfirm()
	{
		return isPreConfirm;
	}

	public void setPreConfirm(boolean isPreConfirm)
	{
		this.isPreConfirm = isPreConfirm;
	}

	public boolean isRequredNewPassword()
	{
		return requredNewPassword;
	}

	public void setRequredNewPassword(boolean requredNewPassword)
	{
		this.requredNewPassword = requredNewPassword;
	}

	/*Обнулить сообщения об ошибках и информационные сообщения */
	public void resetMessages()
	{
		messageList = null;
		errorMessage = null;
	}
}
