"use server"

export interface ConversionHistoryItem {
    id: number;
    originalCurrency: string,
    originalAmount: number,
    newCurrency: string;
    newAmount: number,
    createdDate: Date;
}

export async function useFetchConversions() {
    const conversionRequest = new Request("http://localhost:8080/conversions", {
        method: "GET",
    })
    const response = await fetch(conversionRequest)
    if (response.ok) {
        return await response.json() as Array<ConversionHistoryItem>
    } else return []
}
