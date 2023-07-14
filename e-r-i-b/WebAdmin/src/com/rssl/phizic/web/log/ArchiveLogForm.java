package com.rssl.phizic.web.log;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author gladishev
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveLogForm extends EditFormBase
{
	public static final Form FILTER_FORM = createFilterForm();

	private FormFile archive;

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setType("string");
		fieldBuilder.setName("protocolType");
		fieldBuilder.setDescription("тип протокола");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public FormFile getArchive()
	{
		return archive;
	}

	public void setArchive(FormFile archive)
	{
		this.archive = archive;
	}
}