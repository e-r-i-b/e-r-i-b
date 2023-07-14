package com.rssl.phizic.web.tags;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.security.PageTokenUtil;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Egorova
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class VisibleFormTag extends FormTag
{
	private static final String FORM_ON_SUBMIT = "FormOnSubmit";

	private static final String FORM_ACTION = "FormAction";

	private static final String FORM_ACTION_URL = "FormActionURL";

	private static final String FORM_ENCTYPE = "FormEnctype";

	private boolean show = false;

	private boolean updateAtributes = true;

	private String actionURL;

	private String autocomplete = "off"; //атрибут autocomplete

	private boolean appendToken = false;

	///////////////////////////////////////////////////////////////////////////

	public boolean isShow()
	{
		return show;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

	public boolean isUpdateAtributes()
	{
		return updateAtributes;
	}

	public void setUpdateAtributes(boolean updateAtributes)
	{
		this.updateAtributes = updateAtributes;
	}

	public boolean isAppendToken()
	{
		return appendToken;
	}

	public void setAppendToken(boolean appendToken)
	{
		this.appendToken = appendToken;
	}

	public int doStartTag() throws JspException
	{
		// –ассчитываем полное значение action
		if (action != null)
			actionURL = StrutsUtils.calculateActionURL(pageContext, action);

		ServletRequest request = pageContext.getRequest();
		if (isShow())
		{
			String formAction = (String) request.getAttribute(FORM_ACTION);
			if(formAction != null)
			{
				action = formAction;
				actionURL = (String) request.getAttribute(FORM_ACTION_URL);;
				setOnsubmit( (String) request.getAttribute(FORM_ON_SUBMIT) );
				setEnctype((String) request.getAttribute(FORM_ENCTYPE) );
			}
			if (action == null)
				return EVAL_BODY_INCLUDE;
			else return super.doStartTag();
		}
		else
		{
			// Look up the form bean name, scope, and type if necessary
			this.lookup();

			if (appendToken)
				TagUtils.getInstance().write(pageContext, this.renderToken());

			// Store this tag itself as a page attribute
			pageContext.setAttribute(Constants.FORM_KEY, this, PageContext.REQUEST_SCOPE);
			if (updateAtributes)
			{
				request.setAttribute(FORM_ACTION, action);
				request.setAttribute(FORM_ACTION_URL, actionURL);
				request.setAttribute(FORM_ON_SUBMIT, getOnsubmit());
				request.setAttribute(FORM_ENCTYPE, getEnctype());
			}
			
			this.initFormBean();

			return (EVAL_BODY_INCLUDE);
		}
	}

	@Override
	protected void renderAction(StringBuffer results)
	{
		if (actionURL != null)
		{
			results.append(" action=\"");
			results.append(actionURL);
			results.append("\"");
		}
		else super.renderAction(results);
	}

	public int doEndTag() throws JspException
	{
		if (!isShow() || action == null)
			return EVAL_PAGE;

		PageTokenUtil.addToken(pageContext.getOut());
		return super.doEndTag();
	}
	public String getAutocomplete()
	{
		return autocomplete;
	}

	public void setAutocomplete(String autocomplete)
	{
		this.autocomplete = autocomplete;
	}

	protected void renderOtherAttributes(StringBuffer results)
	{
		if (StringHelper.isNotEmpty(autocomplete))
		{
			results.append(" autocomplete=\"" + autocomplete + "\"");
		}
    }
}
