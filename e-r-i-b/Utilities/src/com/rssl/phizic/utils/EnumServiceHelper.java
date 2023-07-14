package com.rssl.phizic.utils;

/**
 * @author egorova
 * @ created 02.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class EnumServiceHelper implements BeanFormatter
{
	public Object format(Object object)
	{
		return object.toString();
	}
}
