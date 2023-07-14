package com.rssl.phizic.web.ermb.migration.list.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.operations.EditSMSDeliveryOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 10.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшен редактирования и отправки СМС-рассылки
 */
public class EditSMSDeliveryAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.send", "send");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}


	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		EditSMSDeliveryOperation operation = createOperation(EditSMSDeliveryOperation.class, "ErmbMigrationService");
		EditSMSDeliveryForm frm = (EditSMSDeliveryForm)form;

		ActionMessages messages = new ActionMessages();
		messages.add(validate(frm.getSendsSegments(), frm.getText()));
		if (messages.isEmpty())
		{
			operation.send(frm.getSendsSegments(), frm.getBanSendsSegments(), frm.getText());
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(String.format(EditSMSDeliveryForm.NUM_CLIENTS_MESSAGE_TEMPlATE, operation.getNumRecipClients()),false));
		}
		//
		saveErrors(request, messages);
		return mapping.findForward(FORWARD_START);
	}

	private ActionMessages validate(String[] segments, String text)
	{
		ActionMessages msgs = new ActionMessages();
		if (segments == null || segments.length==0)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выберите сегмент для отправки", false));
		}
		if (text == null || "".equals(text))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Поле \"Текст SMS-сообщения\" обязательно для заполнения",false));
		}
		return msgs;
	}
}

