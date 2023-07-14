<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                xmlns:pu="java://com.rssl.phizic.web.util.PaymentUtil"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                exclude-result-prefixes="xalan mask dh pu spu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
	</xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <initialData>
            <form>AirlineReservationPayment</form>
            <AirlineReservationPayment>
                <xsl:call-template name="payment"/>
            </AirlineReservationPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>AirlineReservationPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <AirlineReservationPaymentDocument>
                <xsl:call-template name="payment"/>

                <xsl:variable name="dispatched" select="state = 'DISPATCHED' or state = 'UNKNOW' or state = 'WAIT_CONFIRM'"/>
                <xsl:variable name="executed" select="state = 'EXECUTED'"/>
                <xsl:variable name="isTicketsStatusSet" select="ticketsStatus != ''"/>

                <xsl:if test="$dispatched or $executed">
                    <ticketsInfo>
                        <ticketsStatus>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">ticketsStatus</xsl:with-param>
                                <xsl:with-param name="title">Статус выпуска билетов</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="ticketsStatus"/>
                            </xsl:call-template>
                        </ticketsStatus>
                        <ticketsStatusDescription>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">ticketsStatusDescription</xsl:with-param>
                                <xsl:with-param name="title">Статус выпуска билетов</xsl:with-param>
                                <xsl:with-param name="description">
                                    <xsl:choose>
                                        <xsl:when test="$dispatched">Ожидание исполнения документа.</xsl:when>
                                        <xsl:when test="$executed and not($isTicketsStatusSet)">Документ исполнен, ожидается информация о билетах.</xsl:when>
                                        <xsl:when test="ticketsStatus = 0">Билеты были выпущены успешно.</xsl:when>
                                        <xsl:when test="ticketsStatus = -1">Платеж принят, билеты будут выпущены позднее. Билеты будет выпущены вручную сотрудниками колл-центра ОАО "Аэрофлот" в течение 3х часов.</xsl:when>
                                        <xsl:when test="ticketsStatus = -2 or ticketsStatus = -3">Невозможно выпустить билеты. Необходимо обратиться в колл-центр ОАО "Аэрофлот".</xsl:when>
                                    </xsl:choose>
                                </xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value">
                                    <xsl:choose>
                                        <xsl:when test="$dispatched">Ожидание оплаты брони</xsl:when>
                                        <xsl:when test="$executed and not($isTicketsStatusSet)">Ожидание выпуска билетов</xsl:when>
                                        <xsl:when test="ticketsStatus = 0">Выпущены</xsl:when>
                                        <xsl:when test="ticketsStatus = -1">Будут выпущены позднее</xsl:when>
                                        <xsl:when test="ticketsStatus = -2 or ticketsStatus = -3">Билеты не выпущены</xsl:when>
                                    </xsl:choose>
                                </xsl:with-param>
                            </xsl:call-template>
                        </ticketsStatusDescription>

                        <xsl:if test="$executed and $isTicketsStatusSet and ticketsStatus = 0">
                            <issueInfo>
                                <xsl:variable name="ticketsInfo" select="document('ticketsInfo.xml')/TicketsInfo"/>
                                <xsl:for-each select="$ticketsInfo/TicketsList/TicketNumber">
                                    <ticketNumber>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name">ticketNumber</xsl:with-param>
                                            <xsl:with-param name="title">Билет</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="."/>
                                        </xsl:call-template>
                                    </ticketNumber>
                                </xsl:for-each>
                                <xsl:if test="string-length($ticketsInfo/ItineraryUrl) > 0">
                                    <itineraryUrl>
                                        <xsl:call-template name="linkField">
                                            <xsl:with-param name="name">itineraryUrl</xsl:with-param>
                                            <xsl:with-param name="title">Детали</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="text">Маршрут-квитанция</xsl:with-param>
                                            <xsl:with-param name="url" select="$ticketsInfo/ItineraryUrl"/>
                                        </xsl:call-template>
                                    </itineraryUrl>
                                </xsl:if>
                            </issueInfo>
                        </xsl:if>

                        <interval>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">interval</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="pu:getAirlineReservationPaymentAsyncInterval()"/>
                            </xsl:call-template>
                        </interval>
                        <timeout>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">timeout</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="pu:getAirlineReservationPaymentAsyncTimeout()"/>
                            </xsl:call-template>
                        </timeout>
                    </ticketsInfo>
                </xsl:if>
            </AirlineReservationPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template name="payment">
        <documentNumber>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">documentNumber</xsl:with-param>
                <xsl:with-param name="title">Номер документа</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="documentNumber"/>
            </xsl:call-template>
        </documentNumber>
        <documentDate>
            <xsl:call-template name="dateField">
                <xsl:with-param name="name">documentDate</xsl:with-param>
                <xsl:with-param name="title">Дата документа</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="documentDate"/>
            </xsl:call-template>
        </documentDate>
        <receiverName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">receiverName</xsl:with-param>
                <xsl:with-param name="title">Получатель</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="receiverName"/>
            </xsl:call-template>
        </receiverName>
        <fromResource>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name">fromResource</xsl:with-param>
                <xsl:with-param name="title">Оплата с</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="$mode = 'edit'"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="cardNumber" select="fromAccountSelect"/>
                <xsl:with-param name="link" select="fromResource"/>
                <xsl:with-param name="isView" select="$mode = 'view'"/>
            </xsl:call-template>
        </fromResource>
        <paymentDetails>
            <amount>
                <xsl:call-template name="moneyField">
                    <xsl:with-param name="name">amount</xsl:with-param>
                    <xsl:with-param name="title">Сумма</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="amount"/>
                </xsl:call-template>
            </amount>
            <currency>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">currency</xsl:with-param>
                    <xsl:with-param name="title">Валюта</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="currency"/>
                </xsl:call-template>
            </currency>
            <recIdentifier>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">RecIdentifier</xsl:with-param>
                    <xsl:with-param name="title">Номер заказа</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="RecIdentifier"/>
                </xsl:call-template>
            </recIdentifier>
        </paymentDetails>

        <xsl:variable name="airlineReservations" select="document('airlineReservation.xml')/AirlineReservation"/>
        <xsl:variable name="reservExpiration" select="$airlineReservations/ReservExpiration/text()"/>
        <xsl:variable name="passengers" select="$airlineReservations/PassengersList"/>
        <xsl:variable name="routes" select="$airlineReservations/RoutesList"/>
        
        <reservationInfo>
            <reservationId>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">reservationId</xsl:with-param>
                    <xsl:with-param name="title">Код брони</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="$airlineReservations/ReservId/text()"/>
                </xsl:call-template>
            </reservationId>

            <xsl:if test="string-length($reservExpiration) > 0">
                <reservExpirationDate>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">reservExpirationDate</xsl:with-param>
                        <xsl:with-param name="title">Действует до</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="dh:formatXsdDateTimeToMobileApiDateTime($reservExpiration)"/>
                    </xsl:call-template>
                </reservExpirationDate>
            </xsl:if>

            <countPassengers>
                <xsl:call-template name="integerField">
                    <xsl:with-param name="name">countPassangers</xsl:with-param>
                    <xsl:with-param name="title">Количество пассажиров</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="count($passengers/Passenger)"/>
                </xsl:call-template>
            </countPassengers>

            <countRoutes>
                <xsl:call-template name="integerField">
                    <xsl:with-param name="name">countRoutes</xsl:with-param>
                    <xsl:with-param name="title">Количество рейсов</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="count($routes/Route)"/>
                </xsl:call-template>
            </countRoutes>
        </reservationInfo>

        <passengersInfo>
            <xsl:for-each select="$passengers/*">
                <passenger>
                    <firstName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">firstName</xsl:with-param>
                            <xsl:with-param name="title">Имя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="./FirstName"/>
                        </xsl:call-template>
                    </firstName>
                    <lastName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">lastName</xsl:with-param>
                            <xsl:with-param name="title">Фамилия</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="./LastName"/>
                        </xsl:call-template>
                    </lastName>
                    <type>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">type</xsl:with-param>
                            <xsl:with-param name="title">Тип пассажира</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value">
                                <xsl:choose>
                                    <xsl:when test="./Type = 'Adult'">Взрослый</xsl:when>
                                    <xsl:when test="./Type = 'Child'">Ребенок</xsl:when>
                                    <xsl:when test="./Type = 'Infant Seat'">Младенец с местом</xsl:when>
                                    <xsl:when test="./Type = 'Infant No Seat'">Младенец без места</xsl:when>
                                    <xsl:when test="./Type = 'Youth'">Молодежь</xsl:when>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </type>
                </passenger>
            </xsl:for-each>
        </passengersInfo>

        <routesInfo>
            <xsl:for-each select="$routes/*">
                <route>
                    <departure>
                        <flight>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">departureFlight</xsl:with-param>
                                <xsl:with-param name="title">Номер рейса</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="./Departure/Flight"/>
                            </xsl:call-template>
                        </flight>
                        <dateTime>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">departureDateTime</xsl:with-param>
                                <xsl:with-param name="title">Вылет</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="dh:formatXsdDateTimeToMobileApiDateTime(./Departure/DateTime)"/>
                            </xsl:call-template>
                        </dateTime>
                        <xsl:if test="string-length(./Departure/Location) > 0">
                            <location>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">departureLocation</xsl:with-param>
                                    <xsl:with-param name="title">Местоположение</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="./Departure/Location"/>
                                </xsl:call-template>
                            </location>
                        </xsl:if>
                        <airport>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">departureAirport</xsl:with-param>
                                <xsl:with-param name="title">Аэропорт</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="./Departure/Airport"/>
                            </xsl:call-template>
                        </airport>
                    </departure>
                    <arrival>
                        <flight>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">arrivalFlight</xsl:with-param>
                                <xsl:with-param name="title">Номер рейса</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="./Arrival/Flight"/>
                            </xsl:call-template>
                        </flight>
                        <dateTime>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">arrivalDateTime</xsl:with-param>
                                <xsl:with-param name="title">Прибытие</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="dh:formatXsdDateTimeToMobileApiDateTime(./Arrival/DateTime)"/>
                            </xsl:call-template>
                        </dateTime>
                        <xsl:if test="string-length(./Arrival/Location) > 0">
                            <location>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">arrivalLocation</xsl:with-param>
                                    <xsl:with-param name="title">Местоположение</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="./Arrival/Location"/>
                                </xsl:call-template>
                            </location>
                        </xsl:if>
                        <airport>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">arrivalAirport</xsl:with-param>
                                <xsl:with-param name="title">Аэропорт</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="./Arrival/Airport"/>
                            </xsl:call-template>
                        </airport>
                    </arrival>
                </route>
            </xsl:for-each>
        </routesInfo>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cardNumber"/> <!--номер карты-->
        <xsl:param name="link"/> <!--код карты-->
        <xsl:param name="isView" select="false()"/> <!--false - редактирование, true - просмотр-->

        <xsl:variable name="activeCardsDictionaryName">
            <xsl:choose>
                <xsl:when test="spu:isCreditCardSupported(recipient)">active-not-virtual-cards.xml</xsl:when>
                <xsl:otherwise>active-not-virtual-not-credit-cards.xml</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="activeCards" select="document($activeCardsDictionaryName)/entity-list/*"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <xsl:if test="count($activeCards)>0">
                        <availableValues>
                            <xsl:for-each select="$activeCards">
                                <xsl:variable name="selected" select="$cardNumber=./@key or $link=./field[@name='code']/text()"/>
                                <xsl:if test="not($isView) or $selected">
                                    <valueItem>
                                        <value><xsl:value-of select="./field[@name='code']/text()"/></value>
                                        <selected><xsl:value-of select="string($selected)"/></selected>
                                        <displayedValue>
                                            <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                        </displayedValue>
                                        <currency><xsl:value-of select="field[@name='currencyCode']"/></currency>
                                    </valueItem>
                                </xsl:if>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>