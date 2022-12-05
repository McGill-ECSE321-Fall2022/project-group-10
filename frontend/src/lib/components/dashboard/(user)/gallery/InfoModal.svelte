<script>
  export let selectedArtwork;
  import notFound from '$lib/assets/images/not-found.jpg';

  const imageNotFound = (e) => e.target.src = notFound;

  const addLoanToCartRequest = async () => {
    let headers = new Headers();
	  headers.set('Authorization', `Basic ${btoa('admin@mail.museum.com:admin')}`);
	  const res = await fetch(`http://localhost:8081/loans`,
    {
      body: JSON.stringify({
        artworkId: selectedArtwork.id,
        userId: 1
      }),
      method: "POST", 
      headers: headers 
    });
    const artwork = await res.json();
    return { artwork };
  }
</script>

<div class="container">
  <h1>{selectedArtwork.title}</h1>
  <h2>{selectedArtwork.author}</h2>
  <img class="image"
  src="{selectedArtwork.imageLink}"
  alt="{selectedArtwork.description}"
  on:error={imageNotFound}
  >
  <p>{selectedArtwork.creationDate}</p>
  <p>{selectedArtwork.description}</p>

  {#if selectedArtwork.available}
    <button on:click={addLoanToCartRequest}>Add to cart</button>
  {:else}
    <em>This artwork is not available to be loaned.</em>
  {/if}
</div>

<style>
  .container {
    border-radius: 14px;
    position: fixed;
    top: 50%;
    left: 50%;
    background-color: white;
    transform: translate(-50%, -50%);
    padding: 1rem 3rem;
  }

  .image {
    height: 20rem;
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