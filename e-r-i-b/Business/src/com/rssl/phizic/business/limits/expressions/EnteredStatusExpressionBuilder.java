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
 * Билдер выражений для поиска лимитов в статусе "введен"
 */
public class EnteredStatusExpressionBuilder extends ConfirmedStateExpressionBuilder
{
	@Override
	protected void addDateExpression(Criteria criteria)
	{
		criteria.add(Expression.gt(START_DATE_PROPERTY_NAME, Calendar.getInstance()));
	}
}
