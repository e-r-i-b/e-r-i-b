package com.rssl.phizic.operations.account;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.pfr.PFRLinkService;
import com.rssl.phizic.business.profileSynchronization.products.ResourceInfoSynchronizationHelper;
import com.rssl.phizic.business.resources.ProductPermissionImpl;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.userprofile.AccountsAvailableSettings;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author potehin
 * @ created 21.05.2010
 * @ $Author$
 * @ $Revision$
 * Установка значений системной видимости продуктов
 */
public class SetupProductsSystemViewOperation extends ConfirmableOperationBase
{
	private static final ExternalResourceService externalService = new ExternalResourceService();
	private final MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
	private static final ClientProductsService clientProductsService = GateSingleton.getFactory().service(ClientProductsService.class);
	private static final PFRLinkService pfrLinkService = new PFRLinkService();

	protected Login login;
	protected List<AccountLink> accounts;
	protected List<CardLink> cards;
	protected List<LoanLink> loans;
	private List<DepoAccountLink> depoAccounts;
	protected List<IMAccountLink> imAccounts;
	protected List<SecurityAccountLink> securityAccounts;
	protected List<AccountLink> notUpdatedLinks = new ArrayList<AccountLink>();
	private PFRLink pfrLink;
	protected ErmbProfileImpl ermbProfile;
	protected boolean newProductShowInSms;
	protected ConfirmableObject confirmableObject;
	protected Map<Class<? extends EditableExternalResourceLink>, List<? extends EditableExternalResourceLink>> changedResources =
			new HashMap<Class<? extends EditableExternalResourceLink>, List<? extends EditableExternalResourceLink>>();

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		login = personData.getPerson().getLogin();

		accounts = personData.getAccountsAll();
		cards = personData.getCardsAll();
		loans = personData.getLoansAll();
		depoAccounts = personData.getDepoAccountsAll();
		imAccounts = personData.getIMAccountLinksAll();
		securityAccounts = personData.getSecurityAccountLinksAll();
		pfrLink = pfrLinkService.findByLoginId(login.getId());

		ermbProfile = ErmbHelper.getErmbProfileByLogin(login);
		if (ermbProfile != null)
			newProductShowInSms = ermbProfile.getNewProductShowInSms();
	}

	/**
	 * Получить клиентские счета
	 * @return список счетов клиента
	 * @throws BusinessException
	 */
	public List<AccountLink> getClientAccounts() throws BusinessException
	{
		return accounts;
	}

	/**
	 * Получить клиентские карты
	 * @return список карт клиента
	 * @throws BusinessException
	 */
	public List<CardLink> getClientCards() throws BusinessException
	{
		return cards;
	}

	/**
	 * Получить клиентские кредиты
	 * @return
	 * @throws BusinessException
	 */
	public List<LoanLink> getClientLoans() throws BusinessException
	{
		return loans;
	}

	/**
	 * Получить клиентские счета депо
	 * @return список счетов депо клиента
	 * @throws BusinessException
	 */
	public List<DepoAccountLink> getClientDepoAccounts() throws BusinessException
	{
		return depoAccounts;
	}

	/**
	 * Получить клиентские металл. счета
	 * @return список металл. счетов клиента
	 * @throws BusinessException
	 */
	public List<IMAccountLink> getClientIMAccounts() throws BusinessException
	{
		return imAccounts;
	}
	/**
	 * Получить клиентские сберегательные сертификаты
	 * @return список сберегательных сертификатов
	 * @throws BusinessException
	 */
	public List<SecurityAccountLink> getClientSecurityAccounts() throws BusinessException
	{
		return securityAccounts;
	}
	/**
	 * Получить список шаблонов клиента
	 * @return список шаблонов клиента
	 * @throws BusinessException
	 */

	/**
	 * Получить карточку ПФР
	 * @return карточка ПФР клиента
	 */
	public PFRLink getClientPfrLink()
	{
		return pfrLink;
	}
	
	/**
	 * Возвращает список счетов, по которым
	 * не удалось обновить отображение
	 * @return список счетов
	 */
	public List<AccountLink> getNotUpdatedLinks()
	{
		return notUpdatedLinks;
	}

	/**
	 * @return Логин клиента
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * Установить новые значения настроек системной видимости для счетов
	 * @param selectedIds Список идентификаторов (id) видимых счетов в системе
	 * @throws BusinessException
	 */
	private void updateAccountSettings(String[] selectedIds) throws BusinessException
	{
		for (AccountLink accountLink : accounts)
		{
			Long currentId = accountLink.getId();
			Boolean state = accountLink.getShowInSystem();
			Boolean newState = false;

			for (String accountId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(accountId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				accountLink.setShowInSystem(newState);
				addChangedResource(accountLink);
			}
		}
	}

	protected <T extends EditableExternalResourceLink> void addChangedResource(T resource)
	{
		Class clazz = resource.getClass();
		List list = changedResources.get(clazz);
		if (list == null)
		{
			list = new ArrayList<T>();
			changedResources.put(clazz, list);
		}
		list.add(resource);
	}

	/**
	 * Установить новые значения настроек системной видимости для карт
	 * @param selectedIds Список идентификаторов (id) видимых карт
	 * @throws BusinessException
	 */
	private void updateCardSettings(String[] selectedIds) throws BusinessException
	{
		for (CardLink cardLink : cards)
		{
			Long currentId = cardLink.getId();
			boolean state = cardLink.getShowInSystem();
			boolean newState = false;

			for (String cardId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(cardId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				cardLink.setShowInMain(newState);
				cardLink.setShowInSystem(newState);
				addChangedResource(cardLink);
			}
		}
	}

	/**
	 * Установить новые значения настроек системной видимости для кредитов
	 * @param selectedIds Список идентификаторов (id) видимых кредитов
	 * @throws BusinessException
	 */
	private void updateLoanSettings(String[] selectedIds) throws BusinessException
	{
		for (LoanLink loanLink : loans)
		{
			Long currentId = loanLink.getId();
			boolean state = loanLink.getShowInSystem();
			boolean newState = false;

			for (String loanId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(loanId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				loanLink.setShowInMain(newState);
				loanLink.setShowInSystem(newState);
				addChangedResource(loanLink);
			}
		}
	}

	/**
	 * Установить новые значения настроек системной видимости для счетов депо
	 * @param selectedIds Список идентификаторов (id) видимых счетов депо
	 * @throws BusinessException
	 */
	private void updateDepoAccountSettings(String[] selectedIds) throws BusinessException
	{
		for (DepoAccountLink depoAccountLink : depoAccounts)
		{
			Long currentId = depoAccountLink.getId();
			boolean state = depoAccountLink.getShowInSystem();
			boolean newState = false;

			for (String depoAccountId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(depoAccountId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				depoAccountLink.setShowInMain(newState);
				depoAccountLink.setShowInSystem(newState);
				addChangedResource(depoAccountLink);
			}
		}
	}

	/**
	 * Установить новые значения настроек системной видимости для металл. счетов
	 * @param selectedIds Список идентификаторов (id) видимых металл. счетов
	 * @throws BusinessException
	 */
	private void updateIMAccountSettings(String[] selectedIds) throws BusinessException
	{
		for (IMAccountLink imAccountLink : imAccounts)
		{
			Long currentId = imAccountLink.getId();
			boolean state = imAccountLink.getShowInSystem();
			boolean newState = false;

			for (String accountId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(accountId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				imAccountLink.setShowInMain(newState);
				imAccountLink.setShowInSystem(newState);
				addChangedResource(imAccountLink);
			}
		}
	}
	/**

	  * Установить новые значения настроек системной видимости для сберегательных сертификатов
	 * @param selectedIds Список идентификаторов (id) видимых сберегательных сертификатов
	 * @throws BusinessException
	 */
	private void updateSecurityAccountSettings(String[] selectedIds) throws BusinessException
	{
		for (SecurityAccountLink securityAccountLink : securityAccounts)
		{
			Long currentId = securityAccountLink.getId();
			boolean state = securityAccountLink.getShowInSystem();
			boolean newState = false;

			for (String accountId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(accountId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				securityAccountLink.setShowInSystem(newState);
				addChangedResource(securityAccountLink);
			}
		}
	}

	/**
	 * Установить новые значения настроек системной видимости для карточки ПФР (при наличии СНИЛС)
	 * @param newState -новое значение параметра  isShowInSystem
	 */
	private void updatePFRLink(boolean newState)
	{
		if (pfrLink == null)
			return;

		String SNILS = PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS();
		if(StringHelper.isEmpty(SNILS))
			return;
		
		boolean state = pfrLink.getShowInSystem();
		if(state ^ newState)
		{
			pfrLink.setShowInSystem(newState);
			addChangedResource(pfrLink);
		}
	}

	/**
	 * Отправить СМС сообщение с подтверждением операции
	 * @param ermbProductsNotification - сообщение об изменении оповещений, если true и сообщение об изменении видимости, если false
	 * @throws BusinessException Ошибка при работе с бизнес-сервисом
	 */
	public void sendSmsNotification(boolean ermbProductsNotification) throws BusinessException, BusinessLogicException
	{
		try
		{
			IKFLMessage message;
			if (ermbProductsNotification)
				message = MessageComposer.buildInformingSmsMessage(login.getId(), "com.rssl.iccs.user.sms.ermbProductsSmsNotification");
			else message = MessageComposer.buildInformingSmsMessage(login.getId(), "com.rssl.iccs.user.sms.systemview");
			messagingService.sendSms(message);
		}
		catch (IKFLMessagingException ex)
		{
			throw new BusinessException("Не удалось отправить SMS сообщение с подтверждением операции", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * Установить новые значения настроек системной видимости для счетов
	 * @param selectedAccountIds Список идентификаторов (id) видимых счетов
	 * @param selectedCardIds Список идентификаторов (id) видимых карт
	 * @param selectedLoanIds Список идентификаторов (id) видимых кредитов
	 * @param selectedDepoAccountsIds  Список идентификаторов (id) видимых счетов депо
	 * @param selectedIMAccountsIds  Список идентификаторов (id) видимых металл.счетов
	 * @param selectedPFRLink  Идентификатор карточки ПФР клмента
	 * @throws BusinessException Ошибка при работе с бизнес-сервисами
	 * @throws BusinessLogicException Ошибка отправки СМС с подтверждением
	 */
	public void updateResourcesSettings(String[] selectedAccountIds,  String[] selectedCardIds,
	                                    String[] selectedLoanIds,String[] selectedDepoAccountsIds,
										String[] selectedIMAccountsIds,String[] selectedSecurityAccountsIds, boolean selectedPFRLink) throws BusinessException, BusinessLogicException
	{
		updateAccountSettings(selectedAccountIds);
		updateCardSettings(selectedCardIds);
		updateLoanSettings(selectedLoanIds);
		updateDepoAccountSettings(selectedDepoAccountsIds);
		updateIMAccountSettings(selectedIMAccountsIds);
		updateSecurityAccountSettings(selectedSecurityAccountsIds);
		updatePFRLink(selectedPFRLink);
		//sendSmsNotification();
	}

	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
		{
			List<PFRLink> pfrLink = (List<PFRLink>) changedResources.get(PFRLink.class);
			confirmableObject = new AccountsAvailableSettings(
					(List<AccountLink>)changedResources.get(AccountLink.class),
					(List<CardLink>)changedResources.get(CardLink.class),
					(List<LoanLink>)changedResources.get(LoanLink.class),
					(List<DepoAccountLink>)changedResources.get(DepoAccountLink.class),
					(List<IMAccountLink>)changedResources.get(IMAccountLink.class),
					(List<SecurityAccountLink>)changedResources.get(SecurityAccountLink.class),
					CollectionUtils.isEmpty(pfrLink) ? null : pfrLink.get(0));
		}
		return confirmableObject;
	}

	public void saveConfirm() throws BusinessException, BusinessLogicException
	{
		updateLink(CardLink.class, Card.class);
		updateLink(LoanLink.class, Loan.class);
		updateLink(DepoAccountLink.class, DepoAccountLink.class);
		updateLink(IMAccountLink.class, IMAccount.class);
		updateLink(SecurityAccountLink.class, StoredAccount.class);
		if (pfrLink != null)
			pfrLinkService.addOrUpdate(pfrLink);
		updateAccountLinks();

		ResourceInfoSynchronizationHelper.updateResourceInfo(CardLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(LoanLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(DepoAccountLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(IMAccountLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(AccountLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(SecurityAccountLink.class);
		if (pfrLink != null)
			ResourceInfoSynchronizationHelper.updateResourceInfo(PFRLink.class);
	}

	protected void updateAccountLinks() throws BusinessException, BusinessLogicException
	{
		//обновляем состояние отображения счетов
		List<AccountLink> needUpdateInES = new ArrayList<AccountLink>(); //список линков для отправки состояний их видимости в шину
		List<Pair<Object, ProductPermission>> accountPermissions = new ArrayList<Pair<Object, ProductPermission>>();
		Client client = PersonContext.getPersonDataProvider().getPersonData().getPerson().asClient();

		List<AccountLink> changedAccountLinks = (List<AccountLink>) changedResources.get(AccountLink.class);
		if (CollectionUtils.isEmpty(changedAccountLinks))
			return;

		XmlEntityListCacheSingleton cacheSingleton = XmlEntityListCacheSingleton.getInstance();

		boolean updateProductPermissionAvailable = PermissionUtil.impliesServiceRigid("UpdateProductPermission");

		for (AccountLink accountLink : changedAccountLinks)
		{
			Account account = accountLink.getAccount();
			if (updateProductPermissionAvailable) //сохраняем линк для отправки запроса в шину
			{
				ProductPermissionImpl permission = new ProductPermissionImpl();
				permission.setShowInES(accountLink.getShowInATM());
				/*
				 * CHG079989
				 * - Вклад не доступен, только если он не доступен в каналах СБОЛ, МП и СМС(клиент ЕРМБ)
				 * - Вклад доступен, если он доступен хотя бы в одном из каналов СБОЛ, МП и СМС(клиент ЕРМБ)
				 */
				permission.setShowInSbol(BooleanUtils.toBoolean(accountLink.getShowInSystem()) || BooleanUtils.toBoolean(accountLink.getShowInMobile()) || (ErmbHelper.isERMBConnectedPerson() && accountLink.getShowInSms()));
				accountPermissions.add(new Pair<Object, ProductPermission>(account, permission));
				needUpdateInES.add(accountLink);
			}
			else //меняем только в базе
			{
				externalService.updateLink(accountLink);
				cacheSingleton.clearCache(account, Account.class);
			}
		}
		if (!accountPermissions.isEmpty())
		{
			try
			{
				GroupResult<Object, Boolean> updateResources = clientProductsService.updateProductPermission(client, accountPermissions);

				for (Map.Entry<Object, Boolean> permission : updateResources.getResults().entrySet())
				{
					Account account = (Account) permission.getKey();
					AccountLink accountLink = BankrollServiceHelper.findAccountLinkByNumber(account.getNumber(), needUpdateInES);
					boolean updated = BooleanUtils.isTrue(permission.getValue());
					if (updated)
					{
						externalService.updateLink(accountLink);
						cacheSingleton.clearCache(account, Account.class);
					}
					else
						notUpdatedLinks.add(accountLink);
				}
			}
			catch (GateException ge)
			{
				throw new BusinessException(ge);
			}
			catch (GateLogicException gle)
			{
				throw new BusinessLogicException(gle);
			}
		}
	}

	protected void updateLink(Class<? extends EditableExternalResourceLink> linkClass, Class entityClass) throws BusinessException
	{
		List<? extends ExternalResourceLink> links = changedResources.get(linkClass);
		if (CollectionUtils.isEmpty(links))
			return;
		XmlEntityListCacheSingleton cacheSingleton = XmlEntityListCacheSingleton.getInstance();
		for(ExternalResourceLink link: links)
		{
			externalService.updateLink(link);
			try
			{
				cacheSingleton.clearCache(link.getValue(), entityClass);
			}
			catch (BusinessLogicException e)
			{
				//ничего не делаем
			}
		}
	}

	public boolean noChanges()
	{
		return changedResources.isEmpty();
	}

	public boolean isNewProductShowInSms()
	{
		return newProductShowInSms;
	}

	public void setNewProductShowInSms(boolean newProductShowInSms)
	{
		this.newProductShowInSms = newProductShowInSms;
	}
}
