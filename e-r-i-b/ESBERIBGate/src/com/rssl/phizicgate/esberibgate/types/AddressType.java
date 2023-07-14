package com.rssl.phizicgate.esberibgate.types;

/**
 * “ипы адресов.
 * 1 Ц адрес регистрации,
 * 2 Ц адрес проживани€,
 * 3 Ц адрес дл€ получени€ пенсии военными пенсионерами,
 * 4 - адрес дл€ почтовых уведомлений
 * 5 Ц адрес места работы
 *
 * @author egorova
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum AddressType
{
	REGISTRATION("1"),
	RESIDENCE("2"),
	MILITARY_PENSION("3"),
	POST_NOTIFICATION("4"),
	JOB("5");

	private String type;

	AddressType(String type)
	{
		this.type = type;
	}

	/**
	 * @return „исловой тип адреса
	 */
	public String getType()
	{
		return type;
	}
}
