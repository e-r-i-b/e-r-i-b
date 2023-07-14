package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.ikfl.crediting.CreditingConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.business.clients.SelfRegistrationHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.depo.GetDepoAccountListOperation;
import com.rssl.phizic.operations.deposits.GetDepositListOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.operations.pfr.ListPFRClaimOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.client.ShowAccountsAction;
import com.rssl.phizic.web.client.ShowAccountsForm;
import com.rssl.phizic.web.common.client.Constants;
import com.rssl.phizic.web.util.PersonInfoUtil;
import com.rssl.phizic.web.util.browser.BrowserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * +получение справки о состоянии вклада.
 */
public class ShowAccountsExtendedAction extends ShowAccountsAction
{
	private static final String FORWARD_CONNECT_UDBO = "ConnectUdbo";
	private static final String FORWARD_SHOW_CONNECT_UDBO_MESSAGE = "ConnectUdboMessage";
	private static final String FORWARD_SHOW_WIDGET = "ShowWidget";
	private static final String FORWARD_REGISTRATION = "Registration";
	private final ExtendedBlockedCardFilter extBlockedCardFilter = new ExtendedBlockedCardFilter();
	private final ClosedAccountFilter closedAccountFilter = new ClosedAccountFilter();
	private final ActiveLoanFilter activeLoanFilter = new ActiveLoanFilter();

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = super.getKeyMethodMap();
	    keyMap.put("button.showAccountInformation", "buildAccountInformation");
        return keyMap;
    }

	/**
	 * получить справку о состоянии вклада
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return тот же самый путь, что для выписки только добавляем параметр copying=true
	 * @throws Exception
	 */
	public ActionForward buildAccountInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    ActionForward forward = buildAccountCertificate(mapping, form, request, response);
	    String path = forward.getPath();
	    StringBuilder builder = new StringBuilder(path);

	    builder.append("&copying=true");

	    forward.setPath(builder.toString());
	    return forward;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ShowAccountsForm frm = (ShowAccountsForm) form;

	    RemoteConnectionUDBOConfig config = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class);
	    if (RemoteConnectionUDBOHelper.isShowMoreSbolMenuItem() && RemoteConnectionUDBOHelper.getFromSession().isShowCancelUdboMessage())
        {
	        RemoteConnectionUDBOHelper.getFromSession().setShowCancelUdboMessage(false);
	        String message = "<h1>" + config.getCancelConnectUDBOMessageTitle() + "</h1><br>" + config.getCancelConnectUDBOMessageText();
	        saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);
        }

	    //проверяем нужно ли показать сообщение о подключении УДБО, или может запретить ему работу пока не подключит
	    //клиент не ВИП и карточный или СБОЛ и его ТБ разрешает заключение УДБО, если клиент не подходит по типу - продолжение работы
        if (RemoteConnectionUDBOHelper.isShowMoreSbolMenuItem())
        {
            //если первый вход в 16 релиз - отображаем баннер предложения
            if (RemoteConnectionUDBOHelper.isFirstUserEnter16Release())
            {
                return mapping.findForward(FORWARD_SHOW_CONNECT_UDBO_MESSAGE);
            }
            //непервый вход
            else
            {
                // возможность работы без УДБО
                if (!RemoteConnectionUDBOHelper.isWorkWithoutUDBO())
                {
                    //проверяем тип и карты клиента - переводим на форму заключения
                    if (RemoteConnectionUDBOHelper.checkConnectAbility())
                    {
                        RemoteConnectionUDBOHelper.getFromSession().setRedirectToPayment(true);
                        return mapping.findForward(FORWARD_CONNECT_UDBO);
                    }
                    else
                    {
                        //отправляем на страницу подключения УДБО. Там отображаем форму о невозможности продолжения работы
	                    return mapping.findForward(FORWARD_SHOW_CONNECT_UDBO_MESSAGE);
                    }
                }
                //иначе работа без УДБО не запрещена, все хорошо, продолжаем работу
            }
        }

	    SelfRegistrationHelper selfRegistrationHelper = SelfRegistrationHelper.getIt();

	    boolean newDesign = ConfigFactory.getConfig(SelfRegistrationConfig.class).isNewSelfRegistrationDesign();
	    frm.setNewSelfRegistrationDesign(newDesign);

	    if (newDesign && getNeedShowPreRegistrationMessageNewDesign(selfRegistrationHelper))
	    {
		    if (!selfRegistrationHelper.getHardRegistrationMode())
	            selfRegistrationHelper.setWindowShowed(true);
		    return mapping.findForward(FORWARD_REGISTRATION);
	    }

	    UserPropertiesConfig userPropertiesConfig = ConfigFactory.getConfig(UserPropertiesConfig.class);
	    Calendar dateConnectionUDBO = userPropertiesConfig.getDateConnectionUDBO();

	    ActivePerson curPerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
        if (RemoteConnectionUDBOHelper.isColdPeriod(curPerson.getLogin()))
        {
            if (!userPropertiesConfig.isColdPeriodMessageWasShow())
            {
                int timeColdPeriod = config.getTimeColdPeriod();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateConnectionUDBO != null ? dateConnectionUDBO.getTime() : curPerson.getAgreementDate().getTime());
                calendar.add(Calendar.HOUR, timeColdPeriod);
                String message = config.getColdPeriodInfoMessage();
                message = message.replace("[days/]", String.valueOf(((timeColdPeriod - 1) / 24) + 1)).replace("[before/]", new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime()));
                saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);

                userPropertiesConfig.setColdPeriodMessageWasShow(true);
            }
        }

        if (dateConnectionUDBO != null)
        {
		    if (!userPropertiesConfig.isConnectUDBOMessageWasShow())
		    {
			    saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("<p class='messTtl'>Отлично! Теперь вам доступны все возможности &#171;Сбербанк Онлайн&#187;.</p> <a class='grayMessLink' href='/PhizIC/private/userprofile/accountSecurity.do'>Настройте отображение</a> своих карт, вкладов, кредитов в интернет-банке", false), null);

			    userPropertiesConfig.setConnectUDBOMessageWasShow(true);
		    }
	    }

	    if(PersonInfoUtil.isAvailableWidget() && !BrowserUtils.isOldBrowser())
        {
            frm.setWidgetAvailable(true);
	        if (!getNeedShowPreRegistrationMessage(selfRegistrationHelper))
                return mapping.findForward(FORWARD_SHOW_WIDGET);
        }

	    if (!newDesign && getNeedShowPreRegistrationMessage(selfRegistrationHelper))
	    {
		    selfRegistrationHelper.incRegistrationShowedCount();
	        frm.setNeedShowPreRegistrationWindow(true);
	    }
	    CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);
	    frm.setWaitingTime(creditingConfig.getWaitingTime());

	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

	    if (personData.isOfferReceivingInProgress())
			frm.setShowBanner(false);
	    else
		    frm.setShowBanner(CollectionUtils.isNotEmpty(personData.getLoanOfferByPersonData(1, false))
		        || CollectionUtils.isNotEmpty(personData.getLoanCardOfferByPersonData(1, false)));

	    frm.setCrmOffersEnabled(AuthModule.getAuthModule().implies(new ServicePermission("GetPreapprovedOffersService", false)));
	    frm.setHardRegistrationMode(selfRegistrationHelper.getHardRegistrationMode());
	    frm.setTitlePreRegistrationMessage(ConfigFactory.getConfig(SelfRegistrationConfig.class).getWindowTitleMessage());
	    frm.setPreRegistrationMessage(selfRegistrationHelper.getPreregistrationMessage());
	    frm.setExistCSALogin(selfRegistrationHelper.getRegistrationStatus() == RegistrationStatus.EXIST);
	    if (!selfRegistrationHelper.getHardRegistrationMode())
	        selfRegistrationHelper.setWindowShowed(true);

	    ActivePerson person = personData.getPerson();

		try
		{
			if (checkAccess(GetDepositListOperation.class))
			{
				GetDepositListOperation operation = createOperation(GetDepositListOperation.class);
				frm.setDeposits(operation.getList());
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		try
		{
			if (checkAccess(GetLoanListOperation.class) && PermissionUtil.impliesService("ReceiveLoansOnLogin"))
			{
				setLoanAbstract(frm);
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		try
		{
			if (checkAccess(GetDepoAccountListOperation.class) )
			{
				setDepoAccounts(frm);
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		try
		{
			if (checkAccess(GetAccountAbstractExtendedOperation.class) && checkAccess(GetAccountsOperation.class))
			{
				setAccountAbstract(frm);
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		try
		{
			if (checkAccess(GetCardAbstractOperation.class) && checkAccess(GetCardsOperation.class))
			{
				setCardAbstract(frm);
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

	    if (!frm.isShowBanner())
	    {
			if (checkAccess(GetLoanOfferViewOperation.class)) {
				setBottomLoanOffer(frm);
			}

			if (checkAccess(GetLoanCardOfferViewOperation.class)) {
				setBottomLoanCardOffer(frm);
			}
	    }

	    try
	    {
			if (checkAccess("GetIMAccountShortAbstractOperation") && checkAccess("GetIMAccountOperation"))
			{
				setIMAccountAbstract(frm);
			}
	    }
	    catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

	    if(checkAccess(ListPFRClaimOperation.class))
	    {
		    setPfrInfo(frm);
	    }

	    try
	    {
		    if (checkAccess(LoyaltyProgramInfoOperation.class))
		    {
			    setLoyaltyPogram(frm, person);
		    }
	    }
	    catch (InactiveExternalSystemException ignore)
	    {//на главной странице исключения ловим
	    }
        frm.setUser(person);

	    ActionMessages msgs = new ActionMessages();
	    if(frm.isAllCardDown() )
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по картам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(frm.isAllLoanDown())
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по кредитам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(frm.isAllAccountDown())
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по вкладам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(frm.isAllIMAccountDown())
	        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по обезличенным металлическим счетам из АБС временно недоступна. Повторите операцию позже.", false));

	    if(msgs.size()!=0)
	        saveMessages(request, msgs);
	    if (PermissionUtil.impliesService("PixelMarkingService"))
	        request.setAttribute("needPixelMetric", true);

        return mapping.findForward(FORWARD_SHOW);
    }

	private boolean getNeedShowPreRegistrationMessage(SelfRegistrationHelper selfRegistrationHelper)
	{
		return selfRegistrationHelper.getNeedShowPreRegistrationMessage() &&
			   (selfRegistrationHelper.getRegistrationStatus() == RegistrationStatus.EXIST || PermissionUtil.impliesService("ClientChangeLogin")) &&
			   selfRegistrationHelper.checkRegistrationWindowShowCount();
	}

	private boolean getNeedShowPreRegistrationMessageNewDesign(SelfRegistrationHelper selfRegistrationHelper)
	{
		return selfRegistrationHelper.getNeedShowPreRegistrationMessage()
		       && selfRegistrationHelper.getRegistrationStatus() == RegistrationStatus.NOT_EXIST
		       && PermissionUtil.impliesService("ClientChangeLogin")
		       && selfRegistrationHelper.checkRegistrationWindowShowCount();
	}

    protected void getFailOffer(ActivePerson person,Throwable ex){
        log.error("Ошибка при получении предложений, для пользователя "+person.getId(),ex);
    }

    protected void setBottomLoanOffer(ShowAccountsForm frm) throws BusinessException, BusinessLogicException {
        GetLoanOfferViewOperation operation = createOperation(GetLoanOfferViewOperation.class);
        operation.initialize(false);
        frm.setBottomLoanOffers(operation.getLoanOffers());
    }

    protected void setBottomLoanCardOffer(ShowAccountsForm frm) throws BusinessException, BusinessLogicException {
        GetLoanCardOfferViewOperation operation = createOperation(GetLoanCardOfferViewOperation.class);
        operation.initialize(false);
        frm.setBottomLoanCardOffers(operation.getLoanCardOffers());
    }

    protected void setDepoAccounts(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		Calendar start = GregorianCalendar.getInstance();

		GetDepoAccountListOperation operation = createOperation(GetDepoAccountListOperation.class);
		List<DepoAccountLink> links = getPersonDepoAccountLinks(operation);
		form.setDepoAccounts(links);
		if (!links.isEmpty())
		{
			writeLogInformation(start,
								"Получение информации по счетам депо на главной странице",
								com.rssl.phizic.logging.Constants.SHOW_DEPOCCOUNTS_ON_MAIN_PAGE_KEY);
		}

		for (DepoAccountLink link : links)
		{
			if (link.getDepoAccount() instanceof StoredDepoAccount)
			{
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getDepoAccount()));
				break;
			}
		}
	}

	protected void setAccountAbstract(ShowAccountsForm form) throws BusinessException, BusinessLogicException, DataAccessException
	{
		Calendar start = GregorianCalendar.getInstance();

		GetAccountsOperation getAccountsOperation = createOperation(GetAccountsOperation.class);
		GetAccountAbstractExtendedOperation accountAbstractOperation = createOperation(GetAccountAbstractExtendedOperation.class);

		List<AccountLink> accountLinks = getPersonAccountLinks(getAccountsOperation);
		List<AccountLink> showOperationAccountLinks = getAccountsOperation.getShowOperationLinks(accountLinks);

		accountAbstractOperation.initialize(showOperationAccountLinks);
		Map<AccountLink, AccountAbstract> accountAbstract = accountAbstractOperation.getAccountAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);

		List<AccountLink> activeAccounts = new ArrayList<AccountLink>();
		List<AccountLink> closedAccounts = new ArrayList<AccountLink>();
		for (AccountLink link : accountLinks)
		{
			Account account = link.getAccount();
			//если вернулся заглушечный вклад, то считаем что он закрыт, чтобы нормально отображались страницы с вкладами
			if (MockHelper.isMockObject(account) || closedAccountFilter.accept(account))
				closedAccounts.add(link);
			else
				activeAccounts.add(link);
		}

		sortAccountLinks(activeAccounts);
		form.setActiveAccounts(activeAccounts);
		form.setClosedAccounts(closedAccounts);
		form.setAccountAbstract(accountAbstract);
		form.setAccountAbstractErrors(accountAbstractOperation.getAccountAbstractMsgErrorMap());
		form.setAllAccountDown(CollectionUtils.isEmpty(accountLinks) && (accountAbstractOperation.isBackError() || getAccountsOperation.isBackError()));

		if (!accountLinks.isEmpty())
		{
			writeLogInformation(start,
								"Получение остатка по счетам",
								com.rssl.phizic.logging.Constants.SHOW_ACCOUNTS_ON_MAIN_PAGE_KEY);
		}

		for (AccountLink link : accountLinks)
		{
			if (link.getAccount() instanceof StoredAccount)
			{
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getAccount()));
				break;
			}
		}
	}

	protected void setCardAbstract(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		Calendar start = GregorianCalendar.getInstance();

		GetCardsOperation getCardsOperation = createOperation(GetCardsOperation.class);
		GetCardAbstractOperation cardAbstractOperation = createOperation(GetCardAbstractOperation.class);

		List<CardLink> cardLinks = getPersonCardLinks(getCardsOperation);
		List<CardLink> showOperationCardLinks = getCardsOperation.getShowOperationLinks(cardLinks);

		cardAbstractOperation.initialize(showOperationCardLinks);
		Map<CardLink, CardAbstract> cardAbstract = cardAbstractOperation.getCardAbstract(Constants.MAX_COUNT_OF_TRANSACTIONS);

		List<CardLink> activeCards = new ArrayList<CardLink>();
		List<CardLink> blockedCards = new ArrayList<CardLink>();
		for (CardLink link : cardLinks)
		{
			if (extBlockedCardFilter.accept(link.getCard()))
				blockedCards.add(link);
			else
				activeCards.add(link);
		}

		sortCardLinks(activeCards);
		setAdditionalCards(activeCards);
		form.setActiveCards(activeCards);

		sortCardLinks(blockedCards);
		setAdditionalCards(blockedCards);
		form.setBlockedCards(blockedCards);

		form.setCardAbstract(cardAbstract);
		form.setAllCardDown(CollectionUtils.isEmpty(cardLinks) && (cardAbstractOperation.isBackError() || getCardsOperation.isBackError()));

		if (!cardLinks.isEmpty())
		{
			writeLogInformation(start,
								"Получение остатка по картам",
								com.rssl.phizic.logging.Constants.SHOW_CARDS_ON_MAIN_PAGE_KEY);
		}

		for (CardLink link : cardLinks)
		{
			if (link.getCard() instanceof StoredCard)
			{
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getCard()));
				break;
			}
		}
	}

	/**
	 * Сортируем массив карт. Нам нужно, чтобы дополнительные карты были под родительской.
	 * Данная сортировка применяется, только если клиент не сортировал карты самостоятельно (т.е. позиция не определена)
	 * @param cardLinks - линки карт
	 * @return отсортированный массив карт
	 */
	private void sortCardLinks(List<CardLink> cardLinks)
	{
		if (cardLinks.isEmpty())
			return;

		if (cardLinks.get(0).getPositionNumber() != null)
			return;

		Collections.sort(cardLinks, new Comparator<CardLink>()
		{

			public int compare(CardLink o1, CardLink o2)
			{
				boolean arrestedCard1 = o1.getCard().getCardAccountState() == AccountState.ARRESTED;
				boolean arrestedCard2 = o2.getCard().getCardAccountState() == AccountState.ARRESTED;
				String mainCard1 = o1.isMain() ? o1.getNumber() : StringHelper.getEmptyIfNull(o1.getMainCardNumber());
				//Иногда для дополнительных карт не приходит номер основной карты, хотя должен.
				// Для избежания ошибки при сортировке добавлены дополнительные проверки (BUG036571)
				if (StringHelper.isEmpty(mainCard1))
					return -1;
				String mainCard2 = o2.isMain() ? o2.getNumber() : StringHelper.getEmptyIfNull(o2.getMainCardNumber());
				if (StringHelper.isEmpty(mainCard2))
					return 1;
				int res = mainCard1.compareTo(mainCard2);
				if (res == 0)
				{
					if (o1.isMain())
						return -1;
					else if (o2.isMain())
						return 1;
					else
					{
						//арестованные карты располагаем в конце списка карт (для дополнительных)
						if (arrestedCard1 && !arrestedCard2)
						{
							return 1;
						}
						else if (!arrestedCard1 && arrestedCard2)
						{
							return -1;
						}
						else
							return 0;
					}

				}

				//арестованные карты располагаем в конце списка карт (для основных)
				if (arrestedCard1 && !arrestedCard2)
				{
					return 1;
				}
				else if (!arrestedCard1 && arrestedCard2)
				{
					return -1;
				}

				return res;
			}
		});
	}

	private void setAdditionalCards(List<CardLink> cardLinks)
	{
		for (CardLink link : cardLinks)
		{
			if (link.isMain())
			{
				String number = link.getNumber();
				long countAddCards = 0;
				for (CardLink link2 : cardLinks)
				{
					if (!link2.isMain() && number.equals(link2.getMainCardNumber()))
					{
						countAddCards++;
					}
				}
				link.setCountAdditionalCards(countAddCards);
			}
		}
	}

	protected void setLoanAbstract(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		Calendar start = GregorianCalendar.getInstance();

		GetLoanListOperation getLoansOperation = createOperation(GetLoanListOperation.class);

		List<LoanLink> loanLinks = getPersonLoanLinks(getLoansOperation);
		List<LoanLink> activeLoans = new ArrayList<LoanLink>();
		List<LoanLink> blockedLoans = new ArrayList<LoanLink>();
		for (LoanLink link : loanLinks)
		{
			if (activeLoanFilter.accept(link.getLoan()))
				activeLoans.add(link);
			else
				blockedLoans.add(link);
		}

		form.setActiveLoans(activeLoans);
		form.setBlockedLoans(blockedLoans);

		if (!loanLinks.isEmpty())
		{
			writeLogInformation(start,
								"Получение информации по кредитам",
								com.rssl.phizic.logging.Constants.SHOW_LOANS_ON_MAIN_PAGE_KEY);
		}

		for(LoanLink link : loanLinks)
		{
			if (link.getLoan() instanceof AbstractStoredResource)
			{
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getLoan()));
				break;
			}
		}
	}

	protected void setIMAccountAbstract(ShowAccountsForm form) throws BusinessException, BusinessLogicException
	{
		Calendar start = GregorianCalendar.getInstance();

		GetIMAccountOperation getIMAccountsOperation = createOperation(GetIMAccountOperation.class);
		GetIMAccountAbstractOperation imAccountAbstractOperation = createOperation("GetIMAccountShortAbstractOperation");

		List<IMAccountLink> imAccountLinks = getPersonIMAccountLinks(getIMAccountsOperation);
		List<IMAccountLink> showOperationIMAccountLinks = getIMAccountsOperation.getShowOperationLinks(imAccountLinks);

		//Установить 2ой параметр в true когда будет приходить полная выписка
		imAccountAbstractOperation.initialize(showOperationIMAccountLinks, false);
		Map<IMAccountLink, IMAccountAbstract> imAccountAbstract = imAccountAbstractOperation.getIMAccountAbstractMap(Constants.MAX_COUNT_OF_TRANSACTIONS);

		sortIMAccountLinks(imAccountLinks);
		form.setImAccounts(imAccountLinks);
		form.setImAccountAbstract(imAccountAbstract);
		form.setAllIMAccountDown(CollectionUtils.isEmpty(imAccountLinks) && (imAccountAbstractOperation.isBackError() || getIMAccountsOperation.isBackError()));

		if (!imAccountLinks.isEmpty())
		{
			writeLogInformation(start,
								"Получение информации по ОМС.",
								com.rssl.phizic.logging.Constants.SHOW_IMACCOUNTS_ON_MAIN_PAGE_KEY);
		}

		for (IMAccountLink link : imAccountLinks)
		{
			if (link.getImAccount() instanceof AbstractStoredResource)
			{
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getImAccount()));
				break;	
			}
		}
	}

	private void setPfrInfo(ShowAccountsForm form) throws BusinessException
	{
		ListPFRClaimOperation operation = createOperation(ListPFRClaimOperation.class);
		operation.initialize();
		form.setPfrLink(operation.getPFRLink());
		if (operation.isShowOperations())
		{
			form.setPfrClaimsInitialized(true);
			form.setPfrClaims(operation.getPfrClaims(Constants.MAX_COUNT_OF_TRANSACTIONS.intValue()));
			form.setPfrDown(operation.isError());
		}
	}

	protected List<CardLink> getPersonCardLinks(GetCardsOperation operationCards)
	{
		return operationCards.getPersonShowsCardLinks();
	}

	protected List<LoanLink> getPersonLoanLinks(GetLoanListOperation operationLoans)
	{
		return operationLoans.getShowInMainLoans();
	}

	protected List<AccountLink> getPersonAccountLinks(GetAccountsOperation operationAccounts)
	{
		return operationAccounts.getShowsActiveAndClosedAccounts();
	}

	protected List<DepoAccountLink> getPersonDepoAccountLinks(GetDepoAccountListOperation operationAccounts)
	{
		return operationAccounts.getShowInMainDepoAccounts();
	}

	protected List<IMAccountLink> getPersonIMAccountLinks(GetIMAccountOperation operationAccounts)
	{
		return operationAccounts.getPersonShowsInMainIMAccountLinks();
	}

	private void setLoyaltyPogram(ShowAccountsForm frm, ActivePerson person) throws BusinessException,  BusinessLogicException
	{
		LoyaltyProgramInfoOperation operation = createOperation(LoyaltyProgramInfoOperation.class);
		try
		{
			operation.initialize();
			frm.setLoyaltyProgramLink(operation.getEntity());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения программы лояльности ", e);
		}
	}

	/**
	 * Сортировка списка вкладов.
	 * Не сортируем, если клиент изменял позицию самостоятельно.
	 * @param accountLinks - список вкладов
	 */
	private void sortAccountLinks(List<AccountLink> accountLinks)
	{
		if (accountLinks.isEmpty())
			return;

		if (accountLinks.get(0).getPositionNumber() != null)
			return;

		Collections.sort(accountLinks, new Comparator<AccountLink>()
		{
			public int compare(AccountLink o1, AccountLink o2)
			{
				boolean arrestedAccount1 = o1.getAccount().getAccountState().equals(AccountState.ARRESTED);
				boolean arrestedAccount2 = o2.getAccount().getAccountState().equals(AccountState.ARRESTED);

				//арестованные вклады располагаем в конце списка вкладов
				if (arrestedAccount1 && !arrestedAccount2)
				{
					return 1;
				}
				else if (!arrestedAccount1 && arrestedAccount2)
				{
					return -1;
				}

				return 0;
			}
		});
	}

	/**
	 * Сортировка списка ОМС.
	 * Не сортируем, если клиент изменял позицию самостоятельно.
	 * @param imAccountLinks - список ОМС
	 */
	private void sortIMAccountLinks(List<IMAccountLink> imAccountLinks)
	{
		if (imAccountLinks.isEmpty())
			return;

		if (imAccountLinks.get(0).getPositionNumber() != null)
			return;

		Collections.sort(imAccountLinks, new Comparator<IMAccountLink>()
		{
			public int compare(IMAccountLink o1, IMAccountLink o2)
			{
				int stateIMAccount1 = o1.getImAccount().getState().equals(IMAccountState.arrested) ? 1 : o1.getImAccount().getState().equals(IMAccountState.opened) ? -1 : 2;
				int stateIMAccount2 = o2.getImAccount().getState().equals(IMAccountState.arrested) ? 1 : o2.getImAccount().getState().equals(IMAccountState.opened) ? -1 : 2;

				//арестованные ОМС располагаем после активных
				if (stateIMAccount1 > stateIMAccount2)
				{
					return 1;
				}
				else if (stateIMAccount1 < stateIMAccount2)
				{
					return -1;
				}

				return 0;
			}
		});
	}
}
