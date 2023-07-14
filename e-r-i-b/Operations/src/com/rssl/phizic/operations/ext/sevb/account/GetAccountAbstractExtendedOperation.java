package com.rssl.phizic.operations.ext.sevb.account;

import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

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
		try
		{
			return bankrollService.getAccountExtendedAbstract(getAccount().getAccount(), getDateFrom(), getDateTo());
		}
		catch(GateException ex)
		{
			isBackError = true;
			log.error("Ошибка при получении выписки",ex);
			return null;
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
