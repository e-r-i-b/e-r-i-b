package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.ant.pfp.dictionary.actions.DictionaryRecordsActionBase;
import com.rssl.phizic.business.ant.pfp.dictionary.actions.PFPDictionaryConfig;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Справочники ПФП из xml
 */
public class XmlPFPDictionary implements PFPDictionary<PFPDictionaryRecordWrapper<PFPDictionaryRecord>>
{
	private static final String DICTIONARY_TAG_NAME = "dictionary";
	private static final String DICTIONARY_NAME_ATTRIBUTE_NAME = "name";

	private File sourceFile;  //источник данных
	private Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> dictionaryRecords; //данные

	/**
	 * @param sourceFile источник загрузки
	 */
	public XmlPFPDictionary(File sourceFile)
	{
		this.sourceFile = sourceFile;
	}

	public Collection<PFPDictionaryRecordWrapper<PFPDictionaryRecord>> getDictionary(PFPDictionaryConfig dictionaryConfig)
	{
		Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> values = dictionaryRecords.get(dictionaryConfig);
		if(values == null)
			return Collections.emptyList();
		return values.values();
	}

	/**
	 * обновление данных справочника
	 * @throws ConfigurationException
	 */
	public void refresh() throws ConfigurationException
	{
		try
		{
			dictionaryRecords = new HashMap<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>>();
			XmlFileReader xmlFileReader = new XmlFileReader(sourceFile);
			Element pagesXMLElement = xmlFileReader.readDocument().getDocumentElement();
			XmlHelper.foreach(pagesXMLElement, DICTIONARY_TAG_NAME, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String dictionaryName = element.getAttribute(DICTIONARY_NAME_ATTRIBUTE_NAME);
					PFPDictionaryConfig dictionary = PFPDictionaryConfig.valueOf(dictionaryName);
					DictionaryRecordsActionBase action = dictionary.getAction();
					action.initialize(dictionaryRecords);
					XmlHelper.foreach(element, dictionary.getEntriesName(), action);
					//noinspection unchecked
					dictionaryRecords.put(dictionary, action.getRecords());
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка при построении справочника ПФП.", e);
		}
	}
}
