<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:pu="java://com.rssl.phizic.security.PermissionUtil">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>

    <xsl:variable name="styleClass" select="'form-row'"/>
    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
    <xsl:variable name="styleSpecial" select="''"/>
    <!--todo убрать после того как во всех ТБ на КСШ будет реализован фукционал подачи заявки об утере сберкнижки (CHG039252)
        LossPassbookApplicationThroughKSSH - заявка через КСШ-->
    <xsl:variable name="throughKSSH" select="pu:impliesService('LossPassbookApplicationThroughKSSH')"/>

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
    <input type="hidden" id="moneyOrTransferToAccountRadio" name="moneyOrTransferToAccountRadio"/>
    <input type="hidden" id="toAccountSelect" name="toAccountSelect"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">По вкладу(счету):</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="accountNumber" select="accountNumber"/>
					<xsl:if test="$personAvailable">
                        <xsl:variable name="linkId" select="accountLink"/>
                        <xsl:variable name="passbookAccounts" select="document(concat('passbook-accounts.xml?accountId=', accountLink))/entity-list/*"/>
						<select id="accountSelect" name="accountSelect">
							<xsl:choose>
								<xsl:when test="count($passbookAccounts) = 0">
									<option value="">Нет доступных счетов</option>
								</xsl:when>
								<xsl:otherwise>
                                    <xsl:if test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                                        <option value="">Выберите счет списания</option>
                                    </xsl:if>
									<xsl:for-each select="$passbookAccounts">
										<xsl:variable name="id" select="field[@name='code']/text()"/>
                                        <option>
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="$id"/>
											</xsl:attribute>
											<xsl:if test="$accountNumber = ./@key  or $linkId=$id">
												<xsl:attribute name="selected">true</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="./@key"/>&nbsp;
											[<xsl:value-of select="./field[@name='name']"/>]
											<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
											<xsl:value-of select="./field[@name='currencyCode']"/>
										</option>
									</xsl:for-each>
								</xsl:otherwise>
							</xsl:choose>
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled"><option selected="selected">Счет клиента</option></select>
					</xsl:if>
            </xsl:with-param>
        </xsl:call-template>
        
        <xsl:if test="not($throughKSSH)">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <input type="radio"
                        id="group0_id1"
                        name="closingAmountOrPassbookDuplicateRadio"
                        value="1"
                        onchange="javascript:changedGroup0()"
                        onclick="javascript:changedGroup0()"
                        onkeypress="javascript:changedGroup0()"
                        style="border:none"/>
                        &nbsp;Выдать дубликат сберегательной книжки&nbsp;
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <input type="radio"
                           id="group0_id0"
                           name="closingAmountOrPassbookDuplicateRadio"
                           value="0"
                           onchange="javascript:changedGroup0()"
                           onclick="javascript:changedGroup0()"
                           onkeypress="javascript:changedGroup0()"
                           style="border:none"/>
                    &nbsp;Расторгнуть&nbsp;договор&nbsp;(сумму вклада выдать наличными)
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

		<script type="text/javascript">
		<![CDATA[
		function changedGroup0()
		{
			if ( document.getElementById('group0_id0').checked )
			{
				var moneyOrTransferToAccountRadio = document.getElementById('moneyOrTransferToAccountRadio');
				moneyOrTransferToAccountRadio.value = 0;
			}
			else if ( document.getElementById('group0_id1').checked )
			{
               var closingAmountOrPassbookDuplicateRadio=document.getElementById('group0_id1');
               var moneyOrTransferToAccountRadio = document.getElementById('moneyOrTransferToAccountRadio');
               closingAmountOrPassbookDuplicateRadio.value=1;
               moneyOrTransferToAccountRadio.value = 1;
			}
		}
        	]]>

       function init()
		{
			var group0_id0 = document.getElementById('group0_id0');
            var group0_id1 = document.getElementById('group0_id1');
            var moneyOrTransferToAccountRadio = document.getElementById('moneyOrTransferToAccountRadio');

			<xsl:if test="closingAmountOrPassbookDuplicateRadio = '' or
						  closingAmountOrPassbookDuplicateRadio = '0'">
				group0_id0.checked = true;
                moneyOrTransferToAccountRadio.value = 0;
			</xsl:if>
            <xsl:if test="closingAmountOrPassbookDuplicateRadio = '1'">
				group0_id1.checked = true;
                moneyOrTransferToAccountRadio.value = 1;
			</xsl:if>
        }

        <xsl:if test="not($throughKSSH)">
		    init();
        </xsl:if>    

		</script>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">По вкладу(счету):</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="accountNumber"/>&nbsp;
                [<xsl:value-of select="accountSelectName"/>]&nbsp;
                <xsl:value-of select="amountCurrency"/>
            </xsl:with-param>
        </xsl:call-template>
		<xsl:if test="closingAmountOrPassbookDuplicateRadio = 0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                <xsl:with-param name="rowValue">
                    &nbsp;Расторжение&nbsp;договора&nbsp;с&nbsp;закрытием&nbsp;счета&nbsp;по&nbsp;вкладу&nbsp;<br/>
					<xsl:if test="moneyOrTransferToAccountRadio = 0">
					&nbsp;Причитающуюся&nbsp;сумму&nbsp;вклада&nbsp;выдать&nbsp;<b>наличными&nbsp;деньгами&nbsp;</b>
					</xsl:if>
                </xsl:with-param>
            </xsl:call-template>
		</xsl:if>
		<xsl:if test="closingAmountOrPassbookDuplicateRadio = 1">
            <xsl:call-template name="standartRow">
                  <xsl:with-param name="rowValue">
                    Выдать&nbsp;дубликат&nbsp;сберегательной&nbsp;книжки&nbsp;<br/>
                  </xsl:with-param>
            </xsl:call-template>
		</xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус заявки</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="state">
                        <span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
                            <xsl:choose>
                                <xsl:when test="$app = 'PhizIA'">
                                    <xsl:call-template name="employeeState2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="clientState2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                        </span>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <!--Пустой блок для смещения штампа вниз-->
            <div class="block90"></div>

    </xsl:template>

    <xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрена</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнена</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрена</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">Приостановлена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW' and $webRoot='/PhizIA'">Приостановлена</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template name="standartRow">
        <xsl:param name="id"/>
        <xsl:param name="lineId" />
        <xsl:param name="required" select="'true'"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>

        <div class="{$styleClass}">
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id">
                    <xsl:copy-of select="$lineId"/>
                </xsl:attribute>
            </xsl:if>
            <div class="paymentLabel">
                <span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
                <xsl:if test="$required = 'true' and $mode = 'edit'">
                    <span id="asterisk_{$id}" class="asterisk">*</span>
                </xsl:if>
            </div>
            <div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>
                <div class="errorDiv" style="display: none;"/>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="rowName"/>
        <xsl:param name="lineId" />
        <xsl:param name="colspanNum"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> html" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->