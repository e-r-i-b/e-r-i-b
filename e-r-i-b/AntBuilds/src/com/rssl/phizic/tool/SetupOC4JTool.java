package com.rssl.phizic.tool;

import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xpath.internal.XPathAPI;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.Project.MSG_WARN;

/**
 * @author Erkin
 * @ created 18.03.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Инструмент настройки OC4J.
 * Выполняет следующие задачи:
 *  - прописывает приложения ИКФЛ
 *    в ${ikfl.build.oc4j.home}/j2ee/home/config/server.xml
 *  - прописывает веб-модули для каждого приложения ИКФЛ
 *    в ${ikfl.build.oc4j.home}/j2ee/home/config/default-web-site.xml
 *  - прописывает jms-очереди для каждого приложения ИКФЛ
 *    в ${ikfl.build.oc4j.home}/j2ee/home/config/jms.xml
 */
public class SetupOC4JTool extends Task
{
	private static final String PROPERTY_OC4J_ENABLED = "appserver.type.oracle";
	private static final String PROPERTY_OC4J_HOME = "PHIZIC_OC4J_HOME";
	private static final String PROPERTY_PROJECT_ROOT = "project.root";
	private static final String PROPERTY_CURRENT_CONFIG_NAME = "current.config.name";
	private static final String QUEUETYPE = "javax.jms.Queue";
	private static final String TOPICTYPE = "javax.jms.Topic";
	private static final String TOPICCFTYPE = "javax.jms.TopicConnectionFactory";

	/**
	 * Перечень стандартных приложений OC4J
	 * (Их нельзя удалять из server.xml и default-web-site.xml)
	 */
	private static final Set<String> OC4J_STANDARD_APPLICATIONS
			= new HashSet<String>();

	/**
	 * Стандартные очереди(их location), которые нельзя удалять из jms.xml
	 * jms/demo* добавлены в список для красоты в файле jms.xml(чтобы топики и очереди не сливались)
	 */
	private static final Set<String> OC4J_STANDARD_QUEUES
			= new HashSet<String>();

	/**
	 * Стандартные топики(их location), которые нельзя удалять из jms.xml
	 * jms/demo* добавлены в список для красоты в файле jms.xml(чтобы топики и очереди не сливались)
	 */
	private static final Set<String> OC4J_STANDARD_TOPICS
			= new HashSet<String>();

	static
	{
		OC4J_STANDARD_APPLICATIONS.add("system");
		OC4J_STANDARD_APPLICATIONS.add("default");
		OC4J_STANDARD_APPLICATIONS.add("javasso");
		OC4J_STANDARD_APPLICATIONS.add("ascontrol");

		OC4J_STANDARD_QUEUES.add("jms/demoQueue");
		OC4J_STANDARD_QUEUES.add("jms/RAExceptionQueue");

		OC4J_STANDARD_TOPICS.add("jms/demoTopic");
	}

	private static final DocumentBuilderFactory documentBuilderFactory
			= DocumentBuilderFactory.newInstance();

	static {
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
	}

	private static final TransformerFactory transformerFactory
			= TransformerFactory.newInstance();

	/**
	 * корень проекта
	 */
	private File projectRoot = null;

	/**
	 * директория настроек конфигурации проекта (Settings/configs/${конфигурация})
	 */
	private File   projectConfigSettings = null;

	private String configSettingsPath    = null;

	/**
	 * директория настроек OC4j
	 */
	private File oc4jConfig = null;

	/**
	 * список приложений
	 */
	private List<String> applications = null;

	private String       apps = null;
	/**
	 * коллекция веб-модулей для каждого приложения
	 */
	private Map<String, List<WebApp>> webmodules = null;

	private String ejbConfigPaths = null;

	private Map<String, String> queues;
	private Map<String, String> queueCFs;
	private Map<String, String> topicCFs;
	private Map<String, String> topics;

	public void execute() throws BuildException
	{
		// 0. Check Oracle iAS is enabled
		if (!checkOC4JEnabled())
			return;

		// 1. Collect valuable properties
		setupVariables();

		// 2. Update server.xml
		updateServerXml();

		// 3. Update default-web-site.xml
		updateDefaultWebSiteXml();

		// 4. Update jms.xml
		updateJMSXml();
	}

	private boolean checkOC4JEnabled()
	{
		boolean oc4jEnabled = Boolean.parseBoolean(getProject().getProperty(PROPERTY_OC4J_ENABLED));
		if (!oc4jEnabled) {
			log("Настройка OC4J отключена (см. переменную " + PROPERTY_OC4J_ENABLED + ")", MSG_INFO);
			return false;
		}

		String oc4jHome = getProject().getProperty(PROPERTY_OC4J_HOME);
		if (StringUtils.isEmpty(oc4jHome)) {
			log("Настройка OC4J отключена (не указан путь к OC4J, см. переменную " + PROPERTY_OC4J_HOME + ")", MSG_INFO);
			return false;
		}

		log("Настройка OC4J включена (см. переменную " + PROPERTY_OC4J_ENABLED + ")", MSG_INFO);
		return true;
	}

	private void setupVariables() throws BuildException
	{
		String projectConfig = demandProperty(PROPERTY_CURRENT_CONFIG_NAME);

		projectRoot = demandPathProperty(PROPERTY_PROJECT_ROOT);

		if (StringHelper.isEmpty(configSettingsPath))
		{
			configSettingsPath = demandPathProperty("settings.dir") + "/configs/" + projectConfig;
		}
		projectConfigSettings = new File(configSettingsPath);

		log("Директория настроек конфигурации: " + projectConfigSettings.getAbsolutePath(), MSG_INFO);
		if (!projectConfigSettings.exists())
			throw new BuildException("Не найдена директория настроек конфигурации");

		File oc4jHome = demandPathProperty(PROPERTY_OC4J_HOME);
		log("Директория OC4J: " + oc4jHome.getAbsolutePath() +
				" (см. переменную " + PROPERTY_OC4J_HOME + ")", MSG_INFO);

		oc4jConfig = new File(oc4jHome, "j2ee/home/config");
		log("Директория настроек OC4J: " + oc4jConfig.getAbsolutePath(), MSG_INFO);
		if (!oc4jConfig.exists())
			throw new BuildException("Не найдена директория настроек OC4J");

		readApplications();
		log("OC4J будет настроен на работу со следующими приложениями: " + applications, MSG_INFO);

		readWebmodules();
		if (webmodules.isEmpty()) {
			log("Не найдено ни одного веб-модуля для OC4J", MSG_WARN);
		}
	}

	private void readApplications() throws BuildException
	{
		String appsAsString = StringHelper.isEmpty(apps) ? demandProperty("ikfl.build.app"): apps;
		applications = parseCommaSeparatedList(appsAsString);

		MakefileLoader loader = new MakefileLoader();
		loader.setProjectRoot(projectRoot);
		Makefile makefile = loader.loadMakefile();
		applications.addAll(makefile.getApplications());
	}

	private void readWebmodules() throws BuildException
	{
		webmodules = new HashMap<String, List<WebApp>>(applications.size());

		for (String app : applications) {
			// 1. Define path to application.xml
			File appXmlDirectory = null;
			if (app.equalsIgnoreCase("PhizIC") || app.equalsIgnoreCase("mobile"))
				appXmlDirectory = projectConfigSettings;
			else appXmlDirectory = new File(projectConfigSettings, app);
			File appXmlFile = new File(appXmlDirectory, "application.xml");

			// 2. Load and handle application.xml
			Document appXml = loadXml(appXmlFile);
			NodeList webappNodes = selectNodeList(appXml, "application/module/web");
			List<WebApp> webapps = new ArrayList<WebApp>(webappNodes.getLength());
			for (int i=0; i<webappNodes.getLength(); i++) {
				// 2.1 Lookup for webapp name and root
				Node webappNode = webappNodes.item(i);

				String webappName = getNodeText(webappNode, "web-uri");
				webappName = StringUtils.removeEnd(webappName, ".war");
				if (StringUtils.isEmpty(webappName))
					throw new IllegalArgumentException("webapp name cannot be null nor empty");

				String webappRoot = getNodeText(webappNode, "context-root");
				if (StringUtils.isEmpty(webappRoot))
					throw new IllegalArgumentException("webapp root cannot be null nor empty");

				webapps.add(new WebApp(webappName, webappRoot));
			}
			webmodules.put(app, webapps);
		}
	}

	private void updateServerXml() throws BuildException
	{
		File xmlFile = new File(oc4jConfig, "server.xml");

		// 1. Load server.xml
		Document xmlDocument = loadXml(xmlFile);
		Node serverNode = selectSingleNode(xmlDocument, "application-server");

		// 2. Remove all old applications except standart
		NodeList appNodes = selectNodeList(serverNode, "application");
		Node lastAppNode = null;
		for (int i=0; i<appNodes.getLength(); i++) {
			Element appElement = (Element) appNodes.item(i);
			String appName = appElement.getAttribute("name");
			if (OC4J_STANDARD_APPLICATIONS.contains(appName)) {
				lastAppNode = appElement;
				continue;
			}
			appElement.getParentNode().removeChild(appElement);
		}
		removeNodeBlankLines(serverNode, true);

		// 3. Add new applications
		Node insertRefNode = (lastAppNode != null) ? lastAppNode.getNextSibling() : null;
		for (String app : applications) {
			Element appElement = xmlDocument.createElement("application");
			appElement.setAttribute("name", app);
			appElement.setAttribute("path", formatPath(new File(projectRoot, app + ".ear")));
			appElement.setAttribute("start", "true");
			serverNode.insertBefore(appElement, insertRefNode);
		}

		// 4. Save server.xml
		saveXml(xmlDocument, xmlFile);
	}

	private void updateDefaultWebSiteXml() throws BuildException
	{
		File xmlFile = new File(oc4jConfig, "default-web-site.xml");

		// 1. Load default-web-site.xml
		Document xmlDocument = loadXml(xmlFile);
		Node websiteNode = selectSingleNode(xmlDocument, "web-site");

		// 2. Remove old webapps except standart
		NodeList webappNodes = selectNodeList(websiteNode, "web-app");
		Node lastWebappNode = null;
		for (int i=0; i<webappNodes.getLength(); i++) {
			Element webappElement = (Element) webappNodes.item(i);
			String appName = webappElement.getAttribute("application");
			if (OC4J_STANDARD_APPLICATIONS.contains(appName)) {
				lastWebappNode = webappElement;
				continue;
			}
			webappElement.getParentNode().removeChild(webappElement);
		}
		removeNodeBlankLines(websiteNode, true);

		// 3. Add new webapps
		Node insertRefNode = (lastWebappNode != null) ? lastWebappNode.getNextSibling() : null;
		for (Map.Entry<String, List<WebApp>> webappEntry : webmodules.entrySet()) {
			String app = webappEntry.getKey();
			List<WebApp> webapps = webappEntry.getValue();

			for (WebApp webapp : webapps) {
				Element webappElement = xmlDocument.createElement("web-app");
				webappElement.setAttribute("application", app);
				webappElement.setAttribute("name", webapp.getName());
				webappElement.setAttribute("root", webapp.getRoot());
				webappElement.setAttribute("load-on-startup", "true");
				websiteNode.insertBefore(webappElement, insertRefNode);
			}
		}

		// 4. Save default-web-site.xml
		saveXml(xmlDocument, xmlFile);
	}

	/**
	 * Обновление jms.xml
	 * @throws BuildException
	 */
	private void updateJMSXml() throws BuildException
	{
		queues = new HashMap<String, String>();
		queueCFs = new HashMap<String, String>();
		topics = new HashMap<String, String>();
		topicCFs = new HashMap<String, String>();

		readQueuesFromProps();
		readEjbConfigs();

		File xmlFile = new File(oc4jConfig, "jms.xml");

		// 1. Открываем jms.xml
		Document xmlDocument = loadXml(xmlFile);
		Node jmsNode = selectSingleNode(xmlDocument, "jms");
		Node jmsServerNode = selectSingleNode(jmsNode, "jms-server");

		// 2. Удаляем все старые очереди, топики и фабрики кроме стандартных(определены константами)
		NodeList queueNodes = selectNodeList(jmsServerNode, "queue");
		NodeList queueCFNodes = selectNodeList(jmsServerNode, "queue-connection-factory");
		NodeList topicNodes = selectNodeList(jmsServerNode, "topic");
		NodeList topicCFNodes = selectNodeList(jmsServerNode, "topic-connection-factory");

		Node lastQueueNode = null;
		for (int i = 0; i < queueNodes.getLength(); ++i)
		{
			Element queueElement = (Element) queueNodes.item(i);
			String queueName = queueElement.getAttribute("location");
			if (OC4J_STANDARD_QUEUES.contains(queueName))
			{
				lastQueueNode = queueElement;
				continue;
			}
			queueElement.getParentNode().removeChild(queueElement);
		}

		for (int i = 0; i < queueCFNodes.getLength(); ++i)
		{
			Element queueCFElement = (Element) queueCFNodes.item(i);
			queueCFElement.getParentNode().removeChild(queueCFElement);
		}

		Node lastTopicNode = null;
		for (int i = 0; i < topicNodes.getLength(); ++i)
		{
			Element topicElement = (Element) topicNodes.item(i);
			String topicName = topicElement.getAttribute("location");
			if (OC4J_STANDARD_TOPICS.contains(topicName))
			{
				lastTopicNode = topicElement;
				continue;
			}
			topicElement.getParentNode().removeChild(topicElement);
		}

		for (int i = 0; i < topicCFNodes.getLength(); ++i)
		{
			Element topicCFElement = (Element) topicCFNodes.item(i);
			topicCFElement.getParentNode().removeChild(topicCFElement);
		}

		removeNodeBlankLines(jmsServerNode, true);

		// 3. Добавляем новые очереди, топики и фабрики
		Node insertQueueRefNode = (lastQueueNode != null) ? lastQueueNode.getNextSibling() : null;
		Node insertTopicRefNode = (lastTopicNode != null) ? lastTopicNode.getNextSibling() : null;

		for (String queueCF : queueCFs.keySet())
		{
			Element queueCFElement = xmlDocument.createElement("queue-connection-factory");
			queueCFElement.setAttribute("location", queueCF);
			queueCFElement.setAttribute("name", queueCFs.get(queueCF));
			jmsServerNode.insertBefore(queueCFElement, insertQueueRefNode);
		}

		for (String queue : queues.keySet())
		{
			Element queueElement = xmlDocument.createElement("queue");
			queueElement.setAttribute("location", queue);
			queueElement.setAttribute("name", queues.get(queue));
			jmsServerNode.insertBefore(queueElement, insertQueueRefNode);
		}

		for (String topicCF : topicCFs.keySet())
		{
			Element topicCFElement = xmlDocument.createElement("topic-connection-factory");
			topicCFElement.setAttribute("location", topicCF);
			topicCFElement.setAttribute("name", topicCFs.get(topicCF));
			jmsServerNode.insertBefore(topicCFElement, insertTopicRefNode);
		}

		for (String topic : topics.keySet())
		{
			Element topicElement = xmlDocument.createElement("topic");
			topicElement.setAttribute("location", topic);
			topicElement.setAttribute("name", topics.get(topic));
			jmsServerNode.insertBefore(topicElement, insertTopicRefNode);
		}

		// 4. Сохраняем jms.xml
		saveXml(xmlDocument, xmlFile);
	}

	private String demandProperty(String propertyName) throws BuildException
	{
		String property = getProject().getProperty(propertyName);
		if (StringUtils.isEmpty(property)) {
			throw new BuildException("Не удалось настроить OC4J на работу с системой: " +
					"не определено значение свойства " + propertyName + ". " +
					"Проверьте файл buildIKFL.properties"
			);
		}
		return property;
	}

	private File demandPathProperty(String propertyName) throws BuildException
	{
		return new File(demandProperty(propertyName));
	}

	private static Document loadXml(File file) throws BuildException
	{
		if (file == null)
			throw new NullPointerException("Argument <file> cannot be null");
		if (!file.exists())
			throw new BuildException("Не найден файл " + file.getAbsolutePath());

		try {
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			builder.setEntityResolver(new EntityResolver()
			{
				// avoid dtd download
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException
				{
					if (systemId.endsWith(".dtd"))
						return new InputSource(new StringReader(""));
					else return null;
				}
			});
			return builder.parse(file);
		} catch (IOException ex) {
			throw new BuildException(ex);
		} catch (SAXException ex) {
			throw new BuildException(ex);
		} catch (ParserConfigurationException ex) {
			throw new BuildException(ex);
		}
	}

	private static void saveXml(Document document, File file) throws BuildException
	{
		if (document == null)
			throw new NullPointerException("Argument <document> cannot be null");
		if (file == null)
			throw new NullPointerException("Argument <file> cannot be null");

		try {
			Source domSource = new DOMSource(document);
			Result fileResult = new StreamResult(file);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(domSource, fileResult);
		} catch (TransformerException ex) {
			throw new BuildException(ex);
		}
	}

	private static Node selectSingleNode(Node contextNode, String xpath) throws BuildException
	{
		try {
			return XPathAPI.selectSingleNode(contextNode, xpath);
		} catch (TransformerException ex) {
			throw new BuildException(ex);
		}
	}

	private static NodeList selectNodeList(Node contextNode, String xpath) throws BuildException
	{
		try {
			return XPathAPI.selectNodeList(contextNode, xpath);
		} catch (TransformerException ex) {
			throw new BuildException(ex);
		}
	}

	private static String getNodeText(Node contextNode, String xpath) throws BuildException
	{
		Node node = selectSingleNode(contextNode, xpath);
		if (node == null)
			return null;
		else return node.getTextContent();
	}

	/**
	 * Удаляет пустые (текстовые) строки в заданной XML-ноде
	 * @param contextNode - XML-нода, внутри которой нужно потереть пустые строки
	 * @param recursive - удалять пустые строки рекурсивно
	 */
	private static void removeNodeBlankLines(Node contextNode, boolean recursive)
	{
		NodeList children = contextNode.getChildNodes();
		/**
		 * Алгоритм: из последовательности пустых текстовых нод оставляем только одну
		 * TODO: обработать случай, когда текстовая нода содержит несколько пустых строк  
		 */
		boolean lastNodeIsBlank = false;
		for (int i = children.getLength()-1; i>=0; i--) {
			Node child = children.item(i);
			switch (child.getNodeType()) {
				case Node.ELEMENT_NODE :
					if (recursive)
						removeNodeBlankLines(child, true);
					lastNodeIsBlank = false;
					break;
				case Node.CDATA_SECTION_NODE :
				case Node.TEXT_NODE :
					String nodeValue = child.getNodeValue();
					if (StringUtils.isBlank(nodeValue)) {
						if (lastNodeIsBlank)
							contextNode.removeChild(child);
						else lastNodeIsBlank = true;
					}

					break;
				default :
					lastNodeIsBlank = false;
			}
		}
	}

	private static List<String> parseCommaSeparatedList(String listAsString)
	{
		String[] stringsArray = StringUtils.split(listAsString, ',');
		List<String> stringsList = new ArrayList<String>(stringsArray.length);
		for (String string : stringsArray) {
			string = StringUtils.trimToNull(string);
			if (string != null)
				stringsList.add(string);
		}
		return stringsList;
	}

	private static String formatPath(File file)
	{
		String path = file.getPath();
		path = StringUtils.replaceChars(path, '\\', '/');
		path = StringUtils.stripEnd(path, "/");
		return path;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@SuppressWarnings("UnusedDeclaration")
	public void setApps(String apps)
	{
		this.apps = apps;
	}

	/**
	 * Устанавливает найденные скриптом пути к файлам ejb-jar.xml
	 * @param ejbConfigPaths найденные скриптом пути к файлам ejb-jar.xml
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void setEjbConfigPaths(String ejbConfigPaths)
	{
		this.ejbConfigPaths = ejbConfigPaths;
	}

	@SuppressWarnings("UnusedDeclaration")
	public void setConfigSettingsPath(String configSettingsPath)
	{
		this.configSettingsPath = configSettingsPath;
	}

	/**
	 * Web application description in application.xml
	 */
	private static class WebApp
	{
		private String name;
		private String root;

		private WebApp(String name, String root)
		{
			this.name = name;
			this.root = root;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getRoot()
		{
			return root;
		}

		public void setRoot(String root)
		{
			this.root = root;
		}
	}

	/**
	 * Читает конфигурацию ejb из файлов ejb-jar.xml
	 */
	private void readEjbConfigs()
	{
		if (StringHelper.isNotEmpty(ejbConfigPaths))
		{
			List<String> ejbConfigPathsList = parseCommaSeparatedList(ejbConfigPaths);
			for (String ejbConfigPath : ejbConfigPathsList)
			{
				parseEjbConfigs(ejbConfigPath);
			}
		}
	}

	/**
	 * Парсит файл ejb-jar.xml
	 * @param ejbConfigPath путь к файлу ejb-jar.xml
	 */
	private void parseEjbConfigs(final String ejbConfigPath)
	{
		File xmlFile = new File(ejbConfigPath, "ejb-jar.xml");

		Document xmlDocument = loadXml(xmlFile);
		Node rootNode = selectSingleNode(xmlDocument, "ejb-jar");
		XPath xpath = XPathFactory.newInstance().newXPath();

		NodeList configNodes = ((Element) rootNode).getElementsByTagName("message-driven");
		for (int i = 0; i < configNodes.getLength(); ++i)
		{
			Element configNode = (Element) configNodes.item(i);
			String jmsType = configNode.getElementsByTagName("destination-type").item(0).getTextContent();
			if (jmsType.equals(QUEUETYPE))
			{
				try
				{
					Node queueCF = (Node) xpath.evaluate("resource-ref[res-type='javax.jms.QueueConnectionFactory']", configNode, XPathConstants.NODE);
					String queueCFName = xpath.evaluate("res-ref-name", queueCF);
					if (!queueCFs.containsKey(queueCFName))
						queueCFs.put(queueCFName, queueCF.getAttributes().getNamedItem("id").getTextContent());

					Node queue = (Node) xpath.evaluate("resource-ref[res-type='javax.jms.Queue']", configNode, XPathConstants.NODE);
					String queueName = xpath.evaluate("res-ref-name", queue);
					if (!queues.containsKey(queueName))
						queues.put(queueName, queue.getAttributes().getNamedItem("id").getTextContent());
				}
				catch (XPathExpressionException e)
				{
					throw new RuntimeException(e);
				}
			}
			else if (jmsType.equals(TOPICTYPE))
			{
				NodeList topicRefs = configNode.getElementsByTagName("resource-ref");
				for (int j = 0; j < topicRefs.getLength(); ++j)
				{
					Element topicRef = (Element) topicRefs.item(j);
					String topicName = topicRef.getElementsByTagName("res-ref-name").item(0).getTextContent();
					String topicType = topicRef.getElementsByTagName("res-type").item(0).getTextContent();
					if (topicType.equals(TOPICTYPE))
					{
						if (!topics.containsKey(topicName))
						{
							topics.put(topicName, topicName);
						}
					}
					else if (topicType.equals(TOPICCFTYPE))
					{
						if (!topicCFs.containsKey(topicName))
						{
							topicCFs.put(topicName, topicName);
						}
					}
				}
			}
		}
	}

	/**
	 * Читает очереди, используемые ЕРИБ, из erib-jms-jndi.properties
	 * @throws BuildException
	 */
	private void readQueuesFromProps() throws BuildException
	{
		List<String> queuesProps = parseCommaSeparatedList(demandProperty("erib-jms.queues"));
		List<String> queueCFsProps = parseCommaSeparatedList(demandProperty("erib-jms.queuesCFs"));

		for (String queueCFsProp : queueCFsProps)
		{
			queueCFs.put(queueCFsProp, queueCFsProp);
		}

		for (String queuesProp : queuesProps)
		{
			queues.put(queuesProp, queuesProp);
		}
	}
}
