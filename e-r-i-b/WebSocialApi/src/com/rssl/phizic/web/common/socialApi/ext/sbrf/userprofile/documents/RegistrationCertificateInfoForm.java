package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile.documents;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile.documents.AdditionalDocumentInfoForm;

/**
 * Форма редактирования Свидетельства о регистрации транспортного средства
 *
 * @author muhin
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationCertificateInfoForm extends AdditionalDocumentInfoForm
{
	protected static final String SERIES = "series";
	protected static final String NAME_DOC = "nameDoc";

	public static final Form FORM = createForm();

	private String series;
	private String nameDoc;

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getNameDoc()
	{
		return nameDoc;
	}

	public void setNameDoc(String nameDoc)
	{
		this.nameDoc = nameDoc;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName(NUMBER);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование документа");
		fieldBuilder.setName(NAME_DOC);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия");
		fieldBuilder.setName(SERIES);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());
		return fb.build();
	}

}
