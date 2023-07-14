package com.rssl.phizic.config.ant;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * ���-���� ��� ��������� � ������� JaxRpc
 * �������� �� ���� ��������� �������:
 *     classpath
 *	   name                 ��������
 *	   serviceLocation      URL
 *	   packageName          ��� ������ (��������� �����)
 *	   interfaceName        ���������
 *	   interfaceServantName ���������� ���������� ����������
 *	   nonClassDir          ���� �� ����������, ���� ��������� wsdl
 *	   mapping              ������ ���� ����� � �������
 *	   base                 ���� �� ���������� � ������� ����� ���������(class �����, �������� �������)
 *	   configPath           ���������������� ���� wscompile
 *
 * �� ������ - wsdl � model.xml �����
 *
 * @author Puzikov
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public class GenerateJaxRpcWsdlTask extends GenerateJaxRpcTask
{
	private String serviceLocation;

	@Override
	public void execute() throws BuildException
	{
		if (StringUtils.isEmpty(serviceLocation))
			throw new BuildException("serviceLocation �� ����� ���� ������");

		//1. ������������� wsdl
		super.execute();
		//2. �������� URL � ���������� wsdl
		replaceUrl();
	}

	/**
	 * �������� � ���������� wsdl REPLACE_WITH_ACTUAL_URL �� ���������� URL, �������� � ����������
	 * @throws BuildException
	 */
	private void replaceUrl() throws BuildException
	{
		try
		{
			//1. ������������ ���� �� ���������������� wsdl � �������� ��� ����������
			String wsdlPath = getNonClassDir().getPath() + "/" + getName() + ".wsdl";
			File wsdl = new File(wsdlPath);
			String content = FileUtils.readFileToString(wsdl);
			//2. �������� REPLACE_WITH_ACTUAL_URL �� ���������� URL
			content = content.replaceAll("REPLACE_WITH_ACTUAL_URL", serviceLocation);
			//3. ��������� ���������
			FileUtils.writeStringToFile(wsdl, content);
		}
		catch (IOException e)
		{
			throw new BuildException(e);
		}
	}

	/**
	 * ������� ���������������� xml ��� wscomplile ����:
		 <configuration>
			 <service
			    location="http://localhost:8080/services/TestJaxRpcServiceImpl"
			    name="TestJaxRpcServiceImpl"
			    packageName="com.rssl.phizic.web.gate.test"
			    targetNamespace="http://generated.test.gate.web.phizic.rssl.com"
			    typeNamespace="http://generated.test.gate.web.phizic.rssl.com">
			 <interface
			    name="com.rssl.phizic.web.gate.test.generated.TestJaxRpcService"
			    servantName="com.rssl.phizic.web.gate.test.TestJaxRpcServiceImpl" />
			 </service>
		 </configuration>
	 * @param configPath ���� ��� ������� ����
	 * @return ������� xml ����
	 * @throws BuildException
	 */
	@Override
	protected File buildXml(String configPath) throws BuildException
	{
		try
		{
			File tmpFile = new File(configPath);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element configuration = document.createElement("configuration");
			configuration.setAttribute("xmlns", "http://java.sun.com/xml/ns/jax-rpc/ri/config");
			document.appendChild(configuration);

			Element service = document.createElement("service");
			service.setAttribute("name", getName());
			String targetNamespace = generateNamespace(getPackageName());
			service.setAttribute("targetNamespace", targetNamespace);
			service.setAttribute("typeNamespace", targetNamespace);
			service.setAttribute("location", serviceLocation);
			service.setAttribute("packageName", getPackageName());
			configuration.appendChild(service);

			Element anInterface = document.createElement("interface");
			anInterface.setAttribute("name", getInterfaceName());
			anInterface.setAttribute("servantName", getInterfaceServantName());
			service.appendChild(anInterface);

			saveXml(document, configPath);
			return tmpFile;
		}
		catch (ParserConfigurationException e)
		{
			throw new BuildException(e);
		}
	}

	//com.some.thing -> http:/generated.thing.some.com
	private String generateNamespace(String packageName)
	{
		StringBuilder result = new StringBuilder();
		result.append("http://generated.");

		char separator = '.';
		String[] path = StringUtils.split(packageName, separator);
		ArrayUtils.reverse(path);
		result.append(StringUtils.join(path, separator));

		return result.toString();
	}

	@Override
	protected void initWscompile(File xmlFile)
	{
		super.setFeatures("searchschema");
		super.setConfig(xmlFile);
		super.setDefine(true);
	}

	public String getServiceLocation()
	{
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation)
	{
		this.serviceLocation = serviceLocation;
	}
}
