package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @ author gorshkov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ListFavouriteLinksForm extends ListFormBase
{
	private List<FavouriteLink> favouriteLinks;
	private String nameFavouriteLink;

	/**
	 * @return список ссылок избранного
	 */
	public List<FavouriteLink> getFavouriteLinks()
	{
		return favouriteLinks;
	}

	/**
	 * @param favouriteLinks - список ссылок избранного
	 */
	public void setFavouriteLinks(List<FavouriteLink> favouriteLinks)
	{
		this.favouriteLinks = favouriteLinks;
	}

	/**
	 * @return название ссылки избранного
	 */
	public String getNameFavouriteLink()
	{
		return nameFavouriteLink;
	}

	/**
	 * @param nameFavouriteLink - название ссылки избранного
	 */
	public void setNameFavouriteLink(String nameFavouriteLink)
	{
		this.nameFavouriteLink = nameFavouriteLink;
	}
}
