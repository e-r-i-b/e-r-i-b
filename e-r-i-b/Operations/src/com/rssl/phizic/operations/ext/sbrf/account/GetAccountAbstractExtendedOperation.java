package com.rssl.phizic.operations.ext.sbrf.account;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bankroll.ReverseTransactionAbstract;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class GetAccountAbstractExtendedOperation extends GetAccountAbstractOperation
{	
	public AccountAbstract getAccountAbstract() throws BusinessLogicException
	{
		AccountAbstract accountAbstract;
		if (accountLink != null)
		{
			try
			{
				Account account = accountLink.getAccount();
				if(!MockHelper.isMockObject(account))
				{
					accountAbstract = bankrollService.getAccountExtendedAbstract(account, getDateFrom(), getDateTo());
					if ((accountAbstract != null) && (accountAbstract.getTransactions()!=null) && (!accountAbstract.getTransactions().isEmpty()))
						return new ReverseTransactionAbstract(accountAbstract);
					return accountAbstract;
				}
				else
				{
					isBackError=true;
					return null;
				}
			}
			catch(GateException ex)
			{
				isBackError = true;
				log.error("Ошибка при получении выписки по счету №"+accountLink.getNumber(),ex);
				return null;
			}
			catch (GateLogicException e)
			{
				addAccountAbstractMsgError(e.getMessage());
				throw new BusinessLogicException(e);
			}
			catch(InactiveExternalSystemException es)
			{
				log.error(es.getMessage(),es);
				return null;
			}
		}
		return null;
	}

	private void addAccountAbstractMsgError(String messageError)
	{
		accountAbstractMsgErrorMap.put(accountLink,messageError);
	}

	public DateSpan getPeriod() throws BusinessException, BusinessLogicException
	{
		Account account = accountLink.getAccount();

		if(!MockHelper.isMockObject(account))
		{
			Calendar closeDate = account.getCloseDate();
			if(closeDate!=null)
			{
				return new DateSpan(DateHelper.getCurrentDate(),closeDate);
			}
			else
			{
				return null;
			}
		}

		return null;
	}
}
