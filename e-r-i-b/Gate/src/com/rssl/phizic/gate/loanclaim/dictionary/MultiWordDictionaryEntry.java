package com.rssl.phizic.gate.loanclaim.dictionary;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;

import java.io.Serializable;

/**
 * Запись справочника, заточенная под быстрый поиск по началу слова в БД
 * Одна запись разбивается на несколько слов (постфиксов)
 * @see #searchPostfix
 *
 * @author Puzikov
 * @ created 28.11.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class MultiWordDictionaryEntry extends AbstractDictionaryEntry implements Cloneable, Serializable
{
	/**
	 * Постфикс для поиска записи
	 * запись
	 *      имени К.Маркса-Пушкина
	 * будет представлена четырьмя (зависит от набора разделителей) постфиксами
	 *      имени к.маркса-пушкина
	 *      к.маркса-пушкина
	 *      маркса-пушкина
	 *      пушкина
	 */
	private String searchPostfix;

	public String getSearchPostfix()
	{
		return searchPostfix;
	}

	public void setSearchPostfix(String searchPostfix)
	{
		this.searchPostfix = searchPostfix;
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalErrorException("Не удалось склонировать запись справочника", e);
		}
	}
}
