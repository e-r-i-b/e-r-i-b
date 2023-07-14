package com.rssl.phizic.gate.claims.sbnkd;

/**
 * ���� ���������������� ���������� � ��������.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ContactData
{
	public static enum ContactType
	{
		HOME("Home"), WORK("Work"), FAX("Fax");

		private final String name;

		ContactType(String name)
		{
			this.name = name;
		}

		public String getXmlName()
		{
			return name;
		}

		public static ContactType from(String str)
		{
			for (ContactType ct : values())
			{
				if (ct.getXmlName().equals(str))
					return ct;
			}

			throw new RuntimeException("�� ���������� ���� " + str);
		}
	}

	private ContactType contactType;
	private String contactNum;
	private String phoneOperName;

	/**
	 * @return �������� ���������� ����������.
	 */
	public String getContactNum()
	{
		return contactNum;
	}

	/**
	 * @param contactNum �������� ���������� ����������.
	 */
	public void setContactNum(String contactNum)
	{
		this.contactNum = contactNum;
	}

	/**
	 * @return ��� ���������� ����������.
	 */
	public ContactType getContactType()
	{
		return contactType;
	}

	/**
	 * @param contactType ��� ���������� ����������.
	 */
	public void setContactType(ContactType contactType)
	{
		this.contactType = contactType;
	}

	/**
	 * @return ������������ ���������� ���������
	 */
	public String getPhoneOperName()
	{
		return phoneOperName;
	}

	/**
	 * @param phoneOperName - ������������ ���������� ���������
	 */
	public void setPhoneOperName(String phoneOperName)
	{
		this.phoneOperName = phoneOperName;
	}

}
