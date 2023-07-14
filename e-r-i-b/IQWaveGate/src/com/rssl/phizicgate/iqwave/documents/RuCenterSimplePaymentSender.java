package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizicgate.iqwave.messaging.Constants.REC_IDENTIFIER_SUFFIX_FIELD_NAME;

/**
 * @author Erkin
 * @ created 20.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * РуЦентр (интернет)
 */
public class RuCenterSimplePaymentSender extends SimplePaymentSender
{
	private static final long REC_IDENTIFIER_MIN_LENGTH = 1L;

	private static final long REC_IDENTIFIER_MAX_LENGTH = 8L;

	private static final Map<String, String> contractSuffixes = new HashMap<String, String>();

	static
	{
		contractSuffixes.put("/NIC-D", "100");
		contractSuffixes.put("/NIC-REG", "110");
	}

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public RuCenterSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	///////////////////////////////////////////////////////////////////////////

	protected List<Field> getNewExtendedFields(List<Field> extendedFields) throws GateException
	{
		List<Field> newExtendedFields = new ArrayList<Field>();

		// номер контракта (5-12 цифр)
		Field contractField = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
		if (contractField == null)
		{
			newExtendedFields.add(createIdentifierField());
		}

		// номер контракта (окончание)
		Field contractSuffixField = BillingPaymentHelper.getFieldById(extendedFields, REC_IDENTIFIER_SUFFIX_FIELD_NAME);
		if (contractSuffixField == null)
		{
			newExtendedFields.add(createContractSuffixField());
		}

		// сумма
		if (BillingPaymentHelper.getFieldById(extendedFields, Constants.AMOUNT_FIELD_NAME) == null)
		{
			newExtendedFields.add(BillingPaymentHelper.createAmountField());
		}

		return newExtendedFields;
	}

	protected Field createIdentifierField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.integer);
		field.setExternalId(Constants.REC_IDENTIFIER_FIELD_NAME);
		field.setName("Номер контракта");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setMinLength(REC_IDENTIFIER_MIN_LENGTH);
		field.setMaxLength(REC_IDENTIFIER_MAX_LENGTH);
		field.setHint(String.format("Введите номер контракта (%d-%d цифр)", REC_IDENTIFIER_MIN_LENGTH, REC_IDENTIFIER_MAX_LENGTH));
		return field;
	}

	private Field createContractSuffixField()
	{
		CommonField field = new CommonField();
		field.setExternalId(REC_IDENTIFIER_SUFFIX_FIELD_NAME);
		field.setName("Тип контракта");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setType(FieldDataType.list);
		List<ListValue> values = new ArrayList<ListValue>(1 + contractSuffixes.size());
		values.add(new ListValue("", "")); // «»
		for (String suff : contractSuffixes.keySet())
			values.add(new ListValue(suff, suff));
		field.setValues(values);
		return field;
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			String contractNumber = StringHelper.getEmptyIfNull(super.getIdentifier(payment));

			Field contractSuffixField = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), REC_IDENTIFIER_SUFFIX_FIELD_NAME);
			if (contractSuffixField == null)
				throw new GateException("Не найдено доп.поле " + REC_IDENTIFIER_SUFFIX_FIELD_NAME);

			String contractSuffix = StringHelper.getEmptyIfNull(contractSuffixField.getValue());
			contractSuffix = contractSuffixes.get(contractSuffix);

			return contractNumber + contractSuffix;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}
}
