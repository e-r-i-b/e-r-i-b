package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.util.Collections;

/**
 * @author krenev
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 * схема автоплатежа по выставленному счету
 */
public class InvoiceAutoPayScheme extends AutoPaySchemeBase
{
	public String getParametersByXml()
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(ENTITY, Collections.singletonMap("key", "INVOICE"));
		builder.append(fieldBuilder(CLIENT_HINT, getClientHint()));
		builder.closeEntityTag(ENTITY);
		return builder.toString();
	}
}
