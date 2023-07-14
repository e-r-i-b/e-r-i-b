<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/dictionaries/facilitator/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="resOnPage" value="${form.paginationSize}"/>
    <c:set var="bundle" value="providerBundle"/>

    <tiles:insert definition="providersMain">
        <tiles:put name="submenu" type="string" value="Facilitators"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="${bundle}" key="facilitators.listPage.title"/></tiles:put>
        <tiles:put name="data" type="string">

            <script type="text/javascript">
                var endpointProviderPropertiesString = '';

                //Формируем строку изменённых свойств КПУ
                function addPropertyToSaveString(checkbox)
                {
                    var stringToPaste = checkbox.id + '-';
                    if (endpointProviderPropertiesString.indexOf(checkbox.id) == -1)
                        endpointProviderPropertiesString += (isDataChanged() ? ';' : '') + stringToPaste + (checkbox.checked ? '1' : '0');
                    else
                    {
                        endpointProviderPropertiesString = endpointProviderPropertiesString.replace(stringToPaste + (checkbox.checked ? '0' : '1'), '');
                        if (/^;/.test(endpointProviderPropertiesString) || /;$/.test(endpointProviderPropertiesString))
                        {
                            endpointProviderPropertiesString = endpointProviderPropertiesString.replace(/^;/, '');
                            endpointProviderPropertiesString = endpointProviderPropertiesString.replace(/;$/, '');
                        }
                    }
                }

                //Дизейблим/активируем чекбоксы свойств MobileCheckout у КПУ при изменении свойства фасилитатора
                function disableMobileCheckoutProperties()
                {
                    $('[name=endpointMobileCheckout]').each(function(){this.disabled = !ensureElementByName('facilitatorMobileCheckout').checked;});
                }

                //Дизейблим/активируем чекбоксы свойств EInvoicing у КПУ при изменении свойства фасилитатора
                function disableEInvoicingProperties()
                {
                    var eInvoicingIsUnchecked = !ensureElementByName('facilitatorEInvoicing').checked;

                    //дизейблим/активируем чекбоксы EInvoicing у всех КПУ на странице
                    $('[name=endpointEInvoicing]').each(function(){this.disabled = eInvoicingIsUnchecked;});
                    //если MobileCheckout у фасилитатора включен, то дизейблим/активируем чекбоксы MobileCheckout у всех КПУ на странице
                    if (ensureElementByName('facilitatorMobileCheckout').checked)
                        $('[name=endpointMobileCheckout]').each(function(){this.disabled = eInvoicingIsUnchecked;});
                    //дизейблим/активируем чекбокс MobileCheckout у фасилитатора
                    ensureElementByName('facilitatorMobileCheckout').disabled = eInvoicingIsUnchecked;
                }

                //Дизейблим/активируем чекбоксы свойств MBCheck у КПУ при изменении свойства фасилитатора
                function disableMBCheckProperties()
                {
                    $('[name=endpointMBCheck]').each(function(){this.disabled = !ensureElementByName('facilitatorMBCheck').checked;});
                }

                //Сохраняем изменённые свойства чекбоксов
                function saveEndpointProperties()
                {
                    if (isDataChanged())
                    {
                        var url = "${phiz:calculateActionURL(pageContext, '/private/dictionaries/facilitator/edit')}";
                        var ids = "id=${form.id}" <c:if test="${form.idEndpointProvider != 0}"> + "&idEndpointProvider=${form.idEndpointProvider}" </c:if>;
                        var param = "operation=button.save&" + ids + "&propertyString=" + endpointProviderPropertiesString;
                        ajaxQuery(param, url, function(data){window.location =  url+"?"+ids;}, null, false);
                    }
                }

                //Изменены ли свойства КПУ
                function isDataChanged()
                {
                    return endpointProviderPropertiesString.length > 0;
                }

                //Поаторная инициализация полей после сохранения (переопределение)
                function reinitField()
                {}

                //Вызов сохранения свойтв КПУ (переопределение)
                function findNavigationButton()
                {
                    return createClientButton("button.send", "button.send", function(){saveEndpointProperties();});
                }

                //Сохранение свойтв КПУ перед пагинацией
                function saveAndFilter(callback)
                {
                    if (!isDataChanged()){
                        callback();
                        return;
                    }
                    if (!confirm("<bean:message bundle="${bundle}" key="facilitators.edit.exitWithoutChangesMessage"/>"))
                    {
                        //при нажатии кнопки "Отмена" необходимо оставаться на текущей странице и не сохранять изменения
                        clearLoadMessage();
                        return;
                    }
                    addElementToForm('propertyString', endpointProviderPropertiesString);
                    var url = "${phiz:calculateActionURL(pageContext, '/private/dictionaries/facilitator/edit')}";
                    var param = "operation=button.save&" + "id=${form.id}" <c:if test="${form.idEndpointProvider != 0}"> + "&idEndpointProvider=${form.idEndpointProvider}" </c:if> + "&propertyString=" + endpointProviderPropertiesString;
                    ajaxQuery(param, url, function(data){callback();}, null, false);
                }

                //Сохранение свойтв фасилитатора
                function saveFacilitatorProperties(checkbox)
                {
                    if (!confirm("<bean:message bundle="${bundle}" key="facilitators.edit.confirmMessage"/>"))
                    {
                        //при нажатии кнопки "Отмена" необходимо откатить изменение
                        checkbox.checked = !checkbox.checked;
                        clearLoadMessage();
                        return false;
                    }
                    var facilitatorPropertiesString = checkbox.id + '-' + (checkbox.checked ? '1' : '0');
                    var url = "${phiz:calculateActionURL(pageContext, '/private/dictionaries/facilitator/edit')}";
                    var param = "operation=button.save&id=${form.id}&facilitatorProperty=" + facilitatorPropertiesString;
                    ajaxQuery(param, url, function(data){}, null, false);
                    activateEndpointProperties(checkbox);
                    return true;
                }

                //Активация свойств всех КПУ на странице при включении свойства фасилитатора
                function activateEndpointProperties(checkbox)
                {
                    var isChecked = checkbox.checked;
                    var checkboxName = checkbox.name.replace('facilitator', 'endpoint');
                    if (isChecked)
                        $('[name=' + checkboxName + ']').each(function(){this.checked = true;});
                }

                doOnLoad(function()
                {
                    disableMobileCheckoutProperties();
                    disableEInvoicingProperties();
                    disableMBCheckProperties();
                });
            </script>

            <div>
                <a class="showFilterButton" href="${phiz:calculateActionURL(pageContext, '/private/dictionaries/facilitator/list.do')}"><bean:message bundle="${bundle}" key="facilitators.edit.label.search"/></a>
            </div>


            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="endpointProviderList"/>
                <tiles:put name="text"><bean:message bundle="${bundle}" key="facilitators.edit.table.title"/></tiles:put>

                <c:set var="sizeResult" value="${fn:length(form.data)}"/>
                <c:set var="pageSize" value="${form.paginationSize}"/>
                <c:set var="pageOffset" value="${form.paginationOffset}"/>
                <c:set var="pageItems">
                    <c:choose>
                        <c:when test="${sizeResult < pageSize}">
                            ${sizeResult}
                        </c:when>
                        <c:otherwise>
                            ${pageSize}
                        </c:otherwise>
                    </c:choose>
                </c:set>

                <c:if test="${sizeResult != 0}">
                    <tiles:put name="grid">

                        <table cellspacing="0" cellpadding="0" class="depositProductInfo" align="CENTER" width="100%">
                             <%--Шапка таблицы--%>
                            <tr>
                                <td rowspan="2">
                                    ${form.facilitator.name}<br/>
                                    (<bean:message bundle="${bundle}" key="facilitators.edit.label.update"/>: <c:out value="${phiz:formatDateToStringOnPattern(form.facilitatorProperties.updateDate, 'dd.MM.yyyy HH:mm')}"/>)
                                </td>
                                <td rowspan="2">
                                    <bean:message bundle="${bundle}" key="facilitators.edit.endpointProviderURL"/>
                                </td>
                                <td rowspan="2">
                                    <bean:message bundle="${bundle}" key="facilitators.edit.endpointProviderINN"/>
                                </td>
                                <td class="alignCell" colspan="2">
                                    <bean:message bundle="${bundle}" key="facilitators.edit.availability"/>
                                </td>
                                <td class="alignCell" rowspan="2">
                                    <bean:message bundle="${bundle}" key="facilitators.edit.mbCheck"/><br/>
                                    <input type="checkbox" id="fmbc" name="facilitatorMBCheck" onclick="if (saveFacilitatorProperties(this)) {disableMBCheckProperties()}" <c:if test="${form.facilitatorProperties.mbCheckEnabled}">checked</c:if>/>
                                </td>
                            </tr>
                            <tr>
                                <td class="alignCell">
                                    <bean:message bundle="${bundle}" key="facilitators.edit.availability.EInvoicing"/><br/>
                                    <input type="checkbox" id="feinv" name="facilitatorEInvoicing" onclick="if (saveFacilitatorProperties(this)) {disableEInvoicingProperties()}" <c:if test="${form.facilitator.state eq 'ACTIVE'}">checked</c:if>/>
                                </td>
                                <td class="alignCell" >
                                    <bean:message bundle="${bundle}" key="facilitators.edit.availability.mobileCheckout"/><br/>
                                    <input type="checkbox" id="fmco" name="facilitatorMobileCheckout" onclick="if (saveFacilitatorProperties(this)) {disableMobileCheckoutProperties()}" <c:if test="${form.facilitator.availableMobileCheckout}">checked</c:if>/>
                                </td>
                            </tr>

                            <c:forEach var="item" items="${form.data}" varStatus="stat">
                                <c:if test="${stat.count le pageSize}">
                                    <tr>
                                        <td>${item.name}</td>
                                        <td>${item.url}</td>
                                        <td>${item.inn}</td>
                                        <td class="alignCell">
                                            <input type="checkbox" id="einv-${item.id}" name="endpointEInvoicing" onclick="addPropertyToSaveString(this)" <c:if test="${item.einvoiceEnabled}">checked</c:if>/>
                                        </td>
                                        <td class="alignCell">
                                            <input type="checkbox" id="mco-${item.id}" name="endpointMobileCheckout" onclick="addPropertyToSaveString(this)" <c:if test="${item.mobileCheckoutEnabled}">checked</c:if>/>
                                        </td>
                                        <td class="alignCell">
                                            <input type="checkbox" id="mbc-${item.id}" name="endpointMBCheck" onclick="addPropertyToSaveString(this)" <c:if test="${item.mbCheckEnabled}">checked</c:if>/>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>

                        <%--Пагинация--%>
                        <c:set var="minPaginationSize" value="50"/>
                        <c:set var="mediumPaginationSize" value="100"/>
                        <c:set var="maxPaginationSize" value="150"/>
                        <c:if test="${pageOffset > 0 || sizeResult > minPaginationSize}">
                            <table cellspacing="0" cellpadding="0" class="tblNumRec">
                                <tr><td colspan="6">&nbsp;</td></tr>
                                <tr>
                                    <td style="width:50%;">&nbsp;</td>
                                    <td nowrap="nowrap" style="border:0;">
                                        <c:choose>
                                            <c:when test="${pageOffset>0}">
                                                <a href="#" onclick="saveAndFilter(function(){
                                                        addElementToForm('paginationOffset','${pageOffset}');
                                                        setElement('paginationOffset', '${pageOffset-pageSize}');
                                                        addElementToForm('paginationSize', '${pageSize}');
                                                        callOperation(event,'button.filter'); })">
                                                    <div class="activePaginLeftArrow"></div>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="inactivePaginLeftArrow"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td nowrap="nowrap" style="padding: 5px;">
                                    <span class="tblNumRecIns">
                                        ${pageOffset + 1}
                                        &nbsp;-&nbsp;
                                        ${pageOffset + pageItems}
                                    </span>
                                    </td>
                                    <td nowrap="nowrap" style="border:0;">
                                        <c:choose>
                                            <c:when test="${pageSize < sizeResult}">
                                                <a href="#" onclick="saveAndFilter(function(){
                                                        addElementToForm('paginationOffset','${pageOffset}');
                                                        setElement('paginationOffset', '${pageOffset+pageSize}');
                                                        addElementToForm('paginationSize', '${pageSize}');
                                                        callOperation(event,'button.filter'); })">
                                                    <div class="activePaginRightArrow"></div>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="inactivePaginRightArrow"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td nowrap="nowrap" align="right" width="100%">
                                        <div>
                                            <div class="floatRight">
                                                <div class="float paginationItemsTitle">Показывать по:</div>
                                                <input type="hidden" value="${resOnPage}" name="resOnPage">
                                                <c:choose>
                                                    <c:when test="${resOnPage == minPaginationSize}">
                                                        <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">${minPaginationSize}</span></div></div>
                                                        <div class="paginationSize float"><a onclick="saveAndFilter(function(){addElementToForm('paginationSize', '${mediumPaginationSize}'); callOperation(event,'button.filter');})" href="#" style="padding-right:2px;">${mediumPaginationSize}</a></div>
                                                        <div class="paginationSize float"><a onclick="saveAndFilter(function(){addElementToForm('paginationSize', '${maxPaginationSize}'); callOperation(event,'button.filter');})" href="#" style="padding-right:2px;">${maxPaginationSize}</a></div>
                                                    </c:when>
                                                    <c:when test="${resOnPage == mediumPaginationSize}">
                                                        <div class="paginationSize float"><a onclick="saveAndFilter(function(){addElementToForm('paginationSize', '${minPaginationSize}'); callOperation(event,'button.filter');})" href="#" style="padding-right:2px;">${minPaginationSize}</a></div>
                                                        <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">${mediumPaginationSize}</span></div></div>
                                                        <div class="paginationSize float"><a onclick="saveAndFilter(function(){addElementToForm('paginationSize', '${maxPaginationSize}'); callOperation(event,'button.filter');})" href="#" style="padding-right:2px;">${maxPaginationSize}</a></div>
                                                    </c:when>
                                                    <c:when test="${resOnPage == maxPaginationSize}">
                                                        <div class="paginationSize float"><a onclick="saveAndFilter(function(){addElementToForm('paginationSize', '${minPaginationSize}'); callOperation(event,'button.filter');})" href="#" style="padding-right:2px;">${minPaginationSize}</a></div>
                                                        <div class="paginationSize float"><a onclick="saveAndFilter(function(){addElementToForm('paginationSize', '${mediumPaginationSize}'); callOperation(event,'button.filter');})" href="#" style="padding-right:2px;">${mediumPaginationSize}</a></div>
                                                        <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">${maxPaginationSize}</span></div></div>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </c:if>

                        <tiles:put name="isEmpty" value="${empty form.data}"/>
                    </tiles:put>
                </c:if>
                <tiles:put name="emptyMessage">
                    <bean:message bundle="${bundle}" key="facilitators.emptyProviders.message"/>
                </tiles:put>
            </tiles:insert>
            <div class="floatRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="button.save"/>
                    <tiles:put name="commandHelpKey"    value="button.save.help"/>
                    <tiles:put name="bundle"            value="configureBundle"/>
                    <tiles:put name="viewType"          value="buttonGreen"/>
                    <tiles:put name="onclick"           value="saveEndpointProperties()"/>
                    <tiles:put name="isDefault"         value="true"/>
                </tiles:insert>
            </div>

        </tiles:put>
    </tiles:insert>
</html:form>
