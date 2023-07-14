<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/loanclaim/credit/condition/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>


    <tiles:insert definition="loansList">
    <tiles:put name="submenu" value="CreditProductCondition"/>
    <tiles:put name="menu" type="string">
        <c:if test="${not empty form.data}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle"         value="loanclaimBundle"/>
                <tiles:put name="action" value="/loanclaim/credit/condition/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle"         value="ermbBundle"/>
                <tiles:put name="validationFunction" value="validateSelection()"/>
                <tiles:put name="confirmText"    value="Удалить выбранный кредитный продукт?"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.edit"/>
                <tiles:put name="commandHelpKey" value="button.edit"/>
                <tiles:put name="bundle"         value="loanclaimBundle"/>
                <tiles:put name="onclick"        value="editRedirect()"/>
                <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            <tiles:insert definition="commandButton" flush="false" operation="PublishingCreditProductConditionOperation">
                <tiles:put name="commandKey"     value="button.publish"/>
                <tiles:put name="commandHelpKey" value="button.publish"/>
                <tiles:put name="bundle"         value="loanclaimBundle"/>
                <tiles:put name="validationFunction" value="validateSelection()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false" operation="PublishingCreditProductConditionOperation">
                <tiles:put name="commandKey"     value="button.unpublish"/>
                <tiles:put name="commandHelpKey" value="button.unpublish"/>
                <tiles:put name="bundle"         value="loanclaimBundle"/>
                <tiles:put name="validationFunction" value="validateSelection()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </c:if>
    </tiles:put>
    <tiles:put name="filter" type="string">
        <script type="text/javascript">
            function clearFilter()
            {
                ensureElement("filterKind").selectedIndex = 0;
                ensureElement("filterProduct").selectedIndex = 0;
                ensureElement("filterPublicity").selectedIndex = 0;
            }
        </script>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.loan.claim.credit.product.type"/>
            <tiles:put name="bundle" value="loanclaimBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <html:select property="filter(creditProductTypeId)" styleId="filterKind" styleClass="filterSelect">
                    <html:option value="">Все</html:option>
                    <c:forEach var="type" items="${phiz:getAllCreditProductTypes()}">
                        <html:option value="${type.id}">${type.name}</html:option>
                    </c:forEach>
                </html:select>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.loan.claim.credit.product"/>
            <tiles:put name="bundle" value="loanclaimBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <html:select property="filter(creditProductId)" styleId="filterProduct" styleClass="filterSelect">
                    <html:option value="">Все</html:option>
                    <c:forEach var="product" items="${phiz:getAllCreditProducts()}">
                        <html:option value="${product.id}">${product.name}</html:option>
                    </c:forEach>
                </html:select>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.loan.claim.status.status"/>
            <tiles:put name="bundle" value="loanclaimBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <html:select property="filter(status)" styleId="filterPublicity" styleClass="filterSelect">
                    <html:option value="">Все</html:option>
                    <html:option value="1">Доступен клиенту</html:option>
                    <html:option value="0">Не доступен клиенту</html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function getSelectId()
                {
                    return $('input[name="selectedIds"]:checked')[0].value;
                }
                function  validateSelection()
                {
                    return checkSelection("selectedIds", 'Выберите депозитный продукт') ;
                }

                function editRedirect()
                {
                    if (validateSelection())
                    {
                        var selectId = getSelectId();
                        if (selectId != null)
                        {
                            <c:set var="u" value="/loanclaim/credit/condition/edit.do"/>
                            var url = "${phiz:calculateActionURL(pageContext,u)}";
                            window.location = url + "?id=" + selectId;
                        }
                    }
                }

                function creditSubCodesRedirect(id)
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/loanclaim/credit/product/subcode/view.do')}";
                    var win = window.open(url + "?id=" + id , "", "width=1000,height=250,resizable=0,menubar=0,toolbar=0,scrollbars=1");
                    win.moveTo(screen.width / 2 - 375, screen.height / 2 - 200);
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="loanProductsList"/>
                <tiles:put name="text">
                    <bean:message key="label.loan.claim.credit.conditions" bundle="loanclaimBundle"/>
                </tiles:put>

                <tiles:put name="grid">
                    <div class="gridDiv">
                        <c:forEach var="entry" items="${form.data}">
                            <c:set var="creditType" value="${entry.key}"/>
                            <h2 style="padding-left:12px;"><c:out value="${creditType.name}"/></h2>
                            <span style="padding-left:12px;">
                                <bean:message key="label.loan.claim.credit.product.type.code" bundle="loanclaimBundle"/>
                                -
                                <c:out value="${creditType.code}"/>
                            </span>
                            <div class="kredInfo" style="padding-left:40px;">
                                <c:if test="${empty entry.value}">
                                    <bean:message key="label.loan.claim.credit.no.condition" bundle="loanclaimBundle"/>
                                </c:if>
                                <c:forEach var="condition" items="${entry.value}">
                                    <c:set var="creditProduct" value="${condition.creditProduct}"/>
                                    <div>
                                        <input class="loanRadio" type="radio" name="selectedIds" value="${condition.id}"/>
                                        <span class="bold"><c:out value="${creditProduct.name}"/></span>
                                        <c:choose>
                                            <c:when test="${creditProduct.ensuring}">
                                                <bean:message key="label.loan.claim.with.ensuring" bundle="loanclaimBundle"/>
                                            </c:when>
                                            <c:otherwise>
                                                <bean:message key="label.loan.claim.with.out.ensuring" bundle="loanclaimBundle"/>
                                            </c:otherwise>
                                        </c:choose>
                                        -
                                        <c:choose>
                                            <c:when test="${condition.published}">
                                                <bean:message key="label.loan.claim.credit.client.available" bundle="loanclaimBundle"/>
                                            </c:when>
                                            <c:otherwise>
                                                <bean:message key="label.loan.claim.credit.client.unavailable" bundle="loanclaimBundle"/>
                                            </c:otherwise>
                                        </c:choose>


                                        <c:if test="${not empty creditProduct.creditSubProductTypes}">
                                            &nbsp;&nbsp;&nbsp;
                                            <a onclick="creditSubCodesRedirect(${creditProduct.id});" class="blueGrayLink"><bean:message key="label.loan.claim.credit.sub.product.code" bundle="loanclaimBundle"/></a>
                                        </c:if>
                                    </div>

                                    <div>

                                        <c:choose>
                                            <c:when test="${condition.transactSMPossibility}">

                                                <input class="loanRadio" type="checkbox" checked="true" disabled="true"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="loanRadio" type="checkbox" disabled="true"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <bean:message key="label.loan.claim.credit.TranactSM.available" bundle="loanclaimBundle"/>
                                    </div>
                                    <div>
                                        <c:choose>
                                            <c:when test="${condition.selectionAvaliable}">
                                                <input class="loanRadio" type="checkbox" checked="true" disabled="true"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="loanRadio" type="checkbox" disabled="true"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <bean:message key="label.loan.claim.credit.selection.available" bundle="loanclaimBundle"/>
                                    </div>
                                    <div>
                                        <bean:message key="label.loan.claim.credit.tb.available" bundle="loanclaimBundle"/>
                                        <c:out value="${phiz:getTbNames(condition.departmentsList)}"/>
                                    </div>
                                    <div>
                                        <bean:message key="label.loan.claim.credit.product.code" bundle="loanclaimBundle"/>
                                        -
                                        <c:out value="${creditProduct.code}"/>
                                    </div>
                                    <div>
                                        <bean:message key="label.loan.claim.credit.product.date" bundle="loanclaimBundle"/>
                                        -
                                        <c:if test="${condition.minDuration.duration != 0}">
                                            ${(condition.minDuration.string2)}
                                        </c:if>
                                        <c:if test="${condition.maxDuration.duration != 0}">
                                            ${(condition.maxDuration.string2)}
                                            <c:if test="${condition.maxRangeInclude == false}">*<c:set
                                                    var="include" value="1"/></c:if>
                                        </c:if>
                                    </div>
                                    <c:if test="${condition.useInitialFee}">
                                        <div>
                                            <bean:message key="label.loan.claim.credit.initial.fee" bundle="loanclaimBundle"/>
                                            :
                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                            <c:out value="${condition.minInitialFee}"/>
                                            <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>

                                            <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                            <c:out value="${condition.maxInitialFee}"/>
                                            <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                            <c:if test="${condition.includeMaxInitialFee == false}">*<c:set
                                                    var="include" value="1"/></c:if>
                                        </div>
                                    </c:if>
                                    <div>
                                        <bean:message key="label.loan.claim.ensuring" bundle="loanclaimBundle"/>
                                        :
                                        <c:choose>
                                            <c:when test="${creditProduct.ensuring}">
                                                <bean:message key="label.loan.claim.required" bundle="loanclaimBundle"/>
                                            </c:when>
                                            <c:otherwise>
                                                <bean:message key="label.loan.claim.not.required" bundle="loanclaimBundle"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div>
                                        <table class="standartTable">
                                            <tr>
                                                <th><bean:message key="label.loan.claim.credit.amount" bundle="loanclaimBundle"/></th>
                                                <th><bean:message key="label.loan.claim.credit.current.interest.rate" bundle="loanclaimBundle"/></th>
                                            </tr>
                                            <c:forEach var="currCondition" items="${phiz:getAllActiveCurrCondition(condition)}">
                                                <tr>
                                                    <td>
                                                        <c:if test="${currCondition.minLimitAmount != null}">
                                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                                            <c:out value="${phiz:formatAmountISO(currCondition.minLimitAmount)}"/>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${currCondition.percentUse == false and currCondition.maxLimitAmount != null}">
                                                                <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                                                <c:out value="${phiz:formatAmountISO(currCondition.maxLimitAmount)}"/>
                                                                <c:if test="${currCondition.maxLimitInclude == false}">*<c:set
                                                                        var="include" value="1"/></c:if>
                                                            </c:when>
                                                            <c:when test="${currCondition.percentUse and currCondition.maxLimitPercent != null}">
                                                                <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                                                <c:out value="${currCondition.maxLimitPercent}"/>
                                                                <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                                                <c:if test="${currCondition.maxLimitInclude == false}">*<c:set
                                                                        var="include" value="1"/></c:if>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:out value="${currCondition.minPercentRate} - ${currCondition.maxPercentRate}"/>
                                                        <c:if test="${currCondition.maxPercentRateInclude == false}">*
                                                            <c:set var="include" value="1"/>
                                                        </c:if>
                                                        (
                                                        <bean:message key="label.loan.claim.active.from" bundle="loanclaimBundle"/>
                                                        <c:out value="${phiz:сalendarToString(currCondition.startDate)}"/>
                                                        <c:set var="activeTo" value="${phiz:nextCurrConditionDate(currCondition)}"/>
                                                        <c:if test="${not empty activeTo}">
                                                            <bean:message key="label.loan.claim.active.to" bundle="loanclaimBundle"/>
                                                            <c:out value="${activeTo}"/>
                                                        </c:if>
                                                        )
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                    <c:if test="${include eq 1}">
                                        <div class="loanTitle include">* - не включительно</div>
                                    </c:if>
                                    <c:if test="${not empty condition.additionalConditions}">
                                        <a href="javascript:hideOrShow('additionalConditions${condition.id}')">
                                            <bean:message key="label.loan.claim.credit.addition.terms" bundle="loanclaimBundle"/>
                                        </a>
                                        <div id="additionalConditions${condition.id}" style="display: none">
                                            <c:out value="${condition.additionalConditions}"/>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено данных, соответствующих заданному фильтру"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
