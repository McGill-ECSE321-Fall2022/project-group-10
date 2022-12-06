<script>
    import { createEventDispatcher } from 'svelte';

    export let style = "";
    export let donation;

    const dispatch = createEventDispatcher();

    function onValidateClick() {
        dispatch("validate", donation);
    }
    function onDenyClick() {
        dispatch("deny");
    }

</script>

<div id="pending-donation-card" style={style}>
    {#if donation != undefined}
        <span id="description-field">{donation.description}</span>
        <span id="author-field" style="flex: 1;">By {donation.donor.firstName + " " + donation.donor.lastName}</span>
    {/if}
    <div style="display:flex;justify-content:center;align-items:center">
        <button on:click={onValidateClick}>Validate</button>
        <button on:click={onDenyClick}>Deny</button>
    </div>
</div>

<style>
    #pending-donation-card {
        background-color: white;
        border-radius: 20px;
        min-width: 300px;
        width: fit-content;
        height: fit-content;
        padding: 15px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-bottom: 20px;
    }

    #pending-donation-card span {
        margin: 15px 0 15px 0;
    }

    #pending-donation-card button {
        padding: 10px;
        margin-right:10px;
        border-radius: 15px;
        border: none;
        background-color: #4E36FC;
        color: white;
        transition: background-color 0.3s;
    }

    #pending-donation-card button:hover {
        background-color: #6e5ee7;
    }
</style>