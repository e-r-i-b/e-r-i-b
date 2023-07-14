package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"title", "text"})
@XmlRootElement(name = "help")
public class Help
{
	private HelpTitle title;
	private HelpText  text;

	/**
	 */
	public Help() {}

	/**
	 * @param title ������������ ��� ����������� ������������
	 * @param text ����� � �����
	 */
	public Help(HelpTitle title, HelpText text)
	{
		this.title = title;
		this.text  = text;
	}

	/**
	 * �������� ����� ��������� ��� ��������� ������� �� �������� �
	 * ������ ��� ��������� � ���� ������ �������
	 *
	 * @return ������������ ��� ����������� ������������
	 */
	@XmlElement(name = "title", required = true)
	public HelpTitle getTitle()
	{
		return title;
	}

	/**
	 * �������� ����� ��������� ��� ��������� ������� �� ����� � ����� �
	 * ������ ��� ��������� � ���� ������ ����� ���������� ��������
	 *
	 * @return ����� � �����
	 */
	@XmlElement(name = "text", required = true)
	public HelpText getText()
	{
		return text;
	}

	public void setTitle(HelpTitle title)
	{
		this.title = title;
	}

	public void setText(HelpText text)
	{
		this.text = text;
	}
}
