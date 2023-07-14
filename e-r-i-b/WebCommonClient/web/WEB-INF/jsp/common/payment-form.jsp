<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--
Форма для стандартного платежа. Его  обрамление. Внутренняя таблица с полями прописывается в *.html.xslt платежа

	id          -   имя формы
	title       -   заголовок roundBorder
	name        -   название отображаемое в UI
	description -   описание платежа
	data        -   данные платежа - внутренняя таблица с полями
    buttons     -   кнопка платежа
    alignTable  -   это центровка формы в рабочей области (центр, право, лево)
    stamp       -   штамп (Возможные значения  delayed: ожидание обработки,
                                              received: принято к исполнению,
                                              processed: "Исполняется банком
                                              executed: исполнен,
                                              refused: отказан
                                              accepted: принята). Если пустое, штамп не рисуем.
    template    -   вызов шаблона платежа
    autopay    -    блок с созданием автоплатежа  платежа
    isTemplate  -   isTemplate == true, если это страница сохранения/просмотра шаблона
    isEnterConfirm - isEnterConfirm == true, если это страница подтверждения входа
    isServicePayment - isServicePayment == true, если это оплата услуг
    imageId   -     id изображения для оплаты услуг
    stripe    -   полоска с шагами платежа
    dblLineStripe - позиционирование названий линии жизни в 2 строки
    byCenter  - распологать данный див по центру (в случае, если платеж показан в режиме без правого меню)
    additionInfo  - расширенное описание платежа
    favouriteArea - блок функционала "Добавить в избранное"
    showHeader - нужно ли показывать изображение и описание платежа
    customHeader - используется, если нужно передать шапку, которая отличается от стандартной
    hint - подсказка к форме,отображается сразу под заголовком
--%>

<div id="payment">
<c:choose>
    <c:when test="${not empty color}">
        <c:set var="contentType" value="roundBorder"/>
    </c:when>
    <c:otherwise>
        <c:set var="contentType" value="mainWorkspace"/>
    </c:otherwise>
</c:choose>

<tiles:insert definition="${contentType}" flush="false">
        <tiles:put name="title" value="${title}"/>
        <tiles:put name="color" value="${color}"/>
        <tiles:put name="contentTitle">${contentTitle}</tiles:put>
        <tiles:put name="data">
            <c:if test="${not empty hint}">
                <div>${hint}</div>
            </c:if>
            <c:choose>
                <c:when test="${not isEnterConfirm and showHeader}">
                    <table class="paymentHeader">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty imageId}">
                                        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                        <img class="icon" src="${phiz:getAddressImage(imageData, pageContext)}" alt=""
                                                onerror="this.onerror=null;this.src='${imagePath}/otherPaymentProvider.jpg';"/>
                                    </c:when>
                                    <c:when test="${isTemplate}">
                                        <img class="icon" src="${imagePath}/icon_template.png" alt=""/>
                                    </c:when>
                                    <c:when test="${isServicePayment}">
                                        <img class="icon" src="${imagePath}/IQWave-other.jpg" alt=""/>
                                    </c:when>
                                    <c:when test="${id == 'JurPayment'}">
                                        <img class="icon" src="${globalImagePath}/iconPmntList_JurPayment64.jpg" alt=""/>
                                    </c:when>
                                    <c:when test="${guest or id == 'RemoteConnectionUDBOClaim'}">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="icon" src="${globalImagePath}/iconPmntList_${id}.jpg" alt=""
                                             onerror="this.onerror=null;this.src='${imagePath}/otherPaymentProvider.jpg';"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${description}
                            </td>
                        </tr>
                    </table>
                    ${favouriteArea}
                    <div class="clear"></div>
                    <div id="paymentStripe${byCenter}" class="<c:if test='${dblLineStripe}'>dblLineStripe</c:if>">
                        ${stripe}
                        <div class="clear"></div>
                    </div>
                </c:when>
                <c:when test="${not isEnterConfirm and not empty customHeader}">
                   ${customHeader}
                   <div class="clear"></div>
                </c:when>
            </c:choose>
            <!-- Поля платежки -->
            <div id="paymentForm${byCenter}">
                ${data}
                <div class="clear"></div>
            </div>
            ${confirmInfo}

            <c:choose>
                <c:when test="${id == 'NewRurPayment'}">&nbsp;</c:when>
                <c:when test="${not empty stamp}">
                    <c:set var="OSB" value="${phiz:getOSB(form.department)}"/>
                    <c:set var="CorrAcc" value="${phiz:getCorrByBIC(OSB.BIC)}"/>
                    <c:set var="isDepositaryClaim" value="${id == 'SecurityRegistrationClaim' || id == 'SecuritiesTransferClaim' || id == 'DepositorFormClaim' || id == 'RecallDepositaryClaim'}"/>
                    <c:if test="${id != 'OfficeLoanClaim'}">
                        <c:set var="isITunes" value="${phiz:isITunesDocument(form.document)}"/>
                    </c:if>
                    <div class="stamp ${isITunes ? 'stampITunes' : ''}" style="position:relative;">
                        <div class="forBankStamp <c:if test="${id == 'ReIssueCardClaim' || id == 'BlockingCardClaim'}"> underBankStamp</c:if><c:if test="${id == 'PreapprovedLoanCardClaim' || id == 'LoanCardClaim'}"> rightBankStamp</c:if>
                        <c:if test="${id == 'ExtendedLoanClaim'}"> newLoanClaim</c:if><c:if test="${id == 'AccountOpeningClaim' || id == 'AccountOpeningClaimWithClose'}"> afterRulesStamp</c:if> <c:if test="${id == 'RemoteConnectionUDBOClaim'}"> remoteUdboStamp</c:if>">
                            <c:set var="stampDetails">
                                <c:choose>
                                    <c:when test="${isDepositaryClaim}">
                                        <span class="stampTitle">Депозитарий ОАО "Сбербанк России"</span><br>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${stamp == 'processed'}">
                                            <div style="text-align:center;">
                                                <img src="${imagePath}/stampProcessed_noBorder.gif" width="150px" height="40px"/>
                                            </div>
                                        </c:if>
                                        <span class="stampTitle"><c:out value="${OSB.name}" default="${bankName}"/></span><br>
                                        <span class="stampText">БИК:<c:out value="${OSB.BIC}" default="${bankBIC}"/></span><br>
                                        <span class="stampText">Корр.Счет: <c:out value="${phiz:getCorrByBIC(OSB.BIC)}" default="${phiz:getCorrByBIC(bankBIC)}"/></span><br>
                                    </c:otherwise>
                                </c:choose>
                            </c:set>
                            <c:if test="${!isITunes}">${stampDetails}</c:if>
                            <div class="${isITunes ? 'stampStateITunes' : ''}" style="text-align:center;">

                                <c:if test="${stamp == 'waitconfirm'}">
                                    <img src="${imagePath}/stampWaitConfirm_noBorder.gif"  width="128px" height="45px"/>
                                </c:if>
                                <c:if test="${stamp == 'delayed'}">
                                    <img src="${imagePath}/stampDelayed_noBorder.gif"  width="128px" height="25px"/>
                                </c:if>
                                <c:if test="${stamp == 'received'}">
                                    <img src="${imagePath}/stampReceived_noBorder.gif" width="128px" height="25px"/>
                                </c:if>
                                <c:if test="${stamp == 'executed'}">
                                    <img src="${imagePath}/stampExecuted_noBorder.gif" width="137px" height="18px"/>
                                </c:if>
                                <c:if test="${stamp == 'refused'}">
                                    <img src="${imagePath}/stampRefused_noBorder.gif" width="107px" height="17px"/>
                                </c:if>
                                <c:if test="${stamp == 'accepted'}">
                                    <img src="${imagePath}/stampAccepted_blue.gif" width="150px" height="30px"/>
                                </c:if>

                            </div>
                            <span style="color:#5d417b;font:bold Arial;font-size:10;white-space: nowrap;"> ${docDate}</span>
                            <c:if test="${isITunes}">${stampDetails}</c:if>
                            <c:if test="${not empty message}">
                                <script type="text/javascript">
                                    if (document.getElementById("titleHelp"))
                                        document.getElementById("titleHelp").innerHTML = ${message}
                                </script>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${id == 'IMAOpeningClaim'}">
                        <script type="text/javascript">
                            $(".forBankStamp").addClass("forIMAOpeningClaim");
                        </script>
                    </c:if>
                </c:when>
                <c:otherwise>&nbsp;</c:otherwise>
            </c:choose>
            <c:if test="${not empty template}">
                <div id="create-template-row">
                    ${template}
                </div>
                <div class="clear"></div>
            </c:if>
            <c:if test="${not empty reminder}">
                <div id="create-reminder-row">
                   ${reminder}
                </div>
                <div class="clear"></div>
            </c:if>
            <c:if test="${not empty autopay}">
                <div id="create-autopay-row">
                    ${autopay}
                </div>
                <div class="clear"></div>
            </c:if>
            <c:if test="${!confirmCSA}">
                ${buttons}${btnList}
            </c:if>
            <div class="clear"></div>
            <div class="addition-info">
                ${additionInfo}
            </div>
            <div class="clear"></div>
        </tiles:put>
        <tiles:put name="outerData">${outerData}</tiles:put>
        <tiles:put name="rightDataArea">${rightDataArea}</tiles:put>
    </tiles:insert>
</div>
