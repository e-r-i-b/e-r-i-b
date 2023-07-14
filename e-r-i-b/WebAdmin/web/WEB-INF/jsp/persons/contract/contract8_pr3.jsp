<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<table cellpadding="0" cellspacing="0" width="180mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
<col style="width:180mm">
<tr>
<td valign="top">

<table style="width:100%;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">

<%@include file="footer.jsp"%>

<tbody>
<!-- Шапка документа -->
<tr>
	<td align="right">
		<b>Приложение 3</b><br><br>&nbsp;
		<span class="italic font10">к Договору о предоставлении<br>
		   услуг с использованием системы<br>
		   &ldquo;Электронная Сберкасса&rdquo;<br>
	    </span>
		№<input type="Text" value='${person.agreementNumber}' readonly="true" class="insertInput" style="width:21%">от&nbsp;&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
		 <input id='monthStr31' value='' type="Text" readonly="true" class="insertInput" style="width:13%">20<input value='<bean:write name="agreementDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">г.
	</td>
    <script>
       document.getElementById('monthStr31').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
    </script>
</tr>
<tr>
	<td align="center" style="padding:5mm 0mm 7mm 0mm">
		<b>Перечень операций, представляемых Банком в системе &ldquo;Электронная Сберкасса&rdquo;</b><br>
	</td>
</tr>
<tr>
	<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. Перечень банковских операций, плата за которые взимается в соответствии с Сборником тарифов Сбербанка России ОАО и за предоставление которых с использованием системы &ldquo;Электронная Сберкасса&rdquo; с Клиента взимается ежемесячная абонентская плата, в том числе:
	</td>
</tr>
<tr>
	<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1. перечень банковских операций:
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="15%"><b>№ п/п</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;Наименование операций&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>
			<td class="docTdBorder textPadding">Открытие нового счета по вкладу безналичным путем с автоматическим включением его в Перечень номеров счетов Клиента, указанных в Приложении 1 к настоящему Договору</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">Перевод (перечисление) денежных средств, хранящихся на счете2, на другой действующий счет, в том числе открытый в другом филиале Банка (Операционном управлении Сбербанка России ОАО) или в другой кредитной организации</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">3</td>
			<td class="docTdBorder textPadding">Закрытие счета по вкладу с переводом (перечислением) денежных средств на другой действующий счет</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">4</td>
			<td class="docTdBorder textPadding">Продажа Банком иностранной валюты в безналичном порядке за счет средств на счете по вкладу в рублях (с зачислением полученной суммы на действующий счет по вкладу Клиента в иностранной валюте в том же филиале Банка)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">5</td>
			<td class="docTdBorder textPadding">Покупка Банком иностранной валюты в безналичном порядке со счета по вкладу Клиента в иностранной валюте за рубли с зачислением средств на счет по вкладу Клиента в рублях в том же филиале Банка</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">6</td>
			<td class="docTdBorder textPadding">Покупка и продажа (конверсия) в безналичном порядке иностранной валюты одного вида за иностранную валюту другого вида (с зачислением полученной суммы на действующий счет Клиента в иностранной валюте в том же филиале Банка)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">7</td>
			<td class="docTdBorder textPadding">Списание денежных средств со счета2 в рублях (в бюджет и государственные внебюджетные фонды, в пользу получателей платежей - юридических лиц или индивидуальных предпринимателей, как имеющих, так и не имеющих договорные отношения с Банком).</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">8</td>
			<td class="docTdBorder textPadding">Предоставление детализированной справки о состоянии вкладов Клиента</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">9</td>
			<td class="docTdBorder textPadding">Приостановление операций по счету по вкладу при утрате сберегательной книжки</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">10</td>
			<td class="docTdBorder textPadding">Блокировка карты/приостановка действия карты</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td style="padding:5mm 0mm 2mm 0mm">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2. перечень операций с паями ПИФ:
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="15%"><b>№ п/п</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;Наименование операций&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>
			<td class="docTdBorder textPadding">Приобретение инвестиционных паёв ПИФ</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">Изменение анкетных данных лица, зарегистрированного в реестре владельцев инвестиционных паев ПИФ</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">3</td>
			<td class="docTdBorder textPadding">Закрытие лицевого счёта владельца в реестре владельцев инвестиционных паев ПИФ</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">4</td>
			<td class="docTdBorder textPadding">Подача заявления на получение выписки по лицевому счету зарегистрированного лица</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">5</td>
			<td class="docTdBorder textPadding">Подача заявления на получение справки по лицевому счету зарегистрированного лица</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.Перечень вспомогательных операций, включающих в себя операции, осуществляемые Клиентом для получения услуг в Системе, и операции справочно-информационного характера:
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="15%"><b>№ п/п</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;Наименование операций&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>
			<td class="docTdBorder textPadding">Информация о действующих курсах покупки-продажи иностранной валюты (евро и доллар США)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">Выдача выписки по счету</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">3</td>
			<td class="docTdBorder textPadding">Информация о текущем размере остатка средств на счете</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">4</td>
			<td class="docTdBorder textPadding">Информация о максимальном размере суммы, которую можно снять со счета по вкладу Клиента без нарушения условий договора</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">5</td>
			<td class="docTdBorder textPadding">Отчет об исполнении длительных поручений по вкладу (счету) Клиента в заданном периоде (не более шести последних месяцев, включая текущий)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">6</td>
			<td class="docTdBorder textPadding">Подготовка платежного документа (платежного поручения, инкассового поручения, ф.№ПД-4, ф.№ ПД-4 СБ (налог))</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">7</td>
			<td class="docTdBorder textPadding">Информация о размере остатка средств (балансе) на карте</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">8</td>
			<td class="docTdBorder textPadding">Выдача выписки о движении денежных средств по счету Карты - последние десять операций - и остатке средств на карте</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">9</td>
			<td class="docTdBorder textPadding">Выдача информации об операциях, совершенных в реестре владельцев инвестиционных паев ПИФ, по лицевому счету зарегистрированного лица на основании документов, поданных с использованием Системы.</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
		<tr>
                <td width="50%" align="center" style="padding-top:10mm"><b>от Банка:</b></td>
                <td align="center" style="padding-top:10mm"><b>Клиент:</b></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                        <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
		</table>
	</td>
</tr>
<tr>
	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>М.П.</b></td>
</tr>
<tr><td style="padding-top:100mm"><br><br><br><br><br><br><br></td></tr>
</tbody>
</table>
</td>
</tr>
</table>
