package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.NotFoundPersonalFinanceProfileException;
import com.rssl.phizic.operations.pfp.BackInPassingPFPOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.UnsupportedStateException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 11.05.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class PassingPFPActionBase extends OperationalActionBase
{
	private static final String START_PFP_URL = "/edit.do";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
	    map.put("button.back2", "back");
	    return map;
	}

	private <O extends EditPfpOperationBase> O getOperation(Class<O> operationClass, Long id) throws BusinessException, BusinessLogicException
	{
		O operation = createOperation(operationClass);
		operation.initialize(id, checkAccess("SearchVIPForPFPOperation"));		
		return operation;
	}

	protected <O extends EditPfpOperationBase> O getOperation(Class<O> operationClass, PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(operationClass, form.getId());
	}

	private ActionRedirect getRedirectForward(String action, Long id)
	{
		ActionRedirect redirect = new ActionRedirect(action);
		if (id != null && id != 0)
			redirect.addParameter("id", id);
		return redirect;
	}

	protected ActionRedirect getRedirectForward(String forvardName, PassingPFPFormInterface form)
	{
		return getRedirectForward(getCurrentMapping().findForward(forvardName).getPath(), form.getId());
	}

	protected ActionRedirect getRedirectForward(EditPfpOperationBase operation)
	{
		return getRedirectForward(operation.getDocumentSate().getClientForm(), operation.getProfile().getId());
	}

	protected abstract EditPfpOperationBase getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException;

	protected abstract void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException, BusinessLogicException;

	protected ActionForward getStartForward(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			PassingPFPFormInterface frm = (PassingPFPFormInterface) form;
			EditPfpOperationBase operation = getOperation(frm);
			saveStateMachineEventMessages(request, operation, true);
			updateForm(frm, operation);
			return getStartForward(frm, operation);
		}
		catch (UnsupportedStateException e)
		{
			log.error(e.getMessage(), e);
			return getRedirectForward(e.getRealStatePath(), ((PassingPFPFormInterface) form).getId());
		}
		catch (NotFoundPersonalFinanceProfileException e)
		{
			log.error(e.getMessage(), e);
			return new ActionRedirect(START_PFP_URL);
		}
	}

	/**
	 * Возвращение на предыдущий шаг планирования
	 * @param mapping маппинг
	 * @param form    форма
	 * @param request  реквест
	 * @param response респунс
	 * @return экшен для перехода
	 * @throws Exception
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			BackInPassingPFPOperation operation = getOperation(BackInPassingPFPOperation.class, (EditPersonalFinanceProfileForm) form);
			operation.back();
			return getRedirectForward(operation);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return start(mapping, form, request, response);
		}
	}
}
