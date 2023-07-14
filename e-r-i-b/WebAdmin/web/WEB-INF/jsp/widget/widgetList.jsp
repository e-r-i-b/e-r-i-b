<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/widgets/management" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="WidgetCatalogSettings"/>
        <tiles:put name="pageTitle" type="string">
            Управление каталогом виджетов
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="head">
                    <td>
                        Отображение
                    </td>
                    <td>
                        Виджет
                    </td>
                    <td>&nbsp;</td>
                    <td>
                        Видимость
                    </td>
                </tiles:put>
                <%--<tiles:put name="helpInf">--%>
                    <%--<div>--%>
                        <%--На данной форме вы можете отметить флажками виджеты, которые будут--%>
                        <%--отображаться в каталоге виджетов клиентам, а также изменить их порядок отображения--%>
                    <%--</div>--%>
                <%--</tiles:put>--%>
                <tiles:put name="data">

                    <tr>
                    <td>
                    <c:set var="definitions" value="${form.widgetDefList}"/>
                    <c:set var="definitionsCount" value="${phiz:size(definitions)}"/>
                    <%--<table style="width:505px">--%>

                    <c:forEach var="widgetDef" items="${definitions}"
                               varStatus="line">

                        <tr>
                            <td>
                                <html:hidden property="sortWidget"
                                             value="${widgetDef.codename}"/>
                                <c:choose>
                                    <c:when test="${line.count > 1}">
                                        <div class="linkUp" style="display: inline;"
                                             title="переместить">&#8593;</div>
                                        <%--<div style="display: inline;"> &nbsp; &nbsp; &nbsp;</div>--%>
                                    </c:when>
                                    <c:otherwise>

                                        <div class="linkUp" style="display: none;"
                                             title="переместить">&#8593;</div>

                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${line.count == 1}">
                                        <div class="linkDown" style="display: inline;"
                                             title="переместить">&#8595;</div>
                                    </c:when>
                                    <c:when test="${line.count < definitionsCount}">
                                        <div class="linkDown" style="display: inline;"
                                             title="переместить">&#8595;</div>
                                    </c:when>
                                    <c:otherwise>

                                        <div class="linkDown" style="display: none;"
                                             title="переместить">&#8595;</div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${widgetDef.username}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${widgetDef.codename == 'Twitter'}">
                                        ID <html:text property="field(twitterID)"/>
                                    </c:when>
                                    <c:otherwise>&nbsp;</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <html:multibox name="form"
                                               property="selectedWidget"

                                               value="${widgetDef.codename}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </td>
                    </tr>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>