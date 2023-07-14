package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author: Pakhomova
 * @created: 07.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class PhoneNumberValidator extends FieldValidatorBase
{
	/** ������ ��� ������ ��������. ����� ���� ��������� 3-5 ����, ����� ������ �������� - 5-7 ����
	 *  ����� ���� � ������, � ������ +7, ������ ���� ����� 11
	 * TODO: ��� ��������� ������� ����� ��������� com.rssl.phizicgate.mobilebank.PhoneNumberFormat 
	 */
	private final String REGEXP_FOR_PHONE_NUMBER = "\\+7( )?\\(((\\d{3}\\)( )?\\d{7})|(\\d{4}\\)( )?\\d{6})|(\\d{5}\\)( )?\\d{5}))";

//	private final String ERROR_MESSAGE_FOR_USER =
	/**
	 *
	 * @param fieldName �������� ����, �� ������� �������� ��������� �� ������
	 */
	public PhoneNumberValidator(String fieldName)
	{
		setMessage("������� ���������� �������� � ���� "+ fieldName+ " � ������� +7(���_���������)�����_��������."
				+" � ������ ������ ������ ���� ������ �����, ����� ����� ����, � ������ +7, ������ ���� ����� �����������.");
	}

	public PhoneNumberValidator(){}

	/**
	 *
	 * @param value ������������ �� ����� ����� ��������
	 * @return true, ���� value ����� ���������� ������
	 * ������ ������ �������� (����������)  +7 (���_���������) �����_��������
	 * ������ �����������, ������� �� "(" � ����� ")" �����������
	 */
	public boolean validate(String value) throws TemporalDocumentException
	{
        if (isValueEmpty(value))
        {
            return true;
        }
		RegexpFieldValidator checkValue = new RegexpFieldValidator(REGEXP_FOR_PHONE_NUMBER, getMessage());

		return checkValue.validate(value);
	}
}
