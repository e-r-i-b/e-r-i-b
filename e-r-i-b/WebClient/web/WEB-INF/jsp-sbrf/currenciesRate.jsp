<%--
  Created by IntelliJ IDEA.
  User: lukina
  Date: 09.06.2009
  Time: 9:43:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%-- <%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>        --%>

<html:form action="/private/rate" onsubmit="return setEmptyAction(event)">

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="currentDate" value="${phiz:currentDate()}"/>
<c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
<c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="Info"/>
    <tiles:put name="submenu" value="CurrenciesRate"/>
    <tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
          <tiles:put name="text" value="Курсы валют"/>
          <tiles:put name="cleanText" value="true"/>
          <tiles:put name="data" type="string">
                <tr>
                    <td colspan="3" class="tblNoBorderInf" style="border-bottom:1px solid #ffffff;padding-left:0px;">
                        <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td><bean:message bundle="commonBundle" key="text.SBOL.forOperationsFromDate"/><bean:write name="currentDate" property="time" format="dd.MM.yyyy HH:mm"/></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                <tr class="tblInfHeader">
                    <td>&nbsp;</td>
                    <td width="50%" align="center">Покупка</td>
                    <td width="50%" align="center">Продажа</td>
                </tr>
                <tr>
                     <td align="center" valign="top"  width="50px">                        
                        <img src="${imagePath}/iconMid_dollar.gif" alt="">
                     </td>
                     <td align="right">
                        <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','USD', 'BUY', department, tarifPlanCodeType), 2)}"/>
                        <c:choose>
                            <c:when test="${rate != ''}">${rate}</c:when>
                            <c:otherwise> &mdash; </c:otherwise>
                        </c:choose>
                     </td>
                     <td align="right">
                         <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('USD','RUB', 'SALE', department, tarifPlanCodeType), 2)}"/>
                         <c:choose>
                             <c:when test="${rate != ''}">${rate}</c:when>
                             <c:otherwise> &mdash; </c:otherwise>
                         </c:choose>
                     </td>
                 </tr>
                 <tr>
                     <td align="center" class="rightborder" valign="top"  width="50">
                         <img src="${imagePath}/iconMid_euro.gif" alt="">
                     </td>
                     <td align="right">
                        <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','EUR', 'BUY', department, tarifPlanCodeType), 2)}"/>
                        <c:choose>
                            <c:when test="${rate != ''}">${rate}</c:when>
                            <c:otherwise> &mdash; </c:otherwise>
                        </c:choose>
                     </td>
                     <td align="right">
                         <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('EUR','RUB', 'SALE', department, tarifPlanCodeType), 2)}"/>
                         <c:choose>
                             <c:when test="${rate != ''}">${rate}</c:when>
                             <c:otherwise> &mdash; </c:otherwise>
                         </c:choose>
                     </td>
                 </tr>
            </tiles:put>            
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>