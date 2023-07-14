<%@ page import="com.rssl.phizic.web.actions.StrutsUtils" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
	Параметры
	enabled     - ссылка активна если true
	action      - ссылка по которой осуществляется переход

	text      - текст отображаемый в ссылке
	title     - всплывающая подсказка

	parentName - id родительской вкладки, если входит в leftMenuInsetGroup
--%>
<%
	Object needSaveObj = request.getAttribute("needSave");
	boolean needSave = needSaveObj != null && Boolean.parseBoolean(needSaveObj.toString());
	String action = (String) pageContext.getAttribute("action");
	String onclickScript = "if (!redirectResolved()) return false; ";
	if (needSave)
	{
		boolean forcePostBack = action != null && action.indexOf("$$") > 0; // есть незаполненные параметры
		onclickScript += "return saveAndRedirect('" + StrutsUtils.calculateActionURL(pageContext, action) + "','"+action+"', " + forcePostBack + ");";
	}
	else
	{
		onclickScript += "window.location='"+StrutsUtils.calculateActionURL(pageContext, action)+"';";
	}
%>

<c:choose>
    <c:when test="${enabled}"><div class="subMInactiveInset"><span class="subMTitle"
                                                                   <c:choose>
                                                                       <c:when test="${not empty onclick}">
                                                                           onclick="${onclick}"
                                                                       </c:when>
                                                                       <c:otherwise>
                                                                           onclick="loadNewAction('lmi${action}', 'lmInset');<%=onclickScript%>"
                                                                       </c:otherwise>
                                                                   </c:choose>>

        <i>${text}</i></span>${data}</div>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty parentName}">
            <input type='hidden' id='subMenuName' value='${parentName}'/>
        </c:if>
        <c:choose>
            <c:when test="${empty forceOnclick || forceOnclick == 'false'}">
                <div class='subMActiveInset' id='subMActiveInset'><span class="subMTitle" onclick="<%=onclickScript%>"><i>${text}</i></span>${data}</div>
            </c:when>
            <c:otherwise>
                <div class='subMActiveInset' id='subMActiveInset'><span class="subMTitle" onclick="${onclick}"><i>${text}</i></span>${data}</div>
            </c:otherwise>
        </c:choose>

    </c:otherwise>
</c:choose>
