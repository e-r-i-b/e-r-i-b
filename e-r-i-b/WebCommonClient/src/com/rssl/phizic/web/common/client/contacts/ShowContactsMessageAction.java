package com.rssl.phizic.web.common.client.contacts;

import com.rssl.phizic.operations.contacts.ShowContactsMessageOperation;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 10.06.14
 * $Author$
 * $Revision$
 * Отображение клиенту информирующего сообщения об услуге адресной книги
 */
public class ShowContactsMessageAction extends LoginStageActionSupport
{
	private static final String FORWARD_NEXT = "Next";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.next", "next");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowContactsMessageOperation operation = createOperation(ShowContactsMessageOperation.class);
		ShowContactsMessageForm frm = (ShowContactsMessageForm) form;
		frm.setMessage(operation.getAddressBookMessage());
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Метод сохраняет информацию о том, что клиент прочел информирующее сообщение об услуге адресной книги
	 * @param mapping - маппинг
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард для перехода на следующий шаг
	 * @throws Exception
	 */
	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowContactsMessageOperation operation = createOperation(ShowContactsMessageOperation.class);
		operation.readShowAddressBookMessage();
		completeStage();
		return mapping.findForward(FORWARD_NEXT);
	}
}
