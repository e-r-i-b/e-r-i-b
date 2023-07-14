<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 22.08.2006
  Time: 11:08:34
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
        <tiles:put name="data" type="string">
            <div id="reference">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="title" value="Справочник типов налогового платежа"/>
            <tiles:put name="data">
            <script type="text/javascript">

                $(document).ready(function(){
                    initReferenceSize();
                });

                document.imgPath="${imagePath}/";
                function sendTaxTypeData (event)
                {
                    var id = 0;
                    var el = null;
                    preventDefault(event);
                    while(document.getElementById("selectedId"+id))
                    {
                        el = document.getElementById("selectedId"+id);
                        if (el.checked)
                        {
                            var r=el.parentNode.parentNode;
                            var a=new Array(3);
                            a['taxType']=trim(r.cells[1].innerHTML);
                            window.opener.setTaxType(a);
                            window.close();
                            return true;
                        }
                        id=id+1;
                    }
                    alert("Выберите тип.");
                    return false;
                }
            </script>
            <tiles:insert definition="formHeader" flush="false">
                <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                <tiles:put name="alt" value="Список типов платежа"/>
                <tiles:put name="description">
                    Отметьте интересующий Вас тип платежа в списке и нажмите на кнопку "Выбрать".
                </tiles:put>
            </tiles:insert>
			 <div class="payment-templates">
                <table class="dictionary">
                <tr>
                    <th>&nbsp;</th>
                    <th align="left">Код</th>
                    <th align="center">Описание</th>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId0" value="0" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">0</td>
                    <td align=center class="ListItem">На усмотрение налоговых органов</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId1" value="1" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">НС</td>
                    <td align=center class="ListItem">Уплата налога или сбора</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId2" value="2" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ПЛ</td>
                    <td align=center class="ListItem">Уплата платежа</td>
                <tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId3" value="3" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ГП</td>
                    <td align=center class="ListItem">Уплата пошлины</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId4" value="4" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ВЗ</td>
                    <td align=center class="ListItem">Уплата взноса</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId5" value="5" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">АВ</td>
                    <td align=center class="ListItem">Уплата аванса или предоплата</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId6" value="6" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ПЕ</td>
                    <td align=center class="ListItem">Уплата пени</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId7" value="7" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ПЦ</td>
                    <td align=center class="ListItem">Уплата процентов</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId8" value="8" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">СА</td>
                    <td align=center class="ListItem">Налоговые санкции, установленные Налоговым кодексом Российской Федерации</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId9" value="9" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">АШ</td>
                    <td align=center class="ListItem">Административные штрафы</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxTypeData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId10" value="10" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">ИШ</td>
                    <td align=center class="ListItem">Иные штрафы, установленные соответствующими законодательными или иными нормативными актами</td>
                </tr>
            </table>
            <div class="clear"></div>&nbsp;
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
                   <tiles:put name="onclick"        value="sendTaxTypeData(event);"/>
                </tiles:insert>
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</div>
</tiles:put>
</tiles:insert>