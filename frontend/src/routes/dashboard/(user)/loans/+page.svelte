<script>
    import {apiCall} from '$lib/scripts/restApi.js'
    import Artwork from "$lib/components/dashboard/(user)/gallery/Artwork.svelte";

    let loans, artwork;
    let accepted = [];
    let pending = [];
    let denied = [];
    const loadLoans = async () => {
        const loanRes = await apiCall('GET','loans');
        loans = loanRes.data;
        for (let i =0; i<loans.length; i++){
            switch(loans[i].status){
                case 'VALIDATED':
                    accepted.push(loans[i]);
                    break;
                case 'PENDING':
                    pending.push(loans[i]);
                    break;
                case 'DENIED':
                    denied.push(loans[i]);
                    break;
            }
        }
    };
    let showLoanInfo = false;
    let selectedLoan;
    const toggleInfo = (loan) =>{
        if (!showLoanInfo){
            selectedLoan = loan
        }
        showLoanInfo = !showLoanInfo;
    }
    const imageNotFound = (e) => e.target.src = notFound;
  </script>
{#if showLoanInfo}
    <div class="overlay" on:click={() => toggleInfo(null)} on:keydown={null}></div>
    <div class="loan-info" id="loan-info-id">
        <h1>"{selectedLoan.artwork.title}"</h1>
        <h2><span style="font-size:1rem;">BY</span> {selectedLoan.artwork.author}</h2>
        <img class="image"
        src="{selectedLoan.artwork.imageLink}"
        alt="{selectedLoan.artwork.description}"
        on:error={imageNotFound}
        >
        <p>Created on {selectedLoan.artwork.creationDate}</p>
        <p>{selectedLoan.artwork.description}</p>
        <h1>Loan Info</h1>
        <h2>{selectedLoan.price}<span style="font-size: .75rem;">$/DAY</span></h2>
        <p>Start: {selectedLoan.startDate}, Finish: {selectedLoan.endDate}</p>
        <p>Customer: {selectedLoan.customer.firstName} {selectedLoan.customer.lastName}</p>
        {#if selectedLoan.validator != null}
            <p>Validator: {selectedLoan.validator.firstName} {selectedLoan.validator.lastName}</p>
        {/if}
    </div>
{/if}

<div id="loan-page">
    <h1>Loan</h1>

    <div id="loans-panel">
        {#await loadLoans()}
        <p>Loading</p>
        {:then data}
        <div id="accepted-loans" class="panel-side">
            <h2>Accepted</h2>
            <div id="accepted-container" class="container">
                <!-- Loans -->
                <div class="loans">
                    {#if loans.length === 0}
                        <p>No accepted loans</p>
                    {:else}
                    {#each accepted as loan}
                        <div id=accepted-loan  on:click={() => toggleInfo(loan)} on:keydown={null}>
                            <Artwork artwork={loan.artwork} expandable={true}/>
                        </div>
                    {/each}
                    {/if}
                </div>
            </div>
        </div>
        <div id="pending-loans" class="panel-side">
            <h2>Pending</h2>
            <div id="pending-container">
                <!-- Loans -->
                <div class="loans">
                    {#if loans.length === 0}
                        <p>No accepted loans</p>
                    {:else}
                    {#each pending as loan}
                        <div id=pending-loan on:click={() => toggleInfo(loan)} on:keydown={null}>
                            <Artwork artwork={loan.artwork} expandable={true}/>
                        </div>
                    {/each}
                    {/if}
                </div>
            </div>
        </div>
        <div id="denied-loans" class="panel-side">
            <h2>Denied</h2>
            <div id="denied-container">
                <!-- Loans -->
                <div class="loans">
                    {#if loans.length === 0}
                        <p>No accepted loans</p>
                    {:else}
                    {#each denied as loan}
                        <div id=pending-loan on:click={() => toggleInfo(loan)} on:keydown={null}>
                            <Artwork artwork={loan.artwork} expandable={true}/>
                        </div>
                    {/each}
                    {/if}
                </div>
            </div>
        </div>
        {/await}
    </div>
</div>

<style>
    #loan-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        
    }

    #loans-panel {
        width: 100%;
        height: 96%;
        background-color: white;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
    }

    #accepted-container {
        min-width: 200px;
        min-height: 200px;
        width: 90%;
        height: 90%;
        background-color: #F3F5FA;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
    }
    #pending-container {
        min-width: 200px;
        min-height: 200px;
        width: 90%;
        height: 90%;
        background-color: #F3F5FA;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
    }
    #denied-container {
        min-width: 200px;
        min-height: 200px;
        width: 90%;
        height: 90%;
        background-color: #F3F5FA;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
    }
    .panel-side{
        flex-grow: 3;
        flex-basis: 300px;
        width: 100%;
        /* Center the content */
        display: flex;
        margin-bottom: 5px;
        flex-direction: column;
        justify-content: Left;
        align-items: center;
    }
    .loans{
        flex-grow: 3;
        flex-basis: 300px;
        width: 100%;
        /* Center the content */
        display: flex;
        margin-bottom: 5px;
        flex-direction: column;
        justify-content: Left;
        align-items: center;
    }
    .loan-info {
        border-radius: 14px;
        position: fixed;
        top: 50%;
        left: 50%;
        background-color: white;
        transform: translate(-50%, -50%);
        padding: 1rem 3rem;
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

</style>
