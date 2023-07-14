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

<html:form action="/private/graphics/finance" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="rubCurrency" value="${phiz:getCurrencySign('RUB')}"/>

    <tiles:insert definition="financePlot">

        <tiles:put name="data" type="string">
            <c:if test="${!empty form.cards || !empty form.accounts || !empty form.imAccounts}">

                    <tiles:insert definition="roundBorderWithoutTop" flush="false">
                        <tiles:put name="top">
                            <c:set var="selectedTab" value="myFinance"/>
                            <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                        </tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="financeContainer" flush="false">
                            <tiles:put name="infoText">
                                На этой странице Вы можете посмотреть доступные денежные средства на вкладах, картах и других продуктах. Остаток по валютным счетам пересчитан в рубли по курсу ЦБ, по счетам ОМС по курсу покупки Банка.                                
                            </tiles:put>
                            <tiles:put name="showTitle" value="false"/>
                            <tiles:put name="showFavourite" value="false"/>
                            <tiles:put name="data">
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
                                                var errorProducts = array[2];
                                                var diagram = financeDiagram("diagramId", products, rate, rubCurrency);
                                                initDiagramData(diagram);
                                                drawDiagram(diagram);
                                                if (errorProducts.length == 0)
                                                    return;

                                                var isFirst = true;
                                                var cardError = false;
                                                var accountError = false;
                                                var imAccountError = false;
                                                var securityAccountError = false;

                                                var msg = '';
                                                for (var i = 0; i < errorProducts.length; i++)
                                                {
                                                    var errorProduct = errorProducts[i];

                                                    cardError               = cardError || errorProduct.type == "card";
                                                    accountError            = accountError || errorProduct.type == "account";
                                                    imAccountError          = imAccountError || errorProduct.type == "imAccount";
                                                    securityAccountError    = securityAccountError || errorProduct.type == "securityAccount";

                                                    if (!isFirst)
                                                        msg += ', ';
                                                    isFirst = false;

                                                    msg += errorProduct.title + ' ' + errorProduct.number;
                                                }

                                                if (cardError && !accountError && !imAccountError && !securityAccountError)
                                                {
                                                    if (errorProducts.length == 1)
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.card"/> ' + msg;
                                                    else
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.cards"/> ' + msg;
                                                }
                                                else if (!cardError && accountError && !imAccountError && !securityAccountError)
                                                {
                                                    if (errorProducts.length == 1)
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.account"/> ' + msg;
                                                    else
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.accounts"/> ' + msg;
                                                }
                                                else if (!cardError && !accountError && imAccountError && !securityAccountError)
                                                {
                                                    if (errorProducts.length == 1)
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.account"/> ' + msg;
                                                    else
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.accounts"/> ' + msg;
                                                }
                                                else if (!cardError && !accountError && !imAccountError && securityAccountError)
                                                {
                                                    if (errorProducts.length == 1)
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.securityAccount"/> ' + msg;
                                                    else
                                                        msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.securityAccounts"/> ' + msg;
                                                }
                                                else
                                                {
                                                    msg = '<bean:message bundle="financesBundle" key="message.finance.balance.not.available.products"/> ' + msg;
                                                }
                                                addMessage(msg + ' <bean:message bundle="financesBundle" key="message.finance.balance.not.available.products.suffix"/>');
                                            });
                                        </script>
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
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>

</html:form>
