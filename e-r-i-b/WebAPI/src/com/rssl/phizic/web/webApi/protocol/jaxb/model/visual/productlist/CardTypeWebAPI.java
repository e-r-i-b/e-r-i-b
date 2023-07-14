package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Тип карты
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlEnum
public enum CardTypeWebAPI
{

	debit,
	credit,
	overdraft
}
