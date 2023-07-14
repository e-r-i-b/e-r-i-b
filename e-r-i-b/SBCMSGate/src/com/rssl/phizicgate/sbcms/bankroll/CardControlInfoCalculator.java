package com.rssl.phizicgate.sbcms.bankroll;

import com.rssl.phizic.utils.StringHelper;

/**
 * Алгоритм расчета контрольной информации по карте для передачи в ПЦ.
 * см. <\\Shark\Projects\СБРФ\2006_08_ИКФЛ\ЭСК\Руководство клиента.doc> 
 * @author Egorova
 * @ created 05.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CardControlInfoCalculator
{
	private static final int firstRus = (int) 'А';
	private static final int lastRus = (int) 'Я';
	private static final int frontierRus = (int)'Е';
	private static final int notStandartRus = (int)'Ё';
	private static final int firstEng = (int) 'A';
	private static final int lastEng = (int) 'Z';
	private static final int firstInt = (int) '0';
	private static final int lastInt = (int) '9';

	/**
	 * Расчет контрольной информации, из контрольной информации по карте,
	 * указанной в заявлении на получение банковской карты
	 * @param cardCodeWord - контрольная информация по карте
	 * @return преобразованная контрольная информация
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
