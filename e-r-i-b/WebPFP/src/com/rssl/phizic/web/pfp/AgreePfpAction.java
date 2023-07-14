package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.pfp.PFPZeroStepOperation;
import com.rssl.phizic.operations.pfp.ReplanPFPOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Подтверждение клиентом согласия с условиями ПФП
 * @author lepihina
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class AgreePfpAction extends PassingPFPActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.startNewPlanning", "startNewPlanning");
		return map;
	}

	protected PFPZeroStepOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(PFPZeroStepOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation){}

	public ActionForward startNewPlanning(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReplanPFPOperation operation = getOperation(ReplanPFPOperation.class, (EditPersonalFinanceProfileForm) form);
		EditPersonalFinanceProfileForm frm = (EditPersonalFinanceProfileForm) form;

		//Значение чекбокса, означающего согласие клиента
		String selectAgreed = (String) frm.getField("selectAgreed");
		ActionMessages msgs = new ActionMessages();

		if (selectAgreed == null )
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Для продолжения работы необходимо принять условиия.", false));
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_START);
		}

		operation.replan();
		return getRedirectForward(operation);
	}
}
