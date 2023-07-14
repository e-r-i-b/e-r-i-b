package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Òýã "office"
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"code", "name"})
@XmlRootElement(name = "office")
public class OfficeTag
{
	private String code;
	private String name;

	@XmlElement(name = "code", required = true)
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public OfficeTag()
	{
	}

	public OfficeTag(Office office)
	{
		if (office.getCode() != null)
		{
			Map<String, String> officeCodeFields = office.getCode().getFields();
			if (officeCodeFields.get("branch") != null && officeCodeFields.get("office") != null)
				this.setCode(officeCodeFields.get("branch") + "/" + officeCodeFields.get("office"));
		}
		if (office.getName() != null)
			this.setName(office.getName());
	}
}
