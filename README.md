# DAW project

This system will be composed of a centralized backend service and one or more frontend applications. 
The frontend applications will run on the user's devices, providing the interface between those users and the system. 
The backend service will manage all the game related data and enforce the game rules.

Frontend applications will communicate with the backend service using an HTTP API. 
These applications will not communicate directly between themselves. 
All communication should be done via the backend service, 
which has the responsibility of ensuring all the game rules are followed, 
as well as storing the game states and final outcomes.

YAML : https://github.com/isel-leic-daw/2023-daw-leic53d-2023-daw-leic53d-g09/blob/main/docs/GomokuAPI.yaml
Relatório : https://github.com/isel-leic-daw/2023-daw-leic53d-2023-daw-leic53d-g09/blob/main/docs/Relat%C3%B3rio%20de%20Projeto-G09-DAW.docx
