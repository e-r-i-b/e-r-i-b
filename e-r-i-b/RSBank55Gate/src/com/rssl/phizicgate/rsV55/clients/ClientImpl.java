package com.rssl.phizicgate.rsV55.clients;

import com.rssl.phizgate.common.routable.ClientBase;
import com.rssl.phizic.gate.clients.ClientSex;

/**
 * User: Kidyaev
 * Date: 30.09.2005
 * Time: 18:16:00
 */
public class ClientImpl extends ClientBase
{
    // ѕол€, специфичные дл€ Client
    private boolean partial = true;
	private boolean isResident;
	//пол€ Retail
	private String genderRetail;
	private String residental;

	/**
	 * @return резидентность
	 */
	public String getResidental()
	{
		return residental;
	}

	/**
	 * @param residental резидентность
	 */
	public void setResidental(String residental)
	{
		this.residental = residental;

		if(residental == null || residental.equals("X"))
		{
			isResident = false;
		}
		else
		{
			isResident = true;
		}
	}

	public boolean isResident()
	{
		return isResident;
	}

	/**
	 * @param isResident резидентность
	 */
	public void setIsResident(Boolean isResident)
	{
	    this.isResident = isResident;
	}

	public String getGenderRetail()
	{
		return genderRetail;
	}

	public void setGenderRetail(String genderRetail)
	{
		this.genderRetail = genderRetail;
		if(genderRetail!=null)
		{
			if(genderRetail.equals("1"))
			{
				gender = ClientSex.GENDER_MALE;
			}
			else
			{
				gender = ClientSex.GENDER_FEMALE;
			}

		}
		else
		{
			gender = ClientSex.GENDER_MALE;
		}
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
	    this.email               = that.email;
        this.homePhone           = that.homePhone;
        this.jobPhone            = that.jobPhone;
        this.mobilePhone         = that.mobilePhone;
	    this.gender              = that.gender;
	    this.legalAddress        = that.legalAddress;
	    this.realAddress         = that.realAddress;
	    this.citizenship         = that.citizenship;
    }
}
