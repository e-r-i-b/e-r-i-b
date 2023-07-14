package com.rssl.phizic.business.resources.external;

/**
 * @ author: Gololobov
 * @ created: 29.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр для определения активных, отображаемых в mAPI карт, вкладов, ОМС
 */
public class ActiveAndShowInMobileLinkFilter extends ActiveCardFilter implements AccountFilter, IMAccountFilter
{
	private boolean mobileAccept(ShowInMobileProductLink link)
	{
		if (link instanceof ShowInMobileProductLink)
			return link.getShowInMobile();

		throw new IllegalArgumentException("Неизвестный тип " + link + ". Ожидается ShowInMobileProductLink.");
	}

	public boolean accept(CardLink link)
	{
		return super.accept(link) ? mobileAccept(link) : false;
	}

	public boolean accept(AccountLink accountLink)
	{
		ActiveAccountFilter filter = new ActiveAccountFilter();
		return filter.accept(accountLink) ? mobileAccept(accountLink) : false;
	}

	public boolean accept(IMAccountLink imAccountLink)
	{
		ActiveIMAccountFilter filter = new ActiveIMAccountFilter();
		return filter.accept(imAccountLink) ? mobileAccept(imAccountLink) : false;
	}
}
