<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function cancel() {
	window.location = "/content/search.htm";
}

</script>

<div class="container">
<form method="post" id="vForm" name="vForm" class="form-horizontal" action="/content/create.htm">
<fieldset>
	<legend>컨텐츠 정보</legend>

	<div class="control-group">
		<label class="control-label" for="name">이름</label>
		<div class="controls">
			<input id="name" name="name" value="${content.name}">
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="description">설명</label>
		<div class="controls">
			<textarea id="description" name="description">${content.description}</textarea>
		</div>
	</div>

	<div class="form-actions">
		<button type="submit" class="btn btn-primary">저장</button>
		<button type="button" class="btn" onclick="cancel();">취소</button>
	</div>

</fieldset>
</form>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>