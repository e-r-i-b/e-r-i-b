package com.rssl.phizic.web.departments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 16.08.2006
 * @ $Author: krenev_a $
 * @ $Revision: 54975 $
 */
@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter", "JavaDoc"})
public class ListDepartmentsForm extends ListLimitActionForm<Department>
{
	private Long id;
	private Long level;

	/* �������� ������ � �������� �� ������� � �� � ����:
	   0-� �������: id ������������ (Long)
	   1-� �������: name �������� ������������
	   2-� �������: allowed - ����� �� ��������� ������ � ������������� (1:��, 0:���)
	   3-� �������: child - ���������� ��������
	   4-� �������: show - ����� �� ��������� ������ ������ ������������� (1:��, 0:���)
       5-� �������: TB - ��
       6-� �������: OSB - ���
       7-� �������: VSP - ���
	 */
	private List<Object> children;

	 /* �������� ������ � �������� �� ������� � �� � ����:
	   0-� �������: id ������������ (Long)
	   1-� �������: name �������� ������������
	   3-� �������: child - ���������� ��������
	 */
	private List<Object> allChildrenDepartmetns;

	public List<Object> getChildren()
	{
		return children;
	}

	public void setChildren(List<Object> childern)
	{
		this.children = childern;
	}

	public List<Object> getAllChildrenDepartmetns()
	{
		return allChildrenDepartmetns;
	}

	public void setAllChildrenDepartmetns(List<Object> allChildrenDepartmetns)
	{
		this.allChildrenDepartmetns = allChildrenDepartmetns;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;

		// ������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "������������ ������ ���� �� ����� 256 ��������")

		);

		fb.addField(fieldBuilder.build());

		// ����� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("region");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("\\d{1,3}", "����� ���.����� ������ ���� 3 �����")

		);

		fb.addField(fieldBuilder.build());
		// ����� ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("branchCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("\\d{1,4}", "����� ��������� ������ ���� 4 �����")

		);

		fb.addField(fieldBuilder.build());

		// ����� �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("\\d{1,7}", "����� ������� ������ ���� �� ����� 7 ����")

		);

		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLevel()
	{
		return level;
	}

	public void setLevel(Long level)
	{
		this.level = level;
	}
}