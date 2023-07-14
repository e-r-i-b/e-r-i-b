package com.rssl.phizic.web.common.ext.sbrf.dictionaries;

import java.util.List;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.dictionaries.ShowMultiDepartmentOfficesForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;

/**
 * @author Kosyakov
 * @ created 15.11.2005
 * @ $Author: hudyakov $
 * @ $Revision: 12452 $
 */
public class ShowSBRFOfficesForm extends ShowMultiDepartmentOfficesForm
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Наименование");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("region");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("ТБ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("branch");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("ОСБ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("office");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Филиал");
		formBuilder.addField(fieldBuilder.build());
		//CODE???
		return formBuilder.build();
	}
}
