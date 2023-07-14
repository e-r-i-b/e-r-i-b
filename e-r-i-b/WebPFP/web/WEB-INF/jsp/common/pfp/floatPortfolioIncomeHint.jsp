<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="floatMessageShadow" flush="false">
    <tiles:put name="id" value="floatMessage${portfolio.type}"/>
    <tiles:put name="data"><span class="bold pointer">&#8721;</span>&nbsp;</tiles:put>
    <tiles:put name="showHintImg" value="false"/>
    <tiles:put name="float_" value=""/>
    <tiles:put name="hintClass" value="pfpPortfolioIncomeHint"/>
    <tiles:put name="needClear" value="false"/>
    <tiles:put name="text">
        <div style="text-align: left;">

            <span class="fractionDescr float">—редн€€ доходность портфел€ =&nbsp;</span>
            <div class="float">
                <table cellpadding="0" cellspacing="0" class="fraction">
                    <tr>
                        <td class="dividend">
                            <span class="bold">X1*Y1 + X2*Y2 + ... + Xn*Yn</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="divider">
                            <span class="bold">X1 + X2 + ... + Xn</span>
                        </td>
                    </tr>
                </table>
            </div>
            <span class="fractionDescr float">,</span>
            <div class="clear"></div>
            <span> где:</span>

            <br/><span class="bold fractionWhereDesc">X1, X2, ... , Xn</span> Ц сумма инвестируема€ в n-ый элемент в портфеле;
            <br/><span class="bold fractionWhereDesc">Y1, Y2, ... , Yn</span> Ц доходность n-го элемента в портфеле.

            <c:if test="${not empty portfolioIncome && portfolioIncome != 0}">
                <br/>ј именно:&nbsp;

                <c:set var="numProd" value="0"/>
                <c:forEach var="portfolioProduct" items="${portfolio.currentProductList}">
                    <c:forEach var="baseProduct" items="${portfolioProduct.baseProductList}">
                        <c:set var="numProd" value="${numProd + 1}"/>
                        <c:if test="${numProd != 1}">
                            <c:set var="dividend" value="${dividend} + "/>
                            <c:set var="divider" value="${divider} + "/>
                        </c:if>

                        <c:set var="dividend">${dividend}${phiz:getStringInNumberFormat(baseProduct.amount.decimal)}*${baseProduct.income}%</c:set>
                        <c:set var="divider" value="${divider}${phiz:getStringInNumberFormat(baseProduct.amount.decimal)}"/>
                    </c:forEach>
                </c:forEach>

                <span class="bold">(${dividend})/(${divider})&nbsp;=&nbsp;${portfolioIncome}%</span>
            </c:if>
        </div>
    </tiles:put>
    <tiles:put name="dataClass" value="dataHint"/>
</tiles:insert>