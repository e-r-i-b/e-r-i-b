package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.securityAccount.SecurityAccountUtil;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.operations.securities.ListSecuritiesOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.web.util.MoneyFunctions;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.AbstractFinanceConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.AccountTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.CardTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.IMAccountTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.SecurityAccountTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfGraphicDataRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfGraphicDataRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.AlfGraphicDataResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.CardTypeWebAPI;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Balovtsev
 * @since 16.05.2014
 */
public class AlfGraphicDataConstructor extends AbstractFinanceConstructor<AlfGraphicDataRequest, AlfGraphicDataResponse>
{
	public static final String FIELD_SHOW_CENT = "showCent";
	public static final String SHOW_WITH_OVERDRAFT = "showWithOverdraft";
	public static final String FIELD_CARDS = "cards";
	public static final String FIELD_ACCOUNTS = "accounts";
	public static final String FIELD_IMACCOUNTS = "imaccounts";
	public static final String FIELD_SECURITY_ACCOUNTS = "securityAccounts";
	private static final String CARDS_URL = "/private/cards/info.do?id=%d";
	private static final String ACCOUNTS_URL = "/private/accounts/operations.do?id=%d";
	private static final String IMACCOUNTS_URL = "/private/ima/info.do?id=%d";
	private static final String SECURITY_ACCOUNTS_URL = "/private/security/view.do?id=%d";
	public static final char DECIMAL_SEPARATOR = '.';
	// Согласно РО курс валюты должен быть относительно 99 ТБ
	private static final String REGION_ID = "99";
	private static final DepartmentService departmentService = new DepartmentService();

	private boolean isCardsBackError = false;
	private boolean isAccountsBackError = false;
	private boolean isIMAccountsBackError = false;
	private boolean isSecuriryAccountsBackError = false;

	@Override
	protected MapValuesSource getMapValueSource(AlfGraphicDataRequest request) throws Exception
	{
		AlfGraphicDataRequestBody body = request.getBody();

		Map<String, Object> sources = new HashMap<String, Object>();
		sources.put(FIELD_SHOW_CENT, body.getShowKopeck());
		sources.put(SHOW_WITH_OVERDRAFT, body.getShowWithOverdraft());
		sources.put(FIELD_CARDS, getCards());
		sources.put(FIELD_ACCOUNTS, getAccounts());
		sources.put(FIELD_IMACCOUNTS, getIMAccounts());
		sources.put(FIELD_SECURITY_ACCOUNTS, getSecurityAccountLinks());

		return new MapValuesSource(sources);
	}

	@Override
	protected AlfGraphicDataResponse doMakeResponse(Map<String, Object> sources) throws Exception
	{
		boolean showCent = (Boolean) sources.get(FIELD_SHOW_CENT);
		boolean showWithOverdraft = (Boolean) sources.get(SHOW_WITH_OVERDRAFT);
		String tarifPlanCodeType = TariffPlanHelper.getUnknownTariffPlanCode();
		if (PermissionUtil.impliesService("ReducedRateService"))
			tarifPlanCodeType = PersonHelper.getActivePersonTarifPlanCode();
		AlfGraphicDataResponse response = new AlfGraphicDataResponse();
		Map<String, String> errors = new HashMap<String, String>();
		Office office = departmentService.findByCode(new ExtendedCodeImpl(REGION_ID, null, null));
		Office clientOffice = departmentService.findById(PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId());
		List<CardLink> cardLinks = (List<CardLink>) sources.get(FIELD_CARDS);
		if (cardLinks.isEmpty() && isCardsBackError)
			errors.put(NO_CARD_PRODUCT_MESSAGE, null);
		else
			response.setCards(makeCards(showCent, cardLinks, tarifPlanCodeType, office, showWithOverdraft));

		List<AccountLink> accountLinks = (List<AccountLink>) sources.get(FIELD_ACCOUNTS);
		if (accountLinks.isEmpty() && isAccountsBackError)
			errors.put(NO_ACCOUNT_PRODUCT_MESSAGE, null);
		else
			response.setAccounts(makeAccounts(showCent, accountLinks, tarifPlanCodeType, office));

		List<IMAccountLink> imAccountLinks = (List<IMAccountLink>) sources.get(FIELD_IMACCOUNTS);
		if (imAccountLinks.isEmpty() && isIMAccountsBackError)
			errors.put(NO_IMA_PRODUCT_MESSAGE, null);
		else
			response.setImaccounts(makeImaccounts(showCent, imAccountLinks, tarifPlanCodeType, clientOffice));

		List<SecurityAccountLink> securityAccountLinks = (List<SecurityAccountLink>) sources.get(FIELD_SECURITY_ACCOUNTS);
		if (securityAccountLinks.isEmpty() && isSecuriryAccountsBackError)
			errors.put(NO_SECURITY_ACCOUNTS_MESSAGE, null);
		else
			response.setSecurityAccounts(makeSecurityAccounts(showCent, securityAccountLinks, tarifPlanCodeType, office));

		if (CollectionUtils.isEmpty(response.getCards()) &&
				CollectionUtils.isEmpty(response.getAccounts()) &&
				CollectionUtils.isEmpty(response.getImaccounts()) &&
				CollectionUtils.isEmpty(response.getSecurityAccounts()))
		{
			errors.put(EMPTY_RESOURCE_MESSAGE, null);
			response.setStatus(new Status(StatusCode.ERROR, errors));
			return response;
		}

		if (!errors.isEmpty())
			response.setStatus(new Status(StatusCode.GROUP_OPERATION_ERROR, errors));

		return response;
	}

	private List<IMAccountTag> makeImaccounts(boolean showCent, List<IMAccountLink> imAccountLinks, String tarifPlanCodeType, Office office) throws BusinessLogicException, BusinessException
	{
		List<IMAccountTag> imAccounts = new ArrayList<IMAccountTag>();

		for (IMAccountLink link : imAccountLinks)
		{
			String rurbalance = getFormattedBalance(MoneyUtil.getBalanceInNationalCurrency(link.getImAccount().getBalance(), MoneyUtil.getNationalCurrency(), office, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType).getDecimal(), showCent);
			IMAccountTag imAccountTag = new IMAccountTag(link.getId(), rurbalance, link.getName(), AccountsUtil.getFormattedAccountNumber(link.getNumber()));

			if (link.getImAccount().getBalance() != null)
			{
				imAccountTag.setBalance(new MoneyTag(link.getImAccount().getBalance()));
			}
			imAccountTag.setUrl(LinkUtils.createRedirectUrl(IMACCOUNTS_URL, null, link.getId()));
			imAccounts.add(imAccountTag);
		}

		if (CollectionUtils.isEmpty(imAccounts))
		{
			return null;
		}

		return imAccounts;
	}

	private List<AccountTag> makeAccounts(boolean showCent, List<AccountLink> accountLinks, String tarifPlanCodeType, Office office) throws BusinessLogicException, BusinessException
	{
		List<AccountTag> accounts = new ArrayList<AccountTag>();

		for (AccountLink link : accountLinks)
		{
			String rurbalance = getFormattedBalance(MoneyUtil.getBalanceInNationalCurrency(link.getAccount().getBalance(), MoneyUtil.getNationalCurrency(), office, CurrencyRateType.CB, tarifPlanCodeType).getDecimal(), showCent);
			AccountTag accountTag = new AccountTag(link.getId(), rurbalance, link.getName(), AccountsUtil.getFormattedAccountNumber(link.getNumber()));

			if (link.getMaxSumWrite() != null)
			{
				accountTag.setMaxSumWrite(getFormattedBalance(link.getMaxSumWrite().getDecimal(), showCent));
			}

			if (link.getAccount().getBalance() != null)
			{
				accountTag.setBalance(new MoneyTag(link.getAccount().getBalance()));
			}
			accountTag.setUrl(LinkUtils.createRedirectUrl(ACCOUNTS_URL, null, link.getId()));

			accounts.add(accountTag);
		}

		if (CollectionUtils.isEmpty(accounts))
		{
			return null;
		}

		return accounts;
	}

	private List<CardTag> makeCards(boolean showCent, List<CardLink> cardLinks, String tarifPlanCodeType, Office office, boolean showWithOverdraft) throws BusinessLogicException, BusinessException
	{
		List<CardTag> cards = new ArrayList<CardTag>();
		for (CardLink link : cardLinks)
		{
			Card card = link.getCard();
			String rurbalance;
			BigDecimal availableLimit = MoneyUtil.getBalanceInNationalCurrency(card.getAvailableLimit(), MoneyUtil.getNationalCurrency(), office, CurrencyRateType.CB, tarifPlanCodeType).getDecimal();
			if (showWithOverdraft || card.getCardType() == CardType.debit && link.getCard().getOverdraftLimit() == null)
				rurbalance = getFormattedBalance(availableLimit, showCent);
			else
			{
				BigDecimal currentOverdraftLimit = MoneyUtil.getBalanceInNationalCurrency(link.getCard().getOverdraftLimit(), MoneyUtil.getNationalCurrency(), office, CurrencyRateType.CB, tarifPlanCodeType).getDecimal();
				rurbalance = getFormattedBalance(availableLimit.subtract(currentOverdraftLimit), showCent);
			}
			CardTag cardTag = new CardTag(link.getId(), rurbalance, CardTypeWebAPI.valueOf(card.getCardType().name()), link.getName(), MaskUtil.getCutCardNumber(link.getNumber()));

			if (card.getAvailableLimit() != null)
			{
				cardTag.setAvailableLimit(new MoneyTag(card.getAvailableLimit()));
			}
			cardTag.setUrl(LinkUtils.createRedirectUrl(CARDS_URL, null, link.getId()));
			cards.add(cardTag);
		}

		if (CollectionUtils.isEmpty(cards))
		{
			return null;
		}

		return cards;
	}

	private List<SecurityAccountTag> makeSecurityAccounts(boolean showCent, List<SecurityAccountLink> securityAccountLinks, String tarifPlanCodeType, Office office) throws BusinessLogicException, BusinessException
	{
		List<SecurityAccountTag> securityAccountTags = new ArrayList<SecurityAccountTag>();

		for (SecurityAccountLink link : securityAccountLinks)
		{
			Money securityAmount = SecurityAccountUtil.getSecurityAmount(link.getSecurityAccount());
			if (securityAmount == null)
			{
				continue;
			}

			String rurbalance = getFormattedBalance(MoneyUtil.getBalanceInNationalCurrency(securityAmount, MoneyUtil.getNationalCurrency(), office, CurrencyRateType.CB, tarifPlanCodeType).getDecimal(), showCent);
			SecurityAccountTag securityAccountTag = new SecurityAccountTag(link.getId(), rurbalance, link.getName(), AccountsUtil.getFormattedAccountNumber(link.getNumber()));
			securityAccountTag.setBalance(new MoneyTag(securityAmount));
			securityAccountTag.setUrl(LinkUtils.createRedirectUrl(SECURITY_ACCOUNTS_URL, null, link.getId()));
			securityAccountTags.add(securityAccountTag);
		}

		if (CollectionUtils.isEmpty(securityAccountTags))
		{
			return null;
		}

		return securityAccountTags;
	}

	private List<CardLink> getCards() throws BusinessException
	{
		if (!checkAccess(GetCardsOperation.class))
		{
			return Collections.emptyList();
		}

		GetCardsOperation operation = createOperation(GetCardsOperation.class);
		List<CardLink> cards = operation.getPersonCardLinks(false, true);

		if (operation.isBackError())
		{
			isCardsBackError = true;
			return Collections.emptyList();
		}

		List<String> mainCardNumbers = new ArrayList<String>();

		for (CardLink card : cards)
		{
			if (card.isMain() && card.isActive())
			{
				mainCardNumbers.add(card.getCard().getNumber());
			}
		}

		List<CardLink> values = new ArrayList<CardLink>();
		for (CardLink link : cards)
		{
			AdditionalCardType addType = link.getCard().getAdditionalCardType();

			if ((AdditionalCardType.CLIENTTOCLIENT != addType && AdditionalCardType.CLIENTTOOTHER != addType) || !mainCardNumbers.contains(link.getMainCardNumber()))
			{
				values.add(link);
			}
		}

		return values;
	}

	public List<AccountLink> getAccounts() throws BusinessException
	{
		if (!checkAccess(GetAccountsOperation.class))
		{
			return Collections.emptyList();
		}

		GetAccountsOperation operation = createOperation(GetAccountsOperation.class);
		List<AccountLink> accounts = operation.getActiveAccounts();

		if (operation.isBackError())
		{
			isAccountsBackError = true;
			return Collections.emptyList();
		}

		return accounts;
	}

	public List<IMAccountLink> getIMAccounts() throws BusinessException
	{
		if (!checkAccess(GetIMAccountOperation.class))
		{
			return Collections.emptyList();
		}

		GetIMAccountOperation operation = createOperation(GetIMAccountOperation.class);
		List<IMAccountLink> imaccounts = operation.getActiveIMAccounts();

		if (operation.isBackError())
		{
			isIMAccountsBackError = true;
			return Collections.emptyList();
		}

		return imaccounts;
	}

	protected List<SecurityAccountLink> getSecurityAccountLinks() throws BusinessException, BusinessLogicException
	{
		if (!checkAccess(ListSecuritiesOperation.class))
			return Collections.emptyList();

		ListSecuritiesOperation operation = createOperation(ListSecuritiesOperation.class);
		operation.initialize();
		List<SecurityAccountLink> securityAccountLinks = operation.getSecurityAccountLinksInSystem();
		if (operation.isBackError())
		{
			isSecuriryAccountsBackError = true;
			return Collections.emptyList();
		}

		return securityAccountLinks;
	}

	private String getFormattedBalance(final BigDecimal balance, boolean showCent)
	{
		if (showCent)
		{
			return MoneyFunctions.formatAmount(balance, 2, DECIMAL_SEPARATOR, false);
		}
		else
		{
			return MoneyFunctions.formatAmount(balance, 0, DECIMAL_SEPARATOR, false);
		}
	}
}
