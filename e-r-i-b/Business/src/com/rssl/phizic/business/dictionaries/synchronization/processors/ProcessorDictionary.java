package com.rssl.phizic.business.dictionaries.synchronization.processors;

import com.rssl.phizic.business.dictionaries.synchronization.SynchronizeException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.account.banned.BannedAccountProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.advertising.AdvertisingBlockProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.banks.BankProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.basketiident.BasketIdentifierTypeProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.departments.DepartmentProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.departments.LimitProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.departments.ReceptionTimeProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.departments.WorkCalendarProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.deposit.DepositGlobalProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.deposit.DepositProductProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.exception.ExceptionMappingProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.exception.ExceptionMappingResourceProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ima.IMAProductProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.advertising.AdvertisingBlockResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.advertising.AdvertisingButtonResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.banks.BankResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.news.NewsResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.regions.RegionResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.service.PaymentServiceResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.service.provider.ServiceProviderResourcesProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.mail.ContactCenterAreaProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.mail.MailSubjectProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.news.NewsProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.pfp.*;
import com.rssl.phizic.business.dictionaries.synchronization.processors.promoCodesDeposit.PromoCodesDepositProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.providers.*;
import com.rssl.phizic.business.dictionaries.synchronization.processors.quick.pay.QuickPaymentPanelProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.region.RegionProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.BillingProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.GroupRiskProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.PaymentServiceProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link.*;
import com.rssl.phizic.business.dictionaries.synchronization.processors.spoobk.DepartmentsRecordingProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.template.offer.CreditOfferTemplateProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.udboClaimRules.UDBOClaimRulesProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Справочник процессоров
 */

public final class ProcessorDictionary
{
	private static final Map<String, ProcessorBase> processors = new HashMap<String, ProcessorBase>();

	static
	{
		//ПФП
		registerProcessor(new AccountProductProcessor());
		registerProcessor(new AgeCategoryProcessor());
		registerProcessor(new CardProcessor());
		registerProcessor(new ChannelProcessor());
		registerProcessor(new ComplexFundInvestmentProductProcessor());
		registerProcessor(new ComplexIMAInvestmentProductProcessor());
		registerProcessor(new ComplexInsuranceProductProcessor());
		registerProcessor(new FundProductProcessor());
		registerProcessor(new PfpIMAProductProcessor());
		registerProcessor(new InsuranceCompanyProcessor());
		registerProcessor(new InsuranceProductProcessor());
		registerProcessor(new InsuranceTypeProcessor());
		registerProcessor(new InvestmentPeriodProcessor());
		registerProcessor(new LoanKindProductProcessor());
		registerProcessor(new PensionFundProcessor());
		registerProcessor(new PensionProductProcessor());
		registerProcessor(new PeriodTypeProcessor());
		registerProcessor(new ProductTypeParametersProcessor());
		registerProcessor(new QuestionProcessor());
		registerProcessor(new RiskProcessor());
		registerProcessor(new RiskProfileProcessor());
		registerProcessor(new TargetProcessor());
		registerProcessor(new TrustManagingProductProcessor());
		registerProcessor(new UseCreditCardRecommendationProcessor());
		//Регионы
		registerProcessor(new RegionProcessor());
		//БИК
		registerProcessor(new BankProcessor());
		registerProcessor(new BannedAccountProcessor());
		//Рекламный блок
		registerProcessor(new AdvertisingBlockProcessor());
		//Поставщики
		registerProcessor(new BillingServiceProviderProcessor());
		registerProcessor(new InternetShopsServiceProviderProcessor());
		registerProcessor(new TaxationServiceProviderProcessor());
		registerProcessor(new PaymentServiceProcessor());
		registerProcessor(new BillingProcessor());
		registerProcessor(new GroupRiskProcessor());
		registerProcessor(new ConversionLimitPaymentLinkProcessor());
		registerProcessor(new InternalSocialLimitPaymentLinkProcessor());
		registerProcessor(new JuridicalExternalLimitPaymentLinkProcessor());
		registerProcessor(new PhysicalExternalAccountLimitPaymentLinkProcessor());
		registerProcessor(new PhysicalExternalCardLimitPaymentLinkProcessor());
		registerProcessor(new PhysicalInternalLimitPaymentLinkProcessor());
		registerProcessor(new ServiceProviderSmsAliasProcessor());
		registerProcessor(new BillingProviderServiceProcessor());
		registerProcessor(new ServiceProviderForReplicationWrapperProcessor());

		//Панель быстрой оплаты
		registerProcessor(new QuickPaymentPanelProcessor());

		registerProcessor(new WorkCalendarProcessor());
		registerProcessor(new DepartmentProcessor());
		registerProcessor(new ReceptionTimeProcessor());
		registerProcessor(new LimitProcessor());

		//события
		registerProcessor(new NewsProcessor());

		registerProcessor(new DepositProductProcessor());
		registerProcessor(new DepositGlobalProcessor());
		registerProcessor(new IMAProductProcessor());

		//Письма
		registerProcessor(new MailSubjectProcessor());
		registerProcessor(new ContactCenterAreaProcessor());

		//СПООБК
		registerProcessor(new DepartmentsRecordingProcessor());

		//Локалезависимые сущности
		registerProcessor(new AdvertisingBlockResourcesProcessor());
		registerProcessor(new AdvertisingButtonResourcesProcessor());

		registerProcessor(new NewsResourcesProcessor());
		registerProcessor(new RegionResourcesProcessor());
		registerProcessor(new BankResourcesProcessor());
		registerProcessor(new ServiceProviderResourcesProcessor());
		registerProcessor(new PaymentServiceResourcesProcessor());

		registerProcessor(new BasketIdentifierTypeProcessor());

		//Справочник промо-кодов
		registerProcessor(new PromoCodesDepositProcessor());

		//Маппинг ошибок
		registerProcessor(new ExceptionMappingProcessor());
		registerProcessor(new ExceptionMappingResourceProcessor());

		//Справочник шаблонов кредитных оферт
		registerProcessor(new CreditOfferTemplateProcessor());

		//Условия заявления о подключении УДБО
		registerProcessor(new UDBOClaimRulesProcessor());
	}

	/**
	 * получить процессор по типу справочника
	 * @param dictionaryType тип справочника
	 * @return процессор
	 */
	public static ProcessorBase getProcessor(String dictionaryType) throws SynchronizeException
	{
		ProcessorBase processor = processors.get(dictionaryType);
		if (processor != null)
			return processor;

		throw new SynchronizeException("Не найден обработчик обновления справочника " + dictionaryType);
	}

	/**
	 * получить процессор по типу справочника
	 * @param dictionaryType тип справочника
	 * @return процессор
	 */
	public static ProcessorBase getProcessor(Class dictionaryType)
	{
		return processors.get(getType(dictionaryType));
	}

	private static void registerProcessor(ProcessorBase processor)
	{
		processors.put(getType(processor.getEntityClass()), processor);
	}

	private static String getType(Class dictionaryType)
	{
		return dictionaryType.getName();
	}
}
