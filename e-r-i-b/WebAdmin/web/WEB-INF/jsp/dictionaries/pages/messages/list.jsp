<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/inform/message/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="informMessageList">
        <tiles:put name="submenu" type="string" value="InformMessages"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="list.title" bundle="informMessagesBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditInformMessagesOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="informMessagesBundle"/>
                <tiles:put name="action"  value="/inform/message/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <%-- Фильтр --%>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.key.word"/>
                <tiles:put name="bundle" value="informMessagesBundle"/>
                <tiles:put name="name" value="keyWord"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>
        </tiles:put>

        <%-- Данные --%>
        <tiles:put name="data" type="string">

            <%-- Таблица с данными --%>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="InformMessagesList"/>

                <%-- Данные таблицы --%>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="informMessagesBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.published">
                            <c:if test="${not empty listElement and not empty listElement.startPublishDate}">
                               c&nbsp;<bean:write name="listElement" property="startPublishDate.time" format="dd.MM.yyyy"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${not empty listElement and not empty listElement.cancelPublishDate}">
                                    по&nbsp;<bean:write name="listElement" property="cancelPublishDate.time" format="dd.MM.yyyy"/>
                                </c:when>
                                <c:otherwise>
                                    <bean:message key="label.indefinitely" bundle="informMessagesBundle"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.pages">
                            <c:if test="${not empty listElement and not empty listElement.pages}">
                                ${phiz:pagesToString(listElement.pages, 40)}
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.type">
                            <c:if test="${not empty listElement}">
                               <c:choose>
                                   <c:when test="${listElement.vievType == 'POPUP_WINDOW'}">
                                        <bean:message key="label.popup" bundle="informMessagesBundle"/>
                                   </c:when>
                                   <c:otherwise>
                                        <bean:message key="label.static" bundle="informMessagesBundle"/>
                                   </c:otherwise>
                               </c:choose>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.tb">
                            <c:if test="${not empty listElement and not empty listElement.departments}">
                                ${phiz:departmentsToString(listElement.departments, 25)}
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.text">
                            <c:if test="${not empty listElement and not empty listElement.text}">
                                    <c:set var="text" value="${listElement.text}"/>
                                    <c:if test="${fn:length(text) > 40}">
                                        <c:set var="text" value="${phiz:substring(text, 0, 40)}..."/>
                                    </c:if>
                                    <phiz:link
                                          action="/inform/message/view"
                                          operationClass="ViewInformMessagesOperation"
                                          >
                                          <phiz:param name="id" value="${listElement.id}"/>
                                          <c:out value="${text}"/>
                                    </phiz:link>                                                                
                           </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.importance">
                            <c:if test="${not empty listElement and not empty listElement.importance}">
                                <c:choose>
                                    <c:when test="${listElement.importance == 'HIGH'}">
                                        <bean:message key="label.importance.high" bundle="informMessagesBundle"/>
                                    </c:when>
                                    <c:when test="${listElement.importance == 'MEDIUM'}">
                                        <bean:message key="label.importance.medium" bundle="informMessagesBundle"/>
                                    </c:when>
                                    <c:when test="${listElement.importance == 'LOW'}">
                                        <bean:message key="label.importance.low" bundle="informMessagesBundle"/>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                
                <%-- Кнопки --%>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveInformMessagesOperation">
                            <tiles:put name="commandKey"     value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove.help"/>
                            <tiles:put name="bundle"         value="informMessagesBundle"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection('selectedIds', 'Выберите сообщения для снятия с публикации!');
                                }
                            </tiles:put>
                            <tiles:put name="confirmText"><bean:message key="confirm.text" bundle="informMessagesBundle"/></tiles:put>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>

        </tiles:put>
        
    </tiles:insert>
</html:form>