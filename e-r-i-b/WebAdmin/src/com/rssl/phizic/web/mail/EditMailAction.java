package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.mail.EditMailOperation;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;
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
		EditMailOperation operation = createOperation(EditMailOperation.class, "MailManagment");


		if(frm.getMailId() != null)
		{
			operation.initializeNew(Long.parseLong(frm.getMailId()));
			operation.markMailReceived();
			Mail mail = operation.findMailById(Long.parseLong(frm.getMailId()));
			frm.setField("subject", "RE: " + mail.getSubject());
		}
		else operation.initializeNew();

		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditMailOperation operation = createOperation(EditMailOperation.class, "MailManagment");
        operation.initialize();

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditMailForm.MAIL_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		Mail mail = (Mail) entity;

		frm.setField("num", mail.getNum());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws SecurityDbException, BusinessException
	{
		EditMailForm frm = (EditMailForm)form;
		EditMailOperation op = (EditMailOperation) operation;
		if((frm.getRecipientId() != null) && (!frm.getRecipientId().equals("")))
		{
			frm.setField("recipientType", RecipientType.PERSON);
			Person person = op.findPersonById(Long.parseLong(frm.getRecipientId()));
			frm.setField("recipient", person.getFullName());
			frm.setField("recipientId", frm.getRecipientId());
		}
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Mail mail = (Mail) entity;

		mail.setBody((String) data.get("body"));
		mail.setDirection(MailDirection.CLIENT);
		mail.setSubject((String) data.get("subject"));
		mail.setType(MailType.valueOf((String)data.get("type")));
		mail.setNum(Long.parseLong((String) data.get("num")));
		mail.setSender(AuthModule.getAuthModule().getPrincipal().getLogin());
		mail.setImportant((Boolean) data.get("important"));

	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws Exception
	{
		Recipient recipient;
		EditMailForm frm = (EditMailForm)form;
		EditMailOperation op = (EditMailOperation) editOperation;

		Long recipientId = Long.parseLong((String) frm.getField("recipientId"));
		List<Recipient> listRecipients= new ArrayList<Recipient>();

		if(RecipientType.PERSON == RecipientType.valueOf( (String) frm.getField("recipientType")) )
		{
			recipient = new Recipient();
			recipient.setRecipientType(RecipientType.valueOf( (String) frm.getField("recipientType") ));
			if (frm.getRecipientId().length() == 0)
				recipient.setRecipientId(op.getUserLoginId(recipientId));
			else
				recipient.setRecipientId(recipientId);
			recipient.setRecipientName((String) frm.getField("recipient"));
			listRecipients.add(recipient);
		}
		else
		{
			recipient = new Recipient();
			recipient.setRecipientType(RecipientType.valueOf( (String) frm.getField("recipientType") ));
			recipient.setRecipientId(recipientId);
			recipient.setRecipientName((String) frm.getField("recipient"));
			listRecipients.add(recipient);

			List<CommonLogin> commonLogins = op.getGroupContents(recipientId);
			for (CommonLogin login : commonLogins)
			{
				recipient = new Recipient();
				recipient.setRecipientType(RecipientType.PERSON);
				recipient.setRecipientId(login.getId());
				ActivePerson activePerson;
				try
				{
					activePerson = new PersonService().findByLogin((Login)login);
				}
				catch (NotFoundException e)
				{
					continue; // TODO неактивный человек в группе (таких надо удалять из списка при "удалении" из системы или написать job который их удалит)
				}
				recipient.setRecipientName(activePerson.getFullName());
				listRecipients.add(recipient);
			}
		}

		Mail mail = op.getEntity();
		//TODO: мусор
		//mail.setRecipients(listRecipients);

		if (mail.getId()!=null)
		{
			currentRequest().setAttribute("$$newId", mail.getId());
			frm.setId(mail.getId());
		}
	}
}
