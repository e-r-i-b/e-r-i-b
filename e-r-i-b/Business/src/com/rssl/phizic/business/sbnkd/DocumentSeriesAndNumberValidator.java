package com.rssl.phizic.business.sbnkd;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * ��������� ��� �������� ����� � ������ ����(��� �����)
 * @author lukina
 * @ created 13.01.2015
 * @ $Author$
 * @ $Revision$
 */
public class DocumentSeriesAndNumberValidator extends MultiFieldsValidatorBase
{
	public static final String PARAMETER_DOCUMENT_TYPE = "documentType";
	public static final String PARAMETER_DOCUMENT_NUMBER = "documentSeriesAndNumber";
	private ThreadLocal<String> currentMessage  = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String documenyTypeString = (String) retrieveFieldValue(PARAMETER_DOCUMENT_TYPE, values);
		PersonDocumentType documentType = (StringHelper.isEmpty(documenyTypeString))?null:PersonDocumentType.valueOf((String) retrieveFieldValue(PARAMETER_DOCUMENT_TYPE, values));
		String documentSeriesAndNumber = (String) retrieveFieldValue(PARAMETER_DOCUMENT_NUMBER, values);
		documentSeriesAndNumber = documentSeriesAndNumber.trim();
		int last = documentSeriesAndNumber.lastIndexOf(" ");
		String documentNumber = "";
		String documentSeries = "";
		if (last > 0 )
		{
			documentNumber = documentSeriesAndNumber.substring(last).trim();
			documentSeries = documentSeriesAndNumber.substring(0, last).trim();
		}
		else
			documentNumber = documentSeriesAndNumber;

		//������������� �� ��, ���, ����� ������ ��� �� ������ ��������� � ����� ���������
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
			setMessage("����� ������������� �������� ��������������� ������ �������� �� 2 ����, � ����� -  �� 6 ��� 7 ����.");
			return documentSeries.matches("[�-�A-z]{2}") && (documentNumber.matches("\\d{6,7}"));
		}
		if (documentType.equals(PersonDocumentType.SEAMEN_PASSPORT))
		{
			setMessage("����� �������� ������ ������ �������� �� 2 ���� (��, ��, ��), � ����� �� 7 ����.");
			return (documentSeries.matches("[�-�]{2}") && (documentSeries.equals("��") || documentSeries.equals("��") || documentSeries.equals("��")))&&
					(documentNumber.matches("\\d{7}"));
		}
		if (documentType.equals(PersonDocumentType.RESIDENTIAL_PERMIT_RF))
		{
			setMessage("����� ���� �� ���������� �� ������ �������� �� ����� ��� �� 1 � �� ����� ��� �� 25 ����.");
			return (documentNumber.matches(".{1,25}"));
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
			return documentSN.length()== 25;
		}
		if (documentType.equals(PersonDocumentType.DISPLACED_PERSON_DOCUMENT))
		{
			setMessage("����� ������������� ������������ ����������� ������ �������� �� 2 ����, � ����� �� 6 ��� 7 ����.");
			return documentSeries.matches("[�-�]{2}")&& (documentNumber.matches("\\d{6,7}"));
		}
		if (documentType.equals(PersonDocumentType.TEMPORARY_PERMIT))
		{
			setMessage("����� ���������� �� ��������� ���������� (��� ��� ��� �����������) ������ �������� �� ����� ��� �� 1 � �� ����� ��� �� 25 ����.");
			return (documentNumber.matches(".{1,25}"));
		}
		if(documentType.equals(PersonDocumentType.REGULAR_PASSPORT_USSR))
		{
			setMessage("����� �������� ���������� ���� ������ �������� �� �������� �����, ����������� ��������� (IVXLC ��� 1����), ���� � 2 ������� ��������� ����, � ����� �� 6 ����.");
			return documentSeries.matches("([IVXLCM1�����]+)-([�-�]{2})")&& (documentNumber.matches("\\d{6}"));
		}
		if(documentType.equals(PersonDocumentType.BIRTH_CERTIFICATE))
		{
			setMessage("����� ������������� � �������� ������ �������� �� �������� �����, ����������� ��������� (IVXLC ��� 1����), ���� � 2 ������� ��������� ����, � ����� �� 6 ����.");
			return documentSeries.matches("([IVXLC1����]+)-([�-�]{2})")&& (documentNumber.matches("\\d{6}"));
		}
		if(documentType.equals(PersonDocumentType.OFFICER_IDCARD))
		{
			setMessage("����� ������������� �������� ������� ������ �������� 2 ������� ��������� ����, � ����� �� 6 ��� 7 ����.");
			return documentSeries.matches("([�-�]{2})")&& (documentNumber.matches("(\\d{6,7})"));
		}
		if(documentType.equals(PersonDocumentType.INQUIRY_ON_CLEARING))
		{
			setMessage("����� ������� �� ������������ �� ����� ������� ������� � ����� � ������� ������ ��������� 25 ������.");
			String documentSN = documentSeries + documentNumber;
			return documentSN.length()== 25;
		}
		if(documentType.equals(PersonDocumentType.PASSPORT_MINMORFLOT))
		{
			setMessage("����� �������� ����������� ������ �������� 2 ������� ��������� ����, � ����� �� 6 ����.");
			return documentSeries.matches("([�-�]{2})")&& (documentNumber.matches("(\\d{6})"));
		}
		if(documentType.equals(PersonDocumentType.DIPLOMATIC_PASSPORT_RF))
		{
			setMessage("����� ���������������� �������� ���������� �� ������ �������� 2, � ����� �� 6 ����.");
			return documentSeries.matches("(\\d{2})")&& (documentNumber.matches("(\\d{6})"));
		}
		if (documentType.equals(PersonDocumentType.RESERVE_OFFICER_IDCARD))
		{
			setMessage("����� �������� ������ ������� ������ ������ �������� �� 2 ����, � ����� �� 6 ��� 7 ����.");
			return documentSeries.matches("[�-�]{2}")&& (documentNumber.matches("\\d{6,7}"));
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
