<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>������. ������ � ����������</title>
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
                        <li><a href="/PhizIA/help.do?id=/reports/index" class="active-menu">������ � ����������</a></li>
                        <li><a href="/PhizIA/help.do?id=/technobreak/list">��������������� ��������</a></li>
                        <li><a href="/PhizIA/help.do?id=/pfp/targets/list">��������� ���</a></li>
                        <li><a href="/PhizIA/help.do?id=/pfp/person/search">���������� ���</a></li>
                        <li><a href="/PhizIA/help.do?id=/passingPfpJournal">������ ���</a></li>
                        <li><a href="/PhizIA/help.do?id=/ermb/person/search">����</a></li>
                       	<li><a href="/PhizIA/help.do?id=/ermb/migration/settings">��������</a></li>
                       	<li><a href="/PhizIA/help.do?id=/addressBook/reports/index">������ �� ������� ������������� ��</a></li>
                    </ul>
                 </div>
 				<!-- end help-left-section -->
            	<div id="help-workspace">
                <div class="contextTitle">������ � ����������</div>

                
                    <ul class="page-content">
                        <li><A HREF="#h02">������</a></li>
                        <li><A HREF="#m02">����������</a></li>
                    </ul>

                    <p>� ������� ������ ���� <b>������ � ����������</b> �� ������ ������������ ������, ����������� ��� ������� ������ ������� "��������
                    ������", � ����� ����������� ���������� � ���������� ������ ������� ��� ���������� �������������.</p>

                    <A NAME="h02"></a>
                    <h2>������</h2>

                    <ul class="page-content">
                        <li><A HREF="#h03">������-������</a></li>
                        <li><A HREF="#m04">IT-������</a></li>
                        <li><A HREF="#h05">������� �������</a></li>
                    </ul>

                    <p>����� ���� <b>������</b> ������������ ��� ������������ ������ ����� �������. ��� ���� ����� ������ ������, ��� ���������� ������� ����� ������ ���� � ������������ ��� �������.

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: ����������� ������������ ������� �������� ������ ����������� � ������� ��������������.</p>
                    </div>

                    <A NAME="h03"> </a>
                    <h3>������-������</h3>

                    <ul class="page-content">
                        <li><A HREF="#h08">���������� � ��������� ��������� ����</a></li>
                        <li><A HREF="#h09">�������� ������������ ����</a></li>
                        <li><A HREF="#h10">�������� � ���� �� ������</a></li>
                        <li><A HREF="#h04">������ ������-�������</a></li>
                    </ul>

                    <p>� ������� ������ ������ ���� <b>������-������</b> �� ������ ������������ � ����������� ������ � ������ ������� "��������
                    ������", � ����� ����������� ������ ���� ������-�������.</p>
                    <p>��� ���� ����� ������������ �����, ��������� ��������� ��������:</p>

                    <ol>
                        <li>�������� �� ������ ������ ���� <b>������-������</b>. � ������� ���� ��������� ������� � ������� ������ ������� � ������ ������-�������:
                            <ul class="page-content">
                                <li><A HREF="#h08">���������� � ��������� ��������� ����</a>,</li>
                                <li><A HREF="#h09">�������� ������������ ����</a>,</li>
                                <li><A HREF="#h10">�������� � ���� �� ������</a>,</li>
                                <li><A HREF="#h04">������ ������-�������</a>.</li>
                            </ul>
                        </li>
                        <li>��� ���� ����� ����������� ������ ������� ������������� ��� ����, ������� ����� ������������ � �������, ������� �� ������ �������, ��������, "�������� � ���� �� ������".</li>
                        <li>����� ������� �� ��� �����, ������� ������ ������������. ��������� �������� ������ ���������� ������, �� ������� ��������� ��������� ����:
                            <ul>
                                <li>� ���� "����" ������� ����, �� ������� ������ ������������ �����. �� ��������� � ��� ���� ������������� ������� ����.</li>
                                <li>� ���� "������" ������� ���� ������ � ��������� ������� ������������ ������. �� ��������� ������� �������� ����� �� ������� ����.</li>
                                <li>"����� �������������" - �������� �� ������ <b>����� �������������</b>.
                                ������� ������� �� ����� ������ �������������, � ������� ��� ���������� �������� �������� �� ������������� �����, ������ �� ������� ����� �������� � �����.
                                </li>
                            </ul>
                        </li>
                    </ol>

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: ��� ������ ������� ������ ����� ����� ����������.</p>
                    </div>

                    <p>����� ���� ��� ��� ����������� �������� �������, ������� �� ������ <IMG border="0" src="${skinUrl}/images/iconSm_NewReport.gif" alt=""> <b>������������</b>. ������� ���������� �����, � �� ��������� �� �������� �� ������� ������-�������.</p>

                    <A NAME="h04"> </a>
                    <h3>������ ������-�������</h3>
                    <p>� ������� ������ ������ ���� <b>������-������</b> - <b>������ ������-�������</b> �� ������ ����������� ������ ���� �������, �������������� ���� � ������� "��������
                    ������", ������� �������� ������, � ����� ������� �� �������� ��������� ����������� ������.</p>
                    <p>� ������ ������ ���� ������������� ����������� ������ �������������� ���� ������-�������. ��� ������� ������ � ������ �� ������ ����������� ��������� ����������: ����� � ���� ������������ ������, �������� ������ � ������� ����������, ������, �� ������� ��� ����������� �����, � ������ ������.</p>
                    <p>���� �� ������ ����������� ���������� �����, ������� �� ��� ����� � ������. ��������� �������� ���������, �� ������� �� ������ ������������ � ����������� �� ������.</p>

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: �� ������ ����������� ������ ������ �� �������� "��������".</p>
                    </div>

                    <p>��� ���� ����� ������� ����� �� �������� "��������" ��� "������", � ������ �������� ��� �������� � ������� �� ������ <b>�������</b>. ����� ����� ����� ����� ������ �� �������.</p>
                    <p>�� ������ ��������� ������ ������������ ������ �� ��� ������� (��. ������ ����������� ������� <b>������ � ����������</b> - <A HREF="#h05">������� �������</a>).</p>

                    <A NAME="h08"> </a>
                    <h4>���������� � ��������� ��������� ����</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � ���������� ��������� �� ����������� � ������ "��������
                    ������". ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#h03">������-������</a>. � ����������� �� ��������� ���� ���������� (������� �������������, ������) � ������ ����� ������������ ��������� ����������:</p>

                    <ul>
                        <li>������������ � ����� ���������� ���������������� ����� (��), ��������� (���), ������������ ������������� (���);</li>
                        <li>�������, ��� � �������� ���������-�������������, ������������ �������� �� ������������ (���);</li>
                        <li>����� ���������� ������������� ��������� ����������� ������������ � ��������� �� �������������� ������ "��������
                        ������", ����������� �� ���� ������������ ������;</li>
                        <li>���������� ����������� ������������� ��������� ����������� ������������ � ��������� �� �������������� ������ "��������
                        ������" �� ������� ���������� ������������ ��������� � ������ ���� �� ���� ������������ ������;</li>
                        <li>���������� ����������� ������������� ��������� ����������� ������������ � ��������� �� �������������� ������ "��������
                        ������" �� ������� ���������� ������������ ��������� �� ��������� �����.</li>
                    </ul>

                    <A NAME="h09"> </a>
                    <h4>�������� ������������ ���� �� ����</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � ���������� �������� ������������� �������� "��������
                    ������", � ����� � ���������� ������������� ������ ������� "�������� ������" ��� iPone/iPad. ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#h03">������-������</a>. � ����������� �� ��������� ���� ���������� (������� �������������, ������) � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>������������ � ����� ���������� ���������������� ����� (��), ��������� (���), ������������ ������������� (���);</li>
                        <li>����� ���������� ������������� ��������� ����������� ������������ � ��������� �� �������������� ������ "��������
                        ������", ����������� �� ���� ������������ ������;</li>
                        <li>���������� ��������, ���������� ����� � ������, �� ������ ������� ��������������;</li>
                        <li>���������� ���������������� �������� �� ��������� �����.</li>
                    </ul>

                    <p>� "������ � ���������� �������� iOS �� ����" �� ������ ����������� ��������� ��������:</p>

                    <ul>
                        <li>������������ � ����� ���������� ���������������� ����� (��), ��������� (���), ������������ ������������� (���);</li>
                        <li>���������� ��������, ������������ �������� �� ���� ������������ ������;</li>
                        <li>���������� ��������, ������� ������������ �������� �� ��������� 90 ���� �� ���� ������������ ������.</li>
                    </ul>

                    <p>���� �� ������ ��������� ������ ����� ���� �� ��������� � ������� CVS, �������� �� ������ <b>��������� � ������� CVS</b>, ��������� ����������� ���� ����������, � ������� ������� ���� �� �����, ���� ����� �������� ���� ������.</p>

                    <A NAME="h10"> </a>
                    <h4>�������� � ���� �� ������</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � ����������� ��������� � ������� "��������
                    ������". ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#h03">������-������</a>. � ����������� �� ��������� ���� ���������� (������� �������������, ������) � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>������������ � ����� ���������� ���������������� ����� (��), ��������� (���), ������������ ������������� (���);</li>
                        <li>���� ��������, ����������� � �������;</li>
                        <li>������ ����������� ��������;</li>
                        <li>���������� �������� ������� ����;</li>
                        <li>����� �����, �� ������� ���� ��������� ��������.</li>
                    </ul>

                    <A NAME="m04"> </a>
                    <h3>IT-������</h3>

                    <ul class="page-content">
                        <li><A HREF="#h11">����� �� ������-���������� �� ������</a></li>
                        <li><A HREF="#h12">����� � �������� ���������� �������� �� ������</a> </li>
                        <li><A HREF="#h13">����� � �������� ���������� �������� �� ���� (����������)</a> </li>
                        <li><A HREF="#h14">����� � ������� ����������� �������</a></li>
                        <li><A HREF="#h15">����� �� ������������ �����������</a></li>
                        <li><A HREF="#h06">������ IT-�������</a></li>
                    </ul>

                    <p>� ������� ������ ������ ���� <b>IT-������</b> �� ������ ������������ � ����������� ������ � ���������� ������� "��������
                    ������" � � �������� ����������� ��������, � ����� ����������� ������ ���� IT-�������.</p>
                    <p>��� ���� ����� ������������ �����, ��������� ��������� ��������:</p>

                    <ol>
                        <li>�������� �� ������ ������ ���� <b>IT-������</b>. � ������� ���� ��������� ������� � ������� ������ ������� �
                            ������ IT-�������:
                            <ul class="page-content">
                                <li><A HREF="#h11">����� �� ������-���������� �� ������</a></li>
                                <li><A HREF="#h12">����� � �������� ���������� �������� �� ������</a> </li>
                                <li><A HREF="#h13">����� � �������� ���������� �������� �� ���� (����������)</a></li>
                                <li><A HREF="#h14">����� � ������� ����������� �������</a> </li>
                                <li><A HREF="#h15">����� �� ������������ �����������</a></li>
                                <li><A HREF="#h06">������ IT-�������</a>.</li>
                            </ul>
                        </li>
                        <li>��� ���� ����� ����������� ������ ������� ���������� ����, ������� ����� ������������ � �������, ������� �� ������������ ��� �������.</li>
                        <li>����� ������� �� ��� �����, ������� ������ ������������. ��������� �������� ������� ���������� ������, �� ������� ��������� ��������� ����:
                            <ul>
                                <li>� ���� "������" ������� ���� ������ � ��������� ������� ������������ ������.</li>
                                <li>� ���� "����� �������������" �������� �� ������ <b>����� �������������</b>. �� ����� ������� ������� ������ �������������, � ������� ��� ���������� �������� �������� �� ������������� �����, ������ �� ������� ����� �������� � �����.
                                    <div class="help-important">
                                        <p><span class="help-attention">����������</span> : ����� � ������� ����������� ������� ����������� ��� ����� �����.</p>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ol>

                    <p>����� ���� ��� ��� ����������� �������� �������, ������� �� ������ <IMG border="0" src="${skinUrl}/images/iconSm_NewReport.gif" alt=""> <b>������������</b>. ������� ���������� �����, � �� ��������� �� �������� �� ������� IT-�������.</p>

                    <A NAME="h06"> </a>
                    <h3>������ IT-�������</h3>
                    <p>� ������� ������ ������ ���� <b>IT-������</b> - <b>������ IT-�������</b> �� ������ ����������� ������ ���� IT-�������, �������������� ���� � ������� "��������
                    ������", ������� �������� ������, � ����� ������� �� �������� ��������� ����������� ������.</p>
                    <p>� ������ ������ ���� ������������� ����������� ������ �������������� ���� IT-�������. ��� ������� ������ � ������ �� ������ ����������� ��������� ����������: ����� � ���� ������������ ������, �������� ������ � ������� ����������, ������, �� ������� ��� ����������� �����, � ������ ������.</p>
                    <p>���� �� ������ ����������� ���������� �����, ������� �� ��� ����� � ������. ��������� �������� ���������, �� ������� �� ������ ������������ � ����������� �� ������.</p>

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: �� ������ ����������� ������ ������ �� �������� "��������".</p>
                    </div>

                    <p>��� ���� ����� ������� ����� �� �������� "��������" ��� "������", � ������ �������� ��� �������� � ������� �� ������ <b>�������</b>. ����� ����� ����� ����� ������ �� �������.</p>
                    <p>�� ������ ��������� ������ ������������ ������ �� ��� ������� (��. ������ ����������� �������  <b>������ � ����������</b> - <A HREF="#h05">������� �������</a>).</p>

                    <A NAME="h11"> </a>
                    <h4>����� �� ������-���������� �� ������</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � ������-���������� ������� "��������
                    ������". ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#m04">IT-������</a>. � ����������� �� ��������� ���� ���������� (������� �������������, ������) � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>����� � ������������ ���������� ���������������� ����� (��);</li>
                        <li>������-���������;</li>
                        <li>������� � ������������ ���������� ��������� ����������, ���������� �� ��������, ������������ ���������� � ��������, � ������� ������ ���.</li>
                    </ul>

                    <A NAME="h12"> </a>
                    <h4>����� � �������� ���������� �������� �� ������</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � �������� ���������� �������� � ������� "��������
                    ������" �� ������. ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#m04">IT-������</a>. � ����������� �� ��������� ���� ���������� (������� �������������, ������) � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>������������ � ����� ���������� ���������������� ����� (��);</li>
                        <li>����� ���������� ��������, ����������� ��������� �� ��������� ������;</li>
                        <li>���������� ������� ����������� ��������� �������� �� ��������� ������;</li>
                        <li>���������� ��������, ������� �� �����-���� ������� �� ���� ���������;</li>
                        <li>���� ������������� �������� �� ������ ���������� �������� � �������.</li>
                    </ul>

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: �������� ��������� ��������, ������� ������ �������� � ������� "��������
                        ������", ����� ���� ��� ���� ���������� �� ���������� �� ������� ������� ��� ��������, �� ������� ������� ����� �� ������ ������������. ��������� �������� ��������� �����������.</p>
                    </div>

                    <A NAME="h13"> </a>
                    <h4>����� � �������� ���������� �������� �� ���� (����������)</h4>
                    <p>������ ����� ������������ ��� ����������� ��������� � ������� ���������� � �������� ���������� �������� � ������� "��������
                    ������". ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#m04">IT-������</a>. � ����������� �� ��������� ���� ���������� (������� �������������, ������) � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>������������ � ����� ���������� ���������������� ����� (��);</li>
                        <li>����, �� ������� ����������� �����;</li>
                        <li>����� ���������� ��������, ����������� ��������� �� ��������� ������;</li>
                        <li>���������� ������� ����������� ��������� �������� �� ��������� ������;</li>
                        <li>���������� ��������, ������� �� �����-���� ������� �� ���� ���������;</li>
                        <li>���� ������������� �������� �� ������ ���������� �������� � �������.</li>
                    </ul>

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: �������� ��������� ��������, ������� ������ �������� � ������� "��������
                        ������", ����� ���� ��� ���� ���������� �� ���������� �� ������� ������� ��� ��������, �� ������� ������� ����� �� ������ ������������. ��������� �������� ��������� �����������.</p>
                    </div>

                    <A NAME="h14"> </a>
                    <h4>����� � ������� ����������� �������</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � ����������� �������� ���������� ������� "��������
                    ������". ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#m04">IT-������</a>. � ����������� �� ���������� ���� ������� � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>����� ������ � ������;</li>
                        <li>� ���� "�������" ������������ �������� 1, ���� ������� ���� ��������� ����������������, �������� 2, ���� ������������������� ������� ���� ���������, � �������� 3, ���� �� ������� ����;
                            <div class="help-important">
                                <p><span class="help-attention">����������</span>: ������������������� ������� ��������� ���������, ���� � ������� 1 ������ ������� �� ��������� ������� ���� �� ���� ������-�������� �� ������� ������������.</p>
                            </div>
                        </li>
                        <li>���� � ����� ��������� �������;</li>
                        <li>���� � ����� ������� �������;</li>
                        <li>����� ������� �������.</li>
                    </ul>

                    <A NAME="h15"> </a>
                    <h4>����� �� ������������ �����������</h4>
                    <p>������ ����� ������������ ��� ��������� � ������� ���������� � �������� ���������� ������-�������� � ������� "��������
                    ������".  ��� ���� ����� ������������ �����, ��������� ��������, ��������� � ������� ����������� ������� <b>������ � ����������</b> - <A HREF="#m04">IT-������></a>. � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>�� ����������� � ������� ������� ������������ ������ ��������, �������� ���������� ������� �������������;</li>
                        <li>� �������� �������, ������� �������� ���� ���������, � ����� ���� � ����� ���������� �������� �������� �� �������� ������� (�������) ����� 5 ������, �� 5 �� 10 ������ � ����� 10 ������.
                            <div class="help-important">
                                <p><span class="help-attention">����������</span>: �������� ��������� ��������, ������� ������ �������� � ������� "��������
                                ������", ����� ���� ��� ���� ���������� �� ���������� �� ������� ������� ��� ��������, �� ������� ������� ����� �� ������ ������������. ��������� �������� ��������� �����������.</p>
                            </div>
                        </li>
                    </ul>

                    <A NAME="h05"> </a>
                    <h3>������� �������</h3>
                    <p>� ������� ��� �������������� ������� ������������� ��������� �������:</p>

                    <ul>
                        <li>"��������������" - ����� ��������� � �������� ������������;</li>
                        <li>"��������" - ����� ������� �����������;</li>
                        <li>"������" - ����� ���������� ������������ �� �����-���� �������.</li>
                    </ul>

                    <A NAME="m02"></a>
                    <h2>����������</h2>

                    <ul class="page-content">
                        <li><A HREF="#n11">����������� �����</a></li>
                    </ul>

                    <p>����� ���� <b>����������</b> ������������ ��� ��������� ���������� � ���������� ������� �� ������� ������. ����� � ������ ������ ���� �� ������ ����������� ��������� ����� �� ����������, ������� ��������� �� ��������� � ��� �����.</p>
                    <p><i>����������: ������ ����� ���� �������� ������ ����������� � ������� ��������������.</i></p>
                    <p>��� ���� ����� ����������� ��������� ������ ������� �� ������� ������ �������, �������� ����� ���� <b>������ � ����������</b> - <b>����������</b>. ��������� �������� ��������� ���������� ������ ������� ��� ���������� �������������, �� ������� ������������ ��������� ����������:</p>

                    <ul>
                        <li>� ���� " �������������" �� ������ ������� �� ����������� ��������������� ����, �� �������� ������ ����������� �����.</li>
                    </ul>
                        <div class="help-important">
                            <p><span class="help-attention">����������</span>: ��������� ����� ������������� ������ ������ ���� ���������������� �����, � �������� �� ���������.</p>
                        </div>
                    <ul>
                        <li>"����� ���������� ���������" - ����� ��������� �� �������������� ������ "��������
                        ������", ����������� �� ���� ������ ������� ������ �������;</li>
                        <li>"����� ���������� ������������ ���������" - ����� ������������ ���������  �� �������������� ������ "��������
                        ������";</li>
                        <li>"���������� ����������� ��������� �� ������� ����" - ����� ��������� �� �������������� ������ "��������
                        ������", ����������� � ������� ������������ ���;</li>
                        <li>"���������� ������������ ��������� �� ������� ����" - ����� ������������ ��������� �� �������������� ������ "��������
                        ������" �� ����������� ����;</li>
                        <li>"���������� �������� ������" - ���������� ��������, ���������� � ������� �� ������ ������;</li>
                        <li>"���������� ����������� ������" - ���������� ����������� �����, ���������� � ������� �� ������ ������;</li>
                        <li>"���������� �������� �� ������� ����" - ����� ��������, ����������� ����� ������� "��������
                        ������" �� ������� ����;</li>
                        <li>"���������� ��������� �� ������� ����" - ����� ���������, ����������� � ������� ������� "��������
                        ������" �� ����������� ����.</li>
                        <li>"���������� �������� ��������� ������� �� ������� ����" - ���������� �������� � ���� ��������� �������� �� ������� ����;</li>
                        <li>"���������� ���������� �� ������� ���� � ��������" - ���������� ����������, ��������� ������� �������������� �� �����-���� ������� (��������� �� �������� "�������������");</li>
                        <li>"���������� �������� �� ��������� ����" - ����� ��������, ����������� ����� ������� "��������
                        ������" �� ���������� ����;</li>
                        <li>"���������� ��������� �� ��������� ����" - ����� ���������, ����������� ����� ������� "��������
                        ������" �� ���������� ����;</li>
                        <li>"���������� �������� ��������� ������� �� ��������� ����" - ���������� �������� � ���� ��������� �������� �� ���������� ����;</li>
                        <li>"���������� ���������� �� ��������� ���� � ��������" - ���������� ����������, ��������� ������� �������������� �� �����-���� �������.
                        � ������ ����������� ������ ���������, ����������� �� ��������� ����;</li>
                        <li>"���������� ���������� � ���������" - ���������� ����������, ������� ��������� � ��������� �� ������� ������ (��������� �� �������� "��������������");</li>
                        <li>"���������� ���������� � ��������� ��������� ����� 30 �����" - ����� ����������, ������� �� �����-���� ������� �������������� � ��� ����� ������ 30 �����.</li>
                    </ul>

                    <p>��� ���� ����� ����������� ���������� ������ ����������� �������, ������� �� ������ <b>��������</b>.</p>

                    <div class="help-important">
                        <p><span class="help-attention">����������</span>: �������������� ���������� �������� � ����������� ����������� ���������� ������ 10 �����.</p>
                    </div>

                    <p>���� �������� � ������ ������� ������ ������, �� ��� ��������, ��� �������� ��������� ��������� ������������� ����� ��������������. ���� �������� ������� ������� ������, �� ��� ��������, ��� �������� ��������� ��������� ������������� ����� ������. �� ������ ������ ��������� �������� ���������� ����������� ��� ���������� ���������������� ����� � ������ ���� <b>�������������</b> (��. ������ ����������� ������� <a href="/PhizIA/help.do?id=/departments/list#h07">������������� - ����������� ������ ������������� - ����������</a>).</p>

                    <A NAME="n11"> </a>
                    <h3>����������� �����</h3>
                    <p>����� � ������� ������ ���� <b>����������</b> �� ������ ����������� ��������� ����� �� ���������� ����������, ����������� �� ��������� � �����. ��� ����� �������� �� ������ "���������� ���������� � ���������" ��� "���������� ���������� � ��������� ��������� ����� 30 �����". � ���������� ��������� ��������, �� ������� �� ������ ����������� ��������� ����������: �������� ������������� (������������ ���������������� �����, ��� �������� ����������� �����), ����� ������������ ������ � ������ ���������� � ���������.</p>
                    <p>��� ������� ��������� � ������ ������������ ��������� ����������:</p>

                    <ul>
                        <li>����� ���������;</li>
                        <li>��� �������� (��������, ������� ����� �������, ������ ������� ��� �����);</li>
                        <li>����� � ������ ��������;</li>
                        <li>����� �������� ���������;</li>
                        <li>������� �������, � ������� ��������� ��������.</li>
                    </ul>

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
