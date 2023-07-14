package com.rssl.phizic.gate.loans;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ��������
 */
public enum ContractType
{
	/**
	 * ��������������
	 */
	guarantee("1"),

	/**
	 * ����� ������ �����
	 */
	pledgeSecurity("2"),

	/**
	 * ����� ���������
	 */
	wadset("3"),

	/**
	 * ����� ����.��������
	 */
	pledgePrecious("4");


	private String value;

	ContractType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static ContractType fromValue(String value)
	{
		if( value.equals(guarantee.value)) return guarantee;
		if( value.equals(wadset.value)) return wadset;
		if( value.equals(pledgePrecious.value)) return pledgePrecious;
		if( value.equals(pledgeSecurity.value)) return pledgeSecurity;
		throw new IllegalArgumentException("����������� ��� ��������� [" + value + "]");
	}
}
