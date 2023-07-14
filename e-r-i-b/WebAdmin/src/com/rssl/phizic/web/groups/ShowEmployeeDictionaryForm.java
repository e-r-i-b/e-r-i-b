package com.rssl.phizic.web.groups;

import org.apache.struts.action.ActionForm;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 16:56:16 To change this template use
 * File | Settings | File Templates.
 */
public class ShowEmployeeDictionaryForm extends ListLimitActionForm
{
	private Long groupId;

	public Long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
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

