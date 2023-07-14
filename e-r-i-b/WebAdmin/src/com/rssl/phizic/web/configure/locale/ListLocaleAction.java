package com.rssl.phizic.web.configure.locale;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.locale.ListLocaleOperation;
import com.rssl.phizic.operations.locale.RemoveLocaleOperation;
import com.rssl.phizic.operations.locale.UnloadEribMessagesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.download.DownloadAction;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ListLocaleAction extends OperationalActionBase
{
	private static final String FILE_NAME = "messages.xml";                         //название выгружаемого файла
	private static final String LOCALE_MESSAGES_FILE_TYPE = "LocaleMessagesFileType"; //категория выгружаемого файла

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "filter");
		map.put("button.unload", "unload");
		map.put("button.remove", "remove");
		return map;
	}

	private Map<String,Object> getFilterValues(ListLocaleForm form) throws BusinessLogicException
	{
		FieldValuesSource fieldValuesSource =  new MapValuesSource(form.getFilters());
		if (!fieldValuesSource.isEmpty())
		{
			FormProcessor<ActionMessages, ?> formProcessor   = getFilterFormProcessor(form);
			if (formProcessor.process())
				return formProcessor.getResult();
			throw new FormProcessorException(formProcessor.getErrors());
		}
		return getDefaultFilterParams();
	}

	private FormProcessor<ActionMessages, ?> getFilterFormProcessor(ListLocaleForm frm)
	{
		Map<String, Object> filters = frm.getFilters();
		FieldValuesSource valuesSource = new MapValuesSource(filters);
		Form editForm = ListLocaleForm.FILTER_FORM;
		return createFormProcessor(valuesSource, editForm);
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLocaleForm frm = (ListLocaleForm)form;
		ListLocaleOperation operation = createListOperation(frm);
		operation.setFilterParams(getDefaultFilterParams());
		doFilter(frm,operation);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ListLocaleOperation createListOperation(ListLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(false);
		return createOperation("ListLocaleOperation", "LocaleManagement");
	}

	protected UnloadEribMessagesOperation createUnloadEribMessagesOperation(ListLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(false);
		return createOperation(UnloadEribMessagesOperation.class);
	}

	protected RemoveLocaleOperation createRemoveLocaleOperation(ListLocaleForm frm) throws BusinessException
	{
		frm.setIsCSA(false);
		return createOperation(RemoveLocaleOperation.class);
	}

	/**
	 * Поиск локалей по названию
	 * @param mapping  маппинг
	 * @param form форма
	 * @param request   реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLocaleForm frm = (ListLocaleForm)form;
		ListLocaleOperation operation = createListOperation(frm);
		operation.setFilterParams(getFilterValues(frm));
		doFilter(frm,operation);
		return getCurrentMapping().findForward("Filter");
	}

	private Map<String,Object> getDefaultFilterParams()
	{
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("name",null);
		return map;
	}

	@SuppressWarnings("unchecked")
	private void doFilter(ListLocaleForm form, ListLocaleOperation operation) throws BusinessException
	{
		form.setData(operation.getByFilter());
	}

	/**
	 * Выгрузить файл с текстовками
	 * @param mapping маппинг
	 * @param form форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLocaleForm frm = (ListLocaleForm)form;
		UnloadEribMessagesOperation operation = createUnloadEribMessagesOperation(frm);
		operation.setUnloadedId( frm.getSelectedLocaleId());
		DownloadAction.unload(operation.getFile().getBytes(), LOCALE_MESSAGES_FILE_TYPE, FILE_NAME, frm, request);
		ListLocaleOperation listOperation = createListOperation(frm);
		listOperation.setFilterParams(getDefaultFilterParams());
		doFilter(frm, listOperation);
		return getCurrentMapping().findForward(FORWARD_START);
	}
	/**
	 * Выгрузить файл с текстовками
	 * @param mapping маппинг
	 * @param form форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListLocaleForm frm = (ListLocaleForm)form;
		RemoveLocaleOperation operation = createRemoveLocaleOperation(frm);

		for (String selectedId :frm.getSelectedIds())
		{
			try
			{
				doRemove(operation, selectedId);
			}
			catch (BusinessLogicException ble)
			{
				saveError(request, ble);
			}
		}

		ListLocaleOperation listOperation = createListOperation(frm);
		listOperation.setFilterParams(getDefaultFilterParams());
		doFilter(frm, listOperation);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private void doRemove(RemoveLocaleOperation operation, String id) throws BusinessException, BusinessLogicException
	{
		operation.initialize(id);
		operation.remove();
	}
}
