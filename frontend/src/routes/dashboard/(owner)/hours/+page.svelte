<script>
	// This ap
	import Calendar from '$lib/components/dashboard/Calendar.svelte';
	import {apiCall} from '$lib/scripts/restApi.js'
	import {onMount} from 'svelte';

	var dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
	let monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

	let headers = [];
	let now = new Date();
	let year = now.getFullYear();		//	this is the month & year displayed
	let month = now.getMonth();
	let eventText="Click an item or date";
  let assignedEmployees = [];
  let allEmployees = false;

	var days = [];	//	The days to display in each box

	function randInt(max) {
		return Math.floor(Math.random()*max)+1;
	}

	const registerVisitor = async (id) => {
		const res = await apiCall('POST',`scheduleBlock/${id}/add/self`);
		if (res.status == 200) {
			goto('/dashboard/user/schedule');
			alert("Booked pass!");
		} else {
			//	Failure
			//	Display error message
			alert("Error: " + res.status);
		}
	} 

	//	The Calendar Component just displays stuff in a row & column. It has no knowledge of dates.
	//	The items[] below are placed (by you) in a specified row & column of the calendar.
	//	You need to call findRowCol() to calc the row/col based on each items start date. Each date box has a Date() property.
	//	And, if an item overlaps rows, then you need to add a 2nd item on the subsequent row.
	$: items = [];
	$: currScheduleBlock = null;

	const initMonthItems = async () => {
		let y = year;
		let m = month;
		let d1=new Date(y,m,15);

		const scheduleBlocksRes = await apiCall('GET', 'scheduleBlock');
		const scheduleBlocks = scheduleBlocksRes.data;

		items = scheduleBlocks.map(block => { 
			return {
				id: block.id,
				title: block.event === "MUSEUM_MEETING"? "Event pass" : "Regular pass",
				className: ((block.event === "MUSEUM_OPEN")? "task--primary" : "task--warning"),
				date:new Date((new Date(block.startDate)).setDate((new Date(block.startDate)).getDate() + 1)),
				len:(new Date(block.endDate) - new Date(block.startDate)) / (1000 * 60 * 60 * 24),
			}
		});

		items = items

		console.log(items);

		// items=[
		// 	{title:"11:00 Museum Open",className:"task--primary",date:new Date(y,m,1),len:2, vlen:1},
		// 	{title:"11:00 Museum Open",className:"task--primary",date:new Date(y,m,6),len:3, vlen:1},
		// 	{title:"11:00 Museum Open",className:"task--primary",date:new Date(y,m,13),len:5, vlen:1},
		// 	{title:"7:30 Van Gogh expedition",className:"task--warning",date:d1,len:randInt(4)+2},

		// 	{title:"11:00 Museum Open",className:"task--primary",date:new Date(y,m,21),len:2, vlen:1},
		// 	{title:"11:00 Museum Open",className:"task--primary",date:new Date(y,m,23),len:2, vlen:1},
		// ];

		//This is where you calc the row/col to put each dated item
		for (let i of items) {
			let rc = findRowCol(i.date);
			if (rc == null) {
				console.log('didn`t find date for ',i);
				console.log(i.date);
				console.log(days);
				i.startCol = i.startRow = 0;
			} else {
				i.startCol = rc.col;
				i.startRow = rc.row;
			}
		}
	}

	$: month,year

	onMount(async () => {
		await initContent();
	});

	// await initContent();

	// choose what date/day gets displayed in each date box.
	const initContent = async () => {
		headers = dayNames;
		initMonth();
		await initMonthItems();
	}

	function initMonth() {
		days = [];
		let monthAbbrev = monthNames[month].slice(0,3);
		let nextMonthAbbrev = monthNames[(month+1)%12].slice(0,3);
		//	find the last Monday of the previous month
		var firstDay = new Date(year, month, 1).getDay();
		//console.log('fd='+firstDay+' '+dayNames[firstDay]);
		var daysInThisMonth = new Date(year, month+1, 0).getDate();
		var daysInLastMonth = new Date(year, month, 0).getDate();
		var prevMonth = month==0 ? 11 : month-1;
		
		//	show the days before the start of this month (disabled) - always less than 7
		for (let i=daysInLastMonth-firstDay;i<daysInLastMonth;i++) {
			let d = new Date(prevMonth==11?year-1:year,prevMonth,i+1);
			days.push({name:''+(i+1),enabled:false,date:d,});
		}
		//	show the days in this month (enabled) - always 28 - 31
		for (let i=0;i<daysInThisMonth;i++) {
			let d = new Date(year,month,i+1);
			if (i==0) days.push({name:monthAbbrev+' '+(i+1),enabled:true,date:d,});
			else days.push({name:''+(i+1),enabled:true,date:d,});
			//console.log('i='+i+'  dt is '+d+' date() is '+d.getDate());
		}
		//	show any days to fill up the last row (disabled) - always less than 7
		for (let i=0;days.length%7;i++) {
			let d = new Date((month==11?year+1:year),(month+1)%12,i+1);
			if (i==0) days.push({name:nextMonthAbbrev+' '+(i+1),enabled:false,date:d,});
			else days.push({name:''+(i+1),enabled:false,date:d,});
		}
	}

	function findRowCol(dt) {
		for (let i=0;i<days.length;i++) {
			let d = days[i].date;
			if (d.getYear() === dt.getYear()
				&& d.getMonth() === dt.getMonth()
				&& d.getDate() === dt.getDate())
				return {row:Math.floor(i/7)+2,col:i%7+1};
		}
		return null;	
	}

	async function itemClick(e) {
		let scheduleBlockRes = await apiCall('GET', `scheduleBlock/${e.id}`);
		let scheduleBlock = scheduleBlockRes.data;
		currScheduleBlock = scheduleBlock;
		console.log(scheduleBlock);
	}
	function dayClick(e) {
		eventText='onDayClick '+JSON.stringify(e) + ' localtime='+e.date.toString();
	}
	function headerClick(e) {
		eventText='onHheaderClick '+JSON.stringify(e);
	}
	function next() {
		month++;
		if (month == 12) {
			year++;
			month=0;
		}
	}
	function prev() {
		if (month==0) {
			month=11;
			year--;
		} else {
			month--;
		}
	}

  let event,startDate,visitCapacity,visitFees;
  const date = new Date();
  $: firstOfMonth = new Date(date.getFullYear(), date.getMonth(),1);
  $: lastOfMonth = new Date(date.getFullYear(), date.getMonth()+1, 0);

  const addScheduleBlock = async () => {
    let endDate = new Date(startDate);
    endDate.setDate(endDate.getDate()+1);
    await apiCall('POST', 'scheduleBlock', {event,startDate,endDate,visitCapacity,visitFees});
    await initMonthItems();
    event = null;
    startDate = null;
    visitCapacity = null;
    visitFees = null;
  }

  const loadEmployees = async (scheduleBlockId) => {
    let res = await apiCall('GET', `scheduleBlock/${scheduleBlockId}/staff`);
    let employees = res.data;
    if (allEmployees == false) {
      allEmployees = await apiCall('GET', 'administrator/employee');
      allEmployees = allEmployees.data;
    }
    assignedEmployees = employees;
  }

  const toggleInStatus = async (employee, currScheduleBlock) => {
    console.log(currScheduleBlock.adminIds);
    if (!currScheduleBlock.adminIds.includes(employee.id)) {
      await apiCall('POST', `scheduleBlock/${currScheduleBlock.id}/staff/${employee.id}`);
      currScheduleBlock.adminIds.push(employee.id);
    } else {
      await apiCall('DELETE', `scheduleBlock/${currScheduleBlock.id}/staff/${employee.id}`);
      currScheduleBlock.adminIds = currScheduleBlock.adminIds.filter(e => e != employee.id);
    }
    currScheduleBlock = currScheduleBlock
    console.log(currScheduleBlock.adminIds);
  }
	
</script>

<div class="container">
  <form class="new_block" on:submit|preventDefault={addScheduleBlock}>
    <h1>New Schedule Block</h1>
    <div class="link">
      <select name="event" id="event" bind:value={event}>
        <option value="MUSEUM_OPEN">Regular Schedule</option>
        <option value="MUSEUM_MEETING">Special Event</option>
      </select>
    </div>
    <div class="link">
      <label for="start">Date</label>
      <input type="date" name="start" id="start" required bind:value={startDate} min={firstOfMonth} max={lastOfMonth}>
    </div>
    <div class="link">
      <label for="capacity">Capacity</label>
      <input type="number" name="capacity" id="capacity" required bind:value={visitCapacity}>
    </div>
    <div class="link">
      <label for="fees">Fees</label>
      <input type="number" name="fees" id="fees" step="0.01" required bind:value={visitFees}>
    </div>
    <button type="submit">New Schedule Block</button>
  </form>
  
  <div class="calendar-info">
    <div class="calendar-container">
      <div class="calendar-header">
        <h1>
           {monthNames[month]} {year}
        </h1>
      </div>
    
      <Calendar
        {headers}
        {days}
        {items}
        on:dayClick={(e)=>dayClick(e.detail)}
        on:itemClick={(e)=>itemClick(e.detail)}
        on:headerClick={(e)=>headerClick(e.detail)}
        />
    </div>
    
    <div id="event-desc">
      {#if currScheduleBlock != null}
        <div class="info">
          <h2>{currScheduleBlock.event === "MUSEUM_OPEN" ? "Regular Schedule" : "Special Event"}</h2>
          <div>Starts: {currScheduleBlock.startDate}</div>
          <div>Ends: {currScheduleBlock.endDate}</div>
          <h3>{currScheduleBlock.visitFees}$</h3>
        </div>
        <div class="employees">
          <h2>Employees</h2>
        {#await loadEmployees(currScheduleBlock.id)}
          <div>Loading...</div>
        {:then}
          {#each allEmployees as employee}
            <div><input type="checkbox" on:click={() => toggleInStatus(employee, currScheduleBlock)} checked="{currScheduleBlock.adminIds.includes(employee.id) ? true : false}">{employee.firstName} {employee.lastName}</div>
          {/each}
        {/await}
        </div>
      {/if}
    </div>
  </div>
</div>

<style>
  #event-desc {
    margin: auto;
    width: 70%;
    background-color: white;
    border-radius: 14px;
    padding: 3rem;
    margin-bottom: 1rem;
    display: flex;
  }

  .employees {
    margin-left: auto;
  }

  select {
    border-radius: 14px;
    border: solid;
    border-width: 1px;
    padding: 0.5rem;
    background-color: white;
  }

  input {
    border-radius: 14px;
    border: solid;
    border-width: 1px;
    padding: 0.5rem;
  }

  .container {
    display: flex;
    flex-direction: row;
    margin-top: 1rem;
    gap: 10rem;
    justify-content: center;
  }

  .new_block {
    background-color: white;
    border-radius: 14px;
    padding: 3rem;
    margin-bottom: 1rem;
    width: 20rem;
    height: 30rem;
  }

  .link {
    display: flex;
    flex-direction: column;
    margin-bottom: 1rem;
  }

#event-desc {
	margin: auto;
	width: 70%;
	background-color: #EEEEFF;
	border-radius: 10px;
	margin-top: 1rem;
	padding: .1rem 2rem 2rem 2rem;
	box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
}

.calendar-container {
  width: auto;
  margin: auto;
  overflow: hidden;
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background: #fff;
  max-width: 1200px;
}
.calendar-header {
  text-align: center;
  padding: 20px 0;
  background: #eef;
  border-bottom: 1px solid rgba(166, 168, 179, 0.12);
}
.calendar-header h1 {
  margin: 0;
  font-size: 18px;
}
.calendar-header button {
  background: #eef;
  border: 1px ;
  padding: 6;
  color: rgba(81, 86, 93, 0.7);
  cursor: pointer;
  outline: 0;
}

button {
    background-color: #4E36FC;
    color: white;
    border-style: none;
    padding: 0.5rem 1rem;
    border-radius: 14px; 
    cursor: pointer;
  }
</style>



