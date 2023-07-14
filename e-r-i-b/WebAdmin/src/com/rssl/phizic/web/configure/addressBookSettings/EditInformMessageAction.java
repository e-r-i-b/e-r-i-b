package com.rssl.phizic.web.configure.addressBookSettings;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.config.EditInformMessageOperation;
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
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInformMessageAction  extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMethodMap = new HashMap<String,String>();
		keyMethodMap.put("button.save","save");
		return keyMethodMap;
	}

	protected EditInformMessageOperation getEditOperation() throws BusinessException
	{
		EditInformMessageOperation operation = createOperation(EditInformMessageOperation.class, "EditAddressBookSynchronizationSettings");
		operation.initialize(null);
		return operation;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditInformMessageForm frm = (EditInformMessageForm) form;
		EditInformMessageOperation operation = getEditOperation();
		frm.setField("message",operation.getMessage());
		frm.setField("showMessage", operation.getShowMessage());
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Сохранить информационное сообщение
	 * @param mapping маппинг
	 * @param form форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditInformMessageForm frm = (EditInformMessageForm) form;
		EditInformMessageOperation operation = getEditOperation();
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages,?> formProcessor = createFormProcessor(valuesSource, EditInformMessageForm.EDIT_FORM);
		if(formProcessor.process())
		{
			Map result = formProcessor.getResult();
			operation.updateMessage((String)result.get("message"));
			operation.setShowMessage((Boolean)result.get("showMessage"));
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}
}
