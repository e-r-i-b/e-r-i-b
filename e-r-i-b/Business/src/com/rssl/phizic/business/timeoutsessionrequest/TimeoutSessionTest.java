package com.rssl.phizic.business.timeoutsessionrequest;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author belyi
 * @ created 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class TimeoutSessionTest extends BusinessTestCaseBase
{
	public void testAddAndSelectFromDB() throws Exception
	{
		TimeoutSession ts = new TimeoutSession();

		ts.setUrl("TestBody");
		ts.setParametres("TestPArameters");

		TimeoutSessionService tss = new TimeoutSessionService();
		TimeoutSession timeoutSession = tss.addOrUpdate(ts);
		assertNotNull(timeoutSession);
		assertEquals("TestBody", timeoutSession.getUrl());
		assertEquals("TestPArameters", timeoutSession.getParametres());

		TimeoutSession timeoutSession1 = tss.getByRandomRecordId("1");
		assertEquals(timeoutSession1.getRandomRecordId(), timeoutSession.getRandomRecordId());
		assertEquals(timeoutSession1.getUrl(), timeoutSession.getUrl());
		assertEquals(timeoutSession1.getParametres(), timeoutSession.getParametres());
		tss.remove(timeoutSession);
	}

	public void testGetFullURL() throws Exception
	{
		String url = "url";
		String parameters = "parameters";
		String expected = url + "?" + parameters;
		TimeoutSession ts = new TimeoutSession();

		ts.setUrl(url);
		ts.setParametres(parameters);
		assertEquals(expected, ts.getFullUrl());

		ts.setUrl(url+"?");
		ts.setParametres(parameters);
		assertEquals(expected, ts.getFullUrl());

		ts.setUrl(url+"?");
		ts.setParametres("?"+parameters);
		assertEquals(expected, ts.getFullUrl());

		ts.setUrl(url);
		ts.setParametres("?"+parameters);
		assertEquals(expected, ts.getFullUrl());

		ts.setUrl(url);
		ts.setParametres(null);
		assertEquals(url, ts.getFullUrl());

		url += "?url_parameters";
		expected = url + "&" + parameters;

		ts.setUrl(url);
		ts.setParametres(parameters);
		assertEquals(expected, ts.getFullUrl());

		ts.setUrl(url);
		ts.setParametres("?"+parameters);
		assertEquals(expected, ts.getFullUrl());

		ts.setUrl(url);
		ts.setParametres(null);
		assertEquals(url, ts.getFullUrl());
	}
}
