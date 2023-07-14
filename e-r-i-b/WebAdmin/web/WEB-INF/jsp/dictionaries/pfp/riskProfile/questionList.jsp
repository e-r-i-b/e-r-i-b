<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/riskProfile/question/list">
	<tiles:insert definition="listPFPRiskProfile">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpRiskProfileBundle" key="question.label.pageTitle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="riskProfileQuestionList"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditRiskProfileQuestionOperation">
                <tiles:put name="commandTextKey"    value="question.button.add"/>
                <tiles:put name="commandHelpKey"    value="question.button.add.help"/>
                <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                <tiles:put name="action"            value="/pfp/riskProfile/question/edit.do"/>
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

                function validateRemoveQuestion(type)
                {
                    selectIfOneItem(type);
                    return checkAnySelection(type, '<bean:message bundle="pfpRiskProfileBundle" key="question.label.table.checkSelection"/>');
                }

                function doRemove(type)
                {
                    if(!validateRemoveQuestion(type) || !confirm('<bean:message bundle="pfpRiskProfileBundle" key="question.label.table.removeQuestion"/>'))
                        return;

                    $('[name=selectedIds]:not(.segment' + type + ')').attr('checked', false);
                    callOperation(null, 'button.remove');
               }

                function goToEdit(type)
                {
                    selectIfOneItem(type);
                    if (!checkAnySelection(type, '<bean:message bundle="pfpRiskProfileBundle" key="question.label.table.checkSelection"/>') ||
                        (!checkSingleSelection(type, '<bean:message bundle="pfpRiskProfileBundle" key="question.label.table.checkOneSelection"/>')))
                        return;

                    var url = "${phiz:calculateActionURL(pageContext,'/pfp/riskProfile/question/edit')}";
                    var id = $('[name=selectedIds].segment' + type + ':checked').val();
                    window.location = url + "?id=" + id;
                }

                function selectAll(selector)
                {
                    var myself = $(selector);
                    var className = myself.attr('class');
                    $('[name=selectedIds].' + className).attr('checked', myself.is(':checked'));
                }
            </script>
            <c:set var="questionsMap" value="${form.questionsMap}"/>
            <c:forEach var="segment" items="${form.fields['segmentList']}">
                <c:set var="questionsKey">${segment}</c:set>
                <c:set var="questions" value="${questionsMap[questionsKey]}"/>
                <c:if test="${not empty questions}">
                    <h3 class="dataForSegmentTitle"><bean:message bundle="pfpRiskProfileBundle" key="question.segment.${segment}"/></h3>
                </c:if>
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="riskProfileQuestionList${segment}"/>
                    <tiles:put name="data">
                        <tr>
                            <th class="Width20">
                                <input class="segment${segment}" type="checkbox" name="isSelectAll" onclick="selectAll(this);">
                            </th>
                            <th width="50%">
                                <bean:message bundle="pfpRiskProfileBundle" key="question.label.table.text"/>
                            </th>
                            <th>
                                <bean:message bundle="pfpRiskProfileBundle" key="question.label.table.answers"/>
                            </th>
                            <th width="10%">
                                <bean:message bundle="pfpRiskProfileBundle" key="question.label.table.answerWeights"/>
                            </th>
                        </tr>
                        <c:forEach var="question" items="${questions}">
                            <c:set var="answersCount" value="${phiz:size(question.answers)}"/>
                            <tr>
                                <td rowspan="${answersCount}" valign="top">
                                    <input class="segment${segment}" type="checkbox" name="selectedIds" value="${question.id}">
                                </td>
                                <td rowspan="${answersCount}" valign="top">
                                    <phiz:link action="/pfp/riskProfile/question/edit" operationClass="EditRiskProfileQuestionOperation">
                                        <phiz:param name="id" value="${question.id}"/>
                                        <span class="word-wrap"><c:out value="${question.text}"/></span>
                                    </phiz:link>
                                </td>
                                <td>
                                    <logic:iterate id="answer" name="question" property="answers" indexId="index">
                                                <span class="word-wrap">${answer.text}</span>
                                            </td>
                                            <td>
                                                ${answer.weight}
                                        <c:if test="${index + 1 < answersCount}">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                        </c:if>
                                    </logic:iterate>
                                </td>
                            </tr>
                        </c:forEach>
                    </tiles:put>
                    <tiles:put name="buttons">
                        <tiles:insert definition="clientButton" flush="false" operation="EditRiskProfileQuestionOperation">
                            <tiles:put name="commandTextKey"    value="question.button.edit"/>
                            <tiles:put name="commandHelpKey"    value="question.button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                            <tiles:put name="onclick"           value="goToEdit('${segment}');"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="RemoveRiskProfileQuestionOperation">
                            <tiles:put name="commandTextKey" value="question.button.remove"/>
                            <tiles:put name="commandHelpKey" value="question.button.remove.help"/>
                            <tiles:put name="bundle"         value="pfpRiskProfileBundle"/>
                            <tiles:put name="onclick"        value="doRemove('${segment}');"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty questions}"/>
                    <tiles:put name="emptyMessage">
                        <div class="emptyDataForSegmentMessage">
                            <h3>
                                <bean:message bundle="pfpRiskProfileBundle" key="question.segment.${segment}"/>
                            </h3>
                            <p><bean:message bundle="pfpRiskProfileBundle" key="question.label.tableEmpty"/></p>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </c:forEach>
        </tiles:put>
    </tiles:insert>
</html:form>