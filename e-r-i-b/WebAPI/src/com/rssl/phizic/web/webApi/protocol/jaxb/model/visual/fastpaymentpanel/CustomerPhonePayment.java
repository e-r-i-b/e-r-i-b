package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Picture;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Описывает элемент содержащий данные для оплаты телефона клиента
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"phoneNumber", "picture", "url"})
@XmlRootElement(name = "customerPhonePayment")
public class CustomerPhonePayment
{
	private Picture picture;
	private String phoneNumber;
	private String url;

	/**
	 * @return возвращаемый объект содержит url картинки и ссылку для редиректа в ЕРИБ
	 */
	@XmlElement(required = true)
	public Picture getPicture()
	{
		return picture;
	}

	/**
	 * @return Номер телефона клиента
	 */
	@XmlElement(name = "phoneNumber", required = true)
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @return Ссылка для оплаты
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

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setPicture(Picture picture)
	{
		this.picture = picture;
	}
}
