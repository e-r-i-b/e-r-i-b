package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.DocumentAction;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.*;
import com.rssl.phizic.business.limits.RequireAdditionConfirmLimitException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author vagin
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 * Операция создания заявки на редактирование копилки.
 */
public class EditMoneyBoxOperation extends CreateFormPaymentOperation
{
	public Long save() throws BusinessException, BusinessLogicException
	{
		Calendar operationDate = GregorianCalendar.getInstance();
		try
		{
			createDocumentLimitManager(document, document.getOperationUID(), operationDate).processLimits(new DocumentAction()
			{
				public void action(BusinessDocument document) throws BusinessLogicException, BusinessException
				{
					executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));
					target.save(document);
				}
			});
		}
		// если требуется доп подтверждение то переводим в статус WAIT_CONFIRM
		catch (RequireAdditionConfirmLimitException e)
		{
			if (e.getRestrictionType() != null)
				//устанавливаем в платеж причину дополнительного подтверждения
				document.setReasonForAdditionalConfirm(e.getRestrictionType().name());
			// требуется доп подтверждение
			executor.fireEvent(new ObjectEvent(DocumentEvent.DOWAITCONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		}
		document.setOperationDate(operationDate);
		document.setSessionId(LogThreadContext.getSessionId());
		document.setClientOperationChannel(DocumentHelper.getChannelType());
		target.save(document);
		return document.getId();
	}

	protected DocumentLimitManager createDocumentLimitManager(BusinessDocument document, String operationUID, Calendar operationDate) throws BusinessException, BusinessLogicException
	{
		List<Class<? extends ProcessDocumentStrategy>> strategies = new ArrayList<Class<? extends ProcessDocumentStrategy>>();

		if (document.getCreationType() == CreationType.mobile)
		{
			strategies.add(RecipientByCardDocumentMobileChannelLimitStrategy.class);
		}
		//для смс-канала не нужно проверять лимит IMSI [BUG071380]
		if (document.getCreationType() != CreationType.sms)
			strategies.add(IMSIDocumentLimitStrategy.class);

		strategies.add(ObstructionDocumentLimitStrategy.class);
		strategies.add(OverallAmountPerDayDocumentLimitStrategy.class);
		strategies.add(GroupRiskDocumentLimitStrategy.class);
		strategies.add(MobileLightPlusLimitStrategy.class);

		return DocumentLimitManager.createProcessLimitManager(document, operationUID, operationDate, getPerson(), strategies);
	}
}
