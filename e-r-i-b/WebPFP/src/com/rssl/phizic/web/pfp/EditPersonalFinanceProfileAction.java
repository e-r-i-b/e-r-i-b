package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.operations.pfp.PFPZeroStepOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */

/** Единая точка входа в функционал ПФП.
 * Получаем текущее состояние прохождения планирования и отправляем на нужный урл */
public class EditPersonalFinanceProfileAction extends PFPZeroStepActionBase
{
	protected PFPZeroStepOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return createOperation(PFPZeroStepOperation.class);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation){}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPersonalFinanceProfileForm frm = (EditPersonalFinanceProfileForm) form;
		PFPZeroStepOperation operation = getOperation(frm);

		operation.initialize();
		if (frm.getId() != null)
		{
			operation.initialize(frm.getId(), checkAccess("SearchVIPForPFPOperation"));
			frm.setField("isViewMode", operation.viewMode());
			return getRedirectForward(operation);
		}

		PersonalFinanceProfile completedPFP = operation.getCompletedPFP();
		if (completedPFP != null)
		{
			operation.initialize(completedPFP);
			saveStateMachineEventMessages(request, operation, true);
			frm.setField("isViewMode", operation.viewMode());
			return getRedirectForward(operation);
		}

		PersonalFinanceProfile notCompletedPFP = operation.getNotCompletedPFP();
		boolean notStartedPFP = notCompletedPFP == null;
		operation.initialize(notStartedPFP ? null: notCompletedPFP);
		frm.setField("isViewMode", operation.viewMode());
		frm.setField("isAvailableStartPFP", operation.isAvailableStartPlaning());
		frm.setHasPFP(!notStartedPFP);

		if (notStartedPFP)
		{
			return mapping.findForward(FORWARD_AGREE);
		}

		return mapping.findForward(FORWARD_START);
	}
}
