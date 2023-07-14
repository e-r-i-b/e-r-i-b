package com.rssl.phizic;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Словарь соответствий старого номера ТБ и нового номера ТБ
 * Нужен для случаев когда ТБ сливаются, но номера меняются не везде (пример: Алтайский Банк) 
 * User: Gainanov
 * @ $Author$
 * @ $Revision$
 */
public class TBRenameDictionary extends Config
{
	private static final String PROP_NAME = "com.rssl.iccs.tb.rename.codes";


	//Мап зависимостей <старый номер ТБ, новый номер ТБ>
	private Map<String, String> synonyms = new HashMap<String, String>();

	public TBRenameDictionary(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		String props = getProperty(PROP_NAME);
		String[] synonymsArray =  props.split(",");
		for (String synonym : synonymsArray)
		{
			StringTokenizer st = new StringTokenizer(synonym, "-");
			synonyms.put(st.nextToken(), st.nextToken());
		}
	}

	/**
	 * Вернуть номер нового ТБ. null - соответствий не найдено.
	 * @param synonym - старый номер ТБ, для которого необходимо получить новый номер.
	 * @return Новый номер ТБ
	 */
	public String getNewTbBySynonym(String synonym)
	{
		return synonyms.get(synonym);
	}

	/**
	 * Вернуть номер старого ТБ. null - соответствий не найдено.
	 * @param synonym - новый номер TB, для котого необходимо получить старый номер.
	 * @return Старый номер ТБ.
	 */
	public String getOldTbBySynonym(String synonym)
	{
		for (String tbCode : getAllSynonyms().keySet())
		{
			//Подчиненный код тербанка
			String renameTbCode = getNewTbBySynonym(tbCode);
			String tbCodeWithoutLeadingZeros = StringHelper.removeLeadingZeros(renameTbCode);

			//Сравниваются коды тербанка без лидирующих нулей.
			if (tbCodeWithoutLeadingZeros.startsWith(StringHelper.removeLeadingZeros(synonym)))
				return tbCode;
		}
		return null;
	}

	public Map<String, String> getAllSynonyms()
	{
		return synonyms;
	}
}
