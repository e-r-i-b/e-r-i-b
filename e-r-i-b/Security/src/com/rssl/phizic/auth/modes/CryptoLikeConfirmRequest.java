package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 21.05.2007
 * @ $Author: moshenko $
 * @ $Revision: 43580 $
 */

public class CryptoLikeConfirmRequest implements ConfirmRequest
{
	private String operationId;
	private String sessionId;
	private String privateKey;
	private String stringToSign;

	public CryptoLikeConfirmRequest(String operationId, String sessionId, String privateKey, String stringToSign)
	{
		this.operationId = operationId;
		this.privateKey = privateKey;
		this.sessionId = sessionId;
		this.stringToSign = stringToSign;
	}

	public String getPrivateKey()
	{
		return privateKey;
	}

	public String getStringToSign()
	{
		return stringToSign;
	}

	public String getOperationId()
	{
		return operationId;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.sbrf_custom;
	}

	public boolean isError()
	{
		return false;
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
		return null;
	}

	public void setErrorMessage(String errorMessage)
	{		
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
	}
}