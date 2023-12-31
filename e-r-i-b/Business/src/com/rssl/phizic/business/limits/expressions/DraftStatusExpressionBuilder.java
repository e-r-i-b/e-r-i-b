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
 * ������ ��������� ��� ������ ������� � ������� "��������"
 */
public class DraftStatusExpressionBuilder implements ExpressionBuilder
{
	public void build(Criteria criteria)
	{
		criteria.add(Expression.eq(STATE_PROPERTY_NAME, LimitState.DRAFT));
	}
}
