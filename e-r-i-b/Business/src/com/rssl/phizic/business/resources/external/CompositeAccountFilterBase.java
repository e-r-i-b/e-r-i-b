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
 * ������� ����� ��� ����������� ����� ��������
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
	 * �������� ������ � ������
	 * @param filter - ������
	 */
	public void addFilter(AccountFilter filter)
	{
		if (filter == null)
			throw new NullPointerException("�������� 'filter' �� ����� ���� null");
		filters.add(filter);
	}

	protected Set<AccountFilter> getFilters()
	{
		return Collections.unmodifiableSet(filters);
	}
}
