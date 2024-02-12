'use client'
import {useEffect, useState} from "react";
import {fetchAccount, fetchExpensesForAccount} from "@/actions/clientActions";
import ExpensesTable from "@/components/expense/ExpensesTable";
import Link from "next/link";

export default function AccountPage({params}) {

    const formatter = Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    })

    const [expenses, setExpenses] = useState([]);
    const [accountInfo, setAccountInfo] = useState({balance: 0});
    const accountId = params.id;

    useEffect(() => {
        fetchExpensesForAccount(accountId).then(
            res => setExpenses(res)
        )
        fetchAccount(accountId).then(
            res => setAccountInfo(res)
        );
    }, []);

    return (
        <div>
            <h1>{accountInfo.name} account</h1>
            <p>Balance: {formatter.format(accountInfo.balance)}</p>
            <Link href={`/account/edit/${accountId}`}><button>Edit info</button></Link>
            <ExpensesTable data={expenses}/>
        </div>
    );
}