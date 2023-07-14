package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile.documents;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;

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

	public static final Form FORM = createForm();

	private String series;

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Серия документа");
		fieldBuilder.setName(SERIES);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер документа");
		fieldBuilder.setName(NUMBER);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());
		return fb.build();
	}
}
