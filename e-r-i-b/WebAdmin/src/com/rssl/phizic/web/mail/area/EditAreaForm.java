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
 * ����� �������������� �������� ��
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditAreaForm extends EditFormBase
{
	private String[] selectedTBs = new String[]{};
	private Set<String> departments; //������ �� ������� ����� �������� �� �����
	public static final Form CONTACT_CENTER_AREA_FORM   = createForm();

	/**
	 * @return �������������� ��
	 */
	public String[] getSelectedTBs()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return selectedTBs;
	}

	/**
	 * @param selectedTBs �������������� ��
	 */
	public void setSelectedTBs(String[] selectedTBs)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.selectedTBs = selectedTBs;
	}

	/**
	 * @return ��
	 */
	public Set<String> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * @param departments ��
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
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "�������� ������ ���� �� ����� 50 ��������")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
