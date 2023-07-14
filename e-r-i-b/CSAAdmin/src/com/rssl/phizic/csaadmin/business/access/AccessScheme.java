package com.rssl.phizic.csaadmin.business.access;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����
 */

public class AccessScheme
{
	private Long externalId;
	private String name;
	private String category;
	private boolean CAAdminScheme;
	private boolean VSPEmployeeScheme;
	private boolean mailManagement;

	public Long getExternalId()
	{
		return externalId;
	}

	public void setExternalId(Long externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * ������ ���
	 * @param category ���
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return ������� ����������� ����� ������ ��������������� ��
	 */
	public boolean isCAAdminScheme()
	{
		return CAAdminScheme;
	}

	/**
	 * ������  ������� ����������� ����� ������ ��������������� ��
	 * @param CAAdminScheme ������� ����������� ����� ������ ��������������� ��
	 */
	public void setCAAdminScheme(boolean CAAdminScheme)
	{
		this.CAAdminScheme = CAAdminScheme;
	}

	/**
	 * @return ������� ����������� ����� ������ ����������� ���
	 */
	public boolean isVSPEmployeeScheme()
	{
		return VSPEmployeeScheme;
	}

	/**
	 * ������ ������� ����������� ����� ������ ����������� ���
	 * @param VSPEmployeeScheme ������� ����������� ����� ������ ����������� ���
	 */
	public void setVSPEmployeeScheme(boolean VSPEmployeeScheme)
	{
		this.VSPEmployeeScheme = VSPEmployeeScheme;
	}

	/**
	 * @return ���� �� ����������� �������� � ��������
	 */
	public boolean isMailManagement()
	{
		return mailManagement;
	}

	/**
	 * ������ ������� ����������� �������� � ��������
	 * @param mailManagement ���� �� ����������� �������� � ��������
	 */
	public void setMailManagement(boolean mailManagement)
	{
		this.mailManagement = mailManagement;
	}
}
