package com.rssl.phizic.web.dictionaries.provider.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author komarov
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderLanguageResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = getEditForm();


	@SuppressWarnings({"OverlyLongMethod", "ReuseOfLocalVariable"})
	private static Form getEditForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,160}", "Наименование должено быть не более 160 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Юридическое наименование поставщика услуг");
		fieldBuilder.setName("legalName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,250}$", "Поле Юридическое наименование поставщика услуг не должно превышать 250 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Псевдонимы поставщика услуг");
		fieldBuilder.setName("alias");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование банка");
		fieldBuilder.setName("bankName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());



		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Комментарий для клиентов");
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "Поле Комментарий для клиентов не должно превышать 255 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подсказка по поставщику услуг");
		fieldBuilder.setName("tip");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,255}", "Поле Подсказка по поставщику услуг не должно превышать 255 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сообщение о комиссии");
		fieldBuilder.setName("commissionMessage");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(?s).{0,250}", "Поле Сообщение о комиссии не должно превышать 250 символов."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование поставщика, выводимое в чеке");
		fieldBuilder.setName("nameOnBill");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название услуги в биллинговой системе");
		fieldBuilder.setName("nameService");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,150}", "Поле Название услуги в биллинговой системе не должно превышать 150 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
