package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.common.types.Money;

/**
 * @author mihaylov
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Инициализация портфелей клиента
 */
public class InitPersonPortfolioListHandler extends PersonalFinanceProfileHandlerBase
{
	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		profile.clearPortfolioList();
		
		PersonPortfolio startCapital = new PersonPortfolio(PortfolioType.START_CAPITAL);
		try
		{
			Money startCapitalAmount = profile.getMediumTermAssets().add(profile.getShortTermAssets());
			startCapital.setStartAmount(startCapitalAmount);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		profile.addPortfolio(startCapital);

		PersonPortfolio quarterlyInvestments = new PersonPortfolio(PortfolioType.QUARTERLY_INVEST);
		Money quarterlyInvestmentsAmount = profile.getFreeMoney().multiply(3);
		quarterlyInvestments.setStartAmount(quarterlyInvestmentsAmount);
		profile.addPortfolio(quarterlyInvestments);
	}
}
