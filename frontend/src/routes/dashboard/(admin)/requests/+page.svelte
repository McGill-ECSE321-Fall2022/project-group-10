

<div id="request-page">
    <h1>Requests</h1>

    <div style="clear: both">
        <h2 style="float: left">Loans</h2>
        <h2 style="float: right">Donations</h2>
    </div>
    

    <div class="flex-parent-element" id="request-panel">
        <div class="flex-child-element" id="loan-panel">
            

        </div>
        <div class="flex-child-element" id="donation-panel">
          
            
        </div>
            
    </div>
</div>


<style>

    .flex-parent-element {
        display: flex;
        width: 100%;
    }

    .flex-child-element {
        flex: 1;
        border: 1px solid rgb(84, 82, 224);
        margin: 1px;
       
        min-width: 700px;
        min-height: 450px;
        width: 50%;
        height: 65%;
        background-color: white;
        border-radius: 20px;
        display: flex;
        flex-direction: row;
        /* Animation when the page loads */
        animation: fade-in 1s;
    
    }

    .flex-child-element:first-child {
        margin-right: 20px;
    }

    #request-page {
        /* Fill the screen */
        width: 100%;
        height: 100%;
        
    }

   



</style>