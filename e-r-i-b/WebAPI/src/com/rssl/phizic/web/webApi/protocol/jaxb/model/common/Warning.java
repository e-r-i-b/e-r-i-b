package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Объекты данного типа описывают элемент warning
 *
 * Xml-представление данного класса имеет вид:
 *
 * <pre>
 * {@code
 *  <warning>
 *	    <text>string</text>
 *		<elementId>string</elementId><!--Необязательный элемент-->
 *	</warning>
 * }
 * </pre>
 *
 * @see Status
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"text", "elementId"})
@XmlRootElement(name = "warning")
public class Warning
{
	private String text;
	private String elementId;

	/**
	 */
	public Warning() {}

	/**
	 * @param text
	 * @param elementId
	 */
	public Warning(String text, String elementId)
	{
		this.text = text;
		this.elementId = elementId;
	}

	/**
	 * @return Текст сообщения заключенный в CDATA. Может иметь html теги форматирования.
	 */
	@XmlElement(name = "text", required = true)
	public String getText()
	{
		return text;
	}

	/**
	 * @return Имя элемента сообщения, к которому относится сообщение.
	 */
	@XmlElement(name = "elementId", required = false)
	public String getElementId()
	{
		return elementId;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setElementId(String elementId)
	{
		this.elementId = elementId;
	}
}
