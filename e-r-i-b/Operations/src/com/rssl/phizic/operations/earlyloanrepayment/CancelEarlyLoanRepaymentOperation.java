package com.rssl.phizic.operations.earlyloanrepayment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.RecallDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.SystemWithdrawDocumentSource;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.operations.OperationBase;

import java.util.HashMap;
import java.util.Map;

/**
 * отмена досрочного погашение кредита
 * @author basharin
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 */

public class CancelEarlyLoanRepaymentOperation extends OperationBase
{
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private String terminationRequestId;

	public void initialize(String terminationRequestId)
	{
		this.terminationRequestId = terminationRequestId;
	}

	public void cancelEarlyLoan() throws BusinessException
	{
		if (terminationRequestId == null)
			return;

		BusinessDocument parent = DocumentHelper.getPaymentByExternalId(terminationRequestId);
		if (parent == null)
			throw new BusinessException("Ќе найдено за€вки на досрочное погашение кредита по идентификатору" + terminationRequestId);

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("terminationRequestId", terminationRequestId);
		values.put("parentId", parent.getId());
		FieldValuesSource valuesSource = new MapValuesSource(values);

		RecallDocument document = null;
		try
		{
			DocumentSource source = new SystemWithdrawDocumentSource(FormConstants.CANCEL_EARLY_LOAN_REPAYMENT_CLAIM, valuesSource, parent);
			document = (RecallDocument) source.getDocument();

			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
			executor.initialize(document);
			executor.fireEvent(new ObjectEvent(DocumentEvent.DISPATCH, ObjectEvent.SYSTEM_EVENT_TYPE));

			businessDocumentService.addOrUpdate(document);
		}
		catch (BusinessTimeOutException e)
		{
			try
			{
				StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
				executor.initialize(document);
				DocumentHelper.fireDounknowEvent(executor, ObjectEvent.SYSTEM_EVENT_TYPE, e);
			}
			catch (BusinessLogicException ble)
			{
				throw new BusinessException(ble);
			}
			businessDocumentService.addOrUpdate(document);

			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}
}
