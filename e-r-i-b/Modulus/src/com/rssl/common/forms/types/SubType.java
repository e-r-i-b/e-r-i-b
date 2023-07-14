package com.rssl.common.forms.types;

/**
 * ƒоп. тип пол€ (динамическое, статичеческое)
 *
 * @author khudyakov
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */
public enum SubType
{
	DINAMIC("dinamic"),                //динамическое пол€
	STATIC("static");                  //статическое

	private String value;

	SubType(String value)
	{
		this.value = value;
	}

	/**
	 * ¬ернуть значение енума по строке
	 * @param subType тип
	 * @return значение
	 */
	public static SubType fromValue(String subType)
	{
		if (DINAMIC.getValue().equals(subType))
		{
			return DINAMIC;
		}
		if (STATIC.getValue().equals(subType))
		{
			return STATIC;
		}
		throw new IllegalArgumentException("Ќекорректный тип SubType" + subType);
	}

	/**
	 * @return занчение
	 */
	public String getValue()
	{
		return value;
	}
}
