package com.rssl.phizic.gate.impl;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Collection;

/**
 * @author Evgrafov
 * @ created 19.05.2006
 * @ $Author: niculichev $
 * @ $Revision: 81622 $
 */

public class GateFactoryImpl implements GateFactory
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private Map<Class<? extends Service>, Service> services;
	private Properties properties;
	private ServiceCreator serviceCreator;

	public GateFactoryImpl()
	{
		serviceCreator = new SimpleServiceCreator();
	}

	public GateFactoryImpl(ServiceCreator serviceCreator)
	{
		this();
		this.serviceCreator = serviceCreator;
	}

	/**
	 * Конфигурация фабрики служб
	 */
	@SuppressWarnings({"unchecked"})
	public void initialize() throws GateException
	{
		try
		{
			properties = loadProperties();
			services = new HashMap<Class<? extends Service>, Service>();
			registerService("com.rssl.phizic.logging.messaging.MessageLogWriter");
			registerService("com.rssl.phizic.logging.messaging.MessageLogService");
			registerService("com.rssl.phizic.gate.ermb.ErmbProfileService");
			registerService("com.rssl.phizic.gate.currency.CurrencyService");
			registerService("com.rssl.phizic.gate.currency.CurrencyRateService");
			registerService("com.rssl.phizic.gate.clients.ClientService");
			registerService("com.rssl.phizic.gate.bankroll.BankrollService");
			registerService("com.rssl.phizic.gate.bankroll.BackRefBankrollService");
			registerService("com.rssl.phizic.gate.deposit.DepositService");
			registerService("com.rssl.phizic.gate.messaging.WebBankServiceFacade");
			registerService("com.rssl.phizic.gate.notification.NotificationService");
			registerService("com.rssl.phizic.gate.payments.UpdatePaymentsStateService");
			registerService("com.rssl.phizic.gate.notification.NotificationSubscribeService");
			registerService("com.rssl.phizic.gate.deposit.DepositProductService");
			registerService("com.rssl.phizic.gate.documents.DocumentService");
			registerService("com.rssl.phizic.gate.documents.DebtService");
			registerService("com.rssl.phizic.gate.loans.LoanProductsService");
			registerService("com.rssl.phizic.gate.loans.LoansService");
			registerService("com.rssl.phizic.gate.documents.UpdateDocumentService");
			registerService("com.rssl.phizic.gate.dictionaries.officies.OfficeGateService");
			registerService("com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService");
			registerService("com.rssl.phizic.gate.clients.stoplist.StopListService");
			registerService("com.rssl.phizic.gate.listener.ListenerMessageReceiver");
			registerService("com.rssl.phizic.gate.documents.UpdateDocumentService");
			registerService("com.rssl.phizic.gate.clients.RegistartionClientService");
			registerService("com.rssl.phizic.gate.clients.UpdatePersonService");
			registerService("com.rssl.phizic.gate.payments.systems.gorod.GorodService");
			registerService("com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService");
			registerService("com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService");
			registerService("com.rssl.phizic.gate.dictionaries.BackRefBankInfoService");
			registerService("com.rssl.phizic.gate.utils.CalendarGateService");
			registerService("com.rssl.phizic.gate.dictionaries.CurrenciesGateService");
			registerService("com.rssl.phizic.gate.dictionaries.CountriesGateService");
			registerService("com.rssl.phizic.gate.dictionaries.BanksGateService");
			registerService("com.rssl.phizic.gate.dictionaries.MembersGateService");
			registerService("com.rssl.phizic.gate.dictionaries.CurrencyOpTypeGateService");
			registerService("com.rssl.phizic.gate.dictionaries.KBKGateService");
			registerService("com.rssl.phizic.gate.GateInfoService");
			registerService("com.rssl.phizic.gate.cache.CacheService");
			registerService("com.rssl.phizic.gate.cache.RefreshCacheService");
			registerService("com.rssl.phizic.gate.clients.BackRefClientService");
			registerService("com.rssl.phizic.gate.bankroll.BankrollNotificationService");

			registerService("com.rssl.phizic.gate.mobilebank.MobileBankService");
			registerService("com.rssl.phizic.gate.mobilebank.DepoMobileBankService");
        	registerService("com.rssl.phizic.gate.depo.DepoAccountService");
			registerService("com.rssl.phizic.gate.ima.IMAccountService");
			registerService("com.rssl.phizic.gate.ima.BackRefIMAccountService");
			registerService("com.rssl.phizic.gate.mbuesi.UESIMessagesService");
			registerService("com.rssl.phizic.gate.clients.ClientProductsService");
			registerService("com.rssl.phizic.gate.longoffer.LongOfferService");
			registerService("com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService");
			registerService("com.rssl.phizic.gate.insurance.InsuranceService");
			registerService("com.rssl.phizic.gate.security.SecurityAccountService");
			registerService("com.rssl.phizic.gate.utils.StoredResourcesService");

			registerService("com.rssl.phizic.gate.ips.IPSExecutiveService");
			registerService("com.rssl.phizic.gate.ips.IPSReceiverService");
			registerService("com.rssl.phizic.gate.ips.CardOperationInfoService");

			registerService("com.rssl.phizic.gate.currency.CurrencyRateUpdateService");

			registerService("com.rssl.phizic.gate.mobilebank.GatePaymentTemplateService");
			registerService("com.rssl.phizic.gate.depo.DepoAccountOwnerFormGateService");
			registerService("com.rssl.phizic.gate.depo.BackRefSecurityService");
			registerService("com.rssl.phizic.gate.payments.ReceiverNameService");
			registerService("com.rssl.phizic.gate.pfr.PFRService");

			registerService("com.rssl.phizic.gate.log.LogService");
			registerService("com.rssl.phizic.gate.utils.ExternalSystemGateService");
			registerService("com.rssl.phizic.gate.autopayments.AutoSubscriptionService");

			registerService("com.rssl.phizic.gate.commission.BackRefCommissionTariffService");

			registerService("com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService");
			registerService("com.rssl.phizic.gate.basket.InvoiceService");

			registerService("com.rssl.phizic.gate.loyalty.LoyaltyProgramService");

			registerService("com.rssl.phizic.gate.monitoring.UpdateMonitoringGateConfigService");
			registerService("com.rssl.phizic.gate.monitoring.RunGateMonitoringService");
			registerService("com.rssl.phizic.gate.monitoring.BackRefMonitoringGateConfigService");
			registerService("com.rssl.phizic.gate.monitoring.IncrementMonitoringCounterService");

			registerService("com.rssl.phizic.gate.payments.template.TemplateGateService");

			registerService("com.rssl.phizic.gate.confirmation.ConfirmationInfoService");
			registerService("com.rssl.phizic.gate.clients.AdditionalClientInfoService");

			//сервис сбора статистики исключений
			registerService("com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService");

			registerService("com.rssl.phizic.gate.schemes.AccessSchemeService");
			registerService("com.rssl.phizic.gate.employee.EmployeeService");

			//сервисы Einvoicing
			registerService("com.rssl.phizic.gate.einvoicing.ShopOrderService");
			registerService("com.rssl.phizic.gate.einvoicing.InvoiceGateBackService");

			//сервис MFM
			registerService("com.rssl.phizic.gate.mfm.MFMService");

			//сервисы для сбора средств
			registerService("com.rssl.phizic.gate.fund.FundMultiNodeService");

			//БКИ
			registerService("com.rssl.phizic.gate.bki.CreditBureauService");

			// сообщения обратного потока для корзины
			registerService("com.rssl.phizic.gate.basket.BasketRouteService");
		}
		catch (RuntimeException ex)
		{
			log.error("Ошибка при инициализации гейта", ex);
			throw ex;
		}
		catch (GateException ex)
		{
			log.error("Ошибка при инициализации гейта", ex);
			throw ex;
		}
	}

	private void registerService(String serviceInterfaceName) throws GateException
	{
		String value = properties.getProperty(serviceInterfaceName);

		if (value != null)
		{
			Class<? extends Service> serviceInterface = null;
			try
			{
				serviceInterface = ClassHelper.loadClass(serviceInterfaceName);
			}
			catch (ClassNotFoundException e)
			{
				throw new GateException("Не найден класс для сервиса " + serviceInterfaceName, e);
			}

			Service service = serviceCreator.createService(value, this);
			services.put(serviceInterface, service);
		}
	}

	/**
	 * @param serviceInterface Интерфейс службы
	 * @return служба
	 */
	public <S extends Service> S service(Class<S> serviceInterface)
	{
		Object service = services.get(serviceInterface);
		if (service == null)
		{
			throw new RuntimeException("Служба c интерфейсом " + serviceInterface.getName() + " не найдена");
		}
		//noinspection unchecked
		return (S) service;
	}

	//TODO реализовать
	public <C extends GateConfig> C config(Class<C> configInterface)
	{
		throw new UnsupportedOperationException("Получение конфига для ИСППН не поддерживается!");
	}

	public Collection<? extends Service> services()
	{
		throw new UnsupportedOperationException();
	}

	private Properties loadProperties() throws GateException
	{
		return GateProperties.getProperties();
	}
}
