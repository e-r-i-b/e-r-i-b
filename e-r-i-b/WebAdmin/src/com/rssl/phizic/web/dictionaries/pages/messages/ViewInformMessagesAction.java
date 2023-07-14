package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.dictionaries.pages.messages.RemoveInformMessagesOperation;
import com.rssl.phizic.operations.dictionaries.pages.messages.ViewInformMessagesOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewInformMessagesAction  extends ViewActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.remove", "remove");
		return keyMap; 
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewInformMessagesOperation operation = createOperation(ViewInformMessagesOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewInformMessagesForm form = (ViewInformMessagesForm) frm;
		ViewInformMessagesOperation op = (ViewInformMessagesOperation) operation;
		form.setInformMessage(op.getEntity());
		form.setCanEdit(op.canEdit());
	}

	protected RemoveEntityOperation createRemoveOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		RemoveInformMessagesOperation operation = createOperation(RemoveInformMessagesOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	/**
	 * Удаление информационного сообщения.
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		RemoveEntityOperation operation = createRemoveOperation(frm);
		operation.remove();
		//Фиксируем удаляемые сущности.
		addLogParameters(new BeanLogParemetersReader("Данные удаленной сущности", operation.getEntity()));
		stopLogParameters();
		return mapping.findForward(FORWARD_CLOSE);
	}
}
