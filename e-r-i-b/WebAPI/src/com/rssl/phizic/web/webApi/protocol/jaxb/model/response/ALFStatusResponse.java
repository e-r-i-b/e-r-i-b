package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ, содержащий статус сервиса АЛФ
 * @author Jatsky
 * @ created 12.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "alfServiceStatus"})
@XmlRootElement(name = "message")
public class ALFStatusResponse extends ALFStatusBaseResponse
{
}
