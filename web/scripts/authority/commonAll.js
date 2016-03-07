/**�˵���ת**/
function rightMain(url){
	$('#rightMain').attr('src', url);
}

/** �첽���ض����б� * */
function getFyDhListByFyXqCode() {
	var fyXq = $("#fyXq").val();
	if (fyXq == "" || fyXq == null) {
		$("#fyDh").html('<option value="">--��ѡ��--</option>');
	} else {
		/** �첽���ض����б� * */
		$.ajax({
			type : "POST",
			url : "getFyDhListByFyXqCode.action",
			data : {
				"fyXqCode" : fyXq
			},
			dataType : "json",
			success : function(data) {
				// ����������ݲ�Ϊ�գ����ġ���Դ��Ϣ��
				if (data == null || data == '') {// ���Ϊ��
					alert("��С�������޶����б�����ϵ\n����Աά������Ŷ������");
					$("#fyDh").html('<option value="">--��ѡ��--</option>');
				} else {
					var str = '<option value="">--��ѡ��--</option>';
					// �����ص����ݸ���zTree
					for (var i = 0; i < data.length; i++) {
						str += '<option value="' + data[i].weiduID + '">'
								+ data[i].weiduName + '</option>';
					}
					// alert(str);
					$("#fyDh").html(str);
				}
			}
		});
	}
}

/*
 * �Ƿ�ȫѡ
 */
function selectOrClearAllCheckbox(obj) {
	var checkStatus = $(obj).attr("checked");
	if (checkStatus == "checked") {
		$("input[type='checkbox']").attr("checked", true);
	} else {
		$("input[type='checkbox']").attr("checked", false);
	}
}

/** ���ں������Ӽ��죬������ **/
function getNextDay(dd, dadd) {
	// ���Լ��ϴ�����
	var a = new Date(dd);
	a = a.valueOf();
	a = a + dadd * 24 * 60 * 60 * 1000;
	a = new Date(a);
	var m = a.getMonth() + 1;
	if (m.toString().length == 1) {
		m = '0' + m;
	}
	var d = a.getDate();
	if (d.toString().length == 1) {
		d = '0' + d;
	}
	return a.getFullYear() + "-" + m + "-" + d;
}

/** table�����ͣ��ɫ* */
$(function() {
	// �������Ƶ�����ʱ��ִ�к���
	
	
	
	$(".table tr").mouseover(function() {
		$(this).css({background : "#CDDAEB"});
		$(this).children('td').each(function(index, ele){
			$(ele).css({color: "#1D1E21"});
		});
	}).mouseout(function() {
		$(this).css({background : "#FFF"});
		$(this).children('td').each(function(index, ele){
			$(ele).css({color: "#909090"});
		});
	});
});
