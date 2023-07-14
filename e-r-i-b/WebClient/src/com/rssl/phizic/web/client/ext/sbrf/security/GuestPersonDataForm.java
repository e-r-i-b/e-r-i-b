package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.AgeValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gulov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */
/**
 * Форма ввода уточняющих данных клиента
 */
public class GuestPersonDataForm extends ActionFormBase
{
	public static final Form FORM = createForm();

	private Map<String,String> fields = new HashMap<String, String>();
	private String phone;

	public Map<String,String> getFields()
	{
		return fields;
	}

	public String getField(String key)
	{
		return fields.get(key);
	}

	public void setField(String key, String obj)
	{
		fields.put(key, obj);
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,120}", "Поле Фамилия не должно превышать 120 символов"),
			new RegexpFieldValidator("^[а-яА-ЯЁё]+$", "Недопустимые символы в поле «Фамилия». Вводите Фамилию кириллицей (русскими буквами)"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Имя");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,120}", "Поле Имя не должно превышать 120 символов"),
			new RegexpFieldValidator("^[а-яА-ЯЁё]+$", "Недопустимые символы в поле «Имя». Вводите Имя кириллицей (русскими буквами)"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отчество");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,120}", "Поле Отчество не должно превышать 120 символов"),
			new RegexpFieldValidator("^[а-яА-ЯЁё]+$", "Недопустимые символы в поле «Отчество». Вводите Отчество кириллицей (русскими буквами)"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName("birthday");
		fieldBuilder.setType("date");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new AgeValidator(21, 100, "Ваш возраст не удовлетворяет условиям кредита"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Документ");
		fieldBuilder.setName("document");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,16}", "Поле Документ не должно превышать 16 символов"),
			new RegexpFieldValidator("^[0-9]+$", "Вводите серию и номер паспорта цифрами"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
	private String operation;

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}
}
