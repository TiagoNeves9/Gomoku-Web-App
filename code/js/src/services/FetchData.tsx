
export async function getData(url) {
    console.log(url);
    
    const response = await fetch(url, {
      method: "GET", 
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.json();
}

export async function postData(url, data = {}) {
    const response = await fetch(url, {
      method: "POST", 
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data), 
    });
    return response.json();
  }