package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Условие доступности продукта в правиле включения видимости по умолчанию
 * @author Rtischeva
 * @ created 02.12.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "BankrollProductAvailabilityType")
@XmlEnum
public enum BankrollProductAvailabilityType
{
	@XmlEnumValue("available")
    AVAILABLE("available"),

	@XmlEnumValue("unavailable")
	UNAVAILABLE("unavailable"),

	@XmlEnumValue("unimportant")
	UNIMPORTANT("unimportant");

	private final String value;

	BankrollProductAvailabilityType(String v)
	{
     value = v;
    }

	public String value()
	{
     return name();
    }

	 public static BankrollProductAvailabilityType fromValue(String v)
	 {
	     return valueOf(v);
	 }
}
