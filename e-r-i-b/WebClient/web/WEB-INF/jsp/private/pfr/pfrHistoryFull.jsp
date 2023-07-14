<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/pfr/pfrHistoryFull">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isPrintAbstract" value="${form.printAbstract}"/>

    <tiles:insert definition="accountInfo">

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Пенсионные программы"/>
                <tiles:put name="action" value="/private/npf/list.do"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="pageTitle" type="string">
            <bean:message key="application.title" bundle="accountInfoBundle"/>
        </tiles:put>
        <tiles:put name="mainmenu" value="Info"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.print"/>
                <tiles:put name="commandHelpKey" value="button.print.help"/>
                <tiles:put name="bundle" value="accountInfoBundle"/>
                <tiles:put name="onclick" value="Print()"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.toList"/>
                <tiles:put name="commandHelpKey" value="button.toList.help"/>
                <tiles:put name="bundle" value="accountInfoBundle"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <tiles:insert definition="productTemplate" flush="false">
                        <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
                        <tiles:put name="img" value="${imagePath}/products/icon_pfr.jpg"/>
                        <tiles:put name="title">Виды и размеры пенсий</tiles:put>
                    </tiles:insert>
                    <div class="tabContainer">
                        <tiles:insert definition="paymentTabs" flush="false">
                            <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('account')}"/>
                            <c:choose>
                                <c:when test="${not empty target}">
                                    <tiles:put name="count" value="2"/>
                                </c:when>
                                <c:when test="${!showTemplates}">
                                    <tiles:put name="count" value="3"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="count" value="4"/>
                                </c:otherwise>
                            </c:choose>

                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first"/>
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="title" value="Виды и размеры пенсий"/>
                                    <tiles:put name="action" value="/private/pfr/pfrHistoryFull"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                        <div class="hasLayout accountProductInfo">
                            <fieldset>
                                <table class="additional-product-info">
                                    <tr>
                                        <td class="align-left BlockTitle" colspan="2">
                                            <c:choose>
                                                <c:when test="${isPrintAbstract && !form.accountHasPFRRecords}">
                                                    <div id="warnings" style="padding-left:36px">
                                                        <tiles:insert definition="roundBorderLight" flush="false">
                                                            <tiles:put name="color" value="greenBold"/>
                                                            <tiles:put name="data">
                                                               <p>Зачислений от Пенсионного Фонда России за указанный период не обнаружено</p>
                                                            </tiles:put>
                                                        </tiles:insert>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div id="warnings" style="padding-left:36px">
                                                        <tiles:insert definition="roundBorderLight" flush="false">
                                                            <tiles:put name="color" value="orange"/>
                                                            <tiles:put name="data">
                                                               <p>Если Вы получаете пенсии, пособия или другие выплаты от ПФР, то для получения справки об их видах и размерах заполните форму ниже</p>
                                                            </tiles:put>
                                                        </tiles:insert>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                    </tr>
                                </table>
                                <div class="pfrHistoryFullContent">
                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title" value="Выберите счет:"/>
                                        <tiles:put name="useInPFR" value="true"/>
                                        <tiles:put name="isNecessary" value="true"/>
                                        <tiles:put name="data">
                                            <html:select name="form" property="fromResource" styleId="fromResource">
                                                <html:option value="">Выберите счет/карту списания</html:option>
                                                <c:forEach items="${form.chargeOffResources}" var="resource">
                                                    <html:option value="${resource.code}">
                                                        <c:choose>
                                                            <c:when test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                                                                <c:out value="${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${resource.number} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </html:option>
                                                </c:forEach>
                                            </html:select>
                                            <div class="payments-legend">Выберите счет, на который зачисляется пенсия</div>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="filterDataPeriod" flush="false">
                                        <tiles:put name="useInPFR" value="true"/>
                                        <tiles:put name="title" value="Операции:"/>
                                        <tiles:put name="enableFilterDataPeriod" value="false"/>
                                        <tiles:put name="week" value="false"/>
                                        <tiles:put name="isSimpleTrigger" value="true"/>
                                        <tiles:put name="name" value="Period"/>
                                    </tiles:insert>
                                </div>
                            </fieldset>
                        </div>

                        <div class="buttonsArea pfrButtonsArea">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.printPFRHistoryFull"/>
                                <tiles:put name="commandTextKey" value="button.printPFRHistoryFull"/>
                                <tiles:put name="commandHelpKey" value="button.printPFRHistoryFull.help"/>
                                <tiles:put name="bundle" value="pfrBundle"/>
                            </tiles:insert>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
            <c:if test="${isPrintAbstract && not empty form.accountHasPFRRecords && form.accountHasPFRRecords}">
                <script type="text/javascript">

                    function openExtendedAbstract()
                    {
                        var url = "${phiz:calculateActionURL(pageContext,'/private/pfr/pfrHistoryFullPrint.do')}";
                        var params = "?fromResource=" + "${form.filters['fromResource']}";
                        var typePeriod = $('input[name="filter(typePeriod)"]:checked').val();
                        if (typePeriod == 'period')
                        {
                            params = addParam2List(params, "filter(fromPeriod)", "fromDateString");
                            params = addParam2List(params, "filter(toPeriod)", "toDateString");
                        }
                        else
                        {
                            params += "&typePeriod=" + typePeriod;
                        }
                        window.open(url + params, "PrintAbstract", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                    }

                    doOnLoad(function ()
                    {
                        openExtendedAbstract();
                    });
                </script>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>