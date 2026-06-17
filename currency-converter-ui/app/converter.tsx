"use client"

import { ConversionHistoryItem, useFetchConversions } from "./hooks/fetchConversions";
import { ConversionResult, useConvertCurrency} from "./hooks/convertCurrency";
import { useEffect, useState } from "react";

const Converter = function() {
  const [conversionResult, setConversionResult] = useState<ConversionResult | null>()
  const [conversionHistory, setConversionHistory] = useState<Array<ConversionHistoryItem>>([])
  
  useEffect(() => {
    useFetchConversions()
    .then(setConversionHistory)
  }, [conversionResult])
  

  async function handleConversion(formData: FormData) {
    const convertRequest = {
      originalCurrency: formData.get("originalCurrency")?.toString() ?? "",
      newCurrency: formData.get("newCurrency")?.toString() ?? "",
      amount: formData.get("amount")?.valueOf() as number,
    }

    const result = await useConvertCurrency(convertRequest)
    setConversionResult(result)
  }

  return (
    <div className="flex flex-col flex-1 items-center justify-center bg-zinc-50 font-sans dark:bg-black">
      <main className="flex flex-1 w-full max-w-3xl flex-col items-center py-32 px-16 bg-white dark:bg-black sm:items-start">
        <h1 className="text-3xl mb-2">Currency Converter</h1>
        <form action={handleConversion}>
          <div className="flex">
            <div className="mr-3">
              <label className="text-white">
                Base Currency
              </label>
              <div className="border-white border-2 rounded-sm max-w-25">
                <input className="px-2 outline-none" type="text" name="originalCurrency" />
              </div>
            </div>
            <div className="mr-3">
              <label className="text-white">
                Quote Currency
              </label>
              <div className="border-white border-2 rounded-sm max-w-25">
                <input className="px-2 outline-none" type="text" name="newCurrency" />
              </div>
            </div>
          </div>
          <div className="my-3">
            <label className="text-white">
              Amount
            </label>
            <div className="border-white border-2 rounded-sm">
              <input className="px-2 outline-none" name="amount" />
            </div>
          </div>
          <button type="submit" className="p-4 bg-white text-black rounded-sm cursor-pointer">Convert</button>
        </form>
        { conversionResult ? (
          <div className="my-5">
            <h1 className="my-5 font-bold">Conversion Result</h1>
            <p className="text-white">Converted Amount: {conversionResult.convertedAmount}</p>
            <p className="text-white">Rate Used: {conversionResult.exchangeRate}</p>
            <p className="text-white">Last Fetch: {`${conversionResult.rateRetrievalDate}`}</p>
          </div>
        ) 
        : (<></>) }
        { conversionHistory ? (
          <div className="my-5">
            <h1 className="my-5 font-bold">Conversion History</h1>
            { conversionHistory.map(c => (
              <p key={c.id} className="text-white">{`${c.originalAmount} ${c.originalCurrency} to ${c.newAmount} ${c.newCurrency} on ${c.createdDate}`}</p>
            )) }
          </div>
        ) 
        : (<></>) }
      </main>
    </div>
  );
}

export { Converter }
