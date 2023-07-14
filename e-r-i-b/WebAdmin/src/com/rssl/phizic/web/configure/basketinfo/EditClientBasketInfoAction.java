package com.rssl.phizic.web.configure.basketinfo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.operations.basket.EditClientBasketInfoOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн Редактирование в АРМ информирующих клиента текстов по использованию корзины платежей
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditClientBasketInfoAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditClientBasketInfoOperation operation = createOperation("EditClientBasketInfoOperation", "EditInfoMessage");
		operation.initialize();
		EditClientBasketInfoForm frm = (EditClientBasketInfoForm) form;
		setMessage(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditClientBasketInfoForm frm = (EditClientBasketInfoForm) form;
		EditClientBasketInfoOperation operation = createOperation("EditClientBasketInfoOperation", "EditInfoMessage");
		operation.saveMessage(frm.getField(frm.MESSAGE).toString());
		return mapping.findForward("Success");
	}

	private void setMessage(EditClientBasketInfoForm frm, EditClientBasketInfoOperation operation)
	{
		String messageText = operation.getMessageText();
		frm.setMessage(messageText);
	}
}
