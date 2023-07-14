package com.rssl.phizicgate.mobilebank;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jatsky
 * @ created 04.08.15
 * @ $Author$
 * @ $Revision$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "LINKS")
public class RegistrationInfoPackList
{
	@XmlElement(name = "LINK")
	private List<RegistrationInfoPack> registrationInfoPacks;

	public RegistrationInfoPackList()
	{
	}

	public List<RegistrationInfoPack> getRegistrationInfoPacks()
	{
		return registrationInfoPacks;
	}

	public void setRegistrationInfoPacks(List<RegistrationInfoPack> registrationInfoPacks)
	{
		this.registrationInfoPacks = registrationInfoPacks;
	}
}
