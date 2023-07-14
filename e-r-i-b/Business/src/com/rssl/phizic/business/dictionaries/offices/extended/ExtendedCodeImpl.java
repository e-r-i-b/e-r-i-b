package com.rssl.phizic.business.dictionaries.offices.extended;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kidyaev
* @ created 01.11.2006
* @ $Author$
* @ $Revision$
*/
public class ExtendedCodeImpl implements Code
{
	//Номер территориального банка
    private String region;
	//Номер отделения
    private String branch;
	//Номер филиала
    private String office;

    public ExtendedCodeImpl()
    {
    }

    public ExtendedCodeImpl(String region, String branch, String office)
    {
        this.region = region;
        this.branch = branch;
        this.office = office;
    }

	public ExtendedCodeImpl(Code code)
	{
		if (code==null)
			return;
		Map<String, String> fields = code.getFields();
		this.region = fields.get("region");
		this.branch = fields.get("branch");
		this.office = fields.get("office");
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

		ExtendedCodeImpl extendedCodeImpl = (ExtendedCodeImpl) o;

		if (!StringHelper.equalsNullIgnore(branch, extendedCodeImpl.branch))
			return false;

		if (!StringHelper.equalsNullIgnore(office, extendedCodeImpl.office))
			return false;

		if (!StringHelper.equalsNullIgnore(region, extendedCodeImpl.region))
			return false;

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
