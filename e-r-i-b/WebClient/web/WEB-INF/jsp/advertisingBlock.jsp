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

<html:form action="/private/async/advertising" >
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bunner" value="${form.advertisingBlock}" scope="page"/>
    <c:set var="currentId" value="${bunner.id}" scope="page"/>


    <c:if test="${bunner !=null}">
        <div id="advertiingBlock${bunner.id}" class="bunnerArea" style="display:none">
            <div class="bunnerLists">
                <div class="grayRTC r-top-center"></div>

                <div class="grayRC r-content">
                    <div class="bunnerContent">
                        <c:set var="imageOrder" value="First"/>
                        <c:set var="top" value=""/>
                        <c:forEach var="bunnerArea" items="${bunner.areas}">
                            <c:if test="${bunnerArea.areaName == 'title' && not empty bunner.title}">
                                <c:set var="imageOrder" value=""/>
                                <div class="bunnerTitle" >${phiz:processBBCode(bunner.title)}</div>
                            </c:if>
                            <c:if test="${bunnerArea.areaName == 'text' && not empty bunner.text}">
                                <c:set var="imageOrder" value=""/>
                                <div class="bunnerText" >${phiz:processBBCode(bunner.text)}</div>
                            </c:if>
                            <c:if test="${bunnerArea.areaName == 'image' && bunner.image != null}">
                                <c:set var="imageData" value="${bunner.image}"/>
                                <c:set var="imageUrl" value="${imageData.linkURL}"/>
                                <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                <c:set var ="externalURL" value="${fn:indexOf(imageUrl, 'PhizIC') < 0}" scope="page"/>
                                <div class="bunnerImage${imageOrder}">
                                    <c:set var="top" value="${imageOrder}"/>
                                    <c:choose>
                                        <c:when test="${imageUrl != null}">

                                            <c:if test="${not externalURL}">
                                                <c:choose>
                                                    <c:when test="${fn:indexOf(imageUrl, '?') < 0}">
                                                        <c:set var="imageUrl" value="${imageUrl}?fromBanner=${currentId}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="imageUrl" value="${imageUrl}&fromBanner=${currentId}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <a href="${imageUrl}" onclick="bannerRequest(${currentId}, 'click')" <c:if test="${externalURL}">target="_blank"</c:if>>
                                                <img id="advertisingBlockImg${bunner.id}" src="${imagePath}" />
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <img id="advertisingBlockImg${bunner.id}" src="${imagePath}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <script type="text/javascript">
                                        if(window.isClientApp != undefined)
                                        {
                                            $("#advertisingBlockImg${bunner.id}").attr("src", "${imagePath}").load(function() {showCurrentBunner(${bunner.id})});
                                        }
                                    </script>
                                </div>
                            </c:if>
                            <c:if test="${bunnerArea.areaName == 'buttons' && not empty bunner.buttons}">
                                <c:set var="imageOrder" value=""/>
                                <div class="buttons-area">
                                    <c:forEach  var="bunnerButton" items="${bunner.buttons}">
                                        <c:if test="${bunnerButton.show == 'true'}">
                                            <c:set var ="externalURL" value="${fn:indexOf(bunnerButton.url, 'PhizIC') < 0}" scope="page"/>
                                            <c:set var="buttonImagePath" value=""/>
                                            <c:if test="${bunnerButton.image != null}">
                                                <c:set var="imageBunnerButton" value="${bunnerButton.image}"/>
                                                <c:set var="buttonImagePath" value="${phiz:getAddressImage(imageBunnerButton, pageContext)}"/>
                                            </c:if>

                                            <c:set var="bannerUrl" value="${bunnerButton.url}"/>
                                            <c:if test="${not externalURL}">
                                                <c:choose>
                                                    <c:when test="${fn:indexOf(bannerUrl, '?') < 0}">
                                                        <c:set var="bannerUrl" value="${bannerUrl}?fromBanner=${currentId}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="bannerUrl" value="${bannerUrl}&fromBanner=${currentId}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>

                                            <c:if test="${empty buttonImagePath  && bunnerButton.title != null}">
                                            <div class="clientButton">
                                                <a href="${bannerUrl}" onclick="bannerRequest(${currentId}, 'click')"  <c:if test="${externalURL}">target="_blank"</c:if> >
                                                    <div class="buttonGreen">
                                                        <div class="text">
                                                            <span>${phiz:processBBCode(bunnerButton.title)}</span>
                                                        </div>
                                                    </div>
                                                </a>
                                                <div class="clear"></div>
                                            </div>
                                            </c:if>
                                            <c:if test="${not empty buttonImagePath}">
                                                <div class="clientButton" >
                                                    <a href="${bannerUrl}"  onclick="bannerRequest(${currentId}, 'click')"  <c:if test="${externalURL}">target="_blank"</c:if> >
                                                        <div class="buttonGreen">
                                                            <div class="text" style="background: url('${buttonImagePath}') no-repeat;">
                                                                <span>${phiz:processBBCode(bunnerButton.title)}</span>
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
                        <div id="advertisingBlockContent${bunner.id}" class="advertisingBlockContent opacity50" style="display:none;">
                            <img src="${imgPath}/ajaxLoader.gif" alt="Loading..." title="Loading..." class="abstractLoader"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
            <c:if test="${phiz:size(form.bannersList) > 1}">
                <div class="navigation">
                    <c:forEach  var="navigation" items="${form.bannersList}">
                        <c:choose>
                            <c:when test="${navigation == currentId}">
                                <div id="navigationGreen${bunner.id}" class="navigationGreen"></div>
                            </c:when>
                            <c:otherwise>
                                <div id="navigationGray${bunner.id}" class="navigationGray" onclick="getNextBunner(${navigation});"></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <script type="text/javascript">
            if(window.isClientApp != undefined)
            {
                if (tryShowBlock == null)
                {
                    tryShowBlock = ${bunner.id};
                    loadingBlock.push(tryShowBlock);
                }
                clearTimout();
                setTimout(${bunner.showTime*1000});
                showTime[${bunner.id}] = ${bunner.showTime*1000};
            }
        </script>
    </c:if>
    
</html:form>