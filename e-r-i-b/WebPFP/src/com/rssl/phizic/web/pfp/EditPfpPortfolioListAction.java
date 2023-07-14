package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfoliosCondition;
import com.rssl.phizic.operations.pfp.ShowPortfolioListOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import org.apache.struts.action.*;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Начальная страница формирования портфелей клиента
 */
public class EditPfpPortfolioListAction extends PassingPFPActionBase
{
	private static final String EMPTY_PORTFOLIOS = "Уважаемый клиент! Вы ещё не сформировали ни одного портфеля. Для эффективного управления Вашими средствами добавьте наиболее интересные для Вас продукты в портфели.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMap = super.getKeyMethodMap();
		keyMap.put("button.next","next");
		return keyMap;
	}

	protected ShowPortfolioListOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(ShowPortfolioListOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase op) throws BusinessException, BusinessLogicException
	{
		EditPfpPortfolioListForm frm = (EditPfpPortfolioListForm) form;
		ShowPortfolioListOperation operation = (ShowPortfolioListOperation) op;
		operation.completePortfolioChanges();
		List<PersonPortfolio> portfolioList = operation.getPersonPortfolioList();
		frm.setPortfolioList(portfolioList);

		boolean emptyPortfolios = true;
		boolean fullPortfolios = true;
		for(PersonPortfolio portfolio: portfolioList)
		{
			emptyPortfolios = emptyPortfolios && portfolio.getIsEmpty();
			fullPortfolios = fullPortfolios && portfolio.getIsFull();
		}

		if(emptyPortfolios)
			frm.setPortfoliosState(PersonPortfoliosCondition.EMPTY);
		else if(fullPortfolios)
			frm.setPortfoliosState(PersonPortfoliosCondition.FULL);
		else
			frm.setPortfoliosState(PersonPortfoliosCondition.NOT_EMPTY);

		ActionMessages actionMessages = new ActionMessages();
		if(emptyPortfolios)
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_PORTFOLIOS, false));
		saveMessages(currentRequest(), actionMessages);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpPortfolioListForm frm = (EditPfpPortfolioListForm) form;
		ShowPortfolioListOperation operation = getOperation(ShowPortfolioListOperation.class, frm);
		operation.nextStep();
		return getRedirectForward(operation);
	}
}
