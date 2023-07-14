package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.operations.person.ResetMobileWalletOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileWalletAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.reset", "reset");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMobileWalletForm frm = (EditMobileWalletForm) form;
		ResetMobileWalletOperation operation = createOperation("ResetMobileWalletOperation", "MobileWalletManagment");
		try
		{
			operation.initialize(frm.getPerson());
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
		}

		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward reset(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMobileWalletForm frm = (EditMobileWalletForm) form;
		ResetMobileWalletOperation operation = createOperation("ResetMobileWalletOperation", "MobileWalletManagment");
		try
		{
			operation.initialize(frm.getPerson());
			operation.reset();
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
			updateForm(frm, operation);
			return mapping.findForward(FORWARD_START);
		}

		updateForm(frm, operation);
		saveMessage(request, "Мобильный кошелек клиента успешно обнулен.");
		return mapping.findForward(FORWARD_START);
	}

	protected void updateForm(EditMobileWalletForm frm, Operation op) throws BusinessException, BusinessLogicException
	{
		ResetMobileWalletOperation operation = (ResetMobileWalletOperation) op;
		frm.setTotalAmount(operation.getProfile().getMobileWallet());
		frm.setActivePerson(operation.getPerson());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getPerson()));
	}
}
