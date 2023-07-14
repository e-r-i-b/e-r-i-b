package com.rssl.phizic.web.common.socialApi.loyalty;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * User: Moshenko
 * Date: 01.02.2012
 * Time: 16:20:12
 */
public class LoyaltyForm extends ActionFormBase
{
	/**
	 *URL для перехода на сайта Лояльность
	 */
	public String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
