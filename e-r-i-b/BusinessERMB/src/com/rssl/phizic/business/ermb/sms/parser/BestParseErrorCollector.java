package com.rssl.phizic.business.ermb.sms.parser;

/**
* @author Erkin
* @ created 17.04.2014
* @ $Author$
* @ $Revision$
*/

/**
 * Запоминает ошибку, которая лучшим образом соотвествует СМС-запросу.
 * Ошибка А соответствует СМС-запросу больше ошибки Б, если А возникла ближе к концу текста.
 */
class BestParseErrorCollector implements ParseErrorCollector
{
	private String bestError = null;

	private int bestErrorOffset = -1;

	///////////////////////////////////////////////////////////////////////////

	public void addError(String error, int errorOffset)
	{
		if (bestError == null || errorOffset > bestErrorOffset)
		{
			bestError = error;
			bestErrorOffset = errorOffset;
		}
	}

	String getBestError()
	{
		return bestError;
	}
}
