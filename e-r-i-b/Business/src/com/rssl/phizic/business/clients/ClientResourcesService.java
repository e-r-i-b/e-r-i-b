package com.rssl.phizic.business.clients;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.IMAccountServiceHelper;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.depo.DepoAccountServiceHelper;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.ermb.profile.ErmbResourseSetListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseSetEvent;
import com.rssl.phizic.business.externalsystem.AutoStopSystemService;
import com.rssl.phizic.business.externalsystem.AutoStopSystemTypeWrapper;
import com.rssl.phizic.business.externalsystem.OfflineExternalSystemEvent;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.loans.LoanServiceHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.insurance.InsuranceServiceHelper;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountServiceHelper;
import com.rssl.phizic.business.resources.external.security.StoredSecurityAccount;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.CompositeInactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.utils.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.types.ProductPermissionImpl;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Dorzhinov
 * @ created 03.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ClientResourcesService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static ExternalResourceService resourceService = new ExternalResourceService();
	private static final SecurityService securityService = new SecurityService();
	private static final AutoStopSystemService autoStopSystemService = new AutoStopSystemService();
	private static final AdapterService adapterService = new AdapterService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final AccountTargetService targetService = new AccountTargetService();
	private static final int ACCOUNT_NAME_MAX_LENGTH = 56;
	private static final ClosedAccountFilter closedAccountFilter = new ClosedAccountFilter();
	private static final ExtendedBlockedCardFilter extBlockedCardFilter = new ExtendedBlockedCardFilter();
	private static final ActiveLoanFilter activeLoanFilter = new ActiveLoanFilter();

	/**
	 * Обновление ресурсов клиента
	 * @param person персона
	 * @param onlyWayCards получать только карты из Way
	 * @param clazz классы банковских продуктов, которые нужно обновить
	 */
	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
	public void updateResources(ActivePerson person, boolean onlyWayCards, Class... clazz) throws BusinessException
	{
		ClientProductsService productsService = GateSingleton.getFactory().service(ClientProductsService.class);

		try
		{
			//Получаeм клиента. данные берем по нему из БД физиков. т.к.
			//1) полученение из ВС приводит к падениям в случае недоступности/неработоспособности внешней системы
			//2) лишние запросы в ВС (в случае ритейла Северо-Восточного банка это весьма накладно)
			//3) в ВС данные клиента могут лежать в любом виде, например с дефисами в серии документа.
			//см BUG023608
		    Client client = person.asClient();
			if (onlyWayCards)
			{
				filterPassportWay(client);
			}
			ExtendedDepartment personDepartment = (ExtendedDepartment) client.getOffice();

			// Получаем список продуктов клиента. если подразделение не поддерживает шину или клиент не УДБО запрашиваем из шины только карты
			/*
			  *  Получаем набор списка продуктов.
			  *  Если шина поддерживается дополнительно производится проверка на то, какой это клиент и в соответствии с этим
			  *  загружается список продуктов.
			 */
			Class[] classArray = clazz;
			if (ArrayUtils.isEmpty(clazz))
			{
				Pair<List<Class>, Map<Class,String>> pair = ClientResourceHelper.getClientsProducts(person, true);
				if (CollectionUtils.isEmpty(pair.getFirst()))
				{
					//если ошибок нет, то ничего не делаем
					if (CollectionUtils.isEmpty(pair.getSecond().keySet()))
						return;

					List<String> errors = new ArrayList<String>();
					for (Class productWithException: pair.getSecond().keySet())
					{
						errors.add(pair.getSecond().get(productWithException));
					}

					PersonHelper.setPersonDataNeedUpdate();
					throw new CompositeInactiveExternalSystemException(errors);
				}

				classArray = pair.getFirst().toArray(pair.getFirst().toArray(new Class[pair.getFirst().size()]));
			}

			// Заполняем линки , которые есть у нас в базе
			// Для тех типов продуктов, которые необходимо обновить у клиента
			Map<Class<? extends ExternalResourceLink>,List<? extends ExternalResourceLink>> links = initLinks(person, classArray);

			ErmbResourseSetEvent resourseEvent = new ErmbResourseSetEvent(person.getId());
			if (resourseEvent.getProfile() != null)
				resourseEvent.setOldData(links);

			ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
			if (ArrayUtils.isNotEmpty(classArray) && classArray.length == 1 && ArrayUtils.contains(classArray, Card.class) && CollectionUtils.isNotEmpty(client.getDocuments()) &&
					client.getDocuments().size() > 1 && !ExternalSystemHelper.isActive(externalSystemGateService.findByProduct(client.getOffice(), BankProductType.Deposit)))
			{
				// Оставляем только паспорт Way, если запрос только по картам и недоступен ЦОД
				filterPassportWay(client);
			}

			GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products = productsService.getClientProducts(client, classArray);

			processBadLoadedProducts(client, products, links);

			if (products == null || products.getKeys().isEmpty())
			{
				log.info("Не было найдено ресурсов у клиента "+ client.getId());
				return;
			}

			processGoodLoadedProducts(person, products, links, personDepartment.isEsbSupported(), onlyWayCards);

			// Удаляем оставшиеся линки (на которые не пришли продукты из внешней системы)
			removeOthersLinks(person, personDepartment.isEsbSupported(), links);

			if (resourseEvent.getProfile() != null)
			{
				Map<Class<? extends ExternalResourceLink>,List<? extends ExternalResourceLink>> updatedLinks = initLinks(person, classArray);
				resourseEvent.setNewData(updatedLinks);
				ErmbResourseSetListener listener  = ErmbUpdateListener.getListener();
				listener.onResoursesReload(resourseEvent);
		    }
		}
		catch (InactiveExternalSystemException e)
		{
			PersonHelper.setPersonDataNeedUpdate();
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	private void filterPassportWay(Client client)
	{
		List<? extends ClientDocument> clientDocuments = client.getDocuments();
		if (CollectionUtils.isNotEmpty(clientDocuments))
		{
			Iterator<? extends ClientDocument> iter = clientDocuments.iterator();
			while (iter.hasNext())
			{
				if (iter.next().getDocumentType() != ClientDocumentType.PASSPORT_WAY)
					iter.remove();
			}
		}
	}

	/**
	 * Обрабатывает продукты, которые пришли с проблемами
	 * @param client
	 * @param products
	 * @param links
	 */
	private void processBadLoadedProducts(Client client, GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products, Map<Class<? extends ExternalResourceLink>, List<? extends ExternalResourceLink>> links) throws BusinessException
	{
		if (products == null)
			return;

		try
		{
			if (products.isOneError())
			{
				IKFLException exception = products.getOneErrorException();
				if (exception instanceof OfflineESBException)
				{
					Adapter esbAdapter = adapterService.getAdapterByUUID(ExternalSystemHelper.getESBSystemCode());
					if (ExternalSystemHelper.isActive(esbAdapter))
					{
						OfflineExternalSystemEvent offlineEvent = new OfflineExternalSystemEvent();
						offlineEvent.setAdapter(esbAdapter);
						offlineEvent.setAutoStopSystemType(AutoStopSystemType.ESB);
						offlineEvent.setEventTime(Calendar.getInstance());
						autoStopSystemService.add(offlineEvent);
					}
				}
				log.error("Ошибка при обновлении информации o ресурсах клиента", exception);
				return;
			}
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessException("Ошибка при обновлении информации o ресурсах клиента", e);
		}

		for (Class productType : products.getKeys())
		{
			IKFLException ex = products.getException(productType);
			if (ex != null)
			{
				if (ex instanceof OfflineExternalSystemException)
				{
					List<? extends ExternalSystem> externalSystems = ((OfflineExternalSystemException) ex).getExternalSystems();
					addOfflineExternalSystemInfo(productType, externalSystems);
				}
				log.info("Информация по продукту " + productType.getName() + " не получена. Существующие " +
						"линки удалены не будут, потому что вернулась ошибка по одному или нескольким " +
						"документам клиента. ID клиента = " + client.getId(), ex);
				// Удаляем линки, соответствующие productType, из списка, чтобы не удалить из системы совсем.
				clearLinkList(productType, links);
			}
		}
	}

	private void addOfflineExternalSystemInfo(Class productType, List<? extends ExternalSystem> externalSystems) throws BusinessException
	{
		try
		{
			if (ExternalSystemHelper.isActive(externalSystems))
			{
				List<OfflineExternalSystemEvent> eventList = new ArrayList<OfflineExternalSystemEvent>();
				for (ExternalSystem adapter : externalSystems)
				{
					OfflineExternalSystemEvent offlineEvent = new OfflineExternalSystemEvent();
					offlineEvent.setAdapter((Adapter) adapter);
					offlineEvent.setAutoStopSystemType(AutoStopSystemTypeWrapper.getSystemTypeByClass(productType));
					offlineEvent.setEventTime(Calendar.getInstance());
					eventList.add(offlineEvent);
				}
				autoStopSystemService.addOrUpdateList(eventList);
			}
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessException("Ошибка при обновлении информации o ресурсах клиента", e);
		}
	}

	/**
	 * Обрабатывает продукты, которые пришли без проблем
	 * @param person
	 * @param products
	 * @param links
	 * @param ESBSupported
	 */
	private void processGoodLoadedProducts(ActivePerson person, GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products, Map<Class<? extends ExternalResourceLink>, List<? extends ExternalResourceLink>> links, boolean ESBSupported, boolean onlyWayCards)
	{
		// получает номер карты, по которой вошел клиент
		String lastLoginCardNumber = person.getLogin().getLastLogonCardNumber();

		Map<Object, String> smsAutoAliases = computeSmsAutoAliases(GroupResultHelper.joinValues(products));
		Set<String> clientArrestedCards = new HashSet<String>();

		ErmbProductSettings ermbSettings;
		if (ErmbHelper.isErmbMigration())
		{
			ermbSettings = ErmbProductSettings.ON;
		}
		else
		{
			try
			{
				ermbSettings = ErmbProductSettings.get(person.getId());
			}
			catch (BusinessException e)
			{
				log.error(e);
				ermbSettings = ErmbProductSettings.OFF;
			}
		}

		boolean accessArrested = PermissionUtil.impliesServiceRigid("ArrestedCardsInfo");
		for (Class productType : products.getKeys())
		{
			List<Pair<Object, AdditionalProductData>> pairs = products.getResult(productType);
			if (pairs == null)
				continue;

			for (Pair<Object, AdditionalProductData> pair : pairs)
			{
				if (pair == null)
					continue;

				try
				{
					if (pair.getFirst() != null &&  pair.getFirst() instanceof Card)
					{
						// ищем карту, по которой зашел клиент
						// и определяем по ней последние тб, осб, всп, по которорым зашел клиент
						Card card = (Card) pair.getFirst();
						if (card.getNumber().equals(lastLoginCardNumber))
							saveLastLogonCardDepartmentsInfo(person.getLogin(), card);
						// арестованнные карты сохраняем в контексте персоны
						// для установки статуса в запросе детальной информации
						if (accessArrested && AccountState.ARRESTED == card.getCardAccountState() && !clientArrestedCards.contains(card.getNumber()))
							clientArrestedCards.add(card.getNumber());
					}
					addToLinks(person, links, pair, smsAutoAliases, ESBSupported, onlyWayCards, ermbSettings, accessArrested);
				}
				catch (BusinessException e)
				{
					log.error("Проблема при добавлении/обновлении ссылки на продукт в системе.", e);
				}
			}
		}
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if(!clientArrestedCards.isEmpty())
				personData.setClientArrestedCards(clientArrestedCards);
		}
	}

	/**
	 * Строит автоматические СМС-алиасы для указанных продуктов
	 * @param pairs
	 * @return мапа "продукт -> алиас"
	 */
	private Map<Object, String> computeSmsAutoAliases(Collection<Pair<Object, AdditionalProductData>> pairs)
	{
		Collection<Object> products = new ArrayList<Object>(pairs.size());
		for (Pair<Object, AdditionalProductData> pair : pairs)
			products.add(pair.getFirst());
		try
		{
			return SmsAliasCalculator.PRODUCT.computeSmsAutoAliases(products);
		}
		catch (Exception e)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("Невозможно построить смс-алиасы для продуктов, т.к. существуют продукты, последние 7 цифр номеров которых совпадают: ");
			for (Object product : products)
			{
				if (product instanceof Account)
					builder.append("счет ").append(((Account)product).getNumber()).append("; ");

				if (product instanceof Card)
					builder.append("карта ").append(((Card)product).getNumber()).append("; ");

				if (product instanceof Loan)
					builder.append("кредит ").append(((Loan)product).getAccountNumber()).append("; ");
			}
			log.error(builder.toString(), e);
			return Collections.emptyMap();
		}
	}

	private void addToLinks(ActivePerson person, Map<Class<? extends ExternalResourceLink>, List<? extends ExternalResourceLink>> links, Pair<Object, AdditionalProductData> pair, Map<Object, String> smsAutoAliases, boolean isESBSupported, boolean onlyWayCards, ErmbProductSettings ermbSettings, boolean accessArrested) throws BusinessException
	{
		Object object = pair.getFirst();
		if (object instanceof Account)
		{
			addAccountLinks(person, (Account)object, pair.getSecond(),
					smsAutoAliases, isESBSupported, (List<AccountLink>)links.get(AccountLink.class), ermbSettings);
			return;
		}
		if (object instanceof Card)
		{
			addCardLinks(person, (Card)object,  pair.getSecond(),
					smsAutoAliases, (List<CardLink>)links.get(CardLink.class), onlyWayCards, ermbSettings, accessArrested);
			return;
		}
		if (object instanceof IMAccount)
		{
			addIMAccountLink(person, (IMAccount)object,(List<IMAccountLink>)links.get(IMAccountLink.class) );
			return;
		}
		if (object instanceof Loan)
		{
			addLoanLink(person, (Loan)object,
					smsAutoAliases, (List<LoanLink>)links.get(LoanLink.class), ermbSettings);
			return;
		}
		if (object instanceof DepoAccount)
		{
			addDepoLink(person, (DepoAccount)object, (List<DepoAccountLink>)links.get(DepoAccountLink.class));
			return;
		}

		throw new BusinessException("Ошибка при обновлении ресурсов клиента: неизвестный тип ресурса" + object.getClass().getName());
	}

	private void addDepoLink(ActivePerson person, DepoAccount depoAccount, List<DepoAccountLink> depoAccountLinks) throws BusinessException
	{
		DepoAccountLink depoAccountLink = DepoAccountServiceHelper.findDepoAccountLinkByNumber(depoAccountLinks, depoAccount.getAccountNumber());
		if (depoAccountLink==null)
		{
			depoAccountLink = resourceService.addDepoAccountLink(person.getLogin(), depoAccount);
		}
		else
		{
			if (!depoAccountLink.getExternalId().equals(depoAccount.getId()))
			{
				depoAccountLink.setExternalId(depoAccount.getId());
				resourceService.updateLink(depoAccountLink);
			}
			depoAccountLinks.remove(depoAccountLink);
		}

		addOrUpdateStoredResource(depoAccount, depoAccountLink);
		return;
	}

	private void addLoanLink(ActivePerson person, Loan loan, Map<Object, String> smsAutoAliases, List<LoanLink> loanLinks, ErmbProductSettings ermbSettings) throws BusinessException
	{
		String smsAutoAlias = smsAutoAliases.get(loan);
		LoanLink loanLink = LoanServiceHelper.findLoanLinkByNumber(loanLinks, loan.getAccountNumber());
		if (loanLink==null)
		{
			loanLink = resourceService.addLoanLink(person.getLogin(), smsAutoAlias, ermbSettings, loan);
		}
		else
		{
			boolean update = !loanLink.getExternalId().equals(loan.getId());
			update=update || !StringUtils.equals(loanLink.getAutoSmsAlias(), smsAutoAlias);

			boolean newShowInSms = loanLink.getShowInSystem() && loanLink.getShowInSms() && ermbSettings.isShowInSms();
			if (loanLink.getShowInSms() != newShowInSms && ErmbHelper.isErmbMigration())
			{
				loanLink.setShowInSms(newShowInSms);
				update = true;
			}

			boolean newErmbNotification = loanLink.getShowInSystem() && loanLink.getShowInSms() && ermbSettings.isNotification(loanLink.getResourceType());
			if (loanLink.getErmbNotification() != newErmbNotification && ErmbHelper.isErmbMigration())
			{
				loanLink.setErmbNotification(newErmbNotification);
				update = true;
			}

			if (activeLoanFilter.accept(loan) && loanLink.getClosedState() != null && !loanLink.getClosedState())
			{
				loanLink.setClosedState(null);
				update = true;
			}
			else if (!activeLoanFilter.accept(loan) && loanLink.getClosedState() == null)
			{
				loanLink.setClosedState(true);
				update = true;
			}

			if (update)
			{
				loanLink.setExternalId(loan.getId());
				loanLink.setAutoSmsAlias(smsAutoAlias);
				resourceService.updateLink(loanLink);
			}
			loanLinks.remove(loanLink);
		}
		
		addOrUpdateStoredResource(loan, loanLink);
		return;
	}

	private void addIMAccountLink(ActivePerson person, IMAccount imAccount, List<IMAccountLink> imAccountLinks) throws BusinessException
	{
		IMAccountLink iMAccountLink = IMAccountServiceHelper.getIMAccountLinkByNumber(imAccount.getNumber(), imAccountLinks);
		boolean accessArrested = PermissionUtil.impliesServiceRigid("ArrestedIMAccountsInfo");
		boolean arrestedIMAccount = imAccount.getState().equals(IMAccountState.arrested);
		if (iMAccountLink==null)
		{
			if (arrestedIMAccount && !accessArrested)
				return;

			ProductPermissionImpl permission = new ProductPermissionImpl();
			if (arrestedIMAccount)
			{
				permission.setShowInMobile(false);
				permission.setShowInSocial(false);
				permission.setShowInATM(false);
			}
			iMAccountLink = resourceService.addIMAccountLink(person.getLogin(), imAccount, permission);
		}
		else
		{
			boolean needUpdate = false;

			if (!MockHelper.isMockObject(imAccount))
			{
				Currency imaCurrency = imAccount.getCurrency();

				if (!imaCurrency.compare(iMAccountLink.getCurrency()) ||
					!iMAccountLink.getExternalId().equals(imAccount.getId()))
				{
					iMAccountLink.setExternalId(imAccount.getId());
					iMAccountLink.setCurrency(imaCurrency);
					needUpdate = true;
				}
			}

			if (arrestedIMAccount)
			{
				boolean changeShowInMobile = iMAccountLink.getShowInMobile() != null ? iMAccountLink.getShowInMobile() : true;
				boolean changeShowInATM = iMAccountLink.getShowInATM();

				//если нет прав на арестованные металлические счета, удаляем из линков
				if (!accessArrested)
					resourceService.removeLink(iMAccountLink);
				else if (changeShowInMobile || changeShowInATM)
				{
					iMAccountLink.setShowInMobile(false);
					iMAccountLink.setShowInATM(false);
					needUpdate = true;
				}
			}

			if (needUpdate)
				resourceService.updateLink(iMAccountLink);
			imAccountLinks.remove(iMAccountLink);
		}

		addOrUpdateStoredResource(imAccount, iMAccountLink);
		return;
	}

	private void addCardLinks(ActivePerson person, Card card, AdditionalProductData additionalData,
	                          Map<Object, String> smsAutoAliases, List<CardLink> cardLinks, boolean onlyWayCards, ErmbProductSettings ermbSettings, boolean accessArrested) throws BusinessException
	{
		CardLink cardLink = BankrollServiceHelper.findCardLinkByNumber(card.getNumber(), cardLinks);
		//Группа ограничений для одноразовых паролей (OTP)
		OTPRestriction restriction = (OTPRestriction) additionalData;
		String smsAutoAlias = smsAutoAliases.get(card);

		//если нет доступа то просто не обрабатываем признак арестованных карт. Все карты считаются не арестованными
		boolean arrestedCard = accessArrested && AccountState.ARRESTED == card.getCardAccountState();

		if (cardLink==null)
		{
			ProductPermissionImpl permission = new ProductPermissionImpl();
			if (arrestedCard)
			{
				permission.setShowInMobile(false);
				permission.setShowInSocial(false);
				permission.setShowInATM(false);
			}
			cardLink = resourceService.addCardLink(person.getLogin(), card, smsAutoAlias, ermbSettings, restriction, permission);
		}
		else
		{
			Office clientOffice = departmentService.findById(person.getDepartmentId());
			boolean isCODAvailable;
			ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
			try
			{
				isCODAvailable = ExternalSystemHelper.isActive(externalSystemGateService.findByProduct(clientOffice, BankProductType.Deposit));
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			boolean resetCardAccount = !(Application.atm.equals(LogThreadContext.getApplication()) && onlyWayCards && cardLink.getCardPrimaryAccount() != null);
			boolean needUpdate = false;
			if (isCODAvailable && !cardLink.getExternalId().equals(card.getId()) && resetCardAccount)
			{
				cardLink.setExternalId(card.getId());
				needUpdate = true;
			}
			if (isCODAvailable && (cardLink.getExpireDate() == null || (card.getExpireDate() != null &&
					cardLink.getExpireDate().compareTo(card.getExpireDate()) != 0)) && resetCardAccount)
			{
				cardLink.setExpireDate(card.getExpireDate());
				needUpdate = true;
			}
			
			String accountNumber = card.getPrimaryAccountNumber();

			if (cardLink.getDescription() == null || !cardLink.getDescription().equals(card.getDescription()))
			{
				cardLink.setDescription(card.getDescription());
				needUpdate = true;
			}
			if (cardLink.getCurrency() == null || !cardLink.getCurrency().compare(card.getCurrency()))
			{
				cardLink.setCurrency(card.getCurrency());
				needUpdate = true;
			}
			if (isCODAvailable && (cardLink.getCardPrimaryAccount() == null ||
					!cardLink.getCardPrimaryAccount().equals(accountNumber)) && resetCardAccount)
			{
				cardLink.setCardPrimaryAccount(accountNumber);
				needUpdate = true;
			}
			if (cardLink.isMain() != card.isMain())
			{
				cardLink.setMain(card.isMain());
				cardLink.setMainCardNumber(card.getMainCardNumber());
				needUpdate = true;
			}
			else if (!StringHelper.equals(cardLink.getMainCardNumber(), card.getMainCardNumber()))
			{
				cardLink.setMainCardNumber(card.getMainCardNumber());
				needUpdate = true;
			}
			if (restriction != null)
			{
				if(cardLink.getOTPGet() != restriction.isOTPGet())
				{
					cardLink.setOTPGet(restriction.isOTPGet());
					needUpdate = true;
				}
				if(cardLink.getOTPUse() != restriction.isOTPUse())
				{
					cardLink.setOTPUse(restriction.isOTPUse());
					needUpdate = true;
				}
			}
			else
			{
				cardLink.setOTPGet(null);
				cardLink.setOTPUse(null);
				needUpdate = true;
			}
			if (isCODAvailable && (cardLink.getKind() == null  || !cardLink.getKind().equals(card.getKind())))
			{
				cardLink.setKind(card.getKind());
				needUpdate = true;
			}
			if (isCODAvailable && (cardLink.getSubkind() == null ||  !cardLink.getSubkind().equals(card.getSubkind())))
			{
				cardLink.setSubkind(card.getSubkind());
				needUpdate = true;
			}

			ExtendedCodeImpl officeCode = new ExtendedCodeImpl(card.getOffice().getCode());
			if (isCODAvailable && (cardLink.getGflTB() == null || !cardLink.getGflTB().equals(officeCode.getRegion())))
			{
				cardLink.setGflTB(officeCode.getRegion());
				needUpdate = true;
			}

			if (isCODAvailable && (cardLink.getGflOSB() == null || !cardLink.getGflOSB().equals(officeCode.getBranch())))
			{
				cardLink.setGflOSB(officeCode.getBranch());
				needUpdate = true;
			}

			if (isCODAvailable && (cardLink.getGflVSP() == null || !cardLink.getGflVSP().equals(officeCode.getOffice())))
			{
				cardLink.setGflVSP(officeCode.getOffice());
				needUpdate = true;
			}

			if (!StringUtils.equals(cardLink.getAutoSmsAlias(), smsAutoAlias))
			{
				cardLink.setAutoSmsAlias(smsAutoAlias);
				needUpdate = true;
			}

			boolean newShowInSms = cardLink.getShowInSystem() && cardLink.getShowInSms() && ermbSettings.isShowInSms();
			if (cardLink.getShowInSms() != newShowInSms && ErmbHelper.isErmbMigration())
			{
				cardLink.setShowInSms(newShowInSms);
				needUpdate = true;
			}

			boolean newErmbNotification = cardLink.getShowInSystem() && cardLink.getShowInSms() && ermbSettings.isNotification(cardLink.getResourceType());
			if (cardLink.getErmbNotification() != newErmbNotification && ErmbHelper.isErmbMigration())
			{
				cardLink.setErmbNotification(newErmbNotification);
				needUpdate = true;
			}

			if (cardLink.getAdditionalCardType() == null || cardLink.getAdditionalCardType() != card.getAdditionalCardType())
			{
				cardLink.setAdditionalCardType(card.getAdditionalCardType());
				needUpdate = true;
			}

			if (!StringUtils.equals(cardLink.getClientId(), card.getClientId()))
			{
				cardLink.setClientId(card.getClientId());
				needUpdate = true;
			}

			if (cardLink.isUseReportDelivery() != card.isUseReportDelivery())
			{
				cardLink.setUseReportDelivery(card.isUseReportDelivery());
				needUpdate = true;
			}

			if (!StringUtils.equals(cardLink.getEmailAddress(), card.getEmailAddress()))
			{
				cardLink.setEmailAddress(card.getEmailAddress());
				needUpdate = true;
			}

			if (cardLink.getReportDeliveryType() != card.getReportDeliveryType())
			{
				cardLink.setReportDeliveryType(card.getReportDeliveryType());
				needUpdate = true;
			}

			if (cardLink.getReportDeliveryLanguage() != card.getReportDeliveryLanguage())
			{
				cardLink.setReportDeliveryLanguage(card.getReportDeliveryLanguage());
				needUpdate = true;
			}

			if (arrestedCard)
			{
				boolean changeShowInMobile = cardLink.getShowInMobile() != null ? cardLink.getShowInMobile() : true;
				boolean changeShowInATM = cardLink.getShowInATM();

				if (changeShowInMobile || changeShowInATM)
				{
					cardLink.setShowInMobile(false);
					cardLink.setShowInATM(false);
					needUpdate = true;
				}
			}
			if (!StringUtils.equals(cardLink.getContractNumber(), card.getContractNumber()))
			{
				cardLink.setContractNumber(card.getContractNumber());
				needUpdate = true;
			}

			if (!extBlockedCardFilter.accept(card) && cardLink.getClosedState() != null && !cardLink.getClosedState())
			{
				cardLink.setClosedState(null);
				needUpdate = true;
			}
			else if (extBlockedCardFilter.accept(card) && cardLink.getClosedState() == null)
			{
				cardLink.setClosedState(true);
				needUpdate = true;
			}

			if (needUpdate)
			{
				resourceService.updateLink(cardLink);
			}
			cardLinks.remove(cardLink);
		}

		addOrUpdateStoredResource(card, cardLink);
		return;
	}

	private void addAccountLinks(ActivePerson person, Account account, AdditionalProductData additionalData,
	                             Map<Object, String> smsAutoAliases, boolean isESBSupported, List<AccountLink> accountLinks, ErmbProductSettings ermbSettings) throws BusinessException
	{
		AccountLink accountLink = BankrollServiceHelper.findAccountLinkByNumber(account.getNumber(), accountLinks);
		ProductPermissionImpl permission = (ProductPermissionImpl) additionalData;
		String smsAutoAlias = smsAutoAliases.get(account);

		boolean accessArrested = PermissionUtil.impliesServiceRigid("ArrestedAccountsInfo");
		boolean arrestedAccount = account.getAccountState().equals(AccountState.ARRESTED);
		boolean updated = false;
		log.trace("[com.rssl.phizic.business.clients.ClientResourcesService.addAccountLinks] account.getNumber() = " + account.getNumber() + "; accountLink not empty = " + (accountLink != null));
		if (accountLink != null)
		{
			boolean equals = true;
			if (!accountLink.getExternalId().equals(account.getId()) && isESBSupported)
			{
				//Обновляем внешний идентификатор.
				accountLink.setExternalId(account.getId());
				equals = false;
			}
			log.trace("[com.rssl.phizic.business.clients.ClientResourcesService.addAccountLinks] permission not empty = " + (permission != null));
			if (permission != null)
			{
				boolean showInSystem = BooleanUtils.toBoolean(accountLink.getShowInSystem());
				boolean showInMobile = BooleanUtils.toBoolean(accountLink.getShowInMobile());
				/*
                 * * CHG079989
                 * - Вклад не доступен, только если он не доступен в каналах СБОЛ, МП и СМС(клиент ЕРМБ)
                 * - Вклад доступен, если он доступен хотя бы в одном из каналов СБОЛ, МП и СМС(клиент ЕРМБ)
                 * - Если из внешней системы пришло, что вклад не доступен, то он недоступен во всех каналах.
				 */
				log.trace("[com.rssl.phizic.business.clients.ClientResourcesService.addAccountLinks] permission.showInSbol() = " + permission.showInSbol() + "; showInSystem = "+ showInSystem +
				"; showInMobile = " + showInMobile + "; ErmbHelper.isERMBConnectedPerson() = "+ ErmbHelper.isERMBConnectedPerson() + "; showInSms = "+ ermbSettings);
				if (permission.showInSbol() ^ (showInSystem || showInMobile || (ErmbHelper.isERMBConnectedPerson() && ermbSettings.isShowInSms())))
				{
					log.trace("[com.rssl.phizic.business.clients.ClientResourcesService.addAccountLinks] implies UpdateProductPermission = " + permission.showInSbol());
					if (PermissionUtil.impliesServiceRigid("UpdateProductPermission"))
					{
						accountLink.setShowInSystem(permission.showInSbol());
						accountLink.setShowInMobile(permission.showInSbol());
						equals = false;
						log.error("[com.rssl.phizic.business.clients.ClientResourcesService.addAccountLinks] accountLink.id = " + accountLink.getId() + "; permission.showInSbol() = " + permission.showInSbol());
					}
				}

				boolean showInES = BooleanUtils.toBooleanDefaultIfNull(permission.showInES(), true);
				if (showInES != accountLink.getShowInATM())
				{
					if (PermissionUtil.impliesServiceRigid("UpdateProductPermission"))
					{
						accountLink.setShowInATM(showInES);
						equals = false;
					}
				}
			}
			else
			{
				if (accountLink.getShowInATM())
				{
					if (PermissionUtil.impliesServiceRigid("UpdateProductPermission"))
					{
						accountLink.setShowInATM(true);
						equals = false;
					}
				}
			}

			if (accountLink.getName() == null || accountLink.getName().trim().length()==0) //CHG022000
			{
				String descr = account.getDescription();
				accountLink.setName( StringHelper.truncate(descr, ACCOUNT_NAME_MAX_LENGTH) );
				equals = false;
			}

			if (accountLink.getDescription() == null || !accountLink.getDescription().equals(accountLink.getDescription()))
			{
				accountLink.setDescription(account.getDescription());
				equals = false;
			}
			if (accountLink.getCurrency() == null || account.getCurrency() == null ||
					!accountLink.getCurrency().getNumber().equals(account.getCurrency().getNumber()))
			{
				accountLink.setCurrency(account.getCurrency());
				equals = false;
			}
			if (!StringUtils.equals(accountLink.getAutoSmsAlias(), smsAutoAlias))
			{
				accountLink.setAutoSmsAlias(smsAutoAlias);
				equals = false;
			}

			boolean newShowInSms = accountLink.getShowInSystem() && accountLink.getShowInSms() && ermbSettings.isShowInSms();
			if (accountLink.getShowInSms() != newShowInSms && ErmbHelper.isErmbMigration())
			{
				accountLink.setShowInSms(newShowInSms);
				equals = false;
			}

			boolean newErmbNotification = accountLink.getShowInSystem() && accountLink.getShowInSms() && ermbSettings.isNotification(accountLink.getResourceType());
			if (accountLink.getErmbNotification() != newErmbNotification && ErmbHelper.isErmbMigration())
			{
				accountLink.setErmbNotification(newErmbNotification);
				equals = false;
			}

			ExtendedCodeImpl officeCode = new ExtendedCodeImpl(account.getOffice().getCode());

			if (accountLink.getOfficeTB() == null || !accountLink.getOfficeTB().equals(officeCode.getRegion()))
			{
				accountLink.setOfficeTB(officeCode.getRegion());
				equals = false;
			}

			if (accountLink.getOfficeOSB() == null || !accountLink.getOfficeOSB().equals(officeCode.getBranch()))
			{
				accountLink.setOfficeOSB(officeCode.getBranch());
				equals = false;
			}

			if (accountLink.getOfficeVSP() == null || !accountLink.getOfficeVSP().equals(officeCode.getOffice()))
			{
				accountLink.setOfficeVSP(officeCode.getOffice());
				equals = false;
			}

			if (arrestedAccount)
			{
				boolean changeShowInMobile = accountLink.getShowInMobile() != null ? accountLink.getShowInMobile() : true;
				boolean changeShowInATM = accountLink.getShowInATM();

				//если нет прав на арестованные вклады, удаляем из линков
				if (!accessArrested)
					resourceService.removeLink(accountLink);
				else if (changeShowInMobile || changeShowInATM)
				{
					accountLink.setShowInMobile(false);
					accountLink.setShowInATM(false);
					equals = false;
				}
			}

			if (!closedAccountFilter.accept(account) && accountLink.getClosedState() != null && !accountLink.getClosedState())
			{
				accountLink.setClosedState(null);
				equals = false;
			}
            else if (closedAccountFilter.accept(account) && accountLink.getClosedState() == null)
			{
				accountLink.setClosedState(true);
				equals = false;
			}

			if (!equals)
				resourceService.updateLink(accountLink);

			accountLinks.remove(accountLink);
			updated = true;
		}
		//Для клиента СБОЛ нельзя добавлять свыше того, что ему добавил сотрудник
		if(!isSBOL(person) && !updated)
		{
			if (arrestedAccount && !accessArrested)
				return;

			if (arrestedAccount)
			{
				if (permission == null)
					permission = new ProductPermissionImpl();

				permission.setShowInMobile(false);
				permission.setShowInSocial(false);
				permission.setShowInATM(false);
			}

			accountLink = resourceService.addAccountLink(person.getLogin(), account, smsAutoAlias, ermbSettings, permission);

			AccountTarget target = targetService.findTargetByAccountForLogin(accountLink.getNumber(), person.getLogin());
			if(target != null)
			{
				target.setAccountLink(accountLink);
				targetService.addOrUpdate(target);
			}
		}

		// Если линк существует
		if (accountLink != null)
			addOrUpdateStoredResource(account, accountLink);
		return;
	}

	/**
	 * Проверка, является ли клиент SBOL
	 * @param person клиент
	 * @return true в случае если клиент СБОЛ
	 */
	private boolean isSBOL(ActivePerson person)
	{
		return person.getCreationType() == CreationType.SBOL;
	}

	public static Map<Class<? extends ExternalResourceLink>,List<? extends ExternalResourceLink>>
		initLinks(ActivePerson person, Class[] classArray)
	{
		Map<Class<? extends ExternalResourceLink>,List<? extends ExternalResourceLink>> result =
				new HashMap<Class<? extends ExternalResourceLink>,List<? extends ExternalResourceLink>>(6);

		if(ArrayUtils.contains(classArray,Account.class))
		{
			List<AccountLink> accountLinks = initLink(person, AccountLink.class);
			result.put(AccountLink.class,accountLinks);
		}

		if(ArrayUtils.contains(classArray,Card.class))
		{
			List<CardLink> cardLinks = initLink(person, CardLink.class);
			result.put(CardLink.class,cardLinks);
		}

		if(ArrayUtils.contains(classArray,IMAccount.class))
		{
			List<IMAccountLink> imAccountLinks = initLink(person, IMAccountLink.class);
			result.put(IMAccountLink.class,imAccountLinks);
		}

		if(ArrayUtils.contains(classArray,Loan.class))
		{
			List<LoanLink> loanLinks = initLink(person, LoanLink.class);
			result.put(LoanLink.class,loanLinks);
		}

		if(ArrayUtils.contains(classArray,DepoAccount.class))
		{
			List<DepoAccountLink> depoAccountLinks = initLink(person, DepoAccountLink.class);
			result.put(DepoAccountLink.class,depoAccountLinks);
		}

		return result;
	}

	/**
	 * Получение ресурсов клиента
	 * @param person персона
	 * @param clazz классы банковских продуктов, которые нужно получить
	 */
	public static <T extends ExternalResourceLink> List<T> initLink(ActivePerson person, final Class clazz)
	{
		try{
			List<T> linkList = resourceService.getLinks(person.getLogin(), clazz);
			if(linkList == null)
				return Collections.emptyList();
			return linkList;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при обновлении ресурсов клиента: ошибка при загрузке "+clazz.getName(), e);
			return new ArrayList<T>();
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при обновлении ресурсов клиента: ошибка при загрузке "+clazz.getName(), e);
			return new ArrayList<T>();
		}
	}

	private void removeOthersLinks(ActivePerson person, boolean isESBSupported, Map<Class<? extends ExternalResourceLink>,List<? extends ExternalResourceLink>> links) throws BusinessException
	{
		List<CardLink> cardLinks = (List<CardLink>)links.get(CardLink.class);
		removeLinks(cardLinks);
		if (!isESBSupported)
			return;
		// Клиенту SBOL нельзя удалять линки

		List<AccountLink> accountLinks = (List<AccountLink>)links.get(AccountLink.class);
		if (!isSBOL(person))
			removeLinks(accountLinks);

		List<DepoAccountLink> depoAccountLinks = (List<DepoAccountLink>)links.get(DepoAccountLink.class);
		removeLinks(depoAccountLinks);

		List<LoanLink> loanLinks = (List<LoanLink>)links.get(LoanLink.class);
		removeLinks(loanLinks);

		List<IMAccountLink> imAccountLinks = (List<IMAccountLink>)links.get(IMAccountLink.class);
		removeLinks(imAccountLinks);
	}
	private <T extends ExternalResourceLink> void removeLinks(List<T> links)
	{
		if(CollectionUtils.isEmpty(links))
			return;
		for (ExternalResourceLink link: links)
		{
			try
			{
				AbstractStoredResource storedLink = resourceService.findStoredResourceByResourceLink(link);
				if (storedLink != null)
				{
					resourceService.removeStoredResource(storedLink);
				}

				resourceService.removeLink(link);
			}
			catch (BusinessException ex)
			{
				log.error("Ошибка при обновлении ресурсов клиента: ошибка при удалении старого линка "+ link.getClass().getName()+link.getExternalId());
			}
		}
	}

	/**
	 * Сохранить информацию о подразделении карты, по которой вошел клиент
	 * @param login
	 * @param card
	 */
	private void saveLastLogonCardDepartmentsInfo(Login login, Card card)
	{
		Office office = card.getOffice();
		if (office == null)
			return;

		Code code = office.getCode();
		if (code == null)
			return;

		Map<String, String> codeFields = code.getFields();
		if (codeFields == null)
			return;

		String cardTB = parseCardTB(card.getId(), codeFields);

		updateClientLastLogonDepartmentsData(login, cardTB, codeFields.get("branch"), codeFields.get("office"));
	}

	private String parseCardTB(String cardId, Map<String, String> codeFields)
	{
		if (StringHelper.isNotEmpty(cardId))
		{
			String[] parameters = cardId.split("\\^");
			// См. CardOrAccountCompositeId
			if (ArrayUtils.isNotEmpty(parameters) && parameters.length > 4 && StringHelper.isNotEmpty(parameters[4]))
			{
				return parameters[4];
			}
		}
		
		return codeFields.get("region");
	}

	/**
	 * Сохранение информации о всп, осб, тб последнего входа клиента в БД
	 * @param login
	 */
	private void updateClientLastLogonDepartmentsData(Login login, String tbCode, String osbCode, String vspCode)
	{
		securityService.updateClientLastLogonDepartmentsData(login, tbCode, osbCode, vspCode, null);
	}

	private void clearLinkList(Class productType, Map<Class<? extends ExternalResourceLink>, List<? extends ExternalResourceLink>> links) throws BusinessException
	{
		if (productType.equals(Account.class))
		{
			links.get(AccountLink.class).clear();
			return;
		}
		if (productType.equals(Card.class))
		{
			links.get(CardLink.class).clear();
			return;
		}
		if (productType.equals(Loan.class))
		{
			links.get(LoanLink.class).clear();
			return;
		}
		if (productType.equals(IMAccount.class))
		{
			links.get(IMAccountLink.class).clear();
			return;
		}
		if (productType.equals(DepoAccount.class))
		{
			links.get(DepoAccountLink.class).clear();
			return;
		}
	}

	private void addOrUpdateStoredResource(Object source, ExternalResourceLinkBase resourceLink) throws BusinessException
	{
		AbstractStoredResource abstractStoredResource = resourceService.findStoredResourceByResourceLink(resourceLink);
		if (abstractStoredResource == null)
		{
			abstractStoredResource = StoredResourceHelper.loadStoredResourceInstance(resourceLink);

			abstractStoredResource.update(source);
			abstractStoredResource.setResourceLink(resourceLink);
			resourceService.addStoredResource(abstractStoredResource);
		}
		else
		{
			abstractStoredResource.update(source);
			abstractStoredResource.setResourceLink(resourceLink);
			resourceService.updateStoredResource(abstractStoredResource);
		}
	}

	/**
	 * Добавляем(обновляем) линк страхового продукта
	 * @param person - клиент
	 * @param insuranceApp - страховой продукт
	 * @param insuranceLinks - список соотв. линков клиента
	 * @return добавленный(обновленный) линк
	 * @throws BusinessException
	 */
	private InsuranceLink addInsuranceLink(ActivePerson person, InsuranceApp insuranceApp, List<InsuranceLink> insuranceLinks) throws BusinessException
	{
		InsuranceLink insuranceLink = InsuranceServiceHelper.findInsuranceLinkByNumber(insuranceLinks, insuranceApp.getReference());
		if (insuranceLink == null)
		{
			return resourceService.addInsuranceLink(person.getLogin(), insuranceApp);
		}
		else
		{
			if (!insuranceLink.getExternalId().equals(insuranceApp.getId()) || (StringHelper.isNotEmpty(insuranceApp.getBusinessProcess()) && insuranceLink.getBusinessProcess() != BusinessProcess.valueOf(insuranceApp.getBusinessProcess())))
			{
				insuranceLink.setExternalId(insuranceApp.getId());
				insuranceLink.setBusinessProcess(BusinessProcess.valueOf(insuranceApp.getBusinessProcess()));
				resourceService.updateLink(insuranceLink);
			}
			insuranceLinks.remove(insuranceLink);
		}
		return insuranceLink;
	}

	private SecurityAccountLink addSecurityAccountLink(ActivePerson person, SecurityAccount securityAccount, List<SecurityAccountLink> securityAccountLinks) throws BusinessException
	{
		SecurityAccountLink securityAccountLink = SecurityAccountServiceHelper.findSecurityAccountLinkByNumber(securityAccountLinks, securityAccount.getSerialNumber());
		if (securityAccountLink == null)
		{
			securityAccountLink = resourceService.addSecurityAccountLink(person.getLogin(), securityAccount);
		}
		else
		{
			if (!securityAccountLink.getExternalId().equals(securityAccount.getId()) || (securityAccountLink.getOnStorageInBank() ^ securityAccount.getOnStorageInBank()))
			{
				securityAccountLink.setExternalId(securityAccount.getId());
				securityAccountLink.setOnStorageInBank(securityAccount.getOnStorageInBank());
				resourceService.updateLink(securityAccountLink);
			}
			securityAccountLinks.remove(securityAccountLink);
		}
		addOrUpdateStoredResource(securityAccount, securityAccountLink);
		return securityAccountLink;
	}

	/**
	 * Обновление списка линков сберегательных сертификатов
	 * @param person - клиент
	 * @param securityAccountList список сертификатов
	 * @param securityAccountLinks  список уже добавленных линков
	 * @throws BusinessException
	 */
	public Map<SecurityAccountLink, SecurityAccount>  updateSecurityAccountLink(ActivePerson person, List<SecurityAccount> securityAccountList, List<SecurityAccountLink> securityAccountLinks) throws BusinessException
	{
		Map<SecurityAccountLink, SecurityAccount> linkSecurityAccountMap = new HashMap<SecurityAccountLink, SecurityAccount>();
		if (CollectionUtils.isNotEmpty(securityAccountList))
		{
			for (SecurityAccount securityAccount : securityAccountList)
			{
				SecurityAccountLink link = addSecurityAccountLink(person, securityAccount, securityAccountLinks);
				if (link != null)
						linkSecurityAccountMap.put(link, securityAccount);
			}
		}
		removeLinks(securityAccountLinks);
		return linkSecurityAccountMap;
	}
	/**
	 * Обновление списка линков сберегательных сертификатов оффлайн информацией
	 * @param securityAccountLinks  список уже добавленных линков
	 * @throws BusinessException
	 */
	public Map<SecurityAccountLink, SecurityAccount>  getStoredSecurityAccounts(List<SecurityAccountLink> securityAccountLinks) throws BusinessException
	{
		Map<SecurityAccountLink, SecurityAccount> linkSecurityAccountMap = new HashMap<SecurityAccountLink, SecurityAccount>();
		for (SecurityAccountLink securityAccountLink : securityAccountLinks)
		{
			StoredSecurityAccount storedSecurityAccount = (StoredSecurityAccount)resourceService.findStoredResourceByResourceLink(securityAccountLink);
			if (storedSecurityAccount != null)
				linkSecurityAccountMap.put(securityAccountLink, storedSecurityAccount);
		}
		return linkSecurityAccountMap;
	}
	/**
	 * Обновление списка линков страховых продуктов
	 * @param person - клиент
	 * @param insuranceAppList - список страховых продуктов
	 * @param insuranceLinks - список уже добавленных линков
	 * @return обновленный список
	 * @throws BusinessException
	 */
	public Map<InsuranceLink, InsuranceApp> updateInsuranceLinks(ActivePerson person, List<InsuranceApp> insuranceAppList, List<InsuranceLink> insuranceLinks) throws BusinessException
	{
		Map<InsuranceLink, InsuranceApp> insuranceList = new HashMap<InsuranceLink, InsuranceApp>();
		if (CollectionUtils.isNotEmpty(insuranceAppList))
		{
			for (InsuranceApp insuranceApp : insuranceAppList)
			{
				InsuranceLink insuranceLink = addInsuranceLink(person, insuranceApp, insuranceLinks);
				if (insuranceLink != null)
					insuranceList.put(insuranceLink, insuranceApp);
			}
		}

		for (InsuranceLink link: insuranceLinks)
		{
			try
			{
				resourceService.removeLink(link);
			}
			catch (BusinessException ex)
			{
				log.error("Ошибка при обновлении ресурсов клиента: ошибка при удалении старого линка "+ link.getClass().getName()+link.getExternalId(), ex);
			}
		}
		return insuranceList;
	}
}
