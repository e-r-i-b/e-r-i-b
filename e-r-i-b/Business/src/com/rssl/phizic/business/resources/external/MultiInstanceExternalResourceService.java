package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.MultiInstanceSecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.bankroll.MultiInstanceSendedAbstractService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyUtils;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.resolver.ACPLinkResolver;
import com.rssl.phizic.business.resources.external.resolver.AutoSubscriptionResolver;
import com.rssl.phizic.business.resources.external.resolver.DepositLinkResolver;
import com.rssl.phizic.business.resources.external.resolver.LinkResolver;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 08.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceExternalResourceService
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

    protected static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final MultiInstanceSendedAbstractService sendedAbstractService = new MultiInstanceSendedAbstractService();
	private static final MultiInstancePersonService PERSON_SERVICE = new MultiInstancePersonService();
	private final static MultiInstanceSecurityService securityService = new MultiInstanceSecurityService();

	private static final Map<Class, LinkResolver> linksMap = new HashMap<Class, LinkResolver>();

	static
	{
		linksMap.put(AccountLink.class,         new ACPLinkResolver<AccountLink>(AccountLink.class));
		linksMap.put(CardLink.class,            new ACPLinkResolver<CardLink>(CardLink.class));
		linksMap.put(PaymentSystemIdLink.class, new ACPLinkResolver<PaymentSystemIdLink>(PaymentSystemIdLink.class));
		linksMap.put(DepositLink.class,         new DepositLinkResolver());
		linksMap.put(LoanLink.class,            new ACPLinkResolver<LoanLink>(LoanLink.class));
		linksMap.put(DepoAccountLink.class,     new ACPLinkResolver<DepoAccountLink>(DepoAccountLink.class));
		linksMap.put(IMAccountLink.class,       new ACPLinkResolver<IMAccountLink>(IMAccountLink.class));
		linksMap.put(LongOfferLink.class,       new ACPLinkResolver<LongOfferLink>(LongOfferLink.class));
		linksMap.put(PaymentTemplateLink.class, new ACPLinkResolver<PaymentTemplateLink>(PaymentTemplateLink.class));
		linksMap.put(AutoPaymentLink.class,     new ACPLinkResolver<AutoPaymentLink>(AutoPaymentLink.class));
		linksMap.put(AutoSubscriptionLink.class,    new AutoSubscriptionResolver<AutoSubscriptionLink>());
		linksMap.put(InsuranceLink.class,       new ACPLinkResolver<InsuranceLink>(InsuranceLink.class));
		linksMap.put(SecurityAccountLink.class,       new ACPLinkResolver<SecurityAccountLink>(SecurityAccountLink.class));
	}

	/**
	 *  Добавить ссылку на карту
	 * @param login логин клиента
	 * @param card карта
	 * @param smsAutoAlias - автоматический СМС-алиас (can be null, can be empty)
	 * @param permission - признаки видимости продукта
	 * @param ermbSettings - признак доступности продукта в смс-канале
	 * @return ссылку на карту
	 */
	public CardLink addCardLink(CommonLogin login, Card card, String smsAutoAlias, ErmbProductSettings ermbSettings, OTPRestriction restriction, ProductPermission permission, String instanceName) throws BusinessException
	{
		CardLink link = new CardLink();
		link.setExternalId(card.getId());
		link.setLoginId(login.getId());
		link.setNumber(card.getNumber());
		link.setName(card.getDescription());
		link.setCurrency(CurrencyUtils.adaptCurrency(card.getCurrency()));
		link.setDescription(card.getDescription());
		link.setShowInMain(true);
		link.setShowInSystem(true);
		link.setShowInMobile(card.getCardState() != CardState.closed && (permission != null && permission.showInMobile() != null ? permission.showInMobile() : true));
		link.setShowInSocial(card.getCardState() != CardState.closed && (permission != null && permission.showInSocial() != null ? permission.showInSocial() : true));
		link.setShowInATM(permission != null && permission.showInATM() != null ? permission.showInATM() : true);
		link.setShowOperations(false);
		link.setExpireDate(card.getExpireDate());
		link.setCardPrimaryAccount(card.getPrimaryAccountNumber());
		link.setMain(card.isMain());
		link.setMainCardNumber(card.getMainCardNumber());
		link.setAdditionalCardType(card.getAdditionalCardType());
		link.setOTPGet(restriction == null ? null : restriction.isOTPGet());
		link.setOTPUse(restriction == null ? null : restriction.isOTPUse());
		link.setKind(card.getKind());
		link.setSubkind(card.getSubkind());
		ExtendedCodeImpl officeCode = new ExtendedCodeImpl(card.getOffice().getCode());
		link.setGflTB(officeCode.getRegion());
		link.setGflOSB(officeCode.getBranch());
		link.setGflVSP(officeCode.getOffice());
		link.setAutoSmsAlias(smsAutoAlias);
		link.setUseReportDelivery(card.isUseReportDelivery());
		link.setEmailAddress(card.getEmailAddress());
		link.setReportDeliveryType(card.getReportDeliveryType());
		link.setReportDeliveryLanguage(card.getReportDeliveryLanguage());
		link.setContractNumber(card.getContractNumber());
		link.setShowInSms(ermbSettings.isShowInSms());
		link.setErmbNotification(ermbSettings.isNotification(link.getResourceType()));

		simpleService.add(link,instanceName);
		return link;
	}

	/**
	*  Добавить ссылку на ОМС
	* @param login логин клиента
	* @param imAccount ОМС счёт
	* @param instanceName название инстанса (Shadow на образе и null на основной)
	* @param permission признаки видимости продукта
	* @return ссылку на ОМС
	*/
	public IMAccountLink addIMAccountLink(CommonLogin login, IMAccount imAccount, ProductPermission permission, String instanceName) throws BusinessException
	{
		IMAccountLink link = new IMAccountLink();
		link.setExternalId(imAccount.getId());
		link.setLoginId(login.getId());
		link.setName(imAccount.getName());
		link.setNumber(imAccount.getNumber());
		link.setShowInMain(true);
		link.setShowInSystem(true);
		link.setShowInMobile(imAccount.getState() != IMAccountState.closed && (permission != null && permission.showInMobile() != null ? permission.showInMobile() : true));
		link.setShowInSocial(imAccount.getState() != IMAccountState.closed && (permission != null && permission.showInSocial() != null ? permission.showInSocial() : true));
		link.setShowInATM(permission != null && permission.showInATM() != null ? permission.showInATM() : true);
		link.setShowOperations(false);
		link.setCurrency(imAccount.getCurrency());

		simpleService.add(link, instanceName);
		return link;
	}

	/**
	*  Добавить ссылку на сберегательный сертификат
	* @param login логин клиента
	* @param securityAccount сберегательный сертификат
	* @param instanceName название инстанса
	* @return ссылку на сберегательный сертификат
	*/
	public SecurityAccountLink addSecurityAccountLink(CommonLogin login, SecurityAccount securityAccount, String instanceName) throws BusinessException
	{
		SecurityAccountLink link = new SecurityAccountLink();
		link.setExternalId(securityAccount.getId());
		link.setLoginId(login.getId());
		link.setName("Сберегательный сертификат");
		link.setNumber(securityAccount.getSerialNumber());
		link.setOnStorageInBank(securityAccount.getOnStorageInBank());
		link.setShowInSystem(true);
		simpleService.add(link, instanceName);
		return link;
	}

	/**
	 * Получить список AccountLinks по номеру счета
	 * @param account счет
	 * @param instanceName название инстанса (Shadow на образе и null на основной)
	 * @return список AccountLink
	 */
	public List<AccountLink> getLinksByAccount(final String account, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<AccountLink>>()
			{
			    public List<AccountLink> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.AccountLink.findAccountByNumder");
				    query.setParameter("accountNumber", account);
				    return query.list();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * !!!!!ВАЖНО!!!!! метод возвращает ВСЕ линки, включая линки удаленных клиентов
	 * Получить список AccountLinks по номеру счета включая удаленных клиентов
	 * @param account счет
	 * @param instanceName название инстанса (Shadow на образе и null на основной)
	 * @return список AccountLink
	 */
	private List<AccountLink> getLinksByAccountIncludeDeletedUsers(final String account, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<AccountLink>>()
			{
				public List<AccountLink> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.AccountLink.findAccountByNumberIncludeDeletedUsers");
					query.setParameter("accountNumber", account);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Добавить ссылку на счет
	 * @param login логин клиента
	 * @param account счет
	 * @param smsAutoAlias - автоматический СМС-алиас (can be null, can be empty)
	 * @param ermbSettings - признак доступности продукта в смс-канале
	 * @param permission признак видимости продукта
	 * @return ссылку на карту
	 */
	public AccountLink addAccountLink(CommonLogin login, Account account, String smsAutoAlias, ErmbProductSettings ermbSettings, ProductPermission permission, String instanceName) throws BusinessException
	{
		AccountLink link = new AccountLink();
		link.setExternalId(account.getId());
		link.setLoginId(login.getId());
		link.setNumber(account.getNumber());
		link.setName(account.getDescription());
		link.setDescription(account.getDescription());
		link.setCurrency(CurrencyUtils.adaptCurrency(account.getCurrency()));
		link.setShowInSystem(permission != null && permission.showInSbol() != null ? permission.showInSbol() : true);
		link.setShowInMain(true);
		link.setShowOperations(false);
		link.setShowInMobile(account.getAccountState() != AccountState.CLOSED && (permission != null && permission.showInMobile() != null ? permission.showInMobile() : true));
		link.setShowInSocial(account.getAccountState() != AccountState.CLOSED && (permission != null && permission.showInSocial() != null ? permission.showInSocial() : true));
		link.setShowInATM(permission != null && permission.showInATM() != null ? permission.showInATM() : true);
		link.setShowInSms(ermbSettings.isShowInSms());
		link.setErmbNotification(ermbSettings.isNotification(link.getResourceType()));
		link.setAutoSmsAlias(smsAutoAlias);

		Office office =  account.getOffice();
		if (office != null)
		{
			Code code = office.getCode();
			link.setOfficeTB(code.getFields().get("region"));
			link.setOfficeOSB(code.getFields().get("branch"));
			link.setOfficeVSP(code.getFields().get("office"));
		}

		try
		{
			simpleService.addOrUpdateWithConstraintViolationException(link, instanceName);
		}
		catch (ConstraintViolationException e)
		{
			if (!ConfigFactory.getConfig(BusinessSettingsConfig.class).isChangeAccountOwnerEnabled())
				throw new BusinessException(e);

			log.error("Попытка добавить существующий в системе счет № " + link.getNumber(), e);
			List<AccountLink> oldLinks = getLinksByAccountIncludeDeletedUsers(link.getNumber(), instanceName);
			if (CollectionUtils.isNotEmpty(oldLinks))
			{
				AccountLink oldLink = oldLinks.get(0);
				Person oldLinkOwner = null;
				try
				{
					oldLinkOwner = PERSON_SERVICE.findPersonByLogin((Login) securityService.findById(oldLink.getLoginId(), instanceName), instanceName);
				}
				catch (SecurityDbException e1)
				{
					throw new BusinessException(e);
				}
				Long loginId = link.getLoginId();
				Person newLinkOwner = PERSON_SERVICE.findByLogin(loginId, instanceName);
				if (StringHelper.getEmptyIfNull(newLinkOwner.getFirstName()).equalsIgnoreCase(oldLinkOwner.getFirstName()) &&
						StringHelper.getEmptyIfNull(newLinkOwner.getPatrName()).equalsIgnoreCase(oldLinkOwner.getPatrName()) &&
						DateHelper.clearTime(newLinkOwner.getBirthDay()).compareTo(DateHelper.clearTime(oldLinkOwner.getBirthDay())) == 0)
				{
					// Если имя, отчество и ДР совпадают, привязываем линк к новому клиенту
					oldLink.setLoginId(loginId);
					CardOrAccountCompositeId oldLinkCompositeId = EntityIdHelper.getCardOrAccountCompositeId(oldLink.getExternalId());
					String accountCompositeId = EntityIdHelper.createCardOrAccountCompositeId(
							oldLinkCompositeId.getEntityId(),
							oldLinkCompositeId.getSystemId(),
							oldLinkCompositeId.getRbBrchId(),
							loginId,
							oldLinkCompositeId.getRegionId(),
							oldLinkCompositeId.getBranchId(),
							oldLinkCompositeId.getAgencyId()
					);
					oldLink.setExternalId(accountCompositeId);
					simpleService.update(oldLink, instanceName);
					log.error("Счет № " + link.getNumber() + " перепривязан новому клиенту ID = " +
							newLinkOwner.getId() + ". ID прежнего владельца " + oldLinkOwner.getId());
					return oldLink;
				}
				else
				{
					log.error("Счет № " + link.getNumber() +
							" нельзя привязать к новому клиенту, т.к. имя, отчество или дата рождения не совпадают.");
				}
			}
		}
		return link;
	}

	/**
	 * Добавить ссылку на кредит
	 * @param login логин клиента
	 * @param smsAutoAlias - автоматический СМС-алиас (can be null, can be empty)
	 * @param ermbSettings - признак доступности продукта в смс-канале
	 * @param loan кредит
	 * @return ссылка на кредит
	 * @throws BusinessException
	 */
	public LoanLink addLoanLink(CommonLogin login, String smsAutoAlias, ErmbProductSettings ermbSettings, Loan loan, String instanceName) throws BusinessException
	{
		LoanLink link = new LoanLink();
		link.setExternalId(loan.getId());
		link.setLoginId(login.getId());
		link.setNumber(loan.getAccountNumber());
		link.setName(loan.getDescription());
		link.setShowInMain(true);
		link.setShowInSystem(true);
		link.setShowInMobile(loan.getState() != LoanState.closed);
		link.setShowInSocial(loan.getState() != LoanState.closed);
		link.setShowInATM(true);
		link.setShowOperations(false);
		link.setPersonRole(loan.getPersonRole());
		link.setAutoSmsAlias(smsAutoAlias);
		link.setShowInSms(ermbSettings.isShowInSms());
		link.setErmbNotification(ermbSettings.isNotification(link.getResourceType()));

		simpleService.add(link,instanceName);
		return link;
	}

	/**
	 *  Добавить ссылку на счет депо клиента
	 * @param login  логин клиента
	 * @param depoAccount счет депо
	 * @return  ссылка на счет депо
	 * @throws BusinessException
	 */
	public DepoAccountLink addDepoAccountLink(CommonLogin login, DepoAccount depoAccount, String instanceName) throws BusinessException
	{
		DepoAccountLink link = new DepoAccountLink();
		link.setExternalId(depoAccount.getId());
		link.setLoginId(login.getId());
		link.setAccountNumber(depoAccount.getAccountNumber());
		link.setShowInMain(true);
		link.setShowInSystem(true);
		simpleService.add(link,instanceName);
		return link;
	}

	public LongOfferLink addLongOfferLink(CommonLogin login, LongOffer longOffer, String instanceName) throws BusinessException
	{
		LongOfferLink link = new LongOfferLink();
		link.setExternalId(longOffer.getExternalId());
		link.setLoginId(login.getId());
		link.setNumber(longOffer.getNumber().toString());
		link.setShowInMain(false);
		link.setShowInSystem(true);
		simpleService.add(link, instanceName);
		return link;
	}

	public LongOfferLink addLongOfferLink(LongOfferLink longOfferLink, String instanceName) throws BusinessException
	{
		LongOfferLink link = longOfferLink;
		link.setShowInMain(false);
		link.setShowInSystem(true);
		simpleService.add(link, instanceName);
		return longOfferLink;
	}

	/**
	 * Добавить ссылку на автоплатеж
	 * @param login логин клиаента
	 * @param autoPayment автоплатеж
	 * @param instanceName название инстанса
	 * @return ссылка на автоплатеж
	 * @throws BusinessException
	 */
	public AutoPaymentLink addAutoPaymentLink(CommonLogin login, AutoPayment autoPayment, String instanceName) throws BusinessException
	{
		AutoPaymentLink link = new AutoPaymentLink();
		link.setExternalId(autoPayment.getExternalId());
		link.setLoginId(login.getId());
		link.setNumber(autoPayment.getNumber());
		link.setName(autoPayment.getFriendlyName());
		link.setShowInMain(false);
		link.setShowInSystem(true);
		simpleService.add(link, instanceName);
		return link;	
	}

	/**
	 * Добавить ссылку на страховой продукт
	 * @param login логин клиаента
	 * @param insuranceApp страховой продукт
	 * @param instanceName название инстанса
	 * @return ссылка на страховой продукт
	 * @throws BusinessException
	 */
	public InsuranceLink addInsuranceLink(CommonLogin login, InsuranceApp insuranceApp, String instanceName) throws BusinessException
	{
		if (StringHelper.isNotEmpty(insuranceApp.getBusinessProcess()) && StringHelper.isNotEmpty(insuranceApp.getProgram()))
		{
			InsuranceLink link = new InsuranceLink();
			link.setExternalId(insuranceApp.getId());
			link.setLoginId(login.getId());
			link.setNumber(insuranceApp.getReference());
			link.setName(insuranceApp.getProgram());
			link.setBusinessProcess(BusinessProcess.valueOf(insuranceApp.getBusinessProcess()));
			link.setShowInSystem(true);
			simpleService.add(link, instanceName);
			return link;
		}
		return  null;
	}

	/**
	 * добавить ссылку на идентификатор во внешней платежной системе
	 * @param login логин клиента
	 * @param id идентификатор во внешней системе
	 * @param billing - биллинг
	 * @param instanceName
	 * @return ссылку на идентификатор
	 * @throws BusinessException
	 */
	public PaymentSystemIdLink addPaymentSystemIdLink(CommonLogin login, String id, Billing billing, String instanceName) throws BusinessException
	{
		PaymentSystemIdLink link = new PaymentSystemIdLink();
		link.setExternalId(id);
		link.setLoginId(login.getId());
		link.setBilling(billing);
		simpleService.add(link, instanceName);
		return link;
	}

	/**
	 * Удалить ссылку на внешний ресурс
	 * @param link ссылка на внешний ресурс
	 * @throws BusinessException
	 */
	public void removeLink(ExternalResourceLink link, String instanceName) throws BusinessException
	{
		if (link instanceof CardLink)
		{
			sendedAbstractService.removeAll((CardLink) link, instanceName);
		}

		simpleService.remove(link,instanceName);
	}

	/**
	 * Удалить все ресурсы клиента
	 * @param login логин клиента
	 * @param instanceName имя экземпляра БД
	 * @throws BusinessException
	 */
	public void removeAll(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		//удалить счета
		removeLink(login,AccountLink.class,instanceName);
		//удалить карты
		removeLink(login, CardLink.class,instanceName);
		//удалить ссылку на биллинговую систему
		removeLink(login,PaymentSystemIdLink.class,instanceName);
		//удалить ОМС
		removeLink(login,IMAccountLink.class,instanceName);
		//удалить длительное поручение
		removeLink(login, LongOfferLink.class, instanceName);
		//удалить кредиты
		removeLink(login,LoanLink.class,instanceName);
		//удалить счета депозитария
		removeLink(login,DepoAccountLink.class,instanceName);	
	}

	/**
	 * Получить и удалить ресурс.
	 * @param login - логин для которого удаляем ресурс
	 * @param clazz - класс удаляемого ресурса. Должен быть extends ExternalResourceLinkBase
	 * @param instanceName имя экземпляра БД
	 * @param <T> - тип удаляемого ресурса
	 * @throws BusinessException
	 */
	private <T extends ExternalResourceLinkBase> void removeLink(CommonLogin login, Class clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
		List<T> links = getLinks(login,clazz,instanceName);
		for (T link: links)
		{
			removeLink(link, instanceName);
		}
	}

	/**
	 * обновить ссылку на внешний ресурс
	 * @param link ссылка на внешний ресурс
	 * @throws BusinessException
	 */
	public void updateLink(ExternalResourceLink link, String instanceName) throws BusinessException
	{
		simpleService.update(link,instanceName);
	}

	/**
	 *
	 * Обновить оффлайн версию продуктов
	 *
	 * @param storedResource оффлайн версия продукта
	 * @param instanceName
	 * @throws BusinessException
	 */
	public void updateStoredResource(AbstractStoredResource storedResource, String instanceName) throws BusinessException
	{
		simpleService.update(storedResource, instanceName);
	}

	/**
	 *
	 * Добавить оффлайн версию продуктов
	 *
	 * @param storedResource оффлайн версия продукта
	 * @param instanceName
	 * @throws BusinessException
	 */
	public void addStoredResource(AbstractStoredResource storedResource, String instanceName) throws BusinessException
	{
		simpleService.add(storedResource, instanceName);	
	}

	/**
	 *
	 * Добавить или обновить оффлайн версию продуктов
	 *
	 * @param storedResource оффлайн версия продукта
	 * @param instance
	 * @throws BusinessException
	 */
	public void addOrUpdateStoredResource(AbstractStoredResource storedResource, String instance) throws BusinessException
	{
		simpleService.addOrUpdate(storedResource, instance);	
	}

	/**
	 *
	 * Удалить оффлайн версию продуктов
	 * 
	 * @param storedResource оффлайн версия продукта
	 * @param instanceName
	 * @throws BusinessException
	 */
	public void removeStoredResource(AbstractStoredResource storedResource, String instanceName) throws BusinessException
	{
		simpleService.remove(storedResource, instanceName);	
	}

	/**
	 * добавить или обновить ссылку на внешний ресурс
	 * @param link ссылка на внешний ресурс
	 * @throws BusinessException
	 */
	public void addOrUpdateLink(ExternalResourceLink link, String instanceName) throws BusinessException
	{
		simpleService.addOrUpdate(link, instanceName);
	}

	/**
	 * Вернуть список ссылок пользователя
	 * @param login логин пользователя
	 * @param clazz класс сыллки на ресурс
	 * @return список ссылок пользователя
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> List<T> getLinks(final CommonLogin login, final Class<T> clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
	    return getLinks(login.getId(), clazz, instanceName);
	}

	/**
	 * Вернуть список ссылок пользователя
	 * @param loginId логин пользователя
	 * @param clazz класс сыллки на ресурс
	 * @return список ссылок пользователя
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> List<T> getLinks(final Long loginId, final Class<T> clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
	    LinkResolver linkResolver = linksMap.get(clazz);
		return linkResolver.getLinks(loginId, instanceName);
	}

	/**
	 * Вернуть список линков пользователя, которые должны отображаться в системе
	 * @param login логин пользователя
	 * @param clazz класс сыллки на ресурс
	 * @return список ссылок пользователя
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> List<T> getInSystemLinks(final CommonLogin login, final Class<T> clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
	    LinkResolver linkResolver = linksMap.get(clazz);
		return linkResolver.getInSystemLinks(login, instanceName);
	}
	/**
	 * Вернуть список линков пользователя, которые должны отображаться в мобильной версии
	 * @param login логин пользователя
	 * @param clazz класс сыллки на ресурс
	 * @return список ссылок пользователя
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> List<T> getInMobileLinks(final CommonLogin login, final Class<T> clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
	    LinkResolver linkResolver = linksMap.get(clazz);
		return linkResolver.getInMobileLinks(login, instanceName);
	}

	/**
	 * Вернуть список линков пользователя, которые должны отображаться в мобильной версии
	 * @param login логин пользователя
	 * @param clazz класс сыллки на ресурс
	 * @return список ссылок пользователя
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> List<T> getInSocialLinks(final CommonLogin login, final Class<T> clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
	    LinkResolver linkResolver = linksMap.get(clazz);
		return linkResolver.getInSocialLinks(login, instanceName);
	}

	/**
	 * Вернуть список линков пользователя, которые должны отображаться в УС
	 * @param login логин пользователя
	 * @param clazz класс сыллки на ресурс
	 * @return список ссылок пользователя
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> List<T> getInATMLinks(final CommonLogin login, final Class<T> clazz, String instanceName) throws BusinessException, BusinessLogicException
	{
	    LinkResolver linkResolver = linksMap.get(clazz);
		return linkResolver.getInATMLinks(login, instanceName);
	}


	/**
	 * Найти ссылку по id
	 * @param clazz класс сыллки на ресурс
	 * @param id идентификатор ресурса
	 * @param instanceName
	 * @return ссылка на внешний ресурс
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> T findLinkById(Class<T> clazz, Long id, String instanceName)
			throws BusinessException
	{
		return simpleService.<T>findById(clazz, id, instanceName);
	}

	/**
	 * Найти отображаемую в системе ссылку по id
	 * @param criteria критерии
	 * @param instanceName
	 * @return ссылка на внешний ресурс
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> T findLinkByCriteria(DetachedCriteria criteria, String instanceName)
			throws BusinessException
	{
		if (criteria == null)
			throw new BusinessException("Не заданы условия выборки");

		return simpleService.<T>findSingle(criteria, instanceName);
	}

	/**
	 * Найти ссылку по внешнему ID и пользователю
	 * @param <T>
	 * @param clazz
	 * @param externalId
	 * @param login
	 * @param instanceName
	 * @return
	 * @throws BusinessException
	 */
	public <T extends ExternalResourceLink> T findLinkByExternalId(
			Class<T> clazz,
			String externalId,
			CommonLogin login,
			String instanceName
	) throws BusinessException
	{
		if (clazz == null)
			throw new NullPointerException("Argument 'clazz' cannot be null");
		if (StringHelper.isEmpty(externalId))
			throw new IllegalArgumentException("Argument 'externalId' cannot be empty");

		LinkResolver<T> resolver = linksMap.get(clazz);
		if (resolver == null)
			throw new UnsupportedOperationException(
					"External-link class " + clazz + " is not supported yet");
		return resolver.findByExternalId(login, externalId, instanceName);
	}

	/**
	 * репликация ресурсов пользователя
	 * @param login логин ползователя
	 * @param fromInstanceName имя экземпляра БД - откуда
	 * @param toInstanceName имя экземпляра БД - куда
	 * @return скопированные элементы
	 * @throws BusinessException
	 */
	public List<ExternalResourceLink> replicate(CommonLogin login, String fromInstanceName, String toInstanceName) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinksTo = getLinks(login,AccountLink.class,toInstanceName);
		List<CardLink> cardLinksTo = getLinks(login, CardLink.class,toInstanceName);
		List<PaymentSystemIdLink> paymentSystemIdLinksTo = getLinks(login, PaymentSystemIdLink.class, toInstanceName);
		List<AccountLink> accountLinks = getLinks(login,AccountLink.class,fromInstanceName);
		List<CardLink> cardLinks = getLinks(login, CardLink.class,fromInstanceName);
		List<PaymentSystemIdLink> paymentSystemIdLinks = getLinks(login, PaymentSystemIdLink.class, fromInstanceName);
		List<IMAccountLink> imAccountLinks = getLinks(login, IMAccountLink.class, fromInstanceName);

		for (AccountLink accountLink : accountLinks)
		{
			for (AccountLink link : accountLinksTo)
			{
			    if(link.compareTo(accountLink)==0)
			    {
					// если счет был удален, а потом снова добавлен, то id поменялась
				    // следовательно, при простой репликации primary key по id попытается вставить новую строку,
				    // что приведет к нарушению ограничения уникальности ACCOUNT_LINKS_I_NUMBER_LOGIN
				    accountLink.setId(link.getId());
				    accountLinksTo.remove(link);
				    break;
			    }
			}
			simpleService.replicate(accountLink, toInstanceName);
		}

		for (CardLink cardLink : cardLinks)
		{
			for (CardLink link : cardLinksTo)
			{
				if(link.compareTo(cardLink)==0)
				{
					cardLink.setId(link.getId()); // аналогичная проблема с accountLink
					cardLinksTo.remove(link);
					break;
				}
			}
			simpleService.replicate(cardLink, toInstanceName);
		}

		for (PaymentSystemIdLink paymentSystemIdLink : paymentSystemIdLinks)
		{
			for (PaymentSystemIdLink link : paymentSystemIdLinksTo)
			{
			    if(link.compareTo(paymentSystemIdLink)==0)
			    {
			        paymentSystemIdLink.setId(link.getId()); // аналогичная проблема с accountLink
				    paymentSystemIdLinksTo.remove(link);
				    break;
			    }
			}
			simpleService.replicate(paymentSystemIdLink, toInstanceName);
		}

		for (AccountLink accountLink : accountLinksTo)
		{
			removeLink(accountLink, toInstanceName);
		}

		for (CardLink cardLink : cardLinksTo)
		{
			removeLink(cardLink, toInstanceName);
		}

		for (PaymentSystemIdLink paymentSystemIdLink : paymentSystemIdLinksTo)
		{
			removeLink(paymentSystemIdLink, toInstanceName);
		}

		for (IMAccountLink imAccountLink : imAccountLinks)
		{
			removeLink(imAccountLink, toInstanceName);
		}

		List<ExternalResourceLink> result = new ArrayList<ExternalResourceLink>(accountLinks.size() + cardLinks.size() + paymentSystemIdLinks.size());
		result.addAll(accountLinks);
		result.addAll(cardLinks);
		result.addAll(paymentSystemIdLinks);
		return result;
	}

	public void addNewAccountLink(Login login, Account account) throws BusinessException
	{
		MultiInstancePersonService multiInstancePersonService = new MultiInstancePersonService();
		ActivePerson person = multiInstancePersonService.findByLogin(login, null);
		String name = multiInstancePersonService.getPersonInstanceName(person.getId());
		ErmbProductSettings ermbSettings = ErmbProductSettings.get(person.getId());

		if (MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME.equals(name))
		{
			AccountLink newAccountLink = addAccountLink(login, account, null, ermbSettings, null, name);
			simpleService.replicate(newAccountLink, null);
		}
		else
		{
			addAccountLink(login, account, null, ermbSettings, null, null);
		}
	}

	/**
	 * Возвращает линк пользователя по номеру
	 * Реализовано для счёта, карты, кредита, ОМС и счёта-депо
	 * @param login - логин пользователя
	 * @param type - тип линка
	 * @param number - номер
	 * @param inSystem - отображаемый в системе
	 * @param inMobile - отображаемый в мобильном приложении
	 * @param inATM - отображаемый в УС
	 * @param instanceName
	 * @return линк или null, если не найден ил если для данного вида не существует линков
	 */
	public <T extends ExternalResourceLink> T findLinkByNumber(CommonLogin login, ResourceType type, String number, boolean inSystem, boolean inMobile, boolean inATM, boolean inSocial, String instanceName) throws BusinessException
	{
		return this.<T>findLinkByNumber(login.getId(), type, number, inSystem, inMobile, inATM, inSocial, instanceName);
	}

		/**
	 * Возвращает линк пользователя по номеру
	 * Реализовано для счёта, карты, кредита, ОМС и счёта-депо
	 * @param loginId - логин пользователя
	 * @param type - тип линка
	 * @param number - номер
	 * @param inSystem - отображаемый в системе
	 * @param inMobile - отображаемый в мобильном приложении
	 * @param inATM - отображаемый в УС
	 * @param instanceName
	 * @return линк или null, если не найден ил если для данного вида не существует линков
	 */
	public <T extends ExternalResourceLink> T findLinkByNumber(Long loginId, ResourceType type, String number, boolean inSystem, boolean inMobile, boolean inATM, boolean inSocial, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria;

		Class<? extends ExternalResourceLink> resourceLinkClass = type.getResourceLinkClass();
		if (resourceLinkClass == null) {
			log.warn("Для ресурса типа " + type + " не существует линков");
			return null;
		}

		switch (type)
		{
			case ACCOUNT:
			case CARD:
			case LOAN:
			case IM_ACCOUNT:
				criteria = DetachedCriteria.forClass(resourceLinkClass).
						add(Expression.eq("number", number)).
						add(Expression.eq("loginId", loginId));
				break;
			case DEPO_ACCOUNT:
				criteria = DetachedCriteria.forClass(resourceLinkClass).
							add(Expression.eq("accountNumber", number)).
							add(Expression.eq("loginId", loginId));
				break;
			default:
				log.warn("Данный вид ресурса не поддерживает поиск по номеру: " + type);
				return null;
		}

		if (inSystem)
				criteria.add(Expression.eq("showInSystem", true));

		if(inMobile)
			criteria.add(Expression.eq("showInMobile", true));

		if (inATM)
			criteria.add(Expression.eq("showInATM", true));

        if (inSocial)
            criteria.add(Expression.eq("showInSocial", true));

		return simpleService.<T>findSingle(criteria, instanceName);
	}

	/**
	 * Добавление payment-template линка
	 * @param login - логин пользователя, под которым создаётся линк
	 * @param serviceProvider - поставщик
	 * @param template - шаблон платежа
	 * @param instanceName
	 * @return
	 */
	public PaymentTemplateLink addPaymentTemplateLink(
			CommonLogin login,
			BillingServiceProvider serviceProvider,
			GatePaymentTemplate template,
			String instanceName
	) throws BusinessException
	{
		PaymentTemplateLink link = new PaymentTemplateLink();
		link.setExternalId(template.getExternalId());
		link.setServiceProvider(serviceProvider);
		link.setLoginId(login.getId());
		link = simpleService.add(link, instanceName);
		return link;
	}

	/**
	 * Поиск ссылки на программу лояльности по логину клиента
	 * @param login логин клиента
	 * @param instanceName
	 * @return линк
	 * @throws BusinessException
	 */
	public LoyaltyProgramLink getLoyaltyProgramLink(CommonLogin login, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyProgramLink.class).
						add(Expression.eq("loginId", login.getId()));
		return simpleService.<LoyaltyProgramLink>findSingle(criteria, instanceName);
	}

	/**
	 * Поиск ссылок на страховые продукты по логину клиента и бизнес-процессу
	 * @param login - логин клиента
	 * @param businessProcess - бизнес-процесс
	 * @param instanceName - название инстанса
	 * @return список ссылок
	 * @throws BusinessException
	 */
	public List<InsuranceLink> getInsuranceLinks(CommonLogin login, BusinessProcess businessProcess, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(InsuranceLink.class);
		criteria.add(Expression.eq("loginId", login.getId()));
		criteria.add(Expression.eq("businessProcess", businessProcess));
		return simpleService.find(criteria, instanceName);
	}
	/**
	 * Поиск ссылки на страховой продукт по номеру
	 * @param reference - номер
	 * @param instanceName - название инстанса
	 * @return ссылка
	 * @throws BusinessException
	 */
	public InsuranceLink getInsuranceLinksByReference(String reference, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(InsuranceLink.class);
		criteria.add(Expression.eq("number", reference));
		return simpleService.<InsuranceLink>findSingle(criteria,instanceName);
	}

	/**
	 *
	 * Возвращает оффлайн информ
	 *
	 * @param link
	 * @return
	 * @throws BusinessException
	 */
	public AbstractStoredResource findStoredResourceByResourceLink(ExternalResourceLink link) throws BusinessException
	{
		if (link.getStoredResourceType() == null || link.getId() == null)
		{
			return null;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(link.getStoredResourceType())
				                                    .add(Expression.eq("resourceLink", link));

		return simpleService.findSingle(criteria, null);
	}
}
