package com.rssl.phizicgate.manager.builder.persistentRoutable;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.cache.CacheServiceCreator;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.RegistartionClientService;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.gate.impl.ServiceCreator;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import com.rssl.phizicgate.manager.services.routablePersistent.bankroll.BankrollServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.cache.CacheServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.clients.ClientServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.clients.RegistartionClientServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.currency.CurrencyRateServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.deposit.DepositProductServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.deposit.DepositServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.documents.DebtServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.documents.DocumentServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.documents.UpdateDocumentServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.loyalty.LoyaltyProgramServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.notification.NotificationSubscribeServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.offices.OfficeGateServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.systems.gorod.GorodServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.systems.recipients.PaymentRecipientGateServiceImpl;
import com.rssl.phizicgate.manager.services.routablePersistent.util.CalendarGateServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Персистентно-роутерная фабрика гейта.
 *
 * @author bogdanov
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class GateFactoryImpl implements GateFactory
{
	/**
	 * Связь класса сервиса и объекта сервиса.
	 */
	private Map<Class<? extends Service>, Service> services;
	/**
	 * Отображение сервиса на его персистентный сервис.
	 */
	private static final Map<Class<? extends Service>, Class<? extends Service>> serviceToPersistentService =
			new HashMap<Class<? extends Service>, Class<? extends Service>>();

	/**
	 * Отображение сервиса на его делегата.
	 */
	private static final Map<Class<? extends Service>, Service> serviceToDelegate =
			new HashMap<Class<? extends Service>, Service>();

	private ServiceCreator serviceCreator;
	private Properties properties;

	static
	{
		serviceToPersistentService.put(BankrollService.class,              BankrollServiceImpl.class);
		serviceToPersistentService.put(CacheService.class,                 CacheServiceImpl.class);
		serviceToPersistentService.put(ClientService.class,                ClientServiceImpl.class);
		serviceToPersistentService.put(RegistartionClientService.class,    RegistartionClientServiceImpl.class);
		serviceToPersistentService.put(CurrencyRateService.class,          CurrencyRateServiceImpl.class);
		serviceToPersistentService.put(DepositProductService.class,        DepositProductServiceImpl.class);
		serviceToPersistentService.put(DepositService.class,               DepositServiceImpl.class);
		serviceToPersistentService.put(DocumentService.class,              DocumentServiceImpl.class);
		serviceToPersistentService.put(DebtService.class,                  DebtServiceImpl.class);
		serviceToPersistentService.put(UpdateDocumentService.class,        UpdateDocumentServiceImpl.class);
		serviceToPersistentService.put(NotificationSubscribeService.class, NotificationSubscribeServiceImpl.class);
		serviceToPersistentService.put(GorodService.class,                 GorodServiceImpl.class);
		serviceToPersistentService.put(PaymentRecipientGateService.class,  PaymentRecipientGateServiceImpl.class);
		serviceToPersistentService.put(CalendarGateService.class,          CalendarGateServiceImpl.class);
		serviceToPersistentService.put(OfficeGateService.class,            OfficeGateServiceImpl.class);
		serviceToPersistentService.put(LoyaltyProgramService.class,        LoyaltyProgramServiceImpl.class);
		serviceToPersistentService.put(GateInfoService.class,              null);
		serviceToPersistentService.put(CurrencyService.class,              null);
		serviceToPersistentService.put(ListenerMessageReceiver.class,      null);
		serviceToPersistentService.put(BackRefClientService.class,         null);
		//сервис сбора статистики исключений
		serviceToPersistentService.put(ExceptionStatisticService.class,    null);

		serviceToDelegate.put(UpdateDocumentService.class, null);
		serviceToDelegate.put(OfficeGateService.class,     null);
	}

	public GateFactoryImpl() throws GateException
	{
		serviceCreator = new CacheServiceCreator();
		properties = GateProperties.getProperties();
	}

	public GateFactoryImpl(ServiceCreator serviceCreator) throws GateException
	{
		this();
		this.serviceCreator = serviceCreator;
	}

	/**
	 * Инициализация фабрики гейта.
	 *
	 * @throws GateException
	 */
	public void initialize() throws GateException
	{
		services = new HashMap<Class<? extends Service>, Service>();

		try
		{
			for (Class<? extends Service> serviceClass: serviceToDelegate.keySet())
			{
				addDelegate(serviceClass);
			}
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		for (Class<? extends Service> serviceClass : serviceToPersistentService.keySet())
		{
			registerService(serviceClass);
		}
	}

	private void addDelegate(Class<? extends Service> serviceClass) throws GateException
	{
		if (serviceToDelegate.get(serviceClass) == null)
		{
			serviceToDelegate.put(serviceClass, serviceCreator.createService(properties.getProperty(serviceClass.getName()), this));
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

	private void registerService(Class<? extends Service> serviceClass) throws GateException
	{
		try
		{
			//если для сервиса нет персистентной составляющей,
			//то кладем в мап инстанс сервиса конечного адаптера
			if (serviceToPersistentService.get(serviceClass) == null)
			{
				Service service = serviceCreator.createService(properties.getProperty(serviceClass.getName()), this);
				services.put(serviceClass, service);
				return;
			}

			//создаем инстанс персистентной части менеджера
			Class<? extends Service> managerClass = serviceToPersistentService.get(serviceClass);
			PersistentServiceBase manager = (PersistentServiceBase) managerClass.getConstructor(GateFactory.class).newInstance(this);

			//инициализируем делегат
			if (serviceToDelegate.containsKey(serviceClass))
				//noinspection unchecked
				manager.setDelegate(serviceToDelegate.get(serviceClass));

			services.put(serviceClass, manager);
		}
		catch (Exception e)
		{
			throw new GateException("Не удалость зарегистрировать сервис " + serviceClass.getName(), e);
		}
	}
}
