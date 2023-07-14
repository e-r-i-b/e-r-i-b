package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import static com.rssl.phizgate.common.payments.BillingPaymentHelper.getFieldById;
import static com.rssl.phizicgate.iqwave.messaging.Constants.REC_IDENTIFIER_FIELD_NAME;

/**
 * @author Erkin
 * @ created 22.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Космос ТВ (телевидение)
 */
public class CosmosTVSimplePaymentSender extends SimplePaymentSender
{
	private static final int REC_IDENTIFIER_MIN_LENGTH = 3;

	private static final int REC_IDENTIFIER_MAX_LENGTH = 9;

	/**
	 * Весовой ряд (см. CHG027440)
	 */
	private static final byte[] WEIGHTS = new byte[] { 3, 5, 7, 1, 3, 5, 7 };

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public CosmosTVSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	///////////////////////////////////////////////////////////////////////////

	protected Field createIdentifierField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.integer);
		field.setExternalId(REC_IDENTIFIER_FIELD_NAME);
		field.setName("Номер контракта");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setMinLength((long)REC_IDENTIFIER_MIN_LENGTH);
		field.setMaxLength((long)REC_IDENTIFIER_MAX_LENGTH);
		field.setHint(String.format("Введите номер контракта (%d-%d цифр)", REC_IDENTIFIER_MIN_LENGTH, REC_IDENTIFIER_MAX_LENGTH));
		return field;
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		super.prepare(document);

		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		try
		{
			String contract = StringHelper.getEmptyIfNull(getIdentifier(payment));
			if (StringHelper.isEmpty(contract))
				return;

			CommonField contractField = (CommonField) getFieldById(payment.getExtendedFields(), REC_IDENTIFIER_FIELD_NAME);
			if (!validateContract(contract)) {
				contractField.setError("Указан недопустимый номер контракта");
				payment.setIdFromPaymentSystem(Constants.EMPTY_VALUE);
			}
			else contractField.setError(Constants.EMPTY_VALUE);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private boolean validateContract(String contract)
	{
		return computeFirstCheckNumber(contract) == Byte.valueOf(""+contract.charAt(7))
				&& computeSecondCheckNumber(contract) == Byte.valueOf(""+contract.charAt(8));
	}

	private int computeFirstCheckNumber(String contract)
	{
		if (contract.length() < WEIGHTS.length)
			throw new IllegalArgumentException("Недопустимый номер контракта");

		try
		{
			long summ = 0L;
			for (int i=0; i<WEIGHTS.length; i++)
				summ += WEIGHTS[i] * Byte.parseByte(""+contract.charAt(i));
			String summAsString = String.valueOf(summ);
			return Byte.parseByte("" + summAsString.charAt(summAsString.length()-1));
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Недопустимый номер контракта", e);
		}
	}

	private int computeSecondCheckNumber(String contract)
	{
		if (contract.length() < WEIGHTS.length)
			throw new IllegalArgumentException("Недопустимый номер контракта");

		try
		{
			long summ = 0L;
			for (int i=0; i<WEIGHTS.length; i++)
				summ += WEIGHTS[i] + Byte.parseByte(""+contract.charAt(i));
			String summAsString = String.valueOf(summ);
			return Byte.parseByte("" + summAsString.charAt(summAsString.length()-1));
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Недопустимый номер контракта", e);
		}
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String contract = StringHelper.getEmptyIfNull(super.getIdentifier(payment));
		if (StringHelper.isEmpty(contract))
			return null;
		return StringHelper.addLeadingZeros(contract, REC_IDENTIFIER_MAX_LENGTH);
	}
}
