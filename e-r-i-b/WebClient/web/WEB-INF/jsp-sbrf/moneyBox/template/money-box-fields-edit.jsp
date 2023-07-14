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
            <div id="moneyBoxTitle" style="font-size:16px;font-weight:bold;">������� <input type="checkbox" name="field(createMoneyBox)" id="createMoneyBox" value="true"
                 <c:if test="${createMoneyBoxValue}">checked="true"</c:if>/>
            </div>
        </tiles:put>
    </tiles:insert>
    <div id="moneyBoxFieldsDiv" style="display: none;">
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">��������:</tiles:put>
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
            <tiles:put name="title">����� ��������:</tiles:put>
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
            <tiles:put name="title">��� ����������:</tiles:put>
            <tiles:put name="data">
                <html:select property="field(moneyBoxSumType)" name="form" styleId="moneyBoxSumType" onchange="changeEventType();">
                    <html:option value="FIXED_SUMMA">
                        ������������� �����
                    </html:option>
                    <html:option value="PERCENT_BY_ANY_RECEIPT">
                        ������� �� ����������
                    </html:option>
                    <html:option value="PERCENT_BY_DEBIT">
                        ������� �� ��������
                    </html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <div id="FIXED_SUMMA_DIV">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">����������:</tiles:put>
                <tiles:put name="data">
                    <html:select property="field(longOfferEventType)" name="form" styleId="longOfferEventType" onchange="updateFixedSummaDescription(); updateMoneyBoxName();">
                        <html:option value="ONCE_IN_WEEK">��� � ������</html:option>
                        <html:option value="ONCE_IN_MONTH">��� � �����</html:option>
                        <html:option value="ONCE_IN_QUARTER">��� � �������</html:option>
                        <html:option value="ONCE_IN_YEAR">��� � ���</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">���� ���������� ��������:</tiles:put>
                <tiles:put name="data">
                    <input type="text" name="field(longOfferStartDate)" class="date-pick dp-applied" size="10"
                           value="<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(targetPlanedDate)" format="dd/MM/yyyy"/>" id="longOfferStartDate"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"> </tiles:put>
                <tiles:put name="data">
                    ������� ����� ��������������
                    <div id="awaysPeriodicDescription" style="font-weight: bold;"></div>
                </tiles:put>
            </tiles:insert>
        </div>

        <div id="BY_PERCENT_DIV">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">% �� �����:</tiles:put>
                <tiles:put name="data">
                    <html:text name="form" property="field(moneyBoxPercent)" maxlength="19" styleId="percent" onchange="updateMoneyBoxName();" onkeyup="updateMoneyBoxName();"/>
                </tiles:put>
            </tiles:insert>
        </div>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="clazz" value="moneyBoxSumRow"/>
            <tiles:put name="title"><span id="moneyBoxSumLabel">����� ����������:</span></tiles:put>
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

        var cardHasNoConnectToMBMessage = "�� �� ������� �������� SMS-��������� �� ��������� �� ������ �������, ������ ��� � ����� �������� �� ���������� ������ ���������� ����. ��� ��������� SMS-��������� ���������� ������ ���������� ���� ����� ���������� ����� ��������� �� �������� 8-800-500-55-50. \n ��� ���������� ����������� ������� ������� �� ������ ������������.";

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
                $("#moneyBoxSumLabel").text('����� ����������:');
                changeDescriptionAmountField('');
            }
            else
            {
                hideOrShow(byPercentDiv, false);
                hideOrShow(fixedSummaDiv, true);
                $("#moneyBoxSumLabel").text('������������ �����:');
                changeDescriptionAmountField('���� ������������ ����� ��� ���������� �������� ��������, ��������� � ������ ����, ���� �������� ��� ����������� � ������������ ��������� ������� �����. �� ������� ����������� ���������� �� ������� (����������) ����� �������� �������� ���������, ���� ��������������� ��� - ����� ���������� ���������� �� ����� ��������� ���� ������������ �����');
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

        var daysOfWeekDesc = ["�������������", "���������", "������", "���������", "��������", "��������", "������������"];
        var monthOfYearDesc = ["������", "�������", "�����", "������", "���", "����", "����",   "�������", "��������", "�������","������", "�������"];

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
                    description = "��� � ������, �� " + daysOfWeekDesc[(startDateValue.getDay()+6)%7];
                }
                else if(periodicValue == 'ONCE_IN_MONTH')
                {
                    description = "��� � �����, " + startDateValue.getDate() + "-�� �����";
                }
                else if(periodicValue == 'ONCE_IN_QUARTER')
                {
                    description = "��� � �������, " + startDateValue.getDate() + "-�� ����� " + (startDateValue.getMonth()%3 + 1) + "-�� ������";
                }
                else if(periodicValue == 'ONCE_IN_YEAR')
                {
                    description = "��� � ���, " + startDateValue.getDate()+"-�� " + monthOfYearDesc[startDateValue.getMonth()];
                }
                descriptionElement.innerHTML = description;
            }
        }

        var periodicDesc = ["������������", "�����������", "��������������", "���������"];

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
                    name = periodicDesc[0]+" ����������";
                }
                else if(periodicValue == 'ONCE_IN_MONTH')
                {
                    name = periodicDesc[1]+" ����������";
                }
                else if(periodicValue == 'ONCE_IN_QUARTER')
                {
                    name = periodicDesc[2]+" ����������";
                }
                else if(periodicValue == 'ONCE_IN_YEAR')
                {
                    name = periodicDesc[3]+" ����������";
                }
            }
            else if(eventTypeValue == 'PERCENT_BY_ANY_RECEIPT')
            {
                var percentValue = document.getElementById('percent').value;
                name = percentValue + "% �� �����������"
            }
            else
            {
                var percentValue = document.getElementById('percent').value;
                name = percentValue + "% �� ��������"
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