<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/riskProfile/list">
	<tiles:insert definition="listPFPRiskProfile">
        <tiles:put name="submenu" type="string" value="riskProfileList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpRiskProfileBundle" key="label.pageTitle"/>
        </tiles:put>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditRiskProfileOperation">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                <tiles:put name="action"            value="/pfp/riskProfile/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <%-- данные --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function selectIfOneItem(type)
                {
                    var selector = $('[name=selectedIds].segment' + type);
                    if (selector.length == 1)
                        selector.attr('checked', true);
                }

                function checkAnySelection(type, message)
                {
                    var selector = $('[name=selectedIds].segment' + type + ':checked');
                    if (selector.length == 0){
                        clearLoadMessage();
                        return groupError(message);
                    }
                    return true;
                }

                function checkSingleSelection(type, message)
                {
                    var selector = $('[name=selectedIds].segment' + type + ':checked');
                    if (selector.length != 1){
                        clearLoadMessage();
                        return groupError(message);
                    }
                    return true;
                }

                function validateRemoveRiskProfile(type)
                {
                    selectIfOneItem(type);
                    return checkAnySelection(type, '<bean:message bundle="pfpRiskProfileBundle" key="label.table.checkSelection"/>');
                }

                function doRemove(type)
                {
                    if(!validateRemoveRiskProfile(type) || !confirm('<bean:message bundle="pfpRiskProfileBundle" key="label.table.removeQuestion"/>'))
                        return;

                    $('[name=selectedIds]:not(.segment' + type + ')').attr('checked', false);
                    callOperation(null, 'button.remove');
               }

                function goToEdit(type)
                {
                    selectIfOneItem(type);
                    if (!checkAnySelection(type, '<bean:message bundle="pfpRiskProfileBundle" key="label.table.checkSelection"/>') ||
                        (!checkSingleSelection(type, '<bean:message bundle="pfpRiskProfileBundle" key="label.table.checkOneSelection"/>')))
                        return;

                    var url = "${phiz:calculateActionURL(pageContext,'/pfp/riskProfile/edit')}";
                    var id = $('[name=selectedIds].segment' + type + ':checked').val();
                    goTo(url + "?id=" + id);
                }

                function selectAll(selector)
                {
                    var myself = $(selector);
                    var className = myself.attr('class');
                    $('[name=selectedIds].' + className).attr('checked', myself.is(':checked'));
                }
            </script>
            <c:set var="riskProfilesMap" value="${form.riskProfilesMap}"/>

            <c:forEach var="segment" items="${form.fields['segmentList']}">
                <c:set var="riskProfilesKey">${segment}</c:set>
                <c:set var="riskProfiles" value="${riskProfilesMap[riskProfilesKey]}"/>
                <c:set var="segmentTitle"><bean:message bundle="pfpRiskProfileBundle" key="segment.${segment}"/></c:set>
                <c:if test="${not phiz:isConcertedRiskProfiles(segment)}">
                    <tiles:insert definition="roundBorderLight" flush="false">
                        <tiles:put name="color" value="red"/>
                        <tiles:put name="data">
                            <bean:message bundle="pfpRiskProfileBundle" key="label.tableWarning" arg0="${segmentTitle}"/>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty riskProfiles}">
                    <h3 class="dataForSegmentTitle">${segmentTitle}</h3>
                </c:if>
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="riskProfileList${segment}"/>
                    <tiles:put name="data">
                        <tr>
                            <th class="Width20">
                                <input class="segment${segment}" type="checkbox" name="isSelectAll" onclick="selectAll(this);">
                            </th>
                            <th width="30%">
                                <bean:message bundle="pfpRiskProfileBundle" key="label.table.name"/>
                            </th>
                            <th width=5%">
                                <bean:message bundle="pfpRiskProfileBundle" key="label.table.weight"/>
                            </th>
                            <th>
                                <bean:message bundle="pfpRiskProfileBundle" key="label.table.description"/>
                            </th>
                            <th width="15%">
                                <bean:message bundle="pfpRiskProfileBundle" key="label.table.product"/>
                            </th>
                            <th width="10%">
                                <bean:message bundle="pfpRiskProfileBundle" key="label.table.percent"/>
                            </th>
                        </tr>
                        <c:forEach var="profile" items="${riskProfiles}">
                            <c:set var="productsCount" value="${phiz:mapSize(profile.productsWeights)}"/>
                            <tr>
                                <td rowspan="${productsCount}" valign="top">
                                    <input class="segment${segment}" type="checkbox" name="selectedIds" value="${profile.id}">
                                </td>
                                <td rowspan="${productsCount}" valign="top" class="word-wrap">
                                    <phiz:link  action="/pfp/riskProfile/edit" operationClass="EditRiskProfileOperation">
                                        <phiz:param name="id" value="${profile.id}"/>
                                        <c:out value="${profile.name}"/>
                                    </phiz:link>
                                </td>
                                <td rowspan="${productsCount}" valign="top" class="word-wrap">
                                    <c:set var="minWeight" value="${profile.minWeight}"/>
                                    <c:set var="maxWeight" value="${profile.maxWeight}"/>
                                    <c:choose>
                                        <c:when test="${empty minWeight}">
                                            &nbsp;<bean:message bundle="pfpRiskProfileBundle" key="label.table.weight.negativeInfinity"/>&nbsp;<c:out value="${maxWeight}"/>
                                        </c:when>
                                        <c:when test="${empty maxWeight}">
                                            &nbsp;<bean:message bundle="pfpRiskProfileBundle" key="label.table.weight.positivepositive"/>&nbsp;<c:out value="${minWeight}"/>
                                        </c:when>
                                        <c:otherwise>
                                            &nbsp;<c:out value="${minWeight}"/>&nbsp;
                                            <bean:message bundle="pfpRiskProfileBundle" key="label.table.weight.to"/>&nbsp;
                                            <c:out value="${maxWeight}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td rowspan="${productsCount}" valign="top" class="word-wrap">
                                    <c:out value="${profile.description}"/>
                                </td>
                                <td class="word-wrap">
                                    <c:set var="index" value="${0}"/>
                                    <logic:iterate id="type" name="ListRiskProfileForm" property="productTypeList">
                                        <c:set var="productsWeights" value="${profile.productsWeights[type]}"/>
                                        <c:if test="${not empty productsWeights}">
                                                <c:out value="${type.description}"/>
                                            </td>
                                            <td class="word-wrap">
                                                <c:out value="${profile.productsWeights[type]}"/>&nbsp;<bean:message bundle="pfpRiskProfileBundle" key="label.table.percentChar"/>
                                            <c:if test="${index + 1 < productsCount}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="word-wrap">
                                            </c:if>
                                            <c:set var="index" value="${index + 1}"/>
                                        </c:if>
                                    </logic:iterate>
                                </td>
                            </tr>
                        </c:forEach>

                        <tiles:put name="buttons">
                            <tiles:insert definition="clientButton" flush="false" operation="EditRiskProfileOperation">
                                <tiles:put name="commandTextKey"    value="button.edit"/>
                                <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                                <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                                <tiles:put name="onclick"           value="goToEdit('${segment}');"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false" operation="RemoveRiskProfileOperation">
                                <tiles:put name="commandTextKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle"         value="pfpRiskProfileBundle"/>
                                <tiles:put name="onclick"        value="doRemove('${segment}');"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="isEmpty" value="${empty riskProfiles}"/>
                        <tiles:put name="emptyMessage">
                            <div class="emptyDataForSegmentMessage">
                                <h3>${segmentTitle}</h3>
                                <p><bean:message bundle="pfpRiskProfileBundle" key="label.tableEmpty"/></p>
                            </div>
                        </tiles:put>
                    </tiles:put>
                </tiles:insert>
            </c:forEach>
        </tiles:put>
    </tiles:insert>
</html:form>
