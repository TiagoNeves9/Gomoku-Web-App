import React from "react";
import { Link } from "react-router-dom";
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
        <div>
            <Layout>
                <NavBar />
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
            </Layout>
        </div>
    )
}