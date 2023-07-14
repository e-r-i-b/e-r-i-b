<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/settings/technobreaks/auto" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="autoStopSystemEdit"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="submenu" type="string" value="EditAutoStopSettings"/>
       <tiles:put name="pageName">
           <bean:message key="technobreak.auto.edit.main.header" bundle="technobreaksBundle"/>
       </tiles:put>
       <tiles:put name="pageDescription">
           <bean:message key="technobreak.auto.edit.text" bundle="technobreaksBundle"/>
       </tiles:put>
        <tiles:put name="submenu"  value="EditAutoStopSettings"/>
        <tiles:put name="formAlign"  value="center"/>
        <tiles:put name="data" type="string">

                    <table class="standartTable">
                    <tr><th></th>
                        <th>Предельное число ошибок</th>
                        <th>Длительность перерыва</th>
                    </tr>
                    <c:forEach items="${form.systemTypes}" var="system">
                        <tr>
                            <td class="LabelAll"><bean:message key="technobreak.auto.system.type.${system}" bundle="technobreaksBundle"/></td>
                            <td>
                                <tiles:insert definition="propertyInput" flush="false">
                                    <tiles:put name="fieldName" value="com.rssl.iccs.external.system.error.limit.${system}"/>
                                    <tiles:put name="textSize" value="10"/>
                                    <tiles:put name="textMaxLength" value="10"/>
                                </tiles:insert>
                            </td>
                            <td>
                                <tiles:insert definition="propertyInput" flush="false">
                                    <tiles:put name="fieldName" value="com.rssl.iccs.external.system.break.duration.${system}"/>
                                    <tiles:put name="textSize" value="10"/>
                                    <tiles:put name="textMaxLength" value="10"/>
                                    <tiles:put name="fieldDesc" value="мин."/>
                                </tiles:insert>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.allow.offline.payments"/>
                                <tiles:put name="fieldType" value="checkbox"/>
                                <tiles:put name="fieldDesc" value="Разрешить оффлайн-платежи"/>
                            </tiles:insert>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.allow.gfl.for.each.product"/>
                                <tiles:put name="fieldType" value="checkbox"/>
                                <tiles:put name="fieldDesc" value="Разрешить запрос GFL по каждому из продуктов"/>
                            </tiles:insert>
                        </td>
                    </tr>
                    </table>
                </tiles:put>
                <tiles:put name="formButtons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="technobreaksBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
</html:form>