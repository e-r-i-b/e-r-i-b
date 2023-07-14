<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<style type="text/css">
    .tablePayments {
        font-family: The Times Roman;
        text-align: left;
        font-size: 9px;
        border: 1px solid #000000;
        width: 100%;
        vertical-align: middle;
    }

    .tablePayments td {
        font-family: The Times Roman;
        text-align: center;
        border-right: 1px solid #000000;
        border-bottom: 1px solid #000000;
        vertical-align: middle;
    }

    .label {
        text-align: center;
        font-family:The Times Roman;
        font-size:12px;
    }
</style>

<c:choose>
    <c:when test="${not abstractEmpty}">
        <c:set var="imAccount" value="${imAccountLink.imAccount}"/>
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="id" value="${style}"/>
            <tiles:put name="hideable" value="${empty hideable ? true : hideable}"/>
            <tiles:put name="grid">
                <c:if test="${hideable}">
                    <tiles:put name="id" value="${imAccountLink.id}"/>
                    <tiles:put name="productType" value="imaccount"/>
                    <tiles:put name="show" value="${imAccountLink.showOperations}"/>
                </c:if>
                <sl:collection id="transaction" model="${empty model ? 'no-pagination' : model}" name="imAccountAbstract"
                               property="transactions" collectionSize="${empty recCount ? 3 : recCount}">
                    <c:if test="${not empty transaction.correspondentAccount}">
                        <sl:collectionItem styleClass="align-left">
                            ${transaction.correspondentAccount}
                        </sl:collectionItem>
                    </c:if>
                    <sl:collectionItem>
                        <c:if test="${not empty hideable}">
                            <sl:collectionItemParam id="title" value="Дата"/>
                        </c:if>
                        <c:if test="${not empty transaction.date}">
                            ${phiz:formatDateDependsOnSysDate(transaction.date, false,false)}&nbsp;
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem styleTitleClass="">
                        <c:if test="${not empty hideable}">
                            <sl:collectionItemParam id="title" value="Тип счета"/>
                        </c:if>
                        <c:if test="${not empty imAccount.currency}">
                             ${imAccount.currency.name}&nbsp;(${phiz:normalizeCurrencyCode(imAccount.currency.code)})
                        </c:if>
                    </sl:collectionItem>

                    <%--TODO Убрать, когда появится полная выписка--%>
                    <c:set var="credit" value="${transaction.creditSum}"/>
                    <c:set var="debit"  value="${transaction.debitSum}"/>

                    <c:choose>
                        <c:when test="${empty hideable}">
                            <sl:collectionItem styleTitleClass="align-right">
                                <c:if test="${not empty hideable}">
                                    <sl:collectionItemParam id="title" value=""/>
                                </c:if>
                                <c:choose>
                                    <c:when test="${!empty transaction.creditSumInPhizicalForm && transaction.creditSumInPhizicalForm.decimal > 0.00}">
                                        -${phiz:formatAmount(transaction.creditSumInPhizicalForm)}
                                    </c:when>
                                    <c:when test="${!empty transaction.debitSumInPhizicalForm && transaction.debitSumInPhizicalForm.decimal > 0.00}">
                                        +${phiz:formatAmount(transaction.debitSumInPhizicalForm)}
                                    </c:when>
                                    <%--TODO Отображать пока не появится полная выписка--%>
                                    <c:when test="${!empty credit && credit.decimal > 0.00}">
                                        -${phiz:formatAmount(credit)}
                                    </c:when>
                                    <c:when test="${!empty debit && debit.decimal > 0.00}">
                                        +${phiz:formatAmount(debit)}
                                    </c:when>
                                </c:choose>
                            </sl:collectionItem>
                        </c:when>
                        <c:otherwise>
                            <sl:collectionItem title="Зачисление" styleTitleClass="align-right" styleClass="align-right">
                                <c:if test="${!empty debit && debit.decimal > 0.00}">
                                    ${phiz:formatAmount(debit)}
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem title="Списание" styleTitleClass="align-right" styleClass="align-right">
                                <c:if test="${!empty credit && credit.decimal > 0.00}">
                                    ${phiz:formatAmount(credit)}
                                </c:if>
                            </sl:collectionItem>
                            
                            <c:set var="operationRurSumm" value="${transaction.operationRurSumm}"/>
                            <sl:collectionItem title="Сумма операции" styleTitleClass="align-right" styleClass="align-right">
                                <c:choose>
                                    <c:when test="${operationRurSumm.decimal != '0.00'}">
                                        ${phiz:formatAmount(operationRurSumm)}
                                        <c:if test="${transaction.operationType == 'CS_METAL'}">&nbsp;(слиток)</c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div align="center" >
                                            &mdash;
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </sl:collectionItem>
                        </c:otherwise>
                    </c:choose>
                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        <div class="emptyText">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="greenBold"/>
                <tiles:put name="data">
                    <c:choose>
                        <c:when test="${!empty form.abstractMsgError}">
                            <c:out value="${form.abstractMsgError}"/> 
                        </c:when>
                        <c:otherwise>
                            <bean:message key="message.empty" bundle="imaBundle"/>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>
        </div>
    </c:otherwise>
</c:choose>
