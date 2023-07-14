package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfoService;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.source.NewInvoiceSubscriptionClaimSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionErrorType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.basket.accountingEntity.RemoveAccountingEntityOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;

import java.util.Arrays;
import java.util.List;

/**
 * �������� �������� �������� � ��������� �������
 * @author niculichev
 * @ created 19.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RemoveInvoiceSubscriptionOperation extends OperationBase implements RemoveEntityOperation
{
	private InvoiceSubscription subscription;

	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

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

		InvoiceSubscriptionState subState = tempSub.getState();
		if(subState != InvoiceSubscriptionState.INACTIVE && subState != InvoiceSubscriptionState.AUTO && subState != InvoiceSubscriptionState.DRAFT)
			throw new BusinessException("������ �������� �� ������������ ��������. ������ : " + tempSub.getState());

		if(subState == InvoiceSubscriptionState.INACTIVE && tempSub.getErrorType() != InvoiceSubscriptionErrorType.CRITICAL)
			throw new BusinessException("��������� �������� �� ������������ ��������. ������ : " + tempSub.getErrorType());

		this.subscription = tempSub;
	}

	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
	{
		invoiceSubscriptionService.updateState(InvoiceSubscriptionState.DELETED, subscription.getId());
	}

	public Object getEntity()
	{
		return subscription;
	}
}
