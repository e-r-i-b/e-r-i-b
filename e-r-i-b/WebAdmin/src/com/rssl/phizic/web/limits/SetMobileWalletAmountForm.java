package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class SetMobileWalletAmountForm extends EditFormBase
{
	private long departmentId;

	public static final Form EDIT_MOBILE_WALLET_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		NumericRangeValidator numericValidator= new NumericRangeValidator();
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.00");
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "999999999.99");
		numericValidator.setMessage("Значение суммы должно быть в диапазоне 0,00 - 999 999 999,99");

		fieldBuilder.setName("amount");
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),numericValidator, new MoneyFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(long departmentId)
	{
		this.departmentId = departmentId;
	}
}
