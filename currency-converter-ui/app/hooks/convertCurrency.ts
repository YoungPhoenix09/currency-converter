interface ConvertRequest {
    originalCurrency: string;
    newCurrency: string;
    amount: number;
}

interface ConversionResult {
    convertedAmount: number;
    exchangeRate: number;
    rateRetrievalDate: Date;
}

export default async function useConvertCurrency(req: ConvertRequest) {
    const conversionRequest = new Request("http://localhost:8080/convert", {
        method: "POST",
        body: JSON.stringify(req),
        headers: new Headers({
            "Content-Type": "application/json"
        })
    })
    const response = await fetch(conversionRequest)
    if (response.ok) {
        return await response.json() as ConversionResult
    } else return []
}
