package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizgate.common.payments.documents.StateUpdateInfoImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.payment.recipients.BillingPaymentDocumentService;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author niculichev
 * @ created 23.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class BillingPaymentDocumentAutoSubSender extends BillingPaymentDocumentService
{
	protected static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE) ;

	private static final String NOT_AUTO_PAY_ACCESSIBLE_MESSAGE = "�� �� ������ �������� ���������� � ����� ������� ����������. ����������, �������� ������� ����������.";

	public BillingPaymentDocumentAutoSubSender(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		super.send(document);

		addOfflineDocumentInfo(document);
	}

	@Override
	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		StateUpdateInfo stateUpdateInfo = super.execute(document);
		if (stateUpdateInfo != null)
			return stateUpdateInfo;

		Calendar allowedUpdateDate = getAllowedUpdateDate(document.getClientOperationDate(), ConfigFactory.getConfig(ESBEribConfig.class).getDocumentUpdateWaitingTimeForLongOffer());
		if (allowedUpdateDate != null)
			return new StateUpdateInfoImpl(allowedUpdateDate);

		return null;
	}

	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		if (!CardPaymentSystemPaymentLongOffer.class.isAssignableFrom(transfer.getType()))
			throw new GateException("��������� CardPaymentSystemPaymentLongOffer");

		CardPaymentSystemPaymentLongOffer longOffer = (CardPaymentSystemPaymentLongOffer) transfer;

		Client owner = getBusinessOwner(longOffer);
		Card card = getCard(owner, longOffer.getChargeOffCard(), longOffer.getOffice());

		AutoSubscriptionModRq_Type request = createAutoSubscriptionModRq(longOffer, owner, card);
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setAutoSubscriptionModRq(request);

		return ifxRq;
	}

	protected AutoSubscriptionModRq_Type createAutoSubscriptionModRq(CardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateLogicException, GateException
	{
		return billingRequestHelper.createAutoSubscriptionModRq(payment, owner, card);
	}


	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		AutoSubscriptionModRs_Type responce = ifxRs.getAutoSubscriptionModRs();
		Status_Type statusType = responce.getStatus();
		long statusCode = statusType.getStatusCode();

		if(statusCode == -105)
		{
			//������������� ��������
			throw new GateTimeOutException(statusType.getStatusDesc());
		}

		if (statusCode != 0)
		{
			//��� ������ ����������������.
			throwGateLogicException(statusType, AutoSubscriptionModRs_Type.class);
		}

		CardPaymentSystemPaymentLongOffer payment = (CardPaymentSystemPaymentLongOffer) document;
		payment.setExternalId(payment.getOperationUID());
	}

	protected BillingPayInqRq_Type createBillingPayInqRq(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		BillingPayInqRq_Type request = billingRequestHelper.createBillingPayInqRq(payment);
		// ������������� ������ ����������
		request.setIsAutoPayment(true);

		return request;
	}

	protected BillingPayPrepRq_Type createBillingPayPrepRq(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		try
		{
			// �������� ���� ������� �����
			payment.setExtendedFields(getFieldsWithoutMainSum(payment.getExtendedFields()));
			BillingPayPrepRq_Type request = billingRequestHelper.createBillingPayPrepRq(payment);
			// ������������� ������ ����������
			request.setIsAutoPayment(Boolean.TRUE);

			return request;
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
	}

	protected void processInqResponce(BillingPayInqRs_Type responce, CardPaymentSystemPayment payment) throws GateLogicException, DocumentLogicException, DocumentException, GateException
	{
		long statusCode = responce.getStatus().getStatusCode();
		if (!(statusCode == CORRECT_STATUS_CODE || statusCode == CONTROVERSIAL_STATUS_CODE))
		{
			throwGateLogicException(responce.getStatus(), BillingPayInqRs_Type.class);
		}

		Boolean autoPaymentAccessible = responce.getRecipientRec().getIsAutoPayAccessible();
		if (BooleanUtils.isNotTrue(autoPaymentAccessible))
		{
			//���� � ������ ���������, ��� ����������� �������� �� ���������� �� ��������, �������� ������������
			throw new GateLogicException(NOT_AUTO_PAY_ACCESSIBLE_MESSAGE);
		}
		//���� ������� �� �������, �������� extendedFields, ��� �� �� �������� ����������� �������� �����
		payment.setExtendedFields(null);
		super.processInqResponce(responce, payment);
	}

	protected void processPrepResponce(BillingPayPrepRs_Type responce, CardPaymentSystemPayment payment) throws GateLogicException, DocumentLogicException, DocumentException, GateException
	{
		RecipientRec_Type recipientRec = responce.getRecipientRec();
		Status_Type statusType = responce.getStatus();
		long statusCode = statusType.getStatusCode();
		if (!(statusCode == CORRECT_STATUS_CODE || statusCode == CONTROVERSIAL_STATUS_CODE))
			throwGateLogicException(statusType, BillingPayPrepRs_Type.class);

		payment.setBillingCode(responce.getSystemId());

		updatePaymentByRecipientRec(responce.getRecipientRec(), payment, statusType, false);
		if (responce.getCommission() != null && responce.getCommissionCur() != null && responce.getMadeOperationId() != null && responce.getWithCommision() != null)
		{
			//������ ����, ����������������� �� ��������� ���������� �������
			//������������� ������������ ������� � ��������
			payment.setIdFromPaymentSystem(responce.getMadeOperationId());

			CardPaymentSystemPaymentLongOffer longOffer = (CardPaymentSystemPaymentLongOffer) payment;
			//������������� ��������
			payment.setCommission(billingResponceSerializer.createMoney(responce.getCommission(), responce.getCommissionCur()));
			longOffer.setWithCommision(responce.getWithCommision());
			// ��������� ������ ���������� �������������
			fillAccessAutoPayments(recipientRec, longOffer);
		}
	}

	private void fillAccessAutoPayments(RecipientRec_Type recipientRec, CardPaymentSystemPaymentLongOffer payment) throws GateLogicException, GateException
	{
		AutopayDetails_Type autopayDetails = recipientRec.getAutoPayDetails();

		// �������� �����, ����� � ������� ���� ������ ��, ������� ������ �� ����
		payment.setInvoiceAutoPayScheme(null);
		payment.setAlwaysAutoPayScheme(null);
		payment.setThresholdAutoPayScheme(null);

		// ���� ����� �� ������, ������ �� ������
		if(autopayDetails == null || ArrayUtils.isEmpty(autopayDetails.getAutoPayType()))
			return;

		for(AutopayType_Type type: autopayDetails.getAutoPayType())
		{
			// ���������� �� �����
			if("A".equals(type.getValue()))
			{
				payment.setInvoiceAutoPayScheme(new InvoiceAutoPayScheme());
			}
			// ��������� ����������
			else if("P".equals(type.getValue()))
			{
				ThresholdAutoPayScheme threshold = new ThresholdAutoPayScheme();
				threshold.setInterval(false);
				threshold.setDiscreteValues(
						// �������� �����������, �.�. � ����� ���������� ����������� | � � ������ �������� ;
						StringHelper.getEmptyIfNull(autopayDetails.getLimit()).replace(';','|'));
				payment.setThresholdAutoPayScheme(threshold);
			}
			// ���������� ����������
			else if("R".equals(type.getValue()))
			{
				payment.setAlwaysAutoPayScheme(new AlwaysAutoPayScheme());
			}
			else
			{
			   log.warn("�� ��������� ��� ����������� " + type.getValue());
			}
		}
	}

	private List<Field> getFieldsWithoutMainSum(List<Field> fields)
	{
		if(fields == null || fields.isEmpty())
			return fields;

		List<Field> result = new ArrayList<Field>();
		for(Field field : fields)
		{
			if(!field.isMainSum())
				result.add(field);
		}

		return result;
	}

}
