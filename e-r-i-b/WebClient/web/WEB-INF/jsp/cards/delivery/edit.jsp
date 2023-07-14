<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"         prefix="fn" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/cards/editEmailDelivery" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <h1><bean:message bundle="cardInfoBundle" key="email.report.delivery.win.title"/></h1>

    <div class="description"><bean:message bundle="cardInfoBundle" key="email.report.delivery.win.description"/></div>

    <c:set var="errors" value=""/>
    <script type="text/javascript">
        if (window.isClientApp != undefined)
        {
            function addErrorField(field, message)
            {
                var errorParentContainer = $('[name=' + field + ']').parents('.paymentValue');
                errorParentContainer.addClass('showError');
                var errorContainer = errorParentContainer.find('.errorDiv');
                errorContainer.html(message);
                errorContainer.show();
            }

            doOnLoad(function(){
                <phiz:messages  id="error" bundle="cardInfoBundle" field="field" title="title" message="error">
                    <c:choose>
                        <c:when test="${field != null}">
                            <c:set var="errorValue"><c:out value="${error}"/></c:set>
                            addErrorField('<bean:write name="field" filter="false"/>', "${phiz:replaceNewLine(errorValue,'<br>')}");
                        </c:when>
                        <c:otherwise>
                            <c:set var="errors">${errors}<div class="itemDiv">${phiz:processBBCodeAndEscapeHtml(error, false)}</div></c:set>
                        </c:otherwise>
                    </c:choose>
                </phiz:messages>
            });
        }
    </script>

    <c:set var="errorsLength" value="${fn:length(errors)}"/>
    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errors"/>
        <tiles:put name="isDisplayed" value="${errorsLength gt 0 ? true : false}"/>
        <tiles:put name="data">
            <bean:write name="errors" filter="false"/>
        </tiles:put>
    </tiles:insert>

    <input type="hidden" name="needSave" value="true"/>

    ${form.html}

    <div class="buttonsArea">
        <span class="clientButton chooseConfirmStrategy">
            <c:set var="document" value="${form.document}"/>
            <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
            <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
            <tiles:insert definition="confirmButtons" flush="false">
                <tiles:put name="ajaxUrl" value="/private/async/cards/editEmailDelivery"/>
                <tiles:put name="winConfirmName" value="reportDelivery"/>
                <tiles:put name="confirmRequest"  beanName="confirmRequest"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                <tiles:put name="formName" value="CardReportDeliveryClaim"/>
                <tiles:put name="mode" value="${phiz:getUserVisitingMode()}"/>
                <tiles:put name="stateObject" value="document"/>
                <tiles:put name="messageBundle" value="cardInfoBundle"/>
                <tiles:put name="showButtonHint" value="true"/>
                <tiles:put name="needWindow" value="false"/>
            </tiles:insert>
        </span>
    </div>
</html:form>