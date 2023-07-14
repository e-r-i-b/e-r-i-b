package com.rssl.phizic.web.tags;

import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.service.group.access.ServicesGroupAccessHelper;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.tiles.PutListTag;
import org.apache.struts.taglib.tiles.PutTag;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Evgrafov
 * @ created 03.11.2006
 * @ $Author: komarov $
 * @ $Revision: 79228 $
 */
public class InsertTag extends org.apache.struts.taglib.tiles.InsertTag
{
	private static final String INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_PREFIX = "IESE_PREFIX_";
	private static final String INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_SUFFIX = "_IESE_SUFFIX";

	private String module;
	private String service;
	private String operation;
	private String group;


	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = StringHelper.getNullIfEmpty(operation);
	}

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = StringHelper.getNullIfEmpty(service);
	}

	/**
	 * @return группы из operations-tree.xml разделЄнные зап€той
	 */
	public String getGroup()
	{
		return group;
	}

	/**
	 * @param group группы из operations-tree.xml разделЄнные зап€той
	 */
	public void setGroup(String group)
	{
		this.group = StringHelper.getNullIfEmpty(group);
	}

	public void release()
	{
		super.release();

		module = null;
		service = null;
		operation = null;
		group = null;
	}



	public int doStartTag() throws JspException
	{
		if(!implies())
			return SKIP_BODY;

		return super.doStartTag();
	}


	public int doEndTag() throws JspException
	{
		if(!implies())
			return EVAL_PAGE;

		try
		{
			return super.doEndTag();
		}
		catch (JspException e)
		{
			//поскольку реб€та из apache struts не кладут в JspException само отлавливаемое исключение,
			//то приходитс€ InactiveExternalSystemException формировать именно таким образом

			if (!e.getMessage().contains(INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_PREFIX))
				throw e;

			String message = e.getMessage();
			int start = INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_PREFIX.length() + message.indexOf(INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_PREFIX);
			int end = message.indexOf(INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_SUFFIX);

			throw new InactiveExternalSystemException(message.substring(start, end));
		}
	}

	public void processNestedTag(PutListTag nestedTag) throws JspException
	{
		if(!implies())
			return;

		super.processNestedTag(nestedTag);
	}

	public void processNestedTag(PutTag nestedTag) throws JspException
	{
		if(!implies())
			return;

		super.processNestedTag(nestedTag);
	}

	protected void doInclude(String page) throws ServletException, IOException
	{
		ServletRequest request = pageContext.getRequest();

		Object currentFormBean = request.getAttribute(Constants.BEAN_KEY);
		try
		{
			super.doInclude(page);
		}
		catch (Exception e)
		{
			//поскольку реб€та из apache struts не кладут в JspException само отлавливаемое исключение,
			//то приходитс€ InactiveExternalSystemException формировать именно таким образом

			Throwable throwable = ExceptionLogHelper.getRootCause(e);
			if (throwable instanceof InactiveExternalSystemException)
			{
				throw new ServletException(INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_PREFIX + throwable.getMessage() + INACTIVE_EXTERNAL_SYSTEM_EXCEPTION_SUFFIX);
			}

			throw new ServletException(e);
		}
		finally
		{
			// ¬осстанавливаем форм-бин страницы, если он был подменЄн
			if (currentFormBean != null)
				request.setAttribute(Constants.BEAN_KEY, currentFormBean);
			else request.removeAttribute(Constants.BEAN_KEY);
		}
	}

	/**
	 * ѕроверить доступ если он определен
 	 * @return true если доступ разрешен
	 */
	private boolean implies()
	{
		if(module != null)
			return PermissionUtil.impliesAccessScheme(module);

		if(service != null && operation == null)
			return PermissionUtil.impliesService(service);

		if(operation != null)
			return PermissionUtil.impliesOperation(operation, service);

		if(StringHelper.isNotEmpty(group))
		{
			return impliesGroup();
		}

		return true;
	}

	private boolean impliesGroup()
	{
		if(!EmployeeContext.isAvailable())
			return false;

		EmployeeData data = EmployeeContext.getEmployeeDataProvider().getEmployeeData();

		if(data == null)
			return false;

		String[] groups = group.split(",");
		for(String g : groups)
			if(data.availableGroup(g))
				return true;
		return false;
	}
}