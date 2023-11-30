import {useFetch} from "../custom-hooks/useFetch";
import React from "react";
import {Author} from "../domain/Authors";

export function AboutScreen() {
    const { data: authors, loading, error } =
        useFetch<Author[]>({ uri: "api/about" });
    if (loading)
        return <div>Loading...</div>;

    if (error || !authors)
        return <div>Error fetching data...</div>;

    return (
        <div>
            <h1>Authors</h1>
            <ul>
                {authors.map((author, index) => (
                    <li key={index}>
                        <span style={{ display: 'inline-block', width: '100px', textAlign: 'left' }}>{author.name}</span>
                        <span style={{ display: 'inline-block', width: '100px', textAlign: 'center' }}>{author.number}</span>
                        <span style={{ display: 'inline-block', width: '150px', textAlign: 'right' }}>{author.email}</span>
                    </li>
                ))}
            </ul>
        </div>
    )
}