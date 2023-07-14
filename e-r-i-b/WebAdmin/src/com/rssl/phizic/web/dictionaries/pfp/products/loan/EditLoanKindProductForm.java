package com.rssl.phizic.web.dictionaries.pfp.products.loan;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPImageEditFormBase;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanKindProductForm extends PFPImageEditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	private static final String PERIOD_NOT_IN_INTERVAL_MESSAGE = "����������, ������� ���� �� ��������� � �������� ��������� ��������� ����������� �����.";
	private static final String RATE_NOT_IN_INTERVAL_MESSAGE = "����������, ������� ������ �� ��������� � �������� ��������� ��������� ���������� ������.";

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		NumericRangeValidator amountRangeValidator = new  NumericRangeValidator();
		amountRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100000000");
		amountRangeValidator.setMessage("����������� ���������� ����� ������� �� ����� ���� ������ 100 000 000.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("������������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{1,256}", "���� \"������������\" ������ ��������� �� ����� 250 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromAmount");
		fieldBuilder.setDescription("����� ��");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toAmount");
		fieldBuilder.setDescription("����� ��");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), amountRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromPeriod");
		fieldBuilder.setDescription("���� ��");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toPeriod");
		fieldBuilder.setDescription("���� ��");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultPeriod");
		fieldBuilder.setDescription("���� �� ���������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromRate");
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toRate");
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultRate");
		fieldBuilder.setDescription("������ �� ���������");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		MultiFieldsValidator compareAmountValidator = new CompareValidator();
		compareAmountValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareAmountValidator.setBinding(CompareValidator.FIELD_O1, "fromAmount");
		compareAmountValidator.setBinding(CompareValidator.FIELD_O2, "toAmount");
		compareAmountValidator.setMessage("����������� ���������� ����� ������ ���� ������ ������������ ��������.");

		MultiFieldsValidator comparePeriodValidator = new CompareValidator();
		comparePeriodValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		comparePeriodValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		comparePeriodValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		comparePeriodValidator.setMessage("����������� ���������� ���� ������ ���� ������ ������������ ��������.");

		MultiFieldsValidator compareRateValidator = new CompareValidator();
		compareRateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareRateValidator.setBinding(CompareValidator.FIELD_O1, "fromRate");
		compareRateValidator.setBinding(CompareValidator.FIELD_O2, "toRate");
		compareRateValidator.setMessage("����������� ���������� ������ ������ ���� ������ ������������ ��������.");

		MultiFieldsValidator compareFromPeriodInterval = new CompareValidator();
		compareFromPeriodInterval.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareFromPeriodInterval.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		compareFromPeriodInterval.setBinding(CompareValidator.FIELD_O2, "defaultPeriod");
		compareFromPeriodInterval.setMessage(PERIOD_NOT_IN_INTERVAL_MESSAGE);

		MultiFieldsValidator compareToPeriodInterval = new CompareValidator();
		compareToPeriodInterval.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareToPeriodInterval.setBinding(CompareValidator.FIELD_O1, "defaultPeriod");
		compareToPeriodInterval.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		compareToPeriodInterval.setMessage(PERIOD_NOT_IN_INTERVAL_MESSAGE);

		MultiFieldsValidator compareFromRateInterval = new CompareValidator();
		compareFromRateInterval.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareFromRateInterval.setBinding(CompareValidator.FIELD_O1, "fromRate");
		compareFromRateInterval.setBinding(CompareValidator.FIELD_O2, "defaultRate");
		compareFromRateInterval.setMessage(RATE_NOT_IN_INTERVAL_MESSAGE);
		
		MultiFieldsValidator compareToRateInterval = new CompareValidator();
		compareToRateInterval.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareToRateInterval.setBinding(CompareValidator.FIELD_O1, "defaultRate");
		compareToRateInterval.setBinding(CompareValidator.FIELD_O2, "toRate");
		compareToRateInterval.setMessage(RATE_NOT_IN_INTERVAL_MESSAGE);



		formBuilder.setFormValidators(  compareAmountValidator,
									    comparePeriodValidator,
									    compareRateValidator,
									    compareFromPeriodInterval,
									    compareToPeriodInterval,
									    compareFromRateInterval,
										compareToRateInterval);

		return formBuilder.build();
	}
}
