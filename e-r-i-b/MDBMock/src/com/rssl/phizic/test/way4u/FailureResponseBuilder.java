package com.rssl.phizic.test.way4u;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Random;

/**
 * @author krenev
 * @ created 03.10.2013
 * @ $Author$
 * @ $Revision$
 */

class FailureResponseBuilder extends ResponseBuilderBase
{
	//Иногда вей, подлец, присылает 0 и пустое тело.
	private static final String[] ERROR_CODES = {"0", "1", "-100", "-302", "14", "15", "25", "56", "78", "1900", "2200", "2400", "2620", "2600", "2610"};
	private final static FailureResponseBuilder instance = new FailureResponseBuilder();

	static FailureResponseBuilder getInstance()
	{
		return instance;
	}

	String build(Document document) throws Exception
	{
		Element root = document.getDocumentElement();
		setResponseCodes(root, ERROR_CODES[new Random().nextInt(ERROR_CODES.length)]);

		return XmlHelper.convertDomToText(root);
	}
}
