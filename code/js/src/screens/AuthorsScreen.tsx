import React from "react";
import { useFetch } from "../custom-hooks/useFetch";
import NavBar, { Layout } from "./utils";


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
        <Layout>
            <NavBar />
            <div id="authors">
                <h1 className="h1">Authors</h1>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {authors.map((author, index) => (
                        <li key={index} className="li">
                            <span style={{ display: 'inline-block', width: '100px', textAlign: 'left' }}>{author.name}</span>
                            <span style={{ display: 'inline-block', width: '100px', textAlign: 'center' }}>{author.number}</span>
                            <span style={{ display: 'inline-block', width: '150px', textAlign: 'right' }}>{author.email}</span>
                        </li>
                    ))}
                </ul>
            </div>
        </Layout>
    )
}