<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                exclude-result-prefixes="xalan">
	<xsl:output method="html" indent="yes" encoding="windows-1251" version="1.0"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="curDate"/>
    <xsl:param name="isPension"/>

    <xsl:template match="/product">
        <xsl:call-template name="productInfo">
            <xsl:with-param name="depositProduct" select="."/>
            <xsl:with-param name="dateFilteredElements" select="./data/options/element"/>
        </xsl:call-template>

        <script type="text/javascript">
            function changeVisibleSubType(elementId)
            {
                if ($("input[id="+elementId+"]").attr("checked") == true)
                {
                    $("#"+elementId+"Label").html(' доступен');
                }
                else
                {
                    $("#"+elementId+"Label").html(' не доступен');
                }
            }

        </script>
    </xsl:template>

    <xsl:template name="productInfo">
        <xsl:param name="dateFilteredElements"/>
        <xsl:param name="depositProduct"/>

        <xsl:variable name="productId" select="$depositProduct/data/productId"/>

        <xsl:for-each select="xalan:distinct($dateFilteredElements/id/text())">
            <tr>
                <xsl:variable name="id" select="."/>
                <td>
                    <xsl:value-of select="$id"/>
                </td>
                <td>
                    <div style="margin-left:20px;">
                    <xsl:variable name="subTypeName" select="$dateFilteredElements[id/text()=$id]/subTypeName/text()"/>
                    <xsl:if test="$subTypeName != ''">
                        <xsl:text> "</xsl:text>
                        <xsl:value-of select="$subTypeName"/>
                        <xsl:text>"</xsl:text>
                        <xsl:call-template name="subTypeDescription">
                            <xsl:with-param name="id" select="$id"/>
                            <xsl:with-param name="dateFilteredElements" select="$dateFilteredElements"/>
                        </xsl:call-template>
                    </xsl:if>
                    </div>
                </td>
                <td>
                    <xsl:variable name="checked" select="boolean($dateFilteredElements[id/text()=$id and availToOpen/text()='true'])"/>
                    <input type="checkbox" name="depositSubTypeIds" onclick="changeVisibleSubType(this.id);">
                        <xsl:attribute name="value">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                        <xsl:attribute name="id">depositSubTypeIds<xsl:value-of select="$id"/></xsl:attribute>
                        <xsl:if test="$checked">
                            <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>
                    <label>
                        <xsl:attribute name="id">depositSubTypeIds<xsl:value-of select="$id"/>Label</xsl:attribute>
                        <xsl:choose>
                            <xsl:when test="$checked">
                                <xsl:attribute name="checked">true</xsl:attribute>
                                <xsl:text> доступен</xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text> не доступен</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </label>
                </td>
            </tr>
        </xsl:for-each>
    </xsl:template>

    <!--Описание подвида вклада-->
    <xsl:template name="subTypeDescription">
        <xsl:param name="id"/>
        <xsl:param name="dateFilteredElements"/>

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


</xsl:stylesheet>