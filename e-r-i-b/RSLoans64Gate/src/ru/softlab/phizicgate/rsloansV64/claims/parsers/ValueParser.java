package ru.softlab.phizicgate.rsloansV64.claims.parsers;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� �������� IKFL � ������ Loans.
 */
public interface ValueParser
{
	/**
	 * �������������� �������� � ������ Loans
	 * @param value ��������
	 * @return �������������� ��������
	 */
	String parse(String value);
}
