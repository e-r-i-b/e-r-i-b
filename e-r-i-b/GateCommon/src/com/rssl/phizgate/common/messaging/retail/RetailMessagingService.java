package com.rssl.phizgate.common.messaging.retail;

import com.rssl.api.retail.Retail;
import com.rssl.phizgate.common.messaging.retail.jni.jndi.RetailJNIHelper;
import com.rssl.phizgate.common.messaging.retail.jni.pool.RetailJniPool;
import com.rssl.phizgate.common.messaging.retail.jni.pool.RetailWrapper;
import com.rssl.phizgate.messaging.RetailDocumentValidator;
import com.rssl.phizgate.messaging.RetailMessageData;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.GateMessagingConfigurationException;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

public class RetailMessagingService extends MessagingServiceSupport
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_GATE.toValue());
	
	private RetailJniPool retailFactory = null;
	private static RetailMessagingConfig retailMessagingConfig;
	private static MessagingConfig messageConfig;
	private static final String DEFAULT_PREFIX = "prefix";
	private static final String CLASS_RESPONCE_ERROR = "������ ��� ������������� ������ ��� ������� ���������.";

	static
	{
		retailMessagingConfig = ConfigFactory.getConfig(RetailMessagingConfig.class);
		try
		{
			messageConfig = ConfigurationLoader.load(retailMessagingConfig.getConfigFilePath());
		}
		catch(GateMessagingConfigurationException ex)
		{
			throw new RuntimeException("������ ��� ��������� ������� ��� retail jni",ex);
		}
	}

	public RetailMessagingService(GateFactory factory)  throws GateException
	{
		super(
				factory,
				MessageLogService.getMessageLogWriter(),
				retailMessagingConfig.getSystemName(),
				messageConfig);
		retailFactory = createFactory();
	}

	private static RetailJniPool createFactory() throws GateException
	{
			return RetailJNIHelper.lookupPoolFactory();
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return RetailMessagesCacheManager.getInstance(messageConfig);
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		RetailMessagingService.log.debug("�������� �������� ��������� � ������ � ����:"+Thread.currentThread().getId());
		String prefix = messageInfo.getSubsystem();
		if(prefix==null)
			prefix = DEFAULT_PREFIX;
		
		RetailWrapper wrapper = null;

		try
		{
			RetailMessagingService.log.debug("�������� ����� ��������� retail jni �� ����. ����:"+Thread.currentThread().getId() + 
					"active:"+retailFactory.getNumActive(prefix)+" idle:"+retailFactory.getNumIdle(prefix));

			wrapper = (RetailWrapper)retailFactory.borrowObject(prefix);

			RetailMessagingService.log.debug("�������� ����� ��������� retail jni �� ����. ����:"+Thread.currentThread().getId() +
					"active:"+retailFactory.getNumActive(prefix)+" idle:"+retailFactory.getNumIdle(prefix));

			if(wrapper == null)
				throw new GateException("�� ������ retail");

			byte[] buff = wrapper.getRetail().XML_request((byte[]) messageData.getBody());

			RetailMessageData response = new RetailMessageData();
			response.setBody(buff);
			return response;
		}
		catch (Retail.Failure e)
		{
			throw new GateException(e);
		}
		catch(Exception ex)
		{
			throw new GateException("������ ��� ��������� �������� � retailJni �� ����",ex);
		}
		finally
		{
			try
			{
				if(wrapper != null)
				{
					RetailMessagingService.log.debug("���������� ��������� retail jni �� ����. ����:"+Thread.currentThread().getId() +
					"active:"+retailFactory.getNumActive(prefix)+" idle:"+retailFactory.getNumIdle(prefix));

					retailFactory.returnObject(prefix,wrapper);

					RetailMessagingService.log.debug("���������� ��������� retail jni �� ����. ����:"+Thread.currentThread().getId() + 
					"active:"+retailFactory.getNumActive(prefix)+" idle:"+retailFactory.getNumIdle(prefix));
				}
			}
			catch (Exception ex)
			{
				throw new GateException("������ ��� �������� �������� � retailJni � ���",ex);
			}
			RetailMessagingService.log.debug("��������� �������� ��������� � ������ � ����:"+Thread.currentThread().getId());
		}
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		RetailMessageData retailResponse = (RetailMessageData) response;

		DefaultHandler retailHandler = null;

		try
		{
			Class responceClass = ClassHelper.loadClass(retailMessagingConfig.getResponceHandlerClass());
			retailHandler = (DefaultHandler)responceClass.getConstructor(MessageInfo.class).newInstance(messageInfo);
		}
		catch ( NoSuchMethodException ex)
		{
			throw new RuntimeException(CLASS_RESPONCE_ERROR,ex);
		}
		catch ( ClassNotFoundException ex)
		{
			throw new RuntimeException(CLASS_RESPONCE_ERROR,ex);
		}
		catch ( Exception ex)
		{
			throw new RuntimeException(CLASS_RESPONCE_ERROR,ex);
		}

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;

		try
		{
			saxParser = factory.newSAXParser();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		try
		{
			saxParser.parse(new ByteArrayInputStream((byte[]) retailResponse.getBody()) , retailHandler);

			retailResponse.setId(((ResponseHandler)retailHandler).getMessageId());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		RetailDocumentValidator validator = new RetailDocumentValidator();
		validator.validate((Document)((ResponseHandler)retailHandler).getBody());

		return (ResponseHandler)retailHandler;
	}
}
