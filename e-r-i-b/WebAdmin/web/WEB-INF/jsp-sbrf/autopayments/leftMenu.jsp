<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="person" value="${form.activePerson}"/>
<c:if test="${person == null}">
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
</c:if>

<c:set var="autoSubscriptionsAllow" value="${phiz:impliesServiceRigid('AutoSubscriptionManagment')}"/>
<c:set var="autoTransfersAllow"     value="${phiz:impliesServiceRigid('AutoTransfersManagement')}"/>
<c:set var="moneyBoxesAllow"     value="${phiz:impliesServiceRigid('EmployeeMoneyBoxManagement')}"/>

<c:if test="${autoSubscriptionsAllow || autoTransfersAllow}">
    <tiles:insert definition="leftMenuInset" flush="false">
        <tiles:put name="enabled" value="${submenu != 'SearchClient'}"/>
        <tiles:put name="action" value="/person/search"/>
        <tiles:put name="text">
            <bean:message key="left.menu.search" bundle="autopaymentsBundle"/>
        </tiles:put>
        <tiles:put name="title">
            <bean:message key="left.menu.search.hint" bundle="autopaymentsBundle"/>
        </tiles:put>
    </tiles:insert>
</c:if>

<c:if test="${person != null}">
    <c:if test="${autoSubscriptionsAllow || autoTransfersAllow}">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled" value="${submenu != 'GeneralInfo'}"/>
            <tiles:put name="action" value="/autopayment/person/showInfo"/>
            <tiles:put name="text">
                <bean:message key="left.menu.generalInfo" bundle="autopaymentsBundle"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message key="left.menu.generalInfo.hint" bundle="autopaymentsBundle"/>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="leftMenuInsetGroup" flush="false">
            <tiles:put name="name" value="autosubscriptions"/>
            <tiles:put name="text">
                <bean:message key="left.menu.autopayments" bundle="autopaymentsBundle"/>
            </tiles:put>
            <tiles:put name="enabled" value="${submenu != 'AutoSubscriptions' && submenu != 'AutoTransfers'}"/>
            <tiles:put name="title">
                <bean:message key="left.menu.autopayments.hint" bundle="autopaymentsBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <c:if test="${autoSubscriptionsAllow}">
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled" value="${submenu != 'AutoSubscriptions'}"/>
                        <tiles:put name="action" value="/autopayment/list"/>
                        <tiles:put name="text">
                            <bean:message key="left.menu.autopayments.payments" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="title">
                            <bean:message key="left.menu.autopayments.payments.hint" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="forceOnclick" value="true"/>
                        <tiles:put name="parentName" value="autosubscriptions"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${autoTransfersAllow}">
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled" value="${submenu != 'AutoTransfers'}"/>
                        <tiles:put name="action" value="/autotransfer/list"/>
                        <tiles:put name="forceOnclick" value="true"/>
                        <tiles:put name="text">
                            <bean:message key="left.menu.autopayments.transfers" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="title">
                            <bean:message key="left.menu.autopayments.transfers.hint" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="parentName" value="autosubscriptions"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="leftMenuInsetGroup" flush="false">
            <tiles:put name="name" value="claims"/>
            <tiles:put name="text">
                <bean:message key="left.menu.claims" bundle="autopaymentsBundle"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message key="left.menu.claims.hint" bundle="autopaymentsBundle"/>
            </tiles:put>
            <tiles:put name="enabled" value="${submenu != 'AutoPaymentClaims' && submenu != 'AutoTransfersClaims'}"/>
            <tiles:put name="data">
                <c:if test="${autoSubscriptionsAllow}">
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled" value="${submenu != 'AutoPaymentClaims'}"/>
                        <tiles:put name="action"  value="/autopayment/claims"/>
                        <tiles:put name="text">
                            <bean:message key="left.menu.claims.autopayments" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="title">
                            <bean:message key="left.menu.claims.autopayments.hint" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="parentName" value="claims"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${autoTransfersAllow}">
                    <tiles:insert definition="leftMenuInset" flush="false">
                        <tiles:put name="enabled" value="${submenu != 'AutoTransfersClaims'}"/>
                        <tiles:put name="action"  value="/autotransfer/claims"/>
                        <tiles:put name="text">
                            <bean:message key="left.menu.claims.autotransfers" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="title">
                            <bean:message key="left.menu.claims.autotransfers.hint" bundle="autopaymentsBundle"/>
                        </tiles:put>
                        <tiles:put name="parentName" value="claims"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>
        </tiles:insert>
    </c:if>

    <c:if test="${moneyBoxesAllow}">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled" value="${submenu != 'MoneyBox'}"/>
            <tiles:put name="action" value="/moneyBox/list"/>
            <tiles:put name="text">
                <bean:message key="left.menu.title" bundle="moneyBoxBundle"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message key="left.menu.title.hint" bundle="moneyBoxBundle"/>
            </tiles:put>
        </tiles:insert>
    </c:if>
</c:if>