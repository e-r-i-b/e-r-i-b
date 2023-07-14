package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import static com.rssl.phizicgate.iqwave.messaging.Constants.REC_IDENTIFIER_FIELD_NAME;

/**
 * @author Erkin
 * @ created 25.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * НТВ+ (телевидение)
 */
public class NTVPlusSimplePaymentSender extends SimplePaymentSender
{
	private static final int REC_IDENTIFIER_LENGTH = 10;

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public NTVPlusSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	///////////////////////////////////////////////////////////////////////////

	protected Field createIdentifierField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.integer);
		field.setExternalId(REC_IDENTIFIER_FIELD_NAME);
		field.setName("Номер договора");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setMinLength((long) REC_IDENTIFIER_LENGTH);
		field.setMaxLength((long) REC_IDENTIFIER_LENGTH);
		field.setHint(String.format("Введите номер договора (%d цифр)", REC_IDENTIFIER_LENGTH));
		return field;
	}
}
