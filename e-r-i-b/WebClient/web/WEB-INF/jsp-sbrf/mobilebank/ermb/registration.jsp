<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath"       value="${skinUrl}/images"/>
<c:set var="isERMBProfileBlocked" value="${not form.profile.serviceStatus || form.profile.clientBlocked}"/>
<c:set var="styleParam" value=""/>
<c:if test="${not empty form.confirmableObject}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="isERMBProfileBlocked" value="true"/>
</c:if>
<c:if test="${isERMBProfileBlocked}">
    <c:set var="styleParam" value="disabled"/>
</c:if>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>

<%-- Подключение --%>
<c:set var="gracePeriodEndDate" value="${phiz:сalendarToString(form.gracePeriodEndDate)}"/>

<div>
    <div class="buttonsArea">
        <fieldset>
            <table class="additional-product-info firstColumnFix">
                <%--Дата подключения--%>
                <tr>
                    <td class="align-right field fixColumn">Дата подключения:</td>
                    <td><span class="bold">${phiz:сalendarToString(form.profile.connectionDate)}</span></td>
                </tr>
                <%--Тариф--%>
                <tr>
                    <td class="align-right field">Тариф:</td>
                    <td>
                        <div class="float">
                            <html:select property="selectedTarif" disabled="${isERMBProfileBlocked}"
                                         styleClass="productSelect">
                                <c:forEach items="${form.tarifs}" var="tarif">
                                    <html:option value="${tarif.id}">
                                        <c:out value="${tarif.name}"/>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                            <c:if test="${!isERMBProfileBlocked}">
                                <span class="float">
                                    <a class="selectAddition float blueGrayLinkDotted" onclick="openTarifInfoWindow(); return false;">все тарифы</a>
                                </span>
                            </c:if>
                        </div>
                        <%--Окно с описанием действующих тарифов--%>
                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="tarifDescription"/>
                            <tiles:put name="data">
                                <%@include file="tarifDescription.jsp" %>
                            </tiles:put>
                        </tiles:insert>
                    </td>
                </tr>
                <%--Дата окончания льготного периода--%>
                <c:if test="${not empty gracePeriodEndDate}">
                    <tr>
                        <td class="align-right field">Дата окончания льготного периода:</td>
                        <td><span class="bold">${gracePeriodEndDate}</span></td>
                    </tr>
                </c:if>
                <%--Основная карта для списания платы--%>
                <tr>
                    <td class="align-right field">Основная карта для списания платы:</td>
                    <td>
                        <html:select property="mainCardId" styleClass="productSelect" disabled="${isERMBProfileBlocked}" style="width:300px;">
                            <html:option value="">Все</html:option>
                            <c:forEach items="${form.possiblePaymentCards}" var="card">
                                <html:option value="${card.id}" >
                                    <c:out value="${card.name} ${phiz:getCutCardNumber(card.number)} - ${phiz:formatAmount(item.card.availableLimit)} ${phiz:getCurrencySign(card.currency.code)}"/>
                                </html:option>
                            </c:forEach>
                        </html:select>
                    </td>
                </tr>
                <%--Телефоны клиента--%>
                <c:if test="${not empty form.codesToPhoneNumber}">
                    <tr>
                        <td class="align-right field">Телефоны:</td>
                        <td>
                            <table width="100%">
                                <html:hidden property="mainPhoneNumberCode"/>
                                <c:choose>
                                    <c:when test="${isERMBProfileBlocked}">
                                        <c:forEach var="phone" items="${form.codesToPhoneNumber}">
                                            <tr>
                                                <td>
                                                    <c:out value="${phone.value}"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="phone" items="${form.codesToPhoneNumber}">
                                            <c:set var="phoneNumberCode" value="${phone.key}"/>
                                            <tr id="phoneRow_${phoneNumberCode}">
                                                <html:hidden property="codesToPhoneNumber(${phoneNumberCode})" value="${phoneNumberCode}"/>
                                                <td style="width:30%">
                                                    <div>
                                                        <c:out value="${phone.value}"/>
                                                    </div>
                                                </td>
                                                <td style="width:46%">
                                                    <c:choose>
                                                        <c:when test="${phone.key == form.mainPhoneNumberCode}">
                                                            <span id="phone_${phoneNumberCode}" onclick="selectMainPhone(${phoneNumberCode});" class="normal">
                                                                <c:out value="основной"/>
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span id="phone_${phoneNumberCode}" onclick="selectMainPhone(${phoneNumberCode});" class="selectOperationsTypeActive blueGrayLinkDotted">
                                                                <c:out value="использовать как основной"/>
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <c:set var="displayButtonRemove" value="none"/>
                                                <c:if test="${phone.key != form.mainPhoneNumberCode}">
                                                    <c:set var="displayButtonRemove" value="block"/>
                                                </c:if>
                                                <td id="deletePhone_${phoneNumberCode}" style="display: ${displayButtonRemove}">
                                                    <span id="deletePhone_${phoneNumberCode}" onclick="removePhone(${phoneNumberCode});" class="selectOperationsTypeActive blueGrayLinkDotted">
                                                        <c:out value="удалить"/>
                                                    </span>
                                                    <%--Окно с уведомлением об удалении номера телефона--%>
                                                    <tiles:insert definition="window" flush="false">
                                                        <tiles:put name="id" value="removePhoneNumber"/>
                                                        <tiles:put name="data">
                                                            <h2>Подтверждение операции</h2>
                                                            <p class="confirmText">Удалить этот телефон?</p>
                                                            <div class="buttonsArea">
                                                                <tiles:insert definition="clientButton" flush="false">
                                                                    <tiles:put name="commandTextKey" value="button.cancel"/>
                                                                    <tiles:put name="commandHelpKey" value="button.close.help"/>
                                                                    <tiles:put name="bundle" value="mailBundle"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                    <tiles:put name="onclick" value="win.close('removePhoneNumber')"/>
                                                                </tiles:insert>

                                                                <tiles:insert definition="clientButton" flush="false">
                                                                    <tiles:put name="commandTextKey" value="button.remove"/>
                                                                    <tiles:put name="commandHelpKey" value="button.remove"/>
                                                                    <tiles:put name="bundle" value="mailBundle"/>
                                                                    <tiles:put name="onclick" value="removePhones(); win.close('removePhoneNumber')"/>
                                                                </tiles:insert>
                                                                <div class="clear"></div>
                                                            </div>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert definition="window" flush="false">
                                                        <tiles:put name="id" value="canNotRemovePhoneNumber"/>
                                                        <tiles:put name="data">
                                                            <h2>Подтверждение операции</h2>
                                                            <p class="confirmText">Невозможно удалить телефон.</p>
                                                            <div class="buttonsArea">
                                                                <tiles:insert definition="clientButton" flush="false">
                                                                    <tiles:put name="commandTextKey" value="button.close"/>
                                                                    <tiles:put name="commandHelpKey" value="button.close.help"/>
                                                                    <tiles:put name="bundle" value="mailBundle"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                    <tiles:put name="onclick" value="win.close('canNotRemovePhoneNumber')"/>
                                                                </tiles:insert>
                                                                <div class="clear"></div>
                                                            </div>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                        </td>
                    </tr>
                </c:if>
                <script type="text/javascript">

                    var phoneRemove = 0;

                    function selectMainPhone(newMainPhoneNumber)
                    {
                        var newPhoneLabel = 'phone_'+ newMainPhoneNumber;
                        var actualPhoneNumber = $("input[name^='mainPhoneNumber']").val();

                        $("span[id^='phone_']").attr('className', 'selectOperationsTypeActive blueGrayLinkDotted');
                        $("span[id^='phone_']").text('использовать как основной');
                        getLightText();

                        $("span[id^='"+newPhoneLabel+"']").attr('className', 'normal');
                        $("span[id^='"+newPhoneLabel+"']").text('основной');
                        $("input[name^='mainPhoneNumber']").attr("value", newMainPhoneNumber);

                        $("td[id^='deletePhone_"+actualPhoneNumber+"']").attr('style', 'display: block;');
                        $("td[id^='deletePhone_"+newMainPhoneNumber+"']").attr('style', 'display: none;');
                    }

                    function removePhone(removedPhone)
                    {
                        var actualPhoneNumber = $("input[name^='mainPhoneNumber']").val();
                        if (actualPhoneNumber != removedPhone)
                        {
                            phoneRemove = removedPhone;
                            win.open('removePhoneNumber');

                        }
                        else
                            win.open('canNotRemovePhoneNumber');
                    }
                    function removePhones()
                    {
                        var activePhone = 'phoneRow_'+ phoneRemove;
                           $("tr[id^='"+activePhone+"']").remove();
                    }
                </script>
                <%--График рассылки оповещений--%>
                <tr>
                    <td class="align-right field">График оповещений:</td>
                    <td>
                        <div class="float"><span class="bold textPeriod firstPeriod">с</span></div>
                        <div class="float">
                            <input type="text" ${styleParam} name="ntfStartTimeString" size="5" maxlength="5" class="short-time-template"
                                   value="<bean:write name="form" property="ntfStartTimeString" format="HH:MM"/>"/>
                        </div>
                        <div class="float"><span class="bold textPeriod">до</span></div>
                        <div class="float">
                            <input type="text" ${styleParam} name="ntfEndTimeString" size="5"  maxlength="5" class="short-time-template"
                                   value="<bean:write name="form" property="ntfEndTimeString" format="HH:MM"/>"/>
                        </div>
                    </td>
                </tr>
                <%--Часовой пояс--%>
                <tr>
                    <td class="align-right field"></td>
                    <td>
                        <c:set var="clientTimeZone" value="${form.timeZone}"/>
                        <html:select property="timeZone" styleClass="select" disabled="${isERMBProfileBlocked}">
                            <c:forEach var="timeZone" items="${form.timeZoneList}">
                                <html:option value="${timeZone.code}">
                                    ${timeZone.text}
                                </html:option>
                            </c:forEach>
                        </html:select>
                    </td>
                </tr>
                <%--Выбор дней недели для графика оповещений--%>
                <tr>
                    <td class="align-right field"></td>
                    <td class="noButton">
                        <html:hidden property="notificationDays"/>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.monday"/>
                            <tiles:put name="commandHelpKey" value="btn.monday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="MON"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.tuesday"/>
                            <tiles:put name="commandHelpKey" value="btn.tuesday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="TUE"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.wednesday"/>
                            <tiles:put name="commandHelpKey" value="btn.wednesday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="WED"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.thursday"/>
                            <tiles:put name="commandHelpKey" value="btn.thursday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="THU"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.friday"/>
                            <tiles:put name="commandHelpKey" value="btn.friday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="FRI"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.saturday"/>
                            <tiles:put name="commandHelpKey" value="btn.saturday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="SAT"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="btn.sunday"/>
                            <tiles:put name="commandHelpKey" value="btn.sunday"/>
                            <tiles:put name="onclick" value="editSchedule(this.id);"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="btnId" value="SUN"/>
                            <tiles:put name="viewType" value="buttonLightGray"/>
                            <tiles:put name="enabled" value="${!isERMBProfileBlocked}"/>
                        </tiles:insert>
                    </td>

                    <script type="text/javascript">
                        doOnLoad(setClass);
                        function setClass()
                        {
                            var noteDays = getElement("notificationDays").value;
                            var noteDaysArray = noteDays.split(',');
                            for (var i=0; i<noteDaysArray.length; i++)
                            {
                                if (noteDaysArray[i] != "")
                                    $('#'+noteDaysArray[i]).attr('class', 'buttonGreen');
                            }
                        }
                        function editSchedule(param)
                        {
                            var btnId = '#' +param;
                            var noteDays = getElement("notificationDays").value;
                            if (noteDays.match(param))
                            {
                                noteDays = noteDays.replace(param, '');
                                $(btnId).attr('class', 'buttonLightGray');
                            }
                            else
                            {
                                noteDays = noteDays + ',' + param ;
                                $(btnId).attr('class', 'buttonGreen');
                            }
                            noteDays = noteDays.replace(/,$/, '');
                            noteDays = noteDays.replace(/^,*/, '');
                            noteDays = noteDays.replace(/,{2,}/, ',');

                            setElement("notificationDays", noteDays);
                        }

                        function checkAndUpdate()
                        {
                            var fromTime = $('input[name$="ntfStartTimeString"]').val();
                            var toTime = $('input[name$="ntfEndTimeString"]').val();
                            var isTimePeriodValid = validateTimePeriod(fromTime, toTime);

                            return isTimePeriodValid;
                        }

                        function openTarifInfoWindow()
                        {
                            win.open('tarifDescription');
                        }
                    </script>
                </tr>
                <tr>
                    <td><h2 class="longTitleTable">Настройка оповещений</h2></td>
                </tr>
                <tr>
                    <td class="align-right field">Проценты по счетам:</td>
                    <td>
                        <html:hidden property="depositsTransfer"/>
                        <span id="depositsTransferOn" onclick="changeNotification('depositsTransfer', true);">
                            <bean:message key="title.depositsTransfer.on" bundle="ermbBundle"/>
                        </span>
                        <span id="depositsTransferOff" onclick="changeNotification('depositsTransfer', false);">
                            <bean:message key="title.depositsTransfer.off" bundle="ermbBundle"/>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="align-right field">Быстрый платеж:</td>
                    <td>
                        <html:hidden property="fastServiceAvailable"/>
                        <span id="fastServiceAvailableOn" onclick="changeNotification('fastServiceAvailable', true);">
                            <bean:message key="title.fastServiceAvailable.on" bundle="ermbBundle"/>
                        </span>
                        <span id="fastServiceAvailableOff" onkeydown="" onclick="changeNotification('fastServiceAvailable', false);">
                            <bean:message key="title.fastServiceAvailable.off" bundle="ermbBundle"/>
                        </span>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>

    <script type="text/javascript">
        doOnLoad(setNotifications);
        function setNotifications()
        {
            setNotification('depositsTransfer');
            setNotification('fastServiceAvailable');
        }
        function setNotification(param)
        {
            var paramVal = $("input[name^='"+param+"']").val();
            var isERMBProfileBlocked = ${isERMBProfileBlocked};

            if (paramVal == 'true')
            {
                $("span[id^='"+param+"On']").attr('className','bold');
                $("span[id^='"+param+"Off']").attr('className', isERMBProfileBlocked ? 'displayNone' : 'selectOperationsTypeActive blueGrayLinkDotted');
                getLightText();
            }
            else
            {
                $("span[id^='"+param+"On']").attr('className', isERMBProfileBlocked ? 'displayNone' : 'selectOperationsTypeActive blueGrayLinkDotted');
                getLightText();
                $("span[id^='"+param+"Off']").attr('className', 'bold');
            }
        }
        function changeNotification(param, value)
        {
            $("input[name^='"+param+"']").attr("value", value);
            setNotification(param);
        }

        function getLightText()
        {
            $('.selectOperationsTypeActive.blueGrayLinkDotted').hover(function(){
                $(this).addClass('linkHoverDotted');
                }, function() {
                    $(this).removeClass('linkHoverDotted');
            });
        }
    </script>

    <div class="grayUnderlineDotted"></div>
    <h2 class="inline">Продукты</h2>
    <c:set var="productViewLink" value="/private/userprofile/accountsSystemView"/>
    <c:if test="${phiz:impliesService('NewClientProfile')}">
        <c:set var="productViewLink" value="/private/userprofile/accountSecurity.do?needOpenTab=productView"/>
    </c:if>
    <html:link action="${productViewLink}" styleClass="blueGrayLink">
        <bean:message key="button.goto.product.view" bundle="ermbBundle"/>
    </html:link>
    <div class="note">
        Здесь Вы можете просмотреть список продуктов, которые доступны для операций, выполняемых через SMS.
    </div>

    <%@ include file="accountsViewInSys.jsp"%>

    <%--Кнопки сохранения и отмены--%>
        <div class="buttonsArea">
            <c:choose>
                <c:when test="${not empty confirmRequest}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.backToEdit"/>
                        <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="action" value="/private/mobilebank/ermb/main.do"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                    </tiles:insert>
                    <tiles:insert definition="confirmButtons" flush="false">
                        <tiles:put name="ajaxUrl" value="/private/async/mobilebank/ermb/main"/>
                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                        <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                        <tiles:put name="preConfirmCommandKey" value="button.preConfirm"/>
                        <tiles:put name="anotherStrategy" value="false"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <c:if test="${!isERMBProfileBlocked}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="action" value="/private/mobilebank/ermb/main.do"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="validationFunction">checkAndUpdate();</tiles:put>
                        </tiles:insert>
                    </c:if>
                </c:otherwise>
            </c:choose>
            <div class="clear"></div>
        </div>
</div>

