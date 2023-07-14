package com.rssl.phizic.web.configure.exceptions;

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
 * @author mihaylov
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 * Экшн настройки дефолтных сообщений об ошибках
 */
public class ExceptionDefaultMessageEditAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMethodMap = new HashMap<String,String>();
		keyMethodMap.put("button.save","save");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExceptionDefaultMessageEditForm frm = (ExceptionDefaultMessageEditForm) form;
		ExceptionDefaultMessageEditOperation operation = getEditOperation();
		frm.setField("clientMessage",operation.getClientMessage());
		frm.setField("adminMessage",operation.getAdminMessage());
		return mapping.findForward(FORWARD_START);
	}

	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExceptionDefaultMessageEditForm frm = (ExceptionDefaultMessageEditForm) form;
		ExceptionDefaultMessageEditOperation operation = getEditOperation();
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages,?> formProcessor = createFormProcessor(valuesSource, ExceptionDefaultMessageEditForm.EDIT_FORM);
		if(formProcessor.process())
		{
			Map result = formProcessor.getResult();
			operation.updateMessages((String)result.get("clientMessage"),(String)result.get("adminMessage"));
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	protected ExceptionDefaultMessageEditOperation getEditOperation() throws BusinessException
	{
		ExceptionDefaultMessageEditOperation operation = createOperation(ExceptionDefaultMessageEditOperation.class);
		operation.initialize(null);
		return operation;
	}
}
