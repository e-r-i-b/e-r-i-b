<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 22.08.2006
  Time: 10:41:47
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
        <tiles:put name="title" value="Справочник статусов составителя"/>
        <tiles:put name="data">
            <script type="text/javascript">
                document.imgPath="${imagePath}/";
                function sendTaxStatusData (event)
                {
                    var el = null;
                    preventDefault(event);
                    for(var i=1; i<=document.getElementsByName("selectedId").length; i++)
                    {
                        el = document.getElementById("selectedId"+i);
                        if (el.checked)
                        {
                            var r=el.parentNode.parentNode;
                            var a=new Array(3);
                            a['taxStatus']=trim(r.cells[1].innerHTML);
                            window.opener.setTaxStatus(a);
                            window.close();
                            return true;
                        }
                    }
                    alert("Выберите статус.");
                    return false;
                }

                $(document).ready(function()
                {
                    initReferenceSize();
                });


            </script>
            <div class="payment-templates">
                <tiles:insert definition="formHeader" flush="false">
                    <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                    <tiles:put name="alt" value="Список статусов"/>
                    <tiles:put name="description">
                        Отметьте интересующий Вас статус в списке и нажмите на кнопку "Выбрать".
                    </tiles:put>
                </tiles:insert>
                <div class="clear"></div>
                <table class="dictionary">
                    <tr>
                        <th>&nbsp;</th>
                        <th align="left">Код</th>
                        <th align="center">Описание</th>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId1" value="02" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">02</td>
                        <td align=center class="ListItem">налоговый агент</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId2" value="09" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">09</td>
                        <td align=center class="ListItem">индивидуальный предприниматель</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId3" value="10" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">10</td>
                        <td align=center class="ListItem">нотариус, занимающийся частной практикой</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId4" value="11" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">11</td>
                        <td align=center class="ListItem">адвокат, учредивший адвокатский кабинет</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId5" value="12" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">12</td>
                        <td align=center class="ListItem">глава крестьянского (фермерского) хозяйства</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId6" value="13" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">13</td>
                        <td align=center class="ListItem">иное физическое лицо</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId7" value="14" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">14</td>
                        <td align=center class="ListItem">физическое лицо - плательщик отмененного единого социального налога и страховых взносов, производящий выплаты физическим лицам</td>
                    </tr>
                    <tr onclick="selectRow(this);" ondblclick="sendTaxStatusData();">
                        <td align=center class="ListItem">
                            <input type="radio" name="selectedId" id="selectedId8" value="16" style="border:none"/>
                        </td>
                        <td align=center class="ListItem">16</td>
                        <td align=center class="ListItem">участник внешнеэкономической деятельности - физическое лицо</td>
                    </tr>
                </table>
                </div>
            <div class="clear"></div>
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
                   <tiles:put name="onclick"        value="sendTaxStatusData(event);"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</div>
    </tiles:put>
</tiles:insert>