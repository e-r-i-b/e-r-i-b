package com.rssl.phizic.business.favouritelinks;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.CheckLoginTest;

/**
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class FavouriteLinkServiceTest extends BusinessTestCaseBase
{
	public void testService() throws Exception
	{
		Login testLogin = CheckLoginTest.getTestLogin();
		FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();

		FavouriteLink favouriteLink = new FavouriteLink();
		favouriteLink.setName("MOE");
		favouriteLink.setLink("action/testAction.do");
		favouriteLink.setLoginId(testLogin.getId());

		assertNotNull(favouriteLinkManager.maxLinkInd(testLogin.getId()));


		favouriteLinkManager.add(favouriteLink);

		assertNotNull(favouriteLinkManager.findByUserId(testLogin.getId()));

		favouriteLinkManager.remove(favouriteLink);
	}
}
