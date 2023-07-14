package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.UpdatePaymentsStateService;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

/**
 * User: Novikov_A
 * Date: 28.05.2007
 * Time: 19:12:32
 */
public class UpdatePaymentsStateServiceImpl extends AbstractService implements UpdatePaymentsStateService
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public UpdatePaymentsStateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void update(Long documentId, ObjectEvent event) throws GateException, GateLogicException
	{
		try
		{
			BusinessDocument document = businessDocumentService.findById(documentId);
			if (document != null)
			{
				StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
				executor.initialize(document);
				executor.fireEvent(event);

				businessDocumentService.addOrUpdate(document);
			}
			else
			{
				log.error("документ id=" + documentId + " не найден");
			}
		}
		catch (BusinessException ex)
		{
			throw new GateException(ex);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e);
		}
	}
}
