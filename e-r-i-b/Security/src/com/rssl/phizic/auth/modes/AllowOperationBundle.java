package com.rssl.phizic.auth.modes;

import java.io.Serializable;

/**
 * User: Moshenko
 * Date: 07.06.12
 * Time: 16:48
 * Класс обертка для данных из <allow-operation> из *-authentication-modes.xml
 */
public class AllowOperationBundle implements Serializable
{
	String OperationName;
	String serviceKey;
	AllowOperationVerifier operationVerifier;

	public String getOperationName()
	{
		return OperationName;
	}

	public void setOperationName(String operationName)
	{
		OperationName = operationName;
	}

	public AllowOperationVerifier getOperationVerifier()
	{
		return operationVerifier;
	}

	public void setOperationVerifier(AllowOperationVerifier operationVerifier)
	{
		this.operationVerifier = operationVerifier;
	}

	public String getServiceKey()
	{
		return serviceKey;
	}

	public void setServiceKey(String serviceKey)
	{
		this.serviceKey = serviceKey;
	}
}
