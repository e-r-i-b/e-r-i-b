package com.rssl.phizic.sms.banking.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.sms.banking.security.UserSendException;

import java.util.Map;
import java.util.HashMap;

/**
 * @author hudyakov
 * @ created 01.11.2008
 * @ $Author$
 * @ $Revision$
 */
class CommandService
{
	public static final String CONFIG_FILE_NAME = "command-config.xml";

	private Map<String, Map<String, String>> parameters;
	private Map<String, Map<String, String>> templates;
	private Map<String, String> exceptions;
	private Map<String, String> commands;
	private Map<String, String> help;
	private String defaultCommand;

	public CommandService()
    {
	   initialize();
	}

	private void initialize()
	{
		try
		{
        	Document config = XmlHelper.loadDocumentFromResource(CONFIG_FILE_NAME);
			Element root = config.getDocumentElement();

			defaultCommand = parseDefaultCommand(root);
			parameters  = parseCompoundData(root, "parameter");
			exceptions = parseExceptions(root);
			templates = parseCompoundData(root, "template");
			commands = parseCommands(root);
			help = parseHelp(root);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected Class<? extends Command> getCommand(String message) throws BusinessException, UserSendException
	{
		String commandClass = commands.get(message);
		try
		{
			if (commandClass == null)
			{
				return ClassHelper.loadClass(defaultCommand);
			}
			return ClassHelper.loadClass(commandClass);
		}
		catch (ClassNotFoundException e)
		{
			throw  new BusinessException(e);
		}
	}

	protected String getHelp(String name)
	{
		return help.get(name);
	}

	protected Map<String, String> getParameters(String name)
	{
		return parameters.get(name);
	}

	protected Map<String, String> getTemplates(String name)
	{
		return templates.get(name);
	}

	protected Map<String, String> getExceptions()
	{
		return exceptions;
	}

	private Map<String, String> parseSinonyms(Element root, final String cAttribute) throws Exception
	{
		final Map<String, String> sinonyms = new HashMap<String, String>();
		XmlHelper.foreach(root, "sinonym", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				sinonyms.put(name, cAttribute);
			}
		});
		return sinonyms;
	}

	private  Map<String , String> parseCommands(final Element root) throws Exception
	{
		final Map<String, String> data = new HashMap<String, String>();

		XmlHelper.foreach(root, "command", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String attribute = element.getAttribute("class");
				Element sElement = XmlHelper.selectSingleNode(element, "sinonyms");
				data.putAll(parseSinonyms(sElement, attribute));
            }
		});
		return data;
	}

	private  Map<String , String> parseHelp(final Element root) throws Exception
	{
		final Map<String, String> data = new HashMap<String, String>();

		XmlHelper.foreach(root, "command", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				Element hElement = XmlHelper.selectSingleNode(element, "hint");
				String attribute = hElement.getFirstChild().getNextSibling().getNodeValue();
				Element sElement = XmlHelper.selectSingleNode(element, "sinonyms");
				data.putAll(parseSinonyms(sElement, attribute));
            }
		});
		return data;
	}

	private Map<String, Map<String, String>> parseCompoundData(final Element root, final String source) throws Exception
	{
 		final Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();

		XmlHelper.foreach(root, "command", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				final Element sElement = element;
				XmlHelper.foreach(element, "sinonyms/sinonym", new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						String name = element.getAttribute("name");
						if (source.equals("parameter"))
							data.put(name, parseParameters(sElement));
						if  (source.equals("template"))
							data.put(name, parseTemplates(sElement));
					}
				});
            }
		});
		return data;
	}

	private Map<String, String> parseParameters(Element root) throws Exception
	{
		final Map<String, String> parameters = new HashMap<String, String>();
		XmlHelper.foreach(root, "parameters/parameter", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				String value = element.getAttribute("value");
				parameters.put(name, value);
			}
		});
		return parameters;
	}

	private Map<String, String> parseTemplates(Element root) throws Exception
	{
		final Map<String, String> templates = new HashMap<String, String>();
		XmlHelper.foreach(root, "templates/template", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				Element vElement = XmlHelper.selectSingleNode(element, "value");
				String value = vElement.getFirstChild().getNextSibling().getNodeValue();
				templates.put(name, value);
			}
		});
		return templates;
	}

	private  Map<String , String> parseExceptions(final Element root) throws Exception
	{
		final Map<String, String> exceptions = new HashMap<String, String>();
		XmlHelper.foreach(root, "exceptions/exception", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				Element vElement = XmlHelper.selectSingleNode(element, "value");
				String value = vElement.getFirstChild().getNextSibling().getNodeValue();
				exceptions.put(name, value);
			}
		});
		return exceptions;
	}

	private String parseDefaultCommand(Element root) throws Exception
	{
		Element element = XmlHelper.selectSingleNode(root, "default-command");

		return element.getAttribute("class");
	}
}

