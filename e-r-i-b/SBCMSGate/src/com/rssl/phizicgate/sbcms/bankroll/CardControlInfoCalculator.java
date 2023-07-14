package com.rssl.phizicgate.sbcms.bankroll;

import com.rssl.phizic.utils.StringHelper;

/**
 * �������� ������� ����������� ���������� �� ����� ��� �������� � ��.
 * ��. <\\Shark\Projects\����\2006_08_����\���\����������� �������.doc> 
 * @author Egorova
 * @ created 05.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CardControlInfoCalculator
{
	private static final int firstRus = (int) '�';
	private static final int lastRus = (int) '�';
	private static final int frontierRus = (int)'�';
	private static final int notStandartRus = (int)'�';
	private static final int firstEng = (int) 'A';
	private static final int lastEng = (int) 'Z';
	private static final int firstInt = (int) '0';
	private static final int lastInt = (int) '9';

	/**
	 * ������ ����������� ����������, �� ����������� ���������� �� �����,
	 * ��������� � ��������� �� ��������� ���������� �����
	 * @param cardCodeWord - ����������� ���������� �� �����
	 * @return ��������������� ����������� ����������
	 */
	public static String getCardControlInfo(String cardCodeWord)
	{
		cardCodeWord = cardCodeWord.toUpperCase();
		char[] cardCodeChars;
		if (cardCodeWord.length()<3) cardCodeChars = cardCodeWord.toCharArray();
		else cardCodeChars = cardCodeWord.substring(0,3).toCharArray();
		String cardControlInfo = "";
		for (char cardCodeChar: cardCodeChars)
		{
			int charCode = (int) cardCodeChar;
			if (charCode==notStandartRus) cardControlInfo+='7';
			else if ((firstRus <= charCode) && (charCode <= lastRus))
			{
				if (charCode <= frontierRus)
					cardControlInfo += charCode - firstRus + 1;
				else if (charCode > frontierRus)
					cardControlInfo += charCode - firstRus + 2;
			}
			else if ((firstEng <=charCode) && (charCode<= lastEng))
			{
				cardControlInfo += charCode - firstEng + 1;
			}
			else if ((firstInt <= charCode) && (charCode<=lastInt))
			{
				cardControlInfo += charCode - firstInt;
			}
			else
				cardControlInfo +='0';
		}
		return  StringHelper.appendEndingZeros(cardControlInfo,3);
	}
}
