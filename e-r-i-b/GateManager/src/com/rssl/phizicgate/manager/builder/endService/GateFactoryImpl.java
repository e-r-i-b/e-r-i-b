package com.rssl.phizicgate.manager.builder.endService;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.RegistartionClientService;
import com.rssl.phizic.gate.clients.UpdatePersonService;
import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.config.RefreshGateConfig;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.gate.impl.ServiceCreator;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.loans.LoanProductsService;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.notification.NotificationService;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.gate.payments.ReceiverNameService;
import com.rssl.phizic.gate.payments.UpdatePaymentsStateService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.*;

/**
 * Фабрика конечных сервисов.
 *
 * @author bogdanov
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class GateFactoryImpl implements GateFactory
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private Map<Class<? extends Service>, Service> services;
	private RefreshGateConfig config;

	private Properties properties;
	private ServiceCreator serviceCreator;

	/**
	 * Список классов сервисов для регистрации.
	 */
	private static final List<Class<? extends Service>> servicesList = new ArrayList<Class<? extends Service>>();

	static
	{
		servicesList.add(BankrollService.class);
		servicesList.add(BackRefBankrollService.class);
		servicesList.add(CacheService.class);
		servicesList.add(ClientService.class);
		servicesList.add(RegistartionClientService.class);
		servicesList.add(CurrencyRateService.class);
		servicesList.add(CurrencyService.class);
		servicesList.add(DepositProductService.class);
		servicesList.add(DepositService.class);
		servicesList.add(DocumentService.class);
		servicesList.add(DebtService.class);
		servicesList.add(UpdatePaymentsStateService.class);
		servicesList.add(UpdatePersonService.class);
		servicesList.add(NotificationSubscribeService.class);
		servicesList.add(NotificationService.class);
		servicesList.add(GorodService.class);
		servicesList.add(PaymentRecipientGateService.class);
		servicesList.add(CalendarGateService.class);
		servicesList.add(OfficeGateService.class);
		servicesList.add(BackRefClientService.class);
		servicesList.add(AutoPaymentService.class);
		servicesList.add(BackRefReceiverInfoService.class);
		servicesList.add(LoyaltyProgramService.class);
		servicesList.add(BackRefBankInfoService.class);
		servicesList.add(ReceiverNameService.class);
		servicesList.add(GateInfoService.class);
		servicesList.add(KBKGateService.class);
		servicesList.add(CurrencyOpTypeGateService.class);
		servicesList.add(MembersGateService.class);
		servicesList.add(BanksGateService.class);
		servicesList.add(CountriesGateService.class);
		servicesList.add(CurrenciesGateService.class);
		servicesList.add(LoansService.class);
		servicesList.add(LoanProductsService.class);
		servicesList.add(WebBankServiceFacade.class);
		//сервис сбора статистики исключений
		servicesList.add(ExceptionStatisticService.class);
	}

	public GateFactoryImpl(RefreshGateConfig config) throws GateException
	{
		properties = GateProperties.getProperties();
		serviceCreator = new SimpleServiceCreator();
		this.config = config;
	}

	public GateFactoryImpl(RefreshGateConfig config, ServiceCreator serviceCreator) throws GateException
	{
		this(config);
		this.serviceCreator = serviceCreator;
	}

	public void initialize() throws GateException
	{
		services = new HashMap<Class<? extends Service>, Service>();

		for (Class<? extends Service> serviceClass : servicesList)
		{
			String className = properties.getProperty(serviceClass.getName());
			if (className != null)
			{
				registerService(serviceClass, className);
			}
		}
	}

	private void registerService(Class<? extends Service> serviceInterface, String className) throws GateException
	{
		Service service = serviceCreator.createService(className, this);

		services.put(serviceInterface, service);
	}

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

	public <C extends GateConfig> C config(Class<C> configInterface)
	{
		//noinspection unchecked
		return (C) config;
	}

	public Collection<? extends Service> services()
	{
		return services.values();
	}
}
