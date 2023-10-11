# DAW project

This system will be composed of a centralized backend service and one or more frontend applications. 
The frontend applications will run on the user's devices, providing the interface between those users and the system. 
The backend service will manage all the game related data and enforce the game rules.

Frontend applications will communicate with the backend service using an HTTP API. 
These applications will not communicate directly between themselves. 
All communication should be done via the backend service, 
which has the responsibility of ensuring all the game rules are followed, 
as well as storing the game states and final outcomes.

TODO: Add links to all the existing documentation.