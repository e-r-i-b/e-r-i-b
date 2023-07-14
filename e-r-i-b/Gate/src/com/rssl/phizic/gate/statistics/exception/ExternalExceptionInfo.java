package com.rssl.phizic.gate.statistics.exception;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.System;

/**
 * @author akrenev
 * @ created 31.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ������
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
	 * @return ���� ���������
	 */
	public String getMessageKey()
	{
		return messageKey;
	}

	/**
	 * ������ ���� ���������
	 * @param messageKey ���� ���������
	 */
	public void setMessageKey(String messageKey)
	{
		this.messageKey = messageKey;
	}

	/**
	 * @return ��� ������ ���������
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * ������ ��� ������ ���������
	 * @param errorCode ��� ������ ���������
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return ����� ������
	 */
	public String getErrorText()
	{
		return errorText;
	}

	/**
	 * ������ ����� ������
	 * @param errorText ����� ������
	 */
	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}

	/**
	 * @return �������� ������
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * ������ �������� ������
	 * @param detail �������� ������
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return �������, ���������� �������������� ��������
	 */
	public String getSystem()
	{
		return system;
	}

	/**
	 * ������ �������, ���������� �������������� ��������
	 * @param system �������
	 */
	public void setSystem(String system)
	{
		this.system = system;
	}

	/**
	 * @return �������, � ������ �������� ��������� ����������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ������ �������, � ������ �������� ��������� ����������
	 * @param tb �������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ����������, � ������ �������� ��������� ����������
	 */
	public Application getApplication()
	{
		return application;
	}

	/**
	 * ������ ����������, � ������ �������� ��������� ����������
	 * @param application ����������
	 */
	public void setApplication(Application application)
	{
		this.application = application;
	}

	/**
	 * @return ����, � ������ �������� ��������� ����������
	 */
	public System getGate()
	{
		return gate;
	}

	/**
	 * ������ ����, � ������ �������� ��������� ����������
	 * @param gate ����
	 */
	public void setGate(System gate)
	{
		this.gate = gate;
	}
}
