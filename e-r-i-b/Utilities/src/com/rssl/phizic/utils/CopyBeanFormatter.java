package com.rssl.phizic.utils;

import java.util.Map;

/**
 * »спользуетс€ в BeanHelper-е, если в таблице коррел€ции найдено значение не равное null
 * @author Rtischeva
 * @ created 27.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class CopyBeanFormatter implements BeanFormatter
{
	private Object dest;
	private Map<Class, Class> corl;
	private Map<Class, BeanFormatter> enumFormaters;

	public CopyBeanFormatter(Class destType, Map<Class, Class> corl,  Map<Class, BeanFormatter> enumFormaters) throws Exception
	{
		dest = destType.getConstructor().newInstance();
		this.corl = corl;
		this.enumFormaters = enumFormaters;
	}

	public Object format(Object object) throws Exception
	{
		BeanHelper.copyPropertiesWithDifferentTypes(dest, object, corl, enumFormaters);
		return dest;
	}
}

