<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                exclude-result-prefixes="xalan">
	<xsl:output method="html" indent="yes" encoding="windows-1251" version="1.0"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="curDate"/>
    <xsl:param name="admin"/>
    <xsl:param name="isPension"/>
    <xsl:param name="needInitialFee"/>
    <xsl:param name="name"/>

	<xsl:template match="/">

        <xsl:variable name="partOfNameLowerCase">
            <xsl:call-template name="lowercase">
                <xsl:with-param name="input" select="$name"/>
            </xsl:call-template>
        </xsl:variable>

        <xsl:variable name="nifProducts" select="/products/product[data/main/initialFee != string($needInitialFee)]"/>
        <xsl:variable name="nameProducts" select="$nifProducts[contains(translate(data/name/text(),'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ',
                                                                                                   'йцукенгшщзхъфывапролджэячсмитьбю'), $partOfNameLowerCase)]"/>

        <xsl:variable name="subTypes" select="$nifProducts//subTypeName[contains(translate(./text(),'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ',
                                                                                                   'йцукенгшщзхъфывапролджэячсмитьбю'), $partOfNameLowerCase)]"/>

        <xsl:choose>
            <xsl:when test="count($nameProducts) &gt; 0 or count($subTypes) &gt; 0">
                <xsl:for-each select="$nifProducts">
                    <xsl:variable name="product" select="."/>

                    <xsl:choose>
                        <xsl:when test="contains(translate($product/data/name/text(),'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ',
                                                                                     'йцукенгшщзхъфывапролджэячсмитьбю'), $partOfNameLowerCase)">

                            <tr><td class="tree">
                                <xsl:call-template name="data">
                                    <xsl:with-param name="product" select="$product"/>
                                    <xsl:with-param name="dateFilteredElements"
                                                    select="./data/options/element"/>
                                </xsl:call-template>
                            </td></tr>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="subElements" select="$product/data/options/element"/>
                            <xsl:variable name="selectedSubElements" select="$subElements[contains(translate(subTypeName/text(),'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ',
                                                                                                                                'йцукенгшщзхъфывапролджэячсмитьбю'), $partOfNameLowerCase)]"/>

                            <xsl:if test="count($selectedSubElements) &gt; 0">
                                <tr><td class="tree">
                                    <xsl:call-template name="data">
                                        <xsl:with-param name="product" select="$product"/>
                                        <xsl:with-param name="dateFilteredElements" select="$selectedSubElements"/>
                                    </xsl:call-template>
                                </td></tr>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
                <script type="text/javascript">
                    function selectRow(row)
                    {
                        row.getElementsByTagName('input')[0].checked = true;
                    }
                    function switchProduct(row)
                    {
                        var elements = row.getElementsByTagName('input');
                        var mode = elements[0].checked;
                        for(var i=1;i&lt;elements.length;i++) {
                            elements[i].checked=mode;
                            elements[i].disabled=mode;
                        }
                    }
                    function switchShowSubproduct(row) {
                        var info = $(row).find('div.departmentTree').eq(0);
                        info.css('display', info.css('display') == 'none' ? 'block' : 'none');
                    }
                </script>
            </xsl:when>
            <xsl:otherwise>
                <div class="depositProductAddInfo">
                    <xsl:text>Не найдены данные, удовлетворяющие условиям фильтра.</xsl:text>
                </div>
            </xsl:otherwise>
        </xsl:choose>
	</xsl:template>

    <xsl:template name="data">
        <xsl:param name="product"/>
        <xsl:param name="dateFilteredElements"/>

        <xsl:variable name="productId" select="$product/data/productId"/>


            <div class="">
                <input name="selectedIds" onclick="switchProduct(this.parentNode.parentNode);"
                       type="checkbox">
                    <xsl:attribute name="value">
                        <xsl:value-of select="$productId"/>
                    </xsl:attribute>
                </input>
                <xsl:choose>
                    <xsl:when test="count($dateFilteredElements)&lt;1">
                        <xsl:value-of select="$productId"/>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$product/data/name/text()"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <a href="#" onclick="switchShowSubproduct(this.parentNode.parentNode);">
                        <!-- Название вклада -->
                            <xsl:value-of select="$productId"/>
                            <xsl:text>  </xsl:text>
                            <xsl:value-of select="$product/data/name/text()"/>
                        </a>
                    </xsl:otherwise>
                </xsl:choose>
            </div>

            <!-- Таблица с подвкладами -->

            <div class="departmentTree" style="display:none;">
                <xsl:for-each select="xalan:distinct($dateFilteredElements/id/text())">
                    <div>
                        <xsl:variable name="id" select="."/>
                        <input type="checkbox" name="selectedIds">
                            <xsl:attribute name="value">
                                <xsl:value-of select="$productId"/>
                                <xsl:text>:</xsl:text>
                                <xsl:value-of select="$id"/>
                            </xsl:attribute>
                        </input>

                        <xsl:value-of select="$id"/>
                        <xsl:variable name="subTypeName" select="$dateFilteredElements[id/text()=$id]/subTypeName/text()"/>
                        <xsl:if test="$subTypeName != ''">
                            <xsl:text> "</xsl:text>
                            <xsl:value-of select="$subTypeName"/>
                            <xsl:text>"</xsl:text>
                        </xsl:if>

                        <xsl:choose>
                            <xsl:when test="boolean($dateFilteredElements[id/text()=$id]/period)">
                                <xsl:text> на срок </xsl:text>

                                <xsl:variable name="period" select="$dateFilteredElements[id/text()=$id]/period/text()"/>
                                <xsl:choose>
                                    <xsl:when test="contains($period, 'U')">
                                        <xsl:call-template name="parseInterval">
                                            <xsl:with-param name="interval" select="$period"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="parsePeriod">
                                            <xsl:with-param name="period" select="$period"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text> бессрочный </xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:text> по ставке </xsl:text>
                        <xsl:value-of
                                select="$dateFilteredElements[id/text()=$id]/interestRate/text()"/>
                        <xsl:text>%</xsl:text>
                    </div>
                </xsl:for-each>
            </div>
    </xsl:template>

    <xsl:template name="lowercase">
        <xsl:param name="input"/>
        <xsl:value-of select="translate($input,
        'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮQWERTYUIOPASDFGHJKLZXCVBNM',
        'йцукенгшщзхъфывапролджэячсмитьбюqwertyuiopasdfghjklzxcvbnm')"/>
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
					<xsl:text> год </xsl:text>
				</xsl:when>
                <xsl:when
                        test="(($years mod 10) &lt; 5) and (0 &lt; ($years mod 10)) and (($years &lt; 11) or (20 &lt; $years))">
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
				<xsl:when test="$months &lt; 5">
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
				<xsl:when test="(($days mod 10) &lt; 5) and ($days != 11)">
					<xsl:text> дня </xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text> дней </xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>

	</xsl:template>

    <xsl:template name="parseInterval">
		<xsl:param name="interval"/>
        <xsl:variable name="from" select="substring($interval, 1, 10)"/>
        <xsl:variable name="to" select="substring($interval, 12, 22)"/>

        <xsl:variable name="fromText">
            <xsl:call-template name="parseIntervalPeriod">
                <xsl:with-param name="period" select="$from"/>
            </xsl:call-template>
        </xsl:variable>

        <xsl:variable name="toText">
            <xsl:call-template name="parseIntervalPeriod">
                <xsl:with-param name="period" select="$to"/>
            </xsl:call-template>
        </xsl:variable>

        <xsl:text>от </xsl:text>

        <xsl:call-template name="parsePeriodNumber">
            <xsl:with-param name="period" select="$from"/>
        </xsl:call-template>

        <xsl:if test="$fromText!=$toText">
              <xsl:value-of select="$fromText"/>
        </xsl:if>

        <xsl:text> до </xsl:text>

        <xsl:call-template name="parsePeriodNumber">
            <xsl:with-param name="period" select="$to"/>
        </xsl:call-template>
        <xsl:value-of select="$toText"/>

    </xsl:template>

    <xsl:template name="parseIntervalPeriod">
		<xsl:param name="period"/>
		<xsl:variable name="years" select="number(substring($period, 1, 2))"/>
		<xsl:variable name="months" select="number(substring($period, 4, 2))"/>
		<xsl:variable name="days" select="number(substring($period, 7, 4))"/>
		<xsl:choose>
			<xsl:when test="$years!=0">
				<xsl:choose>
					<xsl:when test="(($years mod 10) = 1) and ($years != 11)">
						<xsl:text> года</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> лет</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$months!=0">
				<xsl:choose>
					<xsl:when test="$months = 1">
						<xsl:text> месяца</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> месяцев</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$days!=0">
				<xsl:choose>
					<xsl:when test="(($days mod 10) = 1) and ($days != 11)">
						<xsl:text> дня</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> дней</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="parsePeriodNumber">
		<xsl:param name="period"/>
		<xsl:variable name="years" select="number(substring($period, 1, 2))"/>
		<xsl:variable name="months" select="number(substring($period, 4, 2))"/>
		<xsl:variable name="days" select="number(substring($period, 7, 4))"/>
		<xsl:choose>
			<xsl:when test="$years!=0">
				<xsl:value-of select="$years"/>
			</xsl:when>
			<xsl:when test="$months!=0">
				<xsl:value-of select="$months"/>
			</xsl:when>
			<xsl:when test="$days!=0">
				<xsl:value-of select="$days"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->