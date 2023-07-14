package com.rssl.auth.csa.front.business.regions;

import java.io.Serializable;

/**
 * �������� ��� �������� ������ � ������� ������������.
 * @author komarov
 * @ created 20.03.2013 
 * @ $Author$
 * @ $Revision$
 */

public class UserRegion implements Serializable
{
	private String code;
	private String cookie;
	private Long profileId;

	/**
	 * ����������� �������� ��� �������� ������ � ������� ������������.
	 */
	public UserRegion()
	{
	}

	/**
	 * ����������� �������� ��� �������� ������ � ������� ������������.
	 * @param cookie ������������� ����
	 */
	public UserRegion(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return ��� �������
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code ��� �������
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return ������������� ����
	 */
	public String getCookie()
	{
		return cookie;
	}

	/**
	 * @param cookie ������������� ����
	 */
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return ������������� ������������
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId ������������� ������������
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}
}
