package com.rssl.phizic.web.news;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * ‘орма создани€ новостной расылки
 * @author gladishev
 * @ created 02.11.13
 * @ $Author$
 * @ $Revision$
 */
public class CreateNewsDistributionForm extends EditFormBase
{
	public static final String MAIL_COUNT_FIELD = "mailCount";
	public static final String TIMEOUT_FIELD = "timeout";
	public static final Form EDIT_FORM = createForm();
	private boolean mainNews = false;

	public boolean isMainNews()
	{
		return mainNews;
	}

	public void setMainNews(boolean mainNews)
	{
		this.mainNews = mainNews;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		//количество писем в одной пачке
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(" оличество писем в пачке");
		fieldBuilder.setName(MAIL_COUNT_FIELD);
		fieldBuilder.setType(IntType.INSTANCE.getName());

		NumericRangeValidator mailCountRangeValidator = new NumericRangeValidator();
		mailCountRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		mailCountRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "20");
		mailCountRangeValidator.setMessage("Ќеверный формат данных в поле: " + fieldBuilder.getDescription() + ", введите числовое значение в диапазоне 1...20.");
		fieldBuilder.addValidators(new RequiredFieldValidator(), mailCountRangeValidator);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ѕериод времени между рассылками пачек");
		fieldBuilder.setName(TIMEOUT_FIELD);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		NumericRangeValidator timeoutRangeValidator = new NumericRangeValidator();
		timeoutRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		timeoutRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "60");
		timeoutRangeValidator.setMessage("Ќеверный формат данных в поле: " + fieldBuilder.getDescription() + ", введите числовое значение в диапазоне 1...60.");
		fieldBuilder.addValidators(new RequiredFieldValidator(), timeoutRangeValidator);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
