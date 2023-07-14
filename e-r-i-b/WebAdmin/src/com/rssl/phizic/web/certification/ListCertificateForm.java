package com.rssl.phizic.web.certification;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;


/**
 * @author Omeliyanchuk
 * @ created 26.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListCertificateForm extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		DateFieldValidator dateFieldValidator = new DateFieldValidator();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата начала действия");
		fieldBuilder.setName("startDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setValidators(dateFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата окончания действия");
		fieldBuilder.setName("endDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setValidators(dateFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отчество");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер ПИН-конверта");
		fieldBuilder.setName("pinNumber");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("status");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

}
