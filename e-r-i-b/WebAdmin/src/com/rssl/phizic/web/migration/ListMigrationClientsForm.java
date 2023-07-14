package com.rssl.phizic.web.migration;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма списка мигрируемых клиентов
 */

public class ListMigrationClientsForm extends ListFormBase
{
	public static final String CLIENT_FIELD_NAME           = "client";
	public static final String DOCUMENT_FIELD_NAME         = "document";
	public static final String DEPARTMENT_FIELD_NAME       = "department";
	public static final String BIRTHDAY_FIELD_NAME         = "birthday";
	public static final String AGREEMENT_TYPE_FIELD_NAME   = "agreementType";
	public static final String AGREEMENT_NUMBER_FIELD_NAME = "agreementNumber";

	public static final Form FILTER_FORM = getFilterForm();
	private List<Department> departments;
	private List<CreationType> agreementTypes;

	private static Form getFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(FieldBuilder.buildStringField(CLIENT_FIELD_NAME,           "Клиент",
				new RegexpFieldValidator("[а-яА-ЯёЁa-zA-Z- ]{3,255}", "Пожалуйста, укажите ФИО клиента не менее 3 символов, содержащее буквы русского или латинского алфавита, дефис или пробел")));
		formBuilder.addField(FieldBuilder.buildStringField(DOCUMENT_FIELD_NAME,         "Документ (ДУЛ)"));
		formBuilder.addField(FieldBuilder.buildStringField(DEPARTMENT_FIELD_NAME,       "Террбанк"));
		formBuilder.addField(FieldBuilder.buildStringField(AGREEMENT_NUMBER_FIELD_NAME, "Номер договора"));
		formBuilder.addField(FieldBuilder.buildDateField(BIRTHDAY_FIELD_NAME,           "Дата рождения"));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AGREEMENT_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("Тип договора");
		fieldBuilder.setParser(new EnumParser<CreationType>(CreationType.class));
		fieldBuilder.addValidators(new EnumFieldValidator<CreationType>(CreationType.class, "Неизвестный тип договора."));
		formBuilder.addField(fieldBuilder.build());

		MultiFieldsValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIELD_NAME, CLIENT_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_FIELD_NAME, DOCUMENT_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(AGREEMENT_NUMBER_FIELD_NAME, AGREEMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("Пожалуйста, заполните хотя бы одно из полей фильтра: клиент, документ(ДУЛ), номер договора.");
		formBuilder.addFormValidators(requiredMultiFieldValidator);

		return formBuilder.build();
	}

	/**
	 * задть список подразделений для фильтрации
	 * @param departments писок подразделений
	 */
	public void setDepartments(List<Department> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}

	/**
	 * @return список подразделений для фильтрации
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<Department> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * задть список типов договоров для фильтрации
	 * @param agreementTypes типы
	 */
	public void setAgreementTypes(List<CreationType> agreementTypes)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.agreementTypes = agreementTypes;
	}

	/**
	 * @return список типов договоров для фильтрации
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public List<CreationType> getAgreementTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return agreementTypes;
	}
}
