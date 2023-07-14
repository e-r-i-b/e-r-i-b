<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 19.06.2009
  Time: 17:19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%@ include file="personData.jsp"%>
<c:if test="${person == null}">
    <%@ include file="personContextData.jsp"%>
</c:if>
<c:if test="${person != null}">
    <c:choose>
        <c:when test="${person.ermbProfile != null}">
            <c:set var="ermbProfile" value="${person.ermbProfile}"/>
        </c:when>
        <c:otherwise>
            <c:set var="ermbProfile" value="${phiz:getErmbProfile(personId, false)}"/>
        </c:otherwise>
    </c:choose>
    <tiles:insert definition="infoBlock" flush="false">
        <tiles:put name="image" value="anonym.jpg"/>
        <tiles:put name="data">
            <div class="size18 personName">${person.fullName}</div>
            <div class="infoBlockStatus">
                <c:choose>
                    <c:when test="${isNewOrTemplate}">
                        <img src="${imagePath}/iconSm_activate.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;Подключение
                    </c:when>
                    <c:when test="${not empty login.blocks and not isCancelation and not isErrorCancelation and not isSignAgreement}">
                        <img src="${imagePath}/iconSm_lock.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;Заблокирован
                    </c:when>
                    <c:when test="${isCancelation}">
                        Прекращение обслуживания
                    </c:when>
                    <c:when test="${isErrorCancelation}">
                        Ошибка расторжения
                    </c:when>
                    <c:when test="${isSignAgreement}">
                        Подписание заявления
                    </c:when>
                    <c:otherwise>
                        Активный
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="form-row-addition">
                <div class="paymentLabel">Мобильный телефон:</div>
                <div class="paymentValue">
                    <div class="paymentInputDiv">
                        <b>
                            <c:choose>
                                <c:when test="${ermbProfile != null && ermbProfile.serviceStatus && not ermbProfile.clientBlocked}" >
                                    ${ermbProfile.mainPhoneNumber}
                                </c:when>
                                <c:otherwise>
                                    ${person.mobilePhone}
                                </c:otherwise>
                            </c:choose>
                        </b>
                    </div>
                </div>
                <div class="paymentLabel" style="width: 185px; padding-left: 30px;">Статус подключения ЕРМБ:</div>
                <div class="paymentValue">
                    <div class="paymentInputDiv">
                        <b>
                            <c:choose>
                                <c:when test='${ermbProfile.serviceStatus and not ermbProfile.paymentBlocked and not ermbProfile.clientBlocked}'>
                                    <span style="color:green"><bean:message key="ermb.service.status.active" bundle="ermbBundle"/></span>
                                </c:when>
                                <c:when test='${ermbProfile.serviceStatus and ermbProfile.clientBlocked}'>
                                    <span style="color:grey"><bean:message key="ermb.service.status.block" bundle="ermbBundle"/></span>
                                </c:when>
                                <c:when test='${ermbProfile.serviceStatus and ermbProfile.paymentBlocked}'>
                                    <span style="color:peru"><bean:message key="ermb.service.status.not.paid" bundle="ermbBundle"/></span>
                                </c:when>
                                <c:when test='${not ermbProfile.serviceStatus}'>
                                    <span style="color:grey"><bean:message key="ermb.service.status.not.connected" bundle="ermbBundle"/></span>
                                </c:when>
                            </c:choose>
                        </b>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="form-row-addition">
                <div class="paymentLabel">Дата рождения:</div>
                <div class="paymentValue"><div class="paymentInputDiv"><b><bean:write name="person" property="birthDay.time" format="dd.MM.yyyy"/></b></div></div>
                <div class="clear"></div>
            </div>

            <c:if test="${mode == 'Users' and !isTrusted}">
                <div class="warningMessage">Функциональность ограничена</div>
            </c:if>
            <c:if test="${isModified}">
                <div class="withoutRegistration">
                    <img src="${imagePath}/important.gif" width="12px" height="12px" alt="" border="0"/>
                    <span class="attention">Анкета не зарегистрирована</span>
                </div>
            </c:if>
        </tiles:put>
    </tiles:insert>
</c:if>
