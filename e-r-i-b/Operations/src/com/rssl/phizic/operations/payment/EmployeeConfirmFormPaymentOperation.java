package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.strategies.DocumentAction;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.BlockDocumentOperationLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.documents.strategies.limits.ObstructionDocumentLimitStrategy;
import com.rssl.phizic.business.limits.ImpossiblePerformOperationException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.MoneyHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author khudyakov
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeConfirmFormPaymentOperation extends ConfirmFormPaymentOperation
{
	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		document = source.getDocument();
		metadata = source.getMetadata();

		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()), getMessageCollector(), getStateMachineEvent());
		executor.initialize(document);
	}

	@Override
	@Transactional
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		Calendar operationDate = GregorianCalendar.getInstance();
		if (!(document.getOwner().isGuest() && "ExtendedLoanClaim".equals(document.getFormName())))
		{
			try
			{
				createDocumentLimitManager(document, document.getOperationUID(), operationDate).processLimits(new DocumentAction()
				{
					public void action(BusinessDocument document) throws BusinessLogicException, BusinessException
					{
						try
						{
							executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.EMPLOYEE_EVENT_TYPE));
						}
						catch (BusinessTimeOutException e)
						{
							log.error(String.format(Constants.TIME_OUT_ERROR_MESSAGE, document.getId()), e);
							DocumentHelper.fireDounknowEvent(executor, ObjectEvent.EMPLOYEE_EVENT_TYPE, e);
						}
					}
				});
			}
			catch(PostConfirmCalcCommission ignore)
			{
				//расчет комиссии после подтверждения. Второй раз не подтверждаем документ(смс/карта).
				new DbDocumentTarget().save(document);
				return;
			}
			catch (ImpossiblePerformOperationException e)
			{
				//в АРМ сотрудника бросаются только исключения по сумме операции
				String message = String.format(Constants.BLOCK_LIMIT_OPERATIONS_EXCEEDED_MESSAGE, MoneyHelper.formatAmount(e.getAmount()), MoneyHelper.formatAmount(e.getLimit().getAmount()));
				throw new BusinessLogicException(message, e);
			}
		} else
		{
			executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.EMPLOYEE_EVENT_TYPE));
		}

		doSaveConfirm(document, operationDate);
	}

	protected DocumentLimitManager createDocumentLimitManager(BusinessDocument document, String operationUID, Calendar operationDate) throws BusinessException, BusinessLogicException
	{
		List<Class<? extends ProcessDocumentStrategy>> strategies = new ArrayList<Class<? extends ProcessDocumentStrategy>>();
		strategies.add(BlockDocumentOperationLimitStrategy.class);
		strategies.add(ObstructionDocumentLimitStrategy.class);

		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		return DocumentLimitManager.createProcessLimitManager(document, operationUID, operationDate, person, strategies);
	}
}
