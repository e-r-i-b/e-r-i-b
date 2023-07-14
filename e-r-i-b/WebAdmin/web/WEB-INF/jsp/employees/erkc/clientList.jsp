<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/erkc/afterChangeNode">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="personList">
        <tiles:put name="submenu" type="string" value="ClientListFull"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="list.title" bundle="personsBundle"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:if test="${not empty form.data}">
                <table width="100%" cellpadding="4">
                    <tbody>
                    <tr>
                        <td align="center" class="messageTab">Найдено несколько клиентов.</td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="personList"/>
                <tiles:put name="text" value="Клиенты"/>
                <tiles:put name="grid">
                    <sl:collection id="person" model="no-pagination" property="data" bundle="personsBundle">
                        <c:set var="login" value="${person.login}"/>
                        <sl:collectionItem title="LOGIN_ID"><c:out value="${login.id}"/></sl:collectionItem>
                        <sl:collectionItem title="label.FIO">
                            <c:set var="blockCount" value="0"/>
                            <c:set var="blockReasons" value="<b>Причина(ы) блокировки:</b>"/>
                            <c:if test="${person.status == 'A' or person.status == 'W'}">
                                <c:set var="blocks" value="${login.blocks}"/>
                                <c:forEach var="block" items="${blocks}" varStatus="status">
                                    <c:if test="${blockCount == 0}">
                                        <span id="state${stateNumber}"
                                              onmouseover="showLayer('state${stateNumber}','stateDescription${stateNumber}','default',10);"
                                              onmouseout="hideLayer('stateDescription${stateNumber}');" style="text-decoration:none">
                                                <img src='${imagePath}/iconSm_lock.gif' width='12px' height='12px' alt='' border='0'/>
                                        </span>
                                    </c:if>
                                    <c:set var="blockCount" value="${blockCount + 1}"/>
                                    <c:set var="reason">
                                        Тип: <phiz:reasonType value="${block.reasonType}"/>
                                    </c:set>
                                    <c:set var="blockReasons">${blockReasons}<br><b>${blockCount}</b>. <c:out value="${reason}"/></c:set>
                                    <c:if test="${not empty block.reasonDescription}">
                                        <c:set var="blockReasons">${blockReasons}, описание: <c:out value="${block.reasonDescription}"/></c:set>
                                    </c:if>
                                    <c:if test="${not empty block.blockedFrom}">
                                        <c:set var="blockReasons">
                                            ${blockReasons}, с
                                            <fmt:formatDate value="${block.blockedFrom}" pattern="dd.MM.yyyy"/>
                                        </c:set>
                                    </c:if>
                                    <c:if test="${not empty block.blockedUntil}">
                                        <c:set var="blockReasons">
                                            ${blockReasons} по
                                            <fmt:formatDate value="${block.blockedUntil}" pattern="dd.MM.yyyy"/>
                                        </c:set>
                                    </c:if>
                                    <c:set var="blockReasons" value="${blockReasons}."/>
                                </c:forEach>
                                <div id="stateDescription${stateNumber}"
                                     onmouseover="showLayer('state${stateNumber}','stateDescription${stateNumber}','default',10);"
                                     onmouseout="hideLayer('stateDescription${stateNumber}');"
                                     class='layerFon' style='position:absolute; display:none; width:300px; height:65px;overflow:auto;'>
                                        ${blockReasons}
                                </div>
                            </c:if>
                            <c:set value="${stateNumber+1}" var="stateNumber"/>
                            <phiz:link action="/erkc/afterChangeNode" serviceId="PersonManagement">
                                <phiz:param name="id" value="${person.id}"/>
                                ${person.surName} ${person.firstName} ${person.patrName}
                            </phiz:link>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.login" name="login" property="userId"/>
                        <sl:collectionItem title="label.agreementNumber" property="agreementNumber"/>
                        <sl:collectionItem title="label.agreementType"><bean:message key="label.agreementType.${person.creationType}" bundle="personsBundle"/></sl:collectionItem>
                        <sl:collectionItem title="Статус" hidden="${not standalone}">
                            <c:set var="status" value="${person.status}"/>
                            <sl:collectionItemParam id="value" value="Активный" condition="${status=='A' && (empty blocks)}"/>
                            <sl:collectionItemParam id="value" value="Подключение" condition="${status=='T'}"/>
                            <sl:collectionItemParam id="value" value="На расторжении" condition="${status=='W'}"/>
                            <sl:collectionItemParam id="value" value="Ошибка расторжения" condition="${status=='E'}"/>
                            <sl:collectionItemParam id="value" value="Подписание заявления" condition="${status=='S'}"/>
                            <sl:collectionItemParam id="value" value="Заблокирован" condition="${status!='T' && status!='W' && status!='E' && status!='S' && (not empty blocks)}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">Клиент не найден в БД ЕРИБ</tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
