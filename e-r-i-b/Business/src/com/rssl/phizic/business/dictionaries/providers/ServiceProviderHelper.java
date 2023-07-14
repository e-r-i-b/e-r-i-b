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
	 * ���������� ���������� �����
	 * @param id - ������������� ���������� �������
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
	 * ���������� ������� ������������ iqvave
	 * @return ������� ������������ iqvave
	 * @throws BusinessException
	 */
	public static String getIQWaveUUID() throws BusinessException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
			throw new BusinessException("�� ������ ������� ������� ��� ��������� ���������");

		return adapter.getUUID();
	}

	/**
	 * ���������� �������� �� ��������� iqwave
	 * @param synchKey ������� ������������� ����������
	 * @return �������� �� ��������� iqwave
	 * @throws BusinessException
	 */
	public static boolean isIQWProvider(String synchKey) throws BusinessException
	{
		if (StringHelper.isEmpty(synchKey))
		{
			throw new IllegalArgumentException("������� ������������� ���������� ����� �� ����� ���� null");
		}

		return getIQWaveUUID().equals(IDHelper.restoreRouteInfo(synchKey));
	}

	/**
	 * @return ��������� �� ������� ��������� ����������� ����� iqwave.
	 * @throws BusinessException
	 */
	public static boolean isIQWaveAutoPaymentPermit() throws BusinessException
	{
		return PermissionUtil.impliesOperation("CreateAutoPaymentOperation", "CreateAutoPaymentPayment");
	}

	/**
	 * @return ��������� �� ������� ��������� ������ �����������.
	 * @throws BusinessException
	 */
	public static boolean isESBAutoPaymentPermit() throws BusinessException
	{
		return PermissionUtil.impliesOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
	}

	/**
	 * ���������� ���������� ����� � ������
	 * @param shortcut ���������� �� ���������� ���������� �����
	 * @return true - �����
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
	 * ����������� ������� ����������
	 * @param shortcut ���������� ����������
	 * @return true - �������
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
			//������������� ���������� � ���������� � ���� ������������ ������������� �� ������������ �������� �����������
			return false;
		}
		if (isIQWProvider(shortcut.getSynchKey()))
		{
			//��� iqw
			return PermissionUtil.impliesOperation("CreateFormPaymentOperation", "CreateAutoPaymentPayment");
		}

		//����������� ������ ����� ����
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
