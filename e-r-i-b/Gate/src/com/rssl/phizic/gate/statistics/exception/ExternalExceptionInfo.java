package com.rssl.phizic.gate.statistics.exception;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.System;

/**
 * @author akrenev
 * @ created 31.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * информация об ошибке
 */

public class ExternalExceptionInfo
{
	private String messageKey;
	private String errorCode;
	private String errorText;
	private String detail;
	private String system;
	private Application application;
	private String tb;
	private System gate;

	/**
	 * @return ключ сообщения
	 */
	public String getMessageKey()
	{
		return messageKey;
	}

	/**
	 * задать ключ сообщения
	 * @param messageKey ключ сообщения
	 */
	public void setMessageKey(String messageKey)
	{
		this.messageKey = messageKey;
	}

	/**
	 * @return код ошибки сообщения
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * задать код ошибки сообщения
	 * @param errorCode код ошибки сообщения
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return текст ошибки
	 */
	public String getErrorText()
	{
		return errorText;
	}

	/**
	 * задать текст ошибки
	 * @param errorText текст ошибки
	 */
	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}

	/**
	 * @return описание ошибки
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * задать описание ошибки
	 * @param detail описание ошибки
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return система, породившая исключительную ситуацию
	 */
	public String getSystem()
	{
		return system;
	}

	/**
	 * задать систему, породившую исключительную ситуацию
	 * @param system система
	 */
	public void setSystem(String system)
	{
		this.system = system;
	}

	/**
	 * @return тербанк, в рамках которого появилось исключение
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * задать тербанк, в рамках которого появилось исключение
	 * @param tb тербанк
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return приложение, в рамках которого появилось исключение
	 */
	public Application getApplication()
	{
		return application;
	}

	/**
	 * задать приложение, в рамках которого появилось исключение
	 * @param application приложение
	 */
	public void setApplication(Application application)
	{
		this.application = application;
	}

	/**
	 * @return шлюз, в рамках которого появилось исключение
	 */
	public System getGate()
	{
		return gate;
	}

	/**
	 * задать шлюз, в рамках которого появилось исключение
	 * @param gate шлюз
	 */
	public void setGate(System gate)
	{
		this.gate = gate;
	}
}
