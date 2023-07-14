package com.rssl.phizic.web.gate.services.util;

import java.util.Map;

/**
 * @author egorova
 * @ created 03.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class Code
{
    private String branch;
    private Map fields;
    private String id;
    private String office;
    private String region;

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
	}

	public Map getFields()
	{
		return fields;
	}

	public void setFields(Map fields)
	{
		this.fields = fields;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}
}
