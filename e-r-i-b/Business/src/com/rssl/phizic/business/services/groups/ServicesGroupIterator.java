package com.rssl.phizic.business.services.groups;

import java.util.*;

/**
 * @author akrenev
 * @ created 26.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Итератор групп сервисов
 */

public class ServicesGroupIterator implements Iterator<ServicesGroupInformation>
{
	private static final Comparator<ServicesGroupInformation> COMPARATOR = new Comparator<ServicesGroupInformation>()
	{
		public int compare(ServicesGroupInformation first, ServicesGroupInformation second)
		{
			return (int) (first.getOrder() - second.getOrder());
		}
	};

	private final List<ServicesGroupInformation> groups;
	private final Stack<Iterator<ServicesGroupInformation>> iterators = new Stack<Iterator<ServicesGroupInformation>>();
	private Iterator<ServicesGroupInformation> current;

	/**
	 * конструктор
	 * @param groups данные
	 */
	public ServicesGroupIterator(List<ServicesGroupInformation> groups)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.groups = groups;
		refresh();
	}

	/**
	 * сбросить состояние итератора
	 */
	public void refresh()
	{
		current = getIterator(groups);
		iterators.clear();
	}

	private Iterator<ServicesGroupInformation> getCurrentIterator()
	{
		if (current == null)
			return null;

		if (current.hasNext())
			return current;

		while (current != null && !current.hasNext())
			if (iterators.size() > 0)
				current = iterators.pop();
			else
				current = null;

		return current;
	}

	private Iterator<ServicesGroupInformation> getIterator(List<ServicesGroupInformation> groups)
	{
		return sort(groups).iterator();
	}

	private List<ServicesGroupInformation> sort(List<ServicesGroupInformation> groups)
	{
		List<ServicesGroupInformation> sortedGroups = new ArrayList<ServicesGroupInformation>(groups);
		Collections.sort(sortedGroups, COMPARATOR);
		return sortedGroups;
	}

	public boolean hasNext()
	{
		if (getCurrentIterator() == null)
			return false;

		return getCurrentIterator().hasNext();
	}

	public ServicesGroupInformation next()
	{
		ServicesGroupInformation next = getCurrentIterator().next();

		next.setLevel(iterators.size());

		if (!next.getSubgroups().isEmpty() || !next.getActions().isEmpty())
		{
			iterators.push(current);
			List<ServicesGroupInformation> union = new ArrayList<ServicesGroupInformation>(sort(next.getSubgroups()));
			union.addAll(sort(next.getActions()));
			current = union.iterator();
		}
		return next;
	}

	public void remove()
	{
		throw new UnsupportedOperationException("Не поддерживается операция удаления");
	}
}
