package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Тэг "card" запроса списка продуктов
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"id", "name", "description", "number", "main", "type", "availableLimit", "state", "virtual", "statusDescExternalCode", "issueDate",
		"expireDate", "office", "additionalCardType", "currency", "mainCardNumber", "primaryAccountNumber", "cardLevel", "cardBonusSign", "showOnMain"})
@XmlRootElement(name = "card")
public class CardTag extends CardCommonTag
{
}
