package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.util.MaskPhoneNumberUtil;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaySchemeBase;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Krenev
 * @ created 07.10.2011
 * @ $Author$
 * @ $Revision$
 * Динамический справочник разрешенных типов автоплатежей
 * Список строится как пересечение настроек в АРМ сотрудника и параметров ВС.
 * Входные параметры:
 * 1) номер карты (очередной бред iqw)
 * 2) код поставщика
 * 3) значение лицевого счета.
 */
public class AutoPaymentTypesListsSource implements EntityListSource
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private void createSimpleEntity(EntityListBuilder builder, List<ExecutionEventType> gateAllowedeventTypes, ExecutionEventType eventType, AutoPaySchemeBase scheme)
	{
		if (gateAllowedeventTypes.contains(eventType))
		{
			builder.openEntityTag(eventType.toString());
			builder.appentField("hint", scheme.getClientHint());
			builder.closeEntityTag();
		}
	}

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListBuilder getEntityListBuilder(Map<String, String> params) throws BusinessException
	{
		String providerId = params.get("provider-id");

		if (StringHelper.isEmpty(providerId))
		{
			throw new BusinessException("Не задан поставщик услуг");
		}

		String requisite = params.get("requisite");

		if (StringHelper.isEmpty(requisite))
		{
			throw new BusinessException("Не задан реквизит, идентфицирующий плательщика");
		}
		requisite = MaskPhoneNumberUtil.unmaskPhone(requisite);
		String cardNumber = params.get("card-number");

		if (StringHelper.isEmpty(cardNumber))
		{
			throw new BusinessException("Не задана карта плательщика");
		}

		ServiceProviderBase provider = serviceProviderService.findById(Long.valueOf(providerId));
		if (provider == null)
		{
			throw new BusinessException("Не найден получатель с идентфикатором " + providerId);
		}

		if (!(provider instanceof BillingServiceProvider))
		{
			throw new BusinessException("Получатель с идентификатором " + providerId + " не является поставщиком услуг");
		}

		BillingServiceProvider billingServiceProvider = (BillingServiceProvider) provider;
		if (!checkAutoPaymentSupported(billingServiceProvider))
		{
			throw new BusinessException("Получатель с идентификатором " + providerId + " не поддерживает автоплатежи");
		}

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();
		try
		{
			AutoPaymentService autoPaymentService = GateSingleton.getFactory().service(AutoPaymentService.class);
			List<ExecutionEventType> gateAllowedeventTypes = autoPaymentService.getAllowedAutoPaymentTypes(cardNumber, requisite, (String) provider.getSynchKey());
			if (billingServiceProvider.getAlwaysAutoPayScheme() != null)
			{
				createSimpleEntity(builder, gateAllowedeventTypes, ExecutionEventType.ONCE_IN_YEAR, billingServiceProvider.getAlwaysAutoPayScheme());
				createSimpleEntity(builder, gateAllowedeventTypes, ExecutionEventType.ONCE_IN_HALFYEAR, billingServiceProvider.getAlwaysAutoPayScheme());
				createSimpleEntity(builder, gateAllowedeventTypes, ExecutionEventType.ONCE_IN_QUARTER, billingServiceProvider.getAlwaysAutoPayScheme());
				createSimpleEntity(builder, gateAllowedeventTypes, ExecutionEventType.ONCE_IN_MONTH, billingServiceProvider.getAlwaysAutoPayScheme());
			}
			ThresholdAutoPayScheme scheme = billingServiceProvider.getThresholdAutoPayScheme();
			if (scheme != null)
			{
				if (gateAllowedeventTypes.contains(ExecutionEventType.REDUSE_OF_BALANCE))
				{
					builder.openEntityTag(ExecutionEventType.REDUSE_OF_BALANCE.toString());
					builder.appentField("hint", scheme.getClientHint());
					if(Boolean.valueOf(scheme.isAccessTotalMaxSum()))
					{
						builder.appentField("isTotalMaxSum", Boolean.TRUE.toString());
						builder.appentField("totalMaxSumPeriod", StringHelper.getEmptyIfNull(scheme.getPeriodMaxSum()));
					}
					if (!scheme.isInterval())
					{
						for (BigDecimal limit : scheme.getAccessValuesAsList())
						{
							builder.appentField("thresholdAutoPayLimit", limit.toString());
						}
					}
					builder.closeEntityTag();
				}
			}
			if (billingServiceProvider.getInvoiceAutoPayScheme() != null)
			{
				createSimpleEntity(builder, gateAllowedeventTypes, ExecutionEventType.BY_INVOICE, billingServiceProvider.getInvoiceAutoPayScheme());
			}
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}

		builder.closeEntityListTag();
		return builder;
	}

	protected boolean checkAutoPaymentSupported(final BillingServiceProvider billingServiceProvider)
	{
		return billingServiceProvider.isAutoPaymentSupported();
	}
}
