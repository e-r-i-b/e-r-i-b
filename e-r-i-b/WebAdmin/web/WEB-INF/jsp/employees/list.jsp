<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html:form action="/employees/list">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="resOnPage" value="${form.paginationSize}"/>

<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="employeesMain">
  <tiles:put name="submenu" type="string" value="List"/>
  <tiles:put name="pageTitle" type="string">
    <bean:message key="list.title" bundle="employeesBundle"/>
  </tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false" operation="EditEmployeeOperation">
		<tiles:put name="commandTextKey"     value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add"/>
		<tiles:put name="bundle"  value="employeesBundle"/>
		<tiles:put name="action"  value="/employees/edit.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
  </tiles:put>

  <tiles:put name="filter" type="string">
      <tiles:insert definition="filterTextField" flush="false">
          <tiles:put name="label" value="login.employee"/>
          <tiles:put name="bundle" value="employeesBundle"/>
          <tiles:put name="mandatory" value="false"/>
          <tiles:put name="name" value="fio"/>
          <tiles:put name="size"   value="50"/>
          <tiles:put name="maxlength"  value="255"/>
          <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
      </tiles:insert>
      <tiles:insert definition="filterEntryField" flush="false">
          <tiles:put name="label" value="label.TB"/>
          <tiles:put name="bundle" value="employeesBundle"/>
          <tiles:put name="data">
              <html:select property="filter(terbankCode)" styleClass="select">
                  <html:option value="">Все</html:option>
                  <c:forEach var="tb" items="${form.allowedTB}">
                      <html:option value="${tb.code.region}">
                          <c:out value="${tb.name}"/>
                      </html:option>
                  </c:forEach>
              </html:select>
          </tiles:put>
      </tiles:insert>
	  <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.branchCode"/>
			<tiles:put name="bundle" value="employeesBundle"/>
			<tiles:put name="name" value="branchCode"/>
            <tiles:put name="maxlength" value="4"/>
            <tiles:put name="size" value="14"/>
	  </tiles:insert>
      <tiles:insert definition="filterTextField" flush="false">
   			<tiles:put name="label" value="label.departmentCode"/>
   			<tiles:put name="bundle" value="employeesBundle"/>
   			<tiles:put name="name" value="departmentCode"/>
               <tiles:put name="maxlength" value="4"/>
               <tiles:put name="size" value="14"/>
   	  </tiles:insert>
	  <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.info"/>
			<tiles:put name="bundle" value="employeesBundle"/>
			<tiles:put name="name" value="info"/>
            <tiles:put name="maxlength" value="50"/>
            <tiles:put name="size" value="14"/>
	  </tiles:insert>
	  <tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.status"/>
			<tiles:put name="bundle" value="employeesBundle"/>
			<tiles:put name="data">
				<html:select property="filter(state)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="0">Активный</html:option>
					<html:option value="1">Заблокирован</html:option>
				</html:select>
			</tiles:put>
	  </tiles:insert>
	  <tiles:insert definition="filterTextField" flush="false">
		    <tiles:put name="label" value="label.filterId"/>
		    <tiles:put name="bundle" value="employeesBundle"/>
		    <tiles:put name="name" value="id"/>
            <tiles:put name="maxlength" value="22"/>
            <tiles:put name="size" value="14"/>
	  </tiles:insert>
      <tiles:insert definition="filterTextField" flush="false">
		    <tiles:put name="label" value="label.login"/>
		    <tiles:put name="bundle" value="employeesBundle"/>
		    <tiles:put name="name" value="login"/>
            <tiles:put name="maxlength" value="22"/>
            <tiles:put name="size" value="14"/>
	  </tiles:insert>
  </tiles:put>



  <tiles:put name="data" type="string">
	<script type="text/javascript">
        function validateManagerInfo()
        {
            var numbers = new Array();
            if( $('[name=accessSchemeId] :selected').attr('class') == 'managerInfoA' )
            {
                var employeeFIO = "";
                var ids = $('[name=selectedIds]:checked');
                for(var i = 0; i < ids.length; i++)
                {
                    var id = ids[i];
                    var isEmptyManagerInfo = $('#managerInfo'+id.value).val();
                    if(isEmptyManagerInfo != "true")
                    {
                        numbers.push(i);
                        employeeFIO = employeeFIO + (employeeFIO.length == 0 ? "" : ", ") + ($('#employeeFIO'+id.value).text());
                    }
                }
                if(employeeFIO.length > 0)
                {
                    var confirmAccess = confirm("Если Вы назначите "+  employeeFIO + " права администратора, то информация о персональном менеджере будет удалена. Назначить данным сотрудникам права администратора?");
                    if(!confirmAccess)
                    {
                        for(var i = 0; i < numbers.length; i++)
                        {
                             ids[numbers[i]].checked = false;
                        }
                        return $('[name=selectedIds]:checked').length;
                    }
                }
            }
            return true;
        }

		function callReasonWindow()
		{
            checkIfOneItem("selectedIds");
			if (!checkSelection("selectedIds", "Выберите сотрудников для блокировки."))
				return;
			window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock')}", "", "width=1000,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=1");
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

        <c:if test="${form.fromStart}">
           //показываем фильтр при старте
           switchFilter(this);
        </c:if>
	</script>
	<input type="hidden" name="blockReason" id="blockReason" value=""/>
	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>

	<c:set var="canEdit" value="${phiz:impliesOperation('EditEmployeeOperation','EmployeeManagement')}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="EmplyeesList"/>
        <c:set var="sizeResult" value="${fn:length(form.data)}"/>
        <c:set var="pageSize" value="${form.paginationSize}"/>
        <c:set var="pageOffset" value="${form.paginationOffset}"/>
        <c:set var="editEmplUrl" value="${phiz:calculateActionURL(pageContext, '/employees/edit')}"/>
        <c:set var="editShemeUrl" value="${phiz:calculateActionURL(pageContext, '/employees/access')}"/>
        <c:set var="pageItems">
            <c:choose>
                <c:when test="${sizeResult<pageSize}">
                    ${sizeResult}
                </c:when>
                <c:otherwise>
                    ${pageSize}
                </c:otherwise>
            </c:choose>
        </c:set>
        <c:if test="${sizeResult !=0}">
            <tiles:put name="grid">
                <table cellspacing="0" cellpadding="0" class="standartTable" align="CENTER" width="100%">
                    <tr class="tblInfHeader">
                        <th width="20px" class="titleTable"><input type="checkbox" onclick="switchSelection(this,'selectedIds')"></th>
                        <th class="titleTable"><bean:message key="label.listId" bundle="employeesBundle"/></th>
                        <th class="titleTable"><bean:message key="label.FIO" bundle="employeesBundle"/></th>
                        <th class="titleTable"><bean:message key="label.department" bundle="employeesBundle"/></th>
                        <th class="titleTable"><bean:message key="label.info" bundle="employeesBundle"/></th>
                        <th class="titleTable"><bean:message key="label.login" bundle="employeesBundle"/></th>
                        <th class="titleTable"><bean:message key="label.scheme" bundle="employeesBundle"/></th>
                    </tr>

                    <c:forEach var="item" items="${form.data}" varStatus="stat">
                        <c:if test="${stat.count le pageSize}">
                            <c:set var="scheme"         value="${item.scheme}" />
                            <c:set var="department"     value="${item.department}" />
                            <c:set var="login"          value="${item.login}"/>
                            <c:set var="blocks"         value="${login.blocks}"/>
                            <c:set var="isEmptyManagerInfo"  value="${empty item.managerId && empty item.managerEMail &&
                                                                      empty item.managerLeadEMail && empty item.managerPhone}"/>
                            <input type="hidden" id="managerInfo${item.id}" value="${isEmptyManagerInfo}"/>
                            <c:set var="employeeFIO">
                                <c:set value="0" var="blockCount"/>
                                <c:set value="<b>Причина(ы) блокировки:</b>" var="blockReasons"/>
                                <c:set var="longInactivityBlockReason"><bean:message key="label.long.inactivity.block" bundle="personsBundle"/></c:set>
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
                                        <phiz:reasonType value="${block.reasonType}"/>
                                    </c:set>
                                    <c:set var="blockReasons"
                                           value="${blockReasons}<br><b>${blockCount}</b>. ${reason}"/>
                                    <c:if test="${not empty block.reasonDescription}">
                                        <c:choose>
                                            <c:when test="${longInactivityBlockReason eq block.reasonDescription}">
                                                <c:set var="blockReasons" value="${blockReasons},<br>${block.reasonDescription}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="blockReasons" value="${blockReasons}, ${block.reasonDescription}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                    <c:set var="blockReasons" value="${blockReasons}."/>
                                </c:forEach>
                                <div id="stateDescription${stateNumber}"
                                     onmouseover="showLayer('state${stateNumber}','stateDescription${stateNumber}','default',10);"
                                     onmouseout="hideLayer('stateDescription${stateNumber}');" class='layerFon'
                                     style='position:absolute; display:none; width:300px; height:50px;overflow:auto;'>
                                        ${blockReasons}
                                </div>
                                <c:set value="${stateNumber+1}" var="stateNumber"/>
                                <span id="employeeFIO${item.id}">${item.surName} ${item.firstName} ${item.patrName}</span>
                            </c:set>
                            <c:set var="fio">
                                <c:choose>
                                    <c:when test="${canEdit}">
                                        <a href="${editEmplUrl}?employeeId=${item.id}">
                                            ${employeeFIO}
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        ${employeeFIO}
                                    </c:otherwise>
                                </c:choose>
                            </c:set>

                            <c:choose>
                                <c:when test="${stat.count%2 !=1}">
                                    <c:set var="trClass" value="ListLine0"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="trClass" value="ListLine1"/>
                                </c:otherwise>
                            </c:choose>
                            <c:set var="styleClass">
                                <c:choose>
                                    <c:when test="${empty scheme || scheme.name=='personal'}">errorText</c:when>
                                    <c:otherwise>ListItem</c:otherwise>
                                </c:choose>
                            </c:set>
                            <c:set var="info">
                                <c:choose>
                                    <c:when test="${empty item.info}">
                                        &nbsp;
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${item.info}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:set>
                            <c:set var="shemeName">
                                <a href="${editShemeUrl}?employeeId=${item.id}">
                                    <c:choose>
                                        <c:when test="${empty scheme}">
                                            Нет схемы прав
                                        </c:when>
                                        <c:when test="${scheme.name=='personal'}">
                                            Индивидуальные права
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${scheme.name}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </c:set>

                            <tr class="${trClass}">
                                <td class="ListItem">
                                    <input type="checkbox" name="selectedIds" value="${item.id}">
                                </td>
                                <td class="ListItem">
                                    ${item.id}
                                </td>
                                <td class="ListItem">
                                    ${fio}
                                </td>

                                <td class="ListItem">
                                    <c:out value="${department.fullName}"/>
                                </td>
                                <td class="ListItem">
                                    ${info}
                                </td>
                                <td class="ListItem">
                                    <c:out value="${login.userId}"/>
                                </td>
                                <td class="${styleClass}">
                                    ${shemeName}
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>

                </table>
                <c:if test="${pageOffset > 0 || sizeResult > 10}">
                <table cellspacing="0" cellpadding="0" class="tblNumRec">
                    <tr><td colspan="6">&nbsp;</td></tr>
                    <tr>
                        <td style="width:50%;">&nbsp;</td>
                        <td nowrap="nowrap" style="border:0;">
                            <c:choose>
                                <c:when test="${pageOffset>0}">
                                    <a href="#" onclick="addElementToForm('paginationOffset','${pageOffset}');
                                            setElement('paginationOffset', '${pageOffset-pageSize}');
                                            callOperation(event,'button.filter'); return false" href="#">
                                        <div class="activePaginLeftArrow"></div>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <div class="inactivePaginLeftArrow"></div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td nowrap="nowrap" style="padding: 5px;">
                            <span class="tblNumRecIns">
                                ${pageOffset + 1}
                                &nbsp;-&nbsp;
                                ${pageOffset + pageItems}
                            </span>
                        </td>
                        <td nowrap="nowrap" style="border:0;">
                            <c:choose>
                                <c:when test="${pageSize < sizeResult}">
                                    <a href="#" onclick="addElementToForm('paginationOffset','${pageOffset}');
                                            setElement('paginationOffset', '${pageOffset+pageSize}');
                                            callOperation(event,'button.filter'); return false">
                                        <div class="activePaginRightArrow"></div>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <div class="inactivePaginRightArrow"></div>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td nowrap="nowrap" align="right" width="100%">
                            <div>
                                <div class="floatRight">
                                    <div class="float paginationItemsTitle">Показывать по:</div>
                                    <input type="hidden" value="${resOnPage}" name="resOnPage">
                                    <c:choose>
                                        <c:when test="${resOnPage == 10}">
                                            <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">10</span></div></div>
                                            <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '20'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">20</a></div>
                                            <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '50'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">50</a></div>
                                        </c:when>
                                        <c:when test="${resOnPage == 20}">
                                            <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '10'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">10</a></div>
                                            <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">20</span></div></div>
                                            <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '50'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">50</a></div>
                                        </c:when>
                                        <c:when test="${resOnPage == 50}">
                                            <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '10'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">10</a></div>
                                            <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '20'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">20</a></div>
                                            <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">50</span></div></div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                </c:if>
            </tiles:put>
        </c:if>

        <tiles:put name="buttons">
            <c:if test="${phiz:impliesOperation('AssignEmployeeAccessOperation','AssignEmployeeAccess')}">

                <div class="buttDiv" style="padding-bottom:5px;">
                    <table cellpadding="0" cellspacing="0" height="100%"><tr><td valign="bottom">
                    <html:select property="accessSchemeId">
                            <html:option value="">
                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                            </html:option>
                            <c:set var="helpers" value="${form.helpers}"/>
                            <c:forEach var="helper" items="${helpers}">
                                <c:set var="helperCategory" value="${helper.category}"/>
                                <c:if test="${fn:length(helpers) > 1}">
                                <optgroup label="<bean:message key="label.scheme.category.${helperCategory}" bundle="schemesBundle"/>">
                                </c:if>
                                <c:set var="accessSchemes" value="${helper.schemes}"/>
                                <c:forEach var="scheme" items="${accessSchemes}">
                                    <c:choose>
                                        <c:when test="${phiz:isMultiblockMode()}">
                                            <c:set var="itemId" value="${scheme.externalId}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="itemId" value="${scheme.id}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <html:option value="${itemId}" styleClass="managerInfo${helperCategory}">
                                        <c:out value="${scheme.name}"/>
                                    </html:option>
                                </c:forEach>
                                <c:if test="${fn:length(helpers) > 1}">
                                </optgroup>
                                </c:if>
                            </c:forEach>
                    </html:select>
                    </td></tr></table>
                </div>
                <tiles:insert definition="commandButton" flush="false" operation="AssignEmployeeAccessOperation">
                    <tiles:put name="commandKey"     value="button.assignScheme"/>
                    <tiles:put name="commandHelpKey" value="button.assignScheme"/>
                    <tiles:put name="bundle"  value="personsBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            if(!checkSelection('selectedIds','Выберите сотрудников'))
                                return false;
                            return validateManagerInfo();
                        }
                    </tiles:put>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="commandButton" flush="false" operation="ChangeLockEmployeeOperation">
                <tiles:put name="commandKey"     value="button.unlock"/>
                <tiles:put name="commandHelpKey" value="button.unlock"/>
                <tiles:put name="bundle"  value="employeesBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds','Выберите сотрудников');
                    }
                </tiles:put>
                <tiles:put name="confirmText"    value="Вы действительно хотите снять блокировку с выбранных сотрудников?"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="ChangeLockEmployeeOperation">
                <tiles:put name="commandTextKey" value="button.lock"/>
                <tiles:put name="commandHelpKey" value="button.lock"/>
                <tiles:put name="bundle" value="employeesBundle"/>
                <tiles:put name="onclick" value="callReasonWindow()"/>
            </tiles:insert>
             <tiles:insert definition="commandButton" flush="false" operation="RemoveEmployeeOperation">
                <tiles:put name="commandKey"     value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle"  value="employeesBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds','Выберите сотрудников');
                    }
                </tiles:put>
                <tiles:put name="confirmText"    value="Вы действительно хотите удалить выбранных сотрудников банка?"/>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage">
            <c:choose>
                <c:when test="${form.fromStart}">
                    Для поиска сотрудников в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                </c:when>
                <c:otherwise>
                    Не найдено ни одного сотрудника, соответствующего заданному фильтру!
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
  </tiles:put>
</tiles:insert>
</html:form>
