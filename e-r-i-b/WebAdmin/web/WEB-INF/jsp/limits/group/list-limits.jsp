<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath"    value="${globalUrl}/images"/>
<c:set var="imagePath"          value="${skinUrl}/images"/>

<html:form action="/limits/group/risk/general/list" onsubmit="return setEmptyAction(event);">

    <tiles:insert definition="limitsMain">
        <c:set var="bundle" value="limitsBundle"/>
        <c:set var="channel">
            <c:choose>
                <c:when test="${param['channel'] == 'general'}">general</c:when>
                <c:when test="${param['channel'] == 'mobile'}">mobile</c:when>
                <c:when test="${param['channel'] == 'atm'}">atm</c:when>
                <c:when test="${param['channel'] == 'ermb'}">ermb</c:when>
                <c:when test="${param['channel'] == 'social'}">social</c:when>
            </c:choose>
        </c:set>
        <c:set var="securityType">
            <c:choose>
                <c:when test="${param['securityType'] == 'HIGHT'}">HIGHT</c:when>
                <c:when test="${param['securityType'] == 'MIDDLE'}">MIDDLE</c:when>
                <c:when test="${param['securityType'] == 'LOW'}">LOW</c:when>
            </c:choose>
        </c:set>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="simpleUrl"  value="/limits/group/risk/${channel}/edit?departmentId=${form.departmentId}&channel=${channel}&securityType=${securityType}"/>
        <c:set var="baseUrl"    value="${phiz:calculateActionURL(pageContext, simpleUrl)}"/>
        <c:set var="notEmptyFormData" value="${not empty form.data}"/>

        <tiles:put name="submenu"   type="string" value="GeneralGroupRiskList/${channel}/${securityType}"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="${bundle}" key="page.list.name"/>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="EditLimitOperation" service="LimitsManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="${simpleUrl}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>

            <c:if test="${notEmptyFormData}">
                <tiles:insert definition="commandButton" flush="false" operation="RemoveLimitOperation" service="ConfirmLimitsManagment">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle"         value="${bundle}"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            var checkBoxList = $("form :checkbox:checked");
                            if(checkBoxList.size() < 1)
                                return groupError('Выберите хотя бы один лимит!');

                            var selectedCheckBoxes = checkBoxList;
                            for (var i = 0; i < selectedCheckBoxes.length; i++)
                            {
                                var selectedCheckBox = selectedCheckBoxes[i];
                                if(selectedCheckBox.value!='on')
                                    selectedCheckBox.name = "selectedIds";
                            }
                            return true;
                        }
                    </tiles:put>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="filter" type="string">
             <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.limit.group.risk"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                       <html:select property="filter(groupRisk)">
                            <html:option value="">Все группы риска</html:option>
                            <c:forEach var="groupRisk" items="${form.groupsRiskForFilter}">
                                <html:option value="${groupRisk.id}">
                                     <c:out value="${groupRisk.name}"/>
                                </html:option>
                            </c:forEach>
                       </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.period.creation.date"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <bean:message bundle="${bundle}" key="filter.creation.date.from"/>
                    &nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                               size="10" name="filter(fromCreationDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(fromCreationDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                    &nbsp;<bean:message bundle="${bundle}" key="filter.creation.date.to"/>&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                               size="10" name="filter(toCreationDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(toCreationDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.period.start.date"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <bean:message bundle="${bundle}" key="filter.start.date.from"/>
                    &nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                               size="10" name="filter(fromStartDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(fromStartDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                    &nbsp;<bean:message bundle="${bundle}" key="filter.start.date.to"/>&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                               size="10" name="filter(toStartDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(toStartDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.limit"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                        <html:select property="filter(restrictionType)" styleId="filter(restrictionType)" onchange="changeDisabledFilter()">
                            <html:option value="">Все</html:option>
                            <html:option value="AMOUNT_IN_DAY">На сумму операций в сутки</html:option>
                            <html:option value="MIN_AMOUNT">На минимальную сумму операции</html:option>
                            <html:option value="OPERATION_COUNT_IN_DAY">На количество операций в сутки</html:option>
                            <html:option value="OPERATION_COUNT_IN_HOUR">На количество операций в час</html:option>
                        </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="filter.amount"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name"      value="amount"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="filter.operation.count"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name"      value="operationCount"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.status"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                        <html:select property="filter(status)">
                            <html:option value="">Все статусы</html:option>
                            <html:option value="1">Действует</html:option>
                            <html:option value="2">Введен</html:option>
                            <html:option value="3">Архив</html:option>
                            <html:option value="4">Черновик</html:option>
                        </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.operation.type"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                        <html:select property="filter(operationType)">
                            <html:option value=""><bean:message bundle="${bundle}" key="filter.operation.type.all"/></html:option>
                            <html:option value="IMPOSSIBLE_PERFORM_OPERATION"><bean:message bundle="${bundle}" key="filter.operation.type.IMPOSSIBLE_PERFORM_OPERATION"/></html:option>
                            <html:option value="NEED_ADDITIONAL_CONFIRN"><bean:message bundle="${bundle}" key="filter.operation.type.NEED_ADDITIONAL_CONFIRN"/></html:option>
                        </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function changeDisabledFilter()
                {
                    var elem = document.getElementById("filter(restrictionType)");
                    var inputAmount = document.getElementsByName("filter(amount)")[0];
                    var inputOperationCount = document.getElementsByName("filter(operationCount)")[0];
                    if (elem != null)
                    {
                        switch (elem.value)
                        {
                            case "AMOUNT_IN_DAY" :
                            {
                                inputAmount.disabled = false;
                                inputOperationCount.disabled = true;
                                return;
                            }
                            case "MIN_AMOUNT" :
                            {
                                inputAmount.disabled = false;
                                inputOperationCount.disabled = true;
                                return;
                            }
                            case "OPERATION_COUNT_IN_DAY" :
                            {
                                inputAmount.disabled = true;
                                inputOperationCount.disabled = false;
                                return;
                            }
                            case "OPERATION_COUNT_IN_HOUR" :
                            {
                                inputAmount.disabled = true;
                                inputOperationCount.disabled = false;
                                return;
                            }
                            default:
                            {
                                inputAmount.disabled = false;
                                inputOperationCount.disabled = false;
                                return;
                            }
                        }
                    }
                }
    
                doOnLoad(changeDisabledFilter);
            </script>
            <c:choose>
                <c:when test="${notEmptyFormData}">
                    <c:forEach var="groupRisk" items="${form.data}">
                        <h3>${groupRisk.first}</h3>
                        <tiles:insert definition="tableTemplate" flush="false">
                            <tiles:put name="id"    value="limitsList"/>
                            <tiles:put name="grid">
                                <sl:collection id="limit" model="list" name="groupRisk" property="second" bundle="${bundle}" >
                                    <sl:collectionParam id="selectType" value="checkbox"/>
                                    <sl:collectionParam id="selectProperty" value="id"/>
                                    <sl:collectionParam id="selectName" value="selectedIds${groupRisk.first}"/>
                                    <sl:collectionItem title="grid.creation.date">
                                        <sl:collectionItemParam id="value" condition="${not empty(limit.creationDate)}">
                                            <c:choose>
                                                <c:when test="${limit.status == 'ACTIVE' || limit.status == 'ENTERED' || limit.status == 'DRAFT'}">
                                                    <a href="${baseUrl}&id=${limit.id}">
                                                        <fmt:formatDate value="${limit.creationDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:formatDate value="${limit.creationDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </sl:collectionItemParam>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="grid.start.date">
                                        <sl:collectionItemParam id="value" condition="${not empty(limit.startDate)}">
                                            <c:if test="${not empty limit}">
                                                <fmt:formatDate value="${limit.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                            </c:if>
                                        </sl:collectionItemParam>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="grid.limit">
                                        <c:if test="${not empty limit}">
                                            <bean:write name="limit" property="restrictionType.description"/>
                                        </c:if>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="grid.amount">
                                        <c:if test="${not empty limit}">
                                            <c:set var="restrictionType" value="${limit.restrictionType}"/>
                                            <c:if test="${restrictionType == 'AMOUNT_IN_DAY' || restrictionType == 'MIN_AMOUNT'}">
                                                <nobr>
                                                    <bean:write name="limit" property="amount.decimal" format="###,##0.00"/>
                                                    <bean:message bundle="${bundle}" key="grid.currency"/>
                                                </nobr>
                                            </c:if>
                                            <c:if test="${restrictionType == 'OPERATION_COUNT_IN_DAY' || restrictionType == 'OPERATION_COUNT_IN_HOUR'}">
                                                <nobr>
                                                    <bean:write name="limit" property="operationCount"/> операций
                                                </nobr>
                                            </c:if>
                                        </c:if>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="grid.operation.type">
                                        <c:if test="${not empty limit}">
                                            <bean:message bundle="${bundle}" key="filter.operation.type.${limit.operationType}"/>
                                        </c:if>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="grid.status">
                                        <c:if test="${not empty limit}">
                                            <bean:message bundle="${bundle}" key="filter.status.${limit.status}"/>
                                        </c:if>
                                    </sl:collectionItem>
                                </sl:collection>
                                <tiles:put name="isEmpty" value="${empty groupRisk.second}"/>
                            </tiles:put>
                            <tiles:put name="emptyMessage" value="Не задан куммулятивный лимит для данной группы риска"/>
                        </tiles:insert>
                        <br/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="messageTab" align="center">
                        <bean:message bundle="${bundle}" key="label.empty.group.risks"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>
