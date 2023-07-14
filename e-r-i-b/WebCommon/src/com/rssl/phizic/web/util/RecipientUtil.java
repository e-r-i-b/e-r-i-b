package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hudyakov
 * @ created 26.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class RecipientUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final RegionDictionaryService regionDictionaryService = new RegionDictionaryService();
	private static final int AUTOPAY_PROVIDER_COUNT = 3;

	private static final ExternalResourceService resourceService = new ExternalResourceService();

	/**
	 * ���������� ���������� �����
	 * @param id - ������������� ���������� �������
	 * @return ServiceProviderBase
	 */
	public static final ServiceProviderShort getServiceProvider(Long id)
	{
		if (id != null && id > 0)
		{
			try
			{
				return providerService.findShortProviderById(id);
			}
			catch (Exception e)
			{
				log.error("������ ����������� ���������� �����", e);
			}
		}
		return null;
	}

	/**
	 * ���������� ���������� ����� � ������ ������������� ���������
	 * @param id - ������������� ���������� �������
	 * @return ServiceProviderBase
	 */
	public static final ServiceProviderBase getServiceProviderConsiderMultiBlock(Long id)
	{
		if (id != null && id > 0)
		{
			try
			{
				return providerService.findById(id, MultiBlockModeDictionaryHelper.getDBInstanceName());
			}
			catch (Exception e)
			{
				log.error("������ ����������� ���������� �����", e);
			}
		}
		return null;
	}

	/**
	 * ���������� ������, ���� � ������� �������� ��������, ���� - ���� ����������
	 * @param provider - ���������� �������
	 * @return boolean
	 */
	public static boolean isPaymentCommissionAvailable(ServiceProviderBase provider)
	{
		if ((provider == null) || (provider.getType() == ServiceProviderType.TAXATION))
		{
			return false;
		}
		else
		{
			GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
			try
			{
				return gateInfoService.isPaymentCommissionAvailable(((BillingServiceProvider) provider).getBilling());
			}
			catch (Exception e)
			{
				log.error("������ ����������� ������������� ������� �������� � ����������� �������", e);
				return false;
			}
		}
	}

	/**
	 * ������� ��� ����� ��������� ���������, ������������ ������ ������������ ����� � ���������� ���������� �� ����� �� ����������
	 * @param link ���� �����������, �� �������� ������ ���������
	 * @return ������ ������������ ����� � ���������� ����������, ���� ����� �������
	 */
	public static TotalAmountPeriod getAutoPayTotalAmountPeriod(AutoPaymentLink link)
	{
		if(link == null)
			throw new IllegalArgumentException("link �� ����� ���� null");

		try
		{
			return getProviderAutoPayTotalAmountPeriod(link.getServiceProvider());
		}
		catch (Exception e)
		{
			log.error("�� ������� �������� ������ ������������ ����� � ����������, c��������������� ����� � id = " + link.getId(), e);
			return null;
		}
	}

	/**
	 * ������� ��� ����� ������ �����������, ������������ ������ ������������ ����� � ���������� ���������� �� ����� �� ����������
	 * @param linkId ������������� ����� �����������, �� �������� ������ ���������
	 * @return ������ ������������ ����� � ���������� ����������, ���� ����� �������
	 *
	 */
	public static TotalAmountPeriod getAutoPayTotalAmountPeriod(String linkId)
	{
		if(StringHelper.isEmpty(linkId))
			throw new IllegalArgumentException("linkId �� ����� ���� null");

		try
		{
			return getAutoPayTotalAmountPeriod(resourceService.findLinkById(AutoPaymentLink.class, Long.parseLong(linkId)));
		}
		catch (Exception e)
		{
			log.error("�� ������� �������� ��������� ������� ������������ ����� � ����������, ���������� � ������������(id = " + linkId + ")", e);
			return null;
		}
	}

	private static TotalAmountPeriod getProviderAutoPayTotalAmountPeriod(ServiceProviderBase providerBase)
	{
		if(providerBase == null)
			return null;

		if(!(providerBase instanceof BillingServiceProvider))
			return null;

		BillingServiceProvider provider = (BillingServiceProvider) providerBase;
		if(!provider.isAutoPaymentSupported())
			return null;

		ThresholdAutoPayScheme scheme = provider.getThresholdAutoPayScheme();
		if(scheme == null)
			return null;

		if(!scheme.isAccessTotalMaxSum())
			return null;

		return scheme.getPeriodMaxSum();
	}

	/**
	 * ������ �����������, ������� ���������� ���������� � �����-����� ������������ � ������� �������
	 * @param regionId - ������ ������
	 * @return  ������ �����������
	 */
	public static List<ServiceProviderShort> getAutoPayPromoBlock(Long regionId)
	{
		List<ServiceProviderShort> list = new ArrayList<ServiceProviderShort>();
		try
		{
			if (regionId != 0 && regionId != -1)
			{
				//���� � ������� ������ ������, ���� 3� ����������� � ���
				list = providerService.getProviderRegions(regionId, AUTOPAY_PROVIDER_COUNT);

				if (list.size() < AUTOPAY_PROVIDER_COUNT)
				{
					//���� ������� ������ ����������� ���� � ������������ �������, ���� �� ����
					Region region = regionDictionaryService.findById(regionId);
					int providersCount = AUTOPAY_PROVIDER_COUNT - list.size();
					if (region.getParent() != null)
					{
						list.addAll(providerService.getProviderRegions(region.getParent().getId(), providersCount));
					}
				}
			}
			//���� ��� ��� �� ������� 3� ����������� ����� ������ �� ��������
			if (list.size() < AUTOPAY_PROVIDER_COUNT)
			{
				String[] providers = ConfigFactory.getConfig(DocumentConfig.class).getAutopaymentPopularProvider();
				int count = AUTOPAY_PROVIDER_COUNT - list.size();
				for(int i = 0; i < count; i++)
				{
					ServiceProviderShort provider  = providerService.findShortProviderById(Long.parseLong(providers[i]));
					if (provider != null && provider.isAutoPaymentSupported())
						list.add(provider);
				}
			}
		}
		catch (Exception ex)
		{
			log.error("������ ��� ��������� ������ �����������", ex);
		}
		return list;
	}

	/**
	 * ���������� ���������� �� ��������� � ����� ���������� ���������
	 * @param externalId - �������� �������������
	 * @return ������� ������
	 */
	public static boolean needShowChangeProviderMessage(Boolean byTemplate, String externalId)
	{
		if (StringHelper.isEmpty(externalId))
			return false;

		if (byTemplate)
		{
			try
			{
				return providerService.isMobileProvider(externalId);
			}
			catch (BusinessException e)
			{
				log.error("������ ��� ����������� ���������� ���������", e);
				return false;
			}
		}
		else
		{
			ProvidersConfig providerConfig = ConfigFactory.getConfig(ProvidersConfig.class);
			String payAnyProviderId = providerConfig.getPayAnyProviderExtId();
			return externalId.equals(payAnyProviderId);
		}
	}
}
