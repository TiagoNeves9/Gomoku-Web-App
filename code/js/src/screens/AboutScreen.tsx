import React from "react";
import { useFetch } from "../custom-hooks/useFetch";


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
        <div id="about">
            <h1 className="h1">About Application</h1>
            <p
                className="version"
                style={{ listStyleType: 'none', paddingBottom: 250 }}
            > Version: {version}
            </p>
        </div>
    )
}