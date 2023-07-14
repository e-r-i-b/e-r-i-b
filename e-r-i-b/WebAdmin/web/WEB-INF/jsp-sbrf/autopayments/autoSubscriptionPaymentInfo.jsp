<%--
  Created by IntelliJ IDEA.
  User: bogdanov
  Date: 13.02.2012
  Time: 10:08:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/autopayment/payment/info" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="menu" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="AutoSubscriptions"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openPrintCheck(event)
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/autopayment/payment/info/print.do')}";
                    var params = "?id=${form.id}&subscriptionId=${form.subscriptionId}" ;
                    openWindow(event, url + params, "", "resizable=1,menubar=1,toolbar=0,scrollbars=1,width=300,height=700");
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="reports"/>
                <tiles:put name="name" value="Детальная информация по исполненному платежу"/>
                <tiles:put name="description" value="На данной странице Вы можете посмотреть детальную информацию по исполненному платежу."/>
                <tiles:put name="data">

                    <c:set var="payment" value="${form.payment}"/>
                    <c:set var="cardLink" value="${form.cardLink}"/>
                    <c:set var="autosubscriptionType" value="autoPayment"/>

                    <%@ include file="/WEB-INF/jsp/common/autosubscription/detailInfo.jsp" %>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.back"/>
                        <tiles:put name="commandHelpKey" value="button.back.help"/>
                        <tiles:put name="bundle" value="dictionariesBundle"/>
                        <tiles:put name="viewType" value="buttonGreen"/>
                        <tiles:put name="action">/autopayment/info.do?id=<bean:write name="form" property="subscriptionId"/></tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print"/>
                        <tiles:put name="commandHelpKey" value="button.print"/>
                        <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                        <tiles:put name="viewType" value="buttonGreen"/>
                        <tiles:put name="onclick">openPrintCheck(event)</tiles:put>
                        <tiles:put name="isDefault" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>