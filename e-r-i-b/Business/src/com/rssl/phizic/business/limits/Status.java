package com.rssl.phizic.business.limits;

/**
 * @author gulov
 * @ created 17.08.2010
 * @ $Authors$
 * @ $Revision$
 */

import com.rssl.phizic.business.limits.expressions.*;
import org.hibernate.Criteria;

/**
 * Статусы лимита
 */
public enum Status
{
	/**
	 * Действует
	 */
	ACTIVE(1, new ActiveStatusExpressionBuilder()),

	/**
	 * Введен
	 */
	ENTERED(2, new EnteredStatusExpressionBuilder()),

	/**
	 * Архивный
	 */
	ARCHIVAL(3, new ArchiveStatusExpressionBuilder()),

	/**
	 * Черновик
	 */
	DRAFT(4, new DraftStatusExpressionBuilder());

	Status(int value, ExpressionBuilder expressionBuilder)
	{
		this.value = value;
		this.expressionBuilder = expressionBuilder;
	}

	/**
	 * Код статуса
	 */
	private final int value;
	private ExpressionBuilder expressionBuilder;

	public int toValue()
	{
		return value;
	}

	/**
	 * Построить выражение для поиска по криетрии
	 * @param criteria критерия
	 */
	public void buildExpression(Criteria criteria)
	{
		expressionBuilder.build(criteria);
	}

	public static Status fromValue(int value)
	{
		if (value == ACTIVE.value)
			return ACTIVE;
		if (value == ENTERED.value)
			return ENTERED;
		if (value == ARCHIVAL.value)
			return ARCHIVAL;
		if (value == DRAFT.value)
			return DRAFT;

		throw new IllegalArgumentException("Неизвестный статус лимита [" + value + "]");
	}
}
