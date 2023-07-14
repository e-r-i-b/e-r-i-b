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
	/** шаблон для номера телефона. длина кода оператора 3-5 цифр, длина номера телефона - 5-7 цифр
	 *  число цифр в номере, с учетом +7, должно быть равно 11
	 * TODO: При изменении шаблона также исправить com.rssl.phizicgate.mobilebank.PhoneNumberFormat 
	 */
	private final String REGEXP_FOR_PHONE_NUMBER = "\\+7( )?\\(((\\d{3}\\)( )?\\d{7})|(\\d{4}\\)( )?\\d{6})|(\\d{5}\\)( )?\\d{5}))";

//	private final String ERROR_MESSAGE_FOR_USER =
	/**
	 *
	 * @param fieldName название поля, на которое выдавать сообщение об ошибке
	 */
	public PhoneNumberValidator(String fieldName)
	{
		setMessage("Введите корректное значение в поле "+ fieldName+ " в формате +7(код_оператора)номер_телефона."
				+" В записи номера должны быть только цифры, общее число цифр, с учетом +7, должно быть равно одиннадцати.");
	}

	public PhoneNumberValidator(){}

	/**
	 *
	 * @param value передаваемый из формы номер телефона
	 * @return true, если value имеет правильный шаблон
	 * шаблон номера телефона (мобильного)  +7 (код_оператора) номер_телефона
	 * Скобки обязательны, пробелы до "(" и после ")" опциональны
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
