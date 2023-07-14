package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailSubject;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.mail.subjects.EditSubjectOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * @author komarov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditMailSubjectAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditSubjectOperation editOperation = createOperation(EditSubjectOperation.class); 
		Long id = frm.getId();
		if(id != null && id != 0)
		{
			editOperation.initialize(id);
			return editOperation;
		}
		editOperation.initialize();
		return editOperation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditMailSubjectForm.MAIL_SUBJECT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		MailSubject subject = (MailSubject)entity;
		subject.setDescription((String)data.get("subject"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		MailSubject subject = (MailSubject)entity;
	 	frm.setField("subject", subject.getDescription());
	}
}
