<script>
  export let selectedArtwork;
  import notFound from '$lib/assets/images/not-found.jpg';
  import {apiCall} from '$lib/scripts/restApi.js'

  const imageNotFound = (e) => e.target.src = notFound;

  let startDateIn = (new Date()).toISOString().split('T')[0];
  let endDateIn = (new Date()).toISOString().split('T')[0];

  const getTotalPrice = (startDate, endDate) => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const diffTime = Math.abs(end - start);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
    return diffDays * selectedArtwork.price;
  }

  const addLoanToCartRequest = async () => {

	  const loanRes = await apiCall('POST', `loans`,
    {
      artworkId: selectedArtwork.id,
      userId: 1,
      endDate: endDateIn,
      price: getTotalPrice(startDateIn, endDateIn),
      startDate: startDateIn,
      status: "INCART"
    });

    const loan = loanRes.data;
    return { loan };
  }
</script>

<div class="container">
  <h1>"{selectedArtwork.title}"</h1>
  <h2><span style="font-size:1rem;">BY</span> {selectedArtwork.author}</h2>
  <img class="image"
  src="{selectedArtwork.imageLink}"
  alt="{selectedArtwork.description}"
  on:error={imageNotFound}
  >
  <p>Created on {selectedArtwork.creationDate}</p>
  <p>{selectedArtwork.description}</p>

  {#if selectedArtwork.available}
  <h2>Loan it?</h2>
  <h2>{selectedArtwork.price}<span style="font-size: .75rem;">$/DAY</span></h2>
    <div>
      From:* <input type="date" bind:value={startDateIn}/>
    </div>
    <div>
      Till:* <input type="date" bind:value={endDateIn} />
    </div>
    <div style="width:25rem; margin-bottom:2rem;">
      <em style="word-break: break-all; width:20rem;">*All artwork can be picked up as early as 10am on pickup day, and must be returned by 5pm on return day.</em>
    </div>
    <div style="display: flex; justify-content:space-between">
      <button on:click={addLoanToCartRequest}>Add to cart</button>
      <h2><span style="font-size: .8rem;">{(new Date(endDateIn) - new Date(startDateIn)) / (1000 * 60 * 60 * 24) + 1} Day(s) * {selectedArtwork.price}</span> = {((new Date(endDateIn) - new Date(startDateIn)) / (1000 * 60 * 60 * 24) + 1) * selectedArtwork.price}$</h2>
    </div>
  {:else}
    <em>This artwork is not available to be loaned.</em>
  {/if}
</div>

<style>
  input {
    width: 100%;
    padding: 0.5em;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }

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

  button:hover {
    background-color: #3B1F9F;
  }

  button:active {
    background-color: #3B1F9F;
  }

  button:focus {
    outline: none;
  }

</style>