package com.rssl.phizic.utils;

import junit.framework.TestCase;

/**
 * @author Erkin
 * @ created 28.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тесты для класса PhoneNumberFormat, PhoneNumber
 */
public class PhoneNumberTest extends TestCase
{
	/**
	 * Проверка работоспособности парсера PhoneNumberFormat.SIMPLE_NUMBER
	 */
	public void testSimpleNumberParsing()
	{
		PhoneNumber phoneNumber = PhoneNumberFormat.SIMPLE_NUMBER.parse("9164578901");

		assertNotNull(phoneNumber);

		System.out.println(phoneNumber);
	}
}
