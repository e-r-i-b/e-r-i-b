<?xml version="1.0" encoding="windows-1251"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

   <xsl:import href="static://include/functions/set/set.distinct.function.xsl"/>

   <xsl:output method="html" encoding="windows-1251"/>

   <xsl:param name="webRoot" select="'webRoot'"/>
   <xsl:param name="detailsUrl" select="'details?id='"/>
   <xsl:variable name="kinds" select="//loan-kinds/loan-kind"/>
   <xsl:variable name="products" select="//loan-products/loan-product"/>

   <xsl:template match="/">
	   <xsl:choose>
		   <xsl:when test="count($products)=0">
			   <span class="workspaceTitle">Не найдено ни одного кредитного продукта</span>
		   </xsl:when>
		   <xsl:otherwise>
			   <span class="workspaceTitle">Кредитные продукты банка</span>
			   <br/><br/><br/>
			   <xsl:for-each select="$kinds">
				   <xsl:variable name="currentKind" select="."/>
				   <xsl:variable name="currentProducts" select="$products[@kindId=$currentKind/@id]"/>
				   <xsl:variable name="currencies"
						 select="$currentProducts/conditions/dynamic/condition/value[@name='currency']"/>
				   <xsl:if test="count($currentProducts) != 0">
					   <xsl:for-each select="set:distinct($currencies)">
							<xsl:variable name="currency" select="text()"/>
							<xsl:variable name="currencyName">
								<xsl:choose>
									<xsl:when test="$currency = 'RUB'">руб.</xsl:when>
									<xsl:when test="$currency = 'USD'">$</xsl:when>
									<xsl:when test="$currency = 'EUR'">евро</xsl:when>
									<xsl:otherwise><xsl:value-of select="$currency"/></xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<span class="infoTitle">
								Вид кредита "<xsl:value-of select="$currentKind/@name"/>"&#160;в&#160;
								<xsl:choose>
									<xsl:when test="$currency = 'RUB'">рублях</xsl:when>
									<xsl:when test="$currency = 'USD'">долларах США</xsl:when>
									<xsl:when test="$currency = 'EUR'">евро</xsl:when>
									<xsl:otherwise><xsl:value-of select="$currency"/></xsl:otherwise>
								</xsl:choose>
							</span>
							<table class="userTab" cellspacing="0" cellpadding="2">
								<tr class="titleTable" align="center">
									<td width="15%" style="vertical-align:middle;">Размер кредита</td>
									<td width="20%" style="vertical-align:middle;">Срок кредитования (месяцев)</td>
									<td width="15%" style="vertical-align:middle;">% ставка по кредиту</td>
									<td width="15%" style="vertical-align:middle;">% ставка по кредиту для зарплатных клиентов</td>
									<td width="35%" style="vertical-align:middle;">Описание кредита</td>
							   </tr>
							   <xsl:for-each select="$currentProducts">
								   <xsl:variable name="currentProduct" select="."/>
								   <xsl:variable name="currentProductValues"
												 select="$currentProduct/conditions/dynamic/condition[value[@name='currency']/text()=$currency]"/>
								   <xsl:if test="count($currentProductValues) != 0">
									   <tr>
										   <td class="ListItem" colspan="4">
											   <table cellspacing="0" cellpadding="0">
												   <tr>
													   <td style="color:#903E98;font-size:8pt;text-align:left;vertical-align:bottom">
														   &#160;Кредит&#160;"<xsl:value-of
															   select="$currentProduct/@name"/>"&#160;&#160;&#160;
													   </td>
													   <td class="infoTitle">
														   <xsl:variable name="details"><xsl:value-of select="@id"/>&#038;currency=<xsl:value-of select="$currency"/></xsl:variable>
														   <a href="{concat($detailsUrl,$details)}">Подробнее</a>
													   </td>
												   </tr>
											   </table>
										   </td>
										   <td class="ListItem">&#160;</td>
									   </tr>
									   <tr>
										   <xsl:variable name="minAmounts"
														 select="$currentProductValues[value[@name='minAmount']/text() != '']/value[@name='minAmount']/text()"/>
										   <xsl:variable name="maxAmounts"
														 select="$currentProductValues[value[@name='maxAmount']/text() != '']/value[@name='maxAmount']/text()"/>
										   <xsl:variable name="durations"
														 select="$currentProductValues/value[@name='duration']"/>
										   <xsl:variable name="rates"
														 select="$currentProductValues/value[@name='rate']"/>
										   <td class="ListItem">
											   <xsl:choose>
													<xsl:when test="count($minAmounts) = 0 and count($maxAmounts) = 0">
														Не указано
													</xsl:when>
													<xsl:otherwise>
															<xsl:if test="$minAmounts != ''">
															   <xsl:for-each select="$minAmounts">
																 <xsl:sort data-type="number"/>
																 <xsl:if test="position()=1">
																	От&#160;<xsl:value-of select="number(.)"/>&#160;
																 </xsl:if>
															  </xsl:for-each>
															</xsl:if>
															<xsl:if test="$maxAmounts != ''">
															  <xsl:for-each select="$maxAmounts">
																 <xsl:sort data-type="number" order="descending"/>
																 <xsl:if test="position()=1">
																	<br/>&#160;до&#160;<xsl:value-of select="number(.)"/>&#160;
																 </xsl:if>
															  </xsl:for-each>
															</xsl:if>
															<xsl:if test="$minAmounts != '' or $maxAmounts != ''">
																<xsl:value-of select="$currencyName"/>
															</xsl:if>
													</xsl:otherwise>
											   </xsl:choose>
										   </td>
										   <td class="ListItem">
											   <xsl:choose>
													<xsl:when test="count($durations) = 0">
														Не указано
													</xsl:when>
													<xsl:otherwise>
														<xsl:for-each select="set:distinct($durations)">
															<xsl:sort select="substring-after(.,'M:')" data-type="number"/>
															<xsl:if test="position()!=1">, </xsl:if>
															<xsl:value-of select="substring-after(.,'M:')"/>
														</xsl:for-each>
													</xsl:otherwise>
											   </xsl:choose>
										   </td>
										   <td class="ListItem">&#160;
											   <xsl:for-each select="set:distinct($rates)">
												   <xsl:sort select="text()" data-type="number"/>
												   <xsl:if test="position()!=1">, </xsl:if>
												   <xsl:value-of select="number(text())"/>
											   </xsl:for-each>
										   </td>
										   <td class="ListItem">&#160;
											   <script type="text/javascript">
												   var ratesSet = new Array();

												   function sortNumber(a,b)
												   {
													 return a - b;
												   }

												   function outArray(array)
												   {
													 array.sort(sortNumber);
													 document.write(array[0]);
													 for (var i=1; i &lt; array.length; i++)
														document.write(", " + array[i]);
												   }

												   function initRatesSet()
												   {
													  <xsl:for-each select="$currentProductValues">
														 var curr = parseFloat('<xsl:value-of
															  select="./value[@name='rate']/text()"/>');
														 var reducingPatronRate = parseFloat('<xsl:value-of
															  select="./value[@name='reducingPatronRate']/text()"/>');
														 if (!isNaN(reducingPatronRate)) curr -= reducingPatronRate;
														 if (!ratesSet.contains(curr)) ratesSet[ratesSet.length] = curr;
													  </xsl:for-each>
												   }

												   initRatesSet();
												   outArray(ratesSet);
												</script>
										   </td>
										   <td class="ListItem">&#160;<xsl:value-of
												   select="$currentProduct/@description"/>&#160;</td>
									 </tr>
								   </xsl:if>
								</xsl:for-each>
								<tr><td>&#160;</td></tr>
								<tr><td>&#160;</td></tr>
						   </table>
					   </xsl:for-each>
				   </xsl:if>
				 </xsl:for-each>
	        </xsl:otherwise>
	    </xsl:choose>
   </xsl:template>

      </xsl:stylesheet>
