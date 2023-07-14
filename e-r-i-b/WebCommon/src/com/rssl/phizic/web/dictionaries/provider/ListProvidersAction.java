package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.ActivateOrLockManyServiceProviderOperation;
import com.rssl.phizic.operations.dictionaries.provider.MigrationServiceProviderOperation;
import com.rssl.phizic.operations.dictionaries.provider.RemoveServiceProvidersOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 15.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListProvidersAction extends SaveFilterParameterAction
{
	private static final String FORWARD_TAX_START = "TaxStart";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.activate", "activate");
		map.put("button.lock", "lock");
		map.put("button.migration", "migration");
		map.put("button.pagingPrev", "pagingPrev");
		map.put("button.pagingNext",  "pagingNext");
		return map;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			operation.initialize(id);
			operation.remove();
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewListServiceProvidersOperation");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveServiceProvidersOperation.class, "ServiceProvidersDictionaryManagement");
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = currentRequest();
		map.put("regionName", request.getParameter("regionName"));
		map.put("regionId", request.getParameter("regionId"));
		map.put("paymentServiceName", request.getParameter("paymentServiceName"));
		map.put("paymentServiceId", request.getParameter("paymentServiceId"));
		map.put("departmentId", request.getParameter("departmentId"));
		map.put("billingId", request.getParameter("billingId"));
		map.put("name", request.getParameter("name"));
		map.put("account", request.getParameter("account"));
		map.put("INN", request.getParameter("INN"));
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
			map.put("state", request.getParameter("state"));
		else
		{
			map.put("state", "ACTIVE");
			String kind = request.getParameter("kind");
			map.put("kind", StringHelper.isEmpty(kind)?"B":kind);
		}
		return map;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("regionId", filterParams.get("regionId"));
		query.setParameter("paymentServiceId", filterParams.get("paymentServiceId"));
		query.setParameter("departmentId", filterParams.get("departmentId"));
		query.setParameter("billingId", filterParams.get("billingId"));
		query.setParameter("name", filterParams.get("name"));
		query.setParameter("account", filterParams.get("account"));
		query.setParameter("INN", filterParams.get("INN"));
		query.setParameter("kind", filterParams.get("kind"));
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
			query.setParameter("state", filterParams.get("state"));
		else
			query.setParameter("state", "ACTIVE");

		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListProvidersForm.FILTER_FORM;
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListProvidersForm frm = (ListProvidersForm) form;
		ActivateOrLockManyServiceProviderOperation operation = createOperation("ActivateOrLockManyServiceProviderOperation");
		try
		{
			operation.initialize(frm.getSelectedIds());
			Map<String, Exception> exceptions = operation.activate();

			for (String name : exceptions.keySet())
			{
				Exception ex = exceptions.get(name);
				if (ex instanceof BusinessException)
				{
					log.error(ex);
					continue;
				}

				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(ex.getMessage(), new ActionMessage(ex.getMessage(), false));
				saveErrors(request, actionErrors);
			}
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

	public ActionForward migration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListProvidersForm frm = (ListProvidersForm) form;
		MigrationServiceProviderOperation operation = createOperation("MigrationServiceProviderOperation");
		operation.initialize(frm.getSelectedId());
		try
		{
			operation.migration();
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
		setCurrentMapping(mapping);
		ListProvidersForm frm = (ListProvidersForm) form;
		ActivateOrLockManyServiceProviderOperation operation = createOperation("ActivateOrLockManyServiceProviderOperation");
		try
		{
			operation.initialize(frm.getSelectedIds());
        	Map<String, Exception> exceptions = operation.lock();

			for (String name : exceptions.keySet())
			{
				Exception ex = exceptions.get(name);
				if (ex instanceof BusinessException)
				{
					log.error(ex);
					continue;
				}

				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(ex.getMessage(), new ActionMessage(ex.getMessage(), false));
				saveErrors(request, actionErrors);
			}
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

	protected ActionForward createActionForward(ListFormBase frm) throws BusinessException
	{
		String kind = (String) frm.getFilter("kind");
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		Application application = applicationConfig.getLoginContextApplication();
		if ("T".equals(kind) && (application == Application.PhizIC))
		{
			return getCurrentMapping().findForward(FORWARD_TAX_START);
		}

		return getCurrentMapping().findForward(FORWARD_START);
	}

	public ActionForward pagingPrev(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListProvidersForm frm = (ListProvidersForm) form;
		frm.decCurrentPage();
		return filter(mapping, frm, request, response);
	}

	public ActionForward pagingNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListProvidersForm frm = (ListProvidersForm) form;
		frm.incCurrentPage();
		return filter(mapping, frm, request, response);
	}
}