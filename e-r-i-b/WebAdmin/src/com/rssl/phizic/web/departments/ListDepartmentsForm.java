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

	/* хранятся данные о потомках из запроса к БД в виде:
	   0-й элемент: id департамента (Long)
	   1-й элемент: name название департамента
	   2-й элемент: allowed - имеет ли сотрудник доступ к подразделению (1:да, 0:нет)
	   3-й элемент: child - количество потомков
	   4-й элемент: show - может ли сотрудник видеть данное подразделение (1:да, 0:нет)
       5-й элемент: TB - ТБ
       6-й элемент: OSB - ОСБ
       7-й элемент: VSP - ВСП
	 */
	private List<Object> children;

	 /* хранятся данные о потомках из запроса к БД в виде:
	   0-й элемент: id департамента (Long)
	   1-й элемент: name название департамента
	   3-й элемент: child - количество потомков
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

		// Наименование
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "Наименование должно быть не более 256 символов")

		);

		fb.addField(fieldBuilder.build());

		// Номер тербанка
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("region");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("\\d{1,3}", "Номер тер.банка должен быть 3 цифры")

		);

		fb.addField(fieldBuilder.build());
		// Номер отделения
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("branchCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("\\d{1,4}", "Номер отделения должен быть 4 цифры")

		);

		fb.addField(fieldBuilder.build());

		// Номер филиала
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator("\\d{1,7}", "Номер филиала должен быть не более 7 цифр")

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