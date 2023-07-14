<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/question/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="loansEdit">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description">
                    <bean:message key="edit.question.title" bundle="loansBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.question.id" bundle="loansBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:choose>
                                <c:when test="${form.fields['questionId'] != null}">
                                    <html:text property="field(questionId)" size="2" maxlength="2" disabled="true"/>
                                    <html:hidden property="field(questionId)"/>
                                </c:when>
                                <c:otherwise>
                                    <html:text property="field(questionId)" size="2" maxlength="2"/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.question.text" bundle="loansBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="field(question)"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.question.answer.type" bundle="loansBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(answerType)">
                                <html:option value="YES_NO"><bean:message key="question.select.option.yes_no" bundle="loansBundle"/></html:option>
                                <html:option value="INPUT_FIELD"><bean:message key="question.select.option.input_field" bundle="loansBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="action" value="/loans/question/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>