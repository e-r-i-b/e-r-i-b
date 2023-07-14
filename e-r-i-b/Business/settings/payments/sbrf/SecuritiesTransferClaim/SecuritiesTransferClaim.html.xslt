<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>
	<xsl:param name="personAvailable" select="true()"/>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

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
        <!--Список открытых, доступных для операций счетов депо клиента-->
        <xsl:variable name="openDepoAccounts" select="document('open-allowed-depo-accounts-position.xml')/entity-list"/>
        <!--Список открытых, недоступных для операций счетов депо клиента-->
        <xsl:variable name="openNotAllowedDepoAccounts" select="document('open-not-allowed-depo-accounts.xml')/entity-list"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Общие параметры операции</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">operationType</xsl:with-param>
            <xsl:with-param name="description">Выберите из выпадающего списка, какую операцию с ценными бумагами Вы хотите совершить. </xsl:with-param>
            <xsl:with-param name="rowName">Тип операции</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="type" select="operationType"/>
                <select id="operationType" name="operationType" onchange="changeOperationType(null);setOperationReason();asteriskAdditionalInfo();">
                    <option value="TRANFER">IC-231 Перевод</option>
                    <option value="RECEPTION"><xsl:if test="$type='RECEPTION'"><xsl:attribute name="selected"/></xsl:if>
                        IC-240 Приём перевода</option>
                    <option value="INTERNAL_TRANFER"><xsl:if test="$type='INTERNAL_TRANFER'"><xsl:attribute name="selected"/></xsl:if>
                        IC-220 Перевод между разделами счета депо</option>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">depositor</xsl:with-param>
			<xsl:with-param name="rowName">Депонент:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="depositor" name="depositor" value="{depositor}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">operationInitiator</xsl:with-param>
			<xsl:with-param name="rowName">Инициатор операции:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="operationInitiator" name="operationInitiator" value="{operationInitiator}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>        

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">operationSubType</xsl:with-param>
            <xsl:with-param name="description">Выберите из выпадающего списка содержание операции, уточняющее ее тип.</xsl:with-param>
            <xsl:with-param name="rowName">Содержание операции:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <select id="operationSubType" name="operationSubType" onchange="setOperationReason();asteriskAdditionalInfo();changeCorrDepositary();">

                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">operationDesc</xsl:with-param>
            <xsl:with-param name="description">Введите необходимую дополнительную информацию. Например, при совершении перевода на счет в другой депозитарий или на счет в реестре Вы можете указать наименование последнего места хранения ценных бумаг. </xsl:with-param>
            <xsl:with-param name="rowName">Комментарий к операции:</xsl:with-param>
            <xsl:with-param name="required" select="false"/>
            <xsl:with-param name="rowValue">
                <textarea id="operationDesc" name="operationDesc" cols="45" rows="4"><xsl:value-of select="operationDesc"/></textarea>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет депо:</xsl:with-param>
            <xsl:with-param name="description">Выберите из выпадающего списка номер счета депо, по которому Вы хотите совершить операцию.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="num" select="depoAccount"/>
                <input type="hidden" name="depoExternalId" value="{depoExternalId}"/>
                <select id="depoAccount" name="depoAccount" onchange="setDepoAccountInfo();setDepoAccountDivisions();" style="width:400px">
                    <xsl:if test="count($openDepoAccounts/*) = 0">
                        <option value="">Нет доступных счетов депо</option>
                    </xsl:if>
                    <xsl:for-each select="$openDepoAccounts/*">
                        <option>
                            <xsl:attribute name="value">
                                <xsl:value-of select="@key"/>
                            </xsl:attribute>
                            <xsl:if test="$num=./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:variable name="depoName"><xsl:value-of select="./field[@name='depoAccountName']"/></xsl:variable>
                            <strong>
                                <xsl:if test="string-length($depoName) != 0">
                                    <xsl:value-of select="./field[@name='depoAccountName']"/>
                                    (<xsl:value-of select="./field[@name='accountNumber']"/>)
                                </xsl:if>
                                <xsl:if test="string-length($depoName) = 0">
                                    <xsl:value-of select="./field[@name='accountNumber']"/>
                                </xsl:if>
                            </strong>
                        </option>
                    </xsl:for-each>
                    <xsl:for-each select="$openDepoAccounts/*">
                        <xsl:variable name="acc">
                            <xsl:value-of select="./field[@name='accountNumber']"/>
                        </xsl:variable>
                        <input type="hidden" name="agrNum{$acc}">
                            <xsl:attribute name="value">
                                <xsl:value-of select="./field[@name='agreementNumber']"/>
                            </xsl:attribute>
                        </input>
                    </xsl:for-each>
                </select>
            </xsl:with-param>            
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">managerFIO</xsl:with-param>
			<xsl:with-param name="rowName">ФИО распорядителя:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="managerFIO" name="managerFIO" value="{managerFIO}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">divisionNumberRow</xsl:with-param>
		    <xsl:with-param name="description">Выберите из выпадающего списка интересующий Вас раздел счета депо.</xsl:with-param>
			<xsl:with-param name="rowName">Тип и номер раздела:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="divisionType" value="{divisionType}"/>
			    <select id="divisionNumber" name="divisionNumber" onchange="changeDepoAccountDivisionType();">
				</select>				
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityName</xsl:with-param>
		    <xsl:with-param name="description">Выберите из справочника наименование нужной ценной бумаги.
		     Чтобы воспользоваться справочником, нажмите на ссылку Выбрать из справочника.
		     В открывшемся окне установите флажок напротив нужной ценной бумаги и нажмите на кнопку Выбрать.
		     Поля «Регистрационный номер» и «Код валюты» заполнятся автоматически.</xsl:with-param>
			<xsl:with-param name="rowName">Наименование ценных бумаг:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input type="hidden" name="securityNameOldValue" value="{securityName}"/>
				<input id="securityName" name="securityName" value="{securityName}" type="text" size="46" readonly="true"/>&nbsp;&nbsp;
				<span onclick="javascript:openSecuritiesDictionary();">
                    <span class="blueGrayLinkDotted">выбрать из справочника</span>
                </span>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">registrationNumber</xsl:with-param>
			<xsl:with-param name="rowName">Регистрационный номер:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="text" name="registrationNumber" value="{registrationNumber}" size="46" readonly="true"/>
				<input type="hidden" id="insideCode" name="insideCode" value="{insideCode}" />
			</xsl:with-param>
		</xsl:call-template>

        <!--TODO В пилотном проекте ЕРИБ используется только значение "Открытый" -->
		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">storageMethod</xsl:with-param>
			<xsl:with-param name="rowName">Способ хранения:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:variable name="method" select="storageMethod"/>
                <select id="storageMethod" name="storageMethod" disabled="true">
                    <option value="open">Открытый</option>
                    <option value="closed"><xsl:if test="$method='closed'"><xsl:attribute name="selected"/></xsl:if>
                        Закрытый</option>
                    <option value="markByNominal"><xsl:if test="$method='markByNominal'"><xsl:attribute name="selected"/></xsl:if>
                        Маркированный по номиналу</option>
                    <option value="markByCoupon"><xsl:if test="$method='markByCoupon'"><xsl:attribute name="selected"/></xsl:if>
                        Маркированный по купону</option>
                    <option value="markByNominalAndCoupon"><xsl:if test="$method='markByNominalAndCoupon'"><xsl:attribute name="selected"/></xsl:if>
                        Маркированный по номиналу и купону</option>
                </select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityCount</xsl:with-param>
		    <xsl:with-param name="description">Укажите, сколько ценных бумаг Вы хотите перевести или принять.</xsl:with-param>
			<xsl:with-param name="rowName">Количество ценных бумаг:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="securityCount" name="securityCount" value="{securityCount}" type="text" size="15" maxlength="10" onKeyUp="changeSecurityAmount()" onchange="changeSecurityAmount()"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityAmount</xsl:with-param>
			<xsl:with-param name="rowName">Суммарная номинальная стоимость ценных бумаг:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="nominalAmount" value="{nominalAmount}"/>
				<input id="securityAmount" name="securityAmount" value="{securityAmount}" readonly="true" type="text" size="46" class="moneyField"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">currencyCode</xsl:with-param>
			<xsl:with-param name="rowName">ISO-код валюты:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="currencyCode" name="currencyCode" value="{currencyCode}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">operationReason</xsl:with-param>
		    <xsl:with-param name="description">Введите основание для изменения прав или формы удостоверения прав собственности на ценные бумаги.
		    <cut/>Заполнение поля «Основание» зависит от того, что  Вы указали в поле «Содержание операции» и заполняется с учетом следующих особенностей:<br/>
		    1)	Если Вы переводите ценные бумаги внутри депозитария, то введите дату и номер Вашего депозитарного договора с банком.<br/>
		    2)	Если Вы переводите ценные бумаги или принимаете перевод с изменением права собственности, то укажите дату и номер документа, который является основанием для изменения права собственности. Для договора купли-продажи дополнительно укажите сумму сделки.<br/>
		    3)	Если Вы принимаете перевод ценных бумаг со счета в реестре, то укажите номера и даты документов, которые являются основанием для перерегистрации ценных бумаг в реестре (документы, которые Вы указали в передаточном распоряжении у регистратора).<br/>
		    4)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в другом депозитарии, то дополнительно укажите номер и дату междепозитарного договора другого депозитария с депозитарием (местом хранения), через который осуществляется перевод.<br/>
		    Кроме информации о документах Вы можете указать дополнительные данные, которые считаете необходимыми.
		    </xsl:with-param>
            <xsl:with-param name="rowName">Основание:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <textarea id="operationReason" name="operationReason" cols="45" rows="4"><xsl:value-of select="operationReason"/></textarea>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">countOfSheet</xsl:with-param>
		    <xsl:with-param name="description">Введите количество листов в приложении.</xsl:with-param>
			<xsl:with-param name="rowName">Количество приложений, листов:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="countOfSheet" name="countOfSheet" value="0" readonly="true" type="text" size="2"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Реквизиты противоположной стороны</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepositary</xsl:with-param>
		    <xsl:with-param name="description">Наименование депозитария или регистратора, через который осуществляется прием или перевод ценных бумаг.
		    <cut/>Укажите наименование депозитария или регистратора:<br/>
            1)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета внутри депозитария, то данное поле будет автоматически заполнено значением «Депозитарий ОАО  «Сбербанк России».<br/>
            2)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в реестре, то введите наименование регистратора ценных бумаг.<br/>
            3)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в другом депозитарии, то введите наименование депозитария или регистратора, через который осуществляется перевод. 
            </xsl:with-param>
			<xsl:with-param name="rowName">Депозитарий/Регистратор:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="corrDepositaryOldValue" value="{corrDepositary}"/>
				<input id="corrDepositary" name="corrDepositary" value="{corrDepositary}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepoAccount</xsl:with-param>
		    <xsl:with-param name="description">Введите номер счета, на который Вы переводите или с которого принимаете ценные бумаги.
		    <cut/>Введите номер счета, на который Вы переводите или с которого принимаете ценные бумаги.<br/>
            1)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета внутри депозитария, то введите номер счета депо и номер раздела счета депо в депозитарии ОАО «Сбербанк России», на который или с которого переводятся ценные бумаги.
                <startRedText/>
                Номер счета состоит из двенадцати цифр, номер раздела -  из шести цифр и символов. Номера указываются без пробелов. Номер раздела указывается после номера счета через запятую.
                <endRedText/><br/>
            2)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в реестре, то введите номер счета в реестре, на который или с которого переводятся ценные бумаги.<br/>
            3)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в другом депозитарии, то введите номер счета и номер раздела другого депозитария у регистратора или депозитария-корреспондента и  идентификатор другого депозитария (при наличии).
            </xsl:with-param>
			<xsl:with-param name="rowName">Номер счета:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="corrDepoAccount" name="corrDepoAccount" value="{corrDepoAccount}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepoAccountOwner</xsl:with-param>
		    <xsl:with-param name="description">Введите полностью наименование или ФИО владельца счета депо, на который  Вы хотите перевести бумаги или с которого хотите их принять. Например, Андреев Леонид Леонидович.
		    <cut/>В зависимости от содержания операции необходимо указать владельца счета депо:<br/>
            1)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета внутри депозитария, то введите наименование (для юр. лиц) или ФИО владельца счета депо.<br/>
            2)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в реестре, то введите наименование или ФИО владельца счета в реестре. Также Вы можете указать реквизиты свидетельства о регистрации (дата, номер, кем выдано) или паспортные данные (серия, номер, дата выдачи, кем выдан) владельца счета в реестре (при переводе на счет в реестре).<br/>
            3)	Если Вы переводите ценные бумаги или принимаете перевод на счет или со счета в другом депозитарии, то введите наименование другого депозитария и контактный телефон депозитария.
            </xsl:with-param>
			<xsl:with-param name="rowName">Владелец счета:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="corrDepoAccountOwner" name="corrDepoAccountOwner" value="{corrDepoAccountOwner}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">additionalInfo</xsl:with-param>
		    <xsl:with-param name="description">При приеме ценных бумаг со счета в реестре введите дату или срок выполнения операции в реестре. При необходимости  укажите другую дополнительную информацию.</xsl:with-param>
			<xsl:with-param name="rowName">Дополнительные реквизиты:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="additionalInfo" name="additionalInfo" value="{additionalInfo}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

        <!--TODO В пилотном проекте ЕРИБ поле на форме отображается, но не заполняется и не может быть отредактировано клиентом.-->
		<xsl:call-template name="standartRow">
            <xsl:with-param name="id">deliveryType</xsl:with-param>
            <xsl:with-param name="rowName">Способ доставки сертификатов:</xsl:with-param>
            <xsl:with-param name="required" select="false"/>
            <xsl:with-param name="rowValue">
                <xsl:variable name="type" select="deliveryType"/>
                <select id="deliveryType" name="deliveryType" disabled="true">
                    <option value=""></option>
                    <option value="FELD"><xsl:if test="$type='FELD'"><xsl:attribute name="selected"/></xsl:if>
                        Фельдсвязью</option>
                    <option value="INKASS"><xsl:if test="$type='INKASS'"><xsl:attribute name="selected"/></xsl:if>
                        Службами инкассации одного из 2-х депозитариев операции</option>
                    <option value="NOT_DELIVER"><xsl:if test="$type='NOT_DELIVER'"><xsl:attribute name="selected"/></xsl:if>
                        Без доставки</option>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <script type="text/javascript">
            var depoAccounts = new Array();

            function initDepoAccounts()
            {
                <xsl:for-each select="$openDepoAccounts/*">
                    var depoAccount = new Object();

                    depoAccount.owner = '<xsl:value-of select="./field[@name='owner']"/>';
                    depoAccount.externalId = '<xsl:value-of select="./field[@name='externalId']"/>';
                    depoAccount.agreementDate = '<xsl:value-of select="./field[@name='agreementDate']"/>';
                    <xsl:variable name="accountNumber" select ="@key"/>
                    var depoAccountsDivisions = new Array();
                    <xsl:for-each select="$openDepoAccounts/entity[@key=$accountNumber]/entity-list/*">
                        var division = new Object();
                        division.number = '<xsl:value-of select="@key"/>';
                        division.type = '<xsl:value-of select="./field[@name='divisionType']"/>';                        
                        depoAccountsDivisions.push(division);                        
                    </xsl:for-each>                    
                    depoAccount.divisions = depoAccountsDivisions;

                    depoAccounts['<xsl:value-of select="@key"/>'] = depoAccount;
                </xsl:for-each>
            }           

            function setDepoAccountInfo()
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var operationSubType = getElementValue('operationSubType');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(depoAccount == null)
                    return;
                depoAccount.agreementNumber = getElementValue('agrNum'+depoAccountSelect);
                setElement("depositor", depoAccount.owner);
                setElement("operationInitiator", "Депонент");
                setElement("managerFIO", depoAccount.owner);
                setElement("depoExternalId", depoAccount.externalId);
                setOperationReason('true');
            }

            function setOperationReason(isNew)
            {
                //если уже заполнено (вернулись в редактирование со страницы подтверждения), то значение не трогаем
                if(getElementValue("operationReason") != ""  &amp;&amp; isNew == 'true')
                    return;

                var depoAccountSelect = getElementValue('depoAccount');
                if (depoAccountSelect == "")
                    return;

                var operationSubType = getElementValue('operationSubType');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(operationSubType != 'LIST_RECEPTION')
                    setElement("operationReason", "Договор " + depoAccount.agreementNumber + " от " + depoAccount.agreementDate);
                else
                    setElement("operationReason", "");
            }

            function setDepoAccountDivisions(number)
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var depoAccount = depoAccounts[depoAccountSelect];
                var depoAccountDivision = getElement('divisionNumber');
                if(depoAccount == null)
                    return;
                depoAccountDivision.length = 0;
                for (var i=0; i &lt; depoAccount.divisions.length; i++)
                {                                      
                    var isSelected = false;
                    if (((number== null || number=='') &amp;&amp; i==0) || (depoAccount.divisions[i].number == number))
                    {
                        isSelected = true;
                        setElement("divisionType", depoAccount.divisions[i].type);
                    }
                    depoAccountDivision.options[i] = new Option(depoAccount.divisions[i].type + " " + depoAccount.divisions[i].number,
                                                                depoAccount.divisions[i].number,
                                                                false, isSelected);
                }
            }

            function changeDepoAccountDivisionType()
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(depoAccount == null)
                    return;
                var depoAccountDivisionNumber = getElementValue('divisionNumber');
                for (var i=0; i &lt; depoAccount.divisions.length; i++)
                {
                    if (depoAccount.divisions[i].number == depoAccountDivisionNumber)
                    {
                        setElement("divisionType", depoAccount.divisions[i].type);
                    }
                }
            }

            function changeOperationType(oldOperSubType)
            {
                var operationType = getElementValue('operationType');
                var operationSubType = getElement('operationSubType');
                operationSubType.length = 0;
                if(operationType == 'TRANFER')
                {
                    operationSubType.options[0] = new Option("перевод на счет депо внутри депозитария","INTERNAL_TRANSFER");
                    operationSubType.options[1] = new Option("перевод на счет в реестре","LIST_TRANSFER");
                    operationSubType.options[2] = new Option("перевод на счет в другой депозитарий","EXTERNAL_TRANSFER");
                }
                else if(operationType == 'RECEPTION')
                {
                    operationSubType.options[0] = new Option("прием перевода со счета депо внутри депозитария","INTERNAL_RECEPTION");
                    operationSubType.options[1] = new Option("прием перевода со счета в реестре","LIST_RECEPTION");
                    operationSubType.options[2] = new Option("прием перевода со счета в другом депозитарии","EXTERNAL_RECEPTION");
                }
                else if(operationType == 'INTERNAL_TRANFER')
                {
                    operationSubType.options[0] = new Option("перевод между разделами счета депо в депозитарии","INTERNAL_ACCOUNT_TRANSFER");
                }
                if(oldOperSubType != null &amp;&amp; oldOperSubType != "")
                   setElement("operationSubType", oldOperSubType);
                changeCorrDepositary();
            }

            function openSecuritiesDictionary()
            {
                var operationType = getElementValue('operationType');
                var account = getElementValue('depoAccount');
                if (account == null || account == "")
                {
                    addError('У Вас нет счетов депо доступных для совершения операций.');
                    return;
                }

                var type    = getElementValue('divisionType');
                var number  = getElementValue('divisionNumber');
                if(operationType == 'RECEPTION')
                {
                    window.setSecuritiesInfo = setSecuritiesInfo;
                    window.open(document.webRoot+'/private/dictionary/securities.do?', 'Securities',
                    "resizable=1,menubar=0,toolbar=0,scrollbars=1");
                }
                else if(operationType == 'INTERNAL_TRANFER' || operationType == 'TRANFER')
                {
                    window.setSecuritiesInfo = setSecuritiesInfo;
                    window.open(document.webRoot+'/private/dictionary/clientSecurities.do?depoAccount='+
                    account + '&amp;divisionType=' + type + '&amp;divisionNumber=' + number,
                    'Securities',
                    "resizable=1,menubar=0,toolbar=0,scrollbars=1");
                }
            }

            function setSecuritiesInfo(securityInfo)
            {
                setElement("securityName",securityInfo["name"]);
                setElement("insideCode",securityInfo["inside-code"]);
                setElement("registrationNumber",securityInfo["registration-number"]);
                setElement("nominalAmount",securityInfo["nominal-amount"]);
                setElement("currencyCode",securityInfo["nominal-currency"]);
                changeSecurityAmount();
            }


            function changeSecurityAmount()
            {                
                var securityNominal = Number(getElementValue('nominalAmount'));
                var securityCount = Number(getElementValue('securityCount'), 10);
                if(securityNominal == 'NaN')
                    securityNominal = 0;
                if(securityCount == 'NaN')
                    securityCount = 0;
                var sumSecurityAmount =
                    Number(Math.round(parseFloat(securityNominal*securityCount).toPrecision(15)*100)/100);
                $("#securityAmount").setMoneyValue(sumSecurityAmount);
            }

            function changeCorrDepositary()
            {
                var operationSubType = getElementValue('operationSubType');
                var corrDepositary = getElementValue('corrDepositary');
                if(operationSubType == 'INTERNAL_TRANSFER' || operationSubType == 'INTERNAL_RECEPTION' || operationSubType == 'INTERNAL_ACCOUNT_TRANSFER')
                {
                    setElement("corrDepositaryOldValue", '');
                    setElement("corrDepositary", "Депозитарий ОАО \"Сбербанк России\"");
                    getElement("corrDepositary").readOnly = true;
                }
                else
                {
                    var corrDepositaryOldValue = getElementValue('corrDepositaryOldValue');
                    setElement("corrDepositaryOldValue", '');
                    setElement("corrDepositary", corrDepositaryOldValue);
                    getElement("corrDepositary").readOnly = false;
                }
            }

            function asteriskAdditionalInfo()
            {
                var asterisk = document.getElementById("asterisk_additionalInfo");
                var operationSubType = getElementValue('operationSubType');
                if(operationSubType == 'LIST_RECEPTION')
                    asterisk.innerHTML = "*";
                else
                    asterisk.innerHTML = " ";
            }

            $(document).ready(function(){
                initDepoAccounts();
                setDepoAccountDivisions('<xsl:value-of select="divisionNumber"/>');
                changeDepoAccountDivisionType();
                changeOperationType('<xsl:value-of select="operationSubType"/>');
                setDepoAccountInfo();
                changeSecurityAmount();
                asteriskAdditionalInfo();
            });

            <![CDATA[ var infoMessage = "Перечень точек депозитарного обслуживания Вы можете посмотреть на сайте банка. Для этого перейдите по ссылке <a href='http://sberbank.ru/ru/person/investments/depository/' class='paperEnterLink' target='_blank'>Депозитарное обслуживание</a>.";]]>
             addMessage(infoMessage);

            <xsl:if test="count($openNotAllowedDepoAccounts/entity) > 0">
                var message  = "Обратите внимание, что в списке счетов депо отсутствуют счета,  операции по которым заблокированы из-за наличия просроченной задолженности перед Депозитарием.<br/>"+
                "Для получения возможности совершать операции по таким счетам депо Вам необходимо оплатить счета  за  оказание депозитарных услуг, список которых можно просмотреть, выбрав пункт меню «задолженность». Совершение операций  будет возможно после  получения Депозитарием  денежных средств, направленных на погашение задолженности.";
                addMessage(message);
            </xsl:if>

        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Общие параметры операции</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип операции:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="operationType2text">
                    <xsl:with-param name="code">
                        <xsl:value-of  select="operationType"/>
                    </xsl:with-param>
                </xsl:call-template>                
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Депонент:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="depositor"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Инициатор операции:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="operationInitiator"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Содержание операции:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="operationSubType2text">
                    <xsl:with-param name="code">
                        <xsl:value-of  select="operationSubType"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Комментарий к операции:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="operationDesc"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет депо:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="depoAccount"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ФИО распорядителя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="managerFIO"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Тип и номер раздела:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="concat(divisionType,' ',divisionNumber)"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Наименование ценных бумаг:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="securityName"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Регистрационный номер:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="registrationNumber"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Способ хранения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="storageMethod2text">
                    <xsl:with-param name="code">
                        <xsl:value-of  select="storageMethod"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Количество ценных бумаг:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="securityCount"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Суммарная номинальная стоимость ценных бумаг:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="format-number(securityCount*nominalAmount, '### ##0,00', 'sbrf')!='NaN'">
                        <span class="summ">
                            <xsl:value-of select="format-number((round(securityCount*nominalAmount*100) div 100), '### ##0,00', 'sbrf')"/>&nbsp;
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                         &nbsp;
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ISO-код валюты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="currencyCode"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Основание:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="operationReason"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Количество приложений, листов:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="countOfSheet"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Реквизиты противоположной стороны</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Депозитарий/Регистратор:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepositary"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер счета:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepoAccount"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Владелец счета:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepoAccountOwner"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дополнительные реквизиты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="additionalInfo"/>
            </xsl:with-param>
        </xsl:call-template>       

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Способ доставки сертификатов:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="deliveryType"/>
            </xsl:with-param>
        </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус
                    <xsl:choose>
                        <xsl:when test="$isTemplate != 'true'"> поручения:</xsl:when>
                        <xsl:otherwise> шаблона:</xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="state">
                        <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
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


    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="storageMethod2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='open'">Открытый</xsl:when>
			<xsl:when test="$code='closed'">Закрытый</xsl:when>
            <xsl:when test="$code='markByNominal'">Маркированный по номиналу</xsl:when>
			<xsl:when test="$code='markByCoupon'">Маркированный по купону</xsl:when>
			<xsl:when test="$code='markByNominalAndCoupon'">Маркированный по номиналу и купону</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="operationType2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='TRANFER'">IC-231 Перевод</xsl:when>
			<xsl:when test="$code='RECEPTION'">IC-240 Прием перевода</xsl:when>
            <xsl:when test="$code='INTERNAL_TRANFER'">IC-220 Перевод между разделами счета депо</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="operationSubType2text">
		<xsl:param name="code"/>
		<xsl:choose>                 
			<xsl:when test="$code='INTERNAL_TRANSFER'">перевод на счет депо внутри депозитария</xsl:when>
			<xsl:when test="$code='LIST_TRANSFER'">перевод на счет в реестре</xsl:when>
            <xsl:when test="$code='EXTERNAL_TRANSFER'">перевод на счет в другом депозитарии</xsl:when>
            <xsl:when test="$code='INTERNAL_RECEPTION'">прием перевода со счета депо внутри депозитария</xsl:when>
			<xsl:when test="$code='LIST_RECEPTION'">прием перевода со счета в реестре</xsl:when>
            <xsl:when test="$code='EXTERNAL_RECEPTION'">прием перевода со счета в другом депозитарии</xsl:when>
            <xsl:when test="$code='INTERNAL_ACCOUNT_TRANSFER'">перевод между разделами счета депо</xsl:when>
		</xsl:choose>
	</xsl:template>

    <!-- шаблон формирующий div описания -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>
   <xsl:variable name="startRedText">
   <![CDATA[
   <span class="redText">
   ]]>
   </xsl:variable>

   <xsl:variable name="endRedText">
   <![CDATA[
   </span>
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

    <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

   <xsl:for-each select="$nodeText/node()">

   <xsl:choose>
		<xsl:when test=" name() = 'cut' and position() = 1 ">
		    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
		</xsl:when>
        <xsl:when test="name() = 'cut' and position() != 1">
            <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
        </xsl:when>
        <xsl:when test="name() = 'startRedText'">
            <xsl:value-of select="$startRedText" disable-output-escaping="yes"/>
        </xsl:when>
        <xsl:when test="name() = 'endRedText'">
            <xsl:value-of select="$endRedText" disable-output-escaping="yes"/>
        </xsl:when>
   		<xsl:otherwise>
		<xsl:copy />
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>                      <!--идентификатор строки-->
	<xsl:param name="required" select="'true'"/>    <!--параметр обязатьльности заполнения-->
	<xsl:param name="rowName"/>                     <!--описание поля-->
	<xsl:param name="rowValue"/>                    <!--данные-->
	<xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
	<xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
    <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

	<xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
	<!-- Определение имени инпута или селекта передаваемого в шаблон -->
	<!-- inputName - fieldName или имя поле вытащеное из rowValue -->
	<xsl:variable name="inputName">
	<xsl:choose>
		<xsl:when test="string-length($fieldName) = 0">
				<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
					<xsl:value-of select="$nodeRowValue/input/@name" />
					<xsl:if test="count($nodeRowValue/select[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/select/@name" />
					</xsl:if>
                    <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/textarea/@name" />
					</xsl:if>
				</xsl:if>
		</xsl:when>
		<xsl:otherwise>
				<xsl:copy-of select="$fieldName"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

    <xsl:variable name="readonly">
		<xsl:choose>
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

 <xsl:variable name="styleClass">
		<xsl:choose>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

<div class="{$styleClass}">
    <xsl:if test="string-length($lineId) > 0">
        <xsl:attribute name="id">
            <xsl:copy-of select="$lineId"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="string-length($rowStyle) > 0">
        <xsl:attribute name="style">
            <xsl:copy-of select="$rowStyle"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

				<xsl:if test="$readonly = 0 and $mode = 'edit'">
					<xsl:call-template name="buildDescription">
						<xsl:with-param name="text" select="$description"/>
	    			</xsl:call-template>
				</xsl:if>
                <div class="errorDiv" style="display: none;">
				</div>
	</div>
    <div class="clear"></div>
</div>

<!-- Устанавливаем события onfocus поля -->
	<xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>

</xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>
