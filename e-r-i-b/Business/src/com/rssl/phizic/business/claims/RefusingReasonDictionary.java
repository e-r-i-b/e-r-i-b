package com.rssl.phizic.business.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.config.Constants;
import com.rssl.phizic.business.dictionaries.config.DictionaryPathConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * —правочник причин отказа дл€ за€вок
 * @author Kidyaev
 * @ created 21.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class RefusingReasonDictionary
{
	private List<String> refusingReasons = new ArrayList<String>();

	/**
	 * @return список причин отказа
	 */
	public List<String> getRefusingReasons()
	{
		return Collections.unmodifiableList(refusingReasons);
	}

	@SuppressWarnings({"JavaDoc"})
	public RefusingReasonDictionary() throws BusinessException
	{
		DictionaryPathConfig dictionaryPathConfig = ConfigFactory.getConfig(DictionaryPathConfig.class);
		String               fileName             = dictionaryPathConfig.getPath(Constants.REFUSING_REASON_DICTIONARY);

		if ( fileName == null )
			throw new BusinessException("Ќе найден путь к файлу справочника причин отказа за€вок ["
										+ Constants.REFUSING_REASON_DICTIONARY + "]");
		
		try
		{
			Document document   = XmlHelper.loadDocumentFromResource(fileName);
			NodeList entityList = XmlHelper.selectNodeList(document.getDocumentElement(), "entity");

			for ( int i = 0; i < entityList.getLength(); i++ )
			{
				Element entity = (Element) entityList.item(i);
				refusingReasons.add(entity.getAttribute("key"));
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
