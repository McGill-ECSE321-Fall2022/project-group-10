<script>

    import {apiCall} from '$lib/scripts/restApi.js'
    import Artwork from "$lib/components/dashboard/(user)/gallery/Artwork.svelte";
    import PendingDonation from '$lib/components/dashboard/(admin)/requests/PendingDonation.svelte';

    $:pendingDonations=[];
    const loadDonation = async () => {
        const donationsRes = await apiCall('GET','donations');

        for(let donation of donationsRes.data){
            if (!donation.validated) {
                pendingDonations.push(donation);
            }
        }

        pendingDonations = pendingDonations;
        console.log(pendingDonations)
    };

    async function onValidateClick(event) {
        const donation = event.detail;
        
        apiCall("PUT", `donations/validate/${donation.id}/${validatorId}`).then(response => {

        }).catch(err => {
            
        });
    }


</script>

<div id="request-page">
    <h1>Requests</h1>

    <div id="request-panel" >
        

        
        <div id="invalid-donation-side"class="panel-side">
            <h2>Pending Donations</h2>
            <div id="donation-forms">
                {#await loadDonation()}
                    <p>Loading</p>
                {:then data}
                    {#if pendingDonations.length == 0}
                        <p>No Pending Donations</p>
                    {:else}
                        {#each pendingDonations as donation}
                            <PendingDonation donation={donation} on:validate={onValidateClick}></PendingDonation>
                        {/each}

                    {/if}

                {/await}
                



            </div>

        </div>
    </div>
</div>
    
        


<style>

    
    

    #request-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        
    }

    #request-panel {
        width: 100%;
        height: 96%;
        background-color: white;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
    }

    #donation-forms {

        min-width: 200px;
        min-height: 200px;
        width: 90%;
        height: 90%;
        background-color: #F3F5FA;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
        align-content: flex-start;
        justify-content: space-around;
        flex-wrap: wrap;
        padding: 15px;
    }

    /* .donation-containter {
        min-width: 200px;
        min-height: 200px;
        width: 90%;
        height: 90%;
        background-color: #F3F5FA;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
    } */

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
    
</style>