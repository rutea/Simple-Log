var loadOnStart = 0;

$(function() {
	if(loadOnStart == 1){
		loadData();
	}
	initFuncs();
	initDatePicker();
	rollToButtom();
});

function initFuncs() {
	$("#srhBtn").click(function() {
		clearTable();
		loadData();
	});
	$("#addBtn").click(function() {
		openAdd();
	});
	$("#cfmBtn").click(function() {
		if ($("#sbmUrl").val() == "add") {
			addData();
		} else {
			updateData();
		}
	});
	$("#cleBtn").click(function() {
		$("#editDiv").hide();
		$("#maskDiv").remove();
	});
	$("#yesBtn").click(function() {
		deleteData();
	});
	$("#noBtn").click(function() {
		$("#cfmDiv").hide();
		$("#maskDiv").remove();
	});
	$("#loadMore").click(function() {
		loadData();
	});
}

function clearTable() {
	var trs = $("#dataTable tr:not([id='headTr'])");
	for (var i = 0; i < trs.length; i++) {
		trs[i].remove();
	}
	$("#startIndex").val(0);
}

function initDatePicker() {
	$(".form-date").datetimepicker({
		language : "zh-CN",
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		format : "yyyy-mm-dd"
	});
}

function rollToButtom() {
	var win = $(window);
	win.scroll(function() {
		if ($(document).height() - win.height() == win.scrollTop()) {
			if ($("#maskDiv") == null || $("#maskDiv").length == 0) {
				loadData();
				$("#srhBtn").attr("disabled", "disabled");
			}
		} else {
			$("#srhBtn").removeAttr("disabled");
		}
	});
}

function loadData() {
	$.ajax({
		url : "query",
		type : "POST",
		data : $("#form").serialize(),
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.items.length == 0) {
				$("#loadMore").hide();
				return;
			}
			if (data.lastPage == true) {
				$("#loadMore").hide();
			} else {
				$("#loadMore").show();
			}
			var index = $("#startIndex").val();
			index = Number(index);
			index += Number(data.items.length);
			$("#startIndex").val(index);
			for (var i = 0; i < data.items.length; i++) {
				var log = data.items[i];
				var dataTd = $("<tr style=\"display:none;\"><td>"
						+ log.time
						+ "</td><td>"
						+ log.log
						+ "</td><td>"
						+ "<span class=\"ctlspn text-danger icon-pencil\" onclick=\"openEdit(this,"
						+ log.id
						+ ");\"></span>"
						+ " "
						+ "<span class=\"ctlspn text-danger icon-trash\" onclick=\"openDel("
						+ log.id + ");\"></span>" + "</td></tr>");
				$("#dataTable").append(dataTd);
				dataTd.show(1500);
			}
		},
		error : function(data) {
		}
	});
}

function addData() {
	$.ajax({
		url : "add",
		type : "POST",
		data : $("#editForm").serialize(),
		dataType : "json",
		async : true
	});
	$("#editDiv").hide();
	afterEdit();
}

function afterEdit() {
	$("#maskDiv").remove();
	clearTable();
	loadData();
}

function openAdd() {
	$("#editId").val("");
	$("#editDate").val("");
	$("#editLog").val("");
	$("#sbmUrl").val("add");

	$("#editDate").removeAttr("disabled");
	openIpt();
}

function openEdit(obj, id) {
	var trs = $(obj).parent().parent().find("td");
	$("#editId").val(id);
	$("#editDate").val($(trs[0]).text());
	$("#editLog").val($(trs[1]).text());
	$("#sbmUrl").val("update");

	$("#editDate").attr("disabled", "disabled");
	openIpt();
}

function openIpt() {
	addMask();
	$("#editDiv").css({
		"display" : "inline",
		"z-index" : 99999
	});
}

function addMask() {
	var maskDiv = "<div id=\"maskDiv\" style=\"width:"
			+ document.body.scrollWidth + "px;height:"
			+ document.body.scrollHeight + "px;\"></div>";
	$("body").append(maskDiv);
}

function openDel(id) {
	addMask();
	$("#delId").val(id);
	$("#cfmDiv").css({
		"display" : "inline",
		"z-index" : 99999
	});
}

function updateData() {
	$.ajax({
		url : $("#sbmUrl").val(),
		type : "POST",
		data : $("#editForm").serialize(),
		dataType : "json",
		async : true
	});
	$("#editDiv").hide();
	afterEdit();
}

function deleteData() {
	var id = $("#delId").val();
	$.ajax({
		url : "delete",
		type : "POST",
		data : {
			"id" : id
		},
		dataType : "json",
		async : true
	});
	$("#cfmDiv").hide();
	afterEdit();
}

function clean(obj) {
	$(obj).val("");
}