import { useFetch } from "../custom-hooks/useFetch";
import React from "react";
import { Author } from "../domain/Authors";
import { Link } from "react-router-dom";

export function AboutScreen() {
    const { data: content, loading, error } = useFetch<{
        properties: {
            version: string;
        }
    }>({ uri: "api/about" });

    if (loading) return <div>Loading...</div>;

    if (error || !content || !content.properties || !content.properties.version)
        return <div>Error fetching data...</div>;

    const { version } = content.properties;

    return (
        <div>
            <Link to="/">Return to home</Link>
            <h1>About</h1>
            <p>Version: {version}</p>
        </div>
    )
}