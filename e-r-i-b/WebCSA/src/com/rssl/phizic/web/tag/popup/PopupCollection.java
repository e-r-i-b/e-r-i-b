package com.rssl.phizic.web.tag.popup;

import com.rssl.phizic.utils.StringHelper;

import javax.servlet.ServletException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

/**
 * Коллекция всплывающих окон
 * User: Андрей
 * Date: 27.10.2013
 * Time: 10:33:13
 */
public class PopupCollection extends BodyTagSupport
{
	public static final String SHOW_POPUP_ATTRIBUTE_NAME = PopupItem.class.getName() + ".POPUP_INFO";

	private static final String DEFAULT_POPUP_KEY = "NonFormat";
	private static final String SHOW_POPUP_JS_OPEN = "<script type=\"text/javascript\">window.onload = function(){";
	private static final String SHOW_POPUP_JS_CLOSE = "}</script>";

    private static final String POPUP_TEMPLATE_OPEN =
		    "<div id=\"%s\" class=\"b-popup\"%s>" +
				"<div class=\"popup_inner ugc\">";

	private static final String POPUP_TEMPLATE_CLOSE =
				"</div>" +
			"</div>";

	private static final String ONCLOSE_TEMPLATE = "onclick=\"return function(){ %s return false;}\"";

    private Map<String, PopupEntity> poppupEntities;
	private String defaultUrl;

    public int doStartTag() throws javax.servlet.jsp.JspException
    {
	    poppupEntities = new HashMap<String, PopupEntity>();
        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody() throws javax.servlet.jsp.JspException
    {
        // ничего личшнего не должно быть
        bodyContent.clearBody();

        try
        {
	        // рисуем добавленные окна
            for(String key : poppupEntities.keySet())
            {
	            PopupEntity entity = poppupEntities.get(key);
	            switch (entity.getContentType())
	            {
		            case BODY:
			            bodyContent.print(buildPopup(entity));
			            break;
		            case PAGE:
			            printPagePopup(entity);
			            break;
	            }
            }

            // идентификатор всплывающего окна
	        PopupInfo popupInfo = (PopupInfo) pageContext.getAttribute(SHOW_POPUP_ATTRIBUTE_NAME, PageContext.REQUEST_SCOPE);
	        if(popupInfo != null)
	        {
		        String popupId = popupInfo.getId();
		        // если есть окно с таким идентификатором, то рисуем его
		        if(popupId != null && poppupEntities.containsKey(popupId))
		        {
			        bodyContent.print(buildShowJS(popupId));
		        }
		        else if(StringHelper.isNotEmpty(popupInfo.getDefaultMessage()))
		        {
			        // иначе дефолтное окно, с дефолтным содержанием
			        PopupEntity defaultPopup = new PopupEntity(
					        DEFAULT_POPUP_KEY, popupInfo.getDefaultMessage(), PopupEntity.ContentType.BODY, true, StringHelper.isEmpty(defaultUrl) ? null : ("window.location.href = '" + defaultUrl + "';"));

			        bodyContent.print(buildPopup(defaultPopup));
			        bodyContent.print(buildShowJS(defaultPopup.getId()));
		        }
	        }

            bodyContent.writeOut(getPreviousOut());
        }
        catch (IOException e)
        {
            throw new JspException(e.getMessage(), e);
        }

        return SKIP_BODY;
    }

    private String buildPopup(PopupEntity entity)
    {
	    return new StringBuilder(String.format(POPUP_TEMPLATE_OPEN, entity.getId(), getPopupParams(entity)))
			    .append(entity.getContent())
			    .append(buildControl(entity))
			    .append(POPUP_TEMPLATE_CLOSE)
			    .toString();
    }

	private String getPopupParams(PopupEntity entity)
	{
		return entity.isClosable() ? " " : " onclick=\"return {closable : false}\"";
	}

	private String buildControl(PopupEntity entity)
	{
		StringBuilder builder = new StringBuilder("<a class=\"popup_close\" title=\"Закрыть\"");
		if(!entity.isClosable())
		{
			builder.append(" style=\"display : none\"");
		}
		else if(StringHelper.isNotEmpty(entity.getOnclose()))
		{
			builder.append(" ").append(String.format(ONCLOSE_TEMPLATE, entity.getOnclose()));
		}
		builder.append("></a>");

		return builder.toString();
	}

	private void printPagePopup(PopupEntity entity) throws JspException
	{
		try
		{
			bodyContent.print(String.format(POPUP_TEMPLATE_OPEN, entity.getId(), getPopupParams(entity)));
			pageContext.include(entity.getContent());
			bodyContent.print(buildControl(entity));
			bodyContent.print(POPUP_TEMPLATE_CLOSE);
		}
		catch (ServletException e)
		{
			throw new JspException(e.getMessage(), e);
		}
		catch (IOException e)
		{
			throw new JspException(e.getMessage(), e);
		}
	}

	private String buildShowJS(String id)
	{
		return new StringBuilder(SHOW_POPUP_JS_OPEN)
				.append(String.format("utils.showPopup('%s', 0, true);", id))
				.append(SHOW_POPUP_JS_CLOSE)
				.toString();
	}

    public int doEndTag() throws javax.servlet.jsp.JspException
    {
        return EVAL_PAGE;
    }

	/**
	 * Добавить всплывающее окнл
	 * @param popupEntity сущность всплывающего окна вслпывающего окна
	 */
    public void addPoppupItem(PopupEntity popupEntity)
    {
	    poppupEntities.put(popupEntity.getId(), popupEntity);
    }

    public void release()
    {
	    poppupEntities = null;
    }

	public String getDefaultUrl()
	{
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl)
	{
		this.defaultUrl = defaultUrl;
	}
}
