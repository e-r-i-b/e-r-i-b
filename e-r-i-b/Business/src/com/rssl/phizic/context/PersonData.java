package com.rssl.phizic.context;

import com.rssl.common.forms.DocumentException;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ClientInvoiceData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.business.persons.ActivePerson;
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
import com.rssl.phizic.common.types.basket.InvoicesSubscriptionsPromoMode;
import com.rssl.phizic.gate.employee.ManagerInfo;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:21:04 */

/**
 * �������� ������������ (�������)
 */
public interface PersonData extends Serializable
{
	/**
	 * @return ���� �������� ���������
	 */
	Calendar getCreationDate();

	/**
	 * @return ������������
	 */
    ActivePerson getPerson();

	/**
	 * @return ����� ������������
	 */
	Login getLogin();

	/**
	 * @return ������������ ������������
	 */
	Department getDepartment();

	/**
	 * @return �� ������������
	 */
	Department getTb();
	/**
	 * @return ������� ������������
	 */
	Profile getProfile() throws BusinessException;

	/**
	 * @return true, ���� ������������ ������������
	 */
	boolean isPersonBlocked();

	/**
	 * @return true, ���� ������������ ��������
	 */
	boolean isFraud();

	/**
	 * ������������� ������� ������������� �������� ������ ������� �� ����� ������ ������ reset.
	 */
	void needPersonUpdate();

	/**
	 * ��������� ������������ ������ ����.
	 */
	void reset(Calendar nextUpdateTime);

	/**
	 * ��������� �������� ���������� ������������.
	 * @param blocked - ������� ���������� ������������.
	 */
	public void setBlocked(boolean blocked);

	/**
	 * ��������� �������� ���������� ������������ � �������������.
	 * @param fraud - ������� ������������� ������������ � �������������.
	 */
	public void setFraud(boolean fraud);

	/**
	 * @return true, ���� � ������� ���� ����-�������; false, ���� ���; null, ���� ������� �� ���������� (������� ������� ����������)
	 */
	Boolean isErmbSupported();

	/**
	 * ��������� �������� ������� ����-������� � �������
	 * @param ermbSupported - ������� ������� � ������� ������� ����
	 */
	public void setErmbSupported(Boolean ermbSupported);

    List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException;
	List<AccountLink> getAccounts(AccountFilter accountFilter) throws BusinessException, BusinessLogicException;
	List<AccountTarget> getAccountTargets() throws BusinessException, BusinessLogicException;
    List<CardLink> getCards() throws BusinessException, BusinessLogicException;
	List<CardLink> getCards(CardFilter cardFilter) throws BusinessException, BusinessLogicException;
	List<DepositLink> getDeposits() throws BusinessException, BusinessLogicException;
	List<LoanLink> getLoans() throws BusinessException, BusinessLogicException;
	List<LoanLink> getLoans(LoanFilter filter) throws BusinessException, BusinessLogicException;
	List<IMAccountLink> getIMAccountLinks() throws BusinessException, BusinessLogicException;
	List<DepoAccountLink> getDepoAccounts() throws BusinessException, BusinessLogicException;
	List<DepoAccountLink> getDepoAccounts(DepoAccountFilter filter) throws BusinessException, BusinessLogicException;
	List<AutoPaymentLink> getAutoPayments() throws BusinessException, BusinessLogicException;
	List<InsuranceLink> getInsuranceLinks(BusinessProcess businessProcess) throws BusinessException;
	List<InsuranceLink> getInsuranceLinks() throws BusinessException, BusinessLogicException;
	InsuranceLink getInsurance(Long linkId) throws BusinessException;

	AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException;
    CardLink getCard(Long cardLinkId) throws BusinessException;
	DepositLink getDeposit(Long depositLinkId) throws BusinessException;
	DepositLink getDepositByExternalId(String depositExternalId) throws BusinessException;
	LoanLink getLoan(Long loanLinkId) throws BusinessException, BusinessLogicException;
	DepoAccountLink getDepoAccount(Long depoAccountLinkId) throws BusinessException;
	String getSkinUrl();
	void setSkinUrl(String skinUrl);
	String getGlobalUrl() throws BusinessException;

	/**
	 * ��������� �������� ������� �������.
	 * @return ������. � ������ ���� � ������� �� ����� ������, �� ������� null.
	 * @throws BusinessException
	 */
	Region getCurrentRegion() throws BusinessException;

	/**
	 * ��������� �������� ������� �������.
	 * @param region ������ �������
	 */
	void setCurrentRegion(Region region);

	IMAccountLink getImAccountLinkById(Long id) throws BusinessException;
	AutoPaymentLink getAutoPayment(Long autoPaymentLinkId) throws BusinessException;
	DepoAccountLink findDepo(String accountNumber) throws BusinessException;
	LoyaltyProgramLink getLoyaltyProgram() throws BusinessException;
	List<MenuLinkInfo> getMenuLinkInfoList();
	ExternalResourceLink getExternalResourceLink(Class clazz, Long id) throws BusinessException;

	public void setMenuLinkInfoList(List<MenuLinkInfo> linkInfoList);
	/**
	 * ��������� �������� ������������� ���������� ��������� ������ �� ����
	 * � ������� ������
	 */
	public void setNeedReloadAccounts();

	/**
	 * ��������� �������� ������������� ���������� ��������� ������������� ������
	 */
	public void setNeedReloadIMAccounts();

	/**
	 * ������������� ������� ������������� ���������� ��������� ����.
	 */
	void setNeedReloadCards();

	/**
     * ����� ���� �� ��� ������
     * @param accountNumber
     * @return
     * @throws BusinessException
     */
    AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException;

	/**
     * ����� ����� �� � ������
     * @param cardNumber ����� �����
     * @return CardLink
     * @throws BusinessException
     */
	CardLink findCard(String cardNumber) throws BusinessException;

	/**
	 * ����� ������� �� ������ �������� �����
	 * @param accountNumber - ����� �������� �����
	 * @return LoanLink
	 * @throws BusinessException
	 */
	LoanLink findLoan(String accountNumber) throws BusinessException;

	/**
	 * ����� �������� �� ��� ������ (��������������� ��� ��������� �������������)
	 * @param alias - ����� (�������������� ��� �������� �������������)
	 * @return ErmbProductLink ��� null, ���� �� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	ErmbProductLink findProductByAlias(String alias) throws BusinessException, BusinessLogicException;

	/**
	 * ����� ��� �� ������
	 * @param imAccountNumber ����� ���
	 * @return IMAccountLink
	 * @throws BusinessException
	 */
	IMAccountLink findIMAccount(String imAccountNumber) throws BusinessException;

	/**
	 * �������� ������ �� ������ �� ����������� ����.
	 * @param code ��� ������ �� ������� ������
	 * @return ������ ��� null.
	 */
	ExternalResourceLink getByCode(String code) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ������ ��� � ������ �������
	 * @param filter ������
	 * @return ������ ������ �� ���
	 */
	List<IMAccountLink> getIMAccountLinks(IMAccountFilter filter) throws BusinessException, BusinessLogicException;

	/**
	 * ���������� ���� �� ������ SMS-������� � ��������� �����
	 * @param linkId - ID ������� SMS-�������
	 * @return ���� (never null)
	 * @deprecated ���������� �� �������� ���
	 */
	@Deprecated
	//todo CHG059738 �������
	PaymentTemplateLink getMobileBankTemplateLinkById(Long linkId) throws BusinessException, BusinessLogicException;

	/**
	 * ���������� ������ ������ �������� ������������ (����������� �� ������ ����������).
	 * @return ������ ��������.
	 */
	List<AutoSubscriptionLink> getAutoSubscriptionLinks() throws BusinessException, BusinessLogicException;

	/**
	 * ���������� ���� �������� ����������� (���������� �� ������ ����������).
	 * @param id ������������� �����������
	 * @return ���� �������� �����������.
	 */
	AutoSubscriptionLink getAutoSubscriptionLink(Long id) throws BusinessException, BusinessLogicException;

	/**
	 * ���������� ���������� ����� �����
	 * @return ����������
	 * @throws BusinessException
	 */
	Long getCountNewLetters() throws BusinessException;

	/**
	 * ��������� ������� ���������� ������������� �����.
	 */
	void decCountNewLetters();

	/**
	 * ���������� ������ id ��������, ������� ���������� ���������� ������� �� ������� ��������.
	 * @return ������ id ��������.
	 */
	List<Long> getBannersList();

	/**
	 * ������������� ������ id ��������, ������� ���������� ���������� ������� �� ������� ��������.
	 */
	void setBannersList(List<Long> bannersList);

	/**
	 * ���������� ������ ����������� �������, ������� ��� ���� ��������
	 */
	List<Long> getShownNotificationList();
	/**
	 * ������������� ������ id �����������, ������� ��� ���� ��������
	 */
	void setShownNotificationList(List<Long> notificationList);

	/**
	 * ���������� ������  ����������� � �������������� ������������
	 */
	List<PersonalOfferNotification> getOfferNotificationList();
	/**
	 * ������������� ������ ����������� � �������������� ������������
	 */
	void setOfferNotificationList(List<PersonalOfferNotification> offerNotificationList);

	/**
	 * @return ������-���������, ��������� ������������ � ������� ������
	 */
	Map<String, WidgetDefinition> getWidgetDefinitions();

	void setWidgetDefinitions(Map<String, WidgetDefinition> widgetDefinitions);

	WidgetDefinition getWidgetDefinition(String codename);

	/**
	 * ���������� ������ ������� (��� �� ����������) ���-�������
	 * @return ������ ������� ���-������� (never null)
	 */
	Map<String,WebPage> getPages();

	/**
	 * ������������� ������ ������� (��� �� ����������) ���-�������
	 * @param pages - ������ ������� ���-������� (never null)
	 */
	void setPages(Map<String, WebPage> pages);

	/**
	 * @return ������ ������������ ���-������� (never null)
	 */
	Map<String,WebPage> getSavedPages();

	/**
	 * @param pages  - ����������� ���-�������� (never null)
	 */
	void setSavedPages(Map<String, WebPage> pages);

	/**
	 * ���������� ���-�������� �� � ������������
	 * @param codename - ����������� (never null)
	 * @return ���-�������� ��� null, ���� �� ���������� ������������ �� ������� ��������
	 */
	WebPage getPage(String codename);

	/**
	 * ���������� ������ �� ��� ������������
	 * @param codename - ����������� ������� (never null)
	 * @return ������ ��� null, ���� �� ���������� ������������ �� ������ ������
	 */
	Widget getWidget(String codename);


	/**
	 * ����� �� ��������� �� ������� ������� ������ �������� �������
	 * @return true - ����� �������� ������� ������� �� ������� �������
	 */
	boolean isNeedLoadLoans();

	/**
	 * ���������� ������� ������������� ������� ������ �������� �� ������� ������� ����� ���������� ������
	 * @param needLoadLoans �������
	 */
	void setNeedLoadLoans(boolean needLoadLoans);

	/**
	 * ����� �� ��������� �� ������� ������� ������ ������ �������
	 * @return true - ����� �������� ����� ������� �� ������� �������
	 */
	boolean isNeedLoadAccounts();

	/**
	 * ���������� ������� ������������� ������� ������ ������ �� ������� ������� ����� ���������� ������
	 * @param needLoadAccounts �������
	 */
	void setNeedLoadAccounts(boolean needLoadAccounts);

	/**
	 * ����� �� ��������� �� ������� ������� ������ ��� �������
	 * @return true - ����� �������� ��� ������� �� ������� �������
	 */
	boolean isNeedLoadIMAccounts();

	/**
	 * ���������� ������� ������������� ������� ������ ��� �� ������� ������� ����� ���������� ������
	 * @param needLoadIMAccounts �������
	 */
	void setNeedLoadIMAccounts(boolean needLoadIMAccounts);

	/**
	 * @return ������������� ������ � ������ ������� ����������� ��������� �������������� ��� ������ ��������
	 */
	String getLogonSessionId();

	/**
	 * ���������� ������������� ���������, ������������ � ������� �������.
	 * @return ��������.
	 */
	ManagerInfo getManager();

	/**
	 * ������������� ������������� ���������, ������������ � ������� �������.
	 */
	void setManager(ManagerInfo manager);

	/**
	 * �������������,  ���� �� � ������� ������������ ������� � ������������������ �������� ���������
	 * @param hasOpenedDifLoan
	 */
	void setHasOpenedDifLoan(Boolean hasOpenedDifLoan);

	/**
	 * @return true - ���� ������������ ������� � ������������������ �������� ���������
	 */
	boolean isHasOpenedDifLoan();

	/**
	 * @return true - ������ ����� ��������� ������ ���
	 */
	boolean isTimeToCheckCSASession();

	/**
	 * @return true - ���������� ������� ��������� � ���, ��� � ���� ���������� �������
	 */
	boolean isShowOldBrowserMessage();

	/**
	 * �������������, ���������� �� ������� ��������� � ���, ��� � ���� ���������� �������
	 * @param showOldBrowserMessage
	 */
	void setShowOldBrowserMessage(boolean showOldBrowserMessage);

	/**
	 * ��������� ���� ������ �� �����, ��� ����������� �� ���������
	 * @return ������ �� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<AccountLink> getAccountsAll() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ���� ������ �� �����, ��� ����������� �� ���������
	 * @return ������ �� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<CardLink> getCardsAll() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ���� ������ �� �������, ��� ����������� �� ���������
	 * @return ������ �� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<LoanLink> getLoansAll() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ���� ������ �� ���, ��� ����������� �� ���������
	 * @return ������ �� ���
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<IMAccountLink> getIMAccountLinksAll() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ���� ������ �� ����� ����, ��� ����������� �� ���������
	 * @return ������ �� ����� ����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	List<DepoAccountLink> getDepoAccountsAll() throws BusinessException, BusinessLogicException;

	List<SecurityAccountLink> getSecurityAccountLinks() throws BusinessException, BusinessLogicException;

	List<SecurityAccountLink> getSecurityAccountLinksAll() throws BusinessException, BusinessLogicException;

	SecurityAccountLink getSecurityAccountLink(Long linkId) throws BusinessException;

	/**
	 * @return �������, ����������� �� ��, ��� ���������� ��� ����� ����� ����������
	 */
	boolean isWayCardsReloaded();

	/**
	 * ���������� �������, ����������� �� ��, ��� ���������� ��� ����� ����� ����������
	 * @param wayCardsReloaded boolean ��������
	 */
	void setWayCardsReloaded(boolean wayCardsReloaded);

	/**
	 * @return �������, ����������� �� ������������� ���������� ��������� ���������� � ����� �������
	 */
	boolean getShowTrainingInfo();

	/**
	 * ���������� �������, ����������� �� ������������� ���������� ��������� ���������� � ����� �������
	 * @param showTrainingInfo - ��������
	 */
	void setShowTrainingInfo(boolean showTrainingInfo);

	/**
	 * @return ������� ����������� ������ "����� ������� ����" ��� "������ ����������"
	 */
	boolean getNewPersonalInfo();

	/**
	 * ���������� ������� ����������� ������ "����� ������� ����" ��� "������ ����������"
	 * @param isNewPersonalInfo - ��������
	 */
	void setNewPersonalInfo(boolean isNewPersonalInfo);

	/**
	 * @return ������� ����������� ������ "����� ������� ����" ��� "���������� ������ � ������"
	 */
	boolean getNewBillsToPay();

	/**
	 * ���������� ������� ����������� ������ "����� ������� ����" ��� "���������� ������ � ������"
	 * @param isNewBillsToPay - ��������
	 */
	void setNewBillsToPay(boolean isNewBillsToPay);

	/**
	 * @param type ��� �������.
	 * @return ���������� �� �������.
	 */
	AvatarInfo getAvatarInfoByType(AvatarType type) throws BusinessException;

	/**
	 * ��������� ���������� �� �������.
	 * @param info ���������� �� �������.
	 */
	void setAvatarInfo(AvatarInfo info);

	/**
	 * ������� ���������� �� ��������.
	 * @param type ���.
	 */
	void deleteAvatarInfo(AvatarType type);

	/**
	 * �������� ������ ������, ������� ���� �������.
	 * @return ������ ������.
	 */
	List<File> getMarkedDeleteFiles();

	/**
	 * �������� ���� ��� ��������.
	 */
	void markDeleteFile(File file);

	/**
	 * @return ���������������� ���������.
	 */
	Properties getUserProperties();

	/**
	 * @param properties ���������������� ���������.
	 */
	void setUserProperties(Properties properties);

	/**
	 * @return ��������� ����������� ����� ��� ����� � �������
	 */
	TutorialProfileState getStateProfilePromo();

	/**
	 * ���������� ��������� ����������� ����� ��� ����� � �������
	 * @param showPromo - ��������
	 */
	void setStateProfilePromo(TutorialProfileState showPromo);

	/**
	 * @param type ��� ���������� ����.
	 * @return ������������� ������������ ��� ��������� ���������� ����.
	 */
	String getUserIdForSocialNet(String type) throws BusinessException;

	InvoicesSubscriptionsPromoMode getPromoMode() throws BusinessException;

	void setPromoMode(InvoicesSubscriptionsPromoMode mode);

	/**
	 * @return ��������� ������������ ���� �������
	 */
	Set<String> getClientArrestedCards();

	/**
	 * ����� ��������� ����������� ����
	 * @param clientArrestedCards - set �� ������� ����
	 */
	void setClientArrestedCards(Set<String> clientArrestedCards);

	/**
	 * ��������� ������ � ������ ������� �������� �������.
	 * @param errorCollector - ���������
	 * @param withRefresh - true - ��������������� ������� ���� ����������
	 * @return ������� �����, ���� ����������� ����������, ������ ������
	 * @throws DocumentException
	 * @throws BusinessException
	 */
	ClientInvoiceData getClientInvoiceData(Set<String> errorCollector, boolean withRefresh) throws BusinessException;

	/**
	 * @param incognito ������� ��������� �������.
	 */
	void setIncognito(boolean incognito);

	/**
	 * @return ������ ��������.
	 */
	boolean isIncognito();

	/**
	 * @return ������ ��������� �������.
	 * @param alternative �������������� ��������.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	Collection<String> getPhones(Boolean alternative) throws BusinessLogicException, BusinessException;

	/**
	 * ��������� ���� ������� �������
	 * @param alternative �������������� �������� (������ ��� ���)
	 * @param checkPhoneInERMB ����� �� ��������� ���� �������
	 * @return ������ ��������� �������.
	 */
	Collection<String> getPhones(Boolean alternative, Boolean checkPhoneInERMB) throws BusinessLogicException, BusinessException;

	/**
	 * @param flag ������� ����������� �������� �������� � ��������� ���� � ������� ������
	 */
	void setActiveCreditViewBlock(Boolean flag);

	/**
	 * @return ���������� �������� ������� � ��������� ����� � ������� ������
	 */
	Boolean getActiveCreditViewBlock();

	/**
	 * ���� ���������� ���������� ������. ������� ������������� �����.
	 * @return true - ����� ��������
	 */
	Boolean getMustUpdateCreditReport();

	/**
	 * ���������� ���� ���������� ���������� ������.
	 * @param mustUpdateCreditReport ���� ���������� ���������� ������.
	 */
	void setMustUpdateCreditReport(Boolean mustUpdateCreditReport);

	/**
	 * @return true - ���� ������ ��� ����������� ����������� ������� ���������� �� ������� � Android ���������
	 */
	boolean isTrustedContactConfirmed();

	/**
	 * @param trustedContactConfirmed  - �������, ��� ������ ��� ����������� ����������� ������� ���������� �� ������� � Android ���������
	 */
	void setTrustedContactConfirmed(boolean trustedContactConfirmed);

	/**
	 * @param externalId - external Id �����
	 */
	boolean isCardDetailInfoUpdated(String externalId);

	/**
	 * @return ����� ��������� ���������� ����� ���������
	 */
	Calendar getPromoBlockUntil() throws BusinessException;

	/**
	 * @param promoBlockUntil ����� ��������� ���������� ����� ���������
	 */
	void setPromoBlockUntil(Calendar promoBlockUntil);

	/**
	 * ��������� ������ ���� ����������� ������� �� ������.
	 * @param number -  ���-�� ������������ �������. ���� null �� ���.
	 * @param isViewed - true(����������)/false(������������)/null(��� ������)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return ������ number ����������� ������� �� ������ ��� ���� �����������, ���� number = null.
	 */
	List<LoanCardOffer> getLoanCardOfferByPersonData(final Integer number, final Boolean isViewed) throws BusinessException;

	/**
	 * �������� ����������� ������� �� ��������.
	 * @param number - ����������� �����������
	 * @param isViewed - true(����������)/false(������������)/null(��� ������)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return ������ number ����������� ������� �� �������� ��� ���� �����������, ���� number = null.
	 */
	List<LoanOffer> getLoanOfferByPersonData(final Integer number, final Boolean isViewed) throws BusinessException;

	/**
	 * @param offerTypes ���� ����������� �� ������ ��� ������� ����� �������� �������
	 * @return ������ ���������� ���������� �� ��������� ��������: �������, ������ � ������� �� ��������� �����.
	 */
	List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes) throws BusinessException;

	/**
	 * @param conditionId  id ������� � ������� ������ ��� ���������� ���������� ��������
	 * @return �����-�������� ������� �������� �������, ����� � ������� �� ����������� �� ��������� �����
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOffer(final Long conditionId) throws BusinessException;

	/**
	 * ��������� ���������� �� ��� �����������, �� �������� ���� ��������� ������ �� ����. �����
	 * @param conditionId id ������� �� ��������� �����
	 * @return true - ����������� ��� ����������, false - ����������� ��� �������
	 * @throws BusinessException
	 */
	boolean existCreditCardOffer(final Long conditionId) throws BusinessException;

	/**
	 * �������� ����������� �� ��������� ����� �� ��������� � ��� �������� � ��������� �� id �����������, � id �������
	 * @param loanCardOfferId  - id ����������� �� ��������� �����
	 * @param conditionId      - id �������
	 * @return  ������ ��������� �� 3-� ���������: CreditCardCondition, LoanCardOffer, CreditCardProduct
	 *          null - ���� ������ �� �������
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOfferIdAndConditionId(final OfferId loanCardOfferId,final Long conditionId) throws BusinessException;

	/**
	 * ��������� ������� �� id ����������� �� �����
	 * @param loanCardOfferId  - id ����������� �� �����
	 * @return id �������
	 * @throws BusinessException
	 */
	Long findConditionIdByOfferId(final OfferId loanCardOfferId) throws BusinessException;

	/**
	 * �������� ����������� �� ������� �� offerId
	 * @param offerId - ������������� �����������
	 * @return ����������� �� �������
	 * @throws BusinessException
	 */
	LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException;

	/**
	 * �������� ����������� �� ����� �� offerId
	 * @param offerId - ������������� �����������
	 * @return ����������� �� �����
	 * @throws BusinessException
	 */
	LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException;

	/**
	 * ��������� ������ �� ����������� �� ��������
	 * @param loanOfferIds - ������ ��������������� ����������� �� ��������
	 * @param feedbackType - ��� �������
	 * @throws BusinessException
	 */
	void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * ��������� ������ �� ����������� �� ������
	 * @param loanCardOfferIds - ������ ��������������� ����������� �� ������
	 * @param feedbackType - ��� �������
	 * @throws BusinessException
	 */
	void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * ����������� �������� ������ (��������� �����������) �� CRM
	 * @return true - ��������� �����
	 * @throws BusinessException
	 */
	boolean isOfferReceivingInProgress() throws BusinessException;

	/**
	 * �������� ����������� �� ������� ��� ��������������
	 * @param offerId - ������������� �����������
	 * @throws BusinessException
	 */
	void updateLoanOfferAsUsed(final OfferId offerId) throws BusinessException;

	/**
	 * �������� ����������� �� ����� ��� ��������������
	 * @param offerId - ������������� �����������
	 * @throws BusinessException
	 */
	void updateLoanCardOfferAsUsed(final OfferId offerId) throws BusinessException;

	/**
	 * ���������� ��������� ������� ��� ����������� �� ������� ��������
	 * @return ��������� �������
	 * @throws BusinessException
	 */
	LoanOffer getLoanOfferForMainPage() throws BusinessException;

	/**
	 * ���������� ��������� ��������� ����� ��� ����������� �� ������� ��������
	 * @return ��������� ��������� �����
	 * @throws BusinessException
	 */
	LoanCardOffer getLoanCardOfferForMainPage() throws BusinessException;

	/**
	 * ��������� �� ������ �� ����������� �� ������
	 * @param offerId - ������������� �����������
	 * @param feedbackType - ��� �������
	 * @return
	 */
	boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * ��������� �� ������ �� ����������� �� ��������� �����
	 * @param offerId - ������������� �����������
	 * @param feedbackType - ��� �������
	 * @return
	 */
	boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * �������� ����������� �� ����� � ����� "����� �����"
	 * @return ����������� �� ����� � ����� "����� �����"
	 * @throws BusinessException
	 */
	LoanCardOffer getNewLoanCardOffer() throws BusinessException;

	/**
	 * �������� ���� ����������� ������ �� ��������� �����
	 * @param offerId - ������������� ����������� �� ��������� �����
	 * @throws BusinessException
	 */
	void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException;

	/**
	 * �������� ���� �������� �� �������� ���������� ������ �� ��������� �����
	 * @param offerId - ������������� ����������� �� ��������� �����
	 * @throws BusinessException
	 */
	void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException;

	/**
	 * @return ����������� � ��
	 */
	Boolean isMB();

	/**
	 * @return id ������ CSA
	 */
	Long getCSAProfileId();

	/**
	 * @return  id ��������� ������
	 */
	Long getGuestProfileId();

	/**
	 * ������� ��������� ���������
	 * @return �������� ��������
	 */
	BusinessDocumentOwner createDocumentOwner();

	/**
	 * @return true - �������� �����, false � ��������� ������
	 */
	boolean isGuest();

	/**
	 * @return ������ ������ �����
	 */
	String getGuestLoginAlias();

	/**
	 * @return ��� �����
	 */
	GuestType getGuestType();

	/**
	 * �������� �� ������� ���������� ������ �� ��������� �����
	 * @return
	 */
	boolean isLoanCardClaimAvailable();

	/**
	 * ���������� ������ ����������� ���������� ������ �� ��������� �����
	 * @param loanCardClaimAvailable
	 */
	void setLoanCardClaimAvailable(boolean loanCardClaimAvailable);

	/**
	 * �������� ������ �� ��������� �������
	 * @param incrementCounter - ��������� �� ������� ���������� ����������� ����� �� ������� ��������
	 * @return
	 * @throws BusinessException
	 */
	OfferPrior getOfferPrior(boolean incrementCounter) throws BusinessException;

	/**
	 *
	 * @param  offerId ������������� �����������
	 * @return �����������. ����� ���������� null
	 * @throws BusinessException
	 */
	LoanOffer getLoanOffer(final OfferId offerId) throws BusinessException;
}
