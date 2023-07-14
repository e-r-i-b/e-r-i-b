<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>

<tiles:importAttribute/>
<%--@elvariable id="skinUrl" type="java.lang.String"--%>
<c:set var="imagePath" value="${skinUrl}/images"/>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.component.WidgetContainerForm"--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--@elvariable id="container" type="com.rssl.phizic.business.web.WebPage"--%>
<c:set var="container" value="${form.container}"/>
<c:set var="containerCodename" value="${form.codename}"/>
<c:url var="containerURL" value="${phiz:calculateActionURL(pageContext, form.containerActionPath)}" context="/">
    <c:param name="codename" value="${containerCodename}"/>
</c:url>

<div class="sortable-handle sortable-light"></div>

<c:if test="${containerCodename == 'main'}">
    <%@ include file="/WEB-INF/jsp-sbrf/accounts/advertisingBlocks.jsp"%>
</c:if>

<div class="light sortable-light">
    <div data-dojo-type="widget.WidgetContainer" class="widget-container">
        <script type="dojo/connect">
            this.codename = "${containerCodename}";
            this.location = "${container.location}";
            this.url = "${containerURL}";
            this.widgets = ${container.layout};
            this.loginId = ${form.login.id};
        </script>

        <c:forEach var="widget" items="${container.widgets}" varStatus="status">
            <c:set var="widgetURL" value="${phiz:calculateActionURL(pageContext, widget.definition.path)}"/>
            <c:catch var="errorJSP">
                <c:set var="widgetProps">
                    codename:   "${widget.codename}",
                    title:      "${widget.title}",
                    size:       "${widget.size}",
                    sizeable:   "${widget.definition.sizeable}",
                    url:        "${widgetURL}",
                    loadMode:   "${widget.definition.loadMode}",
                    definition: ${widget:definitionAsJson(widget.definition)},
                    ${props}
                </c:set>
                <div data-dojo-type="widget.WidgetWindow" class="WidgetWindow" data-dojo-props="${widget:escapeDataDojoProps(widgetProps)}">
                    <c:if test="${widget.definition.loadMode == 'sync'}">
                        <c:url var="actionURL" value="${widget.definition.path}" context="/">
                            <c:param name="page" value="${containerCodename}"/>
                            <c:param name="codename" value="${widget.codename}"/>
                            <c:param name="operation" value=""/>
                        </c:url>
                        <tiles:insert page="${actionURL}" flush="false"/>
                    </c:if>
                </div>
            </c:catch>

            <c:if test="${not empty errorJSP}">
                <%-- TODO(виджеты): что делаем с ошибкой синхронной загрузки виджета? --%>
                ${phiz:writeLogMessage(errorJSP)}
            </c:if>
        </c:forEach>

        <%-- Вице-виджет - ВрИО виджета пока тот не загружен --%>
        <div style="display:none">
            <div class="widget ViceWidget">
                <%@ include file="/WEB-INF/jsp/widget/viceWidgetBody.jsp"%>
            </div>
        </div>
    </div>
</div>
