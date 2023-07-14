<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="faqLink" value="${phiz:calculateActionURL(pageContext, '/faq.do')}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>

<c:set var="showAssistant" value="${phiz:impliesService('ShowAssistantService')}"/>
<c:set var="manager" value="${phiz:getManager()}"/>
<c:set var="noManagerClass" value=""/>
<c:if test="${empty manager && showAssistant}">
    <c:set var="noManagerClass" value="noManagerAssistant"/>
</c:if>
<c:set var="fullWidthClass" value=""/>
<c:if test="${not empty manager && !showAssistant}">
    <c:set var="fullWidthClass" value="fullWidth"/>
</c:if>
<c:set var="helpLinkClass" value="helpLink"/>
<c:if test="${not empty manager && showAssistant}">
    <c:set var="helpLinkClass" value="helpLinkWithAssistant"/>
</c:if>

<tiles:insert definition="roundBorderLight" flush="false">
    <tiles:put name="color" value="greenGradient css3"/>
    <tiles:put name="data">
        <div class="helpDiv ${fullWidthClass}">
            <div class="helpDivLeftBlock">
                <c:if test="${not empty manager}">
                    <div class="content">
                        <div class="right-faq-link ${showAssistant?'showAssistant':''}">
                            <div class="infoText personalManager">Ваш персональный менеджер:</div>
                            <div class="infoText managerFIO">${manager.name}</div>
                            <div class="infoText greenLightText">${manager.email}</div>
                            <div class="infoText bold">${manager.phone}</div>
                        </div>
                    </div>
                </c:if>

                <div class="${noManagerClass} helpBlock">
                    <c:choose>
                        <c:when test="${empty manager && not showAssistant}">
                            <div class="noManagerNoAssistant">
                                <div class="${helpLinkClass} helpPage"><a href="" class="underline" onclick="openHelp('${helpLink}'); return false;" title="Помощь по работе в системе"><span>Помощь</span></a></div>

                                <div class="${helpLinkClass}">
                                    <a href="" onclick="openFAQ('${faqLink}'); return false;" title="Часто задаваемые вопросы">
                                        <span>Часто задаваемые вопросы</span>
                                    </a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <%-- Отображаем картинку со знаком вопроса если нет данных о менежере и ЕСТЬ Вика --%>
                            <div class="${helpLinkClass} helpPage"><a href="" class="underline" onclick="openHelp('${helpLink}'); return false;" title="Помощь по работе в системе"><span>Помощь</span></a></div>

                            <div class="${helpLinkClass} faqPage">
                                <a href="" onclick="openFAQ('${faqLink}'); return false;" title="Часто задаваемые вопросы">
                                    <span>Часто задаваемые вопросы</span>
                                </a>
                            </div>

                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="helpDivRightBlock">
                <c:choose>
                    <c:when test="${showAssistant}">
                        <c:set var="isVirtualGirl">
                            ${phiz:impliesOperation("VirtualGirlOperation","VirtualGirlService")}
                        </c:set>
                        <c:choose>
                            <c:when test="${isVirtualGirl}">
                                <div class="girl-img">
                                    <a href="#" onclick="openInf (this, '${globalImagePath}/svf/Vishnu.swf'); return false;"><img src="${skinUrl}/images/girl_right.gif" alt="" border="0"/></a>
                                </div>

                                <tiles:insert definition="window" flush="false">
                                    <tiles:put name="id" value="infWindow"/>
                                    <tiles:put name="data">

                                        <object width="470" height="320" bgcolor="#ffffff">
                                            <param name="id" value="vishnu">
                                            <param name="name" value="vishnu">
                                            <param name="src" value="${globalImagePath}/svf/Vishnu.swf">
                                            <param name="wmode" value="opaque" />
                                            <param name="wmode" value="window">
                                            <param name="flashvars" value="position=right&size=m">
                                            <param name="type"
                                                   value="application/x-shockwave-flash">

                                            <embed type="application/x-shockwave-flash"
                                                   src="${globalImagePath}/svf/Vishnu.swf" width="470" height="320"
                                                   style=""
                                                   id="vishnu"
                                                   name="vishnu"
                                                   quality="high" wmode="window"
                                                   allowscriptaccess="always" allowdomain="always"
                                                   flashvars="position=right&size=m">
                                        </object>

                                    </tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <div class="girl-img">
                                    <a href="" onclick="openFAQ('${faqLink}'); return false;">
                                        <img src="${skinUrl}/images/girl_right.gif" alt="" border="0"/>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
            </div>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>