package com.rssl.phizgate.common.documents.payments;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.HashMap;

/**
 * Kод департамента
 *
 * @author khudyakov
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedCodeImpl implements Code
{
    private String region;          //Номер территориального банка
    private String branch;          //Номер отделения
    private String office;          //Номер филиала

	public ExtendedCodeImpl(String region, String branch, String office)
	{
		this.region = region;
		this.branch = branch;
		this.office = office;
	}

	public ExtendedCodeImpl(Code code)
	{
		if (code==null)
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

	/**
	 * @return номер отделения
	 */
	public String getBranch()
	{
		return branch;
	}

	/**
	 * @return номер филиала
	 */
	public String getOffice()
	{
		return office;
	}
}
