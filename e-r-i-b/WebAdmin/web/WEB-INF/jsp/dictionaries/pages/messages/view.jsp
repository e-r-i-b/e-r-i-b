<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/inform/message/view">
    <tiles:insert definition="informMessageEdit">
        <tiles:put name="submenu" type="string" value="InformMessages"/>

        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="canEdit" value="${form.canEdit}"/>
        <c:set var="informMessage" value="${form.informMessage}"/>
        <c:set var="pages" value="${informMessage.pages}"/>
        <c:set var="departments" value="${informMessage.departments}"/>
        <c:set var="dateFrom" value="${informMessage.startPublishDate.time}"/>
        <c:set var="dateTo" value="${informMessage.cancelPublishDate.time}"/>
        <c:set var="importance" value="${informMessage.importance}"/>
        <c:set var="text" value="${informMessage.text}"/>
        <c:set var="buttonText" value="button.remove"/>
        <c:set var="redirectToAction" value="/inform/message/list.do"/>
        <c:set var="confirmText"><bean:message key="confirm.text" bundle="informMessagesBundle"/></c:set>
        <c:if test="${informMessage.state == 'TEMPLATE'}">
            <c:set var="buttonText" value="button.delete"/>
            <tiles:put name="submenu" type="string" value="TemplateInformMessages"/>
            <c:set var="redirectToAction" value="/inform/template/list.do"/>
            <c:set var="confirmText"><bean:message key="confirm.templateText" bundle="informMessagesBundle"/></c:set>
        </c:if>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close.help"/>
                <tiles:put name="bundle" value="informMessagesBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="${redirectToAction}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <html:hidden property="id" value="${form.id}"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name">
                    <bean:message key="edit.title" bundle="informMessagesBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="view.description" bundle="informMessagesBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Место публикации:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:forEach items="${pages}" var="page">
                                <b><c:out value="${page.name}"/></b></br>
                            </c:forEach>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Публиковать:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b>
                                <c:choose>
                                    <c:when test="${informMessage.vievType == 'POPUP_WINDOW'}">
                                        <bean:message key="label.popup" bundle="informMessagesBundle"/>
                                    </c:when>
                                    <c:otherwise>
                                        <bean:message key="label.static" bundle="informMessagesBundle"/>
                                    </c:otherwise>
                                </c:choose>
                            </b>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            оступно для ТБ:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:forEach items="${departments}" var="department">
                                <b><c:out value="${phiz:getDepartmentName(department, null, null)}"/></b></br>
                            </c:forEach>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Период публикации:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b>с <bean:write name="dateFrom" format="dd.MM.yyyy"/></b>
                            <c:choose>
                                <c:when test="${not empty dateTo}">
                                    <b>по <bean:write name="dateTo" format="dd.MM.yyyy"/></b>
                                </c:when>
                                <c:otherwise>
                                    <b><bean:message key="label.indefinitely" bundle="informMessagesBundle"/></b>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Важность:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b>
                                <c:choose>
                                    <c:when test="${importance == 'HIGH'}">
                                        <bean:message key="label.importance.high" bundle="informMessagesBundle"/>
                                    </c:when>
                                    <c:when test="${importance == 'MEDIUM'}">
                                        <bean:message key="label.importance.medium" bundle="informMessagesBundle"/>
                                    </c:when>
                                    <c:when test="${importance == 'LOW'}">
                                        <bean:message key="label.importance.low" bundle="informMessagesBundle"/>
                                    </c:when>
                                </c:choose>
                            </b>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Сообщение:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <div id="needSpliting">
                                <div class="message-width word-wrap">
                                    <b>${phiz:processBBCode(text)}</b>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <c:if test="${canEdit}">
                    <tiles:put name="buttons">
                        <tiles:insert definition="clientButton" flush="false" operation="EditInformMessagesOperation">
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit.help"/>
                            <tiles:put name="bundle"         value="informMessagesBundle"/>
                            <tiles:put name="action"        value="/inform/message/edit.do?id=${form.id}"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveInformMessagesOperation">
                            <tiles:put name="commandKey"     value="button.remove"/>
                            <tiles:put name="commandTextKey" value="${buttonText}"/>
                            <tiles:put name="commandHelpKey" value="${buttonText}.help"/>
                            <tiles:put name="bundle"         value="informMessagesBundle"/>
                            <tiles:put name="confirmText" value="${confirmText}"/>
                        </tiles:insert>
                    </tiles:put>
                </c:if>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>