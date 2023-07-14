package com.rssl.phizicgate.sbrf.ws;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: gainanov $
 * @ $Revision: 8019 $
 */

public class MessageGeneratorTest extends TestCase
{
	public void test() throws Exception
	{
		CODMessageGenerator messageGenerator = new CODMessageGenerator();

		Document document = new DocumentImpl();
		Element root = document.createElement("hello_q");
		document.appendChild(root);
		root.appendChild(document.createTextNode("Привет"));

		CODMessageData messageData = messageGenerator.buildMessage(document, null, null);

		assertNotNull( messageData );
	}
}