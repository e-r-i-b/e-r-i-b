package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Статус вклада
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlEnum
public enum AccountState
{
	OPENED,
	CLOSED,
	ARRESTED,
	LOST_PASSBOOK

}
