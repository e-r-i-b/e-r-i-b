package com.rssl.phizic.web.certification;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 20.11.2006 Time: 17:21:02 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandForm extends EditFormBase
{
	private boolean isRefresh = false;

	public boolean isRefresh()
	{
		return isRefresh;
	}

	public void setRefresh(boolean refresh)
	{
		isRefresh = refresh;
	}

	public static final Form EDIT_FORM = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		//Фамилия
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Фамилия должна быть не более 100 символов")
		);
		fb.addField(fieldBuilder.build());

		//Имя
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Имя должно быть не более 100 символов")
		);
		fb.addField(fieldBuilder.build());

		//Отчество
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отчество");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Отчество должно быть не более 100 символов")
		);
		fb.addField(fieldBuilder.build());

		//Идентификатор сертификата
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор");
		fieldBuilder.setName("id");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,10}", "Идентификатор должен быть не более 10 символов")
		);
		fb.addField(fieldBuilder.build());

		//Дата выдачи сертификата
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата");
		fieldBuilder.setName(DateType.INSTANCE.getName());
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Дата должна быть не более 100 символов")
		);
		fb.addField(fieldBuilder.build());

		//Статус сертификата
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("status");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "Статус должен быть не более 50 символов")
		);
		fb.addField(fieldBuilder.build());

		//Причина отказа сертификата
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Причина отказа");
		fieldBuilder.setName("refuseReason");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,256}", "Причина отказа должна быть не более 256 символов")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
