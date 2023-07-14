package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.ALFServiceStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Базовый класс для ответа, содержащего статус сервиса АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlTransient
public abstract class ALFStatusBaseResponse extends Response
{
	private ALFServiceStatus alfServiceStatus;

	@XmlElement(name = "alfServiceStatus", required = false)
	public ALFServiceStatus getAlfServiceStatus()
	{
		return alfServiceStatus;
	}

	public void setAlfServiceStatus(ALFServiceStatus alfServiceStatus)
	{
		this.alfServiceStatus = alfServiceStatus;
	}
}
