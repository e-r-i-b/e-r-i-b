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

<c:choose>
    <c:when test="${state == 'AUTO_SUBSCRIPTION'}">
        <c:set var="entityType" value="autoPayment"/>
    </c:when>
    <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
        <c:set var="entityType" value="document"/>
    </c:when>
    <c:otherwise>
        <c:set var="entityType" value="invoiceSubscription"/>
    </c:otherwise>
</c:choose>

<c:set var="invoiceSubscriptionAdditionalClassName" value="is-typeOther"/>
<c:if test="${(state == 'ACTIVE' || state == 'AUTO_SUBSCRIPTION' || state == 'INACTIVE' || (state == 'WAIT' and (nextState == 'DELETED' || nextState == 'STOPPED'))) && (empty errorType)}">
    <c:set var="invoiceSubscriptionAdditionalClassName" value="is-typeActive"/>
</c:if>

<li id="invoiceSubscription${id}" class="is-invoiceSubscription ${type} ${invoiceSubscriptionAdditionalClassName} ${entityType}Type">
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="${invoiceSubscriptionAdditionalClassName}"/>
        <tiles:put name="data">
            <c:choose>
                <c:when test="${state == 'DRAFT'}">
                    <c:set var="onAreaClick" value="autoCreate('${id}')"/>
                </c:when>
                <c:otherwise>
                    <c:set var="onAreaClick" value="viewSubscription('${id}','${entityType}', '${documentStatus}')"/>
                </c:otherwise>
            </c:choose>

            <div class="is-top" onclick="${onAreaClick}">
                <c:if test="${not empty topTitle}">
                    <div class="is-additionalTop">
                        ${topTitle}
                    </div>
                </c:if>
                <c:if test="${empty topTitle}">
                    <div class="is-additionalTop" style="display:none">
                        <bean:message key="invoice.subscription.infact.title" bundle="basketBundle"/>
                    </div>
                </c:if>
            </div>

            <div class="is-main relative">
                <div class="fakeLayer">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="fakeLayerColors"/>
                        <tiles:put name="data">

                        </tiles:put>
                    </tiles:insert>
                </div>
                <c:choose>
                    <c:when test="${state == 'WAIT'}">
                        <div class="is-statusWait">
                            <bean:message key="invoice.subscription.message.wait" bundle="basketBundle"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${state == 'STOPPED'}">
                            <div class="is-statusStopped">
                                <bean:message key="invoice.subscription.message.stopped" bundle="basketBundle"/>
                            </div>
                        </c:if>
                        <c:if test="${state != 'STOPPED' && not empty statusMessage}">
                            <div class="is-statusError">
                                ${statusMessage}
                            </div>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <c:if test="${state == 'STOPPED' || state == 'WAIT' || not empty statusMessage}">
                    <c:set var="errorClass" value="errorShift"/>
                </c:if>

                <div class="is-left floatLeft" onclick="${onAreaClick}">
                    <div class="is-holdingFlag floatLeft"></div>
                    <div class="is-nameProviderWrap floatLeft">
                        <div class="is-name">
                            ${name}
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>

                <div class="is-provider ${errorClass}">
                    <c:set var="imageId" value="${form.imageIds[id]}"/>
                    <c:choose>
                        <c:when test="${not empty imageId}">
                            <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                            <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="imagePath" value="${skinUrl}/images/IQWave-other.jpg"/>
                        </c:otherwise>
                    </c:choose>
                    <img class="is-providerIcon floatLeft" src="${imagePath}"/>
                </div>
                <div class="is-providerName floatLeft ${errorClass}">
                    ${provider}
                </div>

                <div class="is-center floatLeft" onclick="${onAreaClick}">
                    <c:set var="listSize" value="${phiz:size(form.requisites[id])}"/>
                    <c:if test="${listSize > 0}">
                        <c:set var="req" value="${form.requisites[id][0]}"/>
                        <div class="is-info">
                            <div class="is-info-name floatLeft">
                                ${req.name}:
                            </div>
                            <div class="is-info-value floatLeft">
                                ${phiz:getMaskedValue(req.mask, req.value)}
                            </div>
                            <div class="clear"></div>
                        </div>
                        <c:if test="${listSize > 1}">

                            <a class="text-gray is-showReqList">показать остальные поля</a>

                            <div class="is-reqHiddenList" style="display:none">
                                <c:forEach items="${form.requisites[id]}" var="req" varStatus="loop">
                                    <c:if test="${loop.index > 0}">
                                        <div class="is-info">
                                            <div class="is-info-name floatLeft">
                                                ${req.name}
                                            </div>
                                            <div class="is-info-value floatLeft">
                                                ${phiz:getMaskedValue(req.mask, req.value)}
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </c:if>
                                </c:forEach>
                                <a class="text-gray is-hideReqList">свернуть</a>
                            </div>
                        </c:if>
                    </c:if>
                </div>
                <div class="floatRight <c:if test="${(state == 'STOPPED') || (state != 'STOPPED' && not empty statusMessage)}"> buttonTop</c:if>" >
                    <div class="is-iconPlace floatRight" onclick="${onAreaClick}">
                        <c:if test="${state=='ACTIVE' || state == 'AUTO_SUBSCRIPTION' || state == 'INACTIVE'}">
                            <div class="is-editPencilIcon floatRight"></div>
                        </c:if>
                        <c:if test="${state=='AUTO' || state=='DRAFT'}">
                            <div class="is-closeIcon floatRight" onclick="removeAutoInvoiceSub(event, '${id}');"></div>
                        </c:if>
                    </div>
                    <div class="is-right floatRight">
                        <c:if test="${state == 'AUTO_SUBSCRIPTION'}">
                            <div class="is-autoSubscriptionBlock floatRight" onclick="${onAreaClick})">
                                <div class="is-autoSubscriptionText floatRight">
                                    <bean:message key="auto.subscription.block.title" bundle="basketBundle"/>
                                </div>
                                <div class="is-autoIcon floatRight"></div>
                            </div>
                        </c:if>
                        <c:if test="${state == 'ACTIVE'}">
                            <c:if test="${notPaid > 0 || delayed > 0}">
                                <div class="is-paymentsInfo floatLeft" onclick="${onAreaClick}">
                                    <div class="is-scrollIcon floatLeft"></div>
                                    <div class="floatRight">
                                        <c:if test="${notPaid > 0}">
                                            <div class="is-notPaidNumber">
                                                ${notPaid} не оплачен<c:if test="${notPaid % 10 != 1 || notPaid / 10 == 1}">о</c:if>
                                            </div>
                                        </c:if>
                                        <c:if test="${delayed > 0}">
                                            <div class="is-delayedNumber">
                                                ${delayed} отложен<c:if test="${delayed % 10 != 1 || delayed / 10 == 1}">о</c:if>
                                            </div>
                                            <div class="is-delayDate">
                                                ${date}
                                            </div>

                                        </c:if>

                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                        <c:if test="${state == 'STOPPED'}">
                            <div class="floatRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.restart"/>
                                    <tiles:put name="commandHelpKey" value="button.restart.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="grayBtn"/>
                                    <tiles:put name="textIconClass" value="is-continueIcon"/>
                                    <tiles:put name="onclick" value="recoverySubscription('${id}');"/>
                                </tiles:insert>
                            </div>
                        </c:if>
                        <c:if test="${state == 'INACTIVE' || state == 'WAIT'}">
                            <div id="error-buttons-${id}" class="floatRight">
                                <c:if test="${errorType == 'NOT_CRITICAL'}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.update"/>
                                        <tiles:put name="commandHelpKey" value="button.update.help"/>
                                        <tiles:put name="bundle" value="paymentsBundle"/>
                                        <tiles:put name="viewType" value="grayBtn"/>
                                        <tiles:put name="textIconClass" value="is-refreshIcon"/>
                                        <tiles:put name="onclick" value="if(resolveInvoiceSubError) resolveInvoiceSubError(${id}, 'button.update');"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${errorType == 'CRITICAL'}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.delete"/>
                                        <tiles:put name="commandHelpKey" value="button.delete.help"/>
                                        <tiles:put name="bundle" value="paymentsBundle"/>
                                        <tiles:put name="viewType" value="grayBtn is-refreshTextStyle is-stopped"/>
                                        <tiles:put name="textIconClass" value="is-deleteIcon"/>
                                        <tiles:put name="onclick" value="if(resolveInvoiceSubError) resolveInvoiceSubError(${id}, 'button.remove');"/>
                                    </tiles:insert>
                                </c:if>
                            </div>
                        </c:if>
                        <c:if test="${state == 'AUTO' || state=='DRAFT'}">
                            <div class="floatRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.autoSearch"/>
                                    <tiles:put name="commandHelpKey" value="button.autoSearch"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="grayBtn is-refreshTextStyle"/>
                                    <tiles:put name="onclick" value="autoCreate('${id}');"/>
                                </tiles:insert>
                            </div>
                        </c:if>
                        <c:if test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                            <div class="floatRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.update"/>
                                    <tiles:put name="commandHelpKey" value="button.update.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="grayBtn"/>
                                    <tiles:put name="textIconClass" value="is-refreshIcon"/>
                                    <tiles:put name="onclick" value="viewSubscription('${id}','${entityType}', '${documentStatus}')"/>
                                </tiles:insert>
                            </div>
                        </c:if>
                    </div>
                </div>
            <div class="clear"></div>
            </div>
        </tiles:put>
    </tiles:insert>
</li>