package com.rssl.phizic.operations.locale.user;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * @author komarov
 * @ created 21.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ChangeUserLocaleOperation extends OperationBase implements EditEntityOperation
{
	private ERIBLocale locale;
	private Application application;

	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	/**
	 * @param localeId идентификатор локали
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(String localeId) throws BusinessException, BusinessLogicException
	{
		try
		{
			locale = LOCALE_SERVICE.getById(localeId, getInstanceName());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		application = ApplicationInfo.getCurrentApplication();
		if(!locale.applicationAllowed(application))
			throw new BusinessLogicException("Локаль с id= " + localeId + "недоступна");

	}

	public void save() throws BusinessException, BusinessLogicException
	{
		UserPropertiesConfig userPropertiesConfig = ConfigFactory.getConfig(UserPropertiesConfig.class);
		userPropertiesConfig.setERIBLocaleId(locale.getId(), application);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return locale;
	}
}
