<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<c:if test="${phiz:impliesService('CreateMoneyBoxPayment')}">
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="data">
            <tiles:put name="title"> </tiles:put>
            <c:set var="createMoneyBoxValue">${form.fields['createMoneyBox']}</c:set>
            <div id="moneyBoxTitle" style="font-size:16px;font-weight:bold;">Копилка <input type="checkbox" name="field(createMoneyBox)" id="createMoneyBox" value="true"
                 <c:if test="${createMoneyBoxValue}">checked="true"</c:if>/>
            </div>
        </tiles:put>
    </tiles:insert>
    <div id="moneyBoxFieldsDiv" style="display: none;">
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Название:</tiles:put>
            <tiles:put name="data">
                <div id="MBNameText" class="productTitleMargin word-wrap">
                    <span class="productTitleDetailInfoText" style="font-size: 13px; font-weight: bold; color: #7ea29f;">
                        <span id="moneyBoxName" class="moneyBoxNameTitle"><bean:write name="form" property="field(moneyBoxName)"/></span>
                        <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName(); setClientNameFlag();"></a>
                    </span>
                </div>
                <div id="MBNameEdit" style="display: none;">
                    <html:text name="form" property="field(moneyBoxName)"  maxlength="30" styleId="moneyBoxName"/>
                </div>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Карта списания:</tiles:put>
            <tiles:put name="data">
                <html:select property="field(moneyBoxFromResource)" name="form" styleId="moneyBoxFromResource" onchange="fillCurrency();">
                    <c:forEach items="${form.moneyBoxChargeOffResources}" var="resource">
                        <option value="${resource.code}" <c:if test="${resource.code == form.fields['moneyBoxFromResource']}">selected</c:if>>
                            ${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencyName(resource.currency)}
                        </option>
                    </c:forEach>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">Тип пополнения:</tiles:put>
            <tiles:put name="data">
                <html:select property="field(moneyBoxSumType)" name="form" styleId="moneyBoxSumType" onchange="changeEventType();">
                    <html:option value="FIXED_SUMMA">
                        Фиксированная сумма
                    </html:option>
                    <html:option value="PERCENT_BY_ANY_RECEIPT">
                        Процент от зачислений
                    </html:option>
                    <html:option value="PERCENT_BY_DEBIT">
                        Процент от расходов
                    </html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <div id="FIXED_SUMMA_DIV">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">Переводить:</tiles:put>
                <tiles:put name="data">
                    <html:select property="field(longOfferEventType)" name="form" styleId="longOfferEventType" onchange="updateFixedSummaDescription(); updateMoneyBoxName();">
                        <html:option value="ONCE_IN_WEEK">раз в неделю</html:option>
                        <html:option value="ONCE_IN_MONTH">раз в месяц</html:option>
                        <html:option value="ONCE_IN_QUARTER">раз в квартал</html:option>
                        <html:option value="ONCE_IN_YEAR">раз в год</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">Дата ближайшего перевода:</tiles:put>
                <tiles:put name="data">
                    <input type="text" name="field(longOfferStartDate)" class="date-pick dp-applied" size="10"
                           value="<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(targetPlanedDate)" format="dd/MM/yyyy"/>" id="longOfferStartDate"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"> </tiles:put>
                <tiles:put name="data">
                    Перевод будет осуществляться
                    <div id="awaysPeriodicDescription" style="font-weight: bold;"></div>
                </tiles:put>
            </tiles:insert>
        </div>

        <div id="BY_PERCENT_DIV">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">% от суммы:</tiles:put>
                <tiles:put name="data">
                    <html:text name="form" property="field(moneyBoxPercent)" maxlength="19" styleId="percent" onchange="updateMoneyBoxName();" onkeyup="updateMoneyBoxName();"/>
                </tiles:put>
            </tiles:insert>
        </div>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="clazz" value="moneyBoxSumRow"/>
            <tiles:put name="title"><span id="moneyBoxSumLabel">Сумма пополнения:</span></tiles:put>
            <tiles:put name="data">
                <html:text name="form" property="field(moneyBoxSellAmount)" styleClass="moneyField" maxlength="19" styleId="moneyBoxSellAmount"/>
                <div id="moneyBoxSellAmountCurrency" style="display: inline;"></div>
            </tiles:put>
        </tiles:insert>
    </div>
    <script type="text/javascript">
        var currencies = [];
        var cardMBConnectArray = [];

        <c:forEach items="${form.moneyBoxChargeOffResources}" var="resource">
            currencies['${resource.code}'] = '${resource.currency.code}';
            cardMBConnectArray['${resource.code}'] = '${form.moneyBoxChargeOffResourcesMBConnect[resource.code]}';
        </c:forEach>

        <c:forEach var="entry" items="${phiz:getCurrencySignMap()}">
            currencySignMap.map['${entry.key}'] = '${entry.value}';
        </c:forEach>

        var clientNameFlag = <c:choose><c:when test="${not empty form.fields['moneyBoxName']}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;

        function setClientNameFlag()
        {
            clientNameFlag = true;
        }

        function isEmpty(value)
        {
            return value == null || value == "";

        }

        var cardHasNoConnectToMBMessage = "Вы не сможете получать SMS-сообщения об операциях по данной копилке, потому что к карте списания не подключена услуга «Мобильный банк». Для получения SMS-сообщений подключите услугу «Мобильный банк» через Контактный центр Сбербанка по телефону 8-800-500-55-50. \n Для завершения подключения копилки нажмите на кнопку «Подключить».";

        function hideOrShowMBConnectionMessage()
        {
            removeMessage(cardHasNoConnectToMBMessage);
            var fromResourceValue = getElementValue("field(moneyBoxFromResource)");
            if(cardMBConnectArray[fromResourceValue] == 'false')
                addMessage(cardHasNoConnectToMBMessage, null, true);
        }

        function fillCurrency()
        {
            var fromResourceValue = getElementValue("field(moneyBoxFromResource)");
            if(fromResourceValue != "")
            {
                document.getElementById('moneyBoxSellAmountCurrency').innerHTML = currencySignMap.get(currencies[fromResourceValue]);
                hideOrShowMBConnectionMessage();
            }
        }

        function showEditProductName()
        {
            $("#MBNameText").hide();
            $("#MBNameEdit").show();
            $("#moneyBoxName")[0].selectionStart = $("#moneyBoxName")[0].selectionEnd = $("#moneyBoxName").val().length;
        }

        function changeEventType()
        {
            var eventTypeValue = document.getElementById('moneyBoxSumType').value;
            var byPercentDiv = document.getElementById('BY_PERCENT_DIV');
            var fixedSummaDiv = document.getElementById('FIXED_SUMMA_DIV');
            if(eventTypeValue == 'FIXED_SUMMA')
            {
                hideOrShow(fixedSummaDiv, false);
                hideOrShow(byPercentDiv, true);
                updateFixedSummaDescription();
                $("#moneyBoxSumLabel").text('Сумма пополнения:');
                changeDescriptionAmountField('');
            }
            else
            {
                hideOrShow(byPercentDiv, false);
                hideOrShow(fixedSummaDiv, true);
                $("#moneyBoxSumLabel").text('Максимальная сумма:');
                changeDescriptionAmountField('Если рассчитанная сумма для пополнения превысит значение, указанное в данном поле, Банк направит вам уведомление с предложением перевести большую сумму. Вы сможете подтвердить пополнение на большую (аналогично) сумму направив ответное сообщение, либо проигнорировать его - тогда пополнение произойдет на ранее указанную вами максимальную сумму');
            }
            updateMoneyBoxName();
        }

        function changeDescriptionAmountField(text)
        {
            $(".moneyBoxSumRow>div.form-row>div").each(function(index) {
            if(this.className == 'paymentValue')
            {
                for(var i=0; i<this.children.length; i++)
                {
                    if(this.children[i].className == 'description')
                    {
                        this.children[i].innerHTML = text;
                        break;
                    }
                }
            }
          });
        }

        var daysOfWeekDesc = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];
        var monthOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля",   "августа", "сентября", "октября","ноября", "декабря"];

        function updateFixedSummaDescription()
        {
            var descriptionElement = document.getElementById('awaysPeriodicDescription');
            var eventTypeValue = document.getElementById('moneyBoxSumType').value;
            var startDateValue = Str2Date(document.getElementById('longOfferStartDate').value);
            if(eventTypeValue == 'FIXED_SUMMA')
            {
                var periodicValue = document.getElementById('longOfferEventType').value;
                var description = "";
                if(periodicValue == 'ONCE_IN_WEEK')
                {
                    description = "Раз в неделю, по " + daysOfWeekDesc[(startDateValue.getDay()+6)%7];
                }
                else if(periodicValue == 'ONCE_IN_MONTH')
                {
                    description = "Раз в месяц, " + startDateValue.getDate() + "-го числа";
                }
                else if(periodicValue == 'ONCE_IN_QUARTER')
                {
                    description = "Раз в квартал, " + startDateValue.getDate() + "-го числа " + (startDateValue.getMonth()%3 + 1) + "-го месяца";
                }
                else if(periodicValue == 'ONCE_IN_YEAR')
                {
                    description = "Раз в год, " + startDateValue.getDate()+"-го " + monthOfYearDesc[startDateValue.getMonth()];
                }
                descriptionElement.innerHTML = description;
            }
        }

        var periodicDesc = ["Еженедельное", "Ежемесячное", "Ежеквартальное", "Ежегодное"];

        function updateMoneyBoxName()
        {
            if(clientNameFlag)
                return;
            var eventTypeValue = document.getElementById('moneyBoxSumType').value;
            if(eventTypeValue == 'FIXED_SUMMA')
            {
                var periodicValue = document.getElementById('longOfferEventType').value;
                var name = "";
                if(periodicValue == 'ONCE_IN_WEEK')
                {
                    name = periodicDesc[0]+" пополнение";
                }
                else if(periodicValue == 'ONCE_IN_MONTH')
                {
                    name = periodicDesc[1]+" пополнение";
                }
                else if(periodicValue == 'ONCE_IN_QUARTER')
                {
                    name = periodicDesc[2]+" пополнение";
                }
                else if(periodicValue == 'ONCE_IN_YEAR')
                {
                    name = periodicDesc[3]+" пополнение";
                }
            }
            else if(eventTypeValue == 'PERCENT_BY_ANY_RECEIPT')
            {
                var percentValue = document.getElementById('percent').value;
                name = percentValue + "% от поступлений"
            }
            else
            {
                var percentValue = document.getElementById('percent').value;
                name = percentValue + "% от расходов"
            }
            $('.moneyBoxNameTitle').html(name);
            document.getElementsByName('field(moneyBoxName)')[0].value = name;
        }

        function hideOrShowMoneyBoxFields()
        {
            hideOrShow('moneyBoxFieldsDiv', !document.getElementById('createMoneyBox').checked);
        }

        doOnLoad(function()
        {
            $("#createMoneyBox").change(hideOrShowMoneyBoxFields);
            changeEventType();
            fillCurrency();
            hideOrShowMoneyBoxFields();
        });
    </script>
</c:if>