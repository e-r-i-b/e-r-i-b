package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 *  Форма получения нового порядка отображения списка избранного через аякс
 * @ author gorshkov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncFavouriteLinksForm  extends ActionFormBase
{
	private Long[] sortFavouriteLinks;

	/**
	 * @return массив id ссылок избранного в порядке сортировки
	 */
	public Long[] getSortFavouriteLinks()
	{
		return sortFavouriteLinks;
	}

	/**
	 * @param sortFavouriteLinks - массив id ссылок избранного в порядке сортировки
	 */
	public void setSortFavouriteLinks(Long[] sortFavouriteLinks)
	{
		this.sortFavouriteLinks = sortFavouriteLinks;
	}
}
