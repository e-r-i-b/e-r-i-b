package com.rssl.phizic.web.tags;

import javax.servlet.jsp.*;

/**
 * User: IIvanova
 * Date: 12.04.2006
 * Time: 14:17:55
 */
public class MenuLinkTag extends LinkTag {
    private String id=null;
    private String align=null;

    public int doEndTag() throws JspException {
       StringBuffer result = new StringBuffer();
       if (hasAccess()) {
         if( align != null ) {
            result.append("<span align='");
            result.append(align);
            result.append("' style='float:");
            result.append(align);
            result.append(";'>");
         }
         result.append("<a");
         setAttribute(result,"href", "#");
	     setAttribute(result,"onclick", "loadNewAction('','');window.location='"+makeUrl()+"';");
         result.append(" onmouseover=\"javascript:linkFocus(this,'");
         result.append(id);
         result.append("')\"");
         result.append(" onmouseout=\"javascript:linkUnFocus(this,'");
         result.append(id);
         result.append("')\"");
         result.append(">");
         setImageTag(result,"circle.gif","border='0' id='img" + id + "'");
         result.append("&nbsp;");
         result.append("<span ");
         result.append("class='menuInsertText' id='spanA");
         result.append(id);
         result.append("'>");
         String text = getBodyContent().getString().trim();
         result.append(text.substring(0,1));
         result.append("</span>");
         result.append(text.substring(1));
         result.append("</a>");
         if( align != null ) {
           result.append("</span>");
         }
         outJsp(result);
       }
        release();
        return EVAL_PAGE;
       }

    public void setId(String id) {
        this.id = id;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void release() {
           id=null;
           super.release();
       }
}
