package com.rssl.phizic.business.accounts;

import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 27.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockCode implements Code, MockObject
{
	private static String EMPTY_STRING="";
	private String region;
	private String branch;
	private String office;

	/**
	 * ctor
	 */
	public MockCode()
	{
	}

	/**
	 * ctor
	 * @param region - код региона
	 * @param branch - код ОСБ
	 * @param office - код ВСП
	 */
	public MockCode(String region, String branch, String office)
	{
		this.region = region;
	    this.branch = branch;
	    this.office = office;
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
		return EMPTY_STRING;
	}

	public void toXml(Element parentTag)
	{
		
	}
}
