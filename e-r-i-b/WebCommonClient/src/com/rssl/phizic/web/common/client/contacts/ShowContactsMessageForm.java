package com.rssl.phizic.web.common.client.contacts;

import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author lepihina
 * @ created 10.06.14
 * $Author$
 * $Revision$
 * ����� ����������� ������� �������������� ��������� �� ������ �������� �����
 */
public class ShowContactsMessageForm extends FilterActionForm
{
	private String message;

	/**
	 * @return ������������� ��������� �� ������ �������� �����
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message - ������������� ��������� �� ������ �������� �����
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
