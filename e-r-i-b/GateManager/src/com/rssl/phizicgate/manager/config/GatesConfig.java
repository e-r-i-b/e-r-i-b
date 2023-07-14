package com.rssl.phizicgate.manager.config;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class GatesConfig// implements Config
{
	private static final String CONFIG_FILE_NAME = "gates-configuration.xml";
	private List<GateDescription> descriptors;

	public GatesConfig()
	{
		try
		{
			refresh();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public GateDescription getGateDescription()
	{
		return descriptors.get(0);
	}

	public void refresh() throws ConfigurationException
	{
		try
		{
			Document config = XmlHelper.loadDocumentFromResource(CONFIG_FILE_NAME);
			Element rootElem = config.getDocumentElement();
			descriptors = new ArrayList<GateDescription>();
			readGates(rootElem);
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка чтения конфигурации шлюза", e);
		}
	}

	private void readGates(Element root) throws Exception
	{
		XmlHelper.foreach(root, "gate-configuration", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				GateDescription description = parseGateDescription(element);
				addGateDescriptor(description);
			}
		}
		);
	}

	private GateDescription parseGateDescription(Element element) throws Exception
	{
		final GateDescription gateDescription = new GateDescription();
		XmlHelper.foreach(element, "service", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String interfaceClassName = element.getAttribute("interface");
				String realisationClassName = element.getAttribute("class");
				ServiceDescriptor serviceDescriptor = new ServiceDescriptor(interfaceClassName, realisationClassName);
				//todo параметры;)
				gateDescription.addServiceDescriptor(serviceDescriptor);
			}
		}
		);
		return gateDescription;
	}

	private void addGateDescriptor(GateDescription description)
	{
		descriptors.add(description);
	}
}
