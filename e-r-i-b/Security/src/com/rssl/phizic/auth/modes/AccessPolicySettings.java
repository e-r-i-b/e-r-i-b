package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 18.12.2006
 * @ $Author: jatsky $
 * @ $Revision: 62507 $
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
class AccessPolicySettings implements Serializable
{
	private Long loginId;
	private Long        id;
	private String      accessType;
	private byte[]      properties;

	Long getLoginId()
	{
		return loginId;
	}

	void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	Long getId()
	{
		return id;
	}

	void setId(Long id)
	{
		this.id = id;
	}

	String getAccessType()
	{
		return accessType;
	}

	void setAccessType(String accessType)
	{
		this.accessType = accessType;
	}

	public byte[] getProperties()
	{
		return properties;
	}

	public void setProperties(byte[] properties)
	{
		this.properties = properties;
	}
}