package com.rssl.phizic.utils;

/**
 * »спользуетс€ в BeanHelper-е, если в таблице коррел€ции не найдено значение дл€ класса
 * @author Rtischeva
 * @ created 27.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmptyFormatter implements BeanFormatter
{
	public Object format(Object object)
	{
		return object;
	}
}

