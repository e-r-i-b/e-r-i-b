package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 12.05.2010
 * @ $Author$
 * @ $Revision$
 * ������� ������
�������	        ���	            �����������	                            ���������
<Route>	        RouteCode       ��� ������� (��������)	                [1]
<DebitCard>	    CardInf         ���������� �� ����� ��������	        [1]
<CurrCode>	    IsoCode         ������ ��������	                        [1]
<RecIdentifier>	Requisite       ��������, ���������������� �����������	[1]
<Summa>	        Money           C���� �������	                        [1]
<MBOperCode>    MBOperCode      ��� �������� � ��                       [0-1]
 */
public class SimplePaymentSender extends PaymentSystemPaymentSenderBase
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public SimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (payment.isEinvoicing())
			return new Pair<String, String>(Constants.SIMPLE_PAYMENT_ECOMMERCE_REQUEST, Constants.SIMPLE_PAYMENT_ECOMMERCE_RESPONSE);
		else
			return new Pair<String, String>(Constants.SIMPLE_PAYMENT_REQUEST, Constants.SIMPLE_PAYMENT_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		throw new UnsupportedOperationException();
	}

	protected void addExtendedFieldsToDebtRequest(GateMessage message, List<Field> extendedFields) throws GateException
	{

	}

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
			List<Field> extendedFields    = payment.getExtendedFields();
			List<Field> newExtendedFields = getNewExtendedFields(extendedFields);

			//���������� ����� ��������
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//�����. ������������� ������������� ���� �� ������� �������
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ���������� ����������� ���� �������
	 * @param extendedFields - ������ �����, ������� ��� ���� � �������
	 * @return ������ �����, ������� ����� �������� � �����
	 */
	protected List<Field> getNewExtendedFields(List<Field> extendedFields) throws GateException
	{
		List<Field> newExtendedFields = new ArrayList<Field>();

		//���� �������������� ���� ������� ���� �� ������� � �� ������, �� ��������� ���
		Field identifierField = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
		if (identifierField == null)
		{
			newExtendedFields.add(createIdentifierField());
		}

		//���� �������������� ���� ����� �� ������� � �� ������, �� ��������� ���
		if (BillingPaymentHelper.getMainSumField(extendedFields) == null)
		{
			newExtendedFields.add(createAmountField());
		}

		return newExtendedFields;
	}

	/**
	 * ������ ���� � ��������������� ����������� (����� �������� ����� � �.�.)
	 * @return
	 */
	protected Field createIdentifierField()
	{
		return RequestHelper.createIdentifierField();
	}

	/**
	 * @return ���� ������� �����
	 */
	protected Field createAmountField()
	{
		return BillingPaymentHelper.createAmountField();
	}

	/**
	 * ��������� ��������� �� ��������� �������
	 * @param message ���������
	 * @param payment ������
	 * @throws GateException
	 */
	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		Calendar offCardExpireDate = payment.getChargeOffCardExpireDate();
		String chargeOffCard = payment.getChargeOffCard();
		String pointCode = payment.getReceiverPointCode();
		Money amount = payment.getDestinationAmount();

		//��� ������� (��������)
		RequestHelper.appendRouteCode(message, "Route", Long.valueOf(pointCode));
		//���������� �� ����� ��������
		RequestHelper.appendCardInf(message, chargeOffCard, offCardExpireDate);
		//������ ��������
		message.addParameter("CurrCode", amount.getCurrency().getCode());
		//��������, ���������������� �����������
		message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, getIdentifier(payment));
		//C���� �������
		RequestHelper.appendSumma(message, amount, "Summa");
	    //��� ���������� �����
	    fillMBOperCodeField(message, payment);
}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//������������ ������ �� ����
	}

	/**
	 * ����� �������/������� ������
	 *
	 * <Route>	RouteCode ��� ������� (��������)	[1]
	 * <CurrCode>	IsoCode ������ ��������	[1]
	 * <RecIdentifier>	Requisite ��������, ���������������� �����������	[1]
	 * <OperationIdentifier>	Requisite ������������� �������� �������� IQWAVE	[1]
	 * <Summa>	Money C���� ��������	[1]
	 * <MBOperCode>    MBOperCode  ��� �������� � ��  [0-1]
	 * @param withdrawDocument - �������� ������
	 */
	public void rollback(WithdrawDocument withdrawDocument) throws GateException, GateLogicException
	{
		if (withdrawDocument.getWithdrawType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("�������� ��� ����������� ������� - ��������� CardPaymentSystemPayment");
		}

		CardPaymentSystemPayment cardPaymentSystemPayment = (CardPaymentSystemPayment) (withdrawDocument).getTransferPayment();
		if (StringHelper.isEmpty(cardPaymentSystemPayment.getIdFromPaymentSystem()))
			throw new GateException("�� ���������� ������������� ����������� �������");

		WebBankServiceFacade serviceFacade   = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		String requestName = withdrawDocument.getWithdrawMode() == WithdrawMode.Partial ? Constants.REFUND_SIMPLE_PAYMENT_REQUEST
																						: Constants.REVERSAL_SIMPLE_PAYMENT_REQUEST;
		GateMessage message = serviceFacade.createRequest(requestName);

		Money withdrawAmount = withdrawDocument.getChargeOffAmount();
		String pointCode = cardPaymentSystemPayment.getReceiverPointCode();

		//��� ������� (��������)
		RequestHelper.appendRouteCode(message, Constants.ROUTE_TAG_NAME, Long.valueOf(pointCode));
		//������ ��������
		message.addParameter(Constants.CURR_CODE, withdrawAmount.getCurrency().getCode());
		//��������, ���������������� �����������
		message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, getIdentifier(cardPaymentSystemPayment));
		//������������� �������� �������� IQWAVE
		message.addParameter(Constants.OPERATIOIN_IDENTIFIER, cardPaymentSystemPayment.getIdFromPaymentSystem());
		//C���� �������
		RequestHelper.appendSumma(message, withdrawAmount, Constants.SUMMA_TEG);
	    //��� ���������� �����
	    fillMBOperCodeField(message, withdrawDocument);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		String externalId = getExternalId(response);
		withdrawDocument.setExternalId(externalId);

		addOfflineDocumentInfo(withdrawDocument);
	}
}
