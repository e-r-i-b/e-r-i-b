<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/loans/products/list" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="loansBundle"/>

    <tiles:insert definition="loansList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.product.list" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="LoanProducts"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditLoanProductOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.product.help"/>
                <tiles:put name="viewType" value="blueBorder"/>
                <tiles:put name="bundle"         value="${bundle}"/>
                 <c:choose>
                    <c:when test="${phiz:getCountAllLoanKinds()  > 0}">
                        <tiles:put name="action" value="/loans/products/edit.do"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="onclick" value="alert('Для того чтобы добавить кредитный продукт, необходимо сначала создать вид кредитного продукта');"/>
                    </c:otherwise>
                 </c:choose>
                 </tiles:insert>
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
                <tiles:put name="label" value="label.filter.product.kind"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(kind)" styleId="filterKind" styleClass="filterSelect">
                        <html:option value="">Все</html:option>
                        <c:forEach var="kind" items="${phiz:getAllLoanKinds()}">
                            <html:option value="${kind.id}">${kind.name}</html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.filter.product.name"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(product)" styleId="filterProduct" styleClass="filterSelect">
                        <html:option value="">Все</html:option>
                        <c:forEach var="product" items="${phiz:getAllLoanProducts()}">
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

                var addUrl = "${phiz:calculateActionURL(pageContext,'/loans/products/edit')}";
                function editProduct(id)
                {
                    if(id == null)
                    {
                        checkIfOneItem("selectedIds");
                        if(!checkSelection("selectedIds", "Выберите кредитный продукт"))
                            return;
                        id = getRadioValue(document.getElementsByName("selectedIds"));
                    }
                    window.location = addUrl + "?id=" + id;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="loanProductsList"/>
                <!-- кнопки -->
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditLoanProductOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="onclick"        value="editProduct()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveLoanProductOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.product.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите кредитный продукт');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText"    value="Удалить выбранный кредитный продукт?"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="PublishLoanProductOperation">
                        <tiles:put name="commandKey"     value="button.publish"/>
                        <tiles:put name="commandHelpKey" value="button.publish"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите кредитный продукт');
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="PublishLoanProductOperation">
                        <tiles:put name="commandKey"     value="button.unpublish"/>
                        <tiles:put name="commandHelpKey" value="button.unpublish"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите кредитный продукт');
                            }
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <!-- кредиты -->
                <tiles:put name="grid">
                    <div class="gridDiv"><c:forEach var="entry"
                                                                                 items="${form.productsByKind}">
                        <h2 style="padding-left:12px;"><c:out value="${entry.key.name}"/></h2>

                        <div class="kredInfo" style="padding-left:40px;">
                            <c:forEach var="product" items="${entry.value}">
                                <c:set var="include" value="0"/>
                                <div>
                                    <input class="loanRadio" type="radio" name="selectedIds" value="${product.id}"/>
                                    <span class="bold"><c:out value="${product.name}"/></span>
                                    (${product.needSurety ? 'с обеспечением' : 'без обеспечения'})
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
                                <div>
                                    <span class="bold"><c:out value="Доступен для ТБ:"/></span>
                                    <c:set var="isFirst" value="true"/>
                                    <c:forEach var="terbank" items="${product.terbanks}">
                                        <c:if test="${!isFirst}">,</c:if>
                                        <c:out value="${terbank.name}"/>
                                        <c:set var="isFirst" value="false"/>
                                    </c:forEach>
                                </div>
                                <div>
                                    <span class="bold"><bean:message key="label.product.duration" bundle="${bundle}"/>:</span>
                                    <c:if test="${product.minDuration.duration != 0}">
                                        ${(product.minDuration.string2)}
                                    </c:if>
                                    <c:if test="${product.maxDuration.duration != 0}">
                                        ${(product.maxDuration.string2)}
                                        <c:if test="${product.maxDurationInclude == false}">*<c:set
                                                var="include" value="1"/></c:if>
                                    </c:if>
                                </div>
                                <div>
                                    <c:if test="${product.needInitialInstalment}">
                                        <span class="bold"><bean:message key="label.product.initial.instalment"
                                                      bundle="${bundle}"/>:</span>
                                        <c:if test="${product.minInitialInstalment != null}">
                                            <bean:message key="label.product.from" bundle="${bundle}"/>
                                            <c:out value="${product.minInitialInstalment}"/>
                                            <bean:message key="label.product.percent" bundle="${bundle}"/>
                                        </c:if>
                                        <c:if test="${product.maxInitialInstalment != null}">
                                            <bean:message key="label.product.to" bundle="${bundle}"/>
                                            <c:out value="${product.maxInitialInstalment}"/>
                                            <bean:message key="label.product.percent" bundle="${bundle}"/>
                                            <c:if test="${product.maxInitialInstalmentInclude == false}">*<c:set
                                                    var="include" value="1"/></c:if>
                                        </c:if>
                                    </c:if>
                                </div>
                                <div>
                                    <span class="bold"><bean:message key="label.product.surety"
                                                  bundle="${bundle}"/>:</span> ${product.needSurety ? '' : 'не'}
                                    требуется
                                </div>
                                <div>
                                    <c:if test="${not empty product.conditions}">
                                        <table class="depositProductInfo">
                                            <tr>
                                                <th><bean:message key="label.product.amount"
                                                                  bundle="${bundle}"/></th>
                                                <th><bean:message key="label.product.percent.rate"
                                                                  bundle="${bundle}"/> (<bean:message
                                                        key="label.product.percent" bundle="${bundle}"/>)
                                                </th>
                                            </tr>
                                            <c:forEach var="condition" items="${product.conditions}">
                                                <tr>
                                                    <td>
                                                        <c:if test="${condition.minAmount != null}">
                                                            <bean:message key="label.product.from"
                                                                          bundle="${bundle}"/>
                                                            <c:out value="${phiz:formatAmountISO(condition.minAmount)}"/>
                                                        </c:if>
                                                        <c:choose>
                                                            <c:when test="${condition.amountType == 'CURRENCY' and condition.maxAmount != null}">
                                                                <bean:message key="label.product.to"
                                                                              bundle="${bundle}"/>
                                                                <c:out value="${phiz:formatAmountISO(condition.maxAmount)}"/>
                                                                <c:if test="${condition.maxAmountInclude == false}">*<c:set
                                                                        var="include" value="1"/></c:if>
                                                            </c:when>
                                                            <c:when test="${condition.amountType == 'PERCENT' and condition.maxAmountPercent != null}">
                                                                <bean:message key="label.product.to"
                                                                              bundle="${bundle}"/>
                                                                <c:out value="${condition.maxAmountPercent}"/>
                                                                <bean:message
                                                                        key="label.product.percent.from.cost"
                                                                        bundle="${bundle}"/>
                                                                <c:if test="${condition.maxAmountInclude == false}">*<c:set
                                                                        var="include" value="1"/></c:if>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${condition.minInterestRate != null and condition.maxInterestRate != null}">
                                                                <c:out value="${condition.minInterestRate}-${condition.maxInterestRate}"/>
                                                                <c:if test="${condition.maxInterestRateInclude == false}">*<c:set
                                                                        var="include" value="1"/></c:if>
                                                            </c:when>
                                                            <c:when test="${condition.minInterestRate != null and condition.maxInterestRate == null}">
                                                                <bean:message key="label.product.from"
                                                                              bundle="${bundle}"/>
                                                                <c:out value="${condition.minInterestRate}"/>
                                                            </c:when>
                                                            <c:when test="${condition.minInterestRate == null and condition.maxInterestRate != null}">
                                                                <bean:message key="label.product.to"
                                                                              bundle="${bundle}"/>
                                                                <c:out value="${condition.maxInterestRate}"/>
                                                                <c:if test="${condition.maxInterestRateInclude == false}">*<c:set
                                                                        var="include" value="1"/></c:if>
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </c:if>
                                </div>
                                <c:if test="${include eq 1}">
                                    <div class="loanTitle include">* - не включительно</div>
                                </c:if>
                                <c:if test="${not empty product.additionalTerms}">
                                    <div>
                                        <a href="#" onclick="showDiv(${product.id});"><bean:message
                                                key="label.product.additional.terms" bundle="${bundle}"/></a>

                                        <div id="terms_${product.id}" style="display:none">
                                            <c:out value="${product.additionalTerms}"/>
                                        </div>
                                    </div>
                                </c:if>
                                <br/>
                            </c:forEach>
                        </div>
                    </c:forEach></div>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.productsByKind}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного кредитного продукта!"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>