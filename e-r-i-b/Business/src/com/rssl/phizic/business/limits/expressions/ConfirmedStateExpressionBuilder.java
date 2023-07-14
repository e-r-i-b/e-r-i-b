package com.rssl.phizic.business.limits.expressions;

import com.rssl.phizic.business.limits.LimitState;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 * @author osminin
 * @ created 09.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Билдер выражений для поиска подтвержденных лимитов
 */
public abstract class ConfirmedStateExpressionBuilder implements ExpressionBuilder
{
	public void build(Criteria criteria)
	{
		criteria.add(Expression.eq(STATE_PROPERTY_NAME, LimitState.CONFIRMED));
		addDateExpression(criteria);
	}

	protected abstract void addDateExpression(Criteria criteria);
}
