package com.rssl.phizic.service;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.service.generated.JMXBeanDescriptor;
import com.rssl.phizic.service.generated.ParameterDescriptor;
import com.rssl.phizic.service.generated.ServiceDescriptor;
import com.rssl.phizic.service.generated.ServicesType;
import com.rssl.phizic.startup.StartupService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import org.apache.commons.beanutils.BeanUtils;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Omeliyanchuk
 * @ created 12.05.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для запуска сервисов, которые должны быть запущены при старте приложения. Набор сервисов описан в startServices.xml
 */
public class StartupServiceDictionary
{
	private static String DEFAULT_SERVICE_CONFIG_FILE_PATH = "startServices.xml";
	private ServicesType services = null;

	private static final String ERROR_START = "Ошибка при старте сервиса с именем:";
	private static final String ERROR_START_MBEAN = "Ошибка при старте mbean с именем:";
	private static final String ERROR_STOP = "Ошибка при остановке сервиса с именем:";
	private static final String ERROR_STOP_MBEAN = "Ошибка при остановке mbean с именем:";
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";

	private String configFilePath = DEFAULT_SERVICE_CONFIG_FILE_PATH;

	/**
	 * @param configFilePath - путь к файлу с описанием запускаемых/останавливаемых сервисов
	 */
	public void setConfigFilePath(String configFilePath)
	{
		this.configFilePath = configFilePath;
	}

	private void loadConfig() throws ConfigurationException
	{
		if(services!=null)
			return;
		
		try
		{
			ClassLoader classLoader   = Thread.currentThread().getContextClassLoader();
			JAXBContext context       = JAXBContext.newInstance("com.rssl.phizic.service.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			InputStream inputStream  = ResourceHelper.loadResourceAsStream(configFilePath);
			services      = (ServicesType) unmarshaller.unmarshal(inputStream);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Ошибка при загрузке описаний сервисов", e);
		}
	}

	public void startServices() throws ConfigurationException
	{
		doJob(true, true);
	}

	public void stopServices() throws ConfigurationException
	{
		doJob(false, true);
	}

	/**
	 * Запустить mbean'ы. Для старта нужны конфиги.
	 * @throws ConfigurationException
	 */
	public void startMBeans() throws ConfigurationException
	{
		doJob(true, false);
	}

	public void stopMBeans() throws ConfigurationException
	{
		doJob(false, false);
	}

	private void doJob(boolean isStart, boolean isServices)
	{
		loadConfig();

		if(isServices)
		{
			List<ServiceDescriptor> serviceDescriptors = services.getServiceDescriptors();
			for (ServiceDescriptor serviceDescriptor : serviceDescriptors)
			{
				try
				{
					String classname = serviceDescriptor.getClassName();
					Class serviceClass = ClassHelper.loadClass(classname);
					StartupService startService = (StartupService)serviceClass.newInstance();
					List<ParameterDescriptor> paramDescriptors = serviceDescriptor.getParamfieldDescriptors();
					for (ParameterDescriptor paramDescriptor : paramDescriptors)
					{
						List<String> values = paramDescriptor.getValue();
						StringBuilder builder = new StringBuilder();

						int size = values.size();

						for (int i=0; i<size; i++)
						{
							builder.append(values.get(i));
							if(i != size-1)
								builder.append(',');
						}
						BeanUtils.setProperty(startService, paramDescriptor.getName(),builder.toString() );
					}

					if (isStart && !startService.isInitialized())
						startService.start();
					else if (!isStart && startService.isInitialized())
						startService.stop();
				}
				catch (Exception ex)
				{
					StringBuilder builder = new StringBuilder();
					if(isStart)
						builder.append(ERROR_START);
					else
						builder.append(ERROR_STOP);
					builder.append(serviceDescriptor.getClassName());
					builder.append(LEFT_BRACKET);
					builder.append(serviceDescriptor.getDescription());
					builder.append(RIGHT_BRACKET);
					ex.printStackTrace();
					throw new ConfigurationException(builder.toString(),ex);
				}
			}
		}
	}
}
