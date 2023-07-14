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
 * Билдер выражения для проверки на то, что статус лимита "действует"
 */
public class ActiveStatusExpressionBuilder extends ConfirmedStateExpressionBuilder
{
	@Override
	protected void addDateExpression(Criteria criteria)
	{
		Calendar currentDate = Calendar.getInstance();
		criteria.add(Expression.le(START_DATE_PROPERTY_NAME, currentDate));
		criteria.add(Expression.or(Expression.isNull(END_DATE_PROPERTY_NAME), Expression.gt(END_DATE_PROPERTY_NAME, currentDate)));
	}
}
