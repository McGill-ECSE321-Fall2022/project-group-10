<script>
	import Artwork from "../../../../lib/components/dashboard/(user)/gallery/Artwork.svelte";
  import InfoModal from "../../../../lib/components/dashboard/(user)/gallery/InfoModal.svelte";

  const loadArtworks = async () => {
    let headers = new Headers();
	  headers.set('Authorization', `Basic ${btoa('admin@mail.museum.com:admin')}`);
	  const res = await fetch('http://localhost:8081/artworks', { headers: headers });
	  const artworks = await res.json();
	  return { artworks };
  };

  let hideUnloanable = false;

  // Pop up a modal with the selected artwork
  let selectedArtwork = null;
  let showModal = false;
  const handleSelectArtwork = (event) => {
    selectedArtwork = event.detail.selectedArtwork;
    showModal = true;
  }
</script>

{#if showModal}
  <div class="overlay">
    <InfoModal {selectedArtwork}/>
  </div>
{/if}
<div class="container {showModal ? "disabled" : ""}">
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
        <Artwork {artwork} on:selectArtwork={handleSelectArtwork}/>
      {/if}
      {/each}
    </div>
  {/await}
</div>


<style>
  /* Have a cover over everything when the modal is open */
  .overlay {
    height: 100%;
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.2);
    z-index: 99999;
  }

  .overlay ~ .container {
    overflow: hidden;
  }

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