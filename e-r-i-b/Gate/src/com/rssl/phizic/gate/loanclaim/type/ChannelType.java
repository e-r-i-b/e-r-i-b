package com.rssl.phizic.gate.loanclaim.type;


/**
 * @author Rtischeva
 * @ created 13.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum ChannelType
{
	ATM("12"),

	ITP("13"),

	MP("16"),

	MBK("11"),

	SBOL("8"),

	GUEST("17"),

	TM("3");

	private final String code;

	private ChannelType(String code)
	{
		this.code = code;
	}

	/**
	 * @return значение в кодировке Transact SM
	 */
	public String getCode()
	{
		return code;
	}

	public static ChannelType fromCode(String code)
	{
		for (ChannelType channelType : values())
			if (channelType.getCode().equals(code))
				return channelType;

		throw new IllegalArgumentException("Неизвестен тип канала по коду: " + code);
	}
}
