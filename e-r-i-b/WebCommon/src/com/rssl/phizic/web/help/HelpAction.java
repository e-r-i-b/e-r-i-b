package com.rssl.phizic.web.help;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 17.12.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class HelpAction extends OperationalActionBase
{

	private static final String HELP_MAPPING_FILE_NAME = "helpMapping.xml";
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, final HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Document helpMapping = XmlHelper.loadDocumentFromResource(HELP_MAPPING_FILE_NAME);
		final String helpId = request.getParameter("id");

		String system = getSystemName();
		String destpath = null;
		Document mappings = XmlHelper.extractPart(helpMapping, "help-mapping[@system='" + system + "']");
		if (mappings != null)
		{
			Element root = mappings.getDocumentElement();
			if (helpId != null)
			{

				Element elem = XmlHelper.selectSingleNode(root, "help-entity/from[. = '" + helpId + "']");

				if (elem != null)
				{
					destpath = XmlHelper.getSimpleElementValue((Element) elem.getParentNode(), "to");
				}
				if(destpath == null)
				{
					NodeList nodes = XmlHelper.selectNodeList(root, "help-entity/from");
					for(int i=0; i < nodes.getLength();i++)
					{
						Element node = (Element)nodes.item(i);
						if(node.getTextContent().contains("*"))
						{
							String nodeValue = node.getTextContent();
							String path = node.getTextContent().substring(0, node.getTextContent().indexOf("*"));
							if(!helpId.contains(path))
							    continue;
							if(!helpId.contains(nodeValue.substring(nodeValue.indexOf("*")+1, nodeValue.length())))
								continue;
							destpath = XmlHelper.getSimpleElementValue((Element) node.getParentNode(), "to");
						}
					}
				}
				if (destpath == null)
					destpath = XmlHelper.getSimpleElementValue(root, "main-entity");

				if (destpath.indexOf("#") != -1)
				{
					request.setAttribute("$$sharp", destpath.substring(destpath.indexOf("#")));
					destpath = destpath.substring(0, destpath.indexOf("#"));
				}
			}
			else
				destpath = XmlHelper.getSimpleElementValue(root, "main-entity");

			addLogParameters(new SimpleLogParametersReader("Открываемый файл помощи", destpath));

			request.setAttribute("$$helpLink", destpath);
//		return new ActionForward("/WEB-INF/jsp/common/help/" + destpath, false);
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	protected abstract String getSystemName();
}
