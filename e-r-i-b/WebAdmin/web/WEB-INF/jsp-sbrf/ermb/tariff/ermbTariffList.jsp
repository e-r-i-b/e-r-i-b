<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/ermb/tariff/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="ermbTariffList">
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add.tariff"/>
                <tiles:put name="commandHelpKey" value="button.add.tariff"/>
                <tiles:put name="bundle"         value="ermbBundle"/>
                <tiles:put name="action" value="/ermb/tariff/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="ermb.tariffs" bundle="ermbBundle"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function selectTest()
                {
                    var selectTariff = $('input[name="selectedIds"]:checked');
                    if (selectTariff.length > 1)
                    {
                        alert('Выберите только один тариф для редактирования');
                        return;
                    }
                    if (selectTariff.length == 0)
                    {
                        alert('Выберите хотя бы один тариф для редактирования');
                        return;
                    }
                    return selectTariff[0].value;
                }

                function  validateSelection()
                {
                    return checkSelection("selectedIds", 'Выберите тариф') ;
                }

                function redirect()
                {
                    var selectId = selectTest();
                    if (selectId != null)
                    {
                        <c:set var="u" value="/ermb/tariff/edit.do"/>
                        var url = "${phiz:calculateActionURL(pageContext,u)}";
                        window.location = url + "?id=" + selectId;
                    }
                }
            </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove"/>
                        <tiles:put name="bundle"         value="ermbBundle"/>
                        <tiles:put name="confirmText"    value="Удалить выбранные тарифы?"/>
                        <tiles:put name="validationFunction" value="validateSelection()"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle"         value="ermbBundle"/>
                        <tiles:put name="onclick"        value="redirect()"/>
                    </tiles:insert>
                </tiles:put>
                <!-- тарифы -->
                <tiles:put name="grid">
                    <sl:collection id="tariff" model="list" property="data" bundle="ermbBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="ermb.tariff.plane" property="name"/>
                        <sl:collectionItem title="label.ermb.connection">
                            <c:choose>
                                <c:when test="${empty tariff.connectionCost}">
                                    <bean:message key="ermb.tariff.message.change" bundle="ermbBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${phiz:formatAmount(tariff.connectionCost)}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.grace.period">
                            <c:out value="${tariff.gracePeriod}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.product.class">
                            <bean:message key="ermb.product.class.preferential" bundle="ermbBundle"/><br/>
                            <bean:message key="ermb.product.class.premium" bundle="ermbBundle"/><br>
                            <bean:message key="ermb.product.class.social" bundle="ermbBundle"/><br/>
                            <bean:message key="ermb.product.class.standard" bundle="ermbBundle"/><br/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.subscription.fee">
                            <c:out value="${phiz:formatAmountWithMonth(tariff.graceClass)}"/><br/>
                            <c:out value="${phiz:formatAmountWithMonth(tariff.premiumClass)}"/><br/>
                            <c:out value="${phiz:formatAmountWithMonth(tariff.socialClass)}"/><br/>
                            <c:out value="${phiz:formatAmountWithMonth(tariff.standardClass)}"/><br/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.provided.service">
                            <c:if test="${tariff.noticeConsIncomCardOperation == 'PROVIDED' or tariff.noticeConsIncomCardOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.noticeConsIncomCard" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.noticeConsIncomAccountOperation == 'PROVIDED' or tariff.noticeConsIncomAccountOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.noticeConsIncomAccount" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.cardInfoOperation == 'PROVIDED' or tariff.cardInfoOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.cardInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.accountInfoOperation == 'PROVIDED' or tariff.accountInfoOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.accountInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.cardMiniInfoOperation == 'PROVIDED' or tariff.cardMiniInfoOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.cardMiniInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.accountMiniInfoOperation == 'PROVIDED' or tariff.accountMiniInfoOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.accountMiniInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.reIssueCardOperation == 'PROVIDED' or tariff.reIssueCardOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.reIssueCard" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.jurPaymentOperation == 'PROVIDED' or tariff.jurPaymentOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.jurPayment" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.transfersToThirdPartiesOperation == 'PROVIDED' or tariff.transfersToThirdPartiesOperation == 'NOT_PROVIDED_WHEN_NO_PAY'}">
                                <bean:message key="ermb.tariff.operation.transfersToThirdParties" bundle="ermbBundle"/><br/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.not.provided.service">
                            <c:if test="${tariff.noticeConsIncomCardOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.noticeConsIncomCard" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.noticeConsIncomAccountOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.noticeConsIncomAccount" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.cardInfoOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.cardInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.accountInfoOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.accountInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.cardMiniInfoOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.cardMiniInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.accountMiniInfoOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.accountMiniInfo" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.reIssueCardOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.reIssueCard" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.jurPaymentOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.jurPayment" bundle="ermbBundle"/><br/>
                            </c:if>
                            <c:if test="${tariff.transfersToThirdPartiesOperation == 'NOT_PROVIDED'}">
                                <bean:message key="ermb.tariff.operation.transfersToThirdParties" bundle="ermbBundle"/><br/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.description" property="description"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного тарифа ЕРМБ!"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
