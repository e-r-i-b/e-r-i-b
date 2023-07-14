package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.validators.TemplateNameValidator;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.web.documents.templates.TemplateFormBase;
import com.rssl.phizic.web.validators.SmsChannelTemplateNameValidator;

import java.util.List;

/**
 * @author krenev
 * @ created 01.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditTemplateForm extends TemplateFormBase
{
	public static final String TEMPLATE_NAME_FIELD_NAME = "templateName";
	public static final String NEW_TEMPLATE_NAME_FIELD_NAME = "newTemplateName";
	public static final String CHECK_TEMPLATE_NAME_FIELD_NAME = "templateName";

	private Long payment;//идентифкатор платежа, на основе которого создается шаблон.

	//TODO убрать
	private Boolean back;
	private Boolean copying = false;

	private boolean markReminder;

	/**
	 * форма валидации имени шаблона
	 */
	public static final Form CHANGE_NAME_FORM = createAdditionFieldForm(NEW_TEMPLATE_NAME_FIELD_NAME);
	public static final Form QUICKLY_CREATE_FORM = createEditForm(null);
	public static final Form REMINDER_FORM = createReminderForm();
	public static final Form REMINDER_FORM_WITH_NAME = createReminderFormWithName();
	public static final Form CHECK_NAME_FORM = createCheckNameForm();

	private List<PaymentAbilityERL> chargeOffResources;
	private String fromResource;
	private boolean templateDataStored = false;

	/**
	 * Создание формы с доп данными при сохранении шаблона
	 * @param template шаблон(null - если шаблон еще не сохранее)
	 * @return логическая форма
	 */
	public static Form createEditForm(TemplateDocument template)
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder builder = new FieldBuilder();
		builder.setName(TEMPLATE_NAME_FIELD_NAME);
		builder.setDescription("Название шаблона");
		builder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{0,50}$", "Название шаблона не должно превышать 50 символов. Введите более короткое название."),
				new TemplateNameValidator(template)
			);

		fb.addField(builder.build());
		return fb.build();
	}

	private static Form createAdditionFieldForm(String templateFieldName)
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder builder = new FieldBuilder();
		builder.setName(templateFieldName);
		builder.setDescription("Новое название");
		builder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{0,50}$", "Название шаблона не должно превышать 50 символов. Введите более короткое название."),
				new TemplateNameValidator()
			);
		fb.addField(builder.build());

		return fb.build();
	}

	private static Form createCheckNameForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder builder = new FieldBuilder();
		builder.setName(CHECK_TEMPLATE_NAME_FIELD_NAME);
		builder.setDescription("Новое название шаблона");
		builder.setType(StringType.INSTANCE.getName());
		builder.addValidators(
				new SmsChannelTemplateNameValidator()
		);
		fb.addField(builder.build());

		return fb.build();
	}

	private static Form createReminderForm()
	{
		FormBuilder fb = new FormBuilder();
		fillReminderFields(fb);
		return fb.build();
	}

	private static Form createReminderFormWithName()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder builder = new FieldBuilder();
		builder.setName(TEMPLATE_NAME_FIELD_NAME);
		builder.setDescription("Название напоминания");
		builder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{0,50}$", "Название напоминания не должно превышать 50 символов. Введите более короткое название."),
				new TemplateNameValidator()
		);
		fb.addField(builder.build());
		fillReminderFields(fb);

		return fb.build();
	}

	private static void fillReminderFields(FormBuilder fb)
	{
		FieldBuilder builder = new FieldBuilder();
		builder = new FieldBuilder();
		builder.setName("enableReminder");
		builder.setType(BooleanType.INSTANCE.getName());
		builder.setDescription("Признак включения напоминания");
		fb.addField(builder.build());

		builder = new FieldBuilder();
		builder.setName("reminderType");
		builder.setDescription("Тип напоминания");
		builder.setEnabledExpression(new RhinoExpression("form.enableReminder"));
		builder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^(ONCE|ONCE_IN_MONTH|ONCE_IN_QUARTER)$", "Неверный тип напоминания.")
		);
		fb.addField(builder.build());

		builder = new FieldBuilder();
		builder.setName("dayOfMonth");
		builder.setType(IntType.INSTANCE.getName());
		builder.setEnabledExpression(new RhinoExpression("form.reminderType == 'ONCE_IN_QUARTER' || form.reminderType == 'ONCE_IN_MONTH'"));
		builder.setDescription("Число месяца");
		builder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{0,31}$", "Неверное число месяца напоминания.")
		);
		fb.addField(builder.build());

		builder = new FieldBuilder();
		builder.setName("monthOfQuarter");
		builder.setType(IntType.INSTANCE.getName());
		builder.setEnabledExpression(new RhinoExpression("form.reminderType == 'ONCE_IN_QUARTER'"));
		builder.setDescription("Номер месяца в квартале");
		builder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,3}$", "Неверный номер месяца в квартале.")
		);
		fb.addField(builder.build());

		builder = new FieldBuilder();
		builder.setName("onceDate");
		builder.setDescription("Дата одноразового напоминания");
		builder.setType(DateType.INSTANCE.getName());
		builder.setEnabledExpression(new RhinoExpression("form.reminderType == 'ONCE'"));
		builder.addValidators(
				new RequiredFieldValidator()
		);
		fb.addField(builder.build());
	}

	public String getTemplateName()
	{
		return (String) getField(TEMPLATE_NAME_FIELD_NAME);
	}

	public void setTemplateName(String templateName)
	{
		setField(TEMPLATE_NAME_FIELD_NAME, templateName);
	}

	public Long getPayment()
	{
		return payment;
	}

	public void setPayment(Long payment)
	{
		this.payment = payment;
	}

	public Boolean getBack()
	{
		return back;
	}

	public void setBack(Boolean back)
	{
		this.back = back;
	}

	public Boolean getCopying()
	{
		return copying;
	}

	public void setCopying(Boolean copying)
	{
		this.copying = copying;
	}

	public void setChargeOffResources(List<PaymentAbilityERL> chargeOffResources)
	{
		this.chargeOffResources = chargeOffResources;
	}

	public List<PaymentAbilityERL> getChargeOffResources()
	{
		return chargeOffResources;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	/**
	 * @return были ли данные шаблона сохранены в store
	 */
	public boolean isTemplateDataStored()
	{
		return templateDataStored;
	}

	/**
	 * @param templateDataStored были ли данные шаблона сохранены в store
	 */
	public void setTemplateDataStored(boolean templateDataStored)
	{
		this.templateDataStored = templateDataStored;
	}

	public boolean isMarkReminder()
	{
		return markReminder;
	}

	public void setMarkReminder(boolean markReminder)
	{
		this.markReminder = markReminder;
	}
}
