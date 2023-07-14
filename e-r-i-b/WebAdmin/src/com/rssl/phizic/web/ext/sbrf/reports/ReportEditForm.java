package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;

import java.util.Date;

/**
 * @author mescheryakova
 * @ created 09.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportEditForm extends EditFormBase
{
	protected String[] selectedIds = new String[]{};
	protected short    level;                              // уровень вложенности подразделений (для ТБ - 1, ОСБ - 2, ВСП - 3)
	protected String   periodTypes;
	public static String DATESTAMP = "dd.MM.yyyy";
	public static DateParser dateParser = new DateParser(DATESTAMP);

	/**
	 * @return выделенные идентификаторы
	 */
	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	/**
	 *  Установить идентивикаторы для выделения
	 * @param selectedIds идентификаторы
	 */
	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	/**
	 * Задаем уровень вложенности нужных подразделений
	 * @param level -  номер уровня вложенности
	 */
	public void setLevel(short level)
	{
		this.level = level;
	}

	/**
	 * Получаем уровень вложенности нужных подразделений
	 * @return уровень вложенности
	 */
	public short getLevel()
	{
		return this.level;
	}

	/**
	 * @return тип периода отчета
	 */
	public String getPeriodTypes()
	{
		return periodTypes;
	}

	/**
	 * Установить тип периода отчета
	 * @param periodTypes
	 */
	public void setPeriodTypes(String periodTypes)
	{
		this.periodTypes = periodTypes;
	}

	public static final Form EDIT_FORM = createForm("Указаннная дата для построения отчета выходит за границы имеющихся в базе дат.");

	protected static Form createForm(String validatorMessage)
    {
	    FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

	    DateNotInFutureValidator notInFutureValidator = new DateNotInFutureValidator(DATESTAMP);
	    notInFutureValidator.setMessage("Введенная дата не может быть больше " + DateHelper.toString(new Date()));

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("startDate");
        fieldBuilder.setDescription("Дата");
	    fieldBuilder.setType("date");
	    fieldBuilder.addValidators (
			        new RequiredFieldValidator(),
			        new DateFieldValidator(DATESTAMP, "Введите корректную дату в формате ДД.ММ.ГГГГ."),
			        notInFutureValidator
		);
	    fieldBuilder.setParser(dateParser);

	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("minDate");
	    fieldBuilder.setDescription("Минимальная дата");
	    fieldBuilder.setType("date");

	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("maxDate");
	    fieldBuilder.setDescription("Максимальная дата");
	    fieldBuilder.setType("date");

	    formBuilder.addField(fieldBuilder.build());

	    CompareValidator minDateValidator = new CompareValidator();
		minDateValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		minDateValidator.setBinding(CompareValidator.FIELD_O1, "minDate");
		minDateValidator.setBinding(CompareValidator.FIELD_O2, "startDate");
		minDateValidator.setMessage(validatorMessage);

	    CompareValidator maxDateValidator = new CompareValidator();
		maxDateValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		maxDateValidator.setBinding(CompareValidator.FIELD_O1, "startDate");
		maxDateValidator.setBinding(CompareValidator.FIELD_O2, "maxDate");
		maxDateValidator.setMessage(validatorMessage);

	    formBuilder.addFormValidators(maxDateValidator);
	    formBuilder.addFormValidators(minDateValidator);

	    return formBuilder.build();
    }

}
