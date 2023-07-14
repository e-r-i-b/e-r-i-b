package com.rssl.common.forms;

/**
 * @author egorova
 * @ created 07.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class DocumentLogicMessageException extends DocumentLogicException
{
	private String bundle;

	/**
	 * @return bundle � ������� ���������� ������ ��������� �� ����������� �����
	 */
	public String getBundle()
	{
		return bundle;
	}

	public DocumentLogicMessageException(String message, String bundle)
    {
        super(message);
	    this.bundle = bundle;
    }
}
