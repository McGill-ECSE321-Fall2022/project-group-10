<script>

    function onDonate(){
        alert("Donate button clicked");
    }
</script>

<div id="Donation-page">
    <div id="Donation-Panel">
        <div id="Donation-Form">
            <div id="Donation-Form-Header">
                <h1 class="noselect">Donate</h1>
                <h2 class="prompt">Please enter your details below</h2>
            <input type="text" id="description" name="Artwork details" class="mytext" placeholder="Please enter the artwork title, Author, Creation date, Description, and imageLink" required>
            <button id="Donate-button" on:click={onDonate}><strong>Donate</strong></button>
        </div>
        
        </div>





    </div>
</div>


<style>
    #Donation-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        /* Center the content */
        display: flex;
        justify-content: left;
        align-items: top;
    }

    .mytext{
        width: 700px;
        height: 300px;

        justify-content : top;
    }

    .prompt{
        color: gray;
        font-size : 15px;
        margin-bottom: 30px;
    }
    
   





</style>
