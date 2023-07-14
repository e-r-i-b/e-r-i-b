package com.rssl.phizic.web.struts.layout;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Krenev
 * @ created 23.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CollectionItemParamTag extends ParamTagBase
{
	private static final List<String> allowedProperties = new ArrayList<String>();
	static{
		allowedProperties.add("styleClass");
		allowedProperties.add("action");
		allowedProperties.add("value");
		allowedProperties.add("title");
	}

	protected TemplatedCollectionItemTag getAncestorTag()
	{
		return (TemplatedCollectionItemTag) findAncestorWithClass(this, TemplatedCollectionItemTag.class);
	}

	protected boolean isAllowedProperty(String name)
	{
		return allowedProperties.contains(name);
	}
}
