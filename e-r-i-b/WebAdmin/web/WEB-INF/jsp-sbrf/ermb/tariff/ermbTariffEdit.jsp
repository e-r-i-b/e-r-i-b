<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/ermb/tariff/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="ermbTariffEdit">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Тариф"/>
                <tiles:put name="description" value="Используйте данную форму для редактирования тарифов ЕРМБ."/>
                <tiles:put name="data">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.ermb.name" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(name)" size="50" maxlength="50"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.ermb.connection" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(connectionCost)" size="10" maxlength="10"/>
                        <bean:message key="label.rub" bundle="ermbBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.ermb.license.fee" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <div class="textTitle"><bean:message key="ermb.subscription.fee" bundle="ermbBundle"/></div>
                        <table width="100%" cellspacing="0" cellpadding="0"  class="standartTable">
                            <tbody>
                            <tr>
                                <th>
                                    <bean:message key="ermb.product.class" bundle="ermbBundle"/>
                                </th>
                                <th>
                                    <bean:message key="ermb.payment.amount" bundle="ermbBundle"/>
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ermb.product.class.preferential" bundle="ermbBundle"/>
                                </td>
                                <td>
                                    <html:text property="field(graceClassFee)" size="10" maxlength="10"/>
                                    <bean:message key="label.rub" bundle="ermbBundle"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ermb.product.class.premium" bundle="ermbBundle"/>
                                </td>
                                <td>
                                    <html:text property="field(premiumClassFee)" size="10" maxlength="10"/>
                                    <bean:message key="label.rub" bundle="ermbBundle"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ermb.product.class.social" bundle="ermbBundle"/>
                                </td>
                                <td>
                                    <html:text property="field(socialClassFee)" size="10" maxlength="10"/>
                                    <bean:message key="label.rub" bundle="ermbBundle"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <bean:message key="ermb.product.class.standard" bundle="ermbBundle"/>
                                </td>
                                <td>
                                    <html:text property="field(standardClassFee)" size="10" maxlength="10"/>
                                    <bean:message key="label.rub" bundle="ermbBundle"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.ermb.charge.period" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(chargePeriod)" size="2" maxlength="2"/>
                        <bean:message key="label.month" bundle="ermbBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="ermb.grace.period" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(gracePeriod)" size="2" maxlength="2"/>
                        <bean:message key="label.month" bundle="ermbBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.ermb.grace.period.fee" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(gracePeriodCost)" size="10" maxlength="10"/>
                        <bean:message key="label.rub" bundle="ermbBundle"/>
                    </tiles:put>
                </tiles:insert>


                <div class="clear"></div>

                <div class="textTitle"><bean:message key="label.erbm.tariff.service.list" bundle="ermbBundle"/></div>
                <table width="100%" cellspacing="0" cellpadding="0" class="standartTable">
                    <tbody>
                        <tr>
                            <th>
                                <bean:message key="label.ermb.sercice" bundle="ermbBundle"/>
                            </th>
                            <th>
                                <bean:message key="label.ermb.terms.submitting" bundle="ermbBundle"/>
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.noticeConsIncomCard" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(noticeConsIncomCardOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.noticeConsIncomAccount" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(noticeConsIncomAccountOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.cardInfo" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(cardInfoOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.accountInfo" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(accountInfoOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.cardMiniInfo" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(cardMiniInfoOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.accountMiniInfo" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(accountMiniInfoOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.reIssueCard" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(reIssueCardOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.jurPayment" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(jurPaymentOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="ermb.tariff.operation.transfersToThirdParties" bundle="ermbBundle"/>
                            </td>
                            <td>
                                <tiles:insert definition="ermbTariffOperationVariant" flush="false">
                                    <tiles:put name="operationFieldName" value="field(transfersToThirdPartiesOp)"/>
                                </tiles:insert>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="ermb.description" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:textarea property="field(description)"/>
                    </tiles:put>
                </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle"         value="ermbBundle"/>
                        <tiles:put name="action" value="/ermb/tariff/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle"         value="ermbBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>


</html:form>
