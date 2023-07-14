package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.business.BusinessException;

import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 18.04.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

@SuppressWarnings({"JavaDoc"})
public class EditPolicyTemplateOperationTest extends BusinessTestCaseBase
{
	private AccessPolicy        policy;
	private AccessPolicyService service;
	private Properties          properties;

	protected void setUp() throws Exception
	{
		super.setUp();

		AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class);

		policy = config.getPolicies().get(0);
		service = new AccessPolicyService();
		properties = service.getTemplateProperties(policy.getAccessType());
	}

	protected void tearDown() throws Exception
	{
		if(properties != null)
			service.enableTemplateAccess(policy.getAccessType(), properties);
		else
			service.disableTemplateAccess(policy.getAccessType());

		policy     = null;
		service    = null;
		properties = null;

		super.tearDown();
	}

	public void testEditPolicyTemplateOperation() throws BusinessException
	{

		EditPolicyTemplateOperation operation = new EditPolicyTemplateOperation();

		operation.initialize(policy.getAccessType());
		operation.setEnabled(true);

		operation.setProperty("testp", "testv");
		operation.setProperty("testp2", "");
		operation.save();

		operation.initialize(policy.getAccessType());

		assertTrue(operation.isEnabled());
		assertNotNull(operation.getPolicy());
		assertNotNull(operation.getProperties());

		operation.setEnabled(false);
		operation.save();

		operation.initialize(policy.getAccessType());

		assertFalse(operation.isEnabled());
		assertNotNull(operation.getPolicy());
		assertNotNull(operation.getProperties());
	}
}
