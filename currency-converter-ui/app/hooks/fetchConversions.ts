interface ConversionHistoryItem {
    id: number;
    originalCurrency: string,
    originalAmount: number,
    newCurrency: string;
    newAmount: number,
    createdDate: Date;
}

interface ConversionResult {
    convertedAmount: number;
    exchangeRate: number;
    rateRetrievalDate: Date;
}

export default async function useFetchConversions() {
    const conversionRequest = new Request("http://localhost:8080/conversions", {
        method: "GET",
    })
    const response = await fetch(conversionRequest)
    if (response.ok) {
        return await response.json() as Array<ConversionHistoryItem>
    } else return []
}
