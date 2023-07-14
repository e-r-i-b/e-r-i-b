<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/blockingrules/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="blockingRulesList">
        <tiles:put name="submenu" type="string" value="BlockingRules"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="blockingrules.page.title" bundle="blockingRulesBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" service="BlockingRulesManagement">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="blockingRulesBundle"/>
                <tiles:put name="action" value="/blockingrules/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <c:choose>
                <c:when test="${!form.fields['globalBlock']}">
                    <c:set var="banAllText"><bean:message bundle="commonBundle" key="text.ban.all.clients"/></c:set>
                    <tiles:insert definition="commandButton" flush="false" service="BlockingRulesManagement">
                        <tiles:put name="commandKey" value="button.blockAll"/>
                        <tiles:put name="commandHelpKey" value="button.blockAll.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="confirmText" value="${banAllText}"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="commandButton" flush="false" service="BlockingRulesManagement">
                        <tiles:put name="commandKey" value="button.releaseAll"/>
                        <tiles:put name="commandHelpKey" value="button.releaseAll.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/blockingrules/edit')}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="buttons">
                    <script type="text/javascript">
                        function openMessage(id)
                        {
                           window.open('${phiz:calculateActionURL(pageContext,"/blockingrules/list/message")}?field(id)='+id, "", "width=1000,height=250,resizable=0,menubar=0,toolbar=0,scrollbars=1");
                        }
                    </script>
                    <tiles:insert definition="commandButton" flush="false" service="BlockingRulesManagement">
                        <tiles:put name="commandKey" value="button.unlock"/>
                        <tiles:put name="commandHelpKey" value="button.unlock.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите хотябы одну запись для разблокировки');
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" service="BlockingRulesManagement">
                        <tiles:put name="commandKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите хотябы одну запись для блокировки');
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" service="BlockingRulesManagement">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите хотябы одну запись для удаления');
                        </tiles:put>
                        <tiles:put name="confirmText" value="Вы действительно хотите удалить выбранные правила?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="item" property="data" model="list" bundle="blockingRulesBundle">
                        <c:if test="${phiz:impliesService('BlockingRulesManagement')}">
                            <sl:collectionParam id="selectName" value="selectedIds"/>
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectProperty" value="id"/>
                        </c:if>
                        <sl:collectionItem title="label.name">
                            <c:choose>
                                <c:when test="${phiz:impliesService('BlockingRulesManagement')}">
                                    <a href="${url}?id=${item.id}"><c:out value="${item.name}"/></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${item.name}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.departments">
                            <c:out value="${item.departments}"/>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.state">
                            <c:choose>
                                <c:when test="${item.state == 'blocked'}">
                                    <img src="${imagePath}/iconSm_lock.gif">
                                </c:when>
                                <c:when test="${item.state == 'unblocked'}">
                                    <img src="${imagePath}/iconSm_unlock.gif">
                                </c:when>
                            </c:choose>
                            <c:set var="stateLabel" value="item.state.${item.state}"/>
                            <bean:message key="${stateLabel}" bundle="blockingRulesBundle"/>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.channels">
                            <c:set var="channelsValue" value=""/>
                            <c:if test="${item.applyToERIB}">
                                <c:set var="channelsValue" value="ЕРИБ"/>
                            </c:if>
                            <c:if test="${item.applyToMAPI}">
                                <c:if test="${!empty(channelsValue)}">
                                    <c:set var="channelsValue" value="${channelsValue}, "/>
                                </c:if>
                                <c:set var="channelsValue" value="${channelsValue}mApi"/>
                            </c:if>
                            <c:if test="${item.applyToATM}">
                                <c:if test="${!empty(channelsValue)}">
                                    <c:set var="channelsValue" value="${channelsValue}, "/>
                                </c:if>
                                <c:set var="channelsValue" value="${channelsValue}atmApi"/>
                            </c:if>
                            <c:if test="${item.applyToERMB}">
                                <c:if test="${!empty(channelsValue)}">
                                    <c:set var="channelsValue" value="${channelsValue}, "/>
                                </c:if>
                                <c:set var="channelsValue" value="${channelsValue}ЕРМБ"/>
                            </c:if>
                            <c:out value="${channelsValue}"/>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.unblock.time">
                            <fmt:formatDate value="${item.resumingTime.time}" pattern="dd.MM.yyyy в HH:mm"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного правила блокировки."/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>