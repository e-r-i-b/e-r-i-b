<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="providersPrint">
    <tiles:put name="data" type="string">

        <body onLoad="showMessage();" Language="JavaScript">
            <html:form action="/dictionaries/provider/print">
                <c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
                <c:set var="provider"   value="${form.provider}"/>
                <c:set var="department" value="${form.department}"/>
                <c:set var="regions" value="${provider.regions}"/>
                <c:set var="services" value="${form.services}"/>
                <c:set var="number" value="1"/>
                <c:set var="currentDate" value="${phiz:currentDate()}"/>
                <c:set var="isBillingProvider" value="${provider.type == 'BILLING'}"/>

                <style type="text/css">
                    .tdRLB {border-right:1px solid black; border-left:1px solid black; border-bottom:1px solid black;}
                    .tdLB { border-bottom:1px solid black; border-left:1px solid black;}
                    .tdAll {border:1px solid black;}
                </style>

                <table cellspacing="0" cellpadding="0" class="textDoc" style="margin:20px;font-size:9pt;border-collapse: collapse;">
                    <tr>
                        <td align="center" colspan="3" class="tdAll">
                            &nbsp;<B><bean:message key="print.title" bundle="providerBundle"/></B>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3" class="tdRLB">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3" class="tdRLB">
                            &nbsp;<B><bean:message key="label.bank.info" bundle="providerBundle"/></B>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="filter.name" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2" >
                            &nbsp;<B><bean:write name="provider" property="name"/></B>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.inn" bundle="providerBundle"/>
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="INN"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.account" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="account"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.bic" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="BIC"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.bankName" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="bankName"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.corAccount" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="corrAccount"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3" class="tdRLB">
                            &nbsp;<B><bean:message key="label.overview" bundle="providerBundle"/></B>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.number" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="code"/>&nbsp;
                        </td>
                    </tr>
                    <c:if test="${isBillingProvider}">
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.billing" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                &nbsp;<bean:write name="provider" property="billing.name"/>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.service" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                <c:set var="showComma" value="false"/>
                                &nbsp;<logic:iterate id="paymentService" collection="${services}"><%
                                    %><c:if test="${showComma}">, </c:if><c:set var="showComma" value="true"/><%
                                    %><bean:write name="paymentService" property="name"/><%
                                %></logic:iterate>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.billing.service" bundle="providerBundle"/>
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                &nbsp;<bean:write name="provider" property="nameService"/>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.service.code" bundle="providerBundle"/>
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                &nbsp;<bean:write name="provider" property="codeService"/>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.debt" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                <c:choose>
                                    <c:when test="${provider.deptAvailable}">
                                        &nbsp;<bean:message key="label.yes" bundle="providerBundle"/>&nbsp;
                                    </c:when>
                                    <c:otherwise>
                                        &nbsp;<bean:message key="label.no" bundle="providerBundle"/>&nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.eng4000nsi" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="NSICode"/>&nbsp;
                        </td>
                    </tr>
                    <c:if test="${isBillingProvider}">
                        <tr>
                            <td align="center" colspan="3" class="tdRLB">
                                &nbsp;<B><bean:message key="label.comission" bundle="providerBundle"/></B>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.minComission" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:write name="provider" property="minComissionAmount"/>&nbsp;
                            </td>
                            <td align="center" class="tdRLB" width="10%">
                                &nbsp;RUB&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.maxComission" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:write name="provider" property="maxComissionAmount"/>&nbsp;
                            </td>
                            <td align="center" class="tdRLB" width="10%">
                                &nbsp;RUB&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.rate" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:write name="provider" property="comissionRate"/>&nbsp;
                            </td>
                            <td align="center" class="tdRLB" width="10%">
                                &nbsp;%&nbsp;
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty regions}">
                        <tr>
                            <td align="center" colspan="3" class="tdRLB">
                                &nbsp;<B><bean:message key="label.regions" bundle="providerBundle"/></B>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="tdLB">
                                &nbsp;<B><bean:message key="label.order" bundle="providerBundle"/></B>&nbsp;
                            </td>
                            <td align="center" class="tdRLB" colspan="2">
                                &nbsp;<B><bean:message key="label.region.name" bundle="providerBundle"/></B>&nbsp;
                            </td>
                        </tr>

                        <logic:iterate id="region" name="PrintServiceProvidersForm" property="provider.regions">
                            <tr>
                                <td align="center" class="tdLB">
                                    &nbsp;<c:out value="${number}"/><c:set var="number" value="${number + 1}"/>&nbsp;
                                </td>
                                <td align="left" class="tdRLB" colspan="2">
                                    &nbsp;<bean:write name="region" property="name"/>&nbsp;
                                </td>
                            </tr>
                        </logic:iterate>
                    </c:if>
                    <tr>
                        <td align="center" colspan="3" class="tdRLB">
                            &nbsp;<B><bean:message key="label.calculations" bundle="providerBundle"/></B>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.unit" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="department" property="name"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="tdLB">
                            &nbsp;<bean:message key="label.trAccount" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<bean:write name="provider" property="transitAccount"/>
                        </td>
                    </tr>
                    <c:if test="${isBillingProvider}">
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.separator" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                &nbsp;<bean:write name="provider" property="attrDelimiter"/>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" class="tdLB">
                                &nbsp;<bean:message key="label.value.separator" bundle="providerBundle"/>&nbsp;
                            </td>
                            <td align="left" class="tdRLB" colspan="2">
                                &nbsp;<bean:write name="provider" property="attrValuesDelimiter"/>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="center" colspan="3" class="tdRLB">
                                &nbsp;<B><bean:message key="label.additional.fields" bundle="providerBundle"/></B>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td align="left" colspan="3" class="tdRLB">
                                &nbsp;
                                <c:if test="${not empty provider.fieldDescriptions}">
                                    <logic:iterate id="field" name="PrintServiceProvidersForm" property="provider.fieldDescriptions">
                                        &nbsp;<bean:write name="field" property="name"/>&nbsp;
                                    </logic:iterate>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td align="center" colspan="3" class="tdRLB">
                            &nbsp;<B><bean:message key="label.description" bundle="providerBundle"/></B>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" colspan="3" class="tdRLB">
                            &nbsp;<bean:write name="provider" property="description"/>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3" class="tdRLB">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="tdLB">
                            &nbsp;<bean:message key="label.date" bundle="providerBundle"/>&nbsp;
                        </td>
                        <td align="left" class="tdRLB" colspan="2">
                            &nbsp;<c:out value="${phiz:formatDateWithStringMonth(currentDate)}"/>&nbsp;
                        </td>
                    </tr>
                </table>
            </html:form>
        </body>
    </tiles:put>
</tiles:insert>
