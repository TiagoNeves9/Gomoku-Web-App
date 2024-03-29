import React from "react";
import { useFetch } from "../custom-hooks/useFetch";


export const AuthorsScreen = () => {
    const { data: content, loading, error } = useFetch<{
        properties: {
            authors: {
                number: number;
                name: string;
                email: string;
            }[]
        }
    }>({ uri: "api/authors" });

    if (loading) return <div>Loading...</div>;

    if (error || !content || !content.properties || !content.properties.authors)
        return <div>Error fetching data...</div>;

    const { authors } = content.properties;

    return (
        <div id="authors">
            <h1 className="h1">Authors</h1>
            <ul style={{ listStyleType: 'none', paddingBottom: 200 }}>
                {authors.map((author, index) => (
                    <li key={index} className="li">
                        <span
                            style={{ display: 'inline-block', width: '150px', textAlign: 'left' }}>{author.name}
                        </span>
                        <span
                            style={{ display: 'inline-block', width: '80px', textAlign: 'left' }}>{author.number}
                        </span>
                        <span
                            style={{ display: 'inline-block', width: '150px', textAlign: 'left' }}>{author.email}
                        </span>
                    </li>
                ))}
            </ul>
        </div>
    )
}