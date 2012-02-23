<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function goSearch() {
	location.href = "/content/search.htm";
}

function update() {
	location.href = "/content/update.htm?id=${content.id}";
}

</script>

<div class="container form-horizontal">
<fieldset>
	<legend>컨텐츠 정보</legend>

	<div class="control-group">
		<label class="control-label" for="id">ID</label>
		<div class="controls">
			<input id="id" name="id" value="${content.id}" readonly="readonly">
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="name">이름</label>
		<div class="controls">
			<input id="name" name="name" value="${content.name}" readonly="readonly">
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="description">설명</label>
		<div class="controls">
			<textarea id="description" name="description" readonly="readonly">${content.description}</textarea>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">생성일</label>
		<div class="controls">
			<input readonly="readonly" value='<fmt:formatDate value="${content.createTime}" pattern="yyyy/MM/dd" />'/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">수정일</label>
		<div class="controls">
			<input readonly="readonly" value='<fmt:formatDate value="${content.updateTime}" pattern="yyyy/MM/dd" />'/>
		</div>
	</div>

	<div class="form-actions">
		<button class="btn btn-primary" onclick="update();">수정</button>
		<button class="btn" onclick="goSearch();">목록</button>
	</div>

</fieldset>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>