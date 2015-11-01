function toCurrency(data, type, full) {
	return '&euro; ' + data;
}
function toHumanBoolean(data, type, full) {
	return '<center>' + (data == true ? 'Yes' : 'No') + '</center>';
}
function toOperation(data, type, full) {
	return '<center><a	href=\'/product/'
			+ data
			+ '/edit\'><span	class=\'glyphicon glyphicon-edit\'></span></a> &nbsp; &nbsp; <a href=\'/product/'
			+ data
			+ '/delete\'><span	class=\'glyphicon glyphicon-trash\'></span></a></center>';
}
$(document).ready(function() {
	if ($('#price').length) {
		$('#price').mask('00000000.00', {
			reverse : true
		});
	}
});