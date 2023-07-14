<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
    <head><title>WebAPI test</title>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/XMLDisplay.js"></script>
        <script type="text/javascript">
            var root = 'WebAPI';
            var defaultRoot = 'WebAPI';
            <%
                 String authToken = request.getParameter("AuthToken");
                 String isAuthenticationCompleted = request.getParameter("isAuthenticationCompleted");
            %>
            var authToken = '<%=StringHelper.isEmpty(authToken)?"":authToken%>';
            var isAuthenticationCompleted = '<%=StringHelper.isEmpty(isAuthenticationCompleted)?"":isAuthenticationCompleted%>';

            var operations = {};
//            ��������������
            operations['loginblock'] = "ip=8.8.8.8&token=" + authToken + "&isAuthenticationCompleted=" + isAuthenticationCompleted;
            operations['LOGOFF'] = "ip=8.8.8.8";
//            �����
            operations['PRODUCT_LIST'] = "ip=8.8.8.8&productType=";
//            ���������� �� �����
            operations['CARD_DETAIL'] = "ip=8.8.8.8&id=";
            operations['CARD_ABSTRACT'] = "ip=8.8.8.8&id=&from=&to=&count=10";
//            ���������� �� ������
            operations['ACCOUNT_DETAIL'] = "ip=8.8.8.8&id=";
            operations['ACCOUNT_ABSTRACT'] = "ip=8.8.8.8&id=&from=&to=&count=10";
//            ��������� �������
            operations['checksession'] = "ip=8.8.8.8";

            // ������ ������� ������
            operations['panelfastpayment']                   = "ip=8.8.8.8";
            // ��������� ���������� � ���������� ����������� ���������� (��������� ����)
            operations['menucontainer.content']              = "ip=8.8.8.8&exclude=";
            // ��������� ������������ ��� ����������� � ������ ����
            operations['menucontainer.content.autopayments'] = "ip=8.8.8.8";
            // ������� ��������� � �������� ���������� ����� � �������
            operations['menucontainer.content.mobilebank.hide'] = "ip=8.8.8.8";

//            ���
            // ������ ���������� ��� ����������� ��������� ���������� ��������
            operations['alf.graphics.do'] = 'ip=8.8.8.8&showKopeck=true&showWithOverdraft=true';
            // �������������� � �������� ��������
            operations['alf.operations.edit'] = 'ip=8.8.8.8&operationId=1&operationTitle=title&operationCategoryId=23&newOperationCategoryId[1]=10&newOperationTitle[1]=10&newOperationSum[1]=10&newOperationCategoryId[2]=20&newOperationTitle[2]=20&newOperationSum[2]=20';
            // ���������� �������� ���
            operations['alf.operations.add'] = 'ip=8.8.8.8&operationCategoryId=&operationName=someName&operationAmount=200.45&operationDate=14.05.2014';
            // ������� �������� ���
            operations['alf.operations.list.get'] = "ip=8.8.8.8&from=01.01.2014&to=14.05.2014&showCreditCards=true&categoryId=&income=false&showCash=true&showCashPayments=true&showOtherAccounts=true&paginationSize=&paginationOffset=&selectedCardId=&showTransfers=";
            // ��������� ���������� � ������� ������� ���
            operations['alf.servicestatus.do'] = "ip=8.8.8.8";
            // ����������� ���
            operations['alf.serviceconnect.do'] = "ip=8.8.8.8";
            // ��������� ����������� ��������� ���
            operations['alf.category.list.do'] = "ip=8.8.8.8&incomeType=outcome&paginationSize=&paginationOffset=0";
            // ������ � ��������� ���������� ���
            operations['alf.category.edit'] = "ip=8.8.8.8&id=&name=&operationType=";
            // ��������� �������� �� ����������
            operations['alf.category.filter'] = "ip=8.8.8.8&from=&to=&showCash=true&showCashPayments=true&selectedId=allCards&showTransfers=&showCents=";
            // ��������� ������� ��� ��������� ���������
            operations['alf.category.budgetset'] = "ip=8.8.8.8&id=&budget=";
            // �������� ������� ��� ��������� ���������
            operations['alf.category.budget.remove'] = "ip=8.8.8.8&id=";
            // ��������� ���������� �������� ���� ��������
            operations['alf.financecontent'] = "ip=8.8.8.8";
            // �������������� ������ ��������
            operations['alf.operations.edit.group'] = 'ip=8.8.8.8&oldOperationCategoryId=&generalCategoryId=&id[1]=10&newOperationCategoryId[1]=10&id[2]=20&newOperationCategoryId[2]=20';

            function trim(string)
            {
                return string.replace(/(^\s+)|(\s+$)/g, "");
            }

            function getSelectValue(obj)
            {
                var retval = "";
                if (typeof(obj) == 'string')
                    obj = document.getElementById(obj);
                if (obj) retval = obj.options[obj.selectedIndex].value;
                return retval;
            }

            function changeAddress(obj)
            {
                var val = getSelectValue(obj);
                var params = document.getElementById("params");
                params.value = operations[val];

                changeRoot(val);
                setUrl();
            }

            function changeAddressValue(value)
            {
                var address = document.getElementById("address");
                for (var i = 0; i < address.length; i++)
                    if (address.options[i].value == value)
                    {
                        address.options[i].selected = true;
                        break;
                    }

                changeAddress(address);

            }

            function setParams(value)
            {
                var params = document.getElementById("params");
                params.value = value;
            }

            function changeRoot(value)
            {
                if (root[value] != null)
                    document.getElementById('root').value = root[value];
                else
                    document.getElementById('root').value = defaultRoot;
            }

            function setUrl()
            {
                var host = document.getElementById('host').value;
                var root = document.getElementById('root').value;

                document.getElementById('url').value = host + root;
            }

            var onLoad = window.onload;
            window.onload = function ()
            {
                if (onLoad != null) onLoad();
                changeAddress(${"name=address"});
            };
        </script>
        <style type="text/css">
            .Utility {
                color: black;
            }

            .NodeName {
                font-weight: bold;
                color: #800080;
            }

            .AttributeName {
                font-weight: bold;
                color: black;
            }

            .AttributeValue {
                color: blue;
            }

            .NodeValue {
                color: black;
            }

            .Element {
                border-left-color: #00FF66;
                border-left-width: thin;
                border-left-style: solid;
                padding-top: 0px;
                margin-top: 10px;
            }

            .Clickable {
                font-weight: 900;
                font-size: large;
                color: #800080;
                cursor: pointer;
                vertical-align: middle;
            }

            #XMLtree {
                width: 800px;
            }
        </style>
    </head>
    <body>

    <c:set var="IMAGE_ADDRESS" value="/images"/>

    <h1>Web API</h1>

    <html:form action="/webapi" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <small>�����:</small>
        <br/>
        <html:hidden property="url" styleId="url"/>
        Host: <input type="text" value="http://localhost:8888/" onchange="setUrl()" id="host"/>
        Root: <input type="text" value="WebAPI" onchange="setUrl()" id="root"/>
        <br/>
        <select name="address" onchange="changeAddress (this);" id="address">
            <optgroup label="��������������">
                <option value="loginblock" selected>"loginblock" �������������� �� ������ � ��������� �����</option>
                <option value="LOGOFF">"LOGOFF" �����</option>
            </optgroup>
            <optgroup label="�����">
                <option value="PRODUCT_LIST">"PRODUCT_LIST" ��������� ������ ���������</option>
            </optgroup>
            <optgroup label="���������� �� �����">
                <option value="CARD_DETAIL">"CARD_DETAIL" ��������� ���������� �� �����</option>
                <option value="CARD_ABSTRACT">"CARD_ABSTRACT" ������� �� �����</option>
            </optgroup>
            <optgroup label="���������� �� ������">
                <option value="ACCOUNT_DETAIL">"ACCOUNT_DETAIL" ��������� ���������� �� ������</option>
                <option value="ACCOUNT_ABSTRACT">"ACCOUNT_ABSTRACT" ������� �� ������</option>
            </optgroup>
            <optgroup label="��������� �������">
                <option value="checksession">"checksession" �������� ���������� ������</option>
            </optgroup>
            <optgroup label="������ ������ ��������">alf.graphics.do
                <option value="alf.graphics.do">"alf.graphics.do" ������ ���������� ��� ����������� ��������� ���������� ��������</option>
                <option value="alf.operations.add">"alf.operations.add" ���������� ��������</option>
                <option value="alf.operations.edit">"alf.operations.edit" �������������� � �������� ��������</option>
                <option value="alf.operations.edit.group">"alf.operations.edit.group" �������������� ������ ��������</option>
                <option value="alf.operations.list.get">"alf.operations.list.get" ������� �������� ���</option>
                <option value="alf.servicestatus.do">"alf.servicestatus.do" ��������� ���������� � ������� ������� ���</option>
                <option value="alf.serviceconnect.do">"alf.serviceconnect.do" ����������� ���</option>
                <option value="alf.category.list.do">"alf.category.list.do" ��������� ����������� ��������� ���</option>
                <option value="alf.category.edit">"alf.category.edit" ������ � ��������� ���������� ���</option>
                <option value="alf.category.filter">"alf.category.filter" ��������� �������� �� ����������</option>
                <option value="alf.category.budgetset">"alf.category.budgetset" ��������� ������� ��� ��������� ���������</option>
                <option value="alf.category.budget.remove">"alf.category.budget.remove" �������� ������� ��� ��������� ���������</option>
                <option value="alf.financecontent">"alf.financecontent" ��������� ���������� �������� ���� ��������</option>
            </optgroup>
            <optgroup label="���������� ����������� ����������">
                <option value="panelfastpayment">"panelfastpayment" ������ ������� ������</option>
                <option value="menucontainer.content">"menucontainer.content" ��������� ���������� � ���������� ����������� ����������</option>
                <option value="menucontainer.content.autopayments">"menucontainer.content.autopayments" ��������� ������������ ��� ����������� � ������ ����</option>
                <option value="menucontainer.content.mobilebank.hide">"menucontainer.content.mobilebank.hide" ������� ��������� � �������� ���������� ����� � �������</option>
            </optgroup>
        </select>
        <br/>
        <small>��������� (������: id=4&data=someThing)</small>
        <br/>
        <html:textarea property="params" styleId="params" cols="50" rows="5"/>
        <c:choose>
            <c:when test="${form.submit}">
                <textarea cols="60" rows="5" name="note"><%=request.getParameter("note")%>
                </textarea>
            </c:when>
            <c:otherwise>
                <textarea cols="60" rows="5" name="note">�������: �����������:ip=8.8.8.8&token=<%=authToken%>&isAuthenticationCompleted=<%=isAuthenticationCompleted%>
                </textarea>
            </c:otherwise>
        </c:choose>
        <br/>
        <input type="submit" name="operation" value="send"/><br/>
        <br/>
        <c:if test="${form.submit}">
            <b>�����:</b><br/>
            <c:if test="${IMAGE_ADDRESS ne form.address}">
                <small>Tree:</small>
                <br/>

                <div id="XMLtree"></div>
                <small>Xml:</small>
                <br/>
                <%-- ���������� �������� & �� ����������, ��� ��� textarea �� ��������� ��� ����������� ��������� �
                     ������� � ��� ������� ��������� xml ������ �������������� ������� ��� js �������� ��� ��
                     �������������� --%>
                <html:textarea property="result" styleId="result" cols="70" rows="20"/><br/>
                <script type="text/javascript">
                    window.onload = function () { LoadXMLString('XMLtree', trim(document.getElementById('result').value) ); };
                </script>
            </c:if>
            <script type="text/javascript">
                changeAddressValue("<%=request.getParameter("address")%>");
                setParams("<%=request.getParameter("params")%>");
            </script>
        </c:if>
    </html:form>
    </body>
</html:html>
