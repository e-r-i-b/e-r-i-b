package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Объекты данного типа описывают элемент error
 * Xml-представление данного класса имеет вид:
 *
 * <pre>
 * {@code
 *  <error>
 *      <text>string</text>
 *      <elementId>string</elementId> <!--Необязательный элемент-->
 *  </error>
 * }
 * </pre>
 *
 * @see Status
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"text", "elementId"})
@XmlRootElement(name = "error")
public class Error
{
	private String text;
	private String elementId;

	/**
	 *
	 */
	public Error() {}

	/**
	 * @param text
	 * @param elementId
	 */
	public Error(String text, String elementId)
	{
		this.text      = text;
		this.elementId = elementId;
	}

	/**
	 * @return Текст ошибки заключенный в CDATA. Может иметь  html теги форматирования.
	 */
	@XmlElement(required = true)
	public String getText()
	{
		return text;
	}

	/**
	 * @return Имя элемента сообщения, к которому относится ошибка.
	 */
	@XmlElement(required = false)
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
