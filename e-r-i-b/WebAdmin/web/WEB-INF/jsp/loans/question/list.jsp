<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/loans/question/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="loansList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="list.question.title" bundle="loansBundle"/>
            <div class="use-questionnaire">
                <div class="use-questionnaire-box">
                    <html:checkbox property="useQuestionnaire"/>
                    <bean:message key="use.questionnaire.label" bundle="loansBundle"/>
                </div>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save.use.questionnaire"/>
                    <tiles:put name="commandHelpKey" value="button.save.use.questionnaire.help"/>
                    <tiles:put name="bundle" value="loansBundle"/>
                    <tiles:put name="action" value="/loans/question/list.do"/>
                </tiles:insert>
            </div>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="questionList"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditLoanClaimQuestionOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="loansBundle"/>
                <tiles:put name="action" value="/loans/question/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.filter.question.id"/>
                <tiles:put name="bundle" value="loansBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="questionId"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function checkAction()
                {
                    return selectRecord() != null;
                }
                function selectRecord()
                {
                    var selected = $('input[name="selectedIds"]:checked');
                    if (selected.length > 1)
                    {
                        alert('Выберите одну запись');
                        return;
                    }
                    if (selected.length == 0)
                    {
                        alert('Выберите одну запись');
                        return;
                    }
                    return selected[0].value;
                }
                function edit()
                {
                    var selectId = selectRecord();
                    if (selectId != null)
                    {
                        <c:set var="u" value="/loans/question/edit.do"/>
                        var url = "${phiz:calculateActionURL(pageContext, u)}";
                        window.location = url + "?id=" + selectId;
                    }
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="loanClaimQuestion"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditLoanClaimQuestionOperation">
                        <tiles:put name="commandKey" value="button.publish"/>
                        <tiles:put name="commandHelpKey" value="button.publish"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="action" value="/loans/question/list.do"/>
                        <tiles:put name="validationFunction" value="checkAction();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditLoanClaimQuestionOperation">
                        <tiles:put name="commandKey" value="button.unpublish"/>
                        <tiles:put name="commandHelpKey" value="button.unpublish"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="action" value="/loans/question/list.do"/>
                        <tiles:put name="validationFunction" value="checkAction();"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="EditLoanClaimQuestionOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="onclick" value="edit()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveLoanClaimQuestionOperation">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.product.help"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите вопросы для удаления');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText" value="Удалить выбранные вопросы?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="ID вопроса" property="id"/>
                        <sl:collectionItem title="Текст вопроса" property="question"/>
                        <sl:collectionItem title="Статус" property="status"/>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>