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
	 * �����������
	 */
	public UserConfirmStrategySettings(){}

	/**
	 * �����������
	 * @param authConfirmType ��� ������������� �����
	 * @param userConfirmType �������������� ������ �������������
	 */
	public UserConfirmStrategySettings(String authConfirmType, String userConfirmType)
	{
		this.authConfirmType = authConfirmType;
		this.userConfirmType = userConfirmType;
	}

	/**
	 * @return ��� ������������� �����
	 */
	public String getAuthConfirmType()
	{
		return authConfirmType;
	}

	/**
	 * @return �������������� ������ �������������
	 */
	public String getUserConfirmType()
	{
		return userConfirmType;
	}

}
