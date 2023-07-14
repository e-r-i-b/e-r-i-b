<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>������. ������ ���</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">

    <link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/help.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/help.css"/>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ifixpng.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>

    <script type="text/javascript">
        var skinUrl = '${skinUrl}';
        var globalUrl = '${globalUrl}';
        var scroll = new DivFloat("help-left-section");
        scroll.doOnScroll();
    </script>

</head>

<body>


    <div class="help-container">
        <div id="help-header">
            <p class="helpTitle">������ �� ������� "�������� ������"</p>
        </div>

        <!-- end help-header -->
        <div class="clear"></div>

        <div id="help-content">
            <div id="help-left-section">
                <p class="sidebarTitle">������� ������</p>
                    <ul class="help-menu">
                        <li><a href="/PhizIA/help.do?id=/persons/list">�������</a></li>
                        <li><a href="/PhizIA/help.do?id=/schemes/list">����� ����</a></li>
                        <li><a href="/PhizIA/help.do?id=/employees/list">����������</a></li>
                        <li><a href="/PhizIA/help.do?id=/log/operations">������</a></li>
                        <li><a href="/PhizIA/help.do?id=/audit/businessDocument">�����</a></li>
                        <li><a href="/PhizIA/help.do?id=/person/search">�����������</a></li>
                        <li><a href="/PhizIA/help.do?id=/private/dictionary/banks/national">�����������</a></li>
                        <li><a href="/PhizIA/help.do?id=/private/dictionaries/provider/list">���������� �����</a></li>
                        <li><a href="/PhizIA/help.do?id=/dictionaries/routing/node/list">������� �������</a></li>
                        <li><a href="/PhizIA/help.do?id=/persons/configure">���������</a></li>
                        <li><a href="/PhizIA/help.do?id=/configure/inform/service">������� � ���������</a></li>
                        <li><a href="/PhizIA/help.do?id=/departments/list">�������������</a></li>
                        <li><a href="/PhizIA/help.do?id=/news/list">�������</a></li>
                        <li><a href="/PhizIA/help.do?id=/mail/list">������</a></li>
                        <li><a href="/PhizIA/help.do?id=/deposits/list">��������</a></li>
                        <li><a href="/PhizIA/help.do?id=/loans/products/list">�������</a></li>
                        <li><a href="/PhizIA/help.do?id=/creditcards/products/list">�����</a></li>
                        <li><a href="/PhizIA/help.do?id=/reports/index">������ � ����������</a></li>
                        <li><a href="/PhizIA/help.do?id=/technobreak/list">��������������� ��������</a></li>
                        <li><a href="/PhizIA/help.do?id=/pfp/targets/list">��������� ���</a></li>
                        <li><a href="/PhizIA/help.do?id=/pfp/person/search">���������� ���</a></li>
                        <li><a href="/PhizIA/help.do?id=/passingPfpJournal" class="active-menu">������ ���</a></li>
                        <li><a href="/PhizIA/help.do?id=/ermb/person/search">����</a></li>
                       	<li><a href="/PhizIA/help.do?id=/ermb/migration/settings">��������</a></li>
                       	<li><a href="/PhizIA/help.do?id=/addressBook/reports/index">������ �� ������� ������������� ��</a></li>
                    </ul>
                </div>
 				<!-- end help-left-section -->
            	<div id="help-workspace">
                <div class="contextTitle">������ ���</div>
                   
                    <p>����� �������� ���� <b>������ ���</b> ������������ ��� ��������� ������� ����������� ������������� ����������� ������������.</p>
                    <p>��� ����� � ������ ����� ���� ������������� ����������� ������ ����������� ���. ��� ������� �������� ��� ������������ ���� � ����� ����������, ��� �������, ������� �������� ������������, ���������, ����������� ������������, � ������ ����������� ������������.</p>
                    <p>��� ���� ����� ����������� ���������� ������������, �������� �� ��� ���� ��� �������, � �� ��������� �� ����� ����������� ������������, ��� ����� ������������ ���������� ���������� ���.</p>

                    <H2>����� ���</h2>
                    <A NAME="h02"> </a>
                    <p>����� � ������� ��� �� ������ ��������������� ��������/������� �������. ��� ����� ������� �� ������ <b>�����</b> � ��������� ��������� ����:</p>

                    <ul>
                        <li>"������" - ����� �� ���� ������������;</li>
                        <li>"������" - ����� �� ������ ����������� ������������;</li>
                        <li>"������" - ����� �� �������, ����� � �������� �������, ������������ ������������;</li>
                        <li>"���� ��������" - ����� �� ���� �������� �������;</li>
                        <li>"��������" - ����� �� ����� � ������ ���������, ��������������� �������� �������;</li>
                        <li>"����-�������" - ����� �� ������������� � ���� ������������ ����-�������;</li>
                        <li>"��������" - ����� �� �������, ����� � �������� ����������, ������������ ���;</li>
                        <li>"ID ���������" - ����� �� �������������� ����������, ������������ ���.</li>
                    </ul>

                    <p>����� ���� ��� ��� ����������� �������� �������, ������� �� ������ <b>���������</b>, � ������� ������� ������ ������������ ��� ���. </p>
                    <p>�� ������ ����������� ������ �� ������ ��� ���������� �����, � ����� �� ��������� � ��������� ����� �������.</p>

                    <H2>�������� ������ ���</h2>
                    <A NAME="h03"> </a>
                    <p>�� ������ ��������� ����� � ���������� ���. ��� ����� � ������ ���������� ������� �������� �������, �� ������� �� ������ ������������ �����. ����� �� ������ �������� ������ �� �������������� ���������� � ������� �������. ����� ���� ��� ��� ����������� ������ �������, ������� �� ������ <b>���������</b>. ��������� ����������� ����, � ������� ������� ���� �� �����, � ������� ����� �������� ���� � �������.</p>
                    <p>� �������������� ������ ����� ������������ ��������� ����������: ����� ��, ���, ��� ����������, ������� �������� ���������� ������������, ������, ��������� � ����� ���������� ������������, �������, ��� � �������� �������, ������������ ������������, ���� ��� ��������, ����� � ����� ���������, ��������������� ��������,
                    ���������� ����� �������, ����������� ������ (����� �������� �������, �������������� ��������� ��������), ���������� �������� ������� �� ������ � ������ ������, �������� �������� �������, ���� �� �������������� ��������� �������, 
					���� �� � ������� ��������� ����� � ���������, ����-�������, ��������������� ������� �� ����������� ���, �������, ���, �������� � ID ����������, ������������ ������������, �����, � ������� ���� ��������� ���������� ������������, ����� ����������� ����� � ��������� ������� �������, ������, � ������� ��������� ������������ �� ������ ������ �������. </p>
                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: � ������ ���� ������ �������� ��� ��������������, ��� � ID ���������� �� ����� ������������ � ������.</p>
                    </div>
               <div class="help-to-top"><a href="#top">� ������ �������</a></div>
					<div class="clear"></div>

            </div>
            <!-- end help-workspace -->
        </div>
        <!-- end help-content -->

        <div class="clear"></div>
    </div>
</body>
</html>
