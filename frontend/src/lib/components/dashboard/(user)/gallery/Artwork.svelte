<script>
  import notFound from '$lib/assets/images/not-found.jpg';
  export let artwork;
  console.log(artwork);

  const imageNotFound = (e) => e.target.src = notFound;
</script>

<div class="container">
  <img class="image" 
    src="{artwork.imageLink}" 
    alt="{artwork.description}" 
    on:error={imageNotFound}
    >
</div>

<style>
  .container {
    width: 13rem;
    height: 7rem;
    overflow: hidden;
    padding: 0.5rem;
    border-radius: 14px;
    background-color: white;
  }

  .image {
    border-radius: 14px;
    height: 100%;
    width: 100%;
    object-fit: cover;
  }
</style>