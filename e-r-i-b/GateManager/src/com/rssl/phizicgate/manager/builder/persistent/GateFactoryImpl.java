package com.rssl.phizicgate.manager.builder.persistent;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.RegistartionClientService;
import com.rssl.phizic.gate.clients.UpdatePersonService;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.dictionaries.*;
import com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.gate.impl.ServiceCreator;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.gate.loans.LoanProductsService;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.monitoring.BackRefMonitoringGateConfigService;
import com.rssl.phizic.gate.monitoring.IncrementMonitoringCounterService;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringGateConfigService;
import com.rssl.phizic.gate.notification.NotificationService;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.gate.payments.ReceiverNameService;
import com.rssl.phizic.gate.payments.UpdatePaymentsStateService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import com.rssl.phizicgate.manager.services.persistent.bankroll.BankrollServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.cache.CacheServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.clients.*;
import com.rssl.phizicgate.manager.services.persistent.currency.CurrencyRateServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.deposit.DepositProductServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.deposit.DepositServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.documents.AutoPaymentServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.documents.DocumentServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.documents.PersistentDebtService;
import com.rssl.phizicgate.manager.services.persistent.documents.UpdateDocumentServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.loyalty.LoyaltyProgramServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.mobilebank.MobileBankServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.monitoring.IncrementMonitoringCounterServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.notification.NotificationSubscribeServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.offices.BackRefOfficeGateServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.offices.OfficeGateServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.systems.gorod.GorodServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.systems.recipients.BackRefReceiverInfoServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.systems.recipients.PaymentRecipientGateServiceImpl;
import com.rssl.phizicgate.manager.services.persistent.util.CalendarGateServiceImpl;

import java.util.*;

/**
 * @author hudyakov
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class GateFactoryImpl implements GateFactory
{
	private Map<Class<? extends Service>, Service> services;
	private static final Map<Class<? extends Service>, Class<? extends Service>> map =
			new HashMap<Class<? extends Service>, Class<? extends Service>>();

	private Properties properties;
	private SimpleGateFactory simpleGateFactory;
	//список обратных сервисов
	private static final Set<Class<? extends Service>> backRefs = new HashSet<Class<? extends Service>>();

	private ServiceCreator serviceCreator;

	static
	{
		map.put(BankrollService.class,              BankrollServiceImpl.class);
		map.put(BackRefBankrollService.class,       BackRefBankrollServiceImpl.class);
		map.put(CacheService.class,                 CacheServiceImpl.class);
		map.put(ClientService.class,                ClientServiceImpl.class);
		map.put(RegistartionClientService.class,    RegistartionClientServiceImpl.class);
		map.put(CurrencyRateService.class,          CurrencyRateServiceImpl.class);
		map.put(CurrencyService.class,              null);
		map.put(DepositProductService.class,        DepositProductServiceImpl.class);
		map.put(DepositService.class,               DepositServiceImpl.class);
		map.put(DocumentService.class,              DocumentServiceImpl.class);
		map.put(DebtService.class,                  PersistentDebtService.class);
		map.put(UpdateDocumentService.class,        UpdateDocumentServiceImpl.class);
		map.put(BackRefOfficeGateService.class,     BackRefOfficeGateServiceImpl.class);
		map.put(UpdatePaymentsStateService.class,   null);
		map.put(UpdatePersonService.class,          UpdatePersonServiceImpl.class);
		map.put(NotificationSubscribeService.class, NotificationSubscribeServiceImpl.class);
		map.put(NotificationService.class,          null);
		map.put(GorodService.class,                 GorodServiceImpl.class);
		map.put(PaymentRecipientGateService.class,  PaymentRecipientGateServiceImpl.class);
		map.put(CalendarGateService.class,          CalendarGateServiceImpl.class);
		map.put(OfficeGateService.class,            OfficeGateServiceImpl.class);
		map.put(BackRefClientService.class,         BackRefClientServiceImpl.class);
		map.put(AutoPaymentService.class,           AutoPaymentServiceImpl.class);
		map.put(BackRefReceiverInfoService.class,   BackRefReceiverInfoServiceImpl.class);
		map.put(LoyaltyProgramService.class,        LoyaltyProgramServiceImpl.class);
		map.put(MobileBankService.class,            MobileBankServiceImpl.class);
		map.put(IncrementMonitoringCounterService.class,  IncrementMonitoringCounterServiceImpl.class);
		map.put(UpdateMonitoringGateConfigService.class,  null);
		map.put(BackRefMonitoringGateConfigService.class, null);
		map.put(BackRefBankInfoService.class,       null);
		map.put(ReceiverNameService.class,          null);

		//сервис сбора статистики исключений
		map.put(ExceptionStatisticService.class,    null);

		map.put(GateInfoService.class,              null);
		map.put(KBKGateService.class,               null);
		map.put(CurrencyOpTypeGateService.class,    null);
		map.put(MembersGateService.class,           null);
		map.put(BanksGateService.class,             null);
		map.put(CountriesGateService.class,         null);
		map.put(CurrenciesGateService.class,        null);
		map.put(LoansService.class,                 null);
		map.put(ListenerMessageReceiver.class,      null);
		map.put(LoanProductsService.class,          null);
		map.put(WebBankServiceFacade.class,         null);

		backRefs.add(UpdateDocumentService.class);
		backRefs.add(UpdatePersonService.class);
		backRefs.add(BackRefBankrollService.class);
		backRefs.add(BackRefClientService.class);
		backRefs.add(BackRefReceiverInfoService.class);
		backRefs.add(BackRefBankInfoService.class);
		backRefs.add(BackRefOfficeGateService.class);
	}

	public GateFactoryImpl() throws GateException
	{
		properties = GateProperties.getProperties();
		serviceCreator = new SimpleServiceCreator();
	}

	public GateFactoryImpl(ServiceCreator serviceCreator) throws GateException
	{
		this();
		this.serviceCreator = serviceCreator;
	}

	public void initialize() throws GateException
	{
		services = new HashMap<Class<? extends Service>, Service>();
		simpleGateFactory = new SimpleGateFactory();
		simpleGateFactory.initialize();

		for (Map.Entry<Class<? extends Service>, Class<? extends Service>> entry : map.entrySet())
		{
			String className = properties.getProperty(entry.getKey().getName());
			if (className != null)
			{
				registerService(entry, className);
			}
		}
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
		throw new UnsupportedOperationException();
	}

	public Collection<? extends Service> services()
	{
		throw new UnsupportedOperationException();
	}

	private void registerService(Map.Entry<Class<? extends Service>, Class<? extends Service>> entry, String className) throws GateException
	{
		try
		{
			//создаем инстанс сервиса конечного адаптера
			Service service = serviceCreator.createService(className, simpleGateFactory);
			
			simpleGateFactory.registerService(entry.getKey(), service);

			//если для сервиса нет персистентной составляющей,
			//то кладем в мап инстанс сервиса конечного адаптера
			if (entry.getValue() == null)
			{
				services.put(entry.getKey(), service);
				return;
			}

			//создаем инстанс персистентной части менеджера
			Class<? extends Service> managerClass = entry.getValue();
			PersistentServiceBase manager = (PersistentServiceBase) managerClass.getConstructor(GateFactory.class).newInstance(this);

			//инициализируем делегат
			manager.setDelegate(service);

			services.put(entry.getKey(), manager);
			if (backRefs.contains(entry.getKey()))
			{
				//Обратные сервисы для конечных сервисов должны дергаться через персисентную часть
				simpleGateFactory.registerService(entry.getKey(), manager);
			}
		}
		catch (Exception e)
		{
			throw new GateException("Не удалость зарегистрировать сервис " + entry.getKey().getName(), e);
		}
	}
}
