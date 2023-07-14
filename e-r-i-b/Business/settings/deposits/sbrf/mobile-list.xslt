<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                exclude-result-prefixes="xalan mu">

    <xsl:output method="html" indent="yes" encoding="windows-1251" version="1.0"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="curDate"/>
    <xsl:param name="admin"/>
    <xsl:param name="isPension"/>
    <xsl:param name="needInitialFee"/>
    <xsl:param name="tariff"/>
    <xsl:param name="showNewDeposits"/>
	<xsl:param name="isATM"/>

	<xsl:template match="/">
        <!--Все возможные группы всех вкладных продуктов-->
        <xsl:variable name="groupCodes" select="xalan:distinct(/products/product[data/main/initialFee != string($needInitialFee)]/data/options/element/group/groupCode)"/>
        <!--Доступные для открытия коды групп, соответствующие старым вкладам-->
        <xsl:variable name="allowedGroupCodes" select="document('old-deposit-codes.xml')/entity-list/entity"/>

        <xsl:for-each select="/products/product[data/main/initialFee != string($needInitialFee)]">
			<xsl:variable name="product" select="."/>
            <xsl:variable name="dateFilteredElements" select="./data/options/element[translate(dateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(dateEnd, '.', '')&gt;translate($curDate, '.', '') and translate(interestDateBegin, '.', '')&lt;=translate($curDate, '.', '') and ($admin or availToOpen='true') and (segmentCode = 0 or segmentCode = 1)]"/>

            <xsl:variable name="productType" select="$product/data/productId"/>

            <xsl:for-each select="$groupCodes">
                <xsl:variable name="groupCode" select="."/>

                <!--Проверяем, доступна ли группа для отображения клиенту-->
                <xsl:variable name="allowThisGroup">
                    <xsl:choose>
                        <!--Только если не нужно показывать новые вклады-->
                        <xsl:when test="not($showNewDeposits) and count($allowedGroupCodes) > 0">
                            <xsl:variable name="allowedGroupCode" select="$allowedGroupCodes[field[@name = 'deposit' and text() = $productType]]/field[@name = 'groupCode']/text()"/>
                            <xsl:if test="$allowedGroupCode = $groupCode">
                                <xsl:value-of select="'true'"/>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'true'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <xsl:if test="$allowThisGroup = 'true'">
                    <xsl:variable name="groupFilteredElements" select="$dateFilteredElements[group/groupCode=$groupCode]"/>

                    <xsl:variable name="clientTariff">
                        <xsl:choose>
                            <xsl:when test="count($groupFilteredElements[tariffPlanCode=$tariff]) > 0">
                                <xsl:value-of select="$tariff"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="'0'"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <!--Не пенсионные ставки-->
                    <xsl:variable name="standard_elements" select="$groupFilteredElements[segmentCode != 1]"/>
                    <!--Пенсионные ставки без учета параметров группы-->
                    <xsl:variable name="pension_elements" select="$groupFilteredElements[segmentCode != 0]"/>
                    <!--Пенсионные ставки с учетом параметров группы-->
                    <xsl:variable name="pension_elements_param" select="$pension_elements[group/groupParams/pensionRate = 'true' and segmentCode != 0]"/>

                    <xsl:choose>
                        <xsl:when test="$groupFilteredElements/group/groupCode = '-22'">
                            <xsl:choose>
                                <xsl:when test="$isPension and count($pension_elements) &gt; 0 ">
                                    <xsl:call-template name="dataWithTariff">
                                        <xsl:with-param name="product" select="$product"/>
                                        <xsl:with-param name="dateFilteredElements" select="$pension_elements"/>
                                        <xsl:with-param name="tariffToFind" select="$clientTariff"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="dataWithTariff">
                                        <xsl:with-param name="product" select="$product"/>
                                        <xsl:with-param name="dateFilteredElements" select="$standard_elements"/>
                                        <xsl:with-param name="tariffToFind" select="$clientTariff"/>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="$isPension and count($pension_elements_param) &gt; 0 ">
                                     <xsl:call-template name="dataWithTariff">
                                        <xsl:with-param name="product" select="$product"/>
                                        <xsl:with-param name="dateFilteredElements" select="$pension_elements_param"/>
                                        <xsl:with-param name="tariffToFind" select="$clientTariff"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="dataWithTariff">
                                        <xsl:with-param name="product" select="$product"/>
                                        <xsl:with-param name="dateFilteredElements" select="$standard_elements"/>
                                        <xsl:with-param name="tariffToFind" select="$clientTariff"/>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
            </xsl:for-each>
		</xsl:for-each>
	</xsl:template>

    <xsl:template name="data" >
        <xsl:param name="product"/>
        <xsl:param name="dateFilteredElements"/>

        <xsl:if test="count($dateFilteredElements) &gt; 0">
            <deposit>
                <!--Название депозита-->
                <xsl:variable name="depositName">
                    <xsl:call-template name="depositName">
                        <xsl:with-param name="filteredElements" select="$dateFilteredElements"/>
                    </xsl:call-template>
                </xsl:variable>
                <title>
                    <xsl:value-of select="$depositName"/>
                </title>
                <!-- Идентификатор в базе -->
                <depositId>
                    <xsl:value-of select="$product/@id"/>
                </depositId>
                <!-- Номер вида вклада -->
                <depositType>
                    <xsl:value-of select="$product/data/productId"/>
                </depositType>
                <xsl:if test="$showNewDeposits or $isATM = true()">
                    <!-- Группа депозитного продукта -->
                    <depositGroup>
                        <xsl:value-of select="$dateFilteredElements/group/groupCode"/>
                    </depositGroup>
                </xsl:if>

	            <xsl:if test="$isATM = true()">
		            <!-- Требуется ли первоначальный взнос -->
		            <needInitialFee>
			            <xsl:value-of select="$product/data/main/initialFee/text()"/>
		            </needInitialFee>
		            <!-- Есть ли у вклада неснижаемый остаток -->
		            <withMinimumBalance>
			            <xsl:value-of select="$product/data/main/minimumBalance/text()"/>
		            </withMinimumBalance>
                </xsl:if>

                <!-- Условия размещения средств по валютам -->
                <conditionsList>
                    <xsl:for-each select="xalan:distinct($dateFilteredElements/currencyCode/text())">
                        <xsl:variable name="currency" select="."/>
                        <condition>
                            <!-- Минимальный взнос/неснижаемый остаток -->
                            <minimumBalance>
                                <xsl:for-each select="$dateFilteredElements[currencyCode=$currency]/minBalance">
                                    <xsl:sort data-type="number" order="ascending"/>
                                    <xsl:if test="position() = 1">
                                        <xsl:value-of select="format-number(., '#0.00')"/>
                                    </xsl:if>
                                </xsl:for-each>
                            </minimumBalance>

                            <!-- Валюта -->
                            <currency>
                                <code>
                                    <xsl:value-of select="$currency"/>
                                </code>
                                <name>
                                    <xsl:value-of select="mu:getCurrencySign($currency)" disable-output-escaping="yes"/>
                                </name>
                            </currency>


                            <xsl:if test="boolean($dateFilteredElements/period)">
                                <!-- Диапазон срока хранения вклада (для срочных) -->
                                <period>
                                    <!-- Получение максимального и минимального сроков вклада -->
                                    <xsl:variable name="staticPeriods" select="$dateFilteredElements/period[not(contains(text(), 'U'))]"/>
                                    <xsl:variable name="intervalPeriods" select="$dateFilteredElements/period[contains(text(), 'U')]"/>
                                    <xsl:variable name="minPeriod">
                                        <xsl:call-template name="minPeriod">
                                            <xsl:with-param name="staticPeriods" select="$staticPeriods"/>
                                            <xsl:with-param name="intervalPeriods" select="$intervalPeriods"/>
                                        </xsl:call-template>
                                    </xsl:variable>
                                    <xsl:variable name="maxPeriod">
                                        <xsl:call-template name="maxPeriod">
                                            <xsl:with-param name="staticPeriods" select="$staticPeriods"/>
                                            <xsl:with-param name="intervalPeriods" select="$intervalPeriods"/>
                                        </xsl:call-template>
                                    </xsl:variable>

                                    <from>
                                        <xsl:value-of select="$minPeriod"/>
                                    </from>

                                    <xsl:if test="not($minPeriod = $maxPeriod)">
                                        <to>
                                            <xsl:value-of select="$maxPeriod"/>
                                        </to>
                                    </xsl:if>
                                </period>
                            </xsl:if>

                            <!-- Диапазон процентных ставок -->
                            <interestRate>
                                <xsl:variable name="activeInterestRates">
                                    <xsl:call-template name="activeInterestRates">
                                        <xsl:with-param name="filteredElements" select="$dateFilteredElements[currencyCode=$currency]"/>
                                    </xsl:call-template>
                                </xsl:variable>

                                <xsl:for-each select="xalan:nodeset($activeInterestRates)/node()">
                                    <xsl:sort data-type="number" order="ascending"/>
                                    <xsl:if test="position() = 1">
                                        <from>
                                            <xsl:value-of select="format-number(., '#0.00')"/>
                                        </from>
                                    </xsl:if>
                                    <xsl:if test="position() = last() and not(position() = 1)">
                                        <to>
                                            <xsl:value-of select="format-number(., '#0.00')"/>
                                        </to>
                                    </xsl:if>
                                </xsl:for-each>
                            </interestRate>
                        </condition>
                    </xsl:for-each>
                </conditionsList>
            </deposit>
        </xsl:if>
    </xsl:template>

    <xsl:template name="dataWithTariff" >
        <xsl:param name="product"/>
        <xsl:param name="dateFilteredElements"/>
        <xsl:param name="tariffToFind"/>
        <xsl:choose>
            <xsl:when test="$tariffToFind=0">
                <xsl:call-template name="dataWithTariffDependence">
                    <xsl:with-param name="product" select="$product"/>
                    <xsl:with-param name="filteredElements" select="$dateFilteredElements[tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3]"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="dataWithTariffDependence">
                    <xsl:with-param name="product" select="$product"/>
                    <xsl:with-param name="filteredElements" select="$dateFilteredElements[tariffPlanCode=$tariffToFind]"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="dataWithTariffDependence">
        <xsl:param name="product"/>
        <xsl:param name="filteredElements"/>
        <!--
        Для клиента необходимо выбрать подвиды, отмеченные в таблице зависимости (в справочнике ЦАС НСИ) с ТП клиента. А также те подвиды, которые не указаны в таблице
        (но tariffPlanCode совпадает с ТП клиента, это проверяется выше, в шаблоне dataWithTariff)
        Т.е. отсекаем те, что есть в таблице, но код ТП подвида не соответствует коду ТП клиента. Для определения зависимости используем именно код клиента.
        -->
        <xsl:call-template name="data">
            <xsl:with-param name="product" select="$product"/>
            <xsl:with-param name="dateFilteredElements" select="$filteredElements[(count(tariffDependence) = 0)
            or (tariffDependence/tariff/tariffCode = $tariff and translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($curDate, '.', ''))]"/>
        </xsl:call-template>
    </xsl:template>

	<xsl:template name="max">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort data-type="text" order="descending"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="min">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort data-type="text" order="ascending"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- Возвращает период с максимальной конечной датой -->
	<xsl:template name="maxInterval">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort data-type="text" order="descending" select="substring(text(), 12, 10)"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- Возвращает период с минимальной начальной датой -->
	<xsl:template name="minInterval">
		<xsl:param name="list"/>
		<xsl:for-each select="$list">
			<xsl:sort data-type="text" order="ascending" select="substring(text(), 1, 10)"/>
			<xsl:if test="position() = 1">
				<xsl:value-of select="."/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- Возвращает минимальный срок вклада. Параметры - набор узлов, содержащих сроки в виде интервалов, и набор узлов, содержащих сроки в виде определенного значения -->
	<xsl:template name="minPeriod">
		<xsl:param name="staticPeriods"/>
		<xsl:param name="intervalPeriods"/>
		<xsl:variable name="minStaticPeriod">
			<xsl:call-template name="min">
				<xsl:with-param name="list" select="$staticPeriods"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="minInterval">
			<xsl:call-template name="minInterval">
				<xsl:with-param name="list" select="$intervalPeriods"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="translate(substring($minInterval, 1, 10), '-', '')&lt;translate($minStaticPeriod, '-', '') or $minStaticPeriod = ''">
				<xsl:value-of select="substring($minInterval, 1, 10)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$minStaticPeriod"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Возвращает ммаксимальный срок вклада. Параметры - набор узлов, содержащих сроки в виде интервалов, и набор узлов, содержащих сроки в виде определенного значения -->
	<xsl:template name="maxPeriod">
		<xsl:param name="staticPeriods"/>
		<xsl:param name="intervalPeriods"/>
		<xsl:variable name="maxStaticPeriod">
			<xsl:call-template name="max">
				<xsl:with-param name="list" select="$staticPeriods"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="maxInterval">
			<xsl:call-template name="maxInterval">
				<xsl:with-param name="list" select="$intervalPeriods"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="translate(substring($maxInterval, 12, 10), '-', '')&gt;translate($maxStaticPeriod, '-', '') or $maxStaticPeriod = ''">
				<xsl:value-of select="substring($maxInterval, 12, 10)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$maxStaticPeriod"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="parsePeriod">
		<xsl:param name="period"/>
		<xsl:variable name="years" select="number(substring($period, 1, 2))"/>
		<xsl:variable name="months" select="number(substring($period, 4, 2))"/>
		<xsl:variable name="days" select="number(substring($period, 7, 4))"/>

		<xsl:if test="$years!=0">
			<xsl:value-of select="$years"/>
			<xsl:choose>
				<xsl:when test="(($years mod 10) = 1) and ($years != 11)">
					<xsl:text> года </xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text> лет </xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<xsl:if test="$months!=0">
			<xsl:value-of select="$months"/>
			<xsl:choose>
				<xsl:when test="$months = 1">
					<xsl:text> месяца </xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text> месяцев </xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<xsl:if test="$days!=0">
			<xsl:value-of select="$days"/>
			<xsl:choose>
				<xsl:when test="(($days mod 10) = 1) and ($days != 11)">
					<xsl:text> дня </xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text> дней </xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>

	</xsl:template>

	<xsl:template name="activeInterestRates">
		<xsl:param name="filteredElements"/>
        <xsl:choose>
            <xsl:when test="boolean($filteredElements/period)">
                <xsl:for-each select="xalan:distinct($filteredElements/period)">
                    <xsl:variable name="period" select="."/>
                    <xsl:for-each select="xalan:distinct($filteredElements[period=$period]/minBalance)">
                        <xsl:variable name="minBalance" select="."/>
                        <xsl:variable name="rates" select="xalan:distinct($filteredElements[period=$period and minBalance=$minBalance]/interestRate)"/>
                        <xsl:choose>
                            <xsl:when test="count($rates)&gt;1">
                                <xsl:for-each select="$filteredElements[period=$period and minBalance=$minBalance]">
                                    <xsl:sort data-type="text" order="descending" select="translate(interestDateBegin, '.', '')"/>
                                    <xsl:if test="position()=1">
                                        <xsl:element name="rate">
                                            <xsl:value-of select="interestRate"/>
                                        </xsl:element>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:element name="rate">
                                    <xsl:value-of select="$rates"/>
                                </xsl:element>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="xalan:distinct($filteredElements/minBalance)">
                    <xsl:variable name="minBalance" select="."/>
                    <xsl:variable name="rates" select="xalan:distinct($filteredElements[minBalance=$minBalance]/interestRate)"/>
                    <xsl:choose>
                        <xsl:when test="count($rates)&gt;1">
                            <xsl:for-each select="$filteredElements[minBalance=$minBalance]">
                                <xsl:sort data-type="text" order="descending" select="translate(interestDateBegin, '.', '')"/>
                                <xsl:if test="position()=1">
                                    <xsl:element name="rate">
                                        <xsl:value-of select="interestRate"/>
                                    </xsl:element>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:element name="rate">
                                <xsl:value-of select="$rates"/>
                            </xsl:element>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
	</xsl:template>

    <!--
    Название депозитного продукта.
    Если есть группа (т.е. не равна -22), используется ее название.
    Если это непараметризированный вклад (группа равна -22), используется название первого подвида из списка
    -->
    <xsl:template name="depositName">
        <xsl:param name="filteredElements"/>
        <xsl:variable name="code" select="$filteredElements/group/groupCode"/>
        <xsl:choose>
            <xsl:when test="$code = '-22'">
                <xsl:value-of select="$filteredElements/subTypeName"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$filteredElements/group/groupName"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>