const API_BASE_URL = "https://dummyjson.com";

export async function getAccount(accountId) {
    const response = await fetch(
        `${API_BASE_URL}/users/${accountId}`
    );

    if (!response.ok) {
        throw new Error("Failed to fetch account");
    }

    return response.json();
}
