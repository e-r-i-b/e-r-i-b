<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="document" value="${form.document}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:hidden property="field(accountingEntityId)" name="form"/>
<html:hidden property="autoId" name="form" />
<div>
    <div style="float: left;" class="size24 middleTitle" id="nameProvider"></div>
    <div style="float: right;">
        <c:choose>
            <c:when test="${not empty form.providerImageId}">
                <c:set var="imageData" value="${phiz:getImageById(form.providerImageId)}"/>
                <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
            </c:when>
            <c:otherwise>
                <c:set var="image" value="${imagePath}/defaultProviderIcon.jpg" />
            </c:otherwise>
        </c:choose>
        <img class="icon" src="${image}" alt=""/></div>
    <div class="clear"></div>
</div>

<div class="fullWidth">
    <div class="form-row">
        <div class="paymentLabel">
            <span class="paymentTextLabel">¬ыберите услугу</span><span id="asterisk_payment" class="asterisk">*</span>:
        </div>
        <div class="paymentValue">
            <c:choose>
                <c:when test="${empty form.providers}">
                    <span style="color: red"><c:out value="${noProviderMessage}"/></span>
                </c:when>
                <c:otherwise>
                    <html:select name="form" property="recipient" onchange="pf.showProvider(this.value);$('#phoneFieldParam').val('');" styleId="recipientId" disabled="${fieldDisabled}">
                        <c:forEach items="${form.providers}" var="provider" >
                            <html:option value="${provider.id}"><c:out value="${provider.nameService}"/></html:option>
                        </c:forEach>
                    </html:select>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="clear"></div>
    </div>
</div>

<div id="paymentForm" style="display: none;" class="fullWidth"></div>

<hr style="border-style: none none dotted"/>
<tiles:insert page="/WEB-INF/jsp-sbrf/payments/fromResourceField.jsp" flush="false"/>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">
        <bean:message key="invoice.subscription.name" bundle="basketBundle"/><span class="asterisk">*</span>:
    </tiles:put>
    <tiles:put name="data" type="string">
        <html:text property="field(autoSubName)" name="form"/>
    </tiles:put>
    <tiles:put name="description">
        <bean:message key="invoice.subscription.name.description" bundle="basketBundle"/>
    </tiles:put>
</tiles:insert>

<div class="invoice-sub-params">
    <div class="paymentLabel"></div>
    <div class="paymentValue">
        <div class="bold">
            <bean:message key="invoice.subscription.params.description" bundle="basketBundle"/>
        </div>
        <div class="invoiceSubParameters">
            <div class="linkListOfOperation" onclick="showOrHideOperationBlock($('#autoSubEventTypeMenu .listOfOperation').get(0));" style="position: relative; float: left">
                <span id="visibleAutoSubEventType"></span>
                <html:hidden property="field(autoSubEventType)" name="form" styleId="autoSubEventType"/>
                <div id="autoSubEventTypeMenu">
                    <tiles:insert definition="listOfOperation" flush="false">
                        <tiles:put name="productOperation" value="true"/>
                        <tiles:put name="isLock" value="false"/>
                        <tiles:put name="isShowOperationButton" value="false"/>
                        <tiles:putList name="items">
                            <tiles:add>
                                <a href="#" onclick="setElement('field(autoSubEventType)', 'ONCE_IN_WEEK');updateDescAutoPayParameters();return false;">≈женедельно</a>
                            </tiles:add>
                            <tiles:add>
                                <a href="#" onclick="setElement('field(autoSubEventType)', 'ONCE_IN_MONTH');updateDescAutoPayParameters();return false;">≈жемес€чно</a>
                            </tiles:add>
                            <tiles:add>
                                <a href="#" onclick="setElement('field(autoSubEventType)', 'ONCE_IN_QUARTER');updateDescAutoPayParameters();return false;">≈жеквартально</a>
                            </tiles:add>
                        </tiles:putList>
                    </tiles:insert>
                </div>
                <c:set var="defaultDate" value="${phiz:getNearDateByMonthWithoutCurrent(10)}"/>
                <script type="text/javascript">

                    var daysOfWeekDesc = ["понедельникам", "вторникам", "средам", "четвергам", "п€тницам", "субботам", "воскресень€м"];

                    function updateDescAutoPayParameters()
                    {
                        var visibleNextPayDate = $('#visibleNextPayDateInvoice');
                        var chooseDateInvoice = $("#chooseDateInvoice");

                        var visibleEventType = $('#visibleAutoSubEventType');
                        var eventType = $("#autoSubEventType");

                        if(isEmpty(eventType.val()) || isEmpty(chooseDateInvoice.val()))
                        {
                            eventType.val('ONCE_IN_MONTH');
                            chooseDateInvoice.val('<bean:write name="defaultDate" property="time" format="dd.MM.yyyy"/>');
                        }

                        var nextPayDate = new Str2Date(chooseDateInvoice.val());
                        switch(eventType.val())
                        {
                            case 'ONCE_IN_WEEK':
                            {
                                visibleEventType.text('≈женедельно');
                                visibleNextPayDate.html("по " + daysOfWeekDesc[(nextPayDate.getDay()+6)%7]);
                                break;
                            }
                            case 'ONCE_IN_MONTH':
                            {
                                visibleEventType.text('≈жемес€чно,');
                                visibleNextPayDate.text(nextPayDate.getDate() + "-го числа");
                                break;
                            }
                            case 'ONCE_IN_QUARTER':
                            {
                                visibleEventType.text('≈жеквартально,');
                                visibleNextPayDate.text(nextPayDate.getDate() + "-го числа " + (nextPayDate.getMonth()%3 + 1) + "-го мес€ца");
                                break;
                            }
                        }
                    }

                    $(function(){
                        updateDescAutoPayParameters();

                        var chooseDateElem = $("#visibleNextPayDateInvoice").closest("div").get(0);
                        $("#chooseDateInvoice")
                            .datePicker({displayClose: true, altField: chooseDateElem, dateFormat: 'dd.mm.yyyy' })
                            .change(function(){
                                updateDescAutoPayParameters();
                            });
                    });
                </script>
            </div>
            <div style="float: left; margin-left: 5px;">
                 &nbsp;<span id="visibleNextPayDateInvoice"></span>
            </div>
            <html:hidden property="field(chooseDateInvoice)" name="form" styleId="chooseDateInvoice"/>
        </div>
    </div>
    <div class="clear"></div>
    <tiles:insert page="/WEB-INF/jsp-sbrf/basket/invoiceSubscription/profileDocumentsWin.jsp" flush="false"/>
</div>

