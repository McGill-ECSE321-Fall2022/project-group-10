<script>

    import {apiCall} from '$lib/scripts/restApi.js'
    import Artwork from "$lib/components/dashboard/(user)/gallery/Artwork.svelte";
    import PendingDonation from '$lib/components/dashboard/(admin)/requests/PendingDonation.svelte';
    import PendingLoan from '$lib/components/dashboard/(admin)/requests/PendingLoan.svelte';

    $:pendingDonations=[];
    const loadDonations = async () => {
        const donationsRes = await apiCall('GET','donations');
        pendingDonations = [];

        for(let donation of donationsRes.data){
            if (!donation.validated) {
                pendingDonations.push(donation);
            }
        }
    };
    $:pendingLoans = [];
    const loadLoans = async () => {
        const loanRes = await apiCall('GET','loans');
        pendingLoans = [];
        for (let i =0; i<loanRes.data.length; i++){
            if (loanRes.data[i].status == 'PENDING') {
                pendingLoans.push(loanRes.data[i])
            }
        }
    };

    let selectedData;
    let selectedType;
    let showForm = false;
    let showDonation = false;
    const toggleForm = (type, data) => {
        showForm = !showForm;
        if (!showForm) showDonation = false;
        selectedType = type;
        selectedData = data;
    }
    const previewDonation = () => {
        
        if (!/^[a-z,A-Z,0-9, ]{1,30}$/.test(document.getElementById('art-title').value)) return;
        if (!/^[a-z,A-Z, ]{1,30}$/.test(document.getElementById('art-author').value)) return;
        if (!document.getElementById('art-imageLink').checkValidity()) return;
        if (!/^.{1,200}$/.test(document.getElementById('art-description').value)) return;
        if (!document.getElementById('art-created').checkValidity()) return;
        if (!document.getElementById('art-price').checkValidity()) return;
        showDonation = true;
    }
    const imageNotFound = (e) => e.target.src = notFound;

    const denyDonation = async (donation) => {
        await apiCall("DELETE", `donations/${donation.id}`).then(response => {
            loadDonations();
        }).catch(async err => {
            loadDonations();
        });
    }

    const denyLoan = async (loan) => {
        await apiCall("PUT", `loans/reject/${loan.id}`).then(response => {
            console.log(response);
            if (response.error == null) {
                loadLoans();
            } else {
                alert("Unable to validate the request.");
                loadLoans();
            }
        }).catch(async err => {
            console.log(err);
            alert("Unable to validate the request.");
            loadLoans();
        });
    }

    const onValidateDonationClick = async (donation) => {
        let formdata = {
            price: parseFloat(document.getElementById('art-price').value),
            title: document.getElementById('art-title').value,
            author: document.getElementById('art-author').value,
            imageLink: document.getElementById('art-imageLink').value,
            creationDate: document.getElementById('art-created').value,
            isAvailable: document.getElementById('art-available').checked
        };
        
        await apiCall("PUT", `donations/validate/${donation.id}`, formdata).then(response => {
            toggleForm('donation', donation);
            if (response.error == null) {
                loadDonations();
            } else {
                alert("Unable to validate the request.");
                loadDonations();
            }
        }).catch(err => {
            alert("Unable to validate the request.");
            loadDonations();
        });
    }
    const onValidateLoanClick = async (loan) => {
        
        apiCall("PUT", `loans/validate/${loan.id}`).then(response => {
            toggleForm('loan', loan);
            if (response.error == null) {
                loadLoans();
            } else {
                alert("Unable to validate the request.");
                loadLoans();
            }
        }).catch(async err => {
            alert("Unable to validate the request.");
            loadLoans();
        });
    }

</script>

{#if showForm}
    <div class="overlay" on:click={() => toggleForm(null)} on:keydown={null}></div>
    <div class="form-info" id="form-info-id">
        {#if selectedType == 'donation'}
            <h1>Donation Request</h1>
            <form>
                <label for="title">Title:</label>
                <input type="text" id="art-title" name="artTitle" required><br>
                <label for="title">Author:</label>
                <input type="text" id="art-author" name="artAuthor" required><br>
                <label for="title">Image Link:</label>
                <input type="url" id="art-imageLink" name="artImageLink" pattern="https://.*" placeholder="https://example.com/myimage.jpg" required><br>
                <label for="title">Description:</label>
                <input type="text" id="art-description" name="artTitle" required><br>
                <label for="title">Creation Date:</label>
                <input type="date" id="art-created" name="artCreated" required><br>
                <label for="title">Price:</label>
                <input type="number" min="0" step=".01" id="art-price" name="artPrice" required><br>
                <label for="title">Available for Loans:</label>
                <input type="checkbox" id="art-available" name="artAvailable" required><br>
            </form>
            <button on:click={previewDonation}>Preview</button>
            {#if showDonation}
                <h1>"{document.getElementById('art-title').value}"</h1>
                <h2><span style="font-size:1rem;">BY</span> {document.getElementById('art-author').value}</h2>
                <img class="image"
                src="{document.getElementById('art-imageLink').value}"
                alt="{document.getElementById('art-description').value}"
                on:error={imageNotFound}
                >
                <p>Created on {document.getElementById('art-created').value}</p>
                <p>{document.getElementById('art-description').value}</p>
                <button on:click={() => onValidateDonationClick(selectedData)}>Submit</button>
            {/if}
        {:else if selectedType == 'loan'}
            <h1>Loan Request</h1>
            <h1>"{selectedData.artwork.title}"</h1>
            <h2><span style="font-size:1rem;">BY</span> {selectedData.artwork.author}</h2>
            <img class="image"
            src="{selectedData.artwork.imageLink}"
            alt="{selectedData.artwork.description}"
            on:error={imageNotFound}
            >
            <p>Created on {selectedData.artwork.creationDate}</p>
            <p>{selectedData.artwork.description}</p>
            <h1>Loan Info</h1>
            <h2>{selectedData.price}<span style="font-size: .75rem;">$/DAY</span></h2>
            <p>Start: {selectedData.startDate}, Finish: {selectedData.endDate}</p>
            <p>Customer: {selectedData.customer.firstName} {selectedData.customer.lastName}</p>
            <button on:click={() => onValidateLoanClick(selectedData)}>Submit</button>
        {/if}
    </div>
{/if}

<div id="request-page">
    <h1>Requests</h1>

    <div id="request-panel" >
        

        
        <div id="pending-donation-side"class="panel-side">
            <h2>Pending Donations</h2>
            <div id="donation-forms">
                {#await loadDonations()}
                    <p>Loading</p>
                {:then data}
                    {#if pendingDonations.length == 0}
                        <p>No Pending Donations</p>
                    {:else}
                        {#each pendingDonations as donation}
                            <PendingDonation donation={donation} on:validate={() => toggleForm('donation', donation)} on:deny={() => denyDonation(donation)}></PendingDonation>
                        {/each}

                    {/if}

                {/await}
                



            </div>

        </div>

        <div id="pending-loan-side"class="panel-side">
            <h2>Pending Loans</h2>
            <div id="loan-forms">
                {#await loadLoans()}
                    <p>Loading</p>
                {:then data}
                    {#if pendingLoans.length == 0}
                        <p>No Pending Loans</p>
                    {:else}
                        {#each pendingLoans as loan}
                            <PendingLoan loan={loan} on:validate={() => toggleForm('loan', loan)} on:deny={() => denyLoan(loan)}></PendingLoan>
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

    #loan-forms {
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
    .form-info {
        border-radius: 14px;
        position: fixed;
        top: 50%;
        left: 50%;
        background-color: white;
        transform: translate(-50%, -50%);
        padding: 1rem 3rem;
    }
    .form-info button {
        padding: 10px;
        border-radius: 15px;
        border: none;
        background-color: #4E36FC;
        color: white;
        transition: background-color 0.3s;
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