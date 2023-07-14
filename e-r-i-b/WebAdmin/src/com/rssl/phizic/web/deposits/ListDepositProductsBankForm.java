package com.rssl.phizic.web.deposits;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Field;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.ArrayList;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: egorova $
 * @ $Revision: 16571 $
 */

@SuppressWarnings({"JavaDoc", "ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class ListDepositProductsBankForm extends ListFormBase
{
	private String listHtml;

	public String getListHtml()
	{
		return listHtml;
	}

	public void setListHtml(String listHtml)
	{
		this.listHtml = listHtml;
	}

	                                         
	public static final Form FORM = createForm();

	private static Form createForm()
	{
	    FormBuilder formBuilder = new FormBuilder();
	    FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("selectedDeps");
	    fieldBuilder.setDescription("deps");
	    formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("departmentId");
	    fieldBuilder.setDescription("departmentId");
	    formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("departmentName");
	    fieldBuilder.setDescription("departmentName");
	    formBuilder.addField( fieldBuilder.build() );

	    return formBuilder.build();
	}
}
