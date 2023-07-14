package com.rssl.phizicgate.esberibgate.payment.recipients;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.FieldImpl;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.List;

/**
 * хелпер для формирования запросов на оплату услуг поставщиков Einvoicing (внешние услуги)
 * @author gladishev
 * @ created 26.06.2015
 * @ $Author$
 * @ $Revision$
 */

public class BillingEinvoicingRequestHelper extends BillingRequestHelper
{
	private static final String INVOICE_NAME = "Идентификатор счета в магазине";
	private static final String SUM_NAME = "Сумма оплаты";

	public BillingEinvoicingRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	public Requisite_Type[] createRequisites(CardPaymentSystemPayment payment) throws DocumentException
	{
		if (!payment.getState().getCode().equals("INITIAL"))
			return super.createRequisites(payment);
		PaymentsConfig config = ConfigFactory.getConfig(PaymentsConfig.class);
		Requisite_Type[] requisites = new Requisite_Type[3];
		List<Field> extendedFields = payment.getExtendedFields();
		requisites[0] = createRequisite(new FieldImpl(config.getEinvoicingInvoiceIDKey(), INVOICE_NAME, FieldDataType.string, extendedFields.get(0).getValue()));
		requisites[1] = createRequisite(new FieldImpl(config.getEinvoicingUIDKey(), config.getEinvoicingUIDKey(), FieldDataType.string, extendedFields.get(1).getValue()));
		FieldImpl summ = new FieldImpl(config.getEinvoicingSumKey(), SUM_NAME, FieldDataType.money, payment.getDestinationAmount().getDecimal().toString());
		summ.setMainSum(true);
		requisites[2] = createRequisite(summ);
		return requisites;
	}

	/**
	 * Формирует запрос на отзыв документа
	 * @param document - документ
	 */
	public IFXRq_Type createRecallRequest(WithdrawDocument document) throws GateException, GateLogicException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document.getTransferPayment();
		BillingPayCanRq_Type result = new BillingPayCanRq_Type();
		result.setRqUID(BillingRequestHelper.generateUUID());
		result.setRqTm(BillingRequestHelper.generateRqTm());
		result.setOperUID(BillingRequestHelper.generateOUUID());
		result.setSPName(SPName_Type.BP_ERIB);
		result.setSBOLUID(payment.getOperationUID());
		result.setSBOLTm(getStringDateTime(payment.getClientCreationDate()));
		result.setSumCancel(document.getChargeOffAmount().getDecimal().toString());
		result.setIsFullReturn(false);
		result.setBankInfo(createAuthBankInfo(payment.getInternalOwnerId()));
		result.setSystemId(ExternalSystemHelper.getCode(payment.getBillingCode()));

		Client owner = getBusinessOwner(payment);
		Card paymentCard = getCard(owner, payment.getChargeOffCard(), payment.getOffice());
		CardAcctId_Type cardAcctId_type = createCardAcctId(paymentCard, owner, payment.getChargeOffCardExpireDate(), true, true);
		fillCardAccIdAdditionalInfo(cardAcctId_type, paymentCard, false);
		result.setCardAcctId(cardAcctId_type);
		result.setRecipientRec(createRecipientRec(payment, false));
		result.setCommission(payment.getCommission().getDecimal());
		result.setCommissionCur(payment.getCommission().getCurrency().getCode());
		result.setMadeOperationId(payment.getIdFromPaymentSystem());

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setBillingPayCanRq(result);
		return ifxRq;

	}
}
