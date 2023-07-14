package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.NewAutoPaymentByInvoiceSubscriptionSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * ������� �� �������� �� ������� ����������
 * @author niculichev
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
public class MakeAutoInvoiceSubscriptionOperation extends CreateFormPaymentOperation
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	public static final State SAVED_STATE = new State("SAVED");

	/**
	 * ������������� �������� �� �������� �� �������
	 * @param invoiceSubId ������������� �������� �� �������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(Long invoiceSubId) throws BusinessLogicException, BusinessException
	{
		if(invoiceSubId == null)
			throw new BusinessException("������������� �������� �� ������� �� ����� ���� null.");

		InvoiceSubscription subscription = invoiceSubscriptionService.findById(invoiceSubId);
		if(subscription == null)
			throw new BusinessException("�������� � id = " + invoiceSubId + " �� �������.");

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(subscription))
			throw new BusinessException("�������� � id = " + invoiceSubId + " �� ����������� �������");

		if(InvoiceSubscriptionState.ACTIVE != subscription.getState())
			throw new BusinessException("��� �������� � id = " + invoiceSubId + " �� ��������� �������� ����������� � ������� " + subscription.getState());

		super.initialize(new NewAutoPaymentByInvoiceSubscriptionSource(subscription));
	}

	@Transactional
	public Long save() throws BusinessLogicException, BusinessException
	{
		JurPayment payment = (JurPayment) document;
		payment.setAttributeValue("boolean", "isAutoInvoiceSubscriptionOperation","true");
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));

		// ���������� �� ���������� - ����������
		if(!SAVED_STATE.equals(payment.getState()))
			throw new BusinessLogicException("�� ������� ������� ���������� �� ��������.");

		// ��������� �� �������� ������ �� ������������� �����
		if(!checkSupportInvoiceAutoPay(payment))
			throw new BusinessLogicException("�� ������� ������� ���������� �� ��������.");

		target.save(payment);

		resetConfirmStrategy();
		return document.getId();
	}

	private boolean checkSupportInvoiceAutoPay(JurPayment payment) throws BusinessException
	{
		Map<AutoSubType,Object> autoSubSupport = payment.getAutoPaymentScheme();
		if(MapUtils.isNotEmpty(autoSubSupport) )
			return autoSubSupport.get(AutoSubType.INVOICE) != null;

		return serviceProviderService.isSupportAutoPayType(payment.getReceiverInternalId(), AutoSubType.INVOICE);
	}
}
