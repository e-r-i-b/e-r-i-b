package com.rssl.phizic.web.ext.sbrf.technobreaks;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditTechnoBreakForm extends EditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		DateParser dateParser = new DateParser(Constants.DATESTAMP);
		DateParser timeParser = new DateParser(Constants.TIMESTAMP);

		FieldBuilder fb = new FieldBuilder();
	    fb.setName(Constants.ADAPTER_UUID);
	    fb.setDescription("Внешняя система");
	    fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.FROM_DATE);
	    fb.setDescription("Дата начала действия технологического перерыва");
	    fb.setType(DateType.INSTANCE.getName());
		fb.setParser(dateParser);
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator(Constants.DATESTAMP, "Введите дату начала действия технологического перерыва в формате дд.мм.гггг"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.FROM_TIME);
	    fb.setDescription("Время начала действия технологического перерыва");
	    fb.setType(DateType.INSTANCE.getName());
		fb.setParser(timeParser);
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator(Constants.TIMESTAMP, "Введите время начала действия технологического перерыва в формате чч:мм:сс"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.TO_DATE);
	    fb.setDescription("Дата конца действия технологического перерыва");
	    fb.setType(DateType.INSTANCE.getName());
		fb.clearValidators();
		fb.setParser(dateParser);
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator(Constants.DATESTAMP, "Введите дату конца действия технологического перерыва в формате дд.мм.гггг"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.TO_TIME);
	    fb.setDescription("Время конца действия технологического перерыва");
	    fb.setType(DateType.INSTANCE.getName());
		fb.setParser(timeParser);
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator(Constants.TIMESTAMP, "Введите время конца действия технологического перерыва в формате чч:мм:сс"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.PERIODIC);
	    fb.setDescription("Периодичность");
	    fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.IS_DEFAULT_MESSAGE);
	    fb.setDescription("Тип сообщения");
	    fb.setType(BooleanType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.MESSAGE);
	    fb.setDescription("Сообщение");
	    fb.setType(StringType.INSTANCE.getName());
		fb.setEnabledExpression(new RhinoExpression("form.isDefaultMessage==false"));
		fb.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,200}", "Сообщение должно содержать не более 200 символов"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.ALLOW_OFFLINE_PAYMENTS);
	    fb.setDescription("Разрешить оффлайн-платежи");
	    fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, Constants.FROM_DATE);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, Constants.FROM_TIME);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, Constants.TO_DATE);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, Constants.TO_TIME);
		dateTimeValidator.setMessage("Дата окончания должна быть больше даты начала периода действия технологического перерыва." +
				" Пожалуйста, укажите другие даты.");
		DateTimeCompareValidator timeValidator = new DateTimeCompareValidator();
		timeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		timeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, Constants.FROM_DATE);
		timeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, Constants.FROM_TIME);
		//для сравнения времени не важно какая дата. за основу будем брать дату начала.
		timeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, Constants.FROM_DATE);
		timeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, Constants.TO_TIME);
		timeValidator.setMessage(" Время окончания действия технологичкого перерыва должно быть больше времени начала дейтсвия технологического перерыва." +
				" Пожалуйста, укажите другое время");
		timeValidator.setEnabledExpression(new RhinoExpression("form.periodic != 'SINGLE' "));

		formBuilder.addFormValidators(dateTimeValidator,timeValidator);
		
		return formBuilder.build();
	}
}
