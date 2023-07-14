package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateNotInFutureValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.exception.ExceptionEntryType;
import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.Arrays;
import java.util.Set;

/**
 * @author akrenev
 * @ created 24.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * форма настройки автоматической архивации отчетов об ошибках и ручной выгрузки отчета за период
 */

public class ArchivingExceptionForm extends EditPropertiesFormBase
{
	public static final String UNLOADING_DATE_PARAMETER_NAME = "unloadingDate"; // дата выгрузки
	public static final String EXCEPTION_TYPE_PARAMETER_NAME = "exceptionType"; // тип отчета
	private static final Form FORM = createForm();                              // логическая форма для настройки архивации отчёта
	public static final Form ADDITIONAL_FORM = createAdditionalForm();          // логическая форма для выгрузки отчета

	private static final String DATE_FORMAT = "MM.yyyy";

	private String archivePath;

	@Override
	public Form getForm()
	{
		return FORM;
	}

	/**
	 * @return путь к архивам
	 */
	public String getArchivePath()
	{
		return archivePath;
	}

	/**
	 * задать путь к архивам
	 * @param archivePath путь к архивам
	 */
	public void setArchivePath(String archivePath)
	{
		this.archivePath = archivePath;
	}

	@SuppressWarnings({"ReuseOfLocalVariable"})
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ExceptionSettingsService.USE_ARCHIVING_PARAMETER);
		fieldBuilder.setDescription("Архивировать отчет");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@SuppressWarnings({"ReuseOfLocalVariable"})
	private static Form createAdditionalForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(UNLOADING_DATE_PARAMETER_NAME);
		fieldBuilder.setDescription("Период выгрузки");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator(DATE_FORMAT, "Пожалуйста, укажите период выгрузки в формате ММ.ГГГГ."),
				new DateNotInFutureValidator(DATE_FORMAT));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EXCEPTION_TYPE_PARAMETER_NAME);
		fieldBuilder.setDescription("Тип ошибок для выгрузки");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new FieldValueParser<ExceptionEntryType>(){public ExceptionEntryType parse(String value){return ExceptionEntryType.valueOf(value);}});
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new ChooseValueValidator(Arrays.asList("internal", "external"), "Выберите тип ошибок для выгрузки."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Set<String> getFieldKeys()
	{
		Set<String> result = super.getFieldKeys();
		result.add(ExceptionSettingsService.USE_ARCHIVING_PARAMETER);
		return result;
	}
}
