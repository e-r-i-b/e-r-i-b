package com.rssl.phizic.ws.esberiblistener.mdm.types;

/**
 * @author egorova
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum ContactType
{
	HOME_PHONE("�������� �������"),
	WORK_PHONE("������� �������"),
	MOBILE_PHONE("��������� �������"),
	EMAIL("������������ email"),
	WORK_EMAIL("������� email"),
	FAX("����");

    private String description;

	ContactType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

}
