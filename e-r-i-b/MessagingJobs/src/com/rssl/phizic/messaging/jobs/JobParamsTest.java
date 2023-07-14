package com.rssl.phizic.messaging.jobs;

import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.messaging.info.MessagingInfoTestCaseBase;

/**
 * @author Kosyakov
 * @ created 10.01.2007
 * @ $Author: gladishev $
 * @ $Revision: 61544 $
 */
public class JobParamsTest extends MessagingInfoTestCaseBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String ACCOUNT_NUMBER = "40820840500000000000";

	public void testJobParameters() throws Exception
	{
/*
        TODO переделать(изменился интерфейс)
		Subscription subscription = SubscriptionTest.getTestSubscription();

		final CommonLogin testLogin = subscription.getLogin();

		AccountLink accountLink = new AccountLink();
		accountLink.setExternalId(ACCOUNT_NUMBER);
		accountLink.setPaymentAbility(true);
		accountLink.setLogin(testLogin);
		externalResourceService.addLink(accountLink);

		Map<String, SubscriptionParameter> parameters = new HashMap<String, SubscriptionParameter>();
//		parameters.put("account", new AccountParameter(accountLink));
		parameters.put("decimal", new DecimalParameter(new BigDecimal(12345678.90)));

		subscription.setParameters(parameters);
		subscription = new SimpleService().update(subscription);

		ConfigFactory curr = new ConfigFactory();
		ConfigFactory prev = ConfigFactory.setInstance(curr);

		try
		{
			Initializer.initialize();
		}
		finally
		{
			ConfigFactory.setInstance(prev);
		}
*/
	}
}
