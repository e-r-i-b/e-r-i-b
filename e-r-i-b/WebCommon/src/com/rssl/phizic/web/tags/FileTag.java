package com.rssl.phizic.web.tags;

import org.apache.struts.taglib.html.BaseFieldTag;
import org.apache.struts.taglib.TagUtils;

import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 26.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class FileTag extends BaseFieldTag
{
	private String buttonName = "Выгрузить";
	private String property;
	private String fileName;
	private String fileId;
	private String size = "30";
	private String styleClass ="";
	private String uploadAction = "button.upload";
	private String defaultValue = "";


	protected void setAttribute(StringBuffer result, String name, String value) {
           if (value != null) {
               result.append(" ");
               result.append(name);
               result.append("=\"");
               result.append(value);
               result.append("\"");
           }
    }

	public int doStartTag() throws JspException
	{
		StringBuffer result = new StringBuffer();
		result.append("<div style=\"position: relative;\">\n");
		//input
		result.append("<input type=\"text\" style=\"position:absolute;\"");
		setAttribute(result,"id",property+"-file-name");
		setAttribute(result,"size",size);
		setAttribute(result,"value",getFileName());
		setAttribute(result,"readonly","true");
		setAttribute(result,"class",styleClass);
		setAttribute(result,"name",fileName);
		result.append("/>\n");

		//file
		result.append("<input type=\"file\"");
		setAttribute(result,"id",property);
		setAttribute(result,"name",property);
		setAttribute(result,"size",size);
		setAttribute(result,"class",styleClass);
		setAttribute(result,"onchange","change"+property+"Value();");
		result.append("/>\n");

		//button
		if(!"".equals(getFileId()) && !"0".equals(getFileId()))
		{
			result.append("<input type=\"button\"");
			setAttribute(result,"value",buttonName);
			setAttribute(result,"onclick","upload"+property+"();");
			setAttribute(result,"class",styleClass);
			result.append("/>\n");
		}

		//script
		result.append("<script type=\"text/javascript\">\n");
		result.append("var button = createCommandButton('"+uploadAction+"', '"+buttonName+"');\n");
		if("".equals(getFileId()) || "0".equals(getFileId()))		
		{
			result.append("if (navigator.appName=='Microsoft Internet Explorer')\n");
		}
		result.append("document.getElementById('"+property+"-file-name').style.top=\"1\";\n");
		result.append("function upload"+property+"(){\n");
		result.append("findCommandButton('"+uploadAction+"').click();}\n");
		result.append("function change"+property+"Value(){\n");
		result.append("var filePath = document.getElementById('"+property+"');\n");
		result.append("var fileName = document.getElementById('"+property+"-file-name');\n");
		result.append("fileName.value = filePath.value.split('/').pop().split('\\\\').pop();}\n");
		result.append("</script>\n");
		result.append("</div>\n");
		outJsp(result);
		return EVAL_PAGE;
	}

	private void outJsp(StringBuffer result) {
        try {
            pageContext.getOut().print(result.toString());
        } catch (IOException e){}
    }

	private String getFileName() throws JspException
	{
		Object value =
            TagUtils.getInstance().lookup(pageContext, name, fileName, null);
		if(value == null)
			value = defaultValue;
		return this.formatValue(value);
	}

	private String getFileId() throws JspException
	{
		Object value =
            TagUtils.getInstance().lookup(pageContext, name, fileId, null);
		if(value == null)
			value = defaultValue;
		return this.formatValue(value);
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public void setUploadAction(String uploadAction)
	{
		this.uploadAction = uploadAction;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public void setFileId(String fileId)
	{
		this.fileId = fileId;
	}
	
}
