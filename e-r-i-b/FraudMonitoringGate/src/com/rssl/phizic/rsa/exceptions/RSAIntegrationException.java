package com.rssl.phizic.rsa.exceptions;


/**
 * Интеграционная ошибка с ФМ
 *
 * @author khudyakov
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 */
public class RSAIntegrationException extends RuntimeException
{
	public RSAIntegrationException(Exception e)
	{
		super(e);
	}
}
