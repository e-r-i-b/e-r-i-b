package com.rssl.phizic.gate.schemes;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ����� ����
 */

public interface AccessScheme
{
	/**
	 * @return �������������
	 */
	public Long getId();

	/**
	 * @return ������� ����
	 */
	public Long getExternalId();

	/**
	 * ������ ������� ����
	 * @param externalId ������� ����
	 */
	public void setExternalId(Long externalId);

	/**
	 * @return ��������
	 */
	public String getName();

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name);

	/**
	 * @return ���
	 */
	public String getCategory();

	/**
	 * ������ ���
	 * @param category ���
	 */
	public void setCategory(String category);

	/**
	 * @return ������� ����������� ����� ������ ��������������� ��
	 */
	boolean isCAAdminScheme();

	/**
	 * ������  ������� ����������� ����� ������ ��������������� ��
	 * @param CAAdminScheme ������� ����������� ����� ������ ��������������� ��
	 */
	public void setCAAdminScheme(boolean CAAdminScheme);

	/**
	 * @return ������� ����������� ����� ������ ����������� ���
	 */
	public boolean isVSPEmployeeScheme();

	/**
	 * ������ ������� ����������� ����� ������ ����������� ���
	 * @param VSPEmployeeScheme ������� ����������� ����� ������ ����������� ���
	 */
	public void setVSPEmployeeScheme(boolean VSPEmployeeScheme);

	/**
	 * @return ���� �� ����������� �������� � ��������
	 */
	public boolean isMailManagement();
	/**
	 * ������ ������� ����������� �������� � ��������
	 * @param mailManagement ���� �� ����������� �������� � ��������
	 */
	public void setMailManagement(boolean mailManagement);

}
