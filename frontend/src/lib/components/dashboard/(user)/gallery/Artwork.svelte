<script>
  import notFound from '$lib/assets/images/not-found.jpg';
  export let artwork;
  console.log(artwork);

  const imageNotFound = (e) => e.target.src = notFound;
</script>

<div class="container">
  <div class="image-container">
    <img class="image"
      src="{artwork.imageLink}"
      alt="{artwork.description}"
      on:error={imageNotFound}
      >
  </div>
  <div class="info">
    <p>Title: {artwork.title.length > 10 ? `${artwork.title.slice(0,10)}...` : artwork.title}</p>
    <p>Author: {artwork.author.length > 10 ? `${artwork.author.slice(0,10)}...` : artwork.author}</p>
    <p><a href="">click to loan/more info</a></p>
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
    position: relative;
    top: 0;
    transition: top ease-in-out 0.3s;
  }

  .container:hover .image-container {
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
    transition: all ease-in-out 0.3s;
    margin-bottom: 0;
  }

  .container:hover .info {
    height: 8rem;
    transition: all ease-in-out 0.3s;
  }

</style>