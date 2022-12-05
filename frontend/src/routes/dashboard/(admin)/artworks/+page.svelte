<script>
  import Artwork from "$lib/components/dashboard/(user)/gallery/Artwork.svelte";
  import {apiCall} from '$lib/scripts/restApi.js'

  let selectedStorage;
  let newStorage;

  const loadArtworks = async () => {
	  const artworksRes = await apiCall('GET','artworks');
	  const artworks = artworksRes.data;

    const storageRoomsRes = await apiCall('GET','rooms/storageRoom');
    const storageRooms = await storageRoomsRes.data;

    const exhibitRoomsRes = await apiCall('GET','rooms/exhibitRoom');
    const exhibitRooms = await exhibitRoomsRes.data;

    selectedStorage = storageRooms[0];
    newStorage = storageRooms[0];
	  return { artworks, storageRooms, exhibitRooms };
  };
  let promise = loadArtworks();

  let selectedArtworks = [];
  const toggleSelectedArtwork = (artwork) => {
    if (!selectedArtworks.includes(artwork)) {
      selectedArtworks.push(artwork);
    }
    else {
      let index = selectedArtworks.indexOf(artwork);
      selectedArtworks.splice(index,1);
    }

    // Need this to tell Svelte that selectedArtworks has changed
    selectedArtworks = selectedArtworks;
  }

  const moveArtworksToRoom = async () => {
    selectedArtworks.forEach(async artwork => {
      await apiCall('PUT',`artworks/move/${artwork.id}?roomId=${newStorage.id}`);
    });
    promise = loadArtworks();
  }
</script>

<div class="container">
  {#await promise}
  <p>Loading</p>
  {:then data}
  <!-- Room Select -->
    <div class="select-room">
      <p>Select Current Room</p>
      <select bind:value={selectedStorage}>
        <optgroup label="Storage Rooms">
          {#each data.storageRooms as storageRoom}
            {console.log(storageRoom.name)}
            <option value={storageRoom}>
              {storageRoom.name}
            </option>
          {/each}
        </optgroup>

        <optgroup label="Exhibit Rooms">
          {#each data.exhibitRooms as exhibitRoom}
            <option value={exhibitRoom}>
              {exhibitRoom.name}
            </option>
          {/each}
        </optgroup>
      </select>
    </div>

    <!-- Artworks -->
    <div class="artworks">
      {#if data.artworks.length === 0}
        <p>No artworks yet</p>
      {:else}
        {#each data.artworks as artwork}
          {#if artwork.storage.id == selectedStorage.id}
            <div class="artwork-container"
              on:click={() => toggleSelectedArtwork(artwork)} 
              on:keyup={() => {}}>
              <Artwork {artwork} expandable={false} imageBorderStyle={selectedArtworks.includes(artwork) ? "background-color:#4E36FC" : ""}/>
            </div>
          {/if}
        {/each}
      {/if}
    </div>

    <div class="buttons">
      <div class="select-room">
        <p>Select New Room</p>
        <select bind:value={newStorage}>
          <optgroup label="Storage Rooms">
            {#each data.storageRooms as storageRoom}
              {console.log(storageRoom.name)}
              <option value={storageRoom}>
                {storageRoom.name}
              </option>
            {/each}
          </optgroup>
  
          <optgroup label="Exhibit Rooms">
            {#each data.exhibitRooms as exhibitRoom}
              <option value={exhibitRoom}>
                {exhibitRoom.name}
              </option>
            {/each}
          </optgroup>
        </select>
      </div>
      <button on:click={() => moveArtworksToRoom()}>Move Artwork</button>
      <!-- <button on:click={addArtwork}>Add Artwork</button> -->
    </div>
  {/await}
</div>

<style>
  .container {
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }

  .artworks {
    display: flex;
    flex-wrap: wrap;
    gap: 8rem;
  }

  .select-room {
    display: flex;
    align-items: center;
    background-color: #ECE9FE;
    border-radius: 14px;
    padding: 2rem;
    gap: 4rem;
    width: fit-content;
  }
  
  .select-room p {
    font-size: 2rem;
    padding: 0;
    margin: 0;
  }

  .select-room select {
    height: fit-content;
    background-color: white;
    border: none;
    border-radius: 14px;
    padding: 1rem;
  }

  .artworks {
    background-color: #ECE9FE;
    padding: 3rem;
    border-radius: 14px;
  }
</style>