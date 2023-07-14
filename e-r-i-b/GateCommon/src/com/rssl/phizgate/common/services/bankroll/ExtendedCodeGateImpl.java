package com.rssl.phizgate.common.services.bankroll;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedCodeGateImpl implements Code
{
	//Номер территориального банка
    private String region;
	//Номер отделения
    private String branch;
	//Номер филиала
    private String office;


	public ExtendedCodeGateImpl() {}

	public ExtendedCodeGateImpl(Code code)
	{
		Map<String, String> fields = code.getFields();

		this.region = fields.get("region");
		this.branch = fields.get("branch");
		this.office = fields.get("office");
	}

	/**
	 * Конструктор расширенного кода
	 * @param region - ТБ
	 * @param branch - отделение
	 * @param office - филиал
	 */
	public ExtendedCodeGateImpl(String region, String branch, String office)
	{
		this.region = (region!=null)?StringHelper.removeLeadingZeros(region):null;
	    this.branch = (branch!=null)?StringHelper.removeLeadingZeros(branch):null;
	    this.office = (office!=null)?StringHelper.removeLeadingZeros(office):null;
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

		ExtendedCodeGateImpl extendedCodeImpl = (ExtendedCodeGateImpl) o;

		if (branch != null ? !branch.equals(extendedCodeImpl.branch) : extendedCodeImpl.branch != null) return false;
		if (office != null ? !office.equals(extendedCodeImpl.office) : extendedCodeImpl.office != null) return false;
		if (region != null ? !region.equals(extendedCodeImpl.region) : extendedCodeImpl.region != null) return false;

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
