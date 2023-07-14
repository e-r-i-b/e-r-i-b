package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Gulov
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма редактирования вопроса заявки на кредит
 */
public class EditLoanClaimQuestionForm extends EditFormBase
{
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		RequiredFieldValidator requiredFieldValidator  = new RequiredFieldValidator();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("questionId");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("ID вопроса");
		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setMessage("ID вопроса должен быть больше нуля");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator("\\d*", "Поле 'ID вопроса' должно состоять только из целых чисел."),
				numericRangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("question");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Текст вопроса");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,500}", "Текст вопроса не должен превышать 500 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("answerType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Тип ответа");
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
