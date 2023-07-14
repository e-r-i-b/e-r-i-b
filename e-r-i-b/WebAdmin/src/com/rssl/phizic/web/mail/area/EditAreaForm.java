package com.rssl.phizic.web.mail.area;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.froms.validators.TBValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Set;

/**
 * Форма редактирования площадок КЦ
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditAreaForm extends EditFormBase
{
	private String[] selectedTBs = new String[]{};
	private Set<String> departments; //список ТБ которые будут выведены на экран
	public static final Form CONTACT_CENTER_AREA_FORM   = createForm();

	/**
	 * @return идентификаторы ТБ
	 */
	public String[] getSelectedTBs()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return selectedTBs;
	}

	/**
	 * @param selectedTBs идентификаторы ТБ
	 */
	public void setSelectedTBs(String[] selectedTBs)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.selectedTBs = selectedTBs;
	}

	/**
	 * @return ТБ
	 */
	public Set<String> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * @param departments ТБ
	 */
	public void setDepartments(Set<String> departments)
	{

		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "Название должно быть не более 50 символов")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
