package com.rssl.phizic.business.incognitoDictionary;

/**
 * @author osminin
 * @ created 17.07.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - �������������� �������� � ��������� ���������
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
	 * @param profileId ������������� �������
	 */
	public IncognitoProfiles(long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return ������������� �������
	 */
	public long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId ������������� �������
	 */
	public void setProfileId(long profileId)
	{
		this.profileId = profileId;
	}
}
