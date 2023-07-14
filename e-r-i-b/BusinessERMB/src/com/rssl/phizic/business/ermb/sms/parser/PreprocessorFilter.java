package com.rssl.phizic.business.ermb.sms.parser;

/**
 * ������ ������������� ���-���������
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PreprocessorFilter
{
	/**
	 * ����������� �������� ����� ���-���������, ���� �� ������������� �������������� �������
	 * @param text - �������� �����
	 * @return ���������� ����� ���� ��������
	 */
	String filter(String text); 
}
