package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;
import com.rssl.phizic.business.mail.MailType;
import com.rssl.phizic.business.mail.RecipientMailState;
import com.rssl.phizic.gate.mail.statistics.MailDateSpan;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.mail.DateInPeriodValidator;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class MailStatisticsForm extends ListFormBase
{
	private MailDateSpan averageTime;
	private Calendar firstMailDate;

	private String[] userTBs = new String[]{};
	private String[] areaUUIDs = new String[]{};
	public static final Form STATISTICS_FORM = createForm();
	public static final String DELETED = "DELETED";

	/**
	 * @return идентификаторы департаментов
	 */
	public String[] getUserTBs()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return userTBs;
	}

	/**
	 * @param userTBs идентификаторы департаментов
	 */
	public void setUserTBs(String[] userTBs)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.userTBs = userTBs;
	}

	/**
	 * @return сквозные идентификаторы площадок КЦ
	 */
	public String[] getAreaUUIDs()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return areaUUIDs;
	}

	/**
	 * @param areaUUIDs сквозные идентификаторы площадок КЦ
	 */
	public void setAreaUUIDs(String[] areaUUIDs)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.areaUUIDs = areaUUIDs;
	}

	/**
	 * @return среднее время ответа на письмо
	 */
	public MailDateSpan getAverageTime()
	{
		return averageTime;
	}

	/**
	 * Установить среднее время ответа на письмо
	 * @param averageTime - среднее время ответа на письмо
	 */
	public void setAverageTime(MailDateSpan averageTime)
	{
		this.averageTime = averageTime;
	}

	/**
	 * @return дата первого письма
	 */
	public Calendar getFirstMailDate()
	{
		return firstMailDate;
	}

	/**
	 * Установить дату первого письма
	 * @param firstMailDate - дата первого письма
	 */
	public void setFirstMailDate(Calendar firstMailDate)
	{
		this.firstMailDate = firstMailDate;
	}

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();
	      
	    formBuilder.addFormValidators();

        FieldBuilder fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("fromDate");
        fieldBuilder.setDescription("Период c");
	    fieldBuilder.setType("date");
	    fieldBuilder.setParser(new DateParser());
	    fieldBuilder.addValidators(new RequiredFieldValidator());
        formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("toDate");
	    fieldBuilder.setDescription("Период по");
	    fieldBuilder.setType("date");
	    fieldBuilder.setParser(new DateParser());
        fieldBuilder.addValidators(new RequiredFieldValidator());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("theme");
	    fieldBuilder.setDescription("Тематика обращения");
	    fieldBuilder.setType(StringType.INSTANCE.getName());        
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("response_method");
	    fieldBuilder.setDescription("Способ получения ответа");
	    fieldBuilder.setType(StringType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    for(RecipientMailState state : RecipientMailState.values())
	    {
		    fieldBuilder = new FieldBuilder();
            fieldBuilder.setName(state.name());
            fieldBuilder.setType(BooleanType.INSTANCE.getName());
            fieldBuilder.setDescription(state.getDescription());
            formBuilder.addField(fieldBuilder.build());
	    }

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName(DELETED);
	    fieldBuilder.setType(BooleanType.INSTANCE.getName());
        fieldBuilder.setDescription("Удалено.");
	    formBuilder.addField(fieldBuilder.build());

	    for(MailType type : MailType.values())
	    {
		    fieldBuilder = new FieldBuilder();
            fieldBuilder.setName(type.name());
            fieldBuilder.setType(BooleanType.INSTANCE.getName());
            fieldBuilder.setDescription(type.getDescription());
            formBuilder.addField(fieldBuilder.build());
	    }

	    IsCheckedMultiFieldValidator validator = new IsCheckedMultiFieldValidator();
	    for(RecipientMailState state : RecipientMailState.values())
	   	{
	        validator.setBinding(state.name(), state.name());
	    }
	    validator.setBinding("DELETED", "DELETED");
	    validator.setMessage("Выберите хотя бы один статус.");

	    IsCheckedMultiFieldValidator typeValidator = new IsCheckedMultiFieldValidator();
	    for(MailType type : MailType.values())
	    {
	        typeValidator.setBinding(type.name(), type.name());
	    }
	    typeValidator.setMessage("Выберите хотя бы один Тип сообщения.");

	    CompareValidator dateCompareValidator = new CompareValidator();
		dateCompareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		dateCompareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		dateCompareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		dateCompareValidator.setMessage("Конечная дата должна быть больше начальной!");

	    DateInPeriodValidator dateInPeriodValidator= new DateInPeriodValidator();
	    dateInPeriodValidator.setBinding(DateInPeriodValidator.FIELD_FROM_DATE, "fromDate");
	    dateInPeriodValidator.setBinding(DateInPeriodValidator.FIELD_TO_DATE, "toDate");
	    formBuilder.addFormValidators(dateInPeriodValidator);

		formBuilder.addFormValidators(validator,  typeValidator, dateCompareValidator);

	    return formBuilder.build();
    }
	
}
