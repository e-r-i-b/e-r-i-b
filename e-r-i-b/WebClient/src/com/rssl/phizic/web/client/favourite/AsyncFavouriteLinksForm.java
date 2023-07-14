package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 *  ����� ��������� ������ ������� ����������� ������ ���������� ����� ����
 * @ author gorshkov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncFavouriteLinksForm  extends ActionFormBase
{
	private Long[] sortFavouriteLinks;

	/**
	 * @return ������ id ������ ���������� � ������� ����������
	 */
	public Long[] getSortFavouriteLinks()
	{
		return sortFavouriteLinks;
	}

	/**
	 * @param sortFavouriteLinks - ������ id ������ ���������� � ������� ����������
	 */
	public void setSortFavouriteLinks(Long[] sortFavouriteLinks)
	{
		this.sortFavouriteLinks = sortFavouriteLinks;
	}
}
