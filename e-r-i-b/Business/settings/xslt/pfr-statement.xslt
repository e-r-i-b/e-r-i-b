<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
<xsl:param name="mode" select="'view'"/>

<xsl:variable name="Statement" select="/ФайлПФР/ПачкаИсходящихДокументов/ИЗВЕЩЕНИЕ_О_СОСТОЯНИИ_ИЛС"/>
<xsl:variable name="StatementVersion" select="$Statement/ВариантИзвещения"/>
<xsl:variable name="styleClass"/>
<xsl:variable name="styleSpecial"/>
<xsl:variable name="styleClassTitle"/>


<xsl:template match="/">
    <xsl:choose>
		<xsl:when test="$mode = 'view'">
			<xsl:call-template name="SpecialPart"/>
            <xsl:call-template name="CommonPart"/>
		</xsl:when>
		<xsl:otherwise>
			<h2 class="section">
		        Извещение о состоянии индивидуального лицевого счета из Пенсионного фонда России
		    </h2>
			<xsl:call-template name="SpecialPart"/>
			<br/>
            <xsl:call-template name="CommonPart"/>
		</xsl:otherwise>
	</xsl:choose>
    
</xsl:template>

<xsl:template name="CommonPart">
    <xsl:choose>
		<xsl:when test="$mode = 'view'">
            <xsl:if test="$Statement/СведенияОвзносахНаСтраховую != ''">
                <table id="detailInfoTable" class="simpleTable">
                    <tr class="tblInfHeader">
                        <th class="titleTable">Год</th>
                        <th class="titleTable align-right">Сумма средств на начало года</th>
                        <th class="titleTable align-right">Сумма средств, поступивших за год</th>
                    </tr>
                    <xsl:for-each select="$Statement/СведенияОвзносахНаСтраховую">
                        <tr  class="listItemBottom">
                            <td class="align-left leftPaddingCell"><xsl:value-of select="Год"/></td>
                            <td class="align-right"><xsl:value-of select="format-number(ПоступилоНа01.01.года, '### ###,##', 'sbrf')"/> руб.</td>
                            <td class="align-right"><xsl:value-of select="format-number(ПоступилоЗаГод, '### ###,##', 'sbrf')"/> руб.</td>
                        </tr>
                    </xsl:for-each>
                </table>
            </xsl:if>
		</xsl:when>
		<xsl:otherwise>
            <xsl:if test="$Statement/СведенияОвзносахНаСтраховую != ''">
                <table class="pfr-statement-table">
                    <tr>
                        <th>Год</th>
                        <th>Сумма средств на начало года</th>
                        <th>Сумма средств, поступивших за год</th>
                    </tr>
                    <xsl:for-each select="$Statement/СведенияОвзносахНаСтраховую">
                        <tr>
                            <td><xsl:value-of select="Год"/></td>
                            <td><xsl:value-of select="format-number(ПоступилоНа01.01.года, '### ###,##', 'sbrf')"/> руб.</td>
                            <td><xsl:value-of select="format-number(ПоступилоЗаГод, '### ###,##', 'sbrf')"/> руб.</td>
                        </tr>
                    </xsl:for-each>
                </table>
            </xsl:if>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="SpecialPart">
    <fieldset>
    <table class="pfr-abstract-info">
    <tbody>
        <xsl:call-template name="pfrStatementRow">
            <xsl:with-param name="rowName">По состоянию на</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="$Statement/ДатаПоСостояниюНа"/></xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="pfrStatementRow">
            <xsl:with-param name="rowName">ФИО</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="concat($Statement/ФИО/Имя, ' ',
                                                 $Statement/ФИО/Отчество, ' ',
                                                 substring($Statement/ФИО/Фамилия,1,1), '.')"/></xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="pfrStatementRow">
            <xsl:with-param name="rowName">Страховой номер индивидуального лицевого счета</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="$Statement/СтраховойНомер"/></xsl:with-param>
        </xsl:call-template>
        <xsl:if test="$StatementVersion='СЗИ-6-1' or $StatementVersion='СЗИ-6-2' or $StatementVersion='СЗИ-6-3' or
                      $StatementVersion='СЗИ-6-4' or $StatementVersion='СЗИ-6-5'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Страховые взносы за <xsl:value-of select="$Statement/Год"/> год:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/НакопительныеЗаГод, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-1' or $StatementVersion='СЗИ-6-2'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Передано в УК "<xsl:value-of select="$Statement/НаименованиеУК"/>":</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/СуммаСПНпереданныхВУК, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-3'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Передано в НПФ "<xsl:value-of select="$Statement/НаименованиеНПФ"/>":</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/СуммаСПНпереданныхВНПФ, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-1' or $StatementVersion='СЗИ-6-2' or $StatementVersion='СЗИ-6-3'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">В т.ч. доход ПФР за <xsl:value-of select="$Statement/ГодДохода"/> год:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/ДоходЗаГод, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-1'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Результат инвестирования УК за <xsl:value-of select="$Statement/Год"/> год:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/РезультатИнвестирования, '##,###############', 'sbrf')"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-2' or $StatementVersion='СЗИ-6-3'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Доход от инвестирования УК:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/ДоходОтИнвестирования, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-4' or $StatementVersion='СЗИ-6-5'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Доход ПФР за <xsl:value-of select="$Statement/ГодДохода"/> год:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/ДоходЗаГод, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-4'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Инвестирование осуществляет:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="$Statement/НаименованиеНПФ"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$StatementVersion='СЗИ-6-5'">
            <xsl:call-template name="pfrStatementRow">
                <xsl:with-param name="rowName">Средства НПФ, переданные ВЭБ УК:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="format-number($Statement/СуммаСПНпереданныхИзНПФ, '### ###,##', 'sbrf')"/> руб.</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:call-template name="pfrStatementRow">
            <xsl:with-param name="rowName">Порядковый номер запроса в текущем году:</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="$Statement/ПорядковыйНомерЗапросаВтекущемГоду"/></xsl:with-param>
        </xsl:call-template>
    </tbody>
    </table>
    </fieldset>
</xsl:template>

<xsl:template name="pfrStatementRow">
    <xsl:param name="rowName"/>
    <xsl:param name="rowValue"/>
    <tr>
        <td class="align-right field">
            <xsl:copy-of select="$rowName"/>
        </td>
        <xsl:if test="substring($rowValue,1,3) != 'NaN'">
            <td>
                <span class="bold">
                    <xsl:copy-of select="$rowValue"/>
                </span>
            </td>
        </xsl:if>
    </tr>
</xsl:template>

</xsl:stylesheet>