<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insert definition="printContract">
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <body onLoad="showMessage();" Language="JavaScript">
    <html:form action="/persons/print">
      <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
      <!--------------------------------- ПРИЛОЖЕНИЕ №8 ----------------------------------------->


     <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
    <col style="width:172mm">
    <tr>
    <td>
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <!-- Пространство над шапкой -->
    <tr>
        <td height="20mm">&nbsp;</td>
    </tr>

    <!-- Логотип Сбера-->
    <tr>
        <td><%@ include file="/WEB-INF/jsp-sbrf/sbrfPrintHeader.jsp" %></td>
    </tr>

    <tr>
        <td align="center"><b>ДОГОВОР № <input type="Text" readonly="true" class="insertInput" style="width:10%"></b></td>
    </tr>
    <tr>
        <td align="center"><b>о предоставлении услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo;</b><br>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="25%"><nobr>г. <input value="Москва" type="Text" class="insertInput" style="width: 92%"></nobr></td>
                <td width="43%">&nbsp;</td>
                <td width="7%"><nobr>&ldquo;<input type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td width="25%"><nobr><input type="Text" class="insertInput" style="width: 67%">200<input type="Text" class="insertInput" style="width: 12%">г.</nobr></td>
            </tr>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Открытое акционерное общество «Сбербанк России», именуемое в дальнейшем "Банк", в лице
            <input type="Text" readonly="true" class="insertInput" style="width:30%">
        </td>
    </tr>
    <tr>
        <td><input type="Text" readonly="true" class="insertInput" style="width:82%">, действующего</td>
    </tr>
    <tr>
        <td>на основании<input type="Text" readonly="true" class="insertInput" style="width:65%">, с одной стороны</td>
    </tr>
    <tr>
        <td>и<input value='${person.fullName}' type="Text" readonly="true" class="insertInput" style="width:98%"></td>
    </tr>
    <tr>
        <td><input type="Text" readonly="true" class="insertInput" style="width:99%">,</td>
    </tr>
    <tr>
        <td>именуемый(ая) в дальнейшем "Клиент", с другой стороны, совместно именуемые "Стороны", заключили настоящий Договор о нижеследующем.</td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center" class="titleMargin">
                    <span class="titleDoc"><br>1.&nbsp;&nbsp;ОБЩИЕ ПОЛОЖЕНИЯ</span>
                </td>
            </tr>
            <tr>
                <td class="font9">&nbsp;</td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.Специальные термины, применяемые в тексте настоящего Договора, используются в следующем значении:
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.1.&quot;Электронный документ&quot; &ndash; электронный образ документа (расчетного или иного), представленный в формате, определяемом программными средствами создания документа.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;Электронный расчетный документ&quot; &ndash; электронный документ, являющийся основанием для совершения Банком операций по счету Клиента.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;Электронный служебный документ&quot; &ndash; электронный документ, обеспечивающий обмен информацией между Сторонами, не связанный с совершением операций по счету Клиента.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.2.&ldquo;Автоматизированная система &ldquo;Электронная Сберкасса&rdquo; (далее &ldquo;Система&rdquo;) &ndash; автоматизированная корпоративная информационная система &ldquo;Электронная Сберкасса&rdquo;, функциональный аналог системы &ldquo;Клиент-Банк&rdquo;. Предназначена для предоставления Клиентам интерфейса для работы со своими счетами, открытыми в Сбербанке России, через глобальную сеть Интернет. Интерфейс обеспечивает возможности ввода, редактирования и отправки расчетных и информационных документов в автоматизированные банковские системы Сбербанка России, а также возможность получения из этих автоматизированных банковских систем отчетной информации по поддерживаемым счетам Клиентов.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.3.&ldquo;Счет Клиента&rdquo; &ndash; банковский счет по вкладу резидента-физического лица и нерезидента-физического лица в валюте Российской Федерации или иностранной валюте, текущий счет физического лица-резидента в валюте Российской Федерации, счет физического лица-нерезидента в валюте Российской Федерации, счет банковской карты физического лица в валюте Российской Федерации или иностранной валюте, предусматривающие совершение расчетных операций, не связанных с осуществлением предпринимательской деятельности. Перечень счетов указывается Клиентом в Приложении 1 к  настоящему Договору.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.4.&ldquo;Представитель Клиента&rdquo; &ndash; физическое лицо, уполномоченное Клиентом распоряжаться денежными средствами, находящимися на его счете(ах), действующее от имени Клиента в пределах полномочий, определенных в доверенности, составленной в соответствии с требованиями действующего законодательства и условиями настоящего Договора. Для совершения операций в Системе Представитель Клиента проходит процедуру
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
     <br style="page-break-after:always;">

     <!---------------------------------- страница2----------------------------------------->


    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
           регистрации в Системе такую же, как Клиент.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.5.&quot;Хэш-функция&quot; &ndash; однонаправленное отображение (свертка) содержимого файла или блока данных произвольного размера в блок данных фиксированного размера, обладающее заданными математическими свойствами; используется при формировании аналога собственноручной подписи электронного документа.
        </td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.6.&quot;Плата за организацию расчетного обслуживания с использованием системы &ldquo;Электронная Сберкасса&rdquo;&quot; &ndash; единовременная плата, взимаемая Банком в соответствии с условиями настоящего Договора и Тарифами Банка в день подписания Клиентом и Банком настоящего Договора и акта приема-передачи, в котором отражается факт получения Клиентом от Банка пин-конверта с паролем для доступа в Систему. Данная плата в таком же порядке взимается также в день подписания Представителем Клиента и Банком акт приема-передачи, в котором отражается факт получения Представителем Клиента от Банка пин-конверта с паролем для доступа в Систему в соответствии с Тарифами Банка.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.7.&quot;Ежемесячная плата за предоставление услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo;&quot; &ndash; ежемесячная плата, взимаемая Банком в соответствии с условиями настоящего Договора и Тарифами Банка, по истечении каждого месяца, исчисляемого с даты подписания Сторонами настоящего Договора.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.8.&quot;Плата за восстановление возможности доступа в систему &quot;Электронная Сберкасса&quot; в случае компрометации пароля  Клиентом (Представителем Клиента)&quot; &ndash;  взимается Банком единовременно в день выдачи Клиенту (Представителю Клиента) нового пин-конверта с паролем для доступа в систему &quot;Электронная Сберкасса&quot; в соответствии с Тарифами Банка в случае его компрометации Клиентом (Представителем Клиента).
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">

            <tr>
                <td align="center">
                    <span class="titleDoc"><br>2.&nbsp;&nbsp;ПРЕДМЕТ ДОГОВОРА</span>
                </td>
            </tr>
            <tr>
                <td class="font9">&nbsp;</td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1.Банк на платной основе и с учетом условий обслуживания Счета Клиента, предусмотренных заключенным между Банком и Клиентом договором банковского вклада (счета), предоставляет Клиенту услуги с использованием Системы, указанные в Приложении 3 к настоящему Договору.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2.Клиент может уполномочить Представителя распоряжаться денежными средствами, находящимися на его счетах, указанных в Приложении 1 к настоящему Договору. При этом, Представитель действует от имени Клиента в пределах полномочий, определенных в доверенности, составленной в соответствии с требованиями действующего законодательства и  условиями настоящего Договора, и имеет право на совершение в Системе операций, перечень которых приведен в Приложении 2 к настоящему Договору.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3.Все предусмотренные настоящим Договором операции совершаются в Системе при условии проведения их с учетом допустимости в соответствии с требованиями действующего законодательства и условиями настоящего Договора, при наличии на счете Клиента денежных средств в объеме, достаточном для совершения операции и взимания за неё Банком платы.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.4. Электронные документы передаются и принимаются с использованием Системы без их последующего представления на бумажном носителе, кроме случаев, оговоренных в п.3.2 настоящего Договора.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.5.Клиент (Представитель Клиента) может ознакомиться через Web-сайт Банка со спецификацией программно-аппаратных средств, необходимых для работы с Системой.
                </td>
            </tr>
            </table>
            <table class="textDoc" cellpadding="0" cellspacing="0">

            <tr>
                <td>
                    <table class="textDoc" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="font9">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="center">
                            <span class="titleDoc">3.&nbsp;&nbsp;УСЛОВИЯ ПРЕДОСТАВЛЕНИЯ И ОПЛАТЫ УСЛУГ</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="font9">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.Система состоит из:<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;программных и аппаратных средств, соответствующих требованиям Банка, комплектуемых и эксплуатируемых Клиентом за свой счет;<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;аппаратных средств Банка, эксплуатируемых Банком за свой счет;<br>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

     <!---------------------------------- страница3----------------------------------------->

    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;программных средств Системы, устанавливаемых в соответствующих частях на аппаратных средствах Клиента и Банка и самостоятельно эксплуатируемых Сторонами.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2.При выходе из строя аппаратных или программных средств Системы или их элементов, а также в иных случаях невозможности предоставления услуг по Договору, Банк в течение суток размещает на WEB-сайте Банка сообщение о готовности и сроках возобновления предоставления услуг. На период приостановления использования Системы совершение Клиентом (Представителем Клиента) операций по счетам Клиента осуществляется на бумажных носителях или с использованием иных, предусмотренных Договором банковского вклада (счета) систем и средств связи.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.3.В качестве подтверждения правильности составления всех обязательных реквизитов электронного расчетного документа, Клиент (Представитель Клиента) предъявляет свой пароль доступа в Систему. Аналогом собственноручной подписи электронного расчетного документа Клиента (Представителя Клиента) является  значение хэш-функции, вычисленной по всем обязательным реквизитам электронного расчетного документа и паролю Клиента (Представителя Клиента).
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.4.Стороны признают, что используемая Сторонами в соответствии с настоящим Договором система защиты информации, построенная на использовании стандартных механизмов безопасности Интернет-технологий, уменьшает, но не исключает риски, связанные с нарушением конфиденциальности и целостности информации при ее передаче через сеть Интернет, однако достаточна для обеспечения перечисления средств Клиента только на счета Клиента или счета получателей платежей, включенных Клиентом в перечень, получателей платежей, указанный в Приложении 5 к настоящему Договору.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.5.Порядок применения средств Системы предусматривает, что:<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash; При компрометации или подозрении на компрометацию пароля Клиента (Представителя Клиента) (то есть при ознакомлении или подозрении на ознакомление неуполномоченного лица с паролем, а также при несанкционированном использовании или подозрении на несанкционированное использование пароля) Банк извещается Клиентом (Представителем Клиента) передачей электронного служебного документа по Системе или иным доступным способом о прекращении действия данного пароля.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash; Банк, получив по Системе сообщение о компрометации пароля, блокирует предоставление услуги для Клиента (Представителя Клиента). Для возобновления предоставления услуги Клиент (Представитель Клиента) должен явиться в Банк для подачи заявления о возобновлении предоставления услуги и получения пин-конверта с новым паролем для доступа в Систему.
                </td>
            </tr>
            <tr>
                <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Клиент (Представитель Клиента) самостоятельно выбирает организацию - провайдера, обеспечивающую доступ к сети Интернет, и осуществляет подключение к сети Интернет за счет собственных средств. Все расходы, связанные с подключением к сети Интернет, эксплуатацией и обменом данными по Системе через сеть Интернет осуществляются Клиентом за счет собственных средств.<br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Клиент полностью несет все риски, связанные с подключением его вычислительных средств и вычислительных средств Представителя Клиента к сети Интернет. Клиент (Представитель Клиента) самостоятельно обеспечивает защиту собственных вычислительных средств от несанкционированного доступа и вирусных атак из сети Интернет.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.6.Стороны признают в качестве единой шкалы времени при работе в Системе <input type="Text" class="insertInput" style="width: 22%"> время. Контрольным является время системных часов аппаратных средств Банка.
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.Стороны установили, что моментом получения электронного документа Банком в Системе является текущее время по системным часам Банка в момент помещения информации в архив входящих сообщений.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.Банк исполняет поступившие от Клиента (Представитель Клиента) в пределах
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

     <!---------------------------------- страница4----------------------------------------->

    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            <table class="textDoc" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    операционного времени Банка электронные расчетные документы, на совершение операций по счетам Клиента, указанным в Приложении 1 к настоящему Договору, текущим рабочим днем, а поступившие в послеоперационное время &ndash; следующим рабочим днем.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;На дату заключения настоящего Договора Банком установлено следующее операционное время: в рабочие дни &ndash; с <input type="Text" class="insertInput" style="width: 5%"> ч. <input type="Text" class="insertInput" style="width: 5%"> мин. до <input type="Text" class="insertInput" style="width: 5%"> ч. <input type="Text" class="insertInput" style="width: 5%"> мин., в предвыходные и предпраздничные дни &ndash; с <input type="Text" class="insertInput" style="width: 5%"> ч. <input type="Text" class="insertInput" style="width: 5%"> мин. до <input type="Text" class="insertInput" style="width: 5%"> ч. <input type="Text" class="insertInput" style="width: 5%"> мин.
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
        <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.9.Обмен электронными документами между Клиентом (Представителем Клиента) и Банком является односторонним - Клиент (Представитель Клиента) направляет в Банк на исполнение электронные расчетные документы по счетам Клиента. Непосредственно в том же сеансе связи Клиенту (Представителю Клиента) сообщается о результате приема поручения в работу, с указанием даты его исполнения Банком (&quot;текущим рабочим днем&quot; или &quot;на следующий рабочий день&quot;). Данные сообщения носят информационный характер и не являются электронными документами. Контроль за исполнением Банком электронных расчетных документов по счетам Клиента осуществляется Клиентом (Представителем Клиента) самостоятельно путем просмотра в Системе состояния этих счетов.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.10.За услуги Банка по настоящему Договору Банком взимаются с Клиента в безакцептном порядке в валюте Российской Федерации путем списания мемориальным ордером со счета Клиента, открытого в валюте Российской Федерации, № <input type="Text" class="insertInput" style="width: 25%">  следующие платы:
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <ul type="disc">
                <li>плата за организацию расчетного обслуживания с использованием системы &ldquo;Электронная Сберкасса&rdquo; &ndash;  взимается Банком в соответствии с Тарифами Банка единовременно в день подписания Клиентом и Банком настоящего Договора и акта приема-передачи, в котором отражается факт получения Клиентом от Банка пин-конверта с паролем для доступа в Систему. Данная плата в таком же порядке взимается также в день подписания Представителем Клиента и Банком акт приема-передачи, в котором отражается факт получения Представителем Клиента от Банка пин-конверта с паролем для доступа в Систему в соответствии с Тарифами Банка;
                <li>ежемесячная плата за предоставление услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo; &ndash; взимается Банком ежемесячно по истечении каждого месяца,исчисляемого с даты подписания Сторонами настоящего Договора в соответствии с Тарифами Банка;
                <li>плата за восстановление пароля для доступа в систему &quot;Электронная Сберкасса&quot; в случае его компрометации Клиентом (Представителем Клиента) &ndash; взимается Банком единовременно в день выдачи Клиенту (Представителю Клиента) нового пин-конверта с паролем для доступа в систему &quot;Электронная Сберкасса&quot; в случае его компрометации Клиентом (Представителем Клиента) в соответствии с Тарифами Банка.
        </ul>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.11.При совершении в Системе операций по счетам Клиента, в качестве тарифов Банка на услуги, перечень которых приведен в Приложении 3 настоящего Договора, применяются публичные тарифы Банка, установленные для операций всех других клиентов, совершающих аналогичные операции при личной явке в Банк.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.12.В случае отсутствия на счете Клиента, указанного им для списания в безакцептном порядке платы за услуги Банка, денежных средств в размере, необходимом для ее списания, Банк имеет право списать ее с другого счета Клиента, открытого в валюте Российской Федерации, указанного в Перечне номеров счетов Клиента (Приложение 1 к настоящему Договору).
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.13.При отсутствии на счетах Клиента, указанных им в Перечне номеров счетов Клиента (Приложение 1 к Договору), денежных средств, в размере, необходимом для списания Банком ежемесячной платы за предоставление услуг с использованием системы &ldquo;Электронная Сберкасса&rdquo;, предоставление Клиенту (Представителю Клиента) услуг в Системе приостанавливается.
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

     <!---------------------------------- страница5----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;При этом исчисление ежемесячной платы за предоставление услуг с использованием Системы осуществляется независимо от блокировки услуг и прекращается только после расторжения Договора.
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center">
                <span class="titleDoc"><br>4.&nbsp;&nbsp;ПРАВА И ОБЯЗАННОСТИ СТОРОН</span>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.1.Банк обязуется:</b>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.1.Предоставить Клиенту и его Представителям на время действия настоящего Договора пин-конверты с паролем для доступа в Систему.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.2.Обеспечить регистрацию Клиента (Представителя Клиента) в Системе.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.3.Принимать к исполнению поступившие от Клиента (Представителя Клиента) электронные расчетные документы, оформленные и переданные в Банк в соответствии с условиями настоящего Договора.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.4.Не исполнять поступившие от Клиента (Представителя Клиента) электронные расчетные документы, оформленные с нарушением требований действующего законодательства Российской Федерации, нормативных документов Банка России и условий настоящего Договора и договоров о счете (вкладе) Клиента.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.5.Не разглашать и не передавать третьим лицам (обеспечить конфиденциальность) информацию, связанную с использованием Системы Клиентом (Представителем Клиента), за исключением случаев, предусмотренных действующим законодательством Российской Федерации и условиями настоящего Договора.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.6.Поддерживать до помещения в электронные архивы системные журналы и текущие базы переданных и принятых электронных документов на аппаратных средствах Системы в течение не менее тридцати календарных дней после их получения, а в случае возникновения споров - до их разрешения. Обеспечить сохранность архивов переданных и принятых файлов, а также системных журналов в течение срока, установленного действующим законодательством.
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table class="textDoc" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.7.В случае разработки новых версий производить обновление инструкций по использованию Системы.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.2.Банк имеет право:</b>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.1.В одностороннем порядке досрочно расторгнуть настоящий Договор в случае нарушения Клиентом (Представителем Клиента)  своих обязательств, принятых по настоящему Договору, уведомив Клиента не позднее, чем за _____________ календарных дней до даты расторжения.<br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Расторжение настоящего Договора не влечет за собой прекращение обязательств Клиента по возмещению Банку ежемесячной платы за организацию расчетного обслуживания с использованием системы "Электронная Сберкасса", возникших до даты расторжения настоящего Договора.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.2.Списывать в безакцептном порядке со счетов Клиента, указанных в Приложении 1 к настоящему Договору, суммы платы за услуги Банка.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.3.Блокировать функции предоставления в Системе услуг и совершения операций, предусмотренных настоящим Договором, с направлением Клиенту соответствующего уведомления, в случае недостаточности средств на счете Клиента на момент списания платы.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.4.При установлении возможности нарушения безопасности Системы, выявлении фактов или признаков таких нарушений, немедленно приостановить использование Системы и оповестить Клиента и его Представителей в порядке, предусмотренном в п. 3.2 настоящего Договора.
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.5.В одностороннем порядке вводить новые, изменять и дополнять действующие Тарифы на услуги Банка, порядок и сроки взимания платы. Данная информация размещается на WEB-сайте Банка в разделе "Новости", до даты вступления в силу соответствующих изменений.<br>
            </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

     <!---------------------------------- страница6----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;При этом исчисление ежемесячной платы за предоставление услуг с использованием Системы осуществляется независимо от блокировки услуг и прекращается только после расторжения Договора.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;На дату заключения настоящего Договора Банком установлены Тарифы в соответствии с Приложением 4 к настоящему Договору.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.6.Вводить ограничения или полностью прекратить предоставление Клиенту и его Представителям услуг по настоящему Договору в случае выявления попыток сканирования, атак информационных ресурсов Банка, а также иных признаков нарушения безопасности с использованием программно-аппаратных средств Клиента (Представителя Клиента) на период до прекращения действия указанных нарушений.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.3.Клиент обязуется:</b>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.1.Уплачивать Банку плату за предоставление услуг в соответствии с Тарифами, действующими на момент предоставления услуг. Клиент предоставляет Банку право списывать без распоряжения Клиента в безакцептном порядке с его счетов Клиента в плату в соответствии с Тарифами. При отсутствии у Банка возможности выполнить указание Клиента о списании платы за операцию по счету одновременно с её проведением в связи с недостаточностью денежных средств на счете, Система информирует Клиента (Представителя Клиента)  соответствующим сообщением.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.2.Одновременно с заключением настоящего Договора заключить с Банком предложенное им дополнительное соглашение к договорам банковского вклада и/или договорам банковского счета, по которым будут предоставляться услуги и списываться суммы платы за услуги Банка, предусмотренные настоящим Договором.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.3.Из-за возможности компрометации при работе в Интернет пароля Клиента (Представителя Клиента), Клиент обязуется самостоятельно урегулировать претензии с предопределенными получателями платежей по вопросам несанкционированного перечисления средств в их адрес.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>4.4.Клиент имеет право:</b>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.1.Совершать в Системе операции, а также получать посредством Системы услуги, предусмотренные Приложением 3 к настоящему Договору. При этом Клиент (Представитель Клиента) имеет право перечислять денежные средства со счетов Клиента, открытых в валюте Российской Федерации и указанных в Приложении 1 к настоящему Договору, только в адрес предопределенного Клиентом перечня получателей платежей, указанного в Приложении 5 к настоящему Договору, либо перечислять (переводить) денежные средства со счетов Клиента, указанных в Приложении 1 к настоящему Договору, на счета, открытые на имя Клиента-владельца счета. При формировании электронного расчетного документа по счету Клиента а адрес получателя платежа Клиент (Представитель Клиента) указывает только сумму и номер счета или номер (наименование) получателя платежа в соответствии с перечнем получателем, указанным в Приложении 5 к настоящему Договору.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.2.Клиент вправе изменить перечень обслуживаемых в Системе счетов Клиента, указанных в Приложении 1 к настоящему Договору, перечень своих Представителей, указанных в Приложении 2 к настоящему Договору, а также перечень получателей платежей, указанных в Приложении 5 к настоящему Договору, в адрес которых возможно перечислять денежные средства со своих счетов, открытых в валюте Российской Федерации, путем заключения Дополнительного соглашения к настоящему Договору.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.3.В случае возникновения у Клиента претензий, связанных с принятием или непринятием и/или исполнением или неисполнением электронного документа, направить в Банк мотивированное заявление о разногласии.
        </td>
    </tr>
    <tr>
        <td align="center">
            <span class="titleDoc">5.&nbsp;&nbsp;ОТВЕТСТВЕННОСТЬ СТОРОН</span>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.1.За невыполнение или ненадлежащее выполнение обязательств по настоящему Договору виновная Сторона несет ответственность в соответствии с действующим
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

     <!---------------------------------- страница7----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            законодательством РФ.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.2.В случае возникновения обстоятельств непреодолимой силы, к которым относятся стихийные бедствия, аварии, пожары, массовые беспорядки, забастовки, революции, военные действия, противоправные действия третьих лиц, вступление в силу законодательных актов, правительственных постановлений и распоряжений государственных органов, прямо или косвенно запрещающих или препятствующих осуществлению Сторонами своих функций по Договору, и иных обстоятельств, не зависящих от волеизъявления Сторон, Стороны по настоящему Договору освобождаются от ответственности за неисполнение или ненадлежащее исполнение взятых на себя обязательств.<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;При наступлении обстоятельств непреодолимой силы Сторона должна без промедления известить о них другую Сторону в порядке, предусмотренном п.3.2 настоящего Договора. Извещение должно содержать данные о характере обстоятельств, а также оценку их влияния на возможность исполнения Стороной обязательств по настоящему Договору.<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;По прекращении указанных выше обстоятельств Сторона должна без промедления известить об этом другую Сторону в порядке, предусмотренном п. 3.2 настоящего Договора. В извещении должен быть указан срок, в течение которого предполагается исполнить обязательства по настоящему Договору.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.3.В случае неоплаты Банку предоставленных услуг (в том числе по причине отсутствия достаточной суммы средств на счете(ах) Клиента или невозможности списания средств в связи с приостановлением расходных операций по счету(ам) или наложением ареста на денежные средства, находящиеся на счете(ах), Банк имеет право приостановить на срок до одного месяца предоставление услуг по настоящему Договору, а в случае неоплаты по истечении указанного срока - расторгнуть Договор в одностороннем порядке без соблюдения срока, установленного настоящим Договором. При этом Клиенту направляется письменное уведомление о расторжении настоящего Договора.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.4.Клиент несет ответственность за содержание электронных документов, переданных в Банк с использованием корректного пароля Клиента (Представителя Клиента), и за правильность заполнения и оформления электронных документов.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.5.Банк не несет ответственности за ущерб, возникший вследствие разглашения Клиентом (Представителями Клиента) собственного пароля, его утраты или его передачи, вне зависимости от причин, неуполномоченным лицам.<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Банк не несет ответственности за последствия исполнения электронного расчетного документа Клиента (Представителя Клиента), переданного в Банк с использованием корректного пароля Клиента (Представителя Клиента), в том числе в случае использования пароля и программно-аппаратных средств клиентской части Системы неуполномоченным лицом.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.6.Банк не несет ответственности за неработоспособность оборудования и программных средств третьих лиц, повлекших за собой невозможность доступа Клиента (Представителя Клиента) к банковской части Системы и возникшие в результате задержки в осуществлении платежей Клиента (Представителя Клиента).
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.7.Клиент полностью несет риски связанные с подключением его вычислительных средств и вычислительных средств Представителей Клиента к сети Интернет. Банк не несет никакой ответственности, в том числе финансовой, в случае уничтожения (в полном или частичном объеме) информации на вычислительных средствах и в случае выхода из строя самих вычислительных средств Клиента (Представителя Клиента), подключенных к сети Интернет для обеспечения предоставления услуг по настоящему Договору.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.8.Банк не несет ответственности за урегулирование претензий Клиента с предопределенными получателями платежей по вопросам несанкционированного перечисления денежных средств на счета с использованием пароля Клиента и/или его Представителей.
        </td>
    </tr>
    <tr>
        <td>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

     <!---------------------------------- страница8----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td align="center">
            <span class="titleDoc">6.&nbsp;&nbsp;СРОК ДЕЙСТВИЯ ДОГОВОРА</span>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.1.Настоящий Договор вступает в силу с момента его подписания Сторонами и действует в течение 1 года.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.2.Настоящий договор считается пролонгированным на каждый следующий срок, если ни одна из Сторон не предупредит другую Сторону о прекращении использования Системы не менее, чем за 30 дней до даты окончания срока действия настоящего Договора.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.3.Настоящий Договор может быть расторгнут досрочно любой из Сторон в одностороннем порядке с предварительным письменным уведомлением другой Стороны не менее, чем за 30 дней до даты расторжения настоящего Договора.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.4.Настоящий Договор считается расторгнутым, а обязательства Сторон прекращенными с даты расторжения и/или прекращения обязательств Сторон по договорам и дополнительным соглашениям к ним по Счетам Клиента, указанным в Приложении 1 к настоящему Договору.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.5.Настоящий Договор может быть изменен или дополнен только по взаимному согласию Сторон, за исключением случая, предусмотренного пунктом 4.2.3 настоящего Договора. Указанные изменения и дополнения оформляются в виде дополнительных соглашений к настоящему Договору, являющихся его неотъемлемой частью.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.6.Все приложения, на которые даны ссылки в тексте настоящего Договора, являются его неотъемлемыми частями.
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.7.Настоящий Договор составлен в двух, имеющих равную силу, экземплярах, один экземпляр для Клиента, один для Банка.
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td align="center">
            <span class="titleDoc">7.&nbsp;&nbsp;РЕКВИЗИТЫ СТОРОН</span>
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <b><nobr>КЛИЕНТ:</nobr></b>
                </td>
                <td width="100%">&nbsp;</td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>адрес регистрации</nobr>
                </td>
                <td width="100%">
                    <input value='${person.registrationAddress}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>адрес фактического проживания (для почтовых отправлений)</nobr>
                </td>
                <td width="100%">
                    <input value='${person.residenceAddress}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><input type="Text" class="insertInput" style="width:100%"></td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td style="font-size:12pt;">
                    <nobr>адрес&nbsp;электронной&nbsp;почты&nbsp;(E-mail)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.email}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>телефон&nbsp;(дом.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.homePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>(раб.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.jobPhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>(моб.)</nobr>
                </td>
                <td width="50%">
                    <input value='${person.mobilePhone}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>мобильный оператор</nobr>
                </td>
                <td width="50%">
                    <input value='${person.mobileOperator}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>ИНН</nobr>
                </td>
                <td width="30%">
                    <input value='${person.inn}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>гражданство</nobr>
                </td>
                <td width="70%">
                    <input value='${person.citizenship}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="center" valign="top" style="font-size:8pt;">(при его наличии)</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>вид&nbsp;документа,&nbsp;удостоверяющего&nbsp;личность,</nobr>
                </td>
                <td width="100%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>серия&nbsp;(номер)</nobr>
                </td>
                <td width="30%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>кем,&nbsp;где,&nbsp;когда&nbsp;выдан</nobr>
                </td>
                <td width="70%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>код&nbsp;подразделения&nbsp;(при&nbsp;наличии)</nobr>
                </td>
                <td width="100%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>дата&nbsp;и&nbsp;место&nbsp;рождения</nobr>
                </td>
                <td width="100%">
                    <input value='${person.birthDayString}, ${person.birthPlace}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td><b>БАНК:</b></td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    Филиал
                </td>
                <td width="50%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
                <td>
                    <nobr>структурное&nbsp;подразделение&nbsp;№</nobr>
                </td>
                <td width="50%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>почтовый&nbsp;адрес</nobr>
                </td>
                <td width="100%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    местонахождение
                </td>
                <td width="100%">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    телефон
                </td>
                <td width="100%" align="left">
                    <input type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <br>
            <br>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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
    <br style="page-break-after:always;">

    <!---------------------------------- страница9----------------------------------------->
    <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td width="50%" align="center"><b>от Банка:</b></td>
                <td align="center"><b>Клиент:</b></td>
            </tr>
            <tr>
                <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
            </tr>
            <tr>
                <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                        <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><br>&nbsp;</td>
    </tr>
    <tr>
        <td><span class="tabSpan">&nbsp;</span><b>М.П.</b></td>
    </tr>
    <tr>
        <td>
            <br>
            <br>
            <br>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="font9">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" style="height:12mm;">
            <table style="width:100%;">
                <tr>
                    <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                </tr>
                <tr>
                    <td><b><nobr>От Банка</nobr></b></td>
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

    </td>
    </tr>
    </table>

     <br style="page-break-after:always;">
     <!--------------------------------- ПРИЛОЖЕНИЕ №8_1 ---------------------------------------->

      <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:5mm;margin-right:5mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
      <col style="width:172mm">
      <tr>
      <td>

      <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc" style="vertical-align:100%">
      <!-- Шапка документа -->
      <tr>
          <td align="right">
              <b>Приложение 1</b><br><br>&nbsp;
              <span class="italic font10">к Договору о предоставлении<br>
                 услуг с использованием системы<br>
                 &ldquo;Электронная Сберкасса&rdquo;<br>
              </span>
              №<input type="Text" readonly="true" class="insertInput" style="width:8%">от&nbsp;&ldquo;<input type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
               <input type="Text" readonly="true" class="insertInput" style="width:13%">20<input type="Text" readonly="true" class="insertInput" style="width:3%">г.
          </td>
      </tr>

      <tr>
          <td align="center">
              <br><br>
              <b>ПЕРЕЧЕНЬ<br>
              номеров счетов Клиента
              </b><br>
          </td>
      </tr>

      <tr>
          <td><br><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="10%"><b>п/н</b></td>
                  <td class="docTdBorder" align="center"><b>Номера счета Клиента, по которым предоставляются Услуги,<br>
                                                  предусмотренные настоящим Договором
                                          </b>
                  </td>
              </tr>
      <% int lineNumber = 0;%>
      <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
		  <bean:define id="accountLink" name="entry" property="value"/>
		     <%lineNumber++;%>
              <tr>
                  <td class="docTdBorder" align="center">&nbsp;<%=lineNumber%>&nbsp;</td>
                  <td class="docTdBorder">&nbsp;${accountLink.number}&nbsp;</td>
              </tr>
              </table>
          </td>
      </tr>
      </logic:iterate>
      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" width="50%">Номер(а) счета(ов) Клиента, с которого(ых) в безакцептном порядке Банком производится списание платы за Услуги, предусмотренные настоящим Договором</td>
                  <td class="docTdBorder">
                      <table cellspacing="0" cellpadding="0" width="100%">
                      <tr>


                      </tr>
                      </table>
                  </td>


              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br><b>Способ доставки Банком подтверждений об исполнении операций по счету Клиента:</b></td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%"><input type="Checkbox" style="border:0 solid;"></td>
                  <td class="docTdBorder">&nbsp;SMS-сообщения</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%"><input type="Checkbox" style="border:0 solid;"></td>
                  <td class="docTdBorder">&nbsp;SMS-сообщения</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%"><input type="Checkbox" style="border:0 solid;"></td>
                  <td class="docTdBorder">&nbsp;От подтверждений отказываюсь</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br><br>
              <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
              <tr>
                  <td width="50%" align="center"><b>от Банка:</b></td>
                  <td align="center"><b>Клиент:</b></td>
              </tr>
              <tr>
                  <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                  <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
              </tr>
              <tr>
                  <td style="padding-right:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                  <td style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
              </tr>
              <tr>
                  <td colspan="2">
                      <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                      <tr>
                          <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                          <td width="25%" style="padding-right:8;"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                          <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                          <td width="25%"><nobr>(<input type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                      </tr>
                      </table>
                  </td>
              </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td><span class="tabSpan">&nbsp;</span><b>М.П.</b></td>
      </tr>
      <tr>
          <td valign="bottom" style="height:100%;">
              <table style="width:100%;height:12mm;">
                  <tr>
                      <td colspan="4" class="font10" align="center">Договор №<input type="Text" readonly="true" class="insertInput" style="width:18%"> от "<input type="Text" readonly="true" class="insertInput" style="width:4%">" <input type="Text" readonly="true" class="insertInput" style="width:12%">200<input type="Text" readonly="true" class="insertInput" style="width:2%"> г.</td>
                  </tr>
                  <tr>
                      <td><b><nobr>От Банка</nobr></b></td>
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

      </td>
      </tr>
      </table>

      <br style="page-break-after:always;">
      <!--------------------------------- ПРИЛОЖЕНИЕ №8_2 ---------------------------------------->



      <br style="page-break-after:always;">
      <!--------------------------------- ПРИЛОЖЕНИЕ №8_3 ---------------------------------------->



      <br style="page-break-after:always;">
      <!--------------------------------- ПРИЛОЖЕНИЕ №8_4 ---------------------------------------->



 </html:form>
</body>
</tiles:put>
</tiles:insert>
