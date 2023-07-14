package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfoliosCondition;

import java.util.List;

/**
 * @author mihaylov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpPortfolioListForm extends EditPersonalFinanceProfileForm
{
	private List<PersonPortfolio> portfolioList; // список портфелей клиента
	private PersonPortfoliosCondition portfoliosState;

	public List<PersonPortfolio> getPortfolioList()
	{
		return portfolioList;
	}

	public void setPortfolioList(List<PersonPortfolio> portfolioList)
	{
		this.portfolioList = portfolioList;
	}

	public PersonPortfoliosCondition getPortfoliosState()
	{
		return portfoliosState;
	}

	public void setPortfoliosState(PersonPortfoliosCondition portfoliosState)
	{
		this.portfoliosState = portfoliosState;
	}
}
