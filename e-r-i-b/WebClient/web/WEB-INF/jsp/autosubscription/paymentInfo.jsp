<%--
  Created by IntelliJ IDEA.
  User: bogdanov
  Date: 13.02.2012
  Time: 9:31:19
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<html:form action="/private/autosubscriptions/payment/info" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="accountInfo">
        <tiles:put name="mainmenu" value="AutoSubscription"/>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мои автоплатежи"/>
                <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Детальная информация по исполненному платежу"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="menu" type="string"/>
        <c:set var="payment" value="${form.payment}"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openPrintCheck(event)
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/private/autosubscriptions/payment/info/print.do')}";
                    var params = "?id=${form.id}&subscriptionId=${form.subscriptionId}";
                    openWindow(event, url + params, "", "resizable=1,menubar=1,toolbar=0,scrollbars=1,width=300,height=700");
                }
            </script>
            <c:if test="${not empty payment}">
            <div id="payment">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Детальная информация по исполненному платежу"/>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/longOfferInfo.png"/>
                            <tiles:put name="description">
                                На данной странице Вы можете посмотреть детальную информацию по исполненному платежу.
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>

                        <c:set var="payment" value="${form.payment}"/>
                        <c:set var="cardLink" value="${form.cardLink}"/>
                        <c:set var="autosubscriptionType" value="autoPayment"/>
                        <c:if test="${phiz:isAutoTransfer(form.subscriptionLink)}">
                            <c:set var="autosubscriptionType" value="autoTransfer"/>
                        </c:if>
                        <%@ include file="/WEB-INF/jsp/common/autosubscription/detailInfo.jsp" %>
                    
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.prev"/>
                            <tiles:put name="commandHelpKey" value="button.prev.help"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="blueGrayLink"/>
                            <tiles:put name="action">/private/autosubscriptions/info.do?id=<bean:write name="form" property="subscriptionId"/></tiles:put>
                        </tiles:insert>
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.printCheck"/>
                                <tiles:put name="commandHelpKey" value="button.printCheck.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                                <tiles:put name="onclick">openPrintCheck(event)</tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>