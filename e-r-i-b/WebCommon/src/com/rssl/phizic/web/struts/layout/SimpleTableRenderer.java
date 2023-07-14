package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.dataaccess.query.OrderDirection;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.util.SkinHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.browser.BrowserUtils;
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
 * @author Evgrafov
 * @ created 19.07.2007
 * @ $Author: kichinova $
 * @ $Revision: 80491 $
 */

public class SimpleTableRenderer implements CollectionInterface, SelectionInterface, HiddenInterface, IFooterRenderer, BeforeDataRenderer, PaginationRenderer, SortedInterface
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

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
		buf.append("</tr>");
	}

	public void doEndItems(StringBuffer buf)
	{
		buf.append("</tr>");
	}

	public void doAfterBody(StringBuffer buffer)
	{
		buffer.append("asdasd");
	}

	public void doBeforeBody(StringBuffer buffer, String align)
	{
	}

	public void doEndPanel(StringBuffer buf)
	{
		// пустую таблицу не рисуем
		if (collectionTag.getSize() != 0)
		{
			buf.append("</table>\n");
		}
	}

	public void doPrintBlankLine(StringBuffer buffer, int cols)
	{
	}

	public void doPrintEmptyCollection(StringBuffer buf, String in_message)
	{
		buf.append("<tr><td");

		if (collectionTag.getTempStyleClass() != null)
		{
			buf.append(" class=\"");
			buf.append(collectionTag.getTempStyleClass());
			buf.append("\"");
		}

		buf.append(">");
		buf.append(in_message);
		buf.append("</td></tr>");
	}

	private void doPrintHeaderTable(StringBuffer buf, String in_header, String in_width, String in_sortUrl, int column)
	{
		buf.append("<th");
		if (in_width != null)
		{
			buf.append(" width=\"");
			buf.append(in_width);
			buf.append("\"");
		}

		buf.append(">");
		buf.append("<table class=\"HeaderTable\">");
		buf.append("<tr><th><div id=\"RowContent").append(column).append("\" >");
		buf.append(doPrintSortUrl(in_header, in_sortUrl));
		buf.append("</div></th>");
		if (collectionTag.needHidden()){
			doColumnsHiddenItem(buf, column);
		}
		String sortProperty = collectionTag.getSortProperties().get(Integer.valueOf(column));
		if(StringHelper.isNotEmpty(sortProperty))
		{
			doPrintSortColumn(buf,sortProperty, collectionTag.getOrderParameters());
		}
		buf.append("</tr></table>");
		buf.append("</th>");
	}

	private boolean needTableInsideHeader()
	{
		return collectionTag.needHidden() || !collectionTag.getSortProperties().isEmpty();
	}

	public void doPrintHeader(StringBuffer buf, String in_header, String in_width, String in_sortUrl)
	{
		if (needTableInsideHeader())
		{
			doPrintHeaderTable(buf, in_header,in_width,in_sortUrl, collectionTag.getColumn());
		}
		else{
			doPrintHeaderStart(buf, in_width);
			buf.append(doPrintSortUrl(in_header, in_sortUrl));
			doPrintHeaderEnd(buf);
		}
	}

	private void doPrintHeaderEnd(StringBuffer buf)
	{
		buf.append("</th>");
	}

	private void doPrintHeaderStart(StringBuffer buf, String in_width)
	{
		buf.append("<th");
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

	public void doPrintSortedElements(StringBuffer buf, List<OrderParameter> orderParameters, ArrayList headers, Map<Integer, String> sortProperties)
	{
		buf.append("<tr>");
		int colspan = getColumnCount();
		buf.append("<td colspan = '").append(colspan).append("'>");
		buf.append("Сортировать по: ");
		for(Integer i=0; i < orderParameters.size(); i ++)
		{
			OrderParameter parameter = orderParameters.get(i);
			String parameterName = getOrderParameterName(parameter.getValue());
			buf.append(parameterName);
			buf.append(" ");
			buf.append("<a href='#' onclick=\"");
			buf.append("deleteOrderParameter('").append(parameter.getValue()).append("');");
			buf.append("callOperation(event,'button.filter');");
			buf.append(" return false; \" >");
			buf.append("<img src=\"");
			buf.append(getImagePath());
			buf.append("/close.gif\" alt=\"Удалить\" border=\"0\">");
			buf.append("</a>");
			buf.append("<input type='hidden' name='"+TemplatedCollectionTag.ORDER_PARAMETER_NAME+i+"' value='"+parameter.getValue()+"'/>");
			buf.append("<input type='hidden' name='"+TemplatedCollectionTag.ORDER_PARAMETER_NAME+i+TemplatedCollectionTag.ORDER_PARAMETER_DIRECTION_NAME+"' value='"+parameter.getDirection()+"'/>");
		}
		buf.append("</td>");
		buf.append("</tr>");
	}

	//Общее число столбцов в таблице
	private int getColumnCount()
	{
		int count = collectionTag.getHeaders().size();
		if(collectionTag.needSelection())
			count ++;
		return count;
	}

	private String getOrderParameterName(String parameter)
	{
		String parameterName = "";
		Map<Integer, String> sortProperties = collectionTag.getSortProperties();
		for(Map.Entry<Integer, String> entry : sortProperties.entrySet())
			if(parameter.equals(sortProperties.get(entry.getKey())))
			{
				ItemContext header = (ItemContext)collectionTag.getHeaders().get(entry.getKey());
				String lc_key = header.getTitle();
				String lc_arg0 = Expression.evaluate(header.getArg0(), pageContext);
				String lc_arg1 = Expression.evaluate(header.getArg1(), pageContext);
				Object[] lc_args = new Object[2];
				lc_args[0] = lc_arg0;
				lc_args[1] = lc_arg1;

				try
				{
					parameterName = LayoutUtils.getLabel(pageContext, collectionTag.getBundle(), lc_key, lc_args, false);
				}
				catch (JspException ignored)
				{
					//ничего не делаем, упадем при построении шапки таблицы
				}
				return parameterName;
			}
		return parameterName;
	}

	private void doPrintItemEnd(StringBuffer buf) {
		if(collectionTag.needHidden())
		{
			buf.append("</div>");
			buf.append("<div style=\"display: none;\" id=\"HideContent").append(collectionTag.getColumn()).append("\">&nbsp; </div>");
		}
		buf.append("</td>");
	}

	private void doPrintItemStart(StringBuffer buf, String[] in_styleClass, Integer colspan)
	{
		buf.append("<td");
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
		buf.append("<tr");
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
		buf.append("<tr");
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
		// пустую таблицу не рисуем
		if (collectionTag.getSize() != 0)
		{
			if(collectionTag.isNeedJsPagination())
			{
				String addJsScript = "<script type=\"text/javascript\" src=\"%1$s/jsPagination.js\"></script>";
				buffer.append(String.format(addJsScript, getGlobalScriptPath()));
			}

			buffer.append("<table cellspacing=\"0\" cellpadding=\"0\"");
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

	public void doPrintItemsSelectOne(StringBuffer buffer, String name, String itemId, String[] in_styleClass, boolean selected)
	{
		doPrintSelect(buffer, name, itemId, selected, in_styleClass, "radio");
	}

	private void doPrintSelect(StringBuffer buffer, String name, String itemId, boolean selected, String[] in_styleClass, String type)
	{
		doPrintItemStart(buffer, in_styleClass, null);

		buffer.append("<input type=\"");
		buffer.append(type);
		buffer.append("\"");

		buffer.append(" name=\"");
		buffer.append(name);
		buffer.append("\"");

		buffer.append(" value=\"");
		buffer.append(itemId);
		buffer.append("\"");

		if (selected)
		{
			buffer.append(" checked=\"checked\"");
		}


		buffer.append("/>");

		doPrintItemEnd(buffer);
	}

	public void doPrintItemsSelectMulti(StringBuffer buffer, String name, String itemId, String[] in_styleClass, boolean selected)
	{
		doPrintSelect(buffer, name, itemId, selected, in_styleClass, "checkbox");
	}

	public void doPrintHeaderSelectOne(StringBuffer buffer, String name, String text)
	{
		doPrintHeaderStart(buffer, "20px");
		buffer.append("&nbsp");
		doPrintHeaderEnd(buffer);
	}

	public void doPrintHeaderSelectMulti(StringBuffer buffer, String name, String text)
	{
		doPrintHeaderStart(buffer, "20px");

		buffer.append("<input type=\"checkbox\"");
		buffer.append(" onclick=\"switchSelection(this,'");
		buffer.append(name);
		buffer.append("')\"/>");

		doPrintHeaderEnd(buffer);
	}

	public void doColumnsHiddenItem(StringBuffer buffer, int idHiddenLink)
	{
		buffer.append("<td>");
		buffer.append("<a ");
		buffer.append("onclick=\"hideColumn(event," +
				idHiddenLink +
				", this, ");
		if (collectionTag.getStyleId() != null)
			buffer.append(collectionTag.getStyleId());
		else
			buffer.append("'hiddenClmnTbl'");
		buffer.append(");\"");
		buffer.append(">");
		buffer.append("<img src=\"");
		buffer.append(getImagePath());
		buffer.append("/iconSm_triangleLeft.gif\" alt=\"\" border=\"0\">");
		buffer.append("</a>");
		buffer.append("</td>");
	}

	public void doPrintSortColumn(StringBuffer sb, String parameter, List<OrderParameter> orderParameters)
	{
		sb.append("<td>");
		sb.append("<div>");

		if(!orderParameters.contains(new OrderParameter(parameter,OrderDirection.ASC)))
			doPrintSortButton(sb, parameter,OrderDirection.ASC);
		if(!orderParameters.contains(new OrderParameter(parameter,OrderDirection.DESC)))
			doPrintSortButton(sb, parameter,OrderDirection.DESC);

		sb.append("</div>");
		sb.append("</td>");
	}

	private void doPrintSortButton(StringBuffer sb, String parameter, OrderDirection direction)
	{
		sb.append("<div>");
		sb.append("<a href='#' onclick=\"");
		sb.append("deleteOrderParameter('").append(parameter).append("');");// предварительно попробуем удалить этот параметр из параметров сортировки (вдруг, по нему уже ведется сортровка)
		sb.append("addOrderParameter('").append(parameter).append("','").append(direction).append("');");// добавляем новый параметр сортировки
		sb.append("callOperation(event,'button.filter');");
		sb.append(" return false; \" >");
		sb.append("<img src=\"");
		sb.append(getImagePath());
		if(direction == OrderDirection.ASC)
			sb.append("/bullet_up.gif\" alt=\"Сортировать по возрастанию\" class=\"bulletImg\"/>");
		else
			sb.append("/bullet.gif\" alt=\"Сортировать по убыванию\" class=\"bulletImg\"/>");
		sb.append("</div></a>");
	}

	public void startFooter(StringBuffer buffer)
	{
		buffer.append("<tr");
		if (collectionTag.getStyleClass() != null)
		{
			buffer.append(" class=\"");
			buffer.append(collectionTag.getStyleClass());
			buffer.append("\"");
		}
		buffer.append(">");
	}

	public void endFooter(StringBuffer buffer)
	{
		buffer.append("</tr>");
	}

	public void printFooterElement(StringBuffer buffer, String element, int span)
	{
		buffer.append("<td ");
		if (span > 1)
		{
			buffer.append(" colspan=\"");
			buffer.append(span);
			buffer.append("\"");
		}
		buffer.append(">");
		if(collectionTag.needHidden())
			buffer.append("<div id=\"RowContent").append(collectionTag.getColumn()).append("\">");
		buffer.append(element);
		if(collectionTag.needHidden()){
			buffer.append("</div>");
			buffer.append("<div style=\"display: none;\" id=\"HideContent").append(collectionTag.getColumn()).append("\">&nbsp; </div>");
		}
		buffer.append("</td>");
	}

	public void startBeforeData(StringBuffer buffer)
	{
		buffer.append("<tr");
		if (collectionTag.getStyleClass() != null)
		{
			buffer.append(" class=\"");
			buffer.append(collectionTag.getStyleClass());
			buffer.append("\"");
		}
		buffer.append(">");
	}

	public void endBeforeData(StringBuffer buffer)
	{
		buffer.append("</tr>");
	}

	public void printBeforeDataElement(StringBuffer buffer, String value, int span)
	{
		buffer.append("<td ");
		if (span > 1)
		{
			buffer.append(" colspan=\"");
			buffer.append(span);
			buffer.append("\"");
		}
		buffer.append(">");
		buffer.append(value);
		buffer.append("</td>");
	}

	public void doPrintPagination(StringBuffer buffer, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName, String sizeFieldName, String paginationType)
	{
		if(collectionTag.isNeedJsPagination())
		{
			paginationSize = collectionTag.getCollectionSize();
		}
		if (paginationSize >= realDataSize && paginationOffset == 0 && realDataSize <= collectionTag.getAllowedPaginationSizes().get(0))
		{
			return;
		}

		buffer.append("<tr id=\"pagination\">");
		buffer.append("<td colspan='");
		int spanSize = getColumnCount();
		buffer.append(spanSize);
		buffer.append("' style=\"border:0;\">");
		buffer.append("<table cellpadding='0' cellspacing='0' class='");
		buffer.append(collectionTag.getStyleClass());
		buffer.append("'>");
		buffer.append("<tr>");
		buffer.append("<td style=\"width:50%;border:0;\" class=\"firstPaginBlock\"></td>");
		if ("simple".equals(paginationType))
		{
			buffer.append("<td style=\"border:0;\"></td>");
			printSimplePagination(buffer, paginationOffset, paginationSize, realDataSize, offsetFieldName, sizeFieldName);
			printSimplePaginationSize(buffer, paginationSize, sizeFieldName, paginationOffset, offsetFieldName);
		}
		else
			if ("button".equals(paginationType))
				printButtonPagination(buffer, paginationOffset, paginationSize, realDataSize, offsetFieldName);
			else
			{
				printPagination(buffer, paginationOffset, paginationSize, realDataSize, offsetFieldName);
				printPaginationSize(buffer, paginationSize, sizeFieldName,paginationOffset,offsetFieldName);
			}
		buffer.append("</tr>");
		buffer.append("</table>");
		buffer.append("</td>");
		buffer.append("</tr>");
	}

	private void printSimplePaginationSize(StringBuffer buffer, int paginationSize, String sizeFieldName,int paginationOffset, String offsetFieldName)
	{
		String onClick = "setElement('%1$s', %2$d); " +
				"callOperation(event,'button.filter');";
		if(collectionTag.getOnClick() != null)
			onClick = collectionTag.getOnClick();
		if(collectionTag.isNeedJsPagination())
		{
			 onClick = "jsSimplePagination.moreValues(this);";
		}
		String selectedElement = "<span class='paginationSize'><div class=\"greenSelector\">\n" +
				"                           <span>\n" +
				"                               %1$d \n" +
				"                           </span>\n" +
				"                 </div></span>";

		String element = "<span class='paginationSize'> <a href='#' onclick=\"" + onClick +" return false;\"><span>%2$d</span></a></span>";

		buffer.append("<td class='tblPaginSize' style=\"border:0;\">");
		buffer.append("Показать по: ");
		buffer.append("<input type='hidden' name='"+sizeFieldName+"' value='"+paginationSize+"'/>");
		for (int size : collectionTag.getAllowedPaginationSizes())
		{
			if (size == paginationSize)
				buffer.append(String.format(selectedElement, size));
			else
				buffer.append(String.format(element, sizeFieldName, size, offsetFieldName, 0));//при перключении числа элементов на странице нужно переходить на первую страницу
		}

		buffer.append("</td>");
	}

	private void printPaginationSize(StringBuffer buffer, int paginationSize, String sizeFieldName,int paginationOffset, String offsetFieldName)
	{
        String paginationInputTemplate ="addElementToForm('"+sizeFieldName+"', '"+paginationSize+"');";

		buffer.append("<td style=\"border:0;text-align:right;\" width=\"100%\" nowrap=\"nowrap\">");
		buffer.append("<div class=\"floatRight\"><div class=\"float paginationItemsTitle\">Показать по:</div>");
		buffer.append("<input type=\"hidden\"  name=\"").append(sizeFieldName).append("\" value=\"").append(paginationSize).append("\">");

		String onClick = "setElement('%1$s', %2$d); " + "callOperation(event,'button.filter');";
		if(collectionTag.isNeedJsPagination())
		{
			onClick = "jsPagination.moreValues(this);";
		}

		for(int size : collectionTag.getAllowedPaginationSizes())
		{
			if(size == paginationSize)
			{
				buffer.append("<div class=\"float ").append(BrowserUtils.isIE() ? "circleImage" : "circle").append("\" class=\"float\"><div class=\"greenSelector\"><span>").append(size).append("</span></div></div>");
			}
			else
			{
				buffer.append("<div class=\"paginationSize float\"><a onclick=\"");
				buffer.append(paginationInputTemplate);
				buffer.append(String.format(onClick, sizeFieldName, size));
				buffer.append("\" href=\"#\"><span>").append(size).append("</span></a></div>");
			}
		}

		buffer.append("</div></td>");
	}

	private void printOptions(StringBuffer buffer, List<Integer> sizes, int selected)
	{
		for (int size : sizes)
		{
			buffer.append("<option value='");
			buffer.append(size);
			buffer.append("'");
			if (size == selected)
			{
				buffer.append("selected");
			}
			buffer.append(">");
			buffer.append(size);
			buffer.append("</option>");
		}
	}

	private void printPagination(StringBuffer buffer, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName)
	{
		String actionTemplate = "setElement(\"" + offsetFieldName + "\", %d);callOperation(event,\"button.filter\"); return false";
        String paginationInputTemplate ="addElementToForm(\""+offsetFieldName+"\", \""+paginationOffset+"\");";

		if(collectionTag.isNeedJsPagination())
		{
			actionTemplate="jsPagination.nextValues(this, false);";
			buffer.append("<input type=\"hidden\" name=\"offset\" value=\"0\"/>");
		}

		if (paginationOffset > 0)
		{
			buffer.append("<td style=\"border:0;\" nowrap=\"nowrap\">");
			buffer.append("<a href='#' onclick='");
            buffer.append(paginationInputTemplate);
			buffer.append(String.format(actionTemplate, paginationOffset - paginationSize));
			buffer.append("'>");
			buffer.append("<div class=\"activePaginLeftArrow\"></div>");
			buffer.append("</a>");
			buffer.append("</td>");
		}
		else
		{
			buffer.append("<td style=\"border:0;\" nowrap=\"nowrap\">");
			buffer.append("<div class=\"inactivePaginLeftArrow\"></div>");
			buffer.append("</td>");
		}

		buffer.append("<td style=\"border:0;\" nowrap=\"nowrap\">");
		buffer.append("<span class=\"tblNumRecIns\">").append(paginationOffset + 1).append(" - ").append(paginationOffset + (realDataSize <= paginationSize ? realDataSize : paginationSize)).append("</span>");
		buffer.append("</td>");

		if (realDataSize > paginationSize + collectionTag.getOffset())
		{
			buffer.append("<td style=\"border:0;\" nowrap=\"nowrap\">");
			buffer.append("<a href='#' onclick='");
            buffer.append(paginationInputTemplate);
			buffer.append(String.format(actionTemplate, paginationOffset + paginationSize));
			buffer.append("'>");
			buffer.append("<div class=\"activePaginRightArrow\"></div>");
			buffer.append("</a>");
			buffer.append("</td>");
		}
		else
		{
			buffer.append("<td style=\"border:0;\" nowrap=\"nowrap\">");
			buffer.append("<div class=\"inactivePaginRightArrow\"></div>");
			buffer.append("</td>");
		}
	}

	private void printSimplePagination(StringBuffer buffer, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName, String sizeFieldName)
	{
		String actionTemplate = "addField('hidden', '%3$s', %4$d);callOperation(event,'button.filter');";

		String onClick = this.collectionTag.getOnClick();
		if ( onClick != null )
		    actionTemplate = onClick;

		if(collectionTag.isNeedJsPagination())
		{
			actionTemplate="jsSimplePagination.nextValues(this, false);";
			buffer.append("<input type=\"hidden\" name=\"offset\" value=\"0\"/>");
		}

		buffer.append("<td style=\"border:0;\">");
		if (paginationOffset == 0)
		{
			buffer.append("<div class='inactivePaginLeftArrow'></div>");
		}

		if (paginationOffset > 0)
		{
			buffer.append("<a href='#' onclick=\"");
			buffer.append(String.format(actionTemplate, sizeFieldName, paginationSize, offsetFieldName, paginationOffset - paginationSize));
			buffer.append(" return false; \" >");
			buffer.append("<div class='activePaginLeftArrow'></div>");
			buffer.append("</a>");
		}

		if (realDataSize > paginationSize + collectionTag.getOffset())
		{
			buffer.append("<a href='#' onclick=\"");
			buffer.append(String.format(actionTemplate, sizeFieldName, paginationSize, offsetFieldName, paginationOffset + paginationSize));
			buffer.append(" return false;\" >");
			buffer.append("<div class='activePaginRightArrow'></div>");
			buffer.append("</a>");
		}

		if (realDataSize <= paginationSize + collectionTag.getOffset())
		{
			buffer.append("<div class='inactivePaginRightArrow'></div>");
		}
		buffer.append("</td>");
	}

	private void printButtonPagination(StringBuffer buffer, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName)
	{
		String actionTemplate = "";
		String onClick = this.collectionTag.getOnClick();
		if ( onClick != null )
		    actionTemplate = " onclick=\""+onClick+"\"";

		buffer.append("<td style=\"width: 250px;border:0;\">");
		buffer.append("<input type='hidden' name='"+offsetFieldName+"' value='"+paginationOffset+"' />");
		if (paginationOffset > 0)
		{
			buffer.append("<input type='submit' name='"+ offsetFieldName +"_priv' value='&laquo; Предыдущие " + paginationSize + "' ");
			buffer.append(String.format(actionTemplate, paginationOffset - paginationSize, offsetFieldName));
			buffer.append(" class='gridButton previous' />");
		}

		if (realDataSize > paginationSize + collectionTag.getOffset())
		{
			buffer.append("<input type='submit' name='"+ offsetFieldName +"_next' value='Следующие " + paginationSize + " &raquo;' ");
			buffer.append(String.format(actionTemplate, paginationOffset - paginationSize, offsetFieldName));
			buffer.append(" class='gridButton' />");
		}
		buffer.append("</td>");
	}

	private Object getImagePath()
	{
		try
		{
			return SkinHelper.getSkinUrl() + "/images";
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return "";
		}
	}

	private String getGlobalScriptPath()
	{
		try
		{
			return SkinHelper.getGlobalSkinUrl() + "/scripts";
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return "";
		}
	}
}
