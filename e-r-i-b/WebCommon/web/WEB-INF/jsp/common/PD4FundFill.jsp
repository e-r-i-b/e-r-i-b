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
        <tiles:put name="title" value="���������� ��������� ���������� �������"/>
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
                    alert("�������� ���������.");
                    return false;
                }
            </script>
            <tiles:insert definition="formHeader" flush="false">
                <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                <tiles:put name="alt" value="������ ��������� �������"/>
                <tiles:put name="description">
                    �������� ������������ ��� ��������� ������� � ������ � ������� �� ������ "�������".
                </tiles:put>
            </tiles:insert>
            <div class="payment-templates">
                <table class="dictionary">
                <tr>
                    <th>&nbsp;</th>
                    <th align="left">���</th>
                    <th align="center">��������</th>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId1" value="1" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">0</td>
                    <td align=center class="ListItem">�� ���������� ��������� �������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId2" value="2" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">������� �������� ����</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId3" value="3" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">������������ ��������� ������������� �� �������� ��������� �������� ��� ���������� ���������� �� ������ ������� (������) �� ���������� ������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId4" value="4" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">������� ������� ���������� ��� � �������� ����� (���������� �����), ������������ �� ������ ����������� �����</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId5" value="5" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ������������� �� ���������� ���������� ������ �� ������ ������� (������)</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId6" value="6" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ����������� �������������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId7" value="7" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ����������������� �������������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId8" value="8" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ����������� ������������� � ����� � ��������� �������� ����������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId9" value="9" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� �������������, ���������������� � ���������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId10" value="10" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ������������� �� ���� ��������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId11" value="11" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ������������� �� ��������������� ���������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId12" value="12" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">���������� ����������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId13" value="13" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">���������� ��������� �����</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId14" value="14" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">�������������-��������� (��� ������ ������)</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId15" value="15" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">����� ������������� ���������� ��������� � ���������� ��������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId16" value="16" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">�������������� ��������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId17" value="17" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">���������� ���������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId18" value="18" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">���������� �� ������ ���������� ��������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId19" value="19" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">��������� ������������� ����� ���������� �������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId20" value="20" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">�������� ����������</td>
                </tr>
                <tr onclick="selectRow(this);" ondblclick="sendTaxFundData();">
                    <td align=center class="ListItem">
                        <input type="radio" name="selectedId" id="selectedId21" value="21" style="border:none"/>
                    </td>
                    <td align=center class="ListItem">��</td>
                    <td align=center class="ListItem">���������� � �������������� ��� ������ �������� ������������� ��������� �������� � ���������������� �������</td>
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