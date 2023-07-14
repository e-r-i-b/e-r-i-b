package com.rssl.phizic.business.resources.external;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для составления групп фильтров
 */
public abstract class CompositeAccountFilterBase implements AccountFilter
{
	private final Set<AccountFilter> filters = new LinkedHashSet<AccountFilter>();

	///////////////////////////////////////////////////////////////////////////

	protected CompositeAccountFilterBase() {}

	protected CompositeAccountFilterBase(AccountFilter... filters)
	{
		if (filters != null) {
			for (AccountFilter filter : filters)
				addFilter(filter);
		}
	}

	/**
	 * Добавить фильтр в группу
	 * @param filter - фильтр
	 */
	public void addFilter(AccountFilter filter)
	{
		if (filter == null)
			throw new NullPointerException("Аргумент 'filter' не может быть null");
		filters.add(filter);
	}

	protected Set<AccountFilter> getFilters()
	{
		return Collections.unmodifiableSet(filters);
	}
}
