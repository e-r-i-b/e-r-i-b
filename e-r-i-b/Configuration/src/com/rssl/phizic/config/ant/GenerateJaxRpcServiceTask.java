package com.rssl.phizic.config.ant;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * ���-���� ��� ��������� � ������� JaxRpc �������� �� wsdl
 * �������� �� ���� ���������:
 *	   name                 ��������
 *	   server               ������������ ��������� ����� (true/false)
 *	   client               ������������ ���������� ����� (true/false)
 *	   wsdlLocation         ���� �� �������� wsdl �����
 *	   appRoot              src ������
 *	   endpoint             ���� �� jaxrpc-ri-runtime.xml ������
 *	   packageName          ��� ������
 *	   interfaceName        ���������
 *	   interfaceServantName ���������� ���������� ����������
 *	   nonClassDir          ���� �� ����������, ���� ��������� wsdl
 *	   base                 ���� �� ���������� � ������� ����� ���������(class �����, �������� �������)
 *	   configPath           ���������������� ���� wscompile
 *
 * �� ������ - ��� ��������������� ����� �������
 *
 * @author Puzikov
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public class GenerateJaxRpcServiceTask extends GenerateJaxRpcTask
{
	private String wsdlLocation;
	private String endpoint;
	private String appRoot;

	@Override
	public void execute() throws BuildException
	{
		if (StringUtils.isEmpty(wsdlLocation))
			throw new BuildException("wsdlLocation �� ����� ���� ������");
		if (StringUtils.isEmpty(endpoint) && getServer())
			throw new BuildException("endpoint �� ����� ���� ������");
		if (StringUtils.isEmpty(appRoot))
			throw new BuildException("appRoot �� ����� ���� ������");
		
		//1. ������������� �������
		super.execute();
		//2. �������� ����� ������ �� endpoint, ���� ������ ����� ������
		writeEndpoint();
	}

	/**
	 * ��������� ������ � �������� ������. �������� ��� ��������� ��������� �����
	 * �������. ��������� ��� ���������� ������ � ������� �����.
	 * @throws BuildException
	 */
	private void writeEndpoint() throws BuildException
	{
		try
		{
			//1. � ������ ��������� ���������� ����� �� ���������
			if (getClient())
				return;

			File endpointFile = new File(endpoint);
			String content = FileUtils.readFileToString(endpointFile);
			Document document = XmlHelper.parse(content);

			String xPath = String.format("endpoint[@name='%s']", getName());
			NodeList existedEndpoint = XmlHelper.selectNodeList(document.getDocumentElement(), xPath);
			//2. ���� ������ ����������, �� ���������
			if (existedEndpoint.getLength() != 0)
				return;

			//3. ����� �������� ����� ������
			Element endpointElement = generateEndpoint(document);
			document.getDocumentElement().appendChild(endpointElement);
			saveXml(document, endpoint);
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}

	/**
	 * ������������ xml ��� endpoint ����
	    <endpoint
	        implementation="com.rssl.phizic.web.gate.test.TestJaxRpcServiceImpl"
	        interface="com.rssl.phizic.web.gate.test.generated.TestJaxRpcService"
	        model="/WEB-INF/TestJaxRpcServiceImpl.model.xml"
	        name="TestJaxRpcServiceImpl"
	        port="{com.rssl.phizic.web.gate.test.generated}TestJaxRpcService"
	        service="{com.rssl.phizic.web.gate.test.generated}TestJaxRpcServiceImpl"
	        tie="com.rssl.phizic.web.gate.test.generated.TestJaxRpcService_Tie"
	        urlpattern="/services/TestJaxRpcServiceImpl"
	        wsdl="/WEB-INF/TestJaxRpcServiceImpl.wsdl"/></endpoints>
	 * @param document ������������ ��������
	 * @return xml ������� ��� endpoint
	 */
	private Element generateEndpoint(Document document)
	{
		String interfaceNameTrimmed = StringUtils.substringAfterLast(getInterfaceName(), ".");

		Element result = document.createElement("endpoint");
		result.setAttribute("name", getName());
		result.setAttribute("interface", getInterfaceName());
		result.setAttribute("implementation", getInterfaceServantName());
		result.setAttribute("tie", getInterfaceName() + "_Tie");
		result.setAttribute("model", "/WEB-INF/" + getName() + ".model.xml");
		result.setAttribute("wsdl", "/WEB-INF/" + getName() + ".wsdl");
		result.setAttribute("service", "{" + getPackageName() + "}" + getName());
		result.setAttribute("port", "{" + getPackageName() + "}" + interfaceNameTrimmed);
		result.setAttribute("urlpattern", "/services/" + getName());
		return result;
	}

	/**
	 * ������� ���������������� xml ��� wscomplile ����:
		 <configuration
		 <wsdl
		    location="C:\work\v1_18/WebGate/web/WEB-INF/TestJaxRpcServiceImpl.wsdl"
		    packageName="com.rssl.phizic.web.gate.test.generated" />
		 </configuration>*
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

			Element wsdl = document.createElement("wsdl");
			wsdl.setAttribute("location", wsdlLocation);
			wsdl.setAttribute("packageName", getPackageName());
			configuration.appendChild(wsdl);

			saveXml(document, configPath);
			return tmpFile;
		}
		catch (ParserConfigurationException e)
		{
			throw new BuildException(e);
		}
		catch (DOMException e)
		{
			throw new BuildException(e);
		}
	}


	@Override
	protected void initWscompile(File xmlFile)
	{
		File sourceBase = new File(appRoot);
		super.setSourceBase(sourceBase);
		super.setFeatures("searchschema");
		super.setConfig(xmlFile);
		super.setKeep(true);
	}

	public String getWsdlLocation()
	{
		return wsdlLocation;
	}

	public void setWsdlLocation(String wsdlLocation)
	{
		this.wsdlLocation = wsdlLocation;
	}

	public String getEndpoint()
	{
		return endpoint;
	}

	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}

	public String getAppRoot()
	{
		return appRoot;
	}

	public void setAppRoot(String appRoot)
	{
		this.appRoot = appRoot;
	}
}
