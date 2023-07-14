<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:pu="java://com.rssl.phizic.web.util.PaymentUtil"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="skinUrl" select="'skinUrl'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>
    <xsl:param name="userVisitingMode" select="''"/>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
    <xsl:variable name="styleSpecial" select="''"/>

    <xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit' or $mode = 'view'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <xsl:if test="$mode = 'edit'">
                    <input type="hidden" name="recipient" value="{recipient}"/>
                    <input type="hidden" name="receiverId" value="{receiverId}"/>
                </xsl:if>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>
        
       <!--Получатель-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">receiverName</xsl:with-param>
            <xsl:with-param name="rowName">Наименование:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverName"/></b>
                <xsl:if test="$mode = 'edit'">
                    <input type="hidden" name="receiverName" value="{receiverName}"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">payerAccountSelect</xsl:with-param>
            <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$mode = 'edit'">
                            <xsl:variable name="payerAccount" select="fromAccountSelect"/>
                            <xsl:if test="$personAvailable">
                                <select id="fromResource" name="fromResource">
                                    <xsl:if test="$isTemplate = 'true'">
                                        <option value="">Не задан</option>
                                    </xsl:if>

                                    <xsl:variable name="activeCardsDictionaryName">
                                        <xsl:choose>
                                            <xsl:when test="spu:isCreditCardSupported(recipient)">active-not-virtual-cards.xml</xsl:when>
                                            <xsl:otherwise>active-not-virtual-not-credit-cards.xml</xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <xsl:variable name="activeCards" select="document($activeCardsDictionaryName)/entity-list/*"/>

                                    <xsl:choose>
                                        <xsl:when test="count($activeCards) = 0">Нет доступных карт</xsl:when>
                                        <xsl:otherwise>
                                                <xsl:if test="string-length($payerAccount) = 0">
                                                    <option value="">Выберите карту списания</option>
                                                </xsl:if>
                                                <xsl:for-each select="$activeCards">
                                                    <option>
                                                        <xsl:variable name="id"
                                                                      select="concat('card:',field[@name='cardLinkId']/text())"/>
                                                        <xsl:attribute name="value">
                                                            <xsl:value-of select="$id"/>
                                                        </xsl:attribute>
                                                        <xsl:if test="$payerAccount = ./@key">
                                                            <xsl:attribute name="selected">true</xsl:attribute>
                                                        </xsl:if>
                                                        <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                                                        [<xsl:value-of select="./field[@name='name']"/>]
                                                        <xsl:value-of
                                                                select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                                                        <xsl:variable name="currencyCode"><xsl:value-of
                                                                select="./field[@name='currencyCode']"/></xsl:variable>
                                                        <xsl:value-of select="mu:getCurrencySign($currencyCode)"/>
                                                    </option>
                                                </xsl:for-each>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </select>
                            </xsl:if>
                            <xsl:if test="not($personAvailable)">
                                <select id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
                                    <option value="" selected="selected">Счет клиента&nbsp;</option>
                                </select>
                            </xsl:if>

                    </xsl:when>
                    <xsl:otherwise>
                         <b><xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                         &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                         <xsl:variable name="fromResourceCurrency"><xsl:value-of select="fromResourceCurrency"/></xsl:variable>
                         <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/></b>                        
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>


       <!--Детали платежа-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Детали платежа</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>
        
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">amount</xsl:with-param>
            <xsl:with-param name="rowName">Сумма к оплате:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of  select="mu:getCurrencySign(currency)"/></b>
                <xsl:if test="$mode = 'edit'">
                    <input type="hidden" name="amount" value="{amount}"/>
                    <input type="hidden" name="currency" value="{currency}"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">orderId</xsl:with-param>
            <xsl:with-param name="rowName">Номер заказа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="RecIdentifier"/></b>
                <input type="hidden" name="RecIdentifier" value="{RecIdentifier}"/>
            </xsl:with-param>
        </xsl:call-template>


       <!--Информация о брони-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Информация о брони</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="airlineReservations" select="document('airlineReservation.xml')/AirlineReservation"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">reservationId</xsl:with-param>
            <xsl:with-param name="rowName">Код брони:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$airlineReservations/ReservId/text()"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="reservExpiration" select="$airlineReservations/ReservExpiration/text()"/>
        <xsl:if test="string-length($reservExpiration) > 0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">reservExpiration</xsl:with-param>
                <xsl:with-param name="rowName">Действует до:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="sh:formatDateStringAirlinePayment($reservExpiration)"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        
        <xsl:variable name="passangers" select="$airlineReservations/PassengersList"/>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">countPassangers</xsl:with-param>
            <xsl:with-param name="rowName">Количество пассажиров:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="count($passangers/Passenger)"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="routes" select="$airlineReservations/RoutesList"/>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">countRoutes</xsl:with-param>
            <xsl:with-param name="rowName">Количество рейсов:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="count($routes/Route)"/></b>
            </xsl:with-param>
        </xsl:call-template>


       <!--Информация о пассажирах-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Информация о пассажирах</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>

        <xsl:for-each select="$passangers/*"  >
            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">passenger</xsl:with-param>
                <xsl:with-param name="rowName">Пассажир:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="./FirstName"/>&nbsp;<xsl:value-of  select="./LastName"/></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">passengerType</xsl:with-param>
                <xsl:with-param name="rowName">Тип пассажира:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                    <xsl:choose>
                        <xsl:when test="./Type = 'Adult'">Взрослый</xsl:when>
                        <xsl:when test="./Type = 'Child'">Ребенок</xsl:when>
                        <xsl:when test="./Type = 'Infant Seat'">Младенец с местом</xsl:when>
                        <xsl:when test="./Type = 'Infant No Seat'">Младенец без места</xsl:when>
                        <xsl:when test="./Type = 'Youth'">Молодежь</xsl:when>
                        <xsl:otherwise>&nbsp;</xsl:otherwise>
                    </xsl:choose>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:for-each>

       <!--Информация о маршруте-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Информация о маршруте</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>        

        <xsl:for-each select="$routes/*"  >
            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">flightNumber</xsl:with-param>
                <xsl:with-param name="rowName">Номер рейса:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="./Departure/Flight"/></b>
                </xsl:with-param>
            </xsl:call-template>
            
            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">departureDate</xsl:with-param>
                <xsl:with-param name="rowName">Вылет:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="sh:formatDateStringAirlinePayment(./Departure/DateTime)"/></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">airport</xsl:with-param>
                <xsl:with-param name="rowName">Аэропорт:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:if test="string-length(./Departure/Location) > 0">
                            <xsl:value-of  select="./Departure/Location"/>,&nbsp;
                        </xsl:if>
                        <xsl:value-of  select="./Departure/Airport"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="./Departure/Flight != ./Arrival/Flight">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">arrivalFlightNumber</xsl:with-param>
                    <xsl:with-param name="rowName">Номер рейса:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="./Arrival/Flight"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">arrivalDate</xsl:with-param>
                <xsl:with-param name="rowName">Прибытие:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="sh:formatDateStringAirlinePayment(./Arrival/DateTime)"/></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">arrivalAirport</xsl:with-param>
                <xsl:with-param name="rowName">Аэропорт:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:if test="string-length(./Arrival/Location) > 0">
                            <xsl:value-of  select="./Arrival/Location"/>,&nbsp;
                        </xsl:if>
                        <xsl:value-of  select="./Arrival/Airport"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:for-each>

        <!--Информация о билетах-->
        <xsl:if test="$mode = 'view' and state != 'SAVED'">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName"><b>Информация о билетах</b></xsl:with-param>
                <xsl:with-param name="rowValue">
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="ticketsInfoXml" select="document('ticketsInfo.xml')"/>
        <xsl:variable name="dispatched" select="state = 'DISPATCHED' or state = 'UNKNOW' or state = 'WAIT_CONFIRM'"/>
        <xsl:variable name="ticketsWaiting" select="state = 'TICKETS_WAITING'"/>
        <xsl:variable name="executed" select="state = 'EXECUTED'"/>
        <xsl:variable name="ticketsStatus"><xsl:if test="string-length($ticketsInfoXml/TicketsInfo) > 0"><xsl:value-of select="$ticketsInfoXml/TicketsInfo/TicketsStatus"/></xsl:if></xsl:variable>
        <xsl:variable name="ticketsDescription"><xsl:if test="string-length($ticketsInfoXml/TicketsInfo) > 0"><xsl:value-of select="$ticketsInfoXml/TicketsInfo/TicketsDescription"/></xsl:if></xsl:variable>

        <xsl:if test="$dispatched or $ticketsWaiting or $executed">
            <xsl:if test="not($executed)  and $personAvailable">
                <div id="preloader" align="center">
                    <img src="{concat($skinUrl, '/images/ajaxLoader.gif')}" alt="Please, wait."/>
                </div>
            </xsl:if>

            <xsl:if test="$executed and $ticketsStatus = 0">
                <xsl:for-each select="$ticketsInfoXml/TicketsInfo/TicketsList/TicketNumber">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="id">ticketNumber</xsl:with-param>
                        <xsl:with-param name="rowName">Билет:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="."/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">ticketsStatus</xsl:with-param>
                <xsl:with-param name="lineId">ticketsStatusLineId</xsl:with-param>
                <xsl:with-param name="rowName">Состояние билетов:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><div id="ticketsStatus">
                        <span onmouseover="showLayer('ticketsStatus','ticketsStatusDescription');" onmouseout="hideLayer('ticketsStatusDescription');" class="link">
                            <xsl:choose>
                                <xsl:when test="$dispatched">Ожидание оплаты брони</xsl:when>
                                <xsl:when test="$ticketsWaiting">Ожидание выпуска билетов</xsl:when>
                                <xsl:when test="$ticketsStatus = 0">Выпущены</xsl:when>
                                <xsl:when test="$ticketsStatus = -1">Будут выпущены позднее</xsl:when>
                                <xsl:when test="$ticketsStatus = -2 or $ticketsStatus = -3">Билеты не выпущены</xsl:when>
                            </xsl:choose>
                        </span>
                    </div></b>
                    <div id="ticketsStatusDescription" onmouseover="showLayer('ticketsStatus','ticketsStatusDescription','default');" onmouseout="hideLayer('ticketsStatusDescription');" class="layerFon stateDescription">
                        <div class="floatMessageHeader"></div>
                        <div class="layerFonBlock">
                            <xsl:choose>
                                <xsl:when test="$dispatched">Ожидание исполнения документа.</xsl:when>
                                <xsl:when test="$ticketsWaiting">Документ исполнен, ожидается информация о билетах.</xsl:when>
                                <xsl:when test="$ticketsDescription != ''"><xsl:value-of select="$ticketsDescription"/></xsl:when>
                                <xsl:when test="$ticketsStatus = 0">Билеты были выпущены успешно.</xsl:when>
                                <xsl:when test="$ticketsStatus = -1">Платеж принят, билеты будут выпущены позднее. Билеты будет выпущены вручную сотрудниками колл-центра ОАО "Аэрофлот" в течение 3х часов.</xsl:when>
                                <xsl:when test="$ticketsStatus = -2 or $ticketsStatus = -3">Невозможно выпустить билеты. Необходимо обратиться в колл-центр ОАО "Аэрофлот".</xsl:when>
                            </xsl:choose>
                        </div>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="$executed and $ticketsStatus = 0">
                <xsl:if test="string-length($ticketsInfoXml/TicketsInfo/ItineraryUrl) > 0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="id">ticketsItineraryUrl</xsl:with-param>
                        <xsl:with-param name="rowName">Детали:</xsl:with-param>
                        <xsl:with-param name="rowValue"><a href="{$ticketsInfoXml/TicketsInfo/ItineraryUrl}" target="_blank" class="decoration-none"><b><span class="blueGrayLink">Маршрут-квитанция</span></b></a></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:if>

            <xsl:variable name="ajaxInterval" select="pu:getAirlineReservationPaymentAsyncInterval()"/>
            <xsl:variable name="ajaxTimeout" select="pu:getAirlineReservationPaymentAsyncTimeout()"/>

            <xsl:if test="$personAvailable">
                <xsl:variable name="ajaxUrlPrefix">
                    <xsl:choose>
                        <xsl:when test="$userVisitingMode = 'BASIC'">/private</xsl:when>
                        <xsl:when test="$userVisitingMode = 'PAYORDER_PAYMENT'">/external</xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <xsl:choose>
                    <!--Если статус "Обрабатывается", то крутим прелоудер, периодически проверяя статус платежа в ЕРИБ, и если он изменился (Исполнен или Отказан), то перерисовываем форму-->
                    <xsl:when test="$dispatched">
                        <script type="text/javascript">
                            $(document).ready(function() {
                                currentState = '<xsl:value-of select="state"/>';
                                setIntervalId = startAjaxInterval("<xsl:value-of select="$ajaxUrlPrefix"/>" + "/async/payment/state.do", reloadIfPaymentStateUpdated);
                            });

                            var currentState;
                            var setIntervalId;

                            function reloadIfPaymentStateUpdated(data) {
                                if (data == currentState)
                                    return;
                                window.location.reload();
                            }


                        </script>
                    </xsl:when>
                    <!--Если статус "Исполнен" и статус выпуска билетов еще не установлен, то крутим прелоудер, периодически проверяя статус выпуска билетов, и если он изменился, то перерисовываем форму-->
                    <xsl:when test="$ticketsWaiting">
                        <script type="text/javascript">
                            $(document).ready(function() {
                                onExecuted();
                            });

                            var setIntervalId;

                            function onExecuted() {
                                setIntervalId = startAjaxInterval("<xsl:value-of select="$ajaxUrlPrefix"/>" + "/async/tickets/status.do", reloadIfTicketsStatusUpdated);
                                <!--заранее выставляем таймаут для ajax-запросов-->
                                var timeout = <xsl:value-of select="$ajaxTimeout"/>*1000;
                                window.setTimeout(
                                        function() {
                                            stopAjaxInterval(setIntervalId);
                                            showTicketsStatus("Информация о билетах не была получена"); },
                                        timeout);
                            }

                            function reloadIfTicketsStatusUpdated(data) {
                                if (data == "ASYNC_GET_TICKETS_STATE_NOT_ASSIGNED")
                                    return;

                                if (data == "ASYNC_GET_TICKETS_STATE_ASSIGNED")
                                    window.location.reload();
                            }

                            <!--Обновить поле со статусом-->
                            function showTicketsStatus(message) {
                                var ticketsStatusRow = document.getElementById("ticketsStatusLineId");
                                var span = findChildByClassName(ticketsStatusRow, "link");
                                if (span != null)
                                    span.innerHTML = message;
                            }
                        </script>
                    </xsl:when>
                </xsl:choose>
                <script type="text/javascript">
                    <!--Запустить ajax-запросы по урлу ajaxUrl с установленным в настройках интервалом-->
                    function startAjaxInterval(ajaxUrl, callback) {
                        var url = document.webRoot + ajaxUrl;
                        var params = "id=" + getQueryStringParameter("id");
                        var interval = <xsl:value-of select="$ajaxInterval"/>*1000;
                        return window.setInterval(
                                function() { ajaxQuery(params, url, callback, null, false) },
                                interval);
                    }

                    <!--Остановить ajax-запросы, запущенные в startAjaxInterval-->
                    function stopAjaxInterval(setIntervalId) {
                        window.clearInterval(setIntervalId);

                        var preloader = document.getElementById("preloader");
                        if(preloader != null)
                            preloader.style.display = "none";
                    }
                    $(document).ready(function() {
                        var parentText = $('#ticketsStatus').find('.link');
                        var descriptionHint = $('#ticketsStatusDescription');
                        showMainHint(parentText, descriptionHint);
                    });
                </script>
            </xsl:if>
        </xsl:if>


        <xsl:if test="$mode = 'view'">
            <xsl:if test="string-length(commission)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                            <input name="commission" type="hidden" value="{commission}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><div id="state">
                        <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                            <xsl:choose>
                                <xsl:when test="$app = 'PhizIA'">
                                    <xsl:call-template name="employeeState2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="clientState2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>

                        </span>
                    </div></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        
    </xsl:template>

    <!-- шаблон формирующий div описания -->
    <xsl:template name="buildDescription">
       <xsl:param name="text"/>

       <xsl:variable name="delimiter">
       <![CDATA[
       <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
       <div class="detail" style="display: none">
       ]]>
       </xsl:variable>

       <xsl:variable name="firstDelimiter">
       <![CDATA[
       <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
       <div class="detail" style="display: none">
       ]]>
       </xsl:variable>

       <xsl:variable name="end">
       <![CDATA[ </div>
       ]]>
       </xsl:variable>

       <div class="description" style="display: none">

        <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

       <xsl:for-each select="$nodeText/node()">

       <xsl:choose>
            <xsl:when test=" name() = 'cut' and position() = 1 ">
                <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
            </xsl:when>
            <xsl:when test="name() = 'cut' and position() != 1">
                <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
            </xsl:when>
            <xsl:otherwise>
            <xsl:copy />
            </xsl:otherwise>
       </xsl:choose>
       </xsl:for-each>

       <xsl:if test="count($nodeText/cut) > 0">
       <xsl:value-of select="$end" disable-output-escaping="yes"/>
       </xsl:if>
        </div>

    </xsl:template>    

    <xsl:template name="standartRow">

        <xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--идентификатор строки-->
        <xsl:param name="required" select="'false'"/>    <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->

        <xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
        <!-- Определение имени инпута или селекта передаваемого в шаблон -->
        <!-- inputName - fieldName или имя поле вытащеное из rowValue -->
        <xsl:variable name="inputName">
        <xsl:choose>
            <xsl:when test="string-length($fieldName) = 0">
                    <xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
                        <xsl:value-of select="$nodeRowValue/input/@name" />
                        <xsl:if test="count($nodeRowValue/select[@name]) = 1">
                            <xsl:value-of select="$nodeRowValue/select/@name" />
                        </xsl:if>
                        <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
                            <xsl:value-of select="$nodeRowValue/textarea/@name" />
                        </xsl:if>
                    </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                    <xsl:copy-of select="$fieldName"/>
            </xsl:otherwise>
        </xsl:choose>
        </xsl:variable>

        <xsl:variable name="readonly">
            <xsl:choose>
                <xsl:when test="string-length($inputName)>0">
                    <xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
                </xsl:when>
                <xsl:otherwise>
                    0
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

     <xsl:variable name="styleClass">
		<xsl:choose>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

    <div class="{$styleClass}">
        <xsl:if test="string-length($lineId) > 0">
            <xsl:attribute name="id">
                <xsl:copy-of select="$lineId"/>
            </xsl:attribute>
        </xsl:if>
        <xsl:if test="string-length($rowStyle) > 0">
            <xsl:attribute name="style">
                <xsl:copy-of select="$rowStyle"/>
            </xsl:attribute>
        </xsl:if>
        <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
            <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
        </xsl:if>

        <div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
            <xsl:if test="$required = 'true' and $mode = 'edit'">
                <span id="asterisk_{$id}" class="asterisk">*</span>
            </xsl:if>
        </div>
        <div class="paymentValue">
                    <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

                    <xsl:if test="$readonly = 0 and $mode = 'edit'">
                        <xsl:call-template name="buildDescription">
                            <xsl:with-param name="text" select="$description"/>
                        </xsl:call-template>
                    </xsl:if>
                    <div class="errorDiv" style="display: none;">
                    </div>
        </div>
        <div class="clear"></div>
    </div>

    <!-- Устанавливаем события onfocus поля -->
        <xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
            <script type="text/javascript">
            if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
            document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
            </script>
        </xsl:if>

    </xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
        </div>
    </xsl:template>

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='TICKETS_WAITING'">Ожидание билетов</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='TICKETS_WAITING'">Исполнен</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        </xsl:choose>
    </xsl:template>
    

</xsl:stylesheet>