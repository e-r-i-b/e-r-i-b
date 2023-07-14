package com.rssl.phizicgate.mock.offices;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 29.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class CodeImpl extends ExtendedCodeGateImpl
{
	private String region;
	private String branch;
	private String office;

    public CodeImpl()
    {
    }

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
	}

	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	public CodeImpl(Code code)
	{
		Map<String, String> fields = code.getFields();

		this.region = fields.get("region");
		this.branch = fields.get("branch");
		this.office = fields.get("office");
	}

	public CodeImpl(String region, String branch, String office)
	{
		this.region = (region!=null)?StringHelper.removeLeadingZeros(region):null;
	    this.branch = (branch!=null)?StringHelper.removeLeadingZeros(branch):null;
	    this.office = (office!=null)?StringHelper.removeLeadingZeros(office):null;
	}

	public String getId()
	{
		String result = "";
		if (!StringHelper.isEmpty(region)) result+= StringHelper.appendLeadingZeros(region,4) + "|";
		if (!StringHelper.isEmpty(branch)) result+= StringHelper.appendLeadingZeros(branch,4) + "|";
		if (!StringHelper.isEmpty(office)) result+= StringHelper.appendLeadingZeros(office,5) + "|";
		return result;
	}

	public Map<String, String> getFields()
	{
		Map<String, String> fields = new HashMap<String, String>();

		fields.put("region", region);
		fields.put("branch", branch);
		fields.put("office", office);

		return fields;
	}

	public void setFields(Map<String, String> fields)
	{
		region = fields.get("region");
		branch = fields.get("branch");
		office = fields.get("office");
	}

	public void toXml(Element parentTag)
	{
		Map<String,String> codeFields = getFields();
		if (codeFields.isEmpty())
			return;

		if (StringHelper.isNotEmpty(codeFields.get("id")))
		{
			XmlHelper.appendSimpleElement(parentTag, "id", codeFields.get("id"));
		}
	}
}

