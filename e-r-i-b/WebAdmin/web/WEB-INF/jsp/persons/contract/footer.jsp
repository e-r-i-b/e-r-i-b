<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<tfoot>
      <th>
         <table class="textDoc font11" cellpadding="0" cellspacing="0" width="100%">
         <tr>
            <td valign="bottom" style="height:12mm;padding-top:7mm">
                <table class="textDoc font11" style="width:100%;">
		        <tr>
				    <td colspan="4" class="font10" align="center">Договор №<input type="Text" value='${person.agreementNumber}' readonly="true" class="insertInput" style="width:21%"> от "<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">" <input id='monthStr11' value='' type="Text" readonly="true" class="insertInput" style="width:12%">20<input value='<bean:write name="agreementDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%"> г.</td>
	    		</tr>
                <script>
                    document.getElementById('monthStr11').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
                </script>
                <tr>
			        <td><b><nobr>От&nbsp;Банка</nobr></b></td>
			        <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:98%"></td>
    			    <td><b>Клиент</b></td>
	    		    <td width="50%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
		        </tr>
			    <tr>
    				<td>&nbsp;</td>
	    			<td align="center" valign="top"  class="font10">(подпись работника Банка)</td>
		    		<td>&nbsp;</td>
			    	<td align="center" valign="top"  class="font10">(подпись Клиента)</td>
			    </tr>
        		</table>
            </td>
        </tr>
        </table>
     </th>
</tfoot>
      