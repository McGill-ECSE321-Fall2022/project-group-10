<script>
	import Artwork from "../../../../lib/components/dashboard/(user)/gallery/Artwork.svelte";
  import InfoModal from "../../../../lib/components/dashboard/(user)/gallery/InfoModal.svelte";
  import ShoppingCart from "../../../../lib/components/dashboard/(user)/gallery/ShoppingCart.svelte";
  import {apiCall} from '$lib/scripts/restApi.js'

  const loadArtworks = async () => {
    const artworksRes = await apiCall('GET','artworks');
    let artworks = artworksRes.data;
	  return artworks;
  };

  let hideUnloanable = false;

  // Pop up a modal with the selected artwork
  let selectedArtwork = null;
  let showArtworkModal = false;
  let showShoppingCartModal = false;

  const handleSelectArtwork = (event) => {
    selectedArtwork = event.detail.selectedArtwork;
    showArtworkModal = true;
  }

  const handleCloseArtworkModal = () => {
    showArtworkModal = false;
  }

  const handleClickShoppingCart = (event) => {
    showShoppingCartModal = true;
  }

  const handleCloseShoppingCartModal = () => {
    showShoppingCartModal = false;
  }

</script>

<div class="gallery-header-div" >
  <h1>Browse Artworks</h1>
  <button id="shopping-cart-button" on:click={handleClickShoppingCart}>
    <div>
      <svg style="fill:white; margin-bottom:0.3rem;" width="3rem" height="3rem" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
        <path d="M 5 7 C 4.449219 7 4 7.449219 4 8 C 4 8.550781 4.449219 9 5 9 L 7.21875 9 L 9.84375 19.5 C 10.066406 20.390625 10.863281 21 11.78125 21 L 23.25 21 C 24.152344 21 24.917969 20.402344 25.15625 19.53125 L 27.75 10 L 11 10 L 11.5 12 L 25.15625 12 L 23.25 19 L 11.78125 19 L 9.15625 8.5 C 8.933594 7.609375 8.136719 7 7.21875 7 Z M 22 21 C 20.355469 21 19 22.355469 19 24 C 19 25.644531 20.355469 27 22 27 C 23.644531 27 25 25.644531 25 24 C 25 22.355469 23.644531 21 22 21 Z M 13 21 C 11.355469 21 10 22.355469 10 24 C 10 25.644531 11.355469 27 13 27 C 14.644531 27 16 25.644531 16 24 C 16 22.355469 14.644531 21 13 21 Z M 13 23 C 13.5625 23 14 23.4375 14 24 C 14 24.5625 13.5625 25 13 25 C 12.4375 25 12 24.5625 12 24 C 12 23.4375 12.4375 23 13 23 Z M 22 23 C 22.5625 23 23 23.4375 23 24 C 23 24.5625 22.5625 25 22 25 C 21.4375 25 21 24.5625 21 24 C 21 23.4375 21.4375 23 22 23 Z"/></svg>
    </div>
  </button>
</div>

{#if showArtworkModal}
  <div class="overlay" on:click={handleCloseArtworkModal} on:keydown={null}>
  </div>
  <InfoModal {selectedArtwork}/>
{:else if showShoppingCartModal}
  <div class="overlay" on:click={handleCloseShoppingCartModal} on:keydown={null}>
  </div>
  <div style="z-index: 5;">
    <ShoppingCart/>
  </div>
{/if}

<div style="z-index: -5;">
  {#await loadArtworks()}
  <p>Loading</p>
  {:then artworks}
    <input type="checkbox" name="hideUnloanable" id="hideUnloanable" bind:checked={hideUnloanable}>
    <label for="hideUnloanable">Hide unloanable items</label>
    <div class="artworks">
      {#if artworks.length === 0}
        <p>No artworks yet</p>
      {/if}
      {#each artworks as artwork}
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
    z-index: 0;
  }

  .artworks {
    display: flex;
    flex-wrap: wrap;
    gap: 8rem;
  }

  #hideUnloanable {
    margin-bottom: 2rem;
  }

  .gallery-header-div {
    display: flex;
    justify-content: space-between;
    margin-bottom: 2rem;
  }

  #shopping-cart-button {
    background-color: #4E36FC;
    color: white;
    border-style: none;
    border-radius: 1rem; 
    height: 4rem;
    width: 4rem;
    margin-top: auto;
    margin-bottom: auto;
    cursor: pointer;
  }

  #shopping-cart-button div {
    width: 3rem;
    height: 3rem;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
</style>