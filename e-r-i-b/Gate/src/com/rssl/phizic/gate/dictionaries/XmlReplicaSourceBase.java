package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.EmptyResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Kosyakov
 * @ created 22.09.2006
 * @ $Author: balovtsev $
 * @ $Revision: 54705 $
 */
public abstract class XmlReplicaSourceBase implements XmlReplicaSource
{
	protected static final String PREMATURE_END_MESSAGE = "Преждевременная остановка парсинга.";

	private boolean chained = false;
	protected static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public XMLReader chainXMLReader ( XMLReader xmlReader )
	{
		chained = true;
		clearData();
		return xmlReader;
	}

	protected abstract void clearData ();

	protected abstract InputStream getDefaultStream ();

	protected abstract XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException;

	protected void internalParse () throws GateException, GateLogicException
	{
		if (!chained)
		{
			clearData();
			try
			{
				XMLFilter parser = getDefaultFilter();
				InputStream stream = getDefaultStream();
				if (stream!=null)
				{
					parser.setEntityResolver(new EmptyResolver());
					parser.parse(new InputSource(stream));
				}
			}
			catch (ParserConfigurationException e)
			{
				throw new GateException(e);
			}
			catch (ParsingPrematureCompleteSAXException ignore)
			{
				log.info(PREMATURE_END_MESSAGE);
			}
			catch (SAXException se)
			{
				log.error(se);
				throw new GateLogicException(getErrorMessage(), se);
			}
			catch (IOException ie)
			{
				throw new GateException(ie);
			}
		}
	}

	/**
	 * Преждевременная остановка парсинга(в случае если данных достаточно)
	 * @throws SAXException
	 */
	protected void prematureComplete() throws SAXException
	{
		throw new ParsingPrematureCompleteSAXException();
	}

	protected String getErrorMessage()
	{
		return "Некорректный формат справочника.";
	}

	public void close ()
	{
	}

	/**
	 * Утилитный метод получения ридера из ресурсов
	 * @param resourceName имя файла
	 * @return метод.
	 */
	protected InputStream getResourceReader(String resourceName)
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
	}
}
