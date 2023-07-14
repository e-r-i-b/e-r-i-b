package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: moshenko $
 * @ $Revision: 43580 $
 */
public class ErrorConfirmRequest implements ConfirmRequest, Serializable
{
	private String errorMessage;
	private ConfirmStrategyType strategyType;

	/**
	 * ctor
	 * @param strategyType тип стратегии
	 * @param errorMessage сообщение
	 */
	public ErrorConfirmRequest(ConfirmStrategyType strategyType, String errorMessage)
	{
		this.errorMessage = errorMessage;
		this.strategyType = strategyType;
	}

	/**
	 * @param message номер карты паролей
	 */
	public ErrorConfirmRequest(String message)
	{
		this.errorMessage = message;
	}

	public ConfirmStrategyType getStrategyType()
	{
		return strategyType;
	}

	public boolean isErrorFieldPassword()
	{
		return false;
	}

	public void setErrorFieldPassword(boolean error)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
	
	
	public boolean isError()
	{
		return true;
	}

	/**
	 * @return сообщение об ошибке
	 */
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
		return null;
	}

	public void addMessage(String message)
	{
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
		errorMessage = null;
	}
}