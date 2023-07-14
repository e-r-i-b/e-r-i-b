package com.rssl.phizic.web.configure.exceptions.locale;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.exceptions.ExceptionDefaultMessageEditOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author koptyaev
 * @ created 13.10.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedParameters")
public class ExceptionDefaultMessageResourcesEditAction extends OperationalActionBase
{
	private static final String FORWARD_SUCCESS = "Success";
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMethodMap = new HashMap<String,String>();
		keyMethodMap.put("button.save","save");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExceptionDefaultMessageEditResourcesForm frm = (ExceptionDefaultMessageEditResourcesForm) form;
		ExceptionDefaultMessageEditOperation operation = getEditOperation(frm);
		frm.setField("clientMessage",operation.getClientMessage());
		frm.setField("adminMessage",operation.getAdminMessage());
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Валидирует и сохраняет данные с формы
	 * @param mapping маппинг
	 * @param form форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExceptionDefaultMessageEditResourcesForm frm = (ExceptionDefaultMessageEditResourcesForm) form;
		ExceptionDefaultMessageEditOperation operation = getEditOperation(frm);
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages,?> formProcessor = createFormProcessor(valuesSource, ExceptionDefaultMessageEditResourcesForm.EDIT_FORM);
		if(formProcessor.process())
		{
			Map result = formProcessor.getResult();
			operation.updateMessages((String)result.get("clientMessage"),(String)result.get("adminMessage"));
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}


	protected ExceptionDefaultMessageEditOperation getEditOperation(ExceptionDefaultMessageEditResourcesForm form) throws BusinessException
	{
		ExceptionDefaultMessageEditOperation operation = createOperation(ExceptionDefaultMessageEditOperation.class);
		operation.initialize(form.getLocaleId());
		form.setLocale(operation.getLocale());
		return operation;
	}
}
