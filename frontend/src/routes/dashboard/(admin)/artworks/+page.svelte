<script>
  import Artwork from "$lib/components/dashboard/(user)/gallery/Artwork.svelte";
  import {apiCall} from '$lib/scripts/restApi.js'
	import AddArtworkModal from "../../../../lib/components/dashboard/(admin)/artworks/AddArtworkModal.svelte";

  let selectedStorage;
  let newStorage;
  let storageRooms, exhibitRooms, artworks;
  let showArtworkModal = false;

  const loadArtworks = async () => {
	  const artworksRes = await apiCall('GET','artworks');
	  artworks = artworksRes.data;

    const storageRoomsRes = await apiCall('GET','rooms/storageRoom');
    storageRooms = await storageRoomsRes.data;

    const exhibitRoomsRes = await apiCall('GET','rooms/exhibitRoom');
    exhibitRooms = await exhibitRoomsRes.data;

    selectedStorage = storageRooms[0];
    newStorage = storageRooms[0];
  };

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
      let index = artworks.indexOf(artwork);
      let anArtwork = (await apiCall('PUT',`artworks/move/${artwork.id}?roomId=${newStorage.id}`)).data;
      artworks[index] = anArtwork;
    });
    artworks = artworks;
    selectedArtworks = [];
  }

  const handleAddArtwork = (e) => {
    console.log("hello");
    console.log(e.detail.artwork.data); 
    artworks.push(e.detail.artwork.data);
    artworks = artworks;
    showArtworkModal = false;
  }

  const deleteArtworks = async () => {
    selectedArtworks.forEach(async artwork => {
      await apiCall('DELETE',`artworks/${artwork.id}`,{});
    });
    selectedArtworks.forEach(async artwork => {
      let index = artworks.indexOf(artwork);
      artworks.splice(index,1);
    });
    artworks = artworks;
    selectedArtworks = [];
  }
</script>

<div class="container">
  {#await loadArtworks()}
  <p>Loading</p>
  {:then data}
  {#if showArtworkModal}
    <div class="overlay" on:click={() => showArtworkModal = false} on:keydown={() => {}}>
    </div>
    <AddArtworkModal on:addArtwork={handleAddArtwork} on:close={() => showArtworkModal = false}/>
  {/if}
  <!-- Room Select -->
      <div class="select-room">
        <p>Select Current Room</p>
        <select bind:value={selectedStorage} on:change={() => {selectedArtworks = []}}>
          <optgroup label="Storage Rooms">
            {#each storageRooms as storageRoom}
              <option value={storageRoom}>
                {storageRoom.name}
              </option>
            {/each}
          </optgroup>
          <optgroup label="Exhibit Rooms">
            {#each exhibitRooms as exhibitRoom}
              <option value={exhibitRoom}>
                {exhibitRoom.name}
              </option>
            {/each}
          </optgroup>
        </select>
        {#if !Object.hasOwn(selectedStorage,'capacity')}
          <button on:click={() => showArtworkModal = true}>Add Artwork</button>
        {/if}
        <button class="delete-button" on:click="{() => deleteArtworks()}">Delete Artworks</button>
      </div>
      
    <!-- Artworks -->
    <div class="artworks">
      {#if artworks.length === 0}
        <p>No artworks yet</p>
      {:else}
        {#each artworks as artwork}
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

      <div class="select-room">
        <p>Select New Room</p>
        <select bind:value={newStorage}>
          <optgroup label="Storage Rooms">
            {#each storageRooms as storageRoom}
              {console.log(storageRoom.name)}
              <option value={storageRoom}>
                {storageRoom.name}
              </option>
            {/each}
          </optgroup>
  
          <optgroup label="Exhibit Rooms">
            {#each exhibitRooms as exhibitRoom}
              <option value={exhibitRoom}>
                {exhibitRoom.name}
              </option>
            {/each}
          </optgroup>
        </select>
        <button on:click={() => moveArtworksToRoom()}>Move Artworks</button>
      </div>
      
  {/await}
</div>

<style>
  .delete-button {
    background-color: #F44336
  }

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

  .overlay {
    height: 100%;
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.2);
    z-index: 0;
  }

  button {
    height: fit-content;
    padding: 1rem;
    background-color: #4E36FC;
    color: white;
    border-radius: 14px;
    border: none;
    cursor: pointer;
  }

  button:hover {
    filter: brightness(90%)
  }

  button:active {
    filter: brightness(80%)
  }
</style>