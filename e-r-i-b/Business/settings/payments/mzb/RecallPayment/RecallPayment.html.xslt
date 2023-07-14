<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
		<!ENTITY nbsp "&#160;">
		]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:variable name="formNameValue" select='//recalled-document-form-name'/>
	<xsl:param name="paymentdescriptor" select="document('paymentDescriptors')/entity-list/entity[@key=$formNameValue/text()]"/>

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
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="amount" name="amount" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="amount"/>
			    </xsl:attribute>
		</input>

		<input id="amountCurrency" name="amountCurrency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="amountCurrency"/>
			    </xsl:attribute>
		</input>

		<input id="recalled-document-form-name" name="recalled-document-form-name" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="recalled-document-form-name"/>
			    </xsl:attribute>
		</input>

		<input id="recalledDocumentNumber" name="recalledDocumentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="recalledDocumentNumber"/>
			    </xsl:attribute>
		</input>

		<input id="parentId" name="parentId" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="parentId"/>
	            </xsl:attribute>
		</input>

		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
		     <tr>
		        <td align="right" valign="middle"><img src="{$resourceRoot}/images/RurPaymentPh.gif"
		                                               border="0"/></td>
		        <td colspan="2">
		            <table class="MaxSize paymentTitleFon">
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr>
		                <td align="center" class="silverBott paperTitle">
		                    <table height="100%" width="420" cellspacing="0" cellpadding="0"
		                           class="paymentTitleFon">
		                    <tr>
		                        <td class="titleHelp">
		                             <span class="formTitle">Отзыв перевода</span>
		                             <br/>
		                             Используйте данную форму для отзыва перевода
		                        </td>
		                    </tr>
		                    </table>
		                </td>
		                <td width="100%">&nbsp;</td>
		            </tr>
		            </table>
		        </td>
		    </tr>
			<tr>
				<td class="Width120 LabelAll">Номер отзыва</td>
				<td>
					<input type="text" id="documentNumber" name="documentNumber" size="30"
					       value="{documentNumber}" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата отзыва</td>
				<td>
					<input type="text" id="documentDate" name="documentDate" size="30">
						<xsl:attribute name="value">
							<xsl:value-of select="documentDate"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер отзываемого перевода</td>
				<td>
					<input type="text" id="recalledDocumentNumber" name="recalledDocumentNumber" size="30"
					       disabled="true">
						<xsl:attribute name="value">
							<xsl:value-of select="recalledDocumentNumber"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Tип документа</td>
				<td>
					<input type="text" id="recalledDocumentType" name="recalledDocumentType" size="30"
					       disabled="true" value="">
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Валюта документа</td>
				<td><input id="currency" name="currency" type="text" value="{amountCurrency}" size="30"
				           disabled="true"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма перевода</td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="30" disabled="true"/></td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>

		<script type="text/javascript">
			function setCurrencyCode()
				{
					var currency = document.getElementById("сurrencyCodeSelect");
					var currencyCodeElem       = document.getElementById("currency");
					currencyCodeElem.value  = currency.value;
				}
			function init()
			 {
				 var currencyElem    = document.getElementById("currency");
				 if (currencyElem.value=="")
					 currencyElem.value = "RUB"

	  			 document.getElementById('recalledDocumentType').value='<xsl:value-of select="$paymentdescriptor"/>';
			 }
			 init();
		</script>

	</xsl:template>
    <xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">

            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/RurPaymentPh.gif"
                                                       border="0"/></td>
                <td>
                    <table>
                     <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table width="420" cellspacing="0" cellpadding="0">
                            <tr>
                                <span class="formTitle">Отзыв перевода</span>
                                <br/>
                                <td class="titleHelp"><span id="titleHelp">Используйте данную форму для отзыва перевода</span></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>

			<tr>
				<td class="Width120 LabelAll">Номер отзыва</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата отзыва</td>
				<td>
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер отзываемого перевода</td>
				<td>
					<xsl:value-of select="recalledDocumentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Tип документа</td>
				<td><xsl:value-of select="$paymentdescriptor"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Валюта документа</td>
				<td><xsl:value-of select="amountCurrency"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма перевода</td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Статус платежа</td>
				<td>
					<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
				</td>
			</tr>
            <tr><td colspan="2">&nbsp;<br/>&nbsp;</td></tr>
        </table>
    </xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">На исполнении</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:when test="$code='D'">Отказан</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='V'">Отозван</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>