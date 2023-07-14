package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: moshenko $
 * @ $Revision: 43580 $
 */
public class CryptoConfirmRequest implements ConfirmRequest, Serializable
{
	private Certificate certificate;
	private String      stringToSign;


	/**
	 * ctor
	 * @param certificate сертификат которым надо подписать
	 * @param stringToSign текст который надо подписать
	 */
	public CryptoConfirmRequest(Certificate certificate, String stringToSign)
	{
		this.certificate = certificate;
		this.stringToSign = stringToSign;
	}

	/**
	 * @return сертификат которым надо подписать
	 */
	public Certificate getCertificate()
	{
		return certificate;
	}

	/**
	 * @return текст который надо подписать
	 */
	public String getStringToSign()
	{
		return stringToSign;
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.crypto;
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