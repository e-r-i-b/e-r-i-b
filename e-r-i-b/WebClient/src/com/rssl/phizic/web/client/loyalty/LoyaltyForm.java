package com.rssl.phizic.web.client.loyalty;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * User: Moshenko
 * Date: 19.07.2011
 * Time: 16:37:28
 */
public class LoyaltyForm  extends ActionFormBase
{
	/**
	 *URL для перехода на сайта Лояльность
	 */
	public String url;
	private boolean isOffersPage = false;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isOffersPage()
	{
		return isOffersPage;
	}

	public void setOffersPage(boolean offersPage)
	{
		isOffersPage = offersPage;
	}
}
