package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Категория клиента в правиле включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "ClientCategoryType")
@XmlEnum
public enum ClientCategoryType
{
	@XmlEnumValue("vip")
    VIP("vip"),

	@XmlEnumValue("mbc")
	MBC("mbc"),

	@XmlEnumValue("standart")
	STANDART("standart"),

	@XmlEnumValue("unimportant")
	UNIMPORTANT("unimportant");

	private final String value;

	ClientCategoryType(String v)
	{
		value = v;
    }

	public String value()
	{
		return name();
    }

	 public static ClientCategoryType fromValue(String v)
	 {
		return valueOf(v);
	 }
}
