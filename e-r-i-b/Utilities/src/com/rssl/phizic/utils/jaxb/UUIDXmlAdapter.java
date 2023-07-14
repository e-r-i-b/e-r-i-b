package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.common.types.UUID;

import java.text.ParseException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-Конвертация UUID в/из xsd:string
 */
public class UUIDXmlAdapter extends XmlAdapter<String, UUID>
{
	@Override
	public UUID unmarshal(String uuidAsString) throws ParseException
	{
		return UUID.fromValue(uuidAsString);
	}

	@Override
	public String marshal(UUID uuid)
	{
		return uuid.toString();
	}
}
