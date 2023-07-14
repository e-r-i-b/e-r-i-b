package com.rssl.phizic.web.tags;

import javax.servlet.jsp.*;

/**
 * User: IIvanova
 * Date: 12.04.2006
 * Time: 16:49:53
 */
public class ServiceTag extends CheckAccessTag {
    private String name=null;

      public int doEndTag() throws JspException {
       if (hasAccess()) {
         StringBuffer result = new StringBuffer();
         result.append("<font class='listPayment'>");
         result.append(name);
         result.append("</font><font class='aPayment'>");
         result.append(getBodyContent().getString().trim());
         result.append("</font>");
         outJsp(result);
       }
        release();  
        return EVAL_PAGE;
       }

    public void setName(String name) {
        this.name = name;
    }

    public void release() {
           name=null;
           super.release();
       }
}
