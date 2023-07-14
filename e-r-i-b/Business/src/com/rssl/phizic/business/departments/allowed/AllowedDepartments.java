package com.rssl.phizic.business.departments.allowed;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * Сущность доступных подразделений
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
	 * Конструктор
	 */
	public AllowedDepartments()
	{

	}

	/**
	 * @param loginId логин сотрудника
	 * @param tb номер ТБ
	 * @param osb осб
	 * @param vsp всп
	 */
	public AllowedDepartments(Long loginId, String tb, String osb, String vsp)
	{
		this.loginId = loginId;
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	/**
	 * Возвращает логин
	 * @return логин
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * Устанавливает логин
	 * @param loginId логин
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return номер тербанка
	 */
	public String getTB()
	{
		return tb;
	}

	/**
	 * @param tb номер иербанка
	 */
	public void setTB(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ОСБ
	 */
	public String getOSB()
	{
		return osb;
	}

	/**
	 * @param osb ОСБ
	 */
	public void setOSB(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ВСП
	 */
	public String getVSP()
	{
		return vsp;
	}

	/**
	 * @param vsp ВСП
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
