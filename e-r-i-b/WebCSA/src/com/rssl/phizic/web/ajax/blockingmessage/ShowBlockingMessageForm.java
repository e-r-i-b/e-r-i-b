package com.rssl.phizic.web.ajax.blockingmessage;

import org.apache.struts.action.ActionForm;

/**
 * ����� ������������ ��������� �� ����������� �����.
 * @author mihaylov
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowBlockingMessageForm extends ActionForm
{
	private String blockingMessage;

	/**
	 * @return ��������� � ���������� �������
	 */
	public String getBlockingMessage()
	{
		return blockingMessage;
	}

	/**
	 * ���������� ��������� � ����������
	 * @param blockingMessage - ���������
	 */
	public void setBlockingMessage(String blockingMessage)
	{
		this.blockingMessage = blockingMessage;
	}
}
