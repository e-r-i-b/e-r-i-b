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

		//todo ��� ������������� �� ��, ���, ����� ������ ��� �� ������ ��������� � ����� ���������
		// � ��� ������ �� ���� � ���� �����-����� - ������, �� ��� �� ���������� ������� ��� ������
		// � ��������� �� �� �����.
		if ( documentType==null	&& StringHelper.isEmpty(documentSeries) && StringHelper.isEmpty(documentNumber))
			return true;

		//��� �������� way ����� ������ ���� ��������� �����������
		if (documentType.equals(PersonDocumentType.PASSPORT_WAY) && StringHelper.isEmpty(documentSeries))
		{
			setMessage("������� ����� ���������, ��������������� ��������");
			return false;
		}
		if (documentType.equals(PersonDocumentType.REGULAR_PASSPORT_RF))
		{
			setMessage("����� ���������������� �������� �� ������ �������� �� 4 ����, � ����� �� 6.");
			return (documentNumber.matches("\\d{6}") && documentSeries.matches("\\d{2}\\s{0,}\\d{2}"));
		}
		if (documentType.equals(PersonDocumentType.MILITARY_IDCARD))
		{
			setMessage("����� ������������� �������� ��������������� ������ �������� �� 2 ����, � ����� -  �� 7 ����.");
			return documentSeries.matches("[�-�A-z]{2}") && (documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.SEAMEN_PASSPORT))
		{
			setMessage("����� �������� ������ ������ �������� �� 2 ���� (��, ��, ��), � ����� �� 7 ����.");
			return (documentSeries.matches("[�-�]{2}") && (documentSeries.equals("��") || documentSeries.equals("��") || documentSeries.equals("��")))&&
					(documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.RESIDENTIAL_PERMIT_RF))
		{
			setMessage("����� ���� �� ���������� �� ������ �������� �� 2 ����, � ����� �� 7.");
			return documentSeries.matches("\\d{2}")&& (documentNumber.matches("\\d{7}"));
		}
		if(documentType.equals(PersonDocumentType.FOREIGN_PASSPORT_RF))
		{
			setMessage("����� ������������ �������� �� ������ �������� �� 2 ����, � ����� �� 7.");
			return documentSeries.matches("\\d{2}")&& (documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.REFUGEE_IDENTITY))
		{
			setMessage("����� ������������� ������� ������ �������� �� 1 �����, � ����� �� 6 ����.");
			return documentSeries.matches("[�-�A-z]{1}")&& (documentNumber.matches("\\d{6}"));
		}
		if (documentType.equals(PersonDocumentType.IMMIGRANT_REGISTRATION))
		{
			setMessage("����� ������������� � ����������� ����������� ���������� � ��������� ��� �������� � ����� � ������� ������ ��������� 25 ������.");
			String documentSN = documentSeries + documentNumber;
			return documentSN.length()==25;
		}
		if (documentType.equals(PersonDocumentType.DISPLACED_PERSON_DOCUMENT))
		{
			setMessage("����� ������������� ������������ ����������� ������ �������� �� 2 ����, � ����� �� 6 ��� 7 ����.");
			return documentSeries.matches("[�-�]{2}")&& (documentNumber.matches("\\d{6,7}"));
		}
		if (documentType.equals(PersonDocumentType.TEMPORARY_PERMIT))
		{
			setMessage("����� ���������� �� ��������� ���������� (��� ��� ��� �����������) ������ �������� �� 2, � ����� �� 6 ����.");
			return documentSeries.matches("\\d{2}")&& (documentNumber.matches("\\d{6}"));
		}
		if (documentType.equals(PersonDocumentType.OTHER))
		{
			String documentName = (String) retrieveFieldValue(PARAMETER_DOCUMENT_NAME, values);
			if (StringHelper.isEmpty(documentName))
			{
				setMessage("������� �������� ���������, ��������������� ��������.");
				return false;
			}
		}
		setMessage("����������, ������� ����� ��� ����� ���������, ��������������� ��������.");
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
