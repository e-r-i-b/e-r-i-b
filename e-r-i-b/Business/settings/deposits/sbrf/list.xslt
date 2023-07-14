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
    <xsl:param name="group"/>
    <xsl:param name="sortList"/>
    <xsl:param name="name"/>
    <xsl:param name="clientSegmentList" select="document('client-promo-segment-list.xml')/entity-list"/>
    <xsl:param name="hasSegment" select="string-length($clientSegmentList)!=0"/>

    <xsl:template match="/">
        <xsl:variable name="products" select="/products"/>
        <xsl:choose>
            <!--Для клиента сначала выбираем вклады с промо-кодом и приоритетом 1-->
            <xsl:when test="not ($admin) and $hasSegment">
                <xsl:choose>
                    <!--Если ставки, поддерживающие промокоды-->
                    <xsl:when test="count(/products/product/data/options/element[group/groupParams/promoCodeSupported = 'true']) &gt; 0">
                        <!--Пройдемся по всем сегментам(т.е. промокодам), которые должны отображаться отдельным продуктом-->
                        <xsl:for-each select="$clientSegmentList/entity[field[@name = 'priority' and text()='1']]">
                            <xsl:variable name="segment" select="."/>
                            <xsl:call-template name="sortByGroup">
                                <xsl:with-param name="segment" select="$segment"/>
                                <xsl:with-param name="products" select="$products"/>
                            </xsl:call-template>
                        </xsl:for-each>

                        <!--Отобразим вклады без промокодов-->
                        <xsl:call-template name="sortByGroup">
                            <xsl:with-param name="segment" select="null"/>
                            <xsl:with-param name="products" select="$products"/>
                        </xsl:call-template>
                    </xsl:when>
                    <!--Если ставок для промокодов нет, используем старый алгоритм-->
                    <xsl:otherwise>
                        <xsl:call-template name="sortByGroup">
                            <xsl:with-param name="segment" select="null"/>
                            <xsl:with-param name="products" select="$products"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <!--Для админки или если у клиента нет действующих промо-кодов, отображаем по старому алгоритму-->
            <xsl:otherwise>
                <xsl:call-template name="sortByGroup">
                    <xsl:with-param name="segment" select="null"/>
                    <xsl:with-param name="products" select="$products"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="sortByGroup">
        <!--Передается, только если необходимо отображать в соответствии с промокодами-->
        <xsl:param name="segment"/>
        <xsl:param name="products"/>
        <xsl:choose>
            <xsl:when test="$group != ''">
                <xsl:call-template name="filteredByGroup">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="groupCodes"
                                    select="xalan:distinct($products/product/data[main/initialFee != string($needInitialFee)]/options/element/group[contains(groupCode, $group)]/groupCode)"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="groupCodes" select="xalan:distinct($products/product[data/main/initialFee != string($needInitialFee)]/data/options/element/group/groupCode)"/>
                <xsl:call-template name="filteredByGroup">
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

	<xsl:template name="filteredByGroup">
        <xsl:param name="groupCodes"/>
        <xsl:param name="products"/>
        <xsl:param name="segment"/>

        <xsl:choose>
            <xsl:when test="$tariff=''">
                <xsl:call-template name="dataByTariff">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="tariff" select="0"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                </xsl:call-template>
                <xsl:call-template name="dataByTariff">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="tariff" select="1"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                </xsl:call-template>
                <xsl:call-template name="dataByTariff">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="tariff" select="2"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                </xsl:call-template>
                <xsl:call-template name="dataByTariff">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="tariff" select="3"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="dataByTariff">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="tariff" select="$tariff"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
	</xsl:template>

    <!--Шаблон обрабатывает данные с учетом тарифного плана-->
    <xsl:template name="dataByTariff">
        <xsl:param name="products"/>
        <xsl:param name="segment"/>
        <xsl:param name="tariff"/>
        <xsl:param name="groupCodes"/>
        <div class="depositTariff">
            <xsl:if test="$admin">
                <div class="depositTariffTitle">
                    <xsl:choose>
                        <xsl:when test="$tariff=0">
                            <xsl:text>Тарифный план не установлен</xsl:text>
                        </xsl:when>
                        <xsl:when test="$tariff=1">
                            <xsl:text>Сбербанк Премьер</xsl:text>
                        </xsl:when>
                        <xsl:when test="$tariff=2">
                            <xsl:text>Сбербанк Золотой</xsl:text>
                        </xsl:when>
                        <xsl:when test="$tariff=3">
                            <xsl:text>Сбербанк Первый</xsl:text>
                        </xsl:when>
                    </xsl:choose>
                </div>
            </xsl:if>

            <xsl:call-template name="sortGroup">
                <xsl:with-param name="products" select="$products"/>
                <xsl:with-param name="segment" select="$segment"/>
                <xsl:with-param name="groupCodes" select="$groupCodes"/>
                <xsl:with-param name="sortList" select="$sortList/numberPosition"/>
                <xsl:with-param name="tariff" select="$tariff"/>
            </xsl:call-template>
        </div>

        <script type="text/javascript">function selectRow(row)
            {
                row.getElementsByTagName('input')[0].checked = true;
            }

            <xsl:if test="$admin">
                doOnLoad( function setDataNotFound()
                    {
                        var depositTariffList = $('.depositTariff');
                        for (var i = 0; i &lt; depositTariffList.length; i++)
                        {
                            var depositTariff = $(depositTariffList)[i];
                            var depositCount = $(depositTariff).find('.depositProductAddInfo').size();
                            if (depositCount == 0)
                            {
                                var node = document.createElement('div');
                                node.className = "depositProductAddInfo";
                                node.innerHTML = "Не найдены данные, удовлетворяющие условиям фильтра.";

                                depositTariff.appendChild(node);
                            }
                        }
                    })
            </xsl:if>
        </script>
	</xsl:template>

    <!--Шаблон обрабатывает данные с учетом групп, отсортированных в порядке, указанном в конфиге-->
    <!--
    Вызываем шаблон рекурсивно, убирая каждый раз первый элемент в списке с порядком сортировки.
    Когда элементов не останется - используем все оставшиеся значения групп, не сортируя их.
    -->
    <xsl:template name="sortGroup">
        <xsl:param name="groupCodes"/>
        <xsl:param name="sortList"/>
        <xsl:param name="tariff"/>
        <xsl:param name="products"/>
        <xsl:param name="segment"/>

        <xsl:choose>
            <xsl:when test="count($sortList) > 0">
                <xsl:variable name="firstPosition">
                    <xsl:value-of select="$sortList"/>
                </xsl:variable>

                <xsl:call-template name="groupList">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes[text()=$firstPosition]"/>
                    <xsl:with-param name="tariff" select="$tariff"/>
                </xsl:call-template>

                <xsl:call-template name="sortGroup">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="sortList" select="$sortList[text()!=$firstPosition]"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes[text()!=$firstPosition]"/>
                    <xsl:with-param name="tariff" select="$tariff"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="groupList">
                    <xsl:with-param name="products" select="$products"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="groupCodes" select="$groupCodes"/>
                    <xsl:with-param name="tariff" select="$tariff"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!--Шаблон обрабатывает данные данные о вкладных продуктах, объединяя подвиды вкладов по значению группы-->
    <xsl:template name="groupList">
        <xsl:param name="segment"/>
        <xsl:param name="groupCodes"/>
        <xsl:param name="tariff"/>
        <xsl:param name="products"/>

        <xsl:for-each select="$products/product[data/main/initialFee != string($needInitialFee)]">
            <xsl:variable name="product" select="."/>
            <xsl:variable name="dateFilteredElements"
                          select="./data/options/element[translate(dateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(dateEnd, '.', '')&gt;translate($curDate, '.', '') and translate(interestDateBegin, '.', '')&lt;=translate($curDate, '.', '') and ($admin or availToOpen='true')]"/>

            <xsl:choose>
                <!--Для администратора не используются промокоды-->
                <xsl:when test="$admin">
                    <xsl:call-template name="groupListBySegment">
                        <xsl:with-param name="elements" select="$dateFilteredElements"/>
                        <xsl:with-param name="segment" select="$segment"/>
                        <xsl:with-param name="tariff" select="$tariff"/>
                        <xsl:with-param name="groupCodes" select="$groupCodes"/>
                        <xsl:with-param name="product" select="$product"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:when test="not($hasSegment)">
                    <xsl:call-template name="groupListBySegment">
                        <xsl:with-param name="elements" select="$dateFilteredElements[segmentCode &lt; 2]"/>
                        <xsl:with-param name="segment" select="$segment"/>
                        <xsl:with-param name="tariff" select="$tariff"/>
                        <xsl:with-param name="groupCodes" select="$groupCodes"/>
                        <xsl:with-param name="product" select="$product"/>
                    </xsl:call-template>
                </xsl:when>
                <!--Если есть помокод с приоритетом 1, сначала идет сортировка по промокоду-->
                <xsl:when test="string-length($segment)!=0">
                    <xsl:call-template name="groupListBySegment">
                        <xsl:with-param name="elements" select="$dateFilteredElements[segmentCode = $segment/@key and (group/groupParams/promoCodeSupported = 'true')]"/>
                        <xsl:with-param name="segment" select="$segment"/>
                        <xsl:with-param name="tariff" select="$tariff"/>
                        <xsl:with-param name="groupCodes" select="$groupCodes"/>
                        <xsl:with-param name="product" select="$product"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="segmentCode">
                        <xsl:value-of select="$clientSegmentList/entity[field[@name = 'priority' and text()='1']]/@key"/>
                    </xsl:variable>
                    <!--Ставки для промокода с приоритетом 1 уже отображались-->
                    <xsl:call-template name="groupListBySegment">
                        <xsl:with-param name="elements" select="$dateFilteredElements[segmentCode != $segmentCode]"/>
                        <xsl:with-param name="segment" select="$segment"/>
                        <xsl:with-param name="tariff" select="$tariff"/>
                        <xsl:with-param name="groupCodes" select="$groupCodes"/>
                        <xsl:with-param name="product" select="$product"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
         </xsl:for-each>
    </xsl:template>

    <!--Шаблон обрабатывает данные данные о вкладных продуктах, объединяя подвиды вкладов по значению группы-->
    <!--Сортируем по группе, определяем принадлежность к промокодам-->
    <xsl:template name="groupListBySegment">
        <xsl:param name="groupCodes"/>
        <xsl:param name="tariff"/>
        <xsl:param name="segment"/>
        <xsl:param name="elements"/>
        <xsl:param name="product"/>

            <xsl:for-each select="$groupCodes">
                <xsl:variable name="groupCode" select="."/>
                <xsl:variable name="groupFilteredElements" select="$elements[group/groupCode=$groupCode]"/>

                <xsl:variable name="tariffToFind">
                    <xsl:choose>
                        <xsl:when test="$admin or count($groupFilteredElements[tariffPlanCode=$tariff]) > 0">
                            <xsl:value-of select="$tariff"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'0'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <xsl:choose>
                    <!--Если обрабатывается промовклад (т.е. с промо-кодом с приоритетом 1), не проверяем признак пенсионера. Отображаем только промо-ставки-->
                    <xsl:when test="not($admin) and string-length($segment)!=0">
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$groupFilteredElements"/>
                            <xsl:with-param name="tariffToFind" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:when>
                    <!--
                    Если обрабатывается вклад для клиента, имеющего промо-коды с приоритетом 0 (приоритет 1 не рассматриваем, были обработаны ранее)
                    Выбираем актуальный промокод и определяем ставки, соответствующие ему.
                    А также ставки для пенсионеров (segmentCode = 1) и без определенного сегмента (segmentCode = 0)
                    -->
                    <xsl:when test="not($admin) and string-length($clientSegmentList/entity[field[@name = 'priority' and text()='0']])!=0">
                        <xsl:variable name="promoCodeSegments0">
                            <xsl:for-each select="$clientSegmentList/entity[field[@name = 'priority' and text()='0']]/@key">
                                <xsl:sort data-type="number" order="descending"/>
                                <xsl:variable name="actualSegment" select="."/>
                                <xsl:if test="count($groupFilteredElements[segmentCode = $actualSegment and (group/groupParams/promoCodeSupported = 'true')]) > 0">
                                    <xsl:value-of select="$actualSegment"/>|
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:variable>

                        <xsl:variable name="promoCodeSegment0">
                            <xsl:value-of select="substring-before($promoCodeSegments0, '|')"/>
                        </xsl:variable>

                        <xsl:call-template name="groupListBySegmentPrior">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="groupFilteredElements" select="$groupFilteredElements[(segmentCode = $promoCodeSegment0 and group/groupParams/promoCodeSupported = 'true') or segmentCode = 1 or segmentCode = 0]"/>
                            <xsl:with-param name="tariff" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$clientSegmentList/entity[@key=$promoCodeSegment0]"/>
                        </xsl:call-template>
                    </xsl:when>
                    <!--а тут будут вклады для админки и если у клиента нет промо. Короче, все оставшиеся-->
                    <xsl:otherwise>
                        <xsl:call-template name="groupListBySegmentPrior">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="groupFilteredElements" select="$groupFilteredElements"/>
                            <xsl:with-param name="tariff" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
    </xsl:template>

    <xsl:template name="elementSegmentForPension">
        <xsl:param name="elements"/>
        <xsl:param name="groupParams" select="''"/>

        <xsl:variable name="pension_elements" select="$elements[segmentCode != 0]"/>

        <xsl:choose>
            <xsl:when test="$groupParams != 'true'">
                <xsl:choose>
                    <xsl:when test="count($pension_elements)&gt;1">
                        <xsl:value-of select="'0'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'1'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="pension_elements_param" select="$pension_elements[group/groupParams/pensionRate = 'true']"/>
                <xsl:choose>
                    <xsl:when test="count($pension_elements_param)&gt;1">
                        <xsl:value-of select="'0'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'1'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!--В зависимости от приоритета промо-кода (в случае его наличия)-->
    <xsl:template name="groupListBySegmentPrior">
        <xsl:param name="tariff"/>
        <xsl:param name="segment"/>
        <xsl:param name="groupFilteredElements"/>
        <xsl:param name="product"/>

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

                        <xsl:variable name="e_RUB_segment">
                            <xsl:call-template name="elementSegmentForPension">
                                <xsl:with-param name="elements" select="$groupFilteredElements[currencyCode = 'RUB']"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:variable name="e_EUR_segment">
                            <xsl:call-template name="elementSegmentForPension">
                                <xsl:with-param name="elements" select="$groupFilteredElements[currencyCode = 'EUR']"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:variable name="e_USD_segment">
                            <xsl:call-template name="elementSegmentForPension">
                                <xsl:with-param name="elements" select="$groupFilteredElements[currencyCode = 'USD']"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$groupFilteredElements[currencyCode = 'RUB' and segmentCode != $e_RUB_segment] |
                                                                            $groupFilteredElements[currencyCode = 'EUR' and segmentCode != $e_EUR_segment] |
                                                                            $groupFilteredElements[currencyCode = 'USD' and segmentCode != $e_USD_segment]"/>
                            <xsl:with-param name="tariffToFind" select="$tariff"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$standard_elements"/>
                            <xsl:with-param name="tariffToFind" select="$tariff"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$isPension and count($pension_elements_param) &gt; 0 ">

                        <xsl:variable name="e_RUB_segment">
                            <xsl:call-template name="elementSegmentForPension">
                                <xsl:with-param name="elements" select="$groupFilteredElements[currencyCode = 'RUB']"/>
                                <xsl:with-param name="groupParams" select="'true'"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:variable name="e_EUR_segment">
                            <xsl:call-template name="elementSegmentForPension">
                                <xsl:with-param name="elements" select="$groupFilteredElements[currencyCode = 'EUR']"/>
                                <xsl:with-param name="groupParams" select="'true'"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:variable name="e_USD_segment">
                            <xsl:call-template name="elementSegmentForPension">
                                <xsl:with-param name="elements" select="$groupFilteredElements[currencyCode = 'USD']"/>
                                <xsl:with-param name="groupParams" select="'true'"/>
                            </xsl:call-template>
                        </xsl:variable>

                         <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$groupFilteredElements[currencyCode = 'RUB' and segmentCode != $e_RUB_segment] |
                                                                            $groupFilteredElements[currencyCode = 'EUR' and segmentCode != $e_EUR_segment] |
                                                                            $groupFilteredElements[currencyCode = 'USD' and segmentCode != $e_USD_segment]"/>
                            <xsl:with-param name="tariffToFind" select="$tariff"/>
                             <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariff">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$standard_elements"/>
                            <xsl:with-param name="tariffToFind" select="$tariff"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="dataWithTariff">
        <xsl:param name="product"/>
        <xsl:param name="segment"/>
        <xsl:param name="filteredElements"/>
        <xsl:param name="tariffToFind"/>
        <xsl:choose>
            <xsl:when test="not($admin)">
                <!--Эта часть в 15 релизе будет общей-->
                <xsl:choose>
                    <xsl:when test="$tariffToFind = 0">
                        <xsl:call-template name="dataWithTariffDependence">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$filteredElements[tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3]"/>
                            <xsl:with-param name="tariffToFind" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariffDependence">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$filteredElements[tariffPlanCode=$tariffToFind]"/>
                            <xsl:with-param name="tariffToFind" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <!--
                В связи с уточнениями к 14 релизу (CHG073851): для отображения в АРМ сотрудника НЕ смотрим на параметр tariffPlanCode,
                проверяем исключительно наличие записи о подвиде в таблице зависимости вклада от ТП (в справочнике, у нас в системе это отражено в узлах tariffDependence).
                В 15 (или последующем) релизе будет изменен справочник ЦАС НСИ (добавятся актуальные значения в таблицу dcf_tar)
                и отображение в АРМ сотрудника вернется тому, что реализовано в ревизии 61369.
                -->
                <xsl:call-template name="dataWithTariffDependence">
                    <xsl:with-param name="product" select="$product"/>
                    <xsl:with-param name="filteredElements" select="$filteredElements"/>
                    <xsl:with-param name="tariffToFind" select="$tariffToFind"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="dataWithTariffDependence">
        <xsl:param name="product"/>
        <xsl:param name="filteredElements"/>
        <xsl:param name="tariffToFind"/>
        <xsl:param name="segment"/>
        <xsl:choose>
            <!--
            Для сотрудника совпадение с искомым ТП проверяется при сравнении с элементом tariffCode. В случае, если ищем вкладные продукты без ТП (т.е. ТП == 0),
            выбираем те продукты, у которых нет соответствующих элементов(т.е. нет записей в таблице зависимости от ТП в справочнике ЦАС НСИ),
            либо те, у которых значение элемента tariffCode != 1, 2 и 3 (по аналогии с проверкой для клиента в шаблоне dataWithTariff)
            -->
            <xsl:when test="$admin">
                <xsl:choose>
                    <xsl:when test="$tariffToFind = 0">
                        <xsl:call-template name="data">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$filteredElements[(count(tariffDependence) = 0)]"/>
                            <xsl:with-param name="tariff" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                        <xsl:call-template name="dataWithTariffDependenceForAdmin">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements"
                                            select="$filteredElements[tariffDependence/tariff/tariffCode != 1 and tariffDependence/tariff/tariffCode != 2 and tariffDependence/tariff/tariffCode != 3]"/>
                            <xsl:with-param name="tariffToFind" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="dataWithTariffDependenceForAdmin">
                            <xsl:with-param name="product" select="$product"/>
                            <xsl:with-param name="filteredElements" select="$filteredElements[tariffDependence/tariff/tariffCode = $tariffToFind]"/>
                            <xsl:with-param name="tariffToFind" select="$tariffToFind"/>
                            <xsl:with-param name="segment" select="$segment"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <!--
            Для клиента необходимо выбрать подвиды, отмеченные в таблице зависимости (в справочнике ЦАС НСИ) с ТП клиента. А также те подвиды, которые не указаны в таблице
            (но tariffPlanCode совпадает с ТП клиента, это проверяется выше, в шаблоне dataWithTariff)
            Т.е. отсекаем те, что есть в таблице, но код ТП подвида не соответствует коду ТП клиента. Для определения зависимости используем именно код клиента.
            -->
            <xsl:otherwise>
                <xsl:call-template name="data">
                    <xsl:with-param name="product" select="$product"/>
                    <xsl:with-param name="segment" select="$segment"/>
                    <xsl:with-param name="filteredElements" select="$filteredElements[(count(tariffDependence) = 0)
                    or (tariffDependence/tariff/tariffCode = $tariff and translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($curDate, '.', ''))]"/>
                    <xsl:with-param name="tariff" select="$tariffToFind"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!--
    Шаблон для определения актуальности зависимости тарифа от ТП. Используем, чтобы убрать длинный перечень условий в шаблоне dataWithTariffDependence.
    Используем только для АРМ сотрудника, т.к. шаблон временный (уберется в 15 релизе)
    -->
    <xsl:template name="dataWithTariffDependenceForAdmin">
        <xsl:param name="product"/>
        <xsl:param name="filteredElements"/>
        <xsl:param name="tariffToFind"/>
        <xsl:param name="segment"/>

        <xsl:choose>
            <xsl:when test="$admin">
                <xsl:call-template name="data">
                    <xsl:with-param name="product" select="$product"/>
                    <xsl:with-param name="filteredElements"
                                    select="$filteredElements[translate(tariffDependence/tariff/tariffDateBegin, '.', '')&lt;=translate($curDate, '.', '') and translate(tariffDependence/tariff/tariffDateEnd, '.', '')&gt;translate($curDate, '.', '')]"/>
                    <xsl:with-param name="tariff" select="$tariffToFind"/>
                    <xsl:with-param name="segment" select="$segment"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="data">
        <xsl:param name="product"/>
        <xsl:param name="filteredElements"/>
        <xsl:param name="tariff"/>
        <xsl:param name="segment"/>

        <xsl:variable name="available" select="$product/@available"/>
        <xsl:variable name="depositId" select="$product/@id"/>
        <xsl:variable name="productId" select="$product/data/productId"/>

        <xsl:if test="(count($filteredElements) &gt; 0 )">
            <xsl:variable name="groupCode" select="$filteredElements/group/groupCode"/>

            <xsl:variable name="promoProduct" select="count($segment[field[@name = 'priority' and text()='1']]) > 0"/>

            <!--Название депозита-->
            <xsl:variable name="depositName">
                <xsl:call-template name="depositName">
                    <xsl:with-param name="filteredElements" select="$filteredElements"/>
                </xsl:call-template>
            </xsl:variable>

            <xsl:variable name="depositNameLowerCase">
                <xsl:call-template name="lowercase">
                    <xsl:with-param name="input" select="$depositName"/>
                </xsl:call-template>
            </xsl:variable>

            <xsl:variable name="partOfNameLowerCase">
                <xsl:call-template name="lowercase">
                    <xsl:with-param name="input" select="$name"/>
                </xsl:call-template>
            </xsl:variable>

            <xsl:variable name="depositProductClass">
                <xsl:call-template name="depositProductClass">
                    <xsl:with-param name="premiumSegment" select="$promoProduct"/>
                </xsl:call-template>
            </xsl:variable>

            <!--
            Фильтр по названию депозитного продукта. Проверяем здесь, т.к. название может браться из имени группы или имени подвида
            -->
            <xsl:if test="$name = '' or contains($depositNameLowerCase, $partOfNameLowerCase)">
                <div class="{$depositProductClass}">
                    <xsl:choose>
                        <xsl:when test="not($admin)">
                            <xsl:variable name="segmentCode">
                                <xsl:choose>
                                    <xsl:when test="string-length($segment)!=0">
                                        <xsl:value-of select="$segment/@key"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="null"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <div class="depositProductTitle" onclick="selectRow(this);">
                                <input name="depositProductId" type="radio">
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="$productId"/>:<xsl:value-of select="$groupCode"/>:<xsl:value-of select="$segmentCode"/>
                                    </xsl:attribute>
                                    <xsl:if test="position()=1">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <h2><xsl:value-of select="$depositName"/></h2>
                                <xsl:if test="$promoProduct">
                                    <span class="promoCodeLabel">промо-вклад</span>
                                </xsl:if>
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <div class="depositProductTitle">
                                <xsl:choose>
                                    <xsl:when test="count($filteredElements) &gt; 0">
                                        <span class="depositInfoLink link">
                                            <xsl:attribute name="onclick">
                                                <xsl:choose>
                                                    <xsl:when test="$tariff=''">
                                                        editDepositDetailProduct(<xsl:value-of select="$productId"/>, null, <xsl:value-of select="$groupCode"/>);
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        editDepositDetailProduct(<xsl:value-of select="$productId"/>, <xsl:value-of select="$tariff"/>, <xsl:value-of select="$groupCode"/>);
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                                return false;
                                            </xsl:attribute>
                                            <h2><xsl:value-of select="$depositName"/></h2>
                                        </span>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <div class="depositProductAddInfo">
                                            <h2><xsl:value-of select="$depositName"/></h2>
                                        </div>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                        </xsl:otherwise>
                    </xsl:choose>

                    <xsl:choose>
                        <xsl:when test="count($filteredElements) &gt; 0">
                            <xsl:if test="$admin">
                                <div class="depositProductAddInfo">
                                <xsl:text>Открывать через «Сбербанк Онлайн»</xsl:text>
                                <xsl:choose>
                                   <xsl:when test="$available='true'">
                                        <input type="checkbox" id="availableOnline{$depositId}" name="availableOnline{$depositId}" checked="{$available}" disabled="true"/>
                                   </xsl:when>
                                    <xsl:otherwise>
                                        <input type="checkbox" id="availableOnline{$depositId}" name="availableOnline{$depositId}" disabled="true"/>
                                   </xsl:otherwise>
                                </xsl:choose>
                                </div>
                            </xsl:if>

                            <!-- Таблица с обзорной информацией по вкладу -->
                            <table class="depositProductInfo">
                                <tr class="borderBottom">
                                    <xsl:choose>
                                        <xsl:when test="not($admin)">
                                            <th class="borderBottom newProductEmptyCell"></th>
                                            <th class="clientMinFee">
                                                <xsl:choose>
                                                    <xsl:when test="$product/data/main/minimumBalance/text() = 'true'">
                                                        <xsl:text>Неснижаемый остаток</xsl:text>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:text>Минимальный взнос</xsl:text>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </th>
                                            <th class="clientDepositCurrency">Валюта</th>
                                            <th class="clientDepositRate" >Ставка, %</th>
                                            <th class="clientDepositTerm depositPeriodList">Срок</th>
                                            <th class="clientOperationsAvail">Приходные/Расходные операции</th>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <th class="minFee">
                                                <xsl:choose>
                                                    <xsl:when test="$product/data/main/minimumBalance/text() = 'true'">
                                                        <xsl:text>Неснижаемый остаток</xsl:text>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:text>Минимальный взнос</xsl:text>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </th>
                                            <th class="currency">Валюта вклада</th>
                                            <th class="interestRate">Процентная ставка</th>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </tr>
                                <xsl:variable name="dateFilteredElementsCount" select="count($filteredElements)"/>
                                <xsl:variable name="minDepositSubType">
                                    <xsl:call-template name="min">
                                        <xsl:with-param name="list" select="$filteredElements/id/text()"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                <xsl:for-each select="xalan:distinct($filteredElements/currencyCode/text())">
                                    <xsl:variable name="currency" select="."/>

                                    <xsl:variable name="rowStyle">
                                        <xsl:choose>
                                            <xsl:when test="count($filteredElements[segmentCode = $segment/@key and $currency = currencyCode]) > 0 and not($promoProduct)">
                                                <xsl:value-of select="'premiumDepositProductItem'"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="''"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <xsl:variable name="promoStyle">
                                        <xsl:choose>
                                            <xsl:when test="count($filteredElements[segmentCode = $segment/@key and $currency = currencyCode]) > 0 and not($promoProduct)">
                                                <xsl:value-of select="'rowPromo'"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="''"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <tr>
                                        <td class="{$promoStyle}"> </td>
                                        <td class="{$rowStyle}">
                                            <xsl:for-each select="$filteredElements[currencyCode=$currency]/minBalance">
                                                <xsl:sort data-type="number" order="ascending"/>
                                                <xsl:if test="position() = 1">
                                                    От <xsl:value-of select="format-number(., '# ##0,00', 'sbrf')"/>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </td>
                                        <td class="{$rowStyle}">
                                            <xsl:value-of select="mu:getISOCodeExceptingRubles($currency)"/>
                                        </td>
                                        <td class="{$rowStyle}">
                                            <xsl:variable name="activeInterestRates">
                                                <xsl:call-template name="activeInterestRates">
                                                    <xsl:with-param name="filteredElements" select="$filteredElements[currencyCode=$currency]"/>
                                                    <xsl:with-param name="segmentCode" select="$segment/@key"/>
                                                </xsl:call-template>
                                            </xsl:variable>

                                            <xsl:variable name="rateClass">
                                                <xsl:choose>
                                                    <xsl:when test="$promoProduct or count($filteredElements[segmentCode = $segment/@key and $currency = currencyCode]) > 0">
                                                        <xsl:value-of select="'bold'"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="''"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:variable>
                                            
                                            <div class="{$rateClass}">
                                                <xsl:for-each select="xalan:nodeset($activeInterestRates)/node()">
                                                    <xsl:sort data-type="number" order="ascending"/>
                                                    <xsl:if test="position() = 1">
                                                        <xsl:value-of select="format-number(., '# ##0,00', 'sbrf')"/>
                                                    </xsl:if>
                                                    <xsl:if test="position() = last() and not(position() = 1)">
                                                        <xsl:text>&#160;...&#160;</xsl:text>
                                                        <xsl:value-of select="format-number(., '# ##0,00', 'sbrf')"/>
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </div>

                                            <xsl:if test="$admin">
                                                <xsl:text> %</xsl:text>
                                            </xsl:if>
                                        </td>
                                        <xsl:if test="not($admin)">
                                            <xsl:if test="position() = 1">
                                                <td rowspan="{$dateFilteredElementsCount}" class="depositPeriodList">
                                                    <xsl:call-template name="depositPeriod">
                                                        <xsl:with-param name="filteredElements" select="$filteredElements"/>
                                                    </xsl:call-template>
                                                </td>

                                                <td rowspan="{$dateFilteredElementsCount}">
                                                    <xsl:choose>
                                                        <xsl:when test="count($filteredElements[id=$minDepositSubType and creditOperations/text() = 'true'])&gt;0">
                                                            <xsl:text>наличными деньгами, безналичным путем</xsl:text>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:text>не совершаются</xsl:text>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                    <span class="bold"> / </span>
                                                    <xsl:choose>
                                                        <xsl:when test="count($filteredElements[id=$minDepositSubType and debitOperations = 'true'])&gt;0">
                                                            <xsl:variable name="debitOperationsCode" select="$filteredElements[id=$minDepositSubType]/debitOperationsCode/text()"/>
                                                            <xsl:choose>
                                                                <xsl:when test="$debitOperationsCode = 0">
                                                                    <xsl:text>не совершаются</xsl:text>
                                                                </xsl:when>
                                                                <xsl:when test="$debitOperationsCode = 1">
                                                                    <xsl:text>предусмотрено, в пределах причисленных процентов</xsl:text>
                                                                </xsl:when>
                                                                <xsl:when test="$debitOperationsCode = 2">
                                                                    <xsl:text>предусмотрено, в пределах неснижаемого остатка</xsl:text>
                                                                </xsl:when>
                                                                <xsl:when test="$debitOperationsCode = 3">
                                                                    <xsl:text>предусмотрено, в пределах остатка собственных средств</xsl:text>
                                                                </xsl:when>
                                                                <xsl:when test="$debitOperationsCode = 4">
                                                                    <xsl:text>предусмотрено, до нулевого остатка</xsl:text>
                                                                </xsl:when>
                                                                <xsl:when test="$debitOperationsCode = 5">
                                                                    <xsl:text>предусмотрено, в пределах максимального из двух значений: неснижаемый остаток и сумма причисленных процентов</xsl:text>
                                                                </xsl:when>
                                                            </xsl:choose>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:choose>
                                                                <xsl:when test="count($filteredElements[id=$minDepositSubType and debitOperations = 'false' and interestOperations = 'false'])&gt;0">
                                                                    <xsl:text>не совершаются</xsl:text>
                                                                </xsl:when>
                                                                <xsl:when test="count($filteredElements[id=$minDepositSubType and debitOperations = 'false' and interestOperations = 'true'])&gt;0">
                                                                    <xsl:text>предусмотрено, в пределах причисленных процентов</xsl:text>
                                                                </xsl:when>
                                                            </xsl:choose>
                                                        </xsl:otherwise>
                                                     </xsl:choose>
                                                </td>
                                            </xsl:if>
                                        </xsl:if>
                                    </tr>
                                </xsl:for-each>
                            </table>
                        </xsl:when>
                        <xsl:otherwise>
                            <div class="depositProductAddInfo">
                                <xsl:text>Не найдены данные, удовлетворяющие условиям фильтра.</xsl:text>
                            </div>
                        </xsl:otherwise>
                    </xsl:choose>

                    <xsl:if test="not($admin)">
                        <xsl:variable name="segmentCode">
                            <xsl:choose>
                                <xsl:when test="string-length($segment)!=0">
                                    ,<xsl:value-of select="$segment/@key"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="null"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <span class="depositProductInfoLink link blueGrayLinkDotted">
                            <xsl:attribute name="onclick">
                                openDepositDetalInfo(<xsl:value-of select="$depositId"/>, <xsl:value-of select="$groupCode"/> <xsl:value-of select="$segmentCode"/>);
                                return false;
                            </xsl:attribute>
                            Подробнее
                        </span>
                        <input type="hidden">
                            <xsl:attribute name="id">
                                <xsl:value-of select="$productId"/>
                            </xsl:attribute>
                            <xsl:attribute name="value">
                                <xsl:value-of select="$depositId"/>
                            </xsl:attribute>
                        </input>
                    </xsl:if>
                </div>
        </xsl:if>
        </xsl:if>
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

        <xsl:if test="$admin">
            <xsl:call-template name="depositPeriod">
                <xsl:with-param name="filteredElements" select="$filteredElements"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="lowercase">
        <xsl:param name="input"/>
        <xsl:value-of select="translate($input,
        'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ',
        'йцукенгшщзхъфывапролджэячсмитьбю')"/>
    </xsl:template>

    <xsl:template name="depositProductClass">
        <xsl:param name="premiumSegment"/>
        <xsl:choose>
            <xsl:when test="$premiumSegment = true()">
                <xsl:value-of select="'premiumDepositProductItem'"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'depositProductItem'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="depositPeriod">
        <xsl:param name="filteredElements"/>
        <xsl:choose>
            <xsl:when test="boolean($filteredElements/period)">
                <!-- Получение максимального и минимального сроков вклада -->
                <xsl:variable name="staticPeriods" select="$filteredElements/period[not(contains(text(), 'U'))]"/>
                <xsl:variable name="intervalPeriods" select="$filteredElements/period[contains(text(), 'U')]"/>
                <xsl:variable name="minPeriod">
                    <xsl:call-template name="minPeriod">
                        <xsl:with-param name="staticPeriods"   select="$staticPeriods"/>
                        <xsl:with-param name="intervalPeriods" select="$intervalPeriods"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:variable name="maxPeriod">
                    <xsl:call-template name="maxPeriod">
                        <xsl:with-param name="staticPeriods"   select="$staticPeriods"/>
                        <xsl:with-param name="intervalPeriods" select="$intervalPeriods"/>
                    </xsl:call-template>
                </xsl:variable>

                <xsl:choose>
                    <xsl:when test="not($minPeriod = $maxPeriod)">
                        <xsl:choose>
                            <xsl:when test="$admin">
                                <xsl:text> (на срок от </xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                               <xsl:if test="not($maxPeriod = $minPeriod)"> от </xsl:if>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:call-template name="parsePeriod">
                            <xsl:with-param name="period" select="$minPeriod"/>
                        </xsl:call-template>

                        <xsl:text>до </xsl:text>
                        <xsl:call-template name="parsePeriod">
                            <xsl:with-param name="period" select="$maxPeriod"/>
                        </xsl:call-template>

                        <xsl:if test="$admin">
                            <xsl:text>)</xsl:text>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="years"  select="number(substring($minPeriod, 1, 2))"/>
                        <xsl:variable name="months" select="number(substring($minPeriod, 4, 2))"/>
                        <xsl:variable name="days"   select="number(substring($minPeriod, 7, 4))"/>
                        <xsl:choose>
                            <xsl:when test="$years!=0">
                                <xsl:value-of select="$years"/>
                                <xsl:choose>
                                    <xsl:when test="$years = 1">
                                        <xsl:text> год</xsl:text>
                                    </xsl:when>
                                    <xsl:when test="$years &gt;= 2 and $years &lt; 5">
                                        <xsl:text> года</xsl:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text> лет</xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                            <xsl:when test="$months!=0">
                                <xsl:value-of select="$months"/>
                                <xsl:choose>
                                    <xsl:when test="$years = 1">
                                        <xsl:text> месяц</xsl:text>
                                    </xsl:when>
                                    <xsl:when test="$years &gt;= 2 and $years &lt; 5">
                                        <xsl:text> месяца</xsl:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text> месяцев</xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                            <xsl:when test="$days!=0">
                                <xsl:value-of select="$days"/>
                                <xsl:choose>
                                    <xsl:when test="$years = 1">
                                        <xsl:text> день</xsl:text>
                                    </xsl:when>
                                    <xsl:when test="$years &gt;= 2 and $years &lt; 5">
                                        <xsl:text> дня</xsl:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text> дней</xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$admin">
                        <xsl:text> (бессрочный)</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>бессрочный</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
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
        <xsl:param name="segmentCode"/>

        <xsl:choose>
            <xsl:when test="boolean($filteredElements/period)">
                <xsl:for-each select="xalan:distinct($filteredElements/period)">
                    <xsl:variable name="period" select="."/>
                    <xsl:for-each select="xalan:distinct($filteredElements[period=$period]/minBalance)">
                        <xsl:variable name="minBalance" select="."/>
                        <xsl:variable name="rates" select="xalan:distinct($filteredElements[period=$period and minBalance=$minBalance]/interestRate)"/>
                        <xsl:choose>
                            <xsl:when test="count($rates)&gt;1">
                                <xsl:variable name="elements" select="$filteredElements[period=$period and minBalance=$minBalance]"/>
                                <xsl:variable name="segmentFilteredElements" select="$elements[$segmentCode = segmentCode]"/>

                                <xsl:choose>
                                    <xsl:when test="count($segmentFilteredElements) > 0">
                                        <xsl:call-template name="rates">
                                            <xsl:with-param name="elements" select="$segmentFilteredElements"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="rates">
                                            <xsl:with-param name="elements" select="$elements"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
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

                            <xsl:variable name="elements" select="$filteredElements[minBalance=$minBalance]"/>
                            <xsl:variable name="segmentFilteredElements" select="$elements[$segmentCode = segmentCode]"/>

                            <xsl:choose>
                                <xsl:when test="count($segmentFilteredElements) > 0">
                                    <xsl:call-template name="rates">
                                        <xsl:with-param name="elements" select="$segmentFilteredElements"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="rates">
                                        <xsl:with-param name="elements" select="$elements"/>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
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

    <xsl:template name="rates">
        <xsl:param name="elements"/>

        <xsl:for-each select="$elements">
            <xsl:sort data-type="text" order="descending" select="translate(interestDateBegin, '.', '')"/>
            <xsl:if test="position()=1">
                <xsl:element name="rate">
                    <xsl:value-of select="interestRate"/>
                </xsl:element>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->