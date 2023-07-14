package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.Log;

/**
 * Операция, уточняющая статус платежа
 * @author niculichev
 * @ created 28.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class SpecifyStatusDocumentOperation extends OperationBase implements ViewEntityOperation
{
	private static Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final SimpleService simpleService = new SimpleService();
	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();
	private BusinessDocument document;

	public void specify() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		try
		{
			log.debug("Отправляем повторно документ "+ document.getId());
			executor.fireEvent(new ObjectEvent(DocumentEvent.SPECIFY, "employee"));
		}
		catch (BusinessTimeOutException e)
		{
			throw new BusinessLogicException("Тайм-аут при повторной отправке документа", e);
		}
		catch (BusinessLogicException e)
		{
			log.warn("Ошибка при повторной отправке документа "+ document.getId()+". Отказываем его.", e);
			executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, "employee", e.getMessage()));
		}

		simpleService.addOrUpdate(document) ;
	}

	public void initialize(BusinessDocument document) throws BusinessException
	{
		this.document = document;
	}

	protected StateMachineExecutor getStateMachineExecutor() throws BusinessException
	{
		return new StateMachineExecutor(paymentStateMachineService.getStateMachineByFormName(document.getFormName()));
	}

	public BusinessDocument getEntity() throws BusinessException, BusinessLogicException
	{
		return document;
	}
}
