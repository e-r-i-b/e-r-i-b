<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="contextName" value="${phiz:loginContextName()}"/>
<c:set var="rubCurrency" value="${phiz:getCurrencySign('RUB')}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.MyFinancesWidget"/>
    <tiles:put name="cssClassname" value="MyFinancesWidget"/>
    <tiles:put name="borderColor" value="greenTop"/>
    <tiles:put name="sizeable" value="false"/>
    <tiles:put name="editable" value="false"/>
    <tiles:put name="props">
        rubCurrency: '${rubCurrency}',
        fillCurrencyRate: ${form.fillCurrencyRate},
        buildDiagramData: function()
        {
            <tiles:insert page="/WEB-INF/jsp/common/finance/dataForDiagram.js.jsp" flush="false"/>
            return getProducts();
        }
    </tiles:put>
    <tiles:put name="viewPanel">
        <table class="paymentHeader">
            <tr>
                <td>
                    <h3>
                        <p>
                            Остатки по валютным счетам пересчитаны в рубли по курсу ЦБ, по металлическим счетам – по курсу покупки банка.
                        </p>
                    </h3>
                </td>
            </tr>
        </table>
        <c:choose>
            <c:when test="${form.fillCurrencyRate}">
                <tiles:insert page="/WEB-INF/jsp/common/finance/financesDiagram.jsp" flush="false">
                    <%-- ID диаграммы - это кодификатор виджета --%>
                    <tiles:put name="diagramId" value="${form.codename}"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <span id="financeStructureMessage">В настоящий момент информация о структуре Ваших денежных средств недоступна. Пожалуйста, повторите попытку позже.</span>
                    </tiles:put>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>
