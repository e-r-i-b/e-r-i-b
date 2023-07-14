package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioState;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * @author lepihina
 * @ created 24.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangeStartAmountOperation extends NotCheckStateEditPfpOperationBase
{
	private PersonPortfolio portfolio;
	
	public void initialize(Long profileId, Long portfolioId) throws BusinessException, BusinessLogicException
	{
		initializePerson();
		if (profileId == null || profileId == 0)
			throw new IllegalArgumentException("������������� ������������ �� ����� ���� null");

		personalFinanceProfile = pfpService.getProfileById(profileId);

		if (personalFinanceProfile == null)
			throw new BusinessLogicException("������������ � id=" + profileId + " �� �������.");

		if (!person.getLogin().getId().equals(personalFinanceProfile.getOwner().getId()))
			throw new AccessException("������ � id = " + person.getId() + " �� ����� ������� � ��� � id = " + personalFinanceProfile.getId());

		for(PersonPortfolio portfolio: personalFinanceProfile.getPortfolioList())
			if(portfolioId.equals(portfolio.getId()))
				this.portfolio = portfolio;
		if(this.portfolio == null)
			throw new ResourceNotFoundBusinessException("������������� �������� �� ������",PersonPortfolio.class);
		portfolio.setPortfolioState(PortfolioState.EDIT);
	}

	/**
	 * ���������� ������� (�������������) �������� �������
	 * @return
	 */
	public PersonPortfolio getPortfolio()
	{
		return portfolio;
	}

	public void setNewStartAmount(BigDecimal newStartAmount) throws BusinessException, BusinessLogicException
	{
		portfolio.setTemporaryStartAmount(new Money(newStartAmount, MoneyUtil.getNationalCurrency()));
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
