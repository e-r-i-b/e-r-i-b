package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Тэг "account" запроса списка продуктов
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"id", "name", "number", "balance", "state", "description", "rate", "maxSumWrite", "currency", "openDate",
		"creditAllowed", "debitAllowed", "minimumBalance", "office", "demand", "passbook", "showOnMain"})
@XmlRootElement(name = "account")
public class AccountTag extends AccountCommonTag
{
}
