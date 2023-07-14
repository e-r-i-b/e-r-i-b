package com.rssl.phizic.business.ext.sbrf.dictionaries.offices;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 23.04.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Предназначен для адаптации в бизнесе кода подразделений сбрф
 */
public class SBRFOfficeCodeAdapter implements Code
{
	private String region;
	private String branch;
	private String office;


	public SBRFOfficeCodeAdapter() {}

	public SBRFOfficeCodeAdapter(Code code)
	{
		Map<String, String> fields = code.getFields();

		this.region = fields.get("region");
		this.branch = fields.get("branch");
		this.office = fields.get("office");
	}

	public SBRFOfficeCodeAdapter(String region, String branch, String office)
	{
		this.region = region;
	    this.branch = branch;
	    this.office = office;
	}

	public String getRegion ()
	{
		return region;
	}

	public void setRegion ( String region )
	{
		this.region = region;
	}

	public String getBranch ()
	{
		return branch;
	}

	public void setBranch ( String branch )
	{
		this.branch = branch;
	}

	public String getOffice ()
	{
		return office;
	}

	public void setOffice ( String office )
	{
		this.office = office;
	}

	public Map<String, String> getFields()
	{
		Map<String, String> fields = new HashMap();

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

	//сейчас не используется.
	public String getId()
	{
		String result = "";
		if (!StringHelper.isEmpty(region)) result+= StringHelper.appendLeadingZeros(region,4) + "|";
		if (!StringHelper.isEmpty(branch)) result+= StringHelper.appendLeadingZeros(branch,4) + "|";
		if (!StringHelper.isEmpty(office)) result+= StringHelper.appendLeadingZeros(office,5) + "|";
		return result;
	}

	public void toXml(Element parentTag)
	{
		Map<String,String> codeFields = getFields();
        if (codeFields.isEmpty())
			   return;
		   if (codeFields.get("branch")!=null) XmlHelper.appendSimpleElement(parentTag, "branch", StringHelper.appendLeadingZeros(codeFields.get("branch"),4));
		   if (!(codeFields.get("office")==null || codeFields.get("office").equals("")))
			   XmlHelper.appendSimpleElement(parentTag, "office", StringHelper.appendLeadingZeros(codeFields.get("office"),5));
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SBRFOfficeCodeAdapter extendedCodeImpl = (SBRFOfficeCodeAdapter) o;

		if (!StringHelper.isEmpty(branch) ? !branch.equals(extendedCodeImpl.branch) : extendedCodeImpl.branch != null) return false;
		if (!StringHelper.isEmpty(office) ? !office.equals(extendedCodeImpl.office) : extendedCodeImpl.office != null) return false;
		if (!StringHelper.isEmpty(region) ? !region.equals(extendedCodeImpl.region) : extendedCodeImpl.region != null) return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = (region != null ? region.hashCode() : 0);
		result = 31 * result + (branch != null ? branch.hashCode() : 0);
		result = 31 * result + (office != null ? office.hashCode() : 0);
		return result;
	}
}