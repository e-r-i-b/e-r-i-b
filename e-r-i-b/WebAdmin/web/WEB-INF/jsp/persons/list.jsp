<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<% pageContext.getRequest().setAttribute("mode", "Users");%>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="department" value="${phiz:getDepartmentById(phiz:getEmployeeInfo().departmentId)}"/>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="personList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>                  
	</c:otherwise>
</c:choose>
<html:form action="/persons/list">

<tiles:insert definition="${layout}">
<tiles:put name="submenu" type="string" value="PersonList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="personsBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
    <c:if test="${standalone}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.add"/>
            <tiles:put name="commandHelpKey" value="button.add.help"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="onclick">
             javascript:addClient(event);
            </tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </c:if>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.surName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="surName"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.documentType"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(documentType)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="REGULAR_PASSPORT_RF">Общегражданский паспорт РФ</html:option>
					<html:option value="MILITARY_IDCARD">Удостоверение личности военнослужащего</html:option>
					<html:option value="SEAMEN_PASSPORT">Паспорт моряка</html:option>
					<html:option value="RESIDENTIAL_PERMIT_RF">Вид на жительство РФ</html:option>
					<html:option value="FOREIGN_PASSPORT_RF">Заграничный паспорт РФ</html:option>
					<html:option value="OTHER">Иной документ</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.pinEnvelopeNumber"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
		 	<tiles:put name="name" value="pinEnvelopeNumber"/>
			<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.documentSeries"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="documentSeries"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.agreementNumber"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="agreementNumber"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
		    <tiles:put name="name" value="patrName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.documentNumber"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="documentNumber"/>
			<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.status"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(state)" styleClass="filterSelect" style="width:100px">
					<html:option value="">Все</html:option>
					<html:option value="0">Активный</html:option>
					<html:option value="1">Заблокирован</html:option>
					<html:option value="T">Подключение</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.filterId"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="name" value="id"/>
	</tiles:insert>
	<script type="text/javascript">
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
        function addClient(event)
				{
                    if (${department.service})
                    {
                        window.location = "${phiz:calculateActionURL(pageContext, '/persons/clients/list.do')}";
                    }
                    else
                        alert("Невозможно добавить клиента. Подразделение не обслуживается в системе ИКФЛ.");
				}
	</script>
</tiles:put>
<tiles:put name="data" type="string">
<tiles:importAttribute/>
	<script type="text/javascript">
		function CallReasonWindow()
		{
			checkIfOneItem("selectedIds");
            if (!checkSelection("selectedIds", "Выберите клиента для блокировки."))
				return;
			window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock.do')}", "", "width=900,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=1");
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
	</script>
	<input type="hidden" name="blockReason" id="blockReason" value=""/>
	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="personList"/>
		<tiles:put name="text" value="Список клиентов"/>
        <tiles:put name="buttons">
        <nobr>
		<c:choose>
			<c:when test="${standalone}">
                 <div class="buttDiv" style="padding-bottom:5px;">
                    <table cellpadding="0" cellspacing="0" height="100%"><tr><td valign="bottom">
					<html:select property="accessSchemeId" styleClass="filterSelectMenu">
						<html:option value="">
							<bean:message key="label.noSchemes" bundle="personsBundle"/>
						</html:option>
                       <optgroup label="<bean:message key="label.schemesSimple" bundle="personsBundle"/>">
                        <c:set var="schemes" value="${ListPersonsForm.accessSchemes}"/>
                        <c:forEach var="scheme" items="${schemes}">
                            <html:option value="simple_${scheme.id}">
                                <c:out value="${scheme.name}"/>
                            </html:option>
                        </c:forEach>
                        <optgroup label="<bean:message key="label.schemesSecure" bundle="personsBundle"/>">
                        <c:set var="schemes" value="${ListPersonsForm.accessSchemes}"/>
                        <c:forEach var="scheme" items="${schemes}">
                            <html:option value="secure_${scheme.id}">
                                <c:out value="${scheme.name}"/>
                            </html:option>
                        </c:forEach>

					</html:select>
				</td></tr></table>
                </div>
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.assignScheme"/>
					<tiles:put name="commandHelpKey" value="button.assignScheme.help"/>
					<tiles:put name="bundle" value="personsBundle"/>
				</tiles:insert>
              <%--
             <tiles:insert definition="commandButton" flush="false">
               <tiles:put name="commandKey" value="button.assignScheme.simple"/>
               <tiles:put name="commandHelpKey" value="button.assignScheme.simple.help"/>
               <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="image" value="iconSm_assignScheme.gif"/>
            </tiles:insert>     --%>
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

                <c:set var="allPassCount" value="${listElement[0]}"/>
                <c:set var="passCount" value="${listElement[1]}"/>
                <c:set var="person" value="${listElement[2]}"/>
                <c:set var="login" value="${listElement[3]}"/>
                <c:set var="simpleScheme" value="${listElement[4]}"/>
                <c:set var="secureScheme" value="${listElement[5]}"/>
                <c:set var="blocks" value="${login.blocks}"/>

                <sl:collectionItem title="ID" name="person" property="id"/>
                <sl:collectionItem title="label.FIO">
                    <c:set value="0" var="blockCount"/>
					<c:set value="<b>Причина(ы) блокировки:</b>" var="blockReasons"/>
                    <c:if test="${person.status == 'A'}">
                        <c:forEach var="block" items="${blocks}" varStatus="status">
                            <c:if test="${blockCount == 0}">
                                <a id="state${stateNumber}"
                                   onmouseover="showLayer('state${stateNumber}','stateDescription${stateNumber}','default',10);"
                                   onmouseout="hideLayer('stateDescription${stateNumber}');" style="text-decoration:none">
                                        <img src='${imagePath}/iconSm_lock.gif' width='12px' height='12px' alt='' border='0'/>
                                </a>
                            </c:if>
                            <c:set var="blockCount" value="${blockCount + 1}"/>
                            <c:set var="reason">
                                <phiz:reasonType value="${block.reasonType}"/>
                            </c:set>
                            <c:set var="blockReasons"
                                   value="${blockReasons}<br><b>${blockCount}</b>. ${reason}"/>
                            <c:if test="${not empty block.reasonDescription}">
                                <c:set var="blockReasons"
                                       value="${blockReasons}, ${block.reasonDescription}"/>
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

					<phiz:link action="/persons/edit"
                           serviceId="PersonManagement">
                        <phiz:param name="person" value="${person.id}"/>
					    ${person.surName} ${person.firstName} ${person.patrName}
                    </phiz:link>
                </sl:collectionItem>
                <sl:collectionItem title="ПИН-конверт" name="login" property="userId"/>
                <sl:collectionItem title="Номер договора" name="person" property="agreementNumber"/>
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
                    <br>
                    <html:link action="/persons/useroperations?person=${person.id}">
                        <c:choose>
                            <c:when test="${empty secureScheme}">
                                <span class="errorText">Нет&nbsp;схемы&nbsp;прав</span>
                            </c:when>
                            <c:when test="${secureScheme.name=='personal'}">
                                <span class="errorText">Индивидуальные&nbsp;права</span>
                            </c:when>
                            <c:otherwise>
                                <nobr><bean:write name="secureScheme" property="name"/></nobr>
                            </c:otherwise>
                        </c:choose>
                    </html:link>
                </sl:collectionItem>
                <sl:collectionItem title="Статус" hidden="${not standalone}">
                    <c:set var="status" value="${person.status}"/>
                    <sl:collectionItemParam id="value" value="Активный" condition="${status=='A' && (empty blocks)}"/>
                    <sl:collectionItemParam id="value" value="Подключение" condition="${status=='T'}"/>
                    <sl:collectionItemParam id="value" value="Заблокирован" condition="${status!='T' && (not empty blocks)}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
	<tiles:put name="isEmpty" value="${empty ListPersonsForm.data}"/>
	<tiles:put name="emptyMessage" value="Не найдено ни одного клиента, <br/>соответствующего заданному фильтру!"/>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
