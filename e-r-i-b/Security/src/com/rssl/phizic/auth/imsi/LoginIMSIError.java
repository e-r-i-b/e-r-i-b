package com.rssl.phizic.auth.imsi;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;
import java.util.Date;

/**
 * �������� ���������� �� ������ IMSI �� ���������� ���� ��� ����������� ������������.
 *
 * @author bogdanov
 * @ created 01.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginIMSIError implements Serializable
{
	/**
	 * ������������� ������.
	 */
	private Long id;
	/**
	 * ���� ���������� �������� �� ������ IMSI.
	 */
	private Date checkDate;
	/**
	 * ����� �������, ��� ������� ��������� �������� ����������� �������� IMSI.
	 */
	private CommonLogin login;
	/**
	 * ������� ����, ��� ���-����� �� ���� ��������.
	 */
	private boolean goodIMSI;

	public LoginIMSIError()
	{
	}

	public LoginIMSIError(Long id, Date checkDate, CommonLogin login, boolean goodIMSI)
	{
		this.checkDate = checkDate;
		this.goodIMSI = goodIMSI;
		this.id = id;
		this.login = login;
	}

	/**
	 * @return ���� ���������� �������� �� ����� ���-�����.
	 */
	public Date getCheckDate()
	{
		return checkDate;
	}

	/**
	 * @param checkDate ���� ���������� �������� �� ����� ���-�����.
	 */
	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}

	/**
	 * @return ������� ����, ��� ���-����� �� ���� �������.
	 */
	public boolean isGoodIMSI()
	{
		return goodIMSI;
	}

	/**
	 * @param goodIMSI ������� ����, ��� ���-����� �� ���� �������.
	 */
	public void setGoodIMSI(boolean goodIMSI)
	{
		this.goodIMSI = goodIMSI;
	}

	/**
	 * @return �������������.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ����� �������, ��� ������� �������� �������� ����������� �������� ����� ���-�����.
	 */
	public CommonLogin getLogin()
	{
		return login;
	}

	/**
	 * @param login ����� �������, ��� ������� �������� �������� ����������� �������� ����� ���-�����.
	 */
	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}
}
