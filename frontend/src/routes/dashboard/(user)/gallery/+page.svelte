<script>
	import Artwork from "../../../../lib/components/dashboard/(user)/gallery/Artwork.svelte";

  const loadArtworks = async () => {
    let headers = new Headers();
	  headers.set('Authorization', `Basic ${btoa('admin@mail.museum.com:admin')}`);
	  const res = await fetch('http://localhost:8081/artworks', { headers: headers });
	  const artworks = await res.json();
	  return { artworks };
  };

  let hideUnloanable = false;
</script>

<div class="container">
  {#await loadArtworks()}
  <p>Loading</p>
  {:then data}
    <input type="checkbox" name="hideUnloanable" id="hideUnloanable" bind:checked={hideUnloanable}>
    <label for="hideUnloanable">Hide unloanable items</label>
    <div class="artworks">
      
      {#if data.artworks.length === 0}
        <p>No artworks yet</p>
      {/if}
      {#each data.artworks as artwork}
      {#if artwork.available || !artwork.available && !hideUnloanable }
        <Artwork {artwork}/>
      {/if}
      {/each}
    </div>
  {/await}
</div>

<style>
  .container {
    width: 100%;
    background-color: #F3F5FA;
    padding: 8rem;
  }

  .artworks {
    display: flex;
    flex-wrap: wrap;
    gap: 8rem;
  }

  #hideUnloanable {
    margin-bottom: 2rem;
  }
</style>