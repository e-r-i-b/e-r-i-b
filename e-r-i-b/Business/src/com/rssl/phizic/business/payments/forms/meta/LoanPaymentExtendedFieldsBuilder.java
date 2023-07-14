package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.gate.loans.PenaltyDateDebtItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 07.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentExtendedFieldsBuilder
{
	public List<Field> buildFields()
	{
		List<Field> result = new ArrayList<Field>();
		result.add(buildField(LoanPayment.PRINCIPAL_AMOUNT_ATTRIBUTE_NAME, "Сумма основного долга"));
		result.add(buildField(LoanPayment.INTERESTS_AMOUNT_ATTRIBUTE_NAME, "Сумма выплат по процентам"));
		result.add(buildField(LoanPayment.TOTAL_PAYMENT_ATTRIBUTE_NAME, "Общая сумма платежа"));
		for (PenaltyDateDebtItemType itemType : PenaltyDateDebtItemType.values())
		{
			result.add(buildField(itemType.name(), itemType.getDescription()));
		}

		return result;
	}

	private Field buildField(String fieldName, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(fieldName);
		String source = String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, fieldName);
		fieldBuilder.setSource(source);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType("money");

		return fieldBuilder.build();
	}
}
