package com.rssl.phizicgate.rsretailV6r20.dictionaries.officies;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 24.04.2009
 * @ $Author$
 * @ $Revision$
 */

public class CodeImpl implements Code
{
    private String id;
	private Map<String, String> fields = new HashMap<String, String>();

    public CodeImpl()
    {
    }

    public CodeImpl(String id)
    {
        this.id = id;
    }

	public CodeImpl(Code code)
	{
		this.id = code.getId();
		this.fields = code.getFields();
	}

	public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

	public Map<String, String> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, String> fields)
	{
		this.fields = fields;
	}

	public 	void toXml(Element parentTag)
	{
		Map<String,String> codeFields = getFields();
		  if (codeFields.isEmpty())
		    return;
		if (codeFields.get("id") != null) XmlHelper.appendSimpleElement(parentTag, "id", codeFields.get("id"));
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CodeImpl code = (CodeImpl) o;

		if (id != null ? !id.equals(code.id) : code.id != null) return false;

		return true;
	}

	public int hashCode()
	{
		return (id != null ? id.hashCode() : 0);
	}
}