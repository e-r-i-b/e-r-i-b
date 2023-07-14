package com.rssl.phizic.operations.ermb.person;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.bankroll.BankrollProductRulesService;
import com.rssl.phizic.business.ermb.migration.CodWayErmbConnectionSender;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;
import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEventService;
import com.rssl.phizic.business.ermb.sms.command.ErmbPhoneDeleteTask;
import com.rssl.phizic.business.ermb.sms.module.InternetClientChannel;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.ModuleStaticManager;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.security.PersonConfirmManager;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * User: moshenko
 * Date: 08.10.2012
 * Time: 12:00:50
 * Подключение/редактирование профиля ЕРМБ
 */
public class ErmbProfileOperation extends OperationBase<Restriction> implements EditEntityOperation<ErmbProfileOperationEntity, Restriction>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private final static ErmbTariffService tariffService = new ErmbTariffService();
	private final static PersonService personService = new PersonService();
	private final static SimpleService simpleService = new SimpleService();
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private static final ErmbRegistrationEventService registrationService = new ErmbRegistrationEventService();
	private static final BankrollProductRulesService bankrollProductRulesService = new BankrollProductRulesService();
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();

	private final boolean productAvailabilityCommonAttribute = ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute();

	private ErmbUpdateListener updateListener = ErmbUpdateListener.getListener();
	ErmbResourseParamsEvent resourseEvent;
	ErmbProfileEvent profileEvent; 

	private ErmbProfileOperationEntity entity = new ErmbProfileOperationEntity();
	private boolean isNewProfile = false;

	public void initialize(Long personId, boolean isConnect) throws BusinessException, BusinessLogicException
	{
		entity.setConnect(isConnect);
		List<ErmbTariff> tariffs = tariffService.getAllTariffs();
		if (tariffs.isEmpty())
			throw new BusinessException("Необходимо добавить хотя бы один тариф подключения ЕРМБ");
		entity.setTarifs(tariffs);
		ErmbProfileImpl profile = resolveProfile(personId);
		try
		{
			List<ConnectorInfo> connectorInfos = CSABackResponseSerializer.getConnectorInfos(CSABackRequestHelper.sendGetClientConnectorsRq(PersonHelper.buildUserInfo(profile.getPerson()), ConnectorInfo.Type.MAPI));
			Calendar lastRequestDate = null;
			boolean flag = false;
			for (ConnectorInfo connectorInfo : connectorInfos)
			{
				if (!flag)
				{
					lastRequestDate = connectorInfo.getLastSessionDate();
					flag = true;
				} else if (connectorInfo.getLastSessionDate()!=null && connectorInfo.getLastSessionDate().compareTo(lastRequestDate) > 0)
					lastRequestDate = connectorInfo.getLastSessionDate();
			}
			if (lastRequestDate != null)
				entity.setLastRequestTime(lastRequestDate);
		}
		catch (TransformerException e)
		{
			log.error(e);
		}
		catch (BackLogicException e)
		{
			log.error(e);
		}
		catch (BackException e)
		{
			log.error(e);
		}
		ErmbProfileStatistic statistic = profileService.findStatisticById(profile.getId());
		if (statistic!= null)
			entity.setLastSmsRequestTime(statistic.getLastRequestTime());

		entity.setProfile(profile);
		List<CardLink> cardLinks = profile.getCardLinks();
		List<AccountLink> accountLinks = profile.getAccountLinks();
		List<LoanLink> loanLinks = profile.getLoanLinks();

		if (productAvailabilityCommonAttribute)
		{
			entity.setCardsAvailableInSmsBank(getShowInSmsProductIds(cardLinks));
			entity.setAccountsAvailableInSmsBank(getShowInSmsProductIds(accountLinks));
			entity.setLoansAvailableInSmsBank(getShowInSmsProductIds(loanLinks));
		}
		else
		{
			entity.setCardsNotify(getNotifyProductIds(cardLinks));
			entity.setAccountsNotify(getNotifyProductIds(accountLinks));
			entity.setLoansNotify(getNotifyProductIds(loanLinks));
			entity.setCardsShowInSms(getShowInSmsProductIds(cardLinks));
			entity.setAccountsShowInSms(getShowInSmsProductIds(accountLinks));
			entity.setLoansShowInSms(getShowInSmsProductIds(loanLinks));
		}

		entity.setSelectedTarif(String.valueOf(profile.getTarif().getId()));
		entity.setMainPhoneNumber(profile.getMainPhoneNumber());
		entity.setPhonesNumber(profile.getPhoneNumbers());

		resourseEvent = new ErmbResourseParamsEvent(profile);
		ErmbProfileImpl oldProfile = isNewProfile ? null : ErmbHelper.copyProfile(profile);
		profileEvent = new ErmbProfileEvent(oldProfile);
	}

	private ErmbProfileImpl resolveProfile(Long personId) throws BusinessException, BusinessLogicException
    {
		ErmbProfileImpl result = profileService.findByPersonId(personId);
		if (result != null)
			return result;
		Person person = personService.findById(personId);
		if (person == null)
			throw new BusinessException("Ошибка подключения ЕРМБ. Не найдено пользователя с id = " + personId);
	    Department department = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getDepartment();
		result = ErmbHelper.createErmbProfile(person, false, null, department.getId());
	    bankrollProductRulesService.checkAndApplyRulesToProfile(result);
	    result.setPhones(Collections.<ErmbClientPhone>emptySet());
		isNewProfile = true;
		return result;
	}

	private Set<ErmbClientPhone> updateErmbPhones(ErmbProfileImpl profile, Set<String> newNumbers) throws BusinessException, BusinessLogicException
	{
		Set<ErmbClientPhone> result;
		if (isNewProfile)
		{
			newNumbers.addAll(entity.getPhonesNumber());
			result = ErmbHelper.getErmbPhones(newNumbers, profile);
			profile.setPhones(result);
		}
		else
		{
			result = updatePhones(profile, newNumbers);
			profile.getPhones().addAll(result);
		}
		// определяем главный номер
		for (ErmbClientPhone phone : profile.getPhones())
			phone.setMain(StringHelper.equals(phone.getNumber(), entity.getMainPhoneNumber()));
		return result;
	}

	private Set<ErmbClientPhone> updatePhones(ErmbProfileImpl profile, Set<String> newNumbers) throws BusinessException, BusinessLogicException
	{
		// получаем новые номера, введенные пользователем
		Set<String> updatedNumbers = entity.getPhonesNumber();
		// получаем старые номера из профиля
		Set<String> oldNumbers = profile.getPhoneNumbers();
		// получаем новые номера, которые должны быть добавлены
		newNumbers.addAll(updatedNumbers);
		newNumbers.removeAll(oldNumbers);
		// определяем новые номера
		return ErmbHelper.getErmbPhones(newNumbers, profile);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ErmbProfileImpl profile = entity.getProfile();
					if (isNewProfile || entity.isConnect())
					{   //дата подключение для нового профиля
						if (profile.getConnectionDate() == null)
							profile.setConnectionDate(DateHelper.getCurrentDate());
					}
					Long tarifId = Long.valueOf(entity.getSelectedTarif());
					ErmbTariff tarif = tariffService.findById(tarifId);
					ErmbHelper.updateErmbTariff(profile, tarif);
					if (!StringHelper.isEmpty(entity.getMainCardId()))
					{
						//Приоритетная карта для списания абонентской платы
						Long mainCardId = Long.valueOf(entity.getMainCardId());
						CardLink cardLink = simpleService.findById(CardLink.class, mainCardId);
						profile.setForeginProduct(cardLink);
					}
					else
						profile.setForeginProduct(null);

					///подготавливаем пустые списки для старых и новых номеров телефонов
					Set<String> newNumbers = new HashSet<String>();
					Set<String> removedNumbers = entity.getPhonesToDelete();

					//запоминаем номер старого главного телефона
					String oldMainPhoneNumber = profile.getMainPhoneNumber();

					//Обновить телефоны клиента в ЕРМБ
					Set<ErmbClientPhone> newPhones = updateErmbPhones(profile, newNumbers);

					//достаем главный телефон и проверяем, изменился ли он
					String mainPhoneNumber = profile.getMainPhoneNumber();
					String newMainPhoneNumber = null;
					if (StringHelper.isNotEmpty(mainPhoneNumber) && !mainPhoneNumber.equals(oldMainPhoneNumber))
						newMainPhoneNumber = mainPhoneNumber;

					//отвязать телефоны от МБК
					if (CollectionUtils.isNotEmpty(entity.getMbkPhones()))
					{
						try
						{
							MigrationHelper.removePhoneFromMbk(entity.getMbkPhones());
						}
						catch (Exception e)
						{
							//При получении ошибки от МБК необходимо отображать текст ошибки, понятный сотруднику
							throw new BusinessLogicException("При попытке удаления номера из МБК произошла ошибка", e);
						}
					}

					List<String> newNumbersList = new ArrayList<String>();
					List<String> removedNumbersList = new ArrayList<String>();
					UserInfo userInfo = new UserInfo();

					//если есть удаленные или добавленные телефоны или сменился активный телефон, отправляем запрос в ЦСА
					if (!newNumbers.isEmpty() || !removedNumbers.isEmpty() || StringHelper.isNotEmpty(newMainPhoneNumber))
					{
						userInfo = PersonHelper.buildUserInfo(profile.getPerson());

						newNumbersList.addAll(newNumbers);
						removedNumbersList.addAll(removedNumbers);
						//отправляем в ЦСА информацию об измененных телефонах
						try
						{
							CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(newMainPhoneNumber, userInfo, newNumbersList, removedNumbersList);
						}
						catch (BackLogicException e)
						{
							throw new BusinessLogicException(e);
						}
						catch (BackException e)
						{
							throw new BusinessException(e);
						}
					}

					//если есть удаленные или добавленные телефоны, добавляем их в таблицу ЕРМБ-телефонов в PHIZ_PROXY_MBK
					if (!newNumbers.isEmpty() || !removedNumbers.isEmpty())
					{
						ermbPhoneService.saveOrUpdateERMBPhones(newNumbers, removedNumbers);
					}

					try
					{
						// удаляем номера
						if (removedNumbers.size() != 0)
							removePhoneNumbers(profile, removedNumbers);

						if (entity.isConnect() && !profile.isServiceStatus())
						{
							if (CollectionUtils.isEmpty(profile.getPhones()) || profile.getMainPhoneNumber() == null)
							{
								throw new BusinessLogicException("Нельзя подключить Мобильный Банк без основного телефона подключения");
							}
							profile.setServiceStatus(true);
						}
						// Сохраняем профиль, предварительно увеличив версию, если это требуется.
						profileEvent.setNewProfile(profile);
						updateListener.beforeProfileUpdate(profileEvent);
						profileService.addOrUpdate(profile);
						registrationService.add(newPhones);

						if (isNewProfile && profile.isServiceStatus())
						{
							CodWayErmbConnectionSender sender = new CodWayErmbConnectionSender();
							sender.sendErmbConnected(profile);
						}

						//Обновляем продукты
						updateProduct(profile);
					}
					catch (Exception ex)
					{
						if (!newNumbers.isEmpty() || !removedNumbers.isEmpty() || StringHelper.isNotEmpty(newMainPhoneNumber))
						{
							try
							{
                                CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(oldMainPhoneNumber, userInfo, removedNumbersList, newNumbersList);
							}
							catch (Exception e)
							{
								log.error("Невозможно сделать откат изменений телефонов из ЦСА", e);
							}
						}
						if (!newNumbers.isEmpty() || !removedNumbers.isEmpty())
						{
							ermbPhoneService.saveOrUpdateERMBPhones(removedNumbers, newNumbers);
						}
						throw ex;
					}
					return null;
				}
			});
			//не нужно отсылать приветственное смс, если телефон уже был активен в МБК/МБВ
			boolean isNew = isNewProfile || entity.isConnect();
			//todo добавить проверку на МБВ (пока непонятно что делать с подключаемыми МБВ телефонами)
			boolean isFromMb = CollectionUtils.isNotEmpty(entity.getMbkPhones());
			profileEvent.setSendSms(!(isNew && isFromMb));
			updateListener.afterProfileUpdate(profileEvent);
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void sendConfirmMessage(Person profile, Set<String> phonesToDelete)
	{
		if (phonesToDelete.size()!=0)
		{
			Module m = ModuleStaticManager.getInstance().getModule(InternetClientChannel.class);
			ConfirmEngine ce = m.getConfirmEngine();
			PersonConfirmManager pcm = ce.createPersonConfirmManager(profile);
			for (String phone : phonesToDelete)
			{
				ErmbPhoneDeleteTask task = new ErmbPhoneDeleteTask();
				task.setPhoneToDelete(phone);
				pcm.askForConfirm(task, phone);
			}
		}
	}

	/**
	 * обновляем признаки, уведомлений продуктов клиента
	 */
	private void updateProduct(ErmbProfileImpl profile) throws BusinessException
	{
		List<CardLink> cardLinks = profile.getCardLinks();
		List<AccountLink> accountLinks = profile.getAccountLinks();
		List<LoanLink> loanLinks = profile.getLoanLinks();

		resourseEvent.setOldData(cardLinks, accountLinks, loanLinks);

		if (productAvailabilityCommonAttribute)
		{
			List<String> selectedCardsAvailableInSmsBank = entity.getCardsAvailableInSmsBank();
			updateProductList(cardLinks, selectedCardsAvailableInSmsBank);

			List<String> selectedAccountsAvailableInSmsBank = entity.getAccountsAvailableInSmsBank();
			updateProductList(accountLinks, selectedAccountsAvailableInSmsBank);

			List<String> selectedLoansAvailableInSmsBank = entity.getLoansAvailableInSmsBank();
			updateProductList(loanLinks, selectedLoansAvailableInSmsBank);
		}

		else
		{
			List<String> selectedCardsShowInSms = entity.getCardsShowInSms();
			List<String> selectedCardsNtf = entity.getCardsNotify();

			ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(profile);

			boolean cardsSmsNotificationAllow = permissionCalculator.impliesCardNotification();
			updateProductList(cardLinks, selectedCardsShowInSms, selectedCardsNtf, cardsSmsNotificationAllow);

			List<String> selectedAccountsShowInSms = entity.getAccountsShowInSms();
			List<String> selectedAccountsNtf = entity.getAccountsNotify();

			boolean accountsSmsNotificationAllow = permissionCalculator.impliesAccountNotification();
			updateProductList(accountLinks, selectedAccountsShowInSms, selectedAccountsNtf, accountsSmsNotificationAllow);

			List<String> selectedLoansShowInSms = entity.getLoansShowInSms();
			List<String> selectedLoansNtf = entity.getLoansNotify();

			boolean loanSmsNotificationAllow = permissionCalculator.impliesLoanNotification();
			updateProductList(loanLinks, selectedLoansShowInSms, selectedLoansNtf, loanSmsNotificationAllow);
		}

		resourseEvent.setNewData(cardLinks, accountLinks, loanLinks);
		updateListener.onResoursesUpdate(resourseEvent);
	}

	private void updateProductList(List<? extends ErmbProductLink> productLinks, List<String> selectedProductsShowInSms, List<String> selectedProductsNtf, boolean isTariffAllowSmsNotification) throws BusinessException
	{
		for(ErmbProductLink product : productLinks)
		{
			product.setShowInSms(selectedProductsShowInSms.contains(String.valueOf(product.getId())));
			if (isTariffAllowSmsNotification)
				product.setErmbNotification(selectedProductsNtf.contains(String.valueOf(product.getId())));
			else
				product.setErmbNotification(false);
		}
		simpleService.addOrUpdateList(productLinks);
	}

	private void updateProductList(List<? extends ErmbProductLink> productLinks, List<String> selectedProductsAvailableInSmsBank) throws BusinessException
	{
		for(ErmbProductLink product : productLinks)
		{
			product.setShowInSms(selectedProductsAvailableInSmsBank.contains(String.valueOf(product.getId())));
		}
		simpleService.addOrUpdateList(productLinks);
	}

	private List<String>  getShowInSmsProductIds(List<? extends ErmbProductLink> ermbProductLinks)
	{
		List<String> showInSmsProductIds = new ArrayList<String>();
		for (ErmbProductLink productLink : ermbProductLinks)
		{
			if (productLink.getShowInSms())
				showInSmsProductIds.add(String.valueOf(productLink.getId()));
		}
		return showInSmsProductIds;
	}

	private List<String> getNotifyProductIds(List<? extends ErmbProductLink> ermbProductLinks)
	{
		List<String> notifyProductIds = new ArrayList<String>();
		for (ErmbProductLink productLink : ermbProductLinks)
		{
			if (productLink.getErmbNotification())
				notifyProductIds.add(String.valueOf(productLink.getId()));
		}
		return notifyProductIds;
	}

	public ErmbProfileOperationEntity getEntity()
	{
		return entity;
	}



	/**
	 * удаление телефонов из профиля ЕРМБ
	 * @param profile - ЕРМБ профиль
	 * @param removedNumbers  - номера телефонов для удаления
	 * @throws BusinessException
	 */
	private void removePhoneNumbers(ErmbProfileImpl profile, Set<String> removedNumbers) throws BusinessException
	{
		Set<ErmbClientPhone> profilePhones = profile.getPhones();
		for (Iterator<ErmbClientPhone> iterator = profilePhones.iterator(); iterator.hasNext();)
		{
			ErmbClientPhone phone = iterator.next();
			if (removedNumbers.contains(phone.getNumber()))
			{
				iterator.remove();
			}
		}
		if (profilePhones.size() == 0)
		{
			// Не выставляем клиентскую блокировку согласно
			// BUG075813: ЕРМБ : АРМ Сотрудника : Блокировка услуги при удалении номера
			// ErmbHelper.blockProfile(profile, ErmbHelper.NO_PHONE_BLOCK_DESCRIPTION);
			profile.setServiceStatus(false);
		}
	}
}
