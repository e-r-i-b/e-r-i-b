package com.rssl.phizic.gate.crm;

/**
 * User: Moshenko
 * Date: 12.12.14
 * Time: 17:38
 * Источник заявки, он же Канал создания заявки
 */
public enum ChannelType
{
	WEB("1"),
	TM_INTERNAL("2"),
	TM_EXTERNAL("3"),
	VSP("4"),
	ERIB_SBOL("5"),
	ERIB_MB("6"),
	ERIB_GEST("7");

	private String type;

	ChannelType(String type)
	{
		this.type = type;
	}

	/**
	 * Привести струку к типу енума
	 *
	 * @param value тип
	 * @return значение енума
	 */
	public static ChannelType fromValue(String value)
	{
		if (WEB.getType().equals(value))
		{
			return WEB;
		}
		if (TM_INTERNAL.getType().equals(value))
		{
			return TM_INTERNAL;
		}
		if (TM_EXTERNAL.getType().equals(value))
		{
			return TM_EXTERNAL;
		}
		if (VSP.getType().equals(value))
		{
			return VSP;
		}
		if (ERIB_SBOL.getType().equals(value))
		{
			return ERIB_SBOL;
		}
		if (ERIB_MB.getType().equals(value))
		{
			return ERIB_MB;
		}
		if (ERIB_GEST.getType().equals(value))
		{
			return ERIB_GEST;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * @return тип
	 */
	public String getType()
	{
		return type;
	}

}
