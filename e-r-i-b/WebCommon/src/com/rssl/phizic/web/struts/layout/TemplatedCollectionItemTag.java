package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.utils.StringHelper;
import fr.improve.struts.taglib.layout.collection.FastCollectionItemTag;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Evgrafov
 * @ created 27.07.2007
 * @ $Author: dorzhinov $
 * @ $Revision: 49915 $
 */

public class TemplatedCollectionItemTag extends FastCollectionItemTag implements BodyTag
{
	private String styleId;
	private String styleTitleClass;
	private String beforeData;
	private BodyContent bodyContent;
	private String footerType;
	private boolean hidden = false;
	private String value;
	private boolean valueSpecified = false;
	private String sortProperty;

	{
		filter = false;
	}

	/**
	 * @return класс заголовка
	 */
	public String getStyleTitleClass()
	{
		return styleTitleClass;
	}

	/**
	 * @param styleTitleClass класс заголовка
	 */
	public void setStyleTitleClass(String styleTitleClass)
	{
		this.styleTitleClass = styleTitleClass;
	}

	/**
	 * @return id столбца
	 */
	public String getStyleId()
	{
		return styleId;
	}

	public String getSortProperty()
	{
		return sortProperty;
	}

	public void setSortProperty(String sortProperty)
	{
		this.sortProperty = sortProperty;
	}

	/**
	 * @param styleId id столбца
	 */
	public void setStyleId(String styleId)
	{
		this.styleId = styleId;
	}

	protected String buildUrl() throws JspException
	{
		if (url != null || page != null || forward != null || action != null)
		{
			return LayoutUtils.computeURL(pageContext, forward, url, page, action, null, null, null, false, target);
		}
		return null;
	}

	protected Object buildContent() throws JspException
	{
		Object result = null;
		if (value != null) //если задано значение явно, то использкем его
			result = value;
		else if (valueSpecified)
			result = null;
		else if (getProperty() != null)
		{
			//если задан атрибут property, то берем его значение.
			result = super.buildContent();
		}
		else if (bodyContent != null)
		{
			//... иначе - значение body
			result = bodyContent.getString().trim();
			bodyContent.clearBody();
		}
		if (footerType != null && !"text".equals(footerType))
		{
			((TemplatedCollectionTag) collectionTag).storeColumnData(String.valueOf(result));
		}
		return result;
	}

	@Override public int doStartLayoutTag() throws JspException
	{
		TemplatedCollectionTag tempCollectionTag = (TemplatedCollectionTag) findAncestorWithClass(this, TemplatedCollectionTag.class);
		collectionTag = tempCollectionTag;
		skip = !tempCollectionTag.getTemplateManager().isVisible(styleId);

		if (skip)
		{

			return SKIP_BODY;
		}
		else
		{
			context.reset();
			return EVAL_BODY_BUFFERED;
		}
	}

	@Override public int doEndLayoutTag() throws JspException
	{
		((TemplatedCollectionTag) collectionTag).setColumnBeforeData(beforeData);
		((TemplatedCollectionTag) collectionTag).setColumnFooterType(footerType);
		((TemplatedCollectionTag) collectionTag).setColumnHidden(hidden);
		((TemplatedCollectionTag) collectionTag).setColumnHeaderClass(styleTitleClass);
		if (StringHelper.isNotEmpty(sortProperty))
			((TemplatedCollectionTag) collectionTag).setColumnSortProperty(sortProperty);

		int result = super.doEndLayoutTag();
		release();
		return result;
	}

	@Override public void release()
	{
		super.release();
		styleId = null;
		bodyContent = null;
		footerType = null;
		value = null;
		hidden = false;
		valueSpecified = false;
		filter=false;
	}

	public void setBodyContent(BodyContent bodyContent)
	{
		this.bodyContent = bodyContent;
	}

	public void doInitBody() throws JspException
	{
		// do nothing.
	}

	public int doAfterBody() throws JspException
	{
		// do nothing.
		return SKIP_BODY;
	}

	public void setBeforeData(String beforeData)
	{
		this.beforeData = beforeData;
	}

	public String getBeforeData()
	{
		return beforeData;
	}

	public String getFooterType()
	{
		return footerType;
	}

	public void setFooterType(String footerType)
	{
		this.footerType = footerType;
	}

	public boolean isHidden()
	{
		return hidden;
	}

	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
		valueSpecified = true;
	}
}