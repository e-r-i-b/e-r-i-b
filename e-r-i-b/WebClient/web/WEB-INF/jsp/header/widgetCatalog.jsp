<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
    Каталог виджетов, доступных пользователю для установки на страницу 
--%>
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<script type="text/javascript" src="${globalUrl}/scripts/CitiesDictionaryUtils.js"></script>
<html:form action="/private/async/widgetCatalog">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="personWidgetDefinitions" value="${form.personWidgetDefinitions}"/>
    <c:if test="${not empty personWidgetDefinitions}">
        <div data-dojo-type="widget.WidgetCatalog">
            <div class="button-widget-catalog left-button-widget-catalog">
                <img src='${commonImagePath}/widget-catalog-to-left.gif'/>
            </div>
            <%-- Табличка с виджетами --%>
            <div class="widget-catalog-border">
                <div class="scrollbar">
                    <div class="scroll-arrow-left"></div>
                    <div class="track">
                        <div class="thumb">
                            <div class="end"></div>
                        </div>
                    </div>
                    <div class="scroll-arrow-right"></div>
                </div>
                <c:set var="count" value="0"/>
                <c:forEach var="personWidgetDefinition" items="${personWidgetDefinitions}">
                    <c:if test="${phiz:impliesOperation(personWidgetDefinition.definition.operation, null)&&(personWidgetDefinition.definition.availability)}">
                        <c:set var="count" value="${count + 1}"/>
                    </c:if>
                </c:forEach>
                <c:choose>
                    <c:when test="${count % 2 == 0}">
                        <c:set var="columnCount" value="${count / 2}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="columnCount" value="${(count + 1) / 2}"/>
                    </c:otherwise>
                </c:choose>
                <div class="viewport">
                    <div class="overview">
                        <table>
                            <tr>
                                <c:set var="counter" value="0"/>
                                <c:forEach var="personWidgetDefinition" items="${personWidgetDefinitions}">
                                    <c:if test="${phiz:impliesOperation(personWidgetDefinition.definition.operation, null)&&(personWidgetDefinition.definition.availability)}">
                                        <td>
                                            <tiles:insert page="/WEB-INF/jsp/header/widgetPicture.jsp" flush="false">
                                                <tiles:put name="codename" value="${personWidgetDefinition.definition.codename}"/>
                                                <tiles:put name="username" value="${personWidgetDefinition.definition.username}"/>
                                                <tiles:put name="picture" value="${personWidgetDefinition.definition.picture}"/>
                                                <tiles:put name="description" value="${personWidgetDefinition.definition.description}"/>
                                                <tiles:put name="path" value="${personWidgetDefinition.definition.path}"/>
                                                <tiles:put name="initialSize" value="${personWidgetDefinition.definition.initialSize}"/>
                                                <tiles:put name="sizeable" value="${personWidgetDefinition.definition.sizeable}"/>
                                                <tiles:put name="isNovelty" value="${personWidgetDefinition.novelty}"/>
                                                <tiles:put name="definition" value="${personWidgetDefinition.definitionAsJson}"/>
                                            </tiles:insert>
                                        </td>
                                        <c:set var="counter" value="${counter + 1}"/>
                                        <c:if test="${counter == columnCount}">
                                            </tr>
                                            <tr>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="button-widget-catalog right-button-widget-catalog">
                <img src='${commonImagePath}/widget-catalog-to-right.gif'/>
            </div>
        </div>
    </c:if>
</html:form>
