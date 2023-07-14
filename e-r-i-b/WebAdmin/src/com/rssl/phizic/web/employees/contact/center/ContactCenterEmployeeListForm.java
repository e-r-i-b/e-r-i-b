package com.rssl.phizic.web.employees.contact.center;


import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.List;

/**
 * @author komarov
 * @ created 11.10.13 
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ������ ����������� ����������� ������
 */

public class ContactCenterEmployeeListForm extends ListFormBase
{
	public static final String FIO_PARAMETER_NAME = "fio";
	public static final String AREA_ID_PARAMETER_NAME = "area_id";
	public static final String EMPLOYEE_ID_PARAMETER_NAME = "id";

	public static final Form FILTER_FORM = createForm();

	private List<ContactCenterArea> contactCenterAreas;

	/**
	 * @return ������ �������� ��
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<ContactCenterArea> getContactCenterAreas()
	{
		return Collections.unmodifiableList(contactCenterAreas);
	}

	/**
	 * @param contactCenterAreas ������ �������� ��
	 */
	public void setContactCenterAreas(List<ContactCenterArea> contactCenterAreas)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.contactCenterAreas = contactCenterAreas;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(FieldBuilder.buildStringField(FIO_PARAMETER_NAME, "���������"));
		formBuilder.addField(FieldBuilder.buildStringField(AREA_ID_PARAMETER_NAME, "�������� ��"));
		formBuilder.addField(FieldBuilder.buildLongField(EMPLOYEE_ID_PARAMETER_NAME, "������������� (ID)",
				new RegexpFieldValidator("^\\d*$","������������� ������������ ����� ��������� ������ �����")));

		return formBuilder.build();
	}
}
