package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.util.ApplicationUtil;

/**
 * ’елпер дл€ работы с различными видами линков: карты, вклады, кредиты ...
 *
 * @author saharnova
 * @ created 16.03.15
 * @ $Author$
 * @ $Revision$
 */
public class LinkHelper
{
	/**
	 * ѕровер€ет видимость линка в разрезе канала
	 * @param link - линк(карта, счет, депозит ...)
	 * @return true/false
	 */
	public static boolean isVisibleInChannel(ShowInMobileProductLink link)
	{
		if(link == null)
			return false;
		if(ApplicationUtil.isATMApi())
			return link.getShowInATM();
		if(ApplicationUtil.isMobileApi())
			return link.getShowInMobile();
		if(ApplicationUtil.isSocialApi())
			return link.getShowInSocial();
		//дл€ остальных кроме адмиского showInSystem.
		if(!ApplicationUtil.isAdminApplication())
			return link.getShowInSystem();
		return true;
	}
}
