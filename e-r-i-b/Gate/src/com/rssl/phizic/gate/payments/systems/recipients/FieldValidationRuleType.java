package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * @author akrenev
 * @ created 28.12.2009
 * @ $Author$
 * @ $Revision$
 */
/**
 * тип проверки поля
 */
public enum FieldValidationRuleType
{
	/**
	 * Проверка по регулярному выражению
	 */
	REGEXP;


	public static FieldValidationRuleType fromValue(String value)
	{
		return FieldValidationRuleType.valueOf(value.toUpperCase());
	}
}