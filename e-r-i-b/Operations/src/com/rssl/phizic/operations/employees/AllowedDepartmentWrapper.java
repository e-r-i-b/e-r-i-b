package com.rssl.phizic.operations.employees;

/**
 * @author akrenev
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ���������� ������������� ��� �������������� ������� ������������� ����������
 */

public class AllowedDepartmentWrapper
{
	private Long id;
	private String name;
	private String tb;
	private String osb;
	private String vsp;
	private boolean allowed;

	/**
	 * ��������� �����������
	 */
	public AllowedDepartmentWrapper()
	{}

	/**
	 * �����������
	 * @param tb ��
	 * @param osb ���
	 * @param vsp ���
	 * @param name �������� �������������
	 * @param allowed �������� �� �������������
	 */
	public AllowedDepartmentWrapper(String tb, String osb, String vsp, String name, boolean allowed)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
		this.name = name;
		this.allowed = allowed;
	}

	/**
	 * @return ������������� �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return �������� �������������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ��
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return ���
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @return ���
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @return �������� �� �������������
	 */
	public boolean isAllowed()
	{
		return allowed;
	}
}
