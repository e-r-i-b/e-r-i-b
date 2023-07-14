package com.rssl.phizic.operations.account;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.link.GetExternalResourceLinkOperation;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 13.10.2005 Time: 15:49:27 */
public class GetAccountsOperation extends GetExternalResourceLinkOperation<AccountFilter>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static ExternalResourceService resourceService = new ExternalResourceService();

	//была ли заглушена ошибка
	private boolean isBackError = false;

	/**
	 * Получение информации по счетам клиента.
	 *
	 * @return
	 * @throws BusinessException
	 */
	public List<AccountLink> getAccounts()
	{
		try
		{
			return getPersonData().getAccounts();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка счетов из БД", e);
			return null;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка получения списка счетов из БД", e);
			return null;
		}
	}

	public  List<AccountLink> getActiveAccounts()
	{
		ArrayList<AccountLink> accounts = new ArrayList<AccountLink>();
		for (AccountLink accountLink: getAccounts())
		{
			try {
				if ( (new ActiveAccountFilter()).accept(accountLink.getAccount()) )
					accounts.add(accountLink);
			}
			catch (TemporalBusinessException e)
			{
				isBackError = true;
			}
		}
		return accounts;
	}

	/**
	 * Получить линки активных вкладов/счетов, которые нужно отображать на главной странице
	 * @return список AccountLink
	 */
	public List<AccountLink> getShowsActiveAccounts()
	{
		List<AccountLink> accounts = new ArrayList<AccountLink>();
		for (AccountLink accountLink : getAccounts())
		{
			try
			{
				Account account = accountLink.getAccount();
				if (!isUseStoredResource())
				{
					setUseStoredResource(account instanceof StoredAccount);
				}

				if (accountLink.getShowInMain() && (new ActiveAccountFilter()).accept(account))
					accounts.add(accountLink);
			}
			catch (TemporalBusinessException e)
			{
				isBackError = true;
			}
		}
		return accounts;
	}

	/**
	 * Метод для получени активных и закрытых вкладов/счетов
	 * @param onlyShowInMain флаг отыечающий за необхдимость получения только вклады и счета которые нужно отображать на главной странице
	 * @return
	 */
	private List<AccountLink> getActiveAndClosedAccounts(boolean onlyShowInMain)
	{
		List<AccountLink> accounts = new ArrayList<AccountLink>();

		for (AccountLink accountLink : getAccounts())
		{
			// Если не нужно отображать информацию по этому линку, даже не пытаемся получать ее
			if (accountLink.getShowInMain() || !onlyShowInMain)
			{
					Account account = accountLink.getAccount();
					if (MockHelper.isMockObject(account))
						isBackError = true;

					accounts.add(accountLink);
			}
		}
		return accounts;
	}

	/**
	 * Получить линки активных и закрытых вкладов/счетов, которые нужно отображать на главной странице
	 * @return список AccountLink
	 */
	public List<AccountLink> getShowsActiveAndClosedAccounts()
	{
		List<AccountLink> accountLinks = getAccounts();
		return getShowInMainLinks(accountLinks);
	}

	/**
	 * Получить все линки активных и закрытых вкладов/счетов
	 * @return
	 */
	public List<AccountLink> getAllActiveAndClosedAccounts()
	{
		return getActiveAndClosedAccounts(false);
	}

	/**
	 * была ли ошибка связанная с бэк-офисом
	 * @return
	 */	
	public boolean isBackError()
	{
		return isBackError;
	}

	/**
	 * Метод для получения линков открытых вкладов, доступных в смс-канале
	 * @return список вкладов
	 */
	public List<AccountLink> getErmbActiveAccounts() throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accounts = new ArrayList<AccountLink>();
		for (AccountLink accountLink: getPersonData().getAccountsAll())
		{
			if (accountLink.getShowInSystem() && accountLink.getShowInSms() && accountLink.getAccount().getAccountState() != AccountState.CLOSED)
				accounts.add(accountLink);
		}
		return accounts;
	}

	/**
	 * Получение линков активных вкладов, учитывая их видимость в mAPI
	 * @return
	 */
	public List<AccountLink> getActiveShowInMobileAccounts()
	{
		ArrayList<AccountLink> accounts = new ArrayList<AccountLink>();
		for (AccountLink accountLink: getAccounts())
		{
			if ( (new ActiveAndShowInMobileLinkFilter()).accept(accountLink) )
				accounts.add(accountLink);
		}
		return accounts;
	}

	/**
	 * Задать статус сообщения осзакрытии для вкладов "показано"
	 * @param accountNumber - номер вклада
	 */
	public void setAccountLinkFalseClosedState(String accountNumber)
	{
		try
		{
			resourceService.setAccountLinksFalseClosedState(accountNumber);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при обновлении статуса показа сообщения о закрытии вкладов", e);
		}
	}
}
