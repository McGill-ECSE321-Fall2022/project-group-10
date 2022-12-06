<script>
    import {apiCall} from '$lib/scripts/restApi.js'

    function onDonate(){
        //get the title,author,description,imagelink
        const description = document.getElementById("description").value;
       
       // Send a request to the backend to create a new donation
       apiCall("POST",'donations', {
        description: description
    
        }).then(response => alert("Thank you for your donation!"))

        
    }
</script>

<div id="Donation-page">
    <div id="Donation-Panel">
        <div id="Donation-Form">
            
                <h1 class="noselect">Donate Artwork</h1>
                <h2 class="prompt">Please enter the artwork description below</h2>
                <input id="description" type="text" placeholder="Description" required/>
                <button id="Donate-button" on:click={onDonate}><strong>Donate</strong></button>
            
        
        </div>

    </div>
</div>


<style>





    #Donation-Form {
        flex-grow: 3;
        flex-basis: 300px;
        width: 100%;
        /* Center the content */
        display: flex;
        margin-bottom: 20px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    #Donation-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        /* Center the content */
        display: flex;
        justify-content: center;
        align-items: top;
    }

    #Donation-Panel {
        /* Fill the screen */
        min-width: 800px;
        min-height: 200px;
        max-height: 500px;
        /* Center the content */
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 20px;
        background-color: white;
    }

    

    .prompt{
        color: gray;
        font-size : 15px;
        margin-bottom: 30px;
    }

    #Donation-Form input[type=text] {
        width: 80%;
        padding: 6px 5px;
        margin: 8px 0;
        display: inline-block;
        /* Bottom border only */
        border: none;
        border-bottom: 2px solid #ccc;
        box-sizing: border-box;
        /* Fade transition when focused */
        transition: border-bottom 0.3s;
    }

    #Donate-button {
        width: 80%;
        background-color: rgb(88, 86, 201); /* Dark lime green */
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    #Donate-button:hover {
        background-color: rgb(57, 12, 182); /* Dark green */
    }
    
   





</style>
