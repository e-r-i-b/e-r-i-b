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

	//���� �� ��������� ������
	private boolean isBackError = false;

	/**
	 * ��������� ���������� �� ������ �������.
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
			log.error("������ ��������� ������ ������ �� ��", e);
			return null;
		}
		catch (BusinessLogicException e)
		{
			log.error("������ ��������� ������ ������ �� ��", e);
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
	 * �������� ����� �������� �������/������, ������� ����� ���������� �� ������� ��������
	 * @return ������ AccountLink
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
	 * ����� ��� �������� �������� � �������� �������/������
	 * @param onlyShowInMain ���� ���������� �� ������������ ��������� ������ ������ � ����� ������� ����� ���������� �� ������� ��������
	 * @return
	 */
	private List<AccountLink> getActiveAndClosedAccounts(boolean onlyShowInMain)
	{
		List<AccountLink> accounts = new ArrayList<AccountLink>();

		for (AccountLink accountLink : getAccounts())
		{
			// ���� �� ����� ���������� ���������� �� ����� �����, ���� �� �������� �������� ��
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
	 * �������� ����� �������� � �������� �������/������, ������� ����� ���������� �� ������� ��������
	 * @return ������ AccountLink
	 */
	public List<AccountLink> getShowsActiveAndClosedAccounts()
	{
		List<AccountLink> accountLinks = getAccounts();
		return getShowInMainLinks(accountLinks);
	}

	/**
	 * �������� ��� ����� �������� � �������� �������/������
	 * @return
	 */
	public List<AccountLink> getAllActiveAndClosedAccounts()
	{
		return getActiveAndClosedAccounts(false);
	}

	/**
	 * ���� �� ������ ��������� � ���-������
	 * @return
	 */	
	public boolean isBackError()
	{
		return isBackError;
	}

	/**
	 * ����� ��� ��������� ������ �������� �������, ��������� � ���-������
	 * @return ������ �������
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
	 * ��������� ������ �������� �������, �������� �� ��������� � mAPI
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
	 * ������ ������ ��������� ���������� ��� ������� "��������"
	 * @param accountNumber - ����� ������
	 */
	public void setAccountLinkFalseClosedState(String accountNumber)
	{
		try
		{
			resourceService.setAccountLinksFalseClosedState(accountNumber);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ���������� ������� ������ ��������� � �������� �������", e);
		}
	}
}
