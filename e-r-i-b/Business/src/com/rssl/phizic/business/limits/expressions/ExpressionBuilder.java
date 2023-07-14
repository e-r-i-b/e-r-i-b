package com.rssl.phizic.business.limits.expressions;

import org.hibernate.Criteria;

/**
 * @author osminin
 * @ created 09.11.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������� ��� ��������
 */
public interface ExpressionBuilder
{
	String STATE_PROPERTY_NAME      = "state";
	String START_DATE_PROPERTY_NAME = "startDate";
	String END_DATE_PROPERTY_NAME   = "endDate";

	/**
	 * ��������� ���������
	 * @param criteria ��������
	 */
	void build(Criteria criteria);
}
