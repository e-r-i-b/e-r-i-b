<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
        xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
        xmlns:ds="java://com.rssl.phizic.business.departments.ReissuedCardDepartmentService"
        xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
        xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
        xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
        xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
        xmlns:msk="java://com.rssl.phizic.utils.MaskUtil"
        xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
        xmlns:stgsh="java://com.rssl.phizic.config.SettingsHelper"
        xmlns:ucrs="java://com.rssl.phizic.business.connectUdbo.MultiInstanceUDBOClaimRulesService"
        xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0"  indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClassTitle" select="'rowTitles'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
        <xsl:apply-templates mode="editForm"/>
    </xsl:template>

	<xsl:template match="/form-data" mode="editForm">
        <xsl:choose>
            <xsl:when test="state = 'INITIAL'">
                <xsl:variable name="acceptDepositCondition" select="stgsh:getAcceptConnectUdboMessage()"/>
                 <div class="underStripeDesc">
                    Для подключения всех возможностей Сбербанк Онлайн вам необходимо заключить Договор банковского обслуживания, который определяет общие правила и условия предоставления банковских услуг физическим лицам в ОАО «Сбербанк России».
                </div>
                <div class="okb-dogovor conditionality dogovorFull">
                    <div class="okb-dogovor-yellow float">
                        <xsl:variable name="onclick">
                            printUDBOInfo();
                        </xsl:variable>

                        <div class="okb-dogovor-top">Открыть условия договора в <a class="okb-dogovor-new-win" onclick="{$onclick}">новом окне</a></div>

                        <xsl:attribute name="onclick">
                            <xsl:value-of select="$onclick"/>
                        </xsl:attribute>

                        <div class="okb-dogovor-text accountOpenText" id="accountOpenText">
                            <!--Текст условий открытия удбо-->
                        </div>

                        <div id="agreeForAllRow">
                            <input type="checkbox" id="agreeForAll" name="agreeForAll" value="" class="agreeChbx float"/>
                            <label for="agreeForAll" class="labelTick"> <xsl:value-of select="$acceptDepositCondition"/></label>
                        </div>
                    </div>
                </div>
                <div class="separateDogovor"></div>

                <script>
                    function checkPayment()
                    {
                        var input = document.getElementById("agreeForAll");
                        if (input.checked)
                            return true;

                        addMessage("Для того чтобы подключить все возможности Сбербанк Онлайн, ознакомьтесь с условиями и подтвердите своё согласие установкой флажка в поле «<xsl:value-of select="$acceptDepositCondition"/>».");
                        return false;
                    }

                    function printUDBOInfo()
                    {
                        var termsURL = '<xsl:value-of select="$webRoot"/>/private/udbo/term.do';
                        window.open(termsURL, 'depositInfo', 'menubar=1,toolbar=0,scrollbars=1,resizable=1');
                    }

                    function getDepositInfo()
                    {
                        var termsURL = '<xsl:value-of select="$webRoot"/>/private/udbo/term.do';
                        var params = 'loadInDiv=true';
                        ajaxQuery(params, termsURL, function (data)
                                {
                                    ensureElement('accountOpenText').innerHTML = data;
                                    window.userReadFirstConfirmMessage = true;
                                    window.userReadSecondConfirmMessage = false;
                                },
                            null, false);
                    }

                    doOnLoad(function()
                    {
                        getDepositInfo();
                    });

                </script>
            </xsl:when>
            <xsl:when test="state = 'DRAFT'">
                <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
                <xsl:variable name="personData" select="document('currentPersonData.xml')/entity-list"/>

                <input id="documentNumber" name="documentNumber" type="hidden"/>
                <xsl:variable name="userFullName" select="$currentPerson/entity/field[@name='formatedPersonName']"/>
                <xsl:variable name="gender" select="$personData/entity/field[@name='gender']"/>
                <xsl:variable name="firstName" select="$personData/entity/field[@name='firstName']"/>
                <xsl:variable name="surName" select="$personData/entity/field[@name='surName']"/>
                <xsl:variable name="patrName" select="$personData/entity/field[@name='patrName']"/>
                <xsl:variable name="inn" select="$personData/entity/field[@name='inn']"/>
                <xsl:variable name="isResident" select="$currentPerson/entity/field[@name='isResident']"/>
                <xsl:variable name="address" select="$currentPerson/entity/field[@name='address']"/>
                <xsl:variable name="registrationAddress-city" select="$personData/entity/field[@name='registrationAddress-city']"/>
                <xsl:variable name="email" select="$personData/entity/field[@name='email']"/>
                <xsl:variable name="region" select="$personData/entity/field[@name='region']"/>
                <xsl:variable name="branch" select="$personData/entity/field[@name='logonCardOSB']"/>
                <xsl:variable name="office" select="$personData/entity/field[@name='logonCardVSP']"/>
                <xsl:variable name="birthDay" select="$currentPerson/entity/field[@name='birthDay']"/>
                <xsl:variable name="birthDayString" select="$currentPerson/entity/field[@name='birthDayString']"/>
                <xsl:variable name="citizenship" select="$personData/entity/field[@name='citizen']"/>
                <xsl:variable name="birthPlace" select="$personData/entity/field[@name='birthPlace']"/>
                <xsl:variable name="cardNumber" select="$personData/entity/field[@name='cardNumber']"/>
                <xsl:variable name="ipAddress" select="$personData/entity/field[@name='ipAddress']"/>
                <xsl:variable name="mobilePhone" select="$personData/entity/field[@name='additionalPhone1']"/>
                <xsl:variable name="mobileOperator" select="$personData/entity/field[@name='mobileOperator']"/>
                <xsl:variable name="passportIssueDate" select="dh:formatXsdDateToString($personData/field[@name = 'passportIssueDate'])"/>
                <xsl:variable name="passportSeries" select="$personData/entity/field[@name='passportSeries']"/>
                <xsl:variable name="passportNumber" select="$personData/entity/field[@name='passportNumber']"/>
                <xsl:variable name="passportIssuedBy" select="$personData/entity/field[@name='passportIssueBy']"/>
                <xsl:variable name="identityType" select="$personData/entity/field[@name='identityType']"/>
                <xsl:variable name="identityTypeName" select="$personData/entity/field[@name='identityTypeName']"/>
                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">Краткая информация обо мне</xsl:with-param>
                </xsl:call-template>
          		<xsl:call-template name="standartRow">
                      <xsl:with-param name="id">FIO</xsl:with-param>
          			<xsl:with-param name="rowName">ФИО</xsl:with-param>
          			<xsl:with-param name="rowValue">
          				<b><span><xsl:value-of select="$userFullName"/></span></b>
          			</xsl:with-param>
          		</xsl:call-template>
                <input type="hidden" name="userFullName" value="{$userFullName}"/>
                <input type="hidden" name="firstName" value="{$firstName}"/>
                <input type="hidden" name="surName" value="{$surName}"/>
                <input type="hidden" name="patrName" value="{$patrName}"/>
                <input type="hidden" name="inn" value="{$inn}"/>
                <input type="hidden" name="isResident" value="{$isResident}"/>
                <input type="hidden" name="address" value="{$address}"/>
                <input type="hidden" name="registrationAddress-city" value="{$registrationAddress-city}"/>
                <input type="hidden" name="email" value="{$email}"/>
                <input type="hidden" name="region" value="{$region}"/>
                <input type="hidden" name="office" value="{$office}"/>
                <input type="hidden" name="branch" value="{$branch}"/>
                <input type="hidden" name="passportIssuedBy" value="{$passportIssuedBy}"/>
                <input type="hidden" name="mobileOperator" value="{$mobileOperator}"/>
                <input type="hidden" name="cardNumber" value="{$cardNumber}"/>
                <input type="hidden" name="ipAddress" value="{$ipAddress}"/>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">gender</xsl:with-param>
          			<xsl:with-param name="rowName">Ваш пол</xsl:with-param>
          			<xsl:with-param name="rowValue">
          				<b><span id="gender" name="gender"><xsl:value-of select="$gender"/> </span></b>
          			</xsl:with-param>
          		</xsl:call-template>
                <input type="hidden" name="gender" value="{$gender}"/>
                <input type="hidden" name="birthDay" value="{$birthDay}"/>
                <input type="hidden" name="birthDayString" value="{$birthDayString}"/>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">citizenship</xsl:with-param>
          			<xsl:with-param name="rowName">Гражданство</xsl:with-param>
          			<xsl:with-param name="rowValue">
          				<b><span id="citizenship" name="citizenship"><xsl:value-of select="$citizenship"/> </span></b>
          			</xsl:with-param>
          		</xsl:call-template>
                <input type="hidden" name="citizenship" value="{$citizenship}"/>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">birthPlace</xsl:with-param>
          			<xsl:with-param name="rowName">Место рождения</xsl:with-param>
          			<xsl:with-param name="rowValue">
          				<b><span id="birthPlace" name="birthPlace"><xsl:value-of select="$birthPlace"/> </span></b>
          			</xsl:with-param>
          		</xsl:call-template>
                <input type="hidden" name="birthPlace" value="{$birthPlace}"/>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">mobilePhone</xsl:with-param>
          			<xsl:with-param name="rowName">Мобильный телефон</xsl:with-param>
          			<xsl:with-param name="rowValue">
          				<b><span id="mobilePhone" name="mobilePhone"><xsl:value-of select="mpnu:getCutPhoneForAddressBook($mobilePhone)"/>  </span></b>
          			</xsl:with-param>
          		</xsl:call-template>
                <input type="hidden" name="mobilePhone" value="{$mobilePhone}"/>
                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">Документ, удостоверяющий личность</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">identityTypeName</xsl:with-param>
                    <xsl:with-param name="rowName">Тип документа</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span id="identityTypeName" name="identityTypeName"><xsl:value-of select="$identityTypeName"/>  </span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="required" select="'true'"/>
                      <xsl:with-param name="rowName">Серия и номер:</xsl:with-param>
                      <xsl:with-param name="rowValue">
                          <b><span class="floatLeft"><xsl:value-of select="msk:maskString(concat($passportSeries, $passportNumber), 4, 2, '•')"/></span></b>
                          <div class="simpleHintBlock">
                              <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                              <div class="clear"></div>
                              <div class="layerFon simpleHint">
                                  <div class="floatMessageHeader"></div>
                                  <div class="layerFonBlock"> В целях безопасности Ваши личные данные маскированы </div>
                              </div>
                          </div>
                      </xsl:with-param>
                </xsl:call-template>
                <input type="hidden" name="passportSeries" value="{$passportSeries}"/>
                <input type="hidden" name="passportNumber" value="{$passportNumber}"/>
                <input type="hidden" name="identityType" value="{$identityType}"/>
                <input type="hidden" name="identityTypeName" value="{$identityTypeName}"/>

                <input type="hidden" name="passportIssueDate" value="{$passportIssueDate}"/>
          		<tr>
                    <td class='label120 LabelAll' id="hideLayerL"/>
                    <td id="hideLayerR"/>
                </tr>
          		<tr><td class='label120 LabelAll'>&nbsp;</td><td id="hideLayer"/></tr>
          		<br/><br/>
                <input type="hidden" id="officeCodeRegion" name="officeCodeRegion" value="{officeCodeRegion}"/>
                <input type="hidden" id="officeCodeBranch" name="officeCodeBranch" value="{officeCodeBranch}"/>
                <input type="hidden" id="officeCodeOffice" name="officeCodeOffice" value="{officeCodeOffice}"/>
                <input type="hidden" id="officeName"       name="officeName"       value="{officeName}"/>
                <input type="hidden" id="officeAddress"    name="officeAddress"    value="{officeAddress}"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="state = 'SAVED'">
                   <xsl:call-template name="titleRow">
                       <xsl:with-param name="rowName">Заявление на банковское обслуживание</xsl:with-param>
                   </xsl:call-template>
                   <ol class="udboConditionList">
                       <xsl:value-of select="ucrs:getTermTextForConnectionUdbo()" disable-output-escaping="yes"/>
                   </ol>
                   <br/>
                </xsl:if>

                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">Краткая информация обо мне</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">FIO</xsl:with-param>
                    <xsl:with-param name="rowName">ФИО</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span><xsl:value-of select="userFullName"/></span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">gender</xsl:with-param>
                    <xsl:with-param name="rowName">Ваш пол</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span id="gender" name="gender"><xsl:value-of select="gender"/> </span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">citizenship</xsl:with-param>
                    <xsl:with-param name="rowName">Гражданство</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span id="citizenship" name="citizenship"><xsl:value-of select="citizenship"/> </span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">birthPlace</xsl:with-param>
                    <xsl:with-param name="rowName">Место рождения</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span id="birthPlace" name="birthPlace"><xsl:value-of select="birthPlace"/> </span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="id">mobilePhone</xsl:with-param>
                    <xsl:with-param name="rowName">Мобильный телефон</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span id="mobilePhone" name="mobilePhone"><xsl:value-of select="mpnu:getCutPhoneForAddressBook(mobilePhone)"/>  </span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="titleRow">
                    <xsl:with-param name="rowName">Документ, удостоверяющий личность</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">identityTypeName</xsl:with-param>
                    <xsl:with-param name="rowName">Тип документа</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><span id="identityTypeName" name="identityTypeName"><xsl:value-of select="identityTypeName"/>  </span></b>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="standartRow">
                      <xsl:with-param name="required" select="'true'"/>
                      <xsl:with-param name="rowName">Серия и номер</xsl:with-param>
                      <xsl:with-param name="rowValue">
                          <b><span class="floatLeft"><xsl:value-of select="msk:maskString(concat(passportSeries, passportNumber), 4, 2, '•')"/></span></b>
                          <div class="simpleHintBlock">
                              <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                              <div class="clear"></div>
                              <div class="layerFon simpleHint">
                                  <div class="floatMessageHeader"></div>
                                  <div class="layerFonBlock"> В целях безопасности Ваши личные данные маскированы </div>
                              </div>
                          </div>
                      </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="state != 'SAVED'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Статус заявки</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <div id="state">
                                <span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
                                    <xsl:call-template name="state2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </span>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:if test="$app != 'PhizIA'">
                        <xsl:variable name="acceptDepositCondition" select="stgsh:getAcceptConnectUdboMessage()"/>
                            <div class="okb-dogovor conditionality dogovorFull viewPayDogovor">
                            <div class="okb-dogovor-yellow float">
                                <xsl:variable name="onclick">
                                    printUDBOInfo();
                                </xsl:variable>

                                <div class="okb-dogovor-top">Открыть условия договора в <a class="okb-dogovor-new-win" onclick="{$onclick}">новом окне</a></div>

                                <xsl:attribute name="onclick">
                                    <xsl:value-of select="$onclick"/>
                                </xsl:attribute>

                                <div class="okb-dogovor-text accountOpenText" id="accountOpenText">
                                    <!--Текст условий открытия удбо-->
                                </div>

                            </div>
                        </div>
                        <div class="separateDogovor"></div>

                        <script>
                            function printUDBOInfo()
                            {
                                var termsURL = '<xsl:value-of select="$webRoot"/>/private/udbo/term.do';
                                window.open(termsURL, 'depositInfo', 'menubar=1,toolbar=0,scrollbars=1,resizable=1');
                            }

                            function getDepositInfo()
                            {
                                var termsURL = '<xsl:value-of select="$webRoot"/>/private/udbo/term.do';
                                var params = 'loadInDiv=true';
                                ajaxQuery(params, termsURL, function (data)
                                        {
                                            ensureElement('accountOpenText').innerHTML = data;
                                            window.userReadFirstConfirmMessage = true;
                                            window.userReadSecondConfirmMessage = false;
                                        },
                                    null, false);
                            }

                            doOnLoad(function()
                            {
                                getDepositInfo();
                            });

                        </script>
                    </xsl:if>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
	</xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
		</xsl:choose>
	</xsl:template>

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
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
    <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->

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

<!--  Поиск ошибки по имени поля
В данной реализации ошибки обрабатывает javascript
                <xsl:if test="$mode = 'edit'">
                    <xsl:if test="boolean($validationErrors/entity[@key=$fieldName])">
                        <xsl:copy-of select="$validationErrors/entity[@key=$fieldName]"/>
                    </xsl:if>
                </xsl:if>
-->
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

	<div class="paymentLabel">
	    <span class="paymentTextLabel">
	        <xsl:if test="string-length($id) > 0">
	            <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
    </div>
	<div class="paymentValue">
        <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/></div>

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
    <xsl:param name="rowValue"/>
    <div id="{$lineId}" class="{$styleClassTitle}">
        <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
    </div>
</xsl:template>

<xsl:template name="state2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='DRAFT'">Черновик</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает доп. подтверждения</xsl:when>
            <xsl:when test="$code='ACCEPTED'">Одобрен</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        </xsl:choose>
</xsl:template>

</xsl:stylesheet>