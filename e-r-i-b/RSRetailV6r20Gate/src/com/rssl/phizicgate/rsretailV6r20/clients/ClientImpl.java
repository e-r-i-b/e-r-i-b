package com.rssl.phizicgate.rsretailV6r20.clients;

import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizgate.common.routable.ClientBase;

import java.util.Set;

/**
 * User: Kidyaev
 * Date: 30.09.2005
 * Time: 18:16:00
 */
public class ClientImpl extends ClientBase
{
    // Поля, специфичные для Client
    private Boolean partial = true;
	private Set<String> clientIds;


	private String residentFromRetail;
	private String maleFromRetail;
	private String displayId;

	public boolean isResident()
	{
		return true;//!residentFromRetail.equals("X");			
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
        this.realAddress         = that.realAddress;
        this.email               = that.email;
        this.homePhone           = that.homePhone;
        this.jobPhone            = that.jobPhone;
        this.mobilePhone         = that.mobilePhone;
	    this.citizenship         = that.citizenship;
	    this.documents           = that.documents;
    }

	public void setResidentFromRetail(String resident)
	{
		this.residentFromRetail = resident;
	}

	public String getResidentFromRetail()
	{
		return residentFromRetail;
	}

	public String getMaleFromRetail()
	{
		return maleFromRetail;
	}

	public void setMaleFromRetail(String maleFromRetail)
	{
		this.maleFromRetail = maleFromRetail;
		if ("X".equals(maleFromRetail))
		{
			this.gender = ClientSex.GENDER_MALE;
		}
		else
		{
			this.gender = ClientSex.GENDER_FEMALE;
		}
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
