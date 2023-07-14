package com.rssl.phizic.web.client.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.mail.EditClientMailOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.mail.EditMailForm;

import java.util.Map;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class EditMailAction extends EditActionBase
{
	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditMailForm frm = (EditMailForm)form;
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);

		if(frm.getMailId() != null)
		{
			operation.initializeNew(Long.parseLong(frm.getMailId()));
			operation.markMailReceived();
			Mail mail = operation.findMailById(Long.parseLong(frm.getMailId()));
			frm.setField("subject","RE: "+ mail.getSubject());
		}
		else operation.initializeNew();

		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		operation.initialize();

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditMailForm.MAIL_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Mail mail = (Mail) entity;

		mail.setBody((String)data.get("body"));
		mail.setDirection(MailDirection.ADMIN);
		mail.setSubject((String)data.get("subject"));
		mail.setType(MailType.valueOf((String)data.get("type")));
		mail.setNum(Long.parseLong((String)data.get("num")));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessException
	{
		EditClientMailOperation op = (EditClientMailOperation) editOperation;
		Mail mail = op.getEntity();

		mail.setSender(op.getLogin());
		//TODO: убрать мусор
		//mail.setRecipients(op.getRecipients());

		if (mail.getId() != null)
		{
			currentRequest().setAttribute("$$newId", mail.getId());
			editForm.setId(mail.getId());
		}
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		Mail mail = (Mail) entity;

		frm.setField("num", mail.getNum());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation)
	{
		EditMailForm frm = (EditMailForm)form;

		if(frm.getRecipientId() != null)
			frm.setField("recipientType", RecipientType.ADMIN);
	}
}
