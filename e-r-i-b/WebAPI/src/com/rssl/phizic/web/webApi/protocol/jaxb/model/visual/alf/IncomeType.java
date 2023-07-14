package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Тип дохода
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlEnum
public enum IncomeType
{
	@XmlEnumValue(value = "income")
	income,
	@XmlEnumValue(value = "outcome")
	outcome;
}
