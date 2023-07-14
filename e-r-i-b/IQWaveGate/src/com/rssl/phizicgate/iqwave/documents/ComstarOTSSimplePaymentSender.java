package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.util.regex.Pattern;

/**
 * Сендер для ПУ Комстар ОТС (интернет)
 * @author Dorzhinov
 * @ created 25.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class ComstarOTSSimplePaymentSender extends SimplePaymentSender
{
	private static final Pattern pattern = Pattern.compile("^\\d{6,7}-?\\d{2}$");
	private static final int REC_IDENTIFIER_MIN_LENGTH = 8;
	private static final int REC_IDENTIFIER_MAX_LENGTH = 10;
	private static final String SEPARATOR = "-";

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public ComstarOTSSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Field createIdentifierField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.REC_IDENTIFIER_FIELD_NAME);
		field.setName("Номер лицевого счета");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setMinLength((long)REC_IDENTIFIER_MIN_LENGTH);
		field.setMaxLength((long)REC_IDENTIFIER_MAX_LENGTH);
		field.setHint("Введите номер лицевого счета. Например, 234852-06 или 951236489");
		return field;
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		if(!pattern.matcher(identifier).matches())
			throw new GateException("Номер договора '"+identifier+"' не соответствует формату.");

		if(identifier.contains(SEPARATOR))
			identifier = identifier.replace(SEPARATOR, "");
		return identifier;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		String identifier = super.getIdentifier(payment).toString();
		if(!pattern.matcher(identifier).matches())
			throw new GateLogicException("Номер договора '"+identifier+"' не соответствует формату.");
	}
}
