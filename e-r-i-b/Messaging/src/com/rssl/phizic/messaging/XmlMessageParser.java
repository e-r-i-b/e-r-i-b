package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.TextMessage;

import javax.xml.bind.JAXBException;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public interface XmlMessageParser
{
	XmlMessage parseMessage(TextMessage textMessage) throws JAXBException;
}
