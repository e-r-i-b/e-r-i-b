<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<c:set var="trueId" value="${id}"/>
<c:if test="${type == 'shopOrder'}">
    <c:set var="trueId" value="${externalId}"></c:set>
</c:if>
<div class="il-main">
    <div class="il-invoice" id="${type}${trueId}">
        <div class="il-type" style="display:none">${type}</div>
        <div class="il-left floatLeft" onclick="${onclick}">
            <c:choose>
                <c:when test="${not empty imageId}">
                    <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                    <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="imagePath" value="${skinUrl}/images/IQWave-other.jpg"/>
                </c:otherwise>
            </c:choose>
            <img class="il-providerIcon" src="${imagePath}"/>

            <div class="il-invoiceIcon"></div>
        </div>
        <div class="il-center floatLeft" onclick="${onclick}">
            <div class="il-centerTop floatLeft">
                <div class="il-invoiceName float relative">
                    <span><nobr>${name}</nobr></span>

                    <div class="hideText"></div>
                </div>
                <div class="entityName relative">
                    <c:if test="${not empty entityName}">
                        <div class="il-brownPoint floatLeft"></div>
                        <div class="il-entityName floatLeft">${entityName}</div>
                    </c:if>
                    <c:if test="${type == 'reminder'}">
                        <div class="il-stateIcon floatLeft">
                            <div class="reminder-icon"></div>
                        </div>
                    </c:if>
                    <div class="il-stateIcon floatLeft" id="invoice-newIcon_${type}${trueId}">
                        <c:if test="${isNew}">
                            <div class="il-newIcon"></div>
                        </c:if>
                        <c:if test="${state == 'DELAYED'}">
                            <div class="il-delayedIcon"></div>
                        </c:if>
                    </div>
                </div>
                <div class="il-stateIcon"></div>
            </div>
            <div class="clear"></div>
            <div class="il-centerBottom floatLeft">
                <div class="hideText"></div>
                <div class="il-providerName floatLeft">${providerName} </div>
                <div class="il-keyInfo floatLeft">
                    <div class="il-keyName floatLeft">${keyName}: ${keyValue}</div>
                </div>
            </div>
        </div>
        <div class="il-right floatRight">
            <div class="il-right-masked">
                <div class="il-right-style">
                    <div class="il-sum float invoiceLeftBtn" onclick="${onclick}">
                        <div class="il-sumTextWrap invoiceCenterBtn">
                            <span class="il-payText floatLeft">
                                Оплатить
                            </span>
                            <%-- имеем сумму к оплате со знаками после запятой (т.е. с копейками), избавляемся от них таким образом--%>
                            <span class="il-sumValue">${phiz:formatDecimalToAmountRound(sum, true )}</span>
                            <span class="il-roubles">руб.</span>
                        </div>
                    </div>
                    <div class="il-hiddenListButton float invoiceRightBtn">
                        <div class="invoiceCenterBtn">
                            <div class="invoiceSeparate">
                                <div class="il-hiddenListIcon"></div>
                            </div>
                        </div>
                        <ul class="il-hiddenList">
                            <input type="hidden" name="field(chooseDelayDateInvoice)" id="chooseDelayDate${type}${trueId}"/>
                            <li class="il-delayButton">
                                <span id="delay${type}${trueId}"><bean:message key="button.delay" bundle="basketBundle"/></span>
                            </li>
                            <div class="il-fakeDiv"></div>
                            <c:if test="${accessRecoverAutoSub}">
                                <li class="il-recoverAutoSubButton">
                                    <span id="recoverAutoSub${type}${trueId}"><bean:message key="button.recover.autosub" bundle="basketBundle"/></span>
                                </li>
                                <div class="il-fakeDiv"></div>
                            </c:if>

                            <li class="il-deleteButton">
                                <c:choose>
                                    <c:when test="${appType == 'client'}">
                                        <tiles:insert definition="confirmationButton" flush="false">
                                            <tiles:put name="winId" value="deleteConfirmation${type}${trueId}"/>
                                            <tiles:put name="title"><bean:message key="message.confirm.delete.title" bundle="basketBundle"/></tiles:put>
                                            <tiles:put name="currentBundle" value="basketBundle"/>
                                            <tiles:put name="confirmCommandKey" value="button.removeInvoice"/>
                                            <tiles:put name="buttonViewType" value="listDeleteInvoiceButton"/>
                                            <tiles:put name="message"><bean:message key="message.confirm.delete" bundle="basketBundle"/></tiles:put>
                                            <tiles:put name="afterConfirmFunction" value="removeInvoice('${trueId}', '${type}')"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:otherwise>
                                        <bean:message key="button.removeInvoice" bundle="basketBundle"/>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <c:if test="${appType == 'client'}">
            <script type="text/javascript">
                $(function ()
                {
                    var chooseDateElem = $("#delay${type}${trueId}").closest("li").get(0);
                    $("#chooseDelayDate${type}${trueId}")
                            .datePicker({displayClose: true, altField: chooseDateElem, dateFormat: 'dd.mm.yyyy', showYearMonth: true, startDate: new Date().asString('dd.mm.yyyy')})
                            .unbind('change')
                            .change(function ()
                            {
                                delayInvoice('${trueId}', '${type}');
                            });

                    <c:set var="operationUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/subscription/claim')}"/>
                    $("#recoverAutoSub${type}${trueId}").closest("li").click(function ()
                    {
                        ajaxQuery("invoiceId=${trueId}&operation=button.recoverAutoSub", "${operationUrl}", function (data)
                        {
                        });
                    });
                });
            </script>
        </c:if>

    </div>
</div>