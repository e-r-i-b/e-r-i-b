<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 22.08.2006
  Time: 11:19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="empty">
<tiles:put name="data">
<div id="reference">
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="title" value="Справочник оснований налогового платежа"/>
        <tiles:put name="data">
            <script type="text/javascript">

                $(document).ready(function(){
                    initReferenceSize();
                });

                document.imgPath="${imagePath}/";
                function sendTaxFundData (event)
                {
                    var id = 1;
                    var el = null;
                    preventDefault(event);
                    while(document.getElementById("selectedId"+id))
                    {
                        el = document.getElementById("selectedId"+id);
                        if (el.checked)
                        {
                            var r=el.parentNode.parentNode;
                            var a=new Array(3);
                            a['taxFund']=trim(r.cells[1].innerHTML);
                            window.opener.setTaxFund(a);
                            window.close();
                            return true;
                        }
                        id=id+1;
                    }
                    alert("Выберите основание.");
                    return false;
                }
            </script>
            <tiles:insert definition="formHeader" flush="false">
                <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                <tiles:put name="alt" value="Список оснований платежа"/>
                <tiles:put name="description">
                    Отметьте интересующее Вас основание платежа в списке и нажмите на кнопку "Выбрать".
                </tiles:put>
            </tiles:insert>
            <div class="payment-templates">
                <table class="dictionary">
                <tr>
                    <th>&nbsp;</th>
                    <th align="left">Код</th>
                    <th align="center">Описание</th>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId1" value="1" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">0</td>
                    <td align=center class="ListItem">На усмотрение налоговых органов</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId2" value="2" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ТП</td>
                    <td align=center class="ListItem">Платежи текущего года</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId3" value="3" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ЗД</td>
                    <td align=center class="ListItem">Добровольное погашение задолженности по истекшим налоговым периодам при отсутствии требования об уплате налогов (сборов) от налогового органа</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId4" value="4" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">БФ</td>
                    <td align=center class="ListItem">Текущие платежи физических лиц – клиентов банка (владельцев счета), уплачиваемые со своего банковского счета</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId5" value="5" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ТР</td>
                    <td align=center class="ListItem">Погашение задолженности по требованию налогового органа об уплате налогов (сборов)</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId6" value="6" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ОТ</td>
                    <td align=center class="ListItem">Погашение отсроченной задолженности</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId7" value="7" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">РТ</td>
                    <td align=center class="ListItem">Погашение реструктурируемой задолженности</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId8" value="8" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ВУ</td>
                    <td align=center class="ListItem">Погашение отсроченной задолженности в связи с введением внешнего управления</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId9" value="9" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ПР</td>
                    <td align=center class="ListItem">Погашение задолженности, приостановленной к взысканию</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId10" value="10" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">АП</td>
                    <td align=center class="ListItem">Погашение задолженности по акту проверки</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId11" value="11" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">АР</td>
                    <td align=center class="ListItem">Погашение задолженности по исполнительному документу</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId12" value="12" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ДЕ</td>
                    <td align=center class="ListItem">Таможенная декларация</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId13" value="13" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ПО</td>
                    <td align=center class="ListItem">Таможенный приходный ордер</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId14" value="14" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">КВ</td>
                    <td align=center class="ListItem">Постановление-квитанция (при уплате штрафа)</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId15" value="15" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">КТ</td>
                    <td align=center class="ListItem">Форма корректировки таможенной стоимости и таможенных платежей</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId16" value="16" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ИД</td>
                    <td align=center class="ListItem">Исполнительный документ</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId17" value="17" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ИП</td>
                    <td align=center class="ListItem">Инкассовое поручение</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId18" value="18" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ТУ</td>
                    <td align=center class="ListItem">Требование об уплате таможенных платежей</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId19" value="19" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">БД</td>
                    <td align=center class="ListItem">Документы бухгалтерских служб таможенных органов</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId20" value="20" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ИН</td>
                    <td align=center class="ListItem">Документ инкассации</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId21" value="21" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">КП</td>
                    <td align=center class="ListItem">Соглашение о взаимодействии при уплате крупными плательщиками суммарных платежей в централизованном порядке</td>
                </tr>
            </table><div class="clear"></div>&nbsp;
            </div>
            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                   <tiles:put name="commandTextKey" value="button.cancel"/>
                   <tiles:put name="commandHelpKey" value="button.cancel"/>
                   <tiles:put name="bundle"         value="dictionaryBundle"/>
                   <tiles:put name="viewType"       value="buttonGrey"/>
                   <tiles:put name="onclick"        value="window.close();"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                   <tiles:put name="commandTextKey" value="button.choose"/>
                   <tiles:put name="commandHelpKey" value="button.choose"/>
                   <tiles:put name="bundle"         value="dictionaryBundle"/>
                   <tiles:put name="onclick"        value="sendTaxFundData(event);"/>
                </tiles:insert>
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</div>
</tiles:put>
</tiles:insert>