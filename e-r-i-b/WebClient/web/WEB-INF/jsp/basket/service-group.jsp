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

<div class="sg-serviceGroup" id="serviceGroup${id}">
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="lightBrown"/>
        <tiles:put name="data">
            <div class="sortableWrapper">
                <div class="sg-top">
                    <div class="sg-titlePlace floatLeft">
                        <div class="sg-name">
                            <span class="sg-nameText">${name}</span>
                        </div>
                    </div>
                    <div class="sg-topRight floatRight">
                        <div class="sg-changeButton sg-button">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.editAccountingObject"/>
                                <tiles:put name="commandHelpKey" value="button.editAccountingObject.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="openEditWindow('${type}',${id})"/>
                                <tiles:put name="image" value="editPencil.png"/>
                                <tiles:put name="imageHover" value="editPencilHover.png"/>
                                <tiles:put name="imagePosition" value="left"/>
                            </tiles:insert>
                        </div>
                    </div>
                </div>
                <div class="clear">&nbsp;</div>

                <div class="sg-mainBody">
                    <ul class="activeList pb-subscriptionsList sg-entitySubList" name="subList${id}">
                        <c:forEach items="${form.activeSubscriptions[id]}" var="subscription" varStatus="loop">
                            <c:set var="state" value="${subscription.state}"/>
                            <c:choose>
                                <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                                    <c:set var="nextState" value=""/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="nextState" value="${subscription.nextState}"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:insert definition="invoiceSubscription" flush="false">
                                <tiles:put name="topTitle" value=""/>
                                <tiles:put name="type" value="is-typeEntityService"/>
                                <tiles:put name="state" value="${state}"/>
                                <tiles:put name="nextState" value="${nextState}"/>
                                <tiles:put name="name" value="${subscription.name}"/>
                                <tiles:put name="provider" value="${subscription.recName}"/>
                                <tiles:put name="id" value="${subscription.id}"/>
                                <tiles:put name="errorType" value="" />
                                <tiles:put name="statusMessage" value="${subscription.errorInfo == null || subscription.errorInfo.type != 'LIST' ? '' : subscription.errorInfo.text}"/>
                                <tiles:put name="notPaid" value="${subscription.numberOfNotPaidInvoices}"/>
                                <tiles:put name="delayed" value="${subscription.numberOfDelayedInvoices}"/>
                                <tiles:put name="date" value="${phiz:formatDayWithStringMonth(subscription.delayDate)}"/>
                                <tiles:put name="imageId" value="${form.imageIds[id]}"/>
                            </tiles:insert>
                        </c:forEach>
                    </ul>

                    <div class="clear"></div>
                    <ul class="stoppedList pb-subscriptionsList" name="subList${id}">
                        <c:forEach items="${form.stoppedSubscriptions[id]}" var="subscription" varStatus="loop">
                            <c:set var="state" value="${subscription.state}"/>
                            <c:choose>
                                <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                                    <c:set var="nextState" value=""/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="nextState" value="${subscription.nextState}"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:insert definition="invoiceSubscription" flush="false">
                                <tiles:put name="topTitle" value="Приостановлен автопоиск по услугам"/>
                                <tiles:put name="type" value="is-typeEntityService"/>
                                <tiles:put name="state" value="${state}"/>
                                <tiles:put name="nextState" value="${nextState}"/>
                                <tiles:put name="name" value="${subscription.name}"/>
                                <tiles:put name="provider" value="${subscription.recName}"/>
                                <tiles:put name="id" value="${subscription.id}"/>
                                <tiles:put name="errorType" value="${subscription.errorType}" />
                                <tiles:put name="statusMessage" value="${subscription.errorInfo == null || subscription.errorInfo.type != 'LIST' ? '' : subscription.errorInfo.text}"/>
                                <tiles:put name="notPaid" value="${subscription.numberOfNotPaidInvoices}"/>
                                <tiles:put name="delayed" value="${subscription.numberOfDelayedInvoices}"/>
                                <tiles:put name="imageId" value="${form.imageIds[id]}"/>
                                <c:if test="${not empty subscription.documentStatus}">
                                    <tiles:put name="documentStatus" value="${subscription.documentStatus}"/>
                                </c:if>
                            </tiles:insert>
                        </c:forEach>
                    </ul>

                    <ul class="recommended pb-subscriptionsList" name="subList${id}">
                        <c:forEach items="${form.recommendedSubscriptions[id]}" var="subscription" varStatus="loop">
                            <c:set var="state" value="${subscription.state}"/>
                            <c:choose>
                                <c:when test="${state == 'FAKE_SUBSCRIPTION_PAYMENT'}">
                                    <c:set var="nextState" value=""/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="nextState" value="${subscription.nextState}"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:insert definition="invoiceSubscription" flush="false">
                                <c:if test="${loop.index == 0}">
                                    <tiles:put name="topTitle" value="Рекомендуем настроить автопоиск по услугам"/>
                                </c:if>
                                <c:if test="${loop.index > 0}">
                                    <tiles:put name="topTitle" value=""/>
                                </c:if>
                                <c:if test="${loop.index > 0}">
                                    <tiles:put name="topTitle" value=""/>
                                </c:if>
                                <tiles:put name="type" value="is-typeEntityService"/>
                                <tiles:put name="state" value="${state}"/>
                                <tiles:put name="nextState" value="${nextState}"/>
                                <tiles:put name="name" value="${subscription.name}"/>
                                <tiles:put name="provider" value="${subscription.recName}"/>
                                <tiles:put name="id" value="${subscription.id}"/>
                                <tiles:put name="statusMessage" value="" />
                                <tiles:put name="errorType" value=""/>
                                <tiles:put name="notPaid" value="${subscription.numberOfNotPaidInvoices}"/>
                                <tiles:put name="delayed" value="${subscription.numberOfDelayedInvoices}"/>
                                <tiles:put name="imageId" value="${form.imageIds[id]}"/>
                            </tiles:insert>
                        </c:forEach>
                    </ul>

                    <div class="sg-chooseButton">
                        <div class="sg-chooseButton sg-button">
                            <div class="sg-clientButton">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.chooseService"/>
                                    <tiles:put name="commandHelpKey" value="button.chooseService.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="onclick" value="showServicesList(${id})"/>
                                    <tiles:put name="image" value="addIcon.png"/>
                                    <tiles:put name="imageHover" value="addIconHover.png"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <div class="sg-emptyPart">
                        <tiles:insert definition="roundBorder" flush="false">
                            <tiles:put name="color" value="grayPointer"/>
                            <tiles:put name="data">
                                <div class="sg-hint">
                                    <p>Настройте автопоиск неоплаченных счетов за:</p>
                                    <c:forEach items="${form.serviceCategories[id]}" var="service" varStatus="loop">
                                        <c:if test="${loop.index != 0}">,</c:if>
                                        <a class="text-gray pb-ref" href="${phiz:calculateActionURL(pageContext, 'private/userprofile/serviceContent' )}?serviceId=${service.id}&accountingEntityId=${id}">${service.buttonName}</a>
                                    </c:forEach>
                                    <a class="pb-ref sg-otherServicesRef" href="${phiz:calculateActionURL(pageContext, '/private/userprofile/listServices')}?accountingEntityId=${id}">Другие услуги</a>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</div>

