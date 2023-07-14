package com.rssl.phizic.web.groups;

import java.util.ArrayList;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 15:35:37 To change this template use
 * File | Settings | File Templates.
 */
public class GroupsContainEmployersForm extends ListLimitActionForm
{
	private Long id;
	private String employeeLogins;
	private String category;

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getEmployeeLogins()
	{
		return employeeLogins;
	}

	public void setEmployeeLogins(String employeeLogins)
	{
		this.employeeLogins = employeeLogins;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		ArrayList<Field> fields = new ArrayList<Field>();

		FieldBuilder fieldBuilder;
		  fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("firstName");
        fieldBuilder.setDescription("���");
        fields.add( fieldBuilder.build() );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("patrName");
        fieldBuilder.setDescription("��������");
        fields.add( fieldBuilder.build() );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("surName");
        fieldBuilder.setDescription("�������");
        fields.add( fieldBuilder.build() );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("info");
        fieldBuilder.setDescription("���. ����������");
        fields.add( fieldBuilder.build() );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("branchCode");
        fieldBuilder.setDescription("����� ���������");
        fieldBuilder.setValidators( new RegexpFieldValidator("\\d{1,4}", "����� ��������� ������ �������� �� 4 ����") );
        fields.add( fieldBuilder.build() );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("departmentCode");
        fieldBuilder.setDescription("����� �������");
        fieldBuilder.setValidators( new RegexpFieldValidator("\\d{1,7}", "����� ������� �� ������ ��������� 7 ����") );
        fields.add( fieldBuilder.build() );

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
