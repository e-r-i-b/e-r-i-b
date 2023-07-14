package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author Nady
 * @ created 11.06.15
 * @ $Author$
 * @ $Revision$
 */
/**
 * Форма редактирования настроек для кредитных оферт. Задача КУКО-3
 */
public class LoanOffersSettingsEditForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//УКО - Общие условия
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.commonConditions.employees");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Сотрудники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.commonConditions.salaries");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Зарплатники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.commonConditions.pensioners");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Пенсионеры");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.commonConditions.accredits");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Аккредитовщики");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.commonConditions.street");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Улица");
		formBuilder.addField(fieldBuilder.build());

		//УКО - предодобренные кредиты
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.preapproved.employees");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Сотрудники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.preapproved.salaries");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Зарплатники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.preapproved.pensioners");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Пенсионеры");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.preapproved.accredits");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Аккредитовщики");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.uko.preapproved.street");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Улица");
		formBuilder.addField(fieldBuilder.build());

		//ВСП/DSA - Общие условия
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.commonConditions.employees");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Сотрудники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.commonConditions.salaries");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Зарплатники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.commonConditions.pensioners");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Пенсионеры");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.commonConditions.accredits");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Аккредитовщики");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.commonConditions.street");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Улица");
		formBuilder.addField(fieldBuilder.build());

		//ВСП/DSA - предодобренные
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.preapproved.employees");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Сотрудники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.preapproved.salaries");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Зарплатники");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.preapproved.pensioners");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Пенсионеры");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.preapproved.accredits");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Аккредитовщики");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.vsp.preapproved.street");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Улица");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.offerDisplayCountOnMainPage");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Количество раз отображения оферты на главной странице");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d{1,3}"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.offer.sendAcceptFactToFraudMonitoring");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Отправлять факт акцепта оферты в АС Фрод Мониторинг");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
