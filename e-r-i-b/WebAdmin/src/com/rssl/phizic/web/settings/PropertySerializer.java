package com.rssl.phizic.web.settings;

/**
 * ������������ �������� ��������
 * @author gladishev
 * @ created 07.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PropertySerializer
{
	/**
	 * ������������� �������� ���������
	 * @param value - ��������
	 * @return ��������� ������������� ���������
	 */
	public abstract String serialise(Object value);
}
