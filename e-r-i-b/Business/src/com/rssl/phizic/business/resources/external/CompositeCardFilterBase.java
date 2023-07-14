package com.rssl.phizic.business.resources.external;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для составления групп фильтров
 */
public abstract class CompositeCardFilterBase extends CardFilterBase
{
	private final Set<CardFilter> filters = new LinkedHashSet<CardFilter>();

	///////////////////////////////////////////////////////////////////////////

	protected CompositeCardFilterBase() {}

	protected CompositeCardFilterBase(CardFilter... filters)
	{
		if (filters != null) {
			for (CardFilter filter : filters)
				addFilter(filter);
		}
	}

	/**
	 * Добавить фильтр в группу
	 * @param filter - фильтр
	 */
	public CardFilterBase addFilter(CardFilter filter)
	{
		if (filter == null)
			throw new NullPointerException("Аргумент 'filter' не может быть null");
		filters.add(filter);

		return this;
	}

	protected Set<CardFilter> getFilters()
	{
		return Collections.unmodifiableSet(filters);
	}
}
