package com.rssl.phizic.business.resources.own;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.schemes.AccessScheme;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 03.10.2005
 * Time: 13:53:16
 */
class SchemeOwn
{
    private Long id;
    private AccessScheme accessScheme;
    private Long loginId;
	private String accessType;

    SchemeOwn()
    {
    }

    SchemeOwn(CommonLogin login, AccessType accessType)
    {
        this.loginId = login.getId();
	    this.accessType = accessType.getKey();
    }

    Long getId()
    {
        return id;
    }

    void setId(Long id)
    {
        this.id = id;
    }

    AccessScheme getAccessScheme()
    {
        return accessScheme;
    }

	void setAccessScheme(AccessScheme accessScheme)
    {
        this.accessScheme = accessScheme;
    }

	Long getLoginId()
	{
		return loginId;
	}

	void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getAccessType()
	{
		return accessType;
	}

	public void setAccessType(String accessType)
	{
		this.accessType = accessType;
	}
}
