<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/autopayment/payment/info/print" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="payment" value="${form.payment}"/>
<c:set var="bank" value="${payment.receiverBank}"/>
<c:set var="person" value="${form.activePerson}"/>
<tiles:insert definition="check">
    <tiles:put name="data" type="string">
    <style>
    .checkSize
    {
        width:60mm;
        font-family:Arial;
        font-size:8px;
        word-wrap:break-word;
        text-transform:uppercase;
    }
    .title
    {
        font-weight:bold;
        text-align: center;
    }
    .titleAdditional
    {
        text-align: center;
    }
    .stamp
    {
        border-width:2px;
        font-family:Arial;
        font-size:9px;
        border-style:solid;
        border-color:#025CA2;
        text-align:center;
        font-weight: bold;
        color:#025CA2;
        padding:2mm;
        width:57mm;
    }

    .stamp img
    {
        margin-left: -2mm;
    }

    .mainDiv
    {
       word-wrap:break-all;
       border-width:1px;
       border-style:solid;
       border-color:black;
       width:60mm;
       marign:5mm;
       padding:0mm 3mm 0mm 3mm;
    }
    </style>

    <div id="checkId" class="mainDiv">
        <div class="checkSize title">Сбербанк России ОАО</div><br/>
        <div class="checkSize titleAdditional">чек по операции<br/>
        ПО УСЛУГЕ АВТОПЛАТЕЖ<br/><br/>БЕЗНАЛИЧНАЯ ОПЛАТА УСЛУГ</div>
        <br/>
        
        <div class="checkSize">ДАТА ОПЕРАЦИИ:   <bean:write name="form" property="datePayment" format="dd.MM.yy"/></div>
        <div class="checkSize">ВРЕМЯ ОПЕРАЦИИ (МСК):  <bean:write name="form" property="datePayment" format="HH:mm:ss"/></div>
        <div class="checkSize">ИДЕНТИФИКАТОР ОПЕРАЦИИ:
        ${payment.idFromPaymentSystem}</div>
        <br/>
        <div class="checkSize">КАРТА:  <c:out value="${phiz:getCutCardNumber(payment.chargeOffCard)}"/></div>
        <br/>
        <div class="checkSize">СУММА ОПЕРАЦИИ:  <c:out value="${phiz:formatAmount(payment.amount)}"/></div>
        <div class="checkSize">КОМИССИЯ:
            <c:choose>
                <c:when test="${ not empty payment.commission}">
                     <c:out value="${phiz:formatAmount(payment.commission)}"/>
                </c:when>
                <c:otherwise>
                   0,00 РУБ.
                </c:otherwise>
            </c:choose>
        </div>
        <div class="checkSize">КОД АВТОРИЗАЦИИ:  ${payment.authorizeCode} </div>
        <br/>
        <div class="checkSize">РЕКВИЗИТЫ ПЛАТЕЛЬЩИКА:
        <c:if test="${ not empty person}">
           <br/>${phiz:getFormattedPersonName(person.firstName, person.surName, person.patrName)}
        </c:if>
        </div>
        <c:if test="${ not empty payment.extendedFields}">
            <c:forEach items="${payment.extendedFields}" var="field">
                <c:if test="${field.key}">
                    <c:choose>
                        <c:when test="${not empty field.value}">
                            <c:set var="fieldValue" value="${field.value}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="fieldValue" value="${field.defaultValue}"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="checkSize">${field.name}:
                        <c:choose>
                            <c:when test="${field.type == 'money'}">
                                <span class="bold"><c:out value="${fieldValue}"/>&nbsp;руб.</span>
                            </c:when>
                            <c:when test="${field.type == 'set'}">
                                <c:forTokens delims="@" var="value" items="${fieldValue}">
                                    <span class="bold"><c:out value="${value}"/></span>
                                    <br/>
                                </c:forTokens>
                            </c:when>
                            <c:otherwise>
                               <span class="bold"><c:out value="${fieldValue}"/></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>

        <div class="checkSize">РЕКВИЗИТЫ ПЛАТЕЖА:</div>
        <c:if test="${ not empty payment.extendedFields}">
            <c:forEach items="${payment.extendedFields}" var="field">
                 <c:if test="${(not field.key) && field.requiredForBill}">
                     <c:choose>
                        <c:when test="${not empty field.value}">
                            <c:set var="fieldValue" value="${field.value}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="fieldValue" value="${field.defaultValue}"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="checkSize">${field.name}:
                        <c:choose>
                            <c:when test="${field.type == 'money'}">
                                <span class="bold"><c:out value="${fieldValue}"/>&nbsp;руб.</span>
                            </c:when>
                            <c:when test="${field.type == 'set'}">
                                <c:forTokens delims="@" var="value" items="${fieldValue}">
                                    <span class="bold"><c:out value="${value}"/></span>
                                    <br/>
                                </c:forTokens>
                            </c:when>
                            <c:otherwise>
                               <span class="bold"><c:out value="${fieldValue}"/></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
        <br/><br/><br/>
        <div class="checkSize">
            ПОЛУЧАТЕЛЬ ПЛАТЕЖА:<br/>
            <c:choose>
                <c:when test="${not empty payment.receiverNameForBill}">
                    ${payment.receiverNameForBill}
                </c:when>
                <c:otherwise>
                    ${payment.receiverName}
                </c:otherwise>
            </c:choose>
        </div>
        <br/>
        <div class="checkSize">РЕКВИЗИТЫ ПОЛУЧАТЕЛЯ:</div>
        <c:if test="${ not empty bank}">
            <div class="checkSize">БИК: ${bank.BIC} </div>
        </c:if>
        <c:if test="${ not empty payment.receiverINN}">
            <div class="checkSize">ИНН: ${payment.receiverINN} </div>
        </c:if>
        <c:if test="${ not empty payment.receiverAccount}">
            <div class="checkSize">СЧЕТ: ${payment.receiverAccount} </div>
        </c:if>
        <c:if test="${ not empty bank}">
            <div class="checkSize">КОРР. СЧЕТ: ${bank.account} </div>
        </c:if>
        <br/>
        <div id="stamp" class="stamp title">
                Акционерный коммерческий Сберегательный<br/> банк Российской Федерации (открытое<br/> акционерное общество)
            <br/>
            <img width="137px" src="${imagePath}/stampDispatched_blue.gif">
        </div>

        <br/><br/><br/><br/>
        <div id="standartInfo" class="checkSize titleAdditional">
            по претензиям, связанным со списанием средств со счета, вы можете
            направить заявление по электронной почте
            <br/>(воспользуйтесь формой обратной связи на сайте банка)
            ПО ВОПРОСУ ПРЕДОСТАВЛЕНИЯ УСЛУГИ ОБРАЩАЙТЕСЬ К ПОЛУЧАТЕЛЮ ПЛАТЕЖА
            <c:if test="${not empty payment.receiverPhone}">
                <br/>ПО ТЕЛЕФОНУ: ${payment.receiverPhone}
            </c:if>
        </div>
    </div>

    <script type="text/javascript">
        if (navigator.appName == 'Opera') //чтобы в опере переносились длинные строки
	    {
            var stringLength = 25;     // количество влезающих символов
            var checkDiv = document.getElementById("checkId");
            if (checkDiv != null)
            {
                var divs = checkDiv.getElementsByTagName("div");       // получаем все div'ы, в кот. хранится инфа
                for (var i = 0; i < divs.length; i++)
                {
                    var div = divs[i];
                    if (div.id == "stamp") <%--в URL штампа вставлять пробелы не нужно--%>
                        continue;
                    var contentDiv = div.innerHTML;
                    var lengthContentDiv = div.innerHTML.length;
                    if (lengthContentDiv > stringLength)              // если текст длинный, нужно расставить в нем пробелы (если их там нет)
                    {
                        var newContent = "";
                        for (var j = 0; j < lengthContentDiv; j = j + stringLength)
                        {
                            var partContentDiv = contentDiv.substr(j, stringLength);   // получили кусок текста
                            newContent = newContent + partContentDiv;

                            // если в куске нет пробелов, то ставим в конец пробел (тогда будет автоматический перенос строки)
                            if (!partContentDiv.match(/\s/))
                                newContent = newContent + " ";
                        }
                        div.innerHTML = newContent;
                    }
                }
            }
	    }

        var additionalInfoDiv = document.getElementById("additionalInfo");
        if (additionalInfoDiv != null)
        {
            var standartInfoDiv = document.getElementById("standartInfo");
            var tempInfoHTML  = additionalInfoDiv.innerHTML;              // запомнили текст в одном div
            additionalInfoDiv.innerHTML = standartInfoDiv.innerHTML;      // меняем текст в div'ах
            standartInfoDiv.innerHTML = tempInfoHTML;

        }
    </script>
    </tiles:put>
</tiles:insert>
</html:form>
