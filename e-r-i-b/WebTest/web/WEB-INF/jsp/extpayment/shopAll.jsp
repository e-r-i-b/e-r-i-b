<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="shopAction" value="${phiz:calculateActionURL(pageContext, '/shop')}"/>
<c:set var="sendAirlineTicketsAction" value="${phiz:calculateActionURL(pageContext, '/airlineTicketsInfo')}"/>
<c:set var="goodsReturnAction" value="${phiz:calculateActionURL(pageContext, '/goodsReturn')}"/>
<c:set var="docRollbackAction" value="${phiz:calculateActionURL(pageContext, '/docRollback')}"/>
<c:set var="clientCheckAction" value="${phiz:calculateActionURL(pageContext, '/clientCheck')}"/>

<html>
<head><title>������������ ������� � ������ ����, �������, �������� ��� ���</title></head>
<script type="text/javascript">
    function getRadioValue(radio)
    {
        if (radio.length > 0)
        {
            for (var i = 0; i < radio.length; i++)  if (radio[i].checked) return radio[i].value;
            return null;
        }
        else  return (radio.checked ? radio.value : null);
    }

    function runShop()
    {
        var provider = document.getElementsByName("ProviderName")[0].value;
        var isPartial = getRadioValue(document.getElementsByName("IsPartial"));
        var backUrl = document.getElementsByName("BackUrl")[0].value;
        var mobilePhone = document.getElementsByName("mobilePhone")[0].value;
        var eShopIdBySP = document.getElementsByName("eShopIdBySPDocRegRq")[0].value;
        var eShopURLDocRegRq = document.getElementsByName("eShopURLDocRegRq")[0].value;
        var mobileCheckout = document.getElementsByName("mobileCheckout")[0].checked;
        var facilitator = document.getElementsByName("facilitator")[0].checked;
        var webServiceUrl = document.getElementsByName("webServiceUrl")[0].value;
        var currency = document.getElementsByName("currency")[0].value;
        window.location = "${shopAction}"
                + "?provider=" + provider
                + "&partial=" + isPartial
                + "&backUrl=" + backUrl
                + "&mobilePhone=" + mobilePhone
                + "&mobileCheckout=" + mobileCheckout
                + "&facilitator=" + facilitator
                + "&eShopIdBySP=" + eShopIdBySP
                + "&eShopURL=" + eShopURLDocRegRq
                + "&webServiceUrl=" + webServiceUrl
                + "&currency=" + currency
                + "&systemId=" + getSystemIdValue('docReg');
    }

    function sendAirlineTicketsInfo()
    {
        var eribUUID = document.getElementsByName("eribUUID")[0].value;
        var ticketsStatusCode = getRadioValue(document.getElementsByName("ticketsStatusCode"));
        var itineraryUrl = document.getElementsByName("itineraryUrl")[0].value;
        var webServiceUrl = document.getElementsByName("webServiceUrl")[0].value;
        window.location = "${sendAirlineTicketsAction}"
                + "?eribUUID=" + eribUUID
                + "&ticketsStatusCode=" + ticketsStatusCode
                + "&itineraryUrl=" + itineraryUrl
                + "&webServiceUrl=" + webServiceUrl
                + "&systemId=" + getSystemIdValue('docFlightsInfo');
    }

    function sendGoodReturnRq()
    {
        var eribUUID = document.getElementsByName("goodsReturnEribUUID")[0].value;
        var provider = document.getElementsByName("goodsReturnProviderName")[0].value;
        var webServiceUrl = document.getElementsByName("webServiceUrl")[0].value;
        var eShopIdBySP = document.getElementsByName("eShopIdBySPGoodsReturnRq")[0].value;
        window.location = "${goodsReturnAction}" + "?eribUUID=" + eribUUID + "&provider=" + provider
                + "&webServiceUrl=" + webServiceUrl + "&eShopIdBySP=" + eShopIdBySP + "&systemId=" + getSystemIdValue('goodsReturn');
    }

    function sendDocRollbackRq()
    {
        var eribUUID = document.getElementsByName("docRollbackEribUUID")[0].value;
        var provider = document.getElementsByName("docRollbackProviderName")[0].value;
        var eShopIdBySP = document.getElementsByName("eShopIdBySPDocRollbackRq")[0].value;
        var webServiceUrl = document.getElementsByName("webServiceUrl")[0].value;
        window.location = "${docRollbackAction}" + "?eribUUID=" + eribUUID + "&provider=" + provider
                + "&webServiceUrl=" + webServiceUrl + "&eShopIdBySP=" + eShopIdBySP;
    }

    function sendClientCheckRq()
    {
        var sPName = document.getElementsByName("SPName")[0].value;
        var eShopIdBySP = document.getElementsByName("EShopIdBySP")[0].value;
        var recipientName = document.getElementsByName("RecipientName")[0].value;
        var URL = document.getElementsByName("URL")[0].value;
        var INN = document.getElementsByName("INN")[0].value;
        var phone = document.getElementsByName("Phone")[0].value;
        var webServiceUrl = document.getElementsByName("webServiceUrl")[0].value;
        window.location = "${clientCheckAction}" + "?sPName=" + sPName + "&eShopIdBySP=" + eShopIdBySP + "&recipientName=" + recipientName
                + "&URL=" + URL + "&INN=" + INN + "&phone=" + phone + "&webServiceUrl=" + webServiceUrl;
    }

//    function setText()
//    {
//        document.getElementById("webServiceUrl").value = document.getElementById("webServiceUrlSelect").value;
//    }

    function getSystemIdValue(prefix)
    {
        return document.getElementById("systemId_"+ prefix).value;
    }
</script>

  <body onload="hideFields();">
    <h1>������������ ������� � ������ ����, �������, �������� ��� ���</h1>

    <%--(*) ����� ������:--%>
    <%--<select name="webServiceUrlSelect" id="webServiceUrlSelect" onchange="setText();">--%>
        <%--<option value="http://localhost:8888/ShopProxyListener/axis-services/DocRegServicePort">MultiBlockScheme</option>--%>
        <%--<option value="http://localhost:8888/ShopListener/axis-services/DocRegServicePort">OneBlockScheme</option>--%>
    <%--</select>--%>

    web service url <input type="edit" name="webServiceUrl" id="webServiceUrl" size="150" value="http://localhost:8888/ShopProxyListener/axis-services/DocRegServicePort">

    <ul>
        <li>docReg (����������� ��������� ��� ������)
            <br>

            (*) ���������:
            <select name="ProviderName">
                <option value="OZON" selected=1>����</option>
                <option value="EVENTIM">�������</option>
                <option value="AEROFLOT">��������</option>
                <option value="RZD">���</option>
                <option value="YANDEX">������.������</option>
            </select>
            <br>

            (*) ����� ������:
            <input name="IsPartial" type="radio" value=0 checked=1 onclick="hideFields();">������
            <input name="IsPartial" type="radio" value=1 onclick="hideFields();">���������
            <input name="IsPartial" type="radio" value=2 onclick="showFields();">�������
            <br>

            <script type="text/javascript">
                function hideFields()
                {
                    document.getElementById("mobPhone").style.display = "none";
                    document.getElementById("checout").style.display = "none";
                }

                function showFields()
                {
                    document.getElementById("mobPhone").style.display = "block";
                    document.getElementById("checout").style.display = "block";
                }

                function showHideFacilitatorFields(show)
                {
                    var res = show ? "block" : "none";
                    document.getElementById("eShopIdBySPDiv").style.display = res;
                    document.getElementById("eShopURLDiv").style.display = res;
                }
            </script>

            <div id="mobPhone">
                ��������� �������: <input name="mobilePhone" type="text"/>
            </div>

            <div id="checout">
                MobileCheckout: <input name="mobileCheckout" type="checkbox"/>
            </div>

            URL ��������:
            <input name="BackUrl" type="text" value="http://habrahabr.ru/" size=50>
            <br>
            ������ <select name="currency">
                <option value="RUB">RUB</option>
                <option value="EUR">EUR</option>
                <option value="USD">USD</option>
            </select>
            <br>

            ������ ����� ������������ <input name="facilitator" type="checkbox" onclick="showHideFacilitatorFields(this.checked)"/>
            <br>

            <div id="eShopIdBySPDiv" style="display:none;">
                �������� ���: <input name="eShopIdBySPDocRegRq" type="text" />
                <br>
            </div>

            <div id="eShopURLDiv" style="display:none;">
                URL ���: <input name="eShopURLDocRegRq" type="text" value="http://yandex.ru/"/>
                <br>
            </div>

            <div>
                SystemId: <input id="systemId_docReg" type="text"/>
                <span style="font-size:12px">*��������� ��� ���� ��� �������� �������������� ����� ����</span>
            </div>


            <a href='#' onclick="runShop();">����������������</a>
            <br>
        </li>

        <br>
        <br>

        <li>DocFlightsInfoRq (���������� ���� � ���������� �����������)
           <br>
            (*) ������������� ��������� (ERIBUID):
            <input name="eribUUID" type="text" size="50">
            <br>

            (*) ������ ������� �������:
            <input name="ticketsStatusCode" type="radio" value=0 checked=1>������ ���. ������ ��������
            <input name="ticketsStatusCode" type="radio" value=-1>������ ������, ������ �� ��������
            <input name="ticketsStatusCode" type="radio" value=-2>����� ��� ��� ������� �����
            <input name="ticketsStatusCode" type="radio" value=-3>��������� ��������� ��������� ������
            <br>

            URL, �� �������� ����� �������� ������ �������-���������:
            <input name="itineraryUrl" type="text" value="http://avia.svali.ru/avia" size="50">
            <br>

            <div>
                SystemId: <input id="systemId_docFlightsInfo" type="text"/>
                <span style="font-size:12px">*��������� ��� ���� ��� �������� �������������� ����� ����</span>
            </div>

            <a href='#' onclick="sendAirlineTicketsInfo();">����������</a>
            <br>
        </li>

        <br>
        <br>

        <li>
            <a href="${phiz:calculateActionURL(pageContext,'/shopInfo')}">InfoCheck </a>
        </li>

        <br>
        <br>

        <li>GoodsReturnRq (������� ������)
            <br>
            (*) ������������� ��������� (ERIBUID):
            <input name="goodsReturnEribUUID" type="text" size="50">
            <br>
            (*) ���������:
            <select name="goodsReturnProviderName">
                <option value="OZON" selected=1>����</option>
                <option value="EVENTIM">�������</option>
                <option value="AEROFLOT">��������</option>
                <option value="RZD">���</option>
                <option value="YANDEX">������.������</option>
            </select>
            <br>

            �������� ��� (��� �������������): <input name="eShopIdBySPGoodsReturnRq" type="text" />
            <br>

            <div>
                SystemId: <input id="systemId_goodsReturn" type="text"/>
                <span style="font-size:12px">*��������� ��� ���� ��� �������� �������������� ����� ����</span>
            </div>

            <a href="#" onclick="sendGoodReturnRq();">���������</a>
        </li>

        <br>
        <br>

        <li>DocRollbackRq (������ ������)
            <br>
            (*) ������������� ��������� (ERIBUID):
            <input name="docRollbackEribUUID" type="text" size="50">
            <br>
            (*) ���������:
            <select name="docRollbackProviderName">
                <option value="OZON" selected=1>����</option>
                <option value="EVENTIM">�������</option>
                <option value="AEROFLOT">��������</option>
                <option value="RZD">���</option>
                <option value="YANDEX">������.������</option>
            </select>
            <br>

            �������� ��� (��� �������������): <input name="eShopIdBySPDocRollbackRq" type="text" />
            <br>

            <a href="#" onclick="sendDocRollbackRq();">���������</a>
        </li>

        <br>
        <br>

        <li>ClientCheckRq (�������� ������ ���������� ��������)
            <br>
            (*) ��� ����������:
            <input name="SPName" type="text" value="YANDEX" size=50>
            <br>
            (*) ��� ���:
            <input name="EShopIdBySP" type="text" value="taksi" size=50>
            <br>
            (*) �������� ���:
            <input name="RecipientName" type="text" value="�����.�����" size=50>
            <br>
            (*) ��� ���:
            <input name="URL" type="text" value="http://habrahabr.ru/" size=500>
            <br>
            (*) ��� ���:
            <input name="INN" type="text" value="123456789012" size=12>
            <br>
            (*) ����� ��������:
            <input name="Phone" type="text" value="1234567890" size=10>
            <br>

            <a href="#" onclick="sendClientCheckRq();">���������</a>
        </li>
    </ul>
  </body>

</html>