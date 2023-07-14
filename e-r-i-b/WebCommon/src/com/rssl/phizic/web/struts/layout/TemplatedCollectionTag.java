package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.dataaccess.query.OrderDirection;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.PaginalDataSource;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.config.view.TemplateManager;
import com.rssl.phizic.web.common.ServletContextPropertyReader;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.collection.ItemContext;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.IFooterRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.html.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Actualy we don't need extends CollectionTag.
 * But we forced to do it because CollectionTag used in items and renderers.
 * We have no choice :( (Except compleatly rewrite all involved tags)
 *
 * BTW: Struts Layout have some nice ideas but in details it very ugly at all.
 * What is LSP or OCP? Are they ever heard about it?
 *
 * @author Evgrafov
 * @ created 24.07.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public class TemplatedCollectionTag extends CollectionTag
{
	private static final String PAGINATION_OFFSET_KEY = "pagination.offset.key";
	private static final String PAGINATION_SIZE_KEY = "pagination.size.key";
	private static final String GRID_INDEX_KEY = "com.rssl.phizic.web.struts.layout.TemplatedCollectionTag.GRID_INDEX_KEY";
	private static final String PAGINATION_DEFAULT_SIZE_KEY = "pagination.default.collection.size";

	private static final String TABLE_WIDTH_KEY = "table.width";
	private static final String TABLE_CLASS_KEY = "table.class";
	private static final String TABLE_ITEM_CLASS_KEY = "table.item.class";
	private static final String BEFORE_DATA_CLASS_KEY = "before.data.class";
	private static final String BEFORE_DATA_ITEM_CLASS_KEY = "before.data.item.class";
	private static final String HEADER_CLASS_KEY = "header.class";
	private static final String HEADER_ITEM_CLASS_KEY = "header.item.class";
	private static final String FOOTER_CLASS_KEY = "footer.class";
	private static final String FOOTER_ITEM_CLASS_KEY = "footer.item.class";
	private static final String ITEMS_CLASS_KEY = "items.class";
	private static final String ITEMS_CLASS2_KEY = "items.class2";
	private static final String EMPTY_CLASS_KEY = "empty.class";
	private static final String PAGINATION_CLASS_KEY = "pagination.table.style";
	private static final String PAGINATION_ALLOWED_SIZES_KEY = "pagination.allowed.collection.sizes";
	private static final String PAGINATION_ALLOWED = "pagination.allowed";
	private static final String PAGINATION_TYPE = "pagination.type";
	private static final String BUTTON_TYPE = "button";

	private static final String COLUMNS_HIDDEN_ITEM_KEY = "columns.hiddenItem";
	public  static final String ORDER_PARAMETER_NAME = "$$order_parameter_";
	public  static final String ORDER_PARAMETER_DIRECTION_NAME = "_direction";
	public  static final int    MAX_NUM_OF_ORDER_PARAMETER = 3;

	private ServletContextPropertyReader defaultsReader;
	private TemplateManager templateManager;

	private String view;
	private String selectBean;
	private Integer collectionSize;
	private Integer assignedPaginationSize;
	private String assignedPaginationSizes;
	private String title;
	private Map<Integer, String> beforeColumnsData = new HashMap<Integer, String>();
	private Map<Integer, String> columnsHeadersClass = new HashMap<Integer, String>();
	private Map<Integer, List<String>> columnsData = new HashMap<Integer, List<String>>();
	private Map<Integer, String> footterTypes = new HashMap<Integer, String>();
	private Map<Integer, String> sortProperties = new HashMap<Integer, String>();
	private static Class operationsClass = DataOperations.class;
	private Map<Integer, Boolean> hiddenColumns = new HashMap<Integer, Boolean>();
	private Boolean paginationAllowed = true;
	private Integer paginationOffset;
	private Integer paginationSize;
	private List<Integer> allowedPaginationSizes = new ArrayList<Integer>();
	private String paginationOffsetKey;
	private String paginationSizeKey;
	private List<OrderParameter> orderParameters;
	private Boolean needJsPagination = false;
	// получить пользовательский размер коллекции
	public Integer getCollectionSize()
	{
		return collectionSize;
	}

	public void setCollectionSize(Integer collectionSize)
	{
		this.collectionSize = collectionSize;
	}

	public Boolean isNeedJsPagination()
	{
		return needJsPagination;
	}

	public void setNeedJsPagination(Boolean needJsPagination)
	{
		this.needJsPagination = needJsPagination;
	}

	//получает параметры сортировки
	public List<OrderParameter> getOrderParameters()
	{
		return orderParameters;
	}

	// получить пользовательский размер списка
	public Integer getAssignedPaginationSize()
	{
		return assignedPaginationSize;
	}

	public void setAssignedPaginationSize(Integer assignedPaginationSize)
	{
		this.assignedPaginationSize = assignedPaginationSize;
	}

	//получить пользовательские допустимые размеры списка
	public String getAssignedPaginationSizes()
	{
		return assignedPaginationSizes;
	}

	public void setAssignedPaginationSizes(String assignedPaginationSizes)
	{
		this.assignedPaginationSizes = assignedPaginationSizes;
	}

	// свойство title тега sl:collection
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	//TODO Да уж извращаться приходится изрядно...
	public String[] getTempStyles()
	{
		return (String[]) tempStyles.toArray(new String[tempStyles.size()]);
	}

	private void setPaginationOffset(String key)
	{
		paginationOffset = 0;
		//получаем смещение данных.
		String offset = getParamPagination(key);
		if (offset != null)
			paginationOffset = Integer.parseInt(offset);
		if (paginationOffset < 0)   paginationOffset = 0;
	}

	private void setPaginationSize (String key)
	{
		if (collectionSize != null && collectionSize > 0)
			paginationSize = collectionSize;

		if (paginationSize == null)
		{
			//получаем размер данных.
			String pageSize = getParamPagination(key);
			if (pageSize != null)
			{
				paginationSize = Integer.parseInt(pageSize);
				if (paginationSize >= 0) return ;
			}

			if (assignedPaginationSize != null){
				paginationSize = assignedPaginationSize;
				return;
			}
			paginationSize = Integer.parseInt(defaultsReader.getProperty(PAGINATION_DEFAULT_SIZE_KEY));
		}
	}


	public int doStartLayoutTag() throws JspException
	{
		//Устанавливаем параметры пагинации.
		initializeOrderParameters();
		Integer index = (Integer) pageContext.getAttribute(GRID_INDEX_KEY, PageContext.REQUEST_SCOPE);
		if (index == null)
		{
			index = -1;
		}
		pageContext.setAttribute(GRID_INDEX_KEY, ++index, PageContext.REQUEST_SCOPE);

		if (!paginationAllowed && collectionSize == null)
			return super.doStartLayoutTag();

		Object o = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
		if (!(o instanceof PaginalDataSource || o instanceof List))
		{
			return super.doStartLayoutTag();
		}

		paginationOffsetKey = String.format(defaultsReader.getProperty(PAGINATION_OFFSET_KEY), index);
		paginationSizeKey = String.format(defaultsReader.getProperty(PAGINATION_SIZE_KEY), index);
		setPaginationOffset(paginationOffsetKey);
		setPaginationSize (paginationSizeKey);

		if (paginationSize < 0)
			return super.doStartLayoutTag();

		String paginationType = defaultsReader.getProperty(PAGINATION_TYPE);
		if (BUTTON_TYPE.equals(paginationType))
		{
			if (pageContext.getRequest().getParameter(paginationOffsetKey + "_next") != null)
				paginationOffset += paginationSize;
			if (pageContext.getRequest().getParameter(paginationOffsetKey + "_priv") != null)
				paginationOffset -= paginationSize;
			
			paginationOffset = (paginationOffset<0)?0:paginationOffset;
		}

		if (o instanceof PaginalDataSource)
		{
			PaginalDataSource data = (PaginalDataSource) o;
			data.setOffset(paginationOffset);
			if(isNeedJsPagination())
				paginationSize = ((List) o).size();
			data.setSize(paginationSize + 1);
			data.setOrderParameters(getOrderParameters());
		}
		else
		{
			if(isNeedJsPagination())
				paginationSize = ((List) o).size();
			super.setLength(paginationSize);
			super.setOffset(paginationOffset);
		}

		int result = super.doStartLayoutTag();
		if (size == paginationSize + 1)
		{
			length = paginationSize;
		}

		return result;
	}

	/**
	 * @return имя view в view-config
	 */
	public String getView()
	{
		return view;
	}

	/**
	 * @param view имя view в view-config
	 */
	public void setView(String view)
	{
		this.view = view;
	}

	/**
	 * @return текущий TemplateManager
	 */
	public TemplateManager getTemplateManager()
	{
		return templateManager;
	}

	@Override protected void initDynamicValues()
	{
		if (model == null)
			throw new RuntimeException("You must set model attribute");

		SkinPlugin skinPlugin = SkinPlugin.getInstance(pageContext);

		defaultsReader = skinPlugin.getSkinConfig().getProperty(model);
		templateManager = skinPlugin.getViewConfig().getListTemplateManager(view);

		//получаем необходимость пейджинга
		if (!StringHelper.isEmpty(defaultsReader.getProperty(PAGINATION_ALLOWED)))
			paginationAllowed = Boolean.valueOf(defaultsReader.getProperty(PAGINATION_ALLOWED));

		if (paginationAllowed)
		{
			String sizes;
			if (StringHelper.isEmpty(assignedPaginationSizes))
				sizes = defaultsReader.getProperty(PAGINATION_ALLOWED_SIZES_KEY);
			else
			    sizes = assignedPaginationSizes;
			for (String size : sizes.split(";"))
			{
				allowedPaginationSizes.add(Integer.valueOf(size));
			}
		}
		super.initDynamicValues();
	}

	@Override protected void initPanel(PageContext in_pageContext)
	{
		super.initPanel(in_pageContext);

		// Установим стиль таблицы
		if (isCollectionNotEmpty())
		{
			if (StringHelper.isEmpty(styleClass))
				super.setStyleClass(defaultsReader.getProperty(TABLE_CLASS_KEY));
			else
				super.setStyleClass(defaultsReader.getProperty(TABLE_CLASS_KEY) + " " + styleClass);
		}
		else
		{
			super.setStyleClass(defaultsReader.getProperty(EMPTY_CLASS_KEY));
		}

		if (super.getWidth() == null)
			super.setWidth(defaultsReader.getProperty(TABLE_WIDTH_KEY));
	}

	@Override protected StringBuffer renderHeaders() throws JspException
	{
		StringBuffer buf = new StringBuffer();
		String headerItemClass = defaultsReader.getProperty(HEADER_ITEM_CLASS_KEY);
		boolean needHeader = false;

		// don't draw header for empty colection
		if (isCollectionNotEmpty())
		{
			// Установим стиль заголовка (ХАК)
			super.setStyleClass(defaultsReader.getProperty(HEADER_CLASS_KEY));

			//если необходимо, то выводим список параметров по которым ведется сортировка
			if(getOrderParameters().size() != 0)
			{
				SortedInterface si = (SortedInterface) panel;				
				si.doPrintSortedElements(buf, getOrderParameters(),headers, sortProperties);
			}
			column = 0;
			panel.doStartHeaders(buf);
			tempStyles.add(headerItemClass);
			if (needSelection())
			{
				SelectionInterface si = (SelectionInterface) panel;
				if ("checkbox".equals(selectType))
				{
					si.doPrintHeaderSelectMulti(buf, selectName, null);
				}
				else
				{
					si.doPrintHeaderSelectOne(buf, selectName, null);
				}
			}

			for (column = 0; column < headers.size(); column++)
			{
				ItemContext header = (ItemContext) headers.get(column);
				Integer columnNumber = Integer.valueOf(column);

				if (columnsHeadersClass.get(column) != null)
					tempStyles.set(0, headerItemClass + " " + columnsHeadersClass.get(column));
				needHeader = needHeader || StringHelper.isNotEmpty(header.getTitle());
				//если столбец скрытый, то вначале добавим ему это свойство
				if (hiddenColumns.get(columnNumber))
				{
					tempStyles.add("display:none");
				}
				renderHeader(buf, header);
				//а потом уберем, чтобы следующие отображались
				if (hiddenColumns.get(columnNumber))
				{
					tempStyles.remove(tempStyles.size() - 1);
				}
			}
			tempStyles.remove(tempStyles.size() - 1);
			column = 0;
			panel.doEndHeaders(buf);

			// Установим стиль первой строки (ХАК)
			super.setStyleClass(defaultsReader.getProperty(BEFORE_DATA_CLASS_KEY));
			renderFirstRow(buf);
		}

		// Установим стиль нечетных строк (ХАК)
		super.setStyleClass2(defaultsReader.getProperty(ITEMS_CLASS_KEY));
		// Установим стиль четных (ХАК)
		super.setStyleClass(defaultsReader.getProperty(ITEMS_CLASS2_KEY));

		// Если нет заголовков не выводим шапку
		if (!needHeader)
			return new StringBuffer();

		return buf;
	}

	private void initializeOrderParameters()
	{
		orderParameters = new ArrayList<OrderParameter>();
		ServletRequest request = pageContext.getRequest();
		for(int i = 0; i < MAX_NUM_OF_ORDER_PARAMETER; i++)
		{
			String parameter = request.getParameter(ORDER_PARAMETER_NAME + i);
			String direction = request.getParameter(ORDER_PARAMETER_NAME + i + ORDER_PARAMETER_DIRECTION_NAME);
			if(!StringHelper.isEmpty(parameter) && !StringHelper.isEmpty(direction) && !"null".equals(parameter) && !"null".equals(direction))
			{
				orderParameters.add(new OrderParameter(parameter,OrderDirection.value(direction)));
			}
		}
		if(orderParameters.isEmpty())
		{
			HttpSession session = pageContext.getSession();
			for(int i = 0; i < MAX_NUM_OF_ORDER_PARAMETER; i++)
			{
				OrderParameter parameter = (OrderParameter)session.getAttribute(ORDER_PARAMETER_NAME + i);
				session.removeAttribute(ORDER_PARAMETER_NAME + i);
				if(parameter != null)
				{
					orderParameters.add(parameter);
				}
			}
		}
	}

	@Override protected void renderBlankCollection(StringBuffer buf) throws JspException
	{
/*
		tempStyleClass = "messageTab";
		panel.doPrintEmptyCollection(buf, LayoutUtils.getLabel(pageContext, getBundle(), emptyKey, null, false));
*/
	}

	@Override protected void renderStart(StringBuffer buf) throws JspException
	{
		panel.doStartPanel(buf, align, width);
	}

	protected void setColumnBeforeData(String value)
	{
		beforeColumnsData.put(getColumn(), value);
	}

	protected void setColumnHeaderClass(String value)
	{
		columnsHeadersClass.put(getColumn(), value);
	}

	public void setColumnHidden(boolean hidden)
	{
		hiddenColumns.put(getColumn(), hidden);
	}

	protected void setColumnFooterType(String fotterType)
	{
		footterTypes.put(getColumn(), fotterType);
	}

	public void setColumnSortProperty(String sortProperty)
	{
		sortProperties.put(getColumn(), sortProperty);
	}

	protected void storeColumnData(String value)
	{
		if (getIndex() < 0)
		{
			return;//пропускаем обработку заголовка
		}
		int columnNumber = getColumn();
		List<String> data = columnsData.get(columnNumber);
		if (data == null)
		{
			data = new ArrayList<String>();
			columnsData.put(columnNumber, data);
		}
		data.add(value);
	}

	//рендер 1-й строки после заголовка
	protected void renderFirstRow(StringBuffer buffer)
	{
		if (!(panel instanceof BeforeDataRenderer))
			return;

		BeforeDataRenderer renderer = (BeforeDataRenderer) panel;
		boolean started = false;
		int spanSize = 0;
		for (int i = 0; i < headers.size(); i++)
		{
			if (hiddenColumns.get(i))
			{
				spanSize++;
				continue;
			}
			String beforeDataValue = beforeColumnsData.get(i);
			if (beforeDataValue == null)
			{
				spanSize++;
				continue;
			}
			if (!started)
			{
				renderer.startBeforeData(buffer);
				started = true;
			}
			if (spanSize > 0)
			{
				renderer.printBeforeDataElement(buffer, "&nbsp;", spanSize);
			}

			String value = Expression.evaluate(beforeDataValue, pageContext);
			renderer.printBeforeDataElement(buffer, value, 1);
			spanSize = 0;
		}

		if (started)
		{
			if (spanSize > 0)
			{
				renderer.printBeforeDataElement(buffer, "&nbsp;", spanSize);
			}
			renderer.endBeforeData(buffer);
		}
	}

	@Override protected void renderEnd(StringBuffer buf) throws JspException
	{
		renderLastRow(buf);
		if (paginationAllowed)
			renderPagination(buf);
		panel.doEndPanel(buf);
	}

	protected void renderPagination(StringBuffer buf)
	{
		if (!(panel instanceof PaginationRenderer) || paginationOffset == null || paginationSize == null)
		{
			return;
		}
		String paginationType = defaultsReader.getProperty(PAGINATION_TYPE);
		PaginationRenderer paginationRenderer = (PaginationRenderer) panel;
		Integer index = (Integer) pageContext.getAttribute(GRID_INDEX_KEY, PageContext.REQUEST_SCOPE);
		// Установим стиль  (ХАК)
		setStyleClass(defaultsReader.getProperty(PAGINATION_CLASS_KEY));
		paginationRenderer.doPrintPagination(buf, paginationOffset, paginationSize, size, paginationOffsetKey, paginationSizeKey, paginationType);
	}

	public List<Integer> getAllowedPaginationSizes()
	{
		return allowedPaginationSizes;
	}

	public Map<Integer, String> getSortProperties()
	{
		return Collections.unmodifiableMap(sortProperties);
	}

	//Возвращает true, когда у таблицы есть подписи внизу столбцов
	private boolean needFooterNames()
	{
		boolean needFooter=false;
		for (int i=0;i<headers.size();i++)
		{
			needFooter = StringHelper.isNotEmpty(((ItemContext) headers.get(i)).getFooter())||needFooter;
		}
		return needFooter;
	}

	protected void renderLastRow(StringBuffer buffer) throws JspException
	{
		// Установим стиль футтера (ХАК)
		setStyleClass(defaultsReader.getProperty(FOOTER_CLASS_KEY));
		if (!(panel instanceof IFooterRenderer))
			return;

		IFooterRenderer renderer = (IFooterRenderer) panel;
		boolean started = false;
		int spanSize = 0;

		//нужно чтобы не переезжали подписи в футере, когда есть столбец селектов,
		// а сам футер отображался только когда надо
		if (needSelection()&& needFooterNames())
		{
			renderer.startFooter(buffer);
			started = true;
			renderer.printFooterElement(buffer, "&nbsp;", 1);
		}

		for (column = 0; column < headers.size(); column++)
		{
			String fotterValue = getFooterValue(column);
			if (fotterValue == null)
			{
				//если текущий столбец скрытый, то и в колспан его добавлять не надо
				if (!hiddenColumns.get(Integer.valueOf(column)))
					spanSize++;
				continue;
			}
			if (!started)
			{
				renderer.startFooter(buffer);
				started = true;
			}
			if (spanSize > 0)
			{
				renderer.printFooterElement(buffer, "&nbsp;", spanSize);
			}
			String value = Expression.evaluate(fotterValue, pageContext);
			renderer.printFooterElement(buffer, value, 1);
			spanSize = 0;
		}

		if (started)
		{
			if (spanSize > 0)
			{
				renderer.printFooterElement(buffer, "&nbsp;", spanSize);
			}
			renderer.endFooter(buffer);
		}
	}

	private String getFooterValue(int i) throws JspException
	{
		String fotterType = footterTypes.get(i);
		String footer = ((ItemContext) headers.get(i)).getFooter();
		if (fotterType == null || "text".equals(fotterType))
		{
			return footer;
		}
		return doOperation(fotterType, columnsData.get(i));
	}

	private String doOperation(String operation, List<String> objects) throws JspException
	{
		try
		{
			Method method = operationsClass.getMethod(operation, List.class);
			Object object = method.invoke(null, objects);
			if (object == null)
			{
				return null;
			}
			return String.valueOf(object);
		}
		catch (Exception e)
		{
			throw new JspException(e);
		}
	}

	@Override protected void addItem(StringBuffer buf, String in_item, String in_url, String in_target, String in_onlick) throws JspException
	{
		if (needSelect)
		{
			addItemSelect(buf);
			needSelect = false;
		}

		String text = in_item;

		if (in_url != null)
		{
			text = prepareLink(in_url, in_item);
		}

		String styleClass = getTempStyleClass();
		if (styleClass == null)
		{
			styleClass = defaultsReader.getProperty(TABLE_ITEM_CLASS_KEY);
		}
		//такое вот сокрытие столбцов
		String[] styleClasses;
		if (hiddenColumns.get(getColumn()))
		{
			styleClasses = new String[2];
			styleClasses[1] = "display:none";
		}
		else
		{
			styleClasses = new String[1];
		}
		styleClasses[0] = styleClass;

		panel.doPrintItem(buf, text, styleClasses, null);

	}

	private String prepareLink(String url, String item)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"");
		sb.append(((HttpServletResponse) pageContext.getResponse()).encodeURL(url));
		sb.append("\">");
		sb.append(item);
		sb.append("</a>");
		return sb.toString();
	}

	private void addItemSelect(StringBuffer buf) throws JspException
	{
		if (panel instanceof SelectionInterface)
		{
			SelectionInterface si = (SelectionInterface) panel;
			Object beanForSelect = bean;
			if (selectBean != null)
			{
				beanForSelect = pageContext.findAttribute(selectBean);
			}
			String selectValue = getBeanProperty(beanForSelect, selectProperty);
			String[] styleClasses = {defaultsReader.getProperty(TABLE_ITEM_CLASS_KEY)};

			if ("checkbox".equals(selectType))
			{
				si.doPrintItemsSelectMulti(buf, selectName, selectValue, styleClasses, isValueSelected(selectValue));
			}
			else
			{
				si.doPrintItemsSelectOne(buf, selectName, selectValue, styleClasses, isValueSelected(selectValue));
			}
		}
	}

	@Override protected void renderMultiLevelHeaders(StringBuffer lc_buffer, List multiLevelHeaders2, int headersLevel2) throws JspException
	{
		//not supported
	}

	private boolean isValueSelected(String value) throws JspException
	{
		if (selectName == null)
			return false;

// Current value
		if (value == null)
			return false; //Null can't be selected

		if ("checkbox".equalsIgnoreCase(selectType))
		{
			// Like html:mutickeckbox

			// Selected values
			String[] selectedValues = (String[]) getBeanProperty(pageContext, Constants.BEAN_KEY, selectName);
			if (selectedValues == null)
				return false;

			for (int i = 0; i < selectedValues.length; i++)
			{
				if (value.equals(selectedValues[i]))
				{
					return true;
				}
			}

			return false;
		}
		else
		{
			// radio

			// Selected value
			String selectedValue = (String) getBeanProperty(pageContext, Constants.BEAN_KEY, selectName);

			return value.equals(selectedValue);
		}
	}

	public int doEndLayoutTag() throws JspException
	{
		int result = super.doEndLayoutTag();
		release();
		return result;
	}

	@Override public void release()
	{
		super.release();
		defaultsReader = null;
		beforeColumnsData.clear();
		footterTypes.clear();
		beforeColumnsData.clear();
		hiddenColumns.clear();
		columnsHeadersClass.clear();
		paginationAllowed = true;
		paginationSize = null;
		paginationOffset = null;
		allowedPaginationSizes.clear();
	}

	private boolean isCollectionNotEmpty()
	{
		return iterator.hasNext(pageContext);
	}

	/**
	 * Вычислить значение свойства
	 * @param pageContext контекст
	 * @param beanName имя бина в контексте
	 * @param propertyName имя свойства
	 * @return значение
	 * @throws JspException
	 */
	private Object getBeanProperty(PageContext pageContext, String beanName, String propertyName) throws JspException
	{
		Object temp = pageContext.getAttribute(propertyName);
		if (temp == null)
			return null;

		//noinspection UnusedCatchParameter
		return temp;
	}

	private String getBeanProperty(Object bean, String propertyName) throws JspException
	{
		try
		{
			return BeanUtils.getProperty(bean, propertyName);
		}
		catch (IllegalAccessException e)
		{
			throw new JspException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new JspException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new JspException(e);
		}
	}

	public String getSelectBean()
	{
		return selectBean;
	}

	public void setSelectBean(String selectBean)
	{
		this.selectBean = selectBean;
	}

	public boolean needSelection()
	{
		return selectName != null && panel instanceof SelectionInterface;
	}

	public boolean needHidden()
	{
		return Boolean.valueOf(defaultsReader.getProperty(COLUMNS_HIDDEN_ITEM_KEY)) && panel instanceof HiddenInterface;
	}

	private boolean needRenderSortedElements()
	{
		return sortProperties.get(getColumn()) != null && panel instanceof SortedInterface;
	}

	private String getParamPagination(String key)
	{
		String offset = pageContext.getRequest().getParameter(key);

		if (offset != null)
			return offset;

		if (pageContext.getRequest().getAttribute(key) != null)
		{
			offset = pageContext.getRequest().getAttribute(key).toString();
			pageContext.getRequest().removeAttribute(key);
		}
		else
			offset = null;
		return offset;
	}
}