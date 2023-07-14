<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<html:form action="/private/ima/products/list" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
    <tiles:insert definition="paymentMain">
        <tiles:put name="mainmenu" value="Payments"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="action" value="/private/payments.do"/>
            </tiles:insert>

            <%--Ссылка на категорию--%>
            <c:if test="${not empty form.category}">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name"><bean:message key="category.operations.${form.category}" bundle="paymentServicesBundle"/></tiles:put>
                    <tiles:put name="action" value="/private/payments/category.do?categoryId=${form.category}"/>
                    <tiles:put name="last" value="false"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Открытие обезличенного металлического счета"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Открытие обезличенного металлического счета"/>
                <tiles:put name="data" type="string">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${globalImagePath}/iconPmntList_IMAOpeningClaim.jpg"/>
                        <tiles:put name="description">
                            <c:choose>
                                <c:when test="${not empty form.data}">
                                    <h3>Для того чтобы открыть новый обезличенный металлический счет, выберите
                                        подходящий Вам металл, отметьте его в списке и нажмите на кнопку
                                        &laquo;Продолжить&raquo;.</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3><bean:message bundle="commonBundle" key="text.productList.ima.didNotOpened"/></h3>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                    <c:if test="${not empty form.data}">
                        <div id="paymentStripe">
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="выбор металла"/>
                                <tiles:put name="current" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="заполнение заявки"/>
                                <tiles:put name="future" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="подтверждение"/>
                                <tiles:put name="future" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="статус операции"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>

                        <div id="list">
                            <c:forEach var="ima" items="${form.data}" varStatus="i">
                                <c:set var="currency" value="${ima.currency}"/>
                                <c:if test="${i.last}">
                                    <c:set var="clazz" value="Last"/>
                                </c:if>
                                <c:set var="rate" value="${form.currencyRates[ima.currency]}"/>
                                <c:if test="${not empty rate}">
                                    <div class="imaProductItem${clazz}" onclick="selectIMA(this);">
                                        <input type="radio" name="selectedId" value="${ima.id}"/>
                                        <h2><c:out value="${ima.name}"/> ${phiz:normalizeMetalCode(currency.code)}</h2>
                                            <div class="imaRate">
                                                1.0 ${phiz:getCurrencySign(currency.code)}
                                                &#8594; ${phiz:getFormattedCurrencyRate(rate, 2)}
                                                ${phiz:getCurrencySign(rate.toCurrency.code)}
                                            </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>

                        <div class="clear"></div>
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.back"/>
                                <tiles:put name="commandHelpKey" value="button.back.help"/>
                                <tiles:put name="bundle" value="claimsBundle"/>
                                <tiles:put name="action" value="/private/payments"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.next"/>
                                <tiles:put name="commandHelpKey" value="button.next"/>
                                <tiles:put name="bundle" value="claimsBundle"/>
                                <tiles:put name="onclick" value="next();"/>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>

                        <script type="text/javascript">
                            function selectIMA(element)
                            {
                                $(element).find('input').attr('checked', true);
                            }

                            <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments/payment')}"/>
                            function next()
                            {
                                if (checkOneSelection('selectedId'))
                                {
                                    var imaId = $('input[name=selectedId]:checked').val();
                                    window.location = '${actionUrl}' + '?imaId=' + imaId + '&form=IMAOpeningClaim' + '&fromResource=' + '${param['fromResource']}';
                                }
                            }

                            doOnLoad(function(){
                                $('input[type=radio]').eq(0).attr('checked', true);
                            });
                        </script>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
