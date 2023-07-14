package com.rssl.phizic.csaadmin.business.employee;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� ������
 */

public class ContactCenterEmployee
{
	private Long id;
	private String externalId;
	private String name;
	private String department;
	private String area;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������� �������������
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * ������ ������� �������������
	 * @param externalId �������������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ���
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ���
	 * @param name ���
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return �������������
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * ������ �������������
	 * @param department �������������
	 */
	public void setDepartment(String department)
	{
		this.department = department;
	}

	/**
	 * @return ��������
	 */
	public String getArea()
	{
		return area;
	}

	/**
	 * ������ ��������
	 * @param area ��������
	 */
	public void setArea(String area)
	{
		this.area = area;
	}
}
