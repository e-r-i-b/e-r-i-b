package com.rssl.phizic.gate.monitoring.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

import java.io.Serializable;

/**
 * �������� ��� �������� ������������ ���������
 * @author komarov
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class MonitoringServiceGateStateConfigResources extends LanguageResource implements Serializable
{
	private String messageText;

	/**
	 * @return ������ ��������� �������
	 */
	public String getMessageText()
	{
		return messageText;
	}
	/**
	 * ����� ������ ��������� �������
	 * @param messageText ������ ��������� �������
	 */
	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}
}
