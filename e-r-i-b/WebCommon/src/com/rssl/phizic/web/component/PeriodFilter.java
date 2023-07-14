package com.rssl.phizic.web.component;

import java.util.Map;

/**
 * @author akrenev
 * @ created 31.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ���������� "������ �� ��������� �������"
 */

public interface PeriodFilter
{
	/**
	 * ����������� ��������� �������
	 * �.�. �������� �� ������������� �������� ����������:
	 * ��������, "������" -> [������-������, ������]
	 * @return ��������������� ��������� �������
	 */
	public PeriodFilter normalize();

	/**
	 * @return ��� ��������� ������� � ���� ����
	 */
	public Map<String, Object> getParameters();
}
