<script>
  export let data;
  console.log(data.artworks);
</script>

<h1>Artworks</h1>
<a href="/artworks/new">Create new artwork</a>
{#each data.artworks as artwork}
  <p>{artwork.title}</p>
{/each}