package com.rssl.phizic.business.ant.services.groups;

import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author akrenev
 * @ created 26.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Класс обновления групп сервисов прав доступа
 */

public class ServicesGroupLoader
{
	private static final String FILE_NAME = "operations-tree.xml";

	/**
	 * загрузить данные по группам сервисов
	 * @throws Exception
	 */
	public void load() throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		ServicesGroupReplicator replicator = new ServicesGroupReplicator();

		ServiceGroupHandler handler = new ServiceGroupHandler();
		saxParser.parse(getFile(FILE_NAME), handler);
		replicator.initialize(handler.getResult());
		replicator.replicate();
	}



	private InputStream getFile(String fileName) throws FileNotFoundException
	{
		if(fileName == null)
			throw new IllegalArgumentException("Параметр fileName не может быть null");
		return ResourceHelper.loadResourceAsStream(fileName);
	}
}
