package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Erkin
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Словарь.
 * ВАЖНО! Слова в словаре отсортированы по длине: первыми следуют самые длинные слова
 * ВАЖНО! Словарь не пуст и не содержит слов нулевой длины (null или "")
 */
public class Dictionary
{
	private final List<String> words;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param words - перечень слов (can be empty)
	 */
	public Dictionary(String... words)
	{
		this(Arrays.asList(words));
	}

	/**
	 * ctor
	 * @param words - перечень слов (can be empty)
	 */
	public Dictionary(List<String> words)
	{
		for (String word : words)
		{
			if (StringHelper.isEmpty(word))
				throw new IllegalArgumentException("Входной перечень слов содержит пустую строку: " + Arrays.toString(words.toArray()));
		}

		this.words = sortWords(new ArrayList<String>(words));
	}

	private List<String> sortWords(List<String> words)
	{
		Collections.sort(words, new Comparator<String>()
		{
			public int compare(String o1, String o2)
			{
				return o2.length() - o1.length();
			}
		});
		return words;
	}

	/**
	 * @return все слова словаря
	 */
	public List<String> getWords()
	{
		return Collections.unmodifiableList(words);
	}

	@Override
	public String toString()
	{
		return Arrays.toString(words.toArray());
	}
}
