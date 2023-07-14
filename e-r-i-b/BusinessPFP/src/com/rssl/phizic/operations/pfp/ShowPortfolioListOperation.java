package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

import java.util.List;

/**
 * @author mihaylov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция просмотра/редактирования портфелей клиента
 */
public class ShowPortfolioListOperation extends EditPfpOperationBase
{
	private static final State EDIT_PERSON_PORTFOLIOS = new State("EDIT_PERSON_PORTFOLIOS");

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(EDIT_PERSON_PORTFOLIOS);
	}

	public List<PersonPortfolio> getPersonPortfolioList()
	{
		return personalFinanceProfile.getPortfolioList();
	}

	/**
	 * Завершаем редактирование портфелей клиента.
	 * Если было редактирование портфеля, с которого ушли не применив или не отменив изменения,
	 * то применяем их в этом методе.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void completePortfolioChanges() throws BusinessException, BusinessLogicException
	{
		for(PersonPortfolio portfolio : personalFinanceProfile.getPortfolioList())
			PortfolioHelper.completePortfolioChanges(portfolio,personalFinanceProfile);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	public void nextStep() throws BusinessException, BusinessLogicException
	{
		getExecutor().fireEvent(new ObjectEvent(DocumentEvent.COMPLETE_PORTFOLIOS,ObjectEvent.CLIENT_EVENT_TYPE));
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
