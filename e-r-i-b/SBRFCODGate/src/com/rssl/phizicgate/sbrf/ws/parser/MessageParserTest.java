package com.rssl.phizicgate.sbrf.ws.parser;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.sbrf.ws.Constants;
import com.rssl.phizgate.ext.sbrf.common.messaging.CODFormatResponseHandler;
import junit.framework.TestCase;

import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: niculichev $
 * @ $Revision: 29218 $
 */
@SuppressWarnings({"JavaDoc"})
public class MessageParserTest extends TestCase
{
    private static final String[] MESSAGE_FILES = new String[]
    {
            "extract.xml",
            "extract1.xml",
            "longOffer.xml",
            "preparedPay.xml",
    };

    private static final String[] ERROR_FILES = new String[]
    {
            "error_a.xml",
            "refusal_t.xml",
    };

    private static final String[] VOID_FILES = new String[]
    {
            "com/rssl/phizicgate/sbrf/ws/mock/xml/acknowledge_t.xml",
    };
    private SAXParserFactory saxParserFactory;
    private ClassLoader      classLoader;
	private MessagingConfig  messagingConfig;
	private MessageInfo mockMessageInfo;

	public void test() throws Exception
    {
	    for (String file : MESSAGE_FILES)
        {
            try
            {
                CODFormatResponseHandler messageHandler = new CODFormatResponseHandler(mockMessageInfo);

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
                CODFormatResponseHandler messageHandler = new CODFormatResponseHandler(mockMessageInfo);

                testError(file, messageHandler);
            }
            catch (Exception e)
            {
                throw new Exception("Ошибка при обработке файла: " + file, e);
            }
        }

        for (String file : VOID_FILES)
        {
            try
            {
                CODFormatResponseHandler messageHandler = new CODFormatResponseHandler(mockMessageInfo);

                testVoid(file, messageHandler);
            }
            catch (Exception e)
            {
                throw new Exception("Ошибка при обработке файла: " + file, e);
            }
        }
    }

    private void testFile(String testFile, CODFormatResponseHandler messageHandler) throws Exception
    {
        messageHandler.reset();

        parseFile(testFile, messageHandler);

        String message = "Парсинг файла: " + testFile;

        assertNotNull(message, messageHandler.getMessageId());
        assertNotNull(message, messageHandler.getMessageDate());
        assertNotNull(message, messageHandler.getFromAbonent());
        assertTrue(message, messageHandler.isSuccess());
        assertFalse(message, messageHandler.isVoid());
        assertNull(message, handleWebBankException(messageHandler));
    }

    private void testError(String testFile, CODFormatResponseHandler handler) throws Exception
    {
        handler.reset();

        parseFile(testFile, handler);

        String message = "Парсинг файла: " + testFile;

        assertNotNull(message, handler.getMessageId());
        assertNotNull(message, handler.getMessageDate());
        assertNotNull(message, handler.getFromAbonent());
        assertFalse(message, handler.isSuccess());
        assertFalse(message, handler.isVoid());
        assertNotNull(message, handleWebBankException(handler));
    }

    private void testVoid(String testFile, CODFormatResponseHandler handler) throws Exception
    {
        handler.reset();

        parseFile(testFile, handler);

        String message = "Парсинг файла: " + testFile;

        assertNotNull(message, handler.getMessageId());
        assertNotNull(message, handler.getMessageDate());
        assertNotNull(message, handler.getFromAbonent());
        assertTrue(message, handler.isSuccess());
        assertTrue(message, handler.isVoid());
        assertNull(message, handleWebBankException(handler));
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

    private void parseFile(String testFile, CODFormatResponseHandler handler) throws Exception
    {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        InputStream stream = classLoader.getResourceAsStream(testFile);
        assertNotNull("Файл не найден: " + testFile, stream);
        System.out.println("Обработка файла: " + testFile);
        saxParser.parse(stream, handler);
    }

    public void testPerfomance() throws Exception
    {
        String testFile = "preparedPay.xml";
        SAXParser saxParser = saxParserFactory.newSAXParser();
        System.out.println("Обработка файла: " + testFile);

        Date start = new Date();
        for(int i = 0; i < 1000; i++)
        {
            InputStream stream = classLoader.getResourceAsStream(testFile);
	        CODFormatResponseHandler handler = new CODFormatResponseHandler(mockMessageInfo);
            saxParser.parse(stream, handler);
        }
        Date end = new Date();

        System.out.println("elapsed " +  (end.getTime() - start.getTime()) + " ms" );
    }

    public void testResponseBodyHandler() throws Exception
    {
        CODFormatResponseHandler messageHandler = new CODFormatResponseHandler(messagingConfig.getMessageInfo("agreementRegistration_q"));
        testFile("wellRegistrationResponse.xml", messageHandler);
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