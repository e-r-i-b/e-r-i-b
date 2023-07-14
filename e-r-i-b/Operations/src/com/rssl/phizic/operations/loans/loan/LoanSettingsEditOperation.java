package com.rssl.phizic.operations.loans.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Nady
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * ќпераци€ редактировани€ настроек дл€ кредитов
 */
public class LoanSettingsEditOperation extends OperationBase
{
	private boolean getCreditAtLogon;

	public void initialize()
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		getCreditAtLogon = config.isGetCreditAtLogon();
	}

	public void save(boolean isGetCreditAtLogon) throws BusinessException, BusinessLogicException
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		config.setGetCreditAtLogon(isGetCreditAtLogon);
		config.save();
	}

	public boolean isGetCreditAtLogon()
	{
		return getCreditAtLogon;
	}
}
