package com.rssl.phizic.business.accounts;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.PensionPlusNotClosedNotBlockedAccountFilter;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Calendar;

/**
 * @autor akrenev
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountsUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	public static final Calendar DEMAND_ACCOUNTS_END_DATE = XMLDatatypeHelper.parseDateTime("1800-01-01");
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final DepartmentService departmentService = new DepartmentService();

	/**
	 * Возвращает аккаунт-линк по номеру счёта
	 * @param accountNumber - номер счёта
	 * @return аккаунт-линк либо null
	 */
	public static AccountLink getAccountLink(String accountNumber)
	{
		if (StringHelper.isEmpty(accountNumber))
			return null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			AccountLink accountLink = personData.findAccount(accountNumber);
			if (accountLink == null)
				log.warn("Не могу найти account-link по номеру " + accountNumber);

			return accountLink;
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска аккаунт-линка", ex);
			return null;
		}
	}

	/**
	 * Возвращает аккаунт-линк по id и логину
	 * @param accountId - id счёта
	 * @param login - логин пользователя
	 * @return аккаунт-линк либо null
	 */
	public static AccountLink getAccountLinkById(Long accountId, Login login)
	{
		try
		{
			AccountLink accountLink = externalResourceService.findInSystemLinkById(login, AccountLink.class, accountId);
			if (accountLink == null)
				log.warn("Не могу найти account-link для id " + accountId + " и логина " + login);

			return accountLink;
		}
		catch (Exception e)
		{
			log.error("Ошибка поиска аккаунт-линка", e);
			return null;
		}
	}

	/**
	 *
	 * @param accountNumber номер счета
	 * @return отформатированный номер счета
	 */
	public static String getFormattedAccountNumber(String accountNumber)
	{
		try
		{
			if (StringHelper.isEmpty(accountNumber))
				return "";

			AccountsConfig accountsConfig = ConfigFactory.getConfig(AccountsConfig.class);

			String cardAccountMask = accountsConfig.getAccountNumberMask();
			String cardAccountRegexp = accountsConfig.getAccountNumberRegexp();

			return accountNumber.replaceAll(cardAccountRegexp, cardAccountMask);
		}
		catch (Exception e)
		{
			log.error("Ошибка маскирования номера счета", e);
			return "";
		}
	}

	public static String getInterestTransferCard(String accountId)
	{
		if (StringHelper.isEmpty(accountId))
			return null;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			AccountLink accountLink = personData.getAccount(Long.valueOf(accountId));

			return accountLink.getAccount().getInterestTransferCard();
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска аккаунт-линка", ex);
			return null;
		}
	}

	public static String getInterestTransferAccount(String accountId)
	{
		if (StringHelper.isEmpty(accountId))
			return null;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			AccountLink accountLink = personData.getAccount(Long.valueOf(accountId));

			return accountLink.getAccount().getInterestTransferAccount();
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска аккаунт-линка", ex);
			return null;
		}
	}

	/**
	 * Являетcя ли счет Пенсионным Плюс (вид 34), не закрыт и не заблокирован
	 * @param accountId ИД счета
	 * @return
	 */
	public static boolean isPensionPlusNotClosedNotBlockedAccount(String accountId)
	{
		if (StringHelper.isEmpty(accountId))
			return false;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			AccountLink accountLink = personData.getAccount(Long.valueOf(accountId));
			PensionPlusNotClosedNotBlockedAccountFilter filter = new PensionPlusNotClosedNotBlockedAccountFilter();

			return filter.accept(accountLink);
		}
		catch (Exception ex)
		{
			log.error("Ошибка поиска аккаунт-линка", ex);
			return false;
		}
	}

	/**
	 * Проверяет, достаточно ли денег на счете для списания
	 * @param accountLink - счет для списания
	 * @param chargeOffAmount - сумма списания
	 * @return true, если денег достаточно
	 * @throws BusinessException
	 */
	public static boolean hasAvailableMoney(AccountLink accountLink,  Money chargeOffAmount) throws BusinessException
	{
		if (chargeOffAmount == null || chargeOffAmount.isZero())
			return true;

		Money balance = accountLink.getRest();

		if (balance == null)
		{
			throw new IllegalArgumentException("Не задан остаток источника списания для проверки. ID счета: " + accountLink.getId());
		}

		if (!balance.getCurrency().compare(chargeOffAmount.getCurrency()))
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			CurrencyRateService service = GateSingleton.getFactory().service(CurrencyRateService.class);
			CurrencyRate rate = null;
			try
			{
				rate = service.convert(balance, chargeOffAmount.getCurrency(), departmentService.findById(person.getDepartmentId()), person.getTarifPlanCodeType());
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
			if (rate == null)
				return false;
			balance = new Money(rate.getToValue(), rate.getToCurrency());
		}

		return balance.compareTo(chargeOffAmount) >= 0;
	}

	/**
	 * Получение краткого представления номера счета (карты)
	 * @param number - номер карты
	 * @return
	 */
	public static String getShortAccountNumber(String number)
	{
		return "№..." + MaskUtil.cutStringByLastSevenSymbols(number);
	}
}
