<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/autopayment/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="paymentUrl" value="${phiz:calculateActionURL(pageContext, '/autopayment/info?id=')}"/>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu" type="string" value="AutoSubscriptions"/>
        <tiles:put name="pageTitle" type="string" value="Список автоплатежей"/>
        <tiles:put name="menu" type="string">
            <c:set var="isCreateAutoPayAvailable" value="${phiz:impliesServiceRigid('CreateEmployeeAutoPayment')}"/>
            <c:if test="${isCreateAutoPayAvailable}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle"         value="autopaymentsBundle"/>
                    <tiles:put name="action" value="/autopayment/providers"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <c:if test="${!isCreateAutoPayAvailable && phiz:impliesService('EmployeeFreeDetailAutoSubManagement')}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.addByFreeDetail"/>
                    <tiles:put name="commandHelpKey" value="button.addByFreeDetail.help"/>
                    <tiles:put name="bundle"         value="autopaymentsBundle"/>
                    <tiles:put name="action" value="/autopayment/freeDetatilAutoSub"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <c:if test="${not empty form.data}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print.help"/>
                    <tiles:put name="bundle"         value="autopaymentsBundle"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                    <tiles:put name="onclick">
                        printList(event);
                    </tiles:put>
                </tiles:insert>
            </c:if>

           <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>

        <%@ include file="/WEB-INF/jsp-sbrf/autopayments/list-autosubscriptions.jsp" %>
    </tiles:insert>
</html:form>
