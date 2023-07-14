package com.rssl.phizic.business.profileSynchronization.products;

import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.profileSynchronization.ProfileSynchronizationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.List;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * @author lepihina
 * @ created 28.05.14
 * $Author$
 * $Revision$
 *
 * Хэлпер для работы с настройками отображения и видимости продуктов (для хранения в централизованном хранилище)
 */
public class ResourceInfoSynchronizationHelper
{
	private static final ResourceInfoService resourceInfoService = new ResourceInfoService();
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	/**
	 * Обновляет настройки продуктов клиента
	 * @param resourceLink - линк измененного продукта
	 */
	public static void updateResourceInfo(EditableExternalResourceLink resourceLink)
	{
		updateResourceInfo(getResourceClass(resourceLink));
	}

	/**
	 * Обновляет настройки продуктов клиента
	 * @param resourceClass - линк измененного продукта
	 */
	public static void updateResourceInfo(Class resourceClass)
	{
		if (!isUseReplicatePersonSettings())
			return;

		try
		{
			Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
			List<ResourceInfo> resourceInfoList = resourceInfoService.getResourceInfo(resourceClass, loginId);
			PersonSettingsManager.savePersonData(getResourceSynchronizationKey(resourceClass), resourceInfoList);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private static Class getResourceClass(EditableExternalResourceLink resourceLink)
	{
		if (resourceLink instanceof AccountLink)
			return AccountLink.class;
		else if (resourceLink instanceof CardLink)
			return CardLink.class;
		else if (resourceLink instanceof LoanLink)
			return LoanLink.class;
		else if (resourceLink instanceof IMAccountLink)
			return IMAccountLink.class;
		else if (resourceLink instanceof DepoAccountLink)
			return DepoAccountLink.class;
		else if (resourceLink instanceof SecurityAccountLink)
			return SecurityAccountLink.class;
		else if (resourceLink instanceof PFRLink)
			return PFRLink.class;
		return null;
	}

	private static String getResourceSynchronizationKey(Class resourceClass)
	{
		if (AccountLink.class.equals(resourceClass))
			return PersonSettingsManager.ACCOUNT_INFO_KEY;
		else if (CardLink.class.equals(resourceClass))
			return PersonSettingsManager.CARDS_INFO_KEY;
		else if (LoanLink.class.equals(resourceClass))
			return PersonSettingsManager.LOAN_INFO_KEY;
		else if (IMAccountLink.class.equals(resourceClass))
			return PersonSettingsManager.IMACCOUNT_INFO_KEY;
		else if (DepoAccountLink.class.equals(resourceClass))
			return PersonSettingsManager.DEPO_ACCOUNT_INFO_KEY;
		else if (SecurityAccountLink.class.equals(resourceClass))
			return PersonSettingsManager.SECURITY_ACCOUNT_INFO_KEY;
		else if (PFRLink.class.equals(resourceClass))
			return PersonSettingsManager.PFR_LINK_INFO_KEY;
		return null;
	}

	/**
	 * @return true - механизм сохранения данных в резервное хранилище включен
	 */
	private static boolean isUseReplicatePersonSettings()
	{
		return ConfigFactory.getConfig(ProfileSynchronizationConfig.class).isUseReplicatePersonSettings();
	}
}
