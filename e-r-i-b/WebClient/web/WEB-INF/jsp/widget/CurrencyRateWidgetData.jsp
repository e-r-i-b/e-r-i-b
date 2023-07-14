<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="numberOfShowCurrencyCodes" value="${fn:length(showCurrencyCodes)}"/>
<c:set var="numberOfShowImaCodes" value="${fn:length(showImaCodes)}"/>
<c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>

<div panel="compactTable">
    <c:if test="${not empty description}">
        <div>
            <c:out value="${description}"/>
        </div>
    </c:if>
    <table border="1px" class="course CurrencyRateWidget compact">
        <c:if test="${numberOfShowCurrencyCodes != 0}">
            <tr class="courseTableHeader">
                <th>Валюта</th>
                <th>Покупка</th>
                <th>Продажа</th>
            </tr>
            <c:forEach items="${showCurrencyCodes}" var="code">
                <tr>
                    <th>
                        ${code}
                        <c:if test="${editMode}">
                            <c:set var="checked" value=""/>
                            <span class="courseShowInThisWidget">
                                <input type="checkbox" name="currencyShowInThisWidget" onclick="cancelBubbling(event);" value="${code}"/>
                            </span>
                        </c:if>
                    </th>
                    <td class="compact">
                        <c:set var="rate" value="${phiz:getRateByDepartment(code, 'RUB', 'BUY_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                        ${formattedRate != '' ? formattedRate : '&mdash;'}
                        <c:choose>
                            <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                                <img src="${imagePath}/course_up.png" alt=""/>
                            </c:when>
                            <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                                <img src="${imagePath}/course_down.png" alt=""/>
                            </c:when>
                            <c:otherwise>
                                &nbsp&nbsp&nbsp&nbsp
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="compact">
                        <c:set var="rate" value="${phiz:getRateByDepartment(code, 'RUB', 'SALE_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                        ${formattedRate != '' ? formattedRate : '&mdash;'}
                        <c:choose>
                            <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                                <img src="${imagePath}/course_up.png" alt=""/>
                            </c:when>
                            <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                                <img src="${imagePath}/course_down.png" alt=""/>
                            </c:when>
                            <c:otherwise>
                                &nbsp&nbsp&nbsp&nbsp
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${not editMode}">
                <tr>
                    <th colspan='3'>
                       <a class="text-green courseLink" onclick="return redirectResolved();"
                        href="${phiz:calculateActionURL(pageContext, "/private/payments/payment.do?form=ConvertCurrencyPayment")}">
                        Обменять валюту
                       </a>
                    </th>
                </tr>
            </c:if>
        </c:if>
        <c:if test="${numberOfShowImaCodes != 0}">
            <tr class="courseTableHeader">
                <th>Металлы</th>
                <th>Покупка</th>
                <th>Продажа</th>
            </tr>
        </c:if>
        <c:forEach items="${showImaCodes}" var="numericCode">
            <c:choose>
                <c:when test="${numericCode eq 'A98'}"><c:set var="code" value="AUR"/></c:when>
                <c:when test="${numericCode eq 'A99'}"><c:set var="code" value="ARG"/></c:when>
                <c:when test="${numericCode eq 'A76'}"><c:set var="code" value="PTR"/></c:when>
                <c:when test="${numericCode eq 'A33'}"><c:set var="code" value="PDR"/></c:when>
            </c:choose>
            <tr>
                <th>
                    ${code}
                    <c:if test="${editMode}">
                        <c:set var="checked" value=""/>
                        <span class="courseShowInThisWidget">
                            <input type="checkbox" name="imaShowInThisWidget" onclick="cancelBubbling(event);" value="${numericCode}"/>
                        </span>
                    </c:if>
                </th>

                <td class="compact">
                    <c:set var="rate" value="${phiz:getRateByDepartment(numericCode, 'RUB', 'BUY_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                    <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                    ${formattedRate != '' ? formattedRate : '&mdash;'}
                    <c:choose>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                            <img src="${imagePath}/course_up.png" alt=""/>
                        </c:when>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                            <img src="${imagePath}/course_down.png" alt=""/>
                        </c:when>
                        <c:otherwise>
                            &nbsp&nbsp&nbsp&nbsp
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="compact">
                    <c:set var="rate" value="${phiz:getRateByDepartment(numericCode, 'RUB', 'SALE_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                    <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                    ${formattedRate != '' ? formattedRate : '&mdash;'}
                    <c:choose>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                            <img src="${imagePath}/course_up.png" alt=""/>
                        </c:when>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                            <img src="${imagePath}/course_down.png" alt=""/>
                        </c:when>
                        <c:otherwise>
                            &nbsp&nbsp&nbsp&nbsp
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${not editMode and numberOfShowImaCodes != 0}">
            <tr>
                <th colspan='3'>
                    <a class="text-green courseLink" onclick="return redirectResolved();"
                       href="${phiz:calculateActionURL(pageContext,'/private/payments/payment.do?form=IMAPayment')}">
                        Купить/продать металл
                    </a>
                </th>
            </tr>
        </c:if>
    </table>
</div>

<div panel="wideTable">
    <c:if test="${not empty description}">
        <div>
            <c:out value="${description}"/>
        </div>
    </c:if>
    <table border="1px" class="course CurrencyRateWidget wide">
        <tr>
            <th class="withoutBorder">&nbsp;</th>
            <c:if test="${numberOfShowCurrencyCodes != 0}">
                <th colspan="${numberOfShowCurrencyCodes}">Валюта</th>
            </c:if>
            <c:if test="${numberOfShowImaCodes != 0}">
                <th colspan="${numberOfShowImaCodes}">Металл</th>
            </c:if>
        </tr>
        <tr class="courseTableHeader">
            <c:choose>
                <c:when test="${not editMode}">
                    <th>&nbsp;</th>
                </c:when>
                <c:otherwise>
                   <th class="alignLeft">Отображать:</th>
                </c:otherwise>
            </c:choose>
            <c:forEach items="${showCurrencyCodes}" var="code" varStatus="current">
                <c:set var="clazz" value="${current.first ? 'leftBorder' :current.last ? 'rightBorder' :'withoutBorder'}"/>
                <th class="${clazz}">
                    <c:if test="${editMode}">
                        <c:set var="checked" value=""/>
                        <span class="courseShowInThisWidget">
                            <input type="checkbox" name="currencyShowInThisWidget" onclick="cancelBubbling(event);" value="${code}"/>
                        </span>
                    </c:if>
                    ${code}
                </th>
            </c:forEach>
            <c:forEach items="${showImaCodes}" var="numericCode" varStatus="current">
                <c:choose>
                    <c:when test="${numericCode eq 'A98'}"><c:set var="code" value="AUR"/></c:when>
                    <c:when test="${numericCode eq 'A99'}"><c:set var="code" value="ARG"/></c:when>
                    <c:when test="${numericCode eq 'A76'}"><c:set var="code" value="PTR"/></c:when>
                    <c:when test="${numericCode eq 'A33'}"><c:set var="code" value="PDR"/></c:when>
                </c:choose>
                <c:set var="clazz" value="${current.first ? 'leftBorder' :current.last ? 'rightBorder' :'withoutBorder'}"/>
                    <th class="${clazz}">
                        <c:if test="${editMode}">
                            <c:set var="checked" value=""/>
                            <span class="courseShowInThisWidget">
                                <input type="checkbox" name="imaShowInThisWidget" onclick="cancelBubbling(event);" value="${numericCode}"/>
                            </span>
                        </c:if>
                        ${code}
                    </th>
            </c:forEach>
        </tr>
        <tr>
            <th class="alignLeft">Покупка</th>
            <c:forEach items="${showCurrencyCodes}" var="code" varStatus="current">
                <c:set var="clazz" value="${current.first ? 'leftBorder' :current.last ? 'rightBorder' :''}"/>
                <td class="${clazz}">
                    <c:set var="rate" value="${phiz:getRateByDepartment(code, 'RUB', 'BUY_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                    <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                    ${formattedRate != '' ? formattedRate : '&mdash;'}
                    <c:choose>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                            <img src="${imagePath}/course_up.png" alt=""/>
                        </c:when>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                            <img src="${imagePath}/course_down.png" alt=""/>
                        </c:when>
                        <c:otherwise>
                            &nbsp&nbsp&nbsp&nbsp
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
            <c:forEach items="${showImaCodes}" var="numericCode" varStatus="current">
                    <c:set var="clazz" value="${current.first ? 'leftBorder' :current.last ? 'rightBorder' :''}"/>
                    <td class="${clazz}">
                        <c:set var="rate" value="${phiz:getRateByDepartment(numericCode, 'RUB', 'BUY_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                        ${formattedRate != '' ? formattedRate : '&mdash;'}
                        <c:choose>
                            <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                                <img src="${imagePath}/course_up.png" alt=""/>
                            </c:when>
                            <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                                <img src="${imagePath}/course_down.png" alt=""/>
                            </c:when>
                            <c:otherwise>
                                &nbsp&nbsp&nbsp&nbsp
                            </c:otherwise>
                        </c:choose>
                    </td>
            </c:forEach>
        </tr>
        <tr>
            <th class="alignLeft">Продажа</th>
            <c:forEach items="${showCurrencyCodes}" var="code" varStatus="current">
                <c:set var="clazz" value="${current.first ? 'leftBorder' :current.last ? 'rightBorder' :''}"/>
                <td class="${clazz}">
                    <c:set var="rate" value="${phiz:getRateByDepartment(code, 'RUB', 'SALE_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                    <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                    ${formattedRate != '' ? formattedRate : '&mdash;'}
                    <c:choose>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                            <img src="${imagePath}/course_up.png" alt=""/>
                        </c:when>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                            <img src="${imagePath}/course_down.png" alt=""/>
                        </c:when>
                        <c:otherwise>
                            &nbsp&nbsp&nbsp&nbsp
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
            <c:forEach items="${showImaCodes}" var="numericCode" varStatus="current">
                <c:set var="clazz" value="${current.first ? 'leftBorder' :current.last ? 'rightBorder' :''}"/>
                <td class="${clazz}">
                    <c:set var="rate" value="${phiz:getRateByDepartment(numericCode, 'RUB', 'SALE_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
                    <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
                    ${formattedRate != '' ? formattedRate : '&mdash;'}
                    <c:choose>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                            <img src="${imagePath}/course_up.png" alt=""/>
                        </c:when>
                        <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                            <img src="${imagePath}/course_down.png" alt=""/>
                        </c:when>
                        <c:otherwise>
                            &nbsp&nbsp&nbsp&nbsp
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
        </tr>
        <c:if test="${not editMode}">
            <tr class="withoutBorder" >
                <c:if test="${numberOfShowCurrencyCodes != 0}">
                    <th  class="withoutBorder" colspan='${numberOfShowCurrencyCodes + 1}'>
                       <a class="text-green courseLink" onclick="return redirectResolved();"
                        href="${phiz:calculateActionURL(pageContext, "/private/payments/payment.do?form=ConvertCurrencyPayment")}">
                        Обменять валюту
                       </a>
                    </th>
                </c:if>
                <c:if test="${numberOfShowImaCodes != 0}">
                    <c:set var="colspan" value="${numberOfShowCurrencyCodes == 0 ? 1 : 0}"/>
                    <th  class="withoutBorder" colspan='${numberOfShowImaCodes + colspan}'>
                        <a class="text-green courseLink" onclick="return redirectResolved();"
                           href="${phiz:calculateActionURL(pageContext,'/private/payments/payment.do?form=IMAPayment')}">
                            Купить/продать металл
                        </a>
                    </th>
                </c:if>
            </tr>
        </c:if>
    </table>
</div>