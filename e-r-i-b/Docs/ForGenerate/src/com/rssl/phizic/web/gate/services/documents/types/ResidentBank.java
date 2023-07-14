package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 29.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class ResidentBank
{
	private String BIC;
	private String shortName;
	private Long       id;
    private String     synchKey;
    private String     name;
    private String     place;
    private String     account;

	public ResidentBank()
	{
		this.BIC = "";
		this.shortName = "";
	}

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(String synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPlace()
	{
		return place;
	}

	public void setPlace(String place)
	{
		this.place = place;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}
}
