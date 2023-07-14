<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/synchronize/deposit_settings" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="dictionariesEdit"/>
        <tiles:put name="submenu" type="string" value="DepositSettings"/>
        <tiles:put name="pageName" type="string" value="��������� ������� ��� ��������"/>
        <tiles:put name="pageDescription" value="����������� ������ ����� ��� ��������� �������� �������."/>
        <tiles:put name="formAlign"  value="center"/>
        <tiles:put name="data" type="string">

            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.depositproduct.uploading.mode"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="fieldType" value="radio"/>
                    <tiles:put name="selectItems" value="ALL@��� ����|OPTIONALLY@������� ������ ����� ������� ��� ��������:"/>
                    <tiles:put name="onclick" value="switchProductNumbersTable();"/>
                </tiles:insert>
            </table>

            <%--��������� ������ �������� ���� ������� "ALL"--%>

            <c:choose>
                <c:when test="${showCheckbox == 'true'}">
                    <div class="propertySplitter">
                        <input type="checkbox" id="replicationSelect" value="com.rssl.iccs.depositproduct.kinds.allowed.uploading" name="selectedProperties" style="display: none;"/>
                        <label for="replicationSelect" id="replicationSelectLabel" style="display: none;"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
                </c:when>
                <c:otherwise>
                    <div id="titleRow">
                        <p><b>��������� ��������� ������ �������:</b></p>
                    </div>
                    <div id="buttonsRow" align="right">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.add"/>
                            <tiles:put name="commandHelpKey" value="button.add"/>
                            <tiles:put name="bundle" value="dictionariesBundle"/>
                            <tiles:put name="onclick">addNumber();</tiles:put>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove"/>
                            <tiles:put name="bundle" value="dictionariesBundle"/>
                            <tiles:put name="onclick">removeNumbers();</tiles:put>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                        </tiles:insert>
                    </div>
                    <div class="clear">&nbsp;</div>
                </c:otherwise>
            </c:choose>
            <div id="numbersRow">
                <div id="cardSynchronizeSettingsDiv" style="display: none;">
                    �� ������ ������ ��� ��������
                </div>
                <table id="numbersTable" class="standartTable" cellspacing="0" cellpadding="0" style="display: none;">
                    <tbody>
                        <tr>
                            <c:if test="${showCheckbox != 'true'}">
                                <th>
                                    <input type="checkbox" id="commonCheckbox" checked="false" onclick="checkAll();"/>
                                </th>
                            </c:if>
                            <th>��� ���� ������</th>
                            <th>���� �������� ������</th>
                        </tr>
                    </tbody>
                </table>
            </div>
            <c:if test="${showCheckbox == 'true'}"></div></c:if>

        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false"
                          operation="EditDepositSynchronizeSettingsOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="dictionariesBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        var wrongNumberMessage = "��������� ���� ��� ������������� ���������� ���� ������. ����������, ������� ������ ���."
        doOnLoad(function() {
            init();
            switchProductNumbersTable();
        });

        function init()
        {
            loadNumbers();
            drawTableOnLoad();
            initData();
        }

        function switchProductNumbersTable()
        {
            var radioValue = getRadioValue(document.getElementsByName('field(com.rssl.iccs.depositproduct.uploading.mode)'));
            var hide = (radioValue == 'ALL');
            <c:if test="${showCheckbox != 'true'}">
                ensureElement('titleRow').style.display = hide ? "none" : "";
                ensureElement('buttonsRow').style.display = hide ? "none" : "";
            </c:if>
            ensureElement('numbersRow').style.display = hide ? "none" : "";
            ensureElement('replicationSelect').style.display = hide ? "none" : "";
            ensureElement('replicationSelectLabel').style.display = hide ? "none" : "";
        }

        //��� � ���������
        function number(kind, subkinds)
        {
            this.kind = kind;
            this.subkinds = subkinds;
        }

        var numbers = new Array(); //������ ���� ����� � ���������
        var editingNumber = null; //������������� ��� � ���������
        //�������������� �������� ������� ����� � �������� � ����� � js-������ numbers. ���������� �� onLoad.
        function loadNumbers()
        {
            numbers = new Array();
            <c:forEach var="entry" items="${form.numbers}">
                var subkinds = '';
                var isFirst = true;
                <c:forEach var="subkind" items="${entry.value}">
                    if(!isFirst)
                        subkinds += ',';
                    subkinds += '${subkind}';
                    isFirst = false;
                </c:forEach>
                numbers.push(new number(${entry.key}, subkinds));
            </c:forEach>
        }

        //��������� ��� ������� �� �������.
        function drawTableOnLoad()
        {
            for(var i = 0; i < numbers.length; ++i)
                drawRow(numbers[i].kind, numbers[i].subkinds);
            if (numbers.length == 0)
                setTableOrDivStyle("none", "block");
            else
                setTableOrDivStyle("block", "none");
            <c:if test="${showCheckbox != 'true'}">
                ensureElement("commonCheckbox").checked = false;
            </c:if>
        }

        //��������� ������ � �������
        function drawRow(kind, subkinds)
        {
            var table = document.getElementById("numbersTable");
            var tbody = table.getElementsByTagName('tbody')[0];

            var row = document.createElement("tr");
            tbody.appendChild(row);
            row.id = "number"+kind;

            <c:if test="${showCheckbox != 'true'}">
                //1. checkbox (�� ���������� �� ����� ����������)
                var td = document.createElement("td");
                row.appendChild(td);
                td.innerHTML = "<input name='chkName' type='checkbox' id='chkNumber" + kind + "'/>";
            </c:if>

            //2. ���
            td = document.createElement("td");
            td.className = "kind";
            td.align = "center";

            <c:choose>
                <c:when test="${showCheckbox != 'true'}">
                    var a = document.createElement("a");
                    a.innerHTML = kind;
                    a.href = "#";
                    a.onclick = function(){
                        for(var i = 0; i < numbers.length; ++i)
                            if(numbers[i].kind == kind)
                            {
                                editingNumber = numbers[i];
                                break;
                            }
                        openEditNumberWindow(true);
                    };
                    td.appendChild(a);
                </c:when>
                <c:otherwise>
                    td.innerHTML = kind;
                </c:otherwise>
            </c:choose>

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "kinds[]";
            input.value = kind;
            td.appendChild(input);

            row.appendChild(td);

            //3. �������
            td = document.createElement("td");
            td.className = "subkinds";
            td.align = "center";
            td.innerHTML = (subkinds == '')?'&nbsp;':subkinds;

            input = document.createElement("input");
            input.type = "hidden";
            input.name = "subkinds[]";
            input.value = subkinds;
            td.appendChild(input);

            row.appendChild(td);

            <c:if test="${showCheckbox != 'true'}">
                ensureElement("commonCheckbox").checked = false;
            </c:if>
        }

        //��������� ������ � �������
        function redrawRow(kind, subkinds)
        {
            var tdSubkinds = findChildByClassName(document.getElementById("number"+kind), "subkinds");
            tdSubkinds.innerHTML = (subkinds == '')?'&nbsp;':subkinds;

            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "subkinds[]";
            input.value = subkinds;
            tdSubkinds.appendChild(input);
        }

        //update: true - ��������������, false - ����������
        function openEditNumberWindow(update)
        {
            var url = "${phiz:calculateActionURL(pageContext,'/dictionaries/synchronize/card_settings/edit.do')}" + "?update=" + update;
            var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                    ",width=1000" +
                    ",height=0" + (screen.height - 750) +
                    ",left=0" + ((screen.width) / 2 - 450) +
                    ",top=0" + 100;
            window.open(url, "EditCardSynchronizeSettings", winpar);
        }

        //��������� ���� ����������
        function addNumber()
        {
            openEditNumberWindow(false);
        }

        //��������� ���� ��������������
        function editNumber(kind){
            for(var i = 0; i < numbers.length; ++i)
                if(numbers[i].kind == kind)
                {
                    editingNumber = numbers[i];
                    break;
                }
            openEditNumberWindow(true);
        }

        //������� ��������� ���� �� ������� � �� �������.
        function removeNumbers()
        {
            checkIfOneItem("chkName");
            if(!checkSelection("chkName", "��� �������� ���� ������ �������� ���� ��� ��������� �������."))
                return;

            var tmp = numbers;
            for(var i = numbers.length - 1; i >= 0; --i)
            {
                var kind = numbers[i].kind;
                var chkbx = ensureElement("chkNumber"+kind);
                if(chkbx.checked)
                {
                    var rem = document.getElementById("number"+kind);
                    rem.parentNode.removeChild(rem);
                    tmp.splice(i, 1);
                }
            }
            numbers = tmp;
            if (numbers.length == 0)
                setTableOrDivStyle("none", "block");
            else
                setTableOrDivStyle("block", "none");
            ensureElement("commonCheckbox").checked = false;
        }

        //��� ��������� ������� ���������� �� editCardSynchronizeSettings.jsp
        function getEditingNumber()
        {
            var tmp = editingNumber;
            editingNumber = null;
            return tmp;
        }

        /**
         * ���������/��������� ��� � ������� � � �������.
         * @param editingNumber ������ � ����� � ���������
         * @param update true - ��������������, false - ����������
         * @return 0 ��� �������� ���������� ������
         * -1 ���� ��� ������ �������� ��������� (com.rssl.iccs.cards.kinds.allowed.downloading)
         * -2 ��� ������� �������� ��� ������������ ���
         */
        function addOrUpdateNumber(editingNumber, update)
        {
            var isNew = true;
            var kind = editingNumber.kind;
            var subkinds = editingNumber.subkinds;

            if(kind >= ${form.minCode} && kind <= ${form.maxCode})
                return -1;

            for(var i = 0; i < numbers.length; ++i)
                if(numbers[i].kind == kind)
                {
                    if(!update)
                        return -2;

                    numbers[i].subkinds = subkinds;
                    isNew = false;
                    break;
                }

            if(isNew)
            {
                numbers.push(new number(kind, subkinds));
                drawRow(kind, subkinds);
                setTableOrDivStyle("block", "none");
            }
            else
                redrawRow(kind, subkinds);
            return 0;
        }

        function checkAll()
        {
            var checkboxes = document.getElementsByName("chkName");
            for (var i = 0; i < checkboxes.length; i++)
                checkboxes[i].checked = ensureElement("commonCheckbox").checked;
        }

        function setTableOrDivStyle(styleTable, styleDiv)
        {
            var tableOrDiv = ensureElement("numbersTable");
            tableOrDiv.style.display = styleTable;
            tableOrDiv = ensureElement("cardSynchronizeSettingsDiv");
            tableOrDiv.style.display = styleDiv;
        }

        function getPageTitle()
        {
            return "�������� ��� (������) ������ ��� ��������";
        }

        var superReinitFields = window.reinitField;
        this.reinitField = function(){
            var trArray = document.getElementById('numbersTable').getElementsByTagName('tr');
            for(var i = trArray.length-1; i>0; --i){
                trArray[i].parentNode.removeChild(trArray[i]);
            }
            init();
            switchProductNumbersTable();
        };



    </script>
</html:form>