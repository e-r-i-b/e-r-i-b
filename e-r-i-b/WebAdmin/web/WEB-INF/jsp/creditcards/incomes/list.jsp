<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/creditcards/incomes/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>

    <tiles:insert definition="creditCardsList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.income.list" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="IncomeLevels"/>

        <c:if test="${empty form.data}">
            <tiles:put name="menu" type="string">
                <tiles:insert definition="clientButton" flush="false" operation="EditIncomeLevelOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add"/>
                    <tiles:put name="bundle"         value="${bundle}"/>
                    <tiles:put name="action"         value="/creditcards/incomes/edit.do"/>
                </tiles:insert>
            </tiles:put>
        </c:if>
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

                var addUrl = "${phiz:calculateActionURL(pageContext,'/creditcards/incomes/edit')}";
                function editIncomeLevel(id)
                {
                    if(id == null)
                    {
                        if(!checkSelection("selectedIds", "Выберите уровень дохода"))
                            return;
                        id = getRadioValue(document.getElementsByName("selectedIds"));
                    }
                    window.location = addUrl + "?id=" + id;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="incomeLevelsList"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditIncomeLevelOperation">
                        <tiles:put name="commandTextKey" value="button.add"/>
                        <tiles:put name="commandHelpKey" value="button.add"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="action"         value="/creditcards/incomes/edit.do"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="EditIncomeLevelOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="onclick"        value="editIncomeLevel()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveIncomeLevelOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите уровень дохода')
                        </tiles:put>
                        <tiles:put name="confirmText"    value="Удалить выбранный уровень дохода?"/>
                    </tiles:insert>
                <div class="clear"></div>
                </tiles:put>
                <tiles:put name="grid">
                    <div class="gridDiv kredInfo">
                    <c:forEach var="currency" items="${form.currencies}">
                        <c:set var="include" value="0"/>
                        <div class="tableCaption">
                            Валюта продукта - ${phiz:getISOCodeExceptRubles(currency.code)}
                        </div>
                        <table class="depositProductInfo" cellspacing="0" cellpadding="0" border="0">
                            <tr>
                                <th width="30px"></th>
                                <th><bean:message key="label.income.average.level" bundle="${bundle}"/></th>
                                <th><bean:message key="label.income.available.credit.limit" bundle="${bundle}"/></th>
                                <th><bean:message key="label.income.available.products" bundle="${bundle}"/></th>
                            </tr>
                            <c:forEach var="incomeLevel" items="${form.data}">
                                <tr>
                                    <td>
                                        <input type="radio" name="selectedIds" value="${incomeLevel.id}"/>
                                    </td>
                                    <td>
                                        <c:if test="${incomeLevel.minIncome != null}">
                                            <bean:message key="label.product.from" bundle="${bundle}"/>
                                            <c:out value="${phiz:formatAmountISO(incomeLevel.minIncome)}"/>
                                        </c:if>
                                        <c:if test="${incomeLevel.maxIncome != null}">
                                            <bean:message key="label.product.to" bundle="${bundle}"/>
                                            <c:out value="${phiz:formatAmountISO(incomeLevel.maxIncome)}"/>
                                            <c:if test="${incomeLevel.maxIncomeInclude == false}">*<c:set var="include" value="1"/></c:if>
                                        </c:if>
                                    </td>

                                    <c:set var="haveConditionInThisCurrency" value="false"/>
                                    <c:forEach var="condition" items="${incomeLevel.conditions}">
                                        <c:if test="${condition.currency.number == currency.number}">
                                            <td>
                                                <bean:message key="label.product.from" bundle="${bundle}"/>
                                                <c:out value="${phiz:formatAmountISO(condition.minCreditLimit.value)}"/>
                                                <bean:message key="label.product.to" bundle="${bundle}"/>
                                                <c:out value="${phiz:formatAmountISO(condition.maxCreditLimit.value)}"/>
                                                <c:if test="${condition.maxCreditLimitInclude == false}">*<c:set var="include" value="1"/></c:if>
                                            </td>
                                            <td>
                                                <c:set var="availableProducts" value=""/>
                                                <c:set var="isFirst" value="true"/>
                                                <c:forEach var="product" items="${phiz:getAvailableCreditCardProducts(condition.currency.code, condition.maxCreditLimit.value.decimal, condition.maxCreditLimitInclude)}">
                                                    <c:if test="${!isFirst}">
                                                        <c:set var="availableProducts" value="${availableProducts}, "/>
                                                    </c:if>
                                                    <c:set var="availableProducts" value="${availableProducts}${product.name}"/>
                                                    <c:set var="isFirst" value="false"/>
                                                </c:forEach>
                                                <c:out value="${availableProducts}"/>
                                                &nbsp;
                                            </td>
                                            <c:set var="haveConditionInThisCurrency" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${not haveConditionInThisCurrency}">
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </table>
                        <c:if test="${include eq 1}"><div class="loanTitle include">* - не включительно</div></c:if>
                        <br/>
                    </c:forEach>
                    </div>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не задано ни одного интервала дохода."/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>