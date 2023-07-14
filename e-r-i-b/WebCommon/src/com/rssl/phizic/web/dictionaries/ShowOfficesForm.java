package com.rssl.phizic.web.dictionaries;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * @author Omeliyanchuk
 * @ created 18.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class ShowOfficesForm extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();

	private static final int DEFAULT_PAGE_SIZE = 20;
	
	private int paginationSize = DEFAULT_PAGE_SIZE;
	private int paginationOffset = 0;

	public int getPaginationSize()
	{
		return paginationSize;
	}

	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}
	
	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
