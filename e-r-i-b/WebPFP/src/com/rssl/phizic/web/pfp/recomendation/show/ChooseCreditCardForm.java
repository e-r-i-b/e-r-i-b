package com.rssl.phizic.web.pfp.recomendation.show;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.ProductEfficacy;

/**
 * ����� ��������� �����
 * @author komarov
 * @ created 07.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ChooseCreditCardForm extends ShowRecommendationForm
{
	public Form createForm(ProductEfficacy accountPercent, ProductEfficacy thanksPercent)
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardId");
		fieldBuilder.setDescription("������������� �����");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("account");
		fieldBuilder.setDescription("������������ �� �����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountIncome");
		fieldBuilder.setDescription("�������� ��������� �� �����");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator()
		);
		if ( accountPercent.getFromIncome()!= null && accountPercent.getToIncome() != null)
		{
			NumericRangeValidator numericMinValidator = new NumericRangeValidator();
			numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,accountPercent.getFromIncome().toString());
			numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
			NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
			numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,accountPercent.getToIncome().toString());
			numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
			fieldBuilder.addValidators(numericMinValidator,numericMaxValidator);
		}
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.account == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("thanks");
		fieldBuilder.setDescription("������������ �� \"�������\"");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("thanksPercent");
		fieldBuilder.setDescription("���������� ����������� \"�������\"");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator()
		);
		if ( thanksPercent.getFromIncome()!= null && thanksPercent.getToIncome() != null)
		{
			NumericRangeValidator numericMinValidator = new NumericRangeValidator();
			numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,thanksPercent.getFromIncome().toString());
			numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
			NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
			numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,thanksPercent.getToIncome().toString());
			numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
			fieldBuilder.addValidators(numericMinValidator,numericMaxValidator);
		}
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.thanks == true"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
