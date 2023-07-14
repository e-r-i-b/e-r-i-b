package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pages.Page;
import com.rssl.phizic.business.dictionaries.pages.PageService;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** ���� ��� �������� ����������� �������
 * @author akrenev
 * @ created 30.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class UpdatePagesTask extends SafeTaskBase
{
	private static final PageService service = new PageService();

	private boolean deleteUknownPages;   // ����� �� ������� ����������� ��������
	private String file;                // ��� ����� ����������� �������

	/**
	 * @return ����� �� ������� ����������� ��������
	 */
	public boolean getDeleteUknownPages()
	{
		return deleteUknownPages;
	}

	/**
	 * @param deleteUknownPages ����� �� ������� ����������� ��������
	 */
	public void setDeleteUknownPages(boolean deleteUknownPages)
	{
		this.deleteUknownPages = deleteUknownPages;
	}

	/**
	 * @return ��� ����� ����������� �������
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * @param file ��� ����� ����������� �������
	 */
	public void setFile(String file)
	{
		this.file = file;
	}

	private Map<String, String> parseParametersPage(Element element) throws Exception
	{
		if (element == null)
			return null;
		
		final Map<String, String> pageParameters = new HashMap<String, String>();
		XmlHelper.foreach(element, "parameter", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				pageParameters.put(XmlHelper.getSimpleElementValue(element, "name"), 
								   XmlHelper.getSimpleElementValue(element, "value"));
			}
		});
		return pageParameters;
	}

	private Page parsePage(Element element) throws Exception
	{
		String  pageKey           = XmlHelper.getSimpleElementValue(element, "key");
		String  parentKey         = XmlHelper.getSimpleElementValue(element, "parent");
		String  pageName          = XmlHelper.getSimpleElementValue(element, "name");
		String  pageUrl           = XmlHelper.getSimpleElementValue(element, "url");
		Element parametersElement = XmlHelper.selectSingleNode(element, "parameters");
		String  pageOrder         = XmlHelper.getSimpleElementValue(element, "order");

		Page page = new Page();
		page.setKey(pageKey);
		page.setName(pageName);
		page.setUrl(pageUrl);
		Map<String, String> parameters = parseParametersPage(parametersElement);
		page.setParameters(parameters);
		page.setOrder(pageOrder);

		if (parentKey != null)
		{
			Page parent = new Page();
			parent.setKey(parentKey);
			page.setParent(parent);
		}

		return page;
	}

	private Map<String, Page> parsePages(File xmlSource) throws Exception
	{
		final Map<String, Page> pages = new HashMap<String, Page>();
		XmlFileReader xmlFileReader = new XmlFileReader(xmlSource);
		Element pagesXMLElement = xmlFileReader.readDocument().getDocumentElement();
		XmlHelper.foreach(pagesXMLElement, "page", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				Page page = parsePage(element);
				pages.put(page.getKey(), page);
			}
		});
		return pages;
	}

	private Map<String, Page> getMap(List<Page> pages)
	{
		Map<String, Page> map = new HashMap<String, Page>();
		for (Page page: pages)
		{
			map.put(page.getKey(), page);
		}
		return map;
	}

	public void safeExecute() throws Exception
	{
		Map<String, Page> dbPages = getMap(service.getAll());
		Map<String, Page> xmlPages = parsePages(new File(file));

		for (String xmlPageKey : xmlPages.keySet())
		{
			Page xmlPage = xmlPages.get(xmlPageKey);
			Page addingPage = dbPages.remove(xmlPageKey);

			Page parrentPage = null;
			Page parentXML = xmlPage.getParent();
			if (parentXML != null)
			{
				String parentKey = parentXML.getKey();
				//���� � ����
				parrentPage = dbPages.get(parentKey);
				//���� � xml
				Page xmlParrentPage = xmlPages.get(parentKey);

				if (xmlParrentPage == null && (deleteUknownPages || parrentPage == null))
				{
					//���� �� �� ����� ������ �� ������� ����������
					throw new BusinessLogicException("���������� �������� �������� '" + xmlPageKey + "' � ������ '" + parentKey + "'.");
				}

				if (parrentPage == null)
				{
					parrentPage = xmlParrentPage;
				}
			}

			if (addingPage == null)
			{
				addingPage = xmlPage;
			}
			else
			{
				addingPage.setName(xmlPage.getName());
				addingPage.setUrl(xmlPage.getUrl());
				addingPage.setParameters(xmlPage.getParameters());
			}
			addingPage.setParent(parrentPage);
			xmlPages.put(xmlPageKey, addingPage);
		}

		service.addOrUpdate(new ArrayList(xmlPages.values()));
		if (deleteUknownPages)
		{
			service.remove(new ArrayList(dbPages.values()));
		}
	}
}
