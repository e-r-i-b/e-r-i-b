<?xml version="1.0" encoding="windows-1251"?>

<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" encoding="windows-1251"/>

	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="detailsUrl" select="'details?id='"/>

	<xsl:template match="/">
        <xsl:choose>
            <xsl:when test="count(//product)>0">
                <xsl:call-template name="table"/>
            </xsl:when>
            <xsl:otherwise>
                <br/>
                <br/>
                <div class="messageTab" align="center">Не найдено ни одного депозитного продукта!</div>
            </xsl:otherwise>
        </xsl:choose>
	</xsl:template>

	<xsl:template name="table">
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td class="tblHeaderLeftCorner"><img src="{$resourceRoot}/images/globals/1x1.gif" alt="" width="9" height="1" border="0"/></td>
			<td class="tblHeader">&nbsp;
				<img src="{$resourceRoot}/skins/green/images/iconMid_banksDeposits.gif" alt="" border="0"/>
			</td>
			<td class="tblHeader" width="100%"><span>"Вклады банка"</span></td>
			<td class="tblHeaderRightCorner"><img src="{$resourceRoot}/images/globals/1x1.gif" alt="" width="9" height="1" border="0"/></td>
		</tr>
		<tr>
			<td colspan="4">
				<xsl:call-template name="html"/>
	        </td>
		</tr>
		<tr>
			<td class="tblFooterLeftCorner"><img src="{$resourceRoot}/images/globals/1x1.gif" alt="" width="1" height="10" border="0"/></td>
			<td colspan="2" class="tblFooter"><img src="{$resourceRoot}/images/globals/1x1.gif" alt="" width="1" height="10" border="0"/></td>
			<td class="tblFooterRightCorner"><img src="{$resourceRoot}/images/globals/1x1.gif" alt="" width="1" height="10" border="0"/></td>
		</tr>
	</table>
	</xsl:template>

	<xsl:template name="html">
		<table cellpadding="0" cellspacing="0" width="100%" class="tblInf">
			<tr class="tblInfHeader">		
				<td>Срок вклада</td>
				<td>Cумма вклада</td>
				<td>Валюта вклада</td>
				<td>Процентная ставка (% годовых)</td>
				<td>Периодичность выплаты процентов</td>
				<td>Возможность пополнения вклада</td>
				<td>Возможность снятия части вклада</td>
			</tr>
			<xsl:apply-templates select="//product"/>
		</table>
	</xsl:template>

	<xsl:template match="product">
		<xsl:variable name="product" select="."/>
		<xsl:variable name="currencies" select="dictionaries/entity-list[@name='currencies']/entity/@key"/>
		<tr>
			<td class="ListItem" style="background-color:#F8FBFD;" colspan="7" align="center">
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td width="20%">&#160;</td>
						<td align="center">
							<a href="#" onClick="loadNewAction('','');window.location='{concat($detailsUrl,@id)}'">
								<xsl:value-of select="@name"/>
							</a>
						</td>
						<td align="right" width="20%">
							<a href="#" onClick="loadNewAction('','');window.location='{concat($detailsUrl,@id)}'">
								подробнее...
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<xsl:for-each select="$currencies">
				<tr>
			<td class="ListItem">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="periods" select="$elements_currency/value[@field='period']"/>
					<xsl:for-each select="$periods">
						<xsl:sort data-type="text"/>
						<xsl:if test="position()=1">
							<script type="text/javascript">document.write(formatPeriod('<xsl:value-of select="."/>'))
							</script>
							&#160;
						</xsl:if>
						<xsl:if test="not(position()=1) and position()=count($periods)">
							-
							<script type="text/javascript">document.write(formatPeriod('<xsl:value-of select="."/>'))
							</script>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
			</td>
			<td class="ListItem">
					<xsl:variable name="curr" select="."/>
					<xsl:variable
							name="minimumAmounts"
							select="$product/data/element[value[@field='currency']/text()=$curr]/value[@field='minimumAmount']"/>
					<xsl:variable
							name="maximumAmounts"
							select="$product/data/element[value[@field='currency']/text()=$curr]/value[@field='maximumAmount']"/>
					<xsl:for-each select="$minimumAmounts">
						<xsl:sort data-type="number"/>
						<xsl:if test="position()=1">
							от
							<xsl:value-of select="number(.)"/>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
			</td>
			<td class="ListItem">
					<xsl:value-of select="."/>
					<br/>
			</td>
			<td class="ListItem">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="interestRates"
					              select="$elements_currency/value[@field='interestRate']"/>
					<xsl:for-each select="$interestRates">
						<xsl:sort data-type="number"/>
						<xsl:if test="position()=1">
							от
							<xsl:value-of select="."/>
							&#160;
						</xsl:if>
						<xsl:if test="not(position()=1) and position()=count($interestRates)">
							до
							<xsl:value-of select="."/>
							&#160;
						</xsl:if>
					</xsl:for-each>
					<br/>
			</td>
			<td class="ListItem">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="periods" select="$elements_currency/value[@field='paymentPeriod']"/>
					<xsl:for-each select="$periods">
						<xsl:sort data-type="text"/>
						<xsl:if test="position()=1">
							<xsl:choose>
								<xsl:when test="$periods='at-end'">
									В конце срока
								</xsl:when>
								<xsl:when test="$periods='monthly'">
									Ежемесячно
								</xsl:when>
								<xsl:when test="$periods='quarterly'">
									Ежеквартально
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="$periods"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
					</xsl:for-each>
					<br/>
			</td>
			<td class="ListItem">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="addit" select="$elements_currency/value[@field='additionalFee']"/>
					<xsl:for-each select="$addit">
						<xsl:sort data-type="text"/>
						<xsl:if test="position()=1">
							<xsl:choose>
								<xsl:when test="$addit='true'">
									Да
								</xsl:when>
								<xsl:when test="$addit='false'">
									Нет
								</xsl:when>
							</xsl:choose>
						</xsl:if>
					</xsl:for-each>
					<br/>
			</td>
			<td class="ListItem">
					<xsl:variable name="curr" select="."/>

					<xsl:variable name="elements_currency"
					              select="$product/data/element/*[@field='currency' and .= $curr]/.."/>

					<xsl:variable name="removal" select="$elements_currency/value[@field='anticipatoryRemoval']"/>
					<xsl:for-each select="$removal">
						<xsl:sort data-type="text"/>
						<xsl:if test="position()=1">
							<xsl:choose>
								<xsl:when test="$removal='true'">
									Да
								</xsl:when>
								<xsl:when test="$removal='false'">
									Нет
								</xsl:when>
							</xsl:choose>
						</xsl:if>
					</xsl:for-each>
					<br/>
			</td>
			</tr>
			</xsl:for-each>
		</tr>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="products.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="..\products\Доходный\description.xml" srcSchemaRoot="product" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="table/xsl:apply&#x2D;templates" x="156" y="18"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->