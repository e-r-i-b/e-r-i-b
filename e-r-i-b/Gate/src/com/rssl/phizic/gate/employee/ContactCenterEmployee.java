package com.rssl.phizic.gate.employee;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� ������
 */

public interface ContactCenterEmployee
{
	/**
	 * @return �������������
	 */
	public Long getId();
	/**
	 * @return �������������
	 */
	public String getExternalId();

	/**
	 * @return ���
	 */
	public String getName();

	/**
	 * @return �������������
	 */
	public String getDepartment();

	/**
	 * @return ��������
	 */
	public String getArea();
}
