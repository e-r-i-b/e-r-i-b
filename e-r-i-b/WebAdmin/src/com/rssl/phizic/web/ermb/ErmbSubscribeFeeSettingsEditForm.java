package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ermb.SubscribeFeeConstants;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма для редактирования настроек ФПП для списания абонентской платы ЕРМБ
 * @author Rtischeva
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbSubscribeFeeSettingsEditForm extends EditPropertiesFormBase
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

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.FPP_UNLOAD_TIME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Время выгрузки ФПП");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,250}", "Поле \"Время выгрузки ФПП\" не должно превышать 250 символов"));
		fieldBuilder.addValidators(new RegexpFieldValidator("^((\\s*([0-1][0-9]|[2][0-3]):([0-5][0-9])\\s*)(;\\s*([0-1][0-9]|[2][0-3]):([0-5][0-9])\\s*)*)?$",
				"Введите корректное значение времени выгрузки ФПП в формате HH:ММ через ;"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.FPP_PROC_TOTAL_COUNT);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("Количество процессов выгрузки ФПП");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите Количество процессов выгрузки ФПП"),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"1", "5", "10", "50", "100"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.FPP_PATH);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Путь файла выгрузки ФПП");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Поле \"Путь файла выгрузки ФПП\" не должно превышать 100 символов"));
		formBuilder.addField(fieldBuilder.build());

//		BUG089151 нужно уточнять требование по этой настройке и либо сносить ее совсем, либо переделывать блокировку

//		fieldBuilder = new FieldBuilder();
//		fieldBuilder.setName(SubscribeFeeConstants.FPP_WAITING_DAYS_NUMBER);
//		fieldBuilder.setType(LongType.INSTANCE.getName());
//		fieldBuilder.setDescription("Время ожидания ответа от СОС");
//		fieldBuilder.setType(LongType.INSTANCE.getName());
//		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите количество дней ожидания ответа от СОС"),
//				new RegexpFieldValidator("(\\d{0,3})", "Поле \"Время ожидания ответа от СОС\" должно содержать только цифры"));
//		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SubscribeFeeConstants.MAX_TRANSACTION_COUNT);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("Максимальное количество транзакций в одном ФПП-файле");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите Максимальное количество транзакций в одном ФПП-файле"),
				new RegexpFieldValidator("(\\d{0,10})", "Поле \"Максимальное количество транзакций в одном ФПП-файле\" должно содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
