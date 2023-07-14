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
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * ������ ���  � ���������� �� ������� ������ ������ ����� ���������
 * �������	        ���	            �����������	                    ���������
 * <Route>	        RouteCode       ��� ������� (��������)	        [1]
 * <DebitCard>	    CardInf         ���������� �� ����� ��������	[1]
 * <RecIdentifier>	Requisite       ����� ���������� ���������	    [1]
 * <CurrCode>	    IsoCode         ������ ��������	                [1]
 * <Summa>	        Money           C���� ������� (�������������)	[1]
 */
public class FNSPaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public FNSPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_FNS_REQUEST, Constants.PAYMENT_FNS_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		return Constants.PAYMENT_DEBTS_FNS_REQUEST;
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

			//���� �������������� ���� ������� ���� �� �������, �� ��������� ���
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if (identifierField == null)
			{
				newExtendedFields.add(RequestHelper.createIdentifierField());
			}
			else
			{
				identifierField.setEditable(false);
			}

			//���������� ����� �������
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}
			Debt debt = DebtsHelper.parseMGTSFNSDebt(getDebtResponse(payment));
			Money debtValue = debt.getRows().get(0).getDebt();
			if(debtValue.getAsCents() == 0 && !payment.isTemplate())
				throw new GateLogicException("�� �� ������ ��������� ������ � ������ ������� ����������, �.�. � ��� ����������� �������������.");
			if(debtValue.getAsCents() <0 && !payment.isTemplate() && debt.isFixed())
				throw new GateLogicException("�� �� ������ ��������� ������ � ������ ������� ����������, �.�. � ��� ����������� ������������� (��������� ����������" + debtValue.getDecimal().toString().replace('-',' ') + " �.).");
			//��������� ���� ������������� (���� �� ������ �������)
			CommonField debtField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
			if (debtField == null && !payment.isTemplate())
			{
				debtField = (CommonField) RequestHelper.createDebtField();
				debtField.setType(FieldDataType.money);
				debtField.setDefaultValue(debtValue.getDecimal().toString());
				debtField.setEditable(false);

				newExtendedFields.add(debtField);
			}

			//��������� ���� �����
			CommonField amountField = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
			if (amountField == null)
			{
				amountField = BillingPaymentHelper.createAmountField();
				amountField.setDefaultValue(debtValue.getDecimal().toString());
				amountField.setEditable(payment.isTemplate());

				newExtendedFields.add(amountField);
			}

			//���������� ����� �������
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			if (payment.isTemplate())
			{
				//� ������� ������� ���� ������������� �� ����������
				extendedFields.remove(debtField);
			}
			else
			{
				BigDecimal paymentAmount = new BigDecimal((String) amountField.getValue());
				//���� ����� ������� �� ����� ����� �����������, ��������
				if (paymentAmount.compareTo(debtValue.getDecimal()) != 0)
				{
					amountField.setError("����������, ������� ����� �������, ������ ����� ������� �������������.");
					payment.setIdFromPaymentSystem(Constants.EMPTY_VALUE);
					return;
				}
			}

			//�����. ������������� ������������� ���� �� ������� �������
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
			amountField.setError(Constants.EMPTY_VALUE);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}
}
