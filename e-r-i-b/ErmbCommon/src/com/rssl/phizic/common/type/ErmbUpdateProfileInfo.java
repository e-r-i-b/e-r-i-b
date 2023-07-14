package com.rssl.phizic.common.type;


import java.util.List;

/**
 @author: EgorovaA
 @ created: 16.01.2013
 @ $Author$
 @ $Revision$
 */
public class ErmbUpdateProfileInfo
{
    private ErmbProfileIdentity clientIdentity;
    private List<ErmbProfileIdentity> clientOldIdentity;
	private Long profileVersion;

	///////////////////////////////////////////////////////////////////////////

	public ErmbProfileIdentity getClientIdentity()
	{
		return clientIdentity;
	}

	public void setClientIdentity(ErmbProfileIdentity clientIdentity)
	{
		this.clientIdentity = clientIdentity;
	}

	public List<ErmbProfileIdentity> getClientOldIdentity()
	{
		return clientOldIdentity;
	}

	public void setClientOldIdentity(List<ErmbProfileIdentity> clientOldIdentity)
	{
		this.clientOldIdentity = clientOldIdentity;
	}

	public Long getProfileVersion()
	{
		return profileVersion;
	}

	public void setProfileVersion(Long profileVersion)
	{
		this.profileVersion = profileVersion;
	}
}
