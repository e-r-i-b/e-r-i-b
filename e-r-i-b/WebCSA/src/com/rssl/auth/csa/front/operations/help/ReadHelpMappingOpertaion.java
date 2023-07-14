package com.rssl.auth.csa.front.operations.help;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;

/**
 * @author akrenev
 * @ created 11.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * операция чтения хелп-маппинга
 */

public class ReadHelpMappingOpertaion
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final String HELP_MAPPING_FILE_NAME = "helpMapping.xml";
	private static final String SYSTEM_NAME = "csa";

	private static final String HELP_MAPPING_NODE_PREFIX = "help-mapping[@system='";
	private static final String NODE_PARAMETER_POSTFIX = "']";
	private static final String NODE_PARAMETER_PREFIX = "[. = '";
	private static final String MAIN_ENTRY_NODE_NAME = "main-entity";
	private static final String HELP_ENTITY_FROM_NODE_NAME = "help-entity/from";
	private static final String TO_NODE_NAME = "to";
	private static final Pattern FULL_PATH_SEPARATE_PATTERN = Pattern.compile("#");

	private String path;
	private String sharp;

	private Document readHelpMapping()
	{
		try
		{
			Document allMappings = XmlHelper.loadDocumentFromResource(HELP_MAPPING_FILE_NAME);
			return  XmlHelper.extractPart(allMappings, HELP_MAPPING_NODE_PREFIX.concat(SYSTEM_NAME).concat(NODE_PARAMETER_POSTFIX));
		}
		catch (Exception e)
		{
			log.error("Не возможно прочитать ".concat(HELP_MAPPING_FILE_NAME).concat("."), e);
			return null;
		}
	}

	private void setMainPath(Element root)
	{
		path = XmlHelper.getSimpleElementValue(root, MAIN_ENTRY_NODE_NAME);
		sharp = StringUtils.EMPTY;
	}

	/**
	 * @param helpId идентификатор маппинга
	 */
	public void initialize(String helpId)
	{
		//читаем маппинг
		Document mappings = readHelpMapping();
		if (mappings == null)
			return;

		//корневой элемент текущего маппинга
		Element root = mappings.getDocumentElement();
		//если не дали идентификатор хелпа, то отправляем на содержание
		if (StringHelper.isEmpty(helpId))
		{
			setMainPath(root);
			return;
		}

		//ищем маппинг для идентификатора
		String currentMappingNodeName = HELP_ENTITY_FROM_NODE_NAME.concat(NODE_PARAMETER_PREFIX).concat(helpId).concat(NODE_PARAMETER_POSTFIX);
		Element currentMappingNode = null;

		try
		{
			currentMappingNode = XmlHelper.selectSingleNode(root, currentMappingNodeName);
		}
		catch (TransformerException e)
		{
			log.error("Невозможно прочитать узел ".concat(currentMappingNodeName).concat(" в ").concat(HELP_MAPPING_FILE_NAME).concat("."), e);
			setMainPath(root);
			return;
		}

		//ничего не нашли возвращаем содержание
		if (currentMappingNode == null)
		{
			setMainPath(root);
			return;
		}

		//тянем полный путь
		String fullPath = XmlHelper.getSimpleElementValue((Element) currentMappingNode.getParentNode(), TO_NODE_NAME);

		String[] pathParts = FULL_PATH_SEPARATE_PATTERN.split(fullPath);
		path = pathParts[0];

		if (pathParts.length == 2)
			sharp = pathParts[1];
	}

	/**
	 * @return путь до хелпа
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @return глава хелпа
	 */
	public String getSharp()
	{
		return sharp;
	}
}
