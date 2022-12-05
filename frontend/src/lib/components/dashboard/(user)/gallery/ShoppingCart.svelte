<script>  

  import {apiCall} from '$lib/scripts/restApi.js'

  const getAllLoansInCart = async () => {
    const loansRes = await apiCall('GET', 'loans/self/withStatus/INCART');
    const loans = loansRes.data;
    console.log(loans);
    return loans;
  }

  const removeFromCart = async (id) => {
    const res = await apiCall('DELETE', `loans/${id}`);
    const response = res.data;
    return response;
  }

  const requestLoanInCart = async (id) => {
    const res = await apiCall('PUT', `loans/request/${id}`);
    const response = res.data;
    return response;
  }

  const requestAllLoansInCart = async () => {
    const res = await apiCall('PUT', `loans/self/requestall`);
    const response = res.data;
    return response;
  }

  const emptyCart = async () => {
    let headers = new Headers()
    headers.set('Authorization', `Basic ${btoa('among.us@email.com:lol')}`);

    const res = await apiCall('DELETE', `loans/self/deleteAllInCart`);
    const response = res.data;
    return response;
  }

  </script>
  
  <div class="shopping-cart-container">
    <div style="display: flex; justify-content:space-between;">
      <h2>Shopping Cart</h2>
      <div style="display: flex;">
        <button on:click ={() => requestAllLoansInCart()} style="height: 2.5rem; margin-top:auto; margin-bottom:auto;">Loan All</button>
        <button on:click ={() => emptyCart() } style="margin-left: 1rem; height: 2.5rem; margin-top:auto; margin-bottom:auto; background-color: #9E000A;">Empty Cart</button>
      </div>
    </div>
    <hr>
    <div class="list-view">
      {#await getAllLoansInCart()}
      <p>Loading</p>
      {:then loans}
        {#if loans.length == 0}
          <p>Your shopping cart is empty.</p>
        {:else}
          {#each loans as loan}
            <div class="row">
              <div>
                <img src={loan.artwork.image} alt="artwork" class="img-fluid">
              </div>
              <div>
                <h3>{loan.artwork.title}</h3>
                <p>{loan.artwork.description}</p>
                <h4>Price: {loan.price}$</h4>
              </div>
              <div>
                <p>From: {loan.startDate}</p>
                <p>To: {loan.endDate}</p>
              </div>
              <div>
                <button on:click={() => requestLoanInCart(loan.id)} >Request Loan</button>
                <button on:click={() => removeFromCart(loan.id)} on:keydown={null} style="background-color: #9E000A;"><svg style="fill: white;" width="1rem" height="1rem" viewBox="-32 0 512 512" xmlns="http://www.w3.org/2000/svg"><path d="M268 416h24a12 12 0 0 0 12-12V188a12 12 0 0 0-12-12h-24a12 12 0 0 0-12 12v216a12 12 0 0 0 12 12zM432 80h-82.41l-34-56.7A48 48 0 0 0 274.41 0H173.59a48 48 0 0 0-41.16 23.3L98.41 80H16A16 16 0 0 0 0 96v16a16 16 0 0 0 16 16h16v336a48 48 0 0 0 48 48h288a48 48 0 0 0 48-48V128h16a16 16 0 0 0 16-16V96a16 16 0 0 0-16-16zM171.84 50.91A6 6 0 0 1 177 48h94a6 6 0 0 1 5.15 2.91L293.61 80H154.39zM368 464H80V128h288zm-212-48h24a12 12 0 0 0 12-12V188a12 12 0 0 0-12-12h-24a12 12 0 0 0-12 12v216a12 12 0 0 0 12 12z"/></svg></button>
              </div>
            </div>
          {/each}
        {/if}
      {:catch error}
        <div>
          <h2>Something went wrong</h2>
          <p>{error}</p>
        </div>
      {/await}
    </div>
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
    }

    hr {
      margin: 0;
      background-color: #4E36FC;
      border: 1 px solid #4E36FC
    }

    .list-view {
      height: 90%;
      overflow-y: scroll;
    }

    .list-view::-webkit-scrollbar {
      display: none;
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