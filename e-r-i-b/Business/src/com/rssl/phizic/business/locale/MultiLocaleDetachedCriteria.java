package com.rssl.phizic.business.locale;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizgate.ext.sbrf.technobreaks.locale.LocaledTechnoBreak;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.locale.LocaledCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.locale.LocaledCreditCardProduct;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.locale.LocaledCardOperationCategory;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.locale.LocaledServiceProvider;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.locale.LocaledTariffPlanConfig;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.business.ima.locale.LocaledIMAProduct;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.news.locale.LocaledNews;
import com.rssl.phizic.business.sms.SMSConfirmationResource;
import com.rssl.phizic.business.sms.SMSInformingResource;
import com.rssl.phizic.business.sms.SMSRefusingResource;
import com.rssl.phizic.business.sms.locale.LocaledSMSConfirmationResource;
import com.rssl.phizic.business.sms.locale.LocaledSMSInformingResource;
import com.rssl.phizic.business.sms.locale.LocaledSMSRefusingResource;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.locale.utils.LocaleHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 *
 *  ритери€ дл€ выполнени€ запросов к сущност€м с учетом текущей локали.
 */
public class MultiLocaleDetachedCriteria extends DetachedCriteria
{
	private static final String FILTER_NAME = "localeIdFilter";
	private static final String PARAMETER_NAME = "localeId";
	//мапа дл€ хранени€ соответствий между классом и его локалезависимым представлением.
	private static final Map<Class,Class> localedClassMap = new HashMap<Class, Class>();

	static
	{
		localedClassMap.put(News.class, LocaledNews.class);
		localedClassMap.put(TechnoBreak.class, LocaledTechnoBreak.class);
		localedClassMap.put(CardProduct.class, LocaledCardProduct.class);
		localedClassMap.put(CreditCardProduct.class, LocaledCreditCardProduct.class);
		localedClassMap.put(CardOperationCategory.class, LocaledCardOperationCategory.class);
		localedClassMap.put(ServiceProviderBase.class, LocaledServiceProvider.class);
		localedClassMap.put(BillingServiceProvider.class, LocaledServiceProvider.class);
		localedClassMap.put(TariffPlanConfig.class, LocaledTariffPlanConfig.class);
		localedClassMap.put(IMAProduct.class, LocaledIMAProduct.class);
		localedClassMap.put(SMSConfirmationResource.class, LocaledSMSConfirmationResource.class);
		localedClassMap.put(SMSInformingResource.class, LocaledSMSInformingResource.class);
		localedClassMap.put(SMSRefusingResource.class, LocaledSMSRefusingResource.class);
	}

	private String localeId;

	private MultiLocaleDetachedCriteria(String entityName, String localeId)
	{
		super(entityName);
		this.localeId = localeId;
	}

	/**
	 * ѕолучить критерию дл€ классов с учетом текущей локали.
	 * @param clazz - класс дл€ которого необходимо получить криетрию.
	 * @param localeId - идентификатор локали
	 * @return критери€
	 */
	public static DetachedCriteria forClassInLocale(Class clazz, String localeId) {

		Class localedClass = localedClassMap.get(clazz);
		if(localedClass == null)
			throw new ConfigurationException("ƒл€ класса " + clazz + " не задана локалезависима€ сущность");
		if(LocaleHelper.isDefaultLocale(localeId))
			return DetachedCriteria.forClass(clazz);
		return new MultiLocaleDetachedCriteria( localedClass.getName(), localeId );
	}

	@Override
	public Criteria getExecutableCriteria(Session session)
	{
		session.enableFilter(FILTER_NAME).setParameter(PARAMETER_NAME,localeId);
		return super.getExecutableCriteria(session);
	}
}
