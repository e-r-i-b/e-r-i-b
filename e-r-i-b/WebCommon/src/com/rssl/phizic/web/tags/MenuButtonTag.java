package com.rssl.phizic.web.tags;

import javax.servlet.jsp.JspException;

/** User: IIvanova Date: 10.04.2006 Time: 16:18:55 */
public class MenuButtonTag extends CheckAccessTag
{
	private String id = null;
	private String onclick = null;
	private String operationKey = null;
	private String action = null;
	private String img = null;
	private String confirm = null;
	private String groupError = null;

	private void setActionAttribute(StringBuffer result)
	{
		if (onclick == null && action == null)
		{
			StringBuffer res = new StringBuffer();
			if (groupError == null)
			{
				res.append("javascript:callOperation(event,'");
				res.append(getMessage(operationKey, bundle));
				res.append("'");
				if (confirm != null)
				{
					res.append(",'");
					res.append(confirm);
					res.append("'");
				}
				res.append(");");
			}
			else
			{
				res.append("javascript:callGroupOperation(event,'");
				res.append(getMessage(operationKey, bundle));
				res.append("','");
				if (confirm != null)
				{
					res.append(confirm);
				}
				res.append("','");
				res.append(groupError);
				res.append("');");
			}
			onclick = res.toString();
			setAttribute(result, "onclick", onclick);
		}


		else
		{
			if (action != null)
			{
				//сделать чтобы появлялась тока если есть action
				StringBuffer res = new StringBuffer();
				String fullPath = " document.location = '" + getFullPath();
				res.append(fullPath);
				res.append(action);
				res.append(";return false;");

				onclick = res.toString();
			}
		}
		setAttribute(result, "onclick", onclick);
	}


	public int doEndTag() throws JspException
	{

		if (hasAccess())
		{
			StringBuffer result = new StringBuffer();
			result.append("<button class='butMenu'");
			result.append(" onmouseover=\"buttFocus(this.id);\" ");
			result.append(" onmouseout=\"buttUnFocus(this.id);\" ");
			setAttribute(result, "id", id);
			setTitleAttribute(result);
			setActionAttribute(result);
			result.append(">");
			setImageTag(result, img, "border='0' alt='' width='12px' height='12px'");
			result.append("&nbsp;");
			result.append(getBodyContent().getString().trim());
			result.append("</button>");
			outJsp(result);
		}
		release();
		return EVAL_PAGE;
	}

	public void release()
	{
		id = null;
		onclick = null;
		img = null;
		operationKey = null;
		action = null;
		confirm = null;
		groupError = null;
		super.release();
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public void setOperationKey(String operationKey)
	{
		this.operationKey = operationKey;
	}

	public void setConfirm(String confirm)
	{
		this.confirm = confirm;
	}

	public void setGroupError(String groupError)
	{
		this.groupError = groupError;
	}

	public void setAction(String action)
	{
		this.action = action;
	}
}
