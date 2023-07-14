package com.rssl.phizic.config.ant;

import com.sun.xml.rpc.tools.ant.Wscompile;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.w3c.dom.Document;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Базовый таск для генерации сервисов JaxRpc
 *	   name                 название
 *	   packageName          имя пакета
 *	   interfaceName        интерфейс
 *	   interfaceServantName реализация серверного интерфейса
 *	   configPath           конфигурационный файл wscompile
 * @author Puzikov
 * @ created 13.09.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class GenerateJaxRpcTask extends Wscompile
{
	private String configPath;
	private String name;
	private String interfaceName;
	private String interfaceServantName;
	private String packageName;

	@Override
	public void execute() throws BuildException
	{
		if (StringUtils.isEmpty(configPath))
			throw new BuildException("configPath не может быть пустым");
		if (StringUtils.isEmpty(name))
			throw new BuildException("name не может быть пустым");
		if (StringUtils.isEmpty(interfaceName) && getServer())
			throw new BuildException("interfaceName не может быть пустым");
		if (StringUtils.isEmpty(interfaceServantName) && getServer())
			throw new BuildException("interfaceServantName не может быть пустым");
		if (StringUtils.isEmpty(packageName))
			throw new BuildException("packageName не может быть пустым");

		//1. Создать и инициализировать временный xml для инициализации wscompile
		File xmlFile = buildXml(configPath);
		//2. Инициализировать wscompile параметрами
		initWscompile(xmlFile);
		//3. Запустить wscompile
		super.execute();
	}

	/**
	 * Инициализировать wscompile параметрами
	 * @param xmlFile конфигурационный файл
	 */
	protected abstract void initWscompile(File xmlFile);

	/**
	 * Создать конфигурационный xml для wscomplile
	 * @param configPath
	 * @return конфигурационный xml
	 */
	protected abstract File buildXml(String configPath);

	protected void saveXml(Document document, String file) throws BuildException
	{
		try
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		}
		catch (TransformerException e)
		{
			throw new BuildException(e);
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getInterfaceName()
	{
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName)
	{
		this.interfaceName = interfaceName;
	}

	public String getInterfaceServantName()
	{
		return interfaceServantName;
	}

	public void setInterfaceServantName(String interfaceServantName)
	{
		this.interfaceServantName = interfaceServantName;
	}

	public String getConfigPath()
	{
		return configPath;
	}

	public void setConfigPath(String configPath)
	{
		this.configPath = configPath;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
