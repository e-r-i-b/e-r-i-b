package com.rssl.phizic.operations.access.verifier;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AllowOperationVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * User: Moshenko
 * Date: 01.06.12
 * Time: 16:13
 * Условие на добавление CheckCapConfirmAccessOperation, в права клиента до момента его полной авторизации. 
 */
public class CapAllowOperationVerifier implements AllowOperationVerifier
{
	private static final ServiceService serviceService = new ServiceService();
	private static final String SERVICE_NAME = "CheckCapConfirmAccessService";
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
