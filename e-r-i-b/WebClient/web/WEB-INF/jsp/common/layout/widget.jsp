<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>

<tiles:importAttribute/>

<%--
    Виджет
    digitClassname - имя диджит-класса
    cssClassname - имя CSS-класса
    props - json-строка с параметрами виджета
    sizeable - флажок "виджет может менять размер"
    viewPanel - html-фрагмент для режима отображения виджета
    editPanel - html-фрагмент для режима редактирования виджета
    borderColor - цвет рамки
    title - заголовок (иначе берём с формы)
    linksControl - панель ссылок в шапке
    additionalSetting - дополнительные настройки виджета
--%>

<%--@elvariable id="digitClassname" type="java.lang.String"--%>
<%--@elvariable id="cssClassname" type="java.lang.String"--%>
<%--@elvariable id="props" type="java.lang.String"--%>
<%--@elvariable id="title" type="java.lang.String"--%>
<%--@elvariable id="widgetForm" type="com.rssl.phizic.web.component.WidgetForm"--%>

<%-- Форм-бин виджета (ожидается WidgetForm или его наследник) --%>
<c:set var="widgetForm" value="${phiz:currentForm(pageContext)}"/>

<c:if test="${empty title}">
    <c:set var="title" value="${widgetForm.title}"/>
</c:if>

<c:set var="widgetProps">
    codename: "${widgetForm.codename}",
    settings: ${widgetForm.settings},
    sizeable: ${widgetForm.widget.definition.sizeable},
    resourcesRealPath: '${initParam.resourcesRealPath}',
    ${props}
</c:set>

<div data-dojo-type="${digitClassname}"
     data-dojo-props="${widget:escapeDataDojoProps(widgetProps)}"
     class="widget ${cssClassname}">

    <tiles:insert page="widgetBody.jsp" flush="false">
        <tiles:put name="borderColor" value="${borderColor}"/>
        <tiles:put name="sizeable" value="${sizeable}"/>
        <tiles:put name="editable" value="${editable}"/>
        <tiles:put name="viewPanel" value="${viewPanel}"/>
        <tiles:put name="editPanel" value="${editPanel}"/>
        <tiles:put name="title" value="${title}"/>
        <tiles:put name="linksControl" value="${linksControl}"/>
        <tiles:put name="additionalSetting" value="${additionalSetting}"/>
    </tiles:insert>
</div>
