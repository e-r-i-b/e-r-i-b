package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ���������� � �����
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "aboutBank")
public class AboutBank
{
	private String text;

	/**
	 */
	public AboutBank() {}

	/**
	 * @param text �����, ������������ ������������ (����� ����������� � CDATA. ����� ����� html ���� ��������������)
	 */
	public AboutBank(String text)
	{
		this.text = text;
	}

	/**
	 * �����, ������������ ������������ (����� ����������� � CDATA. ����� ����� html ���� ��������������)
	 *
	 * @return String
	 */
	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "text", required = true)
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
