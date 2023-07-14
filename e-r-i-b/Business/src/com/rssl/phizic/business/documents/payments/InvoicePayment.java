package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Оплата инвойса (счета)
 */
public class InvoicePayment extends JurPayment implements InvoiceAcceptPayment
{
	private static final String SUBSCRIPTION_AUTOPAY_ID_FIELD   = "subscription-autopay-id";
	private static final String SUBSCRIPTION_NAME_FIELD         = "subscription-name";
	private static final String INVOICE_ID_FIELD                = "invoice-id";
	private static final String INVOICE_AUTOPAY_ID_FIELD        = "invoice-autopay-id";
	private static final String INVOICE_STATUS_FIELD            = "invoice-status";
	private static final String FROM_RESOURCE_REST_FIELD        = "from-resource-rest";

	@Override
	public Class<? extends GateDocument> getType()
	{
		return InvoiceAcceptPayment.class;
	}

	public String getAutoSubscriptionId()
	{
		return getNullSaveAttributeStringValue(SUBSCRIPTION_AUTOPAY_ID_FIELD);
	}

	public Money getExactAmount()
	{
		return getDestinationAmount();
	}

	/**
	 * @param subscriptionAutopayId идентификатор автопоиска счетов
	 */
	public void setAutoSubscriptionId(String subscriptionAutopayId)
	{
		setNullSaveAttributeStringValue(SUBSCRIPTION_AUTOPAY_ID_FIELD, subscriptionAutopayId);
	}

	public String getAutoInvoiceId()
	{
		return getNullSaveAttributeStringValue(INVOICE_AUTOPAY_ID_FIELD);
	}

	/**
	 * @param paymentId идентификатор задолженности в АС AutoPay
	 */
	public void setAutoInvoiceId(String paymentId)
	{
		setNullSaveAttributeStringValue(INVOICE_AUTOPAY_ID_FIELD, paymentId);
	}

	/**
	 * @return наименование услуги
	 */
	public String getSubscriptionNameField()
	{
		return getNullSaveAttributeStringValue(SUBSCRIPTION_NAME_FIELD);
	}

	/**
	 * @param subscriptionName наименование услуги
	 */
	public void setSubscriptionName(String subscriptionName)
	{
		setNullSaveAttributeStringValue(SUBSCRIPTION_NAME_FIELD, subscriptionName);
	}

	/**
	 * @return Идентификатор задолженности
	 */
	public Long getInvoiceId()
	{
		return getNullSaveAttributeLongValue(INVOICE_ID_FIELD);
	}

	/**
	 * @param invoiceId Идентификатор задолженности
	 */
	public void setInvoiceId(Long invoiceId)
	{
		setNullSaveAttributeLongValue(INVOICE_ID_FIELD, invoiceId);
	}

	/**
	 * @return статус задолженности
	 */
	public InvoiceStatus getInvoiceStatus()
	{
		return getNullSaveAttributeEnumValue(InvoiceStatus.class, INVOICE_STATUS_FIELD);
	}

	/**
	 * @param status статус задолженности
	 */
	public void setInvoiceStatus(InvoiceStatus status)
	{
		setNullSaveAttributeEnumValue(INVOICE_STATUS_FIELD, status);
	}

	/**
	 * @return Остаток на счете списания
	 */
	public BigDecimal getFromResourceRest()
	{
		String rest = getNullSaveAttributeStringValue(FROM_RESOURCE_REST_FIELD);
		return StringHelper.isEmpty(rest) ? null : new BigDecimal(rest);
	}

	/**
	 * @param rest Остаток на счете списания
	 */
	public void setFromResourceRest(BigDecimal rest)
	{
		setNullSaveAttributeDecimalValue(FROM_RESOURCE_REST_FIELD, rest);
	}
}
