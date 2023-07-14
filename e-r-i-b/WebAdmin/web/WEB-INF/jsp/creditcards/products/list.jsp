<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/creditcards/products/list" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>

    <tiles:insert definition="creditCardsList">
        <tiles:put name="submenu" type="string" value="CreditCardProducts"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.product.list" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditCreditCardProductOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle"         value="${bundle}"/>
                <tiles:put name="action"         value="/creditcards/products/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <script type="text/javascript">
                function clearFilter()
                {
                    ensureElement("filterProduct").selectedIndex = 0;
                    ensureElement("filterPublicity").selectedIndex = 0;
                }
            </script>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.product.name"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(product)" styleId="filterProduct" styleClass="filterSelect">
                        <html:option value="">Все</html:option>
                        <c:forEach var="product" items="${phiz:getAllCreditCardProducts()}">
                            <html:option value="${product.id}">${product.name}</html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.filter.status"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(publicity)" styleId="filterPublicity" styleClass="filterSelect">
                        <html:option value="">Все</html:option>
                        <html:option value="PUBLISHED">Доступен клиенту</html:option>
                        <html:option value="UNPUBLISHED">Не доступен клиенту</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function showDiv(id)
                {
                    var div = ensureElement("terms_"+id);
                    if(div != null)
                    {
                        var hide = div.style.display != "none";
                        div.style.display = hide ? "none" : "block";
                    }
                }

                var addUrl = "${phiz:calculateActionURL(pageContext,'/creditcards/products/edit')}";
                function editProduct(id)
                {
                    if(id == null)
                    {
                        checkIfOneItem("selectedIds");
                        if(!checkSelection("selectedIds", "Выберите продукт"))
                            return;
                        id = getRadioValue(document.getElementsByName("selectedIds"));
                    }
                    window.location = addUrl + "?id=" + id;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="creditCardProductsList"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditCreditCardProductOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="onclick"        value="editProduct()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveCreditCardProductOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите продукт');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText"    value="Удалить выбранный продукт?"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="PublishCreditCardProductOperation">
                        <tiles:put name="commandKey"     value="button.publish"/>
                        <tiles:put name="commandHelpKey" value="button.publish"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите продукт');
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="PublishCreditCardProductOperation">
                        <tiles:put name="commandKey"     value="button.unpublish"/>
                        <tiles:put name="commandHelpKey" value="button.unpublish"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите продукт');
                            }
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <div class="gridDiv kredInfo" style="padding-left:20px;">
                    <c:forEach var="product" items="${form.data}">
                        <c:set var="include" value="0"/>
                        <div>
                            <input class="loanRadio" type="radio" name="selectedIds" value="${product.id}"/>
                            <span class="bold"><c:out value="${product.name}"/></span>
                            -
                            <c:choose>
                                <c:when test="${product.publicity == 'PUBLISHED'}">
                                    доступен клиенту
                                </c:when>
                                <c:when test="${product.publicity == 'UNPUBLISHED'}">
                                    не доступен клиенту
                                </c:when>
                                <c:when test="${product.publicity == 'DELETED'}">
                                    удален
                                </c:when>
                            </c:choose>
                        </div>
                        <c:if test="${product.allowGracePeriod}">
                            <div>
                                <span class="bold"><bean:message key="label.product.grace.period" bundle="${bundle}"/>:</span>
                                <bean:message key="label.product.to" bundle="${bundle}"/>
                                <c:out value="${product.gracePeriodDuration}"/>
                                <bean:message key="label.product.calendar.days" bundle="${bundle}"/>
                            </div>
                            <div>
                                <span class="bold"><bean:message key="label.product.grace.period.interest.rate" bundle="${bundle}"/>:</span>
                                <c:out value="${product.gracePeriodInterestRate}"/>
                                <bean:message key="label.product.percent" bundle="${bundle}"/>
                            </div>
                        </c:if>
                        <c:if test="${product.useForPreapprovedOffers}">
                            <div>
                                <span class="bold"><bean:message key="label.product.for.preapproved.offers" bundle="${bundle}"/></span>
                            </div>
                        </c:if>
                        <c:if test="${product.defaultForPreapprovedOffers}">
                            <div>
                                <span class="bold"><bean:message key="label.product.default.in.preapproved.offers" bundle="${bundle}"/></span>
                            </div>
                        </c:if>
                        <div>
                            <c:if test="${not empty product.conditions}">
                                <table class="depositProductInfo">
                                    <tr>
                                        <th width="25%"><bean:message key="label.product.credit.limit" bundle="${bundle}"/></th>
                                        <th><bean:message key="label.product.percent.rate" bundle="${bundle}"/> (<bean:message key="label.product.percent" bundle="${bundle}"/>)</th>
                                        <th><bean:message key="label.product.offer.percent.rate" bundle="${bundle}"/> (<bean:message key="label.product.percent" bundle="${bundle}"/>)</th>
                                        <th><bean:message key="label.product.year.payment.full" bundle="${bundle}"/></th>
                                    </tr>
                                    <c:forEach var="condition" items="${product.conditions}">
                                        <tr>
                                            <td>
                                                <bean:message key="label.product.from" bundle="${bundle}"/>
                                                <c:out value="${phiz:formatAmountISO(condition.minCreditLimit.value)}"/>
                                                <bean:message key="label.product.to" bundle="${bundle}"/>
                                                <c:out value="${phiz:formatAmountISO(condition.maxCreditLimit.value)}"/>
                                                <c:if test="${condition.maxCreditLimitInclude == false}">*<c:set var="include" value="1"/></c:if>
                                            </td>
                                            <td>
                                                <c:out value="${condition.interestRate}"/>
                                            </td>
                                            <td>
                                                <c:out value="${condition.offerInterestRate}"/>
                                            </td>
                                            <td>
                                                <c:out value="${phiz:formatAmountISO(condition.firstYearPayment)}"/>/<c:out value="${phiz:formatAmountISO(condition.nextYearPayment)}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </div>
                        <c:if test="${include eq 1}"><div class="loanTitle include">* - не включительно</div></c:if>
                        <c:if test="${not empty product.additionalTerms}">
                            <div>
                                <a href="#" onclick="showDiv(${product.id});"><bean:message key="label.product.additional.terms" bundle="${bundle}"/></a>
                                <div id="terms_${product.id}" style="display:none">
                                    <c:out value="${product.additionalTerms}"/>
                                </div>
                            </div>
                        </c:if>
                        <br/>
                    </c:forEach>
                    </div>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено данных, соответствующих заданному фильтру."/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>