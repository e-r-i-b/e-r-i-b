package com.rssl.phizic.gate.dictionaries.officies;

/**
 * @author akrenev
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������������
 */

public class AllowedDepartment
{
	private String tb;
	private String osb;
	private String vsp;

	/**
	 * �����������
	 */
	public AllowedDepartment(){}

	/**
	 * �����������
	 * @param tb ��
	 * @param osb ���
	 * @param vsp ���
	 */
	public AllowedDepartment(String tb, String osb, String vsp)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	/**
	 * @return ��
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ������ ��
	 * @param tb ��
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ���
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * ������ ���
	 * @param osb ���
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ���
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * ������ ���
	 * @param vsp ���
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}
}
