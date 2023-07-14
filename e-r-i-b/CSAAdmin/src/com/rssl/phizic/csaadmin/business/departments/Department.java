package com.rssl.phizic.csaadmin.business.departments;

/**
 * @author akrenev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������������
 */

public class Department
{
	private Long id;
	private String tb;
	private String osb;
	private String vsp;

	/**
	 * �����������
	 */
	public Department()
	{}

	/**
	 * �����������
	 * @param tb ��
	 * @param osb ���
	 * @param vsp ���
	 */
	public Department(String tb, String osb, String vsp)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	/**
	 * ���������� �����������
	 * @param department �������� ������
	 */
	public Department(Department department)
	{
		this(department.getTb(), department.getOsb(), department.getVsp());
	}

	/**
	 * @return ����� ��
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ������ ����� ��
	 * @param tb ����� ��
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ����� ���
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * ������ ����� ���
	 * @param osb ����� ���
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ����� ���
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * ������ ����� ���
	 * @param vsp ����� ���
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}
}
