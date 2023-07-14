<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<%@ page import="com.rssl.phizic.ejbtest.service.SBNKDService" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="sbnkdAction" value="${phiz:calculateActionURL(pageContext, '/sbnkd')}"/>

<html>
<head><title>�������� ��� ��������� �������� �����</title></head>
<script type="text/javascript">
    function loadMessage()
    {
        var uid = document.getElementsByName("uid")[0].value;
        window.location = "${sbnkdAction}"
                + "?uid=" + uid;
    }

</script>

<%
    String uid = request.getParameter("uid");
    if (StringHelper.isNotEmpty(uid))
        uid = uid.split("_")[0];
    String answer = request.getParameter("ans");
%>
<body onload="hideFields();">
<h1>�������� ��� ��������� �������� �����</h1>

<form action="${sbnkdAction}" method="POST">
    (*) ������� UUID ������: <br>
    <input type="text" name="uid" value="<%=StringHelper.isEmpty(uid) ? "" : uid%>" size="50">
    <br>
    <a onclick="loadMessage()" style="text-decoration: underline;cursor: pointer;">��������� ���������</a>
    <br>
    <%
        boolean messageSend = false;
        if (StringHelper.isNotEmpty(uid) && StringHelper.isNotEmpty(answer))
        {
            SBNKDService sbnkdService = new SBNKDService();
            try
            {
                sbnkdService.sendMessage(uid, answer);
    %>
    <span style="color: #007f00;">��������� ����������.</span> <br>
    <%
        messageSend = true;
    }
    catch (Exception e)
    {
    %><span style="color: #ff0000;">������ ��� �������� ��������� : <%=e.getMessage()%></span><br><%
        }
    }

    if (StringHelper.isNotEmpty(uid))
    {
        SBNKDService sbnkdService = new SBNKDService();
        String[] mes = sbnkdService.getMessage(uid);
        if (mes == null)
        {
            if (!messageSend)
            {
%>
    <span style="color: #ff7f7f;">��������� ��� �� ��������.</span> <br>
    <% }
    }
    else
    {
        String income = mes[0];
        String ans = mes[1];
    %>
    <table>
        <tr>
            <td>��������� ������</td>
            <td>������������ �����</td>
        </tr>
        <tr>
            <td>
                <textarea readonly style="width:500px;height:500px"><%=income%>
                </textarea>
            </td>
            <td>
                <textarea name="ans" style="width:500px;height:500px"><%=ans%>
                </textarea>
            </td>
        </tr>
    </table>
    <input type="submit">

    <%
            }
        }
    %>
</form>
</body>

</html>