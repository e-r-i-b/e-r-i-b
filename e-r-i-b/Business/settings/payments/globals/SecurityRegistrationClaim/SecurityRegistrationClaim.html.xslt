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
	<xsl:param name="showCommission" select="'showCommission'"/>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>
    <xsl:variable name="separator" select="'|'"/>

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
       <xsl:variable name="openDepoAccounts" select="document('open-allowed-depo-accounts.xml')/entity-list"/>
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

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">depositor</xsl:with-param>
			<xsl:with-param name="rowName">Депонент:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input id="depositor" name="depositor" value="{depositor}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет депо:</xsl:with-param>
            <xsl:with-param name="description">Выберите Ваш счет депо.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="num" select="depoAccount"/>
                <input type="hidden" name="depoExternalId" value="{depoExternalId}"/>
                <select id="depoAccount" name="depoAccount" onchange="setDepoAccountInfo();" style="width: 300px;">
                    <xsl:choose>
                        <xsl:when test="count($openDepoAccounts/*) = 0">
                            <xsl:attribute name="disabled">true</xsl:attribute>
                            <option value="">Нет доступных счетов депо</option>
                        </xsl:when>
                        <xsl:otherwise>
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
                        </xsl:otherwise>
                    </xsl:choose>                    
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

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Параметры ценной бумаги</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityName</xsl:with-param>
		    <xsl:with-param name="description">
		         Введите наименование ценной бумаги, которую Вы хотите зарегистрировать.
		    </xsl:with-param>
			<xsl:with-param name="rowName">Наименование ценной бумаги:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="securityName" name="securityName" value="{securityName}" type="text" size="46"/>&nbsp;&nbsp;
		</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">issuer</xsl:with-param>
		    <xsl:with-param name="description">
		         Введите наименование лица, выпустившего ценную бумагу. Для векселей или иных неэмиссионных
		         ценных бумаг также укажите местонахождение и контактный телефон эмитента (векселедателя).
		    </xsl:with-param>
			<xsl:with-param name="rowName">Эмитент:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="issuer" name="issuer" value="{issuer}" type="text" size="46"/>&nbsp;&nbsp;
		</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">insideCode</xsl:with-param>
		    <xsl:with-param name="description">
                Введите регистрационный номер: для эмиссионных ценных бумаг укажите государственный регистрационный номер. Для неэмиссионных ценных бумаг - серию (при наличии), номер и номинал ценной бумаги. Обратите внимание на язык ввода - в регистрационном номере обычно используются латинские буквы.
		    </xsl:with-param>
			<xsl:with-param name="rowName">Регистрационный номер:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="securityNumber" name="securityNumber" value="{securityNumber}" type="text" size="46" />
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepositary</xsl:with-param>
		    <xsl:with-param name="description">
		        Для именных ценных бумаг - укажите наименование лица, осуществляющего ведение реестра именных ценных бумаг (регистратора или эмитента). <cut/>
                Для ценных бумаг с централизованным хранением - наименование депозитария, осуществляющего централизованное хранение.<br/>
                Место нахождения Регистратора/Депозитария (при наличии сведений).<br/>
                Для иных ценных бумаг - ставится прочерк.
		    </xsl:with-param>
			<xsl:with-param name="rowName">Депозитарий/Регистратор:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="corrDepositary" name="corrDepositary" value="{corrDepositary}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>
        <div id="securityRegistrationClaimOperations">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Планируемые операции:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="depositaryOperation = 'true'">
                            <input type="checkbox" id="depositaryOperation" name="depositaryOperation" value="true" checked="true"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input type="checkbox" id="depositaryOperation" name="depositaryOperation" value="true"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    &nbsp;&nbsp;<b>Депозитарные переводы</b>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="depositOperation = 'true'">
                            <input type="checkbox" id="depositOperation" name="depositOperation" value="true" checked="true"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input type="checkbox" id="depositOperation" name="depositOperation" value="true"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    &nbsp;&nbsp;<b>Залоговые операции</b>
                    </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="accountOperation = 'true'">
                            <input type="checkbox" id="accountOperation" name="accountOperation" value="true" checked="true"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input type="checkbox" id="accountOperation" name="accountOperation" value="true"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    &nbsp;&nbsp;<b>Только учет и хранение</b>
                    </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="tradeOperation = 'true'">
                            <input type="checkbox" id="tradeOperation" name="tradeOperation" value="true" checked="true"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input type="checkbox" id="tradeOperation" name="tradeOperation" value="true"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    &nbsp;&nbsp;<b>Торговые операции</b>
                    </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="tokenizeString">
                <xsl:with-param name="string" select="clientOperationDesc"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id" select="'clientOperationDescRow'"/>
                <xsl:with-param name="lineId" select="'clientOperationDescRow'"/>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <input id="clientOperationDescField" name="clientOperationDescField" type="text"   size="46" maxlength = "50"/>
                    <input id="clientOperationDesc"      name="clientOperationDesc"      type="hidden" value="{clientOperationDesc}"/>
                    <xsl:call-template name="button"/>
                </xsl:with-param>
                <xsl:with-param name="description">
                    Укажите другое наименование операции.
                    <cut/>В названии операции можно использовать буквы лат. алфавита, кириллицы, пробел, ?, !, цифры, (), "", точку, запятую или тире.
                </xsl:with-param>
            </xsl:call-template>
        </div>

        <script type="text/javascript">
            var depoAccounts = new Array();
            var delimiter = '<xsl:value-of select="$separator"/>';

            function validateClientOperationDescField()
            {
                if ($('input[name="clientOperation"]').length == 3)
                {
                    return "Нельзя добавить больше трех операций."
                }

                var currentInput = document.getElementById('clientOperationDescRow').currentInput;
                if (currentInput != undefined)
                {
                    var validateMessages = validate(currentInput, {VALIDATE_REGEXP_LETTERS_NAME : /^[a-zа-яё0-9!? ()".,-]{1,50}$/i});
                    if (validateMessages.length != 0)
                    {
                        return validateMessages;
                    }
                }
                else
                {
                    return "Пожалуйста, заполните данное поле.";
                }

                var clientOperationDesc = trim(document.getElementById('clientOperationDescField').value);
                if (clientOperationDesc.length == 0)
                {
                    return "Пожалуйста, заполните данное поле.";
                }

                var divs = $("div#securityRegistrationClaimOperations div.paymentInputDiv");
                <![CDATA[
                for (var index = 0; index < divs.length; index++)
                ]]>
                {
                    var currentOperationDescription = trim(divs[index].children[1].innerHTML);
                    if (currentOperationDescription == clientOperationDesc)
                    {
                        return "Данная операция уже существует.";
                    }
                }
                return validateMessages;
            }

            function deleteClientOperation(thisLink)
            {
               var parentDiv = thisLink.parentNode.parentNode.parentNode;
               var text  = $(thisLink.parentNode).find('b').html();
               changeClientOperationDesc(false, text);
               $(parentDiv).remove();
            }

            function changeClientOperationDesc(add, clientOperationDesc)
            {
                var clientOperationsDesc = getElementValue('clientOperationDesc');
                if (add)
                {
                    if (clientOperationsDesc.lastIndexOf(delimiter) != clientOperationsDesc.length-1)
                        clientOperationsDesc = clientOperationsDesc + delimiter;
                    if (clientOperationsDesc.indexOf(delimiter) != 0)
                        clientOperationsDesc = delimiter + clientOperationsDesc;
                    setElement("clientOperationDesc", clientOperationsDesc + trim(clientOperationDesc) + delimiter);
                    return;
                }
                if (clientOperationsDesc == delimiter + clientOperationDesc + delimiter)
                {
                    setElement("clientOperationDesc", "");
                    return;
                }
                setElement("clientOperationDesc", clientOperationsDesc.split(delimiter + clientOperationDesc + delimiter).join(delimiter));
            }

            function getCheckboxText(currentCheckbox)
            {
                return $(currentCheckbox).next().text();
            }

            function checkboxClientOperationClick(thisCheckbox)
            {
                changeClientOperationDesc(thisCheckbox.checked, getCheckboxText(thisCheckbox));
            }

            function getClientOperationRow(clientOperationDesc)
            {
                <![CDATA[
                var line =    '<div onclick="payInput.onClick(this)" class="form-row">';
                line = line + '<div class="paymentLabel"><span class="paymentTextLabel"></span></div>';
                line = line + '<div class="paymentValue"><div class="paymentInputDiv" name="clientOperationRow">';
                line = line + '<input type="checkbox" id="clientOperation" name="clientOperation" value="true" checked onclick="checkboxClientOperationClick(this)"/>';
                line = line + '&nbsp;&nbsp;<b>' + clientOperationDesc + '</b>';
                line = line + '&nbsp;&nbsp;<a style="cursor: pointer;" onclick="deleteClientOperation(this)">Удалить</a></div>';
                line = line + '<div class="description" style="display: none"></div>';
                line = line + '<div class="errorDiv" style="display: none;"></div></div><div class="clear"></div></div>';
                ]]>
                return line;
            }

            function hideErrorMessage()
            {
                $("#clientOperationDescRow .paymentValue .errorDiv").hide();
                document.getElementById("clientOperationDescRow").error = false;
                return validateClientOperationDescField();
            }

            function showErrorMessage(fieldErrMessage)
            {
                payInput.fieldError("clientOperationDescField", fieldErrMessage);
            }

            function addClientOperationDesc()
            {
                var clientOperationDescField = trim(getElementValue('clientOperationDescField'));

                var messages = hideErrorMessage();
                if (messages.length != 0)
                {
                    showErrorMessage(messages);
                    return;
                }

                var clientOperationsDesc = getElementValue('clientOperationDesc');
                setElement("clientOperationDescField", "");
                changeClientOperationDesc(true, clientOperationDescField);
                $("#clientOperationDescRow").before(getClientOperationRow(clientOperationDescField));
            }

            function initDepoAccounts()
            {
                <xsl:for-each select="$openDepoAccounts/*">
                    var depoAccount = new Object();

                    depoAccount.owner = '<xsl:value-of select="./field[@name='owner']"/>';
                    depoAccount.externalId = '<xsl:value-of select="./field[@name='externalId']"/>';

                    depoAccounts['<xsl:value-of select="@key"/>'] = depoAccount;
                </xsl:for-each>
            }

            function setDepoAccountInfo()
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(depoAccount == null)
                    return;
                setElement("depositor", depoAccount.owner);
                setElement("managerFIO", depoAccount.owner);
                setElement("depoExternalId", depoAccount.externalId);
             }

            initDepoAccounts();
            setDepoAccountInfo();
            
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

         <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Депонент:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="depositor"/></b>
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
                <b><xsl:value-of  select="managerFIO"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Параметры ценной бумаги</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Наименование ценной бумаги:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="securityName"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Эмитент:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="issuer"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Регистрационный номер:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="securityNumber"/>
            </xsl:with-param>
        </xsl:call-template>

         <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Депозитарий/Регистратор:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepositary"/>
            </xsl:with-param>
        </xsl:call-template>

         <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Планируемые операции:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:choose>
					<xsl:when test="depositaryOperation = 'true'">
						<input type="checkbox" checked="true" disabled="true"/>
					</xsl:when>
					<xsl:otherwise>
						<input type="checkbox" disabled="true"/>
					</xsl:otherwise>
				</xsl:choose>
                &nbsp;&nbsp;<b>Депозитарные переводы</b>
            </xsl:with-param>
         </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <xsl:choose>
					<xsl:when test="depositOperation = 'true'">
						<input type="checkbox" checked="true" disabled="true"/>
					</xsl:when>
					<xsl:otherwise>
						<input type="checkbox" disabled="true"/>
					</xsl:otherwise>
				</xsl:choose>
                &nbsp;&nbsp; <b>Залоговые операции</b>
          </xsl:with-param>
         </xsl:call-template>
         <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <xsl:choose>
					<xsl:when test="accountOperation = 'true'">
						<input type="checkbox" checked="true" disabled="true"/>
					</xsl:when>
					<xsl:otherwise>
						<input type="checkbox" disabled="true"/>
					</xsl:otherwise>
				</xsl:choose>
                &nbsp;&nbsp;<b>Только учет и хранение</b>
            </xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <xsl:choose>
					<xsl:when test="tradeOperation = 'true'">
						<input type="checkbox" checked="true" disabled="true"/>
					</xsl:when>
					<xsl:otherwise>
						<input type="checkbox" disabled="true"/>
					</xsl:otherwise>
				</xsl:choose>
                &nbsp;&nbsp;<b>Торговые операции</b>
            </xsl:with-param>
		</xsl:call-template>
        
        <xsl:call-template name="tokenizeString">
            <xsl:with-param name="string" select="clientOperationDesc"/>
        </xsl:call-template>

        <xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус заявки</xsl:with-param>
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
        </xsl:if>

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

    <xsl:template name="tokenizeString">
        <xsl:param name="string"/>

        <xsl:if test="string-length($string) > 0">
            <xsl:variable name="before-separator" select="substring-before($string, $separator)"/>
            <xsl:variable name="after-separator" select="substring-after($string, $separator)"/>

            <xsl:choose>
                <xsl:when test="string-length($before-separator)=0 and string-length($after-separator)=0">
                    <xsl:call-template name="clientOperationRow">
                        <xsl:with-param name="string" select="$string"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="string-length($before-separator)!=0">
                        <xsl:call-template name="clientOperationRow">
                            <xsl:with-param name="string" select="$before-separator"/>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:call-template name="tokenizeString">
                        <xsl:with-param name="string" select="$after-separator"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>

    <xsl:template name="clientOperationRow">
        <xsl:param name="string"/>

        <div onclick="payInput.onClick(this)" class="form-row">
            <div class="paymentLabel">
                <span class="paymentTextLabel"></span>
            </div>
            <div class="paymentValue">
                <div class="paymentInputDiv">
                    <xsl:variable name="checkbox">
                        <![CDATA[<input type="checkbox" id="clientOperation" name="clientOperation" value="true" ]]>
                        <xsl:if test="$mode = 'view'">
                            disabled = "true"
                        </xsl:if>
                        <![CDATA[ checked onclick="checkboxClientOperationClick(this)"/>&nbsp;&nbsp;]]>
                    </xsl:variable>
                    <xsl:value-of select="$checkbox" disable-output-escaping="yes"/>
                    <b><xsl:value-of select="$string"/></b>
                    <xsl:if test="$mode = 'edit'">
                        &nbsp;&nbsp;<a style="cursor: pointer;" onclick="deleteClientOperation(this)">Удалить</a>
                    </xsl:if>
                </div>
                <div class="description" style="display: none"></div>
                <div class="errorDiv" style="display: none;"></div>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>

    <xsl:template name="button" mode="edit">
        <div class="clientButton">
            <div class="buttonGrey" onclick="addClientOperationDesc()">
                <div class="left-corner"></div>
                <div class="text">
                    <span>Добавить</span>
                </div>
                <div class="right-corner"></div>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>
