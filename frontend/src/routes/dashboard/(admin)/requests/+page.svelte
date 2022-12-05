

<div id="request-page">
    <h1>Requests</h1>

    <div id="request-panel" >
        
        
        <div id="loan-side"class="panel-side">
            <h2>Loans</h2>
            <div id="loan-forms">



            </div>

        </div>
        
        <div id="donation-side" class="panel-side">
            <h2>Donations</h2>
            <div id="donation-forms">


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
    

   



</style>