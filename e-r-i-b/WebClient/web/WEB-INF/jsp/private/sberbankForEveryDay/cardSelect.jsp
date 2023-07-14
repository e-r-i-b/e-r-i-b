<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<html:hidden property="field(firstNameAndLastNameLatin)"/>
<html:hidden property="field(patrName)" />
<html:hidden property="field(firstName)" />
<html:hidden property="field(lastName)" />
<html:hidden property="field(email)" />
<html:hidden property="field(gender)" />
<html:hidden property="patrNameAbsent" />
<html:hidden property="removeCardNumber" styleId="removeCardNumber"/>
<html:hidden property="editCardNumber" styleId="editCardNumber"/>
<c:choose>
    <c:when test="${form.stageNumber == thisStage}">
        <c:set var="cardNumber" value="0"/>
        <%@ include file="addCard.jsp" %>
    </c:when>
    <c:otherwise>
        <c:forEach var="cardNumber" begin="0" end="${form.cardCount-1}">
            <div class="chooseCardType">
                <div class="cardImg" onclick="editCard(${cardNumber})"></div>
                <div class="cardParams">
                    <c:set var="nameFieldName" value="cardName${cardNumber}"      />

                    <span onclick="editCard(${cardNumber})" class="cardsName">
                        <span class="dashedBottom">
                            <span class="hoverIndent"><c:out value="${form.fields[nameFieldName]}"/></span>
                        </span>
                    </span>

                    <c:set var="currFieldName" value="cardCurrency${cardNumber}"/>
                    <p class="descData"><bean:message key="field.currency.${form.fields[currFieldName]}" bundle="sbnkdBundle"/></p>
                    <c:if test="${cardNumber == 0}">
                        <span class="clientNameRus"><c:out value="${form.issueCardDoc.personFirstName}"/>  <c:out value="${form.issueCardDoc.personMiddleName}"/>    ${fn:substring(form.issueCardDoc.personLastName,0 ,1)}.</span>

                        <p class="descData">
                            <c:set var="namesLatin" value="${form.issueCardDoc.contractEmbossedText}"/>
                            <c:set var="firstName" value="${fn:substring(namesLatin, 0, fn:indexOf(namesLatin, ' '))}"/>
                            <c:set var="lastName" value="${fn:substring(namesLatin, fn:indexOf(namesLatin, ' ')+1, fn:length(namesLatin))}"/>
                            ${fn:substring(lastName, 0, 1)}${phiz:mask('•', fn:length(lastName)-1)}
                            <c:out value="${firstName}"/>
                        </p>
                    </c:if>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="editCardWin${cardNumber}"/>
                        <tiles:put name="closeCallback">hideCardErrorMessage</tiles:put>
                        <tiles:put name="data">
                            <c:set var="cardNumber" value="${cardNumber}" />
                            <h4 class="winTitle">Выбор основной карты</h4>
                            <%@ include file="addCard.jsp" %>
                            <div class="winBtn">
                                <div class="orderseparate"></div>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.addCard"/>
                                    <tiles:put name="commandHelpKey" value="button.addCard"/>
                                    <tiles:put name="bundle" value="sbnkdBundle"/>
                                    <tiles:put name="onclick" value="addCard('${cardNumber}')"/>
                                </tiles:insert>
                                <c:if test="${cardNumber != 0}">
                                    <div class="rightBtn">
                                        <tiles:insert definition="profileButton" flush="false">
                                            <tiles:put name="commandText" value="Удалить карту"/>
                                            <tiles:put name="onclick" value="removeCard(${cardNumber})"/>
                                            <tiles:put name="viewType" value="removeIdentButton"/>
                                            <tiles:put name="icon" value="removeIcon"/>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </div>
            <div class="clear"></div>
        </c:forEach>
        <div class="clear"></div>
        <script type="text/javascript">
            function hideCardErrorMessage()
            {
                var currentWin = arguments.callee.caller.arguments[0];
                var addCardWin = 'addCardWin';
                var editCardWin = 'editCardWin';

                if(currentWin.indexOf(addCardWin) >= 0)
                {
                    $('#editCardWarningMessages' + ${form.cardCount}).addClass('displayNone');
                }
                else
                {
                    var winNumberPos = currentWin.indexOf(editCardWin) >= 0 ? currentWin.indexOf(editCardWin) + editCardWin.length : -1;
                    var winNumber = winNumberPos >= 0 ? currentWin.substring(winNumberPos, winNumberPos + 1) : -1;
                    if(winNumber >= 0)
                        $('#editCardWarningMessages' + winNumber).addClass('displayNone');
                }

                return true;
            }
        </script>
        <div class="addCard" onclick="addNewCard(${form.cardCount});">
            <div class="addCardImg"></div>
            <div class="btnText">Добавить карту</div>
        </div>
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="addCardWin"/>
            <tiles:put name="closeCallback">hideCardErrorMessage</tiles:put>
            <tiles:put name="data">
                <c:set var="cardNumber" value="${form.cardCount}" />
                <h4 class="winTitle">Выбор основной карты</h4>
                <%@ include file="addCard.jsp" %>
                <div class="winBtn">
                    <div class="orderseparate"></div>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.addCard"/>
                        <tiles:put name="commandHelpKey" value="button.addCard"/>
                        <tiles:put name="bundle" value="sbnkdBundle"/>
                        <tiles:put name="onclick" value="addCard('${cardNumber}')"/>
                    </tiles:insert>
                </div>
            </tiles:put>
        </tiles:insert>

    </c:otherwise>
</c:choose>

