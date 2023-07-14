package com.rssl.phizic.business.incognitoDictionary;

/**
 * @author osminin
 * @ created 17.07.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - идентификаторы профилей с признаком инкогнито
 */
public class IncognitoProfiles
{
	private long profileId;

	/**
	 * ctor
	 */
	public IncognitoProfiles()
	{
	}

	/**
	 * ctor
	 * @param profileId идентификатор профиля
	 */
	public IncognitoProfiles(long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return идентификатор профиля
	 */
	public long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId идентификатор профиля
	 */
	public void setProfileId(long profileId)
	{
		this.profileId = profileId;
	}
}
