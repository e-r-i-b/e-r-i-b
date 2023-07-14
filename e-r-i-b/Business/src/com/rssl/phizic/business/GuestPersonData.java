package com.rssl.phizic.business;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.GuestBusinessDocumentOwner;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.offers.*;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.TutorialProfileState;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.web.WebPage;
import com.rssl.phizic.business.web.Widget;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.basket.InvoicesSubscriptionsPromoMode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.employee.ManagerInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

import java.io.File;
import java.util.*;

/**
 * @author Nady
 * @ created 17.02.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данные гостя
 */
public class GuestPersonData implements PersonData
{
	private static final BusinessDocumentService documentService  = new BusinessDocumentService();
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final DepartmentService departmentService = new DepartmentService();

	private final Calendar creationDate = Calendar.getInstance();
	private final GuestPerson guestPerson;
	private final GuestLogin guestLogin;

	private boolean trustedContactConfirmed;
	private Calendar promoBlockUntil;
	private boolean isActiveCreditViewBlock;
	private boolean incognito;
	private String  skinUrl;
	private List<MenuLinkInfo> linkInfoList;
	private List<Long> bannersList;
	private List<Long> notificationList;
	private boolean wayCardsReloaded;
	private boolean showTrainingInfo;
	private boolean isNewPersonalInfo;
	private boolean isNewBillsToPay;
	private Properties userProperties;
	private TutorialProfileState stateProfilePromo;
	private Boolean ermbSupported;
	private List<PersonalOfferNotification> offerNotificationList;
	private Map<String, WidgetDefinition> widgetDefinitions;
	private Map<String, WebPage> pages = Collections.emptyMap();
	private Map<String, WebPage> savedPages = Collections.emptyMap();
	private ManagerInfo manager;
	private boolean needLoadLoans = false;
	private boolean needLoadAccounts = false;
	private boolean needLoadIMAccounts = false;
	private boolean showOldBrowserMessage;
	private Boolean hasOpenedDifLoan;

	private Boolean MB;
	private Long csaProfileId;
	private Long guestProfileId;
	private String guestLoginAlias;
	private GuestType guestType = GuestType.UNREGISTERED;
	private String documentNumber;
	private String documentSeries;

	private final OfferService offerService;

	//Нужно ли заполнить контекст дополнительными данныи
	private boolean needFillContextAdditionData = true;

	private final List<CardLink> cardLinks = new ArrayList<CardLink>();

	private boolean loanCardClaimAvailable;

	//Подразделение гостя
	private Department department;
	//Тербанк гостя
	private Department tb;

	private Region region;

	/**
	 * ctor
	 * @param guestLogin
	 * @param guestPerson
	 * @param connectMB
	 * @param csaProfileId
	 * @param guestProfileId
	 * @param guestLoginAlias
	 */
	public GuestPersonData(GuestLogin guestLogin, GuestPerson guestPerson, Boolean connectMB, Long csaProfileId, Long guestProfileId, String guestLoginAlias)
	{
		this(guestLogin, guestPerson, guestLoginAlias, guestProfileId);

		needFillContextAdditionData = connectMB == null || csaProfileId == null;

		this.csaProfileId = csaProfileId;
		setHasPhoneInMB(connectMB);
	}

	/**
	 * Конструктор для инициализации контекста при входе по номеру телефона и одноразовому паролю
	 * @param guestLogin Гостевой логин
	 * @param guestPerson Персона
	 * @param guestLoginAlias Логин гостя, по которому он заходит в СЦА
	 * @param guestProfileId ИД гостевого профиля
	 */
	public GuestPersonData(GuestLogin guestLogin, GuestPerson guestPerson, String guestLoginAlias, Long guestProfileId)
	{
		if (guestLogin == null)
			throw new IllegalArgumentException("Не указан guestLogin");
		if (guestPerson == null)
			throw new IllegalArgumentException("Не указан guestPerson");
		this.guestLogin = guestLogin;
		this.guestPerson = guestPerson;
		this.guestLoginAlias = guestLoginAlias;
		this.guestProfileId = guestProfileId;

		initDepartmentAndTB();

		Application currentApplication = ApplicationInfo.getCurrentApplication();
		if(currentApplication == Application.Scheduler)
		{
			offerService = new CompositeOffersService(false, false);
		}
		else
		{
			AuthModule authModule = AuthModule.getAuthModule();
			boolean offersV1Available = authModule.implies(new ServicePermission("LoanOfferView", false));
			//до 9 версии mApi новая версия заявок недоступна
			boolean legacyMApi = MobileApiUtil.isMobileApiLT(MobileAPIVersions.V9_00);
			boolean offersV2Available = !legacyMApi && authModule.implies(new ServicePermission("GetPreapprovedOffersService", false));
			offerService = new ETSMLoanOfferFilterService(new CompositeOffersService(offersV1Available, offersV2Available));

			try
			{
				if (CardsUtil.hasClientActiveCreditCard(getCardsAll()))
					loanCardClaimAvailable = false;
				else
				{
					ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
					if (clientConfig.isDoLoanCardClaimExistenceRequest())
						loanCardClaimAvailable = !documentService.isExistLoanCardClaim(guestLogin);
				}
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при попытке определить доступность создания заявки на кредитную карту для клиента", e);
			}
			catch (BusinessLogicException e)
			{
				log.error("Ошибка при попытке определить доступность создания заявки на кредитную карту для клиента", e);
			}
		}
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public ActivePerson getPerson()
	{
		return guestPerson;
	}

	public Login getLogin()
	{
		return guestLogin;
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
		return null;
	}

	public boolean isPersonBlocked()
	{
		return false;
	}

	public boolean isFraud()
	{
		return false;
	}

	public void needPersonUpdate()
	{

	}

	public void reset(Calendar nextUpdateTime){}

	public void setBlocked(boolean blocked){}

	public void setFraud(boolean fraud){}

	public Boolean isErmbSupported()
	{
		return ermbSupported;
	}

	public void setErmbSupported(Boolean ermbSupported)
	{
		this.ermbSupported = ermbSupported;
	}

	public List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<AccountLink>();
	}

	public List<AccountLink> getAccounts(AccountFilter accountFilter) throws BusinessException, BusinessLogicException
	{
		return new ArrayList<AccountLink>();
	}

	public List<AccountTarget> getAccountTargets() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<AccountTarget>();
	}

	public void setCards(List<CardLink> cardLinks)
	{
		this.cardLinks.clear();
		this.cardLinks.addAll(cardLinks);
	}

	public List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		return Collections.unmodifiableList(cardLinks);
	}

	public List<CardLink> getCardsAll() throws BusinessException, BusinessLogicException
	{
		return getCards();
	}

	public List<CardLink> getCards(CardFilter cardFilter) throws BusinessException, BusinessLogicException
	{
		List<CardLink> result = new ArrayList<CardLink>(getCards());
		CollectionUtils.filter(result, cardFilter);
		return result;
	}

	public List<DepositLink> getDeposits() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<DepositLink>();
	}

	public List<LoanLink> getLoans() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<LoanLink>();
	}

	public List<LoanLink> getLoans(LoanFilter filter) throws BusinessException, BusinessLogicException
	{
		return new ArrayList<LoanLink>();
	}

	public List<IMAccountLink> getIMAccountLinks() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<IMAccountLink>();
	}

	public List<DepoAccountLink> getDepoAccounts() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<DepoAccountLink>();
	}

	public List<DepoAccountLink> getDepoAccounts(DepoAccountFilter filter) throws BusinessException, BusinessLogicException
	{
		return new ArrayList<DepoAccountLink>();
	}

	public List<AutoPaymentLink> getAutoPayments() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<AutoPaymentLink>();
	}

	public List<InsuranceLink> getInsuranceLinks(BusinessProcess businessProcess) throws BusinessException
	{
		return new ArrayList<InsuranceLink>();
	}

	public List<InsuranceLink> getInsuranceLinks() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<InsuranceLink>();
	}

	public InsuranceLink getInsurance(Long linkId) throws BusinessException
	{
		return null;
	}

	public AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public CardLink getCard(Long cardLinkId) throws BusinessException
	{
		return null;
	}

	public DepositLink getDeposit(Long depositLinkId) throws BusinessException
	{
		return null;
	}

	public DepositLink getDepositByExternalId(String depositExternalId) throws BusinessException
	{
		return null;
	}

	public LoanLink getLoan(Long loanLinkId) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public DepoAccountLink getDepoAccount(Long depoAccountLinkId) throws BusinessException
	{
		return null;
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
		return null;
	}

	public Region getCurrentRegion() throws BusinessException
	{
		return region;
	}

	public void setCurrentRegion(Region region)
	{
		this.region = region;
	}

	public IMAccountLink getImAccountLinkById(Long id) throws BusinessException
	{
		return null;
	}

	public AutoPaymentLink getAutoPayment(Long autoPaymentLinkId) throws BusinessException
	{
		return null;
	}

	public DepoAccountLink findDepo(String accountNumber) throws BusinessException
	{
		return null;
	}

	public LoyaltyProgramLink getLoyaltyProgram() throws BusinessException
	{
		return null;
	}

	public List<MenuLinkInfo> getMenuLinkInfoList()
	{
		return linkInfoList;
	}

	public ExternalResourceLink getExternalResourceLink(Class clazz, Long id) throws BusinessException
	{
		return null;
	}

	public void setMenuLinkInfoList(List<MenuLinkInfo> linkInfoList)
	{
		this.linkInfoList = linkInfoList;
	}

	public void setNeedReloadAccounts(){}

	public void setNeedReloadIMAccounts(){}

	public void setNeedReloadCards(){}

	public AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public CardLink findCard(String cardNumber) throws BusinessException
	{
		return null;
	}

	public LoanLink findLoan(String accountNumber) throws BusinessException
	{
		return null;
	}

	public ErmbProductLink findProductByAlias(String alias) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public IMAccountLink findIMAccount(String imAccountNumber) throws BusinessException
	{
		return null;
	}

	public ExternalResourceLink getByCode(String code) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public List<IMAccountLink> getIMAccountLinks(IMAccountFilter filter) throws BusinessException, BusinessLogicException
	{
		return new ArrayList<IMAccountLink>();
	}

	public PaymentTemplateLink getMobileBankTemplateLinkById(Long linkId) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public List<AutoSubscriptionLink> getAutoSubscriptionLinks() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<AutoSubscriptionLink>();
	}

	public AutoSubscriptionLink getAutoSubscriptionLink(Long id) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public Long getCountNewLetters() throws BusinessException
	{
		return null;
	}

	public void decCountNewLetters(){}

	public List<Long> getBannersList()
	{
		return bannersList;
	}

	public void setBannersList(List<Long> bannersList)
	{
		this.bannersList = bannersList;
	}

	public List<Long> getShownNotificationList()
	{
		return notificationList;
	}

	public void setShownNotificationList(List<Long> notificationList)
	{
		this.notificationList = notificationList;
	}

	public List<PersonalOfferNotification> getOfferNotificationList()
	{
		return offerNotificationList;
	}

	public void setOfferNotificationList(List<PersonalOfferNotification> offerNotificationList)
	{
		this.offerNotificationList = offerNotificationList;
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
		return null;
	}

	public ManagerInfo getManager()
	{
		return manager;
	}

	public void setManager(ManagerInfo manager)
	{
		this.manager = manager;
	}

	public void setHasOpenedDifLoan(Boolean hasOpenedDifLoan)
	{
		this.hasOpenedDifLoan = hasOpenedDifLoan;
	}

	public boolean isHasOpenedDifLoan()
	{
		return hasOpenedDifLoan;
	}

	public boolean isTimeToCheckCSASession()
	{
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

	public List<AccountLink> getAccountsAll() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<AccountLink>();
	}

	public List<LoanLink> getLoansAll() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<LoanLink>();
	}

	public List<IMAccountLink> getIMAccountLinksAll() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<IMAccountLink>();
	}

	public List<DepoAccountLink> getDepoAccountsAll() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<DepoAccountLink>();
	}

	public List<SecurityAccountLink> getSecurityAccountLinks() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<SecurityAccountLink>();
	}

	public List<SecurityAccountLink> getSecurityAccountLinksAll() throws BusinessException, BusinessLogicException
	{
		return new ArrayList<SecurityAccountLink>();
	}

	public SecurityAccountLink getSecurityAccountLink(Long linkId) throws BusinessException
	{
		return null;
	}

	public boolean isWayCardsReloaded()
	{
		return wayCardsReloaded;
	}

	public void setWayCardsReloaded(boolean wayCardsReloaded)
	{
		this.wayCardsReloaded = wayCardsReloaded;
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
		return null;
	}

	public void setAvatarInfo(AvatarInfo info){}

	public void deleteAvatarInfo(AvatarType type){}

	public List<File> getMarkedDeleteFiles()
	{
		return Collections.emptyList();
	}

	public void markDeleteFile(File file){}

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
		return null;
	}

	public InvoicesSubscriptionsPromoMode getPromoMode() throws BusinessException
	{
		return null;
	}

	public void setPromoMode(InvoicesSubscriptionsPromoMode mode){}

	public Set<String> getClientArrestedCards()
	{
		return Collections.emptySet();
	}

	public void setClientArrestedCards(Set<String> clientArrestedCards){}

	public ClientInvoiceData getClientInvoiceData(Set<String> errorCollector, boolean withRefresh) throws BusinessException
	{
		return null;
	}

	public void setIncognito(boolean incognito)
	{
		this.incognito = incognito;
	}

	public boolean isIncognito()
	{
		return incognito;
	}

	public Collection<String> getPhones(Boolean alternative) throws BusinessLogicException, BusinessException
	{
		return getPhones(alternative, Boolean.TRUE);
	}

	public Collection<String> getPhones(Boolean alternative, Boolean checkPhoneInERMB) throws BusinessLogicException, BusinessException
	{
		ArrayList<String> phones = new ArrayList<String >();
		phones.add(guestLogin.getAuthPhone());
		return phones;
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
		return false;
	}

	public void setMustUpdateCreditReport(Boolean mustUpdateCreditReport){}

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
		return false;
	}

	public Calendar getPromoBlockUntil()
	{
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

	public List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes) throws BusinessException
	{
		return offerService.getCardOfferCompositProductCondition(offerTypes, this.getPerson());
	}

	public ConditionProductByOffer findConditionProductByOffer(Long conditionId) throws BusinessException
	{
		return null;
	}

	public boolean existCreditCardOffer(Long conditionId) throws BusinessException
	{
		return false;
	}

	public ConditionProductByOffer findConditionProductByOfferIdAndConditionId(OfferId loanCardOfferId, Long conditionId) throws BusinessException
	{
		return null;
	}

	public Long findConditionIdByOfferId(OfferId loanCardOfferId) throws BusinessException
	{
		return null;
	}

	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return offerService.findLoanOfferById(offerId);
	}

	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return offerService.findLoanCardOfferById(offerId);
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException{}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException{}

	public boolean isOfferReceivingInProgress() throws BusinessException
	{
		return false;
	}

	public void updateLoanOfferAsUsed(OfferId offerId) throws BusinessException {}

	public void updateLoanCardOfferAsUsed(OfferId offerId) throws BusinessException {}

	public LoanOffer getLoanOfferForMainPage() throws BusinessException
	{
		return null;
	}

	public LoanCardOffer getLoanCardOfferForMainPage() throws BusinessException
	{
		return null;
	}

	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return false;
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return false;
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
		return BooleanUtils.toBoolean(MB);
	}

	/**
	 * Установка значения "Есть ли телефон клиента в МБ"  в контекст и персону
	 * @param hasPhoneInMB Флаг
	 */
	public void setHasPhoneInMB(Boolean hasPhoneInMB)
	{
		this.MB = hasPhoneInMB;
		this.guestPerson.setHaveMBKConnection(BooleanUtils.isTrue(MB));
	}

	public Long getCSAProfileId()
	{
		return csaProfileId;
	}

	/**
	 * Установка значения индентификатора учётной записи в ЦСА
	 * @param csaProfileId Номер учётной записи
	 */
	public void setCsaProfileId(Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;
	}

	public Long getGuestProfileId()
	{
		return guestProfileId;
	}

	public BusinessDocumentOwner createDocumentOwner()
	{
		return new GuestBusinessDocumentOwner(guestLogin, guestPerson);
	}

	public boolean isGuest()
	{
		return true;
	}

	public String getGuestLoginAlias()
	{
		return guestLoginAlias;
	}

	/**
	 * Нужно ли заполнить контекст дополнительной информацией:
	 * <ol>
	 *     <li>Подключён ли телефон к МБ</li>
	 *     <li>Есть ли в ЦСА основная учётная запись</li>
	 * </ol>
	 * @return Да, если нужно. Нет, в противном случае
	 */
	public boolean isNeedFillContextDataAboutAccountCSAAndMB()
	{
		return needFillContextAdditionData;
	}

	/**
	 * Установка флага, что не требуется заполнить контекст дополнительными данными
	 */
	public void markThatContextFillDataAboutAccountCSAAndMB()
	{
		this.needFillContextAdditionData = false;
	}

	public GuestType getGuestType()
	{
		return guestType;
	}

	public void setGuestType(GuestType guestType)
	{
		this.guestType = guestType;
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
		return null;
	}

	public LoanOffer getLoanOffer(OfferId offerId) throws BusinessException
	{
		return null;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getDocumentSeries()
	{
		return documentSeries;
	}

	public void setDocumentSeries(String documentSeries)
	{
		this.documentSeries = documentSeries;
	}

	/**
	 * Инициализация департамента и ТБ гостя
	 */
	private void initDepartmentAndTB()
	{
		if (guestPerson.getDepartmentId() == null)
		{
			return;
		}

		try
		{
			department = departmentService.findById(guestPerson.getDepartmentId());
			if (department != null)
			{
				tb = departmentService.getTB(department);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при инициализации департамента и ТБ гостя", e);
		}
	}
}
