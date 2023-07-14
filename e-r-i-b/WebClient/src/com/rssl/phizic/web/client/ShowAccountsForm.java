package com.rssl.phizic.web.client;

import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.logging.operations.LogEntry;
import com.rssl.phizic.operations.ima.RateOfExchange;
import com.rssl.phizic.web.client.asyncwebpageform.WebPageForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 13.10.2005 Time: 14:06:52 */
public class ShowAccountsForm extends WebPageForm
{
	private List<AccountLink> activeAccounts = new ArrayList<AccountLink>();   //активные счета
	private List<AccountLink> closedAccounts = new ArrayList<AccountLink>();  //закрытые счета
    private String[] selectedAccountsIds=new String[0];

	private List<CardLink> activeCards = new ArrayList<CardLink>();
	private List<CardLink> blockedCards = new ArrayList<CardLink>();
	private String[] selectedCardsIds=new String[0];

	private List<DepositLink> deposits = new ArrayList<DepositLink>();
	private Long warningPeriod;
	private Map<AccountLink, AccountAbstract> accountAbstract;
	private Map<AccountLink, String> accountAbstractErrors; //ошибки, полученные при запросе информации по счетам
	private Map<CardLink, CardAbstract> cardAbstract;
	private Map<LoanLink, ScheduleAbstract> loanAbstract;
	private Map<LoanLink, String> loanAbstractErrors; //ошибки, полученные при запросе информации по кредитам
	private List<IMAccountLink> imAccounts;
	private Map<IMAccountLink, IMAccountAbstract> imAccountAbstract;
   	private List<DepoAccountLink> depoAccounts = new ArrayList<DepoAccountLink>();
	private PFRLink pfrLink; //карточка ПФР
	private List<PFRStatementClaim> pfrClaims = new ArrayList<PFRStatementClaim>(); //заявки на получение выписки из ПФР
	private boolean isPfrClaimsInitialized; //были ли установлены заявки pfrClaims. Для отличия пустого списка от отложенной инициализации
    //предложения для нижней части экрана
    private List<LoanOffer> bottomLoanOffers = new ArrayList<LoanOffer>();
    private List<LoanCardOffer> bottomLoanCardOffers = new ArrayList<LoanCardOffer>();
    //предложения для верхней части экрана
    private List<LoanOffer> topLoanOffer;
	private List<LoanCardOffer> topLoanCardOffer;
	private LoyaltyProgramLink loyaltyProgramLink;

	private boolean isAllCardDown;
	private boolean isAllAccountDown;
	private boolean isAllLoanDown;
	private boolean isAllIMAccountDown;
	private boolean isPfrDown;

	private List<News> news = new ArrayList<News>();
	private String [] selectedDepositsIds=new String[0];
	private List<LogEntry> logEntries = new ArrayList<LogEntry>();
	private ActivePerson user;

	private List<LoanLink> activeLoans = new ArrayList<LoanLink>();   //активные кредиты
	private List<LoanLink> blockedLoans = new ArrayList<LoanLink>();  //заблокированные кредиты
	private boolean emptyLoanOffer;
	private Long id; //id выбранного продукта
	private Map<String, ScheduleItem> scheduleItems = new HashMap<String, ScheduleItem>();

	private Map<String, RateOfExchange> rates;

	private boolean needShowPreRegistrationWindow; //необходимо ли отображать окно пререгистрации.
	private boolean hardRegistrationMode;          //режим жесткой регистрации.
	private String preRegistrationMessage;         //Сообщение о пререгистрации.
	private String titlePreRegistrationMessage;    //Заголовок сообщения о пререгистрации.
	private boolean existCSALogin;                 //Существует ли логин для пользователя в ЦСА.
	private boolean completeRegistration;          //мы зкончили самостоятельную регистрацию, нужно показать сообщение
	private boolean newSelfRegistrationDesign;     //новый дизайн самостоятельной регистрации. от этого меняется внешний вид сообщения

	private boolean haveNotShowClaims;                           //Есть ли не показанные заявки
	private List<ExtendedLoanClaim> extendedLoanClaims; // расширенные заявки на кредит
	private int waitingTime;
	private boolean showBanner;
	private boolean crmOffersEnabled;

    private int promoDivMaxLength;

	private List<Object[]> claimSBNKDData = new ArrayList<Object[]>(); //данные по завкам СБНКД
	private List<LoanCardClaim> loanCardClaimList = new ArrayList<LoanCardClaim>(); //данные по заявкам на кредитные карты клиента
	private int loanCardClaimCount;//кол-во заявок на кредитную карту

	private List<OfficeLoanClaim> officeLoanClaims;//заявки созданные в каналах отличных от УКО

	private boolean offerSeccess; //оферта на кредит принята клиентом

	public Map<String, RateOfExchange> getRates()
	{
		return rates;
	}

	public void setRates(Map<String, RateOfExchange> rates)
	{
		this.rates = rates;
	}

	public ActivePerson getUser()
	{
		return user;
    }

    public void setUser(ActivePerson user) {
        this.user = user;
    }

    public String[] getSelectedAccountsIds()
    {
        return selectedAccountsIds;
    }

    public void setSelectedAccountsIds(String[] selectedAccountsIds)
    {
        this.selectedAccountsIds = selectedAccountsIds;
    }

	/**
	 * @return активные карты
	 */
	public List<CardLink> getActiveCards()
	{
		return activeCards;
	}

	/**
	 * @param activeCards - заблокированные карты
	 */
	public void setActiveCards(List<CardLink> activeCards)
	{
		this.activeCards = activeCards;
	}

	public List<CardLink> getBlockedCards()
	{
		return blockedCards;
	}

	public void setBlockedCards(List<CardLink> blockedCards)
	{
		this.blockedCards = blockedCards;
	}

	public List<DepositLink> getDeposits()
	{
		return deposits;
	}

	public void setDeposits(List<DepositLink> deposits)
	{
		this.deposits = deposits;
	}

	public void setSelectedCardsIds(String[] selectedCardsIds)
	{
	    this.selectedCardsIds = selectedCardsIds;
	}

	public String[] getSelectedCardsIds()
    {
        return selectedCardsIds;
    }

	public List<LogEntry> getLogEntries()
	{
		return logEntries;
	}

	public void setLogEntries(List<LogEntry> logEntries)
	{
		this.logEntries = logEntries;
	}

    Map <String,Object> getFilterFields()
	{
	   return new HashMap<String,Object>();
	}

	public List<News> getNews()
	{
		return news;
	}

	public void setNews(List<News> news)
	{
		this.news = news;
	}

	public Long getWarningPeriod()
	{
		return warningPeriod;
	}

	public void setWarningPeriod(Long warningPeriod)
	{
		this.warningPeriod = warningPeriod;
	}

	public Map<String, ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(Map<String, ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public String[] getSelectedDepositsIds()
	{
		return selectedDepositsIds;
	}

	public void setSelectedDepositsIds(String[] selectedDepositsIds)
	{
		this.selectedDepositsIds = selectedDepositsIds;
	}


	public Map<AccountLink, AccountAbstract> getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(Map<AccountLink, AccountAbstract> accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public Map<CardLink, CardAbstract> getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(Map<CardLink, CardAbstract> cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию ни по одному кредиту, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllLoanDown()
	{
		return isAllLoanDown;
	}

	public void setAllLoanDown(boolean isAllLoanDown)
	{
		this.isAllLoanDown = isAllLoanDown;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию по всем картам, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllCardDown()
	{
		return isAllCardDown;
	}

	public void setAllCardDown(boolean isAllCardDown)
	{
		this.isAllCardDown = isAllCardDown;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию по всем счетам, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllAccountDown()
	{
		return isAllAccountDown;
	}

	public void setAllAccountDown(boolean isAllAccountDown)
	{
		this.isAllAccountDown = isAllAccountDown;
	}
	/**
	 *
	 * @return true - если не удалось получит информацию по всем ОМС, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllIMAccountDown()
	{
		return isAllIMAccountDown;
	}

	public void setAllIMAccountDown(boolean allIMAccountDown)
	{
		isAllIMAccountDown = allIMAccountDown;
	}

	public Map<LoanLink, ScheduleAbstract> getLoanAbstract()
	{
		return loanAbstract;
	}

	public void setLoanAbstract(Map<LoanLink, ScheduleAbstract> loanAbstract)
	{
		this.loanAbstract = loanAbstract;
	}

	public Map<LoanLink, String> getLoanAbstractErrors()
	{
		return loanAbstractErrors;
	}

	public void setLoanAbstractErrors(Map<LoanLink, String> loanAbstractErrors)
	{
		this.loanAbstractErrors = loanAbstractErrors;
	}

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	public Map<IMAccountLink, IMAccountAbstract> getImAccountAbstract()
	{
		return imAccountAbstract;
	}

	public void setImAccountAbstract(Map<IMAccountLink, IMAccountAbstract> imAccountAbstract)
	{
		this.imAccountAbstract = imAccountAbstract;
	}

	public List<DepoAccountLink> getDepoAccounts()
	{
		return depoAccounts;
	}

	public void setDepoAccounts(List<DepoAccountLink> depoAccounts)
	{
		this.depoAccounts = depoAccounts;
	}

	public PFRLink getPfrLink()
	{
		return pfrLink;
	}

	public void setPfrLink(PFRLink pfrLink)
	{
		this.pfrLink = pfrLink;
	}

	public List<PFRStatementClaim> getPfrClaims()
	{
		return pfrClaims;
	}

	public void setPfrClaims(List<PFRStatementClaim> pfrClaims)
	{
		this.pfrClaims = pfrClaims;
	}

	public boolean isPfrClaimsInitialized()
	{
		return isPfrClaimsInitialized;
	}

	public void setPfrClaimsInitialized(boolean pfrClaimsInitialized)
	{
		isPfrClaimsInitialized = pfrClaimsInitialized;
	}

	public boolean isPfrDown()
	{
		return isPfrDown;
	}

	public void setPfrDown(boolean pfrDown)
	{
		isPfrDown = pfrDown;
	}

	public List<LoanOffer> getBottomLoanOffers() {
        return bottomLoanOffers;
    }

    public void setBottomLoanOffers(List<LoanOffer> bottomLoanOffers) {
        this.bottomLoanOffers = bottomLoanOffers;
    }

    public List<LoanCardOffer> getBottomLoanCardOffers() {
        return bottomLoanCardOffers;
    }

    public void setBottomLoanCardOffers(List<LoanCardOffer> bottomLoanCardOffers) {
        this.bottomLoanCardOffers = bottomLoanCardOffers;
    }

    public List<LoanOffer> getTopLoanOffer() {
        return topLoanOffer;
    }

    public void setTopLoanOffer(List<LoanOffer> topLoanOffer) {
        this.topLoanOffer = topLoanOffer;
    }

    public List<LoanCardOffer> getTopLoanCardOffer() {
        return topLoanCardOffer;
    }

    public void setTopLoanCardOffer(List<LoanCardOffer> topLoanCardOffer) {
        this.topLoanCardOffer = topLoanCardOffer;
    }

	public LoyaltyProgramLink getLoyaltyProgramLink()
	{
		return loyaltyProgramLink;
	}

	public void setLoyaltyProgramLink(LoyaltyProgramLink loyaltyProgramLink)
	{
		this.loyaltyProgramLink = loyaltyProgramLink;
	}

	public boolean getEmptyLoanOffer()
	{
		return emptyLoanOffer;
	}

	public void setEmptyLoanOffer(boolean emptyLoanOffer)
	{
		this.emptyLoanOffer = emptyLoanOffer;
	}

	public Map<AccountLink, String> getAccountAbstractErrors()
	{
		return accountAbstractErrors;
	}

	public void setAccountAbstractErrors(Map<AccountLink, String> accountAbstractErrors)
	{
		this.accountAbstractErrors = accountAbstractErrors;
	}

	public boolean getHardRegistrationMode()
	{
		return hardRegistrationMode;
	}

	public void setHardRegistrationMode(boolean hardRegistrationMode)
	{
		this.hardRegistrationMode = hardRegistrationMode;
	}

	public boolean getNeedShowPreRegistrationWindow()
	{
		return needShowPreRegistrationWindow;
	}

	public void setNeedShowPreRegistrationWindow(boolean needShowPreRegistrationWindow)
	{
		this.needShowPreRegistrationWindow = needShowPreRegistrationWindow;
	}

	public String getPreRegistrationMessage()
	{
		return preRegistrationMessage;
	}

	public void setPreRegistrationMessage(String preRegistrationMessage)
	{
		this.preRegistrationMessage = preRegistrationMessage;
	}

	public String getTitlePreRegistrationMessage()
	{
		return titlePreRegistrationMessage;
	}

	public void setTitlePreRegistrationMessage(String titlePreRegistrationMessage)
	{
		this.titlePreRegistrationMessage = titlePreRegistrationMessage;
	}

	public boolean isExistCSALogin()
	{
		return existCSALogin;
	}

	public void setExistCSALogin(boolean existCSALogin)
	{
		this.existCSALogin = existCSALogin;
	}

	/**
	 * @return активные счета
	 */
	public List<AccountLink> getActiveAccounts()
	{
		return activeAccounts;
	}

	/**
	 * @param activeAccounts - активные счета
	 */
	public void setActiveAccounts(List<AccountLink> activeAccounts)
	{
		this.activeAccounts = activeAccounts;
	}

	/**
	 * @return заблокированные счета
	 */
	public List<AccountLink> getClosedAccounts()
	{
		return closedAccounts;
	}

	/**
	 * @param closedAccounts - заблокированные счета
	 */
	public void setClosedAccounts(List<AccountLink> closedAccounts)
	{
		this.closedAccounts = closedAccounts;
	}

	public List<LoanLink> getActiveLoans()
	{
		return activeLoans;
	}

	public void setActiveLoans(List<LoanLink> activeLoans)
	{
		this.activeLoans = activeLoans;
	}

	public List<LoanLink> getBlockedLoans()
	{
		return blockedLoans;
	}

	public void setBlockedLoans(List<LoanLink> blockedLoans)
	{
		this.blockedLoans = blockedLoans;
	}
	public List<ExtendedLoanClaim> getExtendedLoanClaims()
	{
		return extendedLoanClaims;
	}

	public void setExtendedLoanClaims(List<ExtendedLoanClaim> extendedLoanClaims)
	{
		this.extendedLoanClaims = extendedLoanClaims;
	}
	public boolean isCompleteRegistration()
	{
		return completeRegistration;
	}

	public void setCompleteRegistration(boolean completeRegistration)
	{
		this.completeRegistration = completeRegistration;
	}

	public boolean isNewSelfRegistrationDesign()
	{
		return newSelfRegistrationDesign;
	}

	public void setNewSelfRegistrationDesign(boolean newSelfRegistrationDesign)
	{
		this.newSelfRegistrationDesign = newSelfRegistrationDesign;
	}

	public void setWaitingTime(int waitingTime)
	{
		this.waitingTime = waitingTime;
	}

	public int getWaitingTime()
	{
		return waitingTime;
	}

	public void setShowBanner(Boolean showBanner)
	{
		this.showBanner = showBanner;
	}

	public boolean isShowBanner()
	{
		return showBanner;
	}

	/**
	 *
	 * @return данные по завкам СБНКД
	 */
	public List<Object[]> getClaimSBNKDData()
	{
		return claimSBNKDData;
	}

	/**
	 * Задать данные по завкам СБНКД
	 * @param claimSBNKDData - данные
	 */
	public void setClaimSBNKDData(List<Object[]> claimSBNKDData)
	{
		this.claimSBNKDData = claimSBNKDData;
	}

	/**
	 * Список заявок на кредитные карты
	 * @return
	 */
	public List<LoanCardClaim> getLoanCardClaimList()
	{
		return loanCardClaimList;
	}

	/**
	 * Установить список заявок на кредитные карты
	 * @param loanCardClaimList
	 */
	public void setLoanCardClaimList(List<LoanCardClaim> loanCardClaimList)
	{
		this.loanCardClaimList = loanCardClaimList;
	}

	public int getLoanCardClaimCount()
	{
		return loanCardClaimCount;
	}

	public void setLoanCardClaimCount(int loanCardClaimCount)
	{
		this.loanCardClaimCount = loanCardClaimCount;
	}

    public int getPromoDivMaxLength()
    {
        return promoDivMaxLength;
    }

    public void setPromoDivMaxLength(int promoDivMaxLength)
    {
        this.promoDivMaxLength = promoDivMaxLength;
	}

	public boolean isCrmOffersEnabled()
	{
		return crmOffersEnabled;
	}

	public void setCrmOffersEnabled(boolean crmOffersEnabled)
	{
		this.crmOffersEnabled = crmOffersEnabled;
	}

	/**
	 * Есть ли не показанные заявки
	 * @return Да, если есть. Нет, впротивном случае
	 */
	public boolean isHaveNotShowClaims()
	{
		return haveNotShowClaims;
	}

	/**
	 * Установить флаг, есть ли не показанные заявки
	 * @param haveNotShowClaims Флаг.
	 */
	public void setHaveNotShowClaims(boolean haveNotShowClaims)
	{
		this.haveNotShowClaims = haveNotShowClaims;
	}

	public List<OfficeLoanClaim> getOfficeLoanClaims()
	{
		return officeLoanClaims;
	}

	public void setOfficeLoanClaims(List<OfficeLoanClaim> officeLoanClaims)
	{
		this.officeLoanClaims = officeLoanClaims;
	}

	public boolean isOfferSeccess()
	{
		return offerSeccess;
	}

	public void setOfferSeccess(boolean offerSeccess)
	{
		this.offerSeccess = offerSeccess;
	}
}

