package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.services.Service;

/**
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class RestrictionProviderImpl implements RestrictionProvider
{
	private static final RestrictionService restrictionService = new RestrictionService();

	public Restriction get(CommonLogin login, Service service, OperationDescriptor operationDescriptor) throws BusinessException
	{
		RestrictionData restrictionData = restrictionService.find(login, service, operationDescriptor);
		return(restrictionData == null) ? getDefault(operationDescriptor) : restrictionData.buildRestriction();
	}

	public Restriction get(Service service, OperationDescriptor operationDescriptor) throws BusinessException
	{
		CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();
		return get(login, service, operationDescriptor);
	}

	public Restriction getDefault(OperationDescriptor operationDescriptor) throws BusinessException
	{
		try
		{
			String defaultRestrictionClassName = operationDescriptor.getDefaultRestrictionClassName();
			if (defaultRestrictionClassName == null)
				throw new BusinessException("Default restriction class name for operation: "
						+ operationDescriptor + " must be defined.");

			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(defaultRestrictionClassName);
			//noinspection unchecked
			return (Restriction) clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}