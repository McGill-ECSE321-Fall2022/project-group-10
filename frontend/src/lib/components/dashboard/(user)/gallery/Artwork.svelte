<script>
  import notFound from '$lib/assets/images/not-found.jpg';
	import { createEventDispatcher } from 'svelte';
  export let artwork;
  export let expandable = true;
  console.log(artwork);

  const imageNotFound = (e) => e.target.src = notFound;


  const dispatch = createEventDispatcher();

  const openModal = (artwork) => {
    dispatch('selectArtwork', {
      selectedArtwork : artwork
    });
  };
</script>

<div class="container">
  <div class="image-container {expandable ? "expandableImage" : ""}">
    <img class="image"
      src="{artwork.imageLink}"
      alt="{artwork.description}"
      on:error={imageNotFound}
      >
  </div>
  <div class="info {expandable ? "expandableInfo" : ""}">
    <p class="info-text">Title: {artwork.title.length > 10 ? `${artwork.title.slice(0,10)}...` : artwork.title}</p>
    <p class="info-text">Author: {artwork.author.length > 10 ? `${artwork.author.slice(0,10)}...` : artwork.author}</p>
    <p class="click-text" on:click={() => openModal(artwork)}>click to loan/more info</p>
  </div>
</div>

<style>
  .container {
    width: fit-content;
    height: fit-content;
    background-color: rgba(63,76,139,.2);
    border-radius: 14px;
  }

  .image-container {
    width: 13rem;
    height: 7rem;
    overflow: hidden;
    padding: 0.5rem;
    border-radius: 14px;
    background-color: white;
  }

  .expandableImage {
    position: relative;
    top: 0;
    transition: top ease-in-out 0.3s;
  }

  .container:hover .expandableImage {
    position: relative;
    top: -1rem;
    transition: top ease-in-out 0.3s;
  }

  .image {
    border-radius: 14px;
    height: 100%;
    width: 100%;
    object-fit: cover;
  }

  .info {
    height: 0;
    overflow: hidden;
    border-radius: 14px;
    padding: 0 1rem;
    margin-top: -1rem;
    margin-bottom: 0;
  }

  .expandableInfo {
    transition: all ease-in-out 0.3s;
  }

  .container:hover .expandableInfo {
    height: 8rem;
    transition: all ease-in-out 0.3s;
  }

  .info-text {
    color: #29346F;
  }

  .click-text {
    cursor: pointer;
    color: #454545;
    font-size: 0.8rem;
    text-align: right;
    margin-top: 2rem;
  }

</style>