package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.dataaccess.query.OrderDirection;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.util.SkinHelper;
import com.rssl.phizic.utils.StringHelper;
import fr.improve.struts.taglib.layout.collection.ItemContext;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.CollectionInterface;
import fr.improve.struts.taglib.layout.util.IFooterRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @ author gorshkov
 * @ created 13.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SortListRenderer implements CollectionInterface
{
	private TemplatedCollectionTag collectionTag;
	private String styleClass;
	private PageContext pageContext;

	public void init(PageContext pageContext, String in_styleClass, TagSupport in_tag) throws JspException
	{
		this.pageContext = pageContext;
		this.styleClass = in_styleClass;
		this.collectionTag = (TemplatedCollectionTag) in_tag;
	}

	public void doEndHeaders(StringBuffer buf)
	{
		buf.append("</li>");
	}

	public void doEndItems(StringBuffer buf)
	{
		buf.append("</li>");
	}

	public void doAfterBody(StringBuffer buffer)
	{
	}

	public void doBeforeBody(StringBuffer buffer, String align)
	{
	}

	public void doEndPanel(StringBuffer buf)
	{
		// пустой список не рисуем
		if (collectionTag.getSize() != 0)
		{
			buf.append("</ul>\n");
		}
	}

	public void doPrintBlankLine(StringBuffer buffer, int cols)
	{
	}

	public void doPrintEmptyCollection(StringBuffer buf, String in_message)
	{
		buf.append("<li><span");

		if (collectionTag.getTempStyleClass() != null)
		{
			buf.append(" class=\"");
			buf.append(collectionTag.getTempStyleClass());
			buf.append("\"");
		}

		buf.append(">");
		buf.append(in_message);
		buf.append("</li></span>");
	}

	public void doPrintHeader(StringBuffer buf, String in_header, String in_width, String in_sortUrl)
	{
			doPrintHeaderStart(buf, in_width);
			buf.append(doPrintSortUrl(in_header, in_sortUrl));
			doPrintHeaderEnd(buf);
	}

	private void doPrintHeaderEnd(StringBuffer buf)
	{
		buf.append("</span>");
	}

	private void doPrintHeaderStart(StringBuffer buf, String in_width)
	{
		buf.append("<span");
		if (in_width != null)
		{
			buf.append(" width=\"");
			buf.append(in_width);
			buf.append("\"");
		}
		String[] in_styleClass = collectionTag.getTempStyles();
		if (in_styleClass != null && in_styleClass[0] != null)
		{
			buf.append(" class=\"");
			buf.append(in_styleClass[0]);
			buf.append("\"");
		}
		if (in_styleClass.length > 1)
		{
			buf.append(" style=\"");
			for (int i = 0; i < in_styleClass.length - 1; i++)
			{
				buf.append(in_styleClass[i + 1]);
			}
			buf.append("\"");
		}
		buf.append(">");
	}

	public void doPrintItem(StringBuffer buf, String in_item, String[] in_styleClass, String in_id)
	{
		doPrintItemStart(buf, in_styleClass, 1);

		if (in_item != null && !in_item.trim().equals(""))
		{
			buf.append(in_item);
		}
		else
		{
			buf.append("&nbsp;");
		}

		doPrintItemEnd(buf);
	}

	private void doPrintItemEnd(StringBuffer buf) {
		if(collectionTag.needHidden())
		{
			buf.append("</span>");
			buf.append("<span style=\"display: none;\" id=\"HideContent").append(collectionTag.getColumn()).append("\">&nbsp; </span>");
		}
		buf.append("</span>");
	}

	private void doPrintItemStart(StringBuffer buf, String[] in_styleClass, Integer colspan)
	{
		buf.append("<span");
		if (in_styleClass != null && in_styleClass[0] != null)
		{
			buf.append(" class=\"");
			buf.append(in_styleClass[0]);
			buf.append("\"");
		}
		if (in_styleClass.length > 1)
		{
			buf.append(" style=\"");
			for (int i = 0; i < in_styleClass.length - 1; i++)
			{
				buf.append(in_styleClass[i + 1]);
			}
			buf.append("\"");
		}
		if(colspan != null && colspan > 1)
		{
			buf.append(" colspan=\"");
			buf.append(colspan);
			buf.append("\"");
		}
		buf.append(">");
		if(collectionTag.needHidden())
			buf.append("<div id=\"RowContent").append(collectionTag.getColumn()).append("\">");
	}

	protected String doPrintSortUrl(String in_header, String in_sortUrl)
	{
		return in_header;
	}

	public void doPrintTitle(StringBuffer buf, String title)
	{
	}

	public void doStartHeaders(StringBuffer buf)
	{
		buf.append("<li");
		if (collectionTag.getStyleClass() != null)
		{
			buf.append(" class=\"");
			buf.append(collectionTag.getStyleClass());
			buf.append("\"");
		}
		buf.append(">");
	}

	public void doStartItems(StringBuffer buf)
	{
		buf.append("<li");
		boolean lc_onclick = false;

		if (collectionTag.getStyleClass() != null)
		{
			buf.append(" class=\"");
			buf.append(collectionTag.getStyleClass());
			buf.append("\"");
		}
		if (collectionTag.getOnRowClick() != null)
		{
			buf.append(" onclick=\"");
			buf.append(Expression.evaluate(collectionTag.getOnRowClick(), pageContext));
			buf.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowDblClick() != null)
		{
			buf.append(" ondblclick=\"");
			buf.append(Expression.evaluate(collectionTag.getOnRowDblClick(), pageContext));
			buf.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowMouseOver() != null)
		{
			buf.append(" onmouseover=\"");
			buf.append(Expression.evaluate(collectionTag.getOnRowMouseOver(), pageContext));
			buf.append("\"");
		}
		if (collectionTag.getOnRowMouseOut() != null)
		{
			buf.append(" onmouseout=\"");
			buf.append(Expression.evaluate(collectionTag.getOnRowMouseOut(), pageContext));
			buf.append("\"");
		}

		if(collectionTag.isNeedJsPagination() && (collectionTag.getIndex() >= collectionTag.getCollectionSize()))
		{
			buf.append(" style=\"display:none;\"");
		}
		else if (lc_onclick)
		{
			buf.append(" style=\"cursor:pointer;cursor:hand;\"");
		}

		buf.append(">");
	}

	public void doStartPanel(StringBuffer buffer, String align, String width)
	{
		// пустой список не рисуем
		if (collectionTag.getSize() != 0)
		{
			buffer.append("<ul");
			if (collectionTag.getStyleId() != null)
			{
				buffer.append(" id=\"");
				buffer.append(collectionTag.getStyleId());
				buffer.append("\"");
			}
			if (styleClass != null)
			{
				buffer.append(" class=\"");
				buffer.append(styleClass);
				buffer.append("\"");
			}
			if (align != null)
			{
				buffer.append(" align=\"");
				buffer.append(align);
				buffer.append("\"");
			}
			if (width != null)
			{
				buffer.append(" width=\"");
				buffer.append(width);
				buffer.append("\"");
			}
			buffer.append(">\n");
		}
	}
}
