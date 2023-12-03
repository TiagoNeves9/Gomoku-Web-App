import { useState, useEffect } from 'react';


export function useFetch<T>(
    { uri }: { uri: string }
): { data: T | null; error: Error | null; loading: boolean } {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        let canceled = false;

        async function fetchData() {
            setLoading(true);
            try {
                const response = await fetch(uri);
                if (!response.ok) throw new Error('Network response was not ok.');

                const result = await response.json();
                if (!canceled) {
                    setData(result);
                    setError(null);
                }
            } catch (err) {
                if (!canceled) {
                    setError(err);
                    setData(null);
                }
            } finally {
                if (!canceled) setLoading(false);
            }
        }

        fetchData();

        return () => {
            canceled = true;
        };
    }, [uri]);

    return { data, error, loading };
}

export async function _fetch(uri: string, method: string = "GET", body?: Object) {
    const headers = {
        'Content-Type': 'application/json',
    };

    const options = {
        method,
        headers,
    };

    if (body) options['body'] = JSON.stringify(body);

    const response = await fetch(uri, options);
    const content = await response.json();

    return content;
}