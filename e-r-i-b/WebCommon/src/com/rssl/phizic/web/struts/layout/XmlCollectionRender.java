package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.utils.StringHelper;
import fr.improve.struts.taglib.layout.collection.BaseCollectionTag;
import fr.improve.struts.taglib.layout.util.CollectionInterface;
import fr.improve.struts.taglib.layout.util.IFooterRenderer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Rydvanskiy
 * @ created 07.12.2010
 * @ $Author$
 * @ $Revision$
 */

public class XmlCollectionRender implements CollectionInterface, SelectionInterface, HiddenInterface, IFooterRenderer, BeforeDataRenderer, PaginationRenderer
{

	private TemplatedCollectionTag collectionTag;
	private PageContext pageContext;
	private BuildContextConfig buildContextConfig;
	private int curentHeader;

	public void init(PageContext pageContext, String in_styleClass, TagSupport in_tag) throws JspException
	{
		buildContextConfig = ConfigFactory.getConfig(BuildContextConfig.class);
		this.pageContext = pageContext;
		this.collectionTag = (TemplatedCollectionTag) in_tag;
	}

	public void doEndHeaders(StringBuffer buf)
	{
	}

	public void doEndItems(StringBuffer buf)
	{
	}

	public void doAfterBody(StringBuffer buffer)
	{
	}

	public void doBeforeBody(StringBuffer buffer, String align)
	{
	}
	// начало коллекции
	public void doStartPanel(StringBuffer buffer, String align, String width)
	{

		if (collectionTag.getSize() != 0)
			buffer.append("<"+collectionTag.getTitle()+">\n");
	}

   // завершение коллекции
	public void doEndPanel(StringBuffer buf)
	{
        // пустую таблицу не рисуем
		if (collectionTag.getSize() != 0)
		{
			buf.append("</"+collectionTag.getTitle()+">\n");
		}
	}

	public void doPrintBlankLine(StringBuffer buffer, int cols)
	{
	}

	public void doPrintEmptyCollection(StringBuffer buf, String in_message)
	{
	}

	public void doPrintHeader(StringBuffer buf, String in_header, String in_width, String in_sortUrl)
	{
	}

	public void doPrintItem(StringBuffer buf, String in_item, String[] in_styleClass, String in_id)
	{
		String elementTitle = ((BaseCollectionTag.Header)collectionTag.getHeaders().get(curentHeader)).getTitle();
		buf.append("<"+elementTitle+">");


		if (!StringHelper.isEmpty(in_item))
			buf.append(in_item);

		buf.append("</"+elementTitle+">\n");

		// следующий заголовок
		curentHeader++;
	}

	public void doPrintTitle(StringBuffer buf, String title)
	{
	}

	public void doStartHeaders(StringBuffer buf)
	{
	}

	public void doStartItems(StringBuffer buf)
	{
		//новая строка
		curentHeader = 0;
	}

	public void doPrintItemsSelectOne(StringBuffer buffer, String name, String itemId, String[] in_styleClass, boolean selected)
	{
	}

	public void doPrintItemsSelectMulti(StringBuffer buffer, String name, String itemId, String[] in_styleClass, boolean selected)
	{
	}

	public void doPrintHeaderSelectOne(StringBuffer buffer, String name, String text)
	{
	}

	public void doPrintHeaderSelectMulti(StringBuffer buffer, String name, String text)
	{
	}

	public void doColumnsHiddenItem(StringBuffer buffer, int idHiddenLink)
	{
	}

	public void startFooter(StringBuffer buffer)
	{
	}

	public void endFooter(StringBuffer buffer)
	{
	}

	public void printFooterElement(StringBuffer buffer, String element, int span)
	{
	}

	public void startBeforeData(StringBuffer buffer)
	{
	}

	public void endBeforeData(StringBuffer buffer)
	{
	}

	public void printBeforeDataElement(StringBuffer buffer, String value, int span)
	{
	}

	public void doPrintPagination(StringBuffer buffer, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName, String sizeFieldName, String paginationType)
	{
	}

}
