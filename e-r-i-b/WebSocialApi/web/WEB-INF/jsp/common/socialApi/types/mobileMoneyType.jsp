<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:importAttribute ignore="true"/>
<%--
  Компонента для формирования данных типа Money
  sign - знак (+ или -)
  name - название поля

    Варианты использования:
  1. money - деньги типа Money

  2. decimal - сумма типа BigDecimal или подобного (без валюты)
     currency - валюта типа Currency

  3. decimal - сумма типа BigDecimal или подобного (без валюты)
     currencyCode - код валюты в виде строки
     currencySign - символ валюты в виде строки. Если не указан, то определяем сами по currencyCode

     noCents - не отображать копейки
--%>

<c:if test="${name!=''}">
    <${name}>
        <c:choose>
            <c:when test="${not empty money}">
                <c:choose>
                    <c:when test="${noCents}">
                        <amount>${sign}${phiz:getFormatAmountWithNoCents(money.decimal,".")}</amount>
                    </c:when>
                    <c:otherwise>
                        <amount>${sign}<fmt:formatNumber value="${money.decimal}" pattern="0.00"/></amount>
                    </c:otherwise>
                </c:choose>
                <tiles:insert definition="currencyType" flush="false">
                    <tiles:put name="currency" beanName="money" beanProperty="currency"/>
                </tiles:insert>
            </c:when>
            <c:when test="${not empty decimal && not empty currency}">
                <c:choose>
                    <c:when test="${noCents}">
                        <amount>${sign}${phiz:getFormatAmountWithNoCents(decimal,".")}</amount>
                    </c:when>
                    <c:otherwise>
                        <amount>${sign}<fmt:formatNumber value="${decimal}" pattern="0.00"/></amount>
                    </c:otherwise>
                </c:choose>
                <tiles:insert definition="currencyType" flush="false">
                    <tiles:put name="currency" beanName="currency"/>
                </tiles:insert>
            </c:when>
            <c:when test="${not empty decimal && not empty currencyCode}">
                <c:choose>
                    <c:when test="${noCents}">
                        <amount>${sign}${phiz:getFormatAmountWithNoCents(decimal,".")}</amount>
                    </c:when>
                    <c:otherwise>
                        <amount>${sign}<fmt:formatNumber value="${decimal}" pattern="0.00"/></amount>
                    </c:otherwise>
                </c:choose>
                <currency>
                    <code>${currencyCode}</code>
                    <name>${empty currencySign ? phiz:getCurrencySign(currencyCode) : currencySign}</name>
                </currency>
            </c:when>
        </c:choose>
    </${name}>
</c:if>
