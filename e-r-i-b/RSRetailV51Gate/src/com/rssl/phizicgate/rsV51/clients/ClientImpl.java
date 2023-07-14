package com.rssl.phizicgate.rsV51.clients;

import com.rssl.phizgate.common.routable.ClientBase;

/**
 * User: Kidyaev
 * Date: 30.09.2005
 * Time: 18:16:00
 */
public class ClientImpl extends ClientBase
{
    private Boolean partial = true;

	public boolean isResident()
	{
		return false;
	}

	public String getDisplayId()
	{
		return getId();
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

    void setPartial(boolean value)
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
        this.documents           = that.documents;
        this.realAddress         = that.realAddress;
        this.email               = that.email;
        this.homePhone           = that.homePhone;
        this.jobPhone            = that.jobPhone;
        this.mobilePhone         = that.mobilePhone;
        this.citizenship         = that.citizenship;
    }
}
