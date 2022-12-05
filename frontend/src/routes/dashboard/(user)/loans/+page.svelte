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
                case 'ACCEPTED':
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
  </script>

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
                        {artwork=loan.artwork}
                        <div id=accepted-loan>
                            <Artwork artwork={loan.artwork} expandable={false}/>
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
                        <div id=pending-loan>
                            <Artwork artwork={loan.artwork} expandable={false}/>
                        </div>
                    {/each}
                    {/if}
                </div>
            </div>
        </div>
        <div id="denied-loans" class="panel-side">
            <h2>Denied</h2>
            <div id="denied-container">
                
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
</style>
