package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;
import com.rssl.phizic.business.mail.MailType;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 *
 * Форма разархивации писем
 */

public class UnArchiveMailForm extends EditFormBase
{
	private static final List<String> mailTypes = new ArrayList<String>();
	static
	{
		for(MailType type: MailType.values())
		{
			 mailTypes.add(type.name());
		}
	}
	public static final Form UNARCHIVING_FORM = createForm();

	private Map<String,Object> defaultParameters = new HashMap<String, Object>();

	/**
	 * задать дефолтные параметры
	 * @param defaultParameters параметры
	 */
	public void setDefaultParameters(Map<String, Object> defaultParameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.defaultParameters = defaultParameters;
	}

	/**
	 * получить дефолтный параметр
	 * @param key ключ параметра
	 * @return значение параметра
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public Object getDefaultParameter(String key)
	{
		return defaultParameters.get(key);
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb;

		fb = new FieldBuilder();
		fb.setName("folder");
		fb.setDescription("Каталог/Путь");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(),
						 new RegexpFieldValidator(".{1,128}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("fromPeriod");
		fb.setDescription("Период с");
		fb.setType(DateType.INSTANCE.getName());
		fb.addValidators(new DateNotInFutureValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("toPeriod");
		fb.setDescription("Период по");
		fb.setType(DateType.INSTANCE.getName());
		fb.addValidators(new DateNotInFutureValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("showUnArchivingMailToClient");
		fb.setDescription("Отображать клиенту разархивированные письма");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("ANSWER");
		fb.setDescription("Статус: Ответ направлен");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("READ");
		fb.setDescription("Статус: Прочитано");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("DRAFT");
		fb.setDescription("Статус: Черновик ответа");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("NEW_EPLOYEE_MAIL");
		fb.setDescription("Статус: Новое письмо сотрудника");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("ANSWER_EPLOYEE_MAIL");
		fb.setDescription("Статус: Ответ сотрудника");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("NONE");
		fb.setDescription("Статус: Черновик нового письма");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("subject");
		fb.setDescription("Тема письма");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,100}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("type");
		fb.setDescription("Тип письма");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new ChooseValueValidator(mailTypes));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("isAttach");
		fb.setDescription("Наличие приложенных файлов");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"","true","false"})));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("userSurName");
		fb.setDescription("Фамилия клиента");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("userFirstName");
		fb.setDescription("Имя клиента");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("userPatrName");
		fb.setDescription("Отчество клиента");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("employeeSurName");
		fb.setDescription("Фамилия сотрудника");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("employeeFirstName");
		fb.setDescription("Имя сотрудника");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("employeePatrName");
		fb.setDescription("Отчество сотрудника");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("employeeLogin");
		fb.setDescription("Логин сотрудника");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{1,30}"));
		formBuilder.addField(fb.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromPeriod");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromPeriod");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toPeriod");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toPeriod");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.addFormValidators(dateTimeCompareValidator);

		//хотя бы один статус должен быть выбран
		IsCheckedMultiFieldValidator oneStateIsCheckedValidator = new IsCheckedMultiFieldValidator();
		oneStateIsCheckedValidator.setBinding("state1", "ANSWER");
	    oneStateIsCheckedValidator.setBinding("state2", "READ");
		oneStateIsCheckedValidator.setBinding("state3", "DRAFT");
		oneStateIsCheckedValidator.setBinding("state4", "NONE");
		oneStateIsCheckedValidator.setBinding("state5", "NEW_EPLOYEE_MAIL");
		oneStateIsCheckedValidator.setBinding("state6", "ANSWER_EPLOYEE_MAIL");
	    oneStateIsCheckedValidator.setMessage("Выберите хотя бы один статус");
	    formBuilder.addFormValidators(oneStateIsCheckedValidator);

		return formBuilder.build();
	}
}
