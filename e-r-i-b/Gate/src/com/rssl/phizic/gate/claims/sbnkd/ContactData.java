package com.rssl.phizic.gate.claims.sbnkd;

/**
 * Блок нетипизированной информации о контакте.
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

			throw new RuntimeException("Не существует типа " + str);
		}
	}

	private ContactType contactType;
	private String contactNum;
	private String phoneOperName;

	/**
	 * @return значение контактной инфомрации.
	 */
	public String getContactNum()
	{
		return contactNum;
	}

	/**
	 * @param contactNum значение контактной инфомрации.
	 */
	public void setContactNum(String contactNum)
	{
		this.contactNum = contactNum;
	}

	/**
	 * @return Тип контактной информации.
	 */
	public ContactType getContactType()
	{
		return contactType;
	}

	/**
	 * @param contactType Тип контактной информации.
	 */
	public void setContactType(ContactType contactType)
	{
		this.contactType = contactType;
	}

	/**
	 * @return Наименование мобильного оператора
	 */
	public String getPhoneOperName()
	{
		return phoneOperName;
	}

	/**
	 * @param phoneOperName - Наименование мобильного оператора
	 */
	public void setPhoneOperName(String phoneOperName)
	{
		this.phoneOperName = phoneOperName;
	}

}
