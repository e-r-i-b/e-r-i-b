<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/payments/check_print" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="check">
    <tiles:put name="data" type="string">
    <c:choose>
    <c:when test="${form.printCheck == true}">
    <style>
    .checkSize
    {
        width:60mm;
        font-family:Arial;
        font-size:8px;
        word-wrap:break-word;
        text-transform:uppercase;
    }
    .title
    {
        font-weight:bold;
        text-align: center;
    }
    .titleAdditional
    {
        text-align: center;
    }
    .stamp
    {
        border-width:2px;
        font-family:Arial;
        font-size:9px;
        border-style:solid;
        border-color:#025CA2;
        text-align:center;
        font-weight: bold;
        color:#025CA2;
        padding:2mm;
        width:56mm;
    }

    .stamp img
    {
        margin-left: -2mm;
    }

    .mainDiv
    {
       word-wrap:break-all;
       border-width:1px;
       border-style:solid;
       border-color:black;
       width:60mm;
       marign:5mm;
       padding:0mm 3mm 0mm 3mm; 
    }
    </style>

    <script type="text/javascript">
        doOnLoad(function windowPrint()
        {
            window.print();
        });
    </script>

    <div id="checkId" class="mainDiv">
        <div class="checkSize title">��� �������� ������</div><br/>
        <div class="checkSize titleAdditional"><bean:message bundle="commonBundle" key="text.operation.check"/></div><br/>
		${form.html}
        <br/><div id="standartInfo" class="checkSize titleAdditional">
            �� ����������, ��������� �� ��������� ������� �� �����, �� ������
            ��������� ��������� �� ����������� �����
            <br/>(�������������� ������ �������� ����� �� ����� �����)
        </div>
    </div>

    <script type="text/javascript">
        if (navigator.appName == 'Opera') //����� � ����� ������������ ������� ������
	    {
            var stringLength = 25;     // ���������� ��������� ��������
            var checkDiv = document.getElementById("checkId");
            if (checkDiv != null)
            {
                var divs = checkDiv.getElementsByTagName("div");       // �������� ��� div'�, � ���. �������� ����
                for (var i = 0; i < divs.length; i++)
                {
                    var div = divs[i];
                    var contentDiv = div.innerHTML;
                    var lengthContentDiv = div.innerHTML.length;
                    if (lengthContentDiv > stringLength)              // ���� ����� �������, ����� ���������� � ��� ������� (���� �� ��� ���)
                    {
                        var newContent = "";
                        for (var j = 0; j < lengthContentDiv; j = j + stringLength)
                        {
                            var partContentDiv = contentDiv.substr(j, stringLength);   // �������� ����� ������

                            // ���� � ����� ��� ��������, �� ������ � ����� ������ (����� ����� �������������� ������� ������)
                            if (!partContentDiv.match(/\s/))
                            {
                                // �� ������, ���� � div'� ���� ������ ����, �� ������ ������ �� � ����� �����,
                                // � ����� ����� (����� �� �������� ������� ���� ��������)
                                if (partContentDiv.match(/</))
                                    partContentDiv.replace(/</, " <");
                                else
                                    partContentDiv = partContentDiv + " ";
                            }
                            newContent = newContent + partContentDiv;
                        }                
                        div.innerHTML = newContent;
                    }
                }
            }
	    }

        var additionalInfoDiv = document.getElementById("additionalInfo");
        if (additionalInfoDiv != null)
        {
            var standartInfoDiv = document.getElementById("standartInfo");
            var tempInfoHTML  = additionalInfoDiv.innerHTML;              // ��������� ����� � ����� div
            additionalInfoDiv.innerHTML = standartInfoDiv.innerHTML;      // ������ ����� � div'��
            standartInfoDiv.innerHTML = tempInfoHTML;

        }
    </script>
    </c:when>
    <c:otherwise>
        ������ ���� ��� ������ �������� �� �������������
    </c:otherwise>
    </c:choose>
    </tiles:put>
</tiles:insert>
</html:form>