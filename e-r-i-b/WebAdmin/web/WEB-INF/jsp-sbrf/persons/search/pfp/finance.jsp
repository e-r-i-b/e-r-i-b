<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="bundleName" value="commonBundle"/>
<fmt:setLocale value="ru-RU"/>

<html:form action="/pfp/person/finance" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="rubCurrency" value="${phiz:getCurrencySign('RUB')}"/>

    <tiles:insert definition="pfpPassing">
        <tiles:put name="aditionalHeaderData" value="/WEB-INF/jsp/common/plotAdditionalHeader.jsp"/>
        <tiles:put name="submenu" type="string" value="PersonFinances"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="simpleForm" flush="false">
                <tiles:put name="name" value="Средства клиента"/>
                <tiles:put name="description" value="На этой странице Вы можете посмотреть структуру денежных средств клиента на вкладах и картах. Остаток по валютным счетам и картам пересчитан в рубли по курсу ЦБ. Для того чтобы получить подробную информацию, наведите курсор на интересующий Вас сегмент диаграммы."/>
                <tiles:put name="additionalStyle" value="width750"/>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">
                    <c:if test="${!empty form.cards || !empty form.accounts || !empty form.imAccounts}">
                        <c:choose>
                            <c:when test="${form.fillCurrencyRate}">
                                <tiles:insert page="/WEB-INF/jsp/common/finance/financesDiagram.jsp" flush='false'>
                                        <tiles:put name="diagramId" value="diagramId"/>
                                </tiles:insert>
                                <script type="text/javascript">
                                    $(document).ready(function()
                                    {
                                        <tiles:insert page="/WEB-INF/jsp/common/finance/dataForDiagram.js.jsp" flush="false">
                                            <tiles:put name="diagramId" value="diagramId"/>
                                        </tiles:insert>

                                        var rubCurrency = '${rubCurrency}';
                                        var array = getProducts();
                                        var rate = array[0];
                                        var products = array[1];
                                        var diagram = financeDiagram("diagramId", products, rate, rubCurrency);
                                        initDiagramData(diagram);
                                        drawDiagram(diagram);
                                    });
                                </script>
                            </c:when>
                            <c:otherwise>В настоящий момент информация о структуре денежных средств клиента недоступна. Пожалуйста, повторите попытку позже.</c:otherwise>
                        </c:choose>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
