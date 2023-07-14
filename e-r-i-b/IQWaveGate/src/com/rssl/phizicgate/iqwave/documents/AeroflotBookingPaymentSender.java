package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * ������ ��� ������ ����� "���������"
 * @author niculichev
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class AeroflotBookingPaymentSender extends IQWaveAbstractDocumentSender
{
	private static final String DATE_EXPIRATION_ERROR_MESSAGE = "���� ����� ����� �����";

	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public AeroflotBookingPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		if (document.getType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("�������� ��� ������� - ��������� CardPaymentSystemPayment");
		}

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (StringHelper.isEmpty(payment.getChargeOffCard()) || payment.getChargeOffCardExpireDate() == null)
		{
			throw new GateException("��������� ������������ ������ ��������� ��������");
		}

		// ��������� ������� �� ���� �����
		if(isDateExpiration(payment))
			throw new GateLogicException(DATE_EXPIRATION_ERROR_MESSAGE);

		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_RES_REQUEST);
		fillExecutionMessage(message, payment);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		String externalId = getExternalId(response);
		payment.setExternalId(externalId);
		payment.setIdFromPaymentSystem(null);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		if (document.getType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("�������� ��� ������� - ��������� CardPaymentSystemPayment");
		}

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (StringHelper.isEmpty(payment.getChargeOffCard()) || payment.getChargeOffCardExpireDate() == null)
		{
			throw new GateException("��������� ������������ ������ ��������� ��������");
		}

		if (payment.getExternalId() == null)
			throw new GateException(String.format("������ ��������� �������� ������� � id=%s: �� �������� ExternalId", document.getId()));

		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_RES_REQUEST);
		fillExecutionMessage(message, payment);
		// ���������� ��������
		PaymentCompositeId compositeId = new PaymentCompositeId(payment.getExternalId());
		MessageHead messageHead = new MessageHeadImpl(compositeId.getMessageId(), XMLDatatypeHelper.parseDate(compositeId.getMessageDate()), null, null, null, null);
		serviceFacade.sendOnlineMessage(message, messageHead);
	}

	protected String getConfirmRequestName(GateDocument document)
	{
		return Constants.PAYMENT_RES_RESPONSE;
	}

	private void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			List<Field> extendedFields = payment.getExtendedFields();
			//��� ������� (��������)
			RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
			//���������� �� ����� ��������
			RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());

			// ����� ����� (���������-��������)
			Field recIdentifier = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if(recIdentifier == null || StringHelper.isEmpty((String) recIdentifier.getValue()))
				throw new GateException("�� ����� ����� ����� " + Constants.REC_IDENTIFIER_FIELD_NAME);
			message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, recIdentifier.getValue());

			// ������������� ������� ������������
			Field reservationId = BillingPaymentHelper.getFieldById(extendedFields, Constants.RESERVATION_ID);
			if(reservationId == null || StringHelper.isEmpty((String) reservationId.getValue()))
				throw new GateException("�� ����� ������������� " + Constants.RESERVATION_ID + " ������� ������������");
			message.addParameter(Constants.RESERVATION_ID, reservationId.getValue());

			// ��� ���������� ��������
			message.addParameter(Constants.CARD_PRODUCT_TYPE, payment.getChargeOffCardDescription());
			
			// ������������� ������
			Field identifierOrder = BillingPaymentHelper.getFieldById(extendedFields, Constants.RESEVRV_FULL_NUMBER);
			if(identifierOrder == null || StringHelper.isEmpty((String) identifierOrder.getValue()))
				throw new GateException("�� ����� ������� " +  Constants.RESEVRV_FULL_NUMBER + " ���������������� �����");
			message.addParameter(Constants.RESV_FULL_NUMBER, identifierOrder.getValue());

			Money amount = payment.getDestinationAmount();
			// ������ ��������
			message.addParameter(Constants.CURR_CODE, amount.getCurrency().getCode());
			// ����� �������(�������������)
			message.addParameter(Constants.SUMMA_TEG, amount.getDecimal());
		    //��� ���������� �����
		    fillMBOperCodeField(message, payment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ����������� ��������
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("�������� ��� ������� - ��������� CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		if (StringHelper.isEmpty(payment.getChargeOffCard())
				|| payment.getChargeOffCardExpireDate() == null)
			throw new GateException("��������� ������������ ������ ��������� ��������");

		try
		{
			List<Field> extendedFields = payment.getExtendedFields();
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if(identifierField == null)
			{
				// ���� ��� ������ �����, ��������� � ���������� ������������ ��� ����������
				extendedFields.add(AeroflotBookingHelper.createIdentifierBooking());
				//��������� ���� ����������� ��� ������-������ ����� �����������
				extendedFields.add(AeroflotBookingHelper.createAirlineIdentifyField());
			}
			else
			{
				if(!document.isTemplate())
				{
					// ���������� ������ ���������� � ����� � ������������ �����
					AeroflotBookingHelper.sendBookingInfoRequest(payment);
				}	
				//�����. ������������� ������������� ���� �� ������� �������
				payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� �������
	 * @param document  ������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}

	private boolean isDateExpiration(CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			Field dateExpirationField = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.RESERV_EXPIRATION);
			String dateValue = (String) dateExpirationField.getValue();
			Date dateExpiration = DateHelper.parseStringTimeWithoutSecond(dateValue);
			return new Date().compareTo(dateExpiration) > 0;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}
}
