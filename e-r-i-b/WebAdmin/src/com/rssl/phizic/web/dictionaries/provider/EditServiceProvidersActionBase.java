package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.ActivateOrLockServiceProviderOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 24.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class EditServiceProvidersActionBase extends ImageEditActionBase
{
	protected EditServiceProvidersOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditServiceProvidersOperation operation = createOperation(EditServiceProvidersOperation.class, "ServiceProvidersDictionaryManagement");
		operation.initialize(frm.getId());
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditServiceProvidersOperation operation = createOperation("ViewServiceProvidersOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);
		EditServiceProvidersOperation op = (EditServiceProvidersOperation) operation;
		EditServiceProviderFormBase frm = (EditServiceProviderFormBase) form;
		frm.setField("state", op.getEntity().getState());
		//Необходимо задизаблить поля если нет прав редактировать поставщика.
		boolean hasAccess = false;
		try
		{
			//операцию создаем именно редактирования.
			// Т.к. переданная в параметре может быть создана как ViewServiceProvidersOperation
			createEditOperation(frm);
			hasAccess = true;
		}
		catch (AccessControlException e)
		{
			//Либо операция не создалась, либо сработал рестрикшен - дизаблим поля.
			hasAccess = false;
		}
		frm.setEditable(hasAccess);
        frm.setInternetShop(op.getEntity() instanceof InternetShopsServiceProvider);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.activate", "activate");
		map.put("button.lock", "lock");

		return map;
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		ActivateOrLockServiceProviderOperation operation = createOperation("ActivateOrLockServiceProviderOperation");
		try
		{
			operation.initialize(frm.getId());
			operation.activate();
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		stopLogParameters();
		return start(mapping, form, request, response);
	}

	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		ActivateOrLockServiceProviderOperation operation = createOperation("ActivateOrLockServiceProviderOperation");
		try
		{
			operation.initialize(frm.getId());
			operation.lock();
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		stopLogParameters();
		return start(mapping, form, request, response);
	}
}
