<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanclaim/credit/product/subcode/view" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="tbNumbers" value="${form.tbNumbers}"/>
    <c:set var="subTypes" value="${form.creditSubProductTypes}"/>
    <tiles:insert definition="dictionary">

        <tiles:put name="pageTitle" value="Коды субпродуктов"/>

        <tiles:put name="data" type="string">

            <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable">
                <tr>
                    <th>
                        <bean:message key="label.loan.claim.tb.name" bundle="loanclaimBundle"/>
                    </th>
                    <th>
                        <bean:message key="label.loan.claim.rub" bundle="loanclaimBundle"/>
                    </th>
                    <th>
                        <bean:message key="label.loan.claim.usd" bundle="loanclaimBundle"/>
                    </th>
                    <th>
                        <bean:message key="label.loan.claim.eur" bundle="loanclaimBundle"/>
                    </th>
                </tr>
                <c:forEach var="tb" items="${tbNumbers}">
                    <tr>
                        <td class="ListLine">
                            <c:out value="${phiz:getTBNameByCode(tb)}"/>
                        </td>
                        <td>
                            <c:out value="${phiz:getSubCodeByTbAndCorrency(tb,'RUB',subTypes)}"/>
                        </td>
                        <td>
                            <c:out value="${phiz:getSubCodeByTbAndCorrency(tb,'USD',subTypes)}"/>
                        </td>
                        <td>
                            <c:out value="${phiz:getSubCodeByTbAndCorrency(tb,'EUR',subTypes)}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div style="float:right;">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close"/>
                    <tiles:put name="bundle" value="loanclaimBundle"/>
                    <tiles:put name="onclick" value="window.close();"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>