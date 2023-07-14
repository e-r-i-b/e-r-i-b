package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionProvider;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.config.ConfigFactory;

import javax.servlet.ServletException;

/**
 * @author Evgrafov
 * @ created 24.04.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57925 $
 */

public class RestrictionUtil
{
	private static RestrictionProvider restrictionProvider = new RestrictionProviderImpl();

	public static RestrictionProvider getRestrictionProvider()
	{
		return restrictionProvider;
	}

	public static void setRestrictionProvider(RestrictionProvider value)
	{
		restrictionProvider = value;
	}

	public static Restriction createRestriction(String serviceKey, Class operationClass) throws ServletException
	{
		try
		{
			OperationsConfig config = DbOperationsConfig.get();

			OperationDescriptor od = null;
			if (operationClass != null)
				od = config.findOperationByKey(operationClass.getSimpleName());

			Service service = null;
			if (serviceKey != null)
				service = config.findService(serviceKey);

			return restrictionProvider.get(service, od);
		}
		catch (BusinessException e)
		{
			throw new ServletException(e);
		}
	}
}