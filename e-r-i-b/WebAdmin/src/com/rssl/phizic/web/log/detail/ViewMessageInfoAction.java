package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.MessagingLogEntryBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.log.MessageLogOperation;
import com.rssl.phizic.operations.log.csa.ViewCSAMessageLogOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.util.TransformDataUtil;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен просмотра информации о сообщении
 */

public class ViewMessageInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		MessageLogOperation operation;
		String application = (String) frm.getField("app");
		if (StringHelper.isNotEmpty(application) && Application.CSABack == Application.valueOf(application))
			operation = createOperation(ViewCSAMessageLogOperation.class);
		else if(checkAccess(MessageLogOperation.class, "MessageLogService"))
			operation = createOperation(MessageLogOperation.class, "MessageLogService");
		else if(checkAccess(MessageLogOperation.class, "MessageLogServiceEmployee"))
			operation = createOperation(MessageLogOperation.class, "MessageLogServiceEmployee");
		else if(checkAccess(MessageLogOperation.class, "CommonLogService"))
			operation = createOperation(MessageLogOperation.class, "CommonLogService");
		else if(checkAccess(MessageLogOperation.class, "CommonLogServiceEmployee"))
			operation = createOperation(MessageLogOperation.class, "CommonLogServiceEmployee");
		else if(checkAccess(MessageLogOperation.class, "MessageLogServiceEmployeeUseClientForm"))
			operation = createOperation(MessageLogOperation.class, "MessageLogServiceEmployeeUseClientForm");
		else
			operation = createOperation(MessageLogOperation.class);

		ViewMessageInfoForm form = (ViewMessageInfoForm) frm;
		operation.initialize(frm.getId(), form.getMessageType());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewMessageInfoForm frm = (ViewMessageInfoForm) form;
		MessagingLogEntryBase messageLogEntry = (MessagingLogEntryBase) operation.getEntity();
		frm.setMessageLog(messageLogEntry);
		frm.setMessageRequest(TransformDataUtil.transform(messageLogEntry.getMessageRequest(), messageLogEntry.getMessageRequestId()));
		frm.setMessageResponse(TransformDataUtil.transform(messageLogEntry.getMessageResponse(), messageLogEntry.getMessageResponseId()));
	}
}