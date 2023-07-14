package com.rssl.phizic.web.dictionaries.kbk;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 10.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditKBKForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm(){
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код");
		fieldBuilder.setName("code");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("[0-9]{20}", "Код бюджетной классификации должен содержать 20 цифр.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,300}", "Описание должно содержать не более 300 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Краткое наименование налога");
		fieldBuilder.setName("shortName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		
		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", "25");
		lengthValidator.setParameter("minlength", "1");
		lengthValidator.setMessage("Краткое наименование налога должно содержать не более 25 символов.");
		fieldBuilder.setValidators(lengthValidator);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Типовое назначение");
		fieldBuilder.setName("appointment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,210}", "Типовое назначение должно содержать не более 210 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип оплаты");
		fieldBuilder.setName("paymentType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,50}", "Тип оплаты должен содержать не более 50 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальная сумма коммиссии");
		fieldBuilder.setName("minCommission");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальная сумма коммиссии");
		fieldBuilder.setName("maxCommission");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Процентная ставка");
		fieldBuilder.setName("rate");
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(100(.0{1,2})?$|^\\d{1,2}(.\\d{1,2})?$){1}")
			);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
