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
 * Создать из подписки на инвойсы автоплатеж
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
	 * Инициализация операции по подписке на инвойсы
	 * @param invoiceSubId идентификатор подписки на инвойсы
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(Long invoiceSubId) throws BusinessLogicException, BusinessException
	{
		if(invoiceSubId == null)
			throw new BusinessException("Идентификатор подписки на инвойсы не может быть null.");

		InvoiceSubscription subscription = invoiceSubscriptionService.findById(invoiceSubId);
		if(subscription == null)
			throw new BusinessException("Подписка с id = " + invoiceSubId + " не найдена.");

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(subscription))
			throw new BusinessException("Подписка с id = " + invoiceSubId + " не принадлежит клиенту");

		if(InvoiceSubscriptionState.ACTIVE != subscription.getState())
			throw new BusinessException("Для подписка с id = " + invoiceSubId + " не разрешено создание автоплатежа в статусе " + subscription.getState());

		super.initialize(new NewAutoPaymentByInvoiceSubscriptionSource(subscription));
	}

	@Transactional
	public Long save() throws BusinessLogicException, BusinessException
	{
		JurPayment payment = (JurPayment) document;
		payment.setAttributeValue("boolean", "isAutoInvoiceSubscriptionOperation","true");
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));

		// реквизитов не достаточно - отказываем
		if(!SAVED_STATE.equals(payment.getState()))
			throw new BusinessLogicException("Не удалось создать автоплатеж из подписки.");

		// биллингом не разрешен платеж по выставленному счету
		if(!checkSupportInvoiceAutoPay(payment))
			throw new BusinessLogicException("Не удалось создать автоплатеж из подписки.");

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
