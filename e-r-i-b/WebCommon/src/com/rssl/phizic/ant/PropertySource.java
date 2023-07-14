package com.rssl.phizic.ant;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.replicator.ReplicaSource;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.struts.config.ModuleConfigFactory;
import org.apache.commons.digester.Digester;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ConfigRuleSet;
import com.rssl.phizic.web.struts.Native2ASCIIStream;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.servlet.ServletException;

/**
 * @author komarov
 * @ created 15.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class PropertySource implements ReplicaSource<Map.Entry<String, ERIBStaticMessage>>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String KEY_SEPARATOR = "|";
	private static final String BAD_BUNDLE_NAME = "org.apache.struts.action.MESSAGE";

	private Map<String, ERIBStaticMessage> propsMessages;

	/**
	 * @param strutsFiles файлы стратс
	 * @param bundle бандл
	 * @throws BusinessException
	 * @throws IOException
	 * @throws SAXException
	 */
	public PropertySource(FileSet strutsFiles, String bundle) throws BusinessException, IOException, SAXException
	{
		propsMessages = createMap(strutsFiles, bundle);
	}

	public Set<Map.Entry<String, ERIBStaticMessage>> iterator()
	{
		return propsMessages.entrySet();
	}

	private Map<String, ERIBStaticMessage> createMap(FileSet strutsFiles, String bundleName) throws BusinessException
	{
		Map<String, ERIBStaticMessage> propertiesMessages = new HashMap<String, ERIBStaticMessage>();
		//noinspection unchecked
		Iterator<FileResource> iterator = strutsFiles.iterator();
		while (iterator.hasNext())
		{
			ModuleConfig config = getConfig(iterator.next());
			for (MessageResourcesConfig messageConfig : config.findMessageResourcesConfigs())
			{
				String bundle = messageConfig.getKey();

				if(skip(bundle, bundleName))
					continue;
				for (String subConfig : messageConfig.getParameter().split(";"))
				{
					String name = subConfig.replace(".", "/").trim();
					InputStream is = new Native2ASCIIStream(getInputStreamFromClassPath(name + ".properties")).getInputStream();
					loadMessages(is, bundle, propertiesMessages);
				}
			}
		}
		return propertiesMessages;
	}

	private boolean skip(String bundle, String bundleName)
	{
		//Стратсовый бандл, не используем
		if(BAD_BUNDLE_NAME.equals(bundle))
			return true;
		//Если задан бандл, то грузим только его
		if(StringHelper.isNotEmpty(bundleName) && !bundleName.equals(bundle))
			return true;
		return false;
	}

	private void loadMessages(InputStream is, String bundle, Map<String, ERIBStaticMessage> propertiesMessages) throws BusinessException
	{
		if(is == null)
			return;

		Properties props = new Properties();
		try
		{
			props.load(is);
			for(Map.Entry entry : props.entrySet())
			{
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if(StringHelper.isNotEmpty(key) && StringHelper.isNotEmpty(value))
				{
					ERIBStaticMessage message = new ERIBStaticMessage();
					message.setBundle(bundle);
					message.setKey(key);
					message.setMessage(value);
					message.setLocaleId(ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId());
					propertiesMessages.put(bundle + KEY_SEPARATOR + key, message);
				}
			}
		}
		catch (IOException e)
		{
			throw new BusinessException("Не удалось загрузить сообщения.", e);
		}
		catch (Exception ex)
		{
			throw new BusinessException("Не удалось загрузить сообщения.", ex);
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				log.error("Не удалось закрыть поток.", e);
			}
		}
	}

	private ModuleConfig getConfig(FileResource fileResource) throws BusinessException
	{
		try
		{
			ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();
			ModuleConfig config = factoryObject.createModuleConfig(null);
			Digester digester = getConfigDigester(config);
			digester.parse(fileResource.getInputStream());
			return config;
		}
		catch (ServletException se)
		{
			throw new BusinessException(se);
		}
		catch (IOException io)
		{
			throw new BusinessException(io);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
	}

	private Digester getConfigDigester(ModuleConfig config) throws ServletException
	{
		Digester configDigester = new Digester();
		configDigester.setNamespaceAware(true);
		configDigester.addRuleSet(new ConfigRuleSet());
		configDigester.push(config);
		return configDigester;
	}

	private InputStream getInputStreamFromClassPath(String name)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = this.getClass().getClassLoader();
		}
		return classLoader.getResourceAsStream(name);
	}
}
