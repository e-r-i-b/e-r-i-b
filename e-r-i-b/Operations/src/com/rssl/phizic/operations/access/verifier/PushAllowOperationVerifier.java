package com.rssl.phizic.operations.access.verifier;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AllowOperationVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * Условие на добавление CheckPushConfirmAccessOperation, в права клиента до момента его полной авторизации.
 * @author basharin
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushAllowOperationVerifier implements AllowOperationVerifier
{
	private static final ServiceService serviceService = new ServiceService();
	private static final String SERVICE_NAME = "ClientProfilePush";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public boolean isAllow(String operationClassName, CommonLogin login)
	{
		try
		{
			return serviceService.isPersonServices(login.getId(),SERVICE_NAME);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
