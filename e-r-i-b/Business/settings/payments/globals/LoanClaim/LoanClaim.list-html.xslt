<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="no"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>
	<xsl:template match="/">
		<xsl:if test="count(/list-data/entity-list/entity) != 0">
				<xsl:apply-templates select="/list-data/entity-list"/>
		</xsl:if>
	</xsl:template>

	<!-- список -->
	<xsl:template match="/list-data/entity-list">
		<!-- заголовок списка -->
		<!--<table class="userTab" id="tableTitle" cellpadding="0" cellspacing="0" width="100%">-->
			<!--<tbody>-->
				<tr class="tblInfHeader">
					<td width="20">
						<input name="isSelectAll" type="checkbox" style="border:none" onclick="switchSelection()"/>
					</td>
					<td width="50">Номер</td>
					<td width="80">Дата</td>
					<td>Кредитный продукт</td>
					<td nowrap="true">Статус документа</td>
					<td>Запрашиваемая сумма</td>
					<td>Запрашиваемый срок</td>
					<td>Одобренная сумма</td>
					<td>Одобренный срок</td>
				</tr>
				<xsl:apply-templates/>
				<!-- footer -->
			<!--</tbody>-->
		<!--</table>-->
	</xsl:template>

	<!-- строка таблицы -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<tr>
			<xsl:attribute name="style">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
			<xsl:variable name="stateNumber" select="position()"/>
			<xsl:variable name="claimId" select="@key"/>
			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem" align="center">
				<a href="{$webRoot}/private/claims/default-action.do?id={@key}">
					<xsl:choose>
						<xsl:when test="(string-length(documentNumber) = 6)">
							<xsl:value-of select="documentNumber"/>
						</xsl:when>
					    <xsl:otherwise>
						    б/н (без номера)
					    </xsl:otherwise>
					</xsl:choose>
				</a>&nbsp;
			</td>
			<td class="listItem" align="center">                           
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>
				&nbsp;<xsl:value-of select="substring(dateCreated,12,5)"/>
			</td>
			<td class="listItem" align="center">
				<xsl:value-of select="attributes.product-name.value"/>
				&nbsp;
			</td>
			<td class="listItem">
				<span id='{$stateNumber}' style="text-decoration:underline;"
				     onmouseover="showLayerFull('{$stateNumber}','$$description{$stateNumber}','default',event);"
				     onmouseout="hideLayer('$$description{$stateNumber}');">
						<xsl:call-template name='state2text'>
							<xsl:with-param name='code'>
								<xsl:value-of select='state.code'/>
							</xsl:with-param>
						</xsl:call-template>
				</span>
				<xsl:variable name="stateDescription" select="document( concat('claim-state-messages.xml?claimId=',$claimId) )/entity-list/entity/field[@name='message']"/>
				<xsl:if test="$stateDescription"> 
					<div id='$$description{$stateNumber}'
						 onmouseover="showLayerFull('{$stateNumber}','$$description{$stateNumber}','default',event);"
						 onmouseout="hideLayer('$$description{$stateNumber}');"
						 class='layerFon'
						 style='position:absolute; display:none; width:260px; height:85px;overflow:auto;'>
							<xsl:value-of select="$stateDescription"/>
							<xsl:if test="string-length(attributes.bank-comment.value) > 0"><br/>Комментарий банка: <xsl:value-of select="attributes.bank-comment.value"/></xsl:if>
					</div>
				</xsl:if>  &nbsp;
			</td>
			<td class="listItem" align="right">
				<xsl:if test="attributes.client-request-amount.value!=''">
				<xsl:value-of select="attributes.client-request-amount.value"/>
				&nbsp;
					<xsl:value-of select="attributes.loan-currency.value"/>
				</xsl:if>
				&nbsp;
			</td>
			<td class="listItem">
				<xsl:value-of select="attributes.client-request-term.value"/>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<xsl:if test="attributes.bank-accept-amount.value!=''">
				<xsl:if test="state.category='processed'">
					<xsl:value-of select="attributes.bank-accept-amount.value"/>
					&nbsp;
					<xsl:value-of select="attributes.loan-currency.value"/>
				</xsl:if>
				</xsl:if>
				&nbsp;
			</td>
			<td class="listItem">
				<xsl:if test="string(number(substring-before(substring-after(attributes.bank-accept-term.value,'-'),'-')))!='NaN'">
					<xsl:value-of select="number(substring-before(substring-after(attributes.bank-accept-term.value,'-'),'-'))"/>
				</xsl:if>
				&nbsp;
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='DRAFT'">Черновик</xsl:when>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Принят</xsl:when>
			<xsl:when test="$code='COMPLETION'">Требуется доработка</xsl:when>
			<xsl:when test="$code='CONSIDERATION'">В рассмотрении</xsl:when>
			<xsl:when test="$code='EXECUTED'">Кредит выдан</xsl:when>
			<xsl:when test="$code='APPROVED'">Утвержден</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>