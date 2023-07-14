package com.rssl.phizic.auth.modes;

import com.rssl.phizic.config.ConfigFactory;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

@SuppressWarnings({"JavaDoc"})
public class JAXBAuthenticationConfigTest extends TestCase
{

	public void testEmployee()
	{
		verifyConfig((JAXBAuthenticationConfig) ConfigFactory.getConfig(AuthenticationConfig.class));
	}

	public void testClient()
	{
		verifyConfig((JAXBAuthenticationConfig) ConfigFactory.getConfig(AuthenticationConfig.class));
	}

	private void verifyConfig(JAXBAuthenticationConfig config)
	{
		List<AccessPolicy> list = config.getPolicies();

		assertNotNull(list);
		assertTrue(list.size() > 0);

		for (AccessPolicy policy : list)
		{
			assertNotNull(policy);
			AccessType accessType = policy.getAccessType();
			assertNotNull(accessType);
			assertNotNull("Политика " + accessType, policy.getConfirmationMode());
			assertNotNull("Политика " + accessType, config.getPolicy(accessType));

			if (accessType == AccessType.anonymous)
				continue;

			AuthenticationMode authMode = policy.getAuthenticationMode(UserVisitingMode.BASIC);
			assertNotNull("Политика " + accessType, authMode);
			List<Stage> stages = authMode.getStages();

			assertNotNull("Режим " + accessType, stages);

			for (Stage stage : stages)
			{
				assertNotNull(stage);
				assertNotNull(stage.getPrimaryAction());
				stage.getNext();
			}
		}
	}
}
