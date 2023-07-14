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
 * ����� ��� ������ �� ������� �������� � ������ �� ������
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
		fieldBuilder.setDescription("ID �������");
		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setMessage("ID ������� ������ ���� ������ ����");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d*", "���� 'ID �������' ������ �������� ������ �� ����� �����."),
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
