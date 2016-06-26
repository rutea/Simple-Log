<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false"%>
<%@ include file="/common/header.jsp"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Live</title>
</head>
<body>
	<div id="mainDiv">
		<div id="searchDiv">
			<div id="conditionsDiv">
				<form id="form">
					<div id="iptDiv">
						<input type="text" class="form-control" name="log" id="ipt"
							ondblclick="clean(this);">
					</div>
					<div id="dateDiv">
						<input type="text" name="startDate" id="startDate"
							class="form-control form-date" ondblclick="clean(this);">
						<input type="text" name="endDate" class="form-control form-date"
							style="margin-left: 15px;" ondblclick="clean(this);">
					</div>
					<input type="hidden" name="startIndex" id="startIndex" value="0">
					<input type="hidden" name="pageSize" value="10">
				</form>
			</div>
			<div id="srhBtnDiv">
				<input type="button" class="btn btn-warning" id="srhBtn" value="查询">
				<input type="button" class="btn btn-success" id="addBtn" value="新增">
			</div>
		</div>
		<div id="dataDiv">
			<table id="dataTable" class="table table-hover">
				<tr id="headTr">
					<th width="30%">时间</th>
					<th width="60%">记录</th>
					<th>操作</th>
				</tr>
			</table>
		</div>
		<div id="editDiv" style="display: none;">
			<div id="editIpt">
				<input type="hidden" name="sbmUrl" id="sbmUrl">
				<form name="editForm" id="editForm">
					<input type="hidden" name="editId" id="editId">
					<table id="iptTable">
						<tr>
							<td><label>时间：</label></td>
							<td><input type="text" name="editDate" id="editDate"
								class="form-control form-date" ondblclick="clean(this);">
							</td>
						</tr>
						<tr style="margin-top: 10px;">
							<td valign="top"><label>记录：</label></td>
							<td><textarea class="form-control" name="editLog"
									id="editLog" style="width: 300px; height: 200px; resize: none;"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div>
				<input type="button" class="btn btn-primary" id="cfmBtn" value="确认">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
					type="button" class="btn" id="cleBtn" value="取消">
			</div>
		</div>
		<div id="cfmDiv" style="display: none;">
			<input type="hidden" id="delId">
			<div style="margin-top: 35px;">
				<input type="button" class="btn btn-primary" id="yesBtn" value="确认">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
					type="button" class="btn" id="noBtn" value="取消">
			</div>
		</div>
		<div id="loadMore">加载更多</div>
		<div id="buttomDiv">Ami @2016</div>
	</div>
</body>
</html>