<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script type="text/javascript">
	function updateStyle(elem)
	{
		var index = elem.selectedIndex;
		var selectedOption = elem.options[index];
		elem.style.color = selectedOption.style.color;
	}

	function initServicesTable(access)
	{
		var elems = document.getElementsByName(access + '.selectedServices');
		for (var i = 0; i < elems.length; i++)
		{
			setAccessDescription(elems[i]);
		}
	}

	function setAccessDescription(el)
	{
		getLabel(el).innerHTML = (el.checked ? "разрешен" : "запрещен");
	}

	function onAccessChanged(access)
	{
		var checkbox = document.getElementById(access + "_enabled");
		if (checkbox == null)
			return;
		forEach(checkbox.form.elements,
				function(e)
				{   
					e.disabled = !checkbox.checked;
				},
				function(e)
				{
					if (e == checkbox)
						return false;
					if (e.name == null) return false;
					return e.name.match(access)
				}
				);
	}
	function disableAll(access)
	{
		var checkbox = document.getElementById(access + "_enabled");
		if (checkbox == null)
			return;
		forEach(checkbox.form.elements,
				function(e)
				{
					e.disabled = true;
				},
				function(e)
				{
					if (e == checkbox)
						return false;

					return e.name.match(access)
				}
				);
	}

	var updateScheme;
	function onSchemeChanged(select)
	{
		var namePrefix = select.name.split('.')[0];
		var option = select.options[select.selectedIndex];
		var optionId = option.id;
		var category;
		if (optionId != "")
		{
			category = optionId.substring(optionId.length - 1, optionId.length);
		}
		else
		{
			category = "none";
		}
		setElement(namePrefix + ".category", category);
		updateScheme = false;
		disableServices(namePrefix, category, true);
		updateScheme = true;
	}

	function disableServices(namePrefix, exceptCategory, clearDisabled)
	{
		var rx = "_" + exceptCategory + "_chk$";
		var checkboxes = document.getElementsByName(namePrefix + ".selectedServices");
		for (var i = 0; i < checkboxes.length; i++)
		{
			var chk = checkboxes[i];
			var d = !chk.id.match(rx);
			chk.disabled = d;
			if (d & clearDisabled)
			{
				chk.checked = false;
				chk.onclick();
			}
		}
	}

	function onServiceClick(chk, category)
	{
		var namePrefix = chk.name.split('.')[0];
		getLabel(chk).innerHTML = (chk.checked ? 'разрешен' : 'запрещен');

		if (updateScheme)
		{
			var sel = getElement(namePrefix + '.accessSchemeId');
			sel.value = 'personal' + category;
			sel.onchange();
		}
	}

	function turnSelection(isSelectAll, listName, event)
	{
		//получаем имя выбранной категории и имя контрола для выбора
		var index = listName.indexOf('^');
		if(index==-1)
		{
			var ids = document.getElementsByName(listName);
			turnSelectionAlg(ids, isSelectAll, event);
		}
		var selectedName = listName.substring(index+1,listName.length);
		var exceptCategory = listName.substring(0,index);
		//получаем все чекбоксы
		var ids = document.getElementsByName(selectedName);
		var rx = "_" + exceptCategory + "_chk$";
		var k =0;
		var selectedIds = new Array();
		//отбираем только нужную категорию
		for (var i = 0; i < ids.length; i++)
		{
			var checkBox = ids[i];
			var d = checkBox.id.match(rx);
			if(d)
			{
				selectedIds[k] = checkBox;
				k++;
			}
		}
		//меняем
		turnSelectionAlg(selectedIds, isSelectAll, event);
	}

    function clearServiceRadioButton(helperCategory)
    {
         if (helperCategory == 'E')
         {
             document.getElementById("A").checked = false;
         }
         else
         {
             document.getElementById("E").checked = false;
         }
    }
</script>