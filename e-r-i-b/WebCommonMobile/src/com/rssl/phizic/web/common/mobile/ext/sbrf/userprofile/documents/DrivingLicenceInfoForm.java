package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile.documents;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * Форма редактирования водительского удостоверения
 *
 * @author EgorovaA
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class DrivingLicenceInfoForm extends AdditionalDocumentInfoForm
{
	protected static final String SERIES = "series";
	protected static final String ISSUE_BY = "issueBy";
	protected static final String EXPIRE_DATE = "expireDate";
	protected static final String ISSUE_DATE = "issueDate";

	public static final Form FORM = createForm();

	private String series;
	private String issueBy;
	private String expireDate;
	private String issueDate;

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getIssueBy()
	{
		return issueBy;
	}

	public void setIssueBy(String issueBy)
	{
		this.issueBy = issueBy;
	}

	public String getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(String expireDate)
	{
		this.expireDate = expireDate;
	}

	public String getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(String issueDate)
	{
		this.issueDate = issueDate;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		String dateFormat = "dd.MM.yyyy";
		DateParser dataParser = new DateParser(dateFormat);
		DateFieldValidator dateValidator = new DateFieldValidator(dateFormat, "Введите дату в формате ДД.ММ.ГГГГ");
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

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Кем выдан");
		fieldBuilder.setName(ISSUE_BY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата выдачи");
		fieldBuilder.setName(ISSUE_DATE);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, dateValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Действует до");
		fieldBuilder.setName(EXPIRE_DATE);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, dateValidator);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
