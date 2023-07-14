<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:cu="java://com.rssl.phizic.business.dictionaries.currencies.CurrencyUtils"
                xmlns:xalan="http://xml.apache.org/xalan"
                exclude-result-prefixes="cu xalan">
	<xsl:output method="xml" indent="yes" version="1.0" encoding="windows-1251" cdata-section-elements="text"/>
    <!-- Код вида вклада -->
    <xsl:param name="productId"/>
    <!-- Сегодняшняя дата -->
    <xsl:param name="curDate"/>
    <!-- Признак, определяющий, обновляем или впервые выгружаем информацию по вкладу -->
    <xsl:param name="isUpdate"/>
    <!-- Код шаблона договора для тарифного плана "1" -->
    <xsl:param name="tariff1AgreementCode"/>
    <!-- Код шаблона договора для тарифного плана "2" -->
    <xsl:param name="tariff2AgreementCode"/>
    <!-- Код шаблона договора для тарифного плана "3" -->
    <xsl:param name="tariff3AgreementCode"/>
	<!-- Типа шаблона для сберегательного счета -->
    <xsl:param name="type4Kind_61"/>
	<!-- Код шаблона для сберегательного счета -->
    <xsl:param name="code4Kind_61"/>

	<xsl:template match="/">
        <xsl:variable name="qvb"
                      select="/package/rollout/transaction/table[@name='qvb']/replace/record[field[@name='QDTN1' and @value=$productId] and (field[@name='QOPEND' and translate(@value, '.','')&gt;translate($curDate, '.', '')] or $isUpdate='true') and field[@name='QDTSUB' and @value!='48' and @value!='49']]"/>
        <xsl:variable name="qvkl_val"
                      select="/package/rollout/transaction/table[@name='qvkl_val']/replace/record[field[@name='QVKL_T_QDTN1' and @value=$productId]]"/>
        <xsl:variable name="dcf_tar"
                      select="/package/rollout/transaction/table[@name='dcf_tar']/replace/record[field[@name='KOD_VKL_QDTN1' and @value=$productId] and field[@name='KOD_VKL_CLNT' and @value&lt;'200']]"/>
        <xsl:variable name="field_tdog"
                      select="/package/rollout/transaction/table[@name='FIELD_TDOG']/replace/record[field[@name='VID' and @value=$productId]]"/>
        <xsl:variable name="bch_bux"
                      select="/package/rollout/transaction/table[@name='BCH_BUX']/replace/record[field[@name='BCH_VKL' and @value=$productId]]"/>
        <xsl:variable name="contractDeposit"
                      select="/package/rollout/transaction/table[@name='ContractDeposit2']/replace/record[field[@name='QDTN1' and @value=$productId]]"/>
        <xsl:variable name="contractTemplates"
                      select="/package/rollout/transaction/table[@name='ContractTemplate']/replace/record[field[@name='TYPE']/@value='32']"/>
        <xsl:variable name="collateralAgreementWithMinimumBalance"
                      select="/package/rollout/transaction/table[@name='ContractTemplate']/replace/record[field[@name='TYPE' and @value='1'] and field[@name='CODE' and @value='012111064/1']]"/>
        <xsl:variable name="collateralAgreementWithCapitalization"
                      select="/package/rollout/transaction/table[@name='ContractTemplate']/replace/record[field[@name='TYPE' and @value='1'] and field[@name='CODE' and @value='014110003/3']]"/>
        <xsl:variable name="collateralAgreementWithTariffPlan"
                      select="/package/rollout/transaction/table[@name='ContractTemplate']/replace/record[field[@name='TYPE' and @value='1'] and field[@name='CODE' and (translate(@value, ' ', '')=$tariff1AgreementCode or translate(@value, ' ', '')=$tariff2AgreementCode or translate(@value, ' ', '')=$tariff3AgreementCode)]]"/>
        <xsl:variable name="v_alg"
                      select="/package/rollout/transaction/table[@name='v_alg']/replace/record[field[@name='V_AVKL_QDTN1' and @value=$productId]]"/>
        <xsl:variable name="qvb_group"
                      select="/package/rollout/transaction/table[@name='QVB_GROUP']/replace/record"/>
        <xsl:variable name="qvb_tp"
                      select="/package/rollout/transaction/table[@name='QVB_TP']/replace/record[field[@name='TP_QDTN1' and @value=$productId]]"/>
        <xsl:variable name="collateralAgreementWithTariffPlan4Type_61"
                      select="/package/rollout/transaction/table[@name='ContractTemplate']/replace/record[$productId = '61' and field[@name='TYPE' and @value=$type4Kind_61] and field[@name='CODE' and @value=$code4Kind_61] and field[@name='START' and translate(@value, '.', '') &lt;= translate($curDate, '.', '')] and field[@name='STOP' and translate($curDate, '.', '') &lt;= translate(@value, '.', '')]]"/>
        <xsl:variable name="qvb_pargr"
                      select="/package/rollout/transaction/table[@name='QVB_PARGR']/replace/record"/>
        <product>
            <xsl:choose>
            <xsl:when test="count($qvb)&gt;0 and count($qvkl_val)&gt;0 and count($dcf_tar)&gt;0 and count($bch_bux)&gt;0">
			<structure>
				<field name="productId" description="Код вида вклада"/>
				<field name="name" description="Наименование вклада"/>
				<node name="main" description="Общая информация">
                    <field name="initialFee" description="Вклад с начальным взносом или без"/>
                    <field name="minimumBalance" description="Вклад с неснижаемым остатком или без"/>
                    <field name="capitalization" description="Доступна капитализация или нет"/>
				</node>
				<node name="options" description="Отличительная информация по подвидам вкладов">
					<node name="element" description="Подвид вклада в представлении ИКФЛ">
						<field name="id" description="Код подвида вклада"/>
						<field name="dateBegin" description="Дата начала приема"/>
						<field name="dateEnd" description="Дата прекращения открытия вкладов"/>
						<field name="minAdditionalFee" description="Размер минимального допвзноса"/>
						<field name="period" description="Срок вклада."/>
						<field name="periodInDays" description="Срок вклада в днях."/>
						<field name="currencyCode" description="Цифровой ISO-код валюты"/>
						<field name="minBalance" description="Нижняя граница диапазона суммы, для которой открывается данный подвид"/>
						<field name="maxBalance" description="Верхняя граница диапазона суммы, для которой открывается данный подвид"/>
						<field name="interestRate" description="Процентная ставка"/>
						<field name="interestDateBegin" description="Начало действия процентной ставки"/>
                        <field name="templateId" description="Идентификатор шаблона договора для данного подвида"/>
                        <field name="tariffPlanAgreementId" description="Идентификатор дополнительного соглашения по тарифным планам для данного подвида"/>
                        <field name="pension" description="Признак, определяющий, является ли процентная ставка льготной"/>
                        <field name="percent" description="Процентная ставка (для отображения в договоре)"/>
                        <field name="subTypeName" description="Название вклада для конкретного подвида (могут отличаться)"/>
                        <field name="tariffPlanCode" description="Код тарифного плана"/>
                        <field name="segmentCode" description="Номер сегмента клиента"/>
                        <field name="creditOperations" description="Доступность приходных операций"/>
                        <field name="debitOperations" description="Доступность расходных операций"/>
                        <field name="debitOperationsCode" description="Тип расходной операции по вкладу"/>
                        <field name="interestOperations" description="Доступность операций по выдаче процентов"/>
                        <field name="incomingTransactions" description="Приходные операции по вкладу"/>
					    <field name="frequencyAdd" description="Периодичность внесения дополнительных взносов"/>
					    <field name="debitTransactions" description="Расходные операции по вкладу"/>
					    <field name="frequencyPercent" description="Периодичность выплаты процентов"/>
					    <field name="percentOrder" description="Порядок уплаты процентов"/>
					    <field name="incomeOrder" description="Порядок начисления дохода при досрочном востребовании вклада"/>
					    <field name="renewals" description="Количество разрешенных пролонгаций"/>
                        <node name="group" description="Группа вкладов">
                            <field name="groupCode" description="Код группы"/>
                            <field name="groupName" description="Наименование группы"/>
                            <node name="groupParams" description="Параметры группы вкладов">
                                <field name="paramDateBegin" description="Начало действия условия"/>
                                <field name="charitableContribution" description="Отчисления в благотворительные фонды"/>
                                <field name="pensionRate" description="Отдельные ставки для пенсионеров"/>
                                <field name="pensionSumLimit" description="Ограничение максимальной суммы для пенсионеров"/>
                                <field name="percentCondition" description="Начисление процентов на выдаваемые проценты"/>
                                <field name="sumLimit" description="Ограничение максимальной суммы вклада"/>
                                <field name="sumLimitCondition" description="Условие ставки для расчета % на сумму превышения максимальной суммы"/>
                                <field name="socialType" description="Тип группы вкладов"/>
                                <field name="promoCodeSupported" description="Признак наличия промо-ставок"/>
                            </node>
                        </node>
                        <node name="tariffDependence" description="Зависимость подвида от тарифного плана">
                            <node name="tariff" description="Запись о тарифе">
                                <field name="tariffDateBegin" description="Дата начала действия"/>
                                <field name="tariffDateEnd" description="Дата окончания действия"/>
                                <field name="tariffCode" description="Код тарифного плана"/>
                            </node>
                        </node>
					</node>
				</node>
                <node name="templates" description="Шаблоны условий размещения денежных средств во вклад">
                    <node name="template" description="Шаблон">
                        <field name="templateId" description="Идентификатор шаблона"/>
                        <field name="text" description="Текст шаблона"/>
                    </node>
                </node>
                <node name="collateralAgreements" description="Дополнительные соглашения">
                    <node name="collateralAgreementWithMinimumBalance" description="Дополнительное соглашение на изменение неснижаемого остатка">
                        <node name="collateralAgreement" description="Дополнительное соглашение">
                            <field name="agreementId" description="Идентификатор соглашения"/>
                            <field name="text" description="Текст соглашения"/>
                        </node>
                    </node>
                    <node name="collateralAgreementWithCapitalization" description="Дополнительное соглашение на перечисление процентов на карту">
                        <node name="collateralAgreement" description="Дополнительное соглашение">
                            <field name="agreementId" description="Идентификатор соглашения"/>
                            <field name="text" description="Текст соглашения"/>
                        </node>
                    </node>
                    <node name="collateralAgreementWithTariffPlan" description="Дополнительное соглашение по тарифным планам">
                        <node name="collateralAgreement" description="Дополнительное соглашение">
                            <field name="agreementId" description="Идентификатор соглашения"/>
                            <field name="text" description="Текст соглашения"/>
                        </node>
                    </node>
                    <node name="collateralAgreementWithTariffPlan4Type_61" description="Дополнительные соглашения для Сберегательного счета">
                        <node name="collateralAgreement" description="Дополнительное соглашение">
                            <field name="agreementId" description="Идентификатор соглашения"/>
                            <field name="text"        description="Текст соглашения"/>
                        </node>
                    </node>
                </node>
			</structure>
			<data>
				<productId>
					<xsl:value-of select="$productId"/>
				</productId>
				<name>
                    <xsl:variable name="newName" select="normalize-space($qvb[field[@name='QOPEND' and translate(@value, '.','')&gt;translate($curDate, '.', '')]]/field[@name='QDN']/@value)"/>
                    <xsl:choose>
                        <xsl:when test="$newName = ''">
                            <xsl:value-of select="normalize-space($qvb/field[@name='QDN']/@value)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$newName"/>
                        </xsl:otherwise>
                    </xsl:choose>
				</name>
				<main>
                    <initialFee>
                        <xsl:value-of select="$qvb/field[@name='QPFS']/@value != 0"/>
                    </initialFee>
                    <minimumBalance>
                        <xsl:value-of select="$qvb/field[@name='QN_RESN']/@value != 0"/>
                    </minimumBalance>
                    <capitalization>
                        <xsl:value-of select="$qvb/field[@name='QCAP']/@value != 0"/>
                    </capitalization>
				</main>
				<options>
					<xsl:for-each select="$qvb">
						<xsl:variable name="id" select="./field[@name='QDTSUB']/@value"/>
                        <xsl:variable name="debitOperationsCode" select="./field[@name='Q_EXPENS']/@value"/>
                        <xsl:variable name="creditOperations" select="count($v_alg[field[@name='V_TYPE_OP' and @value=26] and field[@name='V_AVKL_QDTSUB' and @value=$id]])&gt;0"/>
                        <xsl:variable name="debitOperations" select="count($v_alg[field[@name='V_TYPE_OP' and @value=27] and field[@name='V_AVKL_QDTSUB' and @value=$id]])&gt;0"/>
                        <xsl:variable name="interestOperations" select="count($v_alg[field[@name='V_TYPE_OP' and @value=65] and field[@name='V_AVKL_QDTSUB' and @value=$id]])&gt;0"/>
                        <xsl:variable name="subTypeName" select="normalize-space(./field[@name='QDN']/@value)"/>
						<xsl:variable name="dateBegin" select="./field[@name='QOPBEG']/@value"/>
						<xsl:variable name="dateEnd" select="./field[@name='QOPEND']/@value"/>
						<xsl:variable name="minAdditionalFee" select="./field[@name='QMINADD']/@value"/>
                        <xsl:variable name="isNullPeriod" select="./field[@name='Q_SROK']/@null"/>
						<xsl:variable name="period">
							<xsl:call-template name="parsePeriod">
								<xsl:with-param name="period" select="./field[@name='Q_SROK']/@value"/>
							</xsl:call-template>
						</xsl:variable>
						<xsl:variable name="periodInDays">
							<xsl:call-template name="periodBux">
								<xsl:with-param name="id" select="$id"/>
							</xsl:call-template>
						</xsl:variable>
						<xsl:variable name="val" select="./field[@name='QVAL']/@value"/>
                        <xsl:variable name="groupCode" select="./field[@name='Q_GROUP']/@value"/>
                        <xsl:variable name="tariffDependence" select="$qvb_tp[field[@name='TP_QDTSUB' and @value=$id]and field[@name='TP_QVAL' and @value=$val]]"/>

						<xsl:for-each
                                select="$qvkl_val[field[@name='QVKL_T_QDTSUB' and @value=$id] and field[@name='QVKL_T_QVAL' and @value=$val]]">
							<xsl:variable name="currencyCode" select="./field[@name='QVKL_V']/@value"/>
							<xsl:variable name="currencyAlphabeticCode">
								<xsl:value-of select="cu:getCurrencyCodeByNumericCode($currencyCode)"/>
							</xsl:variable>
							<xsl:if test="$currencyAlphabeticCode!=''">
                                <!-- Нельготные вклады -->
                                <xsl:call-template name="element">
                                    <xsl:with-param name="dcf_tar" select="$dcf_tar"/>
                                    <xsl:with-param name="id" select="$id"/>
                                    <xsl:with-param name="currencyCode" select="$currencyCode"/>
                                    <xsl:with-param name="dateBegin" select="$dateBegin"/>
                                    <xsl:with-param name="dateEnd" select="$dateEnd"/>
                                    <xsl:with-param name="minAdditionalFee" select="$minAdditionalFee"/>
                                    <xsl:with-param name="isNullPeriod" select="$isNullPeriod"/>
                                    <xsl:with-param name="period" select="$period"/>
                                    <xsl:with-param name="periodInDays" select="$periodInDays"/>
                                    <xsl:with-param name="currencyAlphabeticCode" select="$currencyAlphabeticCode"/>
                                    <xsl:with-param name="contractDeposit" select="$contractDeposit"/>
                                    <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
                                    <xsl:with-param name="collateralAgreementWithTariffPlan"         select="$collateralAgreementWithTariffPlan"/>
                                    <xsl:with-param name="collateralAgreementWithTariffPlan4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
                                    <xsl:with-param name="field_tdog" select="$field_tdog"/>
                                    <xsl:with-param name="pension" select="0"/>
                                    <xsl:with-param name="subTypeName" select="$subTypeName"/>
                                    <xsl:with-param name="groupCode" select="$groupCode"/>
                                    <xsl:with-param name="group" select="$qvb_group"/>
                                    <xsl:with-param name="tariffDependence" select="$tariffDependence"/>
                                    <xsl:with-param name="creditOperations" select="$creditOperations"/>
                                    <xsl:with-param name="debitOperations" select="$debitOperations"/>
                                    <xsl:with-param name="debitOperationsCode" select="$debitOperationsCode"/>
                                    <xsl:with-param name="interestOperations" select="$interestOperations"/>
                                    <xsl:with-param name="groupParams" select="$qvb_pargr"/>
                                </xsl:call-template>
                                <!-- Льготные вклады -->
                                <xsl:call-template name="element">
                                    <xsl:with-param name="dcf_tar" select="$dcf_tar"/>
                                    <xsl:with-param name="id" select="$id"/>
                                    <xsl:with-param name="currencyCode" select="$currencyCode"/>
                                    <xsl:with-param name="dateBegin" select="$dateBegin"/>
                                    <xsl:with-param name="dateEnd" select="$dateEnd"/>
                                    <xsl:with-param name="minAdditionalFee" select="$minAdditionalFee"/>
                                    <xsl:with-param name="isNullPeriod" select="$isNullPeriod"/>
                                    <xsl:with-param name="period" select="$period"/>
                                    <xsl:with-param name="periodInDays" select="$periodInDays"/>
                                    <xsl:with-param name="currencyAlphabeticCode" select="$currencyAlphabeticCode"/>
                                    <xsl:with-param name="contractDeposit" select="$contractDeposit"/>
                                    <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
                                    <xsl:with-param name="collateralAgreementWithTariffPlan"         select="$collateralAgreementWithTariffPlan"/>
                                    <xsl:with-param name="collateralAgreementWithTariffPlan4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
                                    <xsl:with-param name="field_tdog" select="$field_tdog"/>
                                    <xsl:with-param name="pension" select="1"/>
                                    <xsl:with-param name="subTypeName" select="$subTypeName"/>
                                    <xsl:with-param name="groupCode" select="$groupCode"/>
                                    <xsl:with-param name="group" select="$qvb_group"/>
                                    <xsl:with-param name="tariffDependence" select="$tariffDependence"/>
                                    <xsl:with-param name="creditOperations" select="$creditOperations"/>
                                    <xsl:with-param name="debitOperations" select="$debitOperations"/>
                                    <xsl:with-param name="debitOperationsCode" select="$debitOperationsCode"/>
                                    <xsl:with-param name="interestOperations" select="$interestOperations"/>
                                    <xsl:with-param name="groupParams" select="$qvb_pargr"/>
                                </xsl:call-template>
							</xsl:if>
						</xsl:for-each>
					</xsl:for-each>
				</options>
                <templates>
                    <xsl:for-each select="$contractTemplates">
                        <xsl:variable name="template" select="."/>
                        <template>
                            <templateId>
                                <xsl:value-of select="$template/field[@name='TEMPLATEID']/@value"/>
                            </templateId>
                            <text>
                                <xsl:value-of select="normalize-space($template/field[@name='TEXT'])" disable-output-escaping="yes"/>
                            </text>
                        </template>
                    </xsl:for-each>
                </templates>
                <collateralAgreements>
                    <collateralAgreementWithTariffPlan>
                        <xsl:for-each select="$collateralAgreementWithTariffPlan">
                            <xsl:variable name="template" select="."/>
                            <collateralAgreement>
                                <agreementId>
                                    <xsl:value-of select="$template/field[@name='TEMPLATEID']/@value"/>
                                </agreementId>
                                <text>
                                    <xsl:value-of select="normalize-space($template/field[@name='TEXT'])" disable-output-escaping="yes"/>
                                </text>
                            </collateralAgreement>
                        </xsl:for-each>
                    </collateralAgreementWithTariffPlan>
                    <xsl:if test="$qvb/field[@name='QCAP']/@value != 0">
                        <collateralAgreementWithCapitalization>
                            <collateralAgreement>
                                <agreementId>
                                    <xsl:value-of select="$collateralAgreementWithCapitalization/field[@name='TEMPLATEID']/@value"/>
                                </agreementId>
                                <text>
                                    <xsl:value-of select="normalize-space($collateralAgreementWithCapitalization/field[@name='TEXT'])" disable-output-escaping="yes"/>
                                </text>
                            </collateralAgreement>
                        </collateralAgreementWithCapitalization>
                    </xsl:if>
                    <xsl:if test="$qvb/field[@name='QN_RESN']/@value != 0">
                        <collateralAgreementWithMinimumBalance>
                            <collateralAgreement>
                                <agreementId>
                                    <xsl:value-of select="$collateralAgreementWithMinimumBalance/field[@name='TEMPLATEID']/@value"/>
                                </agreementId>
                                <text>
                                    <xsl:value-of select="normalize-space($collateralAgreementWithMinimumBalance/field[@name='TEXT'])" disable-output-escaping="yes"/>
                                </text>
                            </collateralAgreement>
                        </collateralAgreementWithMinimumBalance>
                    </xsl:if>
                    <xsl:if test="count($collateralAgreementWithTariffPlan4Type_61) > 0">
                        <collateralAgreementWithTariffPlan4Type_61>
                            <collateralAgreement>
                                <agreementId>
                                    <xsl:value-of select="$collateralAgreementWithTariffPlan4Type_61/field[@name='TEMPLATEID']/@value"/>
                                </agreementId>
                                <text>
                                    <xsl:value-of select="normalize-space($collateralAgreementWithTariffPlan4Type_61/field[@name='TEXT'])" disable-output-escaping="yes"/>
                                </text>
                            </collateralAgreement>
                        </collateralAgreementWithTariffPlan4Type_61>
                    </xsl:if>
                </collateralAgreements>
			</data>
            </xsl:when>
            <xsl:otherwise>
                <errors>
                    <xsl:if test="count($qvb)=0">
                        <error>
                            <xsl:text>QVB</xsl:text>
                        </error>
                    </xsl:if>
                    <xsl:if test="count($qvkl_val)=0">
                        <error>
                            <xsl:text>QVKL_VAL</xsl:text>
                        </error>
                    </xsl:if>
                    <xsl:if test="count($dcf_tar)=0">
                        <error>
                            <xsl:text>DCF_TAR</xsl:text>
                        </error>
                    </xsl:if>
                    <xsl:if test="count($bch_bux)=0">
                        <error>
                            <xsl:text>BCH_BUX</xsl:text>
                        </error>
                    </xsl:if>
                </errors>
            </xsl:otherwise>
            </xsl:choose>
		</product>
	</xsl:template>

	<xsl:template name="periodBux">
		<xsl:param name="id"/>
        <xsl:variable name="record"
                      select="/package/rollout/transaction/table[@name='BCH_BUX']/replace/record[field[@name='BCH_VKL' and @value=$productId] and field[@name='BCH_PVVKL' and @value=$id]]"/>
		<xsl:variable name="start" select="$record/field[@name='BEG_SROK']/@value"/>
		<xsl:variable name="end" select="$record/field[@name='END_SROK']/@value"/>
		<xsl:value-of select="$start"/>
        <xsl:if test="boolean($start) and boolean($end)">
		    <xsl:text>-</xsl:text>
        </xsl:if>
		<xsl:value-of select="$end"/>
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

	<xsl:template name="parsePeriod">
		<xsl:param name="period"/>
		<xsl:choose>
			<xsl:when test="string-length($period) = 8">
				<xsl:variable name="start" select="substring($period, 4, 2)"/>
				<xsl:variable name="end" select="substring($period, 7, 2)"/>
				<xsl:call-template name="monthsToYYMM">
					<xsl:with-param name="months" select="$start"/>
				</xsl:call-template>
				<xsl:text>-0000U</xsl:text>
				<xsl:call-template name="monthsToYYMM">
					<xsl:with-param name="months" select="$end"/>
				</xsl:call-template>
				<xsl:text>-0000</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="length" select="string-length($period)"/>
				<xsl:choose>
					<xsl:when test="$length &lt; 4">
						<xsl:text>00-00-</xsl:text>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="$period"/>
                            <xsl:with-param name="length" select="4"/>
                        </xsl:call-template>
					</xsl:when>
					<xsl:when test="$length &lt; 6 and $length &gt; 3">
						<xsl:text>00-</xsl:text>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="substring($period, 1, number($length)-3)"/>
                            <xsl:with-param name="length" select="2"/>
                        </xsl:call-template>
						<xsl:text>-</xsl:text>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="substring($period, number($length)-2, 3)"/>
                            <xsl:with-param name="length" select="4"/>
                        </xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="substring($period, 1, number($length)-5)"/>
                            <xsl:with-param name="length" select="2"/>
                        </xsl:call-template>
						<xsl:text>-</xsl:text>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="substring($period, number($length)-4, 2)"/>
                            <xsl:with-param name="length" select="2"/>
                        </xsl:call-template>
						<xsl:text>-</xsl:text>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="substring($period, number($length)-2, 3)"/>
                            <xsl:with-param name="length" select="4"/>
                        </xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="monthsToYYMM">
		<xsl:param name="months"/>
		<xsl:choose>
			<xsl:when test="number($months)&lt;12">
				<xsl:text>00-</xsl:text>
				<xsl:value-of select="$months"/>
			</xsl:when>
			<xsl:otherwise>
                <xsl:call-template name="appendLeadingZeros">
                    <xsl:with-param name="value" select="number($months) div 12"/>
                    <xsl:with-param name="length" select="2"/>
                </xsl:call-template>
				<xsl:text>-</xsl:text>
				<xsl:call-template name="appendLeadingZeros">
                    <xsl:with-param name="value" select="number($months) mod 12"/>
                    <xsl:with-param name="length" select="2"/>
                </xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="appendLeadingZeros">
        <xsl:param name="value"/>
        <xsl:param name="length"/>
        <xsl:choose>
            <xsl:when test="string-length($value) = $length">
                <xsl:value-of select="$value"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="newValue" select="concat('0', $value)"/>
                <xsl:choose>
                    <xsl:when test="string-length($newValue) = $length">
                        <xsl:value-of select="$newValue"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="appendLeadingZeros">
                            <xsl:with-param name="value" select="$newValue"/>
                            <xsl:with-param name="length" select="$length"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

	<xsl:template name="additionalInfo">
		<xsl:param name="fields"/>
        <xsl:param name="id"/>
        <xsl:variable name="filteredFields" select="$fields[field[@name='PVID' and @value = $id]]"/>

		<incomingTransactions>
			<xsl:value-of select="$filteredFields/field[@name='PRIXOD']/@value"/>
		</incomingTransactions>
		<frequencyAdd>
			<xsl:value-of select="$filteredFields/field[@name='PER_ADD']/@value"/>
		</frequencyAdd>
		<debitTransactions>
			<xsl:value-of select="$filteredFields/field[@name='RASXOD']/@value"/>
		</debitTransactions>
		<frequencyPercent>
			<xsl:value-of select="$filteredFields/field[@name='PER_PERCENT']/@value"/>
		</frequencyPercent>
		<percentOrder>
			<xsl:value-of select="$filteredFields/field[@name='ORD_PERCENT']/@value"/>
		</percentOrder>
		<incomeOrder>
			<xsl:value-of select="$filteredFields/field[@name='ORD_DOXOD']/@value"/>
		</incomeOrder>
        <renewals>
            <xsl:value-of select="$filteredFields/field[@name='QPROL']/@value"/>
		</renewals>
	</xsl:template>

    <xsl:template name="findTemplate">
        <xsl:param name="templateIds"/>
        <xsl:param name="contractTemplates"/>
        <xsl:for-each select="$templateIds">
            <xsl:variable name="templateId" select="."/>
            <xsl:if test="boolean($contractTemplates[field[@name='TEMPLATEID']/@value=$templateId])">
                <templateId>
                    <xsl:value-of select="$templateId"/>
                </templateId>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="findCollateralAgreement">
        <xsl:param name="templateIds"/>
        <xsl:param name="contractTemplates"/>
        <xsl:param name="contractTemplates4Type_61"/>
        <xsl:param name="tariffPlan" select="''"/>

        <xsl:variable name="agreementCode">
            <xsl:choose>
                <xsl:when test="$tariffPlan = 1">
                    <xsl:value-of select="$tariff1AgreementCode"/>
                </xsl:when>
                <xsl:when test="$tariffPlan = 2 and count($contractTemplates[field[@name='CODE' and translate(@value, ' ', '')=$tariff2AgreementCode]]) &gt; 0">
                    <xsl:value-of select="$tariff2AgreementCode"/>
                </xsl:when>
                <xsl:when test="$tariffPlan = 3 and count($contractTemplates[field[@name='CODE' and translate(@value, ' ', '')=$tariff3AgreementCode]]) &gt; 0">
                    <xsl:value-of select="$tariff3AgreementCode"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$tariff1AgreementCode"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:choose>
	        <xsl:when test="count($contractTemplates4Type_61) > 0">
		        <tariffPlanAgreementId>
                    <xsl:value-of select="$contractTemplates4Type_61/field[@name='TEMPLATEID']/@value"/>
	            </tariffPlanAgreementId>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="$templateIds">
                    <xsl:variable name="templateId" select="."/>
                    <xsl:if test="boolean($contractTemplates[field[@name='TEMPLATEID']/@value=$templateId and field[@name='CODE' and translate(@value, ' ', '')=$agreementCode]])">
                        <tariffPlanAgreementId>
                            <xsl:value-of select="$templateId"/>
                        </tariffPlanAgreementId>
                    </xsl:if>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="elementWithTariff">
        <xsl:param name="dcf_tar"/>
        <xsl:param name="id"/>
        <xsl:param name="debitOperationsCode"/>
        <xsl:param name="currencyCode"/>
        <xsl:param name="dateBegin"/>
        <xsl:param name="dateEnd"/>
        <xsl:param name="minAdditionalFee"/>
        <xsl:param name="isNullPeriod"/>
        <xsl:param name="period"/>
        <xsl:param name="periodInDays"/>
        <xsl:param name="currencyAlphabeticCode"/>
        <xsl:param name="contractDeposit"/>
        <xsl:param name="contractTemplates"/>
        <xsl:param name="collateralAgreementWithTariffPlan"/>
        <xsl:param name="collateralAgreementWithTariffPlan4Type_61"/>
        <xsl:param name="field_tdog"/>
        <xsl:param name="pension"/>
        <xsl:param name="subTypeName"/>
        <xsl:param name="tariffPlan" select="''"/>
        <xsl:param name="groupCode"/>
        <xsl:param name="group"/>
        <xsl:param name="tariffDependence"/>
        <xsl:param name="creditOperations"/>
        <xsl:param name="debitOperations"/>
        <xsl:param name="interestOperations"/>
        <xsl:param name="groupParams"/>

        <xsl:variable name="dcf_tar_cur"
                      select="$dcf_tar[field[@name='KOD_VKL_QDTSUB']/@value=$id and field[@name='DCF_VAL']/@value=$currencyCode and ((field[@name='DCF_SEG']/@value=$pension and $pension = '1') or (field[@name='DCF_SEG']/@value!='1' and $pension != '1')) and ((not(boolean($tariffPlan)) and field[@name='KOD_VKL_CLNT' and @value!='1' and @value!='2' and @value!='3']) or (boolean($tariffPlan) and field[@name='KOD_VKL_CLNT']/@value=$tariffPlan))]"/>
        <xsl:variable name="selectedGroupParams"
                      select="$groupParams[field[@name='PG_CODGR']/@value=$groupCode]"/>

        <xsl:for-each select="xalan:distinct($dcf_tar_cur/field[@name='SUM_BEG']/@value)">
            <xsl:variable name="sum" select="."/>

            <xsl:for-each select="xalan:distinct($dcf_tar_cur/field[@name='DCF_SEG']/@value)">
                <xsl:variable name="segment" select="."/>

                <xsl:variable name="maxDateInPast">
                    <xsl:call-template name="max">
                        <xsl:with-param name="list"
                                        select="$dcf_tar_cur[field[@name='SUM_BEG' and @value=$sum] and field[@name='DCF_SEG' and @value=$segment] and field[@name='DATE_BEG' and translate(@value, '.', '')&lt;=translate($curDate, '.', '')]]/field[@name='DATE_BEG']/@value"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:for-each
                        select="$dcf_tar_cur[field[@name='SUM_BEG' and @value=$sum] and field[@name='DATE_BEG' and (translate(@value, '.', '')&gt;translate($curDate, '.', '') or translate(@value, '.', '')=translate($maxDateInPast, '.', ''))]]">
                    <xsl:variable name="minBalance"
                                  select="./field[@name='SUM_BEG']/@value"/>
                    <xsl:variable name="maxBalance"
                                  select="./field[@name='SUM_END']/@value"/>
                    <xsl:variable name="interestRate"
                                  select="./field[@name='TAR_VKL']/@value"/>
                    <xsl:variable name="interestDateBegin"
                                  select="./field[@name='DATE_BEG']/@value"/>
                    <xsl:variable name="tariffCodeType"
                                  select="./field[@name='KOD_VKL_CLNT']/@value"/>
                    <xsl:variable name="segmentCode"
                                  select="./field[@name='DCF_SEG']/@value"/>
                    <xsl:variable name="templateIds"
                                  select="$contractDeposit[field[@name='QDTSUB']/@value=$id]/field[@name='CONTRACTTEMPLATE']/@value"/>

                    <element>
                        <id>
                            <xsl:value-of select="$id"/>
                        </id>

                        <dateBegin>
                            <xsl:value-of select="$dateBegin"/>
                        </dateBegin>
                        <dateEnd>
                            <xsl:value-of select="$dateEnd"/>
                        </dateEnd>
                        <minAdditionalFee>
                            <xsl:value-of select="$minAdditionalFee"/>
                        </minAdditionalFee>
                        <xsl:if test="$isNullPeriod = 'false'">
                            <period>
                                <xsl:value-of select="$period"/>
                            </period>
                            <periodInDays>
                                <xsl:value-of select="$periodInDays"/>
                            </periodInDays>
                        </xsl:if>
                        <currencyCode>
                            <xsl:value-of select="$currencyAlphabeticCode"/>
                        </currencyCode>
                        <minBalance>
                            <xsl:value-of select="$minBalance"/>
                        </minBalance>
                        <maxBalance>
                            <xsl:value-of select="$maxBalance"/>
                        </maxBalance>
                        <interestRate>
                            <xsl:value-of select="$interestRate"/>
                        </interestRate>
                        <interestDateBegin>
                            <xsl:value-of select="$interestDateBegin"/>
                        </interestDateBegin>
                        <xsl:call-template name="findTemplate">
                            <xsl:with-param name="templateIds" select="$templateIds"/>
                            <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
                        </xsl:call-template>
                        <xsl:call-template name="findCollateralAgreement">
                            <xsl:with-param name="templateIds"               select="$templateIds"/>
                            <xsl:with-param name="contractTemplates"         select="$collateralAgreementWithTariffPlan"/>
                            <xsl:with-param name="contractTemplates4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
                            <xsl:with-param name="tariffPlan"                select="$tariffPlan"/>
                        </xsl:call-template>
                        <pension>
                            <xsl:call-template name="booleanValue">
                                <xsl:with-param name="value" select="$pension"/>
                            </xsl:call-template>
                        </pension>
                        <percent>
                            <xsl:value-of select="normalize-space($field_tdog[field[@name='PVID']/@value=$id]/field[@name='PERCENT']/@value)"/>
                        </percent>
                        <subTypeName>
                            <xsl:value-of select="$subTypeName"/>
                        </subTypeName>
                        <tariffPlanCode>
                            <xsl:value-of select="$tariffCodeType"/>
                        </tariffPlanCode>
                        <segmentCode>
                            <xsl:value-of select="$segmentCode"/>
                        </segmentCode>
                        <group>
                            <groupCode>
                                <xsl:choose>
                                    <xsl:when test="$groupCode != 0 and $groupCode != ''">
                                        <xsl:value-of select="$groupCode"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text>-22</xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </groupCode>
                            <groupName>
                                <xsl:value-of select="$group[field[@name='GR_CODE']/@value=$groupCode]/field[@name='GR_NAME']/@value"/>
                            </groupName>
                            <xsl:if test="count($selectedGroupParams)&gt;0">
                                <groupParams>
                                    <paramDateBegin>
                                        <xsl:value-of select="$selectedGroupParams/field[@name='PG_BDATE']/@value"/>
                                    </paramDateBegin>
                                    <charitableContribution>
                                        <xsl:call-template name="booleanValue">
                                            <xsl:with-param name="value" select="$selectedGroupParams/field[@name='PG_FCONTR']/@value"/>
                                        </xsl:call-template>
                                    </charitableContribution>
                                    <pensionRate>
                                        <xsl:call-template name="booleanValue">
                                            <xsl:with-param name="value" select="$selectedGroupParams/field[@name='PG_PENS']/@value"/>
                                        </xsl:call-template>
                                    </pensionRate>
                                    <pensionSumLimit>
                                        <xsl:call-template name="booleanValue">
                                            <xsl:with-param name="value" select="$selectedGroupParams/field[@name='PG_FMAXP']/@value"/>
                                        </xsl:call-template>
                                    </pensionSumLimit>
                                    <percentCondition>
                                        <xsl:value-of select="$selectedGroupParams/field[@name='PG_PRONPR']/@value"/>
                                    </percentCondition>
                                    <sumLimit>
                                        <xsl:value-of select="$selectedGroupParams/field[@name='PG_MAXV']/@value"/>
                                    </sumLimit>
                                    <sumLimitCondition>
                                        <xsl:value-of select="$selectedGroupParams/field[@name='PG_CSTAV']/@value"/>
                                    </sumLimitCondition>
                                    <socialType>
                                        <xsl:call-template name="booleanValue">
                                            <xsl:with-param name="value" select="$selectedGroupParams/field[@name='PG_TYPEGR']/@value"/>
                                        </xsl:call-template>
                                    </socialType>
                                    <promoCodeSupported>
                                        <xsl:call-template name="booleanValue">
                                            <xsl:with-param name="value" select="$selectedGroupParams/field[@name='PG_PROMO']/@value"/>
                                        </xsl:call-template>
                                    </promoCodeSupported>
                                </groupParams>
                            </xsl:if>
                        </group>
                        <xsl:if test="count($tariffDependence)&gt;0">
                            <tariffDependence>
                                <xsl:for-each select="$tariffDependence">
                                    <tariff>
                                        <tariffDateBegin>
                                            <xsl:value-of select="./field[@name='TP_DBEG']/@value"/>
                                        </tariffDateBegin>
                                        <tariffDateEnd>
                                            <xsl:value-of select="./field[@name='TP_DEND']/@value"/>
                                        </tariffDateEnd>
                                        <tariffCode>
                                            <xsl:value-of select="./field[@name='TP_CODE']/@value"/>
                                        </tariffCode>
                                    </tariff>
                                </xsl:for-each>
                            </tariffDependence>
                        </xsl:if>
                        <creditOperations>
                            <xsl:value-of select="$creditOperations"/>
                        </creditOperations>
                        <debitOperations>
                            <xsl:value-of select="$debitOperations"/>
                        </debitOperations>
                        <debitOperationsCode>
                            <xsl:value-of select="$debitOperationsCode"/>
                        </debitOperationsCode>
                        <interestOperations>
                            <xsl:value-of select="$interestOperations"/>
                        </interestOperations>
                        <xsl:call-template name="additionalInfo">
                            <xsl:with-param name="fields" select="$field_tdog"/>
                            <xsl:with-param name="id" select="$id"/>
                        </xsl:call-template>
                    </element>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="element">
        <xsl:param name="dcf_tar"/>
        <xsl:param name="id"/>
        <xsl:param name="debitOperationsCode"/>
        <xsl:param name="currencyCode"/>
        <xsl:param name="dateBegin"/>
        <xsl:param name="dateEnd"/>
        <xsl:param name="minAdditionalFee"/>
        <xsl:param name="isNullPeriod"/>
        <xsl:param name="period"/>
        <xsl:param name="periodInDays"/>
        <xsl:param name="currencyAlphabeticCode"/>
        <xsl:param name="contractDeposit"/>
        <xsl:param name="contractTemplates"/>
        <xsl:param name="collateralAgreementWithTariffPlan"/>
        <xsl:param name="collateralAgreementWithTariffPlan4Type_61"/>
        <xsl:param name="field_tdog"/>
        <xsl:param name="pension"/>
        <xsl:param name="subTypeName"/>
        <xsl:param name="groupCode"/>
        <xsl:param name="group"/>
        <xsl:param name="tariffDependence"/>
        <xsl:param name="creditOperations"/>
        <xsl:param name="debitOperations"/>
        <xsl:param name="interestOperations"/>
        <xsl:param name="groupParams"/>

        <!--Подвиды с тарифным планом 1, 2 и 3 должны проверяться независимо друг от друга. Все иные - вместе.-->
        <!--C тарифным планом 1-->
        <xsl:call-template name="elementWithTariff">
            <xsl:with-param name="dcf_tar" select="$dcf_tar"/>
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="debitOperationsCode" select="$debitOperationsCode"/>
            <xsl:with-param name="currencyCode" select="$currencyCode"/>
            <xsl:with-param name="dateBegin" select="$dateBegin"/>
            <xsl:with-param name="dateEnd" select="$dateEnd"/>
            <xsl:with-param name="minAdditionalFee" select="$minAdditionalFee"/>
            <xsl:with-param name="isNullPeriod" select="$isNullPeriod"/>
            <xsl:with-param name="period" select="$period"/>
            <xsl:with-param name="periodInDays" select="$periodInDays"/>
            <xsl:with-param name="currencyAlphabeticCode" select="$currencyAlphabeticCode"/>
            <xsl:with-param name="contractDeposit" select="$contractDeposit"/>
            <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan"         select="$collateralAgreementWithTariffPlan"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
            <xsl:with-param name="field_tdog" select="$field_tdog"/>
            <xsl:with-param name="pension" select="$pension"/>
            <xsl:with-param name="subTypeName" select="$subTypeName"/>
            <xsl:with-param name="tariffPlan" select="1"/>
            <xsl:with-param name="groupCode" select="$groupCode"/>
            <xsl:with-param name="group" select="$group"/>
            <xsl:with-param name="tariffDependence" select="$tariffDependence"/>
            <xsl:with-param name="creditOperations" select="$creditOperations"/>
            <xsl:with-param name="debitOperations" select="$debitOperations"/>
            <xsl:with-param name="interestOperations" select="$interestOperations"/>
            <xsl:with-param name="groupParams" select="$groupParams"/>
        </xsl:call-template>

        <!--C тарифным планом 2-->
        <xsl:call-template name="elementWithTariff">
            <xsl:with-param name="dcf_tar" select="$dcf_tar"/>
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="debitOperationsCode" select="$debitOperationsCode"/>
            <xsl:with-param name="currencyCode" select="$currencyCode"/>
            <xsl:with-param name="dateBegin" select="$dateBegin"/>
            <xsl:with-param name="dateEnd" select="$dateEnd"/>
            <xsl:with-param name="minAdditionalFee" select="$minAdditionalFee"/>
            <xsl:with-param name="isNullPeriod" select="$isNullPeriod"/>
            <xsl:with-param name="period" select="$period"/>
            <xsl:with-param name="periodInDays" select="$periodInDays"/>
            <xsl:with-param name="currencyAlphabeticCode" select="$currencyAlphabeticCode"/>
            <xsl:with-param name="contractDeposit" select="$contractDeposit"/>
            <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan"         select="$collateralAgreementWithTariffPlan"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
            <xsl:with-param name="field_tdog" select="$field_tdog"/>
            <xsl:with-param name="pension" select="$pension"/>
            <xsl:with-param name="subTypeName" select="$subTypeName"/>
            <xsl:with-param name="tariffPlan" select="2"/>
            <xsl:with-param name="groupCode" select="$groupCode"/>
            <xsl:with-param name="group" select="$group"/>
            <xsl:with-param name="tariffDependence" select="$tariffDependence"/>
            <xsl:with-param name="creditOperations" select="$creditOperations"/>
            <xsl:with-param name="debitOperations" select="$debitOperations"/>
            <xsl:with-param name="interestOperations" select="$interestOperations"/>
            <xsl:with-param name="groupParams" select="$groupParams"/>
        </xsl:call-template>

        <!--C тарифным планом 3-->
        <xsl:call-template name="elementWithTariff">
            <xsl:with-param name="dcf_tar" select="$dcf_tar"/>
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="debitOperationsCode" select="$debitOperationsCode"/>
            <xsl:with-param name="currencyCode" select="$currencyCode"/>
            <xsl:with-param name="dateBegin" select="$dateBegin"/>
            <xsl:with-param name="dateEnd" select="$dateEnd"/>
            <xsl:with-param name="minAdditionalFee" select="$minAdditionalFee"/>
            <xsl:with-param name="isNullPeriod" select="$isNullPeriod"/>
            <xsl:with-param name="period" select="$period"/>
            <xsl:with-param name="periodInDays" select="$periodInDays"/>
            <xsl:with-param name="currencyAlphabeticCode" select="$currencyAlphabeticCode"/>
            <xsl:with-param name="contractDeposit" select="$contractDeposit"/>
            <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan"         select="$collateralAgreementWithTariffPlan"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
            <xsl:with-param name="field_tdog" select="$field_tdog"/>
            <xsl:with-param name="pension" select="$pension"/>
            <xsl:with-param name="subTypeName" select="$subTypeName"/>
            <xsl:with-param name="tariffPlan" select="3"/>
            <xsl:with-param name="groupCode" select="$groupCode"/>
            <xsl:with-param name="group" select="$group"/>
            <xsl:with-param name="tariffDependence" select="$tariffDependence"/>
            <xsl:with-param name="creditOperations" select="$creditOperations"/>
            <xsl:with-param name="debitOperations" select="$debitOperations"/>
            <xsl:with-param name="interestOperations" select="$interestOperations"/>
            <xsl:with-param name="groupParams" select="$groupParams"/>
        </xsl:call-template>

        <!--Все остальные тарифные планы-->
        <xsl:call-template name="elementWithTariff">
            <xsl:with-param name="dcf_tar" select="$dcf_tar"/>
            <xsl:with-param name="id" select="$id"/>
            <xsl:with-param name="debitOperationsCode" select="$debitOperationsCode"/>
            <xsl:with-param name="currencyCode" select="$currencyCode"/>
            <xsl:with-param name="dateBegin" select="$dateBegin"/>
            <xsl:with-param name="dateEnd" select="$dateEnd"/>
            <xsl:with-param name="minAdditionalFee" select="$minAdditionalFee"/>
            <xsl:with-param name="isNullPeriod" select="$isNullPeriod"/>
            <xsl:with-param name="period" select="$period"/>
            <xsl:with-param name="periodInDays" select="$periodInDays"/>
            <xsl:with-param name="currencyAlphabeticCode" select="$currencyAlphabeticCode"/>
            <xsl:with-param name="contractDeposit" select="$contractDeposit"/>
            <xsl:with-param name="contractTemplates" select="$contractTemplates"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan"         select="$collateralAgreementWithTariffPlan"/>
            <xsl:with-param name="collateralAgreementWithTariffPlan4Type_61" select="$collateralAgreementWithTariffPlan4Type_61"/>
            <xsl:with-param name="field_tdog" select="$field_tdog"/>
            <xsl:with-param name="pension" select="$pension"/>
            <xsl:with-param name="subTypeName" select="$subTypeName"/>
            <xsl:with-param name="groupCode" select="$groupCode"/>
            <xsl:with-param name="group" select="$group"/>
            <xsl:with-param name="tariffDependence" select="$tariffDependence"/>
            <xsl:with-param name="creditOperations" select="$creditOperations"/>
            <xsl:with-param name="debitOperations" select="$debitOperations"/>
            <xsl:with-param name="interestOperations" select="$interestOperations"/>
            <xsl:with-param name="groupParams" select="$groupParams"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="booleanValue">
        <xsl:param name="value"/>

        <xsl:choose>
            <xsl:when test="$value = 1">
                <xsl:text>true</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>false</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>