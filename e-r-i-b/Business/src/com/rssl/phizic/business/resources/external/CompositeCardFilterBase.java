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
 * ������� ����� ��� ����������� ����� ��������
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
	 * �������� ������ � ������
	 * @param filter - ������
	 */
	public CardFilterBase addFilter(CardFilter filter)
	{
		if (filter == null)
			throw new NullPointerException("�������� 'filter' �� ����� ���� null");
		filters.add(filter);

		return this;
	}

	protected Set<CardFilter> getFilters()
	{
		return Collections.unmodifiableSet(filters);
	}
}
