package com.rssl.phizic.web.configure.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ListLocaleForm extends ListFormBase
{
	private boolean isCSA = false;
 	public static final Form FILTER_FORM = createForm();

	/**
	 * @return идентификатор выбранной локали
	 */
	public String getSelectedLocaleId()
	{
		if (selectedIds.length == 0)
		{
			return null;
		}
		return selectedIds[0];
	}

	/**
	 * @return признак того, что редактируем CSA сущность
	 */
	public boolean getIsCSA()
	{
		return isCSA;
	}

	/**
	 * @param CSA признак того, что редактируем CSA сущность
	 */
	public void setIsCSA(boolean CSA)
	{
		isCSA = CSA;
	}

 	@SuppressWarnings("TooBroadScope")
    private static Form createForm()
    {
	    FormBuilder formBuilder = new FormBuilder();
	    FieldBuilder fieldBuilder;

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("name");
	    fieldBuilder.setDescription("Название");
	    fieldBuilder.setType(StringType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    return formBuilder.build();
    }
}
