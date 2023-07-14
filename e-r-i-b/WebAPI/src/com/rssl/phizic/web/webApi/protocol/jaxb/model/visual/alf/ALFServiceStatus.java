package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.XMLCalendarDateAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * —татус сервиса анализ личных финансов
 * @author Jatsky
 * @ created 12.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "lastModified"})
@XmlRootElement(name = "alfServiceStatus")
public class ALFServiceStatus
{
	private ALFStatus status;
	private Calendar lastModified;

	@XmlElement(name = "status", required = true)
	public ALFStatus getStatus()
	{
		return status;
	}

	public void setStatus(ALFStatus status)
	{
		this.status = status;
	}

	@XmlJavaTypeAdapter(XMLCalendarDateAdapter.class)
	@XmlElement(name = "lastModified", required = false)
	public Calendar getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(Calendar lastModified)
	{
		this.lastModified = lastModified;
	}
}
