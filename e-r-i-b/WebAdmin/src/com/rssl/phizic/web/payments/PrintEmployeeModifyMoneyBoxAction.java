package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.business.documents.payments.source.ChangeStatusMoneyBoxSource;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vagin
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 * Экшен печати заявлений на приостановку/возобновление/закрытие копилки.
 */
public class PrintEmployeeModifyMoneyBoxAction extends PrintEmployeePaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected ViewDocumentOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		String changeStatusType = (String) frm.getField("changeStatus");
		String autoSubNumber = (String) frm.getField("autoSubNumber");
		AutoSubscriptionLink link = PersonContext.getPersonDataProvider().getPersonData().getAutoSubscriptionLink(Long.valueOf(autoSubNumber));
		if(link == null)
			throw new BusinessLogicException("Данная копилка не принадлежит клиенту");

		ChangeStatusMoneyBoxSource source = null;
		try
		{
			source = new ChangeStatusMoneyBoxSource(link, ChangePaymentStatusType.valueOf(changeStatusType));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class, DocumentHelper.getEmployeeServiceName(source));
		operation.initialize(source);
		return operation;
	}
}
