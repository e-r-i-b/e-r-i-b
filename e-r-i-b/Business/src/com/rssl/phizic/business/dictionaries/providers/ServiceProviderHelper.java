package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.wrappers.ServiceProviderShortcut;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author lukina
 * @ created 04.11.2013
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderHelper
{
	private static final ServiceProviderService providerService = new ServiceProviderService();


	/**
	 * Возвращает поставщика услуг
	 * @param id - идентификатор получателя платежа
	 * @return ServiceProviderBase
	 */
	public static final ServiceProviderShort getServiceProvider(Long id) throws BusinessException
	{
		if (id != null && id > 0)
		{
			return providerService.findShortProviderById(id);
		}
		return null;
	}

	/**
	 * Возвращает внешний идентифкатор iqvave
	 * @return внешний идентифкатор iqvave
	 * @throws BusinessException
	 */
	public static String getIQWaveUUID() throws BusinessException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
			throw new BusinessException("Не задана внешняя система для карточных переводов");

		return adapter.getUUID();
	}

	/**
	 * Определяет является ли поставщик iqwave
	 * @param synchKey внешний идентификатор поставщика
	 * @return является ли поставщик iqwave
	 * @throws BusinessException
	 */
	public static boolean isIQWProvider(String synchKey) throws BusinessException
	{
		if (StringHelper.isEmpty(synchKey))
		{
			throw new IllegalArgumentException("Внешний идентификатор поставщика услуг не может быть null");
		}

		return getIQWaveUUID().equals(IDHelper.restoreRouteInfo(synchKey));
	}

	/**
	 * @return разрешено ли клиенту создавать автоплатежи через iqwave.
	 * @throws BusinessException
	 */
	public static boolean isIQWaveAutoPaymentPermit() throws BusinessException
	{
		return PermissionUtil.impliesOperation("CreateAutoPaymentOperation", "CreateAutoPaymentPayment");
	}

	/**
	 * @return разрешено ли клиенту создавать шинные автоплатежи.
	 * @throws BusinessException
	 */
	public static boolean isESBAutoPaymentPermit() throws BusinessException
	{
		return PermissionUtil.impliesOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
	}

	/**
	 * Готовность поставщика услуг к оплате
	 * @param shortcut информация по активности поставшика услуг
	 * @return true - готов
	 */
	public static boolean isReadyToPay(ServiceProviderShortcut shortcut)
	{
		if (shortcut == null)
		{
			return false;
		}

		ServiceProviderState state = shortcut.getState();
		if (!(ServiceProviderState.MIGRATION == state || ServiceProviderState.ACTIVE == state))
		{
			return false;
		}

		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isMobileApi())
		{
			boolean ignore = ConfigFactory.getConfig(MobileApiConfig.class).isTemplateIgnoreProviderAvailability();
			if (ignore)
			{
				return true;
			}
			return DocumentHelper.checkAvailablePayments(shortcut, MobileApiUtil.getApiVersionNumber());
		}
		return DocumentHelper.checkAvailablePayments(shortcut, null);
	}

	/**
	 * Возможность создать автоплатеж
	 * @param shortcut активность поставщика
	 * @return true - активен
	 * @throws BusinessException
	 */
	public static boolean isAutoPaymentAllowed(ServiceProviderShortcut shortcut) throws BusinessException
	{
		if (shortcut == null)
		{
			return PermissionUtil.impliesOperation("CreateFreeDetailAutoSubOperation", "ClientFreeDetailAutoSubManagement");
		}
		if (!("B".equals(shortcut.getKind())) || !(checkAutoPaymentSupport(shortcut)) || shortcut.getState() != ServiceProviderState.ACTIVE)
		{
			//небиллинговые получатели и получатели с явно запрещенными автоплатежами не поддерживают создания автоплатежа
			return false;
		}
		if (isIQWProvider(shortcut.getSynchKey()))
		{
			//для iqw
			return PermissionUtil.impliesOperation("CreateFormPaymentOperation", "CreateAutoPaymentPayment");
		}

		//Биллинговый платеж через шину
		return PermissionUtil.impliesOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
	}

	private static boolean checkAutoPaymentSupport(ServiceProviderShortcut activityInfo)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
		{
			return activityInfo.isATMAutoPaymentAvailability();
		}
		if (applicationInfo.isMobileApi())
		{
			return activityInfo.isMAPIAutoPaymentAvailability();
		}
		if (applicationInfo.isSMS() || applicationInfo.isMbkErmbMigration())
		{
			return activityInfo.isERMBAutoPaymentAvailability();
		}
		if (applicationInfo.isWeb())
		{
			return activityInfo.isWebAutoPaymentAvailability();
		}
		if (applicationInfo.isSocialApi())
		{
			return activityInfo.isSocialAutoPaymentAvailability();
		}
		throw new IllegalArgumentException();
	}
}
