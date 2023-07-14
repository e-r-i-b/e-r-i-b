var clientButtons = new Array();

//Создает новую кнопку ClientButton
function createClientButton(clientId, clientText, onClickFunction)
{
	var button = new ClientButton(clientId, clientText, onClickFunction);
    clientButtons[clientId] = button;
	return button;
}

function ClientButton(clientId, commandText, onClickFunction)
{
	this.id                 = clientId;
	this.commandText        = commandText;
    this.onClickFunction    = onClickFunction; //функция которая должна выполняться при клике на кнопку

    this.click = function (){
        if (this.onClickFunction != null)
            this.onClickFunction();
    }
}
