package com.rssl.phizic.mbproviders.listener;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.mbproviders.listener.generated.EntryType;
import com.rssl.phizic.mbproviders.listener.generated.GetProvidersRqType;
import com.rssl.phizic.mbproviders.listener.generated.GetProvidersRsType;
import com.rssl.phizic.mbproviders.listener.generated.PaymentType;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для получения справочника поставщиков услуг Мобильного Банка
 *
 * @author EgorovaA
 * @ created 14.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class MBProvidersDictionaryServiceImpl
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);

	public GetProvidersRsType getProviders(GetProvidersRqType parameters)
	{
		GetProvidersRsType response = new GetProvidersRsType();
		response.setRqUID(parameters.getRqUID());
		try
		{
			response.setStatus(0);
			response.setResponse(createResponse());
		}
		catch (BusinessException e)
		{
			response.setStatus(1);
			log.error(e.getMessage(), e);
		}

		return response;
	}

	/**
	 * Сформировать тело ответа на запрос справочника поставщиков
	 * @return список с информацией о поставщиках
	 * @throws BusinessException
	 */
	private PaymentType[] createResponse() throws BusinessException
	{
		List<BillingServiceProvider> providers = serviceProviderService.getMBProviders();

		List<PaymentType> paymentInfoList = new ArrayList<PaymentType>();

		for (BillingServiceProvider provider : providers)
		{

			List<String> missingParams = new ArrayList<String>();
			if (StringHelper.isEmpty(provider.getAlias()))
				missingParams.add("providerSmsAlias");
			if (StringHelper.isEmpty(provider.getNameService()))
				missingParams.add("serviceName");

			if (missingParams.isEmpty())
			{
				PaymentType paymentInfo = new PaymentType();
				Long billingId = provider.getBilling().getId();
				paymentInfo.setBillingId(billingId.toString());
				String providerIdStr = provider.getId().toString();
				paymentInfo.setProviderId(providerIdStr);
				paymentInfo.setServiceId(providerIdStr);
				paymentInfo.setProviderName(provider.getName());
				paymentInfo.setProviderSmsAlias(provider.getAlias());
				paymentInfo.setServiceName(provider.getNameService());

				List<? extends Field> fields = serviceProviderService.getRecipientFields(provider);
				if (!fields.isEmpty())
				{
					List<EntryType> entriesList = new ArrayList<EntryType>(fields.size());
					for (Field field : fields)
					{
						// выкидываем из выборки реквизиты с суммой
						if (!field.isMainSum())
						{
							EntryType entry = new EntryType();
							entry.setTitle(field.getName());
							entry.setDescription(field.getDescription());
							entry.setHint(field.getHint());

							entriesList.add(entry);
						}
					}
					if (!entriesList.isEmpty())
						paymentInfo.setPaymentEntries(entriesList.toArray(new EntryType[entriesList.size()]));
				}
				paymentInfoList.add(paymentInfo);
			}
			else
			{
				log.error("[ЕРМБ]:Ошибка при получении информации о поставщике с id = " + provider.getId() + ". " +
						"Отсутствуют параметры: " + missingParams);
			}
		}
		return paymentInfoList.toArray(new PaymentType[paymentInfoList.size()]);
	}
}
