package com.rssl.phizic.business.persons.xmlserialize;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.exceptions.GateException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 27.09.2006
 * Time: 11:57:05
 * To change this template use File | Settings | File Templates.
 */
public class XMLPersonRepresentation
{
	private Long id;
	private String userId;
	private String xmlString;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getXMLString()
	{
		return xmlString;
	}

	public void setXMLString(String xmlString)
	{
		this.xmlString = xmlString;
	}

	public Document getXML() throws BusinessException
	{
		try
		{
			return XmlHelper.parse( getXMLString() );
		}
		catch(ParserConfigurationException ex)
		{
			throw new BusinessException("невозможно сконфигурировать парсер.",ex);
		}
		catch(SAXException ex)
		{
			throw new BusinessException("xml файл неизвестного формата.",ex);
		}
		catch(IOException ex)
		{
			throw new BusinessException("xml не найден.",ex);
		}
	}

}
