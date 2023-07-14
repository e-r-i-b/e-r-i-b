package com.rssl.phizic.gate.office;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Код офиса
 *
 * @author khudyakov
 * @ created 08.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedCodeImpl implements Code
{
	private String region;          //Номер территориального банка
	private String branch;          //Номер отделения
	private String office;          //Номер филиала

	public ExtendedCodeImpl()
	{}

	public ExtendedCodeImpl(String region, String branch, String office)
	{
		this.region = region;
		this.branch = branch;
		this.office = office;
	}

	public ExtendedCodeImpl(Code code)
	{
		if (code == null)
		{
			return;
		}

		Map<String, String> fields = code.getFields();
		this.region = fields.get("region");
		this.branch = fields.get("branch");
		this.office = fields.get("office");
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
		if (MapUtils.isEmpty(fields))
		{
			return;
		}

		region = fields.get("region");
		branch = fields.get("branch");
		office = fields.get("office");
	}

	public String getId()
	{
		return null;
	}

	public void toXml(Element parentTag)
	{

	}

	/**
	 * @return номер территориального банка
	 */
	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return номер отделения
	 */
	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
	}

	/**
	 * @return номер филиала
	 */
	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}
}

