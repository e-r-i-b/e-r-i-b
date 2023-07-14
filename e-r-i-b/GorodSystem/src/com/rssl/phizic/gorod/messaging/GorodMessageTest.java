package com.rssl.phizic.gorod.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import junit.framework.TestCase;

import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Gainanov
 * @ created 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodMessageTest extends TestCase
{
	private static final String[] MESSAGE_FILES = new String[]
    {
            "com/rssl/phizicgate/sbrf/payments/systems/gorod/messaging/xml/ansSaveEOrder.xml",
            "com/rssl/phizicgate/sbrf/payments/systems/gorod/messaging/xml/extentsAns.xml",
    };

    private static final String[] ERROR_FILES = new String[]
    {
            "com/rssl/phizicgate/sbrf/payments/systems/gorod/messaging/xml/error.xml",
    };

    private SAXParserFactory saxParserFactory;
    private ClassLoader      classLoader;
	private MessagingConfig messagingConfig;
	private MessageInfo mockMessageInfo;

	public void test() throws Exception
    {
	    for (String file : MESSAGE_FILES)
        {
            try
            {
                GorodResponseHandler messageHandler = new GorodResponseHandler(mockMessageInfo);

                testFile(file, messageHandler);
            }
            catch (Exception e)
            {
                throw new Exception("Ошибка при обработке файла: " + file, e);
            }
        }

        for (String file : ERROR_FILES)
        {
            try
            {
                GorodResponseHandler messageHandler = new GorodResponseHandler(mockMessageInfo);

                testError(file, messageHandler);
            }
            catch (Exception e)
            {
                throw new Exception("Ошибка при обработке файла: " + file, e);
            }
        }
    }

    private void testFile(String testFile, GorodResponseHandler messageHandler) throws Exception
    {
        messageHandler.reset();

        parseFile(testFile, messageHandler);

        String message = "Парсинг файла: " + testFile;

//        assertNotNull(message, messageHandler.getMessageId());
        assertTrue(message, messageHandler.isSuccess());
        assertFalse(message, messageHandler.isVoid());
        assertNull(message, handleWebBankException(messageHandler));
    }

    private void testError(String testFile, GorodResponseHandler handler) throws Exception
    {
        handler.reset();

        parseFile(testFile, handler);

        String message = "Парсинг файла: " + testFile;

//        assertNotNull(message, handler.getMessageId());
        assertFalse(message, handler.isSuccess());
        assertFalse(message, handler.isVoid());
        assertNotNull(message, handleWebBankException(handler));
    }


    private Exception handleWebBankException(ResponseHandler handler)
    {
        Exception exception = null;

        try
        {
            handler.throwException();
        }
        catch (GateMessagingException e)
        {
            exception = e;
        }
        catch (GateMessagingClientException e)
        {
            exception = e;
        }
        catch (GateMessagingValidationException e)
        {
            exception = e;
        }
        catch (GateLogicException e)
        {
	        exception = e;
        }
        catch (GateException e)
        {
	        exception = e;
        }

	    return exception;
    }

    private void parseFile(String testFile, GorodResponseHandler handler) throws Exception
    {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        InputStream stream = classLoader.getResourceAsStream(testFile);
        assertNotNull("Файл не найден: " + testFile, stream);
        System.out.println("Обработка файла: " + testFile);
        saxParser.parse(stream, handler);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        saxParserFactory = SAXParserFactory.newInstance();
        classLoader = Thread.currentThread().getContextClassLoader();
	    messagingConfig = ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG);

	    Collection<MessageInfo> infos = messagingConfig.getAllMessageInfos();
	    Set<String> strings = new HashSet<String>();
	    for (MessageInfo info : infos)
	    {
		    strings.addAll(info.getResponses());
	    }
	    mockMessageInfo = new MessageInfo("all-valid", "foo", strings, null, null, new HashMap<String, String>());
    }

	protected void tearDown() throws Exception
	{
		saxParserFactory = null;
		classLoader = null;
		messagingConfig = null;
		mockMessageInfo = null;
		super.tearDown();
	}
}
