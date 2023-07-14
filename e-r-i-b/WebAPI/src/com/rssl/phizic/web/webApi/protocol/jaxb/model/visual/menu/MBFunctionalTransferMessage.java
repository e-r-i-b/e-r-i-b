package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Информационное сообщение о переносе функционала мобильного банка и
 * мобильных приложений в профиль клиента.
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
public class MBFunctionalTransferMessage
{
	private String text;

	/**
	 */
	public MBFunctionalTransferMessage() {}

	/**
	 * @param text текст сообщения, отображаемый клиенту. Заключен в блок CDATA
	 */
	public MBFunctionalTransferMessage(String text)
	{
		this.text = text;
	}

	/**
	 * @return Текст сообщения, отображаемый клиенту. Заключен в блок CDATA
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
