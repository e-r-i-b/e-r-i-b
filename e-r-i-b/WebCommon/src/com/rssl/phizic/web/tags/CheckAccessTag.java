package com.rssl.phizic.web.tags;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.messages.MessageConfigRouter;
import com.rssl.phizic.web.servlet.jsp.WebPageContext;
import com.rssl.phizic.security.PermissionUtil;
import org.apache.struts.util.MessageResources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * User: IIvanova
 * Date: 10.04.2006
 * Time: 15:25:16
 */
public class CheckAccessTag extends BodyTagSupport
{
    protected String serviceId=null;
    protected String moduleId=null;
	protected String operationClass=null;
    protected String title=null;
    protected String titleKey=null;
    protected String bundle=null;

    protected String getFullPath ()
    {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
	    return request.getContextPath();
    }

    private String getWebAppInitParameter(String name)
    {
        return pageContext.getServletContext().getInitParameter(name);
    }

    private String makeImageUrl(String img) {

        return getWebAppInitParameter("resourcesRealPath") + "/images/" + img;
    }

    protected String makeActionUrl(String action)
    {
         if (action.startsWith("javascript:"))
             return action;
         return getFullPath() + action + ".do";
    }

	protected String getMessage(String key, String bundle)
	{
		return MessageConfigRouter.getInstance().message(bundle, key);
	}

	protected boolean hasAccess()
	{
		
	    if(StringHelper.isNotEmpty(moduleId))
			return PermissionUtil.impliesAccessScheme(moduleId);

		if(StringHelper.isNotEmpty(serviceId) && StringHelper.isEmpty(operationClass))
			return PermissionUtil.impliesService(serviceId);

		if(StringHelper.isNotEmpty(operationClass))
			return PermissionUtil.impliesOperation(operationClass, serviceId);

        return true;
    }


    
    protected void setAttribute(StringBuffer result, String name, String value)
    {
	    if (value != null)
	    {
		    result.append(" ");
		    result.append(name);
		    result.append("=\"");
		    result.append(value);
		    result.append("\"");
	    }
    }

	protected void setImageTag(StringBuffer result, String img, String options)
	{
		if (img != null)
		{
			result.append("<img ");
			result.append(options);
			setAttribute(result, "src", makeImageUrl(img));
			result.append(">");
		}
	}

	protected void setTitleAttribute(StringBuffer result)
	{
		if (title == null && titleKey != null)
		{
			title = getMessage(titleKey, bundle);
		}
		setAttribute(result, "title", title);
	}

	protected void outJsp(StringBuffer result)
	{
		try
		{
			pageContext.getOut().print(result.toString());
		}
		catch (IOException e)
		{
		}
	}

	public int doStartTag() throws JspException
	{
		// ѕереопредел€ем contextPath в ссылках
		setPageContext(WebPageContext.create(pageContext, null));
		return EVAL_BODY_BUFFERED;
	}

	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setTitleKey(String titleKey)
	{
		this.titleKey = titleKey;
	}

	public void setBundle(String bundle)
	{
		this.bundle = bundle;
	}

	public String getModuleId()
	{
		return moduleId;
	}

	public void setModuleId(String moduleId)
	{
		this.moduleId = moduleId;
	}

	public void setOperationClass(String operationClass)
	{
		//todo заглушка, описание см. LoginFilter.getRedirectURI
		this.operationClass = operationClass;
	}

	public void release()
	{
		serviceId = null;
		moduleId = null;
		title = null;
		titleKey = null;
		bundle = null;
	}
}
