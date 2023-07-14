package com.rssl.phizic.web.actions.templates.offer;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Balovtsev
 * @since 05.06.2015.
 */
public class CreditOfferTemplateEditForm extends EditFormBase
{
	public  static final Form EDIT     = createEditForm();
	public  static final Form EDIT_PDP = createEditPdpForm();

	private static final String FROM_DATE_FORMAT = "dd.MM.yyyy";
	private static final String FROM_TIME_FORMAT = "HH:mm";

	private CreditOfferTemplate template = new CreditOfferTemplate();

	private static Form createEditPdpForm()
	{
		FormBuilder  formBuilder  = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("text");
		fieldBuilder.setDescription("Текст оферты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new LengthFieldValidator(0, 4000));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createEditForm()
	{
		FormBuilder  formBuilder  = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начинает действовать с");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(FROM_DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new DateFieldValidator(FROM_DATE_FORMAT, "Пожалуйста, укажите время начала действия шаблона в формате dd.MM.yyyy hh:mm"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начинает действовать с");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(FROM_TIME_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new DateFieldValidator(FROM_TIME_FORMAT, "Пожалуйста, укажите время начала действия шаблона в формате dd.MM.yyyy hh:mm"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("text");
		fieldBuilder.setDescription("Текст оферты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new LengthFieldValidator(0, 10000));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public void setTemplate(CreditOfferTemplate template)
	{
		this.template = template;
	}

	public CreditOfferTemplate getTemplate()
	{
		return template;
	}
}
