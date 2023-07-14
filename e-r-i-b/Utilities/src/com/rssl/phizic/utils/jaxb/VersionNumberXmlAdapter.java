package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-Конвертация VersionNumber в/из xsd:string
 */
public class VersionNumberXmlAdapter extends XmlAdapter<String, VersionNumber>
{
	@Override
	public VersionNumber unmarshal(String versionNumberAsString) throws MalformedVersionFormatException
	{
		return VersionNumber.fromString(versionNumberAsString);
	}

	@Override
	public String marshal(VersionNumber versionNumber)
	{
		return versionNumber.toString();
	}
}
