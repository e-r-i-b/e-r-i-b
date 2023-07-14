package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.provider.ActivateOrLockServiceProviderOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.operations.dictionaries.provider.ServiceProviderListEntitiesOperationBase;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 * Базовый экшен для списка сущностей, привязанных к поставщику
 */
public abstract class ServiceProviderListActionBase extends ListActionBase
{
	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ServiceProviderListEntitiesOperationBase op = (ServiceProviderListEntitiesOperationBase) operation;
		ServiceProviderListFormBase frm = (ServiceProviderListFormBase) form;
		ServiceProviderBase provider = op.getProvider();
		boolean hasAccess = false;
		//Необходимо задизаблить поля если нет прав редактировать поставщика.
		try
		{
			((EditServiceProvidersOperation)createOperation("EditServiceProvidersOperation")).initialize(frm.getId());
			hasAccess = true;
		}
		catch (AccessControlException e)
		{
			//Либо операция не создалась, либо сработал рестрикшен - дизаблим поля.
			hasAccess = false;
		}
		frm.setEditable(hasAccess);
		frm.setProviderState(provider.getState().toString());
        frm.setInternetShop(provider instanceof InternetShopsServiceProvider);
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.activate", "activate");
		map.put("button.lock", "lock");

		return map;
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ServiceProviderListFormBase frm = (ServiceProviderListFormBase) form;
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
		ServiceProviderListFormBase frm = (ServiceProviderListFormBase) form;
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
