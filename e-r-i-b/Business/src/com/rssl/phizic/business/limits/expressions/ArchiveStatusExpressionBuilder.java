package com.rssl.phizic.business.limits.expressions;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 09.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Билдер выражения для проверки на то, что статус лимита "архивный"
 */
public class ArchiveStatusExpressionBuilder extends ConfirmedStateExpressionBuilder
{
	@Override
	protected void addDateExpression(Criteria criteria)
	{
		Calendar currentDate = Calendar.getInstance();
		criteria.add(Expression.le(START_DATE_PROPERTY_NAME, currentDate));
		criteria.add(Expression.le(END_DATE_PROPERTY_NAME, currentDate));
	}
}
