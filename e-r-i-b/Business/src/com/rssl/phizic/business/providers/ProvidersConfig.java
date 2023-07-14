package com.rssl.phizic.business.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author bogdanov
 * @ created 16.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ProvidersConfig extends Config
{
	/**
	 * Префикс для поиска параметров.
	 */
	protected static final String MOBILE_SERVICE_KEY = "com.rssl.iccs.payments.mobileService.id";
	protected static final String PROVIDER_OPTIONS_PREFIX = "com.rssl.iccs.provider.";
	protected static final String MOBILE_NUMBER_PAYMENT_KEY = PROVIDER_OPTIONS_PREFIX + "mobile";
	protected static final String PAY_ANY_PROVIDER_ID_PROPERTY = "com.rssl.phizic.payments.payAnyProvider.id";
	protected static final String EMAIL_BILLING_CODES_KEY = "com.rssl.iccs.email.billing.codes";
	protected static final String MOBILE_PROVIDER_KEY = "com.rssl.business.mobileProvidersProperties_";
	public static final String YANDEX_PAYMENT_ID_KEY = "com.rssl.business.simple.yandexPaymentId";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ServiceProviderService providerService = new ServiceProviderService();

	/**
	 * Разделитель поставщиков услуг.
	 */
	public static final String PROVIDER_DELIMITER = "\\|";
	/**
	 * Разделитель идентифицирующих свойств поставщика.
	 */
	public static final String PROVIDER_PROPERTY_DELIMITER = ";";
	/**
	 * Множество идентификаторов поставщиков мобильной связи.
	 */
	protected Set<String> mobileProviders = new HashSet<String>();
	/**
	 * Идентификатор услуги "Мобильная связь"
	 */
	private Long mobileServiceId;
	/**
	 * Внешний идентификатор поставщика "Oплата любых мобильных телефонов"
	 */
	private String payAnyProviderExtId;
	/**
	 * Cписок идентификаторов поставщиков мобильной связи.
	 */
	private List<String> mobileProviderIds = new ArrayList<String>();

	private long yandexPaymentId;

	private List<String> emailBillingCode = new ArrayList<String>();

	private ServiceProviderBase yandexProvider;

	public ProvidersConfig(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	/**
	 * @return список идентификаторов поставщиков мобильной связи.
	 */
	public Set<String> getMobileProviderIds()
	{
		if (CollectionUtils.isNotEmpty(mobileProviderIds))
			return new HashSet<String>(mobileProviderIds);

		return mobileProviders;
	}


	/**
	 * @return идентификатор поставшика услуг Яндекс-деньги
	 */
	public long getYandexPaymentId()
	{
		return yandexPaymentId;
	}

	/**
	 * @return идентификатор услуги "Мобильная связь"
	 */
	public Long getMobileServiceId()
	{
		return mobileServiceId;
	}

	/**
	 * @return внешний идентификатор поставщика "Оплата любых мобильных телефонов"
	 */
	public String getPayAnyProviderExtId()
	{
		return payAnyProviderExtId;
	}

	/**
	 * @return коды биллинговых систем, для которых доступен символ @ в реквизитах оплаты
	 */
	public List<String> getEmailBillingCode()
	{
		return emailBillingCode;
	}

	/**
	 * @return Поставщик Яндекс.Деньги либо null, если поставщика нет или не задана настройка "идентификатор поставшика услуг Яндекс-деньги"
	 */
	public ServiceProviderBase getYandexProvider()
	{
		return yandexProvider;
	}

	public void doRefresh() throws ConfigurationException
	{
		yandexPaymentId = getLongProperty(YANDEX_PAYMENT_ID_KEY);

		String mobilePrvdrs = getProperty(MOBILE_NUMBER_PAYMENT_KEY);

		if (mobilePrvdrs == null)
			return;
		
		String[] strs = mobilePrvdrs.split(PROVIDER_DELIMITER);

		mobileProviders.clear();
		for (String s : strs)
		{
			mobileProviders.add(s);
		}

		mobileServiceId = getLongProperty(MOBILE_SERVICE_KEY);
		payAnyProviderExtId = getProperty(PAY_ANY_PROVIDER_ID_PROPERTY);

		String billingCodes = getProperty(EMAIL_BILLING_CODES_KEY);
		if (StringHelper.isNotEmpty(billingCodes))
		{
			emailBillingCode.addAll(Arrays.asList(billingCodes.split(PROVIDER_DELIMITER)));
		}

		Properties providerProperties = getProperties(MOBILE_PROVIDER_KEY);
		for (Object providerId : providerProperties.values())
		{
			mobileProviderIds.add((String)providerId);
		}

		try
		{
			ServiceProviderBase providerBase = providerService.findById(yandexPaymentId);
			if(providerBase != null)
			{
				List<FieldDescription> keyFields = providerBase.getKeyFields();
				if (CollectionUtils.isNotEmpty(keyFields) && keyFields.size() == 1)
					yandexProvider = providerBase;
				else
				{
					yandexProvider = null;
					log.error("Задан некорректный поставщик Яндекс.Деньги");
				}
			}
			else
				yandexProvider = null;
		}
		catch (BusinessException e)
		{
			log.error(e);
		}
	}
}
