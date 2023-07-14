package com.rssl.phizgate.common.services.types;

import com.rssl.phizgate.common.routable.ClientBase;

import java.util.Set;

/**
 * @author: Pakhomova
 * @created: 24.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class ClientImpl extends ClientBase
{
    private Boolean partial = true;
	private Set<String> clientIds;
	private String displayId;

	public boolean isResident()
	{
		return true;
	}

	public String getDisplayId()
	{
		return displayId;
	}

	private long getLongId()
    {
        return Long.parseLong(id);
    }

    private void setLongId(long longId)
    {
        id =  String.valueOf(longId);
    }

    public boolean isPartial()
    {
        return partial;
    }

    public void setPartial(boolean value)
    {
        partial = value;
    }

	void update(ClientImpl that)
    {
        this.firstName           = that.firstName;
        this.surName             = that.surName;
        this.patrName            = that.patrName;
        this.birthDay            = that.birthDay;
        this.birthPlace          = that.birthPlace;
        this.realAddress         = that.realAddress;
        this.email               = that.email;
        this.homePhone           = that.homePhone;
        this.jobPhone            = that.jobPhone;
        this.mobilePhone         = that.mobilePhone;
	    this.citizenship         = that.citizenship;
	    this.documents           = that.documents;
    }

	public Set<String> getClientIds()
	{
		return clientIds;
	}

	public void setClientIds(Set<String> clientIds)
	{
		this.clientIds = clientIds;
	}

	public void setDisplayId(String displayId)
	{
		this.displayId = displayId;
	}
}
