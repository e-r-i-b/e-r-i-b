package com.rssl.phizic.web.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.business.persons.forms.ClientDepartmentValidator;
import com.rssl.phizic.business.persons.forms.DocumentSeriesAndNumberValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.persons.formBuilders.PersonFormBuilderBase;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * @author khudyakov
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class SearchPersonForm extends EditFormBase
{
	public static final Form IDENTY_DOCUMENT_SEACH_FORM = createDocumentForm();
	public static final Form CARD_SEACH_FORM = createCardForm();

	/**
	 * Шаблон для сообщения при незаполненном обязательном поле.
	 */
	private static final String EMPTY_FIELD_MESSAGE_TEMPLATE = "Поле \"%s\" обязательно для заполнения, укажите значение данного поля";

	private ActivePerson activePerson;
	private boolean modified;
	private boolean resetClientInformation;
	private Set<PersonDocument> personDocuments;
	private List<PersonDocumentType> documentTypes;
	private boolean continueSearch;

	public Set<PersonDocument> getPersonDocuments()
	{
		return personDocuments;
	}

	public void setPersonDocuments(Set<PersonDocument> personDocuments)
	{
		this.personDocuments = personDocuments;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	public boolean getResetClientInformation()
	{
		return resetClientInformation;
	}

	public void setResetClientInformation(boolean resetClientInformation)
	{
		this.resetClientInformation = resetClientInformation;
	}

	public List<PersonDocumentType> getDocumentTypes()
	{
		return documentTypes;
	}

	public void setDocumentTypes(List<PersonDocumentType> documentTypes)
	{
		this.documentTypes = documentTypes;
	}

	public boolean isContinueSearch()
	{
		return continueSearch;
	}

	public void setContinueSearch(boolean continueSearch)
	{
		this.continueSearch = continueSearch;
	}

	private static Form createCardForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		//номер карты
		FieldBuilder fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.CARD_NUMBER_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.CARD_NUMBER_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)), new LengthFieldValidator(new BigInteger("15"), new BigInteger("18")));
		formBuilder.addField(fb.build());

		//тип карты
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.CARD_TYPE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.CARD_TYPE_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)));
		formBuilder.addField(fb.build());

		//номер терминала
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.TERMINAL_NUMBER_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.TERMINAL_NUMBER_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)));
		formBuilder.addField(fb.build());

		//дата транзакции
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.TRANSACTION_DATE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.TRANSACTION_DATE_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)));
		formBuilder.addField(fb.build());

		//время транзакции
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.TRANSACTION_TIME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.TRANSACTION_TIME_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createDocumentForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		//фамилия
		FieldBuilder fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.SUR_NAME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.SUR_NAME_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)), new LengthFieldValidator(new BigInteger("50")));
		formBuilder.addField(fb.build());

		//имя
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.FIRST_NAME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.FIRST_NAME_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)), new LengthFieldValidator(new BigInteger("50")));
		formBuilder.addField(fb.build());

		//отчество
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.PATR_NAME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.PATR_NAME_FIELD_DESCRIPTION);
		fb.addValidators(new LengthFieldValidator(new BigInteger("50")));
		formBuilder.addField(fb.build());

		//тип д.у.л.
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.DOCUMENT_TYPE_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.DOCUMENT_TYPE_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)));
		formBuilder.addField(fb.build());

		//название д.у.л.
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.DOCUMENT_NAME_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.DOCUMENT_NAME_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)), new LengthFieldValidator(new BigInteger("80")));
		fb.setEnabledExpression(new RhinoExpression("form.documentType == 'OTHER'"));
		formBuilder.addField(fb.build());

		//номер д.у.л.
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.DOCUMENT_NUMBER_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.DOCUMENT_NUMBER_FIELD_DESCRIPTION);
		formBuilder.addField(fb.build());

		//серия д.у.л.
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.DOCUMENT_SERIES_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.DOCUMENT_SERIES_FIELD_DESCRIPTION);
		formBuilder.addField(fb.build());

		//дата рождения
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.BIRTH_DAY_FIELD);
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.BIRTH_DAY_FIELD_DESCRIPTION);
		fb.addValidators(new RequiredFieldValidator(getEmptyMessage(fb)), new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

		//номер тб
		fb = new FieldBuilder();
		fb.setName(PersonFormBuilderBase.REGION_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription(PersonFormBuilderBase.REGION_FIELD_DESCRIPTION);
		formBuilder.addField(fb.build());

		DocumentSeriesAndNumberValidator documentValidator = new DocumentSeriesAndNumberValidator();
	    documentValidator.setBinding(PersonFormBuilderBase.DOCUMENT_TYPE_FIELD, PersonFormBuilderBase.DOCUMENT_TYPE_FIELD);
		documentValidator.setBinding(PersonFormBuilderBase.DOCUMENT_SERIES_FIELD, PersonFormBuilderBase.DOCUMENT_SERIES_FIELD);
		documentValidator.setBinding(PersonFormBuilderBase.DOCUMENT_NUMBER_FIELD, PersonFormBuilderBase.DOCUMENT_NUMBER_FIELD);
		documentValidator.setBinding(PersonFormBuilderBase.DOCUMENT_NAME_FIELD, PersonFormBuilderBase.DOCUMENT_NAME_FIELD);
		formBuilder.addFormValidators(documentValidator);

		MultiFieldsValidator clientDepartmentValidator = new ClientDepartmentValidator();
		clientDepartmentValidator.setBinding(PersonFormBuilderBase.REGION_FIELD, PersonFormBuilderBase.REGION_FIELD);
		clientDepartmentValidator.setMessage("У Вас не достаточно прав для поиска клиентов по данному тербанку.");
		formBuilder.addFormValidators(clientDepartmentValidator);

		return formBuilder.build();
	}

	/**
	 * Возвращает сообщение об ошибке при незаполненном обязательном поле.
	 *
	 * @param fb строящееся поле.
	 * @return сообщение.
	 */
	private static String getEmptyMessage(FieldBuilder fb)
	{
		return String.format(EMPTY_FIELD_MESSAGE_TEMPLATE, fb.getDescription());
	}
}
