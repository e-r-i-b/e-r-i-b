package com.rssl.phizic.business.departments.allowed;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * �������� ��������� �������������
 * @author komarov
 * @ created 20.12.13 
 * @ $Author$
 * @ $Revision$
 */

public class AllowedDepartments implements Serializable
{
	private Long   loginId;
	private String tb;
	private String osb;
	private String vsp;

	/**
	 * �����������
	 */
	public AllowedDepartments()
	{

	}

	/**
	 * @param loginId ����� ����������
	 * @param tb ����� ��
	 * @param osb ���
	 * @param vsp ���
	 */
	public AllowedDepartments(Long loginId, String tb, String osb, String vsp)
	{
		this.loginId = loginId;
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	/**
	 * ���������� �����
	 * @return �����
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * ������������� �����
	 * @param loginId �����
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ����� ��������
	 */
	public String getTB()
	{
		return tb;
	}

	/**
	 * @param tb ����� ��������
	 */
	public void setTB(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ���
	 */
	public String getOSB()
	{
		return osb;
	}

	/**
	 * @param osb ���
	 */
	public void setOSB(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ���
	 */
	public String getVSP()
	{
		return vsp;
	}

	/**
	 * @param vsp ���
	 */
	public void setVSP(String vsp)
	{
		this.vsp = vsp;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		AllowedDepartments that = (AllowedDepartments) o;

		if (!loginId.equals(that.loginId))
		{
			return false;
		}
		if (!osb.equals(that.osb))
		{
			return false;
		}
		if (!tb.equals(that.tb))
		{
			return false;
		}
		if (!vsp.equals(that.vsp))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = loginId.hashCode();
		result = 31 * result + tb.hashCode();
		result = 31 * result + osb.hashCode();
		result = 31 * result + vsp.hashCode();
		return result;
	}
}
