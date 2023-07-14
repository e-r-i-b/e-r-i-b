package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ����� � ����������� ���������� �����������
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"required", "count"})
@XmlRootElement(name = "notification")
public class Notification
{
	private Integer count;
	private boolean required;

	/**
	 */
	public Notification() {}

	/**
	 * @param count ���������� �����������
	 * @param required ���� ������������� ����������� ������
	 */
	public Notification(Integer count, boolean required)
	{
		this.count = count;
		this.required = required;
	}

	/**
	 * @return ���������� �����������. ���������� ��� required = true
	 */
	@XmlElement(name = "count", required = false)
	public Integer getCount()
	{
		return count;
	}

	/**
	 * @return ���� ������������� ����������� ������
	 */
	@XmlElement(name = "required", required = true)
	public boolean isRequired()
	{
		return required;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}
}
