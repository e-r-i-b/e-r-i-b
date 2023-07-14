<%--
  Created by IntelliJ IDEA.
  User: Barinov
  Date: 01.12.2011
  Time: 18:55:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/groups/personDictionary">
<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
    <bean:message key="personList.title" bundle="groupsBundle"/>
</tiles:put>

    <%--Фильтр--%>
    <tiles:put name="filter" type="string">
        <tiles:put name="fastSearchFilter" value="true"/>
        <c:set var="colCount" value="3" scope="request"/>
        <%@ include file="/WEB-INF/jsp-sbrf/persons/search/filterPersonsList.jsp" %>
    </tiles:put>

     <%--данные--%>
<tiles:put name="data" type="string">
    <c:if test="${not empty form.rowsNum}">В группу добавлено ${form.rowsNum} клиентов.</c:if>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="tableTemplate" flush="false">
        <input type="hidden" name="groupId" value="${ShowPersonDictionaryForm.groupId}"/>
        <tiles:put name="id" value=""/>
        <tiles:put name="text" value=""/>
        <tiles:put name="grid">
            <sl:collection model="list" property="data" id="listElement" bundle="personsBundle" selectBean="person">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="person" value="${listElement[0]}"/>
                <c:set var="login" value="${listElement[1]}"/>
                <c:set var="scheme" value="${listElement[2]}"/>
                <c:set var="blocks" value="${person.login.blocks}"/>
                <c:set var="status" value="${person.status}"/>

                <sl:collectionItem title="LOGIN_ID">
                    <c:out value="${login.id}"/>
                </sl:collectionItem>

                <sl:collectionItem title="label.FIO">
                    <c:if test="${status == 'A'}">
                        <c:if test="${not empty blocks}">
                            <img src="${imagePath}/iconSm_lock.gif"
                                 width="12px" height="12px" alt="" border="0"/>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty person}">
                        <bean:write name="person" property="surName"/>&nbsp;
                        <bean:write name="person" property="firstName"/>&nbsp;
                        <bean:write name="person" property="patrName"/>
                    </c:if>
                </sl:collectionItem>

                <sl:collectionItem title="Номер договора" name="person" property="agreementNumber"/>

                <sl:collectionItem title="Тип договора">
                    <c:choose>
                        <c:when test="${person.creationType == 'UDBO'}">
                            <c:set var="creationType" value="УДБО"/>
                        </c:when>
                        <c:when test="${person.creationType == 'SBOL'}">
                            <c:set var="creationType" value="СБОЛ"/>
                        </c:when>
                        <c:when test="${person.creationType == 'CARD'}">
                            <c:set var="creationType" value="Подключен по карте"/>
                        </c:when>
                    </c:choose>
                    <c:out value="${creationType}"/>
                </sl:collectionItem>

                <sl:collectionItem title="Схема прав доступа" name="scheme" property="name">
                    <sl:collectionItemParam id="styleClass" value="errorText"
                                            condition="${empty scheme || scheme.name=='personal'}"/>
                    <sl:collectionItemParam id="value" value="Нет схемы прав" condition="${empty scheme}"/>
                    <sl:collectionItemParam id="value" value="Индивидуальные права"
                                            condition="${scheme.name=='personal'}"/>
                </sl:collectionItem>

                <sl:collectionItem title="Статус">
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

        <tiles:put name="isEmpty" value="${empty ShowPersonDictionaryForm.data}"/>
        <tiles:put name="emptyMessage">
            <c:choose>
                <c:when test="${ShowPersonDictionaryForm.fromStart}">
                    <script type="text/javascript">switchFilter(this);</script>
                    Для поиска клиентов в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                </c:when>
                <c:otherwise>
                    Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</tiles:put>
    <tiles:put name="menu" type="string">
        <script type="text/javascript">
            function sendPersonData(event)
            {
                var ids = document.getElementsByName("selectedIds");
                preventDefault(event);
                var idString = "";
                for (var i = 0; i < ids.length; i++)
                {
                    if (ids.item(i).checked)
                    {
                        idString = idString + ids.item(i).value + ";";
                    }
                }

                if (idString == "")
                    alert("Выберите пользователя.");
                else
                {
                    window.opener.setPersons(idString);
                    window.close();
                    return;
                }
            }
            //если пользователь открыл фильтр - кнопка "добавить 1000" нужно спрятать.
            var originSwitchFilter = switchFilter;
            switchFilter = function(event)
            {
                hideTitle("addAllButton");
                originSwitchFilter(event);
            };
        </script>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <c:if test="${not empty form.data}">
            <span id="addAllButton">
                <tiles:insert definition="commandButton" flush="false" operation="GetPersonListDictionaryOperation">
                    <tiles:put name="commandKey" value="button.addAll"/>
                    <tiles:put name="commandHelpKey" value="button.addAll"/>
                    <tiles:put name="bundle" value="groupsBundle"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </span>
        </c:if>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.choose"/>
            <tiles:put name="commandHelpKey" value="button.choose"/>
            <tiles:put name="bundle" value="commonBundle"/>
            <tiles:put name="image" value=""/>
            <tiles:put name="onclick" value="sendPersonData(event)"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.return"/>
            <tiles:put name="commandHelpKey" value="button.return"/>
            <tiles:put name="bundle" value="commonBundle"/>
            <tiles:put name="image" value=""/>
            <tiles:put name="onclick" value="window.close();"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>
