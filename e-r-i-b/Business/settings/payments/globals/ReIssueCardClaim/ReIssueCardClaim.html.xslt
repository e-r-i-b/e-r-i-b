<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
        xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
        xmlns:ds="java://com.rssl.phizic.business.departments.ReissuedCardDepartmentService"
        xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
        xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
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

	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="editForm"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="cardNumber"/>

        <select id="{$name}" name="{$name}" onchange="refreshForm(false);">
            <xsl:choose>
                <xsl:when test="count($activeCards) = 0">
                    <option value="">Нет доступных карт</option>
                </xsl:when>
            </xsl:choose>
            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$linkId=$id or $cardNumber=./@key">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                     <xsl:value-of select="field[@name='name']"/>&nbsp;
                    <xsl:if test="string-length(field[@name='amountDecimal']) != 0">
                        <xsl:value-of select="format-number(field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mu:getCurrencySign(field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
        </select>
    </xsl:template>

	<xsl:template match="/form-data" mode="editForm">

        <script type="text/javascript">
			<xsl:variable name="cards" select="document('reissued-card-filter.xml')"/>
			var cards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
                card                = new Object()
                card.id             = '<xsl:value-of select="field[@name='code']/text()"/>';
                card.fio            = '<xsl:value-of select="field[@name='formatedPersonName']/text()"/>';
                card.cardState      = '<xsl:value-of select="field[@name='cardState']/text()"/>';
                card.cardAccountState = '<xsl:value-of select="field[@name='cardAccountState']/text()"/>';
                card.issueDate      = '<xsl:value-of select="field[@name='issueDate']/text()"/>';
                <xsl:choose>
                    <xsl:when test="string-length(field[@name='displayedExpireDate']) != 0">
                        card.expireDate     = '<xsl:value-of select="concat(substring(field[@name='displayedExpireDate']/text(), 3, 2), '/20', substring(field[@name='displayedExpireDate']/text(), 1, 2))"/> (включительно)';
                    </xsl:when>
                    <xsl:otherwise>
                        card.expireDate = '';
                    </xsl:otherwise>
                </xsl:choose>
                card.statusDescExternalCode = '<xsl:value-of select="field[@name='statusDescExternalCode']/text()"/>';
                card.vspInfo        = '<xsl:value-of select="field[@name='officeName']/text()"/>&nbsp;<xsl:value-of select="field[@name='officeAddress']/text()"/>';
                card.officeName     = '<xsl:value-of select="field[@name='officeName']/text()"/>';
                card.officeAddress  = '<xsl:value-of select="field[@name='officeAddress']/text()"/>';
                card.region         = '<xsl:value-of select="field[@name='officeRegionCode']/text()"/>';
                card.branch         = '<xsl:value-of select="field[@name='officeBranchCode']/text()"/>';
                card.office         = '<xsl:value-of select="field[@name='officeOfficeCode']/text()"/>';
                card.UNICardType    = '<xsl:value-of select="field[@name='UNICardType']/text()"/>';
                card.isVirtual      = '<xsl:value-of select="field[@name='isVirtual']/text()"/>';

                cards[card.id] = card;
			</xsl:for-each>

            document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';

            addMessage("Обратите внимание! Для перевыпуска карты в связи со сменой фамилии, имени, отчества или реквизитов паспорта Вам необходимо обратиться в любое отделение Вашего территориального банка.");

            var reissueReason = [
                {code : "S", reason : "Карта украдена",        isCommission : true},
                {code : "L", reason : "Карта утеряна",         isCommission : true},
                {code : "F", reason : "Компрометация",         isCommission : false},
                {code : "P", reason : "Утрата ПИН-кода",       isCommission : true},
                {code : "Y", reason : "Изъята банком/Порча или тех. неисправность", isCommission : false}
            ];

            var blockInfo = new Array();
            blockInfo["S_L"] = {status : "Карта утеряна",           reissueReasons : [1]};
            blockInfo["S_S"] = {status : "Карта украдена",          reissueReasons : [0]};
            blockInfo["S_K"] = {status : "Действие приостановлено", reissueReasons : [2,3,4]};
            blockInfo["S_B"] = {status : "Карта утрачена",          reissueReasons : [4,1,0]};
            blockInfo["S_C"] = {status : "Компрометация",           reissueReasons : [2]};
            blockInfo["S_P"] = {status : "Скомпрометирован ПИН",    reissueReasons : [3,2]};
            blockInfo["S_Y"] = {status : "Сдана/Уничтожена",        reissueReasons : [4]};
            blockInfo["S_J"] = {status : "Действие приостановлено по инициативе банка", reissueReasons : [4,2,3]};
            blockInfo["Active"] = {reissueReasons : [0,1,2,3,4]};

            <![CDATA[
            function refreshForm(firstRun)
            {
                var cardLink = ensureElement("cardLink").value;
                var card = cards[cardLink];

		        if (card != null)
		        {
					$("#FIO").text(card.fio);
					$("#issueDate").text(card.issueDate);
					$("#expireDate").text(card.expireDate);
					if (card.cardState == "active")
					{
						$('#blockReasonRow').hide();
					    if (card.cardAccountState == 'ARRESTED')
					    	$("#cardStatus").text("НА СЧЕТ НАЛОЖЕН АРЕСТ");
                        else
						    $("#cardStatus").text("Активная");

						ensureElement("blockReason").value = "Нет причины блокировки";
						$("#blockReasonFld").text("Нет причины блокировки");
						updateReissuedReasonSelect('Active');
						selectDepartment(card.UNICardType, card, firstRun);
						return;
					}

					$("#cardStatus").text("Заблокированная");
					ensureElement("blockReason").value = blockInfo[card.statusDescExternalCode].status;
					$("#blockReasonFld").text(blockInfo[card.statusDescExternalCode].status);
					$('#blockReasonRow').show();

					updateReissuedReasonSelect(card.statusDescExternalCode);

					selectDepartment(card.UNICardType, card, firstRun);
				}
            }

            /**
             * Обновление списка причин перевыпуска.
             */
            function updateReissuedReasonSelect(blockStatus)
            {
                $('#reissueReason').empty();

                for (var i = 0; i < blockInfo[blockStatus].reissueReasons.length; i++)
                {
                    var reason = reissueReason[blockInfo[blockStatus].reissueReasons[i]];
                    $('#reissueReason').append('<option value="' + reason.code + '">' + reason.reason + '</option>');
                }
            }

            function selectDepartment(UNICardType, card, firstRun)
            {
                if (!firstRun || ensureElement("officeCodeRegion").value == '')
                {
				    setOfficeInfo({"region" : card.region, "branch" : card.branch, "office" : card.office,
							   "address" : card.officeAddress, "name" : card.officeName});
                }


                var selDepartment = ensureElement("selectDepartment");
                if (selDepartment == '' || selDepartment == undefined || selDepartment == null)
                    return;

                if (UNICardType == '21' || UNICardType == '22' || card.isVirtual == 'true')
                {
                    document.getElementById("selectDepartmentLink").onclick = null;
                    $("#selectDepartmentLink").removeClass("blueGrayLink");
                    $("#selectDepartmentLink").addClass("inactiveLink");
                    if (!firstRun || ensureElement("officeCodeRegion").value == '')
                    {
                        setOfficeInfo({"region" : card.region, "branch" : card.branch, "office" : card.office,
                                   "address" : card.officeAddress, "name" : card.officeName});
                    }
                }
                else
                {
                    document.getElementById("selectDepartmentLink").onclick = function() {showDepartmetsList();};
                    $("#selectDepartmentLink").removeClass("inactiveLink");
                    $("#selectDepartmentLink").addClass("blueGrayLink");
                }
            }

            $(document).ready(function(){
                refreshForm(true);
            });

           function showDepartmetsList()
           {
                var width = 820;
                var height = 900;
                var win  =  window.open("/PhizIC/private/reissueCard/office/list.do", 'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width="+width+", height="+height);
                win.moveTo((screen.width - width)/2, (screen.height - height)/2);
                return false;
           };

           function setOfficeInfo(info)
           {
               var cardLink = ensureElement("cardLink").value;
               var card = cards[cardLink];
               var cardOffice = card.region == info["region"] && card.office == info["office"] && card.branch == info["branch"];
               ensureElement("officeCodeRegion").value = info["region"];
               ensureElement("officeCodeBranch").value = info["branch"];
               ensureElement("officeCodeOffice").value = info["office"];
               ensureElement("officeAddress").value = info["address"];
               ensureElement("officeName").value = info["name"];

               var officeInfo = (isEmpty(info["name"]) && isEmpty(info["address"])) ? "№ " + info["branch"] + "/" + info["office"] : info["name"] + " " + info["address"];
               $("#placeForGetCardFld").text(cardOffice ? ("Подразделение Банка по месту оформления карты (" + officeInfo + ")") : officeInfo);
               ensureElement("placeForGetCard").value = $("#placeForGetCardFld").text();
           };
           ]]>
		</script>
		<xsl:variable name="activeCards" select="document('reissued-card-filter.xml')/entity-list/*"/>
 	    <input id="documentNumber" name="documentNumber" type="hidden"/>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Карта</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">cardLink</xsl:with-param>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="linkId" select="cardLink"/>
                    <xsl:with-param name="cardNumber" select="cardNumber"/>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
		<xsl:call-template name="standartRow">
            <xsl:with-param name="id">FIO</xsl:with-param>
			<xsl:with-param name="rowName">ФИО владельца</xsl:with-param>
			<xsl:with-param name="rowValue">
				<b><span id="FIO" name="FIO"> </span></b>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">issueDate</xsl:with-param>
			<xsl:with-param name="rowName">Дата выпуска</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><span id="issueDate" name="issueDate"> </span></b>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">expireDate</xsl:with-param>
			<xsl:with-param name="rowName">Действует до</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><span id="expireDate" name="expireDate"> </span></b>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">cardStatus</xsl:with-param>
			<xsl:with-param name="rowName">Статус карты</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><span id="cardStatus" name="cardStatus"> </span></b>
			</xsl:with-param>
		</xsl:call-template>
        <div id="blockReasonRow" name="blockReasonRow">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">blockReason</xsl:with-param>
			<xsl:with-param name="rowName">Причина блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><span name="blockReasonFld" id="blockReasonFld"> </span></b>
                <input type="hidden" name="blockReason" id="blockReason"/>
			</xsl:with-param>
		</xsl:call-template>
        </div>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина перевыпуска</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="reissueReason" name="reissueReason">
                    <option value="S">Карта украдена</option>
                    <option value="L">Карта утеряна</option>
                    <option value="F">Компрометация</option>
                    <option value="P">Утрата ПИН-кода</option>
                    <option value="Y">Изъята банком/Порча или тех. неисправность</option>
                </select>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">placeForGetCard</xsl:with-param>
            <xsl:with-param name="rowName">Место получения карты</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><span name="placeForGetCardFld" id="placeForGetCardFld"><xsl:value-of select="placeForGetCard"/></span></b>
                <input type="hidden" name="placeForGetCard" id="placeForGetCard" value="{placeForGetCard}"/>
                <xsl:if test="ds:haveDepartmentForCreditCard()">
                    <div name="selectDepartment" id="selectDepartment" class="paymentInputDiv">
                        <a onclick="showDepartmetsList();" id="selectDepartmentLink" class="blueGrayLink">Выбрать из справочника</a>
                    </div>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
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
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Карта</xsl:with-param>
            <xsl:with-param name="rowValue">
	            <b>
                <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>&nbsp;
                <xsl:value-of select="cardName"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign(cardCurrency)"/>
	            </b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ФИО владельца</xsl:with-param>
            <xsl:with-param name="rowValue">
                 <b><xsl:value-of select="ph:getFormattedPersonName(userFirstName, userSurname, userPatrName)" disable-output-escaping="no"/></b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата выпуска</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="issueDate"/></b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Действует до</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="string-length(displayedExpireDate) != 0">
                    <b><xsl:value-of select="concat(substring(displayedExpireDate, 3, 2),'/20',substring(displayedExpireDate, 1, 2))"/> (включительно)</b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус карты</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b>Заблокирована</b>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина блокировки</xsl:with-param>
			<xsl:with-param name="rowValue"><b><xsl:value-of select="blockReason"/></b></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина перевыпуска</xsl:with-param>
			<xsl:with-param name="rowValue">
				<b><xsl:choose>
                    <xsl:when test="reissueReason = 'S'">
                        Карта украдена
                    </xsl:when>
                    <xsl:when test="reissueReason = 'L'">
                        Карта утеряна
                    </xsl:when>
                    <xsl:when test="reissueReason = 'F'">
                        Компрометация
                    </xsl:when>
                    <xsl:when test="reissueReason = 'P'">
                        Утрата ПИН-кода
                    </xsl:when>
                    <xsl:when test="reissueReason = 'Y'">
                        Изъята банком/Порча или тех. неисправность
                    </xsl:when>
				</xsl:choose></b>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Плата за перевыпуск</xsl:with-param>
			<xsl:with-param name="rowValue">
				<b>
                    <xsl:choose>
                        <xsl:when test="isCommission != 'false'">
                            Взимается согласно тарифам банка. С тарифами можно ознакомиться <a href="#" onclick="openTarif('http://sberbank.ru/ru/person/bank_cards/tarif/'); return false;">здесь</a>
                        </xsl:when>
                        <xsl:otherwise>
                            Не взимается
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
			</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Место получения карты</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="placeForGetCard"/></b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус документа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<b><div id="state">
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
				</div></b>
			</xsl:with-param>
		</xsl:call-template>
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
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk" name="asterisk_{$lineId}">*</span>
		</xsl:if>
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

</xsl:stylesheet>