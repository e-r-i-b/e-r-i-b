package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Gulov
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
/**
 * ‘орма дл€ работы со спсиком вопросов в за€вке на кредит
 */
public class ListLoanClaimQuestionForm extends ListFormBase
{
	public static final Form FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("questionId");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("ID вопроса");
		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setMessage("ID вопроса должен быть больше нул€");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d*", "ѕоле 'ID вопроса' должно состо€ть только из целых чисел."),
				numericRangeValidator
		);
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}

	private boolean useQuestionnaire;

	public boolean isUseQuestionnaire()
	{
		return useQuestionnaire;
	}

	public void setUseQuestionnaire(boolean useQuestionnaire)
	{
		this.useQuestionnaire = useQuestionnaire;
	}
}
