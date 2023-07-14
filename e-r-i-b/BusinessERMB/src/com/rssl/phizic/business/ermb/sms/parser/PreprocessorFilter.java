package com.rssl.phizic.business.ermb.sms.parser;

/**
 * ‘ильтр препроцессора смс-сообщений
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PreprocessorFilter
{
	/**
	 * ѕреобразует исходный текст смс-сообщени€, если он удовлетвор€ет опеределенному правилу
	 * @param text - исходный текст
	 * @return замененный текст либо исходный
	 */
	String filter(String text); 
}
