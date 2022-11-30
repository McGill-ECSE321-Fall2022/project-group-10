<script>
</script>

<h1>Create new artwork</h1>
<form method="POST" action="http://localhost:8081/artworks">
  <input type="text" name="title" id="title" >
  <input type="text" name="author" id="author">
  <input type="text" name="description" id="description">
  <input type="text" name="imageLink" id="imageLink">
  <input type="number" name="price" id="price" step="0.01">
  <input type="date" name="creationDate" id="creationDate">
  <button>Submit</button>
</form>