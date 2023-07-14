package com.rssl.phizicgate.esberibgate.types;

/**
 * @author egorova
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum ContactType
{
	HOME_PHONE("�������� �������"),
	JOB_PHONE("������� �������"),
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

	public static ContactType fromDescription(String description)
	{
		for(ContactType contactType : ContactType.values())
		{
			if (description.equals(contactType.description))
				return contactType;
		}
		throw new IllegalArgumentException("����������� ��� �������� \"" + description + "\"");
	}
}
