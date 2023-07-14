<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 11.08.2006
  Time: 15:29:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html>
<tiles:importAttribute/>
 <head>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1251">
        <c:set var="contextName" value="${phiz:loginContextName()}"/>
        <c:if test="${contextName eq 'PhizIC'}">
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
        </c:if>
        <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css">
        <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css">
 </head>
<html:form  action="/private/PD4" onsubmit="return setEmptyAction(event)">

<tiles:insert definition="dictionary">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
  <tiles:put name="pageTitle" type="string" value="Выбор налогового периода"/>

  <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
		        <tiles:put name="commandTextKey" value="button.cancel"/>
		        <tiles:put name="commandHelpKey" value="button.cancel"/>
		        <tiles:put name="image" value=""/>
		        <tiles:put name="bundle" value="dictionaryBundle"/>
		        <tiles:put name="onclick" value="javascript:window.close()"/>
	    </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="TaxPeriod"/>
		<tiles:put name="image" value="iconMid_dictionary.gif"/>
		<tiles:put name="text" value="Налоговые периоды"/>
		<tiles:put name="buttons">
            <script type="text/javascript">
                document.imgPath="${imagePath}/";
                function sendTaxData (event)
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
                            a['taxPeriod']=trim(r.cells[1].innerHTML);
                            if(a['taxPeriod'] == "ГД")
                                a['month']="00";
                            else
                                a['month']="";
                            window.opener.setTaxPeriod(a);
                            window.close();
                            return true;
                        }
                        id=id+1;
                    }
                    alert("Выберите налоговый период.");
                    return false;
                }
            </script>
			 <tiles:insert definition="clientButton" flush="false">
		        <tiles:put name="commandTextKey" value="button.choose"/>
		        <tiles:put name="commandHelpKey" value="button.choose"/>
		        <tiles:put name="image" value="iconSm_select.gif"/>
		        <tiles:put name="bundle" value="dictionaryBundle"/>
		        <tiles:put name="onclick" value="javascript:sendTaxData(event);"/>
	    </tiles:insert>
		</tiles:put>
	    <tiles:put name="data">
			<td>&nbsp;</td>
			<td>Налоговый период</td>
            <td>Описание</td>
        </tr>
		<tr onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	            <input type="radio" name="selectedId" id="selectedId1" value="1" style="border:none"/>
            </td>
			<td align=center class="ListItem">Д1</td>
			<td align=center class="ListItem">платеж за первую декаду месяца</td>
        </tr>
		<tr onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	            <input type="radio" name="selectedId" id="selectedId2" value="2" style="border:none"/>
            </td>
			<td align=center class="ListItem">Д2</td>
			<td align=center class="ListItem">платеж за вторую декаду месяца</td>
        </tr>
		<tr onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	            <input type="radio" name="selectedId" id="selectedId3" value="3" style="border:none"/>
            </td>
			<td align=center class="ListItem">Д3</td>
			<td align=center class="ListItem">платеж за третью декаду месяца</td>
        </tr>
		<tr onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	            <input type="radio" name="selectedId" id="selectedId4" value="4" style="border:none"/>
            </td>
			<td align=center class="ListItem">МС</td>
			<td align=center class="ListItem">месячные платежи</td>
        </tr>
		<tr onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	            <input type="radio" name="selectedId" id="selectedId5" value="5" style="border:none"/>
            </td>
			<td align=center class="ListItem">КВ</td>
			<td align=center class="ListItem">квартальные платежи</td>
        </tr>
		<tr onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	           <input type="radio" name="selectedId" id="selectedId6" value="6" style="border:none"/>
            </td>
			<td align=center class="ListItem">ПЛ</td>
			<td align=center class="ListItem">полугодовые платежи</td>
        </tr>
		<tr  onclick="selectRow(this);" ondblclick="sendTaxData();">
            <td align=center class="ListItem">
	           <input type="radio" name="selectedId" id="selectedId7" value="7" style="border:none"/>
            </td>
			<td align=center class="ListItem">ГД</td>
			<td align=center class="ListItem">годовые платежи</td>
        </tr>
		</tiles:put>
	    </tiles:insert>
	</tiles:put>
</tiles:insert>

</html:form>
</html>