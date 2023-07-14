<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

    <tiles:importAttribute name="needSave" ignore="true" scope="request"/>
    <%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp" %>

    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="image" value="${globalUrl}/commonSkin/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="isGuest" value="${guest == 'true'}"/>
    <body>
    <tiles:insert definition="googleTagManager"/>
    <script type="text/javascript">
        <%-- Функция аналог атрибуту onload в body. Данный вариант необходим в связи с
            организацией единой очереди. В случае прямого модифицирования атрибута onload
            часть элементов из очереди могут пропасть--%>
        function onLoad(event)
        {
            <%=request.getAttribute("onLoad")%>;
            // инициализируем блок "ожидание"
            clientBeforeUnload.init();
        }
        doOnLoad(onLoad);
    </script>

    <div data-dojo-type="widget.WebPage" class="fonContainer WebPage" id="pageContent">
        <script type="dojo/connect">
            this.loginURL = "${phiz:calculateActionURL(pageContext, "/login")}";
            this.url = "${phiz:calculateActionURL(pageContext, "/private/async/webpage")}";
        </script>

        <c:catch var="errorJSP">
            <tiles:importAttribute name="headerGroup" ignore="true"/>
            <tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>
            <tiles:importAttribute name="messagesBundle" ignore="true"/>
            <tiles:importAttribute name="showSlidebar" ignore="true"/>
            <tiles:importAttribute name="haveWidgetMainContainer" ignore="true"/>
        </c:catch>
            <html:form show="true">
                <c:catch var="errorJSP">
                <c:if test="${headerGroup == 'true'}">
                    <c:if test="${showSlidebar == 'true' && widget:isAvailableWidget() && !widget:isOldBrowser()}">
                        <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/slidebar.jsp" flush="false">
                            <tiles:put name="haveWidgetMainContainer" value="${haveWidgetMainContainer}"/>
                        </tiles:insert>
                    </c:if>
                </c:if>
                </c:catch>

                <div class="pageBackground">
                    <div class="pageContent">
                        <c:catch var="errorJSP">
                        <%@ include file="header.jsp" %>
                        </c:catch>
                        <div id="wrapper">
                            <c:catch var="errorJSP">
                            <c:if test="${needMobileBanner == 'true'}">
                                <%@ include file="mobileBanner.jsp" %>
                            </c:if>
                            </c:catch>
                            <div id="${headerGroup == 'false'?'dictionaryList':'content'}"
                                 class="${rightSection == 'false'?'noRightSection':''} ${addStyleClass}">
                                <ul id="sortable">
                                    <c:choose>
                                        <c:when test="${'left' == phiz:sidemenuLocation()}">
                                            <c:catch var="errorJSP">
                                            <li class="sortable-li">
                                                <%@ include file="/WEB-INF/jsp-sbrf/rightSection.jsp" %>
                                            </li>
                                            <li class="sortable-li">
                                                <%@ include file="workspace.jsp" %>
                                            </li>
                                            </c:catch>
                                        </c:when>
                                        <c:otherwise>
                                            <c:catch var="errorJSP">
                                            <li class="sortable-li">
                                                <%@ include file="workspace.jsp" %>
                                            </li>
                                            <li class="sortable-li relative">
                                                <%@ include file="/WEB-INF/jsp-sbrf/rightSection.jsp" %>
                                            </li>
                                            </c:catch>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                                <div class="clear"></div>
                                <%-- end content--%>
                            </div>
                        </div>
                    </div>
                </div>
            </html:form>

        <c:if test="${not empty errorJSP}">
            ${phiz:processExceptionEntry(errorJSP,pageContext)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>
    </div>
    <c:if test="${not empty headerGroup and headerGroup == 'true'}">
        <%@ include file="footer.jsp" %>
    </c:if>
    </body>
</html:html>
