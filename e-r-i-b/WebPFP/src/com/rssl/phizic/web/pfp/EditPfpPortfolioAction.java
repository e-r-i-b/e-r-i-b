package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.EditPortfolioOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpPortfolioAction extends PassingPFPActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.save.portfolio","save");
		map.put("button.cancel","back");
		map.put("button.remove","remove");
		map.put("button.changeStartAmount", "changeStartAmount");
		return map;
	}

	protected EditPortfolioOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getEditPortfolioOperation((EditPfpPortfolioForm) form);
	}

	private EditPortfolioOperation getEditPortfolioOperation(EditPfpPortfolioForm frm) throws BusinessException, BusinessLogicException
	{
		EditPortfolioOperation operation = getOperation(EditPortfolioOperation.class, frm);
		operation.setPortfolioId(frm.getPortfolioId());
		return operation;
	}

	private ActionForward doStart(ActionMapping mapping, EditPfpPortfolioForm frm, EditPortfolioOperation operation)
	{
		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpPortfolioForm frm = (EditPfpPortfolioForm) form;
		EditPortfolioOperation operation = getEditPortfolioOperation(frm);
		operation.applyChanges();
		return getRedirectForward(operation);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		EditPfpPortfolioForm frm = (EditPfpPortfolioForm) form;
		EditPortfolioOperation operation = getEditPortfolioOperation(frm);
		operation.undoChanges();
		return getRedirectForward(operation);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpPortfolioForm frm = (EditPfpPortfolioForm) form;
		EditPortfolioOperation operation = getEditPortfolioOperation(frm);

		operation.removeProductFromPortfolio(frm.getProductId());
		return doStart(mapping, frm, operation);
	}

	protected void updateForm(PassingPFPFormInterface frm, EditPfpOperationBase op)
	{
		EditPfpPortfolioForm form = (EditPfpPortfolioForm) frm;
		EditPortfolioOperation operation = (EditPortfolioOperation) op;
		form.setPortfolio(operation.getPortfolio());
		form.setRiskProfile(operation.getProfile().getPersonRiskProfile());
		form.setClientOwner(!operation.isEmployee());
		form.setProductDistribution(operation.getProductDistribution());
		boolean  needBeCareful = PersonalFinanceProfileUtils.getNeedBeCareful(operation.getProfile());
		form.setNeedBeCareful(needBeCareful);
	}
}
