<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
        xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
        xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
        extension-element-prefixes="phizic">
    <xsl:output method="html" version="1.0" indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="longOffer" select="'longOffer'"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
        <xsl:variable name="fromResourceType" select="fromResourceType"/>
        <xsl:variable name="loanCurrencySign" select="loanCurrency"/>

            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Погашение кредита</xsl:with-param>
            </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ДАТА ОПЕРАЦИИ:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ВРЕМЯ ОПЕРАЦИИ (МСК):</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ИДЕНТИФИКАТОР ОПЕРАЦИИ:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentNumber"/></xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">КАРТА:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint(fromAccountSelect)"/></xsl:with-param>
        </xsl:call-template>


        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">НОМЕР ДОГОВОРА:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="agreementNumber"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">НОМЕР ССУДНОГО СЧЕТА:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="loanAccountNumber"/></xsl:with-param>
        </xsl:call-template>


        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">НОМЕР ОТДЕЛЕНИЯ:</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="office"/></xsl:with-param>
        </xsl:call-template>

        <br/>
        <xsl:if test="string-length(overpayment)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ПЕРЕПЛАТА:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(overpayment, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(earlyBaseDebtAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ДОСРОЧНО В СЧЕТ ОСН.ДОЛГА:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(earlyBaseDebtAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(earlyReturnAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ПЛАТА ЗА ДОСРОЧНЫЙ ВОЗВРАТ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(earlyReturnAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(otherCostsAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">РАСХОДЫ ПО ВЗИМАНИЮ ЗАДОЛЖЕННОСТИ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(otherCostsAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(penaltyUntimelyInsurance)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">НЕУСТОЙКА ЗА СТРАХОВАНИЕ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(penaltyUntimelyInsurance, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(penaltyAccountOperationsAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">НЕУСТ.ЗА ПРОСРОЧ.ПЛАТЫ ЗА ОПЕРАЦИИ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(penaltyAccountOperationsAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(accountOperationsAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ПЛАТА ЗА ОПЕРАЦИИ ПО СЧЕТУ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(accountOperationsAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(penaltyDelayDebtAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">НЕУСТОЙКА ЗА ПРОСРОЧ.ОСН.ДОЛГА:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(penaltyDelayDebtAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(penaltyDelayPercentAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">НЕУСТОЙКА ЗА ПРОСРОЧ.ПРОЦЕНТЫ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(penaltyDelayPercentAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <xsl:if test="string-length(delayedPercentsAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ПРОСРОЧ. ПРОЦЕНТЫ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(delayedPercentsAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <xsl:if test="string-length(interests-amount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">СРОЧНЫЕ ПРОЦЕНТЫ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(interests-amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <xsl:if test="string-length(delayedDebtAmount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ПРОСРОЧ.ОСНОВНОЙ ДОЛГ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(delayedDebtAmount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <xsl:if test="string-length(principal-amount)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">ОСН.ДОЛГ:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="format-number(principal-amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
                </xsl:with-param>
            </xsl:call-template>
         </xsl:if>

         <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">СУММА ПЛАТЕЖА:</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:value-of select="format-number(amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrencySign"/>
            </xsl:with-param>
         </xsl:call-template>

        <xsl:if test="$longOffer">

            <br/>
            <xsl:call-template name="printTitle">
                <xsl:with-param name="value">Параметры длительного поручения</xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Дата окончания:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:variable name="endDate" select="longOfferEndDate"/>
                    <xsl:value-of select="concat(substring($endDate, 9, 2), '.', substring($endDate, 6, 2), '.', substring($endDate, 1, 4))"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Повторяется:</xsl:with-param>
                <xsl:with-param name="value">Каждый месяц</xsl:with-param>
            </xsl:call-template>

            <xsl:variable name="firstDate"  select="firstPaymentDate"/>
            <xsl:variable name="day"    select="substring($firstDate, 9, 2)"/>
            <xsl:variable name="month"  select="substring($firstDate, 6, 2)"/>

            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Дата оплаты:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:variable name="monthDescription" select="document('monthsDictionary')/entity-list/entity[@key=$month]/text()"/>
                    <xsl:value-of select="concat($monthDescription, '. ', $day, ' число')"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Приоритет:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:variable name="priority" select="longOfferPrioritySelect"/>
                    <xsl:value-of select="document('priority.xml')/entity-list/entity[@key=$priority]/text()"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Сводка:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="concat('Каждый месяц', ' ' , $day, '-го числа')"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:variable name="nextPayDate"  select="firstPaymentDate"/>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Ближайший платеж:</xsl:with-param>
                <xsl:with-param name="value">
                    <xsl:value-of select="concat(substring($nextPayDate, 9, 2), '.', substring($nextPayDate, 6, 2), '.', substring($nextPayDate, 1, 4))"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

    </xsl:template>

    <xsl:template name="printTitle">
        <xsl:param name="value"/>
        <div class="checkSize title"><xsl:copy-of select="$value"/></div>
        <br/>
    </xsl:template>

    <xsl:template name="printTitleAdditional">
        <xsl:param name="value"/>
        <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
        <br/>
    </xsl:template>

    <xsl:template name="printText">
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$value"/></div>
    </xsl:template>

    <xsl:template name="paramsCheck">
        <xsl:param name="title"/>
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
    </xsl:template>
  
</xsl:stylesheet>