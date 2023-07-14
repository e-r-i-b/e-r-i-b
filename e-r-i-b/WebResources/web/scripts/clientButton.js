var clientButtons = new Array();

//������� ����� ������ ClientButton
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
    this.onClickFunction    = onClickFunction; //������� ������� ������ ����������� ��� ����� �� ������

    this.click = function (){
        if (this.onClickFunction != null)
            this.onClickFunction();
    }
}
