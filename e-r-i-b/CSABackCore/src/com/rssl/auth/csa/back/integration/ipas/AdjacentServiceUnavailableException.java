package com.rssl.auth.csa.back.integration.ipas;

/**
 * @author krenev
 * @ created 13.09.2013
 * @ $Author$
 * @ $Revision$
 * »сключение, сигнализирующее о недоступности смежных c ipas сервисов(в частности way4).
 * »сключение содержит код отказа от iPas, позвол€ющий детектировать причину и сам смежный сервис.
 *
 */

public class AdjacentServiceUnavailableException extends ServiceUnavailableException
{
	private final String errorCode;

	/**
	 *  онструктор
	 * @param errorCode код отказа
	 */
	public AdjacentServiceUnavailableException(String errorCode)
	{
		super("ѕолучен ответ о недоступности смежных сервисов от iPas: " + errorCode);
		this.errorCode = errorCode;
	}

	/**
	 * @return код отказа от iPas
	 */
	public String getErrorCode()
	{
		return errorCode;
	}
}
