<script>
    import { createEventDispatcher } from 'svelte';
    import Artwork from "$lib/components/dashboard/(user)/gallery/Artwork.svelte";

    export let style = "";
    export let loan;

    const dispatch = createEventDispatcher();

    function onValidateClick() {
        dispatch("validate");
    }
    function onDenyClick() {
        dispatch("deny");
    }

</script>

<div id="pending-loan-card" style={style}>
    {#if loan != undefined}
        <Artwork artwork={loan.artwork} expandable={false}/>
        <span id="artwork-field">{loan.artwork.title}</span>
        <span id="author-field" style="flex: 1;">By {loan.customer.firstName + " " + loan.customer.lastName}</span>
    {/if}
    <div style="display:flex;justify-content:center;align-items:center">
        <button on:click={onValidateClick}>Validate</button>
        <button on:click={onDenyClick}>Deny</button>
    </div>
</div>

<style>
    #pending-loan-card {
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

    #pending-loan-card span {
        margin: 15px 0 15px 0;
    }

    #pending-loan-card button {
        padding: 10px;
        margin-right:10px;
        border-radius: 15px;
        border: none;
        background-color: #4E36FC;
        color: white;
        transition: background-color 0.3s;
    }

    #pending-loan-card button:hover {
        background-color: #6e5ee7;
    }
</style>