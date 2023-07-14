package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Picture;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ��������� ������� ���������� ������ � ����������, ������������ ��� ������ ������� ������
 * Xml-������������� ������� ������ ����� ���:
 *
 * <pre>
 * {@code
 *	<supplier>
 *	    <id>3</id>
 *	    <picture>
 *	        <link>string</link>
 *	        <url>string</url>
 *	    </picture>
 *	    <title>string</title>
 *	</supplier>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"id", "title", "picture", "url"})
@XmlRootElement(name = "supplier")
public class Supplier
{
	private int id;
	private String title;
	private Picture picture;
	private String url;

	/**
	 * @return ������������� ����������
	 */
	@XmlElement(name = "id", required = true)
	public int getId()
	{
		return id;
	}

	/**
	 * @return �������� ����������
	 */
	@XmlElement(name = "title", required = true)
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return ����������� ��� ����������
	 */
	@XmlElement(name = "picture", required = true)
	public Picture getPicture()
	{
		return picture;
	}

	/**
	 * @return ������ ��� ������
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "url", required = true)
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setPicture(Picture picture)
	{
		this.picture = picture;
	}
}
