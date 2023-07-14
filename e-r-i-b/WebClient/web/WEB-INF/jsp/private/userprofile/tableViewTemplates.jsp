<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<html:form action="/private/async/userprofile/accountSecurity/templeteViewShow" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <script type="text/javascript">
        $(document).ready(function()
        {
            $('input[name=selectedIds]').click(function(){
                onTemplateClick(this);
            });
            var nextButtonTemplate = $('#nextButtonTemplate')[0];
            if ($('#nextButtonTemplate') != null)
            {
                nextButtonTemplate.onclick = null;
                $(nextButtonTemplate).find('.buttonGreen').addClass("disabled");
            }
        });
    </script>
    <tiles:insert definition="simpleTableTemplate" flush="false" >
        <tiles:put name="id" value="templateList"/>
        <tiles:put name="grid">
            <sl:collection id="template" model="simple-pagination" property="templates" assignedPaginationSizes="20;50" assignedPaginationSize="20"
                           onClick="ajaxTableList('%1$s', %2$d, '%3$s', %4$d); return false;" >
                <c:set var="templateId" value="${template.id}"/>
                <c:set var="isDraftTemplate" value="${template.state.code == 'DRAFTTEMPLATE' || template.state.code == 'SAVED_TEMPLATE'}"/>
                <c:set var="checkBoxStyleId">
                    <c:choose>
                        <c:when test="${isDraftTemplate}">Draft${templateId}</c:when>
                        <c:otherwise>${templateId}</c:otherwise>
                    </c:choose>
                </c:set>
                <c:set var="clazz">
                    <c:if test="${isDraftTemplate}">draftTemplate</c:if>
                </c:set>
                <sl:collectionItem title="отображать" styleClass="align-center">
                    <input type="checkbox" value="${templateId}" name="selectedIds" id="selectedIds">
                </sl:collectionItem>
                <sl:collectionItem title="Название шаблона, №">
                    <div>
                        <span class="${clazz} word-wrap">
                            <c:out value="${template.templateInfo.name}"/>
                        </span>
                    </div>
                    <div class="draftTemplate">
                        <c:out value="${templateId}"/>
                    </div>
                </sl:collectionItem>
                <sl:collectionItem title="Создан" styleTitleClass="align-right" styleClass="align-right">
                    <span class="${clazz}">
                        <c:out value="${phiz:formatDateDependsOnSysDate(template.clientCreationDate, true, false)}"/>
                    </span>
                </sl:collectionItem>
                <sl:collectionItem title="Статус">
                    <span class="${clazz}">
                        <c:choose>
                            <c:when test="${(template.formType == 'INTERNAL_TRANSFER' || template.formType == 'CONVERT_CURRENCY_TRANSFER' ||
                                template.formType == 'IMA_PAYMENT' || template.formType == 'LOAN_PAYMENT') &&
                                (template.state.code == 'WAIT_CONFIRM_TEMPLATE' || template.state.code == 'TEMPLATE')}">
                                    <bean:message bundle="userprofileBundle" key="templates.state.description.ACTIVE"/>
                            </c:when>
                            <c:otherwise><bean:message bundle="userprofileBundle" key="templates.state.description.${template.state.code}"/></c:otherwise>
                        </c:choose>
                    </span>
                </sl:collectionItem>
                <sl:collectionItem title="Сумма" styleTitleClass="align-right" styleClass="align-right">
                    <span class="${clazz}">
                        <c:choose>
                            <c:when test="${not empty template.chargeOffAmount}">
                                <c:out value="${phiz:formatAmount(template.chargeOffAmount)}"/>
                            </c:when>
                            <c:when test="${not empty template.destinationAmount}">
                                <c:out value="${phiz:formatAmount(template.destinationAmount)}"/>
                            </c:when>
                        </c:choose>
                    </span>
                </sl:collectionItem>
                <sl:collectionItem title="">
                    <html:hidden property="field(checked)" styleId="internetCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInERIB}"/>
                    <html:hidden property="field(checked)" styleId="atmCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInATM}"/>
                    <html:hidden property="field(checked)" styleId="smsCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInERMB}"/>
                    <html:hidden property="field(checked)" styleId="mobileCheckBox${checkBoxStyleId}" name="form" value="${template.templateInfo.useInMAPI}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>