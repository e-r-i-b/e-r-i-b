<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="imgPath" value="${skinUrl}/images"/>

<html:form action="/private/async/notification" >
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="notification" value="${form.offerNotification}" scope="page"/>
    <c:set var="currentId" value="${notification.id}" scope="page"/>
    <c:if test="${notification != null}">
        <div id="notification${notification.id}" class="bunnerArea" style="display:none">
            <div class="bunnerLists">
                <div class="grayRTC r-top-center"></div>
                <div class="grayRC r-content">
                    <div class="bunnerContent">
                        <c:set var="imageOrder" value="First"/>
                        <c:set var="top" value=""/>
                        <c:forEach var="area" items="${notification.areas}">
                            <c:choose>
                                <c:when test="${notification.productType == 'LOAN'}">
                                    <c:set var="offerId" value="${form.loanOffer.offerId}"/>
                                    <c:set var="offerType" value="LOAN"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="offerId" value="${form.loanCardOffer.offerId}"/>
                                    <c:set var="offerType" value="LOAN_CARD"/>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${area.areaName == 'title' && not empty notification.title}">
                                <c:set var="imageOrder" value=""/>
                                <c:set var="offerTitle" value="${phiz:processBBCode(notification.title)}"/>
                                <c:choose>
                                    <c:when test="${notification.productType == 'LOAN'}">
                                        <div class="bunnerTitle" >${phiz:getLoanOfferText(offerTitle, form.loanOffer)}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="bunnerTitle" >${phiz:getLoanCardOfferText(offerTitle, form.loanCardOffer)}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${area.areaName == 'text' && not empty notification.text}">
                                <c:set var="imageOrder" value=""/>
                                <c:set var="offerText" value="${phiz:processBBCode(notification.text)}"/>
                                <c:choose>
                                    <c:when test="${notification.productType == 'LOAN'}">
                                        <div class="bunnerText" >${phiz:getLoanOfferText(offerText, form.loanOffer)}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="bunnerText" >${phiz:getLoanCardOfferText(offerText, form.loanCardOffer)}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${area.areaName == 'image' && notification.imageId!= null}">
                                <c:set var="imageData" value="${phiz:getImageById(notification.imageId)}"/>
                                <c:set var="imageUrl" value="${imageData.linkURL}"/>
                                <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                <c:set var ="externalURL" value="${fn:indexOf(imageUrl, 'PhizIC') < 0}" scope="page"/>
                                <div class="bunnerImage${imageOrder}">
                                    <c:set var="top" value="${imageOrder}"/>
                                    <c:choose>
                                        <c:when test="${imageUrl != null}">
                                            <c:set var="newLoanCardUrl" value="${fn:indexOf(imageUrl, form.loanCardClaimLink) > 0}"/>
                                            <c:if test="${newLoanCardUrl && notification.productType == 'LOAN_CARD'}">
                                                <c:set var="imageUrl" value="${imageUrl}&offerId=${form.loanCardOffer.offerId}"/>
                                            </c:if>
                                            <a href="${imageUrl}" onclick="notificationRequest(${currentId},'${notification.name}', '${offerId}', '${offerType}', 'click')" <c:if test="${externalURL}">target="_blank"</c:if>>
                                                <img id="notificationImg${notification.id}" src="${imagePath}" />
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <img id="notificationImg${notification.id}" src="${imagePath}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <script type="text/javascript">
                                        if(window.isClientApp != undefined)
                                        {
                                            $("#notificationImg${notification.id}").attr("src", "${imagePath}").load(function() {showCurrentNotification(${notification.id})});
                                        }
                                    </script>
                                </div>
                            </c:if>
                            <c:if test="${area.areaName == 'buttons' && not empty notification.buttons}">
                                <c:set var="imageOrder" value=""/>
                                <div class="buttons-area">
                                    <c:forEach  var="button" items="${notification.buttons}">
                                        <c:if test="${button.show == 'true'}">
                                            <c:set var ="externalURL" value="${fn:indexOf(button.url, 'PhizIC') < 0}" scope="page"/>
                                            <c:set var="buttonImagePath" value=""/>
                                            <c:if test="${not empty button.imageId}">
                                                <c:set var="imageData" value="${phiz:getImageById(button.imageId)}"/>
                                                <c:set var="buttonImagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                            </c:if>

                                            <c:set var="buttonUrl" value="${button.url}"/>
                                            <c:set var="buttonTitle" value="${phiz:processBBCode(button.title)}"/>
                                            <c:set var="newLoanCardUrl" value="${fn:indexOf(buttonUrl, form.loanCardClaimLink) > 0}"/>
                                            <c:if test="${empty buttonImagePath  && button.title != null}">
                                                <div class="clientButton">
                                                    <c:if test="${newLoanCardUrl == true && notification.productType == 'LOAN_CARD'}">
                                                         <c:set var="buttonUrl" value="${buttonUrl}&offerId=${form.loanCardOffer.offerId}"/>
                                                    </c:if>
                                                    <a href="${buttonUrl}"  onclick="notificationRequest(${currentId},'${notification.name}', '${offerId}', '${offerType}', 'click')" <c:if test="${externalURL}">target="_blank"</c:if> >
                                                        <div class="buttonGreen">
                                                            <div class="text">
                                                                <span>${buttonTitle}</span>
                                                            </div>
                                                        </div>
                                                    </a>
                                                    <div class="clear"></div>
                                                </div>
                                            </c:if>
                                            <c:if test="${not empty buttonImagePath}">
                                                <div class="clientButton" >
                                                    <a href="${buttonUrl}"  onclick="notificationRequest(${currentId},'${notification.name}', '${offerId}', '${offerType}', 'click')"  <c:if test="${externalURL}">target="_blank"</c:if> >
                                                        <div class="buttonGreen">
                                                            <div class="text" style="background: url('${buttonImagePath}') no-repeat;">
                                                                <span>${buttonTitle}</span>
                                                            </div>
                                                        </div>
                                                    </a>
                                                    <div class="clear"></div>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </c:forEach>
                        <div id="notificationContent${notification.id}" class="advertisingBlockContent opacity50" style="display:none;">
                            <img src="${imgPath}/ajaxLoader.gif" alt="Loading..." title="Loading..." class="abstractLoader"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
            <c:if test="${phiz:size(form.notificationsList) > 1}">
                <div class="navigation">
                    <c:forEach  var="navigation" items="${form.notificationsList}">
                        <c:choose>
                            <c:when test="${navigation.id == currentId}">
                                <div id="navigationGreen${currentId}" class="navigationGreen"></div>
                            </c:when>
                            <c:otherwise>
                                <div id="navigationGray${currentId}" class="navigationGray" onclick="getNextNotification(${navigation.id});"></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <script type="text/javascript">
            if(window.isClientApp != undefined)
            {
                if (tryShowOffer == null)
                {
                    tryShowOffer = ${notification.id};
                    loadingBlockOffer.push(tryShowOffer);
                }
                clearTimoutOffer();
                setTimoutOffer(${notification.showTime*1000});
                showTimeOffer[${notification.id}] = ${notification.showTime*1000};
            }
        </script>
    </c:if>
</html:form>