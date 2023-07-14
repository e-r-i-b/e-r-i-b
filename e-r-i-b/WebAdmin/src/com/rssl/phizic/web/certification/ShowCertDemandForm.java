package com.rssl.phizic.web.certification;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 20.11.2006 Time: 15:25:42 To change this template use
 * File | Settings | File Templates.
 */
public class ShowCertDemandForm  extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Наименование
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фамилия");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "Наименование должно быть не более 256 символов")

		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "Наименование должно быть не более 256 символов")
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отчество");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "Наименование должно быть не более 256 символов")
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("status");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подписан");
		fieldBuilder.setName("signed");
		fieldBuilder.setType("string");
		
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата От");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setValidators(
				new DateFieldValidator()
		);

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата До");
		fieldBuilder.setName("toDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setValidators(
				new DateFieldValidator()
		);

		fb.addField(fieldBuilder.build());


		return fb.build();

	}
}
