package com.rssl.auth.csa.front.business.regions;

import junit.framework.TestCase;
import com.rssl.phizic.utils.RandomGUID;

/**
 * @author komarov
 * @ created 21.03.2013 
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc","MagicNumber","ReuseOfLocalVariable"})
public class RegionServiceTest extends TestCase
{
	private static final RegionCSAService service = new RegionCSAService();
	private static final RandomGUID rndGUID = new RandomGUID();

	public void testFindRegion() throws Exception
	{
		//Идентификатор региона который есть в базе
		Region region = service.findById(1L);
		assertNotNull(region);
		//Идентификатор региона которого нет в базе
		region = service.findById(99991239L);
		assertNull(region);
	}

	public void testSaveUserRegion() throws Exception
	{
		UserRegion userRegion = new UserRegion();
		userRegion.setCookie(rndGUID.getStringValue());

		Region region = service.findById(1L);
		userRegion.setCode(region.getSynchKey().toString());

		userRegion = service.addUserRegion(userRegion);
		assertNotNull(userRegion.getProfileId());

		service.deleteUserRegion(userRegion);
	}

	
}
