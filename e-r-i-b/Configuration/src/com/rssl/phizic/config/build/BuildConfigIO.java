package com.rssl.phizic.config.build;

import com.rssl.phizic.config.ConfigurationException;
import static com.rssl.phizic.config.build.BuildConfig.BUILD_CONFIG_JAXB_PACKAGE;
import static com.rssl.phizic.config.build.BuildConfig.BUILD_CONFIG_XML;
import com.rssl.phizic.config.build.generated.BuildConfigElement;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Erkin
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 */

public final class BuildConfigIO
{
	public static BuildConfig readCurrentBuildConfigXml() throws ConfigurationException
	{
		BuildConfigElement buildConfigDescriptor;
		try
		{
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			InputStream buildConfigXmlStream = contextClassLoader.getResourceAsStream(BUILD_CONFIG_XML);
			if (buildConfigXmlStream == null)
				throw new ConfigurationException("Не найден " + BUILD_CONFIG_XML);

			ClassLoader jaxbClassLoader = BuildConfigIO.class.getClassLoader();
			JAXBContext jaxbContext = JAXBContext.newInstance(BUILD_CONFIG_JAXB_PACKAGE, jaxbClassLoader);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			buildConfigDescriptor = (BuildConfigElement) jaxbUnmarshaller.unmarshal(buildConfigXmlStream);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Ошибка при разборе build-config.xml", e);
		}

		return new BuildConfig(buildConfigDescriptor);
	}

	public static BuildConfig readBuildConfigXml(File buildConfigXml) throws ConfigurationException
	{
		BuildConfigElement buildConfigDescriptor;
		try
		{
			ClassLoader jaxbClassLoader = BuildConfigIO.class.getClassLoader();
			JAXBContext jaxbContext = JAXBContext.newInstance(BUILD_CONFIG_JAXB_PACKAGE, jaxbClassLoader);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			buildConfigDescriptor = (BuildConfigElement) jaxbUnmarshaller.unmarshal(buildConfigXml);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Ошибка при разборе build-config.xml", e);
		}

		return new BuildConfig(buildConfigDescriptor);
	}
}
