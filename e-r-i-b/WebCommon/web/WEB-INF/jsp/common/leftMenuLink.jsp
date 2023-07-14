<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
	ƒл€ работы необходим скрипт WebResources/web/scripts/commandButton.js

	ѕараметры
	enabled     - ссылка активна если true
	action      - то же что и html:link action, ссылка по которой осуществл€етс€ переход

	text      - текст отображаемый в ссылке
	title     - всплывающа€ подсказка
--%>

<!-- герераци€ последовательных id -->
<c:set var="leftMenuLink_linkId" value="${leftMenuLink_linkId + 1}" scope="request"/>

<%
	Object  needSaveObj = request.getAttribute("needSave");
	boolean needSave    = needSaveObj != null && Boolean.parseBoolean(needSaveObj.toString());
	String  action      = (String) pageContext.getAttribute("action");
	// TODO - remove. ѕараметр href DEPRECATED
	String  oldHref     = (String) pageContext.getAttribute("href");
	String  onclickScript;
	if(oldHref != null)
	{
		onclickScript = oldHref + "; return false;";
	}
	else if (needSave)
	{
		boolean forcePostBack = action != null && action.indexOf("$$") > 0; // есть незаполненные параметры
		onclickScript = "return saveAndRedirectOld('" + action + "', " + forcePostBack + ")";
	}
	else
	{
		onclickScript = null;
	}
%>

<c:if test="${not enabled}">
    <table cellpadding="0" cellspacing="0" width="185px">
    <tr>
        <td width="8px">&nbsp;</td>
        <td class="disableLink">
            <a id="a${leftMenuLink_linkId}" disabled="disabled">
		        <span id="spanA${leftMenuLink_linkId}" class="menuInsertText">${phiz:substring(text,0,1)}</span>${phiz:substring(text,1,99)}
	        </a>
        </td>
    </tr>
    </table>
</c:if>
<c:if test="${enabled}">
    <table cellpadding="0" cellspacing="0" width="180px">
    <tr>
        <td width="10px">&nbsp;</td>
        <td width="1px" class="insetSide"><img src="${globalUrl}/images/1x1.gif" border="0" width="4px" height="18px"></td>
        <td class="insetBody linkIndent"
            onmouseover="javascript:this.className = 'mOverHref linkIndent';"
	        onmouseout="javascript:this.className = 'insetBody linkIndent';">
          <html:link
	        title="${title}"
	        href="<%=action == null ? oldHref : null%>"
	        action="<%=action%>"
            onclick="<%=onclickScript%>"
	        style="cursor:pointer;width:93%;height:100%;"
		  >
		  <span id="spanA${leftMenuLink_linkId}"  class="menuInsertText">${phiz:substring(text,0,1)}</span>${phiz:substring(text,1,99)}
	      </html:link>
        </td>
    </tr>
    </table>
</c:if>