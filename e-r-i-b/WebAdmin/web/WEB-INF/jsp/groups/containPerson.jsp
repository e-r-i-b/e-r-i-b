<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/groups/containPerson" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="definitionName" value="personGroup"/>
<c:if test="${not empty form.mailId}">
    <c:set var="definitionName" value="personGroupList"/>
</c:if>
<tiles:insert definition="${definitionName}">
	<tiles:put name="submenu" type="string" value="Content"/>
	<tiles:put name="needSave" type="string" value="false"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listGroup.title" bundle="groupsBundle"/>
    </tiles:put>
	<tiles:put name="menu" type="string">
        <c:if test="${empty form.mailId}">
            <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="image"   value=""/>
		        <tiles:put name="onclick" value="callDictionary()"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
        </c:if>
		<nobr>
	 </tiles:put>

    <%--Фильтр--%>
    <tiles:put name="filter" type="string">
        <tiles:put name="fastSearchFilter" value="true"/>
        <c:set var="colCount" value="3" scope="request"/>
        <%@ include file="/WEB-INF/jsp-sbrf/persons/search/filterPersonsList.jsp" %>
    </tiles:put>

    <%--данные--%>
    <tiles:put name="data" type="string">
        <script>
            function callDictionary()
            {
                <c:set var="personDictionary">${phiz:calculateActionURL(pageContext,"/groups/personDictionary.do")}</c:set>
                var path = '${personDictionary}?groupId=${GroupsContainPersonForm.id}';
                window.open(path, "", "resizable=1,menubar=1,toolbar=1,scrollbars=1");
            }
        </script>
        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <script type="text/javascript">
		  function setPersons(logins)
		  {
			  var el = document.getElementById("personLogins");
			  el.value = logins;
			  var button = new CommandButton('button.add', '');
			  button.click();
			  <%--callOperation(event,'<bean:message key="button.add" bundle="groupsBundle"/>')--%>
		  }
	  </script>
	  <input type="hidden" name="category" value="${GroupsContainPersonForm.category}"/>
	  <input type="hidden" id="personLogins" name="personLogins" value=""/>

    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="groupList"/>
        <c:choose>
            <c:when test="${empty form.groupName}">
                <tiles:put name="text" value="Список группы"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="text">Список группы &quot;<c:out value="${form.groupName}"/>&quot;</tiles:put>
            </c:otherwise>
        </c:choose>
        <tiles:put name="buttons">
            <c:if test="${empty form.mailId}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle"         value="groupsBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            setElement("filter(documentNumber)",'');
                            checkIfOneItem("selectedIds");
                            return checkSelection('selectedIds', 'Выберите одного пользователя');
                        }
                    </tiles:put>
                    <tiles:put name="confirmText"    value="Удалить выбраных пользователей?"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" selectBean="person" bundle="personsBundle">
                <c:if test="${empty form.mailId}">
                    <sl:collectionParam id="selectType" value="checkbox"/>
                    <sl:collectionParam id="selectName" value="selectedIds"/>
                    <sl:collectionParam id="selectProperty" value="id"/>
                </c:if>

                <c:set var="person" value="${listElement[0]}" />
                <c:set var="login" value="${listElement[1]}" />
                <c:set var="blocks" value="${login.blocks}"/>
                <c:set var="simpleScheme" value="${listElement[2]}"/>
                <c:set var="status" value="${person.status}"/>

                <sl:collectionItem title="LOGIN_ID">
                    <c:out value="${login.id}"/>
                </sl:collectionItem>

                <sl:collectionItem title="label.FIO">
                    <c:if test="${status == 'A'}">
                        <c:if test="${not empty blocks}">
                            <img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty person}">
                        <c:choose>
                            <c:when test="${not empty form.mailId}">
                                <phiz:link
                                             action="/persons/edit"
                                             operationClass="ViewPersonOperation">
                                    <phiz:param name="person" value="${person.id}"/>
                                    &nbsp;<bean:write name="person" property="surName"/>
                                    &nbsp;<bean:write name="person" property="firstName"/>
                                    &nbsp;<bean:write name="person" property="patrName"/>
                                </phiz:link>
                            </c:when>
                            <c:otherwise>
                                &nbsp;<bean:write name="person" property="surName"/>
                                &nbsp;<bean:write name="person" property="firstName"/>
                                &nbsp;<bean:write name="person" property="patrName"/>
                            </c:otherwise>
                        </c:choose>
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

                <sl:collectionItem title="Схема прав доступа">
                    <html:link action="/persons/useroperations?person=${person.id}">
                        <c:choose>
                            <c:when test="${empty simpleScheme}">
                                <span class="errorText">Нет&nbsp;схемы&nbsp;прав</span>
                            </c:when>
                            <c:when test="${simpleScheme.name=='personal'}">
                                <span class="errorText">Индивидуальные&nbsp;права</span>
                            </c:when>
                            <c:otherwise>
                                <nobr><bean:write name="simpleScheme" property="name"/></nobr>
                            </c:otherwise>
                        </c:choose>
                    </html:link>                    
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

        <tiles:put name="isEmpty" value="${empty GroupsContainPersonForm.data}"/>
        <tiles:put name="emptyMessage">
        <c:choose>
            <c:when test="${form.fromStart}">
                Для поиска клиентов в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
            </c:when>
            <c:otherwise>
                Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!
            </c:otherwise>
        </c:choose>
    </tiles:put>
    </tiles:insert>
 </tiles:put>
 </tiles:insert>
</html:form>
