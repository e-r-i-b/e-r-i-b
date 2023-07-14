package com.rssl.phizic.business.messaging.info;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.messaging.mail.messagemaking.MessagemakingConfig;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 16.03.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class DistributionMessagemakingConfig extends MessagemakingConfig
{
	private static final String DISTRIBUTION_DESCRIPTIONS_FILE_NAME = "distributions.xml";

	private List<String> channels;
	private Map<String, String> addressBuilderByChannel;
	private Map<String, String> messageBuilderByChannel;
	private Map<String, String> resourceLoaderByChannel;

	public DistributionMessagemakingConfig(PropertyReader reader)
	{
		super(reader);
	}

	public String getMessageBuilderClassName(String channel)
	{
		String mb = messageBuilderByChannel.get(channel).trim();
		if (mb == null)
			throw new RuntimeException("Не найден MessageBuilder для канала : " + channel);
		return mb;
	}

	public String getAddressBuilderClassName(String channel)
	{
		String ab = addressBuilderByChannel.get(channel).trim();
		if (ab == null)
			throw new RuntimeException("Не найден AddressBuilder для канала : " + channel);
		return ab;
	}

	public String getResourceLoaderClassName(String channel)
	{
		String rl = resourceLoaderByChannel.get(channel).trim();
		if (rl == null)
			throw new RuntimeException("Не найден ResourceLoader для канала : " + channel);
		return rl;
	}

	public List<String> getAllChannels()
	{
		return Collections.unmodifiableList(channels);
	}

	public void doRefresh() throws ConfigurationException
	{

		channels = new ArrayList<String>();
		addressBuilderByChannel = new HashMap<String, String>();
		messageBuilderByChannel = new HashMap<String, String>();
		resourceLoaderByChannel = new HashMap<String, String>();
		Document document;

		try
		{
			document = XmlHelper.loadDocumentFromResource(DISTRIBUTION_DESCRIPTIONS_FILE_NAME);
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка чтения XML", e);
		}

		Element root = document.getDocumentElement();

		try
		{
			XmlHelper.foreach(root, "channels/channel", new ForeachElementAction()
			{
				public void execute(Element channelElem) throws Exception
				{
					String channelName = channelElem.getAttribute("name");
					Element mbElem = XmlHelper.selectSingleNode(channelElem, "message-builder");
					String messageBuilder = mbElem.getTextContent();
					Element abElem = XmlHelper.selectSingleNode(channelElem, "address-builder");
					String addressBuilder = abElem.getTextContent();
					Element rlElem = XmlHelper.selectSingleNode(channelElem, "resource-loader");
					String resourceLoader = rlElem.getTextContent();

					channels.add(channelName);
					messageBuilderByChannel.put(channelName, messageBuilder);
					addressBuilderByChannel.put(channelName, addressBuilder);
					resourceLoaderByChannel.put(channelName, resourceLoader);
				}
			}
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}