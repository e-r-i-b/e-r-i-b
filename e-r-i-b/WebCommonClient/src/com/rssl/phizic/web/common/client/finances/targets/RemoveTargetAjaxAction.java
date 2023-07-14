package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.business.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.finances.targets.RemoveAccountTargetOperation;
import com.rssl.phizic.operations.finances.targets.RemoveTargetState;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class RemoveTargetAjaxAction extends OperationalActionBase
{
	private static final String TEMPORAL_EXCEPTION_MESSAGE = "По техническим причинам операция временно недоступна. Повторите попытку позже.";

	private static final String SUCCESS = "Success";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String,String>();
		map.put("button.remove","remove");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new NoSuchMethodException();
	}

	/**
	 * Метод удаления цели (предназначен только для удаления целей, для которых нет вклада или вклад с нулевым остатком)
	 * @param mapping стратс-маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард на построение ответа
	 * @throws BusinessException
	 */
	@SuppressWarnings({"UnusedDeclaration"}) //response
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		RemoveTargetAjaxForm frm = (RemoveTargetAjaxForm) form;
		RemoveAccountTargetOperation operation = createOperation(RemoveAccountTargetOperation.class);
		operation.initialize(frm.getId());

		RemoveTargetState removeState = doRemove(request, operation);

		frm.setRemoveState(removeState);
		frm.setAccountLinkId(operation.getAccountLinkId());
		frm.setClaimId(operation.getClaimId());
		frm.setClaimStateCode(operation.getDocumentStateCode());

		return mapping.findForward(SUCCESS);
	}

	private RemoveTargetState doRemove(HttpServletRequest request, RemoveAccountTargetOperation operation) throws BusinessException
	{
		try
		{
			RemoveTargetState state = operation.remove();
			saveStateMachineEventMessages(request, operation, false);
			return state;
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}
		catch (TemporalBusinessException ignored)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(TEMPORAL_EXCEPTION_MESSAGE, new ActionMessage(TEMPORAL_EXCEPTION_MESSAGE, false));
			saveErrors(request, actionErrors);
		}
		catch (BusinessLogicMessageException e)
		{
			String message = StrutsUtils.getMessage(e.getMessage(), e.getBundle());
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(message, false));
			saveErrors(request, actionErrors);
		}
		catch (ForceRedirectBusinessLogicException e)
		{
			saveError(request, new ActionMessage(e.getMessage(), new ActionMessage(e.getMessage(), false)));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		saveStateMachineEventMessages(request, operation, false);
		return RemoveTargetState.CLAIM_ERROR;
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}
