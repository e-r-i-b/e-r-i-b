package com.rssl.phizic.web.pfp.admin;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author komarov
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListPFPPassingJournalForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Менеджер");
		fieldBuilder.setName("employee_fio");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Клиент");
		fieldBuilder.setName("user_fio");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период с");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период по");
		fieldBuilder.setName("toDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());		
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Риск-профиль");
		fieldBuilder.setName("risk_profile");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName("birthday");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер");
		fieldBuilder.setName("documentNumber");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*","Номер документа должен содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия");
		fieldBuilder.setName("documentSeries");
		fieldBuilder.setType(StringType.INSTANCE.getName());			
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ID менеджера");
		fieldBuilder.setName("manager_id");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*","Поле ID менеджера должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Канал");
		fieldBuilder.setName("channelId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.setFormValidators(compareValidator);		
		
		return formBuilder.build();
	}
}
