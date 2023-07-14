package com.rssl.phizic.gate.depo;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����� ���� �������
 */
public enum DepoAccountState
{
	/**
	 * �������� ���� ����
	 */
	open("������"),
	/**
	 * �������� ���� ����
	 */
	closed("������");

	private String description;

	DepoAccountState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
