<script>
	import Artwork from "../../../../lib/components/dashboard/(user)/gallery/Artwork.svelte";

  const loadArtworks = async () => {
    let headers = new Headers();
	  headers.set('Authorization', `Basic ${btoa('admin@mail.museum.com:admin')}`);
	  const res = await fetch('http://localhost:8081/artworks', { headers: headers });
	  const artworks = await res.json();
	  return { artworks };
  };
</script>

<div class="container">
  
  {#await loadArtworks()}
  <p>Loading</p>
  {:then data}
    {#each data.artworks as artwork}
      <Artwork {artwork}/>
    {/each}
  {/await}
</div>

<style>
  .container {
    width: 100%;
    background-color: #F3F5FA;
    padding: 8rem;
  }
</style>