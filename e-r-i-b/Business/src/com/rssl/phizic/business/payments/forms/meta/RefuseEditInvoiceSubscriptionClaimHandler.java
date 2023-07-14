package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.EditInvoiceSubscriptionClaim;

/**
 * ������� ������ ��������� �������������� �������� �� �������
 * @author saharnova
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */

public class RefuseEditInvoiceSubscriptionClaimHandler extends BusinessDocumentHandlerBase
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			EditInvoiceSubscriptionClaim claim = (EditInvoiceSubscriptionClaim) document;
			Long subscriptionId = claim.getInvoiceSubscriptionId();
			if (subscriptionId == null)
			{
				throw new DocumentException("������������� �������� �� ����� ���� null");
			}
			InvoiceSubscription invoiceSubscription = invoiceSubscriptionService.findById(subscriptionId);
			if (invoiceSubscription == null)
			{
				throw new DocumentException("�������� � id " + subscriptionId + " �� �������.");
			}
			if(!(invoiceSubscription.getLoginId() == claim.getOwner().getPerson().getLogin().getId()))
			{
				throw new DocumentException("�������� � id = " + subscriptionId + " �� ����������� ��������� ���������");
			}

			//���������� ��� ���������, ����� ������ ����� (����� ��������, ������, ���� �������, ��������)
			invoiceSubscription.setPayDate(claim.getOldDayPay());
			invoiceSubscription.setExecutionEventType(claim.getOldEventType());
			invoiceSubscription.setName(claim.getOldSubscriptionName());
			invoiceSubscription.setChargeOffCard(claim.getOldFromResource());
			invoiceSubscriptionService.addOrUpdate(invoiceSubscription);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
