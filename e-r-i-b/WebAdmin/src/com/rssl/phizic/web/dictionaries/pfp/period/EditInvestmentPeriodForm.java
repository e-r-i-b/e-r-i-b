package com.rssl.phizic.web.dictionaries.pfp.period;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.types.StringType;

import java.math.BigInteger;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования горизонта инвестирования
 */

public class EditInvestmentPeriodForm extends EditFormBase
{
	public static final String PERIOD_FIELD_NAME = "period";

	public static final Form EDIT_FORM = createForm();

	private static final BigInteger NAME_MAX_LENGTH = BigInteger.valueOf(50);

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PERIOD_FIELD_NAME);
		fieldBuilder.setDescription("Горизонт инвестирования");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new LengthFieldValidator(NAME_MAX_LENGTH));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
