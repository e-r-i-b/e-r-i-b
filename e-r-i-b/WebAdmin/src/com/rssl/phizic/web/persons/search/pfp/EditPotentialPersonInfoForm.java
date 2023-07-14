package com.rssl.phizic.web.persons.search.pfp;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.persons.formBuilders.PersonFormBuilderBase;
import org.apache.commons.lang.ArrayUtils;

import java.math.BigInteger;

/**
 * @author akrenev
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 *
 * форма редактирования данных потенциального клиента
 */

public class EditPotentialPersonInfoForm extends EditFormBase
{
	public static final String FIRST_NAME_FIELD_NAME = PersonFormBuilderBase.FIRST_NAME_FIELD;
	public static final String PATR_NAME_FIELD_NAME = PersonFormBuilderBase.PATR_NAME_FIELD;
	public static final String SUR_NAME_FIELD_NAME = PersonFormBuilderBase.SUR_NAME_FIELD;
	public static final String DOC_TYPE_FIELD_NAME = PersonFormBuilderBase.DOCUMENT_TYPE_FIELD;
	public static final String DOC_SERIES_FIELD_NAME = PersonFormBuilderBase.DOCUMENT_SERIES_FIELD;
	public static final String DOC_NUMBER_FIELD_NAME = PersonFormBuilderBase.DOCUMENT_NUMBER_FIELD;
	public static final String BIRTHDAY_FIELD_NAME = PersonFormBuilderBase.BIRTH_DAY_FIELD;
	public static final String MOBILE_PHONE_FIELD_NAME = PersonFormBuilderBase.MOBILE_PHONE_FIELD;
	public static final String EMAIL_FIELD_NAME = PersonFormBuilderBase.EMAIL_FIELD;
	public static final String DEPARTMENT_NAME_FIELD_NAME = "departmentName";

	private static final int SEVENTY = 70;

	public static final Form EDIT_FORM = getEditForm();

	private ActivePerson activePerson;
	private boolean continueSave;//продолжить сохранение клиента(многоблочный режим)

	/**
	 * @return редактируемый клиент
	 */
	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	/**
	 * задать редактируемого клиента
	 * @param activePerson редактируемый клиент
	 */
	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	/**
	 * @return признак необходимости продолжить сохранение клиента
	 */
	public boolean isContinueSave()
	{
		return continueSave;
	}

	/**
	 * Установить признак
	 * @param continueSave признак необходимости продолжить сохранение клиента
	 */
	public void setContinueSave(boolean continueSave)
	{
		this.continueSave = continueSave;
	}

	/**
	 * @return нужды /WEB-INF/jsp-sbrf/persons/personData.jsp
	 */
	public boolean isModified()
	{
		return false;
	}

	private static FieldBuilder getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription(description);
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder;
	}

	private static Form getEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//мобильный телефон
		formBuilder.addField(getStringField(MOBILE_PHONE_FIELD_NAME, PersonFormBuilderBase.MOBILE_PHONE_FIELD_DESCRIPTION,
				new RequiredFieldValidator("Поле \"" + PersonFormBuilderBase.MOBILE_PHONE_FIELD_DESCRIPTION + "\" обязательно для заполнения, укажите значение данного поля"),
				new PhoneNumberValidator(PersonFormBuilderBase.MOBILE_PHONE_FIELD_DESCRIPTION)).build());

		//мобильный телефон
		formBuilder.addField(getStringField(EMAIL_FIELD_NAME, PersonFormBuilderBase.EMAIL_FIELD_DESCRIPTION,
				new LengthFieldValidator(BigInteger.valueOf(SEVENTY))).build());

		return formBuilder.build();
	}
}
