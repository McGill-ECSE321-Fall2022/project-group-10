<script>  
  const sendLoanRequest = async () => {
    let headers = new Headers();
      headers.set('Authorization', `Basic ${btoa('admin@mail.museum.com:admin')}`);
      const res = await fetch(`http://localhost:8081/request/{selectedArtwork.id}`,
    { 
      method: "POST", 
      headers: headers 
    });
      const artwork = await res.json();
      return { artwork };
  }

  const getAllLoansInCart = async () => {
    console.log("Trying to fetch loans");
    let headers = new Headers()
    headers.set('Authorization', `Basic ${btoa('jon.doe@email.com:kek123')}`);

    const res = await fetch(`http://localhost:8081/loans/self/withStatus/INCART`, {
      method: "GET",
      headers: headers
    });

    const loans = await res.json();
    console.log(loans);
    return { loans };
  }

  </script>
  
  <div class="shopping-cart-container">
    <h2>Shopping Cart</h2>
    {#await getAllLoansInCart()}
    <p>Loading</p>
    {:then data}
      {#each data.loans as loan}
        <div class="row">
          <div>
            <img src={loan.artwork.image} alt="artwork" class="img-fluid">
          </div>
          <div>
            <h3>{loan.artwork.title}</h3>
            <p>{loan.artwork.description}</p>
            <p>Price: {loan.artwork.price}</p>
          </div>
          <div>
            <p>From: {loan.startDate}</p>
            <p>To: {loan.endDate}</p>
          </div>
          <div>
            <button on:click={sendLoanRequest} >Request Loan</button>
            <button style="background-color: #9E000A;"><svg style="fill: white;" width="1rem" height="1rem" viewBox="-32 0 512 512" xmlns="http://www.w3.org/2000/svg"><path d="M268 416h24a12 12 0 0 0 12-12V188a12 12 0 0 0-12-12h-24a12 12 0 0 0-12 12v216a12 12 0 0 0 12 12zM432 80h-82.41l-34-56.7A48 48 0 0 0 274.41 0H173.59a48 48 0 0 0-41.16 23.3L98.41 80H16A16 16 0 0 0 0 96v16a16 16 0 0 0 16 16h16v336a48 48 0 0 0 48 48h288a48 48 0 0 0 48-48V128h16a16 16 0 0 0 16-16V96a16 16 0 0 0-16-16zM171.84 50.91A6 6 0 0 1 177 48h94a6 6 0 0 1 5.15 2.91L293.61 80H154.39zM368 464H80V128h288zm-212-48h24a12 12 0 0 0 12-12V188a12 12 0 0 0-12-12h-24a12 12 0 0 0-12 12v216a12 12 0 0 0 12 12z"/></svg></button>
          </div>
        </div>
      {/each}
    {:catch error}
      <div>
        <h2>Something went wrong</h2>
        <p>{error}</p>
      </div>
    {/await}
  </div>

  <style>
    .shopping-cart-container {
      border-radius: 14px;
      position: fixed;
      width: 50%;
      height: 80%;
      top: 50%;
      left: 50%;
      background-color: white;
      transform: translate(-50%, -50%);
      padding: 1rem 3rem;
      overflow-y: auto;

    }
    .shopping-cart-container::-webkit-scrollbar {
      display: none;
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

    .row {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      margin: 1rem 0;
      border-bottom: #4E36FC .1rem dotted;
    }
  </style>