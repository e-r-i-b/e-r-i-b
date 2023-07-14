package com.rssl.auth.csa.front.business.regions;

import java.io.Serializable;

/**
 * Сущность для хранения данных о регионе пользователя.
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
	 * Конструктор сущности для хранения данных о регионе пользователя.
	 */
	public UserRegion()
	{
	}

	/**
	 * Конструктор сущности для хранения данных о регионе пользователя.
	 * @param cookie идентификатор куки
	 */
	public UserRegion(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return код региона
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code код региона
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return идентификатор куки
	 */
	public String getCookie()
	{
		return cookie;
	}

	/**
	 * @param cookie идентификатор куки
	 */
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return идентификатор пользователя
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId идентификатор пользователя
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}
}
