package com.rssl.phizic.business.profile;

/**
 * @author komarov
 * @ created 30.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class UserConfirmStrategySettings
{
	private String authConfirmType;
	private String userConfirmType;

	/**
	 * Конструктор
	 */
	public UserConfirmStrategySettings(){}

	/**
	 * Конструктор
	 * @param authConfirmType тип подтверждения входа
	 * @param userConfirmType предпочитаемый способ подтверждения
	 */
	public UserConfirmStrategySettings(String authConfirmType, String userConfirmType)
	{
		this.authConfirmType = authConfirmType;
		this.userConfirmType = userConfirmType;
	}

	/**
	 * @return тип подтверждения входа
	 */
	public String getAuthConfirmType()
	{
		return authConfirmType;
	}

	/**
	 * @return предпочитаемый способ подтверждения
	 */
	public String getUserConfirmType()
	{
		return userConfirmType;
	}

}
