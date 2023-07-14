package com.rssl.phizic.business.dictionaries.pages;

import java.util.Comparator;

/** компаратор иерархии страниц
 * @author akrenev
 * @ created 21.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class PageHierarchyComparator implements Comparator<Page>
{
	public int compare(Page firstPage, Page secondPage)
	{
		Page parentFirstPage = firstPage.getParent();
		Page parentSecondPage = secondPage.getParent();
		if (parentFirstPage == null && parentSecondPage == null)
		{
			return 0;
		}
		if (parentFirstPage == null)
		{
			return -1;
		}
		if (parentSecondPage == null)
		{
			return 1;
		}
		return compare(parentFirstPage, parentSecondPage);
	}
}
