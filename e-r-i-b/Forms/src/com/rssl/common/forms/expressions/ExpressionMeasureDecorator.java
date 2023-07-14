package com.rssl.common.forms.expressions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * @author krenev
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 * Декоратор - замеряльщик времени выполнения скрипта
 */
public class ExpressionMeasureDecorator implements Expression
{
	private static final Log log = LogFactory.getLog(ExpressionMeasureDecorator.class);
	private Expression delegate;

	public ExpressionMeasureDecorator(Expression delegate)
	{
		this.delegate = delegate;
	}

	public Object evaluate(Map<String, Object> form)
	{
		long start = System.currentTimeMillis();
		try
		{
			return delegate.evaluate(form);
		}
		finally
		{
			log.debug("Время выполнения скрипта " + delegate.getClass().getName() + " - " + (System.currentTimeMillis() - start) + " мс");
		}
	}
}
