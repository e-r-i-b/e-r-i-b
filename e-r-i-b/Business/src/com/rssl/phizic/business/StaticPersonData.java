package com.rssl.phizic.business;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.bki.CreditProfileService;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCounter;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.csa.IncognitoService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ClientBusinessDocumentOwner;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.etsm.offer.ETSMOfferHelper;
import com.rssl.phizic.business.etsm.offer.service.OfferPriorWebService;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.offers.*;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.PersonSocialID;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profile.TutorialProfileState;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarService;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.quick.pay.PanelBlock;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.security.ERIBCSAPrincipalPermissionCalculator;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.business.web.WebPage;
import com.rssl.phizic.business.web.Widget;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.common.types.basket.InvoicesSubscriptionsPromoMode;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.employee.ManagerInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplateService;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.crypto.DefaultCryptoService;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.security.PublicKey;
import java.util.*;
import javax.crypto.Cipher;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 13.10.2005 Time: 13:39:36 */
@SuppressWarnings({"OverlyComplexClass"})
public class StaticPersonData implements PersonData
{
	/**
	 * Количество дней свежести кредитного отчета.
	 */
	private static final int CREDIT_REPORT_FRESH_PERIOD = 45;
	/**
	 * Период актуальности контекста в миллисекундах
	 * Т.е. кол-во времени, в течении которого данные контекста считаются актуальными
	 */
	private static final long PERSONDATA_LIFETIME = 60*1000;
	protected static ExternalResourceService  resourceService = new ExternalResourceService();
	private static final SecurityService securityService = new SecurityService();
	private static final DepartmentService departmentService = new DepartmentService();
	protected static final ClientResourcesService clientResourceService = new ClientResourcesService();
	private static final ProfileService profileService = new ProfileService();
	private static final MailService mailService = new MailService();
	private static final AccountTargetService targetService = new AccountTargetService();
	private static final InvoiceSubscriptionService invoiceSubscriptionsService = new InvoiceSubscriptionService();
	private static final CreditProfileService creditProfileService = new CreditProfileService();
	private static final UserCountersService userCountersService = new UserCountersService();
	private static final SimpleService service = new SimpleService();
	private static final OfferPriorWebService offerPriorWebService = new OfferPriorWebService();
	private final ActivePerson person;
	private final Login login;
	private final String clientId;      //криптографический идентификатор клиента для метрики Pixel
	private final Department department; // Подразделение клиента
	private final Department tb; // ТБ клиента
	private boolean needUpdatePersonData = false;
	private Calendar lastUpdateDate = Calendar.getInstance();
	private volatile Profile profile;
	private boolean checkedPromoBlock = false;

	private Map<String, WidgetDefinition> widgetDefinitions = Collections.emptyMap();

	/**
	 * Контейнеры виджетов на странице
	 */
	private Map<String, WebPage> pages = Collections.emptyMap();
	private Map<String, WebPage> savedPages = Collections.emptyMap(); //сохраненные веб-страницы
	protected boolean needReloadCards = false; //признак необходимости обновления списка карт при обращении к ним
	protected boolean needReloadAccounts   = false; //признак необходимости обновления списка счетов при обращении к ним
	protected boolean needReloadIMAccounts = false; //признак необходимости обновления списка металлических счетов при обращении к ним
	private boolean wayCardsReloaded = true; // признак, указывающий на то, что полученные при входе карты перечитаны

	private String skinUrl;
	private String globalUrl;
	private Long   mailCounter;
	private List<Long> bannersList;
	private List<PanelBlock>   blocks;
	private ManagerInfo manager;

	private List<MenuLinkInfo> linkInfoList;

	private boolean blocked = false;
	private boolean fraud = false;
	private Boolean ermbSupported = null;
	private String logonSessionId = null;//идентификатор сессии в рамках которой работает клиент

	private boolean needLoadLoans = false;
	private boolean needLoadAccounts = false;
	private boolean needLoadIMAccounts = false;
	private Boolean hasOpenedDifLoan = null;
	private boolean showOldBrowserMessage = true;
	private List<Long> shownNotificationList = new ArrayList<Long>();
	private List<PersonalOfferNotification> offerNotificationList = new ArrayList<PersonalOfferNotification>();
	private boolean showTrainingInfo = false; //признак, указывающий на необходимость показывать обучающую информацию в новом профиле
	private boolean isNewPersonalInfo = false; //отображение значка "новых пунктов меню" для "Личной информации"
	private boolean isNewBillsToPay = false; //отображение значка "новых пунктов меню" для "Автопоиска счетов к оплате"
	private TutorialProfileState stateProfilePromo = TutorialProfileState.CLOSED; //отображение промо при входе в профиль
	private InvoicesSubscriptionsPromoMode promoMode;               //режим отображения баннера на форме "Счета и платежи"

	private final Object lock = new Object();

	//информация об аватарах.
	private Map<AvatarType, AvatarInfo> avatarInfos = new EnumMap<AvatarType, AvatarInfo>(AvatarType.class);

	private volatile Properties userProperties;

	//картинки, помеченные для удаления.
	private List<File> markDeleteImages = new LinkedList<File>();

	private ClientInvoiceData clientInvoiceData = new ClientInvoiceData();

	private final Set<String> informedLoanOfferIds = new HashSet<String>(); //идентификаторы предложений по кредитам, по которым в сессии клиента отправили информационный отклик
	private final Set<String> informedLoanCardOfferIds = new HashSet<String>(); //идентификаторы предложений по картам, по которым в сессии клиента отправили информационный отклик

	/**
	 * Время последней актуализации контекста
	 * (время последнего обновления контекста данными из бд)
	 * Реализовано периодическая актуализация следующих полей:
	 * - blocked
	 * - logonSessionId
	 */
	private Calendar lastSynchTime = null;
	private Calendar mailSynchTime = null;
	private Calendar csaSessionSynchTime = Calendar.getInstance();

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Список идентификаторо социальных сетей пользователя.
	 */
	private Map<String , PersonSocialID> socialNetIds;

	//список арестованных карт клиента
	private Set<String> clientArrestedCards = new HashSet<String>();

	private final Calendar creationDate = Calendar.getInstance();

	private boolean incognito;

	private volatile Region region;
	private final Object lockRegion = new Object();

	private static final int NOT_ALTERNATIVE_PHONES = 0;
	private static final int ALTERNATIVE_PHONES = 1;
	private static final int NOT_SET_ALTERNATIVE_PHONES = 2;
	private volatile Collection<String> userPhones[] = new Collection[3];
	private boolean trustedContactConfirmed = false;
	//В каком режиме отображать активные кредиты и кредитные карты(блочный/списочный)
	private Boolean isActiveCreditViewBlock=true;

	private Boolean mustUpdateCreditReport;

    private Set<String> cardDetailInfoUpdated= new HashSet<String>();

    private Calendar promoBlockUntil;

	private final OfferService offerService;

	private boolean loanCardClaimAvailable;

	private Boolean isMB;

	private OfferPrior offerPrior;

	public StaticPersonData(ActivePerson person)
	{
		this.person = person;
		this.login = person.getLogin();

		Application currentApplication = ApplicationInfo.getCurrentApplication();
		if (currentApplication == Application.PhizIC && PermissionUtil.impliesService("PixelMarkingService"))
		{
			String genClientId;
			try
			{
				genClientId = generateClientId(login.getLastLogonCardNumber());
			}
			catch (Exception e)
			{
				log.warn("Ошибка генерации криптографического идентификатора клиента", e);
				genClientId = null;
			}
			clientId = genClientId;
		}
		else
			clientId = null;

		department = getSafeDepartment(person);
		tb = getSafeTb(department);
		try
		{
			incognito = IncognitoService.getIncognitoSetting(person);
		}
		catch (Exception e)
		{
			log.warn("Ошибка при получении признака инкогнито клиента", e);
		}

		offerService = initOfferService(person, currentApplication);
	}

	private OfferService initOfferService(ActivePerson person, Application currentApplication)
	{
		if (person.getCreationType() == CreationType.POTENTIAL || currentApplication == Application.Scheduler || currentApplication == Application.ESBERIBListener)
		{
			return new CompositeOffersService(false, false);
		}
		if (currentApplication == Application.PhizIA)
		{
			AuthenticationConfig authConfig = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);
			boolean offersV1Available = false;
			boolean offersV2Available = false;
			AccessPolicy policy = authConfig.getPolicy(AccessType.simple);
			try
			{
				offersV1Available = new ERIBCSAPrincipalPermissionCalculator(new PrincipalImpl(person.getLogin(), policy, null)).impliesService("LoanOfferView", false);
				offersV2Available = new ERIBCSAPrincipalPermissionCalculator(new PrincipalImpl(person.getLogin(), policy, null)).impliesService("GetPreapprovedOffersService", false);
			}
			catch (BusinessException e)
			{
				log.error("Ошибка определения прав клиента на доступ к получению предодобренных предложений", e);
			}

			return new ETSMLoanOfferFilterService(new CompositeOffersService(offersV1Available, offersV2Available));
		}

		AuthModule authModule = AuthModule.getAuthModule();
		boolean offersV1Available = authModule.implies(new ServicePermission("LoanOfferView", false));
		//до 9 версии mApi новая версия заявок недоступна
		boolean legacyMApi = MobileApiUtil.isMobileApiLT(MobileAPIVersions.V9_00);
		boolean offersV2Available = !legacyMApi && authModule.implies(new ServicePermission("GetPreapprovedOffersService", false));
		return new ETSMLoanOfferFilterService(new CompositeOffersService(offersV1Available, offersV2Available));
	}

	private String generateClientId(String cardNum) throws Exception
	{
		if (StringHelper.isEmpty(cardNum))
		{
			log.warn("Номер карты последнего входа пуст");
			return null;
		}

		//крипто-хэш по алгоритму SHA1
		CryptoService cryptoService = new DefaultCryptoService();
		byte[] hashCardNumber = cryptoService.hash(cardNum.getBytes(), "SHA1");
		//шестнадцатиричное представление
		String hexHash = StringUtils.toHexString(hashCardNumber);
		// + рандомный хвост
		String ranHexHash = hexHash + ";" + (int)(Math.random()*Integer.MAX_VALUE);
		//шифруем RSA
		final PublicKey key = MetricPixelSingleton.getPublicKey();
		if (key == null)
			throw new BusinessException("Не удалось получить открытый ключ шифрования RSA");
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cryptoClientId = cipher.doFinal(ranHexHash.getBytes(), 0, ranHexHash.length());
		return StringUtils.toHexString(cryptoClientId);
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public Login getLogin()
	{
		return login;
	}

	/**
	 * @return  криптоидентификатор клиента или null
	 */
	public String getClientId()
	{
		return clientId;
	}

	public Department getDepartment()
	{
		return department;
	}

	public Department getTb()
	{
		return tb;
	}

	public Profile getProfile() throws BusinessException
	{
		if (profile == null)
		{
			synchronized (lock)
			{
				if (profile == null)
					profile = profileService.findByLogin(person.getLogin());
			}
		}

		return profile;
	}

	public boolean isPersonBlocked()
	{
		synchronize();
		return blocked;
	}

	public boolean isFraud()
	{
		return fraud;
	}

	public void needPersonUpdate()
	{
		needUpdatePersonData = true;
	}

	public void reset(Calendar nextUpdateDate)
	{
		if (needUpdatePersonData && (lastUpdateDate == null || lastUpdateDate.before(nextUpdateDate)))
		{
			setNeedReloadCards();
			setNeedReloadAccounts();
			setNeedReloadIMAccounts();
			setNeedLoadLoans(true);
			lastUpdateDate = nextUpdateDate;
			needUpdatePersonData = false;
		}
	}

	public void setBlocked(boolean blocked)
	{
		this.blocked = blocked;
	}

	public void setFraud(boolean fraud)
	{
		this.fraud = fraud;
	}

	public Boolean isErmbSupported()
	{
		return ermbSupported;
	}

	public void setErmbSupported(Boolean ermbSupported)
	{
		this.ermbSupported = ermbSupported;
	}

	private void synchronize()
	{
		Calendar now = Calendar.getInstance();

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();

		boolean needSynch = lastSynchTime == null;
		needSynch = needSynch || lastSynchTime.getTimeInMillis() + PERSONDATA_LIFETIME < now.getTimeInMillis();
		if (needSynch)
		{
			blocked = securityService.isLoginBlocked(login, now.getTime());
			logonSessionId = securityService.getLogonSessionId(login.getId(), ApplicationType.getApplicationType(applicationConfig.getLoginContextApplication()));
			lastSynchTime = now;
		}
	}

	public List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.getInSystemLinks(login, AccountLink.class);
	}

	public List<AccountLink> getAccountsAll() throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.getLinks(login, AccountLink.class);
	}

	protected void reloadAccounts() throws BusinessException, BusinessLogicException
	{
		if (needReloadAccounts || needLoadAccounts)
		{
			try
			{
				clientResourceService.updateResources(person, false, Account.class);
				needReloadAccounts = false;
				needLoadAccounts = false;
			}
			catch (InactiveExternalSystemException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	public List<AccountLink> getAccounts(AccountFilter accountFilter) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinks = getAccounts();
		List<AccountLink> result = new ArrayList<AccountLink>();

		for (AccountLink accountLink : accountLinks)
		{
			if (accountFilter.accept(accountLink))
			{
				result.add(accountLink);
			}
		}
		return result;
	}

	public AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		AccountLink al = resourceService.findInSystemLinkById(login, AccountLink.class, accountLinkId);
		if (al == null)
			throw new ResourceNotFoundBusinessException("cчет c id=" + accountLinkId + " не принадлежит пользователю", AccountLink.class);
		return al;
	}

	public AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.findInSystemLinkByNumber(login, ResourceType.ACCOUNT, accountNumber);
	}

	public List<AccountTarget> getAccountTargets() throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return targetService.findTargetsByOwner(login);
	}

	/**
	 * Устанавливает признак необходимости повторного получения карт.
	 */
	public void setNeedReloadCards()
	{
		needReloadCards = true;
	}

	/**
	 * Установка признака необходимости повторного получения счетов из шины
	 * и очистка счетов
	 */
	public void setNeedReloadAccounts()
	{
		this.needReloadAccounts = true;
	}

	public void setNeedReloadIMAccounts()
	{
		this.needReloadIMAccounts = true;
	}

	protected void reset(List<? extends ExternalResourceLink> links)
	{
		if(links == null)
			return;
		
		for (ExternalResourceLink link : links)
		{
			try
			{
				link.reset();
			}
			catch (Exception ex)
			{
				log.error("Ошибка при очистке кеша для внешнего ресурса " + link.toString(), ex);
			}
		}
	}

	/**
	 * Перезагружает список карт.
	 */
	protected void reloadCards() throws BusinessException, BusinessLogicException
	{
		if (needReloadCards)
		{
            clearCardDetailInfoUpdated();
            reset(resourceService.getInSystemLinks(login, CardLink.class));
			try
			{
				clientResourceService.updateResources(person, false, Card.class);
				needReloadCards = false;
				wayCardsReloaded = true;
			}
			catch (InactiveExternalSystemException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	//По картам
	public List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		reloadCards();
		return resourceService.getInSystemLinks(login, CardLink.class);
	}

	public List<CardLink> getCardsAll() throws BusinessException, BusinessLogicException
	{
		reloadCards();
		return resourceService.getLinks(login, CardLink.class);
	}

	public List<CardLink> getCards(final CardFilter cardFilter) throws BusinessException, BusinessLogicException
	{
		List<CardLink> result = new ArrayList<CardLink>(getCards());
		CollectionUtils.filter(result, cardFilter);
		return result;
	}

	public CardLink getCard(Long cardLinkId) throws BusinessException
	{
		try
		{
			reloadCards();
			CardLink cl = resourceService.findInSystemLinkById(login, CardLink.class, cardLinkId);
			if (cl == null)
				throw new ResourceNotFoundBusinessException("карта c id=" + cardLinkId + " не принадлежит пользователю", CardLink.class);
			return cl;
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск карты по её номеру
	 * @param cardNumber номер карты
	 * @return cardLink
	 * @throws BusinessException
	 */
	public CardLink findCard(String cardNumber) throws BusinessException
	{
		try
		{
			reloadCards();
			return resourceService.findInSystemLinkByNumber(login, ResourceType.CARD, cardNumber);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	protected void reloadIMAccounts() throws BusinessException, BusinessLogicException
	{
		if (needReloadIMAccounts || needLoadIMAccounts)
		{
			try
			{
				clientResourceService.updateResources(person, false, IMAccount.class);
				needReloadIMAccounts = false;
				needLoadAccounts = false;
			}
			catch (InactiveExternalSystemException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Возвращает список ссылок на ОМС
	 * @return List<IMAccountLink> список ссылок
	 * @throws BusinessException
	 */
	public List<IMAccountLink> getIMAccountLinks() throws BusinessException, BusinessLogicException
	{
		reloadIMAccounts();
		return resourceService.getInSystemLinks(login, IMAccountLink.class);
	}

	public List<IMAccountLink> getIMAccountLinksAll() throws BusinessException, BusinessLogicException
	{
		reloadIMAccounts();
		return resourceService.getLinks(login, IMAccountLink.class);
	}

	/**
	 * Возвращаем список ссылок на страховые продукты клиента по бизнес-процессу
	 * @param businessProcess - бизнес-процесс
	 * @return  список ссылок
	 * @throws BusinessException
	 */
	public List<InsuranceLink> getInsuranceLinks(BusinessProcess businessProcess) throws BusinessException
	{
		return resourceService.getInsuranceLinks(login, businessProcess);
	}

	/**
	 * Возвращаем список ссылок на страховые продукты клиента
	 * @return  список ссылок
	 * @throws BusinessException
	 */
	public List<InsuranceLink> getInsuranceLinks() throws BusinessException, BusinessLogicException
	{
		return resourceService.getInSystemLinks(login, InsuranceLink.class);
	}
	/**
	 * Возвращаем список ссылок на сберегательные сертификаты клиента, которые должны отображаться в системе
	 * @return  список ссылок на сберегательные сертификаты
	 * @throws BusinessException
	 */
	public List<SecurityAccountLink> getSecurityAccountLinks() throws BusinessException, BusinessLogicException
	{
		return resourceService.getInSystemLinks(login, SecurityAccountLink.class);
	}
	/**
	 * Возвращаем список ссылок на все сберегательные сертификаты клиента
	 * @return  список ссылок на сберегательные сертификаты
	 * @throws BusinessException
	 */
	public List<SecurityAccountLink> getSecurityAccountLinksAll() throws BusinessException, BusinessLogicException
	{
		return resourceService.getLinks(login, SecurityAccountLink.class);
	}

	/**
	 * возврящает ссылку на  сберегательный сертификат по идентификатору
	 * @param linkId - id  сберегательный сертификат
	 * @return ссылка на  сберегательный сертификат
	 * @throws BusinessException
	 */
	public SecurityAccountLink getSecurityAccountLink(Long linkId)  throws BusinessException
	{
		SecurityAccountLink securityAccountLink = resourceService.findInSystemLinkById(login, SecurityAccountLink.class, linkId);
		if (securityAccountLink == null)
			throw new ResourceNotFoundBusinessException("Сберегательный сертификат с id=" + linkId + " не принадлежит пользователю", SecurityAccountLink.class);
		return securityAccountLink;
	}

	/**
	* Возвращает ссылку на ОМС по идентификатору
	 * @return IMAccountLink ссылка на ОМС
	 * @throws BusinessException
	 */
	public IMAccountLink getImAccountLinkById(Long imaccountLinkId) throws BusinessException
	{
		IMAccountLink imaccountLink = resourceService.findInSystemLinkById(login, IMAccountLink.class, imaccountLinkId);
		if (imaccountLink == null)
			throw new ResourceNotFoundBusinessException("ОМС с id=" + imaccountLinkId + " не принадлежит пользователю", IMAccountLink.class);

		return imaccountLink;
	}

	/**
	 * получение депозита клиента
	 * @param depositLinkId - идентификатор депозита
	 * @return
	 */
	public DepositLink getDeposit(Long depositLinkId) throws BusinessException
	{
		DepositLink link = resourceService.findInSystemLinkById(login, DepositLink.class, depositLinkId);
		if (link == null)
		{
			throw new ResourceNotFoundBusinessException("вклад c id=" + depositLinkId + " не пренадлежит пользователю", DepositLink.class);
		}
		return link;
	}

	public DepositLink getDepositByExternalId(String depositExternalId) throws BusinessException
	{
		return resourceService.findLinkByExternalId(DepositLink.class, depositExternalId, login);
	}

	public List<DepositLink> getDeposits() throws BusinessException, BusinessLogicException
	{
		return resourceService.getInSystemLinks(login, DepositLink.class);
	}

	public List<LoanLink> getLoans() throws BusinessException, BusinessLogicException
	{
		loadLoans();
		return resourceService.getInSystemLinks(login, LoanLink.class);
	}

	public List<LoanLink> getLoansAll() throws BusinessException, BusinessLogicException
	{
		loadLoans();
		return resourceService.getLinks(login, LoanLink.class);
	}

	public List<LoanLink> getLoans(final LoanFilter filter) throws BusinessException, BusinessLogicException
	{
		List<LoanLink> result = new ArrayList<LoanLink>(getLoans());
		CollectionUtils.filter(result, filter);
		return result;
	}

	public LoanLink getLoan(Long loanLinkId) throws BusinessException, BusinessLogicException
	{
		loadLoans();
		LoanLink link = resourceService.findInSystemLinkById(login, LoanLink.class, loanLinkId);
		if (link != null)
			return link;
		throw new ResourceNotFoundBusinessException("кредит c id=" + loanLinkId + " не принадлежит пользователю", LoanLink.class);
	}

	protected void loadLoans() throws BusinessException, BusinessLogicException
	{
		if (needLoadLoans)
		{
			try
			{
				clientResourceService.updateResources(person, false, Loan.class);
				needLoadLoans = false;
			}
			catch (InactiveExternalSystemException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	public LoanLink findLoan(String accountNumber) throws BusinessException
	{
		return resourceService.findInSystemLinkByNumber(login, ResourceType.LOAN, accountNumber);
	}

	public ErmbProductLink findProductByAlias(String alias) throws BusinessException, BusinessLogicException
	{
		if(StringHelper.isEmpty(alias))
			throw new IllegalArgumentException();

		ErmbProductLink link = findProductByAlias(alias, getAccounts());
		if (link == null)
			link = findProductByAlias(alias, getCards());
		if (link == null)
			link = findProductByAlias(alias, getLoans());

		return link;
	}

	private ErmbProductLink findProductByAlias(String alias, Collection<? extends ErmbProductLink> links)
	{
		for (ErmbProductLink link : links)
		{
			if (alias.equals(link.getErmbSmsAlias()))
				return link;

			if (alias.equals(link.getAutoSmsAlias()))
				return link;
		}

		return null;
	}

	public IMAccountLink findIMAccount(String imAccountNumber) throws BusinessException
	{
		return resourceService.findInSystemLinkByNumber(login, ResourceType.IM_ACCOUNT, imAccountNumber);
	}

	/**
	 * возврящает ссылку на счет депо по идентификатору
	 * @param depoAccountLinkId - id счета
	 * @return ссылка на счет депо
	 * @throws BusinessException
	 */
	public DepoAccountLink getDepoAccount(Long depoAccountLinkId) throws BusinessException
	{
		return resourceService.findInSystemLinkById(login, DepoAccountLink.class, depoAccountLinkId);
	}
	/**
	 * возврящает ссылку на страховой продукт по идентификатору
	 * @param linkId - id страхового продукта
	 * @return ссылка на страховой продукт
	 * @throws BusinessException
	 */
	public InsuranceLink getInsurance(Long linkId) throws BusinessException
	{
		return resourceService.findInSystemLinkById(login, InsuranceLink.class, linkId);
	}

	public DepoAccountLink findDepo(String accountNumber) throws BusinessException
	{
		return resourceService.findInSystemLinkByNumber(login, ResourceType.DEPO_ACCOUNT, accountNumber);
	}

	/**
	 * Возвращает список ссылок на счета депо, которые должны отображаться в системе
	 * @return List<DepoAccountLink> список ссылок
	 * @throws BusinessException
	 */
	public List<DepoAccountLink> getDepoAccounts() throws BusinessException, BusinessLogicException
	{
		return resourceService.getInSystemLinks(login, DepoAccountLink.class);
	}

	public List<DepoAccountLink> getDepoAccountsAll() throws BusinessException, BusinessLogicException
	{
		return resourceService.getLinks(login, DepoAccountLink.class);
	}

	public List<DepoAccountLink> getDepoAccounts(final DepoAccountFilter filter) throws BusinessException, BusinessLogicException
	{
		List<DepoAccountLink> result = new ArrayList<DepoAccountLink>(getDepoAccounts());
		CollectionUtils.filter(result, filter);
		return result;
	}

	public String getSkinUrl()
	{
		return skinUrl;
	}

	public void setSkinUrl(String skinUrl)
	{
		this.skinUrl = skinUrl;
	}

	public String getGlobalUrl() throws BusinessException
	{
		if (globalUrl==null)
			loadGlobalUrl();
		return globalUrl;
	}

	private void loadGlobalUrl() throws BusinessException
	{
		SkinsService skinsService = new SkinsService();
		Skin global = skinsService.getGlobalUrl();
		this.globalUrl = global.getUrl();
	}

	public Region getCurrentRegion() throws BusinessException
	{
		if (region == null)
		{
			 synchronized (lockRegion)
			 {
				 if (region == null)
				 {
					 Profile prof = getProfile();
					 if (prof != null)
					 {
						 region = RegionHelper.getRegionByCode(prof.getRegionCode());
					 }
				 }
			 }
		}
		return region;
	}

	public void setCurrentRegion(Region region)
	{
	 	this.region = region;
	}

	public List<AutoPaymentLink> getAutoPayments() throws BusinessException, BusinessLogicException
	{
		return resourceService.getInSystemLinks(login, AutoPaymentLink.class);
	}

	public AutoPaymentLink getAutoPayment(Long linkId) throws BusinessException
	{
		AutoPaymentLink link = resourceService.findLinkById(AutoPaymentLink.class, linkId);
		if (link == null)
			throw new ResourceNotFoundBusinessException("Автоплатеж с id=" + linkId + " не принадлежит пользователю.", AutoPaymentLink.class);
		return link;
	}

	/**
	 * Получить ссылку на ресурс по уникальному коду.
	 * @param code код ссылки на внешний ресурс
	 * @return ссылка или null.
	 */
	public ExternalResourceLink getByCode(String code) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(code))
		{
			return null;
		}
		if (code.startsWith(AccountLink.CODE_PREFIX))
		{
			return getAccount(AccountLink.getIdFromCode(code));
		}
		else if (code.startsWith(CardLink.CODE_PREFIX))
		{
			return getCard(CardLink.getIdFromCode(code));
		}
		else if (code.startsWith(LoanLink.CODE_PREFIX))
		{
			return getLoan(LoanLink.getIdFromCode(code));
		}
		else if (code.startsWith(DepositLink.CODE_PREFIX))
		{
			return getDeposit(DepositLink.getIdFromCode(code));
		}
		else if (code.startsWith(IMAccountLink.CODE_PREFIX))
		{
			return getImAccountLinkById(IMAccountLink.getIdFromCode(code));
		}
		else if (code.startsWith(DepoAccountLink.CODE_PREFIX))
		{
			return getDepoAccount(DepoAccountLink.getIdFromCode(code));
		}
		else if (code.startsWith(AutoPaymentLink.CODE_PREFIX))
		{
			return getAutoPayment(AutoPaymentLink.getIdFromCode(code));
		}
		throw new BusinessException("Неизвестный код внешнего ресурса " + code);
	}

	public List<IMAccountLink> getIMAccountLinks(IMAccountFilter filter) throws BusinessException, BusinessLogicException
	{
		List<IMAccountLink> imAccountLinks = getIMAccountLinks();
		List<IMAccountLink> result = new ArrayList<IMAccountLink>();

		for (IMAccountLink imAccountLink : imAccountLinks)
		{
			if (filter.accept(imAccountLink))
			{
				result.add(imAccountLink);
			}
		}
		return result;
	}

	@Deprecated
	//todo CHG059738 удалить
	public PaymentTemplateLink getMobileBankTemplateLinkById(Long linkId) throws BusinessException, BusinessLogicException
	{
		MobileBankService gateMobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		GatePaymentTemplateService gatePaymentTemplateService = GateSingleton.getFactory().service(GatePaymentTemplateService.class);
		try
		{
			// 1. Загрузим линк-на-шаблон-платежа
			PaymentTemplateLink link = resourceService.findLinkById(PaymentTemplateLink.class, linkId);
			if (link == null)
				throw new NotFoundException("Линк-на-шаблон SMS платежа МБ c id=" + linkId + " не найден");

			// 2. Удостоверимся в праве пользования указанного шаблона
			if (!login.getId().equals(link.getLoginId()))
				throw new AccessException("Линк-на-шаблон SMS платежа МБ c id=" + linkId + " не принадлежит пользователю");

			// 3. Загрузим шаблон по линку
			GatePaymentTemplate template = ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp() ?
					gateMobileBankService.getPaymentTemplate(link.getExternalId()) :
					gatePaymentTemplateService.getPaymentTemplate(link.getExternalId());
			if (template == null)
				throw new NotFoundException("Линк-на-шаблон SMS платежа МБ c id=" + linkId + " устарел");

			// 4. Убедимся в актуальности шаблона
			Card card = findCard(template.getCardNumber()).getCard();
			if (!template.isActive() || card.getCardState() != CardState.active)
				throw new NotActiveMobileBankTemplateException("Шаблон Мобильного банка не активен (не активна карта)");

			// 5. Подведём итог
			link.setTemplate(template);
			return link;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public List<AutoSubscriptionLink> getAutoSubscriptionLinks() throws BusinessException, BusinessLogicException
	{
		return resourceService.getLinks(login, AutoSubscriptionLink.class);
	}

	public AutoSubscriptionLink getAutoSubscriptionLink(Long id) throws BusinessException, BusinessLogicException
	{
		List<AutoSubscriptionLink> links = getAutoSubscriptionLinks();
		if (CollectionUtils.isEmpty(links))
			return null;

		for (AutoSubscriptionLink link : links)
		{
			if (link.getNumber().equals(Long.toString(id)))
				return link;
		}
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
        if(applicationConfig.getLoginContextApplication() == Application.PhizIA)
            throw new BusinessLogicException("Данный автоплатеж не принадлежит выбранному клиенту.");    
        else 
			throw new BusinessLogicException("Запрашиваемый автоплатеж не принадлежит Вам.");
	}
	
	public Long getCountNewLetters() throws BusinessException
	{
		Calendar now = Calendar.getInstance();

		boolean needSynch = mailSynchTime == null;
		needSynch = needSynch || mailSynchTime.getTimeInMillis() + PERSONDATA_LIFETIME < now.getTimeInMillis();
		if (needSynch)
		{
			mailCounter = mailService.countNewClientLetters(login);
			mailSynchTime = now;
		}
		return mailCounter;
	}

	public void decCountNewLetters()
	{
		mailCounter--;
	}

	public List<Long> getBannersList()
	{
		return bannersList;
	}

	public void setBannersList(List<Long> bannersList)
	{
		this.bannersList = bannersList;
	}

	public Map<String, WidgetDefinition> getWidgetDefinitions()
	{
		return Collections.unmodifiableMap(widgetDefinitions);
	}

	public void setWidgetDefinitions(Map<String, WidgetDefinition> widgetDefinitions)
	{
		this.widgetDefinitions = new HashMap<String, WidgetDefinition>(widgetDefinitions);
	}

	public WidgetDefinition getWidgetDefinition(String codename)
	{
		return widgetDefinitions.get(codename);
	}

	public Map<String, WebPage> getPages()
	{
		return Collections.unmodifiableMap(pages);
	}

	public void setPages(Map<String, WebPage> pages)
	{
		this.pages = new HashMap<String, WebPage>(pages);
	}

	public Map<String, WebPage> getSavedPages()
	{
		return Collections.unmodifiableMap(savedPages);
	}

	public void setSavedPages(Map<String, WebPage> savedPages)
	{
		this.savedPages = new HashMap<String, WebPage>(savedPages);
	}

	public WebPage getPage(String pageCodename)
	{
		return pages.get(pageCodename);
	}

	public Widget getWidget(String codename)
	{
		for (WebPage page : pages.values()) {
			Widget widget = page.getWidget(codename);
			if (widget != null)
				return widget;
		}
		return null;
	}

	public boolean isNeedLoadLoans()
	{
		return needLoadLoans;
	}

	public void setNeedLoadLoans(boolean needLoadLoans)
	{
		this.needLoadLoans = needLoadLoans;
	}

	public boolean isNeedLoadAccounts()
	{
		return needLoadAccounts;
	}

	public void setNeedLoadAccounts(boolean needLoadAccounts)
	{
		this.needLoadAccounts = needLoadAccounts;
	}

	public boolean isNeedLoadIMAccounts()
	{
		return needLoadIMAccounts;
	}

	public void setNeedLoadIMAccounts(boolean needLoadIMAccounts)
	{
		this.needLoadIMAccounts = needLoadIMAccounts;
	}

	public String getLogonSessionId()
	{
		synchronize();
		return logonSessionId;
	}

	/**
	 * Возвращает линк программы лояльности по логину клиента
	 * @return линк
	 * @throws BusinessException
	 */
	public LoyaltyProgramLink getLoyaltyProgram() throws BusinessException
	{
		return resourceService.getLoyaltyProgramLink(login);		
	}

	public ManagerInfo getManager()
	{
		return manager;
	}

	public void setManager(ManagerInfo manager)
	{
		this.manager = manager;
	}

	public boolean isHasOpenedDifLoan()
	{
		if (hasOpenedDifLoan != null)
			return hasOpenedDifLoan;
		// Если кредиты еще не получены из внешней системы, считаем, что их нет, чтобы на главной странице не запрашивались.
		if (needLoadLoans)
			return false;
		try
		{
			hasOpenedDifLoan = false;
			List<LoanLink> list = getLoans();
			for(LoanLink link : list)
			{
				if (!link.getLoanShortCut().getIsAnnuity() && link.getLoanShortCut().getState()!= LoanState.closed)
				{
					hasOpenedDifLoan = true;
					break;
				}
			}
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка кредитов", e);
			hasOpenedDifLoan = null;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка получения списка кредитов", e);
			hasOpenedDifLoan = null;
		}
		return hasOpenedDifLoan;
	}

	public void setHasOpenedDifLoan(Boolean hasOpenedDifLoan)
	{
		this.hasOpenedDifLoan = hasOpenedDifLoan;
	}

	public void setMenuLinkInfoList(List<MenuLinkInfo> linkInfoList)
	{
		this.linkInfoList = linkInfoList;
	}

	public List<MenuLinkInfo> getMenuLinkInfoList()
	{
		return linkInfoList;
	}

	public ExternalResourceLink getExternalResourceLink(Class clazz, Long id) throws BusinessException
	{
		return resourceService.findInSystemLinkById(getLogin(), clazz, id);
	}

	public boolean isTimeToCheckCSASession()
	{
		Calendar currentTime = Calendar.getInstance();
		if (csaSessionSynchTime.getTimeInMillis() + PERSONDATA_LIFETIME < currentTime.getTimeInMillis())
		{
			csaSessionSynchTime = currentTime;
			return true;
		}

		return false;
	}

	public boolean isShowOldBrowserMessage()
	{
		return showOldBrowserMessage;
	}

	public void setShowOldBrowserMessage(boolean showOldBrowserMessage)
	{
		this.showOldBrowserMessage = showOldBrowserMessage;
	}

	public boolean isWayCardsReloaded()
	{
		return wayCardsReloaded;
	}

	public void setWayCardsReloaded(boolean wayCardsReloaded)
	{
		this.wayCardsReloaded = wayCardsReloaded;
	}

	private Department getSafeDepartment(ActivePerson activePerson)
	{
		if (activePerson== null)
		{
			return null;
		}
		try
		{
			return departmentService.findById(activePerson.getDepartmentId());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения подраздлениея для клиента " + activePerson.getFullName(), e);
			return null;
		}
	}

	private Department getSafeTb(Department department)
	{
		if (department == null)
		{
			return null;
		}
		try
		{
			return departmentService.getTB(department);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения тербанка для клиента " + person.getFullName(), e);
			return null;
		}
	}

	public List<Long> getShownNotificationList()
	{
		return shownNotificationList;
	}

	public void setShownNotificationList(List<Long> shownNotificationList)
	{
		this.shownNotificationList = shownNotificationList;
	}

	public List<PersonalOfferNotification> getOfferNotificationList()
	{
		return offerNotificationList;
	}

	public void setOfferNotificationList(List<PersonalOfferNotification> offerNotificationList)
	{
		this.offerNotificationList = offerNotificationList;
	}

	public boolean getShowTrainingInfo()
	{
		return showTrainingInfo;
	}

	public void setShowTrainingInfo(boolean showTrainingInfo)
	{
		this.showTrainingInfo = showTrainingInfo;
	}

	public boolean getNewPersonalInfo()
	{
		return isNewPersonalInfo;
	}

	public void setNewPersonalInfo(boolean isNewPersonalInfo)
	{
		this.isNewPersonalInfo = isNewPersonalInfo;
	}

	public boolean getNewBillsToPay()
	{
		return isNewBillsToPay;
	}

	public void setNewBillsToPay(boolean isNewBillsToPay)
	{
		this.isNewBillsToPay = isNewBillsToPay;
	}

	public AvatarInfo getAvatarInfoByType(AvatarType type) throws BusinessException
	{
		if (avatarInfos.containsKey(type))
			return avatarInfos.get(type);

		if (getProfile().getAvatarImageId() == null)
			return null;

		AvatarInfo info = AvatarService.get().getAvatarInfo(type, getProfile().getAvatarImageId());
		avatarInfos.put(type, info);
		return info;
	}

	public void setAvatarInfo(AvatarInfo info)
	{
		avatarInfos.put(info.getType(), info);
	}

	public void deleteAvatarInfo(AvatarType type)
	{
		avatarInfos.remove(type);
	}

	public List<File> getMarkedDeleteFiles()
	{
		return Collections.unmodifiableList(markDeleteImages);
	}

	public void markDeleteFile(File file)
	{
		markDeleteImages.add(file);
	}

	public Properties getUserProperties()
	{
		return userProperties;
	}

	public void setUserProperties(Properties userProperties)
	{
		this.userProperties = userProperties;
	}
	public TutorialProfileState getStateProfilePromo()
	{
		return stateProfilePromo;
	}

	public void setStateProfilePromo(TutorialProfileState stateProfilePromo)
	{
		this.stateProfilePromo = stateProfilePromo;
	}

	public String getUserIdForSocialNet(String type) throws BusinessException
	{
		if (socialNetIds == null)
		{
			socialNetIds = new HashMap<String, PersonSocialID>();
			socialNetIds = profileService.getSocialIds(login);
		}

		if (socialNetIds.containsKey(type))
			return socialNetIds.get(type).getSocialNetworkId();
		return null;
	}

	public InvoicesSubscriptionsPromoMode getPromoMode() throws BusinessException
	{
		if (this.promoMode == null)
			this.promoMode = invoiceSubscriptionsService.isInvoicesExist(login.getId());
		return this.promoMode;
	}

	public void setPromoMode(InvoicesSubscriptionsPromoMode mode)
	{
		promoMode = mode;
	}

	public Set<String> getClientArrestedCards()
	{
		return Collections.unmodifiableSet(clientArrestedCards);
	}

	public void setClientArrestedCards(Set<String> cardNumbers)
	{
		this.clientArrestedCards = new HashSet<String>(cardNumbers);
	}

	public synchronized ClientInvoiceData getClientInvoiceData(Set<String> errorCollector, boolean withRefresh) throws BusinessException
	{
		if (withRefresh && clientInvoiceData.needRefresh())
		{
			clientInvoiceData.refreshInvoices(errorCollector);
		}
		return clientInvoiceData;
	}

	public boolean isIncognito()
	{
		return incognito;
	}

	public void setIncognito(boolean incognito)
	{
		this.incognito = incognito;
	}

	public synchronized Collection<String> getPhones(Boolean alternative, Boolean checkPhonesInERMB) throws BusinessLogicException, BusinessException
	{
		int alternativeFlag = alternative == null ? NOT_SET_ALTERNATIVE_PHONES : (alternative ? ALTERNATIVE_PHONES : NOT_ALTERNATIVE_PHONES);

		if (userPhones[alternativeFlag] == null)
		{
			userPhones[alternativeFlag] = new HashSet<String>(MobileBankManager.getInfoPhones(getLogin(), alternative, checkPhonesInERMB));
		}

		return userPhones[alternativeFlag];
	}

	public synchronized Collection<String> getPhones(Boolean alternative) throws BusinessLogicException, BusinessException
	{
		return getPhones(alternative, Boolean.TRUE);
	}

	/**
	 * Получение списка номеров телефонов клиента.
	 *
	 * @param login логин.
	 * @param alternative альтернативные номера.
	 * @return список номеров телефонов.
	 */
	public static Collection<String> getPhones(Login login, Boolean alternative) throws BusinessException, BusinessLogicException
	{
		PersonData personData = null;
		if (PersonContext.isAvailable())
		{
			personData = PersonContext.getPersonDataProvider().getPersonData();
		}
		else
		{
			personData = new StaticPersonDataForEmployee(new PersonService().findByLogin(login));
		}

		if (personData.getLogin().getId().equals(login.getId()))
			return personData.getPhones(alternative);
		else
			throw new BusinessException("Логины не совпадают для данных персоны");
	}

	public void setActiveCreditViewBlock(Boolean flag)
	{
		isActiveCreditViewBlock = flag;
	}

	public Boolean getActiveCreditViewBlock()
	{
		return isActiveCreditViewBlock;
	}

	public Boolean getMustUpdateCreditReport()
	{
		if (mustUpdateCreditReport != null)
			return mustUpdateCreditReport;
		PersonCreditProfile creditProfile = null;
		try
		{
			creditProfile = creditProfileService.findByPerson(getPerson());
		}
		catch (BusinessException e)
		{
			return Boolean.FALSE;
		}
		if (creditProfile.getLastReport() == null)
			return Boolean.FALSE;
		Calendar newDate = Calendar.getInstance();
		newDate.add(Calendar.DATE, -CREDIT_REPORT_FRESH_PERIOD);
		mustUpdateCreditReport = newDate.after(creditProfile.getLastReport());
		return mustUpdateCreditReport;
	}

	public void setMustUpdateCreditReport(Boolean mustUpdateCreditReport)
	{
		this.mustUpdateCreditReport = mustUpdateCreditReport;
	}

	public boolean isTrustedContactConfirmed()
	{
		return trustedContactConfirmed;
	}

	public void setTrustedContactConfirmed(boolean trustedContactConfirmed)
	{
		this.trustedContactConfirmed = trustedContactConfirmed;
	}

    public boolean isCardDetailInfoUpdated(String externalId)
    {
        if (cardDetailInfoUpdated.contains(externalId))
            return true;
        cardDetailInfoUpdated.add(externalId);
        return false;
    }

    private void clearCardDetailInfoUpdated()
    {
        cardDetailInfoUpdated.clear();
	}

	public Calendar getPromoBlockUntil() throws BusinessException
	{
		if (!checkedPromoBlock)
		{
			checkedPromoBlock = true;
			UserCounter userCounter = userCountersService.findByLoginId(getLogin().getId(), CounterType.PROMO_CODE);
			if (userCounter != null)
				promoBlockUntil = userCounter.getBlockUntil();
		}
		return promoBlockUntil;
	}

	public void setPromoBlockUntil(Calendar promoBlockUntil)
	{
		this.promoBlockUntil = promoBlockUntil;
	}

	public List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, Boolean isViewed) throws BusinessException
	{
		return offerService.getLoanCardOfferByPersonData(number, getPerson(), isViewed);
	}

	public List<LoanOffer> getLoanOfferByPersonData(Integer number, Boolean isViewed) throws BusinessException
	{
		return offerService.getLoanOfferByPersonData(number, getPerson(), isViewed);
	}

	public  List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes) throws BusinessException
	{
		return offerService.getCardOfferCompositProductCondition(offerTypes, this.getPerson());
	}

	public ConditionProductByOffer findConditionProductByOffer(Long conditionId) throws BusinessException
	{
		return offerService.findConditionProductByOffer(this.getPerson(), conditionId);
	}

	public boolean existCreditCardOffer(Long conditionId) throws BusinessException
	{
		return offerService.existCreditCardOffer(this.getPerson(), conditionId);
	}

	public ConditionProductByOffer findConditionProductByOfferIdAndConditionId(OfferId loanCardOfferId, Long conditionId) throws BusinessException
	{
		return offerService.findConditionProductByOfferIdAndConditionId(loanCardOfferId, conditionId);
	}

	public Long findConditionIdByOfferId(OfferId loanCardOfferId) throws BusinessException
	{
		return offerService.findConditionIdByOfferId(loanCardOfferId);
	}

	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return offerService.findLoanOfferById(offerId);
	}

	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return offerService.findLoanCardOfferById(offerId);
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		switch (feedbackType)
		{
			case INFORM:
				synchronized (informedLoanOfferIds)
				{
					List<OfferId> notInformedOfferIds = new ArrayList<OfferId>(loanOfferIds.size());

					for(OfferId offerId : loanOfferIds)
					{
						if (!informedLoanOfferIds.contains(offerId.toString()))
						{
							notInformedOfferIds.add(offerId);
						}
					}

					if (!notInformedOfferIds.isEmpty())
					{
						offerService.sendLoanOffersFeedback(notInformedOfferIds, feedbackType);
						for (OfferId offerId : notInformedOfferIds)
						{
							informedLoanOfferIds.add(offerId.toString());
						}
					}
				}
				break;
			case PRESENTATION:
				offerService.sendLoanOffersFeedback(loanOfferIds, feedbackType);
				break;
		}
	}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		switch (feedbackType)
		{
			case INFORM:
				synchronized (informedLoanCardOfferIds)
				{
					List<OfferId> notInformedOfferIds = new ArrayList<OfferId>(loanCardOfferIds.size());

					for(OfferId offerId : loanCardOfferIds)
					{
						if (!informedLoanCardOfferIds.contains(offerId.toString()))
						{
							notInformedOfferIds.add(offerId);
						}
					}

					if (!notInformedOfferIds.isEmpty())
					{
						offerService.sendLoanCardOffersFeedback(notInformedOfferIds, feedbackType);
						for (OfferId offerId : notInformedOfferIds)
						{
							informedLoanCardOfferIds.add(offerId.toString());
						}
					}
				}
				break;
			default:
				offerService.sendLoanCardOffersFeedback(loanCardOfferIds, feedbackType);
				break;
		}
	}

	public boolean isOfferReceivingInProgress() throws BusinessException
	{
		return offerService.isOfferReceivingInProgress(getPerson());
	}

	public void updateLoanOfferAsUsed(OfferId offerId) throws BusinessException
	{
		offerService.markLoanOfferAsUsed(offerId);
	}

	public void updateLoanCardOfferAsUsed(OfferId offerId) throws BusinessException
	{
		offerService.markLoanCardOfferAsUsed(offerId);
	}

	public LoanOffer getLoanOfferForMainPage() throws BusinessException
	{
		return offerService.getLoanOfferForMainPage(getPerson());
	}

	public LoanCardOffer getLoanCardOfferForMainPage() throws BusinessException
	{
		return offerService.getLoanCardOfferForMainPage(getPerson());
	}

	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		if (feedbackType == FeedbackType.INFORM)
		{
			synchronized (informedLoanOfferIds)
			{
				if (informedLoanOfferIds.contains(offerId.toString()))
					return true;
			}
		}
		return offerService.isFeedbackForLoanOfferSend(offerId, feedbackType);
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		if (feedbackType == FeedbackType.INFORM)
		{
			synchronized (informedLoanCardOfferIds)
			{
				if (informedLoanCardOfferIds.contains(offerId.toString()))
					return true;
			}
		}
		return offerService.isFeedbackForLoanCardOfferSend(offerId, feedbackType);
	}

	public LoanCardOffer getNewLoanCardOffer() throws BusinessException
	{
		return offerService.getNewLoanCardOffer(getPerson());
	}

	public void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException
	{
		offerService.updateLoanCardOfferRegistrationDate(offerId);
	}

	public void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException
	{
		offerService.updateLoanCardOfferTransitionDate(offerId);
	}

	public Boolean isMB()
	{
		if (isMB == null)
		{
			try
			{
				isMB = PersonHelper.isMBConnected(person, getCardsAll());
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при получении кардлинков клиента", e);
			}
			catch (BusinessLogicException e)
			{
				log.error("Ошибка при получении кардлинков клиента", e);
			}
			catch (InactiveExternalSystemException e)
			{
				isMB = false;
				log.error("Неактивна внешняя система.", e);
			}
		}
		return isMB;
	}

	public Long getCSAProfileId()
	{
		return null;
	}

	public Long getGuestProfileId()
	{
		return null;
	}

	public BusinessDocumentOwner createDocumentOwner()
	{
		return new ClientBusinessDocumentOwner(person);
	}

	public boolean isGuest()
	{
		return false;
	}

	public String getGuestLoginAlias()
	{
		return null;
	}

	public GuestType getGuestType()
	{
		return null;
	}

	public boolean isLoanCardClaimAvailable()
	{
		return loanCardClaimAvailable;
	}

	public void setLoanCardClaimAvailable(boolean loanCardClaimAvailable)
	{
		this.loanCardClaimAvailable = loanCardClaimAvailable;
	}

	public OfferPrior getOfferPrior(boolean incrementCounter) throws BusinessException
	{
		if (offerPrior == null || offerPrior.isEmpty())
		{
			if (PermissionUtil.impliesService("EtsmOffer"))
			{
				offerPrior = ETSMOfferHelper.getActualOffer(person, incrementCounter);
			}
		}
		else
		{
			if (incrementCounter && !offerPrior.isEmpty() && !offerPrior.getCounterUpdated())
			{
				Long offerVisibilityCounter = offerPrior.getVisibilityCounter();
				if (offerPrior instanceof OfferOfficePrior)
				{
					offerPriorWebService.updateOfferOfficePriorVisibleCounter(offerPrior.getId());
				}  else {
					offerPrior.setVisibilityCounter(offerVisibilityCounter + 1);
					offerPrior.setCounterUpdated(true);
					service.update(offerPrior);
				}
			}
		}
		return offerPrior;
	}

	public LoanOffer getLoanOffer(OfferId offerId) throws BusinessException
	{
		return offerService.findLoanOfferById(offerId);
	}
}
