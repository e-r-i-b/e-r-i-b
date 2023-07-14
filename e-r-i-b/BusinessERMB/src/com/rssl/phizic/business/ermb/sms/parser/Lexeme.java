package com.rssl.phizic.business.ermb.sms.parser;

/**
 * @author Erkin
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������
 */
enum Lexeme
{
	/**
	 * ��������
	 */
	COMMAND,

	/**
	 * �������� ����� ��������
	 */
	COMMAND_KEYWORD,

	/**
	 * �����������
	 */
	DELIMITER,

	/**
	 * ����������
	 */
	RECIPIENT,

	/**
	 * �������
	 */
	PRODUCT,

	/**
	 * ����� �����
	 */
	OTHER_CARD,

	/**
	 * ����� ��������
	 */
	PHONE,

	/**
	 * ����� (����������, ��������)
	 */
	ALIAS,

	/**
	 * �������� ������
	 */
	TARIFF,

	/**
	 * �����
	 */
	AMOUNT,

	/**
	 * �����
	 */
	NUMBER,

	/**
	 * ������
	 */
	STRING,

	/**
	 * ����� ������
	 */
	EOF
}
