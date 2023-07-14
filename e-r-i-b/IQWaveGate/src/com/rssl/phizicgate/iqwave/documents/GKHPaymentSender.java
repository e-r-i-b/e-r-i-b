package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 * ������ ���
 * �������	            ���	            �����������	                        ���������
 * <Route>	            RouteCode       ��� ������� (��������)	                [1]
 * <DebitCard>	        CardInf         ���������� �� ����� ��������	        [1]
 * <RecIdentifier>	    Requisite       ��������, ���������������� �����������	[1]
 * <DebtsPeriod>	    PayPeriod       ������ �������������	                [1]
 * <DebtsCurrCode>	    IsoCode         ������ �������������	                [1]
 * <DebtsSumma>	        Money           ����� �������������	                    [1]
 * <DebtsComission>	    Money           ��������	                            [1]
 * <DebtsCaseNumber>    Long            ���� �����������, ���� � ������	        [0-1]
 *                                      �������������� �������� ���� ��
 *                                      ���� ������� ������.
 * <MBOperCode>         MBOperCode      ��� ���������� �����                    [0-1]
 */
public class GKHPaymentSender extends PaymentSystemPaymentSenderBase
{
	public GKHPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_GKH_REQUEST, Constants.PAYMENT_GKH_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		return Constants.PAYMENT_DEBTS_GKH_REQUEST;
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
			List<Field> newExtendedFields = new ArrayList<Field>();

			//���� �������������� ���� �� �������, �� ��������� ���, �� ��������� ���� ������ ��� �� �������������
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if (identifierField == null)
			{
				newExtendedFields.add(RequestHelper.createIdentifierField());
			}
			else
			{
				identifierField.setEditable(false);
			}

			//���� ���� �� �������, ��������� ���, �� ��������� ���� ������ ��� �� �������������
			CommonField periodField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.PERIOD_FIELD_NAME);
			if (periodField == null)
			{
				newExtendedFields.add(RequestHelper.createPeriodField());
			}
			else
			{
				periodField.setEditable(false);
			}

			//���������� ����� �������
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			if (payment.isTemplate())
			{
				//���� ������������� � ������� ������ ��� �������� �� ������ �����������.
				Iterator<Field> iter = extendedFields.iterator();
				while (iter.hasNext()) {
					Field field = iter.next();
					if (field.getExternalId().equals(Constants.DEBT_FIELD_NAME) ||
							field.getExternalId().equals(Constants.DEBT_ROW_FIELD_NAME)) {
						iter.remove();
					}
				}

				//��� ������� ������� �� ����������� � ���� ����� �������� �������������
				//���� ���� �� �������, ��������� ���
				CommonField amountField  = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if (amountField == null)
				{
					amountField = BillingPaymentHelper.createAmountField();
					newExtendedFields.add(amountField);
				}
				amountField.setEditable(true);
				amountField.setError(Constants.EMPTY_VALUE);

				//���������� ����� �������
				if  (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}
			}
			else
			{
				//���� ����������� ���� ��������, �������� ����������� � �� ������
				List<Debt> debts = DebtsHelper.parseGKHDebts(getDebtResponse(payment));
				if (debts.isEmpty())
					throw new GateLogicException("�� �� ������ ��������� ������ � ������ ������� ����������, �.�. � ��� ����������� �������������.");

				CommonField debtField    = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
				CommonField debtRowField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_ROW_FIELD_NAME);
				//���� ���� �� �������, ��������� ���, �� ��������� ���� ������ ��� �� �������������
				if (debtField == null)
				{
					debtField = (CommonField) RequestHelper.createDebtField();
					debtField.setType(FieldDataType.list);

					List<ListValue> values = new ArrayList<ListValue>();
					for (Debt debt : debts)
					{
						values.add(new ListValue(debt.getDescription(), debt.getCode()));
					}
					debtField.setValues(values);

					newExtendedFields.add(debtField);
				}
				else
				{
					debtField.setEditable(false);
				}

				//���������� ����� �������
				if (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				//���� ���� �� �������, ��������� ���, �� ��������� ���� ������ ��� �� �������������
				if (debtRowField == null)
				{
					newExtendedFields.add(RequestHelper.createDebtRowField(findDebtRows(debts, (String) debtField.getValue())));
				}
				else
				{
					debtRowField.setEditable(false);
				}

				//���������� ����� �������
				if (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				//��� ������� ������� �� ����������� � ���� ����� �������� �������������
				//���� ���� �� �������, ��������� ���
				CommonField amountField  = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if (amountField == null)
				{
					amountField = BillingPaymentHelper.createAmountField();

					DebtRow debtRow = findDebtRow(debts, (String)debtField.getValue(), (String)debtRowField.getValue());
					//��������� ������ ��� ������������� ������������ ��� ������������� ��������������.
					if(debtRow.getDebt().getDecimal().compareTo(BigDecimal.valueOf(0L)) < 1)
						throw new GateLogicException("�� �� ������ ��������� ������ � ������ ������� ����������, �.�. � ��� ����������� ������������� (��������� ����������" + debtRow.getDebt().getDecimal().toString().replace('-',' ') + "�.).");
					amountField.setDefaultValue(debtRow.getDebt().getDecimal().toString());

					//�������� ��� ������� ������� �� �������������
					payment.setCommission(debtRow.getCommission());
					amountField.setEditable(false);

					newExtendedFields.add(amountField);
				}
				amountField.setError(Constants.EMPTY_VALUE);

				//���������� ����� �������
				if  (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				BigDecimal paymentAmount = new BigDecimal((String) amountField.getValue());
				BigDecimal debtAmount = findDebtRow(debts, (String) debtField.getValue(), (String) debtRowField.getValue()).getDebt().getDecimal();

				//����� ������� ������ ���� ����� ����� �����������, ���������, ��������� ���������
				if (debtAmount.compareTo(paymentAmount) != 0)
				{
					payment.setIdFromPaymentSystem(Constants.EMPTY_VALUE);
					amountField.setError("����������, ������� ����� �������, ������ ����� ������� �������������.");
					return;
				}
			}

			//�����. ������������� ������������� ���� �� ������� �������
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//������������ ������ �� ����
	}

	protected void addExtendedFieldsToDebtRequest(GateMessage message, List<Field> extendedFields) throws GateException, GateLogicException
	{
		CommonField period = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.PERIOD_FIELD_NAME);
		//� ����� ����� ���������� �������� ������ ���/���
		RequestHelper.appendPeriod(message, Constants.PERIOD_FIELD_NAME, (String) period.getValue());
	}

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			Money amount = payment.getDestinationAmount();
			List<Field> extendedFields = payment.getExtendedFields();
			//��� ������� (��������)
			RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
			//���������� �� ����� ��������
			RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());
			//��������, ���������������� �����������
			message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, getIdentifier(payment));
			//������ �������������
			Field period = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.PERIOD_FIELD_NAME);
			RequestHelper.appendPeriod(message, "DebtsPeriod", (String) period.getValue());
			//������ ��������
			message.addParameter("DebtsCurrCode", amount.getCurrency().getCode());
			//����� �������������
			RequestHelper.appendSumma(message, amount, "DebtsSumma");
			//��������
			RequestHelper.appendSumma(message, payment.getCommission(), "DebtsComission");
			//DebtsCaseNumber
			String debtsCaseNumber = (String) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_ROW_FIELD_NAME).getValue();
			message.addParameter("DebtsCaseNumber", debtsCaseNumber.substring(debtsCaseNumber.indexOf("-")+1));
            //��� ���������� �����
		    fillMBOperCodeField(message, payment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private DebtRow findDebtRow(List<Debt> debts, String debtValue, String debtRowValue) throws GateLogicException
	{
		for (DebtRow debtRow : findDebtRows(debts, debtValue))
		{
			if (debtRow.getCode().equals(debtRowValue))
				return debtRow;
		}
		throw new GateLogicException("�� ����������� ������� ��������� �������. ����������, ��������� ��������� � ��������� ��������.");
	}

	private List<DebtRow> findDebtRows(List<Debt> debts, String debtValue) throws GateLogicException
	{
		for (Debt debt : debts)
		{
			if (debt.getCode().equals(debtValue))
				return debt.getRows();
		}
		throw new GateLogicException("�� ����������� ������� ��������� �������. ����������, ��������� ��������� � ��������� ��������.");
	}
}
