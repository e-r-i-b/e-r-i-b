package com.rssl.phizic.business.persons.forms;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Egorova
 * @ created 13.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class DocumentSeriesAndNumberValidator extends MultiFieldsValidatorBase
{
	public static final String PARAMETER_DOCUMENT_TYPE = "documentType";
	public static final String PARAMETER_DOCUMENT_SERIES = "documentSeries";
	public static final String PARAMETER_DOCUMENT_NUMBER = "documentNumber";
	public static final String PARAMETER_DOCUMENT_NAME = "documentName";
	private ThreadLocal<String> currentMessage  = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String documenyTypeString = (String) retrieveFieldValue(PARAMETER_DOCUMENT_TYPE, values);
		PersonDocumentType documentType = (StringHelper.isEmpty(documenyTypeString))?null:PersonDocumentType.valueOf((String) retrieveFieldValue(PARAMETER_DOCUMENT_TYPE, values));
		String documentSeries = (String) retrieveFieldValue(PARAMETER_DOCUMENT_SERIES, values);
		String documentNumber = (String) retrieveFieldValue(PARAMETER_DOCUMENT_NUMBER, values);

		//todo тут закладываемся на то, что, когда клиент еще не трогал комбобокс с типом документа
		// и еще ничего не ввел в поля серия-номер - значит, он еще не собирается вводить эти данные
		// и проверять их не нужно.
		if ( documentType==null	&& StringHelper.isEmpty(documentSeries) && StringHelper.isEmpty(documentNumber))
			return true;

		//для паспорта way серия должна быть заполнена обязательно
		if (documentType.equals(PersonDocumentType.PASSPORT_WAY) && StringHelper.isEmpty(documentSeries))
		{
			setMessage("Укажите серию документа, удостоверяющего личность");
			return false;
		}
		if (documentType.equals(PersonDocumentType.REGULAR_PASSPORT_RF))
		{
			setMessage("Серия общегражданского паспорта РФ должна состоять из 4 цифр, а номер из 6.");
			return (documentNumber.matches("\\d{6}") && documentSeries.matches("\\d{2}\\s{0,}\\d{2}"));
		}
		if (documentType.equals(PersonDocumentType.MILITARY_IDCARD))
		{
			setMessage("Серия удостоверения личности военнослужащего должна состоять из 2 букв, а номер -  из 7 цифр.");
			return documentSeries.matches("[А-яA-z]{2}") && (documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.SEAMEN_PASSPORT))
		{
			setMessage("Серия паспорта моряка должна состоять из 2 букв (МФ, РФ, РХ), а номер из 7 цифр.");
			return (documentSeries.matches("[А-Я]{2}") && (documentSeries.equals("МФ") || documentSeries.equals("РФ") || documentSeries.equals("РХ")))&&
					(documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.RESIDENTIAL_PERMIT_RF))
		{
			setMessage("Серия вида на жительство РФ должна состоять из 2 цифр, а номер из 7.");
			return documentSeries.matches("\\d{2}")&& (documentNumber.matches("\\d{7}"));
		}
		if(documentType.equals(PersonDocumentType.FOREIGN_PASSPORT_RF))
		{
			setMessage("Серия заграничного паспорта РФ должна состоять из 2 цифр, а номер из 7.");
			return documentSeries.matches("\\d{2}")&& (documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.REFUGEE_IDENTITY))
		{
			setMessage("Серия удостоверения беженца должна состоять из 1 буквы, а номер из 6 цифр.");
			return documentSeries.matches("[А-яA-z]{1}")&& (documentNumber.matches("\\d{6}"));
		}
		if (documentType.equals(PersonDocumentType.IMMIGRANT_REGISTRATION))
		{
			setMessage("Серия свидетельства о регистрации ходатайства иммигранта о признании его беженцем в сумме с номером должна содержать 25 знаков.");
			String documentSN = documentSeries + documentNumber;
			return documentSN.length()==25;
		}
		if (documentType.equals(PersonDocumentType.DISPLACED_PERSON_DOCUMENT))
		{
			setMessage("Серия удостоверения вынужденного переселенца должна состоять из 2 букв, а номер из 6 или 7 цифр.");
			return documentSeries.matches("[А-Я]{2}")&& (documentNumber.matches("\\d{6,7}"));
		}
		if (documentType.equals(PersonDocumentType.TEMPORARY_PERMIT))
		{
			setMessage("Серия разрешения на временное проживание (для лиц без гражданства) должна состоять из 2, а номер из 6 цифр.");
			return documentSeries.matches("\\d{2}")&& (documentNumber.matches("\\d{6}"));
		}
		if (documentType.equals(PersonDocumentType.OTHER))
		{
			String documentName = (String) retrieveFieldValue(PARAMETER_DOCUMENT_NAME, values);
			if (StringHelper.isEmpty(documentName))
			{
				setMessage("Введите название документа, удостоверяющего личность.");
				return false;
			}
		}
		setMessage("Пожалуйста, введите серию или номер документа, удостоверяющего личность.");
		return StringHelper.isNotEmpty(documentNumber) || StringHelper.isNotEmpty(documentSeries);
	}

	public String getMessage()
	{
		return currentMessage.get();
	}

	public void setMessage(String message)
	{
		currentMessage.set(message);		
	}
}
