<script>
  import Artwork from "../../../../lib/components/dashboard/(user)/gallery/Artwork.svelte";

  let selectedStorage ;
  const loadArtworks = async () => {
    let headers = new Headers();
	  headers.set('Authorization', `Basic ${btoa('admin@mail.museum.com:admin')}`);
	  const artworksRes = await fetch('http://localhost:8081/artworks', { headers: headers });
	  const artworks = await artworksRes.json();

    const storageRoomsRes = await fetch('http://localhost:8081/rooms/storageRoom', { headers: headers });
    const storageRooms = await storageRoomsRes.json();

    const exhibitRoomsRes = await fetch('http://localhost:8081/rooms/exhibitRoom', { headers: headers });
    const exhibitRooms = await exhibitRoomsRes.json();

    selectedStorage = storageRooms[0];
	  return { artworks, storageRooms, exhibitRooms };
  };

  
</script>

<div>
  {#await loadArtworks()}
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

    <!-- Artwork select -->
    <div class="artworks">
      {#if data.artworks.length === 0}
        <p>No artworks yet</p>
      {:else}
        {#each data.artworks as artwork}
          {#if artwork.storage.id == selectedStorage.id}
            <Artwork {artwork} expandable={false}/>
          {/if}
        {/each}
      {/if}
    </div>
  {/await}
</div>

<style>
  .artworks {
    display: flex;
    flex-wrap: wrap;
    gap: 8rem;
  }
</style>