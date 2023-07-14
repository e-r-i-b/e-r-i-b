package com.rssl.phizic.business.web;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author Erkin
 * @ created 14.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class WebPageHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * ¬озвращает положение бокового меню
	 * @return слева/справа
	 */
	public static Location getSideMenuLocation()
	{
		Location location = Location.right;
		try
		{
			if (PersonContext.isAvailable())
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				WebPage sidemenu = personData.getPage(WebPageContstants.SIDE);
				if (sidemenu != null)
					location = sidemenu.getLocation();
			}
		}
		catch (Exception e)
		{
			log.error("Ќе определено положение бокового меню", e);
		}
		return location;
	}
}
