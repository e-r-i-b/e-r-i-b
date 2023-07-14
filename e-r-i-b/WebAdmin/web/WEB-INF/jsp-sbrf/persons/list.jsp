<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% pageContext.getRequest().setAttribute("mode", "Users");%>
<c:set var="standalone"     value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="personList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>
<html:form action="/persons/list">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="${layout}">

<c:set var="useOwnAuthentication" value="${phiz:useOwnAuthentication()}"/>

<tiles:put name="submenu" type="string" value="PersonList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="personsBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<nobr>
        <c:if test="${!standalone}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.select"/>
                <tiles:put name="commandHelpKey" value="button.select.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="image" value="next.gif"/>
                <tiles:put name="onclick" value="sendClientInfo(event)"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="image" value="back.gif"/>
                <tiles:put name="onclick" value="window.close()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </c:if>
    </nobr>
</tiles:put>

    <%--Фильтр--%>
    <tiles:put name="filter" type="string">
        <tiles:put name="fastSearchFilter" value="true"/>
        <c:set var="colCount" value="3" scope="request"/>
        <%@ include file="/WEB-INF/jsp-sbrf/persons/search/filterPersonsList.jsp" %>
    </tiles:put>

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:put name="data" type="string">
    <script type="text/javascript">
        document.imgPath = "${imagePath}/";
        function initTemplates()
        {
            //setInputTemplate('passportSeries', PASSPORT_SERIES_TEMPLATE);
            //setInputTemplate('passportNumber', PASSPORT_NUMBER_TEMPLATE);
        }

        initTemplates();
        function sendClientInfo(event)
        {
            var ids = document.getElementsByName("selectedIds");
            preventDefault(event);
            for (i = 0; i < ids.length; i++)
            {
                if (ids.item(i).checked)
                {
                    var r = ids.item(i).parentNode.parentNode;
                    var a = new Array(2);
                    a['name'] = trim(r.cells[1].innerHTML);
                    a['id'] = ids.item(i).value;
                    window.opener.setGroupData(a);
                    window.close();
                    return;
                }
            }
            alert("Выберите клиента.");
        }
    </script>
	<script type="text/javascript">
        function restorePassword()
        {
            checkIfOneItem("selectedIds");
            if (!checkSelection("selectedIds", "Выберите клиента для восстановления пароля."))
            {
                return;
            }

            var ids = document.getElementsByName("selectedIds");
            for(var i = 0; i < ids.length; i++)
            {
                if(ids[i].checked)
                {
                    window.location = "${phiz:calculateActionURL(pageContext, "/persons/restorepassword.do")}" + "?id=" + ids[i].value;
                    break;
                }
            }
        }
        function CallReasonWindow()
		{
            checkIfOneItem("selectedIds");
			if (!checkSelection("selectedIds", "Выберите клиента для блокировки."))
				return;
			window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock.do')}", "", "width=1000,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=1");
		}
		function setReason(reason, startDate, endDate)
		{
			var blockReason = document.getElementById("blockReason");
			blockReason.value = reason;

			var blockStartDate = document.getElementById("blockStartDate");
			blockStartDate.value = startDate;
			if (endDate!=null)
			{
				var blockEndDate = document.getElementById("blockEndDate");
				blockEndDate.value = endDate;
			}

			var button = new CommandButton("button.lock", "");
			button.click();
		}
		function checkPersonsStatus()
		{
			checkIfOneItem("selectedIds");
            if (!checkSelection("selectedIds", "Выберите клиента для удаления."))
				return false;
			var ids = document.getElementsByName("selectedIds");
			for (var i = 0; i < ids.length; i++)
			{
				if (ids[i].checked)
				{
					var statusElemValue = document.getElementById("status"+i).value;
					var fioElemValue = document.getElementById("fio"+i).value;
					var mes = "Клиент "+fioElemValue+" не может быть удален.";

					if (statusElemValue=='A')
					{
						alert(mes + " Для расторжения договора с активным клиентом используйте кнопку Удалить в анкете клиента.");
						return false;
					}
					if (!(statusElemValue=='' || statusElemValue=='S' || statusElemValue=='T'))
					{
						alert(mes + " Из списка можно удалить клиента(ов) только со статусом Подписание договора или Подключение. ");
						return false;
					}
				}
			}
			return true;
		}
        <c:if test="${form.fromStart}">
           //показываем фильтр при старте
           switchFilter(this);
        </c:if>
	</script>
	<input type="hidden" name="blockReason" id="blockReason" value=""/>
	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>

    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="personList"/>
        <tiles:put name="buttons">
        <nobr>
		<c:choose>
			<c:when test="${standalone}">
                 <div class="buttDiv" style="padding-bottom:5px;">
                    <table cellpadding="0" cellspacing="0" height="100%"><tr><td valign="bottom">
					<html:select property="accessSchemeId" styleClass="filterSelectMenu clientListSelect">
						<html:option value="">
							<bean:message key="label.noSchemes" bundle="personsBundle"/>
						</html:option>
                        <c:set var="schemes" value="${ListPersonsForm.accessSchemes}"/>
                        <c:forEach var="scheme" items="${schemes}">
                            <html:option value="simple_${scheme.id}">
                                <c:out value="${scheme.name}"/>
                            </html:option>
                        </c:forEach>
					</html:select>
				</td></tr></table>
                </div>
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.assignScheme.simple"/>
					<tiles:put name="commandHelpKey" value="button.assignScheme.simple.help"/>
					<tiles:put name="bundle" value="personsBundle"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                </tiles:insert>
					<%-- временно закоментарил для целей демо-стенда
				   <tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"     value="button.assignScheme.secure"/>
						<tiles:put name="commandHelpKey" value="button.assignScheme.secure.help"/>
						<tiles:put name="bundle"         value="personsBundle"/>
						<tiles:put name="image"          value="assignScheme.gif"/>
				   </tiles:insert>
		   --%>
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.lock"/>
					<tiles:put name="commandHelpKey" value="button.lock"/>
					<tiles:put name="bundle" value="personsBundle"/>
					<tiles:put name="onclick" value="CallReasonWindow()"/>
				</tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.unlock"/>
					<tiles:put name="commandHelpKey" value="button.unlock.help"/>
					<tiles:put name="bundle" value="personsBundle"/>
				</tiles:insert>
                <tiles:insert definition="clientButton" flush="false" service="RestoreClientPasswordService">
                    <tiles:put name="commandTextKey" value="button.sendnew.password"/>
                    <tiles:put name="commandHelpKey" value="button.sendnew.password.help"/>
                    <tiles:put name="bundle"         value="personsBundle"/>
                    <tiles:put name="onclick"        value="restorePassword()"/>
                </tiles:insert>
					<%--
				   <tiles:insert definition="commandButton" flush="false" service="PersonPasswordCardManagment">
						<tiles:put name="commandKey"     value="button.showPasswordCard"/>
						<tiles:put name="commandHelpKey" value="button.showPasswordCard.help"/>
						<tiles:put name="bundle"         value="personsBundle"/>
						<tiles:put name="image"          value="next.gif"/>
				   </tiles:insert>
		   --%>
			</c:when>
			<c:otherwise>
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.select"/>
					<tiles:put name="commandHelpKey" value="button.select.help"/>
					<tiles:put name="bundle" value="mailBundle"/>
					<tiles:put name="onclick" value="sendClientInfo(event)"/>
				</tiles:insert>
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.cancel"/>
					<tiles:put name="commandHelpKey" value="button.cancel.help"/>
					<tiles:put name="bundle" value="mailBundle"/>
					<tiles:put name="onclick" value="window.close()"/>
				</tiles:insert>
			</c:otherwise>

	</c:choose>
	</nobr>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" selectBean="person" bundle="personsBundle">
                <sl:collectionParam id="selectType" value="checkbox" condition="${standalone}"/>
                <sl:collectionParam id="selectType" value="radio"    condition="${not standalone}"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="person.id" condition="${not standalone}"/>
                <sl:collectionParam id="selectProperty" value="id" condition="${standalone}"/>

                <c:set var="person" value="${listElement[0]}"/>
                <c:set var="simpleScheme" value="${listElement[1]}"/>
                <c:set var="login" value="${person.login}"/>


                <sl:collectionItem title="LOGIN_ID">
                    <c:out value="${login.id}"/>
                    <c:set var="personDataNotModified" value="${phiz:isPersonDataNotModified(person.id)}"/>
                    <c:if test="${!personDataNotModified}">
                        <span id="importantImg"
                                   onmouseover="showLayer('importantImg','notRegistered','default',10);"
                                   onmouseout="hideLayer('notRegistered');" style="text-decoration:none">
                                        <img src='${imagePath}/important.gif' width='12px' height='12px' alt='' border='0'/>
                                </span>
                        <div id="notRegistered" onmouseover="showLayer('importantImg','notRegistered','default', 10);"
                             onmouseout="hideLayer('notRegistered');" class="layerFon" style="position:absolute;
                             display:none; width:400px; height:37px;overflow:auto;">
                            Анкета клиента не зарегистрирована. Для регистрации зайдите в анкету клиента.
                        </div>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.FIO">
                    <c:set value="0" var="blockCount"/>
					<c:set value="<b>Причина(ы) блокировки:</b>" var="blockReasons"/>
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
                             class='layerFon' style='position:absolute; display:none; width:300px; overflow:auto; z-index: 1;'>
                                    ${blockReasons}
                        </div>
                    </c:if>
					<c:set value="${stateNumber+1}" var="stateNumber"/>
                    <phiz:link action="/persons/edit"
                            serviceId="PersonManagement">
                     <phiz:param name="person" value="${person.id}"/>   
					${person.surName} ${person.firstName} ${person.patrName}
                    </phiz:link>
                </sl:collectionItem>

                <sl:collectionItem title="Дата последнего входа">
                    <c:if test="${not empty person.login}">
                        <fmt:formatDate value="${person.login.logonDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </c:if>
                </sl:collectionItem>

                <sl:collectionItem title="Логин">
                    <c:if test="${not empty person.login}">
                        <c:choose>
                            <c:when test="${person.login.lastLogonType == 'ATM'}">
                                <c:out value="${phiz:getCutCardNumber(person.login.lastLogonParameter)}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${person.login.lastLogonParameter}"/>
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
                <sl:collectionItem title="Схема прав доступа" hidden="${not standalone}">
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
	<tiles:put name="isEmpty" value="${empty ListPersonsForm.data}"/>
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
