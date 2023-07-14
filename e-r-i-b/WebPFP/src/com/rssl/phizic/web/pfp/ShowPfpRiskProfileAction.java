package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.personData.LoanCount;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.operations.pfp.ShowPfpRiskProfileOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import org.apache.struts.action.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 18.03.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Просмотр риск профиля сформированного для клиента
 */
public class ShowPfpRiskProfileAction extends PassingPFPActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = super.getKeyMethodMap();
		map.put("button.next","next");
		return map;
	}

	protected ShowPfpRiskProfileOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(ShowPfpRiskProfileOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException, BusinessLogicException
	{
		ShowPfpRiskProfileForm frm = (ShowPfpRiskProfileForm) form;
		PersonalFinanceProfile profile = operation.getProfile();
		frm.setRiskProfile(profile.getDefaultRiskProfile());
		frm.setPersonRiskProfile(profile.getPersonRiskProfile());
		frm.setIncomeMoney(profile.getIncomeMoney());
		frm.setOutcomeMoney(profile.getOutcomeMoney());
		frm.setFreeMoney(profile.getFreeMoney());
		frm.setField("haveNotCreditCard", profile.getCreditCardCount() == LoanCount.NONE);
		boolean emptyPortfolio = true;
		if(!CollectionUtils.isEmpty(profile.getPortfolioList()))
		{
			for(PersonPortfolio portfolio : profile.getPortfolioList())
			{
				emptyPortfolio &= portfolio.getIsEmpty();
			}
		}
		frm.setField("isEmptyPortfolio", emptyPortfolio);
		boolean  needBeCareful = PersonalFinanceProfileUtils.getNeedBeCareful(operation.getProfile());
		frm.setNeedBeCareful(needBeCareful);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPfpRiskProfileForm frm = (ShowPfpRiskProfileForm) form;
		ShowPfpRiskProfileOperation operation = getOperation(ShowPfpRiskProfileOperation.class, frm);
		operation.nextStep();
		saveStateMachineEventMessages(request, operation, false);
		return getRedirectForward(operation);
	}
}
