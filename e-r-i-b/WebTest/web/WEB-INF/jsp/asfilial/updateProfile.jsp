<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:html>
<jsp:include page="addPhone.jsp"/>
        ready(function()
        {
            addPhoneField(query("#addphone"),"phoneNumber","mobilePhoneOperator","phoneConfirmHolderCode","phoneAndCode","last");
        });
    });
</script>
<script type="text/javascript">
    function hideTable(el, tableId)
    {
        var table = document.getElementById(tableId);
        if (el.checked === true)
            table.style.display = 'block';
        else
            table.style.display = 'none';

    }

    window.onload = function()
    {
        hideTable(document.getElementsByName('mobileBankService')[0], 'mobileBank');
        hideTable(document.getElementsByName('mobileBankServiceParams')[0], 'mobileBankServiceParamsTable');
        hideTable(document.getElementsByName('clientDataChange')[0], 'clientDataTab');
        hideTable(document.getElementsByName('internetClientServiceChange')[0], 'internetClientServiceTab');
        hideTable(document.getElementsByName('mobileClientServiceChange')[0], 'mobileClientServiceTab');
        hideTable(document.getElementsByName('ATMClientServiceChange')[0], 'ATMClientServiceTab');
        hideTable(document.getElementsByName('informPeriodChange')[0], 'informPeriodTable');
    }
</script>
<head><title>������������ �������������� ���� � �� ������-��������</title></head>
<body>
<h1>���������� ������� �������</h1>

<html:form action="/asfilial" show="true">
<html:hidden property="ASListenerUrl"/>
<table>
<tr>
    <td>
        ����������������� ������ �������([1])
    </td>
    <td>
        <jsp:include page="clientIdentity.jsp"/>
    </td>
</tr>
<tr>
    <td>���������� � �������������, � ������ �������� ����������� ������[1]</td>
    <td>
        <jsp:include page="bankInfo.jsp"/>
    </td>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        ������ �� �������([0-1]
        0 � ������ �� ������� �� ��������)
        <html:checkbox property="clientDataChange" onchange="hideTable(this,'clientDataTab')"/>
    </td>
</tr>
<tr>
    <table id="clientDataTab" style="background:green;">
        <tr>
            <td>
                <a id="addphone" href="#">�������� �����</a>
            </td>
        </tr>
        <tr>
            <td>��� �������� �������([0-n]).</td>
            <td>
                ����� ��������/��������� ��������/��� �������������
                <ul id="phoneAndCode"></ul>
            </td>
        </tr>
    </table>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        ������ �� ������ ���������-������([0-1] 0 � ������ �� ������� �� ��������)
        <html:checkbox property="internetClientServiceChange"
                       onchange="hideTable(this,'internetClientServiceTab')"/>
    </td>
</tr>
<tr>
    <table id="internetClientServiceTab" style="background:green;">
        <tr>
            <td>
                ��������, ��������� � ��������-���������� ([0-n])(��������: card:�����|account:..|loan:..)
            </td>
            <td><html:text property="internetVisibleResources" maxlength="254" size="40" value=""/></td>
        </tr>
    </table>

</tr>
<tr>
    <td colspan="2">
        <hr/>
        ������ �� ������ ���������� ������([0-1] 0 � ������ �� ������� �� ��������)
        <html:checkbox property="mobileClientServiceChange"
                       onchange="hideTable(this,'mobileClientServiceTab')"/>
    </td>
</tr>
<tr>
    <table id="mobileClientServiceTab" style="background:green;">
        <tr>
            <td>
                ��������, ��������� � ��������� ���������� ([0-n])(��������:
                card:�����|account:..|loan:..)
            </td>
            <td><html:text property="mobileVisibleResources" maxlength="254" size="40" value=""/></td>
        </tr>
    </table>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        ������ �� ������ ����������� �����������������([0-1] 0 � ������ �� ������� �� ��������)
        <html:checkbox property="ATMClientServiceChange" onchange="hideTable(this,'ATMClientServiceTab')"/>
    </td>
</tr>
<tr>
    <table id="ATMClientServiceTab" style="background:green;">
        <tr>
            <td>
                ��������, ��������� � ATM([0-n])(��������: card:�����|account:..|loan:..)
            </td>
            <td><html:text property="ATMVisibleResources" maxlength="254" size="40" value=""/></td>
        </tr>
    </table>
</tr>
<tr>
    <td colspan="2">
        <hr/>
        ������ �� ������ ���������� ����([0-1]0
        - ������ �� ������ �� ��������)
        <html:checkbox property="mobileBankService" onchange="hideTable(this,'mobileBank');"/>
    </td>
</tr>
<tr>
    <td>
        <table id="mobileBank" style="background:green;">
            <tr>
                <td>
                    <hr/>
                    ������ ������� ����������.
                    <html:checkbox property="registrationStatus"/>
                </td>
            </tr>
            <tr>
                <td>
                    <hr/>
                    ��������� ������([0-1]0 , ���� ������ ���������)
                    <html:checkbox property="mobileBankServiceParams"
                                   onchange="hideTable(this,'mobileBankServiceParamsTable');"/>
                </td>
            <tr>
                <td>
                    <table id="mobileBankServiceParamsTable" style="background:gold;">
                        <tr>
                            <td>
                                <hr/>
                                �������� ���� full � ������ saving - ���������
                                <html:select property="tariffId">
                                    <html:option value="full"/>
                                    <html:option value="saving"/>
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                ������� ��������� ��� ������� ����������� ������ ������ �������� �
                                ��������� �� ������ ��������([1])
                                <html:checkbox property="quickServices"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                ������ ������� �������� ���������. �� ���� ����� ������������
                                ����������� ������ � ����������� ��. � ���� �� ����� ���������
                                ������� �� �������.
                                ������ ������� � ����� ������ ��������� �������
                                ([0-1])
                                �����:
                                <html:text property="activePhoneNumber" maxlength="11" size="11"/>
                                ��������� ��������:
                                <html:text property="activeMobilePhoneOperator" maxlength="100" size="10"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                ��������, ��������� � ���-������.
                                ������ ������� � �������� ������ ��������� �� ������� [0-n]
                                <html:text property="visibleResources"  maxlength="254" size="40" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                �������� �������, �� ������� ������ ������������ ����������.������
                                ������� � �������� ������ ��������� �� �������([0-n])
                                <html:text property="informResources" maxlength="254" size="40"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                ����� ������������ ����� ��� �������� ����������� ����� ([1])
                                <html:text property="chargeOffCard" maxlength="19" size="19"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                ������ ����������� ����������� �� ����� ������������
                                ��������([1])
                                <html:checkbox property="informNewResource"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <hr/>
                                ��������� ���������, � ������� ��������� ���������� �����������([0-1])
                                <html:checkbox property="informPeriodChange"
                                               onchange="hideTable(this,'informPeriodTable');"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <table id="informPeriodTable" style="background:red;">
                                    <tr>
                                        <td>������ ����������(HH:MM):
                                            c ([1])
                                            <html:text property="ntfStartTimeString" maxlength="5" size="5"/>
                                            �� ([1])
                                            <html:text property="ntfEndTimeString" maxlength="5" size="5"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>������� ����([1]):
                                            <html:select property="timeZone">
                                                <option value="120">
                                                    ��������������� ����� (MSK -01:00)
                                                </option>
                                                <option value="180">
                                                    ���������� ����� (MSK)
                                                </option>
                                                <option value="240">
                                                    ��������� ����� (MSK +01:00)
                                                </option>
                                                <option value="300">
                                                    ���������������� ����� (MSK +02:00)
                                                </option>
                                                <option value="360">
                                                    ������ �����  (MSK +03:00)
                                                </option>
                                                <option value="420">
                                                    ������������ ����� (MSK +04:00)
                                                </option>
                                                <option value="480">
                                                    ��������� ����� (MSK +05:00)
                                                </option>
                                                <option value="540">
                                                    �������� ����� (MSK +06:00)
                                                </option>
                                                <option value="600">
                                                    ��������������� ����� (MSK +07:00)
                                                </option>
                                                <option value="660">
                                                    ��������������� ����� (MSK +08:00)
                                                </option>
                                                <option value="720">
                                                    ���������� ����� (MSK +09:00)
                                                </option>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> ([0-7])
                                            <html:multibox property="ntfDays" value="MON"/> ��.
                                            <html:multibox property="ntfDays" value="TUE"/> ��.
                                            <html:multibox property="ntfDays" value="WED"/> ��.
                                            <html:multibox property="ntfDays" value="THU"/> ��.
                                            <html:multibox property="ntfDays" value="FRI"/> ��.
                                            <html:multibox property="ntfDays" value="SAT"/> ��.
                                            <html:multibox property="ntfDays" value="SUN"/> ��.
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>


                        <tr>
                            <td>
                                <hr/>
                                ������� ������� ��������� ��������([1])
                                <html:checkbox property="suppressAdvertising"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </td>
</tr>
</table>

<html:submit property="operation" value="UpdateProfile"/>
<html:submit property="operation" value="Back"/>
</html:form>
</body>
</html:html>