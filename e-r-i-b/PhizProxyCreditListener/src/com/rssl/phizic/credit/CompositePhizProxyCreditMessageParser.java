package com.rssl.phizic.credit;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 * @author Rtischeva
 * @ created 10.03.15
 * @ $Author$
 * @ $Revision$
 */
public class CompositePhizProxyCreditMessageParser implements XmlMessageParser
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	private final LoanStatusRelease13MessageParser loanStatusRelease13MessageParser = new LoanStatusRelease13MessageParser();
	private final LoanStatusRelease16MessageParser loanStatusRelease16MessageParser = new LoanStatusRelease16MessageParser();
	private final BKIResponseMessageParser bkiResponseMessageParser = new BKIResponseMessageParser();
	private final LoanApplicationRelease19MessageParser loanApplicationRelease19MessageParser = new LoanApplicationRelease19MessageParser();

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		if (!(message instanceof TextMessage))
			throw new IllegalArgumentException("Ожидается текстовое JMS-сообщение");

		log.trace("Получено текстовое JMS-сообщение: " + message);

		List<Exception> parseErrors = new ArrayList<Exception>();

		String text = message.getText();

		try
		{
			return bkiResponseMessageParser.parseMessage(message);
		}
		catch (JAXBException e)
		{
			parseErrors.add(e);
		}

		try
		{
			return loanStatusRelease13MessageParser.parseMessage(message);
		}
		catch (JAXBException e)
		{
			parseErrors.add(e);
		}

		try
		{
			return loanStatusRelease16MessageParser.parseMessage(message);
		}
		catch (JAXBException e)
		{
			parseErrors.add(e);
		}

		try
		{
			return loanApplicationRelease19MessageParser.parseMessage(message);
		}
		catch (JAXBException e)
		{
			parseErrors.add(e);
		}

		for (Exception error : parseErrors)
		{
			log.error(error);
		}
		throw new JAXBException("Неизвестный формат сообщения: " + text);
	}
}
