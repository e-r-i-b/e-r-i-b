package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.business.persons.ActivePerson;

/**
 * @author niculichev
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewPayWaitingConfirmForm extends ViewDocumentForm
{
	public static final String IMSI_CHECK_FIELD         = "isIMSIChecked";
	public static final String CONFIRM_TEMPLATE_FIELD   = "confirmTemplate";
	public static final String CREATE_TEMPLATE_FIELD    = "createTemplate";
	public static final String TEMPLATE_NAME_FIELD      = "templateName";
	public static final Form CREATE_TEMPLATE_FORM       = createAdditionFieldForm(CREATE_TEMPLATE_FIELD);
	public static final Form CONFIRM_TEMPLATE_FORM      = createAdditionFieldForm(CONFIRM_TEMPLATE_FIELD);

	private Long person;
	private ActivePerson activePerson;
	private Boolean modified = false;
	private String stateDescription;

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public String getStateDescription()
	{
		return stateDescription;
	}

	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return Форма создания нового сверхлимитного шаблона/подтверждения шаблона по подтверждаемой операции.
	 */
	private static Form createAdditionFieldForm(String operationType)
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder builder;

		builder = new FieldBuilder();
		builder.setName(operationType);
		builder.setDescription("Призак создания сверхлимитного шаблона");
		builder.setType("boolean");
		fb.addField(builder.build());

		if(CREATE_TEMPLATE_FIELD.equals(operationType))
		{
			builder = new FieldBuilder();
			builder.setName(TEMPLATE_NAME_FIELD);
			builder.setDescription("Название шаблона");
			RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
			requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.createTemplate"));
			RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("^.{0,50}$", "Название шаблона не должно превышать 50 символов. Введите более короткое название.");
			regexpFieldValidator.setEnabledExpression(new RhinoExpression("form.createTemplate"));
			builder.addValidators(requiredFieldValidator, regexpFieldValidator);
			fb.addField(builder.build());
		}

		builder = new FieldBuilder();
		builder.setName(IMSI_CHECK_FIELD);
		builder.setDescription(StrutsUtils.getMessage("label.changePersonIMSIConfirm", "personsBundle"));
		builder.setType("boolean");
		fb.addField(builder.build());

		return fb.build();
	}
}
