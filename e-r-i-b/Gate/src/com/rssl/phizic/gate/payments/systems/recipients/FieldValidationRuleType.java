package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * @author akrenev
 * @ created 28.12.2009
 * @ $Author$
 * @ $Revision$
 */
/**
 * ��� �������� ����
 */
public enum FieldValidationRuleType
{
	/**
	 * �������� �� ����������� ���������
	 */
	REGEXP;


	public static FieldValidationRuleType fromValue(String value)
	{
		return FieldValidationRuleType.valueOf(value.toUpperCase());
	}
}