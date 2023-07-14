package com.rssl.phizic.web.struts.layout;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Krenev
 * @ created 23.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CollectionParamTag extends ParamTagBase
{
	private static final List<String> allowedProperties = new ArrayList<String>();
	static{
		allowedProperties.add("styleClass");
		allowedProperties.add("styleId");
		allowedProperties.add("selectProperty");
		allowedProperties.add("selectName");
		allowedProperties.add("selectType");

		allowedProperties.add("onClick");
		allowedProperties.add("onRowClick");
		allowedProperties.add("onRowDblClick");
		allowedProperties.add("onRowMouseOver");
		allowedProperties.add("onRowMouseOut");
	}

	protected TemplatedCollectionTag getAncestorTag()
	{
		return (TemplatedCollectionTag) findAncestorWithClass(this, TemplatedCollectionTag.class);
	}

	protected boolean isAllowedProperty(String name)
	{
		return allowedProperties.contains(name);
	}
}
