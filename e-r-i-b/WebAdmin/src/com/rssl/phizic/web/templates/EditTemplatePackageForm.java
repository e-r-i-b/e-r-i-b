package com.rssl.phizic.web.templates;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 16:44:28
 * To change this template use File | Settings | File Templates.
 */
public class EditTemplatePackageForm extends EditFormBase
{
	private Long     departmentId;

    private List templates        = new ArrayList();
    private String[] selectedIds = new String[0];
	private boolean  selectAll   = false;

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

    public List getTemplates()
	{
		return templates;
	}

	public void setTemplates(List templates)
	{
		this.templates = templates;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public boolean isSelectAll()
	{
		return selectAll;
	}

	public void setSelectAll(boolean selectAll)
	{
		this.selectAll = selectAll;
	}

	public static final Form PACKAGE_FORM = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
        FormBuilder formBuilder   = new FormBuilder();
        FieldBuilder fieldBuilder = new FieldBuilder();

        fieldBuilder.setName("name");
        fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType("string");
	    fieldBuilder.setValidators
	    (
	        new RequiredFieldValidator(),
	        new RegexpFieldValidator(".{0,256}", "Наименование должено быть не более 256 символов")
	    );

	    formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators
		(
			new RegexpFieldValidator(".{0,256}", "Описание должено быть не более 256 символов")
		);

	    formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
