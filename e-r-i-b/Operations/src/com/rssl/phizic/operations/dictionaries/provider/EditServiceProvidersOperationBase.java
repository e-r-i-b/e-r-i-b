package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderSubType;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.SaveImageOperationBase;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author bogdanov
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class EditServiceProvidersOperationBase extends SaveImageOperationBase<ServiceProvidersRestriction>
{
	protected static final ServiceProviderService providerService = new ServiceProviderService();

	protected ServiceProviderBase provider;

	private static final String MESSAGE_KEY_FIELD_AUTOPAY = "Для поставщика, поддерживающего автоплатеж, должно быть только одно ключевое поле";
    private static final String MESSAGE_KEY_FIELD_MOBILE_BANK = "Для поставщика мобильного банка должно быть только одно ключевое поле";

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	protected <T> T executeTransactional(HibernateAction<T> action) throws BusinessException, BusinessLogicException
	{
		try
		{
			T result = HibernateExecutor.getInstance(getInstanceName()).execute(action);
			sendUpdateDictionaryEvent(provider.getClass());
			return result;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	protected void sendUpdateDictionaryEvent(Class entityClass)
	{
		MultiBlockModeDictionaryHelper.updateDictionary(entityClass);
	}

	/**
	 * Проверяет количество ключевых полей у поставщика, поддерживающего автоплатежи, или поставщика МБ.
	 * только для iqWave и МБ, у остальных поставщиков может быть много ключевых полей
	 *
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	protected final void checkBillingServiceProviderNumOfKeyField() throws BusinessException, BusinessLogicException
	{
		if(!(provider instanceof BillingServiceProvider))
            return;

        BillingServiceProvider billingServiceProvider = (BillingServiceProvider) provider;
        boolean isAutoPaymentSupported = AutoPaymentHelper.isIQWProvider(billingServiceProvider) &&
		        (billingServiceProvider.isAutoPaymentSupported() ||
				        billingServiceProvider.isAutoPaymentSupportedInApi() ||
				        billingServiceProvider.isAutoPaymentSupportedInATM());
        boolean isMobileBank = StringHelper.isNotEmpty(billingServiceProvider.getMobilebankCode());
        if (isAutoPaymentSupported || isMobileBank)
		{
			if(CollectionUtils.isEmpty(provider.getFieldDescriptions()))
			{
				if(isAutoPaymentSupported)
					throw new BusinessLogicException(MESSAGE_KEY_FIELD_AUTOPAY);
				else if(isMobileBank)
					throw new BusinessLogicException(MESSAGE_KEY_FIELD_MOBILE_BANK);
			}
			//считаем ключевые поля
			int countKeyFieldDescription = 0;
			for (FieldDescription fieldDescription : provider.getFieldDescriptions())
            {
				if (fieldDescription.isKey())
					countKeyFieldDescription++;

                if (countKeyFieldDescription > 1)
                {
                    if(isAutoPaymentSupported)
                        throw new BusinessLogicException(MESSAGE_KEY_FIELD_AUTOPAY);
                    else if(isMobileBank)
                        throw new BusinessLogicException(MESSAGE_KEY_FIELD_MOBILE_BANK);
                }
            }
		}
	}

	/**
	 * при изменении поставщика неоходимо чистить кеш xml-справочника поставщиков
	 */
	protected void clearEntitiesListCache()
	{
		//при изменении поставщика неоходимо чистить кеш xml-справочника поставщиков
		XmlEntityListCacheSingleton.getInstance().clearCache(provider, ServiceProviderBase.class);
	}

	/**
	 * Если  тип поставщика – «Мобильная связь», то  только одно ключевое поле  может быть отмечено типом, и тип может принимать только значение – «Мобильный телефон»
	 */
	protected void checkServiceProviderSubTypeFieldRestriction() throws BusinessLogicException
	{
		if (provider.getSubType() == ServiceProviderSubType.mobile)
		{
			int counter = 0;
			for (FieldDescription fd : provider.getFieldDescriptions())
			{
				if (fd.isKey() && fd.getBusinessSubType() != null)
					counter++;
				if (counter > 1 || (fd.getBusinessSubType() != null && fd.getBusinessSubType() != BusinessFieldSubType.phone && fd.isKey()))
					throw new BusinessLogicException("Для поставщика с типом «Мобильная связь» должно быть добавлено одно ключевое поле, имеющее тип «Мобильный телефон»");
			}
			if(counter == 0)
				throw new BusinessLogicException("Для поставщика с типом «Мобильная связь» должно быть добавлено одно ключевое поле, имеющее тип «Мобильный телефон»");			
		}
	}
}
