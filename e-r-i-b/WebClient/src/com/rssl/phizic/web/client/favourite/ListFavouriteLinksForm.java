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
	 * @return ������ ������ ����������
	 */
	public List<FavouriteLink> getFavouriteLinks()
	{
		return favouriteLinks;
	}

	/**
	 * @param favouriteLinks - ������ ������ ����������
	 */
	public void setFavouriteLinks(List<FavouriteLink> favouriteLinks)
	{
		this.favouriteLinks = favouriteLinks;
	}

	/**
	 * @return �������� ������ ����������
	 */
	public String getNameFavouriteLink()
	{
		return nameFavouriteLink;
	}

	/**
	 * @param nameFavouriteLink - �������� ������ ����������
	 */
	public void setNameFavouriteLink(String nameFavouriteLink)
	{
		this.nameFavouriteLink = nameFavouriteLink;
	}
}
