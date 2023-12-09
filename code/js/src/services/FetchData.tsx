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

export async function reqData(url, method, data = {}, token = null) {
  const headers = {
    'Content-Type': 'application/json',
  };

  if (token) headers['Authorization'] = `Bearer ${token}`;

  const options = {
    method: method,
    headers: headers,
    body: JSON.stringify(data),
  };

  console.log("Token:", token);
  const response = await fetch(url, options);
  console.log(response.status, response.statusText);
  const content = await response.json();

  return content;
}