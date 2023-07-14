<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/riskProfile/question/edit" onsubmit="return setEmptyAction();">
	<tiles:insert definition="editPFPRiskProfile">
        <tiles:put name="submenu" type="string" value="riskProfileQuestionEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpDictionary"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.segment"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(segment)" styleClass="required">
                                <c:forEach var="segment" items="${form.fields['segmentList']}">
                                    <html:option value="${segment}"><bean:message bundle="pfpRiskProfileBundle" key="question.segment.${segment}"/></html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.text"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(text)" size="50" maxlength="250"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="defaultAnswerCount" value="${4}"/>
                    <script type="text/javascript">
                        function removeAnswer(id)
                        {
                            $('input[name=fields(answerTextFor'+ id + ')]').val('');
                            $('input[name=fields(answerWeightFor'+ id + ')]').val('');
                            if ($('tr[id^=answerRow]').size() > ${defaultAnswerCount})
                                $('#answerRow'+ id).remove();
                        }
                    </script>
                    <c:set var="drawingAnswerCount" value="0"/>
                    <c:set var="answerCount" value="${form.fields['answerCount']}"/>
                    <c:if test="${answerCount < defaultAnswerCount}">
                        <c:set var="answerCount" value="${defaultAnswerCount}"/>
                    </c:if>
                    <c:forEach var="i" begin="0" end="${answerCount-1}">
                        <c:set var="answerTextFieldName" value="answerTextFor${i}"/>
                        <c:set var="answerWeightFieldName" value="answerWeightFor${i}"/>
                        <c:if test="${not empty form.fields[answerTextFieldName] or not empty form.fields[answerWeightFieldName]}">
                            <div id="answerRow${i}">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.answerText"/>&nbsp;
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <html:text property="fields(answerTextFor${i})" size="50" maxlength="250"/>

                                        <b class="float"><bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.answerWeight"/></b>&nbsp;
                                        <html:text property="fields(answerWeightFor${i})" size="3" maxlength="2" styleClass="float"/>&nbsp;
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="question.label.edit.button.remove.field"/>
                                            <tiles:put name="commandHelpKey" value="question.label.edit.button.remove.field"/>
                                            <tiles:put name="bundle" value="pfpRiskProfileBundle"/>
                                            <tiles:put name="onclick" value="removeAnswer(${i});"/>
                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                        </tiles:insert>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                            <c:set var="drawingAnswerCount" value="${drawingAnswerCount + 1}"/>
                        </c:if>
                    </c:forEach>
                    <c:forEach var="i" begin="${answerCount}" end="${answerCount + defaultAnswerCount - drawingAnswerCount - 1}">
                        <div id="answerRow${i}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.answerText"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text property="fields(answerTextFor${i})" size="50" maxlength="250" styleClass="float"/>

                                    <b class="float centerLine"><bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.answerWeight"/></b>&nbsp;
                                    <html:text property="fields(answerWeightFor${i})" size="3" maxlength="2" styleClass="float"/>&nbsp;
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="question.label.edit.button.remove.field"/>
                                        <tiles:put name="commandHelpKey" value="question.label.edit.button.remove.field"/>
                                        <tiles:put name="bundle" value="pfpRiskProfileBundle"/>
                                        <tiles:put name="onclick" value="removeAnswer(${i});"/>
                                        <tiles:put name="viewType" value="buttonGrayNew"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                        <c:set var="answerCount" value="${i + 1}"/>
                    </c:forEach>
                    <div id="addAnswerBuutonRow">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <script type="text/javascript">
                                    var answerRowCount = ${answerCount};

                                    function checkRows(fildName)
                                    {
                                        for (var i = 0; i < answerRowCount; i++)
                                        {
                                            if (($('[name^="fields(' + fildName + i + ')"]').val() == ''))
                                                return false;
                                        }
                                        return true;
                                    }
                                    function addAnswerRow()
                                    {
                                        if (!checkRows("answerTextFor"))
                                        {
                                            alert('<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.addAnswerButton.errorText"/>');
                                            return;
                                        }
                                        if (!checkRows("answerWeightFor"))
                                        {
                                            alert('<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.addAnswerButton.errorWeight"/>');
                                            return;
                                        }
                                        var answerRowId = 'answerRow' + answerRowCount;
                                        var line = '<div id="' + answerRowId + '">'+
                                                '<div class="form-row"><div class="paymentLabel">' +
                                                '<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.answerText"/>&nbsp;' +
                                                '</div>' +
                                                '<div class="paymentValue">' +
                                                '<html:text property="fields(answerTextFor' + answerRowCount + ')" size="50" maxlength="250" styleClass="float"/>&nbsp;' +
                                                '<b class="float centerLine"><bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.field.answerWeight"/></b>&nbsp; '+
                                                '<html:text property="fields(answerWeightFor' + answerRowCount + ')" size="3" maxlength="2" styleClass="float"/>&nbsp; ' +
                                                '<div class="clientButton"><div class="buttonGrayNew" onclick="removeAnswer(' + answerRowCount + ');"><div class="left-corner"></div>' +
                                                '<div class="text"><span><bean:message bundle='pfpRiskProfileBundle' key='question.label.edit.button.remove.field'/></span></div>' +
                                                '<div class="right-corner"></div></div><div class="clear"></div></div>' +
                                                '</div></div>' +
                                                '<div class="clear"></div></div>';

                                        $("#addAnswerBuutonRow").before(line);
                                        answerRowCount++;
                                        $("#answerCountField").val(answerRowCount);
                                    }

                                    function saveValidate()
                                    {
                                        if ($('[name="fields(text)"]').val() == '')
                                        {
                                            alert('<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.save.needQuestion"/>');
                                            return false;
                                        }

                                        var rowCount = 0;
                                        for (var i = 0; i < answerRowCount; i++)
                                        {
                                            var textNotEnpty = $('[name^="fields(answerTextFor' + i + ')"]').val() != '';
                                            var weightNotEnpty = $('[name^="fields(answerWeightFor' + i + ')"]').val() != '';
                                            if (textNotEnpty && weightNotEnpty)
                                                rowCount++;

                                            if (textNotEnpty && !weightNotEnpty)
                                            {
                                                alert('<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.addAnswerButton.errorWeight"/>');
                                                return false;
                                            }

                                            if (weightNotEnpty && !textNotEnpty)
                                            {
                                                alert('<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.addAnswerButton.errorText"/>');
                                                return false;
                                            }

                                        }
                                        if (rowCount < 2)
                                        {
                                            alert('<bean:message bundle="pfpRiskProfileBundle" key="question.label.edit.save.needToAnswers"/>');
                                            return false;
                                        }
                                        return true;
                                    }
                                </script>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="question.label.edit.addAnswerButton.name"/>
                                    <tiles:put name="commandHelpKey" value="question.label.edit.addAnswerButton.name"/>
                                    <tiles:put name="bundle" value="pfpRiskProfileBundle"/>
                                    <tiles:put name="onclick" value="javascript:addAnswerRow();"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                                <html:hidden property="fields(answerCount)" styleId="answerCountField" value="${answerCount}"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListRiskProfileQuestionOperation">
                        <tiles:put name="commandTextKey"    value="question.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="question.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                        <tiles:put name="action"            value="/pfp/riskProfile/question/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="question.button.save"/>
                        <tiles:put name="commandHelpKey"     value="question.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpRiskProfileBundle"/>
                        <tiles:put name="validationFunction" value="saveValidate();"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>