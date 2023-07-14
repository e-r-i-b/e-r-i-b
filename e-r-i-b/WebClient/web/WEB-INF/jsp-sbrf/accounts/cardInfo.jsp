<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/cards/info"  onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="accountInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLink" value="${form.cardLink}" scope="request"/>
    <c:set var="cardAbstract" value="${form.cardAbstract}" scope="request"/>
    <c:set var="abstractEmpty" value="${empty cardAbstract || empty cardAbstract.transactions}"/>

    <c:set var="page" value="cardsDetail" scope="request" />
    <c:set var="card"    value="${cardLink.card}"/>
    <c:set var="cardState">${card.cardState}</c:set>
    <c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
    <c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
    <tiles:put name="mainmenu" value="Cards"/>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Карты"/>
            <tiles:put name="action" value="/private/cards/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:write name="cardLink" property="name"/> ${phiz:getCutCardNumber(cardLink.number)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
     </tiles:put>
    <tiles:put name="data" type="string">
        <html:hidden property="id"/>
        <script type="text/javascript">
            function openOrCloseWindowForMainCard(winId)
            {
                var cardIsCredit = '${card.cardType}' == 'credit';
                var cardIsMain = ${!empty card && cardLink.main};
                if (cardIsCredit)
                {
                    addMessage("Выписка не может быть сформирована для кредитной карты. По кредитной карте можно посмотреть только мини-выписку (10 последних операций)");
                    return "";
                }
                else if (!cardIsMain)
                {
                    addMessage("Выписка может быть сформирована только для основной карты. По дополнительной карте можно посмотреть только мини-выписку (10 последних операций)");
                    return "";
                }
                else if (!${form.extendedCardAbstractAvailable})
                {
                    addMessage('Для данной карты Вы не можете получить расширенную выписку');
                    return "";
                }
                else
                {
                    return win.open(winId);
                }
            }

            function openExtendedAbstract(event)
            {

                removeAllErrors('errMessagesBlock');
                
                if (document.getElementById('typeAbstract').checked)
                {
                    var err = validatePeriod("filter(fromAbstract)", "filter(toAbstract)", "Ошибка в формате начальной даты.", "Ошибка в формате конечной даты.",templateObj.ENGLISH_DATE);
                    if (err!="")
                    {
                        $('#errMessagesBlock').css("display","block");
                        addError(err,'errMessagesBlock',true);
                        checkErrors();
                        return; 
                    }
					else
						 $('#errMessagesBlock').css("display","none");
                    checkErrors();
                }
                var url = "${phiz:calculateActionURL(pageContext,'/private/accounts/print.do')}";
                var params = "?sel=c:" + ${form.id};
                var typePeriod = getTypePeriod();
                if (typePeriod == 'period')
                {
                    params = addParam2List(params, "filter(fromAbstract)", "fromDateString");
                    params = addParam2List(params, "filter(toAbstract)", "toDateString");
                }
                else
                {
                    params += "&typePeriod=" + typePeriod;
                }
                openWindow(event,url + params,"PrintAbstract","resizable=1,menubar=1,toolbar=0,scrollbars=1");
                checkErrors();
            }

            function checkErrors() {
                if($('#errMessagesBlock:first').css('display') == 'block') {
                    $('.form-row.active-help:first').removeClass('active-help').addClass('error');
                } else {
                    $('.form-row.error:first').removeClass('error').addClass('active-help');
                }
            }

            function getTypePeriod()
            {
                var typePeriods = document.getElementsByName('filter(typeAbstract)');
                var i = 0;
                while (typePeriods)
                {
                    var el = typePeriods[i++];
                    if (el.checked)
                    {
                        return el.value;
                    }
                }
                return "";
            }

            function openPrintCardInfo(event, abstract)
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/cards/print.do')}";
                var params = "?id=" + ${form.id}+"&printAbstract=" + abstract;

                openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
            }
        </script>
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="data">
                <c:set var="showInMainCheckBox" value="true" scope="request"/>

                <c:set var="nameOrNumber">
                    <c:choose>
                        <c:when test="${not empty cardLink.name}">
                            «${cardLink.name}»
                        </c:when>
                        <c:otherwise>
                            ${phiz:getFormattedAccountNumber(cardNumber)}
                        </c:otherwise>
                    </c:choose>
                </c:set>
                <c:set var="pattern">
                    <c:choose>
                        <c:when test="${not empty cardLink.name}">
                            «${cardLink.patternForFavouriteLink}»
                        </c:when>
                        <c:otherwise>
                            ${cardLink.patternForFavouriteLink}
                        </c:otherwise>
                    </c:choose>
                </c:set>

                <div class="abstractContainer3">
                    <c:set var="baseInfo">
                        <bean:message key="favourite.link.card" bundle="paymentsBundle"/>
                    </c:set>
                    <div class="favouriteLinksButton marginFavouriteLinksButton">
                        <tiles:insert definition="addToFavouriteButton" flush="false">
                            <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                            <tiles:put name="patternName"><c:out value='${baseInfo} ${pattern}'/></tiles:put>
                            <tiles:put name="typeFormat">CARD_LINK</tiles:put>
                            <tiles:put name="productId">${form.id}</tiles:put>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                </div>
                <%@ include file="cardTemplate.jsp"%>
                <div class="tabContainer">
                    <tiles:insert definition="paymentTabs" flush="false">
                        <c:set var="cardNotCreditNotMain" value="${phiz:isCardNotCreditNotMain(card)}"/>
                        <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('card')}"/>
                        <tiles:put name="count" value="4"/>
                        <c:if test="${!cardNotCreditNotMain}">
                            <tiles:put name="count" value="3"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${!cardNotCreditNotMain && !showTemplates}">
                                <tiles:put name="count" value="2"/>
                            </c:when>
                            <c:when test="${cardNotCreditNotMain && showTemplates}">
                                <tiles:put name="count" value="4"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="count" value="3"/>
                            </c:otherwise>
                        </c:choose>
                        <tiles:put name="tabItems">
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="first"/>
                                <tiles:put name="active" value="true"/>
                                <tiles:put name="title" value="Последние операции"/>
                                <tiles:put name="action" value="/private/cards/info?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="active" value="false"/>
                                <tiles:put name="title" value="Информация по карте"/>
                                <c:if test="${!cardNotCreditNotMain && !showTemplates}">
                                    <tiles:put name="position" value="last"/>
                                </c:if>
                                <tiles:put name="action" value="/private/cards/detail.do?id=${cardLink.id}"/>
                            </tiles:insert>
                            <c:if test="${showTemplates}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="active" value="false"/>
                                    <c:if test="${!cardNotCreditNotMain}">
                                        <tiles:put name="position" value="last"/>
                                    </c:if>
                                    <tiles:put name="title" value="Шаблоны и платежи"/>
                                    <tiles:put name="action" value="/private/cards/payments.do?id=${cardLink.id}"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${cardNotCreditNotMain}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="last"/>
                                    <tiles:put name="active" value="false"/>
                                    <tiles:put name="title" value="Графическая выписка"/>
                                    <tiles:put name="action" value="/private/cards/graphic-abstract.do?id=${cardLink.id}"/>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
					<tiles:insert definition="window" flush="false">
						<tiles:put name="id" value="setDate"/>
							<tiles:put name="data">
								<h1>Полная банковская выписка</h1>

								<p class="subtitle"><bean:message bundle="cardInfoBundle" key="info.filter" /></p>

                                <tiles:insert definition="errorBlock" flush="false">
                                    <tiles:put name="regionSelector" value="errMessagesBlock"/>
                                    <tiles:put name="isDisplayed" value="false"/>
                                </tiles:insert>

								<%--Фильтр--%>
								<tiles:insert definition="filterDataPeriod" flush="false">
									<tiles:put name="buttonKey" value="button.filter"/>
									<tiles:put name="buttonBundle" value="cardInfoBundle"/>
									<tiles:put name="windowId" value="setDate"/>
									<tiles:put name="name" value="Abstract"/>
									<tiles:put name="onclick">openExtendedAbstract(event)</tiles:put>
								</tiles:insert>
							</tiles:put>
					</tiles:insert>
					<script type="text/javascript">
						createCommandButton('button.showCardAbstract', 'Выписка');

                        function refreshWindow() {
                            location.reload();
                        }
					</script>

                    <%--Окно с заказом выписки на e-mail--%>
                    <c:set var="abstractUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/cards/sendAbstract.do?id=')}"/>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="sendAbstract"/>
                        <tiles:put name="loadAjaxUrl" value="${abstractUrl}${cardLink.id}"/>
                    </tiles:insert>

                    <%--Результат заказа выписки на e-mail--%>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="sendEmailResult"/>
                        <tiles:put name="data">
                            <html:hidden property="id"/>
                            <div class="sendEmailResult" id="sendEmailResult">
                                <div class="sendEmailImg sendEmailHideBlock">
                                    <img src="${globalUrl}/commonSkin/images/mail.png" width="223" height="103"/>
                                </div>
                                <div class="mailTtl" id="sendEmailResultTitle"><bean:message bundle="cardInfoBundle" key="email.report.result.win.title"/></div>
                                <div class="sendEmailDescInfo sendEmailHideBlock">
                                    <bean:message bundle="cardInfoBundle" key="email.report.result.win.label.description" arg0="${phiz:calculateActionURL(pageContext,'/private/payments/common.do?&status=all')}"/>
                                </div>
                            </div>
                        </tiles:put>
                        <tiles:put name="closeCallback" value="refreshWindow"/>
                    </tiles:insert>

                    <c:set var="cardId" value="${cardLink.id}"/>

                    <div class="abstractContainer2">

                        <c:set var="isAvailableEmailReportDelivery" value="${phiz:impliesService('CardReportDeliveryClaim')}"/>
                        <c:set var="isAvailableEditEmailReportDelivery" value="${isAvailableEmailReportDelivery and cardState != 'delivery' and cardState != 'blocked'}"/>

                        <c:if test="${isAvailableEditEmailReportDelivery}">
                            <c:set var="isExistEmailReportDelivery" value="${cardLink.useReportDelivery}"/>
                            <c:if test="${not isExistEmailReportDelivery}">
                                <c:set var="isAvailableEmailReportDeliveryMessage" value="${not sessionScope.CloseEmailDeliveryMessage[cardId] and phiz:isShowAvailableEmailReportDeliveryMessage()}"/>
                                <c:if test="${isAvailableEmailReportDeliveryMessage}">
                                    <c:set var="closeEmailDeliveryMessageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/cards/closeEmailDeliveryMessage')}"/>
                                    <script type="text/javascript">
                                        function closeEmailDeliveryMessage()
                                        {
                                            $('.b-inline-note').hide();
                                            ajaxQuery("operation=closeEmailDeliveryMessage&cardId=${cardId}", '${closeEmailDeliveryMessageURL}');
                                        }
                                    </script>

                                    <div class="b-inline-note">
                                        <div class="inline-note_inner">
                                            <div class="inline-note_content word-wrap"><c:out value="${phiz:getTextAvailableEmailReportDeliveryMessage()}"/></div>
                                            <div class="inline-note_tr"></div><div class="inline-note_tl"></div>
                                            <div class="inline-note_br"></div><div class="inline-note_bl"></div>
                                        </div>
                                        <i class="inline-note_arr"></i>
                                        <a class="inline-note_close" title="Закрыть" onclick="closeEmailDeliveryMessage();"></a>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:if>

                        <c:if test="${isMain || isVirtual}">
                            <div class="cardEmailReportButton">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.email.report"/>
                                    <tiles:put name="commandHelpKey" value="button.email.report.help"/>
                                    <tiles:put name="bundle" value="cardInfoBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="letter.gif"/>
                                    <tiles:put name="imageHover"     value="letterHover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">removeAllErrors('winErrors');payInput.fieldClearError('fromEmail');payInput.fieldClearError('toEmail');payInput.fieldClearError('email');win.open('sendAbstract');</tiles:put>
                                </tiles:insert>
                            </div>
                        </c:if>

                        <c:if test="${isAvailableEmailReportDelivery}">
                            <script type="text/javascript">
                                <c:set var="getEmailDeliveryParametersURL" value="${phiz:calculateActionURL(pageContext, '/private/async/cards/editEmailDelivery')}"/>

                                <c:if test="${isAvailableEditEmailReportDelivery}">
                                    function startReportDelivery()
                                    {
                                        $('#reportDeliveryFormData').html('');
                                        ajaxQuery("cardId=${cardId}", '${getEmailDeliveryParametersURL}', showReportDeliveryData);
                                    }
                                </c:if>

                                function showReportDeliveryData(data)
                                {
                                    var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                                    if (actualToken != undefined)
                                        $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);

                                    $('#reportDelivery').html(data);
                                    win.open('reportDelivery');
                                }
                                <c:set var="claimId" value="${param['claimId']}"/>
                                <c:if test="${not empty claimId}">
                                    doOnLoad(function(){
                                        $('#reportDeliveryFormData').html('');
                                        ajaxQuery("claimId=${claimId}", '${getEmailDeliveryParametersURL}', showReportDeliveryData);
                                    });
                                </c:if>
                            </script>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="reportDelivery"/>
                                <tiles:put name="data">
                                    <div id="reportDeliveryFormData"></div>
                                </tiles:put>
                            </tiles:insert>
                            <div class="emailReportDeliveryButton">
                                <tiles:insert definition="clientButton" flush="false">
                                    <c:choose>
                                        <c:when test="${isExistEmailReportDelivery}">
                                            <tiles:put name="commandTextKey" value="button.email.report.delivery.exist"/>
                                            <tiles:put name="commandHelpKey" value="button.email.report.delivery.exist.help"/>
                                            <tiles:put name="image"         value="deliveryExist.png"/>
                                            <tiles:put name="imageHover"    value="deliveryExistHover.png"/>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:put name="commandTextKey" value="button.email.report.delivery.not-exist"/>
                                            <tiles:put name="commandHelpKey" value="button.email.report.delivery.not-exist.help"/>
                                            <tiles:put name="image"         value="deliveryNotExist.png"/>
                                            <tiles:put name="imageHover"    value="deliveryNotExistHover.png"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <tiles:put name="bundle" value="cardInfoBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">startReportDelivery();</tiles:put>
                                </tiles:insert>
                            </div>
                        </c:if>

                        <c:if test="${not abstractEmpty}">
                            <div class="cardPrintAccountsButton printButtonRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.print.accounts"/>
                                    <tiles:put name="commandHelpKey" value="button.print.accounts.help"/>
                                    <tiles:put name="bundle" value="cardInfoBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="print-check.gif"/>
                                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">openPrintCardInfo(event, true);</tiles:put>
                                </tiles:insert>
                            </div>
                        </c:if>

                        <c:if test="${card.cardType != 'credit'}">
                            <div class="extendedCardsButton">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.extended.accounts"/>
                                    <tiles:put name="commandHelpKey" value="button.extended.accounts.help"/>
                                    <tiles:put name="bundle" value="cardInfoBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="revizity.gif"/>
                                    <tiles:put name="imageHover"     value="revizityHover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">removeAllErrors('errMessagesBlock');openOrCloseWindowForMainCard('setDate');</tiles:put>
                                </tiles:insert>
                            </div>
                        </c:if>

                    </div>
                    <div class="clear"></div>
                    <c:choose>
                        <c:when test="${not abstractEmpty}">
                            <tiles:insert definition="simpleTableTemplate" flush="false" >
                                <tiles:put name="id" value="detailInfoTable"/>
                                <tiles:put name="grid">
                                    <sl:collection id="transaction" model="simple-pagination" name="cardAbstract"
                                               property="transactions">
                                        <sl:collectionItem styleClass="align-left leftPaddingCell" title="Вид операции, место совершения">
                                            <c:if test="${not empty transaction.description}">
                                                ${transaction.description}
                                            </c:if>
                                            <c:if test="${not empty transaction.shopInfo}">
                                                ${transaction.shopInfo}
                                            </c:if>
                                        </sl:collectionItem>
                                        <sl:collectionItem title="Дата" styleTitleClass="">
                                            ${phiz:formatDateDependsOnSysDate(transaction.date, true, false)}
                                        </sl:collectionItem>
                                        <%--Если creditSum 0, а accountCreditSum не ноль, то выводим accountCreditSum.
                                                   Аналогично для debitSum и  accountDebitSum.--%>
                                        <sl:collectionItem styleClass="align-right" title="Сумма" styleTitleClass="align-right">
                                            <c:set var="credit">
                                                <c:if test="${(empty transaction.creditSum || transaction.creditSum.decimal == 0) && !empty transaction.accountCreditSum && transaction.accountCreditSum.decimal!=0}">
                                                    ${phiz:formatAmount(transaction.accountCreditSum)}
                                                </c:if>
                                                <c:if test="${!empty transaction.creditSum && transaction.creditSum.decimal != 0}">
                                                    ${phiz:formatAmount(transaction.creditSum)}
                                                </c:if>
                                            </c:set>
                                            <c:set var="debit">
                                                <c:choose>
                                                    <c:when test="${empty transaction.debitSum or transaction.debitSum.decimal == 0}">
                                                        <c:if test="${not empty transaction.accountDebitSum and transaction.accountDebitSum.decimal != 0}">
                                                            ${phiz:formatAmount(transaction.accountDebitSum)}
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${phiz:formatAmount(transaction.debitSum)}
                                                        <c:if test="${not empty transaction.accountDebitSum and transaction.accountDebitSum.decimal != 0 and transaction.accountDebitSum.currency.code != transaction.debitSum.currency.code}">
                                                            (${phiz:formatAmount(transaction.accountDebitSum)})
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:set>
                                            <c:choose>
                                                <c:when test="${not empty credit}">
                                                    <span class="greenTextSum">+${credit}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    &minus;${debit}
                                                </c:otherwise>
                                            </c:choose>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <div class="emptyText">
                                <tiles:insert definition="roundBorderLight" flush="false">
                                    <tiles:put name="color" value="greenBold"/>
                                    <tiles:put name="data">
                                        <c:choose>
                                            <%--Если пришла ошибка при получении операций--%>
                                            <c:when test="${!empty form.abstractMsgError}">
                                                <c:choose>
                                                    <c:when test="${cardLink.card.cardState == 'delivery' and fn:containsIgnoreCase(form.abstractMsgError,'Истек срок действия карты')}">
                                                        <c:out value="Подлежит выдаче"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${form.abstractMsgError}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                По данной карте Вы не совершили ни одной операции.
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </tiles:put>
        </tiles:insert>
        <c:if test="${not empty form.otherCards}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <c:set var="cardCount" value="${phiz:getClientProductLinksCount('CARD')}"/>
                    <tiles:put name="title">
                        Остальные карты
                        (<a href="${phiz:calculateActionURL(pageContext, "/private/cards/list.do")}" class="blueGrayLink">показать все ${cardCount}</a>)
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="another-items">
                            &nbsp;
                            <c:forEach items="${form.otherCards}" var="others">
                                <div class="another-container">
                                    <c:choose>
                                        <c:when test="${not empty others.card.cardLevel and others.card.cardLevel == 'MQ'}">
                                            <c:set var="imgSrc" value="mq" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="imgSrc" value="${phiz:getCardImageCode(others.description)}" />
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="${cardInfoUrl}${others.id}">
                                        <img src="${image}/cards_type/icon_cards_${imgSrc}32.gif" alt="${others.description}"/>
                                    </a>
                                    <a href="${cardInfoUrl}${others.id}" class="another-name decoration-none">
                                        <bean:write name="others" property="name"/>
                                    </a>
                                    <span class="another-type uppercase">
                                        <c:set var="virtual" value="${others.card.virtual}"/>
                                        <c:choose>
                                            <c:when test="${others.main  && !virtual}">основная</c:when>
                                            <c:when test="${!others.main && !virtual}">дополнительная</c:when>
                                            <c:otherwise>виртуальная</c:otherwise>
                                        </c:choose>
                                    </span>

                                    <div class="another-number">
                                        <a href="${cardInfoUrl}${others.id}" class="another-number">${phiz:getCutCardNumber(others.number)}</a>
                                        <c:set var="state" value="${others.card.cardState}"/>
                                        <c:set var="className">
                                            <c:if test="${state eq 'blocked' or state eq 'delivery'}">
                                                red
                                            </c:if>
                                        </c:set>

                                        <span class="${className}">
                                            <span class="prodStatus status" style="font-weight:normal;">
                                                <c:if test="${not empty className}">
                                                    <nobr>(${state.description})</nobr>
                                                </c:if>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                            &nbsp;
                            <div class="clear"></div>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>
