<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/persons/list/full">
    <c:set var="emptyProfileNodeMessage"><bean:message key="label.list.client.node.empty" bundle="personsBundle"/></c:set>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="personList">
    <tiles:put name="submenu" type="string" value="ClientListFull"/>
    <tiles:put name="pageTitle" type="string">
    	<bean:message key="form.list.full.title" bundle="personsBundle"/>
    </tiles:put>
    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.client"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="name"   value="fio"/>
            <tiles:put name="size"   value="50"/>
            <tiles:put name="maxlength"  value="100"/>
            <tiles:put name="isDefault"><bean:message key="form.list.full.filter.hint.fio" bundle="personsBundle"/></tiles:put>
        </tiles:insert>

        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.dul"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="name"   value="document"/>
            <tiles:put name="size"   value="50"/>
            <tiles:put name="maxlength"  value="30"/>
        </tiles:insert>

        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label"  value="label.terrbank"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="data">
                <html:select property="filter(tb)" styleClass="select">
                    <html:option value=""><bean:message key="form.list.full.filter.hint.all" bundle="personsBundle"/></html:option>
                    <c:forEach var="tb" items="${form.allowedTB}">
                        <html:option value="${tb.key}">
                            <c:out value="${tb.value}"/>
                        </html:option>
                    </c:forEach>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="filterDateField" flush="false">
            <tiles:put name="label"  value="label.birthDay"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="name"   value="birthday"/>
        </tiles:insert>

        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"  value="label.login"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="name"   value="login"/>
            <tiles:put name="size"   value="50"/>
            <tiles:put name="maxlength"  value="30"/>
            <tiles:put name="isDefault"><bean:message key="form.list.full.filter.hint.login" bundle="personsBundle"/></tiles:put>
        </tiles:insert>

        <%--Тип договора--%>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label"  value="label.agreementType"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="data">
                <html:select property="filter(creationType)" styleClass="select">
                    <html:option value="">Все</html:option>
                    <html:option value="UDBO"><bean:message key="label.agreementType.UDBO" bundle="personsBundle"/></html:option>
                    <html:option value="SBOL"><bean:message key="label.agreementType.SBOL" bundle="personsBundle"/></html:option>
                    <html:option value="CARD"><bean:message key="label.agreementType.CARD" bundle="personsBundle"/></html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <%--Номер заявления--%>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label" value="label.agreementNumber"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="maxlength" value="16"/>
            <tiles:put name="name" value="agreementNumber"/>
        </tiles:insert>
      <%--Мобильный телефон--%>
      <tiles:insert definition="filterTextField" flush="false">
          <tiles:put name="label" value="label.mobilePhone"/>
          <tiles:put name="bundle" value="personsBundle"/>
          <tiles:put name="mandatory" value="false"/>
          <tiles:put name="maxlength" value="20"/>
          <tiles:put name="name" value="mobilePhone"/>
      </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <input type="hidden" name="blockReason" id="blockReason" value=""/>
      	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
      	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>
        <script type="text/javascript">
            function callReasonWindow()
    		{
                checkIfOneItem("selectedIds");
    			if (!checkOneSelection("selectedIds", "<bean:message key='form.list.full.table.message.lock.oneSelection.lock' bundle='personsBundle'/>"))
    				return;

                if (!validateNodes())
                {
                    alert('${emptyProfileNodeMessage}');
                    return false;
                }

                window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock')}", "", "width=1000,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=1");
    		}

            function validateNodes()
            {
                var result = true;
                $('[name=selectedIds]:checked').each(function(index, element){
                    var id = $(element).val();
                    var nodeIdElement = $('[name=field(nodeId' + id + ')]');
                    if (isEmpty(nodeIdElement.val()))
                    {
                        result = false;
                        return false;
                    }
                });
                return result;
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

            function validateUnlock()
            {
                if (!checkOneSelection("selectedIds", "<bean:message key='form.list.full.table.message.lock.oneSelection.unlock' bundle='personsBundle'/>"))
                    return false;

                if (!validateNodes())
                {
                    alert('${emptyProfileNodeMessage}');
                    return false;
                }

                return true;
            }

            <c:if test="${form.fromStart}">
               //показываем фильтр при старте
                $(document).ready(function(){switchFilter(this)});
            </c:if>
        </script>
        <c:set var="currentNode" value="${phiz:getCurrentNode()}"/>
    	<tiles:insert definition="tableTemplate" flush="false">
    		<tiles:put name="id" value="personList"/>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false" operation="ChangeLockClientOperation">
  					<tiles:put name="commandTextKey" value="button.lock"/>
  					<tiles:put name="commandHelpKey" value="button.lock"/>
  					<tiles:put name="bundle" value="personsBundle"/>
  					<tiles:put name="onclick" value="callReasonWindow()"/>
  				</tiles:insert>
                  <tiles:insert definition="commandButton" flush="false" operation="ChangeLockClientOperation">
  					<tiles:put name="commandKey" value="button.unlock"/>
  					<tiles:put name="commandHelpKey" value="button.unlock.help"/>
  					<tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="validationFunction" value="validateUnlock()"/>
  				</tiles:insert>
                <tiles:insert definition="commandButton" flush="false" service="CSAClientManagement">
                    <tiles:put name="commandKey" value="button.restore.password"/>
                    <tiles:put name="commandHelpKey" value="button.restore.password.help"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="validationFunction">
                        function(){return checkOneSelection("selectedIds", 'Выберите одного пользователя для восставновления пароля')}
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="item" model="list" property="data" bundle="personsBundle" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                    <c:set var="clientNodeId"   value=""/>
                    <c:set var="clientNodeType" value=""/>
                    <c:set var="isSelected"  value="${false}"/>
                    <c:forEach var="node" items="${item.nodes}">
                        <c:set var="currentNodeType">${node.type}</c:set>
                        <c:set var="currentNodeState">${node.state}</c:set>
                        <c:set var="clientCurrentNode" value="${phiz:getNodeInfo(node.id)}"/>
                        <c:if test="${clientCurrentNode.adminAvailable and not isSelected}">
                            <c:choose>
                                <c:when test="${empty clientNodeId}">
                                    <c:set var="clientNodeId"   value="${node.id}"/>
                                    <c:set var="clientNodeType" value="${currentNodeType}"/>
                                </c:when>
                                <c:when test="${currentNodeType eq 'MAIN'}">
                                    <c:set var="clientNodeId"   value="${node.id}"/>
                                    <c:set var="clientNodeType" value="${currentNodeType}"/>
                                    <c:set var="isSelected"     value="${node.id eq currentNode.id}"/>
                                </c:when>
                                <c:when test="${currentNodeState eq 'WAIT_MIGRATION'}">
                                    <c:set var="clientNodeId"   value="${node.id}"/>
                                    <c:set var="clientNodeType" value="${currentNodeType}"/>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </c:forEach>

                    <sl:collectionItem title="label.FIO">
                        <c:set value="0" var="blockCount"/>
    					<c:set value="<b>Причина(ы) блокировки:</b>" var="blockReasons"/>
                        <c:forEach var="block" items="${item.blocks}" varStatus="status">
                            <c:if test="${blockCount == 0}">
                                <span id="state${stateNumber}"
                                   onmouseover="showLayer('state${stateNumber}','stateDescription${stateNumber}','default',10);"
                                   onmouseout="hideLayer('stateDescription${stateNumber}');" style="text-decoration:none">
                                        <img src='${imagePath}/iconSm_lock.gif' width='12px' height='12px' alt='' border='0'/>
                                </span>
                            </c:if>
                            <c:set var="blockCount" value="${blockCount + 1}"/>
                            <c:set var="blockReasons">${blockReasons}<br><b>${blockCount}</b>. <c:out value="${block.reason}"/></c:set>
                            <c:if test="${not empty block.from}">
                                <c:set var="blockReasons">
                                    ${blockReasons}, с
                                    <fmt:formatDate value="${block.from.time}" pattern="dd.MM.yyyy"/>
                                </c:set>
                            </c:if>
                            <c:if test="${not empty block.to}">
                                <c:set var="blockReasons">
                                    ${blockReasons} по
                                    <fmt:formatDate value="${block.to.time}" pattern="dd.MM.yyyy"/>
                                </c:set>
                            </c:if>
                            <c:set var="blockReasons" value="${blockReasons}."/>
                        </c:forEach>
                        <div id="stateDescription${stateNumber}"
                             onmouseover="showLayer('state${stateNumber}','stateDescription${stateNumber}','default',10);"
                             onmouseout="hideLayer('stateDescription${stateNumber}');"
                             class='layerFon' style='position:absolute; display:none; width:300px; overflow:auto;'>
                                    ${blockReasons}
                        </div>
    					<c:set value="${stateNumber+1}" var="stateNumber"/>

                        <c:set var="userFIO" value="${item.surname} ${item.firstname} ${item.patrname}"/>
                        <c:choose>
                            <c:when test="${empty clientNodeId}">
                                <phiz:link url="#" onclick="alert('${emptyProfileNodeMessage}'); return false;" serviceId="PersonManagement">
                                    <c:out value="${userFIO}"/>
                                </phiz:link>
                            </c:when>
                            <c:when test="${currentNode.id eq clientNodeId}">
                                <phiz:link action="/persons/list/choose" serviceId="PersonManagement">
                                    <phiz:param name="field(firstname)" value="${item.firstname}"/>
                                    <phiz:param name="field(surname)" value="${item.surname}"/>
                                    <phiz:param name="field(patrname)" value="${item.patrname}"/>
                                    <phiz:param name="field(birthDay)"><fmt:formatDate value="${item.birthday.time}" pattern="dd.MM.yyyy"/></phiz:param>
                                    <phiz:param name="field(passport)" value="${item.document}"/>
                                    <phiz:param name="field(tb)" value="${item.tb}"/>
                                    <c:out value="${userFIO}"/>
                                </phiz:link>
                            </c:when>
                            <c:otherwise>
                                <phiz:link action="/nodes/change" serviceId="PersonManagement">
                                    <phiz:param name="parameters(field(firstname))" value="${item.firstname}"/>
                                    <phiz:param name="parameters(field(surname))" value="${item.surname}"/>
                                    <phiz:param name="parameters(field(patrname))" value="${item.patrname}"/>
                                    <phiz:param name="parameters(field(birthDay))"><fmt:formatDate value="${item.birthday.time}" pattern="dd.MM.yyyy"/></phiz:param>
                                    <phiz:param name="parameters(field(passport))" value="${item.document}"/>
                                    <phiz:param name="parameters(field(tb))" value="${item.tb}"/>
                                    <phiz:param name="action" value="/persons/list/choose.do"/>
                                    <phiz:param name="nodeId" value="${clientNodeId}"/>
                                    <c:out value="${userFIO}"/>
                                </phiz:link>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="field(firstName${item.id})" value="${item.firstname}"/>
                        <input type="hidden" name="field(patrname${item.id})" value="${item.patrname}"/>
                        <input type="hidden" name="field(surname${item.id})" value="${item.surname}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.dul">
                        <c:out value="${item.document}"/>
                        <input type="hidden" name="field(passport${item.id})" value="${item.document}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.birthDay">
                        <c:if test="${not empty item.birthday}">
                            <c:set var="birthDay"><fmt:formatDate value="${item.birthday.time}" pattern="dd.MM.yyyy"/></c:set>
                            <c:out value="${birthDay}"/>
                            <input type="hidden" name="field(birthDate${item.id})" value="${birthDay}"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.terrbank">
                        <c:set var="tbName" value="${form.allowedTB[item.tb]}"/>
                        <c:choose>
                            <c:when test="${not empty tbName}">
                                <c:out value="${tbName}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${item.tb}"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="field(tb${item.id})"       value="${item.tb}"/>
                        <input type="hidden" name="field(nodeId${item.id})"   value="${clientNodeId}"/>
                        <input type="hidden" name="field(nodeType${item.id})" value="${clientNodeType}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.agreementType"><bean:message key="label.agreementType.${item.creationType}" bundle="personsBundle"/></sl:collectionItem>
                    <sl:collectionItem title="label.agreementNumber" property="agreementNumber"/>
                    <sl:collectionItem title="label.login">
                        <c:choose>
                            <c:when test="${not empty item.login}">
                                <c:out value="${item.login}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${item.userId}"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="field(login${item.id})" value="<c:out value='${not empty item.login?item.login:item.userId}'/>"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.lastLoginDate">
                        <c:if test="${not empty item.lastLoginDate}">
                            <fmt:formatDate value="${item.lastLoginDate.time}" pattern="dd.MM.yyyy"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.activeErmbPhone">
                        <c:if test="${not empty item.ermbActivePhone}">
                            <c:choose>
                                <c:when test="${empty clientNodeId}">
                                    <phiz:link url="#" onclick="alert('${emptyProfileNodeMessage}'); return false;" serviceId="PersonManagement">
                                        <c:out value="${item.ermbActivePhone}"/>
                                    </phiz:link>
                                </c:when>
                                <c:when test="${currentNode.id eq clientNodeId}">
                                    <phiz:link action="/persons/list/choose/ermb" serviceId="PersonManagement">
                                        <phiz:param name="field(ermbActivePhone)" value="${item.ermbActivePhone}"/>
                                        <c:out value="${item.ermbActivePhone}"/>
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    <phiz:link action="/nodes/change" serviceId="PersonManagement">
                                        <phiz:param name="parameters(field(ermbActivePhone))" value="${item.ermbActivePhone}"/>
                                        <phiz:param name="action" value="/persons/list/choose/ermb.do"/>
                                        <phiz:param name="nodeId" value="${clientNodeId}"/>
                                        <c:out value="${item.ermbActivePhone}"/>
                                    </phiz:link>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.ermbStatus"><bean:message key="label.ermbStatus.${item.ermbStatus}" bundle="personsBundle"/></sl:collectionItem>
                    <sl:collectionItem hidden="true">
                        <input type="hidden" name="field(ermbStatus)" value="${item.ermbStatus}"/>
                    </sl:collectionItem>
                </sl:collection>
            </tiles:put>
    	<tiles:put name="isEmpty" value="${empty form.data}"/>
    	<tiles:put name="emptyMessage"><bean:message key="form.list.full.table.message.emptyList" bundle="personsBundle"/></tiles:put>
        </tiles:insert>
    	</tiles:put>
    </tiles:insert>

    <c:if test="${form.failureIMSICheck}">
        <script>
            $(document).ready(function ()
            {
                $('[name=selectedIds]').each(function (index, element)
                {
                    var id = $(element).val();
                    if (id == ${form.selectedIds[0]})
                    {
                        element.checked = true;
                    }
                });
                win.open('failureIMSICheckWin');
            });
        </script>
    </c:if>

    <tiles:insert definition="failureIMSICheckWin" flush="false">
        <tiles:put name="id" value="failureIMSICheckWin"/>
    </tiles:insert>
</html:form>
