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

	private static final String PERIOD_NOT_IN_INTERVAL_MESSAGE = "Пожалуйста, укажите срок по умолчанию в пределах заданного диапазона допустимого срока.";
	private static final String RATE_NOT_IN_INTERVAL_MESSAGE = "Пожалуйста, укажите ставку по умолчанию в пределах заданного диапазона допустимой ставки.";

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		NumericRangeValidator amountRangeValidator = new  NumericRangeValidator();
		amountRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100000000");
		amountRangeValidator.setMessage("Максимально допустимая сумма кредита не может быть больше 100 000 000.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{1,256}", "Поле \"Наименование\" должно содержать не более 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromAmount");
		fieldBuilder.setDescription("Сумма от");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toAmount");
		fieldBuilder.setDescription("Сумма до");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), amountRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromPeriod");
		fieldBuilder.setDescription("Срок от");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toPeriod");
		fieldBuilder.setDescription("Срок до");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultPeriod");
		fieldBuilder.setDescription("Срок по умолчанию");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromRate");
		fieldBuilder.setDescription("Ставка от");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toRate");
		fieldBuilder.setDescription("Ставка до");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultRate");
		fieldBuilder.setDescription("Ставка по умолчанию");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		MultiFieldsValidator compareAmountValidator = new CompareValidator();
		compareAmountValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareAmountValidator.setBinding(CompareValidator.FIELD_O1, "fromAmount");
		compareAmountValidator.setBinding(CompareValidator.FIELD_O2, "toAmount");
		compareAmountValidator.setMessage("Максимально допустимая сумма должна быть больше минимального значения.");

		MultiFieldsValidator comparePeriodValidator = new CompareValidator();
		comparePeriodValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		comparePeriodValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		comparePeriodValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		comparePeriodValidator.setMessage("Максимально допустимый срок должен быть больше минимального значения.");

		MultiFieldsValidator compareRateValidator = new CompareValidator();
		compareRateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareRateValidator.setBinding(CompareValidator.FIELD_O1, "fromRate");
		compareRateValidator.setBinding(CompareValidator.FIELD_O2, "toRate");
		compareRateValidator.setMessage("Максимально допустимая ставка должна быть больше минимального значения.");

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
