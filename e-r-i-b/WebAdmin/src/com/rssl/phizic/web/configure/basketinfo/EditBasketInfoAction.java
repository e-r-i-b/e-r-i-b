package com.rssl.phizic.web.configure.basketinfo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.operations.basket.EditBasketInfoOperation;
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
 * Ёкшн редактировани€ идентфикаторов корзины
 *
 * @author muhin
 * @ created 01.07.15
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketInfoAction extends OperationalActionBase
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
		EditBasketInfoOperation operation = createOperation("EditBasketInfoOperation", "EditInfoMessage");
		operation.initialize();
		EditBasketInfoForm frm = (EditBasketInfoForm) form;
		setMessage(frm, operation);
		frm.setDocumentTypes(Arrays.asList("RC", "DL", "INN"));
		frm.setField(EditBasketInfoForm.DOCUMENT_TYPE,"DL");
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketInfoForm frm = (EditBasketInfoForm) form;
		EditBasketInfoOperation operation = createOperation("EditBasketInfoOperation", "EditInfoMessage");
		operation.saveMessage(frm.getField(frm.MESSAGE_WITH).toString(), frm.getField(frm.MESSAGE_WITHOUT).toString(), frm.getField(EditBasketInfoForm.DOCUMENT_TYPE).toString());
		return mapping.findForward("Success");
	}

	private void setMessage(EditBasketInfoForm frm, EditBasketInfoOperation operation)
	{
		frm.setMessageWithINN(operation.getMessageWithText(DocumentType.INN.toString()));
		frm.setMessageWithDL(operation.getMessageWithText(DocumentType.DL.toString()));
		frm.setMessageWithoutDL(operation.getMessageWithoutText(DocumentType.DL.toString()));
		frm.setMessageWithRC(operation.getMessageWithText(DocumentType.RC.toString()));
		frm.setMessageWithoutRC(operation.getMessageWithoutText(DocumentType.RC.toString()));
	}
}
