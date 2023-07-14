package com.rssl.phizic.web.pfp.ajax;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CurrentQuarterCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.pfp.PersonalFinanceProfileUtils;

import java.util.List;

/**
 * @author mihaylov
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма редактирования цели клиента
 */
public class EditPfpTargetForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final Form EDIT_FORM = getEditForm();

	private Long profileId; //идентификатор ПФП
	private List<PersonTarget> personTargetList; //цели клиента
	private Boolean showThermometer;

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public List<PersonTarget> getPersonTargetList()
	{
		return personTargetList;
	}

	public void setPersonTargetList(List<PersonTarget> personTargetList)
	{
		this.personTargetList = personTargetList;
	}

	public Boolean getShowThermometer()
	{
		return showThermometer;
	}

	public void setShowThermometer(Boolean showThermometer)
	{
		this.showThermometer = showThermometer;
	}

	private static Form getEditForm()
	{
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		DateParser dateParser = new DateParser(DATE_FORMAT);

		CurrentQuarterCompareValidator currentQuarterCompareValidator = new CurrentQuarterCompareValidator(DATE_FORMAT);
		currentQuarterCompareValidator.setMaxPlanningQuarters(PersonalFinanceProfileUtils.getMaxPlanningYear()*4);
		currentQuarterCompareValidator.setMessages("Пожалуйста, укажите значение, превышающее текущую дату.", "Пожалуйста, укажите значение, не превышающее текущую дату более чем на" + PersonalFinanceProfileUtils.getMaxPlanningYear() + "лет.");

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Цель");
		fieldBuilder.setName("dictionaryTarget");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Комментарий к названию цели");
		fieldBuilder.setName("nameComment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Планируемая дата приобретения цели");
		fieldBuilder.setName("planedDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator,currentQuarterCompareValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Стоимость цели");
		fieldBuilder.setName("amount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
