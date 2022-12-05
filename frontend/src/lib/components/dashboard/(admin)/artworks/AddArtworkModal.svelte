<script>
  import notFound from '$lib/assets/images/not-found.jpg';
  import {apiCall} from '$lib/scripts/restApi.js'
  import { createEventDispatcher } from 'svelte';
  let title,author,creationDate,description,imageLink,price,isAvailable;

  const addArtwork = async () => {
    let artwork = {
      title,
      author,
      creationDate,
      description,
      imageLink,
      price,
      isAvailable
    };
    console.log(artwork);
    artwork = await apiCall('POST', 'artworks', artwork);
    dispatch('addArtwork', {
      artwork
    });

    const close = async () => {
      dispatch('close');
    };
  };

  const dispatch = createEventDispatcher();
</script>

<form class="container" on:submit|preventDefault={addArtwork}>
  <h1>Add New Artwork</h1>
  <div class="input-container">
    <label for="title">Title</label>
    <input type="text" name="title" id="title" required bind:value={title}>
  </div>

  <div class="input-container">
    <label for="author">Author</label>
    <input type="text" name="author" id="author" required bind:value={author}>
  </div>

  <div class="input-container">
    <label for="creationDate">Creation Date</label>
    <input type="date" name="creationDate" id="creationDate" required bind:value={creationDate}>
  </div>

  <div class="input-container">
    <label for="description">Description</label>
    <input type="text" name="description" id="description" required bind:value={description}>
  </div>

  <div class="input-container">
    <label for="imageLink">Image Link</label>
    <input type="url" name="imageLink" id="imageLink" bind:value={imageLink}>
  </div>

  <div class="input-container">
    <label for="price">Price</label>
    <input type="number" name="price" id="price" step="0.01" required bind:value={price}>
  </div>

  <div class="checkbox-input">
    <label for="isAvailable">Is Loanable</label>
    <input type="checkbox" name="isAvailable" id="isAvailable" bind:checked={isAvailable}>
  </div>
  <button type="submit">Create New Artwork</button>
</form>

<style>
  .container {
    display: flex;
    flex-direction: column;
    border-radius: 14px;
    position: fixed;
    top: 50%;
    left: 50%;
    background-color: white;
    transform: translate(-50%, -50%);
    padding: 3rem;
    gap: 1rem;
    width: 30rem;
  }

  .input-container {
    display: flex;
    flex-direction: column;
    gap: 0.5rem
  }

  .checkbox-input {
    display: flex;
    flex-direction: row;
    gap: 0.5rem
  }

  input {
    border-radius: 14px;
    border: solid;
    border-width: 1px;
    padding: 0.5rem;
  }

  button {
    background-color: #4E36FC;
    color: white;
    border-style: none;
    padding: 0.5rem 1rem;
    border-radius: 14px; 
    cursor: pointer;
  }
</style>