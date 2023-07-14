package com.rssl.phizic.business.ermb.bankroll;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductAvailabilityType;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRulesConfig;
import com.rssl.phizic.business.ermb.bankroll.config.ClientCategoryType;
import com.rssl.phizic.business.ermb.products.ErmbNotificationSettingsController;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.person.Person;

import java.util.List;

/**
 * Сервис для проверки и применения правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 12.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRulesService
{

	private final static SimpleService simpleService = new SimpleService();

	/**
	 * проверяет условия правил и применяет правила включения видимости продуктов по умолчанию к профилю
	 * @param profile
	 * @throws BusinessException
	 */
	public void checkAndApplyRulesToProfile(ErmbProfileImpl profile) throws BusinessException
	{
		List<BankrollProductRule> rules = ConfigFactory.getConfig(BankrollProductRulesConfig.class).getBankrollProductRules();

		for (BankrollProductRule rule : rules)
		{
			if (!rule.isActive())
				continue;

			if (checkRuleCondition(rule, profile))
			{
				applyRuleToProfile(rule, profile);
				break;
			}
		}
	}

	private boolean checkRuleCondition(BankrollProductRule rule, ErmbProfileImpl profile) throws BusinessException
	{
		Person person = profile.getPerson();

		if (checkClientCategoryCondition(rule, person))
		{
			if (checkAgeCondition(rule, person))
			{
				if (checkProductsAvailabilityCondition(rule, profile))
				{
					if (checkTerbanksCondition(rule, person))
						return true;
				}
			}
		}
		return false;
	}


	private boolean checkClientCategoryCondition(BankrollProductRule rule, Person person)
	{
		ClientCategoryType clientCategory = rule.getClientCategory();
		SegmentCodeType personSegmentCodeType = person.getSegmentCodeType();

		return (clientCategory == ClientCategoryType.UNIMPORTANT)
				|| (clientCategory == ClientCategoryType.STANDART && (personSegmentCodeType == null || personSegmentCodeType == SegmentCodeType.NOTEXISTS))
				|| (clientCategory == ClientCategoryType.VIP && personSegmentCodeType == SegmentCodeType.VIP)
				|| (clientCategory == ClientCategoryType.MBC && personSegmentCodeType == SegmentCodeType.MVC);
	}

	private boolean checkAgeCondition(BankrollProductRule rule, Person person)
	{
		boolean rc = true;

		Integer age = PersonHelper.getPersonAge(person);
		Integer ageFrom = rule.getAgeFrom();
		Integer ageUntil = rule.getAgeUntil();

		if (ageFrom != null)
			rc = rc && age >= ageFrom;
		if (ageUntil != null)
			rc = rc && age <= ageUntil;

		return rc;
	}

	private boolean checkProductsAvailabilityCondition(BankrollProductRule rule, ErmbProfileImpl profile)
	{
		boolean creditCardsCondition = creditCardsAvailabilityCondition(rule.getCreditCardCriteria(), profile.getCardLinks());
		boolean depositsCondition = depositsAvailabilityCondition(rule.getDepositCriteria(), profile.getAccountLinks());
		boolean loansCondition = loansAvailabilityCondition(rule.getLoanCriteria(), profile.getLoanLinks());

		return creditCardsCondition && depositsCondition && loansCondition;
	}

	private boolean creditCardsAvailabilityCondition(BankrollProductAvailabilityType creditCardCriteria, List<CardLink> cardLinks)
	{
		boolean creditCardsCondition;

		if (creditCardCriteria == BankrollProductAvailabilityType.UNIMPORTANT)
			creditCardsCondition = true;
		else
		{
			boolean creditCardAvailable = false;
			for (CardLink cardLink : cardLinks)
			{
				if (cardLink.getCard().getCardType() == CardType.credit)
					creditCardAvailable = true;
			}

			creditCardsCondition = (creditCardAvailable && creditCardCriteria == BankrollProductAvailabilityType.AVAILABLE) || (!creditCardAvailable && creditCardCriteria == BankrollProductAvailabilityType.UNAVAILABLE);
		}

		return creditCardsCondition;
	}

	private boolean depositsAvailabilityCondition(BankrollProductAvailabilityType depositsCriteria, List<AccountLink> accountLinks)
	{
		boolean depositsCondition;

		if (depositsCriteria == BankrollProductAvailabilityType.UNIMPORTANT)
			depositsCondition = true;
		else
		{
			boolean depositsAvailable = false;

			for (AccountLink accountLink : accountLinks)
			{
				if (accountLink.isDeposit())
					depositsAvailable = true;
			}

			depositsCondition = (depositsAvailable && depositsCriteria == BankrollProductAvailabilityType.AVAILABLE) || (!depositsAvailable && depositsCriteria == BankrollProductAvailabilityType.UNAVAILABLE);
		}

		return depositsCondition;
	}

	private boolean loansAvailabilityCondition(BankrollProductAvailabilityType loansCriteria, List<LoanLink> loanLinks)
	{
		boolean loansCondition;

		if (loansCriteria == BankrollProductAvailabilityType.UNIMPORTANT)
			loansCondition = true;
		else
		{
			boolean loansAvailable = false;
			if (!loanLinks.isEmpty())
				loansAvailable = true;

			loansCondition = (loansAvailable && loansCriteria == BankrollProductAvailabilityType.AVAILABLE) || (!loansAvailable && loansCriteria == BankrollProductAvailabilityType.UNAVAILABLE);
		}

		return loansCondition;
	}


	private boolean checkTerbanksCondition(BankrollProductRule rule, Person person) throws BusinessException
	{
		List<String> terbankCodes = rule.getTerbankCodes();
		DepartmentService departmentService = new DepartmentService();
		Department personTB = departmentService.getTB(person.getDepartmentId());
		String personTBCode = personTB.getCode().getFields().get("region");
		if (terbankCodes.contains(personTBCode))
			return true;
		return false;
	}

	@Transactional
	private void applyRuleToProfile(BankrollProductRule rule, ErmbProfileImpl profile) throws BusinessException
	{
		ErmbTariff ermbTariff = rule.getErmbTariff();
		ErmbHelper.updateErmbTariff(profile, ermbTariff);

		//Выставить по правилам
		List<CardLink> cardLinks = profile.getCardLinks();
		for (CardLink cardLink : cardLinks)
		{
			cardLink.setShowInSms(rule.isCardsVisibility());
			cardLink.setErmbNotification(rule.isCardsNotification());
		}
		simpleService.addOrUpdateList(cardLinks);

		List<AccountLink> accountLinks = profile.getAccountLinks();
		for (AccountLink accountLink : accountLinks)
		{
			accountLink.setShowInSms(rule.isDepositsVisibility());
			accountLink.setErmbNotification(rule.isDepositsNotification());
		}
		simpleService.addOrUpdateList(accountLinks);

		List<LoanLink> loanLinks = profile.getLoanLinks();
		for (LoanLink loanLink : loanLinks)
		{
			loanLink.setShowInSms(rule.isLoansVisibility());
			loanLink.setErmbNotification(rule.isLoansNotification());
		}

		simpleService.addOrUpdateList(loanLinks);

		//Отключить запрещенные для текущего тарифа
		ErmbNotificationSettingsController notificationController = new ErmbNotificationSettingsController(profile);
		notificationController.disableUnsupported();

		profile.setNewProductShowInSms(rule.isNewProductsVisibility());
		profile.setNewProductNotification(rule.isNewProductsNotification());
	}

}
