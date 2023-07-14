package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * Форма для редактирования условий заявления для подключение УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class EditUDBOClaimRulesForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();
	public static final String DATESTAMP = "dd.MM.yyyy";
	public static final String START_DATE_FIELD_NAME = "startDate";

	private FormFile rulesFile;
	private String rulesText;

	/**
	 * @return файл условий заявления
	 */
	public FormFile getRulesFile()
	{
		return rulesFile;
	}

	/**
	 * Задать файл условий заявления
	 * @param rulesFile - файл
	 */
	public void setRulesFile(FormFile rulesFile)
	{
		this.rulesFile = rulesFile;
	}

	/**
	 * @return текст условий
	 */
	public String getRulesText()
	{
		return rulesText;
	}

	/**
	 * Задать текст условий
	 * @param rulesText текст
	 */
	public void setRulesText(String rulesText)
	{
		this.rulesText = rulesText;
	}

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(START_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Дата вступления в силу");
		fieldBuilder.addValidators(new RequiredFieldValidator("Введите дату вступления в силу условий"),
				new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addFields(fieldBuilder.build());

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE);
		dateTimeValidator.setParameter(DateTimeCompareValidator.CUR_DATE, "to_cleartime");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, START_DATE_FIELD_NAME);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage(StrutsUtils.getMessage("settings.connect.udbo.edit.rules.startDate.message.beforeCurrent", "configureBundle"));

		formBuilder.addFormValidators(dateTimeValidator);

		return formBuilder.build();
	}
}
