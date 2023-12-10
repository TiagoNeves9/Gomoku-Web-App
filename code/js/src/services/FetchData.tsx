
export async function getData(url) {
    
  const response = await fetch(url, {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
      throw new Error(`Error fetching data from ${url}: ${response.statusText}`);
  }

  try {
      return await response.json();
  } catch (error) {
      console.error(`Error parsing JSON from ${url}:`, error);
      throw new Error(`Error parsing JSON from ${url}`);
  }
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

export async function deleteData(url, data ={}) {
  const response = await fetch(url, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });
  return response.json();
}
