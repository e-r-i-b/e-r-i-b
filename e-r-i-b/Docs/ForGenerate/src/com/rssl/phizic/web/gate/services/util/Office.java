package com.rssl.phizic.web.gate.services.util;

/**
 * @author egorova
 * @ created 03.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class Office
{
    private String BIC;
    private java.lang.String address;
    private Code code;
    private String name;
    private String postIndex;
    private String sbidnt;
    private String synchKey;
    private String telephone;

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPostIndex()
	{
		return postIndex;
	}

	public void setPostIndex(String postIndex)
	{
		this.postIndex = postIndex;
	}

	public String getSbidnt()
	{
		return sbidnt;
	}

	public void setSbidnt(String sbidnt)
	{
		this.sbidnt = sbidnt;
	}

	public String getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(String synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}
}
