package com.rssl.phizic.gate.mobilebank;

/**
 * Тип карты в контексте миграции мбк
 * @author Puzikov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum MigrationCardType
{
	MAIN("M"),                  //основная мигрируемого клиента
	ADDITIONAL("MM"),           //дополнительная, которую мигрируемый клиент выпустил сам себе (держатель карты – мигрируемый клиент)
	ADDITIONAL_TO_OTHER("MO"),  //дополнительная, которую мигрируемый клиент выпустил другому клиенту (держатель карты другой клиент)
	OTHERS_ADDITIONAL("OM");    //дополнительная, которую другой клиент выпустил мигрируемому клиенту (держатель карты – мигрируемый клиент)

	private String code;

	private MigrationCardType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public static MigrationCardType fromValue(String type)
	{
		for (MigrationCardType codeType : values())
			if (type.equals(codeType.getCode()))
				return codeType;

		throw new IllegalArgumentException("Неизвестный  тип карты в контексте миграции мбк[" + type + "]");
	}
}
