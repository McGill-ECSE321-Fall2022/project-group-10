const URL = "http://localhost:8081/";

/**
 * This function is used to send a request to the server
 * @param {string} method - The method of the request (GET, POST, PUT, DELETE)
 * @param {string} endpoint - The endpoint of the request (e.g. /users)
 * @param {object} data - The data to send to the server 
 * @returns {Promise} - A promise that resolves when the request is done
 */
async function apiCall(method="GET", endpoint="", data={}) {
    return new Promise((resolve, reject) => {

        // Get the email and password from localStorage
        const {email, password} = getCredentials();
        // Set the headers
        let headers = new Headers();
        headers.set("Content-Type", "application/json");
        // Exception for the POST "/visitor" endpoint
        if (!(method == "POST" && endpoint == "visitor")) {
            headers.set('Authorization', 'Basic ' + btoa(email + ":" + password));
            headers.set('Access-Control-Allow-Credentials', 'true');
        }
        headers.set('Access-Control-Allow-Origin', '*');
        headers.set('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
        headers.set('Access-Control-Allow-Headers', 'Content-Type, Authorization');

        // Create and send the request
        if (method === "GET" || method === "DELETE") {
            fetch(URL + endpoint, {
                method: method,
                headers: headers
            }).then(async (res) => [res, await res.json()]).then(ans => {
                if ((ans[1] != null || ans[1] != undefined) && ans[0].status != 401) {
                    resolve({response: ans[0], data: ans[1], error: null});
                } else {
                    reject({response: ans[0], data: ans[1], error: "Unauthorized"});
                }
            }).catch((err) => {
                reject({error: err});
            });
        } else {
            fetch(URL + endpoint, {
                method: method,
                headers: headers,
                body: JSON.stringify(data)
            }).then(async (res) => [res, await res.json()]).then(ans => {
                if ((ans[1] != null || ans[1] != undefined) && ans[0].status != 401) {
                    resolve({response: ans[0], data: ans[1], error: null});
                } else {
                    reject({response: ans[0], data: ans[1], error: "Unauthorized"});
                }
            }).catch((err) => {
                reject({error: err});
            });        
        }
        
    });
}

/**
    This function is used to store the email and password in localStorage
    @param {string} email - The email of the user
    @param {string} password - The password of the user
*/
function setCredentials(email, password) {
    localStorage.setItem("email", email);
    localStorage.setItem("password", password);
}

/**
    This function is used to get the email and password from localStorage
    @returns {object} - An object containing the email and password
*/
function getCredentials() {
    return {
        email: localStorage.getItem("email"),
        password: localStorage.getItem("password")
    }
}

/**
    This function is used to remove the email and password from localStorage
*/
function clearCredentials() {
    localStorage.removeItem("email");
    localStorage.removeItem("password");
}

/**
 * This function checks if the user is logged in 
 * @returns {boolean} - True if the user is logged in, false otherwise
 */
async function isLoggedIn() {
    return new Promise((resolve) => {
        // Make a request to the server to check if the credentials are valid
        apiCall('GET', 'artworks', null).then((response) => {
            if (response.error == null) {
                resolve(true);
            } else {
                resolve(false);
            }
        }).catch((error) => {
            resolve(false);
        });
    });
}

export { apiCall, setCredentials, getCredentials, clearCredentials, isLoggedIn };