package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.business.reminders.ReminderLinkService;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author niculichev
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class AddOrUpdateReminderStateHandler extends BusinessDocumentHandlerBase<BusinessDocument<Department>>
{
	private static final ReminderLinkService reminderLinkService = new ReminderLinkService();

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!document.isMarkReminder() || !document.isByTemplate())
			return;

		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Long loginId = documentOwner.getLogin().getId();
			ReminderLink state = reminderLinkService.getByLoginAndId(loginId, document.getTemplateId());
			if(state == null)
			{
				state = new ReminderLink(loginId, document.getTemplateId(), DateHelper.getCurrentDate(), null);
			}
			else
			{
				state.setProcessDate(DateHelper.getCurrentDate());
				state.setDelayedDate(null);
				state.setResidualDate(null);
			}

			reminderLinkService.addOrUpdate(state);
			ClientInvoiceCounterHelper.resetCounterValue();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
