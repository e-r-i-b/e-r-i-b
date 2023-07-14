package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.Application;

/**
 * @author Nady
 * @ created 20.02.2015
 * @ $Author$
 * @ $Revision$
 */
public enum ChannelPFRRegAApproveType
{
	SBOL("2"),

	MP_SBOL("3"),

	US("5"),

	MB("6");

	private final String code;

	private ChannelPFRRegAApproveType(String code)
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

	public static ChannelPFRRegAApproveType fromCode(String code)
	{
		for (ChannelPFRRegAApproveType channelType : values())
			if (channelType.getCode().equals(code))
				return channelType;

		throw new IllegalArgumentException("Неизвестен тип канала по коду: " + code);
	}

	public static ChannelPFRRegAApproveType fromApplication(Application application)
	{
		switch (application)
		{
			case PhizIC:
				return SBOL;

			case ErmbSmsChannel:
				return MB;

			case atm:
				return US;

			case mobile5:
			case mobile6:
			case mobile7:
			case mobile8:
			case mobile9:
				return MP_SBOL;
			default:
				return SBOL;
		}
	}
}
