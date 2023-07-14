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
    <c:if test="${ermbProfile != null}">
        <tiles:insert definition="infoBlock" flush="false">
            <tiles:put name="image" value="anonym.jpg"/>
            <tiles:put name="data">
                <script type="text/javascript">
                    function showBlockReason()
                    {
                        alert('Причина блокировки: <c:out value="${ermbProfile.lockDescription}"/>');
                    }
                </script>
                <div style="float: left">
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

                    <c:set var="mainPhone" value="${ermbProfile.mainPhoneNumber}"/>
                    <div class="form-row-addition leftAlign">
                        <div class="paymentLabel">Мобильный телефон:</div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <b>
                                    <c:out value="${phiz:getIKFLPhoneNumber(mainPhone)}"/>
                                </b>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row-addition leftAlign ">
                        <div class="paymentLabel">Дата рождения:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><bean:write name="person" property="birthDay.time" format="dd.MM.yyyy"/></b></div></div>
                        <div class="clear"></div>
                    </div>

                    <c:set var="mainDoc" value="${phiz:getMainPersonDocument(person)}"/>
                    <div class="form-row-addition leftAlign">
                        <div class="paymentLabel">Документ:</div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <c:if test="${mainDoc != null}">
                                    <bean:message key="document.type.${mainDoc.documentType}" bundle="personsBundle"/>&nbsp;
                                    <b><bean:write name="mainDoc" property="documentSeries"/>&nbsp;<bean:write name="mainDoc" property="documentNumber"/></b>
                                </c:if>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row-addition leftAlign">
                        <div class="paymentLabel">Террбанк:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${phiz:getPersonTb(person)}"/></b></div></div>
                        <div class="clear"></div>
                    </div>

                </div>
                <div style="float: left; margin-left: 50px">
                    <span class="size18 personName">ЕРМБ</span>
                    <span class="infoBlockStatus">
                        <c:choose>
                            <c:when test='${ermbProfile.serviceStatus and not ermbProfile.paymentBlocked and not ermbProfile.clientBlocked}'>
                                <span style="color:green"><bean:message key="ermb.service.status.active" bundle="ermbBundle"/></span>
                            </c:when>
                            <c:when test='${ermbProfile.serviceStatus and ermbProfile.clientBlocked}'>
                                <a href="#">
                                    <span style="color:grey" onclick="showBlockReason()"><bean:message key="ermb.service.status.block" bundle="ermbBundle"/></span>
                                </a>
                            </c:when>
                            <c:when test='${ermbProfile.serviceStatus and ermbProfile.paymentBlocked}'>
                                <span style="color:peru"><bean:message key="ermb.service.status.not.paid" bundle="ermbBundle"/></span>
                            </c:when>
                            <c:when test='${not ermbProfile.serviceStatus}'>
                                <span style="color:grey"><bean:message key="ermb.service.status.not.connected" bundle="ermbBundle"/></span>
                            </c:when>
                        </c:choose>
                    </span>

                    <div class="infoBlockStatus">
                        Тариф "<bean:write name="ermbProfile" property="tarif.name" ignore="true"/>"
                    </div>

                    <c:forEach var="phone" items="${ermbProfile.phoneNumbers}">
                        <c:if test="${mainPhone != phone}">
                            <div class="form-row-addition leftAlign">
                                <div class="paymentLabel">&nbsp;</div>
                                <div class="paymentValue"><div class="paymentInputDiv">
                                    <c:out value="${phiz:getIKFLPhoneNumber(phone)}"/>
                                </div></div>
                                <div class="clear"></div>
                            </div>
                        </c:if>
                    </c:forEach>

                    <div class="form-row-addition leftAlign">
                        <div class="paymentLabel">Дата подключения:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><bean:write name="ermbProfile" property="connectionDate.time" format="dd.MM.yyyy"/></b></div></div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row-addition leftAlign">
                        <div class="paymentLabel">Оператор сотовой связи:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${phiz:getMobileOperatorNameByProfile(ermbProfile)}"/></b></div></div>
                        <div class="clear"></div>
                    </div>
                </div>

            </tiles:put>
        </tiles:insert>
    </c:if>
</c:if>
