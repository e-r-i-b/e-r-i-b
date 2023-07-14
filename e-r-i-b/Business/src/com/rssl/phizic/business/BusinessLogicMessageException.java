package com.rssl.phizic.business;

import com.rssl.common.forms.DocumentLogicMessageException;

/**
 *
 * ���������� ��� ��������, ����� ��������� ���������� ��� ���� �������
 *
 * @author egorova
 * @ created 07.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class BusinessLogicMessageException extends BusinessLogicException
{
	private String bundle;

	/**
	 * @return bundle � ������� ���������� ������ ��������� �� ����������� �����
	 */
	public String getBundle()
	{
		return bundle;
	}

	public BusinessLogicMessageException(DocumentLogicMessageException e)
    {
        super(e.getMessage());
	    this.bundle = e.getBundle();
    }

	public BusinessLogicMessageException(String message, String bundle)
    {
        super(message);
	    this.bundle = bundle;
    }
}
