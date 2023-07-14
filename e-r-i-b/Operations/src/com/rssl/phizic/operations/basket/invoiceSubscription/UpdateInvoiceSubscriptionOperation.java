package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.NewInvoiceSubscriptionClaimSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionErrorType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Calendar;

/**
 * ���������� �������� �� �������. �� ���� ������ ��������� ������
 * @author niculichev
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateInvoiceSubscriptionOperation extends CreateFormPaymentOperation
{
	private static final InvoiceService invoiceService = new InvoiceService();
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	private InvoiceSubscription subscription;

	/**
	 * ���������������� ��������
	 * @param subscriptionId ������������� ��������
	 */
	public void initialize(Long subscriptionId) throws BusinessLogicException, BusinessException
	{
		if (subscriptionId == null)
			throw new BusinessException("�� ����� ������������� ��������");

		InvoiceSubscription tempSub = invoiceSubscriptionService.findById(subscriptionId);

		if (tempSub == null)
			throw new BusinessException("������ �� �������������� " + subscriptionId + " �� �������.");

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(tempSub))
			throw new BusinessException("�������� � id = " + subscriptionId + " �� ����������� �������");

		if(InvoiceSubscriptionState.WAIT != tempSub.getState())
			throw new BusinessException("������ �������� �� ������������ ����������");

		if(tempSub.getErrorType() == null || tempSub.getErrorType() != InvoiceSubscriptionErrorType.NOT_CRITICAL)
			throw new BusinessException("��������� �������� �� ������������ ����������");

		initialize(new NewInvoiceSubscriptionClaimSource(tempSub, getFormName(tempSub)));
		save();

		this.subscription = tempSub;
	}

	/**
	 * �������� ��������� ��������. �� ���� ��������� �������� ��������� ������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@Transactional
	public void update() throws BusinessLogicException, BusinessException
	{
		try
		{
			// ��������� ���� �������� ��� � ��� �������������
			executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, getSourceEvent()));
			doSaveConfirm(document, Calendar.getInstance());

			// �������� ������
			invoiceService.updateInvoiceSubErrorById(subscription.getId(), null, null);
		}
		catch (BusinessLogicException e)
		{
			fireErrorEvent();
			throw e;
		}
		catch (BusinessException e)
		{
			fireErrorEvent();
			throw e;
		}
		catch (SecurityLogicException e)
		{
			fireErrorEvent();
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	private void doSaveConfirm(BusinessDocument document, Calendar operationDate) throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		document.setOperationDate(operationDate);
		document.setSessionId(LogThreadContext.getSessionId());
		document.setClientOperationChannel(CreationType.internet);
		document.setConfirmStrategyType(ConfirmStrategyType.none);

		target.save(document);
	}

	private void fireErrorEvent() throws BusinessLogicException, BusinessException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.ERROR, getSourceEvent()));
		target.save(document);
	}

	private String getFormName(InvoiceSubscription subscription) throws BusinessException
	{
		switch (subscription.getNextState())
		{
			case ACTIVE:
				return FormConstants.RECOVERY_INVOICE_SUBSCRIPTION_CLAIM;
			case DELETED:
				return FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM;
			case STOPPED:
				return FormConstants.DELAY_INVOICE_SUBSCRIPTION_CLAIM;
			default:
				throw new BusinessException("������ �������� �� ������������ ����������");
		}
	}
}
